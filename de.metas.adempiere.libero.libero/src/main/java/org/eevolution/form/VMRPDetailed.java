/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.form;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel2;
import org.compiere.apps.search.FindHelper;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_Lot;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_Storage;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLot;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.compiere.util.ValueNamePair;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPProductPlanning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPNoteBL;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ASyncProcess;
import de.metas.logging.LogManager;

/**
 * Info MRP
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: VMRPDetailed.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class VMRPDetailed
		extends CPanel
		implements FormPanel2, ActionListener, VetoableChangeListener, ChangeListener, ListSelectionListener, TableModelListener, ASyncProcess
{
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	private final transient IQueryBuilderDAO queryBuilderDAO = Services.get(IQueryBuilderDAO.class);
	private final transient IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
	private final transient IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final long serialVersionUID = 1L;

	/** Qtys Precision: precision used to display quantities in MRP table */
	private int qtyPrecision = sysConfigBL.getIntValue(SYSCONFIG_QtyPrecision, DEFAULT_QtyPrecision);
	private static final String SYSCONFIG_QtyPrecision = "org.eevolution.form.VMRPDetailed.QtyPrecision";
	private static final int DEFAULT_QtyPrecision = 5;

	private static final String COLUMNNAME_CountMRPNotices_All = "CountMRPNotices_All";
	private static final String COLUMNNAME_CountMRPNotices_Error = "CountMRPNotices_Error";
	private static final int INDEX_IDColumn = 0;

	public VMRPDetailed()
	{
		super();

		initLayoutPanels();
	}

	public VMRPDetailed(final int WindowNo, final FormFrame frame, final int productId, final int rangeDays)
	{
		this();

		init(WindowNo, frame);
		setM_Product_ID(productId);
		if (rangeDays > 0)
		{
			setDateLimit(rangeDays);
		}
	}

	private void setDateLimit(final int rangeDays)
	{
		final Timestamp today = SystemTime.asTimestamp();
		fDateTo.setValue(TimeUtil.addDays(today, rangeDays));
		fDateTo.setReadWrite(false);
		fDateFrom.setValue(TimeUtil.addDays(today, -1 * rangeDays));
		fDateFrom.setReadWrite(false);
	}

	/**
	 * Initialize Panel
	 *
	 * @param WindowNo window
	 * @param frame frame
	 */
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(getCtx(), m_WindowNo, I_C_Order.COLUMNNAME_IsSOTrx, "N");

		try
		{
			// UI
			statInit();
			initTable();
			jbInit();
			//
			m_frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			m_frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch (final Exception e)
		{
			log.error("VMRPDetailed.init", e);
		}

		// executeQuery();
	}	// init

	@Override
	public void showFormWindow(int WindowNo, FormFrame frame)
	{
		AEnv.showMaximized(frame);
	}

	// init

	/** Window No */
	private int m_WindowNo = 0;
	/** FormFrame */
	private FormFrame m_frame;
	private final StatusBar statusBar = new StatusBar();
	private final int AD_Client_ID = Env.getAD_Client_ID(getCtx());

	private static Logger log = LogManager.getLogger(VMRPDetailed.class);
	/** Master (owning) Window */
	protected int p_WindowNo;
	/** Key Column Name */
	protected String p_keyColumn;
	/** Enable more than one selection */
	protected boolean p_multiSelection = true;
	/** Initial WHERE Clause */
	protected String p_whereClause = "";

	/** Table */
	protected MiniTable p_table = new MiniTable()
	{
		private static final long serialVersionUID = 5558072738745745149L;

		@Override
		public String getToolTipText(final java.awt.event.MouseEvent event)
		{
			final int modelRow = rowAtPoint(event.getPoint());
			final VMRPDetailedIDColumn mrpColumn = (VMRPDetailedIDColumn)getValueAt(modelRow, INDEX_IDColumn);
			return mrpColumn.getRowTooltipText();
		};
	};
	/** Model Index of Key Column */
	private int m_keyColumnIndex = -1;
	/** OK pressed */
	// private boolean m_ok = false;
	/** Cancel pressed - need to differentiate between OK - Cancel - Exit */
	// private boolean m_cancel = false;
	/** Result IDs */

	/** Layout of Grid */
	protected ColumnInfo[] p_layout;
	/** Main SQL Statement */
	private String m_sqlMain;
	/** Order By Clause */
	private String m_sqlAdd;

	/** Worker */
	private Worker m_worker = null;

	/** Static Layout */
	private final CPanel southPanel = new CPanel();
	private final BorderLayout southLayout = new BorderLayout();
	private final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withRefreshButton(true)
			.withResetButton(true)
			.withCustomizeButton(true)
			.withHistoryButton(true)
			.withZoomButton(true)
			.build();
	protected CPanel parameterPanel = new CPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	//
	private final JPopupMenu popup = new JPopupMenu();
	private final JMenuItem calcMenu = new JMenuItem();

	/** Window Width */
	private static final int INFO_WIDTH = 800;

	// 06777: Introducing new filters

	// Partner
	private final CLabel lPartner_ID = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_C_BPartner_ID));
	private VLookup fPartner_ID;

	// Order
	private final CLabel lOrder_ID = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_C_Order_ID));
	private VLookup fOrder_ID;

	// Order Type
	private final CLabel lOrderType = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_OrderType));
	private VLookup fOrderType;

	// Type MRP search field (Demand/Supply)
	private final CLabel lSearchTypeMRP = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_TypeMRP));
	private VLookup fSearchTypeMRP;

	private final CLabel lProduct_ID = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_M_Product_ID));
	private VLookup fProduct_ID;
	private final CLabel lAttrSetInstance_ID = new CLabel(msgBL.translate(getCtx(), MPPOrder.COLUMNNAME_M_AttributeSetInstance_ID));
	private CButton fAttrSetInstance_ID;
	private final CLabel lResource_ID = new CLabel(msgBL.translate(getCtx(), "PP_Plant_ID"));
	private VLookup fResource_ID;
	private final CLabel lWarehouse_ID = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_M_Warehouse_ID));
	private VLookup fWarehouse_ID;
	private final CLabel lPlanner_ID = new CLabel(msgBL.translate(getCtx(), I_PP_MRP.COLUMNNAME_Planner_ID));
	private VLookup fPlanner_ID;

	// 07952
	// DocumentNo

	private final CLabel lDocumentNo = new CLabel(msgBL.translate(getCtx(), MPPOrder.COLUMNNAME_DocumentNo));

	private VString fDocumentNo = new VString(MPPOrder.COLUMNNAME_DocumentNo, false, false, true, 20, 20, null, null);

	//
	private final CLabel lDateFrom = new CLabel(msgBL.translate(getCtx(), MLot.COLUMNNAME_DateFrom));

	// DueStart Field
	private final VDate fDateFrom = new VDate(MLot.COLUMNNAME_DateFrom, false, false, true, DisplayType.Date, msgBL.translate(getCtx(), MLot.COLUMNNAME_DateFrom))
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(final Object arg0)
		{
			super.setValue(arg0);
			// executeQuery();
		};
	};

	private final CLabel lDateTo = new CLabel(msgBL.translate(getCtx(), MLot.COLUMNNAME_DateTo));
	// DueEnd Field
	private final VDate fDateTo = new VDate(MLot.COLUMNNAME_DateTo, false, false, true, DisplayType.Date, msgBL.translate(getCtx(), MLot.COLUMNNAME_DateTo))
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(final Object arg0)
		{
			super.setValue(arg0);
			// executeQuery();
		};
	};

	private javax.swing.JPanel mainPanel;

	/**
	 * Parameter panel editors
	 */
	private final List<VEditor> editors = new ArrayList<VEditor>();
	private final CLabel lOrderPolicy = new CLabel();
	private final CTextField fOrderPolicy = new CTextField(6);
	private final CLabel lUOM = new CLabel();
	private final CTextField fUOM = new CTextField(5);
	private final CLabel lOrderPeriod = new CLabel();
	private final VNumber fOrderPeriod = new VNumber();
	private final CLabel lTimefence = new CLabel();
	private final VNumber fTimefence = new VNumber();
	private final CLabel lLeadtime = new CLabel();
	private final VNumber fLeadtime = new VNumber();
	// private final CLabel lReplenishMin = new CLabel();
	private final VNumber fReplenishMin = null; // new VNumber(); // we are not showing it
	private final CLabel lQtyMinOrd = new CLabel();
	private final VNumber fQtyMinOrd = new VNumber();
	private final CLabel lQtyMaxOrd = new CLabel();
	private final VNumber fQtyMaxOrd = new VNumber();
	private final CLabel lQtyOrderPacks = new CLabel();
	private final VNumber fQtyOrderPacks = new VNumber();
	private final CLabel lOrderQty = new CLabel();
	private final VNumber fOrderQty = new VNumber();
	private final CLabel lYield = new CLabel();
	private final VNumber fYield = new VNumber();
	private final CLabel lQtyOnhand = new CLabel();
	private final VNumber fQtyOnhand = new VNumber();
	private final CLabel lQtySafetyStock = new CLabel();
	private final VNumber fQtySafetyStock = new VNumber();
	private final CLabel lOrdered = new CLabel();
	private final VNumber fOrdered = new VNumber();
	private final CLabel lQtyReserved = new CLabel();
	private final VNumber fQtyReserved = new VNumber();
	private final CLabel lQtyAvailable = new CLabel();
	private final VNumber fQtyAvailable = new VNumber();

	private final VCheckBox fIsMRP = new VCheckBox(I_PP_Product_Planning.COLUMNNAME_IsMPS, false, false, true,
			msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_IsMPS),
			"",
			false);
	private final VCheckBox fIsRequiredMRP = new VCheckBox(I_PP_Product_Planning.COLUMNNAME_IsRequiredMRP, false, false, true,
			msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_IsRequiredMRP),
			"",
			false);
	private final VCheckBox fIsCreatePlan = new VCheckBox(I_PP_Product_Planning.COLUMNNAME_IsCreatePlan, false, false, true,
			msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_IsCreatePlan),
			"",
			false);

	private static final String COLUMNNAME_DatePromised = I_PP_MRP.COLUMNNAME_DatePromised;
	private static final String COLUMNNAME_QtyScheduledReceipts = "QtyScheduledReceipts";
	private static final String COLUMNNAME_QtyGrossReq = "QtyGrossReq";
	private static final String COLUMNNAME_PlannedQty = "PlannedQty";
	private static final String COLUMNNAME_QtyOnHandProjected = "QtyOnHandProjected";
	private static final String COLUMNNAME_M_Warehouse_ID = I_PP_MRP.COLUMNNAME_M_Warehouse_ID;
	private static final String COLUMNNAME_M_Product_ID = I_PP_MRP.COLUMNNAME_M_Product_ID;
	private static final String COLUMNNAME_OrderType = I_PP_MRP.COLUMNNAME_OrderType;
	private static final String COLUMNNAME_TypeMRP = I_PP_MRP.COLUMNNAME_TypeMRP;

	/**
	 * Produkt Plandaten
	 */
	private static final String ACTION_ProductDataPlanning = "ACTION_ProductDataPlanning";
	private static final int WINDOW_ID_ProductDataPlanning = 53007;
	private final CButton bZoomProductPlanning = new CButton();

	/**
	 * Creates MRP records table layout
	 *
	 * @return table layout
	 */
	private ColumnInfo[] createColumnInfos()
	{
		final Properties ctx = getCtx();

		final Language language = Env.getLanguage(ctx);
		final String languageLogin = language.getAD_Language();
		final boolean isBaseLanguage = Language.getBaseAD_Language().compareTo(languageLogin) == 0;

		return new ColumnInfo[]
		{
				// 0 - PP_MRP_ID
				new ColumnInfo(" ", "PP_MRP.PP_MRP_ID", IDColumn.class)
				,
				// 1 - Product Value
				new ColumnInfo(msgBL.translate(ctx, "ProductValue"),
						"(Select Value from M_Product p where p.M_Product_ID=PP_MRP.M_Product_ID) AS ProductValue",
						KeyNamePair.class)
						.setColumnName(COLUMNNAME_M_Product_ID)
						.setKeyPairColSQL("PP_MRP.M_Product_ID")
				,
				// 2 - Product Name
				new ColumnInfo(msgBL.translate(ctx, "ProductName"),
						"(Select Name from M_Product p where p.M_Product_ID=PP_MRP.M_Product_ID)", String.class)
				,
				// 3 - ASI Description
				// (08074)
				new ColumnInfo(msgBL.translate(ctx, "M_AttributeSetInstance_ID"),
						"(select asi.Description from M_AttributeSetInstance asi where asi.M_AttributeSetInstance_ID=PP_MRP.M_AttributeSetInstance_ID) AS ASIDescription",
						String.class)
				,
				// 4 - Resource
				new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_S_Resource_ID),
						"(Select Name from S_Resource sr where sr.S_Resource_ID=PP_MRP.S_Resource_ID)", String.class)	// 4L - BUG #59
				,
				// 5 - DatePromised
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_DatePromised),
						"PP_MRP.DatePromised", Timestamp.class)
						.setColumnName(COLUMNNAME_DatePromised)
				,
				// 6 - PreparationDate
				new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_PreparationDate),
						"PP_MRP.PreparationDate", Timestamp.class).setDisplayType(DisplayType.DateTime)
				,
				// 7 - Warehouse Name
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_M_Warehouse_ID),
						"(Select Name from M_Warehouse wh where wh.M_Warehouse_ID=PP_MRP.M_Warehouse_ID)",
						KeyNamePair.class)
						.setKeyPairColSQL("PP_MRP.M_Warehouse_ID")
						.setColumnName(COLUMNNAME_M_Warehouse_ID)
				,
				// 8 - Qty Scheduled Receipts (Supply, Firm)
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_QtyScheduledReceipts),
						"(CASE WHEN PP_MRP.TypeMRP='S' AND PP_MRP.DocStatus  IN ('IP','CO') THEN PP_MRP.Qty ELSE NULL END)", BigDecimal.class)
						.setWidthMin(150)
						.setColumnName(COLUMNNAME_QtyScheduledReceipts)
						.setPrecision(qtyPrecision)
				,
				// 8 - Qty Gross Requirements (Demand)
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_QtyGrossReq),
						"(CASE WHEN PP_MRP.TypeMRP='D' THEN PP_MRP.Qty ELSE NULL END)", BigDecimal.class)
						.setColumnName(COLUMNNAME_QtyGrossReq)
						.setPrecision(qtyPrecision)
				,
				// 9 - Qty Planned (Supply, Not Firm)
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_PlannedQty),
						"(CASE WHEN PP_MRP.TypeMRP='S' AND PP_MRP.DocStatus ='DR' THEN PP_MRP.Qty ELSE NULL END)",
						BigDecimal.class)
						.setColumnName(COLUMNNAME_PlannedQty)
						.setPrecision(qtyPrecision)
				,
				// 10 - Project QOH (will be updated later to QtyOnHand - QtyGrossReq + QtyScheduledReceipts + QtyPlanned)
				// NOTE: for the start this is only QtyOnHand
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_QtyOnHandProjected),
						"bomQtyOnHand(PP_MRP.M_Product_ID , PP_MRP.M_Warehouse_ID, 0)", BigDecimal.class)
						.setColumnName(COLUMNNAME_QtyOnHandProjected)
						.setPrecision(qtyPrecision)
				,
				// 11 - TypeMRP Name (Demand or Supply)
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_TypeMRP),
						isBaseLanguage ? "(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=53230 AND Value = PP_MRP.TypeMRP)"
								: "(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  "
										+ "WHERE rl.AD_Reference_ID=53230 AND rlt.AD_Language = '" + languageLogin + "' AND Value = PP_MRP.TypeMRP)",
						ValueNamePair.class)
						.setColumnName(COLUMNNAME_TypeMRP)
						.setKeyPairColSQL("PP_MRP.TypeMRP")
				,
				// 12 - OrderType Name (i.e. similar with Document Base Type)
				new ColumnInfo(msgBL.translate(ctx, COLUMNNAME_OrderType),
						isBaseLanguage ?
								"(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=53229 AND Value = PP_MRP.OrderType)"
								: "(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  "
										+ "WHERE rl.AD_Reference_ID=53229 AND rlt.AD_Language = '" + languageLogin + "' AND Value = PP_MRP.OrderType)",
						ValueNamePair.class)
						.setColumnName(COLUMNNAME_OrderType)
						.setKeyPairColSQL("PP_MRP.OrderType")
				,
				// 13 - DocumentNo
				new ColumnInfo(msgBL.translate(ctx, I_PP_Order.COLUMNNAME_DocumentNo), "documentNo(PP_MRP.PP_MRP_ID)", String.class),
				// 14 - Document Status
				isBaseLanguage ?
						new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_DocStatus),
								"(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=131 AND Value = PP_MRP.DocStatus)", String.class) :
						new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_DocStatus),
								"(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  "
										+ "WHERE rl.AD_Reference_ID=131 AND rlt.AD_Language = '" + languageLogin + "' AND Value = PP_MRP.DocStatus)",
								String.class),
				// 15 - Date Start Schedule
				new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_DateStartSchedule), "PP_MRP.DateStartSchedule", Timestamp.class),
				// 16 - BPartner Name
				new ColumnInfo(msgBL.translate(ctx, I_PP_MRP.COLUMNNAME_C_BPartner_ID),
						"(SELECT cb.Name FROM C_BPartner cb WHERE cb.C_BPartner_ID=PP_MRP.C_BPartner_ID)",
						String.class),
				// Sales Order (if any)
				new ColumnInfo(msgBL.translate(ctx, "C_Order_ID"),
						"(SELECT o.DocumentNo FROM C_OrderLine ol"
								+ " INNER JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID)"
								+ " WHERE ol.C_OrderLine_ID=PP_MRP." + I_PP_MRP.COLUMNNAME_C_OrderLineSO_ID + ")",
						String.class),
		};
	};

	/**
	 * Static Setup - add fields to parameterPanel
	 *
	 * @throws Exception if Lookups cannot be initialized
	 */
	private void statInit() throws Exception
	{
		// Plant Resource Lookup
		final MLookup resourceL = MLookupFactory.get(getCtx(),
				m_WindowNo,
				0, // TabNo
				MColumn.getColumn_ID(I_PP_Product_Planning.Table_Name, I_PP_MRP.COLUMNNAME_S_Resource_ID),
				DisplayType.Table);
		fResource_ID = new VLookup(I_PP_MRP.COLUMNNAME_S_Resource_ID, false, false, true, resourceL);
		lResource_ID.setLabelFor(fResource_ID);
		fResource_ID.setBackground(AdempierePLAF.getInfoBackground());

		// Planner Lookup
		fPlanner_ID = new VLookup(I_PP_MRP.COLUMNNAME_Planner_ID, false, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(I_PP_Product_Planning.Table_Name, I_PP_MRP.COLUMNNAME_Planner_ID), DisplayType.Table));
		lPlanner_ID.setLabelFor(fPlanner_ID);
		fPlanner_ID.setBackground(AdempierePLAF.getInfoBackground());

		// Warehouse Lookup
		fWarehouse_ID = new VLookup(I_PP_MRP.COLUMNNAME_M_Warehouse_ID, false, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0,
						MColumn.getColumn_ID(I_PP_MRP.Table_Name, I_PP_MRP.COLUMNNAME_M_Warehouse_ID),
						DisplayType.TableDir));
		lWarehouse_ID.setLabelFor(fWarehouse_ID);
		fWarehouse_ID.setBackground(AdempierePLAF.getInfoBackground());

		fIsMRP.setSelected(false);
		fIsMRP.setReadWrite(false);
		fIsRequiredMRP.setSelected(false);
		fIsRequiredMRP.setReadWrite(false);
		fIsCreatePlan.setSelected(false);
		fIsCreatePlan.setReadWrite(false);

		lUOM.setText(msgBL.translate(getCtx(), I_C_UOM.COLUMNNAME_C_UOM_ID));
		fUOM.setBackground(AdempierePLAF.getInfoBackground());
		fUOM.setReadWrite(false);

		lOrderPolicy.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Policy));
		fOrderPolicy.setBackground(AdempierePLAF.getInfoBackground());
		fOrderPolicy.setReadWrite(false);

		lOrderPeriod.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Period));
		fOrderPeriod.setBackground(AdempierePLAF.getInfoBackground());
		fOrderPeriod.setReadWrite(false);

		lTimefence.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_TimeFence));
		fTimefence.setBackground(AdempierePLAF.getInfoBackground());
		fTimefence.setReadWrite(false);

		lLeadtime.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_DeliveryTime_Promised));
		fLeadtime.setBackground(AdempierePLAF.getInfoBackground());
		fLeadtime.setReadWrite(false);

		lQtyMinOrd.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Min));
		fQtyMinOrd.setBackground(AdempierePLAF.getInfoBackground());
		fQtyMinOrd.setReadWrite(false);

		lQtyMaxOrd.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Max));
		fQtyMaxOrd.setBackground(AdempierePLAF.getInfoBackground());
		fQtyMaxOrd.setReadWrite(false);

		lQtyOrderPacks.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Pack));
		fQtyOrderPacks.setBackground(AdempierePLAF.getInfoBackground());
		fQtyOrderPacks.setReadWrite(false);

		lOrderQty.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Order_Qty));
		fOrderQty.setBackground(AdempierePLAF.getInfoBackground());
		fOrderQty.setReadWrite(false);

		lYield.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_Yield));
		fYield.setBackground(AdempierePLAF.getInfoBackground());
		fYield.setReadWrite(false);

		lQtyOnhand.setText(msgBL.translate(getCtx(), I_M_Storage.COLUMNNAME_QtyOnHand));
		fQtyOnhand.setBackground(AdempierePLAF.getInfoBackground());
		fQtyOnhand.setReadWrite(false);

		lQtySafetyStock.setText(msgBL.translate(getCtx(), I_PP_Product_Planning.COLUMNNAME_SafetyStock));
		fQtySafetyStock.setBackground(AdempierePLAF.getInfoBackground());
		fQtySafetyStock.setReadWrite(false);

		lQtyReserved.setText(msgBL.translate(getCtx(), I_M_Storage.COLUMNNAME_QtyReserved));
		fQtyReserved.setBackground(AdempierePLAF.getInfoBackground());
		fQtyReserved.setReadWrite(false);

		lQtyAvailable.setText(msgBL.translate(getCtx(), "QtyAvailable"));
		fQtyAvailable.setBackground(AdempierePLAF.getInfoBackground());
		fQtyAvailable.setReadWrite(false);

		lOrdered.setText(msgBL.translate(getCtx(), I_PP_Order.COLUMNNAME_QtyOrdered));
		fOrdered.setBackground(AdempierePLAF.getInfoBackground());
		fOrdered.setReadWrite(false);

		// Product Lookup
		final MLookup productLookup = MLookupFactory.get(getCtx(),
				m_WindowNo, // WindowNo
				0, // TabNo
				MColumn.getColumn_ID(I_PP_MRP.Table_Name, I_PP_MRP.COLUMNNAME_M_Product_ID),
				DisplayType.Search);
		fProduct_ID = new VLookup(I_PP_MRP.COLUMNNAME_M_Product_ID, true, false, true, productLookup)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void setValue(final Object arg0)
			{
				super.setValue(arg0);
				fAttrSetInstance_ID.setValue(null); // reset AttributeSetInstance
			};
		};
		fProduct_ID.enableLookupAutocomplete();

		// AttributeSet Button
		fAttrSetInstance_ID = new CButton()
		{
			private static final long serialVersionUID = 1L;
			private Object value;

			@Override
			public void setText(String text)
			{
				if (text == null)
				{
					text = "---";
				}
				if (text.length() > 23)
				{
					text = text.substring(0, 20) + "...";
				}
				super.setText(text);
			};

			@Override
			public void setValue(final Object arg0)
			{
				value = arg0;
				final int i = arg0 instanceof Integer ? ((Integer)arg0).intValue() : 0;
				if (i == 0)
				{
					setText(null);
				}
			};

			@Override
			public Object getValue()
			{
				return value;
			};
		};

		fAttrSetInstance_ID.setValue(0);
		fAttrSetInstance_ID.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				selectAttributeSetInstance();
				// executeQuery();
			}
		});

		// 06777: Adding new filters
		// Partner

		fPartner_ID = new VLookup(I_PP_MRP.COLUMNNAME_C_BPartner_ID, true, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(I_C_BPartner.Table_Name, I_PP_MRP.COLUMNNAME_C_BPartner_ID), DisplayType.Search));
		lPartner_ID.setLabelFor(fPartner_ID);
		fPartner_ID.setBackground(AdempierePLAF.getInfoBackground());

		lDocumentNo.setLabelFor(fDocumentNo);
		fDocumentNo.setToolTipText(msgBL.translate(getCtx(), I_PP_Order.COLUMNNAME_DocumentNo));
		fDocumentNo.setBackground(AdempierePLAF.getInfoBackground());

		// Order

		fOrder_ID = new VLookup(I_PP_MRP.COLUMNNAME_C_Order_ID, true, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(I_PP_MRP.Table_Name, I_PP_MRP.COLUMNNAME_C_Order_ID), DisplayType.Search));
		fOrder_ID.enableLookupAutocomplete();
		lOrder_ID.setLabelFor(fOrder_ID);
		fOrder_ID.setBackground(AdempierePLAF.getInfoBackground());
		// Order Type
		fOrderType = new VLookup(I_PP_MRP.COLUMNNAME_OrderType, false, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(I_PP_MRP.Table_Name, I_PP_MRP.COLUMNNAME_OrderType), DisplayType.List));

		lOrderType.setLabelFor(fOrderType);
		fOrderType.setBackground(AdempierePLAF.getInfoBackground());

		// Type MRP
		fSearchTypeMRP = new VLookup(I_PP_MRP.COLUMNNAME_TypeMRP, false, false, true,
				MLookupFactory.get(getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(I_PP_MRP.Table_Name, I_PP_MRP.COLUMNNAME_TypeMRP), DisplayType.List));
		lSearchTypeMRP.setLabelFor(fSearchTypeMRP);
		fSearchTypeMRP.setBackground(AdempierePLAF.getInfoBackground());

		lProduct_ID.setLabelFor(fProduct_ID);
		fProduct_ID.setBackground(AdempierePLAF.getInfoBackground());
		//
		lDateFrom.setLabelFor(fDateFrom);
		fDateFrom.setBackground(AdempierePLAF.getInfoBackground());
		fDateFrom.setToolTipText(msgBL.translate(getCtx(), I_M_Lot.COLUMNNAME_DateFrom));
		lDateTo.setLabelFor(fDateTo);
		fDateTo.setBackground(AdempierePLAF.getInfoBackground());
		fDateTo.setToolTipText(msgBL.translate(getCtx(), I_M_Lot.COLUMNNAME_DateTo));

		//
		parameterPanel.setLayout(new ALayout());
		// 1st Row
		addToParameterPanel(lProduct_ID, new ALayoutConstraint(0, 0));
		addToParameterPanel(fProduct_ID, new ALayoutConstraint(0, 1));
		addToParameterPanel(lUOM, new ALayoutConstraint(0, 2));
		addToParameterPanel(fUOM, new ALayoutConstraint(0, 3));
		addToParameterPanel(lOrderPolicy, new ALayoutConstraint(0, 4));
		addToParameterPanel(fOrderPolicy, new ALayoutConstraint(0, 5));

		// 2nd Row
		addToParameterPanel(lAttrSetInstance_ID, new ALayoutConstraint(1, 0));
		addToParameterPanel(fAttrSetInstance_ID, new ALayoutConstraint(1, 1));
		addToParameterPanel(lQtyOnhand, new ALayoutConstraint(1, 2));
		addToParameterPanel(fQtyOnhand, new ALayoutConstraint(1, 3));
		addToParameterPanel(lOrderPeriod, new ALayoutConstraint(1, 4));
		addToParameterPanel(fOrderPeriod, new ALayoutConstraint(1, 5));

		// 3rd Row
		addToParameterPanel(lPlanner_ID, new ALayoutConstraint(2, 0));
		addToParameterPanel(fPlanner_ID, new ALayoutConstraint(2, 1));
		addToParameterPanel(lQtySafetyStock, new ALayoutConstraint(2, 2));
		addToParameterPanel(fQtySafetyStock, new ALayoutConstraint(2, 3));
		addToParameterPanel(lQtyMinOrd, new ALayoutConstraint(2, 4));
		addToParameterPanel(fQtyMinOrd, new ALayoutConstraint(2, 5));

		// 4th Row
		addToParameterPanel(lWarehouse_ID, new ALayoutConstraint(3, 0));
		addToParameterPanel(fWarehouse_ID, new ALayoutConstraint(3, 1));
		addToParameterPanel(lQtyReserved, new ALayoutConstraint(3, 2));
		addToParameterPanel(fQtyReserved, new ALayoutConstraint(3, 3));
		addToParameterPanel(lQtyMaxOrd, new ALayoutConstraint(3, 4));
		addToParameterPanel(fQtyMaxOrd, new ALayoutConstraint(3, 5));

		// 5th Row
		addToParameterPanel(lResource_ID, new ALayoutConstraint(4, 0));
		addToParameterPanel(fResource_ID, new ALayoutConstraint(4, 1));
		addToParameterPanel(lQtyAvailable, new ALayoutConstraint(4, 2));
		addToParameterPanel(fQtyAvailable, new ALayoutConstraint(4, 3));
		addToParameterPanel(lQtyOrderPacks, new ALayoutConstraint(4, 4));
		addToParameterPanel(fQtyOrderPacks, new ALayoutConstraint(4, 5));

		// 6th Row
		addToParameterPanel(lDateFrom, new ALayoutConstraint(5, 0));
		addToParameterPanel(fDateFrom, new ALayoutConstraint(5, 1));
		addToParameterPanel(lOrdered, new ALayoutConstraint(5, 2));
		addToParameterPanel(fOrdered, new ALayoutConstraint(5, 3));
		addToParameterPanel(lOrderQty, new ALayoutConstraint(5, 4));
		addToParameterPanel(fOrderQty, new ALayoutConstraint(5, 5));

		// 7th Row
		addToParameterPanel(lDateTo, new ALayoutConstraint(6, 0));
		addToParameterPanel(fDateTo, new ALayoutConstraint(6, 1));
		addToParameterPanel(fIsMRP, new ALayoutConstraint(6, 3)); // 06777. move the is MPS check box in the middle column
		addToParameterPanel(lTimefence, new ALayoutConstraint(6, 4));
		addToParameterPanel(fTimefence, new ALayoutConstraint(6, 5));

		// 8th Row
		addToParameterPanel(lPartner_ID, new ALayoutConstraint(7, 0)); // 06777
		addToParameterPanel(fPartner_ID, new ALayoutConstraint(7, 1));// 06777
		addToParameterPanel(fIsCreatePlan, new ALayoutConstraint(7, 3));
		addToParameterPanel(lLeadtime, new ALayoutConstraint(7, 4));
		addToParameterPanel(fLeadtime, new ALayoutConstraint(7, 5));

		// 9th Row
		addToParameterPanel(lDocumentNo, new ALayoutConstraint(8, 0)); // 07952
		addToParameterPanel(fDocumentNo, new ALayoutConstraint(8, 1)); // 07952
		addToParameterPanel(fIsRequiredMRP, new ALayoutConstraint(8, 3));
		addToParameterPanel(lYield, new ALayoutConstraint(8, 4));
		addToParameterPanel(fYield, new ALayoutConstraint(8, 5));

		// 10th row
		addToParameterPanel(lOrder_ID, new ALayoutConstraint(9, 0));// 06777
		addToParameterPanel(fOrder_ID, new ALayoutConstraint(9, 1));// 06777

		// 11th Row
		addToParameterPanel(lOrderType, new ALayoutConstraint(10, 0));// 06777
		addToParameterPanel(fOrderType, new ALayoutConstraint(10, 1));// 06777

		// 12th Row
		addToParameterPanel(lSearchTypeMRP, new ALayoutConstraint(11, 0));// 06777
		addToParameterPanel(fSearchTypeMRP, new ALayoutConstraint(11, 1));// 06777

		//
		// Status Bar
		statusBar.setStatusLine("", false);
		statusBar.setStatusToolTip("");
		statusBar.setStatusDB("", null);
	}

	private void addToParameterPanel(final Component comp, final ALayoutConstraint layoutConstraint)
	{
		parameterPanel.add(comp, layoutConstraint);

		//
		// If component is an Editor, add it to our editors list
		if (comp instanceof VEditor)
		{
			final VEditor editor = (VEditor)comp;
			editors.add(editor);
		}
	}

	/**
	 * filter by Attribute Set Instance
	 */
	private void selectAttributeSetInstance()
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return;
		}

		final int warehouseId = getM_Warehouse_ID();

		final MProduct product = MProduct.get(getCtx(), productId);
		final MWarehouse wh = MWarehouse.get(getCtx(), warehouseId);
		final String title = product.get_Translation(I_M_Product.COLUMNNAME_Name)
				+ " - " + wh.get_Translation(I_M_Warehouse.COLUMNNAME_Name);

		final int bpartnerId = getC_BPartner_ID();
		final PAttributeInstance pai = new PAttributeInstance(m_frame, title, warehouseId, 0, productId, bpartnerId);
		if (pai.getM_AttributeSetInstance_ID() != -1)
		{
			fAttrSetInstance_ID.setText(pai.getM_AttributeSetInstanceName());
			fAttrSetInstance_ID.setValue(new Integer(pai.getM_AttributeSetInstance_ID()));
		}
		else
		{
			fAttrSetInstance_ID.setValue(Integer.valueOf(0));
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initLayoutPanels()
	{
		mainPanel = new javax.swing.JPanel();
		final JTabbedPane mainPanelTabs = new javax.swing.JTabbedPane();
		final JPanel panelOrder = new javax.swing.JPanel();
		final JPanel panelFind = new javax.swing.JPanel();
		final JPanel panelCenter = new javax.swing.JPanel();
		final JPanel panelBottom = new javax.swing.JPanel();
		final JPanel results = new javax.swing.JPanel();

		setLayout(new java.awt.BorderLayout());

		mainPanel.setLayout(new java.awt.BorderLayout());

		panelOrder.setLayout(new java.awt.BorderLayout());

		panelOrder.add(panelFind, java.awt.BorderLayout.NORTH);

		panelOrder.add(panelCenter, java.awt.BorderLayout.CENTER);

		panelOrder.add(panelBottom, java.awt.BorderLayout.SOUTH);

		mainPanelTabs.addTab("Order", panelOrder);

		mainPanelTabs.addTab("Results", results);

		mainPanel.add(mainPanelTabs, java.awt.BorderLayout.CENTER);

		add(mainPanel, java.awt.BorderLayout.CENTER);
	}

	/**
	 * Static Init
	 *
	 * @throws Exception
	 */
	protected final void jbInit() throws Exception
	{
		if(m_frame != null)
		{
			m_frame.setIconImage(Images.getImage2(InfoBuilder.ACTION_InfoMRP + "16"));
		}
		
		final Properties ctx = getCtx();

		mainPanel.setLayout(new java.awt.BorderLayout());

		setLayout(new java.awt.BorderLayout());
		southPanel.setLayout(southLayout);
		southPanel.add(confirmPanel, BorderLayout.CENTER);
		southPanel.add(statusBar, BorderLayout.SOUTH);

		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.add(parameterPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		scrollPane.getViewport().add(p_table, null);
		//
		confirmPanel.setActionListener(this);
		confirmPanel.getResetButton().setVisible(hasReset());
		confirmPanel.getCustomizeButton().setVisible(hasCustomize());
		confirmPanel.getHistoryButton().setVisible(hasHistory());
		confirmPanel.getZoomButton().setVisible(hasZoom());
		//
		popup.add(calcMenu);
		calcMenu.setText(msgBL.getMsg(ctx, "Calculator"));
		calcMenu.setIcon(Images.getImageIcon2("Calculator16"));
		calcMenu.addActionListener(this);
		//
		p_table.getSelectionModel().addListSelectionListener(this);

		bZoomProductPlanning.setToolTipText(msgBL.translate(ctx, ACTION_ProductDataPlanning));
		bZoomProductPlanning.setIcon(Images.getImageIcon2("Product24"));
		bZoomProductPlanning.setActionCommand(ACTION_ProductDataPlanning);
		bZoomProductPlanning.addActionListener(this);
		bZoomProductPlanning.setMargin(ConfirmPanel.s_insets);
		bZoomProductPlanning.setSize(confirmPanel.getZoomButton().getSize());
		confirmPanel.addButton(bZoomProductPlanning);

		enableButtons();

	}	// jbInit

	/**
	 * Initialize MRP records table
	 *
	 * @throws Exception if Lookups cannot be initialized
	 */
	private void initTable() throws Exception
	{
		final ColumnInfo[] m_layout = createColumnInfos();
		final String sqlWhereStatic = null;
		prepareTable(
				m_layout, // layout
				I_PP_MRP.Table_Name, // tableName
				sqlWhereStatic, // static where clause
				"ProductValue, DatePromised, PP_MRP_ID" // Order By
		);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final String cmd = e.getActionCommand();
		actionPerformed(cmd);
	}

	private void actionPerformed(final String cmd)
	{
		if (cmd.equals(ConfirmPanel.A_OK))
		{
			m_frame.dispose();
		}
		else if (cmd.equals(ConfirmPanel.A_CANCEL))
		{
			// m_cancel = true;
			m_frame.dispose();
		}
		else if (cmd.equals(ConfirmPanel.A_ZOOM))
		{
			zoom();
		}
		else if (cmd.equals(ConfirmPanel.A_REFRESH))
		{
			executeQuery();
		}
		else if (cmd.equals(ACTION_ProductDataPlanning))
		{
			zoomProductDataPlanning();
		}

		if (m_frame != null)
		{
			m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void dispose()
	{
		// Stop the data loader
		if (m_worker != null && m_worker.isAlive())
		{
			m_worker.interrupt();
			m_worker = null;
		}

		if (m_frame != null)
		{
			// Dispose editors
			// This will avoid another popup to be displayed if there is some search text inside, but not matched
			for (final VEditor editor : editors)
			{
				editor.dispose();
			}

			m_frame.dispose();
		}
		m_frame = null;
	}

	@Override
	public void stateChanged(final ChangeEvent e)
	{
		// nothing
	}

	@Override
	public void tableChanged(final TableModelEvent e)
	{
		// nothing
	}

	@Override
	public void lockUI(final de.metas.process.ProcessInfo processInfo)
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		confirmPanel.getRefreshButton().setEnabled(false);
	}

	@Override
	public void unlockUI(final de.metas.process.ProcessInfo processInfo)
	{
		setCursor(Cursor.getDefaultCursor());
		confirmPanel.getRefreshButton().setEnabled(true);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e)
	{
		// nothing
	}

	@Override
	public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
	{
		// nothing
	}
	
	private final void loadMRPRows()
	{
		// Clear Table
		p_table.setRowCount(0);
		
		final List<Object> sqlParams = new ArrayList<Object>();
		final String sqlFinal = getSQL(sqlParams);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlFinal, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			while (!Thread.currentThread().isInterrupted() && rs.next())
			{
				final int row = p_table.getRowCount();
				p_table.setRowCount(row + 1);

				int colOffset = 1;  // columns start with 1
				for (int col = 0; col < p_layout.length; col++)
				{
					Object data = null;
					final Class<?> c = p_layout[col].getColClass();
					final int colIndex = col + colOffset;
					if (c == IDColumn.class)
					{
						final int countMRPNotices_All = rs.getInt(COLUMNNAME_CountMRPNotices_All);
						final int countMRPNotices_Error = rs.getInt(COLUMNNAME_CountMRPNotices_Error);

						final VMRPDetailedIDColumn id = new VMRPDetailedIDColumn(rs.getInt(colIndex));
						id.setSelected(true);
						id.setCountAllMRPNotices(countMRPNotices_All);
						id.setCountErrorMRPNotices(countMRPNotices_Error);
						data = id;
						p_table.setColumnReadOnly(0, false);
					}
					else if (c == Boolean.class)
					{
						data = new Boolean("Y".equals(rs.getString(colIndex)));
					}
					else if (c == Timestamp.class)
					{
						data = rs.getTimestamp(colIndex);
					}
					else if (c == BigDecimal.class)
					{
						final BigDecimal dataBD = rs.getBigDecimal(colIndex);
						// NOTE: don't set scale here... it's the job of renderer to display it nicely
						// if (dataBD != null)
						// {
						// dataBD = dataBD.setScale(2, RoundingMode.HALF_UP);
						// }
						data = dataBD;
					}
					else if (c == Double.class)
					{
						data = new Double(rs.getDouble(colIndex));
					}
					else if (c == Integer.class)
					{
						data = new Integer(rs.getInt(colIndex));
					}
					else if (c == KeyNamePair.class)
					{
						final String display = rs.getString(colIndex);
						final int key = rs.getInt(colIndex + 1);
						data = new KeyNamePair(key, display);
						colOffset++;
					}
					else if (c == ValueNamePair.class)
					{
						final String display = rs.getString(colIndex);
						final String key = rs.getString(colIndex + 1);
						data = new ValueNamePair(key, display);
						colOffset++;
					}
					else
					{
						data = rs.getString(colIndex);
					}

					// store
					// fix so the column headers can be dragged and dropped.
					final int colIndexView = p_table.convertColumnIndexToView(col);
					p_table.setValueAt(data, row, colIndexView);
				}
			}
		}
		catch (final SQLException e)
		{
			log.error("Info.Worker.run - " + sqlFinal, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			
			p_table.autoSize();
			statusBar.setStatusDB(p_table.getRowCount());
		}
	}
	
	private String getSQL(final List<Object> sqlParams)
	{
		final StringBuilder sql = new StringBuilder(m_sqlMain);

		final String sqlWhereClause = getSQLWhere(sqlParams);
		if (!Check.isEmpty(sqlWhereClause, true))
		{
			sql.append(sqlWhereClause);   // includes first AND
		}

		sql.append(m_sqlAdd);

		//
		// Parse context variables (if any) and apply role restrictions
		String sqlFinal = msgBL.parseTranslation(getCtx(), sql.toString());	// Variables
		sqlFinal = Env.getUserRolePermissions().addAccessSQL(sqlFinal,
				I_PP_MRP.Table_Name,
				IUserRolePermissions.SQL_FULLYQUALIFIED,
				IUserRolePermissions.SQL_RO);

		return sqlFinal;
	}

	/**
	 * Set sql to get the head values
	 *
	 * @param sqlParams list where sql parameters are appended
	 * @return sql
	 */
	private String getSQLWhere(final List<Object> sqlParams)
	{
		final StringBuilder sql = new StringBuilder();

		final int productId = getM_Product_ID();
		if (productId > 0)
		{
			sql.append(" AND PP_MRP.M_Product_ID=?");
			sqlParams.add(productId);

			// Hide forecasts which are in the past
			sql.append(" AND (PP_MRP.OrderType<>? OR PP_MRP.DatePromised >= now())");
			sqlParams.add(X_PP_MRP.ORDERTYPE_Forecast);
		}

		final int attributeSetInstanceId = getM_AttributeSetInstance_ID();
		if (attributeSetInstanceId > 0)
		{

			sql.append(" AND PP_MRP.M_AttributeSetInstance_ID=?");
			sqlParams.add(attributeSetInstanceId);
		}

		//
		// Adding document number as a filter criteria (07952)
		final String documentNo = getDocumentNo();
		if (!Check.isEmpty(documentNo, true))
		{

			final List<Object> documentNoList = new ArrayList<Object>();
			documentNoList.add(documentNo);
			final String whereClauseStr = FindHelper.buildStringRestriction("documentNo(PP_MRP.PP_MRP_ID)", documentNo, false, sqlParams);
			if (!Check.isEmpty(whereClauseStr))
			{
				sql.append(" AND ").append(whereClauseStr).append(" ");
			}
		}

		final int orderId = getC_Order_ID();
		if (orderId > 0)
		{
			// 07952
			// The matching criteria is now done on the C_OrderLineSO_ID.
			// We search for the MRP entries that have the same
			// C_OrderLineSO_ID as the orderlines of the order given as parameter (via document number)
			sql.append("AND EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID=PP_MRP.C_OrderLineSO_ID AND ol.C_Order_ID = ?) ");
			sqlParams.add(orderId);
		}

		final int bpartnerId = getC_BPartner_ID();
		if (bpartnerId > 0)
		{
			sql.append(" AND PP_MRP.C_BPartner_ID=?");
			sqlParams.add(bpartnerId);
		}

		final String orderType = getOrderType();
		if (!Check.isEmpty(orderType, true))
		{
			sql.append(" AND PP_MRP.OrderType=?");
			sqlParams.add(orderType);
		}

		final String typeMRP = getTypeMRP();
		if (!Check.isEmpty(typeMRP))
		{
			sql.append(" AND PP_MRP.TypeMRP=?");
			sqlParams.add(typeMRP);
		}

		final int resourceId = getS_Resource_ID();
		if (resourceId > 0)
		{
			sql.append(" AND PP_MRP.S_Resource_ID=?");
			sqlParams.add(resourceId);
		}

		final int plannerId = getPlanner_ID();
		if (plannerId > 0)
		{
			sql.append(" AND PP_MRP.Planner_ID=?");
			sqlParams.add(plannerId);
		}

		final int warehouseId = getM_Warehouse_ID();
		if (warehouseId > 0)
		{
			sql.append(" AND PP_MRP.M_Warehouse_ID=?");
			sqlParams.add(warehouseId);
		}

		final Date datePromisedFrom = getDueStart();
		if (datePromisedFrom != null)
		{
			sql.append(" AND TRUNC(PP_MRP.DatePromised) >= ?");
			sqlParams.add(datePromisedFrom);
		}

		final Date datePromisedTo = getDueEnd();
		if (datePromisedTo != null)
		{
			sql.append(" AND TRUNC(PP_MRP.DatePromised) <= ?");
			sqlParams.add(datePromisedTo);
		}

		//
		// Hide those MRP records which were excluded by MRP_Exclude flags (08478)
		final Properties ctx = getCtx();
		final IMRPQueryBuilder mrpQueryBuilder = mrpDAO.createMRPQueryBuilder()
				.clear()
				.setContextProvider(new PlainContextAware(ctx));
		mrpQueryBuilder.setSkipIfMRPExcluded(true);

		//
		// Append "mrpQueryBuilder" result to our main SQL where clause
		final ICompositeQueryFilter<I_PP_MRP> mrpQueryFilters = mrpQueryBuilder.createQueryBuilder().getFilters();
		final String mrpQueryFiltersSql = queryBuilderDAO.getSql(ctx, mrpQueryFilters, sqlParams);
		if (!Check.isEmpty(mrpQueryFiltersSql, true))
		{
			sql.append(" AND (").append(mrpQueryFiltersSql).append(")");
		}

		log.debug("MRP Info.setWhereClause={}", sql);
		return sql.toString();
	}

	/**
	 * Fill the header values from {@link I_PP_Product_Planning}
	 */
	private void fillProductPlanning()
	{
		I_PP_Product_Planning pp = productPlanningDAO.find(getCtx(),
				getAD_Org_ID(),
				getM_Warehouse_ID(),
				getS_Resource_ID(),
				getM_Product_ID(),
				ITrx.TRXNAME_None);
		if (pp == null)
		{
			pp = new MPPProductPlanning(getCtx(), 0, ITrx.TRXNAME_None);
		}

		final String orderPolicyName = adReferenceDAO.retriveListName(getCtx(), X_PP_Product_Planning.ORDER_POLICY_AD_Reference_ID, pp.getOrder_Policy());

		fIsMRP.setSelected(pp.isMPS());
		fIsRequiredMRP.setSelected(pp.isRequiredMRP());
		fIsCreatePlan.setSelected(pp.isCreatePlan());
		fOrderPeriod.setValue(pp.getOrder_Period());
		fLeadtime.setValue(pp.getDeliveryTime_Promised());
		fTimefence.setValue(pp.getTimeFence());
		fQtyMinOrd.setValue(pp.getOrder_Min());
		fQtyMaxOrd.setValue(pp.getOrder_Max());
		fQtyOrderPacks.setValue(pp.getOrder_Pack());
		fOrderQty.setValue(pp.getOrder_Qty());
		fYield.setValue(pp.getYield());
		fOrderPolicy.setText(orderPolicyName);
		fQtySafetyStock.setValue(pp.getSafetyStock());
	}

	/**
	 * Fill header storage qtys: QtyOnHand, QtyReserved, QtyAvailable, QtyOrdered, UOM, ReplenishMin
	 */
	private void fillStorageQtys()
	{
		//
		// Reset Qty fields first
		fQtyOnhand.setValue(BigDecimal.ZERO);
		fQtyReserved.setValue(BigDecimal.ZERO);
		fQtyAvailable.setValue(BigDecimal.ZERO);
		fOrdered.setValue(BigDecimal.ZERO);

		//
		// Check Product (mandatory):
		final int M_Product_ID = getM_Product_ID();
		if (M_Product_ID <= 0)
		{
			return;
		}

		//
		// Check Warehouse (mandatory)
		final int warehouseId = getM_Warehouse_ID();
		if (warehouseId <= 0)
		{
			return;
		}

		final int attributeSetInstanceId = getM_AttributeSetInstance_ID();
		final int locatorId = 0; // no specific locator => all warehouse locators shall be considered

		final String sql = new StringBuilder("SELECT ")
				.append("BOMQtyOnHandASI(M_Product_ID,?,?,?) as qtyonhand, ")
				.append("BOMQtyReservedASI(M_Product_ID,?,?,?) as qtyreserved, ")
				.append("BOMQtyAvailableASI(M_Product_ID,?,?,?) as qtyavailable, ")
				.append("BOMQtyOrderedASI(M_Product_ID,?,?,?) as qtyordered")
				.append(" FROM M_Product WHERE M_Product_ID=?")
				.toString();
		final Object[] sqlParams = new Object[] {
				attributeSetInstanceId, warehouseId, locatorId,
				attributeSetInstanceId, warehouseId, locatorId,
				attributeSetInstanceId, warehouseId, locatorId,
				attributeSetInstanceId, warehouseId, locatorId,
				getM_Product_ID()
		};

		//
		// Set Quantities
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				fQtyOnhand.setValue(rs.getBigDecimal(1));
				fQtyReserved.setValue(rs.getBigDecimal(2));
				fQtyAvailable.setValue(rs.getBigDecimal(3));
				fOrdered.setValue(rs.getBigDecimal(4));
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		// Set UOM:
		final int uom_id = MProduct.get(getCtx(), M_Product_ID).getC_UOM_ID();
		final MUOM uom = MUOM.get(getCtx(), uom_id);
		final KeyNamePair kum = new KeyNamePair(uom.getC_UOM_ID(), uom.get_Translation(I_C_UOM.COLUMNNAME_Name));
		fUOM.setText(kum.toString());

		//
		// Set Replenish Min Level:
		if (fReplenishMin != null)
		{
			BigDecimal replenishLevelMin = Env.ZERO;
			if (warehouseId > 0)
			{
				final String sqlLevelMin = "SELECT Level_Min FROM M_Replenish"
						+ " WHERE AD_Client_ID=? AND M_Product_ID=? AND M_Warehouse_ID=?";
				replenishLevelMin = DB.getSQLValueBD(ITrx.TRXNAME_None, sqlLevelMin, AD_Client_ID, M_Product_ID, warehouseId);
			}
			fReplenishMin.setValue(replenishLevelMin);
		}
	}

	/**
	 * Reset Parameters To be overwritten by concrete classes
	 */
	void doReset()
	{
		// nothing
	}

	/**
	 * Has Reset (false) To be overwritten by concrete classes
	 *
	 * @return true if it has reset (default false)
	 */
	boolean hasReset()
	{
		return false;
	}

	/**
	 * History dialog To be overwritten by concrete classes
	 */
	void showHistory()
	{
		// nothing
	}

	/**
	 * Has History (false) To be overwritten by concrete classes
	 *
	 * @return true if it has history (default false)
	 */
	boolean hasHistory()
	{
		return false;
	}

	/**
	 * Customize dialog To be overwritten by concrete classes
	 */
	void customize()
	{
		// nothing
	}

	/**
	 * Has Customize (false) To be overwritten by concrete classes
	 *
	 * @return true if it has customize (default false)
	 */
	boolean hasCustomize()
	{
		return false;
	}

	private I_PP_MRP getSelectedMRPRecord()
	{
		final Integer PP_MRP_ID = getSelectedRowKey();
		if (PP_MRP_ID == null)
		{
			return null;
		}

		final I_PP_MRP mrp = InterfaceWrapperHelper.create(getCtx(), PP_MRP_ID.intValue(), I_PP_MRP.class, ITrx.TRXNAME_None);
		return mrp;
	}

	/**
	 * Zoom action To be overwritten by concrete classes
	 */
	void zoom()
	{
		log.info("InfoMRPDeatiled.zoom");

		final I_PP_MRP mrp = getSelectedMRPRecord();
		if (mrp == null)
		{
			return;
		}

		//
		// Build the MQuery
		final MQuery query;
		final String ordertype = mrp.getOrderType();
		if (X_PP_MRP.ORDERTYPE_PurchaseOrder.equals(ordertype))
		{
			query = new MQuery(I_C_Order.Table_Name);
			query.setForceSOTrx(false);
			query.addRestriction(I_C_Order.COLUMNNAME_C_Order_ID, Operator.EQUAL, mrp.getC_Order_ID());
		}
		else if (X_PP_MRP.ORDERTYPE_SalesOrder.equals(ordertype))
		{
			query = new MQuery(I_C_Order.Table_Name);
			query.setForceSOTrx(true);
			query.addRestriction(I_C_Order.COLUMNNAME_C_Order_ID, Operator.EQUAL, mrp.getC_Order_ID());
		}
		else if (X_PP_MRP.ORDERTYPE_ManufacturingOrder.equals(ordertype))
		{
			query = new MQuery(I_PP_Order.Table_Name);
			query.addRestriction(I_PP_Order.COLUMNNAME_PP_Order_ID, Operator.EQUAL, mrp.getPP_Order_ID());
		}
		else if (X_PP_MRP.ORDERTYPE_MaterialRequisition.equals(ordertype))
		{
			query = new MQuery(I_M_Requisition.Table_Name);
			query.addRestriction(I_M_Requisition.COLUMNNAME_M_Requisition_ID, Operator.EQUAL, mrp.getM_Requisition_ID());
		}
		else if (X_PP_MRP.ORDERTYPE_Forecast.equals(ordertype))
		{
			query = new MQuery(I_M_Forecast.Table_Name);
			query.addRestriction(I_M_Forecast.COLUMNNAME_M_Forecast_ID, Operator.EQUAL, mrp.getM_Forecast_ID());
		}
		else if (X_PP_MRP.ORDERTYPE_DistributionOrder.equals(ordertype))
		{
			query = new MQuery(I_DD_Order.Table_Name);
			query.addRestriction(I_DD_Order.COLUMNNAME_DD_Order_ID, Operator.EQUAL, mrp.getDD_Order_ID());
		}
		else
		{
			return;
		}

		//
		// Zoom
		AEnv.zoom(query);
	}

	/**
	 * Has Zoom (false) To be overwritten by concrete classes
	 *
	 * @return true if it has zoom (default false)
	 */
	boolean hasZoom()
	{
		return true;
	}

	/**
	 * Zoom directly to product data planning
	 */
	private void zoomProductDataPlanning()
	{
		final I_PP_MRP mrp = getSelectedMRPRecord();
		if (mrp == null)
		{
			return;
		}
		final I_M_Product product = mrp.getM_Product();
		if (product == null)
		{
			return;
		}
		AEnv.zoom(I_M_Product.Table_Name, product.getM_Product_ID(), WINDOW_ID_ProductDataPlanning, 0); // PO_Window_ID=0 (not using PO feature)
	}

	/**
	 * Enable OK, History, Zoom if row selected
	 */
	void enableButtons()
	{
		final boolean enable = true;// p_table.getSelectedRow() != -1;

		confirmPanel.getOKButton().setEnabled(true);
		if (hasHistory())
		{
			confirmPanel.getHistoryButton().setEnabled(enable);
		}

		if (hasZoom())
		{
			confirmPanel.getZoomButton().setEnabled(enable);
		}
	}   // enableButtons

	/**************************************************************************
	 * Execute Query
	 */
	void executeQuery()
	{
		// ignore when running
		if (m_worker != null && m_worker.isAlive())
		{
			return;
		}

		//
		// load header MRP info from Product Planning
		fillProductPlanning();
		//
		// load Qtys from Storage
		fillStorageQtys();

		m_worker = new Worker();
		m_worker.start();
	}   // executeQuery

	/**************************************************************************
	 * Prepare Table, Construct SQL (m_m_sqlMain, m_sqlAdd) and size Window
	 *
	 * @param layout layout array
	 * @param from from clause
	 * @param staticWhere static where clause (i.e. does not have any parameters); It shall start with " AND "
	 * @param orderBy order by clause
	 */
	private final void prepareTable(final ColumnInfo[] layout, final String from, final String staticWhere, final String orderBy)
	{
		p_layout = layout;
		final StringBuilder sql = new StringBuilder("SELECT ");
		// add columns & sql
		for (int i = 0; i < layout.length; i++)
		{
			if (i > 0)
			{
				sql.append(", ");
			}
			sql.append(layout[i].getColSQL());
			// adding ID column
			if (layout[i].isKeyPairCol())
			{
				sql.append(",").append(layout[i].getKeyPairColSQL());
			}
			// add to model
			p_table.addColumn(layout[i].getColHeader());
			if (layout[i].isColorColumn())
			{
				p_table.setColorColumn(i);
			}
			if (layout[i].getColClass() == IDColumn.class)
			{
				m_keyColumnIndex = i;
			}
		}
		// Add SQL SELECT: CountMRPNotices_All
		{
			final int ppMRPTableId = adTableDAO.retrieveTableId(I_PP_MRP.Table_Name);
			sql.append(", (SELECT COUNT(1) FROM AD_Note n WHERE n.AD_Table_ID=").append(ppMRPTableId)
					.append(" AND n.Record_ID=PP_MRP.PP_MRP_ID) AS ").append(COLUMNNAME_CountMRPNotices_All);
		}
		// Add SQL SELECT: CountMRPNotices_Error
		{
			final List<String> mrpErrorCodes = Services.get(IMRPNoteBL.class).getMRPErrorCodes();
			final int ppMRPTableId = adTableDAO.retrieveTableId(I_PP_MRP.Table_Name);
			sql.append(", (SELECT COUNT(1) FROM AD_Note n WHERE n.AD_Table_ID=").append(ppMRPTableId)
					.append(" AND n.Record_ID=PP_MRP.PP_MRP_ID")
					.append(" AND EXISTS (SELECT 1 FROM AD_Message m WHERE m.AD_Message_ID=n.AD_Message_ID AND m.Value IN ").append(DB.buildSqlList(mrpErrorCodes)).append(")")
					.append(") AS ").append(COLUMNNAME_CountMRPNotices_Error);
		}

		// set editors (two steps)
		for (int i = 0; i < layout.length; i++)
		{
			p_table.setColumnClass(i, layout[i]);
		}

		sql.append(" FROM ").append(from);
		//
		final StringBuilder where = new StringBuilder("PP_MRP.DocStatus IN ('DR','IP','CO')  AND PP_MRP.IsActive='Y' and PP_MRP.Qty!=0 ");
		sql.append(" WHERE ").append(where.toString());
		if (!Check.isEmpty(staticWhere, true))
		{
			sql.append(" ").append(staticWhere);
		}

		m_sqlMain = sql.toString();

		m_sqlAdd = "";
		if (orderBy != null && orderBy.length() > 0)
		{
			m_sqlAdd = " ORDER BY " + orderBy;
		}

		if (m_keyColumnIndex == -1)
		{
			log.error("No KeyColumn - " + sql);
		}

		// Table Selection
		p_table.setRowSelectionAllowed(true);
		// p_table.addMouseListener(this);
		p_table.setMultiSelection(false);
		p_table.setEditingColumn(0);
		p_table.setColorProvider(new VMRPDetailedTableColorProvider(INDEX_IDColumn));
		p_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Window Sizing
		parameterPanel.setPreferredSize(new Dimension(INFO_WIDTH, parameterPanel.getPreferredSize().height));
		scrollPane.setPreferredSize(new Dimension(INFO_WIDTH, 400));
	}   // prepareTable

	/**
	 * Get the key of currently selected row
	 *
	 * @return selected key
	 */
	protected Integer getSelectedRowKey()
	{
		final int row = p_table.getSelectedRow();
		if (row != -1 && m_keyColumnIndex != -1)
		{
			Object data = p_table.getModel().getValueAt(row, m_keyColumnIndex);
			if (data instanceof IDColumn)
			{
				data = ((IDColumn)data).getRecord_ID();
			}
			if (data instanceof Integer)
			{
				return (Integer)data;
			}
		}
		return null;
	}   // getSelectedRowKey

	protected Properties getCtx()
	{
		return Env.getCtx();
	}

	// 06777 Adding new filters

	// Partner
	protected int getC_BPartner_ID()
	{
		final Object o = fPartner_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	// DocumentNo
	protected String getDocumentNo()
	{
		final Object o = fDocumentNo.getValue();
		return o != null && o instanceof String ? (String)o : String.valueOf("");
	}

	// Order
	protected int getC_Order_ID()
	{
		final Object o = fOrder_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	// OrderType
	protected String getOrderType()
	{
		final Object orderType = fOrderType.getValue();
		return orderType != null ? orderType.toString() : null;
	}

	// TypeMRP
	protected String getTypeMRP()
	{
		final Object typeMRP = fSearchTypeMRP.getValue();
		return typeMRP != null ? typeMRP.toString() : null;
	}

	protected int getM_Product_ID()
	{
		final Object o = fProduct_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	protected int getM_AttributeSetInstance_ID()
	{
		final Object o = fAttrSetInstance_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	public void setM_Product_ID(final int productId)
	{
		fProduct_ID.setValue(productId);
		fProduct_ID.setEnabled(false);
		fProduct_ID.setReadWrite(false);
		actionPerformed(ConfirmPanel.A_REFRESH);
	}

	protected int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(getCtx());
	}

	protected int getAD_Org_ID()
	{
		final int warehouse_id = getM_Warehouse_ID();
		if (warehouse_id <= 0)
		{
			return 0;
		}
		return MWarehouse.get(getCtx(), warehouse_id).getAD_Org_ID();
	}

	protected int getM_Warehouse_ID()
	{
		final Object o = fWarehouse_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	protected int getS_Resource_ID()
	{
		final Object o = fResource_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	protected int getPlanner_ID()
	{
		final Object o = fPlanner_ID.getValue();
		return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
	}

	protected Timestamp getDueStart()
	{
		return fDateFrom.getTimestamp();
	}

	protected Timestamp getDueEnd()
	{
		return fDateTo.getTimestamp();
	}

	protected BigDecimal getQtyOnHand()
	{
		final BigDecimal bd = (BigDecimal)fQtyOnhand.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	/**
	 * Worker
	 */
	private class Worker extends Thread
	{
		/**
		 * Do Work (load data)
		 */
		@Override
		public void run()
		{
			try
			{
				lockUI(null);
				
				loadMRPRows();
				updateProjectedQtyOnHand();
			}
			finally
			{
				unlockUI(null);
			}
		}   // run
	}   // Worker

	/**
	 * Iterate all table rows and updates Projected QtyOnHand (QOH), which initially was set as actual QtyOnHand for that Warehouse/Product.
	 */
	private void updateProjectedQtyOnHand()
	{
		final Timestamp today = SystemTime.asDayTimestamp();

		//
		// Maps (M_Warehouse_ID, M_Product_ID) to Projected QOH
		final Map<ArrayKey, BigDecimal> warehouseProduct2qtyOnHandProjected = new HashMap<ArrayKey, BigDecimal>();

		final int rowCount = p_table.getRowCount();
		for (int row = 0; row < rowCount; row++)
		{
			final KeyNamePair warehouseKNP = (KeyNamePair)p_table.getValueAt(row, COLUMNNAME_M_Warehouse_ID);
			final KeyNamePair productKNP = (KeyNamePair)p_table.getValueAt(row, COLUMNNAME_M_Product_ID);
			final ArrayKey warehouseProductKey = Util.mkKey(warehouseKNP.getKey(), productKNP.getKey());

			//
			// Get current Projected QOH for row's warehouse/product.
			// If it's not found in our map, then we can safely take it from row because there initially we will have the actual QOH
			BigDecimal qtyOnHandProjected = warehouseProduct2qtyOnHandProjected.get(warehouseProductKey);
			if (qtyOnHandProjected == null)
			{
				qtyOnHandProjected = (BigDecimal)p_table.getValueAt(row, COLUMNNAME_QtyOnHandProjected);
				warehouseProduct2qtyOnHandProjected.put(warehouseProductKey, qtyOnHandProjected);
			}

			final ValueNamePair typeMrpVNP = (ValueNamePair)p_table.getValueAt(row, COLUMNNAME_TypeMRP);
			final String TypeMRP = typeMrpVNP.getValue();

			if (X_PP_MRP.TYPEMRP_Demand.equals(TypeMRP))
			{
				final Timestamp datePromised = (Timestamp)p_table.getValueAt(row, COLUMNNAME_DatePromised);

				final ValueNamePair orderTypeVNP = (ValueNamePair)p_table.getValueAt(row, COLUMNNAME_OrderType);
				final String OrderType = orderTypeVNP.getValue();
				final boolean isForecast = X_PP_MRP.ORDERTYPE_Forecast.equals(OrderType);
				final boolean isFutureDate = datePromised == null || datePromised.after(today);
				if (isForecast && !isFutureDate)
				{
					// Forcast is in Past, nothing to do, we are not considering it
				}
				else
				{
					BigDecimal qtyGrossReqs = (BigDecimal)p_table.getValueAt(row, COLUMNNAME_QtyGrossReq);
					if (qtyGrossReqs == null)
					{
						qtyGrossReqs = Env.ZERO;
					}
					qtyOnHandProjected = qtyOnHandProjected.subtract(qtyGrossReqs);
					p_table.setValueAt(qtyOnHandProjected, row, COLUMNNAME_QtyOnHandProjected);
				}
			}
			else if (X_PP_MRP.TYPEMRP_Supply.equals(TypeMRP))
			{
				BigDecimal QtyScheduledReceipts = (BigDecimal)p_table.getValueAt(row, COLUMNNAME_QtyScheduledReceipts);
				if (QtyScheduledReceipts == null)
				{
					QtyScheduledReceipts = Env.ZERO;
				}

				BigDecimal QtyPlan = (BigDecimal)p_table.getValueAt(row, COLUMNNAME_PlannedQty);
				if (QtyPlan == null)
				{
					QtyPlan = Env.ZERO;
				}

				qtyOnHandProjected = qtyOnHandProjected.add(QtyScheduledReceipts.add(QtyPlan));
				p_table.setValueAt(qtyOnHandProjected, row, COLUMNNAME_QtyOnHandProjected);
			}
			else
			{
				// NOTE: there is no other case. TypeMRP is Supply or Demand.
			}

			//
			// Put back the current ProjectedQtyOnHand
			warehouseProduct2qtyOnHandProjected.put(warehouseProductKey, qtyOnHandProjected);
		}
	}
}
