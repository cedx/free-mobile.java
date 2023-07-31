package io.belin.free_mobile;

import java.io.Serial;

/**
 * An exception caused by an error in a {@link Client} request.
 */
public class ClientException extends Exception {

	/**
	 * The serialization version number.
	 */
	@Serial private static final long serialVersionUID = 1L;

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 */
	public ClientException(String message) {
		super(message);
	}

	/**
	 * Creates a new client exception.
	 * @param cause The original cause of the error.
	 */
	public ClientException(Throwable cause) {
		super(cause);
	}
}
