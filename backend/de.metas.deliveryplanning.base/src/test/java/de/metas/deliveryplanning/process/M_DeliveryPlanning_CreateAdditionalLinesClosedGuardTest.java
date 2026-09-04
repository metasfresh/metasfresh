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

package de.metas.deliveryplanning.process;

import de.metas.deliveryplanning.DeliveryInstructionRepository;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.document.dimension.DimensionService;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code CreateAdditionalLines} refuses a closed planning in its precondition, the same guard
 * {@code GenerateShortageOverage} and {@code GenerateReceipt}/{@code GenerateShipment} carry.
 * <p>
 * The refusal is a VISIBLE one: the button stays on screen, disabled, with the reason in its tooltip, so the
 * planner reads why. That is what {@link ProcessPreconditionsResolution#isInternal()} being {@code false}
 * pins - a resolution built by {@code rejectWithInternalReason} would hide the button instead, and would carry
 * the very same message, so asserting the message alone cannot tell the two apart.
 */
class M_DeliveryPlanning_CreateAdditionalLinesClosedGuardTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningRepository deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(mock(DimensionService.class));
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionService,
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, deliveryPlanningService);
	}

	private static int deliveryPlanning(final boolean closed)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setIsClosed(closed);
		InterfaceWrapperHelper.save(record);
		return record.getM_Delivery_Planning_ID();
	}

	private static IProcessPreconditionsContext contextSelecting(final int deliveryPlanningId)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.getSingleSelectedRecordId()).thenReturn(deliveryPlanningId);
		return context;
	}

	@Test
	@DisplayName("CreateAdditionalLines refuses a closed planning VISIBLY, naming it")
	void closedPlanningIsRefusedVisibly()
	{
		final int closedId = deliveryPlanning(true);

		final ProcessPreconditionsResolution resolution =
				new M_DeliveryPlanning_CreateAdditionalLines().checkPreconditionsApplicable(contextSelecting(closedId));

		assertThat(resolution.isAccepted()).isFalse();
		assertThat(resolution.isInternal())
				.as("the planner must SEE the disabled button and its reason, not lose the button altogether")
				.isFalse();
		assertThat(resolution.getRejectReason().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message() + " - " + closedId);
	}

	@Test
	@DisplayName("CreateAdditionalLines does NOT refuse an open planning as closed")
	void openPlanningIsNotRefusedAsClosed()
	{
		final int openId = deliveryPlanning(false);

		final ProcessPreconditionsResolution resolution =
				new M_DeliveryPlanning_CreateAdditionalLines().checkPreconditionsApplicable(contextSelecting(openId));

		// nothing else in the chain objects to a single, open, unblocked planning, so the guard being a no-op
		// for an open one is observable as the whole precondition accepting
		assertThat(resolution.isAccepted()).isTrue();
	}
}
