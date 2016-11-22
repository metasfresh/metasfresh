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
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.adempiere.exceptions.DBException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.CustomColNames;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceSchedule;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPriceList;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.interfaces.I_C_OrderLine;

/**
 * Generate Invoices
 *
 * @author Jorg Janke
 * @author t.schoeneberg@metas.de: invoices are never consolidated if {@link CustomColNames#C_BPartner_ALLOW_CONSOLIDATE_INVOICE} is 'N'
 */
// metas: synched with rev. 9749
public class InvoiceGenerate extends JavaProcess
{

	final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	/** Manual Selection */
	private boolean p_Selection = false;
	/** Date Invoiced */
	private Timestamp p_DateInvoiced = null;
	/** Org */
	private int p_AD_Org_ID = 0;
	/** BPartner */
	private int p_C_BPartner_ID = 0;
	/** Order */
	private int p_C_Order_ID = 0;
	/** Consolidate */
	private boolean p_ConsolidateDocument = true;
	/** Invoice Document Action */
	private String p_docAction = DocAction.ACTION_Complete;

	/** The current Invoice */
	private MInvoice m_invoice = null;
	/** The current Shipment */
	private MInOut m_ship = null;
	/** Numner of Invoices */
	private int m_created = 0;
	/** Line Number */
	private int m_line = 0;
	/** Business Partner */
	private MBPartner m_bp = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Selection"))
				p_Selection = "Y".equals(para[i].getParameter());
			else if (name.equals("DateInvoiced"))
				p_DateInvoiced = (Timestamp)para[i].getParameter();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Order_ID"))
				p_C_Order_ID = para[i].getParameterAsInt();
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(para[i].getParameter());
			else if (name.equals("DocAction"))
				p_docAction = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}

		// Login Date
		if (p_DateInvoiced == null)
			p_DateInvoiced = Env.getContextAsDate(getCtx(), "#Date");
		if (p_DateInvoiced == null)
			p_DateInvoiced = new Timestamp(System.currentTimeMillis());

		// DocAction check
		if (!DocAction.ACTION_Complete.equals(p_docAction))
			p_docAction = DocAction.ACTION_Prepare;
	} // prepare

	/**
	 * Generate Invoices
	 *
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("Selection=" + p_Selection + ", DateInvoiced="
				+ p_DateInvoiced + ", AD_Org_ID=" + p_AD_Org_ID
				+ ", C_BPartner_ID=" + p_C_BPartner_ID + ", C_Order_ID="
				+ p_C_Order_ID + ", DocAction=" + p_docAction
				+ ", Consolidate=" + p_ConsolidateDocument);
		//
		String sql = null;
		if (p_Selection) // VInvoiceGen
		{
			sql = "SELECT C_Order.* FROM C_Order, T_Selection "
					+ "WHERE C_Order.DocStatus='CO' AND C_Order.IsSOTrx='Y' "
					+ "AND C_Order.C_Order_ID = T_Selection.T_Selection_ID "
					+ "AND T_Selection.AD_PInstance_ID=? "
					+ "ORDER BY C_Order.M_Warehouse_ID, C_Order.PriorityRule, C_Order.C_BPartner_ID, C_Order.C_Order_ID";
		}
		else
		{
			// metas: we don't invoice closed orders, even if there are open deliveries!
			sql = "SELECT * FROM C_Order o "
					+ "WHERE DocStatus IN('CO') AND IsSOTrx='Y'";
			// metas end
			if (p_AD_Org_ID != 0)
				sql += " AND AD_Org_ID=?";
			if (p_C_BPartner_ID != 0)
				sql += " AND C_BPartner_ID=?";
			if (p_C_Order_ID != 0)
				sql += " AND C_Order_ID=?";
			//
			sql += " AND EXISTS (SELECT * FROM C_OrderLine ol "
					+ "WHERE o.C_Order_ID=ol.C_Order_ID AND ol.QtyOrdered<>ol.QtyInvoiced) "
					+ "AND o.C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType "
					+ "WHERE DocBaseType='SOO' AND DocSubType NOT IN ('ON','OB','WR')) "
					+ "ORDER BY M_Warehouse_ID, PriorityRule, C_BPartner_ID, C_Order_ID";
		}
		// sql += " FOR UPDATE";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			int index = 1;
			if (p_Selection)
			{
				pstmt.setInt(index, getAD_PInstance_ID());
			}
			else
			{
				if (p_AD_Org_ID != 0)
					pstmt.setInt(index++, p_AD_Org_ID);
				if (p_C_BPartner_ID != 0)
					pstmt.setInt(index++, p_C_BPartner_ID);
				if (p_C_Order_ID != 0)
					pstmt.setInt(index++, p_C_Order_ID);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		return generate(pstmt);
	} // doIt

	/**
	 * Generate Shipments
	 *
	 * @param pstmt
	 *            order query
	 * @return info
	 */
	private String generate(PreparedStatement pstmt)
	{

		ResultSet rs = null;
		try
		{
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MOrder order = new MOrder(getCtx(), rs, get_TrxName());

				final boolean consolidate = computeConsolidate(order);
				final MDocType docType = MDocType.get(Env.getCtx(), order
						.getC_DocType_ID());

				// New Invoice Location
				// metas (2009-0027-G5): a (metas) prepay order is not
				// consolidated
				final boolean prepayOrder = de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas
						.equals(docType.getDocSubType());

				final boolean differentAddress = m_invoice != null
						&& m_invoice.getC_BPartner_Location_ID() != order
								.getBill_Location_ID();

				if (prepayOrder || !consolidate || differentAddress)
				{
					completeInvoice();
				}
				boolean completeOrder = MOrder.INVOICERULE_AfterOrderDelivered
						.equals(order.getInvoiceRule());

				// Schedule After Delivery
				boolean doInvoice = false;
				if (MOrder.INVOICERULE_CustomerScheduleAfterDelivery
						.equals(order.getInvoiceRule()))
				{
					m_bp = new MBPartner(getCtx(), order.getBill_BPartner_ID(),
							null);
					if (m_bp.getC_InvoiceSchedule_ID() == 0)
					{
						log
								.warn("BPartner has no Schedule - set to After Delivery");
						order.setInvoiceRule(MOrder.INVOICERULE_AfterDelivery);
						order.save();
					}
					else
					{
						MInvoiceSchedule is = MInvoiceSchedule.get(getCtx(),
								m_bp.getC_InvoiceSchedule_ID(), get_TrxName());
						if (is.canInvoice(order.getDateOrdered(), order
								.getGrandTotal()))
							doInvoice = true;
						else
							continue;
					}
				} // Schedule

				// After Delivery
				if (doInvoice
						|| MOrder.INVOICERULE_AfterDelivery.equals(order
								.getInvoiceRule()))
				{
					MInOut[] shipments = order.getShipments();
					for (int i = 0; i < shipments.length; i++)
					{
						MInOut ship = shipments[i];
						if (!ship.isComplete() // ignore incomplete or reversals
								|| ship.getDocStatus().equals(
										MInOut.DOCSTATUS_Reversed))
							continue;
						MInOutLine[] shipLines = ship.getLines(false);
						for (int j = 0; j < shipLines.length; j++)
						{
							MInOutLine shipLine = shipLines[j];
							if (!order
									.isOrderLine(shipLine.getC_OrderLine_ID()))
								continue;
							if (!shipLine.isInvoiced())
								createLine(order, ship, shipLine, consolidate);
						}
						m_line += 1000;
					}
				}
				// After Order Delivered, Immediate
				else
				{
					MOrderLine[] oLines = order.getLines(true, null);
					for (int i = 0; i < oLines.length; i++)
					{
						MOrderLine oLine = oLines[i];
						BigDecimal toInvoice = oLine.getQtyOrdered().subtract(
								oLine.getQtyInvoiced());
						if (toInvoice.compareTo(Env.ZERO) == 0
								&& oLine.getM_Product_ID() != 0)
							continue;
						BigDecimal notInvoicedShipment = oLine
								.getQtyDelivered().subtract(
										oLine.getQtyInvoiced());
						//
						boolean fullyDelivered = oLine.getQtyOrdered()
								.compareTo(oLine.getQtyDelivered()) == 0;

						// Complete Order
						if (completeOrder && !fullyDelivered)
						{
							log.debug("Failed CompleteOrder - " + oLine);
							addLog("Failed CompleteOrder - " + oLine); // Elaine
							// 2008/11/25
							completeOrder = false;
							break;
						}
						// Immediate
						else if (MOrder.INVOICERULE_Immediate.equals(order
								.getInvoiceRule()))
						{
							log.debug("Immediate - ToInvoice=" + toInvoice
									+ " - " + oLine);
							BigDecimal qtyEntered = toInvoice;
							// Correct UOM for QtyEntered
							if (oLine.getQtyEntered().compareTo(
									oLine.getQtyOrdered()) != 0)
								qtyEntered = toInvoice.multiply(
										oLine.getQtyEntered()).divide(
										oLine.getQtyOrdered(), 12,
										BigDecimal.ROUND_HALF_UP);
							createLine(order, oLine, toInvoice, qtyEntered,
									consolidate);
						}
						else
						{
							log.debug("Failed: " + order.getInvoiceRule()
									+ " - ToInvoice=" + toInvoice + " - "
									+ oLine);
							addLog("Failed: " + order.getInvoiceRule()
									+ " - ToInvoice=" + toInvoice + " - "
									+ oLine);
						}
					} // for all order lines
					if (MOrder.INVOICERULE_Immediate.equals(order
							.getInvoiceRule()))
						m_line += 1000;
				}

				// Complete Order successful
				if (completeOrder
						&& MOrder.INVOICERULE_AfterOrderDelivered.equals(order
								.getInvoiceRule()))
				{
					MInOut[] shipments = order.getShipments();
					for (int i = 0; i < shipments.length; i++)
					{
						MInOut ship = shipments[i];
						if (!ship.isComplete() // ignore incomplete or reversals
								|| ship.getDocStatus().equals(
										MInOut.DOCSTATUS_Reversed))
							continue;
						MInOutLine[] shipLines = ship.getLines(false);
						for (int j = 0; j < shipLines.length; j++)
						{
							MInOutLine shipLine = shipLines[j];
							if (!order
									.isOrderLine(shipLine.getC_OrderLine_ID()))
								continue;
							if (!shipLine.isInvoiced())
								createLine(order, ship, shipLine, consolidate);
						}
						m_line += 1000;
					}
				} // complete Order
			} // for all orders
		}
		catch (SQLException e)
		{
			// metas: applying best practices
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// metas : renumber invoice lines
		if (m_invoice != null)
		{
			m_bp = new MBPartner(getCtx(), m_invoice.getC_BPartner_ID(),
					get_TrxName());
			if (p_ConsolidateDocument && allowsCons(m_invoice.isSOTrx()))
			{
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(m_invoice, de.metas.adempiere.model.I_C_Invoice.class);
				invoiceBL.renumberLines(invoice, 10);
			}
		}
		// metas end

		completeInvoice();
		return "@Created@ = " + m_created;
	} // generate

	private boolean allowsCons(final boolean isSOTrx)
	{
		// final I_C_BPartner bPartner = InterfaceWrapperHelper.create(m_bp, I_C_BPartner.class);
		// return Services.get(IBPartnerBL.class).isAllowConsolidateInvoiceEffective(bPartner, isSOTrx);
		return isSOTrx;
	}

	/**************************************************************************
	 * Create Invoice Line from Order Line
	 *
	 * @param order
	 *            order
	 * @param orderLine
	 *            line
	 * @param qtyInvoiced
	 *            qty
	 * @param qtyEntered
	 *            qty
	 */
	private void createLine(final MOrder order, final MOrderLine orderLine,
			final BigDecimal qtyInvoiced, final BigDecimal qtyEntered,
			final boolean consolidate)
	{

		if (m_invoice == null)
		{
			m_invoice = new MInvoice(order, 0, p_DateInvoiced);
			m_invoice.setM_PriceList_ID(MPriceList.M_PriceList_ID_None); // US1184
			m_invoice.setC_Currency_ID(order.getC_Currency_ID()); // US1184
			if (!m_invoice.save())
				throw new IllegalStateException("Could not create Invoice (o)");
		}
		//
		MInvoiceLine line = new MInvoiceLine(m_invoice);
		line.setOrderLine(orderLine);
		line.setQtyInvoiced(qtyInvoiced);
		line.setQtyEntered(qtyEntered);

		Services.get(IPOService.class).copyValue(
				orderLine, line, I_C_OrderLine.COLUMNNAME_IsIndividualDescription);

		setLineNo(line, orderLine.getLine(), consolidate);
		if (!line.save())
			throw new IllegalStateException("Could not create Invoice Line (o)");
		log.debug(line.toString());
	} // createLine

	private void setLineNo(final MInvoiceLine line, final int sourceLineNo,
			final boolean consolidate)
	{
		if (consolidate)
		{
			// we have order lines from multiple orders in this invoice, so
			// we use m_line to make sure that there are no collisions
			line.setLine(m_line + sourceLineNo);
		}
		else
		{
			line.setLine(sourceLineNo);
		}
	}

	/**
	 * Create Invoice Line from Shipment
	 *
	 * @param order
	 *            order
	 * @param ship
	 *            shipment header
	 * @param sLine
	 *            shipment line
	 */
	private void createLine(final MOrder order, final MInOut ship,
			final MInOutLine sLine, final boolean consolidate)
	{

		if (m_invoice == null)
		{
			m_invoice = new MInvoice(order, 0, p_DateInvoiced);
			m_invoice.setM_PriceList_ID(MPriceList.M_PriceList_ID_None); // US1184
			m_invoice.setC_Currency_ID(order.getC_Currency_ID()); // US1184
			if (!m_invoice.save())
				throw new IllegalStateException("Could not create Invoice (s)");
		}
		// Create Shipment Comment Line
		if (m_ship == null || m_ship.getM_InOut_ID() != ship.getM_InOut_ID())
		{
			MDocType dt = MDocType.get(getCtx(), ship.getC_DocType_ID());
			if (m_bp == null
					|| m_bp.getC_BPartner_ID() != ship.getC_BPartner_ID())
				m_bp = new MBPartner(getCtx(), ship.getC_BPartner_ID(),
						get_TrxName());

			// Reference: Delivery: 12345 - 12.12.12
			MClient client = MClient.get(getCtx(), order.getAD_Client_ID());
			String AD_Language = client.getAD_Language();
			if (client.isMultiLingualDocument()
					&& m_bp.getAD_Language() != null)
				AD_Language = m_bp.getAD_Language();
			if (AD_Language == null)
				AD_Language = Language.getBaseAD_Language();
			java.text.SimpleDateFormat format = DisplayType.getDateFormat(
					DisplayType.Date, Language.getLanguage(AD_Language));
			String reference = dt.getPrintName(m_bp.getAD_Language()) + ": "
					+ ship.getDocumentNo() + " - "
					+ format.format(ship.getMovementDate());
			m_ship = ship;
			//
			MInvoiceLine line = new MInvoiceLine(m_invoice);
			line.setIsDescription(true);
			line.setDescription(reference);
			setLineNo(line, sLine.getLine() - 2, consolidate);

			if (!line.save())
				throw new IllegalStateException(
						"Could not create Invoice Comment Line (sh)");
			// Optional Ship Address if not Bill Address
			if (order.getBill_Location_ID() != ship.getC_BPartner_Location_ID())
			{
				MLocation addr = MLocation.getBPLocation(getCtx(), ship
						.getC_BPartner_Location_ID(), null);
				line = new MInvoiceLine(m_invoice);
				line.setIsDescription(true);
				line.setDescription(addr.toString());
				setLineNo(line, sLine.getLine() - 1, consolidate);
				if (!line.save())
					throw new IllegalStateException(
							"Could not create Invoice Comment Line 2 (sh)");
			}
		}
		//
		MInvoiceLine line = new MInvoiceLine(m_invoice);
		line.setShipLine(sLine);
		if (sLine.sameOrderLineUOM())
			line.setQtyEntered(sLine.getQtyEntered());
		else
			line.setQtyEntered(sLine.getMovementQty());
		line.setQtyInvoiced(sLine.getMovementQty());
		setLineNo(line, sLine.getLine(), consolidate);

		Services.get(IPOService.class).copyValue(sLine, line,
				CustomColNames.M_InOutLine_PRODUCT_DESC);

		if (!line.save())
			throw new IllegalStateException(
					"Could not create Invoice Line (s):\n"
							+ MiscUtils.loggerMsgsUser());
		// Link
		sLine.setIsInvoiced(true);
		if (!sLine.save())
			throw new IllegalStateException("Could not update Shipment Line.:\n"
					+ MiscUtils.loggerMsgsUser());

		log.debug(line.toString());
	} // createLine

	/**
	 * Complete Invoice
	 */
	private void completeInvoice()
	{
		if (m_invoice != null)
		{
			// metas: Neunumerierung der Rechnung (10, 20, ...)
			if (p_ConsolidateDocument && allowsCons(m_invoice.isSOTrx()))
			{
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(m_invoice, de.metas.adempiere.model.I_C_Invoice.class);
				invoiceBL.renumberLines(invoice, 10);
			}
			// metas end

			if (!m_invoice.processIt(p_docAction))
			{
				log.warn("completeInvoice - failed: " + m_invoice);
				addLog("completeInvoice - failed: " + m_invoice); // Elaine
				// 2008/11/25
			}
			m_invoice.save();
			//
			addLog(m_invoice.getC_Invoice_ID(), m_invoice.getDateInvoiced(),
					null, m_invoice.getDocumentNo());
			m_created++;
		}
		m_invoice = null;
		m_ship = null;
		m_line = 0;
	} // completeInvoice

	/**
	 *
	 * @param order
	 *
	 * @return true iff both {@link #p_ConsolidateDocument} and
	 *         CustomColNames.C_BPartner_ALLOW_CONSOLIDATE_INVOICE of the
	 *         order's boll bPArtner are true.
	 */
	private boolean computeConsolidate(final I_C_Order order)
	{
		if (!p_ConsolidateDocument)
		{
			return false;
		}

		// final I_C_BPartner bPartner = InterfaceWrapperHelper.create(getCtx(), order.getBill_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		// return Services.get(IBPartnerBL.class).isAllowConsolidateInvoiceEffective(bPartner, order.isSOTrx());
		return order.isSOTrx();
	}

} // InvoiceGenerate
