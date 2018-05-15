package zad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import javafx.scene.control.Button;

public class UIClient extends JFrame implements ActionListener {
	// public JTextArea area = new JTextArea();
	//

	public JPanel mainPanel = new JPanel();
	public JPanel botPanel = new JPanel();
	public JButton firstbutton = new JButton("ZALOGUJ");
	public JButton secondbutton = new JButton("WYSLIJ");
	public JTextArea nicknameText = new JTextArea("Wpisz login");
	public JTextArea chatText = new JTextArea();
	public JScrollPane scroll = new JScrollPane(chatText);
	public JLabel ALARM = new JLabel("NAJPIERW ZALOGUJ");
	public Message m;
	public String Login;
	List<Message> list = new ArrayList<Message>();
	private boolean access = false;
	public String text;
	public boolean clicked = false;
	public boolean flag = false;

	public UIClient() {
		super("Czat");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setVisible(true);
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border tmpBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(-1, 0, 500, 420);
		mainPanel.setLayout(null);
		mainPanel.add(ALARM);
		mainPanel.setBorder(blackBorder);
		ALARM.setBackground(Color.BLACK);
		ALARM.setVisible(true);
		ALARM.setBounds(50, 150, 450, 100);
		ALARM.setFont(new Font("Arial", Font.PLAIN, 40));
		chatText.setBounds(-1, 0, 499, 419);
		chatText.setBorder(tmpBorder);
		chatText.setVisible(false);
		chatText.setLineWrap(true);
		mainPanel.add(chatText);
		botPanel.setBackground(Color.BLUE);
		botPanel.setBounds(0, 420, 500, 80);
		botPanel.setLayout(null);
		firstbutton.setBounds(420, -18, 80, 80);
		firstbutton.setBorder(blackBorder);
		secondbutton.setBounds(420, -18, 80, 80);
		secondbutton.setBorder(blackBorder);
		secondbutton.setVisible(false);
		// firstbutton.setHorizontalTextPosition(SwingConstants.CENTER);
		nicknameText.setBounds(0, 0, 420, 80);
		nicknameText.setBorder(tmpBorder);
		nicknameText.setFont(new Font("Arial", Font.PLAIN, 20));
		nicknameText.setLineWrap(true);
		nicknameText.setWrapStyleWord(true);
		botPanel.add(firstbutton);
		botPanel.add(secondbutton);
		botPanel.add(nicknameText);
		this.add(mainPanel);
		this.add(botPanel);
		firstbutton.addActionListener(this);
		secondbutton.addActionListener(this);
		// mainPanel.add(area);
		// area.setBounds(10, 10, 200, 200);

	}

	public void refreshALL(String message) {
		  
		   list.add(new Message(Login, message));
		   StringBuilder sb = new StringBuilder();
		   //Message tmp = new Message();
		   for(Message tmp : list) {
			   sb.append(tmp.getMessage() + "\n");
			   
			   System.out.println(tmp.getMessage() + "\n");
		   }
		   chatText.setText(sb.toString());
		   //list.add(m);
		   //chatText.setText(m.getMessage());
		   
	}

	public boolean getAccess() {
		return this.access;
	}
	
	public String getDataFromTextArea() {
		return nicknameText.getText();
	}
	public void anulateArea() {
		nicknameText.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == firstbutton) {
			ALARM.setVisible(false);
			chatText.setVisible(true);
			firstbutton.setVisible(false);
			secondbutton.setVisible(true);
			Login = nicknameText.getText();
			nicknameText.setText("");
			this.access = true;
		}
		if (e.getSource() == secondbutton) {
			
			flag = true;
			
			// chatText.setText(m.getMessage());
		}

	}

}
