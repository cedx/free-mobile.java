using StringTools;

/** Runs the test suite. **/
function main() {
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	Sys.command('javac -d lib -g -Xlint:all,-path,-processing test/${Tools.javaPackage.replace(".", "/")}/*.java');

	final exitCode = Sys.command('java org.junit.platform.console.ConsoleLauncher --select-package=${Tools.javaPackage}');
	if (exitCode != 0) Sys.exit(exitCode);
}
