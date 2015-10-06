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

package org.adempiere.webui.panel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Datebox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

/**
* Based on InfoAssignment written by Jorg Janke
*  
* @author 	Niraj Sohun
* 			Aug 06, 2007
* 
* Zk Port
* @author Elaine
* @version	InfoAssignment.java Adempiere Swing UI 3.4.1
*/

public class InfoAssignmentPanel extends InfoPanel implements EventListener, ValueChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -935642651768066799L;
	private WEditor fieldResourceType;
	private WEditor fieldResource;
	
	private Button bNew = new Button();
	
	private Datebox fieldFrom = new Datebox();
	private Datebox fieldTo = new Datebox();

	private Label labelFrom = new Label(Msg.translate(Env.getCtx(), "DateFrom"));
	private Label labelTo = new Label(Msg.translate(Env.getCtx(), "DateTo"));
	private Borderlayout layout;
	private Vbox southBody;

	/** From Clause             */
	private static String s_assignmentFROM =
		"S_ResourceAssignment ra, S_ResourceType rt, S_Resource r, C_UOM uom";

	private static String s_assignmentWHERE =
		"ra.IsActive='Y' AND ra.S_Resource_ID=r.S_Resource_ID "
		+ "AND r.S_ResourceType_ID=rt.S_ResourceType_ID AND rt.C_UOM_ID=uom.C_UOM_ID";

	/**  Array of Column Info    */
	private static ColumnInfo[] s_assignmentLayout = {
		new ColumnInfo(" ", "ra.S_ResourceAssignment_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "S_ResourceType_ID"), "rt.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "S_Resource_ID"), "r.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "AssignDateFrom"), "ra.AssignDateFrom", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Qty"), "ra.Qty", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_UOM_ID"), "uom.UOMSymbol", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "AssignDateTo"), "ra.AssignDateTo", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "IsConfirmed"), "ra.IsConfirmed", Boolean.class)
	};

	/**
	 *  Constructor
	 *  
	 *  @param WindowNo WindowNo
	 *  @param  value   Query value Name or Value if contains numbers
	 *  @param multiSelection multiple selection
	 *  @param whereClause where clause
	 */
	public InfoAssignmentPanel (int WindowNo,
		String value, boolean multiSelection, String whereClause)
	{
		this(WindowNo, value, multiSelection, whereClause, true);
	}
	
	/**
	 *  Constructor
	 *
	 *  @param WindowNo WindowNo
	 *  @param  value   Query value Name or Value if contains numbers
	 *  @param multiSelection multiple selection
	 *  @param whereClause where clause
	 */
	public InfoAssignmentPanel (int WindowNo,
		String value, boolean multiSelection, String whereClause, boolean lookup)
	{
		super (WindowNo, "ra", "S_ResourceAssignment_ID",
			multiSelection, whereClause, lookup);
		log.info(value);
		setTitle(Msg.getMsg(Env.getCtx(), "InfoAssignment"));

		if (!initLookups())
			return;
		
		statInit();
		initInfo (value, whereClause);

		int no = contentPanel.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		
		p_loadedOK = true;
	} // InfoAssignmentPanel
	
	/**
	 * 	Initialize Lookups
	 * 	@return true if OK
	 */
	
	private boolean initLookups()
	{
		try
		{
			int AD_Column_ID = 6851; //	S_Resource.S_ResourceType_ID

			fieldResourceType = new WSearchEditor (
					MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir), 
					Msg.translate(Env.getCtx(), "S_ResourceType_ID"), "", false, false, true);
			
			AD_Column_ID = 6826; //	S_ResourceAssignment.S_Resource_ID
			
			fieldResource = new WSearchEditor (
					MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir), 
					Msg.translate(Env.getCtx(), "S_Resource_ID"), "", false, false, true);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "InfoAssignment.initLookup");
			return false;
		}

		bNew.setImage("/images/New16.png");
		
		return true;
	} // initLookups

	/**
	 *	Static Setup - add fields to parameterPanel.
	 *  <pre>
	 * 		ResourceType	Resource	DateTimeFrom	DateTimeTo	New
	 *  </pre>
	 */
	
	private void statInit()
	{
		fieldFrom.setWidth("180px");
		fieldTo.setWidth("180px");
		
		bNew.addEventListener(Events.ON_CLICK, this);
		
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(fieldResourceType.getLabel().rightAlign());
		row.appendChild(fieldResource.getLabel().rightAlign());
		row.appendChild(labelFrom.rightAlign());
		row.appendChild(labelTo.rightAlign());
		row.appendChild(new Label());
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(fieldResourceType.getComponent());
		row.appendChild(fieldResource.getComponent());
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(fieldFrom);
		row.appendChild(div);
		div = new Div();
		div.setAlign("right");
		div.appendChild(fieldTo);
		row.appendChild(div);
		row.appendChild(bNew);
		
		layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        if (!isLookup())
        {
        	layout.setStyle("position: absolute");
        }
        this.appendChild(layout);

        North north = new North();
        layout.appendChild(north);
		north.appendChild(grid);

        Center center = new Center();
		layout.appendChild(center);
		center.setFlex(true);
		div = new Div();
		div.appendChild(contentPanel);
		if (isLookup())
			contentPanel.setWidth("99%");
        else
        	contentPanel.setStyle("width: 99%; margin: 0px auto;");
        contentPanel.setVflex(true);
		div.setStyle("width :100%; height: 100%");
		center.appendChild(div);
        
		South south = new South();
		layout.appendChild(south);
		southBody = new Vbox();
		southBody.setWidth("100%");
		south.appendChild(southBody);
		southBody.appendChild(confirmPanel);
		southBody.appendChild(new Separator());
		southBody.appendChild(statusBar);
	}
	
	/**
	 *	Dynamic Init
	 *  @param value value
	 *  @param whereClause where clause
	 */
	
	private void initInfo(String value, String whereClause)
	{
		//  C_BPartner bp, AD_User c, C_BPartner_Location l, C_Location a

		//	Create Grid
		
		StringBuffer where = new StringBuffer(s_assignmentWHERE);
		
		if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);
		
		prepareTable(s_assignmentLayout, s_assignmentFROM,
			where.toString(), "rt.Name,r.Name,ra.AssignDateFrom");
	} // initInfo
	
	/*************************************************************************/

	/**
	 *  Event Listener
	 *
	 * 	@param e event
	 */
	public void onEvent (Event e)
	{
		//  don't requery if fieldValue and fieldName are empty
		//	return;

		super.onEvent(e);
	} // onEvent

	/*************************************************************************/

	/**
	 *  Get dynamic WHERE part of SQL
	 *	To be overwritten by concrete classes
	 *  @return WHERE clause
	 */
	
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();

		Integer S_ResourceType_ID = (Integer)fieldResourceType.getValue();
		
		if (S_ResourceType_ID != null)
			sql.append(" AND rt.S_ResourceType_ID=").append(S_ResourceType_ID.intValue());

		Integer S_Resource_ID = (Integer)fieldResource.getValue();
		
		if (S_Resource_ID != null)
			sql.append(" AND r.S_Resource_ID=").append(S_Resource_ID.intValue());

		Date f = fieldFrom.getValue();
		Timestamp ts = f != null ? new Timestamp(f.getTime()) : null;
		
		if (ts != null)
			sql.append(" AND TRUNC(ra.AssignDateFrom)>=").append(DB.TO_DATE(ts,false));

		Date t = fieldTo.getValue();
		ts = t != null ? new Timestamp(t.getTime()) : null;

		if (ts != null)
			sql.append(" AND TRUNC(ra.AssignDateTo)<=").append(DB.TO_DATE(ts,false));
		
		return sql.toString();
	} // getSQLWhere

	/**
	 *  Set Parameters for Query
	 *	To be overwritten by concrete classes
	 *  @param pstmt pstmt
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	
	protected void setParameters (PreparedStatement pstmt, boolean forCount) throws SQLException
	{
	}

	/**
	 *  History dialog
	 *	To be overwritten by concrete classes
	 */
	
	protected void showHistory()
	{
	}

	/**
	 *  Has History (false)
	 *	To be overwritten by concrete classes
	 *  @return true if it has history (default false)
	 */
	
	protected boolean hasHistory()
	{
		return false;
	}

	/**
	 *  Customize dialog
	 *	To be overwritten by concrete classes
	 */
	
	protected void customize()
	{
	}

	/**
	 *  Has Customize (false)
	 *	To be overwritten by concrete classes
	 *  @return true if it has customize (default false)
	 */
	
	protected boolean hasCustomize()
	{
		return false;
	}

	/**
	 *  Zoom action
	 *	To be overwritten by concrete classes
	 */
	
	public void zoom()
	{
		if (getSelectedRowKey() != null && getSelectedRowKey() > 0)
		{
			MQuery zoomQuery = new MQuery();   //  ColumnName might be changed in MTab.validateQuery
	        String column = getKeyColumn();
	        //strip off table name, fully qualify name doesn't work when zoom into detail tab
	        if (column.indexOf(".") > 0)
	        	column = column.substring(column.indexOf(".")+1);
	        zoomQuery.addRestriction(column, MQuery.EQUAL, getSelectedRowKey());
	        zoomQuery.setRecordCount(1);
	        zoomQuery.setTableName(column.substring(0, column.length() - 3));
	        
	        AEnv.zoom(236, zoomQuery);
		}
	}

	/**
	 *  Has Zoom (false)
	 *	To be overwritten by concrete classes
	 *  @return true if it has zoom (default false)
	 */
	
	protected boolean hasZoom()
	{
		return true;
	}

	/**
	 *  Save Selection Details
	 *	To be overwritten by concrete classes
	 */
	
	protected void saveSelectionDetail()
	{
	}

	public void valueChange(ValueChangeEvent evt) 
	{
		
	}

	public void tableChanged(WTableModelEvent event) 
	{
	}
		
	@Override
	protected void insertPagingComponent()
    {
		southBody.insertBefore(paging, southBody.getFirstChild());
		layout.invalidate();
	}

}
