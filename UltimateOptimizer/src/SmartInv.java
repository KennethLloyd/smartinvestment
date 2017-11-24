import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class SmartInv extends JPanel {
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel headerPanel;
	private JLabel titleLabel;
	private JButton ultButton;
	
	public SmartInv(final JPanel cardPanel) {
		this.cardPanel = cardPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,1));
		
		headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 0));
		titleLabel = new JLabel("SMART INVESTMENT");
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		ultButton = new JButton("Go to Ultimate Optimizer");
		ultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "MAIN");
			}
		});
		headerPanel.add(titleLabel);
		headerPanel.add(ultButton);
		
		mainPanel.add(headerPanel);
		this.add(mainPanel);
	}
}