/** Runs the test suite. **/
function main() {
	Sys.command("lix Build --debug");

	final pattern = "io/belin/free_mobile/*.java";
	Tools.setClassPath();
	Sys.command('javac -d bin -g -Xlint:all,-processing test/$pattern');

	final exitCode = Sys.command("java org.junit.platform.console.ConsoleLauncher --select-package=io.belin.free_mobile");
	if (exitCode != 0) Sys.exit(exitCode);
}
