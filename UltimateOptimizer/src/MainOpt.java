import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class MainOpt extends JFrame {
	private Container container;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private JPanel smartPanel;
	private JPanel headerPanel;
	private JPanel constHeadPanel;
	private JPanel objPanel;
	private JPanel constPanel;
	private JPanel buttonPanel;
	
	private JLabel titleLabel;
	private JLabel zLabel;
	private JLabel constLabel;
	
	private JTextField zField;
	
	private JButton smartButton;
	private JButton constButton;
	private JButton submitButton;
	
	private JScrollPane sp;
	
	private ButtonGroup optRadio;
	private JRadioButton maxButton;
	private JRadioButton minButton;
	
	private ArrayList<JTextField> lhsList = new ArrayList<JTextField>();
	private ArrayList<JTextField> rhsList = new ArrayList<JTextField>();
	private ArrayList<JComboBox> eqList = new ArrayList<JComboBox>();
	
	private int numConstraints;
	
	public MainOpt() {
		super("Ultimate Optimizer");
		
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		mainPanel = new JPanel();
		sp = new JScrollPane(mainPanel);
		smartPanel = new SmartInv(cardPanel);
		
		mainPanel.setLayout(new GridBagLayout());
		
		headerPanel = new JPanel();
		titleLabel = new JLabel("ULTIMATE OPTIMIZER");
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		smartButton = new JButton("Go to Smart Investment");
		smartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
                cl.show(cardPanel, "SMART");
			}
		});
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints hgc = new GridBagConstraints();
		hgc.insets = new Insets(0, 0, 0, 0);
		
		hgc.weightx = 0.5;
		hgc.weighty = 0.5;
		
		hgc.gridx = 0;
		hgc.gridy = 0;
		
		headerPanel.add(titleLabel, hgc);
		
		hgc.gridx = 0;
		hgc.gridy = 1;
		
		headerPanel.add(smartButton, hgc);
		
		objPanel = new JPanel();
		objPanel.setLayout(new FlowLayout());
		zLabel = new JLabel("Z: ");
		zLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		zField = new JTextField("Input the objective function here...",30);
		zField.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
                zField.setText("");
            }
		});
		objPanel.add(zLabel);
		objPanel.add(zField);
		
		constPanel = new JPanel();
		constPanel.setLayout(new GridBagLayout());
		
		constLabel = new JLabel("CONSTRAINTS");
		constLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		
		constButton = new JButton("Add constraint");
		constButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numConstraints++;
				constPanel.removeAll(); //only for refreshing the ui
				
				String comboItems[] = {"<=",">="};
				lhsList.add(new JTextField(15));
				eqList.add(new JComboBox(comboItems));
				rhsList.add(new JTextField(5));
				
				GridBagConstraints lhsgc = new GridBagConstraints();
	            GridBagConstraints eqgc = new GridBagConstraints();
	            GridBagConstraints rhsgc = new GridBagConstraints();
	            
	            for(int i=0;i<numConstraints;i++)
	            {
	                lhsgc.gridx = 0;
	                lhsgc.gridy = i;
	                
	                eqgc.gridx = 1;
	                eqgc.gridy = i;
	                
	                rhsgc.gridx = 2;
	                rhsgc.gridy = i;

	                constPanel.add(lhsList.get(i), lhsgc);
	                constPanel.add(eqList.get(i), eqgc);
	                constPanel.add(rhsList.get(i), rhsgc);
	            }
	            constPanel.revalidate();
			}
		});
		
		cardPanel.add(sp,"MAIN");
		cardPanel.add(smartPanel,"SMART");
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		mainPanel.add(headerPanel, gc);
		
		gc.weighty = 2.0;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(objPanel, gc);
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.NORTH;
		
		constHeadPanel = new JPanel();
		constHeadPanel.setLayout(new GridBagLayout());
		GridBagConstraints chgc = new GridBagConstraints();

		chgc.insets = new Insets(20, 0, 0, 0);
		chgc.weightx = 0.5;
		chgc.weighty = 0.5;
		
		chgc.gridx = 0;
		chgc.gridy = 0;
		
		constHeadPanel.add(constLabel, chgc);
		
		chgc.gridx = 0;
		chgc.gridy = 1;
		
		constHeadPanel.add(constButton, chgc);

		chgc.gridx = 0;
		chgc.gridy = 2;
		
		constHeadPanel.add(constPanel, chgc);
		
		mainPanel.add(constHeadPanel, gc);
		
		buttonPanel = new JPanel();
		maxButton = new JRadioButton("Maximize");
		maxButton.setFont(new Font("Serif", Font.PLAIN, 30));
		maxButton.setSelected(true);
		minButton = new JRadioButton("Minimize");
		minButton.setFont(new Font("Serif", Font.PLAIN, 30));
		optRadio = new ButtonGroup();
		optRadio.add(maxButton);
		optRadio.add(minButton);
		buttonPanel.add(maxButton);
		buttonPanel.add(minButton);
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(buttonPanel, gc);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setFont(new Font("Serif", Font.PLAIN, 30));
		
		gc.weighty = 15.0;
		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.NORTH;
		
		mainPanel.add(submitButton, gc);
		
		
		this.setContentPane(cardPanel);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainOpt main = new MainOpt();
	}

}