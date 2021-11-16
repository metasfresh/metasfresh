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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.model.I_M_Product;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;

@Repository
public class MembershipContractRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Set<FlatrateTermId> retrieveMembershipSubscriptionIds(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		return queryMembershipRunningSubscription(bpartnerId, orgChangeDate, orgId)
				.listIds(FlatrateTermId::ofRepoId);
	}

	public IQuery<I_C_Flatrate_Term> queryMembershipRunningSubscription(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final IQuery<I_M_Product> membershipProductQuery = queryMembershipProducts(orgId);

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
									 I_M_Product.COLUMNNAME_M_Product_ID,
									 membershipProductQuery)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create();
	}

	public IQuery<I_M_Product> queryMembershipProducts(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_C_CompensationGroup_Schema_ID, null)
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_C_CompensationGroup_Schema_Category_ID, null)
				.create();
	}

	public ImmutableSet<OrderId> retrieveMembershipOrderIds(
			@NonNull final OrgId orgId,
			@NonNull final Instant orgChangeDate)
	{
		final IQuery<I_M_Product> membershipProductQuery = queryMembershipProducts(orgId);

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, orgId)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
									 I_M_Product.COLUMNNAME_M_Product_ID,
									 membershipProductQuery)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.andCollect(I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID, I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.listIds(OrderId::ofRepoId);
	}
}
