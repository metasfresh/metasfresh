/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.ui.web.view.IViewsRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What the window's "CUs annehmen" actions must do with the row they were given: hand BOTH its ids to the
 * shared receive, planning id included.
 * <p>
 * An adapter that dropped the id - or hard-coded it away, or looked it up from the receipt schedule instead of
 * the row (wrong the moment a split gives one schedule several plannings) - would look correct and silently
 * produce a receipt nothing links back to. So the pass-through is asserted, per row shape.
 */
class ReceiptDispositionDeliveryPlanningReceiveProcessTest
{
	private static final ReceiptScheduleId RECEIPT_SCHEDULE_ID = ReceiptScheduleId.ofRepoId(540010);
	private static final DeliveryPlanningId DELIVERY_PLANNING_ID = DeliveryPlanningId.ofRepoId(540020);

	private ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		receiptFromReceiptScheduleService = Mockito.mock(ReceiptFromReceiptScheduleService.class);

		// The two-argument form is required for a Mockito mock: the single-argument one registers under
		// bean.getClass(), which for a mock is the generated subclass and never matches the lookup type.
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, receiptFromReceiptScheduleService);
		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, Mockito.mock(DeliveryPlanningService.class));
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, Mockito.mock(IViewsRepository.class));

		// The HU receipt-schedule BL is resolved when the process is constructed, and its implementation pulls
		// in a slice of the shipping graph. Stubbed rather than built: this test never reaches it - the adapter
		// step under test is the hand-off, and the receive itself is covered in cucumber.
		SpringContextHolder.registerJUnitBean(PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	private ReceiptScheduleAndDeliveryPlanningId capturedSourceIds()
	{
		final ArgumentCaptor<ReceiptScheduleAndDeliveryPlanningId> captor =
				ArgumentCaptor.forClass(ReceiptScheduleAndDeliveryPlanningId.class);
		Mockito.verify(receiptFromReceiptScheduleService).receiveCUs(captor.capture(), Mockito.any());
		return captor.getValue();
	}

	private BigDecimal capturedQtyOverride()
	{
		final ArgumentCaptor<BigDecimal> captor = ArgumentCaptor.forClass(BigDecimal.class);
		Mockito.verify(receiptFromReceiptScheduleService).receiveCUs(Mockito.any(), captor.capture());
		return captor.getValue();
	}

	@Test
	@DisplayName("a PLANNED row hands its planning id to the shared receive")
	void plannedRowPassesItsPlanningId()
	{
		new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs()
				.receive(ReceiptScheduleAndDeliveryPlanningId.of(RECEIPT_SCHEDULE_ID, DELIVERY_PLANNING_ID));

		assertThat(capturedSourceIds().getDeliveryPlanningId()).isEqualTo(DELIVERY_PLANNING_ID);
		assertThat(capturedSourceIds().getReceiptScheduleId()).isEqualTo(RECEIPT_SCHEDULE_ID);
	}

	@Test
	@DisplayName("an UNPLANNED row hands no planning id - the plain receipt against the schedule")
	void unplannedRowPassesNoPlanningId()
	{
		new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs()
				.receive(ReceiptScheduleAndDeliveryPlanningId.ofReceiptScheduleId(RECEIPT_SCHEDULE_ID));

		assertThat(capturedSourceIds().getDeliveryPlanningId()).isNull();
		assertThat(capturedSourceIds().getReceiptScheduleId()).isEqualTo(RECEIPT_SCHEDULE_ID);
	}

	@Test
	@DisplayName("\"CUs annehmen\" states no quantity, so the ROW's own quantity rule decides - not the caller")
	void plainVariantStatesNoQuantity()
	{
		new WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs()
				.receive(ReceiptScheduleAndDeliveryPlanningId.of(RECEIPT_SCHEDULE_ID, DELIVERY_PLANNING_ID));

		// This row is PLANNED, and "no override" must NOT mean "the receipt schedule's whole remaining quantity": a
		// split shares one schedule across plannings, so the first planning received would consume the order line.
		// Passing null hands the decision to ReceiptFromReceiptScheduleService#getQtyToReceive - asserting a number
		// here would move the rule into the adapter and give the window a second definition of it.
		assertThat(capturedQtyOverride())
				.as("null means: let the shared receive resolve the row's own quantity")
				.isNull();
	}
}
