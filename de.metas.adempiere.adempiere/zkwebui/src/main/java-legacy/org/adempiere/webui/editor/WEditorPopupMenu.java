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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 25, 2007
 * @version $Revision: 0.10 $
 */
public class WEditorPopupMenu extends Menupopup implements EventListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8172397145177408454L;
	public static final String EVENT_ATTRIBUTE = "EVENT";
    public static final String ZOOM_EVENT = "ZOOM";
    public static final String REQUERY_EVENT = "REQUERY";
    public static final String PREFERENCE_EVENT = "VALUE_PREFERENCE";
    public static final String NEW_EVENT = "NEW_RECORD";
    public static final String UPDATE_EVENT = "UPDATE_RECORD"; // Elaine 2009/02/16 - update record
    public static final String CHANGE_LOG_EVENT = "CHANGE_LOG";
    
    private boolean newEnabled = true;
    private boolean updateEnabled = true; // Elaine 2009/02/16 - update record
    private boolean zoomEnabled  = true;
    private boolean requeryEnabled = true;
    private boolean preferencesEnabled = true;
    
    /** Map that holds which popup items we dynamically disabled */
    private final Map<String, Boolean> mapDisabledItems = new HashMap<String, Boolean>();
    /** Shall we only disable items or we shall also hide them? */
	private boolean hideDisabledItems = false;
	private boolean hasVisibleItems = false;
    public static final String ACTION_ZOOM = "ZOOM";
    public static final String ACTION_REQUERY = "REQUERY";
    public static final String ACTION_PREFERENCE = "VALUE_PREFERENCE";
    public static final String ACTION_NEW = "NEW_RECORD";
    public static final String ACTION_UPDATE = "UPDATE_RECORD";
    public static final String ACTION_CHANGE_LOG = "CHANGE_LOG";
    public static final String[] ACTIONS = {
    	ACTION_ZOOM, ACTION_REQUERY, ACTION_PREFERENCE, ACTION_NEW, ACTION_UPDATE, ACTION_CHANGE_LOG,
    };
    public static final String CTX_PopupMenuitemDisable = "_TabInfo_PopupMenuDisable";
    
    private Menuitem zoomItem;
    private Menuitem requeryItem;
    private Menuitem prefItem;
    private Menuitem newItem;
    private Menuitem updateItem; // Elaine 2009/02/16 - update record   
    
    private ArrayList<ContextMenuListener> menuListeners = new ArrayList<ContextMenuListener>();
    
    public WEditorPopupMenu(boolean zoom, boolean requery, boolean preferences)
    {
        this(zoom, requery, preferences, false, false);
    }
    
    public WEditorPopupMenu(boolean zoom, boolean requery, boolean preferences, boolean newRecord)
    {
    	this(zoom, requery, preferences, newRecord, false);
    }
    
    public WEditorPopupMenu(boolean zoom, boolean requery, boolean preferences, boolean newRecord, boolean updateRecord)
    {
        super();
        this.zoomEnabled = zoom;
        this.requeryEnabled = requery;
        this.preferencesEnabled = preferences;
        this.newEnabled = newRecord;
        this.updateEnabled = updateRecord; // Elaine 2009/02/16 - update record
        init();
    }

    public boolean isZoomEnabled() {
    	return zoomEnabled;
    }
    
    public boolean isPreferenceEnabled()
    {
    	return preferencesEnabled;
    }
    
    /**
     * Dynamically disable popup items
     * @param name item name (see ACTION_* constants)
     * @param disabled
     */
    public void setDisableItemDynamic(String name, boolean disabled)
    {
    	mapDisabledItems.put(name, disabled);
    	updateItemsDyn();
    }
    
    public void disableAllDynamic()
    {
    	for (final String name: ACTIONS)
    	{
    		setDisableItemDynamic(name, true);
    	}
    }
    
    /**
     * 
     * @param hide true if disabled items shall not be visible
     */
    public void setHideDisabledItems(boolean hide)
    {
    	this.hideDisabledItems = hide;
    	updateItemsDyn();
    }
    
    /**
     * @param name item name (see ACTION_* constants)
     * @return true if the menu item is dynamically disabled.
     * 				Please note that this method is not checking the values used when you constructed the object.
     */
    public boolean isDisabledDynamical(String name)
    {
    	Boolean disabled = mapDisabledItems.get(name);
    	return disabled == null ? false : disabled.booleanValue();
    }
    
    private void init()
    {
        if (zoomEnabled)
        {
            zoomItem = new Menuitem();
            zoomItem.setAttribute(EVENT_ATTRIBUTE, ZOOM_EVENT);
            zoomItem.setLabel(Msg.getMsg(Env.getCtx(), "Zoom"));
            zoomItem.setImage("/images/Zoom16.png");
            zoomItem.addEventListener(Events.ON_CLICK, this);
            this.appendChild(zoomItem);
        }
        
        if (requeryEnabled)
        {
            requeryItem = new Menuitem();
            requeryItem.setAttribute(EVENT_ATTRIBUTE, REQUERY_EVENT);
            requeryItem.setLabel("ReQuery");
            requeryItem.setImage("/images/Refresh16.png");
            requeryItem.addEventListener(Events.ON_CLICK, this);
            this.appendChild(requeryItem);
        }
        
        if (preferencesEnabled)
        {
            prefItem = new Menuitem();
            prefItem.setAttribute(EVENT_ATTRIBUTE, PREFERENCE_EVENT);
            prefItem.setLabel(Msg.getMsg(Env.getCtx(), "ValuePreference"));
            prefItem.setImage("/images/VPreference16.png");
            prefItem.addEventListener(Events.ON_CLICK, this);
            this.appendChild(prefItem);
        }
        
        if (newEnabled)
        {
        	newItem = new Menuitem();
        	newItem.setAttribute(EVENT_ATTRIBUTE, NEW_EVENT);
        	newItem.setLabel(Msg.getMsg(Env.getCtx(), "NewRecord"));
        	newItem.setImage("/images/New16.png");
        	newItem.addEventListener(Events.ON_CLICK, this);
        	this.appendChild(newItem);
        }
        
        // Elaine 2009/02/16 - update record
        if (updateEnabled)
        {
        	updateItem = new Menuitem();
        	updateItem.setAttribute(EVENT_ATTRIBUTE, UPDATE_EVENT);
        	updateItem.setLabel("Update Record");
        	updateItem.setImage("/images/InfoBPartner16.png");
        	updateItem.addEventListener(Events.ON_CLICK, this);
        	this.appendChild(updateItem);
        }
        
        // Don't open the popup if there are no items to show
        this.addEventListener(Events.ON_OPEN, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				final OpenEvent oe = (OpenEvent)event;
				if (oe.isOpen() && !hasVisibleItems)
				{
					close();
				}
			}
		});
        
        //
    	updateItemsDyn();
    }
    
    private void updateItemsDyn()
    {
    	if (zoomItem != null)
    	{
    		zoomItem.setDisabled(isDisabledDynamical(ACTION_ZOOM));
    		zoomItem.setVisible(!hideDisabledItems || !zoomItem.isDisabled());
    	}
    	if (requeryItem != null)
    	{
    		requeryItem.setDisabled(isDisabledDynamical(ACTION_REQUERY));
    		requeryItem.setVisible(!hideDisabledItems || !requeryItem.isDisabled());
    	}
    	if (prefItem != null)
    	{
    		prefItem.setDisabled(isDisabledDynamical(ACTION_PREFERENCE));
    		prefItem.setVisible(!hideDisabledItems || !prefItem.isDisabled());
    	}
    	if (newItem != null)
    	{
    		newItem.setDisabled(isDisabledDynamical(ACTION_NEW));
    		newItem.setVisible(!hideDisabledItems || !newItem.isDisabled());
    	}
    	if (updateItem != null)
    	{
    		updateItem.setDisabled(isDisabledDynamical(ACTION_UPDATE));
    		updateItem.setVisible(!hideDisabledItems || !updateItem.isDisabled());
    	}

    	//
    	// Check if we have visible items to display
    	boolean hasItems = false;
    	for (Object o : this.getChildren())
    	{
    		final Component c = (Component)o;
    		if (c.isVisible())
    		{
    			hasItems = true;
    			break;
    		}
    	}
    	hasVisibleItems = hasItems;
    }

    /**
     * Update popup menu items from editor
     * @param editor
     */
    private void updateItemsFromEditor(WEditor editor)
    {
		GridField gridField = editor.getGridField();
		if (gridField == null)
			return;
		final int windowNo = gridField.getWindowNo();
		
		GridTab gridTab = gridField.getGridTab();
		if (gridTab == null)
			return;
		final int tabNo = gridTab.getTabNo();
		
		final String columnName = gridField.getColumnName();
		
		for (String action : ACTIONS)
		{
			String context = buildDisableItemContext(action, columnName);
			String value = Env.getContext(Env.getCtx(), windowNo, tabNo, context);
			if ("Y".equals(value))
			{
				mapDisabledItems.put(action, true);
				continue;
			}
			else if ("N".equals(value))
			{
				mapDisabledItems.put(action, false);
				continue;
			}
			
			// Check settings for all columns:
			context = buildDisableItemContext(action, "*");
			if ("Y".equals(value))
			{
				mapDisabledItems.put(action, true);
				continue;
			}
			else if ("N".equals(value))
			{
				mapDisabledItems.put(action, false);
				continue;
			}
		}
		
		updateItemsDyn();
    }
    
    private static final String buildDisableItemContext(String action, String columnName)
    {
		return CTX_PopupMenuitemDisable+"|"+action+"|"+columnName;
    }
    
    /**
     * Specify which popup menu items should be disabled
     * @param windowNo
     * @param tabNo
     * @param columnName
     * @param action
     * @param value
     */
    public static void setDisableItemInContext(int windowNo, int tabNo, String columnName, String action, Boolean value)
    {
		String context = buildDisableItemContext(action, columnName);
		String valueStr;
		if (value == null)
			valueStr = null;
		else
			valueStr = value.booleanValue() ? "Y" : "N";
		Env.setContext(Env.getCtx(), windowNo, tabNo, context, valueStr);
    }
    
    public void addMenuListener(ContextMenuListener listener)
    {
    	if (!menuListeners.contains(listener))
    		menuListeners.add(listener);
    	
    	if (listener instanceof WEditor)
    	{
    		WEditor editor = (WEditor)listener;
    		updateItemsFromEditor(editor);
    	}
    }

    public void onEvent(Event event)
    {
        String evt = (String)event.getTarget().getAttribute(EVENT_ATTRIBUTE);
        
        if (evt != null)
        {
            ContextMenuEvent menuEvent = new ContextMenuEvent(evt);
            
            ContextMenuListener[] listeners = new ContextMenuListener[0];
            listeners = menuListeners.toArray(listeners);
            for (ContextMenuListener listener : listeners)
            {
                listener.onMenu(menuEvent);
            }
        }
    }
}
