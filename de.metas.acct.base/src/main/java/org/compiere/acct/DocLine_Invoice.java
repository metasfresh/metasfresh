package org.compiere.acct;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MTax;
import org.slf4j.Logger;

import de.metas.invoice.IMatchInvDAO;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;

public class DocLine_Invoice extends DocLine<Doc_Invoice>
{
	// services
	private static final Logger logger = LogManager.getLogger(DocLine_Invoice.class);
	private final transient IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient ITaxBL taxBL = Services.get(ITaxBL.class);

	private BigDecimal _includedTaxAmt = BigDecimal.ZERO;
	private BigDecimal _qtyReceived = null;

	public DocLine_Invoice(final I_C_InvoiceLine invoiceLine, final Doc_Invoice doc)
	{
		super(InterfaceWrapperHelper.getPO(invoiceLine), doc);

		setIsTaxIncluded(invoiceBL.isTaxIncluded(invoiceLine));

		// Qty
		final BigDecimal qtyInvoiced = invoiceLine.getQtyInvoiced();
		final boolean isCreditMemo = doc.isCreditMemo();
		setQty(isCreditMemo ? qtyInvoiced.negate() : qtyInvoiced, doc.isSOTrx());

		//
		BigDecimal lineNetAmt = invoiceLine.getLineNetAmt();
		BigDecimal priceList = invoiceLine.getPriceList();

		// Correct included Tax
		final int C_Tax_ID = getC_Tax_ID();
		if (isTaxIncluded() && C_Tax_ID > 0)
		{
			final MTax tax = MTax.get(getCtx(), C_Tax_ID);
			if (!tax.isZeroTax())
			{
				final int taxPrecision = doc.getStdPrecision();
				final BigDecimal lineTaxAmt = taxBL.calculateTax(tax, lineNetAmt, true, taxPrecision);
				logger.debug("LineNetAmt={} - LineTaxAmt={}", lineNetAmt, lineTaxAmt);
				lineNetAmt = lineNetAmt.subtract(lineTaxAmt);

				final BigDecimal priceListTax = taxBL.calculateTax(tax, priceList, true, taxPrecision);
				priceList = priceList.subtract(priceListTax);

				_includedTaxAmt = lineTaxAmt;
			}
		}	// correct included Tax

		setAmount(lineNetAmt, priceList, qtyInvoiced);	// qty for discount calc
	}

	public final I_C_InvoiceLine getC_InvoiceLine()
	{
		return getModel(I_C_InvoiceLine.class);
	}

	/**
	 * @return true if this invoice line is part of a credit memo invoice
	 */
	public final boolean isCreditMemo()
	{
		return getDoc().isCreditMemo();
	}

	/**
	 * Adjusts the sign of given relative <code>qty</code> using the credit memo and SOTrx flags.
	 *
	 * Mainly it is:
	 * <ul>
	 * <li>if {@link #isCreditMemo()}, negate the quantity
	 * <li>if {@link #isSOTrx()}, negate the quantity
	 * </ul>
	 *
	 * @param qty
	 * @return quantity (absolute value)
	 */
	private BigDecimal adjustQtySignByCreditMemoAndSOTrx(final BigDecimal qty)
	{
		if (qty == null || qty.signum() == 0)
		{
			return qty;
		}

		BigDecimal qtyAdjusted = qty;
		if (isCreditMemo())
		{
			qtyAdjusted = qtyAdjusted.negate();
		}
		if (isSOTrx())
		{
			qtyAdjusted = qtyAdjusted.negate();
		}

		return qtyAdjusted;
	}

	/**
	 * @return amount of the included tax or ZERO if this line has not included tax.
	 */
	public BigDecimal getIncludedTaxAmt()
	{
		return _includedTaxAmt;
	}

	/**
	 * @return quantity received
	 */
	public final BigDecimal getQtyReceived()
	{
		if (_qtyReceived == null)
		{
			final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
			BigDecimal qtyReceived = matchInvDAO.retrieveForInvoiceLineQuery(invoiceLine)
					.create()
					.aggregate(I_M_MatchInv.COLUMNNAME_Qty, Aggregate.SUM, BigDecimal.class);
			if (qtyReceived == null)
			{
				qtyReceived = BigDecimal.ZERO;
			}
			this._qtyReceived = qtyReceived;
		}
		return _qtyReceived;
	}

	/**
	 * @return quantity received (absolute value)
	 */
	public final BigDecimal getQtyReceivedAbs()
	{
		return adjustQtySignByCreditMemoAndSOTrx(getQtyReceived());
	}

	/**
	 * @return quantity invoiced
	 */
	public final BigDecimal getQtyInvoiced()
	{
		return getC_InvoiceLine().getQtyInvoiced();
	}

	/**
	 * @return quantity invoiced but not received
	 */
	public final BigDecimal getQtyNotReceived()
	{
		return getQtyInvoiced().subtract(getQtyReceived());
	}

	/**
	 * @return quantity invoiced but not received (absolute value)
	 */
	public final BigDecimal getQtyNotReceivedAbs()
	{
		return adjustQtySignByCreditMemoAndSOTrx(getQtyNotReceived());
	}

	/**
	 * Calculate the net amount of quantity received using <code>lineNetAmt</code> as total amount.
	 *
	 * @param lineNetAmt
	 * @return quantity received invoiced amount
	 */
	public BigDecimal calculateAmtOfQtyReceived(final BigDecimal lineNetAmt)
	{
		if (lineNetAmt.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qtyReceived = getQtyReceived();
		if (qtyReceived.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qtyInvoiced = getQtyInvoiced();
		if (qtyInvoiced.signum() == 0)
		{
			// shall not happen
			return BigDecimal.ZERO;
		}

		// If it was fully received, there is no need to divide and then multiply the lineNetAmt.
		// We can return it right away.
		if (qtyInvoiced.compareTo(qtyReceived) == 0)
		{
			return lineNetAmt;
		}

		final BigDecimal qtyReceivedMultiplier = qtyReceived.divide(qtyInvoiced, 12, RoundingMode.HALF_UP);

		final BigDecimal receivedAmt = lineNetAmt.multiply(qtyReceivedMultiplier)
				.setScale(getStdPrecision(), RoundingMode.HALF_UP);
		return receivedAmt;
	}

	/**
	 * Checks if invoice reposting is needed when given <code>matchInv</code> was created.
	 *
	 * @param matchInv
	 * @return true if invoice reposting is needed
	 */
	public static boolean isInvoiceRepostingRequired(final I_M_MatchInv matchInv)
	{
		// Reposting is required if a M_MatchInv was created for a purchase invoice.
		// ... because we book the matched quantity on InventoryClearing and on Expense the not matched quantity
		if (!matchInv.getC_InvoiceLine().getC_Invoice().isSOTrx())
		{
			return true;
		}

		return false; // not needed
	}

}
