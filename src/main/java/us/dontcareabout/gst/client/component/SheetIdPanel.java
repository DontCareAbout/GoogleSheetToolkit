package us.dontcareabout.gst.client.component;

import java.util.ArrayList;

import com.google.common.base.Preconditions;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;

import us.dontcareabout.gst.client.GstEventCenter;
import us.dontcareabout.gst.client.data.SheetId;
import us.dontcareabout.gst.client.data.SheetIdDao;
import us.dontcareabout.gst.client.event.SheetIdSelectEvent;
import us.dontcareabout.gst.client.event.SheetIdSelectEvent.SheetIdSelectHandler;

/**
 * 包含一個新增按鈕、以及 {@link SheetId} 的對應編輯區（{@link #form}）。
 * <p>
 * {@link #form} 會撐滿「新增」按鈕以外的高度，
 * 所以建議此 component 高度在 200～210 之間即可。
 */
public class SheetIdPanel extends Composite {
	private static SheetIdPanelUiBinder uiBinder = GWT.create(SheetIdPanelUiBinder.class);

	//XXX 底下 buttonBar 莫名高度多了一節，目前無解.....
	@UiField FramedPanel form;
	@UiField TextField id;
	@UiField TextField name;
	@UiField CheckBox select;

	private SheetId nowInstance;

	public SheetIdPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		GstEventCenter.addSheetIdSelect(new SheetIdSelectHandler() {
			@Override
			public void onSheetIdSelect(SheetIdSelectEvent event) {
				refresh(event.data);
			}
		});
	}

	@UiHandler("newBtn")
	void selectNew(SelectEvent se) {
		refresh(new SheetId());
	}

	@UiHandler("submitBtn")
	void selectSubmit(SelectEvent se) {
		nowInstance.setId(parseSheetId(id.getCurrentValue()));
		nowInstance.setName(name.getCurrentValue());
		nowInstance.setSelect(select.getValue());

		ArrayList<SheetId> list = SheetIdDao.retrieve();

		if (list.contains(nowInstance)) {
			list.remove(nowInstance);
		}

		if (nowInstance.isSelect()) {
			for (SheetId sid : list) {
				sid.setSelect(false);
			}
		}

		list.add(nowInstance);
		storeAndDisable(list);
	}

	@UiHandler("deleteBtn")
	void selectDelete(SelectEvent se) {
		ArrayList<SheetId> list = SheetIdDao.retrieve();
		list.remove(nowInstance);
		storeAndDisable(list);
	}

	private void refresh(SheetId sid) {
		nowInstance = sid;
		form.setEnabled(true);
		id.setValue(sid.getId() == null ? "" : sheetUrl(sid.getId()));
		name.setValue(sid.getName());
		select.setValue(sid.isSelect());
	}

	private void storeAndDisable(ArrayList<SheetId> list) {
		SheetIdDao.store(list);
		GstEventCenter.refreshStore();
		form.setEnabled(false);
	}

	interface SheetIdPanelUiBinder extends UiBinder<Widget, SheetIdPanel> {}

	private static final String HEADER = "https://docs.google.com/spreadsheets/d/";

	private static String parseSheetId(String url) {
		//TODO 改善 exception message
		Preconditions.checkNotNull(url);
		Preconditions.checkArgument(url.startsWith(HEADER));
		int index = url.indexOf("/", HEADER.length());
		if (index == -1) { index = url.length(); }
		return url.substring(HEADER.length(), index);
	}

	private static String sheetUrl(String sheetId) {
		return HEADER + sheetId;
	}
}
