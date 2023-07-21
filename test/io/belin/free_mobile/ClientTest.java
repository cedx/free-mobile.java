package io.belin.free_mobile;

use PHPUnit\Framework\TestCase;
use PHPUnit\Framework\Attributes\{Test, TestDox};
use Psr\Http\Client\ClientExceptionInterface;
use function PHPUnit\Framework\{assertThat, isNull};

/**
 * Tests the features of the {@see Client} class.
 */
#[TestDox("Client")]
final class ClientTest extends TestCase {

	#[Test]
	#[TestDox("sendMessage(): should throw a `ClientExceptionInterface` if a network error occurred.")]
	function networkError(): void {
		$this->expectException(ClientExceptionInterface::class);
		$client = new Client("anonymous", "secret", baseUrl: "http://localhost:10000/");
		$client->sendMessage("Hello World!");
	}

	#[Test]
	#[TestDox("sendMessage(): should throw a `ClientExceptionInterface` if the credentials are invalid.")]
	function invalidCredentials(): void {
		$this->expectException(ClientExceptionInterface::class);
		$client = new Client("anonymous", "secret");
		$client->sendMessage("Hello World!");
	}

	#[Test]
	#[TestDox("sendMessage(): should send SMS messages if the credentials are valid.")]
	function validCredentials(): void {
		$client = new Client(getenv("FREEMOBILE_ACCOUNT") ?: "", getenv("FREEMOBILE_API_KEY") ?: "");
		assertThat($client->sendMessage("Hello Cédric, from PHP!"), isNull()); // @phpstan-ignore-line
	}
}
