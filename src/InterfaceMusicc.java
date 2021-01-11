import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

public class InterfaceMusicc extends JFrame {

	private JPanel contentPane;
	private JTextField textTitle;
	private JTextField textArtist;
	private JTextField textDuration;
	private JTextField textType;
	private JTextField textLink;
	List<Song> listSongs = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceMusicc frame = new InterfaceMusicc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	<--------------------------------------------->
//  METODA PLAY PENTRU O MELODIE
	public static void playSong(Song song) throws IOException, InterruptedException {
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(song.getLink()));
	}
//	<--------------------------------------------->

//	<--------------------------------------------->
//  METODA VERIFICARE pentru a verifica genul introdus 
	public static boolean verify(String gen) {
		List<String> types = Arrays.asList("pop", "rock", "clasic");
		if (types.contains(gen))
			return true;
		else
			return false;
	}
//	<--------------------------------------------->

// METODA PLAYALL pentru toate melodiile dintr-un playlist
//	<--------------------------------------------->
	public static void playAllSongs(Playlist playlist) throws IOException, InterruptedException {
		PlaylistDAOFactory pDAOFactory = new PlaylistDAOFactory();
		PlaylistDAO pDAO = pDAOFactory.createPlaylistDAO();
		for (int i = 0; i < playlist.getSongs().size(); i++) {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(playlist.getSongs().get(i).getLink()));
			// adauga in tabela songs_played melodia dupa ce am dat play
			pDAO.addSongPlayed(playlist.getSongs().get(i));
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
//	<--------------------------------------------->

//   Metoda pentru a lua toate melodiile din baza de date si apoi chemam playAllSongs (pentru a da play)
//	<--------------------------------------------->
	public static void getSongs(SongDAO sDAO) {
		List<Song> songs = Arrays.asList(sDAO.getAllSongs());
		Playlist playlist = new Playlist(1, songs);
		try {
			playAllSongs(playlist);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	<--------------------------------------------->

// luam toate melodiile din baza de date si le adaugam intr-o lista de object pentru TABEL
//	<--------------------------------------------->
	public List<Object[]> getAllSongs(SongDAO sDAO) {
		List<Song> songs = Arrays.asList(sDAO.getAllSongs());
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < songs.size(); i++) {
			list.add(new Object[] {

					songs.get(i).getId(), songs.get(i).getArtist(), songs.get(i).getTitle(), songs.get(i).getType(),
					songs.get(i).getDuration(), "Play", "Delete", "Add"

			});

		}
		return list;
	}
//	<--------------------------------------------->

// luam toate melodiile din playlist si le adaugam intr-o lista de object pentru TABEL PLAYLIST
//	<--------------------------------------------->
	public List<Object[]> getAllSongsInPlaylist() {
		List<Song> songs = listSongs;
		System.out.println(songs);
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < songs.size(); i++) {
			list.add(new Object[] { songs.get(i).getId(), songs.get(i).getTitle(), "Repeat" });

		}
		return list;
	}
//	<--------------------------------------------->

	public static Song[] cautareMelodii(PlaylistDAO pDAO, String addByArtist) {
		Song[] foundSongs;
		System.out.println(pDAO.searchSongs(addByArtist));
		foundSongs = pDAO.searchSongs(addByArtist);
		List<Song> songs = Arrays.asList(foundSongs);
		System.out.println(songs);
		return foundSongs;
	}

	/**
	 * Create the frame.
	 */
	public InterfaceMusicc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

//		<--------------------------------------------->
		SongDAOFactory sDAOFactory = new SongDAOFactory();
		SongDAO sDAO = sDAOFactory.createSongDAO();

		PlaylistDAOFactory pDAOFactory = new PlaylistDAOFactory();
		PlaylistDAO pDAO = pDAOFactory.createPlaylistDAO();
//		<--------------------------------------------->

// populam tabelul cu melodiile din baza de date
//		<--------------------------------------------->
		List<Object[]> list = getAllSongs(sDAO);
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(list.toArray(new Object[][] {}),
				new String[] { "ID", "Artist", "Title", "Type", "Duration", "Play", "Delete", "Add" });

		JTable table = new JTable(dm);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0, 11, 687, 133);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		contentPane.setLayout(null);
		contentPane.add(scroll);
//		<--------------------------------------------->

//           TITLE
//	<--------------------------------------------->
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(0, 185, 46, 14);
		contentPane.add(lblTitle);

		textTitle = new JTextField();
		textTitle.setBounds(56, 220, 146, 20);
		contentPane.add(textTitle);
		textTitle.setColumns(10);
//	<--------------------------------------------->

//      ARTIST
//	<--------------------------------------------->
		JLabel lblArtist = new JLabel("Artist");
		lblArtist.setBounds(0, 213, 46, 14);
		contentPane.add(lblArtist);

		textArtist = new JTextField();
		textArtist.setBounds(56, 189, 146, 20);
		contentPane.add(textArtist);
		textArtist.setColumns(10);
//	<--------------------------------------------->

//      DURATION
//	<--------------------------------------------->
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setBounds(0, 243, 46, 14);
		contentPane.add(lblDuration);

		textDuration = new JTextField();
		textDuration.setBounds(56, 251, 146, 20);
		contentPane.add(textDuration);
		textDuration.setColumns(10);
//	<--------------------------------------------->

//      TYPE
//	<--------------------------------------------->
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(0, 268, 46, 14);
		contentPane.add(lblType);

		textType = new JTextField();
		textType.setBounds(56, 282, 146, 20);
		contentPane.add(textType);
		textType.setColumns(10);
//	<--------------------------------------------->

//      LINK
//	<--------------------------------------------->
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(0, 293, 46, 14);
		contentPane.add(lblLink);

		textLink = new JTextField();
		textLink.setBounds(56, 308, 146, 20);
		contentPane.add(textLink);
		textLink.setColumns(10);
//	<--------------------------------------------->

//      Actiunea PLAY pentru butonul Play din tabel
//	<--------------------------------------------->	
		Action play = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				System.out.println(selectedRow);
				int id = (int) table.getValueAt(selectedRow, 0);
				System.out.println(id);
				Song sg = sDAO.findById(id);
				System.out.println(sg);
				try {
					playSong(sg);
					pDAO.addSongPlayed(sg);
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
//	<--------------------------------------------->	

		JLabel lblRepeat = new JLabel("Repeat");
		lblRepeat.setBounds(223, 280, 86, 20);
		contentPane.add(lblRepeat);

		JTextField textRepeat = new JTextField();
		textRepeat.setBounds(223, 300, 86, 20);
		contentPane.add(textRepeat);

//		<--------------------------------------------->

		JLabel lblAddArtist = new JLabel("Add by Artist");
		lblAddArtist.setBounds(223, 330, 86, 20);
		contentPane.add(lblAddArtist);

		JTextField textAddArtist = new JTextField();
		textAddArtist.setBounds(223, 350, 86, 20);
		contentPane.add(textAddArtist);

//  Actiunea REPEAT pentru butonul Repeat din tabelul cu playlist
//	<--------------------------------------------->		
		Action repeat = new AbstractAction() {
			public void actionPerformed(ActionEvent e1) {
				JTable table1 = (JTable) e1.getSource();
				int selectedRow = table1.getSelectedRow();
				System.out.println("Repeat");
				int id = (int) table1.getValueAt(selectedRow, 0);
				System.out.println(id);
				Song sg = sDAO.findById(id);

				int countRepeat = Integer.parseInt(textRepeat.getText()) - 1;
				System.out.println(countRepeat);
				System.out.println(sg);

				for (int i = 0; i < countRepeat; i++) {
					listSongs.add(sg);
					System.out.println(listSongs);
				}
			}
		};
//	<--------------------------------------------->	

//  Actiunea ADD TO PLAYLIST pentru butonul Add to Playlist din tabel
//	<--------------------------------------------->		
		Action addToPlaylist = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				int id = (int) table.getValueAt(selectedRow, 0);
				System.out.println(id);
				Song sg = sDAO.findById(id);
				listSongs.add(sg);
				System.out.println(listSongs);

				// refresh tabel
				DefaultTableModel dm1 = new DefaultTableModel();
				dm1.setDataVector(getAllSongsInPlaylist().toArray(new Object[][] {}),
						new String[] { "Id", "Title", "Repeat" });
				JTable table1 = new JTable(dm1);

				ButtonColumn buttonColumn4 = new ButtonColumn(table1, repeat, 2);
				buttonColumn4.setMnemonic(KeyEvent.VK_D);

				JScrollPane scroll1 = new JScrollPane(table1);
				scroll1.setBounds(330, 184, 250, 123);
				table1.setPreferredScrollableViewportSize(table1.getPreferredSize());
				table1.getColumnModel().getColumn(0).setPreferredWidth(100);
				contentPane.setLayout(null);
				table1.setLocation(200, 200);
				contentPane.add(scroll1);
			}
		};

//	<--------------------------------------------->	

//  Actiunea DELETE pentru butonul Delete din tabel
//	<--------------------------------------------->		
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Action delete1 = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						JTable table = (JTable) e.getSource();
						int selectedRow = table.getSelectedRow();
						System.out.println(selectedRow);
						int id = (int) table.getValueAt(selectedRow, 0);
						System.out.println(id);
						// stergem melodia cu id-ul respectiv
						sDAO.delete(id);
						pDAO.deleteSongPlayed(id);
						JOptionPane.showMessageDialog(null, "Song deleted!");

					}
				};
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				System.out.println(selectedRow);
				int id = (int) table.getValueAt(selectedRow, 0);
				System.out.println(id);
				// stergem melodia cu id-ul respectiv
				sDAO.delete(id);
				// stergem melodia si din songs_played
				pDAO.deleteSongPlayed(id);
				// refresh tabel
				List<Object[]> lista = getAllSongs(sDAO);
				DefaultTableModel dm2 = new DefaultTableModel();
				dm2.setDataVector(lista.toArray(new Object[][] {}),
						new String[] { "ID", "Artist", "Title", "Type", "Duration", "Delete", "Play", "Add" });
				table.setModel(dm2);
				ButtonColumn buttonColumn = new ButtonColumn(table, play, 5);
				buttonColumn.setMnemonic(KeyEvent.VK_D);
				ButtonColumn buttonColumn2 = new ButtonColumn(table, delete1, 6);
				buttonColumn2.setMnemonic(KeyEvent.VK_D);
				ButtonColumn buttonColumn3 = new ButtonColumn(table, addToPlaylist, 7);
				buttonColumn3.setMnemonic(KeyEvent.VK_D);
				JOptionPane.showMessageDialog(null, "Song deleted!");

			}
		};

//	<--------------------------------------------->	

//  Crearea coloanelor de butoane in tabelul principal
//		<--------------------------------------------->	
		ButtonColumn buttonColumn = new ButtonColumn(table, play, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		ButtonColumn buttonColumn2 = new ButtonColumn(table, delete, 6);
		buttonColumn2.setMnemonic(KeyEvent.VK_D);
		ButtonColumn buttonColumn3 = new ButtonColumn(table, addToPlaylist, 7);
		buttonColumn3.setMnemonic(KeyEvent.VK_D);
//	<--------------------------------------------->

//  Butonul ADD cu actiunea lui
//	<--------------------------------------------->	
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Song sg = new Song();
				sg.setId(0);
				sg.setTitle(textTitle.getText());
				sg.setArtist(textArtist.getText());
				sg.setDuration(textDuration.getText());
				sg.setType(textType.getText());
				sg.setLink(textLink.getText());

//			<-----------VERIFICARE AICI---------------------------------->
				boolean ok = false;
				do {
					ok = verify(textType.getText());
					System.out.println(ok);
					if (ok == true) {
						sDAO.addSong(sg);
						// refresh tabel
						List<Object[]> list = getAllSongs(sDAO);
						DefaultTableModel dm1 = new DefaultTableModel();
						dm1.setDataVector(list.toArray(new Object[][] {}), new String[] { "ID", "Artist", "Title",
								"Type", "Duration", "Play", "Delete", "Add to Playlist" });
						table.setModel(dm1);

						ButtonColumn buttonColumn = new ButtonColumn(table, play, 5);
						buttonColumn.setMnemonic(KeyEvent.VK_D);
						ButtonColumn buttonColumn2 = new ButtonColumn(table, delete, 6);
						buttonColumn2.setMnemonic(KeyEvent.VK_D);
						ButtonColumn buttonColumn3 = new ButtonColumn(table, addToPlaylist, 7);
						buttonColumn3.setMnemonic(KeyEvent.VK_D);
						JOptionPane.showMessageDialog(null, "Song added!");
						textTitle.setText("");
						textArtist.setText("");
						textDuration.setText("");
						textType.setText("");
						textLink.setText("");
					}
					if (ok == false)
						JOptionPane.showMessageDialog(null, "Introdu un gen valid!");
					ok = true;
				} while (ok == false);

//			<--------------------------------------------->
			}
		});
		btnAdd.setBounds(0, 155, 89, 23);
		contentPane.add(btnAdd);
//	<--------------------------------------------->	

//  Butonul Play All cu actiunea lui
//	<--------------------------------------------->
		JButton btnPlayAll = new JButton("Play All");
		btnPlayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSongs(sDAO);
			}
		});
		btnPlayAll.setBounds(96, 155, 89, 23);
		contentPane.add(btnPlayAll);
//	<--------------------------------------------->

//  Butonul See Top Songs cu actiunea lui
//	<--------------------------------------------->
		JButton btnSeeSongs = new JButton("See songs played");
		btnSeeSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea2 = new JTextArea();
				textArea2.setBounds(20, 380, 180, 150);
				contentPane.add(textArea2);
				textArea2.setText("Top Songs\n");
				for (SongPlayed s : pDAO.songsPlayed()) {
					Song sg = sDAO.findById(s.getIdSong());
					textArea2.setText(textArea2.getText() + "\n" + sg.getArtist() + " - " + sg.getTitle() + " : "
							+ s.getNrRepetari());
				}
			}
		});
		btnSeeSongs.setBounds(200, 155, 120, 23);
		contentPane.add(btnSeeSongs);
//	<--------------------------------------------->

//  Butonul See Top Songs TODAY cu actiunea lui
//	<--------------------------------------------->
		JButton btnSeeSongsToday = new JButton("Daily Statistics");
		btnSeeSongsToday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea1 = new JTextArea();
				textArea1.setBounds(220, 380, 180, 150);
				contentPane.add(textArea1);
				textArea1.setText("Top Songs Daily\n");
				for (SongPlayed s : pDAO.songsPlayedToday()) {
					Song sg = sDAO.findById(s.getIdSong());
					textArea1.setText(textArea1.getText() + "\n" + sg.getArtist() + " - " + sg.getTitle() + " : "
							+ s.getNrRepetari());
				}

			}
		});
		btnSeeSongsToday.setBounds(200, 200, 120, 23);
		contentPane.add(btnSeeSongsToday);
//	<--------------------------------------------->

//  Butonul See Top Songs MONTH cu actiunea lui
//	<--------------------------------------------->
		JButton btnSeeSongsMonth = new JButton("Month Statistics");
		btnSeeSongsMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = new JTextArea();
				textArea.setBounds(420, 380, 180, 150);
				contentPane.add(textArea);
				textArea.setText("Top Songs Monthly\n");
				for (SongPlayed s : pDAO.songsPlayedMonth()) {
					Song sg = sDAO.findById(s.getIdSong());
					textArea.setText(textArea.getText() + "\n" + sg.getArtist() + " - " + sg.getTitle() + " : "
							+ s.getNrRepetari());
				}

			}
		});
		btnSeeSongsMonth.setBounds(200, 250, 120, 23);
		contentPane.add(btnSeeSongsMonth);
//	<--------------------------------------------->

//  Butonul Play Playlist cu actiunea lui
//	<--------------------------------------------->
		JButton btnPlayPlaylist = new JButton("Play Playlist");
		btnPlayPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Playlist playlist = new Playlist(3, listSongs);

				try {
					playAllSongs(playlist);

				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPlayPlaylist.setBounds(330, 155, 115, 23);
		contentPane.add(btnPlayPlaylist);
//	<--------------------------------------------->

		// Butonul Add by Artist cu actiunea lui
//		<--------------------------------------------->
		JButton btnAddByArtist = new JButton("Add by Artist");
		btnAddByArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addByArtist = textAddArtist.getText();
				for (Song sg : pDAO.searchSongs(addByArtist)) {
					listSongs.add(sg);
					System.out.println(sg);
				}
				
				// refresh tabel
				DefaultTableModel dm1 = new DefaultTableModel();
				dm1.setDataVector(getAllSongsInPlaylist().toArray(new Object[][] {}),
						new String[] { "Id", "Title", "Repeat" });
				JTable table1 = new JTable(dm1);

				ButtonColumn buttonColumn4 = new ButtonColumn(table1, repeat, 2);
				buttonColumn4.setMnemonic(KeyEvent.VK_D);

				JScrollPane scroll1 = new JScrollPane(table1);
				scroll1.setBounds(330, 184, 250, 123);
				table1.setPreferredScrollableViewportSize(table1.getPreferredSize());
				table1.getColumnModel().getColumn(0).setPreferredWidth(100);
				contentPane.setLayout(null);
				table1.setLocation(200, 200);
				contentPane.add(scroll1);

			}
		});
		btnAddByArtist.setBounds(450, 155, 115, 23);
		contentPane.add(btnAddByArtist);
//		<--------------------------------------------->
	}

//  Clasa ButtonColumn care creeaza butoanele in tabele
//	<--------------------------------------------->
	public class ButtonColumn extends AbstractCellEditor
			implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
		private JTable table;
		private Action action;
		private int mnemonic;
		private Border originalBorder;
		private Border focusBorder;

		private JButton renderButton;
		private JButton editButton;
		private Object editorValue;
		private boolean isButtonColumnEditor;

		/**
		 * Create the ButtonColumn to be used as a renderer and editor. The renderer and
		 * editor will automatically be installed on the TableColumn of the specified
		 * column.
		 *
		 * @param table  the table containing the button renderer/editor
		 * @param action the Action to be invoked when the button is invoked
		 * @param column the column to which the button renderer/editor is added
		 */
		public ButtonColumn(JTable table, Action action, int column) {
			this.table = table;
			this.action = action;

			renderButton = new JButton();
			editButton = new JButton();
			editButton.setFocusPainted(false);
			editButton.addActionListener(this);
			originalBorder = editButton.getBorder();
			setFocusBorder(new LineBorder(Color.BLUE));

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
			table.addMouseListener(this);
		}

		/**
		 * Get foreground color of the button when the cell has focus
		 *
		 * @return the foreground color
		 */
		public Border getFocusBorder() {
			return focusBorder;
		}

		/**
		 * The foreground color of the button when the cell has focus
		 *
		 * @param focusBorder the foreground color
		 */
		public void setFocusBorder(Border focusBorder) {
			this.focusBorder = focusBorder;
			editButton.setBorder(focusBorder);
		}

		public int getMnemonic() {
			return mnemonic;
		}

		/**
		 * The mnemonic to activate the button when the cell has focus
		 *
		 * @param mnemonic the mnemonic
		 */
		public void setMnemonic(int mnemonic) {
			this.mnemonic = mnemonic;
			renderButton.setMnemonic(mnemonic);
			editButton.setMnemonic(mnemonic);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null) {
				editButton.setText("");
				editButton.setIcon(null);
			} else if (value instanceof Icon) {
				editButton.setText("");
				editButton.setIcon((Icon) value);
			} else {
				editButton.setText(value.toString());
				editButton.setIcon(null);
			}

			this.editorValue = value;
			return editButton;
		}

		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}

//
//  Implement TableCellRenderer interface
//
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				renderButton.setForeground(table.getSelectionForeground());
				renderButton.setBackground(table.getSelectionBackground());
			} else {
				renderButton.setForeground(table.getForeground());
				renderButton.setBackground(UIManager.getColor("Button.background"));
			}

			if (hasFocus) {
				renderButton.setBorder(focusBorder);
			} else {
				renderButton.setBorder(originalBorder);
			}

//		renderButton.setText( (value == null) ? "" : value.toString() );
			if (value == null) {
				renderButton.setText("");
				renderButton.setIcon(null);
			} else if (value instanceof Icon) {
				renderButton.setText("");
				renderButton.setIcon((Icon) value);
			} else {
				renderButton.setText(value.toString());
				renderButton.setIcon(null);
			}

			return renderButton;
		}

//
//  Implement ActionListener interface
//
		/*
		 * The button has been pressed. Stop editing and invoke the custom Action
		 */
		public void actionPerformed(ActionEvent e) {
			int row = table.convertRowIndexToModel(table.getEditingRow());
			fireEditingStopped();

			// Invoke the Action

			ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
			action.actionPerformed(event);
		}

//
//  Implement MouseListener interface
//
		/*
		 * When the mouse is pressed the editor is invoked. If you then then drag the
		 * mouse to another cell before releasing it, the editor is still active. Make
		 * sure editing is stopped when the mouse is released.
		 */
		public void mousePressed(MouseEvent e) {
			if (table.isEditing() && table.getCellEditor() == this)
				isButtonColumnEditor = true;
		}

		public void mouseReleased(MouseEvent e) {
			if (isButtonColumnEditor && table.isEditing())
				table.getCellEditor().stopCellEditing();

			isButtonColumnEditor = false;
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

//	<------------------END OF BUTTONCOLUMN-------------------------->

}
