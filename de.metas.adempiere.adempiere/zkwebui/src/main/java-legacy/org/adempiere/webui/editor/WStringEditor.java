/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.editor;

import java.util.List;

import org.adempiere.webui.ValuePreference;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.adempiere.webui.window.WTextEditorDialog;
import org.compiere.model.GridField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 11, 2007
 * @version $Revision: 0.10 $
 */
public class WStringEditor extends WEditor implements ContextMenuListener
{
    private static final String EDITOR_EVENT = "EDITOR";

	private static final String[] LISTENER_EVENTS = {Events.ON_CHANGE, Events.ON_OK};

    private String oldValue;

    private WEditorPopupMenu	popupMenu;

    private boolean tableEditor = false;

    /**
     * to ease porting of swing form
     */
    public WStringEditor()
    {
    	this("String", false, false, true, 30, 30, "", null);
    }

    public WStringEditor(GridField gridField) {
    	this(gridField, false);
    }

    public WStringEditor(GridField gridField, boolean tableEditor)
    {
        super(gridField.isAutocomplete() ? new Combobox() : new Textbox(), gridField);
        this.tableEditor = tableEditor;
        init(gridField.getObscureType());
    }

    /**
     * to ease porting of swing form
     * @param columnName
     * @param mandatory
     * @param isReadOnly
     * @param isUpdateable
     * @param displayLength
     * @param fieldLength
     * @param vFormat
     * @param obscureType
     */
    public WStringEditor(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
    		int displayLength, int fieldLength, String vFormat, String obscureType)
    {
    	super(new Textbox(), columnName, null, null, mandatory, isReadOnly,isUpdateable);

    	init(obscureType);
    }

    @Override
    public org.zkoss.zul.Textbox getComponent() {
    	return (org.zkoss.zul.Textbox) component;
    }

    @Override
	public boolean isReadWrite() {
		return !getComponent().isReadonly();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setReadonly(!readWrite);
	}

	private void init(String obscureType)
    {
		if (gridField != null)
		{
	        getComponent().setMaxlength(gridField.getFieldLength());
	        int displayLength = gridField.getDisplayLength();
	        if (displayLength <= 0 || displayLength > MAX_DISPLAY_LENGTH)
	        {
	            displayLength = MAX_DISPLAY_LENGTH;
	        }
	        getComponent().setCols(displayLength);

	        if (gridField.getDisplayType() == DisplayType.Text)
	        {
	            getComponent().setMultiline(true);
	            getComponent().setRows(3);
	        }
	        else if (gridField.getDisplayType() == DisplayType.TextLong)
	        {
	            getComponent().setMultiline(true);
	            getComponent().setRows(5);
	        }
	        else if (gridField.getDisplayType() == DisplayType.Memo)
	        {
	            getComponent().setMultiline(true);
	            getComponent().setRows(8);
	        }

	        if (getComponent() instanceof Textbox)
	        	((Textbox)getComponent()).setObscureType(obscureType);

	        popupMenu = new WEditorPopupMenu(false, false, Env.getUserRolePermissions().isShowPreference());
	        Menuitem editor = new Menuitem(Msg.getMsg(Env.getCtx(), "Editor"), "images/Editor16.png");
	        editor.setAttribute("EVENT", EDITOR_EVENT);
	        editor.addEventListener(Events.ON_CLICK, popupMenu);
	        popupMenu.appendChild(editor);
	        
	        if (gridField != null && gridField.getGridTab() != null)
			{
				WFieldRecordInfo.addMenu(popupMenu);
			}

	        getComponent().setContext(popupMenu.getId());

	        if (gridField.isAutocomplete()) {
	        	Combobox combo = (Combobox)getComponent();
	        	combo.setAutodrop(true);
	        	combo.setAutocomplete(true);
	        	combo.setButtonVisible(false);
	        	List<String> items = gridField.getEntries();
	        	for(String s : items) {
	        		combo.appendItem(s);
	        	}
	        }
		}
    }

    public void onEvent(Event event)
    {
    	if (Events.ON_CHANGE.equals(event.getName()) || Events.ON_OK.equals(event.getName()))
    	{
	        String newValue = getComponent().getValue();
	        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
	    	    return;
	    	}
	        if (oldValue == null && newValue == null) {
	        	return;
	        }
	        ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
	        super.fireValueChange(changeEvent);
	        oldValue = newValue;
    	}
    }

    @Override
    public String getDisplay()
    {
        return getComponent().getValue();
    }

    @Override
    public Object getValue()
    {
        return getComponent().getValue();
    }

    @Override
    public void setValue(Object value)
    {
        if (value != null)
        {
            getComponent().setValue(value.toString());
        }
        else
        {
            getComponent().setValue("");
        }
        oldValue = getComponent().getValue();
    }

    protected void setTypePassword(boolean password)
    {
        if (password)
        {
            getComponent().setType("password");
        }
        else
        {
            getComponent().setType("text");
        }
    }

    @Override
    public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

    public WEditorPopupMenu getPopupMenu()
	{
	   	return popupMenu;
	}

    public void onMenu(ContextMenuEvent evt)
	{
		if (WEditorPopupMenu.PREFERENCE_EVENT.equals(evt.getContextEvent()))
		{
			if (Env.getUserRolePermissions().isShowPreference())
				ValuePreference.start (this.getGridField(), getValue(), getDisplay());
			return;
		}
		else if (EDITOR_EVENT.equals(evt.getContextEvent()))
		{
			WTextEditorDialog dialog = new WTextEditorDialog(this.getColumnName(), getDisplay(),
					isReadWrite(), gridField.getFieldLength());
			dialog.setAttribute(Window.MODE_KEY, Window.MODE_MODAL);
			dialog.setHtml(isHtml());
			SessionManager.getAppDesktop().showWindow(dialog);
			if (!dialog.isCancelled()) {
				getComponent().setText(dialog.getText());
				String newText = getComponent().getValue();
		        ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newText);
		        super.fireValueChange(changeEvent);
		        oldValue = newText;
			}
		}
		else if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
	}

	@Override
	public void dynamicDisplay() {
		//referesh auto complete list
		if (gridField.isAutocomplete()) {
        	Combobox combo = (Combobox)getComponent();
        	List<String> items = gridField.getEntries();
        	if (items.size() != combo.getItemCount())
        	{
        		combo.removeAllItems();
        		for(String s : items) {
            		combo.appendItem(s);
            	}
        	}
        }
	}

	public boolean isHtml()
	{
		final boolean isAutocomplete = gridField.isAutocomplete();
		final int displayType = gridField.getDisplayType();
		
		if (isAutocomplete)
		{
			return false;
		}
		
		return displayType == DisplayType.Text
				|| displayType == DisplayType.TextLong
				|| displayType == DisplayType.Memo;
	}
}
