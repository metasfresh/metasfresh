package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractComparableAssert;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class BPartnerDAOTest
{
	private BPartnerDAO bpartnerDAO;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAO = new BPartnerDAO();
	}

	@Test
	public void retrieveAllDiscountSchemaIdsIndexedByBPartnerId()
	{
		final I_C_BPartner bPartnerRecord1 = newInstance(I_C_BPartner.class);
		bPartnerRecord1.setPO_DiscountSchema_ID(23);
		save(bPartnerRecord1);

		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setPO_DiscountSchema_ID(24);
		save(group);

		final I_C_BPartner bPartnerRecord2 = newInstance(I_C_BPartner.class);
		bPartnerRecord2.setC_BP_Group(group);
		save(bPartnerRecord2);

		// invoke the method under test
		final Map<BPartnerId, Integer> result = bpartnerDAO
				.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType.VENDOR);

		assertThat(result)
				.hasSize(2)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord1.getC_BPartner_ID()), 23)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord2.getC_BPartner_ID()), 24);
	}

	@Test
	public void retrieveBPartnerIdByName()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");
		final BPartnerId bpartnerId2 = createBPartnerWithName("BPartner 2");

		assertRetrieveBPartnerIdByName("BPartner 1").isEqualTo(bpartnerId1);
		assertRetrieveBPartnerIdByName("BPartner 2").isEqualTo(bpartnerId2);
		assertRetrieveBPartnerIdByName("BPartner").isNull();
	}

	private AbstractComparableAssert<?, BPartnerId> assertRetrieveBPartnerIdByName(final String queryBPName)
	{
		final BPartnerId bpartnerId = bpartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
				.bpartnerName(queryBPName)
				.onlyOrgId(OrgId.ANY)
				.failIfNotExists(false)
				.build())
				.orElse(null);
		return assertThat(bpartnerId);
	}

	@Test
	public void retrieveBPartnerIdBy_notFound()
	{
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("noSuchPartner")
				.onlyOrgId(OrgId.ofRepoId(20))
				.onlyOrgId(OrgId.ANY)
				.failIfNotExists(true)
				.build();

		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerIdBy(query))
				.isInstanceOf(BPartnerIdNotFoundException.class)
				.hasMessage("Found no existing BPartner;"
						+ " Searched via the following properties one-after-one (list may be empty): Value/Code=noSuchPartner;"
						+ " The search was restricted to the following orgIds (empty means no restriction): [20, 0]");
	}

	private BPartnerId createBPartnerWithName(final String name)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setName(name);
		saveRecord(record);

		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}
}
