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
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org_Mapping;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.stereotype.Repository;

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

	public boolean hasMembershipSubscriptions(final BPartnerId bPartnerId)
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = getMembershipCategoryQuery();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
									 I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
									 membershipCategoryQuery)
				.create()
				.anyMatch();
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

	private IQuery<I_M_Product_Category> getMembershipCategoryQuery()
	{
		final IQuery<I_M_Product_Category> membershipCategoryQuery = queryBL.createQueryBuilder(I_M_Product_Category.class)
				.addInArrayFilter(I_M_Product_Category.COLUMNNAME_Value, "Membership") // todo: see if this can be cleaner
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_AD_Org_ID, 0)
				.create();
		return membershipCategoryQuery;
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

		return cloneBPartner(orgChangeParameters, orgMappingId);
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
		final List<I_C_BPartner_Location> bpartnerLocationRecords = bpartnerDAO.retrieveBPartnerLocations(orgChangeParameters.getBpartnerId());

		reactivateLocationsForPartner(orgChangeParameters, bpartnerLocationRecords);


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

	// TODO
}
