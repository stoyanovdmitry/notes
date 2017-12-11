package com.notes.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users", catalog = "notes_schema")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String email;

	@OneToMany(mappedBy = "user")
	private List<Note> notes;

	public User() {
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (getId() != user.getId()) return false;
		if (!getUsername().equals(user.getUsername())) return false;
		if (!getPassword().equals(user.getPassword())) return false;
		return getEmail().equals(user.getEmail());
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + getUsername().hashCode();
		result = 31 * result + getPassword().hashCode();
		result = 31 * result + getEmail().hashCode();
		return result;
	}
}