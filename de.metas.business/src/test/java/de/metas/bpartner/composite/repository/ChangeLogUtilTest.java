package de.metas.bpartner.composite.repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Timestamp;
import java.time.Instant;

import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

class ChangeLogUtilTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void createContactChangeLog_negative_AD_User_updatedBy_zero_changeLogs()
	{
		final I_AD_User userRecord = createUserRecordWith(20/* createBy */, -1/* updatedBy */);

		final CompositeRelatedRecords relatedRecords = createEmptyRelatedRecords();

		// invoke the method under test
		final RecordChangeLog result = ChangeLogUtil.createContactChangeLog(userRecord, relatedRecords);

		assertThat(result.getCreatedByUserId().getRepoId()).isEqualTo(20);
		assertThat(result.getLastChangedByUserId()).isNull();
		assertThat(result.getLastChangedTimestamp()).isEqualTo(Instant.parse("2007-12-03T10:15:30.00Z")); // ..the one from createUserRecordWithNegativeUpdatedBy()
		assertThat(result.getRecordId().getSingleRecordId().getAsInt()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.getTableName()).isEqualTo(I_AD_User.Table_Name);
		assertThat(result.getEntries()).isEmpty();
	}

	@Test
	void createContactChangeLog_negative_AD_User_createdBy_zero_changeLogs()
	{
		final I_AD_User userRecord = createUserRecordWith(-1/* createBy */, -1/* updatedBy */);

		final CompositeRelatedRecords relatedRecords = createEmptyRelatedRecords();

		// invoke the method under test
		final RecordChangeLog result = ChangeLogUtil.createContactChangeLog(userRecord, relatedRecords);

		assertThat(result.getCreatedByUserId()).isNull();
		assertThat(result.getLastChangedByUserId()).isNull();
		assertThat(result.getLastChangedTimestamp()).isEqualTo(Instant.parse("2007-12-03T10:15:30.00Z")); // ..the one from createUserRecordWithNegativeUpdatedBy()
		assertThat(result.getRecordId().getSingleRecordId().getAsInt()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.getTableName()).isEqualTo(I_AD_User.Table_Name);
		assertThat(result.getEntries()).isEmpty();
	}

	@Test
	void createContactChangeLog_negative_AD_User_updatedBy_one_changeLog()
	{
		final I_AD_User userRecord = createUserRecordWith(20/* createBy */, -1/* updatedBy */);

		assertThat(ChangeLogUtil.AD_USER_COLUMN_MAP).containsEntry(I_AD_User.COLUMNNAME_Name, BPartnerContact.NAME); // guard

		final RecordChangeLogEntry recordChangeLogEntry = RecordChangeLogEntry
				.builder()
				.columnName(I_AD_User.COLUMNNAME_Name) // must be one contained in the map column which ChangeLogUtil cares about
				.columnDisplayName(TranslatableStrings.constant("userRecordColumnDisplayName"))
				.changedTimestamp(Instant.parse("2007-12-03T10:05:30.00Z")) // *before* the one from createUserRecordWithNegativeUpdatedBy()
				.changedByUserId(UserId.ofRepoId(20))
				.build();

		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = ImmutableListMultimap.of(
				TableRecordReference.of(userRecord), recordChangeLogEntry);
		final CompositeRelatedRecords relatedRecords = createRelatedRecordsWith(
				ImmutableMap.of()/* locationId2Location */,
				ImmutableMap.of()/* countryId2Country */,
				recordRef2LogEntries);

		// invoke the method under test
		final RecordChangeLog result = ChangeLogUtil.createContactChangeLog(userRecord, relatedRecords);

		assertThat(result.getLastChangedByUserId()).isNull();
		assertThat(result.getLastChangedTimestamp()).isEqualTo(Instant.parse("2007-12-03T10:15:30.00Z")); // ..the one from createUserRecordWithNegativeUpdatedBy()
		assertThat(result.getRecordId().getSingleRecordId().getAsInt()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.getTableName()).isEqualTo(I_AD_User.Table_Name);

		assertThat(result.getEntries()).extracting("changedTimestamp", "changedByUserId", "columnName", "columnDisplayName")
				.containsExactly(tuple(Instant.parse("2007-12-03T10:05:30.00Z"), UserId.ofRepoId(20), BPartnerContact.NAME, TranslatableStrings.constant("userRecordColumnDisplayName")));
	}

	private I_AD_User createUserRecordWith(final int createdBy, final int updatedby)
	{
		final Timestamp userRecordUpdated = Timestamp.from(Instant.parse("2007-12-03T10:15:30.00Z"));

		final I_AD_User userRecord = newInstance(I_AD_User.class);
		InterfaceWrapperHelper.setValue(userRecord, I_AD_User.COLUMNNAME_CreatedBy, createdBy);
		InterfaceWrapperHelper.setValue(userRecord, I_AD_User.COLUMNNAME_UpdatedBy, updatedby);
		saveRecord(userRecord);
		InterfaceWrapperHelper.setValue(userRecord, I_AD_User.COLUMNNAME_Updated, userRecordUpdated);

		// guards
		assertThat(userRecord.getUpdated()).isEqualTo(userRecordUpdated);
		assertThat(userRecord.getCreatedBy()).isEqualTo(createdBy);
		assertThat(userRecord.getUpdatedBy()).isEqualTo(updatedby);
		return userRecord;
	}

	@Test
	void createBPartnerLocationChangeLog()
	{
		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		saveRecord(countryRecord);

		final I_C_Location locationRecord = createLocationRecordWithNegativeUpdatedBy(countryRecord);
		final I_C_BPartner_Location bpartnerLocalocationRecord = createBPartnerLocationRecordWithNegativeUpdatedBy(locationRecord);

		assertThat(ChangeLogUtil.C_BPARTNER_LOCATION_COLUMN_MAP).containsEntry(I_C_BPartner_Location.COLUMNNAME_Name, BPartnerLocation.NAME); // guard

		final CompositeRelatedRecords relatedRecords = createRelatedRecordsWith(
				ImmutableMap.of(locationRecord.getC_Location_ID(), locationRecord)/* locationId2Location */,
				ImmutableMap.of(countryRecord.getC_Country_ID(), countryRecord)/* countryId2Country */,
				ImmutableListMultimap.of() /* recordRef2LogEntries */);

		// invoke the method under test
		final RecordChangeLog result = ChangeLogUtil.createBPartnerLocationChangeLog(bpartnerLocalocationRecord, relatedRecords);

		assertThat(result.getLastChangedByUserId()).isNull();
		assertThat(result.getLastChangedTimestamp()).isEqualTo(Instant.parse("2007-12-03T10:15:30.00Z")); // ..the one from createUserRecordWithNegativeUpdatedBy()
		assertThat(result.getRecordId().getSingleRecordId().getAsInt()).isEqualTo(bpartnerLocalocationRecord.getC_BPartner_Location_ID());
		assertThat(result.getTableName()).isEqualTo(I_C_BPartner_Location.Table_Name);

		assertThat(result.getEntries()).isEmpty();
	}

	private I_C_Location createLocationRecordWithNegativeUpdatedBy(@NonNull final I_C_Country countryRecord)
	{
		final Timestamp bpartnerLocationRecordUpdated = Timestamp.from(Instant.parse("2007-12-03T10:15:30.00Z"));

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(countryRecord.getC_Country_ID());

		InterfaceWrapperHelper.setValue(locationRecord, I_C_Location.COLUMNNAME_UpdatedBy, -1);
		saveRecord(locationRecord);
		InterfaceWrapperHelper.setValue(locationRecord, I_C_Location.COLUMNNAME_Updated, bpartnerLocationRecordUpdated);

		// guards
		assertThat(locationRecord.getUpdated()).isEqualTo(bpartnerLocationRecordUpdated);
		assertThat(locationRecord.getUpdatedBy()).isEqualTo(-1);
		return locationRecord;
	}

	private I_C_BPartner_Location createBPartnerLocationRecordWithNegativeUpdatedBy(@NonNull final I_C_Location locationRecord)
	{
		final Timestamp bpartnerLocationRecordUpdated = Timestamp.from(Instant.parse("2007-12-03T10:15:30.00Z"));

		final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());

		InterfaceWrapperHelper.setValue(bpartnerLocationRecord, I_C_BPartner_Location.COLUMNNAME_UpdatedBy, -1);
		saveRecord(bpartnerLocationRecord);
		InterfaceWrapperHelper.setValue(bpartnerLocationRecord, I_C_BPartner_Location.COLUMNNAME_Updated, bpartnerLocationRecordUpdated);

		// guards
		assertThat(bpartnerLocationRecord.getUpdated()).isEqualTo(bpartnerLocationRecordUpdated);
		assertThat(bpartnerLocationRecord.getUpdatedBy()).isEqualTo(-1);
		return bpartnerLocationRecord;
	}

	private CompositeRelatedRecords createRelatedRecordsWith(
			@NonNull final ImmutableMap<Integer, I_C_Location> locationId2Location,
			@NonNull final ImmutableMap<Integer, I_C_Country> countryId2Country,
			@NonNull final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries)
	{
		final CompositeRelatedRecords relatedRecords = new CompositeRelatedRecords(
				ImmutableListMultimap.of(), /* bpartnerId2Users */
				ImmutableListMultimap.of(), /* bpartnerId2BPartnerLocations */
				locationId2Location,
				ImmutableMap.of(), /* postalId2Postal */
				countryId2Country,
				ImmutableListMultimap.of(), /* bank accounts */
				recordRef2LogEntries);
		return relatedRecords;
	}

	private CompositeRelatedRecords createEmptyRelatedRecords()
	{
		final CompositeRelatedRecords relatedRecords = new CompositeRelatedRecords(
				ImmutableListMultimap.of(), /* bpartnerId2Users */
				ImmutableListMultimap.of(), /* bpartnerId2BPartnerLocations */
				ImmutableMap.of(), /* locationId2Location */
				ImmutableMap.of(), /* postalId2Postal */
				ImmutableMap.of(), /* countryId2Country */
				ImmutableListMultimap.of(), /* bank accounts */
				ImmutableListMultimap.of() /* recordRef2LogEntries */);
		return relatedRecords;
	}

}
