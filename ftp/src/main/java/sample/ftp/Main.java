package sample.ftp;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

public class Main {
  public static final String FTP_SERVER = "";
  public static final String FTP_ID = "";
  public static final String FTP_PW = "";
  public static final String REMOTE_FILE_NAME = "";
  public static final String LOCAL_FILE_NAME = "test.txt";

  public static void main(String[] args) {
    try {
      FTPClient client = new FTPClient();
      client.connect(FTP_SERVER);
      if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
        System.out.println("fail");
        client.disconnect();
        return;
      }
      boolean result = client.login(FTP_ID, FTP_PW);
      if (!result) {
        System.out.println("fail");
        client.disconnect();
        return;
      }

      FTPFile[] list = client.listFiles();
      for (FTPFile file : list) {
        System.out.println(file.getName());
        System.out.println(file.getGroup());
      }

      client.enterLocalPassiveMode();
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(LOCAL_FILE_NAME)));
      client.setFileType(FTP.BINARY_FILE_TYPE);
      if (!client.retrieveFile(REMOTE_FILE_NAME, bufferedOutputStream)) {
        System.out.println("fail");
        bufferedOutputStream.close();
        client.disconnect();
        return;
      }
      System.out.println(client.getReplyCode());
      bufferedOutputStream.flush();
      bufferedOutputStream.close();

      System.out.println("success");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
