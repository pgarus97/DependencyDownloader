package DependencyDownloader.DownloaderProto;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	DependencyDownloader dd = new DependencyDownloader();
    	dd.downloadDependencies();
    }
}
