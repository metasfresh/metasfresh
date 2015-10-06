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

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
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
* Based on InfoPayment written by Jorg Janke
*  
* @author 	Niraj Sohun
* 			Aug, 02, 2007
* 
* Zk Port
* @author Elaine
* @version	InfoAsset.java Adempiere Swing UI 3.4.1 
*/

public class InfoAssetPanel extends InfoPanel implements ValueChangeListener, EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3324796198694097770L;

	/** From Clause             */
	private static String s_assetFROM = "A_ASSET a"
		+ " LEFT OUTER JOIN M_Product p ON (a.M_Product_ID=p.M_Product_ID)"
		+ " LEFT OUTER JOIN C_BPartner bp ON (a.C_BPartner_ID=bp.C_BPartner_ID)"
		+ " LEFT OUTER JOIN AD_User u ON (a.AD_User_ID=u.AD_User_ID)";

	/**  Array of Column Info    */
	private static final ColumnInfo[] s_assetLayout = {
		new ColumnInfo(" ", "a.A_Asset_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Value"), "a.Value", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "a.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "M_Product_ID"), "p.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"), "bp.Name",  String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "AD_User_ID"), "u.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "AssetServiceDate"), "a.AssetServiceDate", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "GuaranteeDate"), "a.GuaranteeDate", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "VersionNo"), "a.VersionNo", String.class)
	};

	private Textbox fieldValue = new Textbox();
	private Textbox fieldName = new Textbox();
	
	private WEditor fBPartner_ID;
	private WEditor fProduct_ID;

	private Label labelValue = new Label();
	private Label labelName = new Label();
	
	private Borderlayout layout;

	private Vbox southBody;

	/**
	 *	Standard Constructor
	 * @param WindowNo window no
	 * @param A_Asset_ID asset
	 * @param value    Query Value or Name if enclosed in @
	 * @param multiSelection multiple selections
	 * @param whereClause where clause
	 */
	public InfoAssetPanel(	int WindowNo, int A_Asset_ID, String value,
							boolean multiSelection, String whereClause)
	{
		this(WindowNo, A_Asset_ID, value, multiSelection, whereClause, true);
	}

	/**
	 *	Standard Constructor
	 * @param WindowNo window no
	 * @param A_Asset_ID asset
	 * @param value    Query Value or Name if enclosed in @
	 * @param multiSelection multiple selections
	 * @param whereClause where clause
	 */
	
	public InfoAssetPanel(	int WindowNo, int A_Asset_ID, String value,
							boolean multiSelection, String whereClause, boolean lookup)
	{
		super (WindowNo, "a", "A_Asset_ID", multiSelection, whereClause, lookup);
		
		log.info(value + ", ID=" + A_Asset_ID + ", WHERE=" + whereClause);
		setTitle(Msg.getMsg(Env.getCtx(), "InfoAsset"));

		statInit();
		initInfo(value, A_Asset_ID, whereClause);

		int no = contentPanel.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		
		//	AutoQuery
		if (value != null && value.length() > 0)
			executeQuery();
		
		p_loadedOK = true;
	} // InfoProduct
	
	/**
	 *	Static Setup - add fields to parameterPanel
	 */
	
	private void statInit()
	{
		fieldValue.setWidth("100%");
		fieldName.setWidth("100%");
		
		labelValue.setValue(Msg.getMsg(Env.getCtx(), "Value"));
		fieldValue.addEventListener(Events.ON_CHANGE, this);
		
		labelName.setValue(Msg.getMsg(Env.getCtx(), "Name"));
		fieldName.addEventListener(Events.ON_CANCEL, this);
		// From A_Asset.
		fBPartner_ID = new WSearchEditor(
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 8065, DisplayType.Search), 
				Msg.translate(Env.getCtx(), "C_BPartner_ID"), "", false, false, true);
		fBPartner_ID.addValueChangeListener(this);
		
		fProduct_ID = new WSearchEditor(
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 8047, DisplayType.Search), 
				Msg.translate(Env.getCtx(), "M_Product_ID"), "", false, false, true);
		fProduct_ID.addValueChangeListener(this);
		
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(labelValue.rightAlign());
		row.appendChild(fieldValue);
		row.appendChild(fBPartner_ID.getLabel().rightAlign());
		row.appendChild(fBPartner_ID.getComponent());
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(labelName.rightAlign());
		row.appendChild(fieldName);
		row.appendChild(fProduct_ID.getLabel().rightAlign());
		row.appendChild(fProduct_ID.getComponent());
		
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
		Div div = new Div();
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
	
	private void initInfo (String value, int A_Asset_ID, String whereClause)
	{
		//	Create Grid
		StringBuffer where = new StringBuffer();
		where.append("a.IsActive='Y'");
		
		if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);
		
		prepareTable(s_assetLayout, s_assetFROM, where.toString(), "a.Value");

		//  Set Value
		if (value == null)
			value = "%";
		
		if (!value.endsWith("%"))
			value += "%";
	} // initInfo
	
	/*************************************************************************/
	/**
	 *	Construct SQL Where Clause and define parameters.
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return WHERE clause
	 */
	
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();
	
		//	=> Value
		
		String value = fieldValue.getText().toUpperCase();
		
		if (!(value.equals("") || value.equals("%")))
			sql.append(" AND UPPER(a.Value) LIKE ?");
		
		//	=> Name
		
		String name = fieldName.getText().toUpperCase();
		
		if (!(name.equals("") || name.equals("%")))
			sql.append (" AND UPPER(a.Name) LIKE ?");
		
		//	C_BPartner_ID
		
		Integer C_BPartner_ID = null;
		
		if (fBPartner_ID.getDisplay() != "")
			C_BPartner_ID = (Integer)fBPartner_ID.getValue();
		
		if (C_BPartner_ID != null)
			sql.append (" AND a.C_BPartner_ID=").append(C_BPartner_ID);

		//	M_Product_ID
		
		Integer M_Product_ID = null;
		
		if (fProduct_ID.getDisplay() != "")
			M_Product_ID = (Integer)fProduct_ID.getValue();
		
		if (M_Product_ID != null)
			sql.append (" AND a.M_Product_ID=").append(M_Product_ID);

		return sql.toString();
	} // getSQLWhere

	/**
	 *  Set Parameters for Query
	 *  (as defined in getSQLWhere)
	 *
	 * @param pstmt pstmt
	 * @param forCount for counting records
	 * @throws SQLException
	 */
	
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		
		//	=> Value
		
		String value = fieldValue.getText().toUpperCase();
		
		if (!(value.equals("") || value.equals("%")))
		{
			if (!value.endsWith("%"))
				value += "%";
		
			pstmt.setString(index++, value);
			log.fine("Value: " + value);
		}
		
		//	=> Name
		
		String name = fieldName.getText().toUpperCase();
		
		if (!(name.equals("") || name.equals("%")))
		{
			if (!name.endsWith("%"))
				name += "%";
		
			pstmt.setString(index++, name);
			log.fine("Name: " + name);
		}
	} // setParameters

	/**
	 *  Save Selection Details
	 *  Get Location/Partner Info
	 */
	
	public void saveSelectionDetail()
	{
		int row = contentPanel.getSelectedRow();
		
		if (row == -1)
			return;

		//  publish for Callout to read
		
		Integer ID = getSelectedRowKey();
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "A_Asset_ID", ID == null ? "0" : ID.toString());
	} // saveSelectionDetail

	/*************************************************************************/
	/**
	 *	Show History
	 */

	protected void showHistory()
	{
		log.info( "InfoAsset.showHistory");
	}	//	showHistory

	/**
	 *	Has History
	 *  @return true
	 */
	
	protected boolean hasHistory()
	{
		return false;
	} // hasHistory

	// Elaine 2008/12/16
	/**
	 *	Zoom
	 */
	public void zoom()
	{
		log.info( "InfoAsset.zoom");
		Integer A_Asset_ID = getSelectedRowKey();
		
		if (A_Asset_ID == null)
			return;
		
		MQuery query = new MQuery("A_Asset");
		query.addRestriction("A_Asset_ID", MQuery.EQUAL, A_Asset_ID);
		query.setRecordCount(1);
		
		int AD_WindowNo = getAD_Window_ID("A_Asset", true);
		AEnv.zoom(AD_WindowNo, query);
	} // zoom
	//

	/**
	 *	Has Zoom
	 *  @return true
	 */
	
	protected boolean hasZoom()
	{
		return true;
	} // hasZoom

	/**
	 *	Customize
	 */
	
	protected void customize()
	{
		log.info( "InfoAsset.customize");
	} // customize

	/**
	 *	Has Customize
	 *  @return false
	 */
	
	protected boolean hasCustomize()
	{
		return false; // for now
	} // hasCustomize
	
	public void tableChanged(WTableModelEvent event) 
	{
		
	}
	
	public void valueChange(ValueChangeEvent evt)
	{
		if (fBPartner_ID.equals(evt.getSource()))
		{
	    	fBPartner_ID.setValue(evt.getNewValue());
		}
		
		if (fProduct_ID.equals(evt.getSource()))
		{
			fProduct_ID.setValue(evt.getNewValue());
		}
	}

	@Override
	protected void insertPagingComponent()
    {
		southBody.insertBefore(paging, southBody.getFirstChild());
		layout.invalidate();
	}
}
