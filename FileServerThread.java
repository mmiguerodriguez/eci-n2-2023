package example;

import java.net.*;

public class FileServerThread extends Thread {
  private Socket socket;

  public FileServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      FileServer server = new FileServer();
      if (server.start(socket)) {
        System.out.println("[FILESERVER THREAD] File server started!");

        while(true) {

          if(server.hasRequest()) {

            if(server.hasClosed()) {
              server.close();
              return;
            }

            if(!server.hasFile()) {
              server.sendZeroByte();
              continue;
            }

            while(!server.eof()) {
              server.sendByte();
            }

            server.sendZeroByte();
          }

        }
      } else {
        System.out.println("[FILESERVER THREAD] Could not start server!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
