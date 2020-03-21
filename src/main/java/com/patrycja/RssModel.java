package com.patrycja;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RssModel {
	@Id
	@GeneratedValue
	private Long id;
	private String link;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "RssModel [id=" + id + ", link=" + link + "]";
	}
	
	
	
	
}
