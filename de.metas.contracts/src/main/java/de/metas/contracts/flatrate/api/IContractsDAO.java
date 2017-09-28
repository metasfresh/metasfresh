package de.metas.contracts.flatrate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;

public interface IContractsDAO extends ISingletonService
{

	/**
	 * @return the flatrate terms with missing candidates, gathered in a list.
	 */
	List<I_C_Flatrate_Term> retrieveSubscriptionTermsWithMissingCandidates(int limit);

	
	/**
	 * 
	 * Check if the term given as parameter was extended (has a predecessor).
	 * @param term
	 * @return
	 */
	boolean termHasAPredecessor (I_C_Flatrate_Term term);

	/**
	 * Sums up the <code>Qty</code> values of all {@link I_C_SubscriptionProgress} records that reference the given
	 * term.
	 * 
	 * @param term
	 * @return
	 */
	BigDecimal retrieveSubscriptionProgressQtyForTerm(I_C_Flatrate_Term term);
}
