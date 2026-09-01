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

import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryStatusColorPaletteService;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.document.dimension.DimensionService;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code GenerateShortageOverage} refuses a closed planning in its precondition, the same guard
 * {@code GenerateReceipt}/{@code GenerateShipment} carry.
 * <p>
 * {@code M_Delivery_Planning_GenerateShortageOverage} eagerly resolves an {@link InventoryRepository} field
 * initializer via {@code SpringContextHolder}, so a bean must be registered for it to succeed; the closed
 * guard fires before that repository is ever exercised, so the mock needs no stubbing.
 */
class M_Delivery_Planning_GenerateShortageOverageClosedGuardTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)),
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, deliveryPlanningService);
		SpringContextHolder.registerJUnitBean(InventoryRepository.class, Mockito.mock(InventoryRepository.class));
		SpringContextHolder.registerJUnitBean(HuForInventoryLineFactory.class, Mockito.mock(HuForInventoryLineFactory.class));
	}

	private static int deliveryPlanning(final boolean closed)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setIsClosed(closed);
		// a TransportDirection is required so getReceiptInfoIfHasReceipt (reached only for an OPEN planning,
		// after the closed guard already let it through) does not NPE on a null column
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		InterfaceWrapperHelper.save(record);
		return record.getM_Delivery_Planning_ID();
	}

	private static IProcessPreconditionsContext contextSelecting(final int deliveryPlanningId)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.isSingleSelection()).thenReturn(true);
		when(context.getSingleSelectedRecordId()).thenReturn(deliveryPlanningId);
		return context;
	}

	@Test
	@DisplayName("GenerateShortageOverage refuses a closed planning, naming it")
	void closedPlanningIsRefused()
	{
		final int closedId = deliveryPlanning(true);

		final ProcessPreconditionsResolution resolution =
				new M_Delivery_Planning_GenerateShortageOverage().checkPreconditionsApplicable(contextSelecting(closedId));

		assertThat(resolution.isAccepted()).isFalse();
		assertThat(resolution.getRejectReason().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message() + " - " + closedId);
	}

	@Test
	@DisplayName("GenerateShortageOverage does NOT refuse an open planning as closed - it is let through to the next precondition")
	void openPlanningIsNotRefusedAsClosed()
	{
		final int openId = deliveryPlanning(false);

		final ProcessPreconditionsResolution resolution =
				new M_Delivery_Planning_GenerateShortageOverage().checkPreconditionsApplicable(contextSelecting(openId));

		// rejected for the NEXT reason in line (no receipt) - never for being closed, proving the guard is a no-op
		// for an open planning and lets execution proceed past it
		assertThat(resolution.isAccepted()).isFalse();
		assertThat(resolution.getRejectReason().translate("en_US"))
				.as("must not be refused as closed")
				.doesNotContain(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message());
	}
}
