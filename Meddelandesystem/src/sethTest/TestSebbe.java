package sethTest;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TestSebbe {

	private JFrame frame;
	private ImageIcon img; //Use with message
	private JButton btnChoose;
	
	public TestSebbe() {
		start();
	}
	
	public void start() {
		frame = new JFrame();
		frame.setBounds(0, 0, 200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		initializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}
	
	public void initializeGUI() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 200, 200);
		btnChoose = new JButton("Choose image");
		btnChoose.addActionListener(new ImageListener());
		btnChoose.setBounds(50, 50, 100, 50);
		panel.add(btnChoose);
		frame.add(panel);
	}
	
	//To draw the chosen image.
	public void drawImage(String filename) {
		JFrame imgFrame = new JFrame(filename);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		}catch (Exception e) {
			e.printStackTrace();
		}
		JLabel lbl = new JLabel();
		lbl.setIcon(new ImageIcon(img));
		imgFrame.getContentPane().add(lbl, BorderLayout.CENTER);
		imgFrame.pack();
		imgFrame.setLocationRelativeTo(null);
		imgFrame.setVisible(true);
	}
	
	private class ImageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnChoose) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Images", "jpg", "png", "gif");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String imagename = chooser.getSelectedFile().getPath();
//					System.out.println("You chose " + chooser.getSelectedFile().getName());
					System.out.println("You chose " + imagename);	
					img = new ImageIcon(
							new ImageIcon(imagename).getImage());
					drawImage(imagename);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		new TestSebbe();
	}
	
}
