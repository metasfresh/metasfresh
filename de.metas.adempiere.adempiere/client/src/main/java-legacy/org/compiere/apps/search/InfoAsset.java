/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.search;

import java.awt.Frame;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Asset Information
 *	
 *  @author Jorg Janke
 *  @version $Id: InfoAsset.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class InfoAsset extends Info
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6014685562933753813L;

	/**
	 *	Standard Constructor

	 * @param frame frame
	 * @param modal modal
	 * @param WindowNo window no
	 * @param A_Asset_ID asset
	 * @param value    Query Value or Name if enclosed in @
	 * @param multiSelection multiple selections
	 * @param whereClause where clause
	 */
	public InfoAsset (Frame frame, boolean modal, int WindowNo,
		int A_Asset_ID, String value,
		boolean multiSelection, String whereClause)
	{
		super (frame, modal, WindowNo, "a", "A_Asset_ID", multiSelection, whereClause);
		log.info(value + ", ID=" + A_Asset_ID + ", WHERE=" + whereClause);
		setTitle(Msg.getMsg(Env.getCtx(), "InfoAsset"));
		//
		statInit();
		initInfo (value, A_Asset_ID, whereClause);
		//
		int no = p_table.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		//	AutoQuery
		if (value != null && value.length() > 0)
			executeQuery();
		p_loadedOK = true;
		//	Focus
	//	fieldValue.requestFocus();

		AEnv.positionCenterWindow(frame, getWindow());
	}	//	InfoProduct

	/** From Clause             */
	private static String s_assetFROM = "A_ASSET a"
		+ " LEFT OUTER JOIN M_Product p ON (a.M_Product_ID=p.M_Product_ID)"
		+ " LEFT OUTER JOIN C_BPartner bp ON (a.C_BPartner_ID=bp.C_BPartner_ID)"
		+ " LEFT OUTER JOIN AD_User u ON (a.AD_User_ID=u.AD_User_ID)";

	/**  Array of Column Info    */
	private static final Info_Column[] s_assetLayout = {
		new Info_Column(" ", "a.A_Asset_ID", IDColumn.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Value"), "a.Value", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Name"), "a.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "M_Product_ID"), "p.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "C_BPartner_ID"), "bp.Name",  String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "AD_User_ID"), "u.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "AssetServiceDate"), "a.AssetServiceDate", Timestamp.class),
		new Info_Column(Msg.translate(Env.getCtx(), "GuaranteeDate"), "a.GuaranteeDate", Timestamp.class),
		new Info_Column(Msg.translate(Env.getCtx(), "VersionNo"), "a.VersionNo", String.class)
	};
	
	//
	private CLabel labelValue = new CLabel();
	private CTextField fieldValue = new CTextField(10);
	private CLabel labelName = new CLabel();
	private CTextField fieldName = new CTextField(10);
	//
	private CLabel lBPartner_ID = new CLabel(Msg.translate(Env.getCtx(), "BPartner"));
	private VLookup fBPartner_ID;
	private CLabel lProduct_ID = new CLabel(Msg.translate(Env.getCtx(), "Product"));
	private VLookup fProduct_ID;
	
	/**
	 *	Static Setup - add fields to parameterPanel
	 */
	private void statInit()
	{
		final int p_WindowNo = getWindowNo();
		
		labelValue.setText(Msg.getMsg(Env.getCtx(), "Value"));
		fieldValue.setBackground(AdempierePLAF.getInfoBackground());
		fieldValue.addActionListener(this);
		labelName.setText(Msg.getMsg(Env.getCtx(), "Name"));
		fieldName.setBackground(AdempierePLAF.getInfoBackground());
		fieldName.addActionListener(this);
		//	From A_Asset.
		fBPartner_ID = new VLookup("C_BPartner_ID", false, false, true,
			MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 8065, DisplayType.Search));
		lBPartner_ID.setLabelFor(fBPartner_ID);
		fBPartner_ID.setBackground(AdempierePLAF.getInfoBackground());
		fProduct_ID = new VLookup("M_Product_ID", false, false, true,
			MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 8047, DisplayType.Search));
		lProduct_ID.setLabelFor(fProduct_ID);
		fProduct_ID.setBackground(AdempierePLAF.getInfoBackground());
		//
		parameterPanel.setLayout(new ALayout());
		//
		parameterPanel.add(labelValue, new ALayoutConstraint(0,0));
		parameterPanel.add(fieldValue, null);
		parameterPanel.add(lBPartner_ID, null);
		parameterPanel.add(fBPartner_ID, null);
		//		
		parameterPanel.add(labelName, new ALayoutConstraint(1,0));
		parameterPanel.add(fieldName, null);
		parameterPanel.add(lProduct_ID, null);
		parameterPanel.add(fProduct_ID, null);
	}	//	statInit

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
		//
		prepareTable(s_assetLayout, s_assetFROM,
			where.toString(),
			"a.Value");

		//  Set Value
		if (value == null)
			value = "%";
		if (!value.endsWith("%"))
			value += "%";
		fieldValue.setText(value);
	}	//	initInfo

	/*************************************************************************/

	/**
	 *	Construct SQL Where Clause and define parameters.
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return WHERE clause
	 */
	@Override
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
		Integer C_BPartner_ID = (Integer)fBPartner_ID.getValue();
		if (C_BPartner_ID != null)
			sql.append (" AND a.C_BPartner_ID=").append(C_BPartner_ID);
		//	M_Product_ID
		Integer M_Product_ID = (Integer)fProduct_ID.getValue();
		if (M_Product_ID != null)
			sql.append (" AND a.M_Product_ID=").append(M_Product_ID);
		//
		return sql.toString();
	}	//	getSQLWhere

	/**
	 *  Set Parameters for Query
	 *  (as defined in getSQLWhere)
	 *
	 * @param pstmt pstmt
	 *  @param forCount for counting records
	 * @throws SQLException
	 */
	@Override
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
			log.debug("Value: " + value);
		}
		//	=> Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
		{
			if (!name.endsWith("%"))
				name += "%";
			pstmt.setString(index++, name);
			log.debug("Name: " + name);
		}
	}	//	setParameters

	/**
	 *  Save Selection Details
	 *  Get Location/Partner Info
	 */
	@Override
	public void saveSelectionDetail()
	{
		int row = p_table.getSelectedRow();
		if (row == -1)
			return;

		//  publish for Callout to read
		Integer ID = getSelectedRowKey();
		Env.setContext(Env.getCtx(), getWindowNo(), Env.TAB_INFO, "A_Asset_ID", ID == null ? "0" : ID.toString());
	}   //  saveSelectionDetail


	/*************************************************************************/

	/**
	 *	Show History
	 */
	@Override
	protected void showHistory()
	{
		log.info( "InfoAsset.showHistory");
	}	//	showHistory

	/**
	 *	Has History
	 *  @return true
	 */
	@Override
	protected boolean hasHistory()
	{
		return false;
	}	//	hasHistory

	/**
	 *	Zoom
	 */
	@Override
	protected void zoom()
	{
		log.info( "InfoAsset.zoom");
		Integer A_Asset_ID = getSelectedRowKey();
		if (A_Asset_ID == null)
			return;
		MQuery query = new MQuery("A_Asset");
		query.addRestriction("A_Asset_ID", Operator.EQUAL, A_Asset_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("A_Asset", true);
		zoom (AD_WindowNo, query);
	}	//	zoom

	/**
	 *	Has Zoom
	 *  @return true
	 */
	@Override
	protected boolean hasZoom()
	{
		return true;
	}	//	hasZoom

	/**
	 *	Customize
	 */
	@Override
	protected void customize()
	{
		log.info( "InfoAsset.customize");
	}	//	customize

	/**
	 *	Has Customize
	 *  @return false
	 */
	@Override
	protected boolean hasCustomize()
	{
		return false;	//	for now
	}	//	hasCustomize

}	//	InfoAsset
