package io.belin.free_mobile;

import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link Client} class.
 */
@DisplayName("Client")
final class ClientTest {

	@Test
	@DisplayName("sendMessage(): should throw a `ClientExceptionInterface` if a network error occurred.")
	void networkError() {
		var client = new Client("anonymous", "secret", URI.create("http://localhost:10000/"));
		assertThrows(ClientException.class, () -> client.sendMessage("Hello World!"));
	}

	@Test
	@DisplayName("sendMessage(): should throw a `ClientExceptionInterface` if the credentials are invalid.")
	void invalidCredentials() {
		var client = new Client("anonymous", "secret");
		assertThrows(ClientException.class, () -> client.sendMessage("Hello World!"));
	}

	@Test
	@DisplayName("sendMessage(): should send SMS messages if the credentials are valid.")
	void validCredentials() {
		var client = new Client(System.getenv("FREEMOBILE_ACCOUNT"), System.getenv("FREEMOBILE_API_KEY"));
		assertDoesNotThrow(() -> client.sendMessage("Hello Cédric, from Java!"));
	}
}
