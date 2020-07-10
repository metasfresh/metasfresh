package de.metas.fresh.api.invoicecandidate;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.ISingletonService;

public interface IFreshInvoiceCandBL extends ISingletonService
{

	/**
	 * For the time being, the only invoice doc type we set for IC is Produzentenabrechnung.
	 *
	 * In case there will be more in the future, please complete this logic
	 *
	 * In case the new invoice is made for a partner that has the flag freshProduzentenabrechnung set, or the invoice candidate was changed so now it has such a partner, set it's C_DocTypeInvoice on
	 * freshProduzentenabrechnung ( this doc type is the only one that has baseType API and subType VI
	 *
	 * In case the candidate used to have this invoice doc type set and now it was changed and has a partner that doesn't have the flag, also change the invoice doctype (set it on null)
	 * <p>
	 * As "Produzentenabrechnung" is only for purchase transactions, this method will do nothing if the given <code>candidate</code> has <code>SOTrx=Y</code>.
	 * <p>
	 * <b>IMPORTANT:</b> please keep in sync with the view <code>"de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V </code> (issue <a href="https://github.com/metasfresh/metasfresh/issues/353">#353</a>)
	 *
	 * @param candidate
	 */
	void updateC_DocTypeInvoice(I_C_Invoice_Candidate candidate);
}
