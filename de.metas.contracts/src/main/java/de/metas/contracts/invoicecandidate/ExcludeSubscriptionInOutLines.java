package de.metas.contracts.invoicecandidate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOutLine;

import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
public class ExcludeSubscriptionInOutLines
{

	/**
	 * Make sure that no {@link I_C_Invoice_Candidate}s are created for inout lines that belong to a subscription contract.
	 */
	public void registerFilterForInvoiceCandidateCreation()
	{
		final IQueryFilter<I_M_InOutLine> filter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.contracts.model.I_M_InOutLine.COLUMNNAME_C_SubscriptionProgress_ID, null);

		final InOutLinesWithMissingInvoiceCandidate dao = Adempiere.getBean(InOutLinesWithMissingInvoiceCandidate.class);
		dao.addAdditionalFilter(filter);
	}
}
