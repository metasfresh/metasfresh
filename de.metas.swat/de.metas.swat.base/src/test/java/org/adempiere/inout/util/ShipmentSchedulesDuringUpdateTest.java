package org.adempiere.inout.util;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.Before;
import org.junit.Test;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
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

public class ShipmentSchedulesDuringUpdateTest
{
	private static final BigDecimal LINE_2_QTY = BigDecimal.valueOf(15);
	private static final BigDecimal LINE_1_QTY = BigDecimal.valueOf(10);

	private ShipmentSchedulesDuringUpdate shipmentCandidates;
	private DeliveryGroupCandidate group;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		group = DeliveryGroupCandidate.builder()
				.groupId(20)
				.shipperId(ShipperId.optionalOfRepoId(30))
				.warehouseId(WarehouseId.ofRepoId(40))
				.bPartnerAddress("bPartnerAddress")
				.build();

		shipmentCandidates = new ShipmentSchedulesDuringUpdate();
		shipmentCandidates.addGroup(group);
	}

	@Test
	public void addAndRemoveLine()
	{
		final DeliveryLineCandidate line1 = createLine(DeliveryRule.COMPLETE_LINE, CompleteStatus.INCOMPLETE_LINE, LINE_1_QTY);

		shipmentCandidates.addLine(line1);
		assertThat(shipmentCandidates.getAllLines()).containsExactly(line1);

		final I_M_ShipmentSchedule sched1Reloaded = load(line1.getShipmentScheduleId(), I_M_ShipmentSchedule.class);
		final DeliveryLineCandidate line1WithReloadedSched = group.createAndAddLineCandidate(sched1Reloaded, line1.getCompleteStatus());
		line1WithReloadedSched.setQtyToDeliver(line1.getQtyToDeliver());

		shipmentCandidates.removeLine(line1WithReloadedSched);
		assertThat(shipmentCandidates.getAllLines()).isEmpty();
	}

	@Test
	public void updateCompleteStatus_completeLine()
	{
		final DeliveryLineCandidate line1 = createLine(DeliveryRule.COMPLETE_LINE, CompleteStatus.INCOMPLETE_LINE, LINE_1_QTY);
		final DeliveryLineCandidate line2 = createLine(DeliveryRule.COMPLETE_LINE, CompleteStatus.OK, LINE_2_QTY);

		shipmentCandidates.updateCompleteStatusAndSetQtyToZeroWhereNeeded();

		assertThat(line1.getQtyToDeliver()).isZero();
		assertThat(line1.isDiscarded()).isTrue();

		assertThat(line2.getQtyToDeliver()).isEqualByComparingTo(LINE_2_QTY);
		assertThat(line2.isDiscarded()).isFalse();
	}

	@Test
	public void updateCompleteStatus_availiability()
	{
		final DeliveryLineCandidate line1 = createLine(DeliveryRule.AVAILABILITY, CompleteStatus.INCOMPLETE_LINE, LINE_1_QTY);
		final DeliveryLineCandidate line2 = createLine(DeliveryRule.AVAILABILITY, CompleteStatus.INCOMPLETE_LINE, LINE_2_QTY);

		shipmentCandidates.updateCompleteStatusAndSetQtyToZeroWhereNeeded();

		assertThat(line1.getQtyToDeliver()).isEqualByComparingTo(LINE_1_QTY);
		assertThat(line1.isDiscarded()).isFalse();

		assertThat(line2.getQtyToDeliver()).isEqualByComparingTo(LINE_2_QTY);
		assertThat(line2.isDiscarded()).isFalse();
	}

	@Test
	public void updateCompleteStatus_completeOrder()
	{
		final DeliveryLineCandidate line1 = createLine(DeliveryRule.COMPLETE_ORDER, CompleteStatus.INCOMPLETE_LINE, LINE_1_QTY);
		final DeliveryLineCandidate line2 = createLine(DeliveryRule.COMPLETE_ORDER, CompleteStatus.OK, LINE_2_QTY);

		shipmentCandidates.updateCompleteStatusAndSetQtyToZeroWhereNeeded();

		assertThat(line1.getQtyToDeliver()).isZero();
		assertThat(line1.getCompleteStatus()).isEqualTo(CompleteStatus.INCOMPLETE_ORDER);
		assertThat(line1.isDiscarded()).isTrue();

		assertThat(line2.getQtyToDeliver()).isZero();
		assertThat(line2.getCompleteStatus()).isEqualTo(CompleteStatus.INCOMPLETE_ORDER);
		assertThat(line2.isDiscarded()).isTrue();
	}

	private DeliveryLineCandidate createLine(DeliveryRule deliveryRule, CompleteStatus completeStatus, BigDecimal qty)
	{
		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setDeliveryRule(deliveryRule.getCode());
		save(sched);

		final DeliveryLineCandidate line = group.createAndAddLineCandidate(sched, completeStatus);
		line.setQtyToDeliver(qty);

		shipmentCandidates.addLine(line);

		return line;
	}
}
