package com.e3factory.common.util;

import java.util.Locale;

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

	/**
	 * メッセージオブジェクトを生成する。
	 * @param source MessageSourceインスタンス
	 * @param msgId messages.propertiesに定義済みのキー
	 * @param level メッセージレベル
	 * @param param プレースホルダーパラメータ
	 * @return
	 */
	public static AppMessage getMessage(MessageSource source, String msgId, Level level, String... param) {
		String message = getMessage(source, msgId, param);
		AppMessage appMessage = new AppMessage(msgId, level, message);
		return appMessage;
	}

	/**
	 * メッセージを生成する。
	 * @param source MessageSourceインスタンス
	 * @param msgId messages.propertiesに定義済みのキー
	 * @param params プレースホルダーパラメータ
	 * @return
	 */
	public static String getMessage(MessageSource source, String msgId, String... params ) {
		return source.getMessage(msgId, params, Locale.getDefault());
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
