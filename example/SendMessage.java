import io.belin.free_mobile.Client;
import io.belin.free_mobile.ClientException;

/**
 * Sends an SMS notification.
 */
@SuppressWarnings({"PMD.NoPackage", "PMD.UseUtilityClass"})
class SendMessage {

	/**
	 * Application entry point.
	 * @param args The command line arguments.
	 */
	@SuppressWarnings("PMD.SystemPrintln")
	public static void main(String... args) {
		try {
			var client = new Client("your account identifier", "your API key");
			client.sendMessage("Hello World from Java!");
			System.out.println("The message was sent successfully.");
		}
		catch (ClientException e) {
			System.err.println("An error occurred: " + e.getMessage());
		}
	}
}
