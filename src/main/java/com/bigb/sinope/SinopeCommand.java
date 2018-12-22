package com.bigb.sinope;

public enum SinopeCommand {
	/**
	 * A ping request/answer.
	 */
	PING("Ping"),

	/**
	 * A login request/answer.
	 */
	LOGIN("Login"),

	/**
	 * A login request/answer.
	 */
	DEVICE_LINK("Device Link"),

	/**
	 * An authentication key request/answer.
	 */
	AUTH_KEY("Authentication Key"),

	/**
	 * Generic type when reading a device data. The real command will be in the field {@link JsonFields#TYPE}.
	 */
	DATA_READ("Data Read"),

	/**
	 * A read room temperature request/answer.
	 */
	ROOM_TEMPERATURE("Read Room Temperature"),

	/**
	 * A read room set point request/answer.
	 */
	ROOM_SET_POINT("Read Room Set Point"),

	/**
	 * A read room heat level request/answer.
	 */
	ROOM_HEAT_LVL("Read Room Heat Level"),

	/**
	 * A read room load value request/answer.
	 */
	ROOM_LOAD("Read Room Load Value"),

	/**
	 * A read outdoor temperature request/answer.
	 */
	OUTDOOR_TEMPERATURE("Read Outdoor Temperature"),

	/**
	 * A check for the away status request/answer.
	 */
	AWAY_STATUS("Read Away Status");

	private final String cmdName;

	private SinopeCommand(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getCommandName() {
		return this.cmdName;
	}

	public static SinopeCommand fromCommandName(String cmdName) {
		for (SinopeCommand cmd : SinopeCommand.values()) {
			if (cmd.getCommandName().equalsIgnoreCase(cmdName)) {
				return cmd;
			}
		}

		return null;
	}
}
