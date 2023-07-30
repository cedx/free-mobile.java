package io.belin.free_mobile;

import java.net.http.HttpResponse;

/**
 * An exception caused by an error in a {@link Client} request.
 */
public class ClientException extends Exception {

	/**
	 * The serialization version number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The optional HTTP response causing the error.
	 */
	public final HttpResponse<Void> response;

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 * @param cause The original cause of the error.
	 */
	public ClientException(String message, Throwable cause) {
		super(message, cause);
		this.response = null;
	}

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 * @param cause The original cause of the error.
	 */
	public ClientException(String message, HttpResponse<Void> response) {
		super(message);
		this.response = response;
	}
}
