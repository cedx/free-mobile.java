package io.belin.free_mobile;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of the {@link Client} class.
 */
@DisplayName("Client")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ClientTest {

	@Test
	@DisplayName("sendMessage(): should throw a `Client.Exception` if a network error occurred.")
	void networkError() {
		var client = new Client("anonymous", "secret", "http://localhost:10000");
		assertThrows(Client.Exception.class, () -> client.sendMessage("[Sync] Hello World!"));
	}

	@Test
	@DisplayName("sendMessageAsync(): should complete exceptionnaly with a `Client.Exception` if a network error occurred.")
	void networkErrorAsync() {
		var client = new Client("anonymous", "secret", "http://localhost:10000");
		assertThrows(ExecutionException.class, () -> client.sendMessageAsync("[Async] Hello World!").get());
	}

	@Test
	@DisplayName("sendMessage(): should throw a `Client.Exception` if the credentials are invalid.")
	void invalidCredentials() {
		var client = new Client("anonymous", "secret");
		assertThrows(Client.Exception.class, () -> client.sendMessage("[Sync] Hello World!"));
	}

	@Test
	@DisplayName("sendMessageAsync(): should complete exceptionnaly with a `Client.Exception` if the credentials are invalid.")
	void invalidCredentialsAsync() {
		var client = new Client("anonymous", "secret");
		assertThrows(ExecutionException.class, () -> client.sendMessageAsync("[Async] Hello World!").get());
	}

	@Test
	@DisplayName("sendMessage(): should send SMS messages if the credentials are valid.")
	void validCredentials() {
		var client = new Client(System.getenv("FREEMOBILE_ACCOUNT"), System.getenv("FREEMOBILE_API_KEY"));
		assertDoesNotThrow(() -> client.sendMessage("[Sync] Hello Cédric, from Java!"));
	}

	@Test
	@DisplayName("sendMessageAsync(): should send SMS messages if the credentials are valid.")
	void validCredentialsAsync() {
		var client = new Client(System.getenv("FREEMOBILE_ACCOUNT"), System.getenv("FREEMOBILE_API_KEY"));
		assertDoesNotThrow(() -> client.sendMessageAsync("[Async] Hello Cédric, from Java!").get());
	}
}
