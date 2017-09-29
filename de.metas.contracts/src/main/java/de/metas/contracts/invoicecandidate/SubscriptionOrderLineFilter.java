package de.metas.contracts.invoicecandidate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IC_OrderLine_HandlerDAO;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class SubscriptionOrderLineFilter
{

	/**
	 * Make sure that no {@link I_C_Invoice_Candidate}s are created for order lines that belong to a subscription contract.
	 */
	public void registerFilterForInvoiceCandidateCreation()
	{
		final IQueryFilter<I_C_OrderLine> f = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.contracts.subscription.model.I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID, null);

		Services.get(IC_OrderLine_HandlerDAO.class).addAdditionalFilter(f);
	}
}
