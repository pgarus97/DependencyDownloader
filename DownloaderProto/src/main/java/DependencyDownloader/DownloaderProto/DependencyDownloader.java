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
			System.out.println("Starting maven download...");
			ProcessBuilder mvnInit = new ProcessBuilder("cmd","/c",  " mvn -Dclassifier=sources -DexcludeTransitive=true dependency:copy-dependencies");
			mvnInit.directory(inputDir.toFile());
			ProcessBuilder mvnSRCInit = new ProcessBuilder("cmd","/c",  " mvn -Dclassifier=src -DexcludeTransitive=true dependency:copy-dependencies");
			mvnSRCInit.directory(inputDir.toFile());
			try {
				Process p = mvnInit.start();
				p.waitFor();
				Process p2 = mvnSRCInit.start();
				p2.waitFor();
				System.out.println("Maven download finished!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//download dependencies from npm 
		if(Files.exists(inputDir.resolve("package.json"))) {
			System.out.println("Starting npm download...");

			ProcessBuilder npmInit = new ProcessBuilder("cmd","/c",  " npm install --legacy-peer-deps");
				npmInit.directory(inputDir.toFile());
				try {
					Process p = npmInit.start();
					p.waitFor();
					System.out.println("NPM download finished!");
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
		
		//runs scancode on the downloaded sources
		Scancode sc = new Scancode();
		sc.runScancode();
	}
	
}



