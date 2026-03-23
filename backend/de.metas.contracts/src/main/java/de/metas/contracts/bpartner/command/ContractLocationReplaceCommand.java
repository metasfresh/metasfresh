/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.bpartner.command;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.util.Env;

@Builder
public class ContractLocationReplaceCommand
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private final BPartnerLocationId oldBPLocationId;

	@NonNull
	private final BPartnerLocationId newBPLocationId;

	@Builder.Default
	private boolean updateBillLocation = false;

	@Builder.Default
	private boolean updateDropShipLocation = false;

	public void execute()
	{
		updateFlatrateTerms();
	}

	private void updateFlatrateTerms()
	{
		if (updateBillLocation)
		{
			updateFlatrateTermColumn(I_C_Flatrate_Term.COLUMNNAME_Bill_Location_ID, oldBPLocationId, newBPLocationId);
		}

		if (updateDropShipLocation)
		{
			updateFlatrateTermColumn(I_C_Flatrate_Term.COLUMNNAME_DropShip_Location_ID, oldBPLocationId, newBPLocationId);
		}
	}

	private void updateFlatrateTermColumn(final String columnName, final RepoIdAware oldLocationId, final RepoIdAware newLocationId)
	{
		final ICompositeQueryUpdater<I_C_Flatrate_Term> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Flatrate_Term.class)
				.addSetColumnValue(columnName, newLocationId);

		final IQueryFilter<I_C_Flatrate_Term> endDateFilters = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, Env.getDate())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, null);

		queryBL
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(columnName, oldLocationId)
				.filter(endDateFilters)
				.create()
				.update(queryUpdater);
	}
}
