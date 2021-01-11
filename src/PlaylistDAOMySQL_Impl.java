import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO {

	public static final String CONNECTION_URL = "jdbc:mysql://localhost/song_db";

	public Song searchSong(String title, String artist) {
		try {
			Song sg = null;
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("select * from songs where title = ? AND artist = ?");
			ps.setString(1, title);
			ps.setString(2, artist);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title2 = rs.getString("title");
				String artist2 = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				sg = new Song(id, title2, artist2, duration, type, link);

			}

			closeConnection(conn);
			return sg;

		} catch (SQLException e) {
			return null;
		}
	}

	public Song[] searchSongs(String artist) {
		try {
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("select * from songs where artist = ?");
			ps.setString(1, artist);
			ResultSet rs = ps.executeQuery();
			List<Song> songList = new ArrayList<Song>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String artist2 = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				songList.add(new Song(id, title, artist2, duration, type, link));

			}

			closeConnection(conn);
			return songList.toArray(new Song[songList.size()]);

		} catch (SQLException e) {
			return null;
		}
	}

	
	public SongPlayed[] songsPlayed() {
		try {
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT *, Count(*) FROM song_db.songs_played GROUP BY idSong ORDER BY Count(*) DESC LIMIT 5;");
			ResultSet rs = ps.executeQuery();
			List<SongPlayed> songsPlayed = new ArrayList<SongPlayed>();
			while (rs.next()) {
				int idSong = rs.getInt("idSong");
				String date = rs.getString("date");
				int nrRepetari = rs.getInt("Count(*)");
				songsPlayed.add(new SongPlayed(idSong, date, nrRepetari));

			}

			closeConnection(conn);
			return songsPlayed.toArray(new SongPlayed[songsPlayed.size()]);

		} catch (SQLException e) {
			return null;
		}
	}
	
	public SongPlayed[] songsPlayedToday() {
		try {
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT *, Count(*) FROM song_db.songs_played WHERE date = CURDATE() GROUP BY idSong ORDER BY Count(*) DESC LIMIT 5;");
			ResultSet rs = ps.executeQuery();
			List<SongPlayed> songsPlayed = new ArrayList<SongPlayed>();
			while (rs.next()) {
				int idSong = rs.getInt("idSong");
				String date = rs.getString("date");
				int nrRepetari = rs.getInt("Count(*)");
				songsPlayed.add(new SongPlayed(idSong, date, nrRepetari));

			}

			closeConnection(conn);
			return songsPlayed.toArray(new SongPlayed[songsPlayed.size()]);

		} catch (SQLException e) {
			return null;
		}
	}
	
	public SongPlayed[] songsPlayedMonth() {
		try {
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT *, Count(*) FROM song_db.songs_played WHERE MONTH(date) = MONTH(CURDATE()) GROUP BY idSong ORDER BY Count(*) DESC LIMIT 5;");
			ResultSet rs = ps.executeQuery();
			List<SongPlayed> songsPlayed = new ArrayList<SongPlayed>();
			while (rs.next()) {
				int idSong = rs.getInt("idSong");
				String date = rs.getString("date");
				int nrRepetari = rs.getInt("Count(*)");
				songsPlayed.add(new SongPlayed(idSong, date, nrRepetari));

			}

			closeConnection(conn);
			return songsPlayed.toArray(new SongPlayed[songsPlayed.size()]);

		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public boolean deleteSongPlayed(int idSong) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from songs_played where idSong = ?");
			ps.setInt(1, idSong); 
			int affectedRows = ps.executeUpdate();
			closeConnection(conn);
			return affectedRows == 1;
		} catch (SQLException e) {
			return false;
		}
	}
	
	
	@Override
	public void addSongPlayed(Song s) {
		try {
			Connection conn = getConnection();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date currentDate = new Date();
			//System.out.println(dateFormat.format(dated));
			PreparedStatement ps = conn.prepareStatement("insert into songs_played(idSong,date) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, s.getId());
			ps.setString(2, dateFormat.format(currentDate));

			int affectedRows = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				s.setId(rs.getInt(1));
			}
			closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, "root", "root");
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
}
