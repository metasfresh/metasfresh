package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.IBPBankAccountDAO;
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
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.marketing.base.model.CampaignId;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.location.impl.PostalQueryFilter;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.util.Check.isBlank;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.X_AD_User.ISINVOICEEMAILENABLED_No;
import static org.compiere.model.X_AD_User.ISINVOICEEMAILENABLED_Yes;

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

final class BPartnerCompositeSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerBL bpartnerBL;
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IBPBankAccountDAO bpBankAccountsDAO = Services.get(IBPBankAccountDAO.class);

	BPartnerCompositeSaver(
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.bpartnerBL = bpartnerBL;
	}

	public void save(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final ImmutableList<ITranslatableString> validateResult = bpartnerComposite.validate();

		if (!validateResult.isEmpty())
		{
			final String errors = validateResult
					.stream()
					.map(trl -> trl.translate(Env.getADLanguageOrBaseLanguage()))
					.collect(Collectors.joining("\n"));

			throw new AdempiereException("Can't save an invalid bpartnerComposite")
					.appendParametersToMessage()
					.setParameter("errors", errors)
					.setParameter("bpartnerComposite", bpartnerComposite);
		}

		final BPartner bpartner = bpartnerComposite.getBpartner();
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, bpartner.getId()))
		{
			saveBPartner(bpartner, bpartnerComposite.getOrgId());
		}
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, bpartner.getId()))
		{
			saveBPartnerLocations(bpartner.getId(), bpartnerComposite.getLocations(), bpartnerComposite.getOrgId());

			saveBPartnerContacts(bpartner.getId(), bpartnerComposite.getContacts(), bpartnerComposite.getOrgId());

			saveBPartnerBankAccounts(bpartner.getId(), bpartnerComposite.getBankAccounts(), bpartnerComposite.getOrgId());
		}
	}

	private void saveBPartner(@NonNull final BPartner bpartner, @Nullable final OrgId orgId)
	{
		final I_C_BPartner bpartnerRecord = loadOrNew(bpartner.getId(), I_C_BPartner.class);
		bpartnerRecord.setIsActive(bpartner.isActive());

		if (orgId != null)
		{
			bpartnerRecord.setAD_Org_ID(orgId.getRepoId());
		}

		// companyName
		if (bpartner.isCompany()
				|| !isBlank(bpartner.getCompanyName())) // kept this logic here for legacy purpose
		{
			bpartnerRecord.setIsCompany(true);
			bpartnerRecord.setCompanyName(bpartner.getCompanyName().trim());
		}
		else
		{
			bpartnerRecord.setIsCompany(false);
			bpartnerRecord.setCompanyName(null);
		}

		bpartnerRecord.setExternalId(ExternalId.toValue(bpartner.getExternalId()));

		// load within trx and set to the record. otherwise MBPartner.beforeSave() might fail
		final I_C_BP_Group bpGroupRecord = load(bpartner.getGroupId().getRepoId(), I_C_BP_Group.class); // since we validated, we know it's set
		bpartnerRecord.setC_BP_Group(bpGroupRecord);
		// bpartner.getId() used only for lookup

		bpartnerRecord.setAD_Language(Language.asLanguageStringOrNull(bpartner.getLanguage()));
		bpartnerRecord.setName(bpartner.getName());
		bpartnerRecord.setName2(bpartner.getName2());
		bpartnerRecord.setName3(bpartner.getName3());
		bpartnerRecord.setC_Greeting_ID(GreetingId.toRepoId(bpartner.getGreetingId()));

		bpartnerRecord.setBPartner_Parent_ID(BPartnerId.toRepoId(bpartner.getParentId()));
		bpartnerRecord.setPhone2(bpartner.getPhone());
		bpartnerRecord.setURL(bpartner.getUrl());
		bpartnerRecord.setURL2(bpartner.getUrl2());
		bpartnerRecord.setURL3(bpartner.getUrl3());

		bpartnerRecord.setIsVendor(bpartner.isVendor());
		bpartnerRecord.setIsCustomer(bpartner.isCustomer());
		if (bpartner.getInvoiceRule() != null)
		{
			bpartnerRecord.setInvoiceRule(bpartner.getInvoiceRule().getCode());
		}

		bpartnerRecord.setVATaxID(bpartner.getVatId());

		if (!isBlank(bpartner.getValue()))
		{
			bpartnerRecord.setValue(bpartner.getValue());
		}
		bpartnerRecord.setGlobalId(bpartner.getGlobalId());
		bpartnerRecord.setMemo(bpartner.getMemo());

		if (bpartner.getCustomerPricingSystemId() != null)
		{
			bpartnerRecord.setM_PricingSystem_ID(bpartner.getCustomerPricingSystemId().getRepoId());
		}
		if (bpartner.getCustomerPaymentTermId() != null)
		{
			bpartnerRecord.setC_PaymentTerm_ID(bpartner.getCustomerPaymentTermId().getRepoId());
		}

		if (bpartner.getVendorPricingSystemId() != null)
		{
			bpartnerRecord.setPO_PricingSystem_ID(bpartner.getVendorPricingSystemId().getRepoId());
		}
		if (bpartner.getVendorPaymentTermId() != null)
		{
			bpartnerRecord.setPO_PaymentTerm_ID(bpartner.getVendorPaymentTermId().getRepoId());
		}

		bpartnerRecord.setExcludeFromPromotions(bpartner.isExcludeFromPromotions());
		bpartnerRecord.setReferrer(bpartner.getReferrer());
		bpartnerRecord.setMKTG_Campaign_ID(CampaignId.toRepoId(bpartner.getCampaignId()));

		assertCanCreateOrUpdate(bpartnerRecord);
		saveRecord(bpartnerRecord);

		//
		// Update from saved record
		bpartner.setId(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()));
		bpartner.setValue(bpartnerRecord.getValue());
		bpartner.setCompany(bpartnerRecord.isCompany());
	}

	private void saveBPartnerLocations(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerLocation> bpartnerLocations,
			@Nullable final OrgId orgId)
	{
		final ArrayList<BPartnerLocationId> savedBPartnerLocationIds = new ArrayList<>();
		for (final BPartnerLocation bPartnerLocation : bpartnerLocations)
		{
			saveBPartnerLocation(bpartnerId, bPartnerLocation, orgId);
			savedBPartnerLocationIds.add(bPartnerLocation.getId());
		}

		// set location records that we don't have in 'bpartnerLocations' to inactive
		final ICompositeQueryUpdater<I_C_BPartner_Location> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_C_BPartner_Location.class)
				.addSetColumnValue(I_C_BPartner_Location.COLUMNNAME_IsActive, false);
		queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_BPartner_Location.COLUMN_C_BPartner_Location_ID, savedBPartnerLocationIds)
				.create()
				.update(columnUpdater);
	}

	private void saveBPartnerLocation(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerLocation bpartnerLocation,
			@Nullable final OrgId orgId)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BPartner_Location.Table_Name, bpartnerLocation.getId()))
		{
			final I_C_BPartner_Location bpartnerLocationRecord = loadOrNew(bpartnerLocation.getId(), I_C_BPartner_Location.class);
			if (orgId != null)
			{
				bpartnerLocationRecord.setAD_Org_ID(orgId.getRepoId());
			}

			bpartnerLocationRecord.setExternalId(ExternalId.toValue(bpartnerLocation.getExternalId()));
			bpartnerLocationRecord.setGLN(GLN.toCode(bpartnerLocation.getGln()));
			// bpartnerLocation.getId() // id is only for lookup and won't be updated later

			bpartnerLocationRecord.setIsActive(bpartnerLocation.isActive());
			bpartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
			bpartnerLocationRecord.setName(bpartnerLocation.getName());
			bpartnerLocationRecord.setBPartnerName(bpartnerLocation.getBpartnerName());

			final BPartnerLocationType locationType = bpartnerLocation.getLocationType();
			if (locationType != null)
			{
				locationType.getBillTo().ifPresent(bpartnerLocationRecord::setIsBillTo);
				locationType.getBillToDefault().ifPresent(bpartnerLocationRecord::setIsBillToDefault);
				locationType.getShipTo().ifPresent(bpartnerLocationRecord::setIsShipTo);
				locationType.getShipToDefault().ifPresent(bpartnerLocationRecord::setIsShipToDefault);
			}

			final BPartnerLocationAddressPart address = saveLocationRecord(bpartnerLocation);
			bpartnerLocationRecord.setC_Location_ID(address.getExistingLocationId().getRepoId());

			bpartnerBL.setAddress(bpartnerLocationRecord);

			assertCanCreateOrUpdate(bpartnerLocationRecord);

			bpartnerLocationRecord.setAD_Org_Mapping_ID(OrgMappingId.toRepoId(bpartnerLocation.getOrgMappingId()));
			saveRecord(bpartnerLocationRecord);

			//
			// Update model from saved record:
			bpartnerLocation.setId(BPartnerLocationId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID(), bpartnerLocationRecord.getC_BPartner_Location_ID()));
			bpartnerLocation.setLocationType(BPartnerCompositesLoader.extractBPartnerLocationType(bpartnerLocationRecord));
			bpartnerLocation.setFromAddress(address);
		}
	}

	private BPartnerLocationAddressPart saveLocationRecord(@NonNull final BPartnerLocation bpartnerLocation)
	{
		if (bpartnerLocation.isAddressSpecifiedByExistingLocationIdOnly())
		{
			final LocationId existingLocationId = Check.assumeNotNull(
					bpartnerLocation.getExistingLocationId(),
					"existingLocationId not null: {}", bpartnerLocation);

			final I_C_Location existingLocationRecord = locationDAO.getById(existingLocationId);
			return BPartnerCompositesLoader.toBPartnerLocationAddressPart(
					existingLocationRecord,
					locationDAO,
					countryDAO);
		}

		final BPartnerLocationAddressPart oldAddress;
		if (bpartnerLocation.getExistingLocationId() != null)
		{
			final I_C_Location existingLocationRecord = locationDAO.getById(bpartnerLocation.getExistingLocationId());
			oldAddress = BPartnerCompositesLoader.toBPartnerLocationAddressPart(
					existingLocationRecord,
					locationDAO,
					countryDAO);
		}
		else
		{
			oldAddress = null;
		}

		BPartnerLocationAddressPart newAddress = bpartnerLocation.toAddress();
		if (oldAddress == null || !BPartnerLocationAddressPart.equals(oldAddress, newAddress))
		{
			newAddress = createNewLocationRecord(newAddress);
		}

		return newAddress;
	}

	private BPartnerLocationAddressPart createNewLocationRecord(@NonNull final BPartnerLocationAddressPart address)
	{
		final LocationCreateRequest.LocationCreateRequestBuilder requestBuilder = LocationCreateRequest.builder()
				.address1(address.getAddress1())
				.address2(address.getAddress2())
				.address3(address.getAddress3())
				.address4(address.getAddress4());

		if (address.getCountryCode() != null && !isBlank(address.getCountryCode()))
		{
			final CountryId countryId = countryDAO.getCountryIdByCountryCode(address.getCountryCode());
			requestBuilder.countryId(countryId);
		}

		boolean postalDataSetFromPostalRecord = false;
		final I_C_Postal postalRecord = retrievePostal(address);
		if (postalRecord != null)
		{
			requestBuilder.countryId(CountryId.ofRepoId(postalRecord.getC_Country_ID()));
			requestBuilder.postalId(PostalId.ofRepoId(postalRecord.getC_Postal_ID()));
			requestBuilder.postal(postalRecord.getPostal());
			requestBuilder.city(postalRecord.getCity());
			requestBuilder.regionName(postalRecord.getRegionName());

			postalDataSetFromPostalRecord = true;
		}

		if (!postalDataSetFromPostalRecord)
		{
			requestBuilder.postal(address.getPostal());
			requestBuilder.city(address.getCity());
			requestBuilder.regionName(address.getRegion());
		}

		requestBuilder.poBox(address.getPoBox());

		final LocationId newLocationId = locationDAO.createLocation(requestBuilder.build());
		return address.withExistingLocationId(newLocationId);
	}

	@Nullable
	private I_C_Postal retrievePostal(final @NonNull BPartnerLocationAddressPart address)
	{
		final String postalCode = StringUtils.trimBlankToNull(address.getPostal());
		if (postalCode == null)
		{
			return null;
		}

		final CountryId countryId = StringUtils.trimBlankToOptional(address.getCountryCode())
				.map(countryDAO::getCountryIdByCountryCode)
				.orElse(null);

		final IQueryBuilder<I_C_Postal> postalQueryBuilder = queryBL
				.createQueryBuilder(I_C_Postal.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.filter(PostalQueryFilter.of(postalCode));
		if (!isBlank(address.getDistrict()))
		{
			postalQueryBuilder.addEqualsFilter(I_C_Postal.COLUMN_District, address.getDistrict());
		}
		if (countryId != null)
		{
			postalQueryBuilder.addEqualsFilter(I_C_Postal.COLUMN_C_Country_ID, countryId);
		}

		postalQueryBuilder.orderBy().addColumn(I_C_Postal.COLUMNNAME_District, Direction.Ascending, Nulls.First);

		final List<I_C_Postal> postalRecords = postalQueryBuilder
				.create()
				.list();

		if (postalRecords.isEmpty())
		{
			return null;
		}
		else if (postalRecords.size() == 1)
		{
			return postalRecords.get(0);
		}
		else if (countryId != null)
		{
			return postalRecords
					.stream()
					.filter(r -> countryId.equalsToRepoId(r.getC_Country_ID()))
					.findFirst()
					.orElse(null);
		}
		else
		{
			return null;
		}
	}

	private void saveBPartnerContacts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerContact> contacts,
			@Nullable final OrgId orgId)
	{
		final ArrayList<BPartnerContactId> savedBPartnerContactIds = new ArrayList<>();
		for (final BPartnerContact bpartnerContact : contacts)
		{
			saveBPartnerContact(bpartnerId, bpartnerContact, orgId);
			savedBPartnerContactIds.add(bpartnerContact.getId());
		}

		final ICompositeQueryUpdater<I_AD_User> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_AD_User.class)
				.addSetColumnValue(I_AD_User.COLUMNNAME_IsActive, false);
		queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_AD_User.COLUMNNAME_AD_User_ID, savedBPartnerContactIds)
				.create()
				.update(columnUpdater);
	}

	private void saveBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerContact bpartnerContact,
			@Nullable final OrgId orgId)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_AD_User.Table_Name, bpartnerContact.getId()))
		{
			final I_AD_User bpartnerContactRecord = loadOrNew(bpartnerContact.getId(), I_AD_User.class);

			if (orgId != null)
			{
				bpartnerContactRecord.setAD_Org_ID(orgId.getRepoId());
			}
			bpartnerContactRecord.setExternalId(ExternalId.toValue(bpartnerContact.getExternalId()));
			bpartnerContactRecord.setIsActive(bpartnerContact.isActive());
			bpartnerContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
			bpartnerContactRecord.setName(bpartnerContact.getName());
			bpartnerContactRecord.setEMail(bpartnerContact.getEmail());

			bpartnerContactRecord.setFirstname(bpartnerContact.getFirstName());
			bpartnerContactRecord.setLastname(bpartnerContact.getLastName());

			bpartnerContactRecord.setIsNewsletter(bpartnerContact.isNewsletter());
			bpartnerContactRecord.setIsMembershipContact(bpartnerContact.isMembershipContact());
			bpartnerContactRecord.setIsSubjectMatterContact(bpartnerContact.isSubjectMatterContact());

			final BPartnerContactType contactType = bpartnerContact.getContactType();
			if (contactType != null)
			{
				contactType.getDefaultContact().ifPresent(bpartnerContactRecord::setIsDefaultContact);
				contactType.getBillToDefault().ifPresent(bpartnerContactRecord::setIsBillToContact_Default);
				contactType.getShipToDefault().ifPresent(bpartnerContactRecord::setIsShipToContact_Default);
				contactType.getSales().ifPresent(bpartnerContactRecord::setIsSalesContact);
				contactType.getSalesDefault().ifPresent(bpartnerContactRecord::setIsSalesContact_Default);
				contactType.getPurchase().ifPresent(bpartnerContactRecord::setIsPurchaseContact);
				contactType.getPurchaseDefault().ifPresent(bpartnerContactRecord::setIsPurchaseContact_Default);
			}

			bpartnerContactRecord.setDescription(bpartnerContact.getDescription());

			bpartnerContactRecord.setPhone(bpartnerContact.getPhone());
			bpartnerContactRecord.setFax(bpartnerContact.getFax());
			bpartnerContactRecord.setMobilePhone(bpartnerContact.getMobilePhone());

			String invoiceEmailEnabled = null;

			if (bpartnerContact.getInvoiceEmailEnabled() != null)
			{
				invoiceEmailEnabled = bpartnerContact.getInvoiceEmailEnabled() ? ISINVOICEEMAILENABLED_Yes : ISINVOICEEMAILENABLED_No;
			}

			bpartnerContactRecord.setIsInvoiceEmailEnabled(invoiceEmailEnabled);

			bpartnerContactRecord.setC_Greeting_ID(GreetingId.toRepoIdOr(bpartnerContact.getGreetingId(), 0));

			bpartnerContactRecord.setAD_Org_Mapping_ID(OrgMappingId.toRepoId(bpartnerContact.getOrgMappingId()));

			bpartnerContactRecord.setBirthday(TimeUtil.asTimestamp(bpartnerContact.getBirthday()));
			bpartnerContactRecord.setC_BPartner_Location_ID(bpartnerContact.getBPartnerLocationId() != null ? bpartnerContact.getBPartnerLocationId().getRepoId() : -1);

			assertCanCreateOrUpdate(bpartnerContactRecord);
			saveRecord(bpartnerContactRecord);

			//
			// Update model from saved record:
			bpartnerContact.setId(BPartnerContactId.ofRepoId(bpartnerId, bpartnerContactRecord.getAD_User_ID()));
			bpartnerContact.setContactType(BPartnerCompositesLoader.extractBPartnerContactType(bpartnerContactRecord));
		}
	}

	private void saveBPartnerBankAccounts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerBankAccount> bankAccounts,
			@Nullable final OrgId orgId)
	{
		final ArrayList<BPartnerBankAccountId> savedBPBankAccountIds = new ArrayList<>();
		for (final BPartnerBankAccount bankAccount : bankAccounts)
		{
			saveBPartnerBankAccount(bpartnerId, bankAccount, orgId);
			savedBPBankAccountIds.add(bankAccount.getId());
		}

		bpBankAccountsDAO.deactivateIBANAccountsByBPartnerExcept(bpartnerId, savedBPBankAccountIds);
	}

	private void saveBPartnerBankAccount(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerBankAccount bankAccount,
			@Nullable final OrgId orgId)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_BP_BankAccount.Table_Name, bankAccount.getId()))
		{

			final I_C_BP_BankAccount record = loadOrNew(bankAccount.getId(), I_C_BP_BankAccount.class);

			if (orgId != null)
			{
				record.setAD_Org_ID(orgId.getRepoId());
			}
			record.setC_BPartner_ID(bpartnerId.getRepoId());

			record.setIBAN(bankAccount.getIban());
			record.setC_Currency_ID(bankAccount.getCurrencyId().getRepoId());
			record.setIsActive(bankAccount.isActive());

			record.setAD_Org_Mapping_ID(OrgMappingId.toRepoId(bankAccount.getOrgMappingId()));

			assertCanCreateOrUpdate(record);
			saveRecord(record);

			final BPartnerBankAccountId id = BPartnerBankAccountId.ofRepoId(bpartnerId, record.getC_BP_BankAccount_ID());

			bankAccount.setId(id);
		}
	}

	private void assertCanCreateOrUpdate(@NonNull final Object record)
	{
		if (Adempiere.isUnitTestMode())
		{
			return;
		}
		PermissionServiceFactories
				.currentContext()
				.createPermissionService()
				.assertCanCreateOrUpdate(record);
	}

}
