package com.notes.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Note implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Note() {
	}

	public Note(String text) {
		this.text = text;
	}

	public Note(String text, User user) {
		this.text = text;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Note note = (Note) o;

		if (getId() != note.getId()) return false;
		return getText().equals(note.getText());
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + getText().hashCode();
		return result;
	}
}
