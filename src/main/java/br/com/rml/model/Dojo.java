package br.com.rml.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Dojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dateTime;
	private String moderator;
	private String dojoLink;
	private String costCenter;

	public Dojo() {
	}

	public Dojo(String dateTime, String moderator, String dojoLink, String costCenter) {
		super();
		this.dateTime = dateTime;
		this.moderator = moderator;
		this.dojoLink = dojoLink;
		this.costCenter = costCenter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public String getDojoLink() {
		return dojoLink;
	}

	public void setDojoLink(String dojoLink) {
		this.dojoLink = dojoLink;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	@Override
	public String toString() {
		return dateTime + moderator + costCenter + dojoLink;
	}

}
