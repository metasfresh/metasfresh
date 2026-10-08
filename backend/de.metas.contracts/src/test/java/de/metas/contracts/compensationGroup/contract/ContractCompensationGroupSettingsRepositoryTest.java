package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_DocType;
import de.metas.document.DocTypeId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.cache.CCache;
import de.metas.cache.CCacheConfig;
import de.metas.cache.CCacheStatsPredicate;
import de.metas.cache.CacheMgt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

/** Reads of the compensation-group contract settings with their document types. */
class ContractCompensationGroupSettingsRepositoryTest
{
	private static final DocTypeId DOC_TYPE_1_ID = DocTypeId.ofRepoId(301);
	private static final DocTypeId DOC_TYPE_2_ID = DocTypeId.ofRepoId(302);
	private static final DocTypeId DOC_TYPE_3_ID = DocTypeId.ofRepoId(303);

	private IQueryBL queryBLSpy;
	private ContractCompensationGroupSettingsRepository settingsRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy); // before the repository picks up IQueryBL
		settingsRepository = ContractCompensationGroupSettingsRepository.newInstanceForUnitTesting();
	}

	@Test
	void getBySettingsId_returnsSchemaAndActiveDocTypes()
	{
		final GroupTemplateId schemaId = createSchema();
		final ContractCompensationGroupSettingsId settingsId = createSettings(schemaId);
		createDocType(settingsId, DOC_TYPE_1_ID, true);
		createDocType(settingsId, DOC_TYPE_2_ID, false); // inactive -> not returned

		assertThat(settingsRepository.getBySettingsId(settingsId)).isEqualTo(ContractCompensationGroupSettings.builder()
				.settingsId(settingsId)
				.schemaId(schemaId)
				.docTypeIds(ImmutableSet.of(DOC_TYPE_1_ID))
				.build());
	}

	@Test
	void getBySettingsId_secondReadOfTheSameSettings_runsNoQuery()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings(createSchema());
		createDocType(settingsId, DOC_TYPE_1_ID, true);
		final ContractCompensationGroupSettings firstRead = settingsRepository.getBySettingsId(settingsId);
		Mockito.clearInvocations(queryBLSpy);

		final ContractCompensationGroupSettings secondRead = settingsRepository.getBySettingsId(settingsId);

		assertThat(secondRead).isSameAs(firstRead); // the settings record is not loaded again
		Mockito.verify(queryBLSpy, Mockito.never()).createQueryBuilder(I_C_CompensationGroup_ContractSettings_DocType.class);
	}

	@Test
	void getBySettingsId_savingTheSettingsOrADocType_isSeenByTheNextRead()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings(createSchema());
		final I_C_CompensationGroup_ContractSettings_DocType docType1 = createDocType(settingsId, DOC_TYPE_1_ID, true);
		assertThat(settingsRepository.getBySettingsId(settingsId).getDocTypeIds()).containsExactly(DOC_TYPE_1_ID);

		final GroupTemplateId otherSchemaId = createSchema();
		final I_C_CompensationGroup_ContractSettings settingsRecord = load(settingsId, I_C_CompensationGroup_ContractSettings.class);
		settingsRecord.setC_CompensationGroup_Schema_ID(otherSchemaId.getRepoId());
		saveRecord(settingsRecord);
		assertThat(settingsRepository.getBySettingsId(settingsId).getSchemaId()).isEqualTo(otherSchemaId);

		createDocType(settingsId, DOC_TYPE_3_ID, true);
		assertThat(settingsRepository.getBySettingsId(settingsId).getDocTypeIds()).containsExactlyInAnyOrder(DOC_TYPE_1_ID, DOC_TYPE_3_ID);

		docType1.setC_DocType_ID(DOC_TYPE_2_ID.getRepoId());
		saveRecord(docType1);
		assertThat(settingsRepository.getBySettingsId(settingsId).getDocTypeIds()).containsExactlyInAnyOrder(DOC_TYPE_2_ID, DOC_TYPE_3_ID);
	}

	private static GroupTemplateId createSchema()
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		return GroupTemplateId.ofRepoId(schema.getC_CompensationGroup_Schema_ID());
	}

	private static ContractCompensationGroupSettingsId createSettings(final GroupTemplateId schemaId)
	{
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schemaId.getRepoId());
		saveRecord(settings);
		return ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID());
	}

	private static I_C_CompensationGroup_ContractSettings_DocType createDocType(final ContractCompensationGroupSettingsId settingsId, final DocTypeId docTypeId, final boolean isActive)
	{
		final I_C_CompensationGroup_ContractSettings_DocType record = newInstance(I_C_CompensationGroup_ContractSettings_DocType.class);
		record.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		record.setC_DocType_ID(docTypeId.getRepoId());
		record.setIsActive(isActive);
		saveRecord(record);
		return record;
	}

	@Test
	void cache_isBoundedLRU()
	{
		// repository created in beforeEach

		assertThat(CacheMgt.get().streamStats(CCacheStatsPredicate.builder().cacheNameContains(I_C_CompensationGroup_ContractSettings.Table_Name).build()).filter(stats -> stats.getName().equals(I_C_CompensationGroup_ContractSettings.Table_Name)))
				.isNotEmpty()
				.allSatisfy(stats -> assertThat(stats.getConfig())
						.returns(CCache.CacheMapType.LRU, CCacheConfig::getCacheMapType)
						.returns(100, CCacheConfig::getMaximumSize));
	}
}
