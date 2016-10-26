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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;
import org.compiere.apps.search.history.impl.InvoiceHistory;
import org.compiere.apps.search.history.impl.InvoiceHistoryContext;
import org.compiere.grid.ed.VComboBox;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MDocType;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextArea;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.jdesktop.swingx.JXTaskPane;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Search Product and return selection
 * 
 * @author Jorg Janke
 * @version $Id: InfoProduct.java,v 1.4 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Bogdan Ioan, SC ARHIPAC SERVICE SRL <li>FR [ 2012362 ] Info Product: Add Product Category
 * 
 * @deprecated not used anymore but de.metas.adempiere.gui.search.InfoProduct is used instead
 */
@Deprecated
public final class InfoProduct extends Info implements ActionListener,
		ChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2674502713710863413L;

	// metas: start: c.ghita@metas.ro
	private final String LOAD_ALL_RECORDS_INFOPRODUCT = "LOAD_ALL_RECORDS_INFOPRODUCT";
	private boolean loadResult = false;

	// metas: end: c.ghita@metas.ro

	/**
	 * Standard Constructor
	 * 
	 * @param frame
	 *            frame
	 * @param modal
	 *            modal
	 * @param WindowNo
	 *            window no
	 * @param M_Warehouse_ID
	 *            warehouse
	 * @param M_PriceList_ID
	 *            price list
	 * @param value
	 *            Query Value or Name if enclosed in @
	 * @param multiSelection
	 *            multiple selections
	 * @param whereClause
	 *            where clause
	 */
	public InfoProduct(Frame frame, boolean modal, int WindowNo,
			int M_Warehouse_ID, int M_PriceList_ID, String value,
			boolean multiSelection, String whereClause)
	{
		super(frame, modal, WindowNo, "p", "M_Product_ID", multiSelection,
				whereClause);
		log.info(value + ", Wh=" + M_Warehouse_ID + ", PL=" + M_PriceList_ID
				+ ", WHERE=" + whereClause);

		loadResult = MSysConfig.getBooleanValue(LOAD_ALL_RECORDS_INFOPRODUCT, false); // metas: c.ghita@metas.ro

		setTitle(Msg.getMsg(Env.getCtx(), "InfoProduct"));
		//
		statInit();
		initInfo(value, M_Warehouse_ID, M_PriceList_ID);
		m_C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), WindowNo,
				"C_BPartner_ID");

		//
		int no = p_table.getRowCount();
		setStatusLine(Integer.toString(no) + " "
				+ Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		// AutoQuery
		if (value != null && value.length() > 0)
			executeQueryOnInit();
		p_loadedOK = true;
		// Focus
		fieldValue.requestFocus();

		// Begin - fer_luck @ centuryon
		mWindowNo = WindowNo;
		// End - fer_luck @ centuryon
		AEnv.positionCenterWindow(frame, getWindow());
		if (loadResult) // metas: c.ghita@metas.ro
			executeQueryOnInit(); // metas-2009_0021_AP1_CR046
	} // InfoProduct

	/** SQL From */
	private static final String s_productFrom = "M_Product p"
			+ " LEFT OUTER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID AND pr.IsActive='Y')"
			// metas: including plv and currency to display it name in the main table
			+ " LEFT OUTER JOIN M_PriceList_Version plv ON (pr.M_PriceList_Version_ID=plv.M_PriceList_Version_ID AND plv.IsActive='Y')"
			+ " LEFT OUTER JOIN M_PriceList pl ON (plv.M_PriceList_ID=pl.M_PriceList_ID)"
			+ " LEFT OUTER JOIN C_Currency c ON (pl.C_Currency_ID=c.C_Currency_ID)"
			// metas end
			+ " LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID)"
			+ " LEFT OUTER JOIN M_Product_PO ppo ON (p.M_Product_ID=ppo.M_Product_ID and ppo.IsCurrentVendor='Y')"
			+ " LEFT OUTER JOIN C_BPartner bp ON (ppo.C_BPartner_ID=bp.C_BPartner_ID)";

	/** Array of Column Info */
	private static Info_Column[] s_productLayout = null;
	private static int INDEX_NAME = 0;
	private static int INDEX_PATTRIBUTE = 0;

	//
	private CLabel labelValue = new CLabel();
	private CTextField fieldValue = new CTextField(10);
	private CLabel labelName = new CLabel();
	private CTextField fieldName = new CTextField(10);
	private CLabel labelUPC = new CLabel();
	private CTextField fieldUPC = new CTextField(10);
	private CLabel labelSKU = new CLabel();
	private CTextField fieldSKU = new CTextField(10);
	private CLabel labelPriceList = new CLabel();
	private VComboBox pickPriceList = new VComboBox();
	private CLabel labelWarehouse = new CLabel();
	private VComboBox pickWarehouse = new VComboBox();
	private CLabel labelVendor = new CLabel();
	private CTextField fieldVendor = new CTextField(10);
	private CLabel labelProductCategory = new CLabel();
	private VComboBox pickProductCategory = new VComboBox();

	// Begin - fer_luck @ centuryon
	private CTextArea fieldDescription = new CTextArea();
	JXTaskPane warehouseStockPanel = new JXTaskPane();
	CPanel tablePanel = new CPanel();
	MiniTable warehouseTbl = new MiniTable();
	String m_sqlWarehouse;
	MiniTable substituteTbl = new MiniTable();
	String m_sqlSubstitute;
	MiniTable relatedTbl = new MiniTable();
	String m_sqlRelated;
	// Available to Promise Tab
	private MiniTable m_tableAtp = new MiniTable();
	private DefaultTableModel m_modelAtp = null;
	private int m_M_Product_ID = 0;
	int mWindowNo = 0;
	// End - fer_luck @ centuryon

	/** Search Button */
	private CButton m_InfoPAttributeButton = new CButton(Images.getImageIcon2("PAttribute16"));
	/** Instance Button */
	private CButton m_PAttributeButton = null;
	/** ASI */
	private int m_M_AttributeSetInstance_ID = -1;
	/** Locator */
	private int m_M_Locator_ID = 0;

	private String m_pAttributeWhere = null;
	private int m_C_BPartner_ID = 0;

	/**
	 * Static Setup - add fields to parameterPanel
	 */
	private void statInit()
	{
		labelValue.setText(Msg.getMsg(Env.getCtx(), "Value"));
		fieldValue.setBackground(AdempierePLAF.getInfoBackground());
		fieldValue.addActionListener(this);

		labelName.setText(Msg.getMsg(Env.getCtx(), "Name"));
		fieldName.setBackground(AdempierePLAF.getInfoBackground());
		fieldName.addActionListener(this);

		labelUPC.setText(Msg.translate(Env.getCtx(), "UPC"));
		fieldUPC.setBackground(AdempierePLAF.getInfoBackground());
		fieldUPC.addActionListener(this);

		labelSKU.setText(Msg.translate(Env.getCtx(), "SKU"));
		fieldSKU.setBackground(AdempierePLAF.getInfoBackground());
		fieldSKU.addActionListener(this);

		labelWarehouse.setText(Msg.getMsg(Env.getCtx(), "Warehouse"));
		pickWarehouse.setBackground(AdempierePLAF.getInfoBackground());

		labelPriceList.setText(Msg.getMsg(Env.getCtx(), "PriceListVersion"));
		pickPriceList.setBackground(AdempierePLAF.getInfoBackground());

		labelProductCategory.setText(Msg.translate(Env.getCtx(),
				"M_Product_Category_ID"));
		pickProductCategory.setBackground(AdempierePLAF.getInfoBackground());

		m_InfoPAttributeButton.setMargin(new Insets(2, 2, 2, 2));
		m_InfoPAttributeButton.setToolTipText(Msg.getMsg(Env.getCtx(),
				"InfoPAttribute"));
		m_InfoPAttributeButton.addActionListener(this);

		labelVendor.setText(Msg.translate(Env.getCtx(), "Vendor"));
		fieldVendor.setBackground(AdempierePLAF.getInfoBackground());
		fieldVendor.addActionListener(this);

		// Line 1
		parameterPanel.setLayout(new ALayout());
		parameterPanel.add(labelValue, new ALayoutConstraint(0, 0));
		parameterPanel.add(fieldValue, null);
		parameterPanel.add(labelUPC, null);
		parameterPanel.add(fieldUPC, null);
		parameterPanel.add(labelWarehouse, null);
		parameterPanel.add(pickWarehouse, null);
		parameterPanel.add(m_InfoPAttributeButton);
		// Line 2
		parameterPanel.add(labelName, new ALayoutConstraint(1, 0));
		parameterPanel.add(fieldName, null);
		parameterPanel.add(labelSKU, null);
		parameterPanel.add(fieldSKU, null);
		parameterPanel.add(labelVendor, null);
		parameterPanel.add(fieldVendor, null);

		// Line 3
		parameterPanel.add(labelPriceList, new ALayoutConstraint(2, 0));
		parameterPanel.add(pickPriceList, null);
		parameterPanel.add(labelProductCategory, null);
		parameterPanel.add(pickProductCategory, null);

		// Product Attribute Instance
		m_PAttributeButton = ConfirmPanel.createPAttributeButton(true);
		confirmPanel.addButton(m_PAttributeButton);
		m_PAttributeButton.addActionListener(this);
		m_PAttributeButton.setEnabled(false);

		// Begin - fer_luck @ centuryon
		// add taskpane
		fieldDescription.setBackground(AdempierePLAF.getInfoBackground());
		fieldDescription.setEditable(false);
		fieldDescription.setPreferredSize(new Dimension(INFO_WIDTH - 100, 100));

		warehouseStockPanel.setTitle(Msg.translate(Env.getCtx(), "WarehouseStock"));
		// NOTE: see org.adempiere.plaf.AdempiereLookAndFeel
		// warehouseStockPanel.setUI(new AdempiereTaskPaneUI());
		// warehouseStockPanel.getContentPane().setBackground(new ColorUIResource(251, 248, 241));
		// warehouseStockPanel.getContentPane().setForeground(new ColorUIResource(251, 0, 0));

		ColumnInfo[] s_layoutWarehouse = new ColumnInfo[] {
				new ColumnInfo(Msg.translate(Env.getCtx(), "Warehouse"),
						"Warehouse", String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"),
						"sum(QtyAvailable)", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"),
						"sum(QtyOnHand)", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"),
						"sum(QtyReserved)", Double.class) };
		/** From Clause */
		String s_sqlFrom = " M_PRODUCT_STOCK_V ";
		/** Where Clause */
		String s_sqlWhere = "Value = ?";
		m_sqlWarehouse = warehouseTbl.prepareTable(s_layoutWarehouse,
				s_sqlFrom, s_sqlWhere, false, "M_PRODUCT_STOCK_V");
		m_sqlWarehouse += " Group By Warehouse, documentnote ";
		warehouseTbl.setRowSelectionAllowed(true);
		warehouseTbl.setMultiSelection(false);
		warehouseTbl.addMouseListener(this);
		warehouseTbl.getSelectionModel().addListSelectionListener(this);
		warehouseTbl.setShowTotals(true);
		warehouseTbl.autoSize();

		ColumnInfo[] s_layoutSubstitute = new ColumnInfo[] {
				new ColumnInfo(Msg.translate(Env.getCtx(), "Warehouse"),
						"orgname", String.class),
				new ColumnInfo(
						Msg.translate(Env.getCtx(), "Value"),
						"(Select Value from M_Product p where p.M_Product_ID=M_PRODUCT_SUBSTITUTERELATED_V.Substitute_ID)",
						String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name",
						String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"),
						"QtyAvailable", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"),
						"QtyOnHand", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"),
						"QtyReserved", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "PriceStd"),
						"PriceStd", Double.class) };
		s_sqlFrom = "M_PRODUCT_SUBSTITUTERELATED_V";
		s_sqlWhere = "M_Product_ID = ? AND M_PriceList_Version_ID = ? and RowType = 'S'";
		m_sqlSubstitute = substituteTbl.prepareTable(s_layoutSubstitute,
				s_sqlFrom, s_sqlWhere, false, "M_PRODUCT_SUBSTITUTERELATED_V");
		substituteTbl.setRowSelectionAllowed(false);
		substituteTbl.setMultiSelection(false);
		substituteTbl.addMouseListener(this);
		substituteTbl.getSelectionModel().addListSelectionListener(this);
		substituteTbl.autoSize();

		ColumnInfo[] s_layoutRelated = new ColumnInfo[] {
				new ColumnInfo(Msg.translate(Env.getCtx(), "Warehouse"),
						"orgname", String.class),
				new ColumnInfo(
						Msg.translate(Env.getCtx(), "Value"),
						"(Select Value from M_Product p where p.M_Product_ID=M_PRODUCT_SUBSTITUTERELATED_V.Substitute_ID)",
						String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name",
						String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"),
						"QtyAvailable", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"),
						"QtyOnHand", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"),
						"QtyReserved", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "PriceStd"),
						"PriceStd", Double.class) };
		s_sqlFrom = "M_PRODUCT_SUBSTITUTERELATED_V";
		s_sqlWhere = "M_Product_ID = ? AND M_PriceList_Version_ID = ? and RowType = 'R'";
		m_sqlRelated = relatedTbl.prepareTable(s_layoutRelated, s_sqlFrom,
				s_sqlWhere, false, "M_PRODUCT_SUBSTITUTERELATED_V");
		relatedTbl.setRowSelectionAllowed(false);
		relatedTbl.setMultiSelection(false);
		relatedTbl.addMouseListener(this);
		relatedTbl.getSelectionModel().addListSelectionListener(this);
		relatedTbl.autoSize();

		// Available to Promise Tab
		m_tableAtp.setRowSelectionAllowed(false);
		m_tableAtp.setMultiSelection(false);

		CTabbedPane jTab = new CTabbedPane();
		jTab.addTab(Msg.translate(Env.getCtx(), "Warehouse"), new JScrollPane(
				warehouseTbl));
		jTab.setPreferredSize(new Dimension(INFO_WIDTH, 105));
		jTab.addTab(Msg.translate(Env.getCtx(), "Description"),
				new JScrollPane(fieldDescription));
		jTab.addTab(Msg.translate(Env.getCtx(), "Substitute_ID"),
				new JScrollPane(substituteTbl));
		jTab.addTab(Msg.translate(Env.getCtx(), "RelatedProduct_ID"),
				new JScrollPane(relatedTbl));
		jTab.addTab(Msg.getMsg(Env.getCtx(), "ATP"),
				new JScrollPane(m_tableAtp));
		jTab.addChangeListener(this);
		tablePanel.setPreferredSize(new Dimension(INFO_WIDTH, 110));
		tablePanel.add(jTab);

		warehouseStockPanel.setCollapsed(true);
		warehouseStockPanel.add(tablePanel);
		this.addonPanel.add(warehouseStockPanel);

		this.p_table.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent ke)
			{
				int row = ((MiniTable)ke.getSource()).getSelectedRow();
				refresh(((MiniTable)ke.getSource()).getValueAt(row, 2),
						new BigDecimal(pickWarehouse.getValue().toString())
								.intValue(), new BigDecimal(pickPriceList
								.getValue().toString()).intValue());
				warehouseStockPanel.setCollapsed(false);
			}
		});

		this.p_table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent me)
			{
				int row = ((MiniTable)me.getSource()).getSelectedRow();
				refresh(((MiniTable)me.getSource()).getValueAt(row, 2),
						new BigDecimal(pickWarehouse.getValue().toString())
								.intValue(), new BigDecimal(pickPriceList
								.getValue().toString()).intValue());
				warehouseStockPanel.setCollapsed(false);
			}
		});
		// End - fer_luck @ centuryon
	} // statInit

	// Begin - fer_luck @ centuryon
	/**
	 * Refresh Query
	 */
	private void refresh(Object obj, int M_Warehouse_ID,
			int M_PriceList_Version_ID)
	{
		// int M_Product_ID = 0;
		String sql = m_sqlWarehouse;
		// Add description to the query
		sql = sql.replace(" FROM", ", DocumentNote FROM");
		log.trace(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, (String)obj);
			rs = pstmt.executeQuery();
			fieldDescription.setText("");
			warehouseTbl.loadTable(rs);
			rs = pstmt.executeQuery();
			if (rs.next())
				if (rs.getString("DocumentNote") != null)
					fieldDescription.setText(rs.getString("DocumentNote"));
		}
		catch (Exception e)
		{
			log.warn(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		try
		{
			sql = "SELECT M_Product_ID FROM M_Product WHERE Value = ?";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, (String)obj);
			rs = pstmt.executeQuery();
			if (rs.next())
				m_M_Product_ID = rs.getInt(1);
		}
		catch (Exception e)
		{
			log.warn(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		sql = m_sqlSubstitute;
		log.trace(sql);
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, M_PriceList_Version_ID);
			rs = pstmt.executeQuery();
			substituteTbl.loadTable(rs);
			rs.close();
		}
		catch (Exception e)
		{
			log.warn(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		sql = m_sqlRelated;
		log.trace(sql);
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, M_PriceList_Version_ID);
			rs = pstmt.executeQuery();
			relatedTbl.loadTable(rs);
			rs.close();
		}
		catch (Exception e)
		{
			log.warn(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		initAtpTab(M_Warehouse_ID);
	} // refresh

	// End - fer_luck @ centuryon

	/**
	 * Dynamic Init
	 * 
	 * @param value
	 *            value
	 * @param M_Warehouse_ID
	 *            warehouse
	 * @param M_PriceList_ID
	 *            price list
	 */
	private void initInfo(String value, int M_Warehouse_ID, int M_PriceList_ID)
	{
		// Pick init
		fillPicks(M_PriceList_ID);
		int M_PriceList_Version_ID = findPLV(M_PriceList_ID);
		// Set Value or Name
		if (value.startsWith("@") && value.endsWith("@"))
			fieldName.setText(value.substring(1, value.length() - 1));
		else
			fieldValue.setText(value);
		// Set Warehouse
		if (M_Warehouse_ID == 0)
			M_Warehouse_ID = Env.getContextAsInt(Env.getCtx(),
					"#M_Warehouse_ID");
		if (M_Warehouse_ID != 0)
			setWarehouse(M_Warehouse_ID);
		// Set PriceList Version
		if (M_PriceList_Version_ID != 0)
			setPriceListVersion(M_PriceList_Version_ID);

		// Create Grid
		StringBuffer where = new StringBuffer();
		where.append("p.IsActive='Y'");
		if (M_Warehouse_ID != 0)
			where.append(" AND p.IsSummary='N'");
		// dynamic Where Clause
		if (p_whereClause != null && p_whereClause.length() > 0)
			where.append(" AND ") // replace fully qualified name with alias
					.append(Util.replace(p_whereClause, "M_Product.", "p."));
		//
		prepareTable(getProductLayout(), s_productFrom, where.toString(),
				"QtyAvailable DESC, Margin DESC");
		p_table.setShowTotals(false);
		//
		pickWarehouse.addActionListener(this);
		pickPriceList.addActionListener(this);
		pickProductCategory.addActionListener(this);
	} // initInfo

	/**
	 * Fill Picks with values
	 * 
	 * @param M_PriceList_ID
	 *            price list
	 */
	private void fillPicks(int M_PriceList_ID)
	{
		// Price List
		String SQL = "SELECT M_PriceList_Version.M_PriceList_Version_ID,"
				+ " M_PriceList_Version.Name || ' (' || c.Iso_Code || ')' AS ValueName "
				+ "FROM M_PriceList_Version, M_PriceList pl, M_PricingSystem s, C_Currency c "
				+ "WHERE M_PriceList_Version.M_PriceList_ID=pl.M_PriceList_ID"
				+ " AND pl.C_Currency_ID=c.C_Currency_ID"
				+ " AND s.M_PricingSystem_ID=pl.M_PricingSystem_ID"
				+ " AND M_PriceList_Version.IsActive='Y' AND pl.IsActive='Y' AND s.IsActive='Y'"
				+ " AND s.IsSubscriptionOnly='N'"
		// metas: just an idea, but doesn't work as expected
		// + " AND pl.IsSOPriceList='"
		// + (Env.isSOTrx(Env.getCtx())?"Y":"N") + "'"
		;
		// Same PL currency as original one
		if (M_PriceList_ID != 0)
			SQL += " AND EXISTS (SELECT * FROM M_PriceList xp WHERE xp.M_PriceList_ID="
					+ M_PriceList_ID
					+ " AND pl.C_Currency_ID=xp.C_Currency_ID)";
		// Add Access & Order
		SQL = Env.getUserRolePermissions().addAccessSQL(SQL, "M_PriceList_Version", true,
				false) // fully qualidfied - RO
				+ " ORDER BY M_PriceList_Version.Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pickPriceList.addItem(new KeyNamePair(0, ""));
			pstmt = DB.prepareStatement(SQL, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair(rs.getInt(1), rs.getString(2));
				pickPriceList.addItem(kn);
			}
			DB.close(rs, pstmt);

			// Warehouse
			SQL = Env.getUserRolePermissions().addAccessSQL(
					"SELECT M_Warehouse_ID, Value || ' - ' || Name AS ValueName "
							+ "FROM M_Warehouse " + "WHERE IsActive='Y'",
					"M_Warehouse", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO)
					+ " ORDER BY Value";
			pickWarehouse.addItem(new KeyNamePair(0, ""));
			pstmt = DB.prepareStatement(SQL, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair(rs.getInt("M_Warehouse_ID"),
						rs.getString("ValueName"));
				pickWarehouse.addItem(kn);
			}
			DB.close(rs, pstmt);

			// Product Category
			SQL = Env
					.getUserRolePermissions()
					.addAccessSQL(
							"SELECT M_Product_Category_ID, Value || ' - ' || Name FROM M_Product_Category WHERE IsActive='Y'",
							"M_Product_Category", IUserRolePermissions.SQL_NOTQUALIFIED,
							IUserRolePermissions.SQL_RO)
					+ " ORDER BY Value";
			for (KeyNamePair kn : DB.getKeyNamePairs(SQL, true))
			{
				pickProductCategory.addItem(kn);
			}
		}
		catch (SQLException e)
		{
			log.error(SQL, e);
			setStatusLine(e.getLocalizedMessage(), true);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	} // fillPicks

	/**
	 * Set Warehouse
	 * 
	 * @param M_Warehouse_ID
	 *            warehouse
	 */
	private void setWarehouse(int M_Warehouse_ID)
	{
		for (int i = 0; i < pickWarehouse.getItemCount(); i++)
		{
			KeyNamePair kn = (KeyNamePair)pickWarehouse.getItemAt(i);
			if (kn.getKey() == M_Warehouse_ID)
			{
				pickWarehouse.setSelectedIndex(i);
				return;
			}
		}
	} // setWarehouse

	/**
	 * Set PriceList
	 * 
	 * @param M_PriceList_Version_ID
	 *            price list
	 */
	private void setPriceListVersion(int M_PriceList_Version_ID)
	{
		log.info("M_PriceList_Version_ID=" + M_PriceList_Version_ID);
		for (int i = 0; i < pickPriceList.getItemCount(); i++)
		{
			KeyNamePair kn = (KeyNamePair)pickPriceList.getItemAt(i);
			if (kn.getKey() == M_PriceList_Version_ID)
			{
				pickPriceList.setSelectedIndex(i);
				return;
			}
		}
		log.debug("NOT found");
	} // setPriceList

	/**
	 * Find Price List Version and update context
	 * 
	 * @param M_PriceList_ID
	 *            price list
	 * @return M_PriceList_Version_ID price list version
	 */
	private int findPLV(int M_PriceList_ID)
	{
		final int p_WindowNo = getWindowNo();
		
		Timestamp priceDate = null;
		// Sales Order Date
		String dateStr = Env.getContext(Env.getCtx(), p_WindowNo, "DateOrdered");
		if (dateStr != null && dateStr.length() > 0)
			priceDate = Env.getContextAsDate(Env.getCtx(), p_WindowNo, "DateOrdered");
		else
		// Invoice Date
		{
			dateStr = Env.getContext(Env.getCtx(), p_WindowNo, "DateInvoiced");
			if (dateStr != null && dateStr.length() > 0)
				priceDate = Env.getContextAsDate(Env.getCtx(), p_WindowNo, "DateInvoiced");
		}
		// Today
		if (priceDate == null)
			priceDate = new Timestamp(System.currentTimeMillis());
		//
		log.info("M_PriceList_ID=" + M_PriceList_ID + " - " + priceDate);
		int retValue = 0;
		String sql = "SELECT plv.M_PriceList_Version_ID, plv.ValidFrom "
				+ "FROM M_PriceList pl, M_PriceList_Version plv "
				+ "WHERE pl.M_PriceList_ID=plv.M_PriceList_ID"
				+ " AND plv.IsActive='Y'" + " AND pl.M_PriceList_ID=? " // 1
				+ "ORDER BY plv.ValidFrom DESC";
		// find newest one
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_PriceList_ID);
			rs = pstmt.executeQuery();
			while (rs.next() && retValue == 0)
			{
				Timestamp plDate = rs.getTimestamp(2);
				if (!priceDate.before(plDate))
					retValue = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		Env.setContext(Env.getCtx(), p_WindowNo, "M_PriceList_Version_ID",
				retValue);
		return retValue;
	} // findPLV

	/**************************************************************************
	 * Construct SQL Where Clause and define parameters (setParameters needs to set parameters) Includes first AND
	 * 
	 * @return SQL WHERE clause
	 */
	@Override
	protected String getSQLWhere()
	{
		StringBuffer where = new StringBuffer();

		// Optional PLV
		int M_PriceList_Version_ID = 0;
		KeyNamePair pl = (KeyNamePair)pickPriceList.getSelectedItem();
		if (pl != null)
			M_PriceList_Version_ID = pl.getKey();
		if (M_PriceList_Version_ID != 0)
			where.append(" AND pr.M_PriceList_Version_ID=?");

		// Optional Product Category
		if (getM_Product_Category_ID() > 0)
		{
			where.append(" AND p.M_Product_Category_ID=?");
		}

		// Product Attribute Search
		if (m_pAttributeWhere != null)
		{
			where.append(m_pAttributeWhere);
			return where.toString();
		}

		// => Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
			where.append(" AND UPPER(p.Value) LIKE ?");

		// => Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
			where.append(" AND UPPER(p.Name) LIKE ?");

		// => UPC
		String upc = fieldUPC.getText().toUpperCase();
		if (!(upc.equals("") || upc.equals("%")))
			where.append(" AND UPPER(p.UPC) LIKE ?");

		// => SKU
		String sku = fieldSKU.getText().toUpperCase();
		if (!(sku.equals("") || sku.equals("%")))
			where.append(" AND UPPER(p.SKU) LIKE ?");
		// => Vendor
		String vendor = fieldVendor.getText().toUpperCase();
		if (!(vendor.equals("") || vendor.equals("%")))
			where
					.append(" AND UPPER(bp.Name) LIKE ? AND ppo.IsCurrentVendor='Y'");

		return where.toString();
	} // getSQLWhere

	/**
	 * Set Parameters for Query (as defined in getSQLWhere)
	 * 
	 * @param pstmt
	 *            pstmt
	 * @param forCount
	 *            for counting records
	 * @throws SQLException
	 */
	@Override
	protected void setParameters(PreparedStatement pstmt, boolean forCount)
			throws SQLException
	{
		int index = 1;

		// => Warehouse
		int M_Warehouse_ID = 0;
		KeyNamePair wh = (KeyNamePair)pickWarehouse.getSelectedItem();
		if (wh != null)
			M_Warehouse_ID = wh.getKey();
		if (!forCount) // parameters in select
		{
			for (int i = 0; i < p_layout.length; i++)
			{
				if (p_layout[i].getColSQL().indexOf('?') != -1)
					pstmt.setInt(index++, M_Warehouse_ID);
			}
		}
		log
				.debug("M_Warehouse_ID=" + M_Warehouse_ID + " (" + (index - 1)
						+ "*)");

		// => PriceList
		int M_PriceList_Version_ID = 0;
		KeyNamePair pl = (KeyNamePair)pickPriceList.getSelectedItem();
		if (pl != null)
			M_PriceList_Version_ID = pl.getKey();
		if (M_PriceList_Version_ID != 0)
		{
			pstmt.setInt(index++, M_PriceList_Version_ID);
			log.debug("M_PriceList_Version_ID=" + M_PriceList_Version_ID);
		}
		// => Product Category
		int M_Product_Category_ID = getM_Product_Category_ID();
		if (M_Product_Category_ID > 0)
		{
			pstmt.setInt(index++, M_Product_Category_ID);
			log.debug("M_Product_Category_ID=" + M_Product_Category_ID);
		}
		// Rest of Parameter in Query for Attribute Search
		if (m_pAttributeWhere != null)
			return;

		// => Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
		{
			if (!value.endsWith("%"))
				value += "%";
			pstmt.setString(index++, value);
			log.debug("Value: " + value);
		}

		// => Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
		{
			if (!name.endsWith("%"))
				name += "%";
			pstmt.setString(index++, name);
			log.debug("Name: " + name);
		}

		// => UPC
		String upc = fieldUPC.getText().toUpperCase();
		if (!(upc.equals("") || upc.equals("%")))
		{
			if (!upc.endsWith("%"))
				upc += "%";
			pstmt.setString(index++, upc);
			log.debug("UPC: " + upc);
		}

		// => SKU
		String sku = fieldSKU.getText().toUpperCase();
		if (!(sku.equals("") || sku.equals("%")))
		{
			if (!sku.endsWith("%"))
				sku += "%";
			pstmt.setString(index++, sku);
			log.debug("SKU: " + sku);
		}

		// => Vendor
		String vendor = fieldVendor.getText().toUpperCase();
		if (!(vendor.equals("") || vendor.equals("%")))
		{
			if (!vendor.endsWith("%"))
				vendor += "%";
			pstmt.setString(index++, vendor);
			log.debug("Vendor: " + vendor);
		}

	} // setParameters

	/**************************************************************************
	 * Action Listner
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// don't requery if fieldValue and fieldName are empty
		if ((e.getSource() == pickWarehouse || e.getSource() == pickPriceList)
				&& (fieldValue.getText().length() == 0 && fieldName.getText()
						.length() == 0))
			return;

		// Product Attribute Search
		if (e.getSource().equals(m_InfoPAttributeButton))
		{
			cmd_InfoPAttribute();
			return;
		}
		m_pAttributeWhere = null;

		// Query Product Attribure Instance
		int row = p_table.getSelectedRow();
		if (e.getSource().equals(m_PAttributeButton) && row != -1)
		{
			Integer productInteger = getSelectedRowKey();
			String productName = (String)p_table.getValueAt(row, INDEX_NAME);
			KeyNamePair warehouse = (KeyNamePair)pickWarehouse
					.getSelectedItem();
			if (productInteger == null || productInteger.intValue() == 0
					|| warehouse == null)
				return;
			String title = warehouse.getName() + " - " + productName;
			PAttributeInstance pai = new PAttributeInstance(getWindow(), title,
					warehouse.getKey(), 0, productInteger.intValue(),
					m_C_BPartner_ID);
			m_M_AttributeSetInstance_ID = pai.getM_AttributeSetInstance_ID();
			m_M_Locator_ID = pai.getM_Locator_ID();
			if (m_M_AttributeSetInstance_ID != -1)
				dispose(true);
			return;
		}
		//
		super.actionPerformed(e);
	} // actionPerformed

	/**
	 * Enable PAttribute if row selected/changed
	 */
	@Override
	protected void enableButtons()
	{
		m_M_AttributeSetInstance_ID = -1;
		if (m_PAttributeButton != null)
		{
			int row = p_table.getSelectedRow();
			boolean enabled = false;
			if (row >= 0)
			{
				Object value = p_table.getValueAt(row, INDEX_PATTRIBUTE);
				enabled = Boolean.TRUE.equals(value);
			}
			m_PAttributeButton.setEnabled(enabled);
		}
		super.enableButtons();
	} // enableButtons

	/**
	 * Query per Product Attribute. <code>
	 * 	Available synonyms:
	 * 		M_Product p
	 * 		M_ProductPrice pr
	 * 		M_AttributeSet pa
	 *	</code>
	 */
	private void cmd_InfoPAttribute()
	{
		InfoPAttribute ia = new InfoPAttribute(getWindow());
		m_pAttributeWhere = ia.getWhereClause();
		if (m_pAttributeWhere != null)
			executeQuery();
	} // cmdInfoAttribute

	/**
	 * Show History
	 */
	@Override
	protected void showHistory()
	{
		log.info("");
		Integer M_Product_ID = getSelectedRowKey();
		if (M_Product_ID == null)
			return;
		KeyNamePair kn = (KeyNamePair)pickWarehouse.getSelectedItem();
		int M_Warehouse_ID = kn.getKey();
		int M_AttributeSetInstance_ID = m_M_AttributeSetInstance_ID;
		if (m_M_AttributeSetInstance_ID < -1) // not selected
			M_AttributeSetInstance_ID = 0;
		//

		final int C_BPartner_ID = 0;
		final InvoiceHistoryContext ihCtx = InvoiceHistoryContext.builder()
				.setC_BPartner_ID(C_BPartner_ID)
				.setM_Product_ID(M_Product_ID.intValue())
				.setM_Warehouse_ID(M_Warehouse_ID)
				.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID)
				.build();

		final IInvoiceHistoryTabHandler invoiceHistoryTabHandler = ihCtx.getInvoiceHistoryTabHandler();
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_PRICEHISTORY, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RESERVED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ORDERED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_UNCONFIRMED, true);

		// task 08777: was true; setting to false because currently that tab is a performance nightmare and it's rarely used; TODO re-enable with task 08881
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ATP, false);

		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RECEIVED, false);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_DELIVERED, false);

		//
		InvoiceHistory ih = new InvoiceHistory(ihCtx, getWindow());
		ih.setVisible(true);
		ih = null;
	} // showHistory

	/**
	 * Has History
	 * 
	 * @return true (has history)
	 */
	@Override
	protected boolean hasHistory()
	{
		return true;
	} // hasHistory

	/**
	 * Zoom
	 */
	@Override
	protected void zoom()
	{
		log.info("");
		Integer M_Product_ID = getSelectedRowKey();
		if (M_Product_ID == null)
			return;
		// AEnv.zoom(MProduct.Table_ID, M_Product_ID.intValue(), true); // SO

		MQuery query = new MQuery("M_Product");
		query.addRestriction("M_Product_ID", Operator.EQUAL, M_Product_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("M_Product", true); // SO
		zoom(AD_WindowNo, query);
	} // zoom

	/**
	 * Has Zoom
	 * 
	 * @return (has zoom)
	 */
	@Override
	protected boolean hasZoom()
	{
		return true;
	} // hasZoom

	/**
	 * Customize
	 */
	@Override
	protected void customize()
	{
		log.info("");
	} // customize

	/**
	 * Has Customize
	 * 
	 * @return false (no customize)
	 */
	@Override
	protected boolean hasCustomize()
	{
		return false; // for now
	} // hasCustomize

	/**
	 * Save Selection Settings for PriceList
	 */
	@Override
	protected void saveSelectionDetail()
	{
		// publish for Callout to read
		final int p_WindowNo = getWindowNo();
		Integer ID = getSelectedRowKey();
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "M_Product_ID",
				ID == null ? "0" : ID.toString());
		KeyNamePair kn = (KeyNamePair)pickPriceList.getSelectedItem();
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
				"M_PriceList_Version_ID", kn.getID());
		kn = (KeyNamePair)pickWarehouse.getSelectedItem();
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
				"M_Warehouse_ID", kn.getID());
		//
		if (m_M_AttributeSetInstance_ID == -1) // not selected
		{
			Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
					"M_AttributeSetInstance_ID", "0");
			Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
					"M_Locator_ID", "0");
		}
		else
		{
			Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
					"M_AttributeSetInstance_ID", String
							.valueOf(m_M_AttributeSetInstance_ID));
			Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO,
					"M_Locator_ID", String.valueOf(m_M_Locator_ID));
		}
	} // saveSelectionDetail

	/**
	 * Get Product Layout
	 * 
	 * @return array of Column_Info
	 */
	protected Info_Column[] getProductLayout()
	{
		if (s_productLayout != null)
			return s_productLayout;
		//
		if (s_productLayout == null)
		{
			ArrayList<Info_Column> list = new ArrayList<Info_Column>();
			list.add(new Info_Column(" ", "p.M_Product_ID", IDColumn.class,
					!isMultiSelection()));
			list.add(new Info_Column(Msg
					.translate(Env.getCtx(), "Discontinued").substring(0, 1),
					"p.Discontinued", Boolean.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "Value"),
					"p.Value", String.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "Name"),
					"p.Name", String.class));
//			list.add(new Info_Column(Msg.translate(Env.getCtx(), "CommissionPoints"),
//					"pr.CommissionPoints", BigDecimal.class));
			list.add(new Info_Column(Msg
					.translate(Env.getCtx(), "QtyAvailable"),
					"bomQtyAvailable(p.M_Product_ID,?,0) AS QtyAvailable",
					Double.class, true, true, null));

			// metas: adding PLV to show which price is displayed in case we
			// don't filter for a specific PLV.
			// Note: using strings instead of proper constqants, because their
			// use would make this code more unreadable
			list.add(new Info_Column(Msg.translate(Env.getCtx(),
					"M_PriceList_Version_ID"), "(plv.Name || ' (' || c.Iso_Code || ')') AS PLV_Name",
					String.class, true, true, null));
			// metas: end
			list
					.add(new Info_Column(
							Msg.translate(Env.getCtx(), "PriceList"),
							"bomPriceList(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceList",
							BigDecimal.class));
			list
					.add(new Info_Column(
							Msg.translate(Env.getCtx(), "PriceStd"),
							"bomPriceStd(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceStd",
							BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyOnHand"),
					"bomQtyOnHand(p.M_Product_ID,?,0) AS QtyOnHand",
					Double.class));
			list.add(new Info_Column(
					Msg.translate(Env.getCtx(), "QtyReserved"),
					"bomQtyReserved(p.M_Product_ID,?,0) AS QtyReserved",
					Double.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "QtyOrdered"),
					"bomQtyOrdered(p.M_Product_ID,?,0) AS QtyOrdered",
					Double.class));
			if (isUnconfirmed())
			{
				list
						.add(new Info_Column(
								Msg.translate(Env.getCtx(), "QtyUnconfirmed"),
								"(SELECT SUM(c.TargetQty) FROM M_InOutLineConfirm c INNER JOIN M_InOutLine il ON (c.M_InOutLine_ID=il.M_InOutLine_ID) INNER JOIN M_InOut i ON (il.M_InOut_ID=i.M_InOut_ID) WHERE c.Processed='N' AND i.M_Warehouse_ID=? AND il.M_Product_ID=p.M_Product_ID) AS QtyUnconfirmed",
								Double.class));
				list
						.add(new Info_Column(
								Msg.translate(Env.getCtx(),
										"QtyUnconfirmedMove"),
								"(SELECT SUM(c.TargetQty) FROM M_MovementLineConfirm c INNER JOIN M_MovementLine ml ON (c.M_MovementLine_ID=ml.M_MovementLine_ID) INNER JOIN M_Locator l ON (ml.M_LocatorTo_ID=l.M_Locator_ID) WHERE c.Processed='N' AND l.M_Warehouse_ID=? AND ml.M_Product_ID=p.M_Product_ID) AS QtyUnconfirmedMove",
								Double.class));
			}
			list
					.add(new Info_Column(
							Msg.translate(Env.getCtx(), "Margin"),
							"bomPriceStd(p.M_Product_ID, pr.M_PriceList_Version_ID)-bomPriceLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS Margin",
							BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(), "Vendor"),
					"bp.Name", String.class));
			list
					.add(new Info_Column(
							Msg.translate(Env.getCtx(), "PriceLimit"),
							"bomPriceLimit(p.M_Product_ID, pr.M_PriceList_Version_ID) AS PriceLimit",
							BigDecimal.class));
			list.add(new Info_Column(Msg.translate(Env.getCtx(),
					"IsInstanceAttribute"), "pa.IsInstanceAttribute",
					Boolean.class));
			s_productLayout = new Info_Column[list.size()];
			list.toArray(s_productLayout);
			INDEX_NAME = 3;
			INDEX_PATTRIBUTE = s_productLayout.length - 1; // last item
		}
		return s_productLayout;
	} // getProductLayout

	/**
	 * System has Unconfirmed records
	 * 
	 * @return true if unconfirmed
	 */
	private boolean isUnconfirmed()
	{
		int no = DB.getSQLValue(null,
				"SELECT COUNT(*) FROM M_InOutLineConfirm WHERE AD_Client_ID=?",
				Env.getAD_Client_ID(Env.getCtx()));
		if (no > 0)
			return true;
		no = DB
				.getSQLValue(
						null,
						"SELECT COUNT(*) FROM M_MovementLineConfirm WHERE AD_Client_ID=?",
						Env.getAD_Client_ID(Env.getCtx()));
		return no > 0;
	} // isUnconfirmed

	/**
	 * Tab Changed
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (e.getSource() instanceof CTabbedPane)
		{
			CTabbedPane tab = (CTabbedPane)e.getSource();

			if (tab.getSelectedIndex() == 4 & warehouseTbl.getRowCount() > 0)
			{
				String value = (String)warehouseTbl.getValueAt(warehouseTbl
						.getSelectedRow(), 0);
				int M_Warehouse_ID = DB
						.getSQLValue(
								null,
								"SELECT M_Warehouse_ID FROM M_Warehouse WHERE UPPER(Name) = UPPER(?) AND AD_Client_ID=?",
								new Object[] { value,
										Env.getAD_Client_ID(Env.getCtx()) });
				initAtpTab(M_Warehouse_ID);
			}
		}

	} // stateChanged

	/**
	 * Query ATP
	 */
	private void initAtpTab(int m_M_Warehouse_ID)
	{

		// Header
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyOnHand"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyOrdered"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyReserved"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Locator_ID"));
		columnNames.add(Msg
				.translate(Env.getCtx(), "M_AttributeSetInstance_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "DocumentNo"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));

		// Fill Storage Data
		boolean showDetail = LogManager.isLevelFine();
		String sql = "SELECT s.QtyOnHand, s.QtyReserved, s.QtyOrdered,"
				+ " productAttribute(s.M_AttributeSetInstance_ID), s.M_AttributeSetInstance_ID,";
		if (!showDetail)
			sql = "SELECT SUM(s.QtyOnHand), SUM(s.QtyReserved), SUM(s.QtyOrdered),"
					+ " productAttribute(s.M_AttributeSetInstance_ID), 0,";
		sql += " w.Name, l.Value "
				+ "FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
				+ " INNER JOIN M_Warehouse w ON (l.M_Warehouse_ID=w.M_Warehouse_ID) "
				+ "WHERE M_Product_ID=?";
		if (m_M_Warehouse_ID != 0)
			sql += " AND l.M_Warehouse_ID=?";
		if (m_M_AttributeSetInstance_ID > 0)
			sql += " AND s.M_AttributeSetInstance_ID=?";
		sql += " AND (s.QtyOnHand<>0 OR s.QtyReserved<>0 OR s.QtyOrdered<>0)";
		if (!showDetail)
			sql += " GROUP BY productAttribute(s.M_AttributeSetInstance_ID), w.Name, l.Value";
		sql += " ORDER BY l.Value";

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		double qty = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			if (m_M_AttributeSetInstance_ID > 0)
				pstmt.setInt(3, m_M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(9);
				line.add(null); // Date
				double qtyOnHand = rs.getDouble(1);
				qty += qtyOnHand;
				line.add(new Double(qtyOnHand)); // Qty
				line.add(null); // BPartner
				line.add(new Double(rs.getDouble(3))); // QtyOrdered
				line.add(new Double(rs.getDouble(2))); // QtyReserved
				line.add(rs.getString(7)); // Locator
				String asi = rs.getString(4);
				if (showDetail && (asi == null || asi.length() == 0))
					asi = "{" + rs.getInt(5) + "}";
				line.add(asi); // ASI
				line.add(null); // DocumentNo
				line.add(rs.getString(6)); // Warehouse
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Orders
		sql = "SELECT o.DatePromised, ol.QtyReserved,"
				+ " productAttribute(ol.M_AttributeSetInstance_ID), ol.M_AttributeSetInstance_ID,"
				+ " dt.DocBaseType, bp.Name,"
				+ " dt.PrintName || ' ' || o.DocumentNo As DocumentNo, w.Name "
				+ "FROM C_Order o"
				+ " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID)"
				+ " INNER JOIN C_DocType dt ON (o.C_DocType_ID=dt.C_DocType_ID)"
				+ " INNER JOIN M_Warehouse w ON (ol.M_Warehouse_ID=w.M_Warehouse_ID)"
				+ " INNER JOIN C_BPartner bp  ON (o.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE ol.QtyReserved<>0" + " AND ol.M_Product_ID=?";
		if (m_M_Warehouse_ID != 0)
			sql += " AND ol.M_Warehouse_ID=?";
		if (m_M_AttributeSetInstance_ID > 0)
			sql += " AND ol.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY o.DatePromised";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			if (m_M_AttributeSetInstance_ID > 0)
				pstmt.setInt(3, m_M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(9);
				line.add(rs.getTimestamp(1)); // Date
				double oq = rs.getDouble(2);
				String DocBaseType = rs.getString(5);
				Double qtyReserved = null;
				Double qtyOrdered = null;
				if (MDocType.DOCBASETYPE_PurchaseOrder.equals(DocBaseType))
				{
					qtyOrdered = new Double(oq);
					qty += oq;
				}
				else
				{
					qtyReserved = new Double(oq);
					qty -= oq;
				}
				line.add(new Double(qty)); // Qty
				line.add(rs.getString(6)); // BPartner
				line.add(qtyOrdered); // QtyOrdered
				line.add(qtyReserved); // QtyReserved
				line.add(null); // Locator
				String asi = rs.getString(3);
				if (showDetail && (asi == null || asi.length() == 0))
					asi = "{" + rs.getInt(4) + "}";
				line.add(asi); // ASI
				line.add(rs.getString(7)); // DocumentNo
				line.add(rs.getString(8)); // Warehouse
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Table
		MiniTable table = null;
		m_modelAtp = new DefaultTableModel(data, columnNames);
		m_tableAtp.setModel(m_modelAtp);
		table = m_tableAtp;
		//
		table.setColumnClass(0, Timestamp.class, true); // Date
		table.setColumnClass(1, Double.class, true); // Quantity
		table.setColumnClass(2, String.class, true); // Partner
		table.setColumnClass(3, Double.class, true); // Quantity
		table.setColumnClass(4, Double.class, true); // Quantity
		table.setColumnClass(5, String.class, true); // Locator
		table.setColumnClass(6, String.class, true); // ASI
		table.setColumnClass(7, String.class, true); // DocNo
		table.setColumnClass(8, String.class, true); // Warehouse
		//
		table.autoSize();
	} // initAtpTab

	/**
	 * @return selected product category ID
	 */
	public int getM_Product_Category_ID()
	{
		int M_Product_Category_ID = 0;
		KeyNamePair pc = (KeyNamePair)pickProductCategory.getSelectedItem();
		if (pc != null)
			M_Product_Category_ID = pc.getKey();
		return M_Product_Category_ID;
	}
} // InfoProduct
