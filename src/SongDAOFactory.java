
public class SongDAOFactory {

	SongDAO createSongDAO() {
		return new SongDAOMySQL_Impl();
	}
}
