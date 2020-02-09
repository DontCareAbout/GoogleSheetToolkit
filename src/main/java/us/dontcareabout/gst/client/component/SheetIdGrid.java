package us.dontcareabout.gst.client.component;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

import us.dontcareabout.gst.client.GstEventCenter;
import us.dontcareabout.gst.client.data.SheetId;
import us.dontcareabout.gst.client.data.SheetIdDao;
import us.dontcareabout.gst.client.event.RefreshStoreEvent;
import us.dontcareabout.gst.client.event.RefreshStoreEvent.RefreshStoreHandler;
import us.dontcareabout.gxt.client.component.Grid2;
import us.dontcareabout.gxt.client.model.GetValueProvider;

public class SheetIdGrid extends Grid2<SheetId> {
	private static final Properties properties = GWT.create(Properties.class);

	public SheetIdGrid() {
		init();
		view.setForceFit(true);
		getSelectionModel().addSelectionHandler(new SelectionHandler<SheetId>() {
			@Override
			public void onSelection(SelectionEvent<SheetId> event) {
				GstEventCenter.sheetIdSelect(event.getSelectedItem());
			}
		});
		GstEventCenter.addRefreshStore(new RefreshStoreHandler() {
			@Override
			public void onRefreshSheetIdStore(RefreshStoreEvent event) {
				refresh();
			}
		});
		refresh();
	}

	@Override
	protected ListStore<SheetId> genListStore() {
		ListStore<SheetId> result = new ListStore<>(properties.key());
		result.addSortInfo(new StoreSortInfo<>(properties.select(), SortDir.DESC));
		return result;
	}

	@Override
	protected ColumnModel<SheetId> genColumnModel() {
		ColumnConfig<SheetId, Boolean> defaultSelect = new ColumnConfig<>(properties.select(), 100, "預設開啟");
		defaultSelect.setFixed(true);
		defaultSelect.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		defaultSelect.setCell(new AbstractCell<Boolean>() {
			@Override
			public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
				if (!value) { return; }

				sb.appendHtmlConstant(
					"<div style='margin-left:auto;margin-right:auto;width:16px;height:24px;font-size:14px;color:red;'>V</div>"
				);
			}
		});

		TextButtonCell reloadBtn = new TextButtonCell() {
			@Override
			public boolean handlesSelection() {
				return true;
			}
		};
		reloadBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				GstEventCenter.reloadSheet(store.get(event.getContext().getIndex()).getId());
			}
		});
		ColumnConfig<SheetId, String> reload = new ColumnConfig<>(new GetValueProvider<SheetId, String>() {
			@Override
			public String getValue(SheetId object) {
				return "載入";
			}
		}, 50, "");
		reload.setFixed(true);
		reload.setCell(reloadBtn);

		ArrayList<ColumnConfig<SheetId, ?>> list = new ArrayList<>();
		list.add(defaultSelect);
		list.add(new ColumnConfig<>(properties.name(), 40, "名稱"));
		list.add(new ColumnConfig<>(properties.id(), 100, "Sheet ID"));
		list.add(reload);
		return new ColumnModel<>(list);
	}

	private void refresh() {
		store.replaceAll(SheetIdDao.retrieve());
	}

	interface Properties extends PropertyAccess<SheetId> {
		@Path("id") ModelKeyProvider<SheetId> key();
		ValueProvider<SheetId, String> id();
		ValueProvider<SheetId, Boolean> select();
		ValueProvider<SheetId, String> name();
	}
}
