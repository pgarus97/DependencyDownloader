package DependencyDownloader.DownloaderProto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;


public class DependencyDownloader {

	public void downloadDependencies() throws IOException {
		Path inputDir = Paths.get("Input");
		try {
			Files.createDirectories(inputDir);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//download dependencies from maven with source and src classifier
		if(Files.exists(inputDir.resolve("pom.xml"))) {	
			ProcessBuilder mvnInit = new ProcessBuilder("cmd","/c",  " mvn -Dclassifier=sources -DexcludeTransitive=true dependency:copy-dependencies");
			mvnInit.directory(inputDir.toFile());
			ProcessBuilder mvnSRCInit = new ProcessBuilder("cmd","/c",  " mvn -Dclassifier=src -DexcludeTransitive=true dependency:copy-dependencies");
			mvnSRCInit.directory(inputDir.toFile());
			try {
				Process p = mvnInit.start();
				p.waitFor();
				Process p2 = mvnSRCInit.start();
				p2.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//download dependencies from npm 
		if(Files.exists(inputDir.resolve("package.json"))) {	
			ProcessBuilder npmInit = new ProcessBuilder("cmd","/c",  " npm install --legacy-peer-deps");
				npmInit.directory(inputDir.toFile());
				try {
					Process p = npmInit.start();
					p.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		UnZipper zip = new UnZipper();
    	String sourcePath = "Input/target/dependency";
    	
    	zip.unzipAll(sourcePath);
    	
    	//deletes output folder (sources.jars)
    	File sourceOutDir = new File(sourcePath);
		FileUtils.deleteDirectory(sourceOutDir);
		
        //move file directories into correct positions for scancode scan
    	Files.move(Paths.get("Input/node_modules"), Paths.get("Output/node_modules"), StandardCopyOption.REPLACE_EXISTING);
    	Files.move(Paths.get("Output"), Paths.get("scancode-toolkit-21.3.31/Output"), StandardCopyOption.REPLACE_EXISTING);

    	//runs scancode scan over all downloaded sources
    	ProcessBuilder scancodeInit = new ProcessBuilder("cmd","/c" , " scancode -c -n 16 --json-pp output.json Output");
		Path scancodeDir = Paths.get("scancode-toolkit-21.3.31");
		scancodeInit.directory(scancodeDir.toFile());
		try {
			Process p = scancodeInit.start();
			p.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//only for testing; deletes the created unzipped .jars and the node_modules 
		//File scanOutDir = new File("scancode-toolkit-21.3.31/Output");
		//FileUtils.deleteDirectory(scanOutDir);
	}
	
}



