package us.dontcareabout.gst.client;

import us.dontcareabout.gst.client.data.SheetIdDao;
import us.dontcareabout.gwt.client.GFEP;
import us.dontcareabout.gwt.client.iCanUse.Feature;

/**
 * 加強版的 {@link GFEP}，
 * 會強制 child class 提供 {@link SheetIdDao#setting(String, String)} 所需參數，
 * 以及預設呼叫 {@link #needFeature(Feature...)} 確保 client 有支援 {@link Feature#Storage}。
 */
public abstract class GSTEP extends GFEP {
	public GSTEP(String storageKey, String defaultId) {
		SheetIdDao.setting(storageKey, defaultId);
		needFeature(Feature.Storage);
	}
}
