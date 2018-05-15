/**
 *
 *  @author Kharchenko Oleksandr S15638
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Client implements Runnable {

	public UIClient ui;
	private Selector selector;
	public String Name;

	public Client() {
		ui = new UIClient();
		this.Name = ui.Login;

	}

	public static void main(String[] args) throws Exception {
		// UIClient clientUI = new UIClient();
		Client cl = new Client();
		new Thread(cl).start();

	}

	private static void log(String str) {
		System.out.println(str);
	}

	@Override
	public void run() {
		SocketChannel channel;
		try {
			selector = Selector.open();
			channel = SocketChannel.open();
			channel.configureBlocking(false);

			channel.register(selector, SelectionKey.OP_CONNECT);
			channel.connect(new InetSocketAddress("127.0.0.1", 1111));

			while (!Thread.interrupted()) {

				selector.select(1000);

			//	Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

				
					SelectionKey key = channel.keyFor(selector);
					//keys.remove();

					if (!key.isValid())
						continue;

					if (key.isConnectable()) {
						ui.nicknameText.setText("You are connected, write your login");
						connect(key);
					}
					if (key.isReadable() && ui.getAccess()) {
						read(key);
						System.out.println("READ");
						//ui.flag = true;
					}
					
					if (key.isWritable() && ui.getAccess() && ui.flag) {
						write(key);
						System.out.println("WRITE");
						ui.flag = false;
						ui.anulateArea();
					}
					
				}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			close();
		}
	}

	private void close() {
		try {
			selector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(1000);
		readBuffer.clear();
		int length;
		try {
			length = channel.read(readBuffer);
		} catch (IOException e) {
			System.out.println("Reading problem, closing connection");
			key.cancel();
			channel.close();
			return;
		}
		if (length == -1) {
			System.out.println("Nothing was read from server");
			channel.close();
			key.cancel();
			return;
		}
		readBuffer.flip();
		byte[] buff = new byte[1024];
		readBuffer.get(buff, 0, length);
		//ui.chatText.setText(ui.m.getLogin() + new String(buff));
		ui.refreshALL(new String(buff));
		System.out.println("TAKES: " + new String(buff));
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		System.out.println("FLAG IS WRITE");
		
	}

	private void write(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		String line = "";
		
		    	//if(ui.getDataFromTextArea().length() > 1) {
		    		line = ui.Login +": " + ui.getDataFromTextArea();
					channel.write(ByteBuffer.wrap(line.getBytes()));
					key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					
		    	//}
				//else {
					
				//	channel.write(ByteBuffer.wrap(line.getBytes()));
				//	key.interestOps(SelectionKey.OP_WRITE);
				//}
					System.out.println("FLAG IS READ");
			
		    	
		
	}

	private void connect(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		if (channel.isConnectionPending()) {
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		//key.interestOps(SelectionKey.OP_READ);
		//key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		System.out.println("FLAG IS READ");
	}

}
