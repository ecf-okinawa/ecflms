package com.e3factory.common.util;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Utility {

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
