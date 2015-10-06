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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation;

public interface IAggregationDAO extends ISingletonService
{
	/**
	 * Returns the invoice candidate aggregator to be used for the given ic.
	 * 
	 * Loads the first invoice candidate aggregator with
	 * <ul>
	 * <li>AD_Org_ID=0 or equal to the given ic's AD_Org_ID</li>
	 * <li>C_BPartner_ID= or equal to the given ic's Bill_BPartner_ID</li>
	 * <li>M_ProductGroup_ID=0 or equal to a M_ProductGroup matching the given ic's M_Product_ID</li>
	 * </ul>
	 * 
	 * If there is more than one matching aggregator, then records with lower SeqNo values take precedence over higher ones. If two records have the same seqNo, then then AD_Org_ID>0 takes precedence
	 * over AD_Org_ID=0. If they also have the same AD_Org_ID, then the one with the lower C_Invoice_Candidate_Agg_ID takes precedence (i.e. the one that was created first).
	 * 
	 * @param ic
	 * @return
	 */
	I_C_Invoice_Candidate_Agg retrieveAggregate(I_C_Invoice_Candidate ic);

	/**
	 * Finds/creates the {@link I_C_Invoice_Candidate_HeaderAggregation} for given {@link I_C_Invoice_Candidate#getHeaderAggregationKey_Calc()}.
	 * 
	 * @param ic
	 * @return C_Invoice_Candidate_HeaderAggregation_ID
	 */
	int findC_Invoice_Candidate_HeaderAggregationKey_ID(final I_C_Invoice_Candidate ic);
}
