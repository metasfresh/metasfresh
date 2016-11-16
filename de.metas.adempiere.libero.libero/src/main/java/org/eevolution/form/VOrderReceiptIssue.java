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
 *                 Teo Sarca, http://www.arhipac.ro                           *
 ******************************************************************************/

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
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VPAttribute;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MColumn;
import org.compiere.model.MLocator;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPAttributeLookup;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MTab;
import org.compiere.model.MWarehouse;
import org.compiere.model.MWindow;
import org.compiere.plaf.CompiereColor;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.swing.CTextPane;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnable;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.PPOrderBOMLineModel;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ASyncProcess;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

/**
 * 
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 * @author Teo Sarca, http://www.arhipac.ro
 */

@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class VOrderReceiptIssue extends CPanel implements FormPanel,
		ActionListener, VetoableChangeListener, ChangeListener,
		ListSelectionListener, TableModelListener, ASyncProcess
{
	private static final long serialVersionUID = 1L;

	//
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/** Window No */
	private int m_WindowNo = 0;
	/** FormFrame */
	private FormFrame m_frame;
	private StatusBar statusBar = new StatusBar();

	private static Logger log = LogManager.getLogger(VOrderReceiptIssue.class);

	// Variables declaration for visual elements - do not modify
	private CPanel mainPanel = new CPanel();
	private CPanel Generate = new CPanel();
	private CPanel PanelBottom = new CPanel();
	private CPanel PanelCenter = new CPanel();
	private CPanel northPanel = new CPanel();
	private CButton Process = new CButton();
	private CPanel ReceiptIssueOrder = new CPanel();
	private javax.swing.JTabbedPane TabsReceiptsIssue = new JTabbedPane();
	private VPAttribute attribute = null;
	private CLabel attributeLabel = new CLabel();
	private VNumber orderedQtyField = new VNumber("QtyOrdered", false, false, false, DisplayType.Quantity, "QtyOrdered");
	private CLabel orderedQtyLabel = new CLabel();
	private VNumber deliveredQtyField = new VNumber("QtyDelivered", false, false, false, DisplayType.Quantity, "QtyDelivered");
	private CLabel deliveredQtyLabel = new CLabel();
	private VNumber openQtyField = new VNumber("QtyOpen", false, false, false, DisplayType.Quantity, "QtyOpen");
	private CLabel openQtyLabel = new CLabel();
	private VNumber toDeliverQty = new VNumber("QtyToDeliver", true, false, true, DisplayType.Quantity, "QtyToDeliver");
	private CLabel toDeliverQtyLabel = new CLabel();
	private javax.swing.JScrollPane issueScrollPane = new JScrollPane();
	private MiniTable issue = new MiniTable();
	private VDate movementDateField = new VDate("MovementDate", true, false, true, DisplayType.Date, "MovementDate");
	private CLabel movementDateLabel = new CLabel();
	private VLookup orderField = null;
	private CLabel orderLabel = new CLabel();
	private VNumber rejectQty = new VNumber("Qtyreject", false, false, true, DisplayType.Quantity, "QtyReject");
	private CLabel rejectQtyLabel = new CLabel();
	private VLookup resourceField = null;
	private CLabel resourceLabel = new CLabel();
	private VLookup warehouseField = null;
	private CLabel warehouseLabel = new CLabel();
	private VNumber scrapQtyField = new VNumber("Qtyscrap", false, false, true, DisplayType.Quantity, "Qtyscrap");
	private CLabel scrapQtyLabel = new CLabel();
	private CLabel backflushGroupLabel = new CLabel(msgBL.translate(Env.getCtx(), "BackflushGroup"));
	private CTextField backflushGroup = new CTextField(10);
	private CLabel productLabel = new CLabel(msgBL.translate(Env.getCtx(), "M_Product_ID"));
	private VLookup productField = null;
	private CLabel uomLabel = new CLabel(msgBL.translate(Env.getCtx(), "C_UOM_ID"));
	private VLookup uomField = null;
	private CLabel uomorderLabel = new CLabel(msgBL.translate(Env.getCtx(), "MO UOM"));
	private VLookup uomorderField = null;
	private CLabel locatorLabel = new CLabel(msgBL.translate(Env.getCtx(), "M_Locator_ID"));
	private VLocator locatorField = null;
	private CLabel labelcombo = new CLabel(msgBL.translate(Env.getCtx(), "DeliveryRule"));
	private VComboBox pickcombo = new VComboBox();
	private CLabel QtyBatchsLabel = new CLabel();
	private VNumber qtyBatchsField = new VNumber("QtyBatchs", false, false, false, DisplayType.Quantity, "QtyBatchs");
	private CLabel QtyBatchSizeLabel = new CLabel();
	private VNumber qtyBatchSizeField = new VNumber("QtyBatchSize", false, false, false, DisplayType.Quantity, "QtyBatchSize");
	private CTextPane info = new CTextPane();

	/**
	 * Initialize Panel
	 * 
	 * @param WindowNo window
	 * @param frame frame
	 */
	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		log.info("VOrderReceipIssue.init - WinNo=" + m_WindowNo);

		try
		{
			// UI
			fillPicks();
			jbInit();
			//
			dynInit();
			pickcombo.addActionListener(this);
			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch (Exception e)
		{
			log.error("VOrderReceipIssue.init", e);
		}
	} // init

	/**
	 * Fill Picks Column_ID from C_Order This is only run as part of the windows initialization process
	 * 
	 * @throws Exception if Lookups cannot be initialized
	 */
	private void fillPicks() throws Exception
	{

		Properties ctx = Env.getCtx();

		MLookup orderL = MLookupFactory.get(ctx, m_WindowNo,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_PP_Order_ID),
				DisplayType.Search, "PP_Order_ID", 0, false,
				"PP_Order.DocStatus = '" + MPPOrder.DOCACTION_Complete + "'");

		orderField = new VLookup("PP_Order_ID", false, false, true, orderL);
		orderField.setBackground(AdempierePLAF.getInfoBackground());
		orderField.addVetoableChangeListener(this);

		MLookup resourceL = MLookupFactory.get(ctx, m_WindowNo, 0,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_S_Resource_ID),
				DisplayType.TableDir);
		resourceField = new VLookup("S_Resource_ID", false, false, false, resourceL);

		MLookup warehouseL = MLookupFactory.get(ctx, m_WindowNo, 0,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_M_Warehouse_ID),
				DisplayType.TableDir);
		warehouseField = new VLookup("M_Warehouse_ID", false, false, false, warehouseL);

		MLookup productL = MLookupFactory.get(ctx, m_WindowNo, 0,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_M_Product_ID),
				DisplayType.TableDir);
		productField = new VLookup("M_Product_ID", false, false, false, productL);

		MLookup uomL = MLookupFactory.get(ctx, m_WindowNo, 0,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_C_UOM_ID),
				DisplayType.TableDir);
		uomField = new VLookup("C_UOM_ID", false, false, false, uomL);

		MLookup uomorderL = MLookupFactory.get(ctx, m_WindowNo, 0,
				MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_C_UOM_ID),
				DisplayType.TableDir);
		uomorderField = new VLookup("C_UOM_ID", false, false, false, uomorderL);

		MLocatorLookup locatorL = new MLocatorLookup(ctx, m_WindowNo);
		locatorField = new VLocator("M_Locator_ID", true, false, true, locatorL, m_WindowNo);

		MPAttributeLookup attributeL = new MPAttributeLookup(ctx, m_WindowNo);
		attribute = new VPAttribute(false, false, true, m_WindowNo, attributeL);
		attribute.setValue(0);
		// Tab, Window
		int m_Window = MWindow.getWindow_ID("Manufacturing Order");
		GridFieldVO vo = GridFieldVO.createStdField(ctx, m_WindowNo, 0,
				m_Window, MTab.getTab_ID(m_Window, "Order"), false, false, false);
		// M_AttributeSetInstance_ID
		vo.setAD_Column_ID(MColumn.getColumn_ID(MPPOrder.Table_Name, MPPOrder.COLUMNNAME_M_AttributeSetInstance_ID));
		GridField field = new GridField(vo);
		attribute.setField(field);
		// 4Layers - Further init
		scrapQtyField.setValue(Env.ZERO);
		rejectQty.setValue(Env.ZERO);
		// 4Layers - end
		pickcombo.addItem(new KeyNamePair(1, msgBL.translate(Env.getCtx(), "IsBackflush")));
		pickcombo.addItem(new KeyNamePair(2, msgBL.translate(Env.getCtx(), "OnlyIssue")));
		pickcombo.addItem(new KeyNamePair(3, msgBL.translate(Env.getCtx(), "OnlyReceipt")));
		pickcombo.addActionListener(this);
		Process.addActionListener(this);
		toDeliverQty.addActionListener(this);
		scrapQtyField.addActionListener(this);

		movementDateField.setValue(SystemTime.asDayTimestamp()); // metas
	} // fillPicks

	/**
	 * Static Init. Places static visual elements into the window. This is only run as part of the windows initialization process
	 * 
	 * <pre>
	 *  mainPanel
	 *      northPanel
	 *      centerPanel
	 *          xMatched
	 *          xPanel
	 *          xMathedTo
	 *      southPanel
	 * </pre>
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		setLayout(new java.awt.BorderLayout());
		mainPanel.setLayout(new java.awt.BorderLayout());
		ReceiptIssueOrder.setLayout(new java.awt.BorderLayout());
		PanelCenter.setLayout(new java.awt.BorderLayout());
		issueScrollPane.setBorder(new javax.swing.border.TitledBorder(""));
		issueScrollPane.setViewportView(issue);
		PanelCenter.add(issueScrollPane, java.awt.BorderLayout.CENTER);
		ReceiptIssueOrder.add(PanelCenter, java.awt.BorderLayout.CENTER);
		Process.setText(msgBL.translate(Env.getCtx(), "OK"));
		PanelBottom.add(Process);
		ReceiptIssueOrder.add(PanelBottom, java.awt.BorderLayout.SOUTH);
		northPanel.setLayout(new java.awt.GridBagLayout());
		orderLabel.setText(msgBL.translate(Env.getCtx(), "PP_Order_ID"));

		northPanel.add(orderLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(orderField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		resourceLabel.setText(msgBL.translate(Env.getCtx(), "S_Resource_ID"));
		northPanel.add(resourceLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(resourceField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		warehouseLabel.setText(msgBL.translate(Env.getCtx(), "M_Warehouse_ID"));
		northPanel.add(warehouseLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(warehouseField, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(warehouseLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));

		// Product

		northPanel.add(productLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(productField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(uomLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(uomField, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(uomorderLabel, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(uomorderField, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		orderedQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyOrdered"));
		northPanel.add(orderedQtyLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(orderedQtyField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		deliveredQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyDelivered"));
		northPanel.add(deliveredQtyLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(deliveredQtyField, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		openQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyOpen"));
		northPanel.add(openQtyLabel, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(openQtyField, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		QtyBatchsLabel.setText(msgBL.translate(Env.getCtx(), "QtyBatchs"));
		northPanel.add(QtyBatchsLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(qtyBatchsField, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		QtyBatchSizeLabel.setText(msgBL.translate(Env.getCtx(), "QtyBatchSize"));
		northPanel.add(QtyBatchSizeLabel, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(qtyBatchSizeField, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(labelcombo, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(pickcombo, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		toDeliverQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyToDeliver"));
		northPanel.add(toDeliverQtyLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(toDeliverQty, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		scrapQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyScrap"));
		northPanel.add(scrapQtyLabel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(scrapQtyField, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		rejectQtyLabel.setText(msgBL.translate(Env.getCtx(), "QtyReject"));
		northPanel.add(rejectQtyLabel, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(rejectQty, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		movementDateLabel.setText(msgBL.translate(Env.getCtx(), "MovementDate"));
		northPanel.add(movementDateLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(movementDateField, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		locatorLabel.setText(msgBL.translate(Env.getCtx(), "M_Locator_ID"));
		northPanel.add(locatorLabel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(locatorField, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		attributeLabel.setText(msgBL.translate(Env.getCtx(), "M_AttributeSetInstance_ID"));
		northPanel.add(attributeLabel, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(attribute, new GridBagConstraints(5, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(backflushGroupLabel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(backflushGroup, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));

		ReceiptIssueOrder.add(northPanel, java.awt.BorderLayout.NORTH);
		TabsReceiptsIssue.add(ReceiptIssueOrder, msgBL.translate(Env.getCtx(), "IsShipConfirm"));
		TabsReceiptsIssue.add(Generate, msgBL.translate(Env.getCtx(), "Generate"));
		Generate.setLayout(new BorderLayout());
		Generate.add(info, BorderLayout.CENTER);
		Generate.setEnabled(false);
		info.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		info.setEditable(false);
		TabsReceiptsIssue.addChangeListener(this);
		add(TabsReceiptsIssue, java.awt.BorderLayout.CENTER);
		mainPanel.add(TabsReceiptsIssue, java.awt.BorderLayout.CENTER);
		add(mainPanel, java.awt.BorderLayout.NORTH);
	} // jbInit

	/**
	 * Dynamic Init. Table Layout, Visual, Listener This is only run as part of the windows initialization process
	 */
	private void dynInit()
	{
		disableToDeliver();
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID); // 0
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_IsCritical); // 1
		issue.addColumn("Value"); // 2
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID); // 3
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID); // 4
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_M_AttributeSetInstance_ID); // 5
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered); // 6
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyDelivered); // 7
		issue.addColumn("QtyToDeliver"); // 8
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyScrap); // 9
		issue.addColumn("QtyOnHand"); // 10
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyReserved); // 11
		issue.addColumn("QtyAvailable"); // 12
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_M_Locator_ID); // 13
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID); // 14
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyBOM); // 15
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_IsQtyPercentage); // 16
		issue.addColumn(I_PP_Order_BOMLine.COLUMNNAME_QtyBatch); // 17

		issue.setMultiSelection(true);
		issue.setRowSelectionAllowed(true);

		// set details
		issue.setColumnClass(0, IDColumn.class, false, " ");
		issue.setColumnClass(1, Boolean.class, true, msgBL.translate(Env.getCtx(), "IsCritical"));
		issue.setColumnClass(2, String.class, true, msgBL.translate(Env.getCtx(), "Value"));
		issue.setColumnClass(3, KeyNamePair.class, true, msgBL.translate(Env.getCtx(), "M_Product_ID"));
		issue.setColumnClass(4, KeyNamePair.class, true, msgBL.translate(Env.getCtx(), "C_UOM_ID"));
		issue.setColumnClass(5, String.class, true, msgBL.translate(Env.getCtx(), "M_AttributeSetInstance_ID"));
		issue.setColumnClass(6, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyRequired"));
		issue.setColumnClass(7, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyDelivered"));
		issue.setColumnClass(8, BigDecimal.class, false, msgBL.translate(Env.getCtx(), "QtyToDeliver"));
		issue.setColumnClass(9, BigDecimal.class, false, msgBL.translate(Env.getCtx(), "QtyScrap"));
		issue.setColumnClass(10, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyOnHand"));
		issue.setColumnClass(11, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyReserved"));
		issue.setColumnClass(12, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyAvailable"));
		issue.setColumnClass(13, String.class, true, msgBL.translate(Env.getCtx(), "M_Locator_ID"));
		issue.setColumnClass(14, KeyNamePair.class, true, msgBL.translate(Env.getCtx(), "M_Warehouse_ID"));
		issue.setColumnClass(15, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyBom"));
		issue.setColumnClass(16, Boolean.class, true, msgBL.translate(Env.getCtx(), "IsQtyPercentage"));
		issue.setColumnClass(17, BigDecimal.class, true, msgBL.translate(Env.getCtx(), "QtyBatch"));

		issue.autoSize();
		issue.getModel().addTableModelListener(this);

		CompiereColor.setBackground(this);
		issue.setCellSelectionEnabled(true);
		issue.getSelectionModel().addListSelectionListener(this);
		issue.setRowCount(0);
	} // dynInit

	/**
	 * Called when events occur in the window
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.debug("Event:" + e.getSource());
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}

		if (e.getSource().equals(Process))
		{
			if (getMovementDate() == null)
			{
				JOptionPane.showMessageDialog(null, msgBL.getMsg(Env.getCtx(), "NoDate"), "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if ((isOnlyReceipt() || isBackflush()) && getM_Locator_ID() <= 0)
			{
				JOptionPane.showMessageDialog(null, msgBL.getMsg(Env.getCtx(), "NoLocator"), "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Switch Tabs
			TabsReceiptsIssue.setSelectedIndex(1);

			generateSummaryTable();

			if (ADialog.ask(m_WindowNo, this, "Update"))
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				if (cmd_process())
				{
					dispose();
					return;
				}
				setCursor(Cursor.getDefaultCursor());
			}
			TabsReceiptsIssue.setSelectedIndex(0);
		}

		if (e.getSource().equals(toDeliverQty) || e.getSource().equals(scrapQtyField))
		{
			if (getPP_Order_ID() > 0 && isBackflush())
			{
				executeQuery();
			}
		}

		if (e.getSource().equals(pickcombo))
		{
			if (isOnlyReceipt())
			{
				enableToDeliver();
				locatorLabel.setVisible(true);
				locatorField.setVisible(true);
				attribute.setVisible(true);
				attributeLabel.setVisible(true);
				issue.setVisible(false);
			}
			else if (isOnlyIssue())
			{
				disableToDeliver();
				locatorLabel.setVisible(false);
				locatorField.setVisible(false);
				attribute.setVisible(false);
				attributeLabel.setVisible(false);
				issue.setVisible(true);
				executeQuery();
			}
			else if (isBackflush())
			{
				enableToDeliver();
				locatorLabel.setVisible(true);
				locatorField.setVisible(true);
				attribute.setVisible(true);
				attributeLabel.setVisible(true);
				issue.setVisible(true);
				executeQuery();
			}
			setToDeliverQty(getOpenQty()); // reset toDeliverQty to openQty
		}
	}

	public void enableToDeliver()
	{
		setToDeliver(true);
	}

	public void disableToDeliver()
	{
		setToDeliver(false);
	}

	private void setToDeliver(Boolean state)
	{
		toDeliverQty.getComponent(0).setEnabled(state); // textbox component
		toDeliverQty.getComponent(1).setEnabled(state); // button component
		scrapQtyLabel.setVisible(state);
		scrapQtyField.setVisible(state);
		rejectQtyLabel.setVisible(state);
		rejectQty.setVisible(state);
	}

	/**
	 * Queries for and fills the table in the lower half of the screen This is only run if isBackflush() or isOnlyIssue
	 */
	private void executeQuery()
	{
		issue.removeAll();

		final String sql = "SELECT "
				+ "obl.PP_Order_BOMLine_ID," // 1
				+ "obl.IsCritical," // 2
				+ "p.Value," // 3
				+ "obl.M_Product_ID,p.Name," // 4,5
				+ "p.C_UOM_ID,u.Name," // 6,7
				+ "obl.QtyRequiered," // 8
				+ "obl.QtyReserved," // 9
				+ "bomQtyAvailable(obl.M_Product_ID,obl.M_Warehouse_ID,0 ) AS QtyAvailable," // 10
				+ "bomQtyOnHand(obl.M_Product_ID,obl.M_Warehouse_ID,0) AS QtyOnHand," // 11
				+ "p.M_Locator_ID," // 12
				+ "obl.M_Warehouse_ID,w.Name," // 13,14
				+ "obl.QtyBom," // 15
				+ "obl.isQtyPercentage," // 16
				+ "obl.QtyBatch," // 17
				+ "obl.ComponentType," // 18
				+ "obl.QtyRequiered - QtyDelivered AS QtyOpen," // 19
				+ "obl.QtyDelivered" // 20
				+ " FROM PP_Order_BOMLine obl"
				+ " INNER JOIN M_Product p ON (obl.M_Product_ID = p.M_Product_ID) "
				+ " INNER JOIN C_UOM u ON (p.C_UOM_ID = u.C_UOM_ID) "
				+ " INNER JOIN M_Warehouse w ON (w.M_Warehouse_ID = obl.M_Warehouse_ID) "
				+ " WHERE obl.PP_Order_ID = ?"
				+ " ORDER BY obl." + I_PP_Order_BOMLine.COLUMNNAME_Line;
		// reset table
		int row = 0;
		issue.setRowCount(row);
		// Execute
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getPP_Order_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// extend table
				issue.setRowCount(row + 1);
				// set values
				// issue.
				IDColumn id = new IDColumn(rs.getInt(1));
				BigDecimal qtyBom = rs.getBigDecimal(15);
				Boolean isQtyPercentage = rs.getString(16).equals("Y");
				Boolean isCritical = rs.getString(2).equals("Y");
				BigDecimal qtyBatch = rs.getBigDecimal(17);
				BigDecimal qtyRequired = rs.getBigDecimal(8);
				BigDecimal qtyOnHand = rs.getBigDecimal(11);
				BigDecimal qtyOpen = rs.getBigDecimal(19);
				BigDecimal qtyDelivered = rs.getBigDecimal(20);
				String componentType = rs.getString(18);
				BigDecimal toDeliverQty = getToDeliverQty();
				BigDecimal openQty = getOpenQty();
				BigDecimal scrapQty = getScrapQty();
				BigDecimal componentToDeliverQty = Env.ZERO;
				BigDecimal componentScrapQty = Env.ZERO;
				BigDecimal componentQtyReq = Env.ZERO;
				BigDecimal componentQtyToDel = Env.ZERO;

				id.setSelected(isOnlyReceipt());

				issue.setValueAt(id, row, 0);                                                // PP_OrderBOMLine_ID
				issue.setValueAt(isCritical, row, 1);                       				 // IsCritical
				issue.setValueAt(rs.getString(3), row, 2);                                   // Product's Search key
				issue.setValueAt(new KeyNamePair(rs.getInt(4), rs.getString(5)), row, 3);    // Product
				issue.setValueAt(new KeyNamePair(rs.getInt(6), rs.getString(7)), row, 4);    // UOM
				// ... 5 - ASI
				issue.setValueAt(qtyRequired, row, 6);                                       // QtyRequiered
				issue.setValueAt(qtyDelivered, row, 7);                              		 // QtyDelivered

				// ... 8, 9, 10 - QtyToDeliver, QtyScrap, QtyOnHand
				issue.setValueAt(qtyOnHand, row, 10);                                        // OnHand
				issue.setValueAt(rs.getBigDecimal(9), row, 11);                              // QtyReserved
				issue.setValueAt(rs.getBigDecimal(10), row, 12);                             // QtyAvailable
				// ... 13 - M_Locator_ID
				issue.setValueAt(new KeyNamePair(rs.getInt(13), rs.getString(14)), row, 14); // Warehouse
				issue.setValueAt(qtyBom, row, 15);                                           // QtyBom
				issue.setValueAt(isQtyPercentage, row, 16);                                  // isQtyPercentage
				issue.setValueAt(qtyBatch, row, 17);                                         // QtyBatch

				if (componentType.equals(MPPProductBOMLine.COMPONENTTYPE_Component)
						|| componentType.equals(MPPProductBOMLine.COMPONENTTYPE_Packing))
				{
					// If the there is product on hand and product is required the product should be selected
					id.setSelected(qtyOnHand.signum() > 0 && qtyRequired.signum() > 0);

					if (isQtyPercentage)
					{
						// If the quantity of product is calculated as a percentage
						BigDecimal qtyBatchPerc = qtyBatch.divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);

						if (isBackflush())
						{ // Is Backflush - Calculate Component from Qty To Deliver
							if (qtyRequired.signum() == 0 || qtyOpen.signum() == 0)
							{
								componentToDeliverQty = Env.ZERO;
							}
							else
							{
								componentToDeliverQty = toDeliverQty.multiply(qtyBatchPerc);

								if (qtyRequired.subtract(qtyDelivered).signum() < 0 | componentToDeliverQty.signum() == 0)
									componentToDeliverQty = qtyRequired.subtract(qtyDelivered);

							}

							if (componentToDeliverQty.signum() != 0)
							{
								// TODO: arhipac: teo_sarca: is this a bug ? ...instead of toDeliverQty, qtyRequired should be used!
								// componentQtyReq = toDeliverQty.multiply(qtyBatchPerc); // TODO: set scale 4
								componentQtyToDel = componentToDeliverQty.setScale(4, BigDecimal.ROUND_HALF_UP);
								// issue.setValueAt(toDeliverQty.multiply(qtyBatchPerc), row, 6); // QtyRequiered
								issue.setValueAt(componentToDeliverQty, row, 8); // QtyToDelivery

							}
						}
						else
						{ // Only Issue - Calculate Component from Open Qty
							componentToDeliverQty = qtyOpen;
							if (componentToDeliverQty.signum() != 0)
							{
								componentQtyReq = openQty.multiply(qtyBatchPerc); // scale 4
								componentQtyToDel = componentToDeliverQty.setScale(4, BigDecimal.ROUND_HALF_UP);
								issue.setValueAt(componentToDeliverQty.setScale(8, BigDecimal.ROUND_HALF_UP), row, 8); // QtyToDelivery
								issue.setValueAt(openQty.multiply(qtyBatchPerc), row, 6); // QtyRequiered
							}
						}

						if (scrapQty.signum() != 0)
						{
							componentScrapQty = scrapQty.multiply(qtyBatchPerc);
							if (componentScrapQty.signum() != 0)
							{
								issue.setValueAt(componentScrapQty, row, 9); // QtyScrap
							}
						}
					}
					else
					{ // Absolute Qtys (not Percentage)
						if (isBackflush())
						{ // Is Backflush - Calculate Component from Qty To Deliver
							componentToDeliverQty = toDeliverQty.multiply(qtyBom); // TODO: set Number scale
							if (componentToDeliverQty.signum() != 0)
							{
								componentQtyReq = toDeliverQty.multiply(qtyBom);
								componentQtyToDel = componentToDeliverQty;
								issue.setValueAt(componentQtyReq, row, 6); // QtyRequiered
								issue.setValueAt(componentToDeliverQty, row, 8); // QtyToDelivery
							}
						}
						else
						{ // Only Issue - Calculate Component from Open Qty
							componentToDeliverQty = qtyOpen;
							if (componentToDeliverQty.signum() != 0)
							{
								componentQtyReq = openQty.multiply(qtyBom);
								componentQtyToDel = componentToDeliverQty;
								issue.setValueAt(componentQtyReq, row, 6); // QtyRequiered
								issue.setValueAt(componentToDeliverQty, row, 8); // QtyToDelivery
							}
						}

						if (scrapQty.signum() != 0)
						{
							componentScrapQty = scrapQty.multiply(qtyBom); // TODO: set Number scale
							if (componentScrapQty.signum() != 0)
							{
								issue.setValueAt(componentScrapQty, row, 9); // ScrapQty
							}
						}

					}
				}
				else if (componentType.equals(MPPProductBOMLine.COMPONENTTYPE_Tools))
				{
					componentToDeliverQty = qtyBom; // TODO; set Number scale
					if (componentToDeliverQty.signum() != 0)
					{
						componentQtyReq = qtyBom;
						componentQtyToDel = componentToDeliverQty;
						issue.setValueAt(qtyBom, row, 6); // QtyRequiered
						issue.setValueAt(componentToDeliverQty, row, 8); // QtyToDelivery
					}
				}
				else
				{
					issue.setValueAt(Env.ZERO, row, 6); // QtyRequiered
					issue.setValueAt(Env.ZERO, row, 8); // QtyToDelivery
				}

				row++;

				if (isOnlyIssue() || isBackflush())
				{
					int warehouse_id = rs.getInt(13);
					int product_id = rs.getInt(4);
					row += lotes(row, id, warehouse_id, product_id, componentQtyReq, componentQtyToDel);
				}
			} // while
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		issue.autoSize();
	} // executeQuery

	/**
	 * Adds Attribute Set Instances Quantities to table. Extension to {@link #executeQuery()}
	 * 
	 * @return how many lines were added
	 */
	private int lotes(int row, IDColumn id,
			int Warehouse_ID, int M_Product_ID,
			BigDecimal qtyRequired, BigDecimal qtyToDelivery)
	{
		int linesNo = 0;
		BigDecimal qtyRequiredActual = qtyRequired;

		final String sql = "SELECT "
				+ "s.M_Product_ID , s.QtyOnHand, s.M_AttributeSetInstance_ID"
				+ ", p.Name, masi.Description, l.Value, w.Value, w.M_warehouse_ID,p.Value"
				+ "  FROM M_Storage s "
				+ " INNER JOIN M_Product p ON (s.M_Product_ID = p.M_Product_ID) "
				+ " INNER JOIN C_UOM u ON (u.C_UOM_ID = p.C_UOM_ID) "
				+ " INNER JOIN M_AttributeSetInstance masi ON (masi.M_AttributeSetInstance_ID = s.M_AttributeSetInstance_ID) "
				+ " INNER JOIN M_Warehouse w ON (w.M_Warehouse_ID = ?) "
				+ " INNER JOIN M_Locator l ON(l.M_Warehouse_ID=w.M_Warehouse_ID and s.M_Locator_ID=l.M_Locator_ID) "
				+ " WHERE s.M_Product_ID = ? and s.QtyOnHand > 0 "
				+ " and s.M_AttributeSetInstance_ID <> 0 "
				+ " ORDER BY s.Created ";
		// Execute
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				issue.setRowCount(row + 1);

				// Qty On Hand
				final BigDecimal qtyOnHand = rs.getBigDecimal(2);

				// ID/M_AttributeSetInstance_ID
				IDColumn id1 = new IDColumn(rs.getInt(3));
				id1.setSelected(false);
				issue.setRowSelectionAllowed(true);
				issue.setValueAt(id1, row, 0);
				// Product
				KeyNamePair productkey = new KeyNamePair(rs.getInt(1), rs.getString(4));
				issue.setValueAt(productkey, row, 3);
				// QtyOnHand
				issue.setValueAt(qtyOnHand, row, 10);
				// ASI
				issue.setValueAt(rs.getString(5), row, 5);
				// Locator
				issue.setValueAt(rs.getString(6), row, 13);
				// Warehouse
				KeyNamePair m_warehousekey = new KeyNamePair(rs.getInt(8), rs.getString(7));
				issue.setValueAt(m_warehousekey, row, 14);
				// Qty Required:
				if (qtyRequiredActual.compareTo(qtyOnHand) < 0)
				{
					issue.setValueAt(qtyRequiredActual.signum() > 0 ? qtyRequiredActual : Env.ZERO, row, 6);
				}
				else
				{
					issue.setValueAt(qtyOnHand, row, 6);
				}
				qtyRequiredActual = qtyRequiredActual.subtract(qtyOnHand);

				linesNo++;
				row++;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return linesNo;
	}

	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}

	@Override
	public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException
	{
		String name = e.getPropertyName();
		Object value = e.getNewValue();
		log.debug("VOrderReceip.vetoableChange - " + name + "=" + value);
		if (value == null)
			return;

		// PP_Order_ID
		if (name.equals("PP_Order_ID"))
		{
			orderField.setValue(value);

			MPPOrder pp_order = getPP_Order();
			if (pp_order != null)
			{
				setS_Resource_ID(pp_order.getS_Resource_ID());
				setM_Warehouse_ID(pp_order.getM_Warehouse_ID());

				if (pp_order.getM_Warehouse_ID() > 0)
				{
					final MWarehouse warehouse = MWarehouse.get(Env.getCtx(), pp_order.getM_Warehouse_ID());
					final MLocator locator = MLocator.getDefault(warehouse);
					setM_Locator_ID(locator == null ? -1 : locator.getM_Locator_ID());
				}

				setDeliveredQty(pp_order.getQtyDelivered());
				setOrderedQty(pp_order.getQtyOrdered());
				// m_PP_order.getQtyOrdered().subtract(m_PP_order.getQtyDelivered());
				setQtyBatchs(pp_order.getQtyBatchs());
				setQtyBatchSize(pp_order.getQtyBatchSize());
				setOpenQty(pp_order.getQtyOrdered().subtract(pp_order.getQtyDelivered()));
				setToDeliverQty(getOpenQty());
				setM_Product_ID(pp_order.getM_Product_ID());
				MProduct m_product = MProduct.get(Env.getCtx(), pp_order.getM_Product_ID());
				setC_UOM_ID(m_product.getC_UOM_ID());
				setOrder_UOM_ID(pp_order.getC_UOM_ID());
				// Default ASI defined from the Parent BOM Order
				setM_AttributeSetInstance_ID(pp_order.getPP_Order_BOM().getM_AttributeSetInstance_ID());
				pickcombo.setSelectedIndex(0);  // default to first entry - isBackflush
			}
		} // PP_Order_ID
	}

	/**
	 * Performs what ever task is attached to the combo box
	 * 
	 * @return Whether the process was successful or not
	 */
	private boolean cmd_process()
	{

		if (isOnlyReceipt() || isBackflush())
		{
			if (getM_Locator_ID() <= 0)
			{
				JOptionPane.showMessageDialog(null, msgBL.getMsg(Env.getCtx(), "NoLocator"), "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (getPP_Order() == null || getMovementDate() == null)
		{
			return false;
		}
		final boolean isCloseDocument = ADialog.ask(m_WindowNo, this, msgBL.parseTranslation(Env.getCtx(), "@IsCloseDocument@ : " + getPP_Order().getDocumentNo()));

		try
		{
			Services.get(ITrxManager.class).run(new TrxRunnable()
			{
				@Override
				public void run(String trxName)
				{
					MPPOrder order = new MPPOrder(Env.getCtx(), getPP_Order_ID(), trxName);
					if (isBackflush() || isOnlyIssue())
					{
						createIssue(order);
					}
					if (isOnlyReceipt() || isBackflush())
					{
						final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
						final IReceiptCostCollectorCandidate candidate = ppCostCollectorBL.createReceiptCostCollectorCandidate();
						candidate.setPP_Order(order);
						candidate.setMovementDate(getMovementDate());
						candidate.setQtyToReceive(getToDeliverQty());
						candidate.setQtyScrap(getScrapQty());
						candidate.setQtyReject(getRejectQty());
						candidate.setM_Product(order.getM_Product());
						candidate.setC_UOM(order.getC_UOM());
						candidate.setM_Locator_ID(getM_Locator_ID());
						candidate.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
						ppCostCollectorBL.createReceipt(candidate);
						if (isCloseDocument)
						{
							order.setDateFinish(getMovementDate());
							order.closeIt();
							order.saveEx();
						}
					}
				}
			});
		}
		catch (Exception e)
		{
			log.warn(e.getLocalizedMessage(), e);
			ADialog.error(m_WindowNo, this, "Error", e.getLocalizedMessage());
			return false;
		}
		finally
		{
			m_PP_order = null;
		}

		return true;
	}

	private void createIssue(MPPOrder order)
	{
		Timestamp movementDate = getMovementDate();
		Timestamp minGuaranteeDate = movementDate;
		boolean isCompleteQtyDeliver = false;

		// metas : cg task: 06004 - refactored a bit : start

		final Map<Integer, PPOrderBOMLineModel> m_issue = new HashMap<Integer, PPOrderBOMLineModel>();

		int row = 0;
		// Check Available On Hand Qty
		for (int i = 0; i < issue.getRowCount(); i++)
		{
			final IDColumn id = (IDColumn)issue.getValueAt(i, 0);
			final KeyNamePair key = new KeyNamePair(id.getRecord_ID(), id.isSelected() ? "Y" : "N");
			final boolean isCritical = (Boolean)issue.getValueAt(i, 1); // 1 - IsCritical
			final String value = (String)issue.getValueAt(i, 2); // 2 - Value
			final int M_Product_ID = ((KeyNamePair)issue.getValueAt(i, 3)).getKey(); // 3 - Product id
			final BigDecimal qtyToDeliver = getValueBigDecimal(i, 8); // 4 - QtyToDeliver
			final BigDecimal qtyScrapComponent = getValueBigDecimal(i, 9); // 5 - QtyScrapComponent

			final PPOrderBOMLineModel bomLineModel = new PPOrderBOMLineModel(key, isCritical, value, M_Product_ID, qtyToDeliver, qtyScrapComponent);
			//
			m_issue.put(row, bomLineModel);
			row++;
		}

		isCompleteQtyDeliver = MPPOrder.isQtyAvailable(order, m_issue, minGuaranteeDate);

		if (!isCompleteQtyDeliver)
		{
			ADialog.error(m_WindowNo, this, "NoQtyAvailable");
			throw new LiberoException("@NoQtyAvailable@");
		}

		for (int i = 0; i < m_issue.size(); i++)
		{
			final PPOrderBOMLineModel model = m_issue.get(i);

			final KeyNamePair key = model.getKnp();
			boolean isSelected = key.getName().equals("Y");
			if (key == null || !isSelected)
			{
				continue;
			}

			final String value = model.getValue();
			final int M_Product_ID = model.getM_Product_ID();

			I_PP_Order_BOMLine orderbomLine = null;
			int PP_Order_BOMLine_ID = 0;
			int M_AttributeSetInstance_ID = 0;

			final BigDecimal qtyToDeliver = model.getQtyToDeliver();
			final BigDecimal qtyScrapComponent = model.getQtyScrapComponent();

			final I_M_Product product = InterfaceWrapperHelper.create(order.getCtx(), I_M_Product.Table_Name, M_Product_ID, I_M_Product.class, order.get_TrxName());
			if (product != null && product.getM_Product_ID() != 0 && Services.get(IProductBL.class).isStocked(product))
			{

				if (value == null && isSelected)
				{
					M_AttributeSetInstance_ID = key.getKey();
					orderbomLine = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLine(order, product);
					if (orderbomLine != null)
					{
						PP_Order_BOMLine_ID = orderbomLine.getPP_Order_BOMLine_ID();
					}
				}
				else if (value != null && isSelected)
				{
					PP_Order_BOMLine_ID = key.getKey();
					if (PP_Order_BOMLine_ID > 0)
					{
						orderbomLine = InterfaceWrapperHelper.create(order.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, order.get_TrxName());
						M_AttributeSetInstance_ID = orderbomLine.getM_AttributeSetInstance_ID();
					}
				}

				MStorage[] storages = MPPOrder.getStorages(Env.getCtx(),
						M_Product_ID,
						order.getM_Warehouse_ID(),
						M_AttributeSetInstance_ID, minGuaranteeDate, order.get_TrxName());

				MPPOrder.createIssue(order, PP_Order_BOMLine_ID, movementDate,
						qtyToDeliver, qtyScrapComponent,
						Env.ZERO, storages, false);
			}
		}
		// metas : cg task: 06004 - refactored a bit : end
	}

	/**
	 * Generate Summary of Issue/Receipt.
	 */
	private void generateSummaryTable()
	{
		StringBuffer iText = new StringBuffer();
		iText.append("<b>");
		iText.append(msgBL.translate(Env.getCtx(), "IsShipConfirm"));
		iText.append("</b>");
		iText.append("<br />");

		if (isOnlyReceipt() || isBackflush())
		{

			String[][] table = {
					{ msgBL.translate(Env.getCtx(), "Name"),
							msgBL.translate(Env.getCtx(), "C_UOM_ID"),
							msgBL.translate(Env.getCtx(), "M_AttributeSetInstance_ID"),
							msgBL.translate(Env.getCtx(), "QtyToDeliver"),
							msgBL.translate(Env.getCtx(), "QtyDelivered"),
							msgBL.translate(Env.getCtx(), "QtyScrap") },
					{ productField.getDisplay(),
							uomField.getDisplay(),
							attribute.getDisplay(),
							toDeliverQty.getDisplay(),
							deliveredQtyField.getDisplay(),
							scrapQtyField.getDisplay() }
			};
			iText.append(createHTMLTable(table));
		}

		if (isBackflush() || isOnlyIssue())
		{
			iText.append("<br /><br />");

			ArrayList<String[]> table = new ArrayList<String[]>();

			table.add(new String[] {
					msgBL.translate(Env.getCtx(), "Value"),						// 0
					msgBL.translate(Env.getCtx(), "Name"),						// 1
					msgBL.translate(Env.getCtx(), "C_UOM_ID"),					// 2
					msgBL.translate(Env.getCtx(), "M_AttributeSetInstance_ID"),	// 3
					msgBL.translate(Env.getCtx(), "QtyToDeliver"),				// 4
					msgBL.translate(Env.getCtx(), "QtyDelivered"),				// 5
					msgBL.translate(Env.getCtx(), "QtyScrap")						// 6
			});

			// check available on hand
			for (int i = 0; i < issue.getRowCount(); i++)
			{
				IDColumn id = (IDColumn)issue.getValueAt(i, 0);
				if (id != null && id.isSelected())
				{
					KeyNamePair m_productkey = (KeyNamePair)issue.getValueAt(i, 3);
					int m_M_Product_ID = m_productkey.getKey();
					KeyNamePair m_uomkey = (KeyNamePair)issue.getValueAt(i, 4);

					if (issue.getValueAt(i, 5) == null) // M_AttributeSetInstance_ID is null
					{
						Timestamp m_movementDate = getMovementDate();
						Timestamp minGuaranteeDate = m_movementDate;
						MStorage[] storages = MPPOrder.getStorages(Env.getCtx(),
								m_M_Product_ID,
								getPP_Order().getM_Warehouse_ID(), // FIXME: use M_Warehouse_ID from issue table, which is PP_Order_BOMLine.M_Warehouse_ID atm
								0, minGuaranteeDate, null);

						BigDecimal todelivery = getValueBigDecimal(i, 8); // QtyOpen
						BigDecimal scrap = getValueBigDecimal(i, 9); // QtyScrap
						BigDecimal toIssue = todelivery.add(scrap);
						for (MStorage storage : storages)
						{
							// TODO Selection of ASI

							if (storage.getQtyOnHand().signum() == 0)
								continue;

							BigDecimal issueact = toIssue;
							if (issueact.compareTo(storage.getQtyOnHand()) > 0)
								issueact = storage.getQtyOnHand();
							toIssue = toIssue.subtract(issueact);

							String desc = new MAttributeSetInstance(Env.getCtx(), storage.getM_AttributeSetInstance_ID(), null).getDescription();

							String[] row = { "", "", "", "", "0.00", "0.00", "0.00" };
							row[0] = issue.getValueAt(i, 2) != null ? issue.getValueAt(i, 2).toString() : "";
							row[1] = m_productkey.toString();
							row[2] = m_uomkey != null ? m_uomkey.toString() : "";
							row[3] = desc != null ? desc : "";
							row[4] = issueact.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
							row[5] = getValueBigDecimal(i, 7).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
							row[6] = getValueBigDecimal(i, 9).toString();
							table.add(row);

							if (toIssue.signum() <= 0)
								break;
						}
					}
					else
					// if M_AttributeSetInstance_ID isn't null
					{
						String[] row = { "", "", "", "", "0.00", "0.00", "0.00" };
						row[0] = issue.getValueAt(i, 2) != null ? issue.getValueAt(i, 2).toString() : "";
						row[1] = m_productkey.toString();
						row[2] = m_uomkey != null ? m_uomkey.toString() : "";
						row[3] = issue.getValueAt(i, 5) != null ? issue.getValueAt(i, 5).toString() : "";
						row[4] = getValueBigDecimal(i, 8).toString();
						row[5] = getValueBigDecimal(i, 7).toString();
						row[6] = getValueBigDecimal(i, 9).toString();
						table.add(row);
					}

				}

			}

			String[][] tableArray = table.toArray(new String[table.size()][]);
			iText.append(createHTMLTable(tableArray));
		}
		info.setText(iText.toString());
	} // generateInvoices_complete

	/**
	 * Determines whether the Delivery Rule is set to 'OnlyReciept'
	 * 
	 * @return
	 */
	private boolean isOnlyReceipt()
	{
		return (pickcombo.getDisplay().equals("OnlyReceipt"));
	}

	/**
	 * Determines whether the Delivery Rule is set to 'OnlyIssue'
	 * 
	 * @return
	 */
	private boolean isOnlyIssue()
	{
		return (pickcombo.getDisplay().equals("OnlyIssue"));
	}

	/**
	 * Determines whether the Delivery Rule is set to 'isBackflush'
	 * 
	 * @return
	 */
	protected boolean isBackflush()
	{
		return (pickcombo.getDisplay().equals("IsBackflush"));
	}

	protected Timestamp getMovementDate()
	{
		return movementDateField.getValue();
	}

	protected BigDecimal getOrderedQty()
	{
		BigDecimal bd = (BigDecimal)orderedQtyField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setOrderedQty(BigDecimal qty)
	{
		this.orderedQtyField.setValue(qty);
	}

	protected BigDecimal getDeliveredQty()
	{
		BigDecimal bd = (BigDecimal)deliveredQtyField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setDeliveredQty(BigDecimal qty)
	{
		deliveredQtyField.setValue(qty);
	}

	protected BigDecimal getToDeliverQty()
	{
		BigDecimal bd = (BigDecimal)toDeliverQty.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setToDeliverQty(BigDecimal qty)
	{
		toDeliverQty.setValue(qty);
	}

	protected BigDecimal getScrapQty()
	{
		BigDecimal bd = (BigDecimal)scrapQtyField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected BigDecimal getRejectQty()
	{
		BigDecimal bd = (BigDecimal)rejectQty.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected BigDecimal getOpenQty()
	{
		BigDecimal bd = (BigDecimal)openQtyField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setOpenQty(BigDecimal qty)
	{
		openQtyField.setValue(qty);
	}

	protected BigDecimal getQtyBatchs()
	{
		BigDecimal bd = (BigDecimal)qtyBatchsField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setQtyBatchs(BigDecimal qty)
	{
		qtyBatchsField.setValue(qty);
	}

	protected BigDecimal getQtyBatchSize()
	{
		BigDecimal bd = (BigDecimal)qtyBatchSizeField.getValue();
		return bd != null ? bd : Env.ZERO;
	}

	protected void setQtyBatchSize(BigDecimal qty)
	{
		qtyBatchSizeField.setValue(qty);
	}

	protected int getM_AttributeSetInstance_ID()
	{
		Integer ii = (Integer)attribute.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		attribute.setValue(M_AttributeSetInstance_ID);
	}

	protected int getM_Locator_ID()
	{
		Integer ii = (Integer)locatorField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setM_Locator_ID(int M_Locator_ID)
	{
		locatorField.setValue(M_Locator_ID);
	}

	protected int getPP_Order_ID()
	{
		Integer ii = (Integer)orderField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	private MPPOrder m_PP_order = null;

	protected MPPOrder getPP_Order()
	{
		int id = getPP_Order_ID();
		if (id <= 0)
		{
			m_PP_order = null;
			return null;
		}
		if (m_PP_order == null || m_PP_order.get_ID() != id)
		{

			m_PP_order = new MPPOrder(Env.getCtx(), id, null);
		}
		return m_PP_order;
	}

	protected int getS_Resource_ID()
	{
		Integer ii = (Integer)resourceField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setS_Resource_ID(int S_Resource_ID)
	{
		resourceField.setValue(S_Resource_ID);
	}

	protected int getM_Warehouse_ID()
	{
		Integer ii = (Integer)warehouseField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setM_Warehouse_ID(int M_Warehouse_ID)
	{
		warehouseField.setValue(M_Warehouse_ID);
	}

	protected int getM_Product_ID()
	{
		Integer ii = (Integer)productField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setM_Product_ID(int M_Product_ID)
	{
		productField.setValue(M_Product_ID);
		Env.setContext(Env.getCtx(), m_WindowNo, "M_Product_ID", M_Product_ID);
	}

	protected int getC_UOM_ID()
	{
		Integer ii = (Integer)uomField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setC_UOM_ID(int C_UOM_ID)
	{
		uomField.setValue(C_UOM_ID);
	}

	protected int getOrder_UOM_ID()
	{
		Integer ii = (Integer)uomorderField.getValue();
		return ii != null ? ii.intValue() : 0;
	}

	protected void setOrder_UOM_ID(int C_UOM_ID)
	{
		uomorderField.setValue(C_UOM_ID);
	}

	/**
	 * Creates a HTML Table out of a two dimensional array.
	 * 
	 * @param table A two dimensional array of strings that will be rendered into an html table
	 * @return The html for the table
	 */
	private String createHTMLTable(String[][] table)
	{
		StringBuffer html = new StringBuffer("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");

		for (int i = 0; i < table.length; i++)
		{
			if (table[i] != null)
			{
				html.append("<tr>");
				for (int j = 0; j < table[i].length; j++)
				{
					html.append("<td>");
					if (table[i][j] != null)
					{
						html.append(table[i][j]);
					}
					html.append("</td>");
				}
				html.append("</tr>");
			}
		}
		html.append("</table>");

		return html.toString();
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
	}

	@Override
	public void lockUI(de.metas.process.ProcessInfo processInfo)
	{
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
	}

	@Override
	public void unlockUI(de.metas.process.ProcessInfo processInfo)
	{
	}

	private BigDecimal getValueBigDecimal(int row, int col)
	{
		BigDecimal bd = (BigDecimal)issue.getValueAt(row, col);
		return bd == null ? Env.ZERO : bd;
	}
}
