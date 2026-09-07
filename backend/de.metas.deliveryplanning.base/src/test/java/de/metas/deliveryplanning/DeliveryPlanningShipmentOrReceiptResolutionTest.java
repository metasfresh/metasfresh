/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.document.dimension.DimensionService;
import de.metas.shipping.TransportDirection;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Which side {@code DeliveryPlanningRepository#getShipmentOrReceiptInfo} resolves a planning to.
 * <p>
 * {@code isIncomingOrDropship()} and {@code isOutgoingOrDropship()} both hold for {@link TransportDirection#Dropship},
 * so the {@code if / else if} ordering alone decides that case - nothing in the code states the choice. Today a
 * dropship planning carries a receipt schedule and no shipment schedule of its own, so receipt-side is the correct
 * answer; this test pins it. Should a later change give dropship plannings a shipment schedule, that branch would go
 * on silently answering receipt - this test fails instead and forces the choice to be made explicitly.
 */
class DeliveryPlanningShipmentOrReceiptResolutionTest
{
	private static final int RECEIPT_SCHEDULE_ID = 540101;
	private static final int SHIPMENT_SCHEDULE_ID = 540102;
	private static final int BPARTNER_ID = 540103;

	private DeliveryPlanningRepository deliveryPlanningRepository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
	}

	@Test
	void dropship_resolvesReceiptSide()
	{
		final DeliveryPlanningId deliveryPlanningId = createDeliveryPlanning(TransportDirection.Dropship, RECEIPT_SCHEDULE_ID, null);

		assertThat(resolveSide(deliveryPlanningId))
				.as("a dropship planning satisfies both predicates and resolves receipt-side, carrying no shipment schedule of its own")
				.isEqualTo("receipt");
	}

	/**
	 * The counterpart that proves the shipment branch is reachable at all - without it the dropship assertion above
	 * would also pass if the shipment mapper were simply broken.
	 */
	@Test
	void outgoing_resolvesShipmentSide()
	{
		final DeliveryPlanningId deliveryPlanningId = createDeliveryPlanning(TransportDirection.Outgoing, null, SHIPMENT_SCHEDULE_ID);

		assertThat(resolveSide(deliveryPlanningId)).isEqualTo("shipment");
	}

	private String resolveSide(final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getShipmentOrReceiptInfo(deliveryPlanningId, receiptInfo -> "receipt", shipmentInfo -> "shipment");
	}

	private DeliveryPlanningId createDeliveryPlanning(
			final TransportDirection transportDirection,
			@Nullable final Integer receiptScheduleId,
			@Nullable final Integer shipmentScheduleId)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(transportDirection.getCode());
		record.setC_BPartner_ID(BPARTNER_ID);
		if (receiptScheduleId != null)
		{
			record.setM_ReceiptSchedule_ID(receiptScheduleId);
		}
		if (shipmentScheduleId != null)
		{
			record.setM_ShipmentSchedule_ID(shipmentScheduleId);
		}
		InterfaceWrapperHelper.save(record);
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}
}
