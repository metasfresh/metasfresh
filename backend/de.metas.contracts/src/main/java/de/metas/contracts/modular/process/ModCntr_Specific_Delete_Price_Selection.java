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

package de.metas.contracts.modular.process;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.ModCntrSpecificPriceId;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;

import java.util.Set;

public class ModCntr_Specific_Delete_Price_Selection extends JavaProcess
{
	@NonNull private final ModularContractPriceService modularContractPriceService = SpringContextHolder.instance.getBean(ModularContractPriceService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt()
	{
		retrieveContractSpecificPricesFromSelection()
				.forEach(modularContractPriceService::deleteById);

		return MSG_OK;
	}

	private ImmutableSet<ModCntrSpecificPriceId> retrieveContractSpecificPricesFromSelection()
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, getSelectedContracts())
				.create()
				.listIds(ModCntrSpecificPriceId::ofRepoId);
	}

	private Set<FlatrateTermId> getSelectedContracts()
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

}
