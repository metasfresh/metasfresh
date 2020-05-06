package de.metas.invoicecandidate.api;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation;
import de.metas.util.ISingletonService;

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
