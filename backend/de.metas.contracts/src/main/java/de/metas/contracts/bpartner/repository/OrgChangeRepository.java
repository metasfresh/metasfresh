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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class OrgChangeRepository
{
	private static final Logger logger = LogManager.getLogger(OrgChangeRepository.class);
	final ILoggable loggable = Loggables.withLogger(logger, Level.INFO);

	final IQueryBL queryBL = Services.get(IQueryBL.class);
	final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	final IProductDAO productDAO = Services.get(IProductDAO.class);

	final BPartnerCompositeRepository bPartnerCompositeRepo;
	final OrgMappingRepository orgMappingRepo;

	public OrgChangeRepository(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepo,
			@NonNull final OrgMappingRepository orgMappingRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
		this.orgMappingRepo = orgMappingRepo;
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(@NonNull final BPartnerId bpartnerId, @NonNull final LocalDate orgChangeDate)
	{
		final OrgMappingId orgMappingId = orgMappingRepo.getCreateOrgMappingId(bpartnerId);
		final BPartnerComposite bPartnerComposite = bPartnerCompositeRepo.getById(bpartnerId);

		return OrgChangeBPartnerComposite.builder()
				.bPartnerComposite(bPartnerComposite)
				.bPartnerOrgMappingId(orgMappingId)
				.membershipSubscriptions(retrieveMembershipSubscriptions(bpartnerId, orgChangeDate))
				.nonMembershipSubscriptions(retrieveNonMembershipSubscriptions(bpartnerId, orgChangeDate))
				.build();
	}

	public void saveOrgChangeBPartnerComposite(@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite)
	{
		final BPartnerComposite bPartnerComposite = orgChangeBPartnerComposite.getBPartnerComposite();

		bPartnerComposite.getBpartner().setOrgMappingId(orgChangeBPartnerComposite.getBPartnerOrgMappingId());

		bPartnerCompositeRepo.save(bPartnerComposite);

		// TODO subscriptions
	}

	public BPartnerLocation getBillToDefaultLocationOrNull(final List<BPartnerLocation> locations)
	{
		return locations.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location ->
						{
							final BPartnerLocationType locationType = location.getLocationType();
							return locationType.getIsBillToDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerLocation getShipToDefaultLocationOrNull(final List<BPartnerLocation> locations)
	{
		return locations.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location ->
						{
							final BPartnerLocationType locationType = location.getLocationType();
							return locationType.getIsShipToDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerContact getDefaultContactOrNull(final List<BPartnerContact> contacts)
	{
		return contacts.stream()
				.filter(BPartnerContact::isActive)
				.filter(contact ->
						{
							final BPartnerContactType contactTypeType = contact.getContactType();
							return contactTypeType.getIsDefaultContactOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerContact getBillToDefaultContactOrNull(final List<BPartnerContact> contacts)
	{
		return contacts.stream()
				.filter(BPartnerContact::isActive)
				.filter(contact ->
						{
							final BPartnerContactType contactTypeType = contact.getContactType();
							return contactTypeType.getIsBillToDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerContact getShipToDefaultContactOrNull(final List<BPartnerContact> contacts)
	{
		return contacts.stream()
				.filter(BPartnerContact::isActive)
				.filter(contact ->
						{
							final BPartnerContactType contactTypeType = contact.getContactType();
							return contactTypeType.getIsShipToDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerContact getPurchaseDefaultContactOrNull(final List<BPartnerContact> contacts)
	{
		return contacts.stream()
				.filter(BPartnerContact::isActive)
				.filter(contact ->
						{
							final BPartnerContactType contactTypeType = contact.getContactType();
							return contactTypeType.getIsPurchaseDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public BPartnerContact getSalesDefaultContactOrNull(final List<BPartnerContact> contacts)
	{
		return contacts.stream()
				.filter(BPartnerContact::isActive)
				.filter(contact ->
						{
							final BPartnerContactType contactTypeType = contact.getContactType();
							return contactTypeType.getIsSalesDefaultOr(false);
						})
				.findFirst()
				.orElse(null);
	}

	public void unmarkBillToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();
			type.setBillToDefault(false);
		}
	}

	public void unmarkShipToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();
			type.setShipToDefault(false);
		}
	}

	public void unmarkDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setDefaultContact(false);
		}
	}

	public void unmarkBillToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setBillToDefault(false);
		}
	}

	public void unmarkPurchaseDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setPurchaseDefault(false);
		}
	}

	public void unmarkSalesDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setSalesDefault(false);
		}
	}

	public void unmarkShipToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setShipToDefault(false);
		}
	}

	public BPartnerLocation createNewLocation(@NonNull final BPartnerLocation existingLocationInInitialPartner)
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

	public BPartnerContact createNewContact(@NonNull final BPartnerContact existingContactInInitialPartner)
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

	public BPartnerBankAccount createNewBankAccount(@NonNull final BPartnerBankAccount existingBankAccountInInitialPartner)
	{
		return BPartnerBankAccount.builder()
				.orgMappingId(existingBankAccountInInitialPartner.getOrgMappingId())
				.active(true)
				.currencyId(existingBankAccountInInitialPartner.getCurrencyId())
				.iban(existingBankAccountInInitialPartner.getIban())
				.build();
	}

	public boolean hasMembershipSubscriptions(final @NonNull BPartnerId bPartnerId, final @NonNull LocalDate orgChangeDate)
	{
		final IQuery<I_C_Flatrate_Term> membershipRunningSubscriptionQuery = createMembershipRunningSubscriptionQuery(bPartnerId, orgChangeDate);

		final I_C_Flatrate_Term membershipSubscription = membershipRunningSubscriptionQuery.stream().findFirst().orElse(null);

		if (membershipSubscription == null)
		{
			loggable.addLog("Org change date={}; current date={}; BPartner {} -> no membership subscriptions",
							orgChangeDate,
							SystemTime.asDate(),
							bPartnerId);
		}
		else
		{
			loggable.addLog("Org change date={}; current date={}; BPartner {} -> membership subscription {}",
							orgChangeDate,
							SystemTime.asDate(),
							bPartnerId,
							membershipSubscription);
		}

		return membershipSubscription != null;
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

	private final IQuery<I_M_Product_Category> getMembershipCategoryQuery()
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

	public void createOrgChangeHistory(
			@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final OrgMappingId orgMappingId,
			@NonNull final BPartnerComposite bPartnerToComposite)
	{
		final I_AD_OrgChange_History orgChangeHistory = newInstance(I_AD_OrgChange_History.class);

		orgChangeHistory.setAD_Org_From_ID(orgChangeRequest.getOrgFromId().getRepoId());
		orgChangeHistory.setAD_OrgTo_ID(orgChangeRequest.getOrgToId().getRepoId());
		orgChangeHistory.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
		orgChangeHistory.setC_BPartner_From_ID(orgChangeRequest.getBpartnerId().getRepoId());
		orgChangeHistory.setC_BPartner_To_ID(bPartnerToComposite.getBpartner().getId().getRepoId());
		orgChangeHistory.setDate_OrgChange(TimeUtil.asTimestamp(orgChangeRequest.getStartDate()));

		save(orgChangeHistory);
	}

	public BPartnerId getOrCreateCounterpartBPartner(@NonNull final OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
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
