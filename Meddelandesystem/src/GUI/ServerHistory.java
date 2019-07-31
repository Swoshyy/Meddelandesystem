package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import server.ServerController;
import server.ServerLogger;
import java.awt.Color;

public class ServerHistory extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private JFrame frame;
	private JTextField txtYyyymmdd;
	private JTextField txtYyyymmdd_1;
	private JTextArea taLog;
	private ServerController controller;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args)
//	{
//		EventQueue.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					ServerHistory window = new ServerHistory();
//					window.frame.setVisible(true);
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public ServerHistory(ServerController controller) {
		this.controller = controller;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		frame = new JFrame();
		setBounds(100, 100, 550, 551);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		taLog = new JTextArea();
		panel_1.add(new JScrollPane(taLog), BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 5, 5, 5));

		JLabel lblFrom = new JLabel("From:");
		panel.add(lblFrom);

		txtYyyymmdd = new JTextField();
		txtYyyymmdd.setText("2019/07/24");
		panel.add(txtYyyymmdd);
		txtYyyymmdd.setColumns(10);

		JLabel lblTo = new JLabel("To: ");
		panel.add(lblTo);

		txtYyyymmdd_1 = new JTextField();
		txtYyyymmdd_1.setText("2019/07/31");
		panel.add(txtYyyymmdd_1);
		txtYyyymmdd_1.setColumns(10);

		JButton btnShowHistory = new JButton("Show History");
		btnShowHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				taLog.setText("");
				controller.checkDate(txtYyyymmdd.getText(), txtYyyymmdd_1.getText());
			}

		});
		panel.add(btnShowHistory);
		setVisible(true);
	}

	public void showLog(String message) {
		taLog.append(message + "\n");

	}
}
