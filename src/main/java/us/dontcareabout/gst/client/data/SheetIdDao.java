package us.dontcareabout.gst.client.data;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;

/**
 * 提供各種讀取 sheet id 設定值的方式，細節參見 {@link #priorityValue()}。
 * <p>
 * 若要使用 local storage 相關 method，請先作 {@link #setting(String, String)}。
 *
 * @see StorageDao
 */
//這邊會全然使用 static 的方式操作
//主要原因是 UI component 設計上比較便利
//反正 JS 是 single thread、local storage 也只有一個
//真的有需要搞多個，自己用 StorageDao 想辦法 [捏鼻]
public class SheetIdDao {
	private static Mapper mapper = GWT.create(Mapper.class);
	private static StorageDao<SheetId> storageDao;
	private static String defaultValue;

	/**
	 * @param storeKey local storage 的 key 值
	 * @param defaultId {@link #priorityValue()} 優先等級最低的預設 sheet id 值
	 */
	public static void setting(String storeKey, String defaultId) {
		storageDao = new StorageDao<SheetId>(storeKey, mapper);
		defaultValue = defaultId;
	}

	public static String getStoreKey() {
		return storageDao.key;
	}

	/**
	 * @return URL query string 的 sheetId 值
	 */
	public static String urlValue() {
		return Location.getParameter("sheetId");
	}

	/**
	 * @return host page 中 JS 變數 sheetId 值
	 */
	public static native String jsValue() /*-{
		return $wnd.sheetId;
	}-*/;

	/**
	 * @return 優先等級最低的預設 sheet id 值
	 * @see #setting(String, String)
	 */
	public static String defaultValue() {
		return defaultValue;
	}

	/**
	 * 依照內定的優先順序所決定的 sheet id 值。其順序如下：
	 * <ol>
	 * 	<li>{@link #urlValue()}</li>
	 * 	<li>{@link #jsValue()}</li>
	 * 	<li>
	 * 		儲存在 local storage 的 {@link SheetId}，第一個 {@link SheetId#isSelect()} 為 ture 的 instance，
	 * 		其 {@link SheetId#getId()} 值。
	 * 	</li>
	 * 	<li>{@link #defaultValue()}</li>
	 * </ol>
	 */
	public static String priorityValue() {
		if (urlValue() != null) { return urlValue(); }
		if (jsValue() != null) { return jsValue(); }

		for (SheetId id : retrieve()) {
			if (id.isSelect()) { return id.getId(); }
		}

		return defaultValue;
	}

	/**
	 * @return 儲存在 local storage 的 {@link SheetId} list
	 */
	public static ArrayList<SheetId> retrieve() {
		checkDao();
		return storageDao.retrieve();
	}

	/**
	 * 將指定的 {@link SheetId} list 儲存至 local storage
	 */
	public static void store(ArrayList<SheetId> list) {
		checkDao();
		storageDao.store(list);
	}

	/**
	 * 清空所有儲存在 local storage 的 {@link SheetId} 資料
	 */
	public static void clear() {
		checkDao();
		storageDao.clear();
	}

	private static void checkDao() {
		Preconditions.checkNotNull(storageDao, "setting() did not call");
	}

	interface Mapper extends ObjectMapper<ArrayList<SheetId>> {}
}
