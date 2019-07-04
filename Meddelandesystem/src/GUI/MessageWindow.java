package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import client.Client;
import client.ClientController;
import message.Message;

public class MessageWindow
{
	private ClientController controller;

	private JFrame frame;
	private JTextField tfInput;
	private JPanel pnlMessage;
	private JButton btnOpenImages;
	private JButton btnSend;
	private JTextArea textArea;

	//	private TestSebbe imageChooser;
	private ImageIcon img;

	/**
	 * Create the application.
	 */
	public MessageWindow(ClientController controller)
	{
		this.controller = controller;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Messenger");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		pnlMessage = new JPanel();
		pnlMessage.setBackground(new Color(250, 128, 114));
		pnlMessage.setBounds(200, 0, 584, 461);
		frame.getContentPane().add(pnlMessage);
		pnlMessage.setLayout(null);

		tfInput = new JTextField();
		tfInput.setCaretPosition(0);
		tfInput.setFont(new Font("Georgia", Font.PLAIN, 12));
		tfInput.setBackground(new Color(255, 250, 250));
		tfInput.setBounds(10, 432, 320, 20);
		pnlMessage.add(tfInput);
		tfInput.addKeyListener(new ButtonListener());
		tfInput.setColumns(10);

		btnOpenImages = new JButton("Open Images");
		btnOpenImages.setBackground(new Color(240, 248, 255));
		btnOpenImages.setFont(new Font("Georgia", Font.PLAIN, 11));
		btnOpenImages.setBounds(458, 431, 116, 23);
		pnlMessage.add(btnOpenImages);

		btnSend = new JButton("Send Message");
		btnSend.setBackground(new Color(240, 248, 255));
		btnSend.setFont(new Font("Georgia", Font.PLAIN, 11));
		btnSend.setBounds(340, 431, 116, 23);
		btnSend.addActionListener(new ButtonListener());
		pnlMessage.add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 564, 410);
		pnlMessage.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setMargin(new Insets(20, 20, 20, 20));
		textArea.setBackground(new Color(255, 250, 250));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JPanel pnlList = new JPanel();
		pnlList.setBackground(new Color(255, 250, 250));
		pnlList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlList.setBounds(0, 0, 200, 461);
		frame.getContentPane().add(pnlList);

		btnOpenImages.addActionListener(new ButtonListener());

		frame.setResizable(false);
	}

	public void append(Message message)
	{
		textArea.append(message.getText() + "\n\n");
		if(message.getImage() != null)
		{
			JOptionPane.showMessageDialog(null, message.getImage());
		}
	}

	public void showImage(ImageIcon icon)
	{
	}

	private class ButtonListener implements ActionListener, KeyListener
	{
		//		private ImageIcon img = null;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == btnSend)
			{
				System.out.println("Enter hit");
				controller.sendMessage(new Message(tfInput.getText(), img));
				tfInput.setText(null);
				img = null;
				System.out.println("Done entering");

			}

			if (e.getSource() == btnOpenImages)
			{
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					String imagename = chooser.getSelectedFile().getPath();
					img = new ImageIcon(new ImageIcon(imagename).getImage());

					//					JOptionPane.showConfirmDialog(null, img);
				}

			}
		}

		public void keyPressed(KeyEvent ee)
		{
			if (ee.getKeyCode() == KeyEvent.VK_ENTER)
			{
				controller.sendMessage(new Message(tfInput.getText(), img));
				tfInput.setText(null);
				img = null;
			}
		}

		public void keyReleased(KeyEvent e)
		{
		}

		public void keyTyped(KeyEvent e)
		{
		}

	}

}