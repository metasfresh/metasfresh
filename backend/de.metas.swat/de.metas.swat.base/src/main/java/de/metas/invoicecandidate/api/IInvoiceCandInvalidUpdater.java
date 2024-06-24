package de.metas.invoicecandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lock.api.ILock;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import java.util.Properties;

/**
 * Updates {@link I_C_Invoice_Candidate}s which are scheduled to be recomputed.
 *
 * @author tsa
 *
 */
public interface IInvoiceCandInvalidUpdater
{

	/**
	 * Updates invoice candidates (which were scheduled to be recomputed)
	 * <p>
	 * NOTEs:
	 * <ul>
	 * <li>only those candidates will be updated that were previously invalidated
	 * <li>If some candidates will be updated in database, they need to be refreshed by caller method if caller method wants to use them subsequently (i.e. use
	 * {@link InterfaceWrapperHelper#refresh(Object)}). That's because the implementation won't actually work with the instances from the provided iterator
	 * </ul>
	 */
	void update();

	IInvoiceCandInvalidUpdater setContext(final Properties ctx, final String trxName);

	IInvoiceCandInvalidUpdater setContext(IContextAware context);

	/**
	 * @param lockedBy
	 *            <ul>
	 *            <li>if not null it will consider only those records which are locked by given {@link ILock}
	 *            <li>if null, it will consider only NOT locked records
	 *            </ul>
	 */
	IInvoiceCandInvalidUpdater setLockedBy(ILock lockedBy);

	/**
	 * Consider only those invoice candidates which were not tagged.
	 */
	IInvoiceCandInvalidUpdater setTaggedWithNoTag();

	/**
	 * Consider any invalid invoice candidate, no matter if they are tagged or not.
	 * <p>
	 * NOTE:
	 * <ul>
	 * <li>this is the default behavior if no setTaggedWith methods are called.
	 * </ul>
	 */
	IInvoiceCandInvalidUpdater setTaggedWithAnyTag();

	/**
	 * Sets maximum number of invoice candidates to update.
	 */
	IInvoiceCandInvalidUpdater setLimit(int limit);

	/**
	 * Sets the tag to be used when tagging the invoice candidates.
	 */
	IInvoiceCandInvalidUpdater setRecomputeTagToUse(InvoiceCandRecomputeTag tag);

	IInvoiceCandInvalidUpdater setOnlyInvoiceCandidateIds(@NonNull InvoiceCandidateIdsSelection onlyInvoiceCandidateIds);

	// TODO: find a better place for this method
	static void updatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic, @NonNull final PriceAndTax priceAndTax)
	{
		//
		// Pricing System & Currency
		if (priceAndTax.getPricingSystemId() != null)
		{
			ic.setM_PricingSystem_ID(priceAndTax.getPricingSystemId().getRepoId());
		}
		if (priceAndTax.getPriceListVersionId() != null)
		{
			ic.setM_PriceList_Version_ID(priceAndTax.getPriceListVersionId().getRepoId());
		}
		if (priceAndTax.getCurrencyId() != null)
		{
			ic.setC_Currency_ID(priceAndTax.getCurrencyId().getRepoId());
		}

		//
		// Price & Discount
		if (priceAndTax.getPriceEntered() != null)
		{
			ic.setPriceEntered(priceAndTax.getPriceEntered()); // task 06727
		}
		if (priceAndTax.getPriceActual() != null)
		{
			ic.setPriceActual(priceAndTax.getPriceActual());
		}
		if (priceAndTax.getPriceUOMId() != null)
		{
			ic.setPrice_UOM_ID(priceAndTax.getPriceUOMId().getRepoId());
		}
		if (priceAndTax.getDiscount() != null)
		{
			ic.setDiscount(Percent.toBigDecimalOrZero(priceAndTax.getDiscount()));
		}
		if (priceAndTax.getInvoicableQtyBasedOn() != null)
		{
			ic.setInvoicableQtyBasedOn(priceAndTax.getInvoicableQtyBasedOn().getCode());
		}
		//
		// Tax
		if (priceAndTax.getTaxIncluded() != null)
		{
			ic.setIsTaxIncluded(priceAndTax.getTaxIncluded());
		}
		if (priceAndTax.getTaxId() != null)
		{
			ic.setC_Tax_ID(priceAndTax.getTaxId().getRepoId());
		}
		
		//
		// Compensation group
		if (priceAndTax.getCompensationGroupBaseAmt() != null)
		{
			ic.setGroupCompensationBaseAmt(priceAndTax.getCompensationGroupBaseAmt());
		}
	}
}
