package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocation.BPartnerLocationBuilder;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.greeting.GreetingId;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.metas.util.StringUtils.trimBlankToNull;

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

final class BPartnerCompositesLoader
{
	private static final Logger logger = LogManager.getLogger(BPartnerCompositesLoader.class);
	private final LogEntriesRepository recordChangeLogRepository;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Builder
	private BPartnerCompositesLoader(@NonNull final LogEntriesRepository recordChangeLogRepository)
	{
		this.recordChangeLogRepository = recordChangeLogRepository;
	}

	public ImmutableMap<BPartnerId, BPartnerComposite> retrieveByIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		final List<I_C_BPartner> bPartnerRecords = queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyContextClient()
				.addInArrayFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.list();

		return createBPartnerComposites(bPartnerRecords);
	}

	private ImmutableMap<BPartnerId, BPartnerComposite> createBPartnerComposites(@NonNull final List<I_C_BPartner> bPartnerRecords)
	{
		final ImmutableListMultimap<Integer, I_C_BPartner> id2bpartners = Multimaps.index(bPartnerRecords, I_C_BPartner::getC_BPartner_ID);

		final ImmutableList<BPartnerId> bPartnerIds = id2bpartners
				.keySet()
				.stream()
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final CompositeRelatedRecords relatedRecords = retrieveRelatedRecords(bPartnerIds);

		final ImmutableMap.Builder<BPartnerId, BPartnerComposite> result = ImmutableMap.<BPartnerId, BPartnerComposite>builder();

		for (final I_C_BPartner bPartnerRecord : bPartnerRecords)
		{
			final BPartnerId id = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

			final BPartner bpartner = ofBPartnerRecord(bPartnerRecord, relatedRecords.getRecordRef2LogEntries());

			final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
					.orgId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID()))
					.bpartner(bpartner)
					.contacts(ofContactRecords(id, relatedRecords))
					.locations(ofLocationRecords(id, relatedRecords))
					.bankAccounts(ofBankAccountRecords(id, relatedRecords))
					.build();

			result.put(id, bpartnerComposite);
		}
		return result.build();
	}

	private CompositeRelatedRecords retrieveRelatedRecords(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final List<TableRecordReference> allTableRecordRefs = new ArrayList<>();
		bPartnerIds.forEach(bPartnerId -> allTableRecordRefs.add(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId.getRepoId())));

		final List<I_AD_User> contactRecords = queryBL
				.createQueryBuilder(I_AD_User.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_AD_User> bpartnerId2Users = Multimaps.index(contactRecords, I_AD_User::getC_BPartner_ID);
		contactRecords.forEach(contactRecord -> allTableRecordRefs.add(TableRecordReference.of(contactRecord)));

		final List<I_C_BPartner_Location> bPartnerLocationRecords = queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_C_BPartner_Location> bpartnerId2BPartnerLocations = Multimaps.index(bPartnerLocationRecords, I_C_BPartner_Location::getC_BPartner_ID);
		bPartnerLocationRecords.forEach(bPartnerLocationRecord -> allTableRecordRefs.add(TableRecordReference.of(bPartnerLocationRecord)));

		final ImmutableSet<Integer> locationIds = CollectionUtils.extractDistinctElementsIntoSet(bPartnerLocationRecords, I_C_BPartner_Location::getC_Location_ID);
		Check.assume(!locationIds.contains(0), "C_BPartner_Location.C_Location_ID is mandatory, so locationIds may not contain 0; locationIds={}", locationIds); // useful when unit-testing
		final List<I_C_Location> locationRecords = queryBL
				.createQueryBuilder(I_C_Location.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_C_Location.COLUMNNAME_C_Location_ID, locationIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Location> locationId2Location = Maps.uniqueIndex(locationRecords, I_C_Location::getC_Location_ID);
		locationRecords.forEach(locationRecord -> allTableRecordRefs.add(TableRecordReference.of(locationRecord)));

		final ImmutableList<Integer> postalIds = CollectionUtils.extractDistinctElements(locationRecords, I_C_Location::getC_Postal_ID);
		final List<I_C_Postal> postalRecords = queryBL
				.createQueryBuilder(I_C_Postal.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addInArrayFilter(I_C_Postal.COLUMNNAME_C_Postal_ID, postalIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Postal> postalId2Postal = Maps.uniqueIndex(postalRecords, I_C_Postal::getC_Postal_ID);
		postalRecords.forEach(postalRecord -> allTableRecordRefs.add(TableRecordReference.of(postalRecord)));

		final ImmutableList<Integer> countryIds = CollectionUtils.extractDistinctElements(locationRecords, I_C_Location::getC_Country_ID);
		final List<I_C_Country> countryRecords = queryBL
				.createQueryBuilder(I_C_Country.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addInArrayFilter(I_C_Country.COLUMNNAME_C_Country_ID, countryIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Country> countryId2Country = Maps.uniqueIndex(countryRecords, I_C_Country::getC_Country_ID);
		countryRecords.forEach(countryRecord -> allTableRecordRefs.add(TableRecordReference.of(countryRecord)));

		final ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> bpBankAccounts = Services.get(IBPBankAccountDAO.class).getAllByBPartnerIds(bPartnerIds);

		final LogEntriesQuery logEntriesQuery = LogEntriesQuery.builder()
				.tableRecordReferences(allTableRecordRefs)
				.followLocationIdChanges(true)
				.build();
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> //
				recordRef2LogEntries = recordChangeLogRepository.getLogEntriesForRecordReferences(logEntriesQuery);

		return new CompositeRelatedRecords(
				bpartnerId2Users,
				bpartnerId2BPartnerLocations,
				locationId2Location,
				postalId2Postal,
				countryId2Country,
				bpBankAccounts,
				recordRef2LogEntries);
	}

	private static BPartner ofBPartnerRecord(
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> changeLogEntries)
	{
		final RecordChangeLog recordChangeLog = ChangeLogUtil.createBPartnerChangeLog(bpartnerRecord, changeLogEntries);

		return BPartner.builder()
				.active(bpartnerRecord.isActive())
				.value(bpartnerRecord.getValue())
				.companyName(trimBlankToNull(bpartnerRecord.getCompanyName()))
				.company(bpartnerRecord.isCompany())
				.externalId(ExternalId.ofOrNull(bpartnerRecord.getExternalId()))
				.globalId(bpartnerRecord.getGlobalId())
				.groupId(BPGroupId.ofRepoId(bpartnerRecord.getC_BP_Group_ID()))
				.language(Language.asLanguage(bpartnerRecord.getAD_Language()))
				.id(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.name(trimBlankToNull(bpartnerRecord.getName()))
				.name2(trimBlankToNull(bpartnerRecord.getName2()))
				.name3(trimBlankToNull(bpartnerRecord.getName3()))
				.parentId(BPartnerId.ofRepoIdOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				.phone(trimBlankToNull(bpartnerRecord.getPhone2()))
				.url(trimBlankToNull(bpartnerRecord.getURL()))
				.url2(trimBlankToNull(bpartnerRecord.getURL2()))
				.url3(trimBlankToNull(bpartnerRecord.getURL3()))
				.invoiceRule(InvoiceRule.ofNullableCode(bpartnerRecord.getInvoiceRule()))
				.vendor(bpartnerRecord.isVendor())
				.customer(bpartnerRecord.isCustomer())
				.vatId(trimBlankToNull(bpartnerRecord.getVATaxID()))
				.shipmentAllocationBestBeforePolicy(bpartnerRecord.getShipmentAllocation_BestBefore_Policy())
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(bpartnerRecord.getAD_Org_Mapping_ID()))
				//
				.changeLog(recordChangeLog)
				//
				.build();
	}

	private static ImmutableList<BPartnerLocation> ofLocationRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_C_BPartner_Location> bpartnerLocationRecords = relatedRecords
				.getBpartnerId2BPartnerLocations()
				.get(bpartnerId.getRepoId());

		final ImmutableList.Builder<BPartnerLocation> result = ImmutableList.builder();
		for (final I_C_BPartner_Location bPartnerLocationRecord : bpartnerLocationRecords)
		{
			final BPartnerLocation location = ofLocationRecord(bPartnerLocationRecord, relatedRecords);
			result.add(location);
		}

		return result.build();
	}

	private static BPartnerLocation ofLocationRecord(
			@NonNull final I_C_BPartner_Location bPartnerLocationRecord,
			@NonNull final CompositeRelatedRecords locationRelatedRecords)
	{
		final I_C_Location locationRecord = locationRelatedRecords.getLocationId2Location().get(bPartnerLocationRecord.getC_Location_ID());
		final I_C_Country countryRecord = locationRelatedRecords.getCountryId2Country().get(locationRecord.getC_Country_ID());

		final RecordChangeLog changeLog = ChangeLogUtil.createBPartnerLocationChangeLog(bPartnerLocationRecord, locationRelatedRecords);

		final BPartnerLocationBuilder location = BPartnerLocation.builder()
				.active(bPartnerLocationRecord.isActive())
				.name(trimBlankToNull(bPartnerLocationRecord.getName()))
				.bpartnerName(trimBlankToNull(bPartnerLocationRecord.getBPartnerName()))
				.locationType(BPartnerLocationType.builder()
						.billTo(bPartnerLocationRecord.isBillTo())
						.billToDefault(bPartnerLocationRecord.isBillToDefault())
						.shipTo(bPartnerLocationRecord.isShipTo())
						.shipToDefault(bPartnerLocationRecord.isShipToDefault())
						.build())
				.address1(trimBlankToNull(locationRecord.getAddress1()))
				.address2(trimBlankToNull(locationRecord.getAddress2()))
				.address3(trimBlankToNull(locationRecord.getAddress3()))
				.address4(trimBlankToNull(locationRecord.getAddress4()))
				.city(trimBlankToNull(locationRecord.getCity()))
				.countryCode(trimBlankToNull(countryRecord.getCountryCode()))
				.externalId(ExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(GLN.ofNullableString(bPartnerLocationRecord.getGLN()))
				.id(BPartnerLocationId.ofRepoId(bPartnerLocationRecord.getC_BPartner_ID(), bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.poBox(trimBlankToNull(locationRecord.getPOBox()))
				.postal(trimBlankToNull(locationRecord.getPostal()))
				.region(trimBlankToNull(locationRecord.getRegionName()))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(bPartnerLocationRecord.getAD_Org_Mapping_ID()))
				.changeLog(changeLog);

		if (locationRecord.getC_Postal_ID() > 0)
		{
			final I_C_Postal postalRecord = locationRelatedRecords.getPostalId2Postal().get(locationRecord.getC_Postal_ID());
			location.district(trimBlankToNull(postalRecord.getDistrict()));
		}

		return location.build();
	}

	private static ImmutableList<BPartnerContact> ofContactRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_AD_User> userRecords = relatedRecords
				.getBpartnerId2Users()
				.get(bpartnerId.getRepoId());

		final ImmutableList.Builder<BPartnerContact> result = ImmutableList.builder();
		for (final I_AD_User userRecord : userRecords)
		{
			final BPartnerContact contact = ofContactRecord(userRecord, relatedRecords);
			result.add(contact);
		}

		return result.build();
	}

	private static BPartnerContact ofContactRecord(
			@NonNull final I_AD_User contactRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final RecordChangeLog changeLog = ChangeLogUtil.createContactChangeLog(contactRecord, relatedRecords);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contactRecord.getC_BPartner_ID());
		return BPartnerContact.builder()
				.active(contactRecord.isActive())
				.id(BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID()))
				.contactType(BPartnerContactType.builder()
						.defaultContact(contactRecord.isDefaultContact())
						.billToDefault(contactRecord.isBillToContact_Default())
						.shipToDefault(contactRecord.isShipToContact_Default())
						.sales(contactRecord.isSalesContact())
						.salesDefault(contactRecord.isSalesContact_Default())
						.purchase(contactRecord.isPurchaseContact())
						.purchaseDefault(contactRecord.isPurchaseContact_Default())
						.subjectMatter(contactRecord.isSubjectMatterContact())
						.build())
				.email(trimBlankToNull(contactRecord.getEMail()))
				.externalId(ExternalId.ofOrNull(contactRecord.getExternalId()))
				.value(trimBlankToNull(contactRecord.getValue()))
				.firstName(trimBlankToNull(contactRecord.getFirstname()))
				.lastName(trimBlankToNull(contactRecord.getLastname()))
				.name(trimBlankToNull(contactRecord.getName()))
				.newsletter(contactRecord.isNewsletter())
				.phone(trimBlankToNull(contactRecord.getPhone()))
				.mobilePhone(trimBlankToNull(contactRecord.getMobilePhone()))
				.description(trimBlankToNull(contactRecord.getDescription()))
				.fax(trimBlankToNull(contactRecord.getFax()))
				.greetingId(GreetingId.ofRepoIdOrNull(contactRecord.getC_Greeting_ID()))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(contactRecord.getAD_Org_Mapping_ID()))
				.changeLog(changeLog)
				.build();
	}

	private Collection<? extends BPartnerBankAccount> ofBankAccountRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_C_BP_BankAccount> bpBankAccountrecords = relatedRecords
				.getBpartnerId2BankAccounts()
				.get(bpartnerId);
		final ImmutableList.Builder<BPartnerBankAccount> result = ImmutableList.builder();
		for (final I_C_BP_BankAccount bpBankAccountRecord : bpBankAccountrecords)
		{ // this used to be a stream..more compact, but much harder to debug
			final BPartnerBankAccount bPartnerBankAccount = ofBankAccountRecordOrNull(bpBankAccountRecord, relatedRecords);
			if (bPartnerBankAccount != null)
			{
				result.add(bPartnerBankAccount);
			}
		}
		return result.build();
	}

	/**
	 * IMPORTANT: please keep in sync with {@link de.metas.banking.api.IBPBankAccountDAO#deactivateIBANAccountsByBPartnerExcept(BPartnerId, Collection)}
	 */
	private static BPartnerBankAccount ofBankAccountRecordOrNull(
			@NonNull final I_C_BP_BankAccount bankAccountRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final String iban = bankAccountRecord.getIBAN();
		if (iban == null)
		{
			logger.debug("ofBankAccountRecordOrNull: Return null for {} because IBAN is not set", bankAccountRecord);
			return null;
		}

		final RecordChangeLog changeLog = ChangeLogUtil.createBankAccountChangeLog(bankAccountRecord, relatedRecords);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankAccountRecord.getC_BPartner_ID());

		return BPartnerBankAccount.builder()
				.id(BPartnerBankAccountId.ofRepoId(bpartnerId, bankAccountRecord.getC_BP_BankAccount_ID()))
				.active(bankAccountRecord.isActive())
				.iban(iban)
				.currencyId(CurrencyId.ofRepoId(bankAccountRecord.getC_Currency_ID()))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(bankAccountRecord.getAD_Org_Mapping_ID()))
				.changeLog(changeLog)
				.build();
	}
}
