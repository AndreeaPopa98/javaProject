import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Color;

public class MusicInterface2 extends JFrame {

	private JPanel contentPane;
	private JTable table_2;

	table_1 = new JTable();
	table_1.setBounds(529, 308, -256, -109);
	contentPane.add(table_1);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicInterface2 frame = new MusicInterface2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addRowToJTable(SongDAO sDAO) {
		DefaultTableModel model = (DefaultTableModel) table_2.getModel();
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
	
	

	/**
	 * Create the frame.
	 */
	public MusicInterface2() {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		SongDAOFactory sDAOFactory = new SongDAOFactory();
		SongDAO sDAO = sDAOFactory.createSongDAO();
		//headers for the table
       
        //actual data for the table in a 2d array
		
		
		List<Song> songs = Arrays.asList(sDAO.getAllSongs());
		 List<Object[]> list = new ArrayList<Object[]>();
		 for(int i=0; i<songs.size();i++) {
		        list.add(new Object[] { 
		        		
		        		songs.get(i).getId(),
		    			songs.get(i).getArtist(),
		    			songs.get(i).getTitle(),
		    			songs.get(i).getType(),
		    			songs.get(i).getDuration(),
		    			"button"

		                              });

		    }
		 
		 
		table_2 = new JTable();
		table_2.setBackground(Color.PINK);
		
		
		 table_2.setModel(new DefaultTableModel(list.toArray(new Object[][] {}), 
                 new String[] {"First Name", "Surname", "Phone Number","jiy","kjhjuyg",""}));
		
		 
		contentPane.add(table_2, BorderLayout.CENTER);
		
	
		ListSelectionModel cellSelectionModel = table_2.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	        String selectedData = null;

	        int[] selectedRow = table_2.getSelectedRows();
	        int[] selectedColumns = table_2.getSelectedColumns();

	        for (int i = 0; i < selectedRow.length; i++) {
	          for (int j = 0; j < selectedColumns.length; j++) {
	            selectedData = (String) table_2.getValueAt(selectedRow[i], selectedColumns[j]);
	          }
	        }
	        System.out.println("Selected: " + selectedData);
	      }
	    });
		
		
		
        
	}

}
