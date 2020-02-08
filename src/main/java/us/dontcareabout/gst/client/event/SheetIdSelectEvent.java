package us.dontcareabout.gst.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.gst.client.data.SheetId;
import us.dontcareabout.gst.client.event.SheetIdSelectEvent.SheetIdSelectHandler;

/**
 * UI 元件選擇某個 {@link SheetId} 時產生的 event。
 */
public class SheetIdSelectEvent extends GwtEvent<SheetIdSelectHandler> {
	public static final Type<SheetIdSelectHandler> TYPE = new Type<SheetIdSelectHandler>();
	public final SheetId data;

	public SheetIdSelectEvent(SheetId sid) {
		this.data = sid;
	}

	@Override
	public Type<SheetIdSelectHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SheetIdSelectHandler handler) {
		handler.onSheetIdSelect(this);
	}

	public interface SheetIdSelectHandler extends EventHandler{
		public void onSheetIdSelect(SheetIdSelectEvent event);
	}
}
