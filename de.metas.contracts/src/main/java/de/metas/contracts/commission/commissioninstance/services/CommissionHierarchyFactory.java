package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.HashSet;

import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy.HierarchyBuilder;
import lombok.NonNull;

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

@Service
public class CommissionHierarchyFactory
{
	// very crude but simple implementation; expand and make more efficient as needed
	public Hierarchy createFor(@NonNull final BPartnerId bPartnerId)
	{
		return createFor(
				bPartnerId/* starting point */,
				Hierarchy.builder() /* result builder */,
				new HashSet<BPartnerId>() /* helper to make sure we don't enter a cycle */
		);
	}

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
		final BPartnerId parentBPartnerId = BPartnerId.ofRepoIdOrNull(bPartnerRecord.getBPartner_Parent_ID());

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
