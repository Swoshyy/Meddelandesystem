package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

import client.ClientController;
import displayUsersTest.AllUsersGUI;
import message.Message;
import user.User;

public class MessageWindow {
	private ClientController controller;

	private JFrame frame;
	private JTextField tfInput;
	private JPanel pnlMessage;
	private JButton btnOpenImages;
	private JButton btnSend;
	private JTextPane textPane;
	
	private User user;
	
	private AllUsersGUI userListGUI = new AllUsersGUI();

	// private TestSebbe imageChooser;
	private ImageIcon img;

	/**
	 * Create the application.
	 */
	public MessageWindow(ClientController controller, User user) {
		this.user = user;
//		userListGUI.addUser(user);
		this.controller = controller;
//		controller.setUserListGUI(userListGUI);
		initialize();
		frame.setVisible(true);
	}
	
	public MessageWindow(ClientController controller) {
		this.controller = controller;
		initialize();
		frame.setVisible(true);
	}
	
//	public MessageWindow() {
//		initialize();
//		frame.setVisible(true);
//	}
	
//	public static void main(String[] args)
//	{
//		new MessageWindow();
//	}
	
	public void addListOfUsers(LinkedList<User> users) {
		userListGUI.addListOfOnlineUsers(users);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Messenger " + user.getName());
		frame.getContentPane().setBackground(new Color(250, 128, 114));
		frame.setBounds(100, 100, 951, 697);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		pnlMessage = new JPanel();
		pnlMessage.setBackground(new Color(250, 128, 114));
		pnlMessage.setBounds(210, 0, 725, 647);
		frame.getContentPane().add(pnlMessage);
		pnlMessage.setLayout(null);

		tfInput = new JTextField();
		tfInput.setCaretPosition(0);
		tfInput.setFont(new Font("Georgia", Font.PLAIN, 12));
		tfInput.setBackground(new Color(255, 250, 250));
		tfInput.setBounds(10, 616, 453, 20);
		pnlMessage.add(tfInput);
		tfInput.addKeyListener(new ButtonListener());
		tfInput.setColumns(10);

		btnOpenImages = new JButton("Open Images");
		btnOpenImages.setBackground(new Color(240, 248, 255));
		btnOpenImages.setFont(new Font("Georgia", Font.PLAIN, 11));
		btnOpenImages.setBounds(599, 615, 116, 23);
		pnlMessage.add(btnOpenImages);

		btnSend = new JButton("Send Message");
		btnSend.setBackground(new Color(240, 248, 255));
		btnSend.setFont(new Font("Georgia", Font.PLAIN, 11));
		btnSend.setBounds(473, 615, 116, 23);
		btnSend.addActionListener(new ButtonListener());
		pnlMessage.add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 705, 594);
		pnlMessage.add(scrollPane);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setMargin(new Insets(20, 20, 20, 20));
		textPane.setBackground(new Color(255, 250, 250));
		textPane.setAutoscrolls(true);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textPane);

		DefaultCaret caret = (DefaultCaret) textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JPanel pnlList = new JPanel();
		pnlList.setBackground(new Color(255, 250, 250));
		pnlList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlList.setBounds(0, 0, 200, 600);
		pnlList.setLayout(null);
		userListGUI.setBounds(2, 2, 210, 598);
		pnlList.add(userListGUI);
		frame.getContentPane().add(pnlList);

		btnOpenImages.addActionListener(new ButtonListener());

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	
	public void append(Message message) {

		try {
			Document doc = textPane.getDocument();
			doc.insertString(doc.getLength(), message.getText() + "\n"+"\n", null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}

		if (message.getImage() != null) {

			JLabel label = new JLabel("");

			// YEAH........ so thats the mouse listener shhhhhh no one will know
			label.addMouseListener(new java.awt.event.MouseListener() {

				public void mouseClicked(MouseEvent ee) {
					JOptionPane.showMessageDialog(null, message.getImage());

				}

				public void mouseEntered(MouseEvent ee) {

				}

				public void mouseExited(MouseEvent ee) {

				}

				public void mousePressed(MouseEvent ee) {

				}

				public void mouseReleased(MouseEvent ee) {

				}
			});
			// its not that much right?

//			ImageIcon yourImage = message.getImage();
//			Image image = yourImage.getImage();
//			BufferedImage buffered = (BufferedImage) image;
			
			BufferedImage bufferedImg = new BufferedImage(
					message.getImage().getIconWidth(),
					message.getImage().getIconHeight(),
				    BufferedImage.TYPE_INT_RGB);
				Graphics g = bufferedImg.createGraphics();
				// paint the Icon to the BufferedImage.
				message.getImage().paintIcon(null, g, 0,0);
				g.dispose();
			
			Image resizedImg = bufferedImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			label.setIcon(new ImageIcon(resizedImg));

			label.setAlignmentY(0.85f);
			textPane.insertComponent(label);

			try {
				Document doc = textPane.getDocument();
				doc.insertString(doc.getLength(),  "\n"+"\n" , null);
			} catch (BadLocationException exc) {
				exc.printStackTrace();
			}

		}
	}

	public void showImage(ImageIcon icon) {
	}

	private class ButtonListener implements ActionListener, KeyListener {
		// private ImageIcon img = null;
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSend) {
				System.out.println("Enter hit");
				controller.sendMessage(new Message(tfInput.getText(), img));
				tfInput.setText(null);
				img = null;
				System.out.println("Done entering");

			}

			if (e.getSource() == btnOpenImages) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					String imagename = chooser.getSelectedFile().getPath();
					

					img = new ImageIcon(new ImageIcon(imagename).getImage());

					// JOptionPane.showConfirmDialog(null, img);
				}

			}
		}

		public void keyPressed(KeyEvent ee) {
			if (ee.getKeyCode() == KeyEvent.VK_ENTER) {
				controller.sendMessage(new Message(tfInput.getText(), img));
				tfInput.setText(null);
				img = null;
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}

	}

	public void displayUser(User user2)
	{
		userListGUI.addUser(user2);
	}

}