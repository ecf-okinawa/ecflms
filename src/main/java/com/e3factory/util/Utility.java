package com.e3factory.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.context.MessageSource;

public class Utility {
	/**
	 * メッセージを取得する。
	 * @param source MessageSourceインスタンス
	 * @param msgId messages.propertiesに定義済みのキー
	 * @param params プレースホルダー埋め込み用
	 * @return
	 */
	public static String getMessage(MessageSource source, String msgId, String... params ) {
		return source.getMessage(msgId, params, Locale.getDefault());
	}

	/**
	 * 指定文字列の日付フォーマット(YYYY/MM/DD)チェックを行います。
	 * @param value
	 * @return
	 */
	public static boolean checkDateFormat(String value) {
		//YYYY/MM/DD 形式にマッチしなければfalse
		if( !value.matches("^[0-9]{4}/[0-9]{2}/[0-9]{2}$") ) {
			return false;
		}
		String[] values = value.split("/");
		//実日付チェック
		try {
			int year = Integer.parseInt(values[0]);
			int month = Integer.parseInt(values[1]);
			int dayofmonth = Integer.parseInt(values[2]);
			//日付変換処理。例外発生有無をチェックするだけ
			LocalDate.of(year,month,dayofmonth);
		} catch(DateTimeException e) {
			//日付にできなければfalse
			return false;
		}
		return true;
	}
}
