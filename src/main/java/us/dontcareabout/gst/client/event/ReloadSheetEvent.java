package us.dontcareabout.gst.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.gst.client.event.ReloadSheetEvent.ReloadSheetHandler;

/**
 * UI 元件要求重新載入指定 sheet id 時產生的 event。
 */
public class ReloadSheetEvent extends GwtEvent<ReloadSheetHandler> {
	public static final Type<ReloadSheetHandler> TYPE = new Type<ReloadSheetHandler>();

	public final String sheetId;

	public ReloadSheetEvent(String sheetId) {
		this.sheetId = sheetId;
	}

	@Override
	public Type<ReloadSheetHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadSheetHandler handler) {
		handler.onReloadSheet(this);
	}

	public interface ReloadSheetHandler extends EventHandler{
		public void onReloadSheet(ReloadSheetEvent event);
	}
}
