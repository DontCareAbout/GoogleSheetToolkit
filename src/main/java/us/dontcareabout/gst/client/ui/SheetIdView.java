package us.dontcareabout.gst.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;

public class SheetIdView extends Composite {
	private static SheetIdViewUiBinder uiBinder = GWT.create(SheetIdViewUiBinder.class);

	public SheetIdView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	interface SheetIdViewUiBinder extends UiBinder<Widget, SheetIdView> {}
}
