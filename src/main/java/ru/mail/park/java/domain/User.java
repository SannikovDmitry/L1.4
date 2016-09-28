package ru.mail.park.java.domain;

public class User {
	private final String name;
	private long power;
	private int wins;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getPower() {
		return power;
	}

	public int getWins() {
		return wins;
	}

	public synchronized User increasePower() {
		power++;
		return this;
	}

	public synchronized User fight(User otherUser) {
		synchronized (otherUser) {
			if (power > otherUser.power) {
				wins++;
				power -= otherUser.power;
				otherUser.power = 0;
			} else if (power < otherUser.power) {
				otherUser.wins++;
				otherUser.power -= power;
				power = 0;
			}
			return this;
		}
	}
}
