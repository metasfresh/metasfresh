package de.metas.contracts;

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

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.order.OrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;

import java.math.BigDecimal;
import java.util.List;

public interface IContractsDAO extends ISingletonService
{

	/**
	 * @return the flatrate terms with missing candidates, gathered in a list.
	 */
	List<I_C_Flatrate_Term> retrieveSubscriptionTermsWithMissingCandidates(String typconditions, int limit);


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


	List<I_C_SubscriptionProgress> getSubscriptionProgress(I_C_Flatrate_Term currentTerm);

	List<I_C_Flatrate_Term> retrieveFlatrateTermsForOrderIdLatestFirst(@NonNull final OrderId orderId);

	IQueryBuilder<I_C_Flatrate_Term> createTermWithMissingCandidateQueryBuilder(String typeConditions, boolean ignoreDateFilters);

	I_C_Flatrate_Term retrieveLatestFlatrateTermForBPartnerId(BPartnerId bpartnerId);

	I_C_Flatrate_Term retrieveFirstFlatrateTermForBPartnerId(BPartnerId bpartnerId);

	<T extends I_C_Flatrate_Conditions> T getConditionsById(ConditionsId conditionsId, Class<T> modelClass);
}
