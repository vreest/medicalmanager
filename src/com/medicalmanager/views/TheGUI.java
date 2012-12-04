package com.medicalmanager.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.medicalmanager.models.Patient;
import com.medicalmanager.models.Persistence;

/* 
 * Docs --->
 *	  A1 "Integrated Patient Information Management System"
 *	 	  -- Clinical environment has poor record keeping and management
 *	 	  		-- Get rid of paper
 *	 	  			-- Paper can be lost, stolen, copied, etc
 *	 	  -- What does the user need
 *	 
 *	  A2 Criterion for success
 *	  	  -- Capabilities etc.. 'will be able...'
 *
 * ToDo ---> 
 * 		
 * 	  
 * 
 */
public class TheGUI extends JFrame {

	private CardLayout card = new CardLayout(0,0);
	
	private JPanel contentPane;
	
	private JButton mainAppButton;
	private JButton aboutButton;
	private JButton newPatientButton;
	private JButton searchButton;
	private JButton sortByIDButton;
	
	private JTextArea patientInfoArea;

	private JList patientList;
	public static DefaultListModel listModel;
	public ArrayList<Patient> sortedArray = new ArrayList<Patient>();
	public static ArrayList<Patient> patientArray = new ArrayList<Patient>();

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public TheGUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 838, 609);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(card);
		
		setContentPane(contentPane);
	
		placeMenu();
		makeWelcomePanel();
		makePatientPanel();
		actionTime();	
		
	}

	public void placeMenu(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("New menu");
		menuBar.add(fileMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		fileMenu.add(mntmNewMenuItem);
		
		JMenu helpMenu = new JMenu("New menu");
		menuBar.add(helpMenu);
		
		JMenuItem menuItem = new JMenuItem("New menu item");
		helpMenu.add(menuItem);
	}
	
	public void makePatientPanel() throws IOException{
		JPanel patientPanel = new JPanel();
		contentPane.add(patientPanel, "patientPanel");

		JSplitPane splitPane = new JSplitPane();
		
		JToolBar patientToolBar = new JToolBar();
		GroupLayout patientLayout = new GroupLayout(patientPanel);
		patientLayout.setHorizontalGroup(
			patientLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
				.addComponent(patientToolBar, GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
		);
		patientLayout.setVerticalGroup(
			patientLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, patientLayout.createSequentialGroup()
					.addComponent(patientToolBar, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
		);
		
		newPatientButton = new JButton("New Patient");

		patientToolBar.add(newPatientButton);
		
		searchButton = new JButton("Search");
		sortByIDButton = new JButton("Sort By ID");

		patientToolBar.add(searchButton);
		patientToolBar.add(sortByIDButton);

		patientInfoArea = new JTextArea();
		splitPane.setRightComponent(patientInfoArea);
		patientPanel.setLayout(patientLayout);
		
		listModel = new DefaultListModel<Object>();
		patientList = new JList(listModel);
		
		for(Patient p: patientArray){
			listModel.addElement(p.getName());
		}

		patientList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				Patient rawr = patientArray.get(patientList.getSelectedIndex());
				patientInfoArea.setText("AGE: " + rawr.getAge() + "\n" 
										 + "Name: " + rawr.getName() + "\n"  
										 + "HEIGHT: " + rawr.getHeight() + "\n" 
										 + "DOB: " + rawr.getDOB() + "\n"
										 + "BMI: " + rawr.getCalculatedBMI());
			}
		});
		
		Persistence.writeToFile("rawrs");
		Persistence.readAllPatients();
		patientList.setMinimumSize(new Dimension(200, 0));
		splitPane.setLeftComponent(patientList);
		
	}

	public static void updateList(Patient p){
		int theSize = patientArray.size();
		patientArray.add(p);
		listModel.addElement(p.getName());
	}
		
	public void makeWelcomePanel(){
		JPanel welcomePanel = new JPanel();
		contentPane.add(welcomePanel, "welcomePanel");
		card.show(contentPane, "welcomePanel");
		
		JLabel welcomeLabel = new JLabel("Welcome to the application");
		
		mainAppButton = new JButton("Main App");
		
		aboutButton = new JButton("About");
		
		JLabel createdByLabel = new JLabel("Created By: Ben Vest");
		GroupLayout welcomeLayout = new GroupLayout(welcomePanel);
		welcomeLayout.setHorizontalGroup(
			welcomeLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(welcomeLayout.createSequentialGroup()
					.addGap(305)
					.addComponent(mainAppButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(aboutButton)
					.addContainerGap(363, Short.MAX_VALUE))
				.addGroup(welcomeLayout.createSequentialGroup()
					.addGap(313)
					.addComponent(welcomeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(355))
				.addGroup(Alignment.TRAILING, welcomeLayout.createSequentialGroup()
					.addContainerGap(363, Short.MAX_VALUE)
					.addComponent(createdByLabel)
					.addGap(346))
		);
		welcomeLayout.setVerticalGroup(
			welcomeLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(welcomeLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(welcomeLabel)
					.addGap(30)
					.addGroup(welcomeLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(aboutButton)
						.addComponent(mainAppButton))
					.addPreferredGap(ComponentPlacement.RELATED, 437, Short.MAX_VALUE)
					.addComponent(createdByLabel)
					.addContainerGap())
		);
		welcomePanel.setLayout(welcomeLayout);
	}
	
	public void actionTime(){
		mainAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.next(contentPane);
			}
		});
		
		newPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewPatient newUsar = new NewPatient();
				newUsar.setVisible(true);
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search dialog = new Search();
				dialog.setVisible(true);
			}
		});
		
		sortByIDButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				sortedArray = Persistence.sortPatients(patientArray);
				int theSize = sortedArray.size();
			
				listModel.clear();
				System.out.println(listModel);
				
				for(int i = 0; i < theSize; i++){
					listModel.addElement(sortedArray.get(i).getName());
				}
				
				sortedArray.clear();
			}
		});
	}
}