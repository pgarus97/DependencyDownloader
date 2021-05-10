package DependencyDownloader.DownloaderProto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper {
    public void unzipAll(String path, String method) {
        String fileName;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); 
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileName = listOfFiles[i].getName();
                if (fileName.endsWith(".jar")) {
                    try {
                    	if(method.equals("zip")) {
                    		unzipFile(listOfFiles[i],"Output"+File.separator+fileName);	
                    	}else if(method.equals("jar")){
                    		unzipJar("Output"+File.separator+fileName, listOfFiles[i].getAbsolutePath());
    					} 
                    }catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }
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
    
    public void unzipJar(String destinationDir, String jarPath) throws IOException {
		File file = new File(jarPath);
		JarFile jar = new JarFile(file);

		// fist get all directories,
		// then make those directory on the destination Path
		for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
			JarEntry entry = (JarEntry) enums.nextElement();

			String fileName = destinationDir + File.separator + entry.getName();
			File f = new File(fileName);

			if (fileName.endsWith("/")) {
				f.mkdirs();
			}
		}
		// now create all files
		for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
			JarEntry entry = (JarEntry) enums.nextElement();

			String fileName = destinationDir + File.separator + entry.getName();
			File f = new File(fileName);
			
            System.out.println("File unzip : " + f);


			if (!fileName.endsWith("/")) {
				InputStream is = jar.getInputStream(entry);
				FileOutputStream fos = new FileOutputStream(f);

				// write contents of 'is' to 'fos'
				while (is.available() > 0) {
					fos.write(is.read());
				}
				fos.close();
				is.close();
			}
		}
	}
}

