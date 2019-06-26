package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;

public class MessageWindow
{

	private JFrame frame;
	private JTextField tfInput;
	private JPanel pnlMessage;
	private JButton btnOpenImages;
	private JButton btnSend;
	private JTextArea textArea;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MessageWindow window = new MessageWindow();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MessageWindow()
	{
		initialize();
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
		tfInput.setFont(new Font("Georgia", Font.PLAIN, 12));
		tfInput.setBackground(new Color(255, 250, 250));
		tfInput.setBounds(10, 432, 320, 20);
		pnlMessage.add(tfInput);
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
		pnlMessage.add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 564, 410);
		pnlMessage.add(scrollPane);

		textArea = new JTextArea();
		textArea.setMargin(new Insets(20, 20, 20, 20));
		textArea.setBackground(new Color(255, 250, 250));
		scrollPane.setViewportView(textArea);

		JPanel pnlList = new JPanel();
		pnlList.setBackground(new Color(255, 250, 250));
		pnlList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlList.setBounds(0, 0, 200, 461);
		frame.getContentPane().add(pnlList);
	}
}