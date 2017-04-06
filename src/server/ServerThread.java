
package server;

import java.io.IOException;
import java.net.*;

public class ServerThread extends Thread{
	private DatagramPacket receivePacket;
	private byte[] sendData;
	private DatagramSocket serverSocket;
	private byte[] receiveData;
	private InetAddress IPAddress;
	private int PORT;
	public ServerThread (DatagramPacket receivePacket, byte[] sendData, DatagramSocket serverSocket, byte[] receiveData, InetAddress IPAddress, int PORT){
		this.receivePacket= receivePacket;
		this.receiveData= receiveData;
		this.sendData = sendData;
		this.serverSocket= serverSocket;
		this.IPAddress=IPAddress;
		this.PORT=PORT;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("packet received");

		String sentence = new String(receiveData);
		//String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
		System.out.println(sentence);
		//InetAddress IPAddress = receivePacket.getAddress();
		System.out.println("get address " + IPAddress );
		//int port = receivePacket.getPort();
		System.out.println("get port " + PORT);
		String capitalizedSentence = sentence.toUpperCase();
		sendData = capitalizedSentence.getBytes();
		DatagramPacket sendPacket =
				new DatagramPacket(sendData, sendData.length, IPAddress,
						PORT);
		try {
			serverSocket.send(sendPacket);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
