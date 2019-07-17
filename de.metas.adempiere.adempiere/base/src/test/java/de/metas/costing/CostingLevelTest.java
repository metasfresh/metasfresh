package de.metas.costing;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.junit.Assert;
import org.junit.Test;

import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class CostingLevelTest
{
	@Test
	public void test_ClientLevel_effectiveValue()
	{
		Assert.assertEquals(OrgId.ANY, CostingLevel.Client.effectiveValue(OrgId.ANY));
		Assert.assertEquals(OrgId.ANY, CostingLevel.Client.effectiveValue(OrgId.ofRepoId(123)));

		Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Client.effectiveValue(AttributeSetInstanceId.NONE));
		Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Client.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}

	@Test(expected = AdempiereException.class)
	public void test_OrgLevel_effectiveValue_AnyOrg()
	{
		CostingLevel.Organization.effectiveValue(OrgId.ANY);
	}

	@Test
	public void test_OrgLevel_effectiveValue()
	{
		// Assert.assertEquals(OrgId.ANY, CostingLevel.Organization.effectiveValue(OrgId.ANY));
		Assert.assertEquals(OrgId.ofRepoId(123), CostingLevel.Organization.effectiveValue(OrgId.ofRepoId(123)));

		Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.NONE));
		Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}

	@Test(expected = AdempiereException.class)
	public void test_BatchLotLevel_effectiveValue_NoASI()
	{
		CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE);
	}

	@Test
	public void test_BatchLotLevel_effectiveValue()
	{
		Assert.assertEquals(OrgId.ANY, CostingLevel.BatchLot.effectiveValue(OrgId.ANY));
		Assert.assertEquals(OrgId.ANY, CostingLevel.BatchLot.effectiveValue(OrgId.ofRepoId(123)));

		// Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE));
		Assert.assertEquals(AttributeSetInstanceId.ofRepoId(123), CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}
}
