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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import org.adempiere.ad.dao.QueryLimit;

import java.util.Collections;
import java.util.Iterator;

public class PlainInvoiceCandidateHandler extends AbstractInvoiceCandidateHandler
{
	public final static String TABLENAME = "PlainInvoiceCandidateHandler";

	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.DONT;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(Object model)
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<Object> retrieveAllModelsWithMissingCandidates(final QueryLimit limit_IGNORED)
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

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
	}

	@Override
	public String getSourceTable()
	{
		return PlainInvoiceCandidateHandler.TABLENAME;
	}

	@Override
	public int getAD_User_InCharge_ID(final I_C_Invoice_Candidate ic)
	{
		return -1;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}
}
