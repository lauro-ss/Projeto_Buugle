package cliente;

import java.net.MalformedURLException;
import java.net.URL;

public class Hyperlink {
	private String title;
	private String url;
	private String host;
	
	public Hyperlink(String url, String title) throws MalformedURLException {
		this.title = title;
		this.url = url;
		URL aux_url = new URL(url);
		this.host = aux_url.getHost();
	}
	
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public String getHost() {
		return host;
	}
}
