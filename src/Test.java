import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void adaugare(SongDAO sDAO) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdu titlul melodiei:");
		String title = sc.next();
		System.out.println("Introdu artistul:");
		String artist = sc.next();
		System.out.println("Introdu durata:");
		String duration = sc.next();
		boolean ok = false;
		do {
			System.out.println("Introdu genul:");
			String type = sc.next();
			ok = verificare(type);
			System.out.println(ok);
			if (ok == true) {
				System.out.println("Introdu link:");
				String link = sc.next();
				Song song = new Song(1, title, artist, duration, type, link);
				sDAO.addSong(song);
			}

		} while (ok == false);
	}

	public static boolean verificare(String gen) {
		List<String> types = Arrays.asList("pop", "rock", "clasic");
		if (types.contains(gen))
			return true;
		else
			return false;
	}

	public static void stergere(SongDAO sDAO) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdu id-ul melodiei:");
		int id = sc.nextInt();
		sDAO.delete(id);
	}

	public static void afisareMelodii(SongDAO sDAO) {
		for (Song s : sDAO.getAllSongs())
			System.out.println(s);
	}

	public static Song cautareMelodie(PlaylistDAO pDAO) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdu titlul melodiei:");
		String title = sc.next();
		System.out.println("Introdu artistul:");
		String artist = sc.next();
		Song foundSong = pDAO.searchSong(title, artist);
		System.out.println(foundSong);
		return foundSong;
	}

	public static void adaugareMelodie(PlaylistDAO pDAO) {
		List<Song> songs = new ArrayList<>();
		Song sg = cautareMelodie(pDAO);
		songs.add(sg);
		Playlist playlist = new Playlist(1, songs);
		System.out.println(playlist);

	}

	public static Song[] cautareMelodii(PlaylistDAO pDAO) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdu artistul:");
		String artist = sc.next();
		Song[] foundSongs = pDAO.searchSongs(artist);
		return foundSongs;
	}

	public static void adaugareMelodii(PlaylistDAO pDAO) {
		List<Song> songs = Arrays.asList(cautareMelodii(pDAO));
		Playlist playlist = new Playlist(2, songs);
		System.out.println(playlist);
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
	
	public static void playSong(Song song) throws IOException, InterruptedException {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(song.getLink()));
		}
	

	public static void main(String[] args) throws IOException, InterruptedException {

		SongDAOFactory sDAOFactory = new SongDAOFactory();
		SongDAO sDAO = sDAOFactory.createSongDAO();

		PlaylistDAOFactory pDAOFactory = new PlaylistDAOFactory();
		PlaylistDAO pDAO = pDAOFactory.createPlaylistDAO();

		//adaugare(sDAO);
		// stergere(sDAO);
		//afisareMelodii(sDAO);
		// adaugareMelodie(pDAO);
		adaugareMelodii(pDAO);
		/*
		 * List<Song> songs = new ArrayList<>(); 
		 * Song sg = new
		 * Song(1,"nume","inna","1:05","pop",
		 * "https://www.youtube.com/watch?v=Ck3sTkdtm0I"); Song sg2 = new
		 * Song(2,"nume","lady gaga","3:50","pop",
		 * "https://www.youtube.com/watch?v=erg2UprJUH4&t=20s"); songs.add(sg);
		 * songs.add(sg2); Playlist playlist = new Playlist(1,songs);
		 * System.out.println(playlist); play(pDAO, playlist);
		 */
		//Song sg = new Song(1,"nume","inna","1:05","pop","https://www.youtube.com/watch?v=Ck3sTkdtm0I");
		//playSong(sg);
	}

}
