package io.belin.free_mobile;

import java.io.IOException;

/**
 * An exception caused by an error in a {@link Client} request.
 */
class ClientException extends IOException {

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 */
	public ClientException(String message) {
		super(message);
	}

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 * @param cause The original cause of the error.
	 */
	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
