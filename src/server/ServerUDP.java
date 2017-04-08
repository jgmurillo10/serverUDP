package server;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
class ServerUDP {

	static DatagramSocket serverSocket;
	static boolean nuevo;
	static ArrayList<String> clientes;
	
	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	public static void main(String args[]) throws Exception
	{
		// TODO Auto-generated method stub
		//int port= Integer.parseInt(args[0]);

		serverSocket = new DatagramSocket(5000);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		System.out.println("before receive packet");
		nuevo=true;
		clientes=new ArrayList<String>();
		

		while(true)

		{
			DatagramPacket receivePacket =
					new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			System.out.println(new String(receivePacket.getData()) + ":: data receive packet");
			System.out.println(receivePacket.getPort());
			String cliente= receivePacket.getAddress() + "," + receivePacket.getPort();
			if(clientes.size()!=0){
				for(int i=0;i<clientes.size()&& nuevo;i++){
					System.out.println(clientes.get(i));
					System.out.println(cliente);
					if(cliente.equals(clientes.get(i))){
						nuevo=false;
					}
					else{
						nuevo=true;
					}
				}
			}
			
			if(nuevo){
				clientes.add(cliente);
				
			}
			String linea = new String(receiveData);
			String[] s=linea.split("=");
			String[] s1 = s[1].split("t");
			String seq = s1[0];
			String fecha = s[2];
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date fechaSalida = df.parse(fecha);
			Date actual = new Date();
			long dif = getDateDiff(fechaSalida,actual,TimeUnit.MILLISECONDS);
			String content = seq + ":" + dif + "ms";
			new ServerThread(receivePacket, sendData, serverSocket, receivePacket.getData(), receivePacket.getAddress(), receivePacket.getPort(),nuevo,content).start();
		}
	}

}