package DependencyDownloader.DownloaderProto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DependencyDownloader {

	public void downloadDependencies() throws IOException {
		Path inputDir = Paths.get("Input");
		try {
			Files.createDirectories(inputDir);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(Files.exists(inputDir.resolve("pom.xml"))) {	
			ProcessBuilder mvnInit = new ProcessBuilder("cmd","/c", "start " , " mvn -Dclassifier=sources -DexcludeTransitive=true dependency:copy-dependencies");
				mvnInit.directory(inputDir.toFile());
				try {
					Process p = mvnInit.start();
					p.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		if(Files.exists(inputDir.resolve("package.json"))) {	
			ProcessBuilder npmInit = new ProcessBuilder("cmd","/c", "start " , " npm install --legacy-peer-deps");
				npmInit.directory(inputDir.toFile());
				try {
					Process p = npmInit.start();
					p.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


