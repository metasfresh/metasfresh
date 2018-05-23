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
package org.compiere.apps.search.history.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.AppsAction;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.dao.IInvoiceHistoryDAO;
import org.compiere.apps.search.dao.impl.OrderLineHistoryVO;
import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_DocType;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.pricing.service.IPriceListBL;

/**
 * Price History for BPartner/Product
 *
 * @author Jorg Janke
 * @version $Id: InvoiceHistory.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class InvoiceHistory
		extends CDialog
		implements ActionListener, ChangeListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7886949815469558804L;

	/** Logger */
	private static Logger log = LogManager.getLogger(InvoiceHistory.class);

	public static final int TAB_PRICEHISTORY = 0;
	public static final int TAB_ORDERED = 1;
	public static final int TAB_RECEIVED = 2;
	public static final int TAB_RESERVED = 3;
	public static final int TAB_DELIVERED = 4;
	public static final int TAB_UNCONFIRMED = 5;
	public static final int TAB_ATP = 6; // ATP means available to promise

	private static final String A_ZOOM_PRIMARY = "Zoom";
	private static final String A_ZOOM_SECONDARY = "ZoomAcross";

	//
	// Services
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final InvoiceHistoryContext ihCtx;

	/**
	 * Show History
	 *
	 * @param C_BPartner_ID partner
	 * @param M_Product_ID product
	 * @param M_Warehouse_ID warehouse
	 * @param M_AttributeSetInstance_ID ASI
	 */
	public InvoiceHistory(final InvoiceHistoryContext ihCtx, final Window owner)
	{
		super(owner, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "PriceHistory"), true);

		log.info(ihCtx.toString());
		Check.assumeNotNull(ihCtx, "ihCtx not null");
		this.ihCtx = ihCtx;

		try
		{
			jbInit();
			dynInit();
		}
		catch (final Exception ex)
		{
			log.error("", ex);
		}
		mainPanel.setPreferredSize(new Dimension(700, 400));
		AEnv.positionCenterWindow(owner, this);
	}

	private final CPanel mainPanel = new CPanel();
	private final BorderLayout mainLayout = new BorderLayout();
	private final CPanel northPanel = new CPanel();
	private final JLabel label = new JLabel();
	private final FlowLayout northLayout = new FlowLayout();
	//
	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOK();
	private final JTabbedPane centerTabbedPane = new JTabbedPane();
	//
	private final JScrollPane pricePane = new JScrollPane();
	private final MiniTable m_tablePrice = new MiniTable();
	private DefaultTableModel m_modelPrice = null;

	//
	// Tab Reserved
	private final JScrollPane reservedPane = new JScrollPane();
	private final MiniTable m_tableReserved = new MiniTable();
	private DefaultTableModel m_modelReserved = null;

	//
	// Tab Ordered
	private final JScrollPane orderedPane = new JScrollPane();
	private final MiniTable m_tableOrdered = new MiniTable();
	private DefaultTableModel m_modelOrdered = null;

	//
	// Tab Unconfirmed
	private final JScrollPane unconfirmedPane = new JScrollPane();
	private final MiniTable m_tableUnconfirmed = new MiniTable();
	private DefaultTableModel m_modelUnconfirmed = null;

	//
	// Tab ATP (Available to Promise)
	private final JScrollPane atpPane = new JScrollPane();
	private final MiniTable m_tableAtp = new MiniTable();
	private DefaultTableModel m_modelAtp = null;

	//
	// Tab Received
	private final JScrollPane receivedPane = new JScrollPane();
	private final MiniTable m_tableReceived = new MiniTable();
	private DefaultTableModel m_modelReceived = null;

	//
	// Tab Delivered
	private final JScrollPane deliveredPane = new JScrollPane();
	private final MiniTable m_tableDelivered = new MiniTable();
	private DefaultTableModel m_modelDelivered = null;

	//
	// Zoom Buttons
	/**
	 * Primary zoom button (i.e main document for which history is viewed)
	 */
	private CButton bZoomPrimary;
	/**
	 * Secondary zoom button (i.e linked document of the main one for which history is viewed)
	 */
	private CButton bZoomSecondary;

	/**
	 * Static Init
	 */
	void jbInit() throws Exception
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainPanel.setLayout(mainLayout);
		label.setText("Label");
		northPanel.setLayout(northLayout);
		northLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(mainPanel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(label, null);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		mainPanel.add(centerTabbedPane, BorderLayout.CENTER);

		centerTabbedPane.addChangeListener(this);

		final IInvoiceHistoryTabHandler ihTabHandler = ihCtx.getInvoiceHistoryTabHandler();
		if (ihTabHandler.isTabEnabled(TAB_PRICEHISTORY))
		{
			centerTabbedPane.add(pricePane, msgBL.getMsg(ctx, "PriceHistory"));
		}
		if (ihTabHandler.isTabEnabled(TAB_ORDERED))
		{
			centerTabbedPane.add(orderedPane, msgBL.translate(ctx, "QtyOrdered"));
		}
		if (ihTabHandler.isTabEnabled(TAB_RECEIVED))
		{
			centerTabbedPane.add(receivedPane, msgBL.translate(ctx, "QtyReceived"));
		}
		if (ihTabHandler.isTabEnabled(TAB_RESERVED))
		{
			centerTabbedPane.add(reservedPane, msgBL.translate(ctx, "QtyReserved"));
		}
		if (ihTabHandler.isTabEnabled(TAB_DELIVERED))
		{
			centerTabbedPane.add(deliveredPane, msgBL.translate(ctx, "QtyDelivered"));
		}
		if (ihTabHandler.isTabEnabled(TAB_UNCONFIRMED))
		{
			centerTabbedPane.add(unconfirmedPane, msgBL.getMsg(ctx, "QtyUnconfirmed"));
		}
		if (ihTabHandler.isTabEnabled(TAB_ATP)
				&& ihCtx.getM_Product_ID() != 0)
		{
			centerTabbedPane.add(atpPane, msgBL.getMsg(ctx, "ATP"));
		}

		//
		// Add tabs
		pricePane.getViewport().add(m_tablePrice, null);
		orderedPane.getViewport().add(m_tableOrdered, null);
		receivedPane.getViewport().add(m_tableReceived, null);
		reservedPane.getViewport().add(m_tableReserved, null);
		deliveredPane.getViewport().add(m_tableDelivered, null);
		unconfirmedPane.getViewport().add(m_tableUnconfirmed, null);
		if (ihCtx.getM_Product_ID() != 0)
		{
			atpPane.getViewport().add(m_tableAtp, null);
		}

		//
		// Table Selection Listeners
		addEnableDisableZoomTableListSelectionListener(m_tablePrice);
		addEnableDisableZoomTableListSelectionListener(m_tableReserved);
		addEnableDisableZoomTableListSelectionListener(m_tableOrdered);
		addEnableDisableZoomTableListSelectionListener(m_tableUnconfirmed);
		addEnableDisableZoomTableListSelectionListener(m_tableAtp);
		addEnableDisableZoomTableListSelectionListener(m_tableReceived);
		addEnableDisableZoomTableListSelectionListener(m_tableDelivered);

		//
		// Zoom Buttons
		//
		// Primary
		{
			bZoomPrimary = createZoomButton(A_ZOOM_PRIMARY);
			bZoomPrimary.addActionListener(this);
			confirmPanel.addButton(bZoomPrimary);
		}
		//
		// Secondary
		{
			bZoomSecondary = createZoomButton(A_ZOOM_SECONDARY);
			bZoomSecondary.addActionListener(this);
			confirmPanel.addButton(bZoomSecondary);
		}

		confirmPanel.setActionListener(this);

		setModal(false); // 08574: for Zoom buttons
		getOwner().setEnabled(false); // disable owner

		//
		// Run initial queries to fill label
		if (ihCtx.getC_BPartner_ID() == 0)
		{
			queryBPartner();
		}
		else
		{
			queryProduct();
		}
	}

	private void addEnableDisableZoomTableListSelectionListener(final JTable table)
	{
		final ListSelectionModel selectionModel = table.getSelectionModel();
		final ListSelectionListener zoomListSelectionListener = new ListSelectionListener()
		{
			@Override
			public void valueChanged(final ListSelectionEvent e)
			{
				enableDisableButtons(table);
			}
		};
		selectionModel.addListSelectionListener(zoomListSelectionListener);
	}

	private CButton createZoomButton(final String zoomAction)
	{
		return AppsAction.builder()
				.setAction(zoomAction)
				.setToolTipText(zoomAction)
				.setButtonInsets(ConfirmPanel.s_insets)
				.buildAndGetCButton();
	}

	/**
	 * Dynamic Init for Price Tab
	 */
	private void dynInit()
	{
		final ChangeEvent e = new ChangeEvent(this);
		stateChanged(e); // force fire state changed
	}	// dynInit

	/**
	 * Get Info for Product for given Business Partner
	 */
	private Vector<Vector<Object>> queryProduct()
	{
		String sql = "SELECT p.Name,l.PriceActual,l.PriceList,l.QtyInvoiced,"					// 1,2,3,4
				+ "i.DateOrdered,i.DateInvoiced,dt.PrintName || ' ' || i.DocumentNo As DocumentNo,"	// 5,6,7
				+ "o.Name, "																		// 8
				+ "NULL, i.M_PriceList_ID,"															// 9,10
				+ "0," 																				// 11 -- task 08264: ASI-ID
				+ "i.C_Invoice_ID, p.M_Product_ID "													// 12, 13
				+ "FROM C_Invoice i"
				+ " INNER JOIN C_InvoiceLine l ON (i.C_Invoice_ID=l.C_Invoice_ID)"
				+ " INNER JOIN C_DocType dt ON (i.C_DocType_ID=dt.C_DocType_ID)"
				+ " INNER JOIN AD_Org o ON (i.AD_Org_ID=o.AD_Org_ID)"
				+ " INNER JOIN M_Product p  ON (l.M_Product_ID=p.M_Product_ID) "
				+ "WHERE i.C_BPartner_ID=? "
				+ "ORDER BY i.DateInvoiced DESC";

		final Vector<Vector<Object>> data = fillTable(sql, ihCtx.getC_BPartner_ID());

		sql = "SELECT Name from C_BPartner WHERE C_BPartner_ID=?";
		fillLabel(sql, ihCtx.getC_BPartner_ID());
		return data;
	}   // queryProduct

	/**
	 * Get Info for Business Partners for given Product
	 */
	private Vector<Vector<Object>> queryBPartner()
	{
		String sql = "SELECT bp.Name,l.PriceActual,l.PriceList,l.QtyInvoiced,"					// 1,2,3,4
				+ "i.DateOrdered,i.DateInvoiced,dt.PrintName || ' ' || i.DocumentNo As DocumentNo,"	// 5,6,7
				+ "o.Name,"																			// 8
				+ "NULL, i.M_PriceList_ID,"															// 9,10
				+ "0,"																				// 11 -- task 08264: ASI-ID
				+ "i.C_Invoice_ID, l.M_Product_ID "													// 12, 13
				+ " FROM C_Invoice i"
				+ " INNER JOIN C_InvoiceLine l ON (i.C_Invoice_ID=l.C_Invoice_ID)"
				+ " INNER JOIN C_DocType dt ON (i.C_DocType_ID=dt.C_DocType_ID)"
				+ " INNER JOIN AD_Org o ON (i.AD_Org_ID=o.AD_Org_ID)"
				+ " INNER JOIN C_BPartner bp ON (i.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE l.M_Product_ID=? "
				+ "ORDER BY i.DateInvoiced DESC";

		final Vector<Vector<Object>> data = fillTable(sql, ihCtx.getM_Product_ID());

		sql = "SELECT Name from M_Product WHERE M_Product_ID=?";
		fillLabel(sql, ihCtx.getM_Product_ID());
		return data;
	}	// qyeryBPartner

	/**
	 * Fill Table
	 */
	private Vector<Vector<Object>> fillTable(final String sql, final int parameter)
	{
		final Properties ctx = Env.getCtx();

		log.debug(sql + "; Parameter=" + parameter);
		final Vector<Vector<Object>> data = new Vector<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, parameter);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Vector<Object> line = new Vector<>(6);
				// 0-Name, 1-PriceActual, 2-QtyInvoiced, 3-Discount, 4-DocumentNo, 5-DateInvoiced
				line.add(rs.getString(1));      // Name
				line.add(rs.getBigDecimal(2));  // Price
				line.add(rs.getBigDecimal(4));      // Qty
				BigDecimal discountBD = rs.getBigDecimal(9);
				if (discountBD == null)
				{
					final BigDecimal priceList = rs.getBigDecimal(3);
					final BigDecimal priceActual = rs.getBigDecimal(2);
					if (!Check.isEmpty(priceList))
					{
						final int precision = Services.get(IPriceListBL.class).getPricePrecision(rs.getInt(10));
						final RoundingMode roundingMode = RoundingMode.HALF_UP;

						discountBD = priceList.subtract(priceActual).divide(priceList, precision, roundingMode).multiply(Env.ONEHUNDRED);
						// Rounding:
						if (discountBD.scale() > precision)
						{
							discountBD = discountBD.setScale(precision, roundingMode);
						}
					}
					else
					{
						discountBD = Env.ZERO;
					}
				}
				line.add(discountBD);           // Discount
				line.add(rs.getString(7));      // DocNo 7
				line.add(rs.getTimestamp(5));   // Date
				line.add(rs.getTimestamp(6));   // DatePromised
				line.add(rs.getString(8));		// Org/Warehouse 8
				line.add(rs.getString(11));		// ASI 11
				line.add(rs.getInt(12));		// Primary ID 12
				line.add(rs.getInt(13));		// Secondary ID 13

				data.add(line);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		log.debug("#" + data.size());

		return data;
	}	// fillTable

	private Date getDatePromisedOrNull()
	{
		final Timestamp datePromisedTimestamp = ihCtx.getDatePromisedOrNull();
		if (datePromisedTimestamp == null)
		{
			return null;
		}
		final Date datePromised = new Date(datePromisedTimestamp.getTime());
		return datePromised;
	}

	private void initPriceHistoryTab(final MiniTable table)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		// Header
		final Vector<String> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(ctx, ihCtx.getC_BPartner_ID() == 0 ? "C_BPartner_ID" : "M_Product_ID"));
		columnNames.add(msgBL.translate(ctx, "PriceActual"));
		columnNames.add(msgBL.translate(ctx, "QtyInvoiced"));
		columnNames.add(msgBL.translate(ctx, "Discount"));
		columnNames.add(msgBL.translate(ctx, "DocumentNo"));
		columnNames.add(msgBL.translate(ctx, "DateOrdered"));
		columnNames.add(msgBL.translate(ctx, "DateInvoiced"));
		columnNames.add(msgBL.translate(ctx, "AD_Org_ID"));

		// Fill Data
		Vector<Vector<Object>> data = null;
		if (ihCtx.getC_BPartner_ID() == 0)
		{
			data = queryBPartner();		// BPartner of Product
		}
		else
		{
			data = queryProduct();		// Product of BPartner
		}

		// Table
		m_modelPrice = new DefaultTableModel(data, columnNames);
		table.setModel(m_modelPrice);
		//
		table.setColumnClass(0, String.class, true);      // Product/Partner
		table.setColumnClass(1, BigDecimal.class, true);  	 // Price
		table.setColumnClass(2, BigDecimal.class, true);      // Quantity
		table.setColumnClass(3, BigDecimal.class, true);  // Discount (%) to limit precision
		table.setColumnClass(4, String.class, true);      // DocNo
		table.setColumnClass(5, Timestamp.class, true);   // DateOrdered
		table.setColumnClass(6, Timestamp.class, true);   // DateInvoiced
		table.setColumnClass(7, String.class, true);   	 // Org
		//
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(ihCtx.isRowSelectionAllowed());
		//
		table.autoSize();

		//
		// Remove DateOrdered at the end to preserve current display
		table.removeColumn(table.getColumn(5));
	}

	/**
	 * Query Reserved/Ordered
	 *
	 * @param table
	 *
	 * @param reserved po/so
	 */
	private void initReservedOrderedTab(final MiniTable table, final boolean reserved)
	{
		// Done already
		if (reserved && m_modelReserved != null)
		{
			return;
		}
		if (!reserved && m_modelOrdered != null)
		{
			return;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		// Header
		final Vector<String> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(ctx, ihCtx.getC_BPartner_ID() == 0 ? "C_BPartner_ID" : "M_Product_ID"));
		columnNames.add(msgBL.translate(ctx, "PriceActual"));
		columnNames.add(msgBL.translate(ctx, reserved ? "QtyReserved" : "QtyOrdered"));
		columnNames.add(msgBL.translate(ctx, "Discount"));
		columnNames.add(msgBL.translate(ctx, "DocumentNo"));
		columnNames.add(msgBL.translate(ctx, "DateOrdered"));
		columnNames.add(msgBL.translate(ctx, "DatePromised"));  // added Date promised 06998
		columnNames.add(msgBL.translate(ctx, "M_Warehouse_ID"));
		columnNames.add(msgBL.translate(ctx, "M_AttributeSetInstance_ID")); // 08264: display ASI

		final String columnNameC_Order_ID = msgBL.translate(ctx, reserved ? "C_Order_ID" : "C_OrderPO_ID") + "-ID";
		columnNames.add(columnNameC_Order_ID);

		final Date datePromised = getDatePromisedOrNull();

		// Fill Data
		final Vector<Vector<Object>> data;
		if (ihCtx.getC_BPartner_ID() == 0)
		{
			final String sql = "SELECT bp.Name, ol.PriceActual,ol.PriceList,ol.QtyReserved,"   // 1 2 3 4
					+ "o.DateOrdered,o.DatePromised, dt.PrintName || ' ' || o.DocumentNo As DocumentNo, "   // 5 6 7
					+ "w.Name,"                                                              // 8
					+ "ol.Discount, 0, "															// 9,10=M_PriceList_ID
					+ "olASI.Description, "															// 11
					+ "o.C_Order_ID, 0 "															// 12, 13
					+ "FROM C_Order o"
					+ " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance olASI ON (ol.M_AttributeSetInstance_ID=olASI.M_AttributeSetInstance_ID)"
					+ " INNER JOIN C_DocType dt ON (o.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (ol.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN C_BPartner bp  ON (o.C_BPartner_ID=bp.C_BPartner_ID) "
					+ "WHERE ol.QtyReserved<>0"
					+ " AND ol.M_Product_ID=?"
					+ " AND o.IsSOTrx=" + (reserved ? "'Y'" : "'N'")
					+ " AND ol.IsHUStorageDisabled='N'" // FIXME: workaround introduced by 08242, to hide certain order lines from this info
					// FIXME shitty fillTable should be replaced (parameters are way too hard to add)
					+ (datePromised != null ? " AND o.DatePromised::DATE='" + datePromised.toString() + "'::DATE" : "")
					+ " ORDER BY o.DateOrdered";
			data = fillTable(sql, ihCtx.getM_Product_ID());	// Product By BPartner
		}
		else
		{
			final String sql = "SELECT p.Name, ol.PriceActual,ol.PriceList,ol.QtyReserved,"
					+ "o.DateOrdered,o.DatePromised, dt.PrintName || ' ' || o.DocumentNo As DocumentNo, "
					+ "w.Name,"
					+ "ol.Discount, 0, "															// 9,10=M_PriceList_ID
					+ "olASI.Description, "															// 11
					+ "o.C_Order_ID, 0 "															// 12, 13
					+ "FROM C_Order o"
					+ " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance olASI ON (ol.M_AttributeSetInstance_ID=olASI.M_AttributeSetInstance_ID)"
					+ " INNER JOIN C_DocType dt ON (o.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (ol.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN M_Product p  ON (ol.M_Product_ID=p.M_Product_ID) "
					+ "WHERE ol.QtyReserved<>0"
					+ " AND o.C_BPartner_ID=?"
					+ " AND o.IsSOTrx=" + (reserved ? "'Y'" : "'N'")
					+ " AND ol.IsHUStorageDisabled='N'" // FIXME: workaround introduced by 08242, to hide certain order lines from this info
					// FIXME shitty fillTable should be replaced (parameters are way too hard to add)
					+ (datePromised != null ? " AND o.DatePromised::DATE='" + datePromised.toString() + "'::DATE" : "")
					+ " ORDER BY o.DateOrdered";
			data = fillTable(sql, ihCtx.getC_BPartner_ID()); // Product of BP
		}

		// Table
		if (reserved)
		{
			m_modelReserved = new DefaultTableModel(data, columnNames);
			table.setModel(m_modelReserved);
		}
		else
		{
			m_modelOrdered = new DefaultTableModel(data, columnNames);
			table.setModel(m_modelOrdered);
		}
		//
		table.setColumnClass(0, String.class, true);		// Product/Partner
		table.setColumnClass(1, BigDecimal.class, true);	// Price
		table.setColumnClass(2, BigDecimal.class, true);	// Quantity
		table.setColumnClass(3, BigDecimal.class, true);	// Discount (%)
		table.setColumnClass(4, String.class, true);		// DocNo
		table.setColumnClass(5, Timestamp.class, true);		// Date
		table.setColumnClass(6, Timestamp.class, true);		// PromisedDate
		table.setColumnClass(7, String.class, true);		// Warehouse
		table.setColumnClass(8, String.class, true);		// ASI
		table.setColumnClass(9, Integer.class, true);		// C_Order_ID
		//
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(ihCtx.isRowSelectionAllowed());
		//
		table.autoSize();
	}	// initReservedOrderedTab

	/**
	 * Query Unconfirmed
	 *
	 * @param table
	 */
	private void initUnconfirmedTab(final MiniTable table)
	{
		// Done already
		if (m_modelUnconfirmed != null)
		{
			return;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		// Header
		final Vector<String> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(ctx, ihCtx.getC_BPartner_ID() == 0 ? "C_BPartner_ID" : "M_Product_ID"));
		columnNames.add(msgBL.translate(ctx, "MovementQty"));
		columnNames.add(msgBL.translate(ctx, "MovementDate"));
		columnNames.add(msgBL.translate(ctx, "IsSOTrx"));
		columnNames.add(msgBL.translate(ctx, "DocumentNo"));
		columnNames.add(msgBL.translate(ctx, "M_Warehouse_ID"));

		// Fill Data
		String sql = null;
		int parameter = 0;
		if (ihCtx.getC_BPartner_ID() == 0)
		{
			sql = "SELECT bp.Name,"
					+ " CASE WHEN io.IsSOTrx='Y' THEN iol.MovementQty*-1 ELSE iol.MovementQty END AS MovementQty,"
					+ " io.MovementDate,io.IsSOTrx,"
					+ " dt.PrintName || ' ' || io.DocumentNo As DocumentNo,"
					+ " w.Name "
					+ "FROM M_InOutLine iol"
					+ " INNER JOIN M_InOut io ON (iol.M_InOut_ID=io.M_InOut_ID)"
					+ " INNER JOIN C_BPartner bp  ON (io.C_BPartner_ID=bp.C_BPartner_ID)"
					+ " INNER JOIN C_DocType dt ON (io.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (io.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN M_InOutLineConfirm lc ON (iol.M_InOutLine_ID=lc.M_InOutLine_ID) "
					+ "WHERE iol.M_Product_ID=?"
					+ " AND lc.Processed='N' "
					+ "ORDER BY io.MovementDate,io.IsSOTrx";
			parameter = ihCtx.getM_Product_ID();
		}
		else
		{
			sql = "SELECT p.Name,"
					+ " CASE WHEN io.IsSOTrx='Y' THEN iol.MovementQty*-1 ELSE iol.MovementQty END AS MovementQty,"
					+ " io.MovementDate,io.IsSOTrx,"
					+ " dt.PrintName || ' ' || io.DocumentNo As DocumentNo,"
					+ " w.Name "
					+ "FROM M_InOutLine iol"
					+ " INNER JOIN M_InOut io ON (iol.M_InOut_ID=io.M_InOut_ID)"
					+ " INNER JOIN M_Product p  ON (iol.M_Product_ID=p.M_Product_ID)"
					+ " INNER JOIN C_DocType dt ON (io.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (io.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN M_InOutLineConfirm lc ON (iol.M_InOutLine_ID=lc.M_InOutLine_ID) "
					+ "WHERE io.C_BPartner_ID=?"
					+ " AND lc.Processed='N' "
					+ "ORDER BY io.MovementDate,io.IsSOTrx";
			parameter = ihCtx.getC_BPartner_ID();
		}
		final Vector<Vector<Object>> data = new Vector<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, parameter);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Vector<Object> line = new Vector<>(6);
				// 1-Name, 2-MovementQty, 3-MovementDate, 4-IsSOTrx, 5-DocumentNo
				line.add(rs.getString(1));      		// Name
				line.add(rs.getBigDecimal(2));  // Qty
				line.add(rs.getTimestamp(3));   		// Date
				line.add(new Boolean("Y".equals(rs.getString(4))));	// IsSOTrx
				line.add(rs.getString(5));				// DocNo
				line.add(rs.getString(6));				// Warehouse
				data.add(line);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		log.debug("#" + data.size());

		// Table
		m_modelUnconfirmed = new DefaultTableModel(data, columnNames);
		table.setModel(m_modelUnconfirmed);
		//
		table.setColumnClass(0, String.class, true);		// Product/Partner
		table.setColumnClass(1, BigDecimal.class, true);	// MovementQty
		table.setColumnClass(2, Timestamp.class, true);		// MovementDate
		table.setColumnClass(3, Boolean.class, true);		// IsSOTrx
		table.setColumnClass(4, String.class, true);		// DocNo
		//
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(ihCtx.isRowSelectionAllowed());
		//
		table.autoSize();
	}	// initUnconfirmedTab

	/**
	 * Query ATP
	 *
	 * @param table
	 */
	private void initAtpTab(final MiniTable table)
	{
		// Done already
		if (m_modelAtp != null)
		{
			return;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		// Header
		final Vector<String> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(ctx, "DatePromised"));
		columnNames.add(msgBL.translate(ctx, "QtyOnHand"));
		columnNames.add(msgBL.translate(ctx, "C_BPartner_ID"));
		columnNames.add(msgBL.translate(ctx, "QtyOrdered"));
		columnNames.add(msgBL.translate(ctx, "QtyReserved"));
		columnNames.add(msgBL.translate(ctx, "M_Locator_ID"));
		columnNames.add(msgBL.translate(ctx, "M_AttributeSetInstance_ID"));
		columnNames.add(msgBL.translate(ctx, "DocumentNo"));
		columnNames.add(msgBL.translate(ctx, "M_Warehouse_ID"));

		// Fill Storage Data
		final boolean showDetail = LogManager.isLevelFine();

		final IInvoiceHistoryDAO invoiceHistoryDAO = Services.get(IInvoiceHistoryDAO.class);
		final String storageSql = invoiceHistoryDAO.buildStorageInvoiceHistorySQL(showDetail, ihCtx.getM_Warehouse_ID(), ihCtx.getM_AttributeSetInstance_ID());

		final Vector<Vector<Object>> data = new Vector<>();
		BigDecimal qty = BigDecimal.ZERO;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(storageSql, null);
			pstmt.setInt(1, ihCtx.getM_Product_ID());
			if (ihCtx.getM_Warehouse_ID() > 0)
			{
				pstmt.setInt(2, ihCtx.getM_Warehouse_ID());
			}

			if (ihCtx.getM_AttributeSetInstance_ID() > 0)
			{
				pstmt.setInt(3, ihCtx.getM_AttributeSetInstance_ID());
			}

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Vector<Object> line = new Vector<>(9);
				line.add(null);							// Date
				final BigDecimal qtyOnHand = rs.getBigDecimal(1);
				qty = qty.add(qtyOnHand);
				line.add(qtyOnHand);  					// Qty
				line.add(null);							// BPartner
				line.add(rs.getBigDecimal(3));  		// QtyOrdered
				line.add(rs.getBigDecimal(2));  		// QtyReserved
				line.add(rs.getString(7));      		// Locator
				String asi = rs.getString(4);
				if (showDetail && (asi == null || asi.length() == 0))
				{
					asi = "{" + rs.getInt(5) + "}";
				}
				line.add(asi);							// ASI
				line.add(null);							// DocumentNo
				line.add(rs.getString(6));  			// Warehouse
				data.add(line);
			}
		}
		catch (final SQLException e)
		{
			log.error(storageSql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Orders
		final List<OrderLineHistoryVO> orderLineHistoryVOs = invoiceHistoryDAO.retrieveOrderLineHistory(ihCtx.getM_Product_ID(), ihCtx.getM_AttributeSetInstance_ID(), ihCtx.getM_Warehouse_ID());
		for (final OrderLineHistoryVO orderLineHistoryVO : orderLineHistoryVOs)
		{
			final Vector<Object> line = new Vector<>(9);
			line.add(orderLineHistoryVO.getDatePromised());				// Date

			final BigDecimal oq = orderLineHistoryVO.getQtyReserved();
			final String DocBaseType = orderLineHistoryVO.getDocBaseType();

			BigDecimal qtyReserved = null;
			BigDecimal qtyOrdered = null;
			if (X_C_DocType.DOCBASETYPE_PurchaseOrder.equals(DocBaseType))
			{
				qtyOrdered = oq;
				qty = qty.add(oq);
			}
			else
			{
				qtyReserved = oq;
				qty = qty.subtract(oq);
			}
			line.add(qty); 		 										// Qty
			line.add(orderLineHistoryVO.getPartnerName());				// BPartner
			line.add(qtyOrdered);										// QtyOrdered
			line.add(qtyReserved);										// QtyReserved
			line.add(null);				      							// Locator
			String asi = orderLineHistoryVO.getASIDescription();
			if (showDetail && (asi == null || asi.length() == 0))
			{
				asi = "{" + orderLineHistoryVO.getASIId() + "}";
			}
			line.add(asi);												// ASI
			line.add(orderLineHistoryVO.getDocumentNo());				// DocumentNo
			line.add(orderLineHistoryVO.getWarehouseName());  			// Warehouse
			data.add(line);
		}

		// Table
		m_modelAtp = new DefaultTableModel(data, columnNames);
		table.setModel(m_modelAtp);
		//
		table.setColumnClass(0, Timestamp.class, true);   				// Date
		table.setColumnClass(1, BigDecimal.class, true);  				// Quantity
		table.setColumnClass(2, String.class, true);      				// Partner
		table.setColumnClass(3, BigDecimal.class, true);  				// Quantity
		table.setColumnClass(4, BigDecimal.class, true);  				// Quantity
		table.setColumnClass(5, String.class, true);   	  				// Locator
		table.setColumnClass(6, String.class, true);   	  				// ASI
		table.setColumnClass(7, String.class, true);      				// DocNo
		table.setColumnClass(8, String.class, true);   	  				// Warehouse
		//
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(ihCtx.isRowSelectionAllowed());
		//
		table.autoSize();
	}	// initAtpTab

	/**
	 * 08754: M_InOut history tab
	 *
	 * Query Received/Delivered
	 *
	 * @param table
	 *
	 * @param received PO / delivered SO
	 */
	private void initReceivedDeliveredTab(final MiniTable table, final boolean received)
	{
		// Done already
		if (received && m_modelReceived != null)
		{
			return;
		}
		if (!received && m_modelDelivered != null)
		{
			return;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		// Header
		final Vector<String> columnNames = new Vector<>();
		columnNames.add(msgBL.translate(ctx, ihCtx.getC_BPartner_ID() == 0 ? "C_BPartner_ID" : "M_Product_ID"));
		columnNames.add(msgBL.translate(ctx, "DocumentNo"));

		final String orderHeader = (received ? "@C_OrderPO_ID@ " : "@C_Order_ID@ ") + "@DocumentNo@";
		columnNames.add(msgBL.parseTranslation(ctx, orderHeader));

		columnNames.add(msgBL.translate(ctx, "PriceEntered"));
		columnNames.add(msgBL.translate(ctx, received ? "QtyReceived" : "QtyDelivered"));
		columnNames.add(msgBL.translate(ctx, "MovementDate"));
		columnNames.add(msgBL.translate(ctx, "DateOrdered"));
		columnNames.add(msgBL.translate(ctx, "DatePromised"));
		columnNames.add(msgBL.translate(ctx, "M_Warehouse_ID"));
		columnNames.add(msgBL.translate(ctx, "M_AttributeSetInstance_ID"));

		final String columnNameM_InOut_ID = msgBL.translate(ctx, "M_InOut_ID") + "-ID";
		columnNames.add(columnNameM_InOut_ID);

		final String columnNameC_Order_ID = msgBL.translate(ctx, received ? "C_OrderPO_ID" : "C_Order_ID") + "-ID";
		columnNames.add(columnNameC_Order_ID);

		final Date datePromised = getDatePromisedOrNull();

		final String sql;
		final int productPartnerId;

		if (ihCtx.getC_BPartner_ID() == 0)
		{
			sql = "SELECT bp.Name,"
					+ "dt.PrintName || ' ' || io.DocumentNo As M_InOut_DocumentNo,"
					+ "odt.PrintName || ' ' || o.DocumentNo As C_Order_DocumentNo,"
					+ "(SELECT COALESCE(AVG(ic.PriceEntered_Override), AVG(ic.PriceEntered)) AS PriceEntered FROM C_InvoiceCandidate_InOutLine iciol INNER JOIN C_Invoice_Candidate ic ON (iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID) WHERE iol.M_InOutLine_ID = iciol.M_InOutLine_ID GROUP BY iol.M_InOutLine_ID)::NUMERIC AS PriceEntered,"
					+ "iol.MovementQty,"
					+ "io.MovementDate,o.DateOrdered,COALESCE(o.DatePromised, io.MovementDate)::TIMESTAMP,"
					+ "w.Name,"
					+ "iolASI.Description,"
					+ "io.M_InOut_ID, o.C_Order_ID "
					+ "FROM M_InOut io"
					+ " INNER JOIN M_InOutLine iol ON (io.M_InOut_ID=iol.M_InOut_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance iolASI ON (iol.M_AttributeSetInstance_ID=iolASI.M_AttributeSetInstance_ID)"
					+ " INNER JOIN C_DocType dt ON (io.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (io.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN C_BPartner bp ON (io.C_BPartner_ID=bp.C_BPartner_ID) "
					+ " LEFT OUTER JOIN C_Order o ON o.C_Order_ID=io.C_Order_ID "
					+ " LEFT OUTER JOIN C_DocType odt ON (o.C_DocType_ID=odt.C_DocType_ID)"
					+ "WHERE iol.MovementQty<>0"
					+ " AND iol.M_Product_ID=?"
					+ " AND io.IsSOTrx=" + (!received ? "'Y'" : "'N'")
					+ (datePromised != null ? " AND io.MovementDate::DATE=?" : "")
					+ " ORDER BY io.DateOrdered";
			productPartnerId = ihCtx.getM_Product_ID(); // Product By BPartner
		}
		else
		{
			sql = "SELECT p.Name,"
					+ "dt.PrintName || ' ' || io.DocumentNo As M_InOut_DocumentNo,"
					+ "odt.PrintName || ' ' || o.DocumentNo As C_Order_DocumentNo,"
					+ "(SELECT COALESCE(AVG(ic.PriceEntered_Override), AVG(ic.PriceEntered)) AS PriceEntered FROM C_InvoiceCandidate_InOutLine iciol INNER JOIN C_Invoice_Candidate ic ON (iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID) WHERE iol.M_InOutLine_ID = iciol.M_InOutLine_ID GROUP BY iol.M_InOutLine_ID)::NUMERIC AS PriceEntered,"
					+ "iol.MovementQty,"
					+ "io.MovementDate,o.DateOrdered,COALESCE(o.DatePromised, io.MovementDate)::TIMESTAMP,"
					+ "w.Name,"
					+ "iolASI.Description,"
					+ "io.M_InOut_ID, o.C_Order_ID "
					+ "FROM M_InOut io"
					+ " INNER JOIN M_InOutLine iol ON (io.M_InOut_ID=iol.M_InOut_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance iolASI ON (iol.M_AttributeSetInstance_ID=iolASI.M_AttributeSetInstance_ID)"
					+ " INNER JOIN C_DocType dt ON (io.C_DocType_ID=dt.C_DocType_ID)"
					+ " INNER JOIN M_Warehouse w ON (io.M_Warehouse_ID=w.M_Warehouse_ID)"
					+ " INNER JOIN M_Product p ON (iol.M_Product_ID=p.M_Product_ID) "
					+ " LEFT OUTER JOIN C_Order o ON o.C_Order_ID=io.C_Order_ID "
					+ " LEFT OUTER JOIN C_DocType odt ON (o.C_DocType_ID=odt.C_DocType_ID)"
					+ "WHERE iol.MovementQty<>0"
					+ " AND io.C_BPartner_ID=?"
					+ " AND io.IsSOTrx=" + (!received ? "'Y'" : "'N'")
					+ (datePromised != null ? " AND io.MovementDate::DATE=?" : "")
					+ " ORDER BY io.DateOrdered";
			productPartnerId = ihCtx.getC_BPartner_ID(); // Product of BPartner
		}

		// Fill Data
		final Vector<Vector<Object>> data = new Vector<>();
		{
			log.debug(sql + "; Parameter=" + productPartnerId);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, productPartnerId);
				if (datePromised != null)
				{
					pstmt.setDate(2, datePromised);
				}
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final Vector<Object> line = new Vector<>(10);
					line.add(rs.getString(1));      // Product/Partner
					line.add(rs.getString(2));      // M_InOut DocumentNo
					line.add(rs.getString(3));      // C_Order DocumentNo
					line.add(rs.getBigDecimal(4));  // PriceEntered
					line.add(rs.getBigDecimal(5));  // MovementQty
					line.add(rs.getTimestamp(6));   // MovementDate
					line.add(rs.getTimestamp(7));   // MovementDate
					line.add(rs.getTimestamp(8));   // DatePromised
					line.add(rs.getString(9));		// Warehouse
					line.add(rs.getString(10));		// ASI
					line.add(rs.getInt(11));		// M_InOut_ID
					line.add(rs.getInt(12));		// C_Order_ID

					data.add(line);
				}
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			log.debug("#" + data.size());
		}

		// Table
		if (received)
		{
			m_modelReceived = new DefaultTableModel(data, columnNames);
			table.setModel(m_modelReceived);
		}
		else
		{
			m_modelDelivered = new DefaultTableModel(data, columnNames);
			table.setModel(m_modelDelivered);
		}
		//
		table.setColumnClass(0, String.class, true);		// Product/Partner
		table.setColumnClass(1, String.class, true);		// M_InOut DocumentNo
		table.setColumnClass(2, String.class, true);		// C_Order DocumentNo
		table.setColumnClass(3, BigDecimal.class, true);	// PriceEntered
		table.setColumnClass(4, BigDecimal.class, true);	// MovementQty
		table.setColumnClass(5, Timestamp.class, true);		// MovementDate
		table.setColumnClass(6, Timestamp.class, true);		// DateOrdered
		table.setColumnClass(7, Timestamp.class, true);		// DatePromised
		table.setColumnClass(8, String.class, true);		// Warehouse
		table.setColumnClass(9, String.class, true);		// ASI
		table.setColumnClass(10, Integer.class, true);		// M_InOut_ID
		table.setColumnClass(11, Integer.class, true);		// C_Order_ID
		//
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(ihCtx.isRowSelectionAllowed());
		//
		table.autoSize();
	}	// initReceivedDeliveredTab

	/**
	 * Set Label
	 * to product or bp name
	 */
	private void fillLabel(final String sql, final int parameter)
	{
		log.debug(sql + "; Parameter=" + parameter);
		final String retValue = DB.getSQLValueString(null, sql, parameter);
		if (retValue != null)
		{
			label.setText(retValue);
		}
	}	// fillLabel

	/**
	 * Action Listener
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			dispose();
		}
		else if (e.getActionCommand().equals(A_ZOOM_PRIMARY))
		{
			zoomPrimary();
		}
		else if (e.getActionCommand().equals(A_ZOOM_SECONDARY))
		{
			zoomSecondary();
		}
	}

	private void zoomPrimary()
	{
		final int tabIndex = centerTabbedPane.getSelectedIndex();
		final IInvoiceHistoryTabHandler ihTabHandler = ihCtx.getInvoiceHistoryTabHandler();

		final Integer tableId;
		final Integer recordId;
		if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_RESERVED))
		{
			tableId = adTableDAO.retrieveTableId(I_C_Order.Table_Name);

			final int selectedRow = m_tableReserved.getSelectedRow();
			recordId = (Integer)m_modelReserved.getValueAt(selectedRow, 9);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_ORDERED))
		{
			tableId = adTableDAO.retrieveTableId(I_C_Order.Table_Name);

			final int selectedRow = m_tableOrdered.getSelectedRow();
			recordId = (Integer)m_modelOrdered.getValueAt(selectedRow, 9);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_RECEIVED))
		{
			tableId = adTableDAO.retrieveTableId(I_M_InOut.Table_Name);

			final int selectedRow = m_tableReceived.getSelectedRow();
			recordId = (Integer)m_modelReceived.getValueAt(selectedRow, 10);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_DELIVERED))
		{
			tableId = adTableDAO.retrieveTableId(I_M_InOut.Table_Name);

			final int selectedRow = m_tableDelivered.getSelectedRow();
			recordId = (Integer)m_modelDelivered.getValueAt(selectedRow, 10);
		}
		else
		{
			return;
		}
		zoom(tableId, recordId);
	}

	private void zoomSecondary()
	{
		final int tabIndex = centerTabbedPane.getSelectedIndex();
		final IInvoiceHistoryTabHandler ihTabHandler = ihCtx.getInvoiceHistoryTabHandler();

		final Integer tableId;
		final Integer recordId;
		if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_RECEIVED))
		{
			tableId = adTableDAO.retrieveTableId(I_C_Order.Table_Name);

			final int selectedRow = m_tableReceived.getSelectedRow();
			recordId = (Integer)m_modelReceived.getValueAt(selectedRow, 11);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_DELIVERED))
		{
			tableId = adTableDAO.retrieveTableId(I_C_Order.Table_Name);

			final int selectedRow = m_tableDelivered.getSelectedRow();
			recordId = (Integer)m_modelDelivered.getValueAt(selectedRow, 11);
		}
		else
		{
			return;
		}
		zoom(tableId, recordId);
	}

	private final void zoom(final Integer tableId, final Integer recordId)
	{
		if (recordId == null || recordId < 0)
		{
			return;
		}
		AEnv.zoom(tableId, recordId);
	}

	/**
	 * Tab Changed
	 *
	 * @param e event
	 */
	@Override
	public void stateChanged(final ChangeEvent e)
	{
		final int tabIndex = centerTabbedPane.getSelectedIndex();
		final IInvoiceHistoryTabHandler ihTabHandler = ihCtx.getInvoiceHistoryTabHandler();

		final Runnable initializeTabRunnable;
		final int tabHandlerIndex; // will be used to map between active tab and TabHandlerInnerIndex

		if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_PRICEHISTORY))
		{
			setButtonVisibility(bZoomPrimary, false);
			setButtonVisibility(bZoomSecondary, false);

			tabHandlerIndex = TAB_PRICEHISTORY;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initPriceHistoryTab(m_tablePrice);
					enableDisableButtons(m_tablePrice);
				}
			};
			enableDisableButtons(m_tablePrice);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_ORDERED))
		{
			setButtonVisibility(bZoomPrimary, true);
			setButtonVisibility(bZoomSecondary, false);

			tabHandlerIndex = TAB_ORDERED;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initReservedOrderedTab(m_tableOrdered, false);
					enableDisableButtons(m_tableOrdered);
				}
			};
			enableDisableButtons(m_tableOrdered);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_RECEIVED))
		{
			setButtonVisibility(bZoomPrimary, true);
			setButtonVisibility(bZoomSecondary, true);

			tabHandlerIndex = TAB_RECEIVED;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initReceivedDeliveredTab(m_tableReceived, true);
					enableDisableButtons(m_tableReceived);
				}
			};
			enableDisableButtons(m_tableReceived);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_RESERVED))
		{
			setButtonVisibility(bZoomPrimary, true);
			setButtonVisibility(bZoomSecondary, false);

			tabHandlerIndex = TAB_RESERVED;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initReservedOrderedTab(m_tableReserved, true);
					enableDisableButtons(m_tableReserved);
				}
			};
			enableDisableButtons(m_tableReserved);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_DELIVERED))
		{
			setButtonVisibility(bZoomPrimary, true);
			setButtonVisibility(bZoomSecondary, true);

			tabHandlerIndex = TAB_DELIVERED;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initReceivedDeliveredTab(m_tableDelivered, false);
					enableDisableButtons(m_tableDelivered);
				}
			};
			enableDisableButtons(m_tableDelivered);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_UNCONFIRMED))
		{
			setButtonVisibility(bZoomPrimary, false);
			setButtonVisibility(bZoomSecondary, false);

			tabHandlerIndex = TAB_UNCONFIRMED;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initUnconfirmedTab(m_tableUnconfirmed);
					enableDisableButtons(m_tableUnconfirmed);
				}
			};
			enableDisableButtons(m_tableUnconfirmed);
		}
		else if (tabIndex == ihTabHandler.getTabIndexEffective(TAB_ATP))
		{
			setButtonVisibility(bZoomPrimary, false);
			setButtonVisibility(bZoomSecondary, false);

			tabHandlerIndex = TAB_ATP;
			initializeTabRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					initAtpTab(m_tableAtp);
					enableDisableButtons(m_tableAtp);
				}
			};
			enableDisableButtons(m_tableAtp);
		}
		else
		{
			return;
		}

		//
		// Initialize tab
		ihTabHandler.initializeTab(initializeTabRunnable, tabHandlerIndex);
	}

	private void setButtonVisibility(final CButton button, final boolean visible)
	{
		if (button == null)
		{
			return;
		}
		button.setVisible(visible);
	}

	private void enableDisableButtons(final JTable table)
	{
		final boolean enabled = table.getSelectedRow() >= 0;
		if (bZoomPrimary != null)
		{
			bZoomPrimary.setEnabled(enabled);
		}
		if (bZoomSecondary != null)
		{
			bZoomSecondary.setEnabled(enabled);
		}
	}

	@Override
	public void dispose()
	{
		// task 09330: we reversed the order of setEnabled and dispose and that apparently solved the problem from this task
		getParent().setEnabled(true);

		super.dispose();
	}
}
