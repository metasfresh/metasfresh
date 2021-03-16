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

package de.metas.contracts.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.process.FlatrateTermCreator;
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
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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

	//TODO Try to use a domain object instead of the service

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

	public BPartnerId retrieveOrCloneBPartner(final @NonNull OrgChangeParameters orgChangeParameters)
	{
		final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(orgChangeParameters.getBpartnerId());

		int orgMappingId = bPartnerRecord.getAD_Org_Mapping_ID();

		if (orgMappingId <= 0)
		{
			orgMappingId = createOrgMappingForBPartner(bPartnerRecord);

			bPartnerRecord.setAD_Org_Mapping_ID(orgMappingId);

			save(bPartnerRecord);
		}
		else
		{
			final BPartnerId counterpartBPartnerId = retrieveCounterpartBPartner(orgMappingId, orgChangeParameters.getOrgToId());

			if (counterpartBPartnerId != null)
			{
				markIsActiveBPartner(counterpartBPartnerId, true);

				return counterpartBPartnerId;
			}
		}

		createOrgChangehistory(orgMappingId, bPartnerRecord.getAD_Org_ID(), orgChangeParameters.getOrgToId());

		return cloneBPartner(orgChangeParameters, orgMappingId);
	}

	private void createOrgChangehistory(final int orgMappingId,
			final int orgFromId,
			final OrgId orgToId)
	{
		final I_AD_OrgChange_History orgChangeHistory = newInstance(I_AD_OrgChange_History.class);
		orgChangeHistory.setAD_Org_From_ID(orgFromId);
		orgChangeHistory.setAD_OrgTo_ID(orgToId.getRepoId());
		orgChangeHistory.setAD_Org_Mapping_ID(orgMappingId);

		save(orgChangeHistory);
	}

	private int createOrgMappingForBPartner(final I_C_BPartner bPartnerRecord)
	{
		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BPartner.class));
		orgMapping.setValue(bPartnerRecord.getValue());

		save(orgMapping);

		return orgMapping.getAD_Org_Mapping_ID();
	}

	private BPartnerId retrieveCounterpartBPartner(final int orgMappingId, final @NonNull OrgId orgToId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMN_AD_Org_Mapping_ID, orgMappingId)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgToId)
				.create()
				.firstId(BPartnerId::ofRepoIdOrNull);
	}

	private BPartnerId cloneBPartner(final @NonNull OrgChangeParameters orgChangeParameters, final int orgMappingId)
	{
		final I_C_BPartner oldBpartner = bpartnerDAO.getById(orgChangeParameters.getBpartnerId());

		final I_C_BPartner newBPartner = copy()
				.setFrom(oldBpartner)
				.addTargetColumnNameToSkip(I_C_BPartner.COLUMNNAME_AD_Org_ID)
				.copyToNew(I_C_BPartner.class);

		newBPartner.setAD_Org_ID(orgChangeParameters.getOrgToId().getRepoId());
		newBPartner.setAD_Org_Mapping_ID(orgMappingId);
		save(newBPartner);

		return BPartnerId.ofRepoId(newBPartner.getC_BPartner_ID());
	}

	private void markIsActiveBPartner(final @NonNull BPartnerId bPartnerId, boolean isActive)
	{
		final I_C_BPartner bpartnerRecord = bpartnerDAO.getById(bPartnerId);
		bpartnerRecord.setIsActive(isActive);
		save(bpartnerRecord);
	}

	public void retrieveOrCloneLocations(final @NonNull OrgChangeParameters orgChangeParameters,
			final @NonNull BPartnerId newBPartnerId)
	{
		final List<I_C_BPartner_Location> bpartnerActiveLocationRecords = bpartnerDAO.retrieveBPartnerLocations(orgChangeParameters.getBpartnerId());

		for (I_C_BPartner_Location location : bpartnerActiveLocationRecords)
		{
			final int orgMappingId = location.getAD_Org_Mapping_ID();

			// if(orgMappingId > 0 )
			// {
			// 	I_C_BPartner_Location counterpartBpartnerLocation = retrieveCounterpartBPartnerLocation(orgMappingId, orgChangeParameters.getOrgToId());
			// }
			// if(counterpartBpartnerLocation == null)
			// {
			// 	counterpartBpartnerLocation = cloneBPartnerLocation(location, orgMappingId )
			// }
		}
		reactivateLocationsForPartner(orgChangeParameters, bpartnerActiveLocationRecords);

	}

	private I_C_BPartner_Location retrieveCounterpartBPartnerLocation(final int orgMappingId, final OrgId orgToId)
	{
		// TODO
		return null;
	}

	private void reactivateLocationsForPartner(final OrgChangeParameters orgChangeParameters, final List<I_C_BPartner_Location> bpartnerLocationRecords)
	{

	}

	// private final IQueryBL queryBL = Services.get(IQueryBL.class);
	//
	// public OrgMappingId retrieveOrgMappingId(
	// 		@NonNull final AdTableId tableId,
	// 		@Nullable String value)
	// {
	//
	// }

	public void createSubscription(final @NonNull OrgChangeParameters orgChangeParams)
	{
		// now the source partner should have a counterpart in the new org.
		// Also, the active users and locations have counterparts as well
		// => we can now create counterparts for the active subscriptions

		// 1. create the membership subscription

		final ProductId membershipProductId = orgChangeParams.getMembershipProductId();
		final I_M_Product newOrgMembershipProduct = getNewOrgProductForMapping(membershipProductId, orgChangeParams.getOrgToId());
		final I_C_Flatrate_Term membershipSubscription = retrieveMembershipSubscription(orgChangeParams.getBpartnerId(), orgChangeParams.getStartDate());

		final I_C_BPartner partner = retrieveCounterpartBPartner(membershipSubscription.getBill_BPartner_ID()); // todo

		final I_AD_User user = retrieveCounterpartUser(membershipSubscription.getAD_User_InCharge_ID());

		final Iterator<I_C_BPartner> it = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.iterate(I_C_BPartner.class);

		final FlatrateTermCreator membershipSubscriptionCreator = FlatrateTermCreator.builder()
				.bPartners(() -> it)
				.startDate(TimeUtil.asTimestamp(orgChangeParams.getStartDate()))
				.isSimulation(false)
				.conditions(membershipSubscription.getC_Flatrate_Conditions())
				.product(newOrgMembershipProduct)
				.userInCharge(user)
				.build();

		membershipSubscriptionCreator.createTermsForBPartners();

		// 2. clone the other subscriptions if their products have counterparts in this org

		final List<I_C_Flatrate_Term> nonMembershipSubscriptions = retrieveNonMembershipSubscriptions(orgChangeParams.getBpartnerId(), orgChangeParams.getStartDate());

		for (final I_C_Flatrate_Term subscription : nonMembershipSubscriptions)
		{
			final I_M_Product counterpartProduct = getNewOrgProductForMapping(
					ProductId.ofRepoId(subscription.getM_Product_ID()),
					orgChangeParams.getOrgToId());

			if (counterpartProduct != null)
			{
				final FlatrateTermCreator subscriptionCreator = FlatrateTermCreator
						.builder()
						.bPartners(() -> it)
						.startDate(TimeUtil.asTimestamp(orgChangeParams.getStartDate()))
						.isSimulation(false)
						.conditions(subscription.getC_Flatrate_Conditions())
						.product(counterpartProduct)
						.userInCharge(user)
						.build();
			}

			membershipSubscriptionCreator.createTermsForBPartners();
		}
	}

	private I_AD_User retrieveCounterpartUser(final int ad_user_inCharge_id)
	{
		return null;
	}

	private I_C_BPartner_Location retrieveCounterpartBPartnerLocation(final int bill_location_id)
	{
		return null;
	}

	private I_C_BPartner retrieveCounterpartBPartner(final int bill_bPartner_id)
	{
		return null;
	}

	private I_C_Flatrate_Term retrieveMembershipSubscription(final @NonNull BPartnerId bpartnerId, final @NonNull LocalDate orgChangeDate)
	{
		return createMembershipRunningSubscriptionQuery(bpartnerId, orgChangeDate)
				.first();
	}

	private List<I_C_Flatrate_Term> retrieveNonMembershipSubscriptions(final @NonNull BPartnerId bPartnerId,
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
				.list();
	}

}
