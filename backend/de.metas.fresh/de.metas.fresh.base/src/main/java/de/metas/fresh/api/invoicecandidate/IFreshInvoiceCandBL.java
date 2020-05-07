package de.metas.fresh.api.invoicecandidate;

/*
 * #%L
 * de.metas.fresh.base
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

import org.adempiere.util.ISingletonService;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

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
