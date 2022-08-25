package de.metas.costing;

import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		assertThat(CostingLevel.Client.effectiveValue(OrgId.ANY)).isEqualTo(OrgId.ANY);
		assertThat(CostingLevel.Client.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ANY);

		assertThat(CostingLevel.Client.effectiveValue(AttributeSetInstanceId.NONE)).isEqualTo(AttributeSetInstanceId.NONE);
		assertThat(CostingLevel.Client.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.NONE);
	}

	@Test
	public void test_OrgLevel_effectiveValue_AnyOrg()
	{
		assertThatThrownBy(() -> CostingLevel.Organization.effectiveValue(OrgId.ANY))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Regular organization expected when costing level is Organization");
	}

	@Test
	public void test_OrgLevel_effectiveValue()
	{
		// Assert.assertEquals(OrgId.ANY, CostingLevel.Organization.effectiveValue(OrgId.ANY));
		assertThat(CostingLevel.Organization.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ofRepoId(123));

		assertThat(CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.NONE)).isEqualTo(AttributeSetInstanceId.NONE);
		assertThat(CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.NONE);
	}

	@Test
	public void test_BatchLotLevel_effectiveValue_NoASI()
	{
		assertThatThrownBy(() -> CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Regular ASI expected when costing level is Batch/Lot");
	}

	@Test
	public void test_BatchLotLevel_effectiveValue()
	{
		assertThat(CostingLevel.BatchLot.effectiveValue(OrgId.ANY)).isEqualTo(OrgId.ANY);
		assertThat(CostingLevel.BatchLot.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ANY);

		// Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE));
		assertThat(CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.ofRepoId(123));
	}
}
