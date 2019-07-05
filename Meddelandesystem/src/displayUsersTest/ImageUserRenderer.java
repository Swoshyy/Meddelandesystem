package displayUsersTest;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import user.User;

/**
 * Custom cellrenderer for the UsersGUI objects
 * Shows an image and text next to it
 * @author sethoberg
 *
 */

public class ImageUserRenderer extends JLabel implements ListCellRenderer<User> {

	
	public ImageUserRenderer() {
		setOpaque(true);
	}
	
	public Component getListCellRendererComponent(JList<? extends User> list, User value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		setIcon(value.returnImageUrl());
		setText(value.toString());
		
		
		if(isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		return this;
	}

	
	
	

}
