package DependencyDownloader.DownloaderProto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper {
	
	//unzips all files in a directory
    public void unzipAll(String path) {
		System.out.println("Start unzipping downloads...");

        String fileName;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); 
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileName = listOfFiles[i].getName();
                if (fileName.endsWith(".jar")) {
                    try {
                    		unzipFile(listOfFiles[i],"Output"+File.separator+fileName);	 	
                    }catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }
		System.out.println("Unzipping finished!");

    }

    public void unzipFile(File zipFile, String outputPath) throws IOException {
        byte[] buffer = new byte[1024];
     
            // create output directory is not exists
            File outputFolder = new File(outputPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdir();
            }
     
            // get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
     
            while (ze != null) {
               String fileName = ze.getName();
               File newFile = new File(outputFolder + File.separator + fileName);
               
               System.out.println("File unzip : " + newFile);

                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                if (ze.isDirectory()) {
                    newFile.mkdirs();
                } else {
                	new File(newFile.getParent()).mkdirs();
                    newFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(newFile);             
     
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();   
            }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        
	    }
   
}

