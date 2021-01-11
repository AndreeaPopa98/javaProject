
public class SongPlayed {

	int idSong;
	String date;
	int nrRepetari;
	
	public SongPlayed(int idSong, String date, int nrRepetari) {
		this.idSong = idSong;
		this.date = date;
		this.nrRepetari = nrRepetari;
	}
	public int getIdSong() {
		return idSong;
	}
	public void setIdSong(int idSong) {
		this.idSong = idSong;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getNrRepetari() {
		return nrRepetari;
	}
	public void setNrRepetari(int nrRepetari) {
		this.nrRepetari = nrRepetari;
	}
	@Override
	public String toString() {
		return "SongPlayed [idSong=" + idSong + ", date=" + date + ", nrRepetari=" + nrRepetari + "]";
	}
	
	
	
}
