package wa.LibraRepo;

import wa.LibraRepo.LibraRepo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Test code
    	int[] appWait = {60, 1000, 6000, 3000};
        LibraRepo lrp = new LibraRepo("userid", "passwd", "503", appWait, "windows", "firefox", "no");
		try { Thread.sleep(6000); } catch(InterruptedException e) {}
		lrp.shutdown();

    }
}
