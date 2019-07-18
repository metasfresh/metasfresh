package de.metas.bpartner.composite;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.table.ComposedRecordId;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.composite.BPartnerCompositeRepository.CompositeRelatedRecords;
import de.metas.interfaces.I_C_BPartner;
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

public class ChangeLogUtil
{

	private static final ImmutableMap<String, String> BPARTNER_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_BPartner.COLUMNNAME_Value, BPartner.VALUE)
			.put(I_C_BPartner.COLUMNNAME_CompanyName, BPartner.COMPANY_NAME)
			.put(I_C_BPartner.COLUMNNAME_ExternalId, BPartner.EXTERNAL_ID)
			.put(I_C_BPartner.COLUMNNAME_C_BP_Group_ID, BPartner.GROUP_ID)
			.put(I_C_BPartner.COLUMNNAME_AD_Language, BPartner.LANGUAGE)
			.put(I_C_BPartner.COLUMNNAME_C_BPartner_ID, BPartner.ID)
			.put(I_C_BPartner.COLUMNNAME_Name, BPartner.NAME)
			.put(I_C_BPartner.COLUMNNAME_Name2, BPartner.NAME_2)
			.put(I_C_BPartner.COLUMNNAME_Name3, BPartner.NAME_3)
			.put(I_C_BPartner.COLUMNNAME_BPartner_Parent_ID, BPartner.PARENT_ID)
			.put(I_C_BPartner.COLUMNNAME_Phone2, BPartner.PHONE)
			.put(I_C_BPartner.COLUMNNAME_URL, BPartner.URL)
			.put(I_C_BPartner.COLUMNNAME_URL2, BPartner.URL_2)
			.put(I_C_BPartner.COLUMNNAME_URL3, BPartner.URL_3)
			.put(I_C_BPartner.COLUMNNAME_IsActive, BPartner.ACTIVE)
			.build();

	private static final ImmutableMap<String, String> AD_USER_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_AD_User.COLUMNNAME_AD_User_ID, "id")
			.put(I_AD_User.COLUMNNAME_EMail, "email")
			.put(I_AD_User.COLUMNNAME_ExternalId, "externalId")
			.put(I_AD_User.COLUMNNAME_Firstname, "firstName")
			.put(I_AD_User.COLUMNNAME_Lastname, "lastName")
			.put(I_AD_User.COLUMNNAME_Name, "name")
			.put(I_AD_User.COLUMNNAME_Phone, "phone")
			.put(I_AD_User.COLUMNNAME_IsDefaultContact, "defaultContact")
			.put(I_AD_User.COLUMNNAME_IsBillToContact_Default, "billToDefault")
			.put(I_AD_User.COLUMNNAME_IsShipToContact_Default, "shipToDefault")
			.put(I_AD_User.COLUMNNAME_IsSalesContact, "sales")
			.put(I_AD_User.COLUMNNAME_IsSalesContact_Default, "salesDefault")
			.put(I_AD_User.COLUMNNAME_IsPurchaseContact, "purchase")
			.put(I_AD_User.COLUMNNAME_IsPurchaseContact_Default, "purchaseDefault")
			.put(I_AD_User.COLUMNNAME_IsSubjectMatterContact, "subjectMatter")
			.put(I_AD_User.COLUMNNAME_IsActive, "active")
			.put(I_AD_User.COLUMNNAME_IsNewsletter, "newsletter")
			.put(I_AD_User.COLUMNNAME_Fax, "fax")
			.put(I_AD_User.COLUMNNAME_MobilePhone, "mobilePhone")
			.put(I_AD_User.COLUMNNAME_Description, "description")
			.put(I_AD_User.COLUMNNAME_C_Greeting_ID, "greetingId")
			.build();

	private static final ImmutableMap<String, String> C_LOCATION_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_Location.COLUMNNAME_Address1, "address1")
			.put(I_C_Location.COLUMNNAME_Address2, "address2")
			.put(I_C_Location.COLUMNNAME_Address3, "address3")
			.put(I_C_Location.COLUMNNAME_Address4, "address4")
			.put(I_C_Location.COLUMNNAME_City, "city")
			.put(I_C_Location.COLUMNNAME_POBox, "poBox")
			.put(I_C_Location.COLUMNNAME_Postal, "postal")
			.put(I_C_Location.COLUMNNAME_RegionName, "region")
			.build();

	private static final ImmutableMap<String, String> C_BPARTNER_LOCATION_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_BPartner_Location.COLUMNNAME_ExternalId, "externalId")
			.put(I_C_BPartner_Location.COLUMNNAME_GLN, "gln")
			.put(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, "id")
			.put(I_C_BPartner_Location.COLUMNNAME_Name, "name")
			.put(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault, "billToDefault")
			.put(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault, "shipToDefault")
			.put(I_C_BPartner_Location.COLUMNNAME_IsBillTo, "billTo")
			.put(I_C_BPartner_Location.COLUMNNAME_IsShipTo, "shipTo")
			.put(I_C_BPartner_Location.COLUMNNAME_IsActive, "active")
			.build();

	private static final ImmutableMap<String, String> C_POSTAL_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_Postal.COLUMNNAME_District, "district")
			.build();

	private static final ImmutableMap<String, String> C_COUNTRY_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_Country.COLUMNNAME_CountryCode, "countryCode")
			.build();

	public static RecordChangeLog createBPartnerChangeLog(
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> immutableListMultimap)
	{
		final ImmutableList<RecordChangeLogEntry> bpartnerEntries = immutableListMultimap.get(TableRecordReference.of(bpartnerRecord));

		IPair<Instant, UserId> updated = ImmutablePair.of(
				TimeUtil.asInstant(bpartnerRecord.getUpdated()),
				UserId.ofRepoId(bpartnerRecord.getUpdatedBy()));

		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		for (final RecordChangeLogEntry bpartnerLocationEntry : bpartnerEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(bpartnerLocationEntry, BPARTNER_COLUMN_MAP);
			updated = latestOf(entry, updated);
			entry.ifPresent(domainEntries::add);
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoId(bpartnerRecord.getCreatedBy()))
				.createdTimestamp(TimeUtil.asInstant(bpartnerRecord.getCreated()))
				.lastChangedByUserId(updated.getRight())
				.lastChangedTimestamp(updated.getLeft())
				.recordId(ComposedRecordId.singleKey(I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnerRecord.getC_BPartner_ID()))
				.tableName(I_C_BPartner.Table_Name)
				.entries(domainEntries)
				.build();
	}

	public static RecordChangeLog createcontactChangeLog(
			@NonNull final I_AD_User userRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = relatedRecords.getRecordRef2LogEntries();
		final ImmutableList<RecordChangeLogEntry> userEntries = recordRef2LogEntries.get(TableRecordReference.of(userRecord));

		IPair<Instant, UserId> updated = ImmutablePair.of(
				TimeUtil.asInstant(userRecord.getUpdated()),
				UserId.ofRepoId(userRecord.getUpdatedBy()));

		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		for (final RecordChangeLogEntry userEntry : userEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(userEntry, AD_USER_COLUMN_MAP);
			updated = latestOf(entry, updated);
			entry.ifPresent(domainEntries::add);
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoId(userRecord.getCreatedBy()))
				.createdTimestamp(TimeUtil.asInstant(userRecord.getCreated()))
				.lastChangedByUserId(updated.getRight())
				.lastChangedTimestamp(updated.getLeft())
				.recordId(ComposedRecordId.singleKey(I_AD_User.COLUMNNAME_AD_User_ID, userRecord.getAD_User_ID()))
				.tableName(I_AD_User.Table_Name)
				.entries(domainEntries)
				.build();
	}

	public static RecordChangeLog createBPartnerLocationChangeLog(
			@NonNull final I_C_BPartner_Location bpartnerLocationRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = relatedRecords.getRecordRef2LogEntries();

		// bpartnerLocation
		Timestamp created = bpartnerLocationRecord.getCreated();
		int createdBy = bpartnerLocationRecord.getCreatedBy();

		IPair<Instant, UserId> updated = ImmutablePair.of(
				TimeUtil.asInstant(bpartnerLocationRecord.getUpdated()),
				UserId.ofRepoId(bpartnerLocationRecord.getUpdatedBy()));

		final ImmutableList<RecordChangeLogEntry> bpartnerLocationEntries = recordRef2LogEntries.get(TableRecordReference.of(bpartnerLocationRecord));
		for (final RecordChangeLogEntry bpartnerLocationEntry : bpartnerLocationEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(bpartnerLocationEntry, C_BPARTNER_LOCATION_COLUMN_MAP);
			updated = latestOf(entry, updated);
			entry.ifPresent(domainEntries::add);
		}

		// location
		final I_C_Location locationRecord = relatedRecords.getLocationId2Location().get(bpartnerLocationRecord.getC_Location_ID());
		if (locationRecord.getCreated().before(created))
		{
			created = locationRecord.getCreated();
			createdBy = locationRecord.getCreatedBy();
		}
		final ImmutableList<RecordChangeLogEntry> locationEntries = recordRef2LogEntries.get(TableRecordReference.of(locationRecord));
		for (final RecordChangeLogEntry locationEntry : locationEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(locationEntry, C_LOCATION_COLUMN_MAP);
			updated = latestOf(entry, updated);
			entry.ifPresent(domainEntries::add);
		}

		// country
		final I_C_Country countryRecord = relatedRecords.getCountryId2Country().get(locationRecord.getC_Country_ID());
		final ImmutableList<RecordChangeLogEntry> countryEntries = recordRef2LogEntries.get(TableRecordReference.of(countryRecord));
		for (final RecordChangeLogEntry countryEntry : countryEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(countryEntry, C_COUNTRY_COLUMN_MAP);
			updated = latestOf(entry, updated);
			entry.ifPresent(domainEntries::add);
		}

		// postal
		final I_C_Postal postalRecord = relatedRecords.getPostalId2Postal().get(locationRecord.getC_Postal_ID());
		if (postalRecord != null)
		{
			final ImmutableList<RecordChangeLogEntry> postalEntries = recordRef2LogEntries.get(TableRecordReference.of(postalRecord));
			for (final RecordChangeLogEntry postalEntry : postalEntries)
			{
				final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(postalEntry, C_POSTAL_COLUMN_MAP);
				updated = latestOf(entry, updated);
				entry.ifPresent(domainEntries::add);
			}
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoId(createdBy))
				.createdTimestamp(TimeUtil.asInstant(created))
				.lastChangedByUserId(updated.getRight())
				.lastChangedTimestamp(updated.getLeft())
				.recordId(ComposedRecordId.singleKey(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpartnerLocationRecord.getC_BPartner_Location_ID()))
				.tableName(I_C_BPartner_Location.Table_Name)
				.entries(domainEntries)
				.build();
	}

	private static Optional<RecordChangeLogEntry> entryWithDomainFieldName(
			@NonNull final RecordChangeLogEntry entry,
			@NonNull final ImmutableMap<String, String> columnMap)
	{
		final String fieldName = columnMap.get(entry.getColumnName());
		if (fieldName == null)
		{
			return Optional.empty();
		}

		final RecordChangeLogEntry result = entry
				.toBuilder()
				.columnName(columnMap.get(entry.getColumnName()))
				.build();
		return Optional.of(result);
	}

	private static IPair<Instant, UserId> latestOf(
			@NonNull final Optional<RecordChangeLogEntry> entry,
			@NonNull final IPair<Instant, UserId> previous)
	{
		if (!entry.isPresent())
		{
			return previous;
		}

		final Instant entryTimestamp = entry.get().getChangedTimestamp();
		if (previous.getLeft().isAfter(entryTimestamp))
		{
			return previous;
		}

		return ImmutablePair.of(entryTimestamp, entry.get().getChangedByUserId());
	}
}
