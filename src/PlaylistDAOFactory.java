
public class PlaylistDAOFactory {
	PlaylistDAO createPlaylistDAO() {
		return new PlaylistDAOMySQL_Impl();
	}
}
