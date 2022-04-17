
     
package com.hust.soict.Dao_Dang_Son.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import com.hust.soict.Dao_Dang_Son.helper.*;

public class Server {
  public static void main(String[] args) {
    System.out.println("Server running");
    int clientNum = 0;
    try (ServerSocket listener = new ServerSocket(9898)) {
      while (true) {
        new Sorter(listener.accept(), clientNum).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static class Sorter extends Thread {
    private Socket socket;
    private int clientNum;

    public Sorter(Socket socket, int clientNum) {
      this.socket = socket;
      this.clientNum = clientNum;
    }

    @Override
    public void run() {
      System.out.println("Client number #" + clientNum + " connected");
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
          String input = in.readLine();
          if (input == null || input.isEmpty()) {
            break;
          }

          String[] nums = input.split(" ");
          int[] intarr = new int[nums.length];
          int i = 0;
          for (String txt : nums) {
            intarr[i] = Integer.parseInt(txt);
            i++;
          }
          new SelectionSort().sort(intarr);
          String[] strArr = Arrays.stream(intarr).mapToObj(String::valueOf).toArray(String[]::new);
          out.println(Arrays.toString(strArr));
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          this.socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("Client number #" + clientNum + " disconnected");
      }
    }
  }
}
