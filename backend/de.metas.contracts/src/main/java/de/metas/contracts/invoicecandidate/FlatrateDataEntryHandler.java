package de.metas.contracts.invoicecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.create;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.Iterator;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.util.Check;
import de.metas.util.Services;

public class FlatrateDataEntryHandler extends AbstractInvoiceCandidateHandler
{
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
	public Iterator<I_C_Flatrate_DataEntry> retrieveAllModelsWithMissingCandidates(final int limit)
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
	 * Implementation invalidates the C_Flatrate_DataEntry's C_Invoice_Candidate and C_Invoice_Candidate_Corr (if set).
	 */
	@Override
	public void invalidateCandidatesFor(final Object dataEntryObj)
	{
		final I_C_Flatrate_DataEntry dataEntry = create(dataEntryObj, I_C_Flatrate_DataEntry.class);
		if (dataEntry.getC_Invoice_Candidate_ID() > 0)
		{
			Services.get(IInvoiceCandDAO.class).invalidateCand(dataEntry.getC_Invoice_Candidate());
		}
		if (dataEntry.getC_Invoice_Candidate_Corr_ID() > 0)
		{
			Services.get(IInvoiceCandDAO.class).invalidateCand(dataEntry.getC_Invoice_Candidate_Corr());
		}
	}

	/**
	 * Returns "C_Flatrate_DataEntry".
	 */
	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_DataEntry.Table_Name;
	}

	/**
	 * Implementation returns the user that is set as "user in charge" in the flatrate term of the {@link I_C_Flatrate_DataEntry} that references the given invoice candidate.
	 * <p>
	 * Note: It is assumed that there is a flatrate data entry referencing the given ic
	 */
	@Override
	public int getAD_User_InCharge_ID(final I_C_Invoice_Candidate ic)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_DataEntry dataEntry = flatrateDB.retrieveDataEntryOrNull(ic);
		Check.assumeNotNull(dataEntry, "Param 'ic' needs to be referenced by a C_Flatrate_DataEntry record; ic={}", ic);

		return dataEntry.getC_Flatrate_Term().getAD_User_InCharge_ID();
	}

	/**
	 * Returns false, because the user in charge is taken from the flatrate term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
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
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		// note: we can assume that #setQtyOrdered() was already called
		ic.setQtyDelivered(ic.getQtyOrdered()); // when changing this, make sure to threat ProductType.Service specially
		ic.setQtyDeliveredInUOM(ic.getQtyEntered());

		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}
}
