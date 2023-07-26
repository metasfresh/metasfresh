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

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public class ModularContractSettingsDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public ModularContractSettings getByFlatrateTermIdOrNull(@NonNull final FlatrateTermId contractId)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, contractId)
				.andCollect(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID)
				.andCollect(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.orderByDescending(I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID)
				.create()
				.firstOptional()
				.map(this::getBySettings)
				.orElse(null);
	}

	@Nullable
	public ModularContractSettings getByFlatrateConditonsIdOrNull(@NonNull final ConditionsId conditionsId)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsId)
				.andCollect(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.create()
				.firstOptional()
				.map(this::getBySettings)
				.orElse(null);
	}

	@NonNull
	private ModularContractSettings getBySettings(@NonNull final I_ModCntr_Settings settings)
	{
		final List<I_ModCntr_Module> moduleRecords = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Module.COLUMN_ModCntr_Settings_ID, settings.getModCntr_Settings_ID())
				.create()
				.list();

		return fromPOs(settings, moduleRecords);
	}

	@NonNull
	private static ModularContractSettings fromPOs(
			@NonNull final I_ModCntr_Settings settingsRecord,
			@NonNull final List<I_ModCntr_Module> moduleRecords)
	{

		final ModularContractSettings.ModularContractSettingsBuilder result = ModularContractSettings.builder()
				.id(ModularContractSettingsId.ofRepoId(settingsRecord.getModCntr_Settings_ID()))
				.orgId(OrgId.ofRepoId(settingsRecord.getAD_Org_ID()))
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(settingsRecord.getC_Year_ID(), settingsRecord.getC_Calendar_ID()))
				.pricingSystemId(PricingSystemId.ofRepoIdOrNull(settingsRecord.getM_PricingSystem_ID()))
				.productId(ProductId.ofRepoId(settingsRecord.getM_Product_ID()))
				.name(settingsRecord.getName());

		for (final I_ModCntr_Module moduleRecord : moduleRecords)
		{
			final I_ModCntr_Type modCntrType = moduleRecord.getModCntr_Type();

			final ModuleConfig moduleConfig = ModuleConfig.builder()
					.name(moduleRecord.getName())
					.productId(ProductId.ofRepoId(moduleRecord.getM_Product_ID()))
					.seqNo(SeqNo.ofInt(moduleRecord.getSeqNo()))
					.invoicingGroup(moduleRecord.getInvoicingGroup())
					.modularContractType(ModularContractType.builder()
							.id(ModularContractTypeId.ofRepoId(modCntrType.getModCntr_Type_ID()))
							.value(modCntrType.getValue())
							.name(modCntrType.getName())
							.className(modCntrType.getClassname())
							.build())
					.build();

			result.moduleConfig(moduleConfig);
		}

		return result.build();
	}

	public boolean isSettingsUsedInCompletedFlatrateConditions(final ModularContractSettingsId modCntrSettingsId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addEqualsFilter(I_ModCntr_Settings.COLUMN_ModCntr_Settings_ID, modCntrSettingsId)
				.andCollectChildren(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.anyMatch();
	}

	public boolean isSettingsExists(final @NonNull ModularContractSettingsQuery query)
	{
		final YearAndCalendarId yearAndCalendarId = query.yearAndCalendarId();
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, yearAndCalendarId.yearId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Product_ID, query.productId())
				.anyMatch();
	}
}
