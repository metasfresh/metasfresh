/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;

/**
 * Match PO Model.
 * = Created when processing Shipment or Order
 * - Updates Order (delivered, invoiced)
 * - Creates PPV acct
 *
 * @author Jorg Janke
 * @version $Id: MMatchPO.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Bayu Cahya, Sistematika
 *         <li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2314749 ] MatchPO not considering currency PriceMatchDifference
 *
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 2215840 ] MatchPO Bug Collection
 *         <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MMatchPO extends X_M_MatchPO
{
	private static final long serialVersionUID = 7189366329684552916L;

	private static final Logger logger = LogManager.getLogger(MMatchPO.class);

	public MMatchPO(final Properties ctx, final int M_MatchPO_ID, final String trxName)
	{
		super(ctx, M_MatchPO_ID, trxName);
		if (is_new())
		{
			// setC_OrderLine_ID (0);
			// setDateTrx (new Timestamp(System.currentTimeMillis()));
			// setM_InOutLine_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			// setQty (BigDecimal.ZERO);
			setPosted(false);
			setProcessed(false);
			setProcessing(false);
		}
	}

	public MMatchPO(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get PriceActual from Invoice and convert it to Order Currency
	 *
	 * @return Price Actual in Order Currency
	 */
	private BigDecimal getInvoicePriceActual()
	{
		final I_C_InvoiceLine iLine = getC_InvoiceLine();
		final I_C_Invoice invoice = iLine.getC_Invoice();
		final I_C_Order order = getC_OrderLine().getC_Order();

		BigDecimal priceActual = iLine.getPriceActual();
		final int invoiceCurrency_ID = invoice.getC_Currency_ID();
		final int orderCurrency_ID = order.getC_Currency_ID();
		if (invoiceCurrency_ID != orderCurrency_ID)
		{
			priceActual = Services.get(ICurrencyBL.class).convert(
					priceActual, 
					CurrencyId.ofRepoId(invoiceCurrency_ID), 
					CurrencyId.ofRepoId(orderCurrency_ID),
					TimeUtil.asLocalDate(invoice.getDateInvoiced()), 
					CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
					ClientId.ofRepoId(getAD_Client_ID()), 
					OrgId.ofRepoId(getAD_Org_ID()));
		}
		return priceActual;
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Set Trx Date
		if (getDateTrx() == null)
		{
			setDateTrx(SystemTime.asDayTimestamp());
		}

		// Set Acct Date
		if (getDateAcct() == null)
		{
			Timestamp ts = getNewerDateAcct();
			if (ts == null)
			{
				ts = getDateTrx();
			}
			setDateAcct(ts);
		}

		// Set ASI from Receipt
		final int mpoASIId = getM_AttributeSetInstance_ID();
		if (mpoASIId <= 0 && getM_InOutLine_ID() > 0)
		{
			final I_M_InOutLine iol = getM_InOutLine();
			setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
		}

		// Bayu, Sistematika
		// BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
		// If newRecord, set c_invoiceline_id while null
		if (newRecord && getC_InvoiceLine_ID() <= 0)
		{
			final List<I_M_MatchInv> matchInvs = Services.get(IMatchInvDAO.class).retrieveForInOutLine(getM_InOutLine());
			for (final I_M_MatchInv matchInv : matchInvs)
			{
				if (matchInv.getC_InvoiceLine_ID() > 0 && matchInv.getM_AttributeSetInstance_ID() == mpoASIId)
				{
					setC_InvoiceLine_ID(matchInv.getC_InvoiceLine_ID());
					break;
				}
			}
		}
		// end Bayu

		// Find OrderLine
		if (getC_OrderLine_ID() <= 0)
		{
			final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
			if (invoiceLine != null && invoiceLine.getC_OrderLine_ID() > 0)
			{
				setC_OrderLine_ID(invoiceLine.getC_OrderLine_ID());
			}

			if (getC_OrderLine_ID() <= 0)
			{
				final I_M_InOutLine inoutLine = getM_InOutLine();
				if (inoutLine != null && inoutLine.getC_OrderLine_ID() > 0)
				{
					setC_OrderLine_ID(inoutLine.getC_OrderLine_ID());
					if (invoiceLine != null)
					{
						invoiceLine.setC_OrderLine_ID(inoutLine.getC_OrderLine_ID());
						InterfaceWrapperHelper.save(invoiceLine);
					}
				}
			}
		}	// find order line

		// Price Match Approval
		if (getC_OrderLine_ID() > 0
				&& getC_InvoiceLine_ID() > 0
				&& (newRecord || is_ValueChanged(COLUMNNAME_C_OrderLine_ID) || is_ValueChanged(COLUMNNAME_C_InvoiceLine_ID)))
		{
			final BigDecimal poPrice = getC_OrderLine().getPriceActual();
			final BigDecimal invPrice = getInvoicePriceActual();
			BigDecimal difference = poPrice.subtract(invPrice);
			if (difference.signum() != 0)
			{
				difference = difference.multiply(getQty());
				setPriceMatchDifference(difference);
				// Approval
				final BPartnerId bpartnerId = BPartnerId.ofRepoId(getC_OrderLine().getC_BPartner_ID());
				final I_C_BP_Group bpGroup = Services.get(IBPGroupDAO.class).getByBPartnerId(bpartnerId);
				final BigDecimal matchTolerance = bpGroup.getPriceMatchTolerance();
				if (matchTolerance != null && matchTolerance.signum() != 0)
				{
					final BigDecimal poAmt = poPrice.multiply(getQty());
					BigDecimal maxTolerance = poAmt.multiply(matchTolerance);
					maxTolerance = maxTolerance.abs().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
					difference = difference.abs();
					final boolean ok = difference.compareTo(maxTolerance) <= 0;
					logger.info("Difference=" + getPriceMatchDifference() + ", Max=" + maxTolerance + " => " + ok);
					setIsApproved(ok);
				}
			}
			else
			{
				setPriceMatchDifference(difference);
				setIsApproved(true);
			}
		}

		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Purchase Order Delivered/Invoiced
		// (Reserved in VMatch and MInOut.completeIt)
		if (getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = getC_OrderLine();

			if (InterfaceWrapperHelper.isValueChanged(this, COLUMNNAME_M_InOutLine_ID) /* task 09084 => */ || newRecord)
			{
				if (getM_InOutLine_ID() > 0)
				{
					// a new delivery line was linked to the order line => add the qty
					orderLine.setQtyDelivered(orderLine.getQtyDelivered().add(getQty()));
					orderLine.setDateDelivered(getDateTrx());	// overwrite=last
				}
				else if (getM_InOutLine_ID() <= 0 && !newRecord)
				{
					// a previously linked delivery line was unlinked from an existing matchPO (and thus from the order line) => subtract the qty
					orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
				}
			}

			if (InterfaceWrapperHelper.isValueChanged(this, COLUMNNAME_C_InvoiceLine_ID) /* task 09084 => */ || newRecord)
			{
				if (getC_InvoiceLine_ID() > 0)
				{
					// a new invoice line was linked to the order line => add the qty
					orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().add(getQty()));
					orderLine.setDateInvoiced(getDateTrx());	// overwrite=last
				}
				else if (getC_InvoiceLine_ID() <= 0 && !newRecord)
				{
					// a previously linked invoice line was unlinked from an existing matchPO (and thus from the order line) => subtract the qty
					orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
				}
			}

			// Update Order ASI if full match
			if (orderLine.getM_AttributeSetInstance_ID() == 0
					&& getM_InOutLine_ID() > 0)
			{
				final I_M_InOutLine iol = getM_InOutLine();
				if (iol.getMovementQty().compareTo(orderLine.getQtyOrdered()) == 0)
				{
					orderLine.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
				}
			}
			InterfaceWrapperHelper.save(orderLine);
		}

		//
		return true;
	}	// afterSave

	/**
	 * Get the later Date Acct from invoice or shipment
	 *
	 * @return date or null
	 */
	private Timestamp getNewerDateAcct()
	{
		Timestamp invoiceDate = null;
		Timestamp shipDate = null;

		if (getC_InvoiceLine_ID() > 0)
		{
			final String sql = "SELECT i.DateAcct "
					+ "FROM C_InvoiceLine il"
					+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
					+ "WHERE C_InvoiceLine_ID=?";
			invoiceDate = DB.getSQLValueTS(null, sql, getC_InvoiceLine_ID());
		}
		//
		if (getM_InOutLine_ID() > 0)
		{
			final String sql = "SELECT io.DateAcct "
					+ "FROM M_InOutLine iol"
					+ " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
					+ "WHERE iol.M_InOutLine_ID=?";
			shipDate = DB.getSQLValueTS(null, sql, getM_InOutLine_ID());
		}
		//
		// Assuming that order date is always earlier
		if (invoiceDate == null)
		{
			return shipDate;
		}
		if (shipDate == null)
		{
			return invoiceDate;
		}
		if (invoiceDate.after(shipDate))
		{
			return invoiceDate;
		}
		return shipDate;
	}	// getNewerDateAcct

	@Override
	protected boolean beforeDelete()
	{
		if (isPosted())
		{
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), X_C_DocType.DOCBASETYPE_MatchPO, getAD_Org_ID());
			setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocumentModel(this);
		}

		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);
		costDetailService.voidAndDeleteForDocument(CostingDocumentRef.ofMatchPOId(getM_MatchPO_ID()));

		return true;
	}

	@Override
	protected boolean afterDelete(final boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Order Delivered/Invoiced
		// (Reserved in VMatch and MInOut.completeIt)
		final I_C_OrderLine orderLine = getC_OrderLine();
		if (orderLine != null)
		{
			if (getM_InOutLine_ID() > 0)
			{
				orderLine.setQtyDelivered(orderLine.getQtyDelivered().subtract(getQty()));
			}
			if (getC_InvoiceLine_ID() > 0)
			{
				orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().subtract(getQty()));
			}
			InterfaceWrapperHelper.save(orderLine);
		}

		return true;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("MMatchPO[")
				.append(getM_MatchPO_ID())
				.append(",Qty=").append(getQty())
				.append(",C_OrderLine_ID=").append(getC_OrderLine_ID())
				.append(",M_InOutLine_ID=").append(getM_InOutLine_ID())
				.append(",C_InvoiceLine_ID=").append(getC_InvoiceLine_ID())
				.append("]")
				.toString();
	}
}	// MMatchPO
