package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_DocType;
import de.metas.document.DocTypeId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

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

@Repository
public class ContractCompensationGroupSettingsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ContractCompensationGroupSettings getBySettingsId(final int settingsId)
	{
		final I_C_CompensationGroup_ContractSettings settingsRecord = load(settingsId, I_C_CompensationGroup_ContractSettings.class);

		final GroupTemplateId schemaId = GroupTemplateId.ofRepoId(settingsRecord.getC_CompensationGroup_Schema_ID());

		final ImmutableSet<DocTypeId> docTypeIds = retrieveDocTypeIds(settingsId);

		return ContractCompensationGroupSettings.builder()
				.schemaId(schemaId)
				.docTypeIds(docTypeIds)
				.build();
	}

	private ImmutableSet<DocTypeId> retrieveDocTypeIds(final int settingsId)
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
