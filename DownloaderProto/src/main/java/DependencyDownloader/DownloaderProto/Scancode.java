package DependencyDownloader.DownloaderProto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Scancode {

	public void runScancode() throws IOException {
		System.out.println("Starting scancode scan...");

		//move file directories into correct positions for scancode scan
		//TODO eventually create variable paths as parameters
    	Files.move(Paths.get("Input/node_modules"), Paths.get("Output/node_modules"), StandardCopyOption.REPLACE_EXISTING);
    	Files.move(Paths.get("Output"), Paths.get("scancode-toolkit-21.3.31/Output"), StandardCopyOption.REPLACE_EXISTING);

    	//runs scancode scan over all downloaded sources
    	ProcessBuilder scancodeInit = new ProcessBuilder("cmd","/c" , " scancode -c -n 16 --json-pp output.json Output");
		Path scancodeDir = Paths.get("scancode-toolkit-21.3.31");
		scancodeInit.directory(scancodeDir.toFile());
		try {
			Process p = scancodeInit.start();
			p.waitFor();
			System.out.println("Scancode scan finished! See output.json in the scancode directory!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//only for testing; deletes the created unzipped .jars and the node_modules 
		//File scanOutDir = new File("scancode-toolkit-21.3.31/Output");
		//FileUtils.deleteDirectory(scanOutDir);
	}
}
