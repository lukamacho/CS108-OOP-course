import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebWorker extends Thread {

    private String urlAddress;
    private int rowNum;
    private WebFrame webFrame;
    private String statusText;
	private final String errorMessage =" Error happened";
	private final String interruptedMessage = "Download interrupted";

	/**
	 *
	 * @param urlAddress
	 * @param rowNum
	 * @param webFrame
	 * Web worker construction it gives url address , frame and the row index where the change
	 * must be made.
	 */
	public WebWorker(String urlAddress,int rowNum, WebFrame webFrame){
		this.urlAddress = urlAddress;
		this.rowNum = rowNum;
		this.webFrame = webFrame;
	}
	@Override
	public void run(){
		download();
	}
    // This is the core web/download i/o code...

	/**
	 * This function makes the download process from the intetnet.
	 */
 	 private void download() {
		 InputStream input = null;
		 StringBuilder contents = null;
		 try {
			 URL url = new URL(urlAddress);
			 URLConnection connection = url.openConnection();

			 // Set connect() to throw an IOException
			 // if connection does not succeed in this many msecs.
			 connection.setConnectTimeout(5000);

			 connection.connect();
			 input = connection.getInputStream();

			 BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			 char[] array = new char[1000];
			 long start = System.currentTimeMillis();

			 int len;
			 contents = new StringBuilder(1000);
			 int siteSize = 0;
			 while ((len = reader.read(array, 0, array.length)) > 0) {
				 contents.append(array, 0, len);
				 //if(rowNum==0)Thread.sleep(10000);
				 Thread.sleep(100);
				 siteSize += len;
			 }
			 long downloadTime = System.currentTimeMillis() - start;
			 String startDate = new SimpleDateFormat("HH:MM:SS").format(new Date(start));
			 statusText = startDate + " "+ downloadTime + "ms " + siteSize + "bytes.";
			 webFrame.releaseWorker(rowNum,statusText);
		 }
		 // Otherwise control jumps to a catch...
		 catch (MalformedURLException ignored) {
		 	statusText = errorMessage;
		 	webFrame.releaseWorker(rowNum,statusText);
		 } catch (InterruptedException exception) {
			 statusText = interruptedMessage;
			 webFrame.releaseWorker(rowNum,statusText);
		 } catch (IOException ignored) {
		 	statusText = errorMessage;
		 	webFrame.releaseWorker(rowNum,statusText);
		 }
		 // "finally" clause, to close the input stream
		 // in any case
		 finally {
			 try {
				 if (input != null) input.close();
			 } catch (IOException ignored) {
			 }
		 }

	 }
	
}
