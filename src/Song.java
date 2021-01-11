
public class Song {

	private int id;
	private String title;
	private String artist;
	private String duration;
	private String type;
	private String link;

	public Song(int id, String title, String artist, String duration, String type, String link) {

		this.id = id;
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.type = type;
		this.link = link;
	}
	
	

	public Song() {
		
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", duration=" + duration + ", type="
				+ type + ", link=" + link + "]";
	}

}
