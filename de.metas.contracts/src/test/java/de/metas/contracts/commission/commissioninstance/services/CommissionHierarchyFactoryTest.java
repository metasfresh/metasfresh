package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.services.CommissionHierarchyFactory;

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

class CommissionHierarchyFactoryTest
{
	@BeforeEach
	void beforeAll()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void createFor()
	{
		final I_C_BPartner headOfSalesRecord = newInstance(I_C_BPartner.class);
		saveRecord(headOfSalesRecord);

		final I_C_BPartner salesSuperVisor = newInstance(I_C_BPartner.class);
		salesSuperVisor.setBPartner_Parent_ID(headOfSalesRecord.getC_BPartner_ID());
		saveRecord(salesSuperVisor);

		final I_C_BPartner salesRep1 = newInstance(I_C_BPartner.class);
		salesRep1.setBPartner_Parent_ID(salesSuperVisor.getC_BPartner_ID());
		saveRecord(salesRep1);

		// sibling of salesRep1; shall not be part of salesRep1's hierachy
		final I_C_BPartner salesRep2 = newInstance(I_C_BPartner.class);
		salesRep2.setBPartner_Parent_ID(salesSuperVisor.getC_BPartner_ID());
		saveRecord(salesRep2);

		// add a cycle to make sure the code can handle it
		headOfSalesRecord.setBPartner_Parent_ID(salesRep1.getC_BPartner_ID());
		saveRecord(headOfSalesRecord);

		// invoke the method under test
		final Hierarchy result = new CommissionHierarchyFactory().createFor(BPartnerId.ofRepoId(salesRep1.getC_BPartner_ID()));

		assertThat(result.getParent(node(salesRep2.getC_BPartner_ID()))).isNotPresent();
		assertThat(result.getParent(node(salesRep1.getC_BPartner_ID()))).contains(node(salesSuperVisor.getC_BPartner_ID()));
		assertThat(result.getParent(node(salesSuperVisor.getC_BPartner_ID()))).contains(node(headOfSalesRecord.getC_BPartner_ID()));
		assertThat(result.getParent(node(headOfSalesRecord.getC_BPartner_ID()))).isNotPresent();
	}

	private HierarchyNode node(final int id)
	{
		return HierarchyNode.of(Beneficiary.of(BPartnerId.ofRepoId(id)));
	}

}
