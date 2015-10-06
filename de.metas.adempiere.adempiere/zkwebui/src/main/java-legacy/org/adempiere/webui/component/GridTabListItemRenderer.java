/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WEditorPopupMenu;
import org.adempiere.webui.editor.WebEditorFactory;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.panel.AbstractADWindowPanel;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.GridTabDataBinder;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.ListitemRendererExt;
import org.zkoss.zul.Paging;
import org.zkoss.zul.RendererCtrl;

/**
 * ListItem renderer for GridTab list box.
 * @author hengsin
 *
 */
public class GridTabListItemRenderer implements ListitemRenderer, ListitemRendererExt, RendererCtrl {

	private static final int MAX_TEXT_LENGTH = 60;
	private GridTab gridTab;
	private int windowNo;
	private GridTabDataBinder dataBinder;
	private Map<GridField, WEditor> editors = new HashMap<GridField, WEditor>();
	private Paging paging;

	/**
	 * 
	 * @param gridTab
	 * @param windowNo
	 */
	public GridTabListItemRenderer(GridTab gridTab, int windowNo) {
		this.gridTab = gridTab;
		this.windowNo = windowNo;
		this.dataBinder = new GridTabDataBinder(gridTab);
	}
	
	/**
	 * @param listitem
	 * @param data
	 * @see ListitemRenderer#render(Listitem, Object)
	 */
	public void render(Listitem listitem, Object data) throws Exception {
		//don't render if not visible
		for(Component c = listitem.getParent(); c != null; c = c.getParent()) {
			if (!c.isVisible())
				return;
		}
		Object[] values = (Object[])data;
		int columnCount = gridTab.getTableModel().getColumnCount();
		GridField[] gridField = gridTab.getFields();
		for (int i = 0; i < columnCount; i++) {
			if (!gridField[i].isDisplayed()) {
				continue;
			}
			if (editors.get(gridField[i]) == null)
				editors.put(gridField[i], WebEditorFactory.getEditor(gridField[i], true));
			
			int rowIndex = listitem.getIndex();			
			if (paging != null && paging.getPageSize() > 0) {
				rowIndex = (paging.getActivePage() * paging.getPageSize()) + rowIndex;
			}
			Listcell cell = null;
			if (rowIndex == gridTab.getCurrentRow() && gridField[i].isEditable(true)) {
				cell = getEditorCell(gridField[i], values[i], i);
				cell.setParent(listitem);
			} else {
				if (gridField[i].getDisplayType() == DisplayType.YesNo) {
					cell = new Listcell("", null);
					cell.setParent(listitem);
					cell.setStyle("text-align:center");
					createReadonlyCheckbox(values[i], cell);
				} else {
					String text = getDisplayText(values[i], i);
					String display = text;
					if (text != null && text.length() > MAX_TEXT_LENGTH)
						display = text.substring(0, MAX_TEXT_LENGTH - 3) + "...";
					cell = new Listcell(display, null);
					cell.setParent(listitem);
					if (text != null && text.length() > MAX_TEXT_LENGTH)
						cell.setTooltiptext(text);
					if (DisplayType.isNumeric(gridField[i].getDisplayType())) {
						cell.setStyle("text-align:right");
					} else if (gridField[i].getDisplayType() == DisplayType.Image) {
						cell.setStyle("text-align:center");
					}
				}
			}
			CellListener listener = new CellListener((Listbox) listitem.getParent());
			cell.addEventListener(Events.ON_DOUBLE_CLICK, listener);
		}
	}

	private void createReadonlyCheckbox(Object value, Listcell cell) {
		Checkbox checkBox = new Checkbox();
		if (value != null && "true".equalsIgnoreCase(value.toString()))
			checkBox.setChecked(true);
		else
			checkBox.setChecked(false);
		checkBox.setDisabled(true);
		checkBox.setParent(cell);
	}

	private Listcell getEditorCell(GridField gridField, Object object, int i) {
		Listcell cell = new Listcell("", null);
		WEditor editor = editors.get(gridField);
		if (editor != null)  {
			if (editor instanceof WButtonEditor)
            {
				Object window = SessionManager.getAppDesktop().findWindow(windowNo);
            	if (window != null && window instanceof ADWindow)
            	{
            		AbstractADWindowPanel windowPanel = ((ADWindow)window).getADWindowPanel();
            		((WButtonEditor)editor).addActionListener(windowPanel);
            	}            		
            }
			else
			{
				editor.addValueChangeListener(dataBinder);
			}
			cell.appendChild(editor.getComponent());
			if (editor.getComponent() instanceof Checkbox || editor.getComponent() instanceof Image) {
				cell.setStyle("text-align:center");
			}
			else if (DisplayType.isNumeric(gridField.getDisplayType())) {
				cell.setStyle("text-align:right");
			}
			gridField.addPropertyChangeListener(editor);
			editor.setValue(gridField.getValue());
			WEditorPopupMenu popupMenu = editor.getPopupMenu();
            
            if (popupMenu != null)
            {
            	popupMenu.addMenuListener((ContextMenuListener)editor);
            	cell.appendChild(popupMenu);
            }
            
            //streach component to fill grid cell
            editor.fillHorizontal();
		}
		
		return cell;
	}
	
	/**
	 * Detach all editor and optionally set the current value of the editor as cell label.
	 * @param updateCellLabel
	 */
	public void stopEditing(boolean updateCellLabel) {
		for (Entry<GridField, WEditor> entry : editors.entrySet()) {
			if (entry.getValue().getComponent().getParent() != null) {
				if (updateCellLabel) {
					Listcell cell = (Listcell) entry.getValue().getComponent().getParent();
					if (entry.getKey().getDisplayType() == DisplayType.YesNo) {
						cell.setLabel("");
						createReadonlyCheckbox(entry.getValue().getValue(), cell);
					} else {
						cell.setLabel(getDisplayText(entry.getValue().getValue(), getColumnIndex(entry.getKey())));
					}
				}
				entry.getValue().getComponent().detach();
				entry.getKey().removePropertyChangeListener(entry.getValue());
				entry.getValue().removeValuechangeListener(dataBinder);
			}
		}
	}

	private int getColumnIndex(GridField field) {
		GridField[] fields = gridTab.getFields();
		for(int i = 0; i < fields.length; i++) {
			if (fields[i] == field)
				return i;
		}
		return 0;
	}

	/**
	 * @see ListitemRendererExt#getControls()
	 */
	public int getControls() {
		return DETACH_ON_RENDER;
	}

	/**
	 * @param item
	 * @see ListitemRendererExt#newListcell(Listitem)
	 */
	public Listcell newListcell(Listitem item) {
		ListCell listCell = new ListCell();
		listCell.applyProperties();
		listCell.setParent(item);
		return listCell;
	}

	/**
	 * @param listbox
	 * @see ListitemRendererExt#newListitem(Listbox)
	 */
	public Listitem newListitem(Listbox listbox) {
		ListItem item = new ListItem();
		item.applyProperties();
		return item;
	}

	private Map<Integer, Map<Object, String>> lookupCache = null;
	
	private String getDisplayText(Object value, int columnIndex)
	{
		if (value == null)
			return "";
		
		GridField[] gridField = gridTab.getFields();
		if (gridField[columnIndex].isEncryptedField())
		{
			return "********";
		}
		else if (gridField[columnIndex].isLookup())
    	{
			if (value == null) return "";
			
			if (lookupCache != null)
			{
				Map<Object, String> cache = lookupCache.get(columnIndex);
				if (cache != null && cache.size() >0) 
				{
					String text = cache.get(value);
					if (text != null) 
					{
						return text;
					}				
				}
			}
			NamePair namepair = gridField[columnIndex].getLookup().get(value);
			if (namepair != null)
			{
				String text = namepair.getName();
				if (lookupCache != null)
				{
					Map<Object, String> cache = lookupCache.get(columnIndex);
					if (cache == null) 
					{
						cache = new HashMap<Object, String>();
						lookupCache.put(columnIndex, cache);
					}
					cache.put(value, text);
				}
				return text;
			}
			else
				return "";
    	}
    	else if (gridTab.getTableModel().getColumnClass(columnIndex).equals(Timestamp.class))
    	{
    		SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
    		return dateFormat.format((Timestamp)value);
    	}
    	else if (DisplayType.isNumeric(gridField[columnIndex].getDisplayType()))
    	{
    		return DisplayType.getNumberFormat(gridField[columnIndex].getDisplayType()).format(value);
    	}
    	else if (DisplayType.Button == gridField[columnIndex].getDisplayType())
    	{
    		return "";
    	}
    	else if (DisplayType.Image == gridField[columnIndex].getDisplayType())
    	{
    		if (value == null || (Integer)value <= 0)
    			return "";
    		else
    			return "...";
    	}
    	else
    		return value.toString();
	}
	
	class CellListener implements EventListener {

		private Listbox _listbox;
		
		public CellListener(Listbox listbox) {
			_listbox = listbox;
		}
		
		public void onEvent(Event event) throws Exception {
			if (Events.ON_DOUBLE_CLICK.equals(event.getName())) {
				Event evt = new Event(Events.ON_DOUBLE_CLICK, _listbox);
				Events.sendEvent(_listbox, evt);
			}
		}
		
	}

	/**
	 * Is renderer initialize
	 * @return boolean
	 */
	public boolean isInitialize() {
		return !editors.isEmpty();
	}

	/**
	 * 
	 * @return active editor list
	 */
	public List<WEditor> getEditors() {
		List<WEditor> editorList = new ArrayList<WEditor>();
		if (!editors.isEmpty())
			editorList.addAll(editors.values());
		
		return editorList;
	}
	
	/**
	 * @param paging
	 */
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	/**
	 * @see RendererCtrl#doCatch(Throwable)
	 */
	public void doCatch(Throwable ex) throws Throwable {
		lookupCache = null;
	}

	/**
	 * @see RendererCtrl#doFinally()
	 */
	public void doFinally() {
		lookupCache = null;
	}

	/**
	 * @see RendererCtrl#doTry()
	 */
	public void doTry() {
		lookupCache = new HashMap<Integer, Map<Object,String>>();
	}
}
