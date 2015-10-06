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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Check;
import org.adempiere.webui.ValuePreference;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Searchbox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.grid.WBPartner;
import org.adempiere.webui.panel.IInfoPanel;
import org.adempiere.webui.panel.InfoPanel;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

/**
 * Search Editor for web UI.
 * Web UI port of search type VLookup
 *
 * @author Ashley G Ramdass
 *
 */
public class WSearchEditor extends WEditor implements ContextMenuListener, ValueChangeListener, IZoomableEditor
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CLICK, Events.ON_CHANGE, Events.ON_OK};
	private Lookup 				lookup;
	private String				m_tableName = null;
	private String				m_keyColumnName = null;
	private String 				columnName;
	private WEditorPopupMenu	popupMenu;
    private Object              value;
    private IInfoPanel			infoPanel = null;

	private static CLogger log = CLogger.getCLogger(WSearchEditor.class);

	public WSearchEditor (GridField gridField)
	{
		super(new Searchbox(), gridField);

		lookup = gridField.getLookup();
		
		if (lookup != null)
			columnName = lookup.getColumnName();
		
		init();
	}

	
    @Override
	public Searchbox getComponent() {
		return (Searchbox) super.getComponent();
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
	 * Constructor for use if a grid field is unavailable
	 *
	 * @param lookup		Store of selectable data
	 * @param label			column name (not displayed)
	 * @param description	description of component
	 * @param mandatory		whether a selection must be made
	 * @param readonly		whether or not the editor is read only
	 * @param updateable	whether the editor contents can be changed
	 */
	public WSearchEditor (Lookup lookup, String label, String description, boolean mandatory, boolean readonly, boolean updateable)
	{
		super(new Searchbox(), label, description, mandatory, readonly, updateable);

		if (lookup == null)
		{
			throw new IllegalArgumentException("Lookup cannot be null");
		}

		this.lookup = lookup;
        columnName = lookup.getColumnName();
		super.setColumnName(columnName);
		init();
	}

	public WSearchEditor(String columnName, boolean mandatory, boolean readonly, boolean updateable,
    		Lookup lookup) 
	{
		super(new Searchbox(), null, null, mandatory, readonly, updateable);

		if (lookup == null)
		{
			throw new IllegalArgumentException("Lookup cannot be null");
		}

		this.lookup = lookup;
        this.columnName = columnName;
		super.setColumnName(columnName);
		init();
	}


	/**
     * initialise editor
     * @param columnName columnName
	 */
	private void init()
	{

		columnName = this.getColumnName();
                
		final boolean popupShowPreferences = Env.getUserRolePermissions().isShowPreference();
		if (columnName.equals("C_BPartner_ID"))
		{
			popupMenu = new WEditorPopupMenu(true, true, popupShowPreferences, true, true);
			getComponent().setButtonImage("/images/BPartner10.png");
		}
		else if (columnName.equals("M_Product_ID"))
		{
			popupMenu = new WEditorPopupMenu(true, true, popupShowPreferences, false, false);
			getComponent().setButtonImage("/images/Product10.png");
		}
		else
		{
			popupMenu = new WEditorPopupMenu(true, true, popupShowPreferences, false, false);
			getComponent().setButtonImage("/images/PickOpen10.png");
		}
		
		getComponent().getTextbox().setContext(popupMenu.getId());
		if (gridField != null && gridField.getGridTab() != null)
		{
			WFieldRecordInfo.addMenu(popupMenu);
		}

		return;
	}

	public WEditorPopupMenu getPopupMenu()
	{
	   	return popupMenu;
	}

	@Override
	public void setValue(Object value)
	{
        this.value = value;
		if (value != null && !"".equals(String.valueOf(value)))
		{
		    String text = lookup.getDisplay(value);

            if (text.startsWith("_"))
            {
                text = text.substring(1);
            }

            getComponent().setText(text);
		}
		else
		{
			getComponent().setText("");
		}
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public String getDisplay()
	{
		return getComponent().getText();
	}

	public void onEvent(Event e)
	{
		if (Events.ON_CHANGE.equals(e.getName()) || Events.ON_OK.equals(e.getName()))
		{
			if (infoPanel != null)
		 	{
				infoPanel.detach();
		 	 	infoPanel = null;
		 	}
			actionText(getComponent().getText());

		}
		else if (Events.ON_CLICK.equals(e.getName()))
		{
			if (infoPanel != null)
			{
				infoPanel.detach();
				infoPanel = null;
			}
			actionButton("", true);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if ("FieldValue".equals(evt.getPropertyName()))
		{
			if ( evt.getNewValue()== null)
			{
				actionRefresh("");
			}
			else
			{
				actionRefresh(evt.getNewValue());
			}
		}
	}

	private void actionRefresh(Object value)
	{
//		boolean mandatory = isMandatory();
//		AEnv.actionRefresh(lookup, value, mandatory);
    	setValue(value);
	}

	public void actionZoom()
	{
	   	AEnv.actionZoom(lookup, getValue());
	}
    private void actionZoom(Object value)
    {
        AEnv.actionZoom(lookup, value);
    }

	public void onMenu(ContextMenuEvent evt)
	{
		if (WEditorPopupMenu.REQUERY_EVENT.equals(evt.getContextEvent()))
		{
			actionRefresh(getValue());
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
		else if (WEditorPopupMenu.NEW_EVENT.equals(evt.getContextEvent()))
		{
			if (infoPanel != null)
			{
				infoPanel.detach();
				infoPanel = null;
			}
			actionBPartner(true);
		}
		// Elaine 2009/02/16 - update record
		else if (WEditorPopupMenu.UPDATE_EVENT.equals(evt.getContextEvent()))
		{
			if (infoPanel != null)
			{
				infoPanel.detach();
				infoPanel = null;
			}
			actionBPartner(false);
		}
		else if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
		//
	}

	private void actionText(String text)
	{
		//	Nothing entered
		if (text == null || text.trim().length() == 0 || text.equals("%"))
		{
			actionButton(text, false);
			return;
		}
		text = text.toUpperCase();
		log.config(getColumnName() + " - " + text);

		//	Exact first
		PreparedStatement pstmt = null;
		String finalSQL = Msg.parseTranslation(Env.getCtx(), getDirectAccessSQL(text));
		int id = -3;

		try
		{
			pstmt = DB.prepareStatement(finalSQL, null);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				id = rs.getInt(1);		//	first
				if (rs.next())
					id = -1;			//	only if unique
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, finalSQL, e);
			id = -2;
		}

		//	Try like
		if (id == -3 && !text.endsWith("%"))
		{
			text += "%";
			finalSQL = Msg.parseTranslation(Env.getCtx(), getDirectAccessSQL(text));

			try
			{
				pstmt = DB.prepareStatement(finalSQL, null);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					id = rs.getInt(1);		//	first
					if (rs.next())
						id = -1;			//	only if unique
				}
				rs.close();
				pstmt.close();
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, finalSQL, e);
				id = -2;
			}
		}

		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (Exception e)
		{
		}

		//	No (unique) result
		if (id <= 0)
		{
			if (id == -3)
				log.fine(getColumnName() + " - Not Found - " + finalSQL);
			else
				log.fine(getColumnName() + " - Not Unique - " + finalSQL);

			//m_value = null;	// force re-display
			actionButton(getComponent().getText(), false);
			return;
		}
		log.fine(getColumnName() + " - Unique ID=" + id);

		actionCombo(new Integer(id));          //  data binding
		//m_text.requestFocus();
	}	//	actionText


	private void actionCombo (Object value)
	{
		log.fine("Value=" + value);

		ValueChangeEvent evt = new ValueChangeEvent(this, this.getColumnName(), getValue(), value);
		// -> ADTabpanel - valuechange
		fireValueChange(evt);
		
		//  is the value updated ?
		boolean updated = false;
		if (value instanceof Object[] && ((Object[])value).length > 0)
		{
			value = ((Object[])value)[0];
		}
		
		if (value == null && getValue() == null)
			updated = true;
		else if (value != null && value.equals(getValue()))
			updated = true;
		if (!updated)
		{
			setValue(value);
		}
	}	//	actionCombo

	/**
	 *	Action - Special BPartner Screen
	 *  @param newRecord true if new record should be created
	 */
	
	private void actionBPartner (boolean newRecord)
	{
		WBPartner vbp = new WBPartner (lookup.getWindowNo());
		int BPartner_ID = 0;
		
		//  if update, get current value
		if (!newRecord)
		{
			if (value instanceof Integer)
				BPartner_ID = ((Integer)value).intValue();
			else if (value != null)
				BPartner_ID = Integer.parseInt(value.toString());
		}

		vbp.loadBPartner (BPartner_ID);
		
		
		vbp.setVisible(true);
		AEnv.showWindow(vbp);
		
		//  get result
		int result = vbp.getC_BPartner_ID();
		
		if (result == 0					//	0 = not saved
			&& result == BPartner_ID)	//	the same
			return;
		
		//  Maybe new BPartner - put in cache
		lookup.getDirect(IValidationContext.NULL, new Integer(result), false, true);
		setValue(new Integer(result));
		actionCombo (new Integer(result));      //  data binding
		
		//setValue(getValue());
	}	//	actionBPartner
	
	/**
	 * 
	 * @param queryValue
	 * @param userManualCmd action was invoked by user, by pressing the small popup button
	 */
	private void actionButton(String queryValue, boolean userManualCmd)
	{
		if (lookup == null)
			return;		//	leave button disabled
		
		// metas: begin: 02923:
		// When user clears the input text field, we shall set the value of this field to null
		if (!userManualCmd && Check.isEmpty(queryValue, true))
		{
			log.fine(getColumnName() + " - Result = null (reset because queryValue was empty)");
			actionCombo(null); // reset value and fire events
			return;
		}
		// metas: end
		
		/**
		 *  Three return options:
		 *  - Value Selected & OK pressed   => store result => result has value
		 *  - Cancel pressed                => store null   => result == null && cancelled
		 *  - Window closed                 -> ignore       => result == null && !cancalled
		 */

		Object result[] = null;			
		boolean cancelled = false;	

		String col = lookup.getColumnName();		//	fully qualified name

		if (col.indexOf('.') != -1)
			col = col.substring(col.indexOf('.')+1);

		//  Zoom / Validation
		String whereClause = getWhereClause();

		log.fine(col + ", Zoom=" + lookup.getZoom() + " (" + whereClause + ")");

		// boolean resetValue = false;	// Reset value so that is always treated as new entry

		if (col.equals("M_Product_ID"))
		{
			//	Reset
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_Product_ID", null);
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_AttributeSetInstance_ID", null);
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_Lookup_ID", null);

			//  Replace Value with name if no value exists
			if (queryValue.length() == 0 && getComponent().getText().length() > 0)
				queryValue = "@" + getComponent().getText() + "@";   //  Name indicator - otherwise Value

			int M_Warehouse_ID = Env.getContextAsInt(Env.getCtx(), lookup.getWindowNo(), "M_Warehouse_ID");
			int M_PriceList_ID = Env.getContextAsInt(Env.getCtx(), lookup.getWindowNo(), "M_PriceList_ID");

			//	Show Info
			
			//metas: c.ghita@metas.ro start
			IInfoPanel ip = InfoPanel.create(lookup.getWindowNo(), "M_Product", "M_Product_ID", queryValue,
					true, whereClause); // metas: changed
			ip.setAttribute("M_Warehouse_ID", M_Warehouse_ID);
			ip.setAttribute("M_PriceList_ID", M_PriceList_ID);
			//metas: c.ghita@metas.ro end
			ip.setVisible(true);
			ip.setTitle("Product Info");
			ip.setStyle("border: 2px");
			ip.setClosable(true);
			ip.setAttribute("mode", "modal");
			ip.addValueChangeListener(this);
			infoPanel = ip;
			AEnv.showWindow(ip);
			
			cancelled = ip.isCancelled();
			result = ip.getSelectedKeys();
		}
		else if (col.equals("C_BPartner_ID"))
		{
			//  Replace Value with name if no value exists
			if (queryValue.length() == 0 && getComponent().getText().length() > 0)
				queryValue = getComponent().getText();

			//metas: c.ghita@metas.ro start
//			boolean isSOTrx = true;     //  default
//
//			if (Env.getContext(Env.getCtx(), lookup.getWindowNo(), "IsSOTrx").equals("N"))
//				isSOTrx = false;
			IInfoPanel ip = InfoPanel.create(lookup.getWindowNo(),  "C_BPartner", "C_BPartner_ID", queryValue, false, whereClause);
			//metas: c.ghita@metas.ro start
			ip.setVisible(true);
			ip.setTitle("Business Partner Info");
			ip.setStyle("border: 2px");
			ip.setClosable(true);
			ip.setAttribute("mode", "modal");
			ip.addValueChangeListener(this);
			infoPanel = ip;
			AEnv.showWindow(ip);

			cancelled = ip.isCancelled();
			result = ip.getSelectedKeys();
		}
		else	//	General Info
		{
			if (m_tableName == null)	//	sets table name & key column
				getDirectAccessSQL("*");

            if (queryValue.length() == 0 && getComponent().getText().length() > 0)
                queryValue = getComponent().getText();

			IInfoPanel ig = InfoPanel.create(lookup.getWindowNo(), m_tableName,m_keyColumnName,queryValue, false, whereClause);
			ig.setVisible(true);
			ig.setStyle("border: 2px");
			ig.setClosable(true);
			ig.setAttribute("mode", "modal");
			ig.addValueChangeListener(this);
			infoPanel = ig;
			AEnv.showWindow(ig);

			cancelled = ig.isCancelled();
			result = ig.getSelectedKeys();

		}

		infoPanel = null;
		//  Result
		if (result != null && result.length > 0)
		{
			//ensure data binding happen
			if (result.length > 1)
				actionCombo (result);
			else
				actionCombo (result[0]);
		}
		else if (cancelled)
		{
			log.config(getColumnName() + " - Result = null (cancelled)");
			actionCombo(null);
		}
		else
		{
			log.config(getColumnName() + " - Result = null (not cancelled)");
		}
		
	}

	/**
	 * 	Generate Access SQL for Search.
	 * 	The SQL returns the ID of the value entered
	 * 	Also sets m_tableName and m_keyColumnName
	 *	@param text uppercase text for LIKE comparison
	 *	@return sql or ""
	 *  Example
	 *	SELECT C_Payment_ID FROM C_Payment WHERE UPPER(DocumentNo) LIKE x OR ...
	 */
	private String getDirectAccessSQL (String text)
	{
		String m_columnName = getColumnName();
		
		StringBuffer sql = new StringBuffer();
		m_tableName = m_columnName.substring(0, m_columnName.length()-3);
		m_keyColumnName = m_columnName;

		if (m_columnName.equals("M_Product_ID"))
		{
			//	Reset
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_Product_ID", "0");
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_AttributeSetInstance_ID", "0");
			Env.setContext(Env.getCtx(), lookup.getWindowNo(), Env.TAB_INFO, "M_Locator_ID", "0");

			sql.append("SELECT M_Product_ID FROM M_Product WHERE (UPPER(Value) LIKE ")
				.append(DB.TO_STRING(text))
				.append(" OR UPPER(Name) LIKE ").append(DB.TO_STRING(text))
				.append(" OR UPC LIKE ").append(DB.TO_STRING(text)).append(")");
		}
		else if (m_columnName.equals("C_BPartner_ID"))
		{
			sql.append("SELECT C_BPartner_ID FROM C_BPartner WHERE (UPPER(Value) LIKE ")
				.append(DB.TO_STRING(text))
				.append(" OR UPPER(Name) LIKE ").append(DB.TO_STRING(text)).append(")");
		}
		else if (m_columnName.equals("C_Order_ID"))
		{
			sql.append("SELECT C_Order_ID FROM C_Order WHERE UPPER(DocumentNo) LIKE ")
				.append(DB.TO_STRING(text));
		}
		else if (m_columnName.equals("C_Invoice_ID"))
		{
			sql.append("SELECT C_Invoice_ID FROM C_Invoice WHERE UPPER(DocumentNo) LIKE ")
				.append(DB.TO_STRING(text));
		}
		else if (m_columnName.equals("M_InOut_ID"))
		{
			sql.append("SELECT M_InOut_ID FROM M_InOut WHERE UPPER(DocumentNo) LIKE ")
				.append(DB.TO_STRING(text));
		}
		else if (m_columnName.equals("C_Payment_ID"))
		{
			sql.append("SELECT C_Payment_ID FROM C_Payment WHERE UPPER(DocumentNo) LIKE ")
				.append(DB.TO_STRING(text));
		}
		else if (m_columnName.equals("GL_JournalBatch_ID"))
		{
			sql.append("SELECT GL_JournalBatch_ID FROM GL_JournalBatch WHERE UPPER(DocumentNo) LIKE ")
				.append(DB.TO_STRING(text));
		}
		else if (m_columnName.equals("SalesRep_ID"))
		{
			sql.append("SELECT AD_User_ID FROM AD_User WHERE UPPER(Name) LIKE ")
				.append(DB.TO_STRING(text));
			
			m_tableName = "AD_User";
			m_keyColumnName = "AD_User_ID";
		}
		
		//	Predefined
		
		if (sql.length() > 0)
		{
			String wc = getWhereClause();
			
			if (wc != null && wc.length() > 0)
				sql.append(" AND ").append(wc);
			
			sql.append(" AND IsActive='Y'");
			//	***
			
			log.finest(m_columnName + " (predefined) " + sql.toString());
			
			return Env.getUserRolePermissions().addAccessSQL(sql.toString(),
				m_tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		}
		
		//	Check if it is a Table Reference
		
		if (lookup != null && lookup instanceof MLookup)
		{
			int AD_Reference_ID = ((MLookup)lookup).getAD_Reference_Value_ID();
		
			if (AD_Reference_ID != 0)
			{
				boolean isValueDisplayed = false;
				String query = "SELECT kc.ColumnName, dc.ColumnName, t.TableName, rt.IsValueDisplayed "
					+ "FROM AD_Ref_Table rt"
					+ " INNER JOIN AD_Column kc ON (rt.AD_Key=kc.AD_Column_ID)"
					+ " INNER JOIN AD_Column dc ON (rt.AD_Display=dc.AD_Column_ID)"
					+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
					+ "WHERE rt.AD_Reference_ID=?";
				
				String displayColumnName = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try
				{
					pstmt = DB.prepareStatement(query, null);
					pstmt.setInt(1, AD_Reference_ID);
					rs = pstmt.executeQuery();
				
					if (rs.next())
					{
						m_keyColumnName = rs.getString(1);
						displayColumnName = rs.getString(2);
						m_tableName = rs.getString(3);
						String t = rs.getString(4);
						isValueDisplayed = "Y".equalsIgnoreCase(t);
					}
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, query, e);
				}
				finally
				{
					DB.close(rs, pstmt);
				}
				
				
				if (displayColumnName != null)
				{
					sql = new StringBuffer();
					sql.append("SELECT ").append(m_keyColumnName)
						.append(" FROM ").append(m_tableName)
						.append(" WHERE (UPPER(").append(displayColumnName)
						.append(") LIKE ").append(DB.TO_STRING(text));
					if (isValueDisplayed)
					{
						sql.append(" OR UPPER(").append("Value")
						   .append(") LIKE ").append(DB.TO_STRING(text));
					}
					sql.append(")");
					sql.append(" AND IsActive='Y'");
				
					String wc = getWhereClause();
					
					if (wc != null && wc.length() > 0)
						sql.append(" AND ").append(wc);
					
					//	***
					
					log.finest(m_columnName + " (Table) " + sql.toString());
					
					return Env.getUserRolePermissions().addAccessSQL(sql.toString(),
								m_tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
				}
			} // Table Reference
		} // MLookup
		
		/** Check Well Known Columns of Table - assumes TableDir	**/
		
		String query = "SELECT t.TableName, c.ColumnName "
			+ "FROM AD_Column c "
			+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID AND t.IsView='N') "
			+ "WHERE (c.ColumnName IN ('DocumentNo', 'Value', 'Name') OR c.IsIdentifier='Y')"
			+ " AND c.AD_Reference_ID IN (10,14)"
			+ " AND EXISTS (SELECT * FROM AD_Column cc WHERE cc.AD_Table_ID=t.AD_Table_ID"
				+ " AND cc.IsKey='Y' AND cc.ColumnName=?)";
		
		m_keyColumnName = m_columnName;
		sql = new StringBuffer();
		PreparedStatement pstmt = null;
		
		try
		{
			pstmt = DB.prepareStatement(query, null);
			pstmt.setString(1, m_keyColumnName);
			ResultSet rs = pstmt.executeQuery();
		
			while (rs.next())
			{
				if (sql.length() != 0)
					sql.append(" OR ");
			
				m_tableName = rs.getString(1);
				sql.append("UPPER(").append(rs.getString(2)).append(") LIKE ").append(DB.TO_STRING(text));
			}
			
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.log(Level.SEVERE, query, ex);
		}
		
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		if (sql.length() == 0)
		{
			log.log(Level.SEVERE, m_columnName + " (TableDir) - no standard/identifier columns");
			return "";
		}
		//
		StringBuffer retValue = new StringBuffer ("SELECT ")
			.append(m_columnName).append(" FROM ").append(m_tableName)
			.append(" WHERE ").append(sql)
			.append(" AND IsActive='Y'");
		
		String wc = getWhereClause();
		
		if (wc != null && wc.length() > 0)
			retValue.append(" AND ").append(wc);
		//	***
		log.finest(m_columnName + " (TableDir) " + sql.toString());
		return Env.getUserRolePermissions().addAccessSQL(retValue.toString(),
					m_tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
	}
	
	private String getWhereClause()
	{
		String whereClause = "";

		if (lookup == null)
			return "";

		if (lookup.getZoomQuery() != null)
			whereClause = lookup.getZoomQuery().getWhereClause();

		String validation = lookup.getValidation();

		if (validation == null)
			validation = "";

		if (whereClause.length() == 0)
			whereClause = validation;
		else if (validation.length() > 0)
			whereClause += " AND (" + validation + ")";

		//	log.finest("ZoomQuery=" + (lookup.getZoomQuery()==null ? "" : lookup.getZoomQuery().getWhereClause())
	//		+ ", Validation=" + lookup.getValidation());

		if (whereClause.indexOf('@') != -1)
		{
			String validated = Env.parseContext(Env.getCtx(), lookup.getWindowNo(), whereClause, false);

			if (validated.length() == 0)
				log.severe(getColumnName() + " - Cannot Parse=" + whereClause);
			else
			{
				log.fine(getColumnName() + " - Parsed: " + validated);
				return validated;
			}
		}
		return whereClause;
	}	//	getWhereClause


	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

	public void valueChange(ValueChangeEvent evt)
	{
        if ("zoom".equals(evt.getPropertyName()))
        {
            actionZoom(evt.getNewValue());
        }
        else
        {
        	if (evt.getNewValue() != null)
			{
				actionCombo(evt.getNewValue());
			}
        }

	}

	/**
	 * @param windowNo
	 * @return WSearchEditor
	 */
	public static WSearchEditor createBPartner(int windowNo) {
		int AD_Column_ID = 3499;    //  C_Invoice.C_BPartner_ID
		try
		{
			Lookup lookup = MLookupFactory.get (Env.getCtx(), windowNo, 
				0, AD_Column_ID, DisplayType.Search);
			return new WSearchEditor ("C_BPartner_ID", false, false, true, lookup);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		return null;
	}

	/**
	 * @param windowNo
	 * @return WSearchEditor 
	 */
	public static WSearchEditor createProduct(int windowNo) {
		int AD_Column_ID = 3840;    //  C_InvoiceLine.M_Product_ID
		try
		{
			Lookup lookup = MLookupFactory.get (Env.getCtx(), windowNo, 0, 
				AD_Column_ID, DisplayType.Search);
			return new WSearchEditor("M_Product_ID", false, false, true, lookup);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		return null;
	}
}
