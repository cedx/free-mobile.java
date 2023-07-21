package io.belin.free_mobile;

use Nyholm\Psr7\Uri;
use Psr\Http\Message\UriInterface;

/**
 * Sends messages by SMS to a Free Mobile account.
 */
final class Client {

	/**
	 * The Free Mobile account.
	 */
	readonly string $account;

	/**
	 * The Free Mobile API key.
	 */
	readonly string $apiKey;

	/**
	 * The base URL of the remote API endpoint.
	 */
	readonly UriInterface $baseUrl;

	/**
	 * Creates a new client.
	 * @param account The Free Mobile account.
	 * @param apiKey The Free Mobile API key.
	 * @param baseUrl The base URL of the remote API endpoint.
	 */
	function __construct(string $account, string $apiKey, string $baseUrl = "https://smsapi.free-mobile.fr/") {
		$this->account = $account;
		$this->apiKey = $apiKey;
		$this->baseUrl = new Uri($baseUrl);
	}

	/**
	 * Sends a SMS message to the underlying account.
	 * @param text The message text.
	 * @throws \Psr\Http\Client\ClientExceptionInterface An error occurred while sending the message.
	 */
	function sendMessage(string $text): void {
		$handle = curl_init((string) $this->baseUrl->withPath("{$this->baseUrl->getPath()}sendmsg")->withQuery(http_build_query([
			"msg" => mb_substr(trim($text), 0, 160),
			"pass" => $this->apiKey,
			"user" => $this->account
		], arg_separator: "&", encoding_type: PHP_QUERY_RFC3986)));

		if (!$handle) throw new ClientException("Unable to allocate the cURL handle.", 500);
		if (!curl_exec($handle)) throw new ClientException("An error occurred while sending the message.", 500);

		$code = intdiv($status = curl_getinfo($handle, CURLINFO_RESPONSE_CODE), 100);
		if ($code != 2) {
			$message = $code == 4 ? "The provided credentials are invalid." : "An error occurred while sending the message.";
			throw new ClientException($message, $status);
		}
	}
}
