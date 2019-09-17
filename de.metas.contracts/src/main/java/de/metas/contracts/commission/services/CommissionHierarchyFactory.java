package de.metas.contracts.commission.services;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.businesslogic.Beneficiary;
import de.metas.contracts.commission.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.businesslogic.hierarchy.Hierarchy.HierarchyBuilder;
import de.metas.contracts.commission.businesslogic.hierarchy.HierarchyNode;
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
		return createFor(bPartnerId, Hierarchy.builder());
	}

	private Hierarchy createFor(final BPartnerId bPartnerId, final HierarchyBuilder hierarchyBuilder)
	{
		final I_C_BPartner bPartnerRecord = loadOutOfTrx(bPartnerId, I_C_BPartner.class);
		final BPartnerId parentBPartnerId = BPartnerId.ofRepoIdOrNull(bPartnerRecord.getBPartner_Parent_ID());

		if (parentBPartnerId == null)
		{
			hierarchyBuilder.addChildren(node(bPartnerId), ImmutableList.of());
			return hierarchyBuilder.build();
		}

		hierarchyBuilder.addChildren(node(parentBPartnerId), ImmutableList.of(node(bPartnerId)));

		// recurse
		createFor(parentBPartnerId, hierarchyBuilder);

		return hierarchyBuilder.build();
	}

	private HierarchyNode node(final BPartnerId bPartnerId)
	{
		return HierarchyNode.of(Beneficiary.of(bPartnerId));
	}
}
