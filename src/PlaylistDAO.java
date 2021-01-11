
public interface PlaylistDAO{
	
	public Song searchSong(String title, String artist);
	public Song[] searchSongs(String artist);
	public SongPlayed[] songsPlayed();
	public SongPlayed[] songsPlayedToday();
	public SongPlayed[] songsPlayedMonth();
	public void addSongPlayed(Song s);
	public boolean deleteSongPlayed(int idSong);
	
}