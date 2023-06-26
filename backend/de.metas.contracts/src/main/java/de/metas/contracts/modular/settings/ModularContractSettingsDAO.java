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

package de.metas.contracts.modular.settings;

import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.ClassLoaderUtil;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModularContractSettingsDAO
{

	public ModularContractSettings getFor(@NonNull final FlatrateTermId contractId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_ModCntr_Settings settingsRecord = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, contractId)
				.andCollect(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID)
				.andCollect(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.create()
				.firstOnly();

		final List<I_ModCntr_Module> moduleRecords = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Module.COLUMN_ModCntr_Settings_ID, settingsRecord.getModCntr_Settings_ID())
				.create()
				.list();

		return fromPOs(settingsRecord, moduleRecords);
	}

	private static ModularContractSettings fromPOs(
			@NonNull final I_ModCntr_Settings settingsRecord,
			@NonNull final List<I_ModCntr_Module> moduleRecords)
	{

		final ModularContractSettings.ModularContractSettingsBuilder result = ModularContractSettings.builder()
				.id(ModularContractSettingsId.ofRepoId(settingsRecord.getModCntr_Settings_ID()))
				.orgId(OrgId.ofRepoId(settingsRecord.getAD_Org_ID()))
				.yearId(YearId.ofRepoId(settingsRecord.getC_Calendar_ID(), settingsRecord.getC_Year_ID()))
				.pricingSystemId(PricingSystemId.ofRepoId(settingsRecord.getM_PricingSystem_ID()))
				.productId(ProductId.ofRepoId(settingsRecord.getM_Product_ID()))
				.name(settingsRecord.getName());

		for (final I_ModCntr_Module moduleRecord : moduleRecords)
		{
			final I_ModCntr_Type modCntrType = moduleRecord.getModCntr_Type();
			final Class<?> handlerImplClass = ClassLoaderUtil.validateJavaClassname(modCntrType.getClassname(), IModularContractTypeHandler.class);
			final IModularContractTypeHandler handlerImpl = (IModularContractTypeHandler)ClassLoaderUtil.newInstanceFromNoArgConstructor(handlerImplClass);

			final ModuleConfig moduleConfig = ModuleConfig.builder()
					.name(moduleRecord.getName())
					.productId(ProductId.ofRepoId(moduleRecord.getM_Product_ID()))
					.seqNo(moduleRecord.getSeqNo())
					.invoicingGroup(moduleRecord.getInvoicingGroup())
					.handlerImpl(handlerImpl)
					.build();

			result.moduleConfig(moduleConfig);
		}

		return result.build();
	}

}
