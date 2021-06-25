package lock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class checking {

	public static void main(String[] args) throws IOException, InterruptedException {
		File source = new File("C:\\Users\\roeysdomi\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies");
	    File dest = new File("C:\\Users\\roeysdomi\\Desktop\\mypro\\pic433443");
		
		killchrome();
		//TASKKILL /IM chrome.exe
		TimeUnit.SECONDS.sleep(4);
		
	    
	    copyAndDelete(source, dest);
	 
	    startchrome();
	    TimeUnit.SECONDS.sleep(8);
	   killchrome();
	    TimeUnit.SECONDS.sleep(2);
	    copyAndDelete(dest, source);
	   startchrome();
	   
	}
	public static void copyAndDelete(File source, File dest) throws IOException {
		if (Files.exists(dest.toPath())) { // if the target folder exists, delete it first;
			
			 Files.deleteIfExists(dest.toPath());
	    }
	    try {
	        Files.move(source.toPath(), dest.toPath());
	        Files.deleteIfExists(source.toPath());
	    } catch (IOException ignored) {
	        ignored.printStackTrace();
	    }
	    
	}
	public static void killchrome() throws IOException {
		Process p = Runtime
                .getRuntime()
                .exec("TASKKILL /IM chrome.exe");
	    
	}
	public static void startchrome() throws IOException {
		 Process p3t = Runtime
	                .getRuntime()
	                .exec("cmd /c start chrome.exe");
	}
	
}
