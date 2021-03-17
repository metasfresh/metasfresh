/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_AD_Org_Mapping;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class OrgChangeRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	final IProductDAO productDAO = Services.get(IProductDAO.class);

	final BPartnerCompositeRepository bPartnerCompositeRepo;

	public OrgChangeRepository(final BPartnerCompositeRepository bPartnerCompositeRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
	}

	//TODO Try to use a domain object instead of the service

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(@NonNull final BPartnerId bpartnerId, @NonNull final LocalDate orgChangeDate)
	{
		final OrgMappingId orgMappingId = getCreateOrgMappingId(bpartnerId);
		final BPartnerComposite bPartnerComposite = bPartnerCompositeRepo.getById(bpartnerId);

		return OrgChangeBPartnerComposite.builder()
				.bPartnerComposite(bPartnerComposite)
				.bPartnerOrgMappingId(orgMappingId)
				.membershipSubscriptions(retrieveMembershipSubscriptions(bpartnerId, orgChangeDate))
				.nonMembershipSubscriptions(retrieveNonMembershipSubscriptions(bpartnerId, orgChangeDate))
				.build();
	}

	public void moveToNewOrg(@NonNull final OrgChangeRequest orgChangeRequest)
	{
		final OrgChangeBPartnerComposite orgChangeBPartnerComposite = getByIdAndOrgChangeDate(orgChangeRequest.getBpartnerId(), orgChangeRequest.getStartDate());

		final OrgMappingId orgMappingId = orgChangeBPartnerComposite.getBPartnerOrgMappingId();

		final BPartnerId newBPartnerId = getOrCreateCounterpartBPartner(orgChangeRequest, orgMappingId);

		// gets the partner with all the active and inactive locations, users and bank accounts
		final BPartnerComposite newBPartnerComposite = bPartnerCompositeRepo.getById(newBPartnerId);

		final List<BPartnerLocation> newLocations = getOrCreateLocations(orgChangeRequest, orgChangeBPartnerComposite, newBPartnerComposite);

		final List<BPartnerContact> newContacts = getOrCreateContacts(orgChangeRequest, orgChangeBPartnerComposite, newBPartnerComposite);
		final List<BPartnerBankAccount> newBPBankAccounts = getOrCreateBPBankAccounts(orgChangeRequest, orgChangeBPartnerComposite, newBPartnerComposite);

		newBPartnerComposite.toBuilder()
				.locations(newLocations)
				.contacts(newContacts)
				.bankAccounts(newBPBankAccounts)
				.build();

		bPartnerCompositeRepo.save(newBPartnerComposite);

		saveOrgChangeBPartnerComposite(orgChangeBPartnerComposite);
		createOrgChangeHistory(orgMappingId, orgChangeBPartnerComposite.getBPartnerComposite().getOrgId(), orgChangeRequest.getOrgToId());

	}

	private List<BPartnerBankAccount> getOrCreateBPBankAccounts(@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite, final BPartnerComposite newBPartnerComposite)
	{
		final List<BPartnerBankAccount> existingBankAccountsInInitialPartner = orgChangeBPartnerComposite.getBPartnerComposite().getBankAccounts();
		final List<BPartnerBankAccount> existingBankAccountsInNewPartner = newBPartnerComposite.getBankAccounts();

		final List<BPartnerBankAccount> newBankAccounts = new ArrayList<>();
		for (final BPartnerBankAccount existingBankAccountInInitialPartner : existingBankAccountsInInitialPartner)
		{
			final OrgMappingId bankAccountOrgMappingId = getCreateOrgMappingId(existingBankAccountInInitialPartner);

			existingBankAccountInInitialPartner.setOrgMappingId(bankAccountOrgMappingId);
			final Optional<BPartnerBankAccount> matchingBankAccount = existingBankAccountsInNewPartner.stream()
					.filter(bankAccount -> bankAccount.getOrgMappingId() == bankAccountOrgMappingId)
					.findFirst();

			if (matchingBankAccount != null)
			{
				final BPartnerBankAccount existingBankAccount = matchingBankAccount.get();
				existingBankAccount.setActive(true);
				newBankAccounts.add(existingBankAccount);
			}
			else
			{
				BPartnerBankAccount newBankAccount = createNewBankAccount(existingBankAccountInInitialPartner, orgChangeRequest);
				newBankAccounts.add(newBankAccount);
			}
		}
		return newBankAccounts;
	}

	private void saveOrgChangeBPartnerComposite(@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite)
	{
		final BPartnerComposite bPartnerComposite = orgChangeBPartnerComposite.getBPartnerComposite();

		bPartnerComposite.getBpartner().setOrgMappingId(orgChangeBPartnerComposite.getBPartnerOrgMappingId());

		bPartnerCompositeRepo.save(bPartnerComposite);

		// TODO subscriptions
	}

	private List<BPartnerLocation> getOrCreateLocations(@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite newBPartnerComposite)
	{
		final List<BPartnerLocation> existingLocationsInInitialPartner = orgChangeBPartnerComposite.getBPartnerComposite().getLocations();
		final List<BPartnerLocation> existingLocationsInNewPartner = newBPartnerComposite.getLocations();

		final List<BPartnerLocation> newLocations = new ArrayList<>();
		for (final BPartnerLocation existingLocationInInitialPartner : existingLocationsInInitialPartner)
		{
			final OrgMappingId locationOrgMappingId = getCreateOrgMappingId(existingLocationInInitialPartner);
			existingLocationInInitialPartner.setOrgMappingId(locationOrgMappingId);

			final Optional<BPartnerLocation> matchingLocation = existingLocationsInNewPartner.stream()
					.filter(location -> location.getOrgMappingId() == locationOrgMappingId)
					.findFirst();

			if (matchingLocation != null)
			{
				final BPartnerLocation existingLocation = matchingLocation.get();
				existingLocation.setActive(true);
				newLocations.add(existingLocation);
			}
			else
			{
				BPartnerLocation newLocation = createNewLocation(existingLocationInInitialPartner, orgChangeRequest);
				newLocations.add(newLocation);
			}
		}
		return newLocations;
	}

	private List<BPartnerContact> getOrCreateContacts(final OrgChangeRequest orgChangeRequest, final OrgChangeBPartnerComposite orgChangeBPartnerComposite, final BPartnerComposite newBPartnerComposite)
	{
		final List<BPartnerContact> existingContactsInInitialPartner = orgChangeBPartnerComposite.getBPartnerComposite().getContacts();
		final List<BPartnerContact> existingContactsInNewPartner = newBPartnerComposite.getContacts();

		final List<BPartnerContact> newContacts = new ArrayList<>();
		for (final BPartnerContact existingContactInInitialPartner : existingContactsInInitialPartner)
		{
			final OrgMappingId contactOrgMappingId = getCreateOrgMappingId(existingContactInInitialPartner);
			existingContactInInitialPartner.setOrgMappingId(contactOrgMappingId);

			final Optional<BPartnerContact> matchingContact = existingContactsInNewPartner.stream()
					.filter(contact -> contactOrgMappingId.equals(contact.getOrgMappingId()))
					.findFirst();

			if (matchingContact != null)
			{
				final BPartnerContact existingContact = matchingContact.get();
				existingContact.setActive(true);
				newContacts.add(existingContact);
			}
			else
			{
				BPartnerContact newContact = createNewContact(existingContactInInitialPartner, orgChangeRequest);
				newContacts.add(newContact);
			}
		}
		return newContacts;
	}

	private BPartnerLocation createNewLocation(@NonNull final BPartnerLocation existingLocationInInitialPartner,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		return BPartnerLocation.builder()
				.active(true)
				.address1(existingLocationInInitialPartner.getAddress1())
				.address2(existingLocationInInitialPartner.getAddress2())
				.address3(existingLocationInInitialPartner.getAddress3())
				.address4(existingLocationInInitialPartner.getAddress4())
				.bpartnerName(existingLocationInInitialPartner.getBpartnerName())
				.city(existingLocationInInitialPartner.getCity())
				.countryCode(existingLocationInInitialPartner.getCountryCode())
				.district(existingLocationInInitialPartner.getDistrict())
				.externalId(existingLocationInInitialPartner.getExternalId())
				.gln(existingLocationInInitialPartner.getGln())
				.name(existingLocationInInitialPartner.getName())
				.orgMappingId(existingLocationInInitialPartner.getOrgMappingId())
				.poBox(existingLocationInInitialPartner.getPoBox())
				.postal(existingLocationInInitialPartner.getPostal())
				.locationType(existingLocationInInitialPartner.getLocationType())
				.region(existingLocationInInitialPartner.getRegion()) // Important: To be changed with the organic org change!
				.build();

	}

	private BPartnerContact createNewContact(@NonNull final BPartnerContact existingContactInInitialPartner,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		return BPartnerContact.builder()
				.orgMappingId(existingContactInInitialPartner.getOrgMappingId())
				.active(true)
				.value(existingContactInInitialPartner.getValue())
				.firstName(existingContactInInitialPartner.getFirstName())
				.lastName(existingContactInInitialPartner.getLastName())
				.name(existingContactInInitialPartner.getName())
				.contactType(existingContactInInitialPartner.getContactType())
				.description(existingContactInInitialPartner.getDescription())
				.email(existingContactInInitialPartner.getEmail())
				.externalId(existingContactInInitialPartner.getExternalId())
				.fax(existingContactInInitialPartner.getFax())
				.greetingId(existingContactInInitialPartner.getGreetingId())
				.mobilePhone(existingContactInInitialPartner.getMobilePhone())
				.newsletter(existingContactInInitialPartner.isNewsletter())
				.phone(existingContactInInitialPartner.getPhone())
				.build();

	}

	private BPartnerBankAccount createNewBankAccount(@NonNull final BPartnerBankAccount existingBankAccountInInitialPartner,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		return BPartnerBankAccount.builder()
				.orgMappingId(existingBankAccountInInitialPartner.getOrgMappingId())
				.active(true)
				.currencyId(existingBankAccountInInitialPartner.getCurrencyId())
				.iban(existingBankAccountInInitialPartner.getIban())
				.build();
	}

	private OrgMappingId getCreateOrgMappingId(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(bpartnerId);

		OrgMappingId orgMappingId = OrgMappingId.ofRepoIdOrNull(bPartnerRecord.getAD_Org_Mapping_ID());

		if (orgMappingId == null)
		{
			orgMappingId = createOrgMappingForBPartner(bPartnerRecord);

			bPartnerRecord.setAD_Org_Mapping_ID(orgMappingId.getRepoId());

			save(bPartnerRecord);
		}

		return orgMappingId;
	}

	private OrgMappingId getCreateOrgMappingId(final BPartnerLocation existingLocationInInitialPartner)
	{

		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BPartner_Location.class));
		orgMapping.setValue(existingLocationInInitialPartner.getName());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	private OrgMappingId getCreateOrgMappingId(final BPartnerContact existingContactInInitialPartner)
	{
		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BP_BankAccount.class));
		orgMapping.setValue(existingContactInInitialPartner.getValue());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	private OrgMappingId getCreateOrgMappingId(final BPartnerBankAccount existingBankAccountInInitialPartner)
	{
		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BP_BankAccount.class));
		orgMapping.setValue(existingBankAccountInInitialPartner.getIban());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	public boolean hasMembershipSubscriptions(final @NonNull BPartnerId bPartnerId, final @NonNull LocalDate orgChangeDate)
	{
		return createMembershipRunningSubscriptionQuery(bPartnerId, orgChangeDate).anyMatch();
	}

	public boolean hasAnyMembershipProduct(final OrgId orgId)
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = getMembershipCategoryQuery();
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addInSubQueryFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID,
									 I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
									 membershipCategoryQuery)
				.create()
				.anyMatch();

	}

	public I_M_Product getNewOrgProductForMapping(final ProductId productId, final OrgId orgId)
	{
		final de.metas.product.model.I_M_Product productRecord = productDAO.getById(productId, de.metas.product.model.I_M_Product.class);
		return queryBL.createQueryBuilder(de.metas.product.model.I_M_Product.class)
				.addEqualsFilter(de.metas.product.model.I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(de.metas.product.model.I_M_Product.COLUMNNAME_M_Product_Mapping_ID, productRecord.getM_Product_Mapping_ID())
				.create()
				.first();
	}

	private IQuery<I_M_Product_Category> getMembershipCategoryQuery()
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = queryBL.createQueryBuilder(I_M_Product_Category.class)
				.addInArrayFilter(I_M_Product_Category.COLUMNNAME_Value, "Membership") // todo: see if this can be cleaner
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_AD_Org_ID, 0)
				.create();
		return membershipCategoryQuery;
	}

	private IQuery<I_C_Flatrate_Term> createMembershipRunningSubscriptionQuery(final BPartnerId bPartnerId,
			final LocalDate orgChangeDate)
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = getMembershipCategoryQuery();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
									 I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
									 membershipCategoryQuery)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Running)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_MasterEndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create();
	}

	// public BPartnerId retrieveOrCloneBPartner(final @NonNull OrgChangeRequest orgChangeRequest)
	// {
	// 	final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(orgChangeRequest.getBpartnerId());
	//
	// 	OrgMappingId orgMappingId = OrgMappingId.ofRepoIdOrNull(bPartnerRecord.getAD_Org_Mapping_ID());
	//
	// 	if (orgMappingId == null)
	// 	{
	// 		orgMappingId = createOrgMappingForBPartner(bPartnerRecord);
	//
	// 		bPartnerRecord.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
	//
	// 		save(bPartnerRecord);
	// 	}
	// 	else
	// 	{
	// 		final BPartnerId counterpartBPartnerId = getOrCreateCounterpartBPartner(orgChangeRequest, orgMappingId);
	//
	// 		if (counterpartBPartnerId != null)
	// 		{
	// 			markIsActiveBPartner(counterpartBPartnerId, true);
	//
	// 			return counterpartBPartnerId;
	// 		}
	// 	}
	//
	// 	createOrgChangeHistory(orgMappingId, bPartnerRecord.getAD_Org_ID(), orgChangeRequest.getOrgToId());
	//
	// 	return cloneBPartner(orgChangeRequest, orgMappingId);
	// }

	private void createOrgChangeHistory(@NonNull final OrgMappingId orgMappingId,
			final OrgId orgFromId,
			final OrgId orgToId)
	{
		final I_AD_OrgChange_History orgChangeHistory = newInstance(I_AD_OrgChange_History.class);
		orgChangeHistory.setAD_Org_From_ID(orgFromId.getRepoId());
		orgChangeHistory.setAD_OrgTo_ID(orgToId.getRepoId());
		orgChangeHistory.setAD_Org_Mapping_ID(orgMappingId.getRepoId());

		save(orgChangeHistory);
	}

	private OrgMappingId createOrgMappingForBPartner(@NonNull final I_C_BPartner bPartnerRecord)
	{
		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BPartner.class));
		orgMapping.setValue(bPartnerRecord.getValue());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	private BPartnerId getOrCreateCounterpartBPartner(@NonNull final OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		BPartnerId bPartnerId = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMN_AD_Org_Mapping_ID, orgMappingId)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgChangeRequest.getOrgToId())
				.create()
				.firstId(BPartnerId::ofRepoIdOrNull);

		if (bPartnerId == null)
		{
			bPartnerId = cloneBPartner(orgChangeRequest, orgMappingId);
		}

		return bPartnerId;
	}

	public void markIsActiveBPartner(final @NonNull BPartnerId bPartnerId, boolean isActive)
	{
		final I_C_BPartner bpartnerRecord = bpartnerDAO.getById(bPartnerId);
		bpartnerRecord.setIsActive(isActive);
		save(bpartnerRecord);
	}

	private BPartnerId cloneBPartner(final @NonNull OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		final I_C_BPartner oldBpartner = bpartnerDAO.getById(orgChangeRequest.getBpartnerId());

		final I_C_BPartner newBPartner = copy()
				.setFrom(oldBpartner)
				.addTargetColumnNameToSkip(I_C_BPartner.COLUMNNAME_AD_Org_ID)
				.copyToNew(I_C_BPartner.class);

		newBPartner.setAD_Org_ID(orgChangeRequest.getOrgToId().getRepoId());
		newBPartner.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
		save(newBPartner);

		return BPartnerId.ofRepoId(newBPartner.getC_BPartner_ID());
	}

	public void markIsActiveBPartnerLocation(final @NonNull BPartnerLocationId bPartnerLocationId, boolean isActive)
	{
		final I_C_BPartner_Location bpartnerLocationRecord = bpartnerDAO.getBPartnerLocationById(bPartnerLocationId);
		bpartnerLocationRecord.setIsActive(isActive);
		save(bpartnerLocationRecord);
	}

	// public void retrieveOrCloneLocations(final @NonNull OrgChangeRequest orgChangeRequest,
	// 		final @NonNull BPartnerId newBPartnerId)
	// {
	// 	final List<I_C_BPartner_Location> bpartnerActiveLocationRecords = bpartnerDAO.retrieveBPartnerLocations(orgChangeRequest.getBpartnerId());
	//
	// 	for (I_C_BPartner_Location location : bpartnerActiveLocationRecords)
	// 	{
	// 		BPartnerLocationId counterpartBPartnerLocationId = null;
	//
	// 		int orgMappingId = location.getAD_Org_Mapping_ID();
	//
	// 		if (orgMappingId <= 0)
	// 		{
	// 			orgMappingId = createOrgMappingForBPartnerLocation(location);
	//
	// 			location.setAD_Org_Mapping_ID(orgMappingId);
	//
	// 			save(location);
	//
	// 		}
	// 		else
	// 		{
	// 			counterpartBPartnerLocationId = retrieveCounterpartBPartnerLocation(orgMappingId,
	// 																				orgChangeRequest.getOrgToId());
	// 			if (counterpartBPartnerLocationId != null)
	// 			{
	// 				markIsActiveBPartnerLocation(counterpartBPartnerLocationId, true);
	// 			}
	// 		}
	//
	// 		if (counterpartBPartnerLocationId == null)
	// 		{
	// 			counterpartBPartnerLocationId = cloneBPartnerLocation(location, orgMappingId, orgChangeRequest);
	// 		}
	// 	}
	//}

	private BPartnerLocationId cloneBPartnerLocation(final I_C_BPartner_Location oldBPLocation, final int orgMappingId, final OrgChangeRequest orgChangeRequest)
	{
		final OrgId orgToId = orgChangeRequest.getOrgToId();
		final I_C_Location location = oldBPLocation.getC_Location();

		final I_C_Location newLocation = copy()
				.setFrom(location)
				.addTargetColumnNameToSkip(I_C_Location.COLUMNNAME_AD_Org_ID)
				.copyToNew(I_C_Location.class);

		newLocation.setAD_Org_ID(orgToId.getRepoId());
		save(newLocation);

		final I_C_BPartner_Location newBPLocation = copy()
				.setFrom(oldBPLocation)
				.addTargetColumnNameToSkip(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID)
				.addTargetColumnNameToSkip(I_C_BPartner_Location.COLUMNNAME_C_Location_ID)
				.copyToNew(I_C_BPartner_Location.class);

		newBPLocation.setC_Location_ID(newLocation.getC_Location_ID());
		newBPLocation.setAD_Org_ID(orgChangeRequest.getOrgToId().getRepoId());
		newBPLocation.setAD_Org_Mapping_ID(orgMappingId);
		save(newBPLocation);

		return BPartnerLocationId.ofRepoId(newBPLocation.getC_BPartner_ID(), newBPLocation.getC_BPartner_Location_ID());
	}

	private BPartnerLocationId retrieveCounterpartBPartnerLocation(final int orgMappingId, final OrgId orgToId)
	{
		final I_C_BPartner_Location location = queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_AD_Org_Mapping_ID, orgMappingId)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID, orgToId)
				.create()
				.first(I_C_BPartner_Location.class);

		return location == null ? null :
				BPartnerLocationId.ofRepoIdOrNull(location.getC_BPartner_ID(), location.getC_BPartner_Location_ID());

	}

	private void reactivateLocationsForPartner(final OrgChangeRequest orgChangeRequest, final List<I_C_BPartner_Location> bpartnerLocationRecords)
	{

	}

	// public void createSubscription(final @NonNull OrgChangeRequest orgChangeParams)
	// {
	// 	// now the source partner should have a counterpart in the new org.
	// 	// Also, the active users and locations have counterparts as well
	// 	// => we can now create counterparts for the active subscriptions
	//
	// 	// 1. create the membership subscription
	//
	// 	final ProductId membershipProductId = orgChangeParams.getMembershipProductId();
	// 	final I_M_Product newOrgMembershipProduct = getNewOrgProductForMapping(membershipProductId, orgChangeParams.getOrgToId());
	// 	final I_C_Flatrate_Term membershipSubscription = retrieveMembershipSubscription(orgChangeParams.getBpartnerId(), orgChangeParams.getStartDate());
	//
	// 	final I_C_BPartner partner = getOrCreateCounterpartBPartner(membershipSubscription.getBill_BPartner_ID()); // todo
	//
	// 	final I_AD_User user = retrieveCounterpartUser(membershipSubscription.getAD_User_InCharge_ID());
	//
	// 	final Iterator<I_C_BPartner> it = queryBL.createQueryBuilder(I_C_BPartner.class)
	// 			.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
	// 			.create()
	// 			.iterate(I_C_BPartner.class);
	//
	// 	final FlatrateTermCreator membershipSubscriptionCreator = FlatrateTermCreator.builder()
	// 			.bPartners(() -> it)
	// 			.startDate(TimeUtil.asTimestamp(orgChangeParams.getStartDate()))
	// 			.isSimulation(false)
	// 			.conditions(membershipSubscription.getC_Flatrate_Conditions())
	// 			.product(newOrgMembershipProduct)
	// 			.userInCharge(user)
	// 			.build();
	//
	// 	membershipSubscriptionCreator.createTermsForBPartners();
	//
	// 	// 2. clone the other subscriptions if their products have counterparts in this org
	//
	// 	final List<I_C_Flatrate_Term> nonMembershipSubscriptions = retrieveNonMembershipSubscriptions(orgChangeParams.getBpartnerId(), orgChangeParams.getStartDate());
	//
	// 	for (final I_C_Flatrate_Term subscription : nonMembershipSubscriptions)
	// 	{
	// 		final I_M_Product counterpartProduct = getNewOrgProductForMapping(
	// 				ProductId.ofRepoId(subscription.getM_Product_ID()),
	// 				orgChangeParams.getOrgToId());
	//
	// 		if (counterpartProduct != null)
	// 		{
	// 			final FlatrateTermCreator subscriptionCreator = FlatrateTermCreator
	// 					.builder()
	// 					.bPartners(() -> it)
	// 					.startDate(TimeUtil.asTimestamp(orgChangeParams.getStartDate()))
	// 					.isSimulation(false)
	// 					.conditions(subscription.getC_Flatrate_Conditions())
	// 					.product(counterpartProduct)
	// 					.userInCharge(user)
	// 					.build();
	// 		}
	//
	// 		membershipSubscriptionCreator.createTermsForBPartners();
	// 	}
	// }

	private Set<FlatrateTermId> retrieveMembershipSubscriptions(final @NonNull BPartnerId bpartnerId, final @NonNull LocalDate orgChangeDate)
	{
		return createMembershipRunningSubscriptionQuery(bpartnerId, orgChangeDate)
				.listIds(FlatrateTermId::ofRepoId);
	}

	private Set<FlatrateTermId> retrieveNonMembershipSubscriptions(final @NonNull BPartnerId bPartnerId,
			final @NonNull LocalDate orgChangeDate)
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = getMembershipCategoryQuery();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addNotInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
										I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
										membershipCategoryQuery)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Running)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_MasterEndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

}
