package server;
import java.io.*;
import java.net.*;
class ServerUDP extends Thread {

	static DatagramSocket serverSocket;


	public static void main(String args[]) throws Exception
	{
		serverSocket = new DatagramSocket(5000);
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		DatagramPacket receivePacket =
				new DatagramPacket(receiveData, receiveData.length);
		System.out.println("before receive packet");

		while(true)

		{
			serverSocket.receive(receivePacket);
			System.out.println(new String(receivePacket.getData()) + ":: data receive packet");
			new ServerThread(receivePacket, sendData, serverSocket, receivePacket.getData(), receivePacket.getAddress(), receivePacket.getPort()).start();
		}
	}

}