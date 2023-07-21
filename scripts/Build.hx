using StringTools;

/** Builds the project. **/
function main() {
	final debug = Sys.args().contains("--debug");
	Tools.setClassPath();
	Sys.command('javac -d bin ${debug ? "-g -Xlint:all,-path,-processing" : ""} src/${Tools.javaPackage.replace(".", "/")}/*.java');
}
