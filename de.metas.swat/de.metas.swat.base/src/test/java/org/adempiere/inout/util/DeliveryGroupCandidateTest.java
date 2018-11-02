package org.adempiere.inout.util;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.Before;
import org.junit.Test;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.ShipperId;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DeliveryGroupCandidateTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void toSTringAndHashCodeAndEqualsDontCauseStackOverflow()
	{
		final DeliveryGroupCandidate group = DeliveryGroupCandidate.builder()
				.groupId(10)
				.warehouseId(WarehouseId.ofRepoId(20))
				.shipperId(ShipperId.optionalOfRepoId(1))
				.bPartnerAddress("bPartnerAddress")
				.build();

		group.createAndAddLineCandidate(newInstance(I_M_ShipmentSchedule.class), CompleteStatus.OK);

		assertThat(group.toString()).isNotEmpty();
		group.hashCode(); // throws no exception
		assertThat(group.equals(group)).isTrue();
	}

}
