package us.dontcareabout.gst.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.gst.client.data.SheetId;
import us.dontcareabout.gst.client.event.RefreshStoreEvent.RefreshStoreHandler;

/**
 * local storage 儲存的 {@link SheetId} 資料有異動時產生的 event。
 */
public class RefreshStoreEvent extends GwtEvent<RefreshStoreHandler> {
	public static final Type<RefreshStoreHandler> TYPE = new Type<RefreshStoreHandler>();

	@Override
	public Type<RefreshStoreHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshStoreHandler handler) {
		handler.onRefreshSheetIdStore(this);
	}

	public interface RefreshStoreHandler extends EventHandler{
		public void onRefreshSheetIdStore(RefreshStoreEvent event);
	}
}
