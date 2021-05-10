package DependencyDownloader.DownloaderProto;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	DependencyDownloader dd = new DependencyDownloader();
    	dd.downloadDependencies();
    	//TODO start after download finishes
    	UnZipper zip = new UnZipper();
    	String path = "Input/target/dependency";
    	zip.unzipAll(path,"zip");
    	//TODO run scancode and move nodemodules to output before running it 
    }
}
