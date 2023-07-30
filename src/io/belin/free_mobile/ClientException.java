package io.belin.free_mobile;

import java.io.Serial;
import java.net.http.HttpResponse;

/**
 * An exception caused by an error in a {@link Client} request.
 */
public class ClientException extends Exception {

	/**
	 * The serialization version number.
	 */
	@Serial private static final long serialVersionUID = 1L;

	/**
	 * The optional HTTP response that caused the error.
	 */
	public final HttpResponse<Void> response;

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 * @param cause The original cause of the error.
	 */
	public ClientException(Throwable cause) {
		super(cause);
		this.response = null;
	}

	/**
	 * Creates a new client exception.
	 * @param message The error message.
	 * @param response The HTTP response that caused the error.
	 */
	public ClientException(String message, HttpResponse<Void> response) {
		super(message);
		this.response = response;
	}
}
