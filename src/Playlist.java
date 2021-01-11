import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private int idPlaylist;
	private List<Song> songs = new ArrayList<>();

	public Playlist(int idPlaylist, List<Song> songs) {
		this.idPlaylist = idPlaylist;
		this.songs = songs;
	}

	public int getIdPlaylist() {
		return idPlaylist;
	}

	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Playlist [idPlaylist=" + idPlaylist + ", songs=" + songs + "]";
	}

}
