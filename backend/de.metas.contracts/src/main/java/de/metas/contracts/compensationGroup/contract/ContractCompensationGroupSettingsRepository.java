package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_DocType;
import de.metas.document.DocTypeId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Repository Tables: C_CompensationGroup_ContractSettings, C_CompensationGroup_ContractSettings_DocType, C_Flatrate_Conditions
 * <p>
 * Loads a {@code C_CompensationGroup_ContractSettings} record and resolves the settings id a
 * {@code C_Flatrate_Conditions} record points to.
 */
@Repository
public class ContractCompensationGroupSettingsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ContractCompensationGroupSettings getBySettingsId(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		final I_C_CompensationGroup_ContractSettings settingsRecord = load(settingsId, I_C_CompensationGroup_ContractSettings.class);

		final GroupTemplateId schemaId = GroupTemplateId.ofRepoId(settingsRecord.getC_CompensationGroup_Schema_ID());

		final ImmutableSet<DocTypeId> docTypeIds = retrieveDocTypeIds(settingsId);

		return ContractCompensationGroupSettings.builder()
				.schemaId(schemaId)
				.docTypeIds(docTypeIds)
				.build();
	}

	/** @return the settings id that {@code conditionsId}'s {@code C_CompensationGroup_ContractSettings_ID} points to, or {@code null} when unset */
	@Nullable
	public ContractCompensationGroupSettingsId getSettingsIdByConditionsId(@NonNull final ConditionsId conditionsId)
	{
		final I_C_Flatrate_Conditions conditions = load(conditionsId, I_C_Flatrate_Conditions.class);
		return ContractCompensationGroupSettingsId.ofRepoIdOrNull(conditions.getC_CompensationGroup_ContractSettings_ID());
	}

	private ImmutableSet<DocTypeId> retrieveDocTypeIds(@NonNull final ContractCompensationGroupSettingsId settingsId)
	{
		return queryBL.createQueryBuilder(I_C_CompensationGroup_ContractSettings_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_CompensationGroup_ContractSettings_ID, settingsId)
				.create()
				.listDistinct(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_DocType_ID, Integer.class)
				.stream()
				.map(DocTypeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
