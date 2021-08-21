package us.dontcareabout.gst.client.data;

import com.google.gwt.user.client.Window.Location;

public class ApiKey {
	/**
	 * @return URL query string 的 apiKey 值
	 */
	public static String urlValue() {
		return Location.getParameter("apiKey");
	}

	/**
	 * @return host page 中 JS 變數 apiKey 值
	 */
	public static native String jsValue() /*-{
		return $wnd.apiKey;
	}-*/;


	/**
	 * @return 若 {@link #urlValue()} 有值則優先回傳，否則回傳 {@link #jsValue()}
	 */
	public static String priorityValue() {
		if (urlValue() != null) { return urlValue(); }

		return jsValue();
	}
}
