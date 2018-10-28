package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerType;

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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
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
		final Map<BPartnerId, Integer> result = new BPartnerDAO()
				.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType.VENDOR);

		assertThat(result)
				.hasSize(2)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord1.getC_BPartner_ID()), 23)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord2.getC_BPartner_ID()), 24);
	}
}
