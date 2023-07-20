import haxe.xml.Access;
import haxe.zip.Reader;
import sys.FileSystem;
import sys.io.File;
using Lambda;
using StringTools;
using haxe.io.Path;
using haxe.zip.Tools;

/** Runs the test suite. **/
function main() {
	final pkgPath = Tools.javaPackage.replace(".", "/");
	Sys.command("lix Build --debug");
	Tools.setClassPath();
	Sys.command('javac -d lib -g -Xlint:all,-path,-processing test/$pkgPath/*.java');

	final jacocoJar = "jacocoagent.jar";
	extractJacocoJar(jacocoJar, "var");

	final jacocoExec = "var/jacoco.exec";
	final exitCode = Sys.command("java", [
		'-javaagent:var/$jacocoJar=append=false,destfile=$jacocoExec,includes=${Tools.javaPackage}.*',
		"org.junit.platform.console.ConsoleLauncher",
		'--select-package=${Tools.javaPackage}'
	]);

	if (exitCode != 0) Sys.exit(exitCode);
	generateCoverageReport(jacocoExec, 'lib/$pkgPath');
}

/** Extracts the JAR of the JaCoCo Agent into the specified output directory. **/
private function extractJacocoJar(jarName: String, outputDirectory: String) {
	final path = Path.join([outputDirectory, jarName]);
	if (FileSystem.exists(path)) return;

	final dependencies = new Access(Xml.parse(File.getContent("ivy.xml")).firstElement()).node.dependencies.nodes.dependency;
	final version = dependencies.filter(dependency -> dependency.att.name == "org.jacoco.agent").pop().att.rev;
	final input = File.read(Path.join([
		Sys.getEnv(Sys.systemName() == "Windows" ? "USERPROFILE" : "HOME"),
		'.ivy2/cache/org.jacoco/org.jacoco.agent/jars/org.jacoco.agent-$version.jar'
	]));

	final entry = Reader.readZip(input).filter(entry -> entry.fileName == jarName).pop();
	if (entry.compressed) entry.uncompress();
	File.saveBytes(path, entry.data);
	input.close();
}

/** Generates the coverage report from the specified JaCoCo execution file and class directory. **/
private function generateCoverageReport(execFile: String, classDirectory: String) {
	FileSystem.readDirectory(classDirectory)
		.filter(file -> file.endsWith("Test.class"))
		.iter(file -> FileSystem.deleteFile('$classDirectory/$file'));

	Sys.command("java", ["org.jacoco.cli.internal.Main", "report",
		execFile,
		'--classfiles=$classDirectory',
		"--html=var/coverage", // TODO useless
		"--sourcefiles=src",
		"--xml=var/coverage.xml"
	]);
}
