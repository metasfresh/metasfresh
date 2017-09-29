/**
 *
 */
package de.metas.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.exceptions.InvalidQtyForPartialAmtToInvoiceException;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;

/**
 * @author cg
 *
 */
public class ManualCandidateHandler extends AbstractInvoiceCandidateHandler
{
	/**
	 * Not actually a real table name but a marker that is used to pick this manual handler. Please note that {@link #getSourceTable()} returns {@link #MANUAL}.
	 */
	final public static String MANUAL = "ManualCandidateHandler";

	private final static transient Logger logger = InvoiceCandidate_Constants.getLogger(ManualCandidateHandler.class);
	
	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return false;
	}
	
	@Override
	public boolean isCreateMissingCandidatesAutomatically(Object model)
	{
		return false;
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<Object> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return Collections.emptyIterator();
	}

	/**
	 * @return empty result
	 */
	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		return InvoiceCandidateGenerateResult.of(this);
	}

	/**
	 * Implementation invalidates the credit memo's C_Invoice_Candidate
	 */
	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		// nothing to do
	}

	/**
	 * Returns CreditMemo, which is not a real table; the source doesn't exist actually
	 */
	@Override
	public String getSourceTable()
	{
		return ManualCandidateHandler.MANUAL;
	}

	/**
	 * Returns <code>true</code>.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return true;
	}

	/**
	 * Sets NetAmtToInvoice by using super{@link #setNetAmtToInvoice(I_C_Invoice_Candidate)}.
	 *
	 * If the amount is negative than we calculate the over all amount of the invoice and we adjust the NetAmtToInvoice in order to get a positive GrandTotal for the invoice
	 */
	@Override
	public void setNetAmtToInvoice(final I_C_Invoice_Candidate ic)
	{
		super.setNetAmtToInvoice(ic);

		final BigDecimal netAmtToInvoice = ic.getNetAmtToInvoice();
		if (netAmtToInvoice.signum() > 0)
		{
			return; // the superclass already did the job
		}
		ManualCandidateHandler.logger.debug("NetAmtToInvoice: {}", netAmtToInvoice);

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		final IInvoiceCandidateQuery query = invoiceCandDAO.newInvoiceCandidateQuery();
		query.setHeaderAggregationKey(ic.getHeaderAggregationKey());
		query.setMaxManualC_Invoice_Candidate_ID(ic.getC_Invoice_Candidate_ID() - 1); // For manual candidates, fetch only those which were created before this one
		query.setProcessed(false); // only those which are not processed
		final int adClientId = ic.getAD_Client_ID();
		final int adOrgId = ic.getAD_Org_ID();
		final int targetCurrencyId = ic.getC_Currency_ID();

		// TODO: handle the case when everything is negative

		final BigDecimal amtOthers = invoiceCandDAO.retrieveInvoicableAmount(ctx, query, targetCurrencyId, adClientId, adOrgId, I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice, trxName);
		ManualCandidateHandler.logger.debug("Amt on other lines: {}", amtOthers);

		final BigDecimal amtTotal = netAmtToInvoice.add(amtOthers);
		ManualCandidateHandler.logger.debug("Amt on all lines: {}", amtTotal);

		final BigDecimal netAmtToInvoiceNew;
		final BigDecimal splitAmt;
		if (amtTotal.signum() >= 0)
		{
			netAmtToInvoiceNew = netAmtToInvoice;
			splitAmt = BigDecimal.ZERO;
		}
		else
		{
			netAmtToInvoiceNew = amtOthers.negate();
			splitAmt = netAmtToInvoice.subtract(netAmtToInvoiceNew);
		}
		ManualCandidateHandler.logger.debug("NetAmtToInvoiceNew: {}", netAmtToInvoiceNew);
		ManualCandidateHandler.logger.debug("SplitAmt: {}", splitAmt);

		//
		// Validation: In case we need to change the NetAmtToInvoice then we need to enforce that QtyToInvoice=1
		// else we could not adjust the NetAmtToInvoice because invoice is generated using Price/Qty and NetAmt is calculated
		if (netAmtToInvoice.compareTo(netAmtToInvoiceNew) != 0)
		{
			// task 08507: ic.getQtyToInvoice() is already the "effective". Qty even if QtyToInvoice_Override is set, the system will decide what to invoice (e.g. based on RnvoiceRule and QtDdelivered)
			// and update QtyToInvoice accordingly, possibly to a value that is different from QtyToInvoice_Override.
			final BigDecimal qtyToInvoice = ic.getQtyToInvoice(); // Services.get(IInvoiceCandBL.class).getQtyToInvoice(ic);
			if (BigDecimal.ONE.compareTo(qtyToInvoice) != 0)
			{
				throw new InvalidQtyForPartialAmtToInvoiceException(qtyToInvoice, ic, netAmtToInvoice, netAmtToInvoiceNew);
			}
		}

		ic.setNetAmtToInvoice(netAmtToInvoiceNew);
		ic.setSplitAmt(splitAmt);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}
}
