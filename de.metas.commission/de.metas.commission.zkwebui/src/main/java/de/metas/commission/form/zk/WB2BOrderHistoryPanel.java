package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.text.Format;
import java.util.Properties;

import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.component.GridPanel;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.East;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.webui.component.PropertyInfoPanel;
import de.metas.adempiere.webui.util.ZkUtil;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.interfaces.I_C_OrderLine;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class WB2BOrderHistoryPanel extends Borderlayout implements DataStatusListener

{
	private static final String B2BORDERHISTORYPANEL_RELATEDDOCUMENTS = "B2BOrderHistoryPanel.RelatedDocuments";

	public static final String B2BORDERHISTORYPANEL_ORDER = "B2BOrderHistoryPanel.Order";

	private static final long serialVersionUID = -5700650367021934239L;

	/** order Tab */
	public GridTab orderTab = null;
	/** order grid panel */
	public GridPanel orderPanel = null;
	/** order related documents Tab */
	private GridTab orderRelatedDocumentsTab = null;
	/** order related documents grid panel */
	private GridPanel orderRelatedDocumentsPanel = null;

	final private PropertyInfoPanel m_orderInfo = new PropertyInfoPanel();

	final private PropertyInfoPanel m_orderDocumentsInfoPIP = new PropertyInfoPanel();

	private int m_WindowNo;
	final private int AD_WINDOW_ID = 540071; // Auftrag Info window
	final private String RV_ORDER_DOCUMENTS_TABLE_NAME = "RV_Order_Documents";

	public WB2BOrderHistoryPanel(int windowNo, boolean simple)
	{
		super();

		m_WindowNo = windowNo;
		North lNorth = new North();
		this.appendChild(lNorth);
		lNorth.setHeight("25%");
		South lSouth = new South();
		this.appendChild(lSouth);
		lSouth.setHeight("75%");

		// info part
		Borderlayout blNorth = new Borderlayout();
		lNorth.appendChild(blNorth);

		West westInfo = new West();
		blNorth.appendChild(westInfo);
		westInfo.appendChild(m_orderInfo);
		m_orderInfo.setVflex(true);
		westInfo.setWidth("37%");

		East eastInfo = new East();
		blNorth.appendChild(eastInfo);
		eastInfo.appendChild(m_orderDocumentsInfoPIP);
		m_orderDocumentsInfoPIP.setVflex(true);
		m_orderDocumentsInfoPIP.setSize("20%", "80%");
		eastInfo.setWidth("63%");

		// order part
		Borderlayout blSouth = new Borderlayout();
		lSouth.appendChild(blSouth);

		West west = new West();
		blSouth.appendChild(west);

		East east = new East();
		blSouth.appendChild(east);

		// prepare the panel for order tab
		orderPanel = new GridPanel(m_WindowNo);
		west.appendChild(orderPanel);
		west.setTitle(Msg.translate(getCtx(), B2BORDERHISTORYPANEL_ORDER));
		west.setWidth("37%");

		// prepare the panel for order related documents
		orderRelatedDocumentsPanel = new GridPanel(m_WindowNo);
		east.appendChild(orderRelatedDocumentsPanel);
		east.setTitle(Msg.translate(getCtx(), B2BORDERHISTORYPANEL_RELATEDDOCUMENTS));
		east.setWidth("63%");
		
		if (simple)
			loadOrderTab();

	}


	public void loadOrderTab()
	{
		// load order tab
		MQuery query = new MQuery(I_C_Order.Table_ID);
		int AD_User_ID = Env.getAD_User_ID(getCtx());
		String whereClause = I_C_Order.COLUMNNAME_C_BPartner_ID + " = "
				+ "(SELECT u.C_BPartner_ID FROM AD_User u "
				+ " WHERE u.AD_User_ID = " + AD_User_ID + ")";
		query.addRestriction(whereClause);

		orderTab = ZkUtil.createGridTab(m_WindowNo, AD_WINDOW_ID, I_C_Order.Table_Name, query);
		orderTab.setQuery(query);
		orderTab.addDataStatusListener(this);

		orderPanel.refresh(orderTab);
		orderPanel.scrollToCurrentRow();
		orderPanel.showGrid(true);
		
		// load order related documents tab
		query = new MQuery(RV_ORDER_DOCUMENTS_TABLE_NAME);
		orderRelatedDocumentsTab = ZkUtil.createGridTab(m_WindowNo, AD_WINDOW_ID,
										RV_ORDER_DOCUMENTS_TABLE_NAME, query);
		orderRelatedDocumentsTab.setQuery(query);
		orderRelatedDocumentsTab.addDataStatusListener(this);

		orderRelatedDocumentsPanel.refresh(orderRelatedDocumentsTab);
		orderRelatedDocumentsPanel.showGrid(true);
		setInfoOrder(getCurrentRecord_ID());
		setInfoOrderDocuments(getRelatedDocumentCurrentRecord_ID(), getDocumentTableName(), getDocument_AD_Window_ID());

	}
	
	public void selectOrder(int order_id)
	{
		if (order_id > 0)
			orderTab.setCurrentRow(order_id);
	}

	public void queryOrder(int order_id)
	{
		MQuery query = new MQuery(RV_ORDER_DOCUMENTS_TABLE_NAME);
		query.addRestriction(I_C_Order.COLUMNNAME_C_Order_ID, MQuery.EQUAL, order_id);
		orderRelatedDocumentsTab.setQuery(query);
		orderRelatedDocumentsTab.query(false);
	}

	public int getCurrentRecord_ID()
	{
		return orderTab.getRecord_ID();
	}

	public int getRelatedDocumentCurrentRecord_ID()
	{
		return orderRelatedDocumentsTab.getRecord_ID();
	}

	public String getDocumentTableName()
	{
		if (!orderRelatedDocumentsTab.getTableModel().isOpen())
			return null;
		
		int row = orderRelatedDocumentsTab.getCurrentRow();
		Integer AD_Table_ID = (Integer)orderRelatedDocumentsTab.getValue(row, "AD_Table_ID");
		if (AD_Table_ID == null)
			return null;
		return MTable.getTableName(getCtx(), AD_Table_ID);
	}

	public int getDocument_AD_Window_ID()
	{
		Integer AD_Window_ID = (Integer)orderRelatedDocumentsTab.getValue("AD_Window_ID");
		if (AD_Window_ID == null)
			return -1;
		return AD_Window_ID;
	}

	public void setInfoOrder(int record_id)
	{
		m_orderInfo.resetValues();
		m_orderInfo.resetProperties();
		//
		MOrder order = new MOrder(getCtx(), record_id, null);
		IOrderBL orderBL = Services.get(IOrderBL.class);
		I_C_Order orderPO = POWrapper.create(order, I_C_Order.class);
		ZkUtil.setOrderOverview(m_orderInfo, orderBL, orderPO);

	}

	public void setInfoOrderDocuments(int record_id, String tableName, int AD_Window_ID)
	{

		// info variant
		PO po = (MTable.get(getCtx(), tableName)).getPO(record_id, null);
		//
		m_orderDocumentsInfoPIP.resetValues();
		m_orderDocumentsInfoPIP.resetProperties();
		m_orderDocumentsInfoPIP.setSize("50%", "50%");
		//
		final Format mf = DisplayType.getNumberFormat(DisplayType.Amount, Env.getLanguage(getCtx()));
		//
		if (I_C_Order.Table_Name.equals(tableName))
		{
			for (MOrderLine ol : ((MOrder)po).getLines())
			{
				String property = Msg.translate(getCtx(), I_C_OrderLine.COLUMNNAME_Line) + " "
								+ ol.getLine() + ":  " + ol.getQtyOrdered() + " x "
								+ ol.getM_Product().getValue() + " " + ol.getM_Product().getName()
								+ (Check.isEmpty(ol.getDescription(), true) ? "" : ", "+ol.getDescription())
								+ " :"
				;
				I_C_OrderLine olW = POWrapper.create(ol, I_C_OrderLine.class);

				String value = mf.format(ol.getLineNetAmt()) + " " + ol.getC_Currency().getCurSymbol()
								+ ", " + Msg.translate(getCtx(), I_C_OrderLine.COLUMNNAME_CommissionPointsSum)
								+ " " + mf.format(olW.getCommissionPointsSum())
								+ ", " + olW.getC_Tax().getName();

				m_orderDocumentsInfoPIP.setValues(property, value);
			}
		}
		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			for (MInvoiceLine il : ((MInvoice)po).getLines())
			{
				String property = Msg.translate(getCtx(), I_C_InvoiceLine.COLUMNNAME_Line) + " "
								+ il.getLine() + ":  " + il.getQtyInvoiced() + " x "
								+ il.getM_Product().getValue() + " " + il.getM_Product().getName() + " :";

				I_C_InvoiceLine ilW = POWrapper.create(il, I_C_InvoiceLine.class);

				String value = mf.format(il.getLineNetAmt()) + " " + ((MInvoice)po).getCurrencyISO()
								+ ", " + Msg.translate(getCtx(), I_C_OrderLine.COLUMNNAME_CommissionPointsSum)
								+ " " + mf.format(ilW.getCommissionPointsSum())
								+ ", " + ilW.getC_Tax().getName();

				m_orderDocumentsInfoPIP.setValues(property, value);
			}
		}
		if (I_M_InOut.Table_Name.equals(tableName))
		{
			m_orderDocumentsInfoPIP.setSize("15%", "85%");
			for (MInOutLine iol : ((MInOut)po).getLines())
			{
				String property = Msg.translate(getCtx(), I_M_InOutLine.COLUMNNAME_Line) + " "
								+ iol.getLine() + ":";

				String value = iol.getMovementQty() + " x " + iol.getM_Product().getValue()
								+ " " + iol.getM_Product().getName();

				m_orderDocumentsInfoPIP.setValues(property, value);
			}
		}

	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public void dataStatusChanged(DataStatusEvent dse)
	{
		if ((dse.getSource() instanceof GridTab && ((GridTab)dse.getSource()).getTableName().equals("C_Order"))
				|| (dse.getSource() instanceof GridTable && ((GridTable)dse.getSource()).getTableName().equals("C_Order")))
		{
			int record_id = (Integer)dse.Record_ID;
			queryOrder(record_id);
			setInfoOrder(record_id);

		}
		else if ((dse.getSource() instanceof GridTab && ((GridTab)dse.getSource()).getTableName().equals("RV_Order_Documents"))
				|| (dse.getSource() instanceof GridTable && ((GridTable)dse.getSource()).getTableName().equals("RV_Order_Documents")))
		{
			//
			String tableName = getDocumentTableName();
			int AD_Window_ID = getDocument_AD_Window_ID();
			int record_id = getRelatedDocumentCurrentRecord_ID();
			if (tableName == null || AD_Window_ID < 0 || record_id < 0)
				return;
			setInfoOrderDocuments(record_id, tableName, AD_Window_ID);
		}
	}

}
