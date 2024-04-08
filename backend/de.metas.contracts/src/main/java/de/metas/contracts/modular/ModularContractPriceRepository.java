/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class ModularContractPriceRepository
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public I_ModCntr_Specific_Price retrievePriceForProductAndContract(@NonNull final ModularContractModuleId modularContractModuleId, @NonNull final FlatrateTermId flatrateTermId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, modularContractModuleId)
				.create()

				.firstOnly(I_ModCntr_Specific_Price.class);
	}

}
