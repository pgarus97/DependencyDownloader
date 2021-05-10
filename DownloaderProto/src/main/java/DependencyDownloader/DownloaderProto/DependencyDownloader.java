package DependencyDownloader.DownloaderProto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


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
			ProcessBuilder mvnInit = new ProcessBuilder("cmd","/c",  " mvn -Dclassifier=sources -DexcludeTransitive=true dependency:copy-dependencies");
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
    	String path = "Input/target/dependency";
    	zip.unzipAll(path);
    	Path depFolder = Paths.get("Input/target/dependency");
        File index = depFolder.toFile();
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
    	
    	Files.move(Paths.get("Input/node_modules"), Paths.get("Output/node_modules"), StandardCopyOption.REPLACE_EXISTING);
    	Files.move(Paths.get("Output"), Paths.get("scancode-toolkit-21.3.31/Output"), StandardCopyOption.REPLACE_EXISTING);

    	
    	ProcessBuilder npmInit = new ProcessBuilder("cmd","/c", "start " , " scancode -c -n 4 --json-pp output.json Output");
		Path scancodeDir = Paths.get("scancode-toolkit-21.3.31");
		npmInit.directory(scancodeDir.toFile());
		try {
			Process p = npmInit.start();
			p.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//only for testing 
        Path unzipPath = Paths.get("scancode-toolkit-21.3.31/Output");
        File index2 = unzipPath.toFile();
        String[]entries2 = index2.list();
        for(String s: entries2){
            File currentFile = new File(index2.getPath(),s);
            currentFile.delete();
        }
	}
}



