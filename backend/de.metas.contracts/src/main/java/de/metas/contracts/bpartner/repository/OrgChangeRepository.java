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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrgChangeRepository
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final BPartnerCompositeRepository bPartnerCompositeRepo;
	private final FlatrateTermRepo flatrateTermRepo;
	private final OrgMappingRepository orgMappingRepo;
	private final MembershipContractRepository membershipContractRepo;

	public OrgChangeRepository(
			@NonNull final BPartnerCompositeRepository bPartnerCompositeRepo,
			@NonNull final FlatrateTermRepo flatrateTermRepo,
			@NonNull final OrgMappingRepository orgMappingRepo,
			@NonNull final MembershipContractRepository membershipContractRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
		this.flatrateTermRepo = flatrateTermRepo;
		this.orgMappingRepo = orgMappingRepo;
		this.membershipContractRepo = membershipContractRepo;
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate)
	{
		final OrgMappingId orgMappingId = orgMappingRepo.getCreateOrgMappingId(bpartnerId);
		final BPartnerComposite bPartnerComposite = bPartnerCompositeRepo.getById(bpartnerId);

		return OrgChangeBPartnerComposite.builder()
				.bPartnerComposite(bPartnerComposite)
				.bPartnerOrgMappingId(orgMappingId)
				.membershipSubscriptions(getMembershipSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.allRunningSubscriptions(getAllRunningSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.groupCategoryId(getGroupCategoryId(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()).orElse(null))
				.build();
	}

	private Optional<GroupCategoryId> getGroupCategoryId(@NonNull final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		return membershipContractRepo.queryMembershipRunningSubscription(bpartnerId, orgChangeDate, orgId)
				.stream()
				.map(term -> productDAO.getById(term.getM_Product_ID()))
				.filter(product -> product.getC_CompensationGroup_Schema_Category_ID() > 0)
				.map(product -> GroupCategoryId.ofRepoId(product.getC_CompensationGroup_Schema_Category_ID()))
				.findFirst();

	}

	private Collection<FlatrateTerm> getAllRunningSubscriptions(@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final Set<FlatrateTermId> flatrateTermIds = flatrateDAO.retrieveAllRunningSubscriptionIds(bpartnerId, orgChangeDate, orgId);
		return flatrateTermIds.stream()
				.map(flatrateTermRepo::getById)
				.collect(ImmutableList.toImmutableList());
	}

	private List<FlatrateTerm> getMembershipSubscriptions(final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		final Set<FlatrateTermId> membershipFlatrateTermIds = membershipContractRepo.retrieveMembershipSubscriptionIds(bpartnerId, orgChangeDate, orgId);
		return membershipFlatrateTermIds.stream()
				.map(flatrateTermRepo::getById)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasAnyMembershipProduct(@NonNull final OrgId orgId)
	{
		return membershipContractRepo.queryMembershipProducts(orgId).anyMatch();
	}




}
