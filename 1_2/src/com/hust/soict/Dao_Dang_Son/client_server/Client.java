package com.hust.soict.Dao_Dang_Son.client_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class Client
{
	public static void main(String[] args) throws Exception
	{
	
		System.out.println("Start!");
		Socket socket = new Socket("127.0.0.1",9898);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		//System.out.println(in.readLine());
		Scanner scanner =  new Scanner(System.in);
		String messageString;
		while(true)
		{
			System.out.println("Insert message!");
			messageString = scanner.nextLine();
			if( messageString == null || messageString.isEmpty())
			{
				break;
			}
			out.println(messageString);
			String receiveString = in.readLine();
			System.out.println(receiveString);
		}
		socket.close();
		scanner.close();
	}
}
