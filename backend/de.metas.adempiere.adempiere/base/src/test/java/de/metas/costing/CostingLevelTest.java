package de.metas.costing;

import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
		Assertions.assertEquals(OrgId.ANY, CostingLevel.Client.effectiveValue(OrgId.ANY));
		Assertions.assertEquals(OrgId.ANY, CostingLevel.Client.effectiveValue(OrgId.ofRepoId(123)));

		Assertions.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Client.effectiveValue(AttributeSetInstanceId.NONE));
		Assertions.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Client.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}

	@Test
	public void test_OrgLevel_effectiveValue_AnyOrg()
	{
		Assertions.assertThrows(AdempiereException.class, () -> CostingLevel.Organization.effectiveValue(OrgId.ANY));
	}

	@Test
	public void test_OrgLevel_effectiveValue()
	{
		// Assert.assertEquals(OrgId.ANY, CostingLevel.Organization.effectiveValue(OrgId.ANY));
		Assertions.assertEquals(OrgId.ofRepoId(123), CostingLevel.Organization.effectiveValue(OrgId.ofRepoId(123)));

		Assertions.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.NONE));
		Assertions.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}

	@Test
	public void test_BatchLotLevel_effectiveValue_NoASI()
	{
		Assertions.assertThrows(AdempiereException.class, () -> CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE));
	}

	@Test
	public void test_BatchLotLevel_effectiveValue()
	{
		Assertions.assertEquals(OrgId.ANY, CostingLevel.BatchLot.effectiveValue(OrgId.ANY));
		Assertions.assertEquals(OrgId.ANY, CostingLevel.BatchLot.effectiveValue(OrgId.ofRepoId(123)));

		// Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE));
		Assertions.assertEquals(AttributeSetInstanceId.ofRepoId(123), CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.ofRepoId(123)));
	}
}
