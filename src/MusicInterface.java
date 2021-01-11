import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTable;

public class MusicInterface {

	private JFrame frame;
	private JTable jtable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MusicInterface window = new MusicInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void play(PlaylistDAO pDAO, Playlist playlist) throws IOException, InterruptedException {

		for (int i = 0; i < playlist.getSongs().size(); i++) {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(playlist.getSongs().get(i).getLink()));
			System.out.println(playlist.getSongs().get(i).getDuration());
			String[] parts = playlist.getSongs().get(i).getDuration().split(":");
			String minutes = parts[0];
			String seconds = parts[1];
			int minute = Integer.parseInt(minutes);
			int secunde = Integer.parseInt(seconds);
			System.out.println(minute * 60 + secunde);
			TimeUnit.SECONDS.sleep(minute * 60 + secunde);
		}

	}

	public static void afisareMelodii(SongDAO sDAO) {
		for (Song s : sDAO.getAllSongs())
			System.out.println(s.getTitle() + " " + s.getArtist());
	}

	/**
	 * Create the application.
	 */
	public MusicInterface() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void addRowToJTable(SongDAO sDAO) {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		List<Song> list = Arrays.asList(sDAO.getAllSongs());
		Object rowData[] = new Object[5];
		for(int i=0; i<list.size();i++)
		{
			rowData[0] = list.get(i).getId();
			rowData[1] = list.get(i).getArtist();
			rowData[2] = list.get(i).getTitle();
			rowData[3] = list.get(i).getType();
			rowData[4] = list.get(i).getDuration();
			model.addRow(rowData);	
		}
	}

	private void initialize() {
		
		SongDAOFactory sDAOFactory = new SongDAOFactory();
		SongDAO sDAO = sDAOFactory.createSongDAO();

		PlaylistDAOFactory pDAOFactory = new PlaylistDAOFactory();
		PlaylistDAO pDAO = pDAOFactory.createPlaylistDAO();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnPlay = new JButton("Play");
		btnPlay.setVerticalAlignment(SwingConstants.TOP);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Song> songs = new ArrayList<>();
				Song sg = new Song(1, "nume", "inna", "1:05", "pop", "https://www.youtube.com/watch?v=Ck3sTkdtm0I");
				Song sg2 = new Song(2, "nume", "lady gaga", "3:50", "pop","https://www.youtube.com/watch?v=erg2UprJUH4&t=20s");
				songs.add(sg);
				songs.add(sg2);
				Playlist playlist = new Playlist(1, songs);
				System.out.println(playlist);
				try {
					play(pDAO, playlist);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnPlay, BorderLayout.NORTH);
		JButton btnSeeAll = new JButton("See All");
		btnSeeAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRowToJTable(sDAO);
			}
		});
		frame.getContentPane().add(btnSeeAll, BorderLayout.SOUTH);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		frame.getContentPane().add(btnAdd, BorderLayout.EAST);
		
		jtable = new JTable();
		frame.getContentPane().add(jtable, BorderLayout.CENTER);
		/*DefaultTableModel model = (DefaultTableModel) jtable.getModel();

		
		jtable.setBackground(Color.LIGHT_GRAY);
		jtable.setForeground(Color.black);
		jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		jtable.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(jtable);
		jtable.setFillsViewportHeight(true);
		List<Song> list = Arrays.asList(sDAO.getAllSongs());
		System.out.println(list);
		Object rowData[] = new Object[5];
		for(int i=0; i<list.size();i++)
		{
			rowData[0] = list.get(i).getId();
			rowData[1] = list.get(i).getArtist();
			rowData[2] = list.get(i).getTitle();
			rowData[3] = list.get(i).getType();
			rowData[4] = list.get(i).getDuration();
			model.addRow(rowData);	
		}*/
		
		String[] columns = new String[] {
	            "Id", "Name", "Hourly Rate", "Part Time"
	        };
	         
	        //actual data for the table in a 2d array
	        Object[][] data = new Object[][] {
	            {1, "John", 40.0, false },
	            {2, "Rambo", 70.0, false },
	            {3, "Zorro", 60.0, true },
	        };
	        //create table with data
	        JTable jtable = new JTable(data, columns);
	        jtable.setBackground(Color.LIGHT_GRAY);
		
		
	

	}

}