using StringTools;

/** Runs the test suite. **/
function main() {
	final pkg = "io.belin.free_mobile";
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	Sys.command('javac -d bin -g -Xlint:all,-processing test/${pkg.replace(".", "/")}/*.java');

	final exitCode = Sys.command("java org.junit.platform.console.ConsoleLauncher --select-package=io.belin.free_mobile");
	if (exitCode != 0) Sys.exit(exitCode);
}
