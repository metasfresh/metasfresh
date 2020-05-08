package org.adempiere.ad.table;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.RecordRefWithLogEntryProcessor.RecordRefWithLogEntry;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class RecordRefWithLogEntryProcessorTest
{

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/** derives log entries from changed C_BPartnerLocation.C_Locaion_IDs, event with a changelog's valueNew=NULL (shouldn't happen but sometimes did and at this point we can't do anything about it) */
	@Test
	void processRecordRefsWithLogEntries()
	{
		// given
		final I_C_Location location1 = newInstance(I_C_Location.class);
		POJOWrapper.setInstanceName(location1, "location1");
		location1.setAddress1("address1");
		saveRecord(location1);

		final I_C_Location location2 = newInstance(I_C_Location.class);
		POJOWrapper.setInstanceName(location2, "location2");
		location2.setAddress1("address2");
		saveRecord(location2);

		final I_C_Location location3 = newInstance(I_C_Location.class);
		POJOWrapper.setInstanceName(location3, "location3");
		location3.setAddress1("address3");
		saveRecord(location3);

		final I_C_Location location4 = newInstance(I_C_Location.class);
		POJOWrapper.setInstanceName(location4, "location4");
		location4.setAddress1("address4");
		saveRecord(location4);

		final TableRecordReference recordRef = TableRecordReference.of(I_C_BPartner_Location.Table_Name, 20);

		// this is the C_BPartner_Location's changeLog entry, where C_Location_ID was changed from oldLocation to newLocation
		final RecordRefWithLogEntry recordRefWithLogEntry1 = new RecordRefWithLogEntry(recordRef, RecordChangeLogEntry.builder()
				.changedByUserId(UserId.ofRepoId(1))
				.columnName(I_C_Location.COLUMNNAME_C_Location_ID)
				.columnDisplayName(TranslatableStrings.constant("Address"))
				.changedTimestamp(Instant.now())
				.valueOld(KeyNamePair.of(location1.getC_Location_ID()))
				.valueNew(KeyNamePair.of(location2.getC_Location_ID()))
				.build());
		final RecordRefWithLogEntry recordRefWithLogEntry2 = new RecordRefWithLogEntry(recordRef, RecordChangeLogEntry.builder()
				.changedByUserId(UserId.ofRepoId(1))
				.columnName(I_C_Location.COLUMNNAME_C_Location_ID)
				.columnDisplayName(TranslatableStrings.constant("Address"))
				.changedTimestamp(Instant.now())
				.valueOld(KeyNamePair.of(location2.getC_Location_ID()))
				.valueNew(null)
				.build());
		final RecordRefWithLogEntry recordRefWithLogEntry3 = new RecordRefWithLogEntry(recordRef, RecordChangeLogEntry.builder()
				.changedByUserId(UserId.ofRepoId(1))
				.columnName(I_C_Location.COLUMNNAME_C_Location_ID)
				.columnDisplayName(TranslatableStrings.constant("Address"))
				.changedTimestamp(Instant.now())
				.valueOld(KeyNamePair.of(location3.getC_Location_ID()))
				.valueNew(KeyNamePair.of(location4.getC_Location_ID()))
				.build());

		// expected result
		final RecordChangeLogEntry expectedChangeLogEntry1 = RecordChangeLogEntry.builder()
				.changedByUserId(UserId.ofRepoId(1))
				.columnName(I_C_Location.COLUMNNAME_Address1)
				.columnDisplayName(TranslatableStrings.constant("Address1"))
				.changedTimestamp(TimeUtil.asInstant(location2.getUpdated()))
				.valueOld("address1")
				.valueNew("address2")
				.build();
		final RecordChangeLogEntry expectedChangeLogEntry2 = RecordChangeLogEntry.builder()
				.changedByUserId(UserId.ofRepoId(1))
				.columnName(I_C_Location.COLUMNNAME_Address1)
				.columnDisplayName(TranslatableStrings.constant("Address1"))
				.changedTimestamp(TimeUtil.asInstant(location2.getUpdated()))
				.valueOld("address3")
				.valueNew("address4")
				.build();

		// this is how the POInfo-dependent code in RecordChangeLogEntryLoader shall distill the two C_Locations into one RecordChangeLogEntry
		final BiFunction<TableRecordReference, ImmutableList<I_C_Location>, ImmutableList<RecordRefWithLogEntry>> //
		derivedLocationEntriesProvider = (tableRecordReference, locationRecords) -> {

			assertThat(tableRecordReference).isEqualTo(recordRef);
			assertThat(locationRecords).containsExactlyInAnyOrder(location1, location2, location3, location4);
			return ImmutableList.of(
					new RecordRefWithLogEntry(recordRef, expectedChangeLogEntry1),
					new RecordRefWithLogEntry(recordRef, expectedChangeLogEntry2));
		};
		final LogEntriesQuery logEntriesQuery = LogEntriesQuery.builder()
				.tableRecordReference(recordRef)
				.followLocationIdChanges(true).build();

		final List<RecordRefWithLogEntry> recordRefsWithLogEntries = ImmutableList.of(recordRefWithLogEntry1, recordRefWithLogEntry2, recordRefWithLogEntry3);

		// when
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> result = RecordRefWithLogEntryProcessor
				.builder()
				.referencesLocationTablePredicate(r -> true)
				.changeLogActiveForLocationTable(true)
				.derivedLocationEntriesProvider(derivedLocationEntriesProvider)
				.build()
				.processRecordRefsWithLogEntries(logEntriesQuery, recordRefsWithLogEntries);

		// then
		assertThat(result.size()).isEqualTo(2); // number of key-value-pair in the multimap
		assertThat(result.get(recordRef)).hasSize(2);
		assertThat(result.get(recordRef)).containsExactly(expectedChangeLogEntry1, expectedChangeLogEntry2);

	}
}
