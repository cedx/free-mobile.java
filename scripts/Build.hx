using StringTools;

/** Builds the project. **/
function main() {
	final debug = Sys.args().contains("--debug");
	final pkg = "io.belin.free_mobile";
	Tools.setClassPath();
	Sys.command('javac -d bin ${debug ? "-g -Xlint:all,-processing" : ""} src/${pkg.replace(".", "/")}/*.java');
}
