package com.e3factory.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * メッセージユーティリティ
 * Singleton設計
 * @author ecf_pc
 *
 */
@Component
public class MessageUtil {

	@Autowired
	private MessageSource messageSource;

	/**
	 * メッセージIDに応じたメッセージを返します
	 * @param msgId メッセージID
	 * @param params 可変パラメータ {0},{1},....に対応
	 * @return
	 */
	public String getMessage(String msgId, String... params) {
		return messageSource.getMessage(msgId, params, Locale.getDefault());
	}

	/**
	 * メッセージIDに応じたメッセージオブジェクトを返す
	 * @param msgId
	 * @param params
	 * @return
	 */
	public AppMessage getAppMessage(String msgId, String... params) {
		AppMessage appMessage = new AppMessage();
		appMessage.setLevel(getLevel(msgId));
		appMessage.setMsgId(msgId);
		appMessage.setMessage(getMessage(msgId,params));
		return appMessage;
	}

	/**
	 * メッセージIDからメッセージレベルを識別します。
	 * @param msgId
	 * @return
	 */
	public String getLevel(String msgId){
		String[] work = msgId.split("\\.");
		if( "i".equals(work[1].substring(0, 1)) ) {
			return "INFO";
		} else if( "w".equals(work[1].substring(0, 1)) ) {
			return "WARN";
		} else {
			return "ERROR";
		}
	}
}
