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

import java.beans.PropertyChangeEvent;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.webui.ValuePreference;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Comboitem;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 12, 2007
 * @version $Revision: 0.10 $
 */
public class WTableDirEditor extends WEditor implements ListDataListener, 
ContextMenuListener, IZoomableEditor
{
    public final static String[] LISTENER_EVENTS = {Events.ON_SELECT};
    
    @SuppressWarnings("unused")
	private static final CLogger logger;
    
    static
    {
        logger = CLogger.getCLogger(WTableDirEditor.class);
    }
    
    private final Lookup lookup;
    private Object oldValue;
    private WEditorPopupMenu popupMenu;
       
    public WTableDirEditor(GridField gridField)
    {
        super(new Combobox(), gridField);
        lookup = gridField.getLookup();
        init();
    }
	
	/** 
	 * Constructor for use if a grid field is unavailable
	 * 
	 * @param lookup		Store of selectable data
	 * @param label			column name (not displayed)
	 * @param description	description of component
	 * @param mandatory		whether a selection must be made
	 * @param readonly		whether or not the editor is read only
	 * @param updateable	whether the editor contents can be changed
	 */   
    public WTableDirEditor(Lookup lookup, String label, String description, boolean mandatory, boolean readonly, boolean updateable)
	{
		super(new Combobox(), label, description, mandatory, readonly, updateable);
		
		if (lookup == null)
		{
			throw new IllegalArgumentException("Lookup cannot be null");
		}
		
		this.lookup = lookup;
		super.setColumnName(lookup.getColumnName());
		init();
	}
    
    /**
     * For ease of porting swing form
     * @param columnName
     * @param mandatory
     * @param isReadOnly
     * @param isUpdateable
     * @param lookup
     */
    public WTableDirEditor(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
    		Lookup lookup)
    {
    	super(new Combobox(), columnName, null, null, mandatory, isReadOnly, isUpdateable);
    	if (lookup == null)
		{
			throw new IllegalArgumentException("Lookup cannot be null");
		}
    	this.lookup = lookup;
    	init();
    }
    
    private void init()
    {
        getComponent().setWidth("200px"); 
        getComponent().setAutocomplete(true);
        getComponent().setAutodrop(true);
        getComponent().addEventListener(Events.ON_BLUR, this);

        boolean zoom= false;
        if (lookup != null)
        {
            lookup.addListDataListener(this);
            //always need the empty item for zk to work correctly
            lookup.setMandatory(false);
            
            if ((lookup.getDisplayType() == DisplayType.List && Env.getContextAsInt(Env.getCtx(), "#AD_Role_ID") == 0)
            		|| lookup.getDisplayType() != DisplayType.List) 
            {
    			zoom= true;
            }
            
            //no need to refresh readonly lookup
            if (isReadWrite())
            	lookup.refresh();
            refreshList();
        }
        
        if (gridField != null) 
        {
        	popupMenu = new WEditorPopupMenu(zoom, true, Env.getUserRolePermissions().isShowPreference());
        	if (gridField != null &&  gridField.getGridTab() != null)
    		{
    			WFieldRecordInfo.addMenu(popupMenu);
    		}
        	getComponent().setContext(popupMenu.getId());
        }
    }

    @Override
    public String getDisplay()
    {

        String display = null;
        Comboitem selItem = getComponent().getSelectedItem();
        if (selItem != null)
        {
        	display = selItem.getLabel();
        }
        return display;
    }

    @Override
    public Object getValue()
    {
        Object retVal = null;
        Comboitem selItem = getComponent().getSelectedItem();
        if (selItem != null)
        {
            retVal = selItem.getValue();
            if ((retVal instanceof Integer) && (Integer)retVal == -1)
            	retVal = null;
        }
        return retVal;
    }

    public void setValue(Object value)
    {
    	if (value != null && (value instanceof Integer || value instanceof String))
        {

            getComponent().setValue(value);            
            if (!getComponent().isSelected(value))
            {
            	if (isReadWrite() && lookup != null)
            		lookup.refresh();
            	Object curValue = oldValue;
                oldValue = value;
                refreshList();
                
                //still not in list, reset to zero
                if (!getComponent().isSelected(value))
                {
                	if (value instanceof Integer && (Integer)value == 0)
                	{
                		getComponent().setValue(null);
                		if (curValue == null)
                			curValue = value;
                		ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), curValue, null);
            	        super.fireValueChange(changeEvent);
                		oldValue = null;
                	}
                }
            }
            else
            {
            	oldValue = value;
            }
        }
        else
        {
            getComponent().setValue(null);
            oldValue = value;
        }                                
    }
    
    @Override
	public Combobox getComponent() {
		return (Combobox) component;
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	private void refreshList()
    {
    	if (getComponent().getItemCount() > 0)
    		getComponent().removeAllItems();

    	if (isReadWrite())
    	{
	        if (lookup != null)
	        {
	            int size = lookup.getSize();
	            
	            boolean found = false;
	            for (int i = 0; i < size; i++)
	            {
	                Object obj = lookup.getElementAt(i);
	                if (obj instanceof KeyNamePair)
	                {
	                    KeyNamePair lookupKNPair = (KeyNamePair) obj;
	                    getComponent().appendItem(lookupKNPair.getName(), lookupKNPair.getKey());
	                    if (!found && oldValue != null && oldValue instanceof Integer &&
	                    	lookupKNPair.getKey() == (Integer)oldValue)
	                    {
	                    	found = true;
	                	}
	                }
	                else if (obj instanceof ValueNamePair)
	                {
	                    ValueNamePair lookupKNPair = (ValueNamePair) obj;
	                    getComponent().appendItem(lookupKNPair.getName(), lookupKNPair.getValue());
	                    if (!found && oldValue != null && lookupKNPair.getValue().equals(oldValue.toString()))
		                {
	                    	found = true;
	                	}
	            	}
	        	}	        	        
	            if (!found && oldValue != null)
	            {
	            	NamePair pair = lookup.getDirect(IValidationContext.NULL, oldValue, false, false);
	            	if (pair != null) {
		    			if (pair instanceof KeyNamePair) {
		    				int key = ((KeyNamePair)pair).getKey();
		    				getComponent().appendItem(pair.getName(), key);
		    			} else if (pair instanceof ValueNamePair) {
		    				ValueNamePair valueNamePair = (ValueNamePair) pair;
		                    getComponent().appendItem(valueNamePair.getName(), valueNamePair.getValue());
		    			}
	            	}
	            }
	        }
    	}
    	else
    	{
    		if (lookup != null)
	        {
    			NamePair pair = lookup.getDirect(IValidationContext.NULL, oldValue, false, false);
    			if (pair != null) {
    				if (pair instanceof KeyNamePair) {
    					int key = ((KeyNamePair)pair).getKey();
    					getComponent().appendItem(pair.getName(), key);
    				} else if (pair instanceof ValueNamePair) {
    					ValueNamePair valueNamePair = (ValueNamePair) pair;
                    	getComponent().appendItem(valueNamePair.getName(), valueNamePair.getValue());
    				}
	        	}
    		}
    	}
    	getComponent().setValue(oldValue);
    }
    
    public void onEvent(Event event)
    {
    	if (Events.ON_SELECT.equalsIgnoreCase(event.getName()))
    	{
	        Object newValue = getValue();
	        if (isValueChange(newValue)) {
		        ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
		        super.fireValueChange(changeEvent);
		        oldValue = newValue;
	        }
    	}
    	else if (Events.ON_BLUR.equalsIgnoreCase(event.getName()))
    	{
    		Comboitem item = getComponent().getSelectedItem();
    		if (item == null) 
    		{
    			setValue(oldValue);
    		}
    		else 
    		{
    			//on select not fire for empty label item
    			if (item.getLabel().equals(""))
    			{
    				Object newValue = getValue();
    				if (isValueChange(newValue)) {
	    				ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
	    		        super.fireValueChange(changeEvent);
	    		        oldValue = newValue;
    				}
    			}
    		}
    	}
    }

    private boolean isValueChange(Object newValue) {
		return (oldValue == null && newValue != null) || (oldValue != null && newValue == null) 
			|| ((oldValue != null && newValue != null) && !oldValue.equals(newValue));
	}
    
    public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

    public void contentsChanged(ListDataEvent e)
    {
        refreshList();
    }

    public void intervalAdded(ListDataEvent e)
    {}

    public void intervalRemoved(ListDataEvent e)
    {}
    
    public WEditorPopupMenu getPopupMenu()
    {
    	return popupMenu;
    }
    
    public void actionRefresh()
    {    	
		if (lookup != null)
        {
			Object curValue = getValue();
			
			if (isReadWrite())
				lookup.refresh();
            refreshList();
            if (curValue != null)
            {
            	setValue(curValue);
            }
        }
    }
    
    /* (non-Javadoc)
	 * @see org.adempiere.webui.editor.IZoomableEditor#actionZoom()
	 */
    public void actionZoom()
	{
    	AEnv.actionZoom(lookup, getValue());
	}	
    
	public void onMenu(ContextMenuEvent evt) 
	{
		if (WEditorPopupMenu.REQUERY_EVENT.equals(evt.getContextEvent()))
		{
			actionRefresh();
		}
		else if (WEditorPopupMenu.ZOOM_EVENT.equals(evt.getContextEvent()))
		{
			actionZoom();
		}
		else if (WEditorPopupMenu.PREFERENCE_EVENT.equals(evt.getContextEvent()))
		{
			if (Env.getUserRolePermissions().isShowPreference())
				ValuePreference.start (this.getGridField(), getValue(), getDisplay());
			return;
		}
		else if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
	}
	
	public  void propertyChange(PropertyChangeEvent evt)
	{
		if ("FieldValue".equals(evt.getPropertyName()))
		{
			setValue(evt.getNewValue());
		}
	}
	
	@Override
	public void dynamicDisplay()
    {    	
		if ((lookup != null) && (!lookup.isValidated() || !lookup.isLoaded()))
			this.actionRefresh();
    }
	
	//metas: cg: start: task US682
	public Lookup getLookup()
	{
		return lookup;
	}
	//metas: cg: end: task US682
}
