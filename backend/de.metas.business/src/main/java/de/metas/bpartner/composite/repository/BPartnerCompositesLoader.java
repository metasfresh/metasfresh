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
import de.metas.bpartner.composite.BPartnerLocationAddressPart;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.marketing.base.model.CampaignId;
import de.metas.common.util.StringUtils;
import de.metas.common.util.time.SystemTime;
import de.metas.greeting.GreetingId;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

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

		final ImmutableMap.Builder<BPartnerId, BPartnerComposite> result = ImmutableMap.builder();

		for (final I_C_BPartner bPartnerRecord : bPartnerRecords)
		{
			final BPartnerId id = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

			final BPartner bpartner = ofBPartnerRecord(bPartnerRecord, relatedRecords.getRecordRef2LogEntries());

			final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
					.orgId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID()))
					.bpartner(bpartner)
					.contacts(ofContactRecords(id, relatedRecords))
					.locations(ofBPartnerLocationRecords(id, relatedRecords))
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

		final ImmutableListMultimap<BPartnerId, I_AD_User> bpartnerId2Users = retrieveBPartnerContactRecords(bPartnerIds);
		bpartnerId2Users.forEach((bpartnerId, contactRecord) -> allTableRecordRefs.add(TableRecordReference.of(contactRecord)));

		final ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> bpartnerLocationRecords = retrieveBPartnerLocationRecords(bPartnerIds);
		bpartnerLocationRecords.forEach((bpartnerId, bPartnerLocationRecord) -> allTableRecordRefs.add(TableRecordReference.of(bPartnerLocationRecord)));

		final ImmutableMap<LocationId, I_C_Location> locationRecords = retrieveLocationRecords(bpartnerLocationRecords.values());
		locationRecords.forEach((locationId, locationRecord) -> allTableRecordRefs.add(TableRecordReference.of(locationRecord)));

		final ImmutableMap<PostalId, I_C_Postal> postalRecords = retrievePostals(locationRecords.values());
		postalRecords.forEach((postalId, postalRecord) -> allTableRecordRefs.add(TableRecordReference.of(postalRecord)));

		final ImmutableSet<CountryId> countryIds = extractCountryIds(locationRecords.values());
		countryIds.forEach(countryId -> allTableRecordRefs.add(TableRecordReference.of(I_C_Country.Table_Name, countryId)));

		final ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> bpBankAccounts = bpBankAccountDAO.getAllByBPartnerIds(bPartnerIds);

		final LogEntriesQuery logEntriesQuery = LogEntriesQuery.builder()
				.tableRecordReferences(allTableRecordRefs)
				.followLocationIdChanges(true)
				.build();
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> //
				recordRef2LogEntries = recordChangeLogRepository.getLogEntriesForRecordReferences(logEntriesQuery);

		return CompositeRelatedRecords.builder()
				.bpartnerId2Users(bpartnerId2Users)
				.bpartnerId2BPartnerLocations(bpartnerLocationRecords)
				.locationId2Location(locationRecords)
				.postalId2Postal(postalRecords)
				.bpartnerId2BankAccounts(bpBankAccounts)
				.recordRef2LogEntries(recordRef2LogEntries)
				.build();
	}

	private ImmutableListMultimap<BPartnerId, I_AD_User> retrieveBPartnerContactRecords(final @NonNull Collection<BPartnerId> bpartnerIds)
	{
		final List<I_AD_User> contactRecords = queryBL
				.createQueryBuilder(I_AD_User.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.list();
		return Multimaps.index(contactRecords, contactRecord -> BPartnerId.ofRepoId(contactRecord.getC_BPartner_ID()));
	}

	private ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> retrieveBPartnerLocationRecords(final @NonNull Collection<BPartnerId> bpartnerIds)
	{
		final List<I_C_BPartner_Location> bpartnerLocationRecords = queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				// .addOnlyActiveRecordsFilter() also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.list();
		return Multimaps.index(bpartnerLocationRecords, bpartnerLocationRecord -> BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()));
	}

	private ImmutableMap<LocationId, I_C_Location> retrieveLocationRecords(final Collection<I_C_BPartner_Location> bpartnerLocationRecords)
	{
		final ImmutableSet<LocationId> locationIds = extractLocationIds(bpartnerLocationRecords);
		final List<I_C_Location> locationRecords = locationDAO.getByIds(locationIds);
		return Maps.uniqueIndex(locationRecords, location -> LocationId.ofRepoId(location.getC_Location_ID()));
	}

	private static ImmutableSet<LocationId> extractLocationIds(final Collection<I_C_BPartner_Location> bpartnerLocationRecords)
	{
		return bpartnerLocationRecords.stream()
				.map(I_C_BPartner_Location::getC_Location_ID)
				.distinct()
				.map(LocationId::ofRepoIdOrNull)
				.filter(locationId -> {
					if (locationId == null)
					{
						throw new AdempiereException("C_BPartner_Location.C_Location_ID is mandatory: " + bpartnerLocationRecords); // useful when unit-testing
					}
					else
					{
						return true;
					}
				})
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<CountryId> extractCountryIds(final Collection<I_C_Location> locationRecords)
	{
		return locationRecords
				.stream()
				.map(I_C_Location::getC_Country_ID)
				.distinct()
				.map(CountryId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableMap<PostalId, I_C_Postal> retrievePostals(final Collection<I_C_Location> locationRecords)
	{
		final ImmutableSet<PostalId> postalIds = locationRecords.stream()
				.map(I_C_Location::getC_Postal_ID)
				.distinct()
				.map(PostalId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Postal> postalRecords = locationDAO.getPostalByIds(postalIds);
		return Maps.uniqueIndex(postalRecords, postalRecord -> PostalId.ofRepoId(postalRecord.getC_Postal_ID()));
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
				.greetingId(GreetingId.ofRepoIdOrNull(bpartnerRecord.getC_Greeting_ID()))
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
				.memo(bpartnerRecord.getMemo())
				.customerPaymentTermId(PaymentTermId.ofRepoIdOrNull(bpartnerRecord.getC_PaymentTerm_ID()))
				.customerPricingSystemId(PricingSystemId.ofRepoIdOrNull(bpartnerRecord.getM_PricingSystem_ID()))
				.vendorPaymentTermId(PaymentTermId.ofRepoIdOrNull(bpartnerRecord.getPO_PaymentTerm_ID()))
				.vendorPricingSystemId(PricingSystemId.ofRepoIdOrNull(bpartnerRecord.getPO_PricingSystem_ID()))
				//
				.excludeFromPromotions(bpartnerRecord.isExcludeFromPromotions())
				.referrer(bpartnerRecord.getReferrer())
				.campaignId(CampaignId.ofRepoIdOrNull(bpartnerRecord.getMKTG_Campaign_ID()))
				//
				.changeLog(recordChangeLog)
				//
				.build();
	}

	private ImmutableList<BPartnerLocation> ofBPartnerLocationRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		return relatedRecords.getBPartnerLocationsByBPartnerId(bpartnerId)
				.stream()
				.map(bpartnerLocationRecord -> ofBPartnerLocationRecord(bpartnerLocationRecord, relatedRecords))
				.collect(ImmutableList.toImmutableList());
	}

	private BPartnerLocation ofBPartnerLocationRecord(
			@NonNull final I_C_BPartner_Location bPartnerLocationRecord,
			@NonNull final CompositeRelatedRecords locationRelatedRecords)
	{
		final BPartnerLocationAddressPart address = retrieveBPartnerLocationAddressPart(
				LocationId.ofRepoId(bPartnerLocationRecord.getC_Location_ID()),
				locationRelatedRecords);

		final RecordChangeLog changeLog = ChangeLogUtil.createBPartnerLocationChangeLog(bPartnerLocationRecord, locationRelatedRecords);

		final BPartnerLocation bpartnerLocation = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bPartnerLocationRecord.getC_BPartner_ID(), bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.externalId(ExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(GLN.ofNullableString(bPartnerLocationRecord.getGLN()))
				.active(bPartnerLocationRecord.isActive())
				.name(trimBlankToNull(bPartnerLocationRecord.getName()))
				.bpartnerName(trimBlankToNull(bPartnerLocationRecord.getBPartnerName()))
				.locationType(extractBPartnerLocationType(bPartnerLocationRecord))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(bPartnerLocationRecord.getAD_Org_Mapping_ID()))
				.changeLog(changeLog)
				.build();

		bpartnerLocation.setFromAddress(address);

		return bpartnerLocation;
	}

	static BPartnerLocationType extractBPartnerLocationType(final @NonNull I_C_BPartner_Location bpartnerLocationRecord)
	{
		return BPartnerLocationType.builder()
				.billTo(bpartnerLocationRecord.isBillTo())
				.billToDefault(bpartnerLocationRecord.isBillToDefault())
				.shipTo(bpartnerLocationRecord.isShipTo())
				.shipToDefault(bpartnerLocationRecord.isShipToDefault())
				.build();
	}

	private BPartnerLocationAddressPart retrieveBPartnerLocationAddressPart(
			@NonNull final LocationId fromLocationRecordId,
			@NonNull final CompositeRelatedRecords locationRelatedRecords)
	{
		final I_C_Location locationRecord = locationRelatedRecords.getLocationById(fromLocationRecordId)
				.orElseGet(() -> locationDAO.getById(fromLocationRecordId));

		return toBPartnerLocationAddressPart(locationRecord, locationRelatedRecords, locationDAO, countryDAO);
	}

	static BPartnerLocationAddressPart toBPartnerLocationAddressPart(
			@NonNull final I_C_Location locationRecord,
			@NonNull final ILocationDAO locationDAO,
			@NonNull final ICountryDAO countryDAO)
	{
		return toBPartnerLocationAddressPart(locationRecord, CompositeRelatedRecords.builder().build(), locationDAO, countryDAO);
	}

	private static BPartnerLocationAddressPart toBPartnerLocationAddressPart(
			@NonNull final I_C_Location locationRecord,
			@NonNull final CompositeRelatedRecords locationRelatedRecords,
			@NonNull final ILocationDAO locationDAO,
			@NonNull final ICountryDAO countryDAO)
	{
		final CountryId countryId = CountryId.ofRepoId(locationRecord.getC_Country_ID());
		final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(countryId);

		final PostalId postalId = PostalId.ofRepoIdOrNull(locationRecord.getC_Postal_ID());
		final String district;
		if (postalId != null)
		{
			final I_C_Postal postal = locationRelatedRecords.getPostalById(postalId)
					.orElseGet(() -> locationDAO.getPostalById(postalId));
			district = postal.getDistrict();
		}
		else
		{
			district = null;
		}

		return BPartnerLocationAddressPart.builder()
				.existingLocationId(LocationId.ofRepoId(locationRecord.getC_Location_ID()))
				.address1(trimBlankToNull(locationRecord.getAddress1()))
				.address2(trimBlankToNull(locationRecord.getAddress2()))
				.address3(trimBlankToNull(locationRecord.getAddress3()))
				.address4(trimBlankToNull(locationRecord.getAddress4()))
				.city(trimBlankToNull(locationRecord.getCity()))
				.countryCode(countryCode)
				.poBox(trimBlankToNull(locationRecord.getPOBox()))
				.postal(trimBlankToNull(locationRecord.getPostal()))
				.region(trimBlankToNull(locationRecord.getRegionName()))
				.district(district)
				.build();
	}

	private static ImmutableList<BPartnerContact> ofContactRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		return relatedRecords.getContactsByBPartnerId(bpartnerId)
				.stream()
				.map(contactRecord -> ofContactRecord(contactRecord, relatedRecords))
				.collect(ImmutableList.toImmutableList());
	}

	private static BPartnerContact ofContactRecord(
			@NonNull final I_AD_User contactRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final RecordChangeLog changeLog = ChangeLogUtil.createContactChangeLog(contactRecord, relatedRecords);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(contactRecord.getC_BPartner_ID());
		return BPartnerContact.builder()
				.active(contactRecord.isActive())
				.id(BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID()))
				.contactType(extractBPartnerContactType(contactRecord))
				.email(trimBlankToNull(contactRecord.getEMail()))
				.externalId(ExternalId.ofOrNull(contactRecord.getExternalId()))
				.value(trimBlankToNull(contactRecord.getValue()))
				.firstName(trimBlankToNull(contactRecord.getFirstname()))
				.lastName(trimBlankToNull(contactRecord.getLastname()))
				.name(trimBlankToNull(contactRecord.getName()))
				.newsletter(contactRecord.isNewsletter())
				.membershipContact(contactRecord.isMembershipContact())
				.subjectMatterContact(contactRecord.isSubjectMatterContact())
				.invoiceEmailEnabled(StringUtils.toBoolean(contactRecord.getIsInvoiceEmailEnabled(), null))
				.phone(trimBlankToNull(contactRecord.getPhone()))
				.mobilePhone(trimBlankToNull(contactRecord.getMobilePhone()))
				.description(trimBlankToNull(contactRecord.getDescription()))
				.fax(trimBlankToNull(contactRecord.getFax()))
				.greetingId(GreetingId.ofRepoIdOrNull(contactRecord.getC_Greeting_ID()))
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(contactRecord.getAD_Org_Mapping_ID()))
				.changeLog(changeLog)
				.birthday(TimeUtil.asLocalDate(contactRecord.getBirthday(), SystemTime.zoneId()))
				.bPartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(contactRecord.getC_BPartner_ID(), contactRecord.getC_BPartner_Location_ID()))
				.build();
	}

	static BPartnerContactType extractBPartnerContactType(final @NonNull I_AD_User contactRecord)
	{
		return BPartnerContactType.builder()
				.defaultContact(contactRecord.isDefaultContact())
				.billToDefault(contactRecord.isBillToContact_Default())
				.shipToDefault(contactRecord.isShipToContact_Default())
				.sales(contactRecord.isSalesContact())
				.salesDefault(contactRecord.isSalesContact_Default())
				.purchase(contactRecord.isPurchaseContact())
				.purchaseDefault(contactRecord.isPurchaseContact_Default())
				.build();
	}

	private Collection<? extends BPartnerBankAccount> ofBankAccountRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_C_BP_BankAccount> bpBankAccountRecords = relatedRecords.getBankAccountsByBPartnerId(bpartnerId);
		final ImmutableList.Builder<BPartnerBankAccount> result = ImmutableList.builder();
		for (final I_C_BP_BankAccount bpBankAccountRecord : bpBankAccountRecords)
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
	@Nullable
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
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bankAccountRecord.getC_BPartner_ID());

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
