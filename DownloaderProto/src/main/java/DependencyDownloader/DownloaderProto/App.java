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
    	//dd.downloadDependencies();
    	UnZipper zip = new UnZipper();
    	String path = "Input/target/dependency";
    	zip.unzipAll(path,"zip");
    }
}
