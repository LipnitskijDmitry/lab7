package bsu.rfe.group7.lab7.varB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;



public class InstantMassage {

	private String sender;
	private MainFrame frame;
	private ArrayList<MessageListener> listeners;
	
	public void addMessageListener(MessageListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}
		public void removeMessageListener(MessageListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}
		
	private void notifyListeners(Peer sender, String message) {
		synchronized (listeners) {
			for (MessageListener listener : listeners) {
				listener.messageReceived(sender, message);
			}
		}
	}
	public String getSender(){
		return sender;
	}
	
	public void setSender( String sender0 ){
		sender = sender0;
	}
	
	private void startServer(int SERVER_PORT){
		new Thread(new Runnable() {

			public void run() {
				try {
					
				final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
				
				while (!Thread.interrupted()) {
					final Socket socket = serverSocket.accept();
					final DataInputStream in = new DataInputStream(
					socket.getInputStream());

					final String senderName = in.readUTF();
					

					final String message = in.readUTF();

					socket.close();

					final String address = 
						((InetSocketAddress) socket
							.getRemoteSocketAddress())
								.getAddress()
									.getHostAddress();
					

						frame.textAreaIncoming.append(senderName+ 
						" (" + address + "): " + 
						message + "\n");
			}
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame,
						"Ошибка в работе сервера", "Ошибка",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}).start();
	}
	
	public void sendMessage(String senderName, String destinationAddress, 
			int SERVER_PORT) {
		try {
				
				/*/final String senderName = textFieldFrom.getText();
				final String destinationAddress = textFieldTo.getText();
				/*/
			
			final String message = frame.textAreaOutgoing.getText();
				if (senderName.isEmpty()) {
					JOptionPane.showMessageDialog(frame, 
								"Введите имя отправителя", "Ошибка",
								JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (destinationAddress.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
								"Введите адрес узла-получателя", "Ошибка",
								JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (message.isEmpty()) {
					JOptionPane.showMessageDialog(frame, 
								"Введите текст сообщения", "Ошибка",
								JOptionPane.ERROR_MESSAGE);
					return;
				}

				final Socket socket = new Socket(destinationAddress, SERVER_PORT);

				final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
				out.writeUTF(senderName);

				out.writeUTF(message);

				socket.close();

				frame.textAreaIncoming.append("Я -> " + destinationAddress + ": "
				+ message + "\n");

				frame.textAreaOutgoing.setText("");
			}
		catch (UnknownHostException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame,
				"Не удалось отправить сообщение: узел-адресат не найден",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame,
						"Не удалось отправить сообщение", "Ошибка",
						JOptionPane.ERROR_MESSAGE);
			}
		   
		}
	
	public InstantMassage(int SERVER_PORT,MainFrame frame) {
		this.frame = frame;
		this.startServer(SERVER_PORT);
		
	}

}
