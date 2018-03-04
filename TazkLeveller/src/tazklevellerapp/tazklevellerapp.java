package tazklevellerapp;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.TextArea;
import java.util.Calendar;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class tazklevellerapp {
	private static final String filepathPrefs=".\\Data\\Preferences.ser";
	private static final String filepathList=".\\Data\\List.ser";
	
	private JFrame frame;
	private JPanel panel;
	
	private Preferences prefs;
	private Calendar calendar = Calendar.getInstance();
	private int timeHr = calendar.get(Calendar.HOUR_OF_DAY);
	private JTable table;
	private TaskList taskList;
	
	private int percent;
	private JLabel HappyPuppy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tazklevellerapp window = new tazklevellerapp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public tazklevellerapp() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		if (!new File(filepathPrefs).exists())
		{
			prefs = new Preferences();
		} else {
			prefs = (Preferences) readObjectFromFile(filepathPrefs);
		}
		if (!new File(filepathList).exists())
		{
			taskList = new TaskList();
		} else {
			taskList = (TaskList) readObjectFromFile(filepathList);
		}
				
		frame = new JFrame();
		frame.getContentPane().setBackground(prefs.getColor());
		frame.setBounds(100, 100, 647, 952);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(prefs.getColor());
		tabbedPane.setBounds(0, 0, 625, 896);
		frame.getContentPane().add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("Home",new ImageIcon("./Img/home.png") , panel, null);
		
		JLabel lblGood = new JLabel("Hello,");
		lblGood.setHorizontalAlignment(SwingConstants.CENTER);
		lblGood.setBounds(15, 16, 590, 65);
		panel.setLayout(null);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new File(filepathPrefs);
				new File(filepathList);
				writeObjectToFile(prefs,filepathPrefs);
				writeObjectToFile(taskList,filepathList);
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setBounds(15, 16, 80, 29);
		panel.add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					initialize();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLoad.setBounds(101, 16, 95, 29);
		panel.add(btnLoad);
		
		lblGood.setFont(new Font("Segoe UI", Font.BOLD, 40));
		
		panel.add(lblGood);
		
		if(timeHr >= 6&& timeHr < 12) {
			lblGood.setText("Good Morning,");
		} else if(timeHr >= 12 && timeHr < 18) {
			lblGood.setText("Good Afternoon,");
		} else{
			lblGood.setText("Good Evening,");
		}
		
		SimpleAttributeSet attribs = new SimpleAttributeSet();  
		StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER); 
		StyleConstants.setFontFamily(attribs,"Segoe UI Light");
		StyleConstants.setFontSize(attribs, 30);
		
		JTextPane txtpnWhatWouldYou = new JTextPane();
		txtpnWhatWouldYou.setBackground(prefs.getColor());
		txtpnWhatWouldYou.setParagraphAttributes(attribs,true);
		txtpnWhatWouldYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 30));
		txtpnWhatWouldYou.setEditable(false);
		txtpnWhatWouldYou.setText("What would you like to accomplish today?");
		txtpnWhatWouldYou.setBounds(25, 86, 580, 58);
		
		
		panel.add(txtpnWhatWouldYou);
		JLabel label = new JLabel("");
		panel.add(label);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(-211, 175, 492, 71);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(15, 200, 421, 71);
		panel.add(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 19));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Urgent", "Normal", "Low"}));
		comboBox.setSelectedIndex(1);
		comboBox.setBounds(451, 200, 154, 71);
		panel.add(comboBox);
		
		JLabel label_1 = new JLabel("Incomplete Tasks: " + taskList.getIncompleteCount());
		label_1.setBounds(15, 792, 213, 20);
		panel.add(label_1);
		
		JButton btnAddTask = new JButton("Add Task");
		btnAddTask.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (textArea.getText().trim().length() > 0) {
					taskList.addTask(textArea.getText(),comboBox.getSelectedIndex());
					updateHomeTable();
					label_1.setText("Incomplete tasks: "+taskList.getIncompleteCount());
					System.out.println(taskList.getIncompleteCount());
				} else {}
				
			}
		});
		btnAddTask.setBounds(243, 287, 115, 29);
		panel.add(btnAddTask);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateHomeTable();
		table.getColumnModel().getColumn(0).setPreferredWidth(236);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.setBounds(15, 391, 590, 385);
		panel.add(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBackground(Color.WHITE);
		table.setRowHeight(55);
		
		JButton btnCompleteTask = new JButton("Completed");
		btnCompleteTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCompleteTask.setBounds(379, 788, 113, 29);
		panel.add(btnCompleteTask);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(499, 788, 95, 29);
		panel.add(btnDelete);
		
		
		
		
		
		ImageIcon happy = new ImageIcon("./Img/Happy.png");
		JLabel happyLabel = new JLabel(happy);
		
		ImageIcon Crying = new ImageIcon("./Img/Crying.png");
		JLabel cryingLabel = new JLabel(Crying);
		
		ImageIcon Okay = new ImageIcon("./Img/Not feeling Good.png");
		JLabel okayLabel = new JLabel(Okay);
		
		
		
		
		happyLabel.setBounds(130, 198, 334, 323);
		
		cryingLabel.setBounds(130, 198, 334, 323);
		
		okayLabel.setBounds(130, 198, 334, 323);
		
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		tabbedPane.addTab("Mood", new ImageIcon("./Img/mood.png"), panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Your Puppy's emotion is Happy");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 30));
		lblNewLabel.setBounds(16, 630, 538, 83);
		panel_1.add(lblNewLabel);
		
		if( taskList.getTotalCount() != 0) {
		
		percent = taskList.getCompletedCount()/taskList.getTotalCount();
		
		}
		else {
			percent = 20;
		}
		
		if (percent <= 100 && percent >= 66) {
			
			lblNewLabel.setText("Your Puppy's emotion is Happy");
			panel_1.add(happyLabel);
			
		}
		
		
		if(percent < 66 && percent >= 33) {
			
			lblNewLabel.setText("Your Puppy is task Worried ");
			panel_1.add(okayLabel);
			
		}
		
		if(percent < 33) {
			
			lblNewLabel.setText("Your Puppy's emotion is Sad");
			panel_1.add(cryingLabel);
			
		}
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(100);
		progressBar.setForeground(Color.WHITE);
		progressBar.setBounds(94, 46, 440, 33);
		panel_1.add(progressBar);
		
		progressBar.setValue(percent);
		
		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("All Tasks", new ImageIcon("./Img/notepad.png"), panel_2, null);
		UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(10, 100, 0, 0));
	}
	public void writeObjectToFile(Object serObj,String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            fileOut.close();
            System.out.println("The Object  was successfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public Object readObjectFromFile(String filepath) throws IOException {
		Object readCase = null;
		ObjectInputStream objectinputstream = null;
		try {
		    FileInputStream streamIn = new FileInputStream(filepath);
		    objectinputstream = new ObjectInputStream(streamIn);
		    readCase = objectinputstream.readObject();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(objectinputstream != null){
		        objectinputstream.close();
		    } 
		}
		return readCase;
	}
	
	public void updateHomeTable(){
		if (taskList.getTotalCount()==0) {
			table.setModel(new DefaultTableModel(new Object[][] {
				{"To-Do", "Severity"},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"To-Do", "Severity"
			}));
		}
		else {
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			int loopSize = Math.min(taskList.getIncompleteCount()+1, 7);
			for(int i =1;i<loopSize;i++) {
				tableModel.setValueAt(taskList.getNameIncomplete(i-1), i, 0);
				tableModel.setValueAt(taskList.getSeverityIncomplete(i-1), i, 1);
			}
			table.setModel(tableModel);
		}
	}
}
