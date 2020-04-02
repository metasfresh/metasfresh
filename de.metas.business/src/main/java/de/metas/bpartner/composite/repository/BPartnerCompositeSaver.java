package de.metas.bpartner.composite.repository;

import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.impl.PostalQueryFilter;
import de.metas.security.PermissionServiceFactories;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
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

final class BPartnerCompositeSaver
{
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
		saveBPartner(bpartner);

		saveBPartnerLocations(bpartner.getId(), bpartnerComposite.getLocations());

		saveBPartnerContacts(bpartner.getId(), bpartnerComposite.getContacts());

		saveBPartnerBankAccounts(bpartner.getId(), bpartnerComposite.getBankAccounts());
	}

	private void saveBPartner(@NonNull final BPartner bpartner)
	{
		final I_C_BPartner bpartnerRecord = loadOrNew(bpartner.getId(), I_C_BPartner.class);
		bpartnerRecord.setIsActive(bpartner.isActive());

		// companyName
		if (isEmpty(bpartner.getCompanyName(), true))
		{
			bpartnerRecord.setIsCompany(false);
			bpartnerRecord.setCompanyName(null);
		}
		else
		{
			bpartnerRecord.setIsCompany(true);
			bpartnerRecord.setCompanyName(bpartner.getCompanyName().trim());
		}

		bpartnerRecord.setExternalId(ExternalId.toValue(bpartner.getExternalId()));
		bpartnerRecord.setC_BP_Group_ID(bpartner.getGroupId().getRepoId()); // since we validated, we know it's set
		// bpartner.getId() used only for lookup

		bpartnerRecord.setAD_Language(Language.asLanguageStringOrNull(bpartner.getLanguage()));
		bpartnerRecord.setName(bpartner.getName());
		bpartnerRecord.setName2(bpartner.getName2());
		bpartnerRecord.setName3(bpartner.getName3());

		bpartnerRecord.setBPartner_Parent_ID(BPartnerId.toRepoId(bpartner.getParentId()));
		bpartnerRecord.setPhone2(bpartner.getPhone());
		bpartnerRecord.setURL(bpartner.getUrl());
		bpartnerRecord.setURL2(bpartner.getUrl2());
		bpartnerRecord.setURL3(bpartner.getUrl3());

		bpartnerRecord.setIsVendor(bpartner.isVendor());
		bpartnerRecord.setIsCustomer(bpartner.isCustomer());

		bpartnerRecord.setValue(bpartner.getValue());

		assertCanCreateOrUpdate(bpartnerRecord);
		saveRecord(bpartnerRecord);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
		bpartner.setId(bpartnerId);
	}

	private void saveBPartnerLocations(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerLocation> bpartnerLocations)
	{
		final ArrayList<BPartnerLocationId> savedBPartnerLocationIds = new ArrayList<>();
		for (final BPartnerLocation bPartnerLocation : bpartnerLocations)
		{
			saveBPartnerLocation(bpartnerId, bPartnerLocation);
			savedBPartnerLocationIds.add(bPartnerLocation.getId());
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

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
			@NonNull final BPartnerLocation bpartnerLocation)
	{
		final I_C_BPartner_Location bpartnerLocationRecord = loadOrNew(bpartnerLocation.getId(), I_C_BPartner_Location.class);
		bpartnerLocationRecord.setIsActive(bpartnerLocation.isActive());
		bpartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpartnerLocationRecord.setName(bpartnerLocation.getName());
		bpartnerLocationRecord.setBPartnerName(bpartnerLocation.getBpartnerName());

		final BPartnerLocationType locationType = bpartnerLocation.getLocationType();
		if (locationType != null)
		{
			locationType.getBillTo().ifPresent(b -> bpartnerLocationRecord.setIsBillTo(b));
			locationType.getBillToDefault().ifPresent(b -> bpartnerLocationRecord.setIsBillToDefault(b));
			locationType.getShipTo().ifPresent(b -> bpartnerLocationRecord.setIsShipTo(b));
			locationType.getShipToDefault().ifPresent(b -> bpartnerLocationRecord.setIsShipToDefault(b));
		}

		boolean anyLocationChange = false;

		// C_Location is immutable; never update an existing record, but create a new one
		final I_C_Location locationRecord = newInstance(I_C_Location.class);

		anyLocationChange = anyLocationChange || bpartnerLocation.isActiveChanged();
		locationRecord.setIsActive(bpartnerLocation.isActive());

		anyLocationChange = anyLocationChange || bpartnerLocation.isAddress1Changed();
		locationRecord.setAddress1(bpartnerLocation.getAddress1());

		anyLocationChange = anyLocationChange || bpartnerLocation.isAddress2Changed();
		locationRecord.setAddress2(bpartnerLocation.getAddress2());

		anyLocationChange = anyLocationChange || bpartnerLocation.isAddress3Changed();
		locationRecord.setAddress3(bpartnerLocation.getAddress3());

		anyLocationChange = anyLocationChange || bpartnerLocation.isAddress4Changed();
		locationRecord.setAddress4(bpartnerLocation.getAddress4());

		anyLocationChange = anyLocationChange || bpartnerLocation.isCountryCodeChanged();
		if (!isEmpty(bpartnerLocation.getCountryCode(), true))
		{
			final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
			final CountryId countryId = countryDAO.getCountryIdByCountryCode(bpartnerLocation.getCountryCode());
			locationRecord.setC_Country_ID(CountryId.toRepoId(countryId));
		}

		boolean postalDataSetFromPostalRecord = false;
		anyLocationChange = anyLocationChange || bpartnerLocation.isPostalChanged();
		if (!isEmpty(bpartnerLocation.getPostal(), true))
		{
			final IQueryBuilder<I_C_Postal> postalQueryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Postal.class)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.filter(PostalQueryFilter.of(bpartnerLocation.getPostal().trim()));
			if (!isEmpty(bpartnerLocation.getDistrict(), true))
			{
				postalQueryBuilder.addEqualsFilter(I_C_Postal.COLUMN_District, bpartnerLocation.getDistrict());
			}
			else
			{
				// prefer C_Postal records that have no district set
			}

			postalQueryBuilder.orderBy().addColumn(I_C_Postal.COLUMNNAME_District, Direction.Ascending, Nulls.First);

			final List<I_C_Postal> postalRecords = postalQueryBuilder
					.create()
					.list();

			final I_C_Postal postalRecord;
			if (postalRecords.isEmpty())
			{
				postalRecord = null;
			}
			else if (postalRecords.size() == 1)
			{
				postalRecord = postalRecords.get(0);
			}
			else if (locationRecord.getC_Country_ID() > 0)
			{
				postalRecord = postalRecords
						.stream()
						.filter(r -> (r.getC_Country_ID() == locationRecord.getC_Country_ID()))
						.findFirst()
						.orElse(null);
			}
			else
			{
				postalRecord = null;
			}

			if (postalRecord != null)
			{
				locationRecord.setC_Country_ID(postalRecord.getC_Country_ID());
				locationRecord.setC_Postal_ID(postalRecord.getC_Postal_ID());
				locationRecord.setPostal(postalRecord.getPostal());
				locationRecord.setCity(postalRecord.getCity());
				locationRecord.setRegionName(postalRecord.getRegionName());

				postalDataSetFromPostalRecord = true;
			}
		}

		bpartnerLocationRecord.setExternalId(ExternalId.toValue(bpartnerLocation.getExternalId()));
		bpartnerLocationRecord.setGLN(GLN.toCode(bpartnerLocation.getGln()));
		// bpartnerLocation.getId() // id is only for lookup and won't be updated later

		if (!postalDataSetFromPostalRecord)
		{
			locationRecord.setPostal(bpartnerLocation.getPostal());
			locationRecord.setCity(bpartnerLocation.getCity());
			locationRecord.setRegionName(bpartnerLocation.getRegion());
		}

		anyLocationChange = anyLocationChange || bpartnerLocation.isPoBoxChanged();
		locationRecord.setPOBox(bpartnerLocation.getPoBox());

		if (anyLocationChange)
		{
			assertCanCreateOrUpdate(locationRecord);
			saveRecord(locationRecord);
			bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		}

		Services.get(IBPartnerBL.class).setAddress(bpartnerLocationRecord);

		assertCanCreateOrUpdate(bpartnerLocationRecord);
		saveRecord(bpartnerLocationRecord);

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID(), bpartnerLocationRecord.getC_BPartner_Location_ID());
		bpartnerLocation.setId(bpartnerLocationId);
	}

	private void saveBPartnerContacts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerContact> contacts)
	{
		final ArrayList<BPartnerContactId> savedBPartnerContactIds = new ArrayList<>();
		for (final BPartnerContact bpartnerContact : contacts)
		{
			saveBPartnerContact(bpartnerId, bpartnerContact);
			savedBPartnerContactIds.add(bpartnerContact.getId());
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryUpdater<I_AD_User> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_AD_User.class)
				.addSetColumnValue(I_AD_User.COLUMNNAME_IsActive, false);
		queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_AD_User.COLUMN_AD_User_ID, savedBPartnerContactIds)
				.create()
				.update(columnUpdater);
	}

	private void saveBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerContact bpartnerContact)
	{
		final I_AD_User bpartnerContactRecord = loadOrNew(bpartnerContact.getId(), I_AD_User.class);
		bpartnerContactRecord.setExternalId(ExternalId.toValue(bpartnerContact.getExternalId()));
		bpartnerContactRecord.setIsActive(bpartnerContact.isActive());
		bpartnerContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpartnerContactRecord.setName(bpartnerContact.getName());
		bpartnerContactRecord.setEMail(bpartnerContact.getEmail());

		bpartnerContactRecord.setFirstname(bpartnerContact.getFirstName());
		bpartnerContactRecord.setLastname(bpartnerContact.getLastName());

		bpartnerContactRecord.setIsNewsletter(bpartnerContact.isNewsletter());

		final BPartnerContactType contactType = bpartnerContact.getContactType();
		if (contactType != null)
		{
			contactType.getDefaultContact().ifPresent(b -> bpartnerContactRecord.setIsDefaultContact(b));
			contactType.getBillToDefault().ifPresent(b -> bpartnerContactRecord.setIsBillToContact_Default(b));
			contactType.getShipToDefault().ifPresent(b -> bpartnerContactRecord.setIsShipToContact_Default(b));
			contactType.getSales().ifPresent(b -> bpartnerContactRecord.setIsSalesContact(b));
			contactType.getSalesDefault().ifPresent(b -> bpartnerContactRecord.setIsSalesContact_Default(b));
			contactType.getPurchase().ifPresent(b -> bpartnerContactRecord.setIsPurchaseContact(b));
			contactType.getPurchaseDefault().ifPresent(b -> bpartnerContactRecord.setIsPurchaseContact_Default(b));
			contactType.getSubjectMatter().ifPresent(b -> bpartnerContactRecord.setIsSubjectMatterContact(b));
		}

		bpartnerContactRecord.setDescription(bpartnerContact.getDescription());

		bpartnerContactRecord.setPhone(bpartnerContact.getPhone());
		bpartnerContactRecord.setFax(bpartnerContact.getFax());
		bpartnerContactRecord.setMobilePhone(bpartnerContact.getMobilePhone());

		bpartnerContactRecord.setC_Greeting_ID(GreetingId.toRepoIdOr(bpartnerContact.getGreetingId(), 0));

		assertCanCreateOrUpdate(bpartnerContactRecord);
		saveRecord(bpartnerContactRecord);

		final BPartnerContactId bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, bpartnerContactRecord.getAD_User_ID());

		bpartnerContact.setId(bpartnerContactId);
	}

	private void saveBPartnerBankAccounts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerBankAccount> bankAccounts)
	{
		final ArrayList<BPartnerBankAccountId> savedBPBankAccountIds = new ArrayList<>();
		for (final BPartnerBankAccount bankAccount : bankAccounts)
		{
			saveBPartnerBankAccount(bpartnerId, bankAccount);
			savedBPBankAccountIds.add(bankAccount.getId());
		}

		final IBPBankAccountDAO bpBankAccountsDAO = Services.get(IBPBankAccountDAO.class);
		bpBankAccountsDAO.deactivateByBPartnerExcept(bpartnerId, savedBPBankAccountIds);
	}

	private void saveBPartnerBankAccount(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerBankAccount bankAccount)
	{
		final I_C_BP_BankAccount record = loadOrNew(bankAccount.getId(), I_C_BP_BankAccount.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());

		record.setIBAN(bankAccount.getIban());
		record.setC_Currency_ID(bankAccount.getCurrencyId().getRepoId());
		record.setIsActive(bankAccount.isActive());

		assertCanCreateOrUpdate(record);
		saveRecord(record);

		final BPartnerBankAccountId id = BPartnerBankAccountId.ofRepoId(bpartnerId, record.getC_BP_BankAccount_ID());

		bankAccount.setId(id);
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
