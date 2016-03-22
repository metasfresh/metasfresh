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
package org.adempiere.webui.editor;

import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.webui.component.DatetimeBox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.compiere.model.GridField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author Low Heng Sin
 */
public class WDatetimeEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CHANGE, Events.ON_OK};
    @SuppressWarnings("unused")
	private static final Logger logger;

    static
    {
        logger = LogManager.getLogger(WDatetimeEditor.class);
    }

    private Timestamp oldValue = new Timestamp(0);
	private WEditorPopupMenu popupMenu;

    /**
     *
     * @param gridField
     */
    public WDatetimeEditor(GridField gridField)
    {
        super(new DatetimeBox(), gridField);
        init();
    }


	/**
	 * Constructor for use if a grid field is unavailable
	 *
	 * @param label
	 *            column name (not displayed)
	 * @param description
	 *            description of component
	 * @param mandatory
	 *            whether a selection must be made
	 * @param readonly
	 *            whether or not the editor is read only
	 * @param updateable
	 *            whether the editor contents can be changed
	 */
	public WDatetimeEditor (String label, String description, boolean mandatory, boolean readonly, boolean updateable)
	{
		super(new DatetimeBox(), label, description, mandatory, readonly, updateable);
		setColumnName("Datetime");
		init();
	}

	public WDatetimeEditor()
	{
		this("Datetime", "Datetime", false, false, true);
	}   // VDate

	/**
	 *
	 * @param columnName
	 * @param mandatory
	 * @param readonly
	 * @param updateable
	 * @param title
	 */
	public WDatetimeEditor(String columnName, boolean mandatory, boolean readonly, boolean updateable,
			String title)
	{
		super(new DatetimeBox(), columnName, title, null, mandatory, readonly, updateable);
		init();
	}

	private void init()
	{
		getComponent().setDateFormat(DisplayType.getDateFormat());
		popupMenu = new WEditorPopupMenu(false, false, Env.getUserRolePermissions().isShowPreference());
		popupMenu.addMenuListener(this);
		if (gridField != null && gridField.getGridTab() != null)
		{
			WFieldRecordInfo.addMenu(popupMenu);
		}
		getComponent().setContext(popupMenu.getId());
	}

	public void onEvent(Event event)
    {
		if (Events.ON_CHANGE.equalsIgnoreCase(event.getName()) || Events.ON_OK.equalsIgnoreCase(event.getName()))
		{
	        Date date = getComponent().getValue();
	        Timestamp newValue = null;

	        if (date != null)
	        {
	            newValue = new Timestamp(date.getTime());
	        }
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
    	// Elaine 2008/07/29
    	return getComponent().getText();
    	//
    }

    @Override
    public Object getValue()
    {
    	// Elaine 2008/07/25
    	if(getComponent().getValue() == null) return null;
    	return new Timestamp(getComponent().getValue().getTime());
    	//
    }

    @Override
    public void setValue(Object value)
    {
    	if (value == null || value.toString().trim().length() == 0)
    	{
    		oldValue = null;
    		getComponent().setValue(null);
    	}
    	else if (value instanceof Timestamp)
        {
            getComponent().setValue((Timestamp)value);
            oldValue = (Timestamp)value;
        }
    	else
    	{
    		try
    		{
    			getComponent().setText(value.toString());
    		} catch (Exception e) {}
    		if (getComponent().getValue() != null)
    			oldValue = new Timestamp(getComponent().getValue().getTime());
    		else
    			oldValue = null;
    	}
    }

	@Override
	public DatetimeBox getComponent() {
		return (DatetimeBox) component;
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}


	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }


	@Override
	public void onMenu(ContextMenuEvent evt) {
		if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
	}
}
