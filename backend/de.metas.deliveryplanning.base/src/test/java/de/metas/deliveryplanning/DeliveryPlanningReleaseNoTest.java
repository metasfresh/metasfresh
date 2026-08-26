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

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.compiere.model.I_M_Delivery_Planning;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What a move and a removal do to the planning's own two instruction-derived fields, {@code ReleaseNo} and
 * {@code M_ShipperTransportation_ID}.
 * <p>
 * These are the fields a stale value can hide in: {@code ReleaseNo} is the number the forwarder quotes, so one
 * left over from the document the cargo has LEFT is worse than none - two records would then disagree about where
 * the cargo is.
 */
class DeliveryPlanningReleaseNoTest
{
	private DeliveryPlanningRepository deliveryPlanningRepository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
	}

	private static ShipperTransportationId deliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	private static DeliveryPlanningId deliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		InterfaceWrapperHelper.save(record);
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return InterfaceWrapperHelper.load(deliveryPlanningId, I_M_Delivery_Planning.class);
	}

	@Test
	@DisplayName("a move re-stamps the release number from the target and discards the source's")
	void moveReStampsReleaseNoFromTheTarget()
	{
		final DeliveryPlanningId deliveryPlanningId = deliveryPlanning();
		final ShipperTransportationId source = deliveryInstruction("SOURCE-1");
		final ShipperTransportationId target = deliveryInstruction("TARGET-9");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(deliveryPlanningId), source);
		final String releaseNoOnSource = reload(deliveryPlanningId).getReleaseNo();
		assertThat(releaseNoOnSource).startsWith("SOURCE-1-");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(deliveryPlanningId), target);

		final I_M_Delivery_Planning moved = reload(deliveryPlanningId);
		assertThat(moved.getReleaseNo())
				.as("the release number names the target, and nothing of the source survives in it")
				.startsWith("TARGET-9-")
				.doesNotContain("SOURCE-1");
		assertThat(moved.getM_ShipperTransportation_ID()).isEqualTo(target.getRepoId());
	}

	@Test
	@DisplayName("a removal clears the release number and the instruction reference, so the planning is planable again")
	void removalClearsReleaseNoAndReference()
	{
		final DeliveryPlanningId deliveryPlanningId = deliveryPlanning();
		final ShipperTransportationId deliveryInstructionId = deliveryInstruction("SOURCE-2");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(deliveryPlanningId), deliveryInstructionId);
		assertThat(reload(deliveryPlanningId).getReleaseNo()).isNotNull();

		deliveryPlanningRepository.clearInstructionReference(ImmutableList.of(deliveryPlanningId));

		final I_M_Delivery_Planning removed = reload(deliveryPlanningId);
		assertThat(removed.getReleaseNo()).isNull();
		assertThat(removed.getM_ShipperTransportation_ID()).isLessThanOrEqualTo(0);
	}

	@Test
	@DisplayName("the other plannings of the instruction keep their release numbers when one is removed")
	void removalLeavesTheOtherPlanningsAlone()
	{
		final DeliveryPlanningId staying = deliveryPlanning();
		final DeliveryPlanningId leaving = deliveryPlanning();
		final ShipperTransportationId deliveryInstructionId = deliveryInstruction("SHARED-3");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(staying, leaving), deliveryInstructionId);
		final String releaseNoOfStaying = reload(staying).getReleaseNo();

		deliveryPlanningRepository.clearInstructionReference(ImmutableList.of(leaving));

		assertThat(reload(staying).getReleaseNo())
				.as("removing one planning must not touch the release number of the consignment's other lines")
				.isEqualTo(releaseNoOfStaying);
	}
}
