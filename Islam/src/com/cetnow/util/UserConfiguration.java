package com.cetnow.util;

import java.io.Serializable;

public class UserConfiguration implements Serializable{

	private int ReaderId=44;
	private int BookId=7;

	public static int userId(){
		return 5;
	}
	public int getReaderId() {
		return ReaderId;
	}

	public void setReaderId(int readerId) {
		ReaderId = readerId;
	}

	public int getBookId() {
		return BookId;
	}

	public void setBookId(int bookId) {
		BookId = bookId;
	}
}
