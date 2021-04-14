package bsu.rfe.group7.lab7.varB;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class MainFrame extends JFrame {

	private static final String FRAME_TITLE = "Клиент мгновенных сообщений";
	
	private static final int FRAME_MINIMUM_WIDTH = 500;
	private static final int FRAME_MINIMUM_HEIGHT = 500;
	
	private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
	private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
	
	private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
	private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
	
	private static final int SMALL_GAP = 5;
	private static final int MEDIUM_GAP = 10;
	private static final int LARGE_GAP = 15;
	
	private static final int SERVER_PORT = 4567;
	
	private final JTextField textFieldFrom;
	private final JTextField textFieldTo;
	
	public final JTextArea textAreaIncoming;
	public final JTextArea textAreaOutgoing;
	
	private InstantMassage IM;
	
	public MainFrame() {
		super(FRAME_TITLE);
		setMinimumSize(
				new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
	

		final Toolkit kit = Toolkit.getDefaultToolkit();
		setLocation((kit.getScreenSize().width - getWidth()) / 2,
				(kit.getScreenSize().height - getHeight()) / 2);
		
		
		textAreaIncoming = new JTextArea();
		textAreaIncoming.setEditable(false);
		/*/textAreaIncoming.addHyperlinkListener(new  HyperlinkListener() {
	        public void hyperlinkUpdate(HyperlinkEvent he) {
	            // Проверка типа события
	            if ( he.getEventType() != HyperlinkEvent.EventType.ACTIVATED) 
	                return;
	          
	           
	        }
	    });/*/

		final JScrollPane scrollPaneIncoming = new JScrollPane(textAreaIncoming);

		final JLabel labelFrom = new JLabel("Подпись");
		final JLabel labelTo = new JLabel("Получатель");

		textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
		textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);
		textFieldTo.setText("127.0.0.1");

		textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);

		final JScrollPane scrollPaneOutgoing = new JScrollPane(textAreaOutgoing);

		final JPanel messagePanel = new JPanel();
		messagePanel.setBorder(BorderFactory.createTitledBorder("Сообщение"));

		final JButton sendButton = new JButton("Отправить");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IM.sendMessage(textFieldFrom.getText(), textFieldTo.getText(),SERVER_PORT
						);
			}
		});

		final GroupLayout layout2 = new GroupLayout(messagePanel);
		messagePanel.setLayout(layout2);
		
		layout2.setHorizontalGroup(layout2.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout2.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout2.createSequentialGroup()
					.addComponent(labelFrom)
					.addGap(SMALL_GAP)
					.addComponent(textFieldFrom)
					.addGap(LARGE_GAP)
					.addComponent(labelTo)
					.addGap(SMALL_GAP)
					.addComponent(textFieldTo))
				.addComponent(scrollPaneOutgoing)
				.addComponent(sendButton))
			.addContainerGap());
		layout2.setVerticalGroup(layout2.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout2.createParallelGroup(Alignment.BASELINE)
					.addComponent(labelFrom)
					.addComponent(textFieldFrom)
					.addComponent(labelTo)
					.addComponent(textFieldTo))
				.addGap(MEDIUM_GAP)
				.addComponent(scrollPaneOutgoing)
				.addGap(MEDIUM_GAP)
				.addComponent(sendButton)
				.addContainerGap());
			
				final GroupLayout layout1 = new GroupLayout(getContentPane());
				setLayout(layout1);
				
				layout1.setHorizontalGroup(layout1.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout1.createParallelGroup()
						.addComponent(scrollPaneIncoming)
						.addComponent(messagePanel))
					.addContainerGap());
				layout1.setVerticalGroup(layout1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneIncoming)
					.addGap(MEDIUM_GAP)
					.addComponent(messagePanel)
					.addContainerGap());
			
				
				
				IM = new InstantMassage(SERVER_PORT, this );
	}
	
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					final MainFrame frame = new MainFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			});
     }
}
