package com.bigb.sinope;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bigb.sinope.request.CommandRequest;

/**
 * A Sinope Connection.
 * 
 * @author Francis Beaule
 *
 */
public class SinopeConnection {
    /**
     * The logger for this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SinopeConnection.class);

    /**
     * The GT125 host.
     */
    private final String host;

    /**
     * The socket.
     */
    private Socket socket = new Socket();

    /**
     * The writer.
     */
    private SinopeDataWriter writer;

    /**
     * The reader.
     */
    private SinopeDataReader reader;

    /**
     * The executor.
     */
    private ExecutorService executor;

    /**
     * @param host The GT125 host.
     */
    public SinopeConnection(String host) {
        this.host = host;
    }

    /**
     * Connect to the Sinope gateway (GT125) and start reading on it.
     * 
     * @param answerCallback The callback for new answers
     * @throws IOException Error
     */
    public void connect(final SinopeConnectionAnswerCallback answerCallback) throws IOException {
        if (this.socket.isConnected()) {
            throw new IOException("Already connected to " + this.host);
        }

        this.socket.connect(new InetSocketAddress(this.host, 4550));
        this.reader = new SinopeDataReader(this.socket.getInputStream());
        this.writer = new SinopeDataWriter(this.socket.getOutputStream());

        this.executor = Executors.newSingleThreadExecutor();
        this.executor.execute(() -> {
            while (true) {
                try {
                    answerCallback.newAnswer(this.reader.read());
                } catch (Throwable t) {
                    LOG.error("Error while reading", t);
                }
            }
        });
    }

    /**
     * Sends a new command to a Sinope device through the gateway (GT125).
     * 
     * @param command The command to send.
     * @throws IOException Error
     */
    public void newCommand(CommandRequest command) throws IOException {
        if (!this.socket.isConnected()) {
            throw new IOException("Not connected");
        }

        command.sendRequest(this.writer);
    }

    /**
     * Disconnect the Sinope connection which can be reused.
     * 
     * @throws IOException Not connected
     */
    public void disconnect() throws IOException {
        if (!this.socket.isConnected()) {
            throw new IOException("Not connected");
        }

        this.executor.shutdownNow();

        try {
            this.socket.close();
        } catch (Exception e) {
            LOG.warn("Error while disconnecting the socket", e);
        }

        this.socket = new Socket();
        this.executor = null;
        this.reader = null;
        this.writer = null;
    }

    /**
     * Callback for received answers.
     * 
     * @author Francis Beaule
     *
     */
    private static interface SinopeConnectionAnswerCallback {
        /**
         * @param json The JSON answer object
         */
        void newAnswer(JsonObject json);
    }
}
