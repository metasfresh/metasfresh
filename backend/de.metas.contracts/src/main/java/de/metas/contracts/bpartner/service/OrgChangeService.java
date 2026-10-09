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
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.contracts.bpartner.repository.OrgChangeRepository;
import de.metas.contracts.bpartner.repository.OrgMappingRepository;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.model.I_M_Product;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrgChangeService
{
	private final OrgChangeRepository orgChangeRepo;
	private final BPartnerCompositeRepository bpCompositeRepo;
	private final OrgMappingRepository orgMappingRepo;
	private final OrgChangeHistoryRepository orgChangeHistoryRepo;
	private final GroupTemplateRepository groupTemplateRepo;

	final IProductDAO productDAO = Services.get(IProductDAO.class);

	public void moveToNewOrg(@NonNull final OrgChangeRequest request)
	{
		OrgChangeCommand.builder()
				.bpCompositeRepo(bpCompositeRepo)
				.orgMappingRepo(orgMappingRepo)
				.orgChangeRepo(orgChangeRepo)
				.orgChangeHistoryRepo(orgChangeHistoryRepo)
				.groupTemplateRepo(groupTemplateRepo)
				.request(request)
				.build()
				.execute();
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(final BPartnerId partnerId, final Instant orgChangeDate)
	{
		return orgChangeRepo.getByIdAndOrgChangeDate(partnerId, orgChangeDate);
	}

	public boolean hasAnyMembershipProduct(final OrgId orgId)
	{
		return orgChangeRepo.hasAnyMembershipProduct(orgId);
	}

	public boolean isGroupCategoryContainsProductsInTargetOrg(@NonNull final GroupCategoryId groupCategoryId,
			@NonNull final OrgId targetOrgId)
	{
		final Optional<I_M_Product> productOfGroupCategory = productDAO.getProductOfGroupCategory(groupCategoryId, targetOrgId);

		return productOfGroupCategory.isPresent();
	}
}
