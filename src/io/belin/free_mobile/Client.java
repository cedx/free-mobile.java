package io.belin.free_mobile;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Objects;
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
		this.account = account;
		this.apiKey = apiKey;
		this.baseUrl = Objects.requireNonNullElse(baseUrl, URI.create("https://smsapi.free-mobile.fr/"));
	}

	/**
	 * Sends a SMS message to the underlying account.
	 * @param text The message text.
	 * @throws ClientException An error occurred while sending the message.
	 */
	public void sendMessage(String text) throws ClientException {
		try {
			var response = HttpClient.newHttpClient().send(createRequest(text), BodyHandlers.discarding());
			var status = response.statusCode() / 100;
			if (status != 2) switch (status) {
				case 4 -> throw new ClientException("The provided credentials are invalid.", response);
				default -> throw new ClientException("An error occurred while sending the message.", response);
			}
		}
		catch (IOException|InterruptedException e) {
			throw new ClientException("An error occurred while sending the message.", e);
		}
	}

	/*
	public CompletableFuture<Void> sendMessageAsync(String text) {
		// TODO
	}*/

	/**
	 * Creates the HTTP request corresponding to the specified message.
	 * @param text The message text.
	 * @return The newly created HTTP request.
	 */
	private HttpRequest createRequest(String text) {
		var strippedText = text.strip();

		var query = new HashMap<String, String>();
		query.put("msg", strippedText.substring(0, Math.min(160, strippedText.length())));
		query.put("pass", apiKey);
		query.put("user", account);

		var url = baseUrl.resolve("sendmsg?" + query.entrySet()
			.stream()
			.map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Charset.defaultCharset()))
			.collect(Collectors.joining("&")));

		return HttpRequest.newBuilder(url).GET().build();
	}
}
