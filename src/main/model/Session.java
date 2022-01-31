package main.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ApplicationSession")
@NamedQueries(value = {
		@NamedQuery(name = Session.GET_TODAYS_SESSION_BY_STATUS, query = "from Session where startingTime >= :todayMidnight and endingTime <= :tomorrowMidnight"), //
		@NamedQuery(name = Session.GET_SESSION_BY_ID, query = "from Session where id = :id") })
public class Session implements Serializable, Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3101125995648809002L;

	public static final String GET_TODAYS_SESSION_BY_STATUS = "Get.todays.session.by.status";

	public static final String GET_SESSION_BY_ID = "Session.by.id";

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	@Column(nullable = false)
	private String name;

	@Lob
	private String text;

	private String ownerName;

	@Column(nullable = false)
	private Date startingTime;

	@Column(nullable = false)
	private Date endingTime;

	@Enumerated(EnumType.STRING)
	private SessionStatus status;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Session_User", //
			joinColumns = { @JoinColumn(name = "session_id") }, //
			inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users = new ArrayList<>();

	public Session() {
		this("", new Date(), new Date(), SessionStatus.OFF);
	}

	public Session(String name, Date startingTime, Date endingTime, SessionStatus status) {
		this.name = name;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.status = status;
		this.text = "public class Test {" + System.lineSeparator() + "\t public static void main(String[] args) {"
				+ System.lineSeparator() + //
				"\t\t" + System.lineSeparator() + "\t }" + System.lineSeparator() + "}";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	public long getId() {
		return id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static String[] getColumns() {
		return new String[] { "ID", "Name", "Starting Time", "Ending Time", "Status" };
	}

	@Override
	public String[] getData() {
		return new String[] { id + "", name, new SimpleDateFormat("dd/MM/yyyy - hh:mm").format(startingTime),
				new SimpleDateFormat("dd/MM/yyyy - hh:mm").format(endingTime), status.toString() };
	}

	public void setEndingTime(Date time) {
		this.endingTime = time;
	}

	public Date getEndingTime() {
		return this.endingTime;
	}

	public void setStatus(SessionStatus on) {
		this.status = on;
	}

	public String getText() {
		return text;
	}

	public void setText(String text2) {
		this.text = text2;
	}

	public SessionStatus getStatus() {
		return status;
	}
}
