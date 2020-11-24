package de.metas.bpartner.composite.repository;

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
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.interfaces.I_C_BPartner;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
@UtilityClass
final class ChangeLogUtil
{
	private static final ImmutableMap<String, String> BPARTNER_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_BPartner.COLUMNNAME_Value, BPartner.VALUE)
			.put(I_C_BPartner.COLUMNNAME_CompanyName, BPartner.COMPANY_NAME)
			.put(I_C_BPartner.COLUMNNAME_ExternalId, BPartner.EXTERNAL_ID)
			.put(I_C_BPartner.COLUMNNAME_GlobalId, BPartner.GLOBAL_ID)
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
			.put(I_C_BPartner.COLUMNNAME_IsVendor, BPartner.VENDOR)
			.put(I_C_BPartner.COLUMNNAME_IsCustomer, BPartner.CUSTOMER)
			.put(I_C_BPartner.COLUMNNAME_VATaxID, BPartner.VAT_ID)
			.build();

	@VisibleForTesting
	static final ImmutableMap<String, String> AD_USER_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_AD_User.COLUMNNAME_AD_User_ID, BPartnerContact.ID)
			.put(I_AD_User.COLUMNNAME_C_BPartner_ID, BPartnerContact.BPARTNER_ID)
			.put(I_AD_User.COLUMNNAME_EMail, BPartnerContact.EMAIL)
			.put(I_AD_User.COLUMNNAME_ExternalId, BPartnerContact.EXTERNAL_ID)
			.put(I_AD_User.COLUMNNAME_Value, BPartnerContact.VALUE)
			.put(I_AD_User.COLUMNNAME_Firstname, BPartnerContact.FIRST_NAME)
			.put(I_AD_User.COLUMNNAME_Lastname, BPartnerContact.LAST_NAME)
			.put(I_AD_User.COLUMNNAME_Name, BPartnerContact.NAME)
			.put(I_AD_User.COLUMNNAME_Phone, BPartnerContact.PHONE)
			.put(I_AD_User.COLUMNNAME_IsDefaultContact, BPartnerContactType.DEFAULT_CONTACT)
			.put(I_AD_User.COLUMNNAME_IsBillToContact_Default, BPartnerContactType.BILL_TO_DEFAULT)
			.put(I_AD_User.COLUMNNAME_IsShipToContact_Default, BPartnerContactType.SHIP_TO_DEFAULT)
			.put(I_AD_User.COLUMNNAME_IsSalesContact, BPartnerContactType.SALES)
			.put(I_AD_User.COLUMNNAME_IsSalesContact_Default, BPartnerContactType.SALES_DEFAULT)
			.put(I_AD_User.COLUMNNAME_IsPurchaseContact, BPartnerContactType.PURCHASE)
			.put(I_AD_User.COLUMNNAME_IsPurchaseContact_Default, BPartnerContactType.PURCHASE_DEFAULT)
			.put(I_AD_User.COLUMNNAME_IsSubjectMatterContact, BPartnerContactType.SUBJECT_MATTER)
			.put(I_AD_User.COLUMNNAME_IsActive, BPartnerContact.ACTIVE)
			.put(I_AD_User.COLUMNNAME_IsNewsletter, BPartnerContact.NEWSLETTER)
			.put(I_AD_User.COLUMNNAME_Fax, BPartnerContact.FAX)
			.put(I_AD_User.COLUMNNAME_MobilePhone, BPartnerContact.MOBILE_PHONE)
			.put(I_AD_User.COLUMNNAME_Description, BPartnerContact.DESCRIPTION)
			.put(I_AD_User.COLUMNNAME_C_Greeting_ID, BPartnerContact.GREETING_ID)
			.build();

	@VisibleForTesting
	static final ImmutableMap<String, String> C_BPARTNER_LOCATION_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_BPartner_Location.COLUMNNAME_ExternalId, BPartnerLocation.EXTERNAL_ID)
			.put(I_C_BPartner_Location.COLUMNNAME_GLN, BPartnerLocation.GLN)
			.put(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, BPartnerLocation.ID)
			.put(I_C_BPartner_Location.COLUMNNAME_Name, BPartnerLocation.NAME)
			.put(I_C_BPartner_Location.COLUMNNAME_BPartnerName, BPartnerLocation.BPARTNERNAME)
			.put(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault, BPartnerLocationType.BILL_TO_DEFAULT)
			.put(I_C_BPartner_Location.COLUMNNAME_IsBillTo, BPartnerLocationType.BILL_TO)
			.put(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault, BPartnerLocationType.SHIP_TO_DEFAULT)
			.put(I_C_BPartner_Location.COLUMNNAME_IsShipTo, BPartnerLocationType.SHIP_TO)
			.put(I_C_BPartner_Location.COLUMNNAME_IsActive, BPartnerLocation.ACTIVE)

			// C_Location is immutable and therefore individual C_Location records don't have a change log.
			// However, when we load the change log records of C_BPartner_Location,
			// then we iterate the change log history of the C_BPartner_Location.C_Location_ID column,
			// load the respective C_Location records and derive change log entries which we add to C_BPartner_Location's change log.
			.put(I_C_Location.COLUMNNAME_Address1, BPartnerLocation.ADDRESS_1)
			.put(I_C_Location.COLUMNNAME_Address2, BPartnerLocation.ADDRESS_2)
			.put(I_C_Location.COLUMNNAME_Address3, BPartnerLocation.ADDRESS_3)
			.put(I_C_Location.COLUMNNAME_Address4, BPartnerLocation.ADDRESS_4)
			.put(I_C_Location.COLUMNNAME_City, BPartnerLocation.CITY)
			.put(I_C_Location.COLUMNNAME_POBox, BPartnerLocation.PO_BOX)
			.put(I_C_Location.COLUMNNAME_Postal, BPartnerLocation.POSTAL)
			.put(I_C_Location.COLUMNNAME_RegionName, BPartnerLocation.REGION)
			.build();

	private static final ImmutableMap<String, String> C_POSTAL_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_Postal.COLUMNNAME_District, BPartnerLocation.DISTRICT)
			.build();

	private static final ImmutableMap<String, String> C_COUNTRY_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_Country.COLUMNNAME_CountryCode, BPartnerLocation.COUNTRYCODE)
			.build();

	@VisibleForTesting
	private static final ImmutableMap<String, String> C_BP_BANKACCOUNT_COLUMN_MAP = ImmutableMap
			.<String, String> builder()
			.put(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, BPartnerBankAccount.ID)
			.put(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, BPartnerBankAccount.BPARTNER_ID)
			.put(I_C_BP_BankAccount.COLUMNNAME_IBAN, BPartnerBankAccount.IBAN)
			.put(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, BPartnerBankAccount.CURRENCY_ID)
			.put(I_C_BP_BankAccount.COLUMNNAME_IsActive, BPartnerBankAccount.ACTIVE)
			.build();

	public static RecordChangeLog createBPartnerChangeLog(
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> changeLogEntries)
	{
		final ImmutableList<RecordChangeLogEntry> bpartnerEntries = changeLogEntries.get(TableRecordReference.of(bpartnerRecord));

		IPair<Instant, UserId> lastChanged = ImmutablePair.of(
				TimeUtil.asInstant(bpartnerRecord.getUpdated()),
				UserId.ofRepoIdOrNull(bpartnerRecord.getUpdatedBy()/* might be -1 */));

		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		for (final RecordChangeLogEntry bpartnerLocationEntry : bpartnerEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(bpartnerLocationEntry, BPARTNER_COLUMN_MAP);
			lastChanged = latestOf(entry, lastChanged);
			entry.ifPresent(domainEntries::add);
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoIdOrNull(bpartnerRecord.getCreatedBy()/* might be -1 */))
				.createdTimestamp(TimeUtil.asInstant(bpartnerRecord.getCreated()))
				.lastChangedByUserId(lastChanged.getRight())
				.lastChangedTimestamp(lastChanged.getLeft())
				.recordId(ComposedRecordId.singleKey(org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnerRecord.getC_BPartner_ID()))
				.tableName(org.compiere.model.I_C_BPartner.Table_Name)
				.entries(domainEntries)
				.build();
	}

	public static RecordChangeLog createContactChangeLog(
			@NonNull final I_AD_User userRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = relatedRecords.getRecordRef2LogEntries();
		final ImmutableList<RecordChangeLogEntry> userEntries = recordRef2LogEntries.get(TableRecordReference.of(userRecord));

		IPair<Instant, UserId> lastChanged = ImmutablePair.of(
				TimeUtil.asInstant(userRecord.getUpdated()),
				UserId.ofRepoIdOrNull(userRecord.getUpdatedBy()/* might be -1 */));

		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		for (final RecordChangeLogEntry userEntry : userEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(userEntry, AD_USER_COLUMN_MAP);
			lastChanged = latestOf(entry, lastChanged);
			entry.ifPresent(domainEntries::add);
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoIdOrNull(userRecord.getCreatedBy()/* might be -1 */))
				.createdTimestamp(TimeUtil.asInstant(userRecord.getCreated()))
				.lastChangedByUserId(lastChanged.getRight())
				.lastChangedTimestamp(lastChanged.getLeft())
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

		IPair<Instant, UserId> lastChanged = ImmutablePair.of(
				TimeUtil.asInstant(bpartnerLocationRecord.getUpdated()),
				UserId.ofRepoIdOrNull(bpartnerLocationRecord.getUpdatedBy()/* might be -1 */));

		final ImmutableList<RecordChangeLogEntry> bpartnerLocationEntries = recordRef2LogEntries.get(TableRecordReference.of(bpartnerLocationRecord));
		for (final RecordChangeLogEntry bpartnerLocationEntry : bpartnerLocationEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(bpartnerLocationEntry, C_BPARTNER_LOCATION_COLUMN_MAP);
			lastChanged = latestOf(entry, lastChanged);
			entry.ifPresent(domainEntries::add);
		}

		// location
		final I_C_Location locationRecord = relatedRecords.getLocationId2Location().get(bpartnerLocationRecord.getC_Location_ID());
		if (locationRecord.getCreated().before(created))
		{
			created = locationRecord.getCreated();
			createdBy = locationRecord.getCreatedBy();
		}

		// country
		final I_C_Country countryRecord = relatedRecords.getCountryId2Country().get(locationRecord.getC_Country_ID());
		final ImmutableList<RecordChangeLogEntry> countryEntries = recordRef2LogEntries.get(TableRecordReference.of(countryRecord));
		for (final RecordChangeLogEntry countryEntry : countryEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(countryEntry, C_COUNTRY_COLUMN_MAP);
			lastChanged = latestOf(entry, lastChanged);
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
				lastChanged = latestOf(entry, lastChanged);
				entry.ifPresent(domainEntries::add);
			}
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoIdOrNull(createdBy/* might be -1 */))
				.createdTimestamp(TimeUtil.asInstant(created))
				.lastChangedByUserId(lastChanged.getRight())
				.lastChangedTimestamp(lastChanged.getLeft())
				.recordId(ComposedRecordId.singleKey(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpartnerLocationRecord.getC_BPartner_Location_ID()))
				.tableName(I_C_BPartner_Location.Table_Name)
				.entries(domainEntries)
				.build();
	}

	public static RecordChangeLog createBankAccountChangeLog(
			@NonNull final I_C_BP_BankAccount bankAccountRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = relatedRecords.getRecordRef2LogEntries();
		final ImmutableList<RecordChangeLogEntry> userEntries = recordRef2LogEntries.get(TableRecordReference.of(bankAccountRecord));

		IPair<Instant, UserId> lastChanged = ImmutablePair.of(
				TimeUtil.asInstant(bankAccountRecord.getUpdated()),
				UserId.ofRepoIdOrNull(bankAccountRecord.getUpdatedBy()/* might be -1 */));

		final List<RecordChangeLogEntry> domainEntries = new ArrayList<>();
		for (final RecordChangeLogEntry userEntry : userEntries)
		{
			final Optional<RecordChangeLogEntry> entry = entryWithDomainFieldName(userEntry, C_BP_BANKACCOUNT_COLUMN_MAP);
			lastChanged = latestOf(entry, lastChanged);
			entry.ifPresent(domainEntries::add);
		}

		return RecordChangeLog.builder()
				.createdByUserId(UserId.ofRepoIdOrNull(bankAccountRecord.getCreatedBy()/* might be -1 */))
				.createdTimestamp(TimeUtil.asInstant(bankAccountRecord.getCreated()))
				.lastChangedByUserId(lastChanged.getRight())
				.lastChangedTimestamp(lastChanged.getLeft())
				.recordId(ComposedRecordId.singleKey(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, bankAccountRecord.getC_BP_BankAccount_ID()))
				.tableName(I_C_BP_BankAccount.Table_Name)
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
			@NonNull final Optional<RecordChangeLogEntry> currentLogEntry,
			@NonNull final IPair<Instant, UserId> previousLastChanged)
	{
		if (!currentLogEntry.isPresent())
		{
			return previousLastChanged;
		}

		final Instant entryTimestamp = currentLogEntry.get().getChangedTimestamp();
		if (previousLastChanged.getLeft().isAfter(entryTimestamp))
		{
			return previousLastChanged;
		}

		return ImmutablePair.of(entryTimestamp, currentLogEntry.get().getChangedByUserId());
	}
}
