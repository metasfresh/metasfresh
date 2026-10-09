/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.contracts.modular.process;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Set;

@UtilityClass
public class ModCntrSpecificPriceProcessHelper
{
	private static final AdMessageKey DIFFERENT_CONTRACTS_MESSAGE = AdMessageKey.of("Different_Contracts");

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (countNonEligibleContractTypes(context) > 0)
		{
			return ProcessPreconditionsResolution.reject(DIFFERENT_CONTRACTS_MESSAGE);
		}

		return ProcessPreconditionsResolution.accept();
	}

	private int countNonEligibleContractTypes(final @NonNull IProcessPreconditionsContext context)
	{
		final ICompositeQueryFilter<I_C_Flatrate_Term> notEligibleFilter = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.Completed)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.MODULAR_CONTRACT.getCode());

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(context.getQueryFilter(I_C_Flatrate_Term.class))
				.filter(notEligibleFilter)
				.create()
				.count();

	}

	public Set<FlatrateTermId> getSelectedContracts(final IQueryFilter<I_C_Flatrate_Term> processFilter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addFilter(processFilter)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}
}
