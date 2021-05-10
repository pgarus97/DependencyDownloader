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
    	//TODO run scancode and move nodemodules to output before running it 
    }
}
