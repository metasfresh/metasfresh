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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrgChangeRepository
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final BPartnerCompositeRepository bPartnerCompositeRepo;
	private final OrgMappingRepository orgMappingRepo;
	private final MembershipContractRepository membershipContractRepo;

	public OrgChangeRepository(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepo,
			@NonNull final OrgMappingRepository orgMappingRepo,
			@NonNull final MembershipContractRepository membershipContractRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
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
				.map(this::createFlatrateTerm)
				.collect(ImmutableList.toImmutableList());
	}

	private List<FlatrateTerm> getMembershipSubscriptions(final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		final Set<FlatrateTermId> membershipFlatrateTermIds = membershipContractRepo.retrieveMembershipSubscriptionIds(bpartnerId, orgChangeDate, orgId);
		return membershipFlatrateTermIds.stream()
				.map(this::createFlatrateTerm)
				.collect(ImmutableList.toImmutableList());
	}

	private FlatrateTerm createFlatrateTerm(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term term = flatrateDAO.getById(flatrateTermId);

		final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());

		final ProductId productId = ProductId.ofRepoId(term.getM_Product_ID());
		final I_C_UOM termUom = uomDAO.getById(CoalesceUtil.coalesce(UomId.ofRepoIdOrNull(term.getC_UOM_ID()), productBL.getStockUOMId(productId)));

		final BPartnerLocationAndCaptureId billPartnerLocationAndCaptureId = ContractLocationHelper.extractBillToLocationId(term);
		final BPartnerLocationAndCaptureId dropshipLPartnerLocationAndCaptureId = ContractLocationHelper.extractDropshipLocationId(term);

		return FlatrateTerm.builder()
				.flatrateTermId(flatrateTermId)
				.orgId(orgId)
				.billPartnerLocationAndCaptureId(billPartnerLocationAndCaptureId)
				.dropshipPartnerLocationAndCaptureId(dropshipLPartnerLocationAndCaptureId)
				.productId(productId)
				.flatrateConditionsId(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID()))
				.isSimulation(term.isSimulation())
				.status(FlatrateTermStatus.ofNullableCode(term.getContractStatus()))
				.userInChargeId(UserId.ofRepoIdOrNull(term.getAD_User_InCharge_ID()))
				.startDate(TimeUtil.asInstant(term.getStartDate()))
				.endDate(TimeUtil.asInstant(term.getEndDate()))
				.masterStartDate(TimeUtil.asInstant(term.getMasterStartDate()))
				.masterEndDate(TimeUtil.asInstant(term.getMasterEndDate()))
				.plannedQtyPerUnit(Quantity.of(term.getPlannedQtyPerUnit(), termUom))
				.deliveryRule(DeliveryRule.ofNullableCode(term.getDeliveryRule()))
				.deliveryViaRule(DeliveryViaRule.ofNullableCode(term.getDeliveryViaRule()))

				.build();
	}

	public boolean hasAnyMembershipProduct(@NonNull final OrgId orgId)
	{
		return membershipContractRepo.queryMembershipProducts(orgId).anyMatch();
	}




}
