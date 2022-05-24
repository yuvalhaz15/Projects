import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldCrews;
	private JTextField textFieldWorkTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel headLine= new JLabel("JAVA Pizza & Coffee");
		headLine.setFont(new Font("Arial", Font.BOLD, 25));
		headLine.setBounds(170, 35, 322, 29);
		contentPane.add(headLine);

		ImageIcon icon = new ImageIcon("data\\pizzaPic.jpg");
		JLabel label = new JLabel(icon);
		label.setBounds(150, 50, 50, 29);
		//label.setSize(200, 200);
	
		contentPane.add(label);
		JLabel lblNumberOfPizzaGuys= new JLabel("Number of Pizza Guys:");
		lblNumberOfPizzaGuys.setFont(new Font("Times New Roman ", Font.BOLD, 14));
		lblNumberOfPizzaGuys.setBounds(33, 108, 201, 29);
		contentPane.add(lblNumberOfPizzaGuys );

		JLabel kitchenWorkingTime= new JLabel("Kitchen Workers working time:");
		kitchenWorkingTime.setFont(new Font("Times New Roman ", Font.BOLD, 14));
		kitchenWorkingTime.setBounds(300, 108, 290, 29);
		contentPane.add(kitchenWorkingTime);

		textFieldCrews = new JTextField();
		textFieldCrews.setText("1");
		textFieldCrews.setBounds(77, 148, 86, 20);
		contentPane.add(textFieldCrews);
		textFieldCrews.setColumns(10);

		textFieldWorkTime = new JTextField();
		textFieldWorkTime.setText("2");
		textFieldWorkTime.setBounds(397, 148, 86, 20);
		contentPane.add(textFieldWorkTime);
		textFieldWorkTime.setColumns(10);

		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberOfPizzaGuys = 2;
				double timeOfCooking = 1;

				String techsStr = textFieldCrews.getText();
				String timeStr = textFieldWorkTime.getText();

				boolean canStart = true;
				
				if(canStart == false)
					return;

				numberOfPizzaGuys = Integer.parseInt(techsStr);
				timeOfCooking = Double.parseDouble(timeStr);

				Restaurant rest = new Restaurant(numberOfPizzaGuys,timeOfCooking);
				rest.startWork();
				
			}

			
		});
		btnStart.setBounds(74, 281, 89, 23);
		contentPane.add(btnStart);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(397, 281, 89, 23);
		contentPane.add(btnExit);
	}
}