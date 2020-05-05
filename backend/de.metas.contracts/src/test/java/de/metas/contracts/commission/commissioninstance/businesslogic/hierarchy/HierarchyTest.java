package de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;

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

class HierarchyTest
{

	private HierarchyNode headOfSalesNode;
	private HierarchyNode salesSupervisorNode;
	private HierarchyNode salesrepNode1;
	private HierarchyNode salesrepNode2;
	private Hierarchy hierarchy;

	@BeforeEach
	void before()
	{
		headOfSalesNode = HierarchyNode.of(Beneficiary.of(BPartnerId.ofRepoId(40)));

		salesSupervisorNode = HierarchyNode.of(Beneficiary.of(BPartnerId.ofRepoId(30)));

		salesrepNode1 = HierarchyNode.of(Beneficiary.of(BPartnerId.ofRepoId(20)));
		salesrepNode2 = HierarchyNode.of(Beneficiary.of(BPartnerId.ofRepoId(21)));

		hierarchy = Hierarchy.builder()
				.addChildren(headOfSalesNode, ImmutableList.of(salesSupervisorNode))
				.addChildren(salesSupervisorNode, ImmutableList.of(salesrepNode2))
				.addChildren(salesSupervisorNode, ImmutableList.of(salesrepNode1))
				.build();
	}

	@Test
	void test()
	{
		assertThat(hierarchy.getParent(headOfSalesNode)).isNotPresent();
		assertThat(hierarchy.getChildren(headOfSalesNode)).containsExactly(salesSupervisorNode);
		assertThat(hierarchy.getChildren(salesSupervisorNode)).containsExactly(salesrepNode2, salesrepNode1);
	}

	@Test
	void getUpStream()
	{
		final Iterable<HierarchyNode> result = hierarchy.getUpStream(Beneficiary.of(BPartnerId.ofRepoId(21)));
		final Iterator<HierarchyNode> iterator = result.iterator();

		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(salesrepNode2);

		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(salesSupervisorNode);

		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(headOfSalesNode);

		assertThat(iterator.hasNext()).isFalse();
	}

	@Test
	void singleNodeHierarchy()
	{
		final Hierarchy hierarchy = Hierarchy.builder()
				.addChildren(headOfSalesNode, ImmutableList.of())
				.build();

		// parent and children
		assertThat(hierarchy.getChildren(headOfSalesNode)).isEmpty();
		assertThat(hierarchy.getParent(headOfSalesNode)).isNotPresent();

		// upstream
		final Iterable<HierarchyNode> result = hierarchy.getUpStream(headOfSalesNode.getBeneficiary());
		final Iterator<HierarchyNode> iterator = result.iterator();

		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(headOfSalesNode);
		assertThat(iterator.hasNext()).isFalse();
	}

}
