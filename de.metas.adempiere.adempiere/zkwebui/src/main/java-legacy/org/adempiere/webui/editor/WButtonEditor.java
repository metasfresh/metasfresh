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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.event.ActionEvent;
import org.adempiere.webui.event.ActionListener;
import org.compiere.model.GridField;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

/**
 * This class is based on org.compiere.grid.ed.VButton written by Jorg Janke.
 * @author Jorg Janke
 * 
 * Modifications - UI Compatibility
 * @author ashley
 */
public class WButtonEditor extends WEditor
{
    private static final String[] LISTENER_EVENTS = {Events.ON_CLICK};
    
    private static final Logger logger;
    
    static
    {
        logger = LogManager.getLogger(WButtonEditor.class);
    }
    
    private String          m_text;
    private boolean         m_mandatory;
    private Object          m_value;
    /** List of Key/Name        */
    private HashMap<String,String>  m_values = null;
   
    /** Description as ToolTip  */
  
    private MLookup         m_lookup;
  
    private int AD_Process_ID;
    private GridField gridfield = null;
    
    private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
    
    public WButtonEditor(GridField gridField)
    {
        super(new Button(), gridField);
        m_text = gridField.getHeader();
        AD_Process_ID = gridField.getAD_Process_ID();
        gridfield = gridField;
        init();
    }

    /**
	 *	Get AD_Process_ID
	 *  @return AD_Process_ID or 0
	 */
	public int getProcess_ID()
	{
		return AD_Process_ID;
	}	//	getProcess_ID
	
	public GridField getGridField()
	{
		return gridfield;
	}

    private void init()
    {
        label.setValue(" ");
        getComponent().setLabel(gridField.getHeader());
        getComponent().setTooltiptext(gridField.getDescription());
        
        String columnName = super.getColumnName();
        if (columnName.equals("PaymentRule"))
        {
            readReference(gridField.getAD_Reference_Value_ID()); // metas: use reference from field instead of hardcoded 195=_Payment Rule
            getComponent().setImage("/images/Payment16.png");    //  29*14
        }
        else if (columnName.equals("DocAction"))
        {
            readReference(135);
            getComponent().setImage("/images/Process16.png");    //  16*16
        }
        else if (columnName.equals("CreateFrom"))
        {
            getComponent().setImage("/images/Copy16.png");       //  16*16
        }
        else if (columnName.equals("Record_ID"))
        {
            getComponent().setImage("/images/Zoom16.png");       //  16*16
            getComponent().setLabel(Msg.getMsg(Env.getCtx(), "ZoomDocument"));
        }
        else if (columnName.equals("Posted"))
        {
            readReference(234);
            getComponent().setImage("/images/InfoAccount16.png");    //  16*16
        }
        
        if (gridField.getColumnName().endsWith("_ID") && !gridField.getColumnName().equals("Record_ID"))
        {
            m_lookup = MLookupFactory.get(Env.getCtx(), gridField.getWindowNo(), 0,
                    gridField.getAD_Column_ID(), DisplayType.Search);
        }
        else if (gridField.getAD_Reference_Value_ID() != 0)
        {
            //  Assuming List
            m_lookup = MLookupFactory.get(Env.getCtx(), gridField.getWindowNo(), 0,
                    gridField.getAD_Column_ID(), DisplayType.List);
        }
    }

     @Override
    public String getDisplay()
    {
        return m_value.toString();
    }

    @Override
    public Object getValue()
    {
        return m_values;
    }

    @Override
    public boolean isMandatory()
    {
        return m_mandatory;
    }
   
    
    @Override
    public void setMandatory(boolean mandatory)
    {
        m_mandatory = mandatory;
    }

    @Override
    public void setValue(Object value)
    {
        m_value = value;
        String text = m_text;

        //  Nothing to show or Record_ID
        if (value == null || super.getColumnName().equals("Record_ID"))
        {
            ;
        }
        else if (m_values != null)
        {
            text = (String)m_values.get(value);
        }
        else if (m_lookup != null)
        {
            NamePair pp = m_lookup.get(value);
            if (pp != null)
                text = pp.getName();
        }
        getComponent().setLabel(text != null ? text : "");
    }
    
    @Override
	public Button getComponent() {
		return (Button) component;
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	public HashMap<String, String> getValues()
    {
    	return m_values;
    }	//	getValues
    
    /**
     *  Fill m_Values with Ref_List values
     *  @param AD_Reference_ID reference
     */
    private void readReference(int AD_Reference_ID)
    {
        m_values = new HashMap<String,String>();
        
        String SQL;
        if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
            SQL = "SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID=?  AND IsActive='Y'";
        else
            SQL = "SELECT l.Value, t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t "
                + "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
                + " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
                + " AND l.AD_Reference_ID=?  AND l.IsActive='Y'";

        try
        {
            PreparedStatement pstmt = DB.prepareStatement(SQL, null);
            pstmt.setInt(1, AD_Reference_ID);
            ResultSet rs = pstmt.executeQuery();
           
            while (rs.next())
            {
                String value = rs.getString(1);
                String name = rs.getString(2);
                m_values.put(value,name);
            }
           
            
            rs.close();
            pstmt.close();
        }
        catch (SQLException e)
        {
            logger.error(SQL, e);
        }
       
    }   //  readReference
    
    public void addActionListener(ActionListener actionListener)
    {
    	if (!actionListeners.contains(actionListener))
    		actionListeners.add(actionListener);
    }

    @Override
    public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

	public void onEvent(Event event) throws Exception 
	{
		if (Events.ON_CLICK.equals(event.getName()))
		{
			ActionEvent actionEvent = new ActionEvent(this, getColumnName(), Events.ON_CLICK);
			ActionListener[] listeners = new ActionListener[0];
			listeners = actionListeners.toArray(listeners);
			for (ActionListener evtListener : listeners)
			{
				evtListener.actionPerformed(actionEvent);
			}
		}
	}
}
