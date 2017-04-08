
package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;



public class ServerThread extends Thread{
	private DatagramPacket receivePacket;
	private byte[] sendData;
	private DatagramSocket serverSocket;
	private byte[] receiveData;
	private InetAddress IPAddress;
	private int PORT;
	public boolean nuevo;
	public String content;
	public ServerThread (DatagramPacket receivePacket, byte[] sendData, DatagramSocket serverSocket, byte[] receiveData, InetAddress IPAddress, int PORT, boolean nuevo,String content){
		this.receivePacket= receivePacket;
		this.receiveData= receiveData;
		this.sendData = sendData;
		this.serverSocket= serverSocket;
		this.IPAddress=IPAddress;
		this.PORT=PORT;
		this.nuevo=nuevo;
		this.content=content;



	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		super.run();
		System.out.println("packet received");
		String cliente = IPAddress + "," + PORT + "";

		try{

			byte[] contentInBytes = content.getBytes();
			if(nuevo){

				try {
					FileOutputStream fos = new FileOutputStream(new File("./data",cliente + ".txt"));
					BufferedOutputStream bos = new BufferedOutputStream(fos);

					bos.write(contentInBytes);
					bos.flush();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else{

				try{  
					FileWriter fw = new FileWriter("./data"+cliente + ".txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);

					out.println("\n"+content);

					out.close();
					bw.close();
					fw.close();

				}  
				catch( IOException e ){
					// File writing/opening failed at some stage.
				}

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


		String sentence = new String(receiveData);
		//String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
		System.out.println(sentence);
		//InetAddress IPAddress = receivePacket.getAddress();
		System.out.println("get address " + IPAddress );
		//int port = receivePacket.getPort();
		System.out.println("get port " + PORT);


	}




}
