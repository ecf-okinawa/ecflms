package com.e3factory.common.util;

import lombok.Data;

@Data
public class AppMessage {
	/** エラーレベル */
	private String level;
	/** メッセージID */
	private String msgId;
	/** メッセージ */
	private String message;
}
