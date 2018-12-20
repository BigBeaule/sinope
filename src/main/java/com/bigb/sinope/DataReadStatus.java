package com.bigb.sinope;

/**
 * The supported data read statuses.
 * 
 * @author Francis Beaule
 *
 */
public enum DataReadStatus {
    /**
     * Request received and queued; waiting for device answer. Should receive another answer of type
     * {@link DataReadStatus#DATA_ANSWER} or {@link DataReadStatus#NO_ANSWER} for this request.
     */
    WAIT_FOR_ANSWER(0),

    /**
     * Request received and queued, wait for device answer not supported. No more answer for this
     * request will be generated.
     */
    DONE(1),

    /**
     * Request aborted / removed from queue. No more answer for this request will be generated.
     */
    ABORTED(2),

    /**
     * Answer containing the data to the request. No more answer for this request will be generated.
     */
    DATA_ANSWER(10),

    /**
     * A device on the network is sending an unsolicited data report to the server (API).
     */
    DATA_NOTIFICATION(11),

    /**
     * Request failed and no more answer for this request will be generated.
     */
    REQUEST_FAILED(-1),

    /**
     * Queue full so retry later. No more answer for this request will be generated.
     */
    BUFFER_FULL(-2),
    
    /**
     * Reserved and should not happen.
     */
    RESERVED(-3),

    /**
     * Device is not responding. No more answer for this request will be generated.
     */
    NO_ANSWER(-4),

    /**
     * Request not found in queue.
     */
    ABORT_FAILED(-5),

    /**
     * The destination DeviceID is invalid or not a member of this network. No more answer for this
     * request will be generated.
     */
    UNKNOWN_DEVICE(-6);

    /**
     * The signed status value.
     */
    private final byte value;

    /**
     * @param value The signed status value.
     */
    private DataReadStatus(int value) {
        this.value = (byte) value;
    }

    /**
     * @return The signed status value.
     */
    public byte getValue() {
        return value;
    }

    /**
     * Retrieves a {@link DataReadStatus} from the status value.
     * 
     * @param value The signed status value.
     * @return The {@link DataReadStatus} or null if not found
     */
    public static DataReadStatus getStatus(int value) {
        byte b = (byte) value;
        for (DataReadStatus status : DataReadStatus.values()) {
            if (status.value == b) {
                return status;
            }
        }

        return null;
    }
}
