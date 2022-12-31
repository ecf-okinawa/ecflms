package com.e3factory.common.util;

import org.springframework.context.MessageSource;

/**
 * システムのメッセージオブジェクト
 * @author ecf_pc
 *
 */
public class AppMessage {
	public static enum Level{
		INFO,
		WARN,
		ERROR
	};

	private String id;
	private Level level;
	private String message;

	private AppMessage(String id, Level level, String message) {
		this.id = id;
		this.level = level;
		this.message = message;
	}

	public static AppMessage getMessage(MessageSource source, String id, Level level, String... param) {
		String message = Utility.getMessage(source, id, param);
		AppMessage appMessage = new AppMessage(id, level, message);
		return appMessage;
	}

	public String getId() {
		return id;
	}

	public Level getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}
}
