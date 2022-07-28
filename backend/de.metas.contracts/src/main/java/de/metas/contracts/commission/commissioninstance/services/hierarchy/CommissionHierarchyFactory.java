package de.metas.contracts.commission.commissioninstance.services.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy.HierarchyBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.contracts
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

/**
 * Crates a hierarchy of BPartners by recursively following {@code C_BPartner.C_BPartner_SalesRep_ID} references.
 */
@Service
public class CommissionHierarchyFactory
{
	// very crude but simple implementation; expand and make more efficient as needed
	@NonNull
	public Hierarchy createForCustomer(@NonNull final BPartnerId bPartnerId, @NonNull final BPartnerId salesRepId)
	{
		if (bPartnerId.equals(salesRepId))
		{
			return createFor(salesRepId, Hierarchy.builder(), new HashSet<>());
		}

		final HierarchyBuilder hierarchyBuilder = Hierarchy.builder()
				.addChildren(node(salesRepId), ImmutableList.of(node(bPartnerId)));

		final HashSet<BPartnerId> seenBPartnerIds = new HashSet<>();
		seenBPartnerIds.add(bPartnerId);

		return createFor(
				salesRepId/* starting point */,
				hierarchyBuilder /* result builder */,
				seenBPartnerIds /* helper to make sure we don't enter a cycle */
		);
	}

	@NonNull
	private Hierarchy createFor(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final HierarchyBuilder hierarchyBuilder,
			@NonNull final HashSet<BPartnerId> seenBPartnerIds)
	{
		if (!seenBPartnerIds.add(bPartnerId))
		{
			return hierarchyBuilder.build(); // there is a loop in our supposed tree; stoppping now, because we saw it all
		}

		final I_C_BPartner bPartnerRecord = loadOutOfTrx(bPartnerId, I_C_BPartner.class);
		final BPartnerId parentBPartnerId = BPartnerId.ofRepoIdOrNull(bPartnerRecord.getC_BPartner_SalesRep_ID());

		if (parentBPartnerId == null || seenBPartnerIds.contains(parentBPartnerId))
		{
			hierarchyBuilder.addChildren(node(bPartnerId), ImmutableList.of());
			return hierarchyBuilder.build();
		}

		hierarchyBuilder.addChildren(node(parentBPartnerId), ImmutableList.of(node(bPartnerId)));

		// recurse
		createFor(parentBPartnerId, hierarchyBuilder, seenBPartnerIds);

		return hierarchyBuilder.build();
	}

	private HierarchyNode node(@NonNull final BPartnerId bPartnerId)
	{
		return HierarchyNode.of(Beneficiary.of(bPartnerId));
	}
}
