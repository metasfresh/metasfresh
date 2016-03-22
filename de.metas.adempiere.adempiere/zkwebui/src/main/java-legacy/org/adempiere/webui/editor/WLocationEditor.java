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
import java.beans.PropertyChangeListener;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Locationbox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.adempiere.webui.window.WLocationDialog;
import org.compiere.model.GridField;
import org.compiere.model.MLocation;
import org.compiere.model.MLocationLookup;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 * @author Sendy Yagambrum
 * @date July 16, 2007
 * 
 * This class is based on VLocation written by Jorg Janke
 **/
public class WLocationEditor extends WEditor implements EventListener, PropertyChangeListener, ContextMenuListener
{
    private static final String[] LISTENER_EVENTS = {Events.ON_CLICK};
    
    private static Logger log = LogManager.getLogger(WLocationEditor.class);
    private MLocationLookup     m_Location;
    private MLocation           m_value;

	private WEditorPopupMenu popupMenu;
    
    /**
     * Constructor without GridField
     * @param columnName    column name
     * @param mandatory     mandatory
     * @param isReadOnly    read only
     * @param isUpdateable  updateable
     * @param mLocation     location model
    **/
    public WLocationEditor(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
            MLocationLookup mLocation)
    {
        super(new Locationbox(), "Address","",mandatory,isReadOnly,isUpdateable);
       
        setColumnName(columnName);
        m_Location = mLocation;
        init();
    }

    /**
     * 
     * @param gridField
     */
    public WLocationEditor(GridField gridField) {
		super(new Locationbox(), gridField);
		m_Location = (MLocationLookup)gridField.getLookup();
	}

    private void init()
    {
    	getComponent().setButtonImage("/images/Location10.png");
    	
    	popupMenu = new WEditorPopupMenu(false, false, Env.getUserRolePermissions().isShowPreference());
    	popupMenu.addMenuListener(this);
    	if (gridField != null && gridField.getGridTab() != null)
		{
			WFieldRecordInfo.addMenu(popupMenu);
		}
    	getComponent().setContext(popupMenu.getId());
    }
    
	@Override
    public String getDisplay()
    {
        return getComponent().getText();
    }

    @Override
    public Object getValue()
    {
        if (m_value == null)
            return null;
        return new Integer(m_value.getC_Location_ID());
    }

    @Override
    public void setValue(Object value)
    {
        if (value == null)
        {
            m_value = null;
            getComponent().setText(null);
        }
        else
        {
            m_value = m_Location.getLocation(value, null);
            if (m_value == null)
                getComponent().setText("<" + value + ">");
            else
                getComponent().setText(m_value.toString());
        }
    }
    
    @Override
	public Locationbox getComponent() {
		return (Locationbox) component;
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	/**
     *  Return Editor value
     *  @return value
     */
    public int getC_Location_ID()
    {
        if (m_value == null)
            return 0;
        return m_value.getC_Location_ID();
    }   
    
    /**
     *  Property Change Listener
     *  @param evt PropertyChangeEvent
     */
    public void propertyChange (PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
            setValue(evt.getNewValue());
    }
    
    public void onEvent(Event event) throws Exception
    {    
        //
        if ("onClick".equals(event.getName()))
        {
            log.info( "actionPerformed - " + m_value);
            WLocationDialog ld = new WLocationDialog(Msg.getMsg(Env.getCtx(), "Location"), m_value);
            ld.setVisible(true);
            AEnv.showWindow(ld);
            m_value = ld.getValue();
            //
           if (!ld.isChanged())
                return;
    
            //  Data Binding
            int C_Location_ID = 0;
            if (m_value != null)
                C_Location_ID = m_value.getC_Location_ID();
            Integer ii = new Integer(C_Location_ID);
            //  force Change - user does not realize that embedded object is already saved.
            ValueChangeEvent valuechange = new ValueChangeEvent(this,getColumnName(),null,null);
            fireValueChange(valuechange);   //  resets m_mLocation
            if (C_Location_ID != 0)
            {
                ValueChangeEvent vc = new ValueChangeEvent(this,getColumnName(),null,ii);
                fireValueChange(vc);
            }
            setValue(ii); 
        }
    }
    
    /**
     * return listener events to be associated with editor component
     */
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
