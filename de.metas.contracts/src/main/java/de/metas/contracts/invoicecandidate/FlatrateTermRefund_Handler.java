package de.metas.contracts.invoicecandidate;

import java.util.Iterator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class FlatrateTermRefund_Handler extends AbstractInvoiceCandidateHandler
{

	/**
	 * One invocation returns a maximum of <code>limit</code> {@link I_C_Flatrate_Term}s that are completed subscriptions and don't have an invoice candidate referencing them.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return Services.get(IContractsDAO.class)
				.retrieveSubscriptionTermsWithMissingCandidates(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund, limit)
				.iterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(InvoiceCandidateGenerateRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_Flatrate_Term term = InterfaceWrapperHelper.create(model, I_C_Flatrate_Term.class);
		HandlerTools.invalidateCandidatesFor(term);
	}


	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_Term.Table_Name;
	}

	/**
	 * Returns false, because the user in charge is taken from the C_Flatrate_Term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(I_C_Invoice_Candidate ic)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setDeliveredData(I_C_Invoice_Candidate ic)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBPartnerData(I_C_Invoice_Candidate ic)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setC_UOM_ID(I_C_Invoice_Candidate ic)
	{
		// TODO Auto-generated method stub

	}

}
