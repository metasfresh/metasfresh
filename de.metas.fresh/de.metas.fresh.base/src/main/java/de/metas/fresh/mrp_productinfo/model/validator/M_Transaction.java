package de.metas.fresh.mrp_productinfo.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
@Interceptor(I_M_Transaction.class)
public class M_Transaction
{
	public static final M_Transaction INSTANCE = new M_Transaction();

	private M_Transaction()
	{
	}

	/**
	 * Note: it's important to enqueue the purchaseCandidate after it was saved, because we need its <code>PMM_PurchaseCandidate_ID</code>.
	 *
	 * @param purchaseCandidate
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE })
	public void enqueuePurchaseCandidates(final I_M_Transaction transaction)
	{
		UpdateMRPProductInfoTableWorkPackageProcessor.schedule(transaction);
	}
}
