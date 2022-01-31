package main.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ApplicationUser")
@NamedQueries(value = { @NamedQuery(name = User.GET_USER_BY_USERNAME, query = "from User where username = :username"), //
		@NamedQuery(name = User.GET_USER_BY_EMAIL, query = "from User where email = :email") //
})

public class User implements Serializable, Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8659409060120793825L;

	public static final String GET_USER_BY_USERNAME = "Get.user.by.username";
	public static final String GET_USER_BY_EMAIL = "Get.user.by.email";

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Session> sessions;

	public User() {
	}

	public User(String userName, String password, String email) {
		this.username = userName;
		this.password = password;
		this.email = email;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	@Override
	public String[] getData() {
		return new String[] { username };
	}

	public static String[] getColumns() {
		return new String[] { "Username" };
	}
}
