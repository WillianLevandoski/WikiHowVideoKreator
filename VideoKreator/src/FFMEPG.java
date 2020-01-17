import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FFMEPG {
	
	public static void main(String argvs[]) {
		try {
	     ProcessBuilder pb = new ProcessBuilder( );
	        pb.command( "C:\\Windows\\System32\\cmd.exe", "/c", 
	        "c:\\ffmpeg\\bin\\ffmpeg.exe", "-version" ); 
	        Process process; 
	        process = pb.start();
	        
	        InputStream inputstream = process.getInputStream();

	        
            int data = inputstream.read();
            while (data != -1) {
                   System.out.print((char) data);
                          data = inputstream.read();

            }
	        
//	        
//	        OutputStream stdOutput = process.getOutputStream();
//	        System.out.println(stdOutput);
//	        InputStream inputStream = process.getInputStream();
//	        System.out.println(inputStream);
//	        InputStream errorStream = process.getErrorStream();
//	        System.out.println(errorStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} // End class WrapperExe