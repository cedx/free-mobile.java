package io.belin.free_mobile;

import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Sends messages by SMS to a Free Mobile account.
 */
public final class Client {

	/**
	 * The Free Mobile account.
	 */
	public final String account;

	/**
	 * The Free Mobile API key.
	 */
	public final String apiKey;

	/**
	 * The base URL of the remote API endpoint.
	 */
	public final URI baseUrl;

	/**
	 * Creates a new client.
	 * @param account The Free Mobile account.
	 * @param apiKey The Free Mobile API key.
	 */
	public Client(String account, String apiKey) {
		this(account, apiKey, null);
	}

	/**
	 * Creates a new client.
	 * @param account The Free Mobile account.
	 * @param apiKey The Free Mobile API key.
	 * @param baseUrl The base URL of the remote API endpoint.
	 */
	public Client(String account, String apiKey, URI baseUrl) {
		this.account = Objects.requireNonNull(account);
		this.apiKey = Objects.requireNonNull(apiKey);
		this.baseUrl = Objects.requireNonNullElse(baseUrl, URI.create("https://smsapi.free-mobile.fr/"));
	}

	/**
	 * Sends a SMS message to the underlying account.
	 * @param text The message text.
	 * @throws Exception An error occurred while sending the message.
	 */
	public void sendMessage(String text) throws Exception {
		try {
			var response = HttpClient.newHttpClient().send(createRequest(text), BodyHandlers.discarding());
			var status = response.statusCode() / 100;
			if (status != 2) switch (status) {
				case 4 -> throw new Exception("The provided credentials are invalid.");
				default -> throw new Exception("An error occurred while sending the message.");
			}
		}
		catch (InterruptedException|IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Asynchronously sends a SMS message to the underlying account.
	 * @param text The message text.
	 * @return Completes when the message has been sent.
	 */
	@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
	public CompletableFuture<Void> sendMessageAsync(String text) {
		return HttpClient.newHttpClient().sendAsync(createRequest(text), BodyHandlers.discarding()).handle((response, e) -> {
			if (e != null) throw new RuntimeException(new Exception(e));
			return switch (response.statusCode() / 100) {
				case 2 -> null;
				case 4 -> throw new RuntimeException(new Exception("The provided credentials are invalid."));
				default -> throw new RuntimeException(new Exception("An error occurred while sending the message."));
			};
		});
	}

	/**
	 * Creates the HTTP request corresponding to the specified message.
	 * @param text The message text.
	 * @return The newly created HTTP request.
	 */
	private HttpRequest createRequest(String text) {
		var strippedText = Objects.requireNonNull(text).strip();

		var query = new HashMap<String, String>();
		query.put("msg", strippedText.substring(0, Math.min(160, strippedText.length())));
		query.put("pass", apiKey);
		query.put("user", account);

		var charset = Charset.defaultCharset();
		var url = baseUrl.resolve("sendmsg?" + query.entrySet()
			.stream()
			.map(entry -> URLEncoder.encode(entry.getKey(), charset) + "=" + URLEncoder.encode(entry.getValue(), charset))
			.collect(Collectors.joining("&")));

		return HttpRequest.newBuilder(url).GET().build();
	}

	/**
	 * An exception caused by an error in a {@link Client} request.
	 */
	public static class Exception extends java.lang.Exception {

		/**
		 * The serialization version number.
		 */
		@Serial private static final long serialVersionUID = 1L;

		/**
		 * Creates a new client exception.
		 * @param message The error message.
		 */
		public Exception(String message) {
			super(message);
		}

		/**
		 * Creates a new client exception.
		 * @param cause The original cause of the error.
		 */
		public Exception(Throwable cause) {
			super(cause);
		}
	}
}
