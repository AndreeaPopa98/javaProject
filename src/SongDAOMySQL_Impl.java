import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAOMySQL_Impl implements SongDAO{

	public static final String CONNECTION_URL = "jdbc:mysql://localhost/song_db";
	
	@Override
	public void addSong(Song s) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into songs(title,artist,duration,type,link) values(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, s.getTitle());
			ps.setString(2, s.getArtist());
			ps.setString(3, s.getDuration());
			ps.setString(4, s.getType());
			ps.setString(5, s.getLink());

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
	
	@Override
	public boolean update(Song s) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("update songs set title = ?, artist = ?,duration = ?, type = ?,link = ? where id = ?");
			ps.setString(1, s.getTitle());
			ps.setString(2, s.getArtist());
			ps.setString(3, s.getDuration());
			ps.setString(4, s.getType());
			ps.setString(5, s.getLink());
			ps.setInt(6, s.getId());
			
			int affectedRows = ps.executeUpdate();
			closeConnection(conn);
			return affectedRows == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from songs where id = ?");
			ps.setInt(1, id); 
			int affectedRows = ps.executeUpdate();
			closeConnection(conn);
			return affectedRows == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Song findById(int id) {
		Song s = null;
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from songs where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id2 = rs.getInt("id");
				String title = rs.getString("title");
				String artist = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				s = new Song(id2, title, artist, duration, type, link);
			}
			closeConnection(conn);
			return s;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Song[] getAllSongs() {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from songs");
			ResultSet rs = ps.executeQuery();
			List<Song> songList = new ArrayList<Song>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String artist = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				songList.add(new Song(id, title, artist, duration, type, link));
			}
			closeConnection(conn);
			return songList.toArray(new Song[songList.size()]);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, "root", "root");
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
}
