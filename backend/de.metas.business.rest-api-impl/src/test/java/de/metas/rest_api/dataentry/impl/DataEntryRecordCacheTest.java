package de.metas.rest_api.dataentry.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import ch.qos.logback.classic.Level;
import de.metas.cache.CacheMgt;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordField;
import de.metas.dataentry.data.DataEntryRecordFieldString;
import de.metas.dataentry.data.DataEntryRecordId;
import de.metas.dataentry.data.DataEntryRecordQuery;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.logging.LogManager;
import de.metas.rest_api.dataentry.impl.DataEntryRecordCache.DataEntryRecordIdIndex;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DataEntryRecordCacheTest
{
	private DataEntryRecordRepository entryRecordsRepo;
	private DataEntryRecordCache entryRecordsCache;

	private static final UserId USER_ID = UserId.ofRepoId(100);
	private static final DataEntryFieldId FIELD_ID1 = DataEntryFieldId.ofRepoId(111);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		LogManager.setLoggerLevel(DataEntryRecordIdIndex.class, Level.TRACE);

		entryRecordsRepo = new DataEntryRecordRepository(new JSONDataEntryRecordMapper());
		entryRecordsCache = new DataEntryRecordCache(entryRecordsRepo, 1000);
	}

	private static Set<DataEntrySubTabId> toDataEntrySubTabIds(final int... repoIds)
	{
		return CollectionUtils.asIntSet(repoIds)
				.stream()
				.map(DataEntrySubTabId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private DataEntryRecordId createEntryRecord(
			final TableRecordReference mainRecord,
			final DataEntrySubTabId subTabId,
			final String fieldValue)
	{
		final CreatedUpdatedInfo createdUpdatedInfo = CreatedUpdatedInfo.createNew(USER_ID, ZonedDateTime.now());
		final DataEntryRecordField<?> field = DataEntryRecordFieldString.of(FIELD_ID1, createdUpdatedInfo, fieldValue);

		return entryRecordsRepo.save(DataEntryRecord.builder()
				.id(null)
				.mainRecord(mainRecord)
				.dataEntrySubTabId(subTabId)
				.fields(ImmutableList.of(field))
				.build());
	}

	private void updateEntityRecord(
			final DataEntryRecordId id,
			final String fieldValue)
	{
		final DataEntryRecord record = entryRecordsRepo.getById(id);
		record.setRecordField(FIELD_ID1, USER_ID, fieldValue);
		entryRecordsRepo.save(record);
	}

	private void deleteEntityRecord(
			final ITableRecordReference mainRecord,
			final DataEntrySubTabId subTabId)
	{
		entryRecordsRepo.deleteBy(DataEntryRecordQuery.builder()
				.recordId(mainRecord.getRecord_ID())
				.dataEntrySubTabId(subTabId)
				.build());
	}

	@Test
	@SuppressWarnings("unused")
	public void testStandardScenario()
	{
		final TableRecordReference mainRecordRef = TableRecordReference.of("MyTable", 1);

		final DataEntryRecordId entryRecordId1 = createEntryRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(1), "initial value");
		final DataEntryRecordId entryRecordId2 = createEntryRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(2), "initial value");
		final DataEntryRecordId entryRecordId3 = createEntryRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(3), "initial value");

		{
			final DataEntryRecordsMap r = entryRecordsCache.get(mainRecordRef.getRecord_ID(), toDataEntrySubTabIds(1, 2, 3));
			assertThat(r.getSubTabIds()).isEqualTo(toDataEntrySubTabIds(1, 2, 3));
		}

		final DataEntryRecordId entryRecordId4 = createEntryRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(4), "initial value");

		{
			final DataEntryRecordsMap r = entryRecordsCache.get(mainRecordRef.getRecord_ID(), toDataEntrySubTabIds(1, 2, 3, 4));
			assertThat(r.getSubTabIds()).isEqualTo(toDataEntrySubTabIds(1, 2, 3, 4));
		}

		deleteEntityRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(4));

		{
			final DataEntryRecordsMap r = entryRecordsCache.get(mainRecordRef.getRecord_ID(), toDataEntrySubTabIds(1, 2, 3, 4));
			assertThat(r.getSubTabIds()).isEqualTo(toDataEntrySubTabIds(1, 2, 3));
		}

		updateEntityRecord(entryRecordId2, "update1");

		{
			final DataEntryRecordsMap r = entryRecordsCache.get(mainRecordRef.getRecord_ID(), toDataEntrySubTabIds(1, 2, 3, 4));
			assertThat(r.getSubTabIds()).isEqualTo(toDataEntrySubTabIds(1, 2, 3));

			final DataEntryRecord record = r.getBySubTabId(DataEntrySubTabId.ofRepoId(2)).get();
			assertThat(record.getFieldValue(FIELD_ID1).get()).isEqualTo("update1");
		}
	}

	@Test
	public void testFullCacheReset()
	{
		final TableRecordReference mainRecordRef = TableRecordReference.of("MyTable", 1);

		final Set<DataEntrySubTabId> subTabIds = new HashSet<>();
		for (int subTabRepoId = 1; subTabRepoId <= 100; subTabRepoId++)
		{
			createEntryRecord(mainRecordRef, DataEntrySubTabId.ofRepoId(subTabRepoId), "initial value");
			subTabIds.add(DataEntrySubTabId.ofRepoId(subTabRepoId));
		}

		{
			final DataEntryRecordsMap r = entryRecordsCache.get(mainRecordRef.getRecord_ID(), subTabIds);
			assertThat(r.getSubTabIds()).isEqualTo(subTabIds);
			assertThat(entryRecordsCache.getDataEntryRecordIdIndexSize()).isEqualTo(100);
		}

		{
			CacheMgt.get().reset(I_DataEntry_Record.Table_Name);
			assertThat(entryRecordsCache.getDataEntryRecordIdIndexSize()).isZero();
		}
	}
}
