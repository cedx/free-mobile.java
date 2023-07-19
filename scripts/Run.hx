/** Runs a Java main class. **/
function main() {
	Tools.setClassPath();
	Sys.command("java", Sys.args());
}
