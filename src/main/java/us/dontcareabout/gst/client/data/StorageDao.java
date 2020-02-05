package us.dontcareabout.gst.client.data;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.storage.client.Storage;

/**
 * 以 {@link ArrayList} 為單位、存取 local storage 的 utility。
 */
//XXX 就 GST 的角度，其實可以直接指定 T，程式可能還會看起來簡單一點
//不過這裡就當作實驗，也許有一天會提升到 GF 去（如果 gwt-jackson 視為 GF 一部分的話）
public class StorageDao<T> {
	private static Storage storage = Storage.getLocalStorageIfSupported();

	public final String key;

	private final ObjectMapper<ArrayList<T>> mapper;

	public StorageDao(String key, ObjectMapper<ArrayList<T>> mapper) {
		this.key = key;
		this.mapper = mapper;
	}

	/**
	 * @return 儲存在 local storage 的 list
	 */
	public ArrayList<T> retrieve() {
		String json = storage.getItem(key);
		return json == null ? new ArrayList<T>() : mapper.read(json);
	}

	/**
	 * 將指定 list 儲存至 local storage
	 */
	public void store(ArrayList<T> list) {
		storage.setItem(key, mapper.write(list));
	}

	/**
	 * 清空所有儲存在 local storage 的資料
	 */
	public void clear() {
		storage.removeItem(key);
	}
}
