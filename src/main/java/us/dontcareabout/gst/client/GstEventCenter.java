package us.dontcareabout.gst.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gst.client.data.SheetId;
import us.dontcareabout.gst.client.event.RefreshStoreEvent;
import us.dontcareabout.gst.client.event.RefreshStoreEvent.RefreshStoreHandler;
import us.dontcareabout.gst.client.event.ReloadSheetEvent;
import us.dontcareabout.gst.client.event.ReloadSheetEvent.ReloadSheetHandler;
import us.dontcareabout.gst.client.event.SheetIdSelectEvent;
import us.dontcareabout.gst.client.event.SheetIdSelectEvent.SheetIdSelectHandler;

public class GstEventCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	public static void sheetIdSelect(SheetId sid) {
		eventBus.fireEvent(new SheetIdSelectEvent(sid));
	}

	public static HandlerRegistration addSheetIdSelect(SheetIdSelectHandler handler) {
		return eventBus.addHandler(SheetIdSelectEvent.TYPE, handler);
	}

	public static void refreshStore() {
		eventBus.fireEvent(new RefreshStoreEvent());
	}

	public static HandlerRegistration addRefreshStore(RefreshStoreHandler handler) {
		return eventBus.addHandler(RefreshStoreEvent.TYPE, handler);
	}

	public static void reloadSheet(String sheetId) {
		eventBus.fireEvent(new ReloadSheetEvent(sheetId));
	}

	public static HandlerRegistration addReloadSheet(ReloadSheetHandler handler) {
		return eventBus.addHandler(ReloadSheetEvent.TYPE, handler);
	}
}
