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

package de.metas.ui.web.receiptlogistics.process;

import de.metas.deliveryplanning.DeliveryInstructionRepository;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanning;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.document.dimension.DimensionService;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.organization.OrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_RV_ReceiptLogistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The shared base every receipt-logistics action sits on, on its two jobs.
 * <p>
 * <b>Turning a selected row back into its source records.</b> {@code RV_ReceiptLogistics} unions a PLANNED
 * branch (an active {@code Incoming} planning carrying a receipt schedule) with an UNPLANNED one (a receipt
 * schedule no active planning refers to), so the same selection hands an action both row shapes. Reading the
 * planning id off an unplanned row must yield {@code null} rather than a zero, a {@code -1} or an exception -
 * that {@code null} is what later tells the action which of the two paths a row takes.
 * <p>
 * <b>Refusing an ineligible selection before anything is produced.</b> A planning may hold AT MOST ONE receipt
 * or shipment; {@code Processed} says it already does (or that it was closed). The refusal has to be
 * all-or-nothing and has to name every offending row, because the alternative - skipping the bad rows - hands
 * the planner a partial result they never asked for and no indication of which rows were dropped.
 */
class ReceiptLogisticsViewBasedProcessTest
{
	/** The receipt-logistics window itself ("Wareneingangslogistik"), so a reader is not misled about what is under test. */
	private static final WindowId WINDOW_ID = WindowId.of(542190);

	private static int nextId = 1;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningRepository deliveryPlanningRepository = Mockito.mock(DeliveryPlanningRepository.class);
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
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

		SpringContextHolder.registerJUnitBean(deliveryPlanningService);
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, Mockito.mock(IViewsRepository.class));

		// The HU receipt-schedule BL is resolved when any action on this base is constructed (it is what turns a
		// row's receipt schedule id back into a record), and its implementation pulls in a slice of the shipping
		// graph. Stubbed rather than built: nothing here loads a receipt schedule.
		SpringContextHolder.registerJUnitBean(PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	/**
	 * One grid row as the WebUI hands it to a process: field values keyed by column name, read back through the
	 * same JSON-value path production uses. {@code null} for the planning id is how the view's unplanned branch
	 * arrives - {@code M_Delivery_Planning_ID} is {@code NULL::numeric(10)} there.
	 */
	private static IViewRow row(final int receiptScheduleRepoId, @Nullable final Integer deliveryPlanningRepoId)
	{
		return ViewRow.builder(WINDOW_ID)
				.setRowId(DocumentId.of(receiptScheduleRepoId))
				.putFieldValue(I_RV_ReceiptLogistics.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleRepoId)
				.putFieldValue(I_RV_ReceiptLogistics.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningRepoId)
				.build();
	}

	private static DeliveryPlanning.DeliveryPlanningBuilder planning()
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.transportDirection(TransportDirection.Incoming);
	}

	@Nested
	@DisplayName("the two source ids of one selected row")
	class SourceIds
	{
		@Test
		@DisplayName("a PLANNED row yields both ids")
		void plannedRowYieldsBothIds()
		{
			final ReceiptScheduleAndDeliveryPlanningId ids =
					ReceiptLogisticsViewBasedProcess.extractReceiptScheduleAndPlanningId(row(540010, 540020));

			assertThat(ids.getReceiptScheduleId()).isEqualTo(ReceiptScheduleId.ofRepoId(540010));
			assertThat(ids.getDeliveryPlanningId()).isEqualTo(DeliveryPlanningId.ofRepoId(540020));
			assertThat(ids.isPlanned()).isTrue();
		}

		@Test
		@DisplayName("an UNPLANNED row yields the receipt schedule id and null")
		void unplannedRowYieldsScheduleIdAndNull()
		{
			final ReceiptScheduleAndDeliveryPlanningId ids =
					ReceiptLogisticsViewBasedProcess.extractReceiptScheduleAndPlanningId(row(540011, null));

			assertThat(ids.getReceiptScheduleId()).isEqualTo(ReceiptScheduleId.ofRepoId(540011));
			assertThat(ids.getDeliveryPlanningId()).isNull();
			assertThat(ids.isPlanned()).isFalse();
		}

		@Test
		@DisplayName("a row without a receipt schedule is refused loudly, never silently dropped from the selection")
		void rowWithoutReceiptScheduleIsRefused()
		{
			assertThatThrownBy(() -> ReceiptLogisticsViewBasedProcess.extractReceiptScheduleAndPlanningId(row(0, 540020)))
					.hasMessageContaining(I_RV_ReceiptLogistics.COLUMNNAME_M_ReceiptSchedule_ID);
		}
	}

	@Nested
	@DisplayName("the shared precondition: no selected planning may already be processed")
	class NoneProcessed
	{
		private ReceivingTestProcess process;

		@BeforeEach
		void createProcess()
		{
			process = new ReceivingTestProcess();
		}

		@Test
		@DisplayName("a selection of eligible rows is accepted, and produces every one of them")
		void eligibleSelectionIsAccepted()
		{
			final DeliveryPlanning first = planning().build();
			final DeliveryPlanning second = planning().build();
			final DeliveryPlanningList selection = DeliveryPlanningList.of(first, second);

			assertThat(process.checkNoneProcessed(selection).isAccepted()).isTrue();

			process.receive(selection);
			assertThat(process.received).containsExactlyInAnyOrder(first.getId(), second.getId());
		}

		@Test
		@DisplayName("a selection with no planning at all - only unplanned rows - is accepted")
		void allUnplannedSelectionIsAccepted()
		{
			assertThat(process.checkNoneProcessed(DeliveryPlanningList.EMPTY).isAccepted()).isTrue();
		}

		@Test
		@DisplayName("one processed row refuses the WHOLE selection, and the reason names it")
		void oneProcessedRowRefusesTheWholeSelection()
		{
			final DeliveryPlanning eligible = planning().build();
			final DeliveryPlanning processed = planning().processed(true).build();

			final ProcessPreconditionsResolution resolution =
					process.checkNoneProcessed(DeliveryPlanningList.of(eligible, processed));

			assertThat(resolution.isAccepted()).isFalse();
			assertThat(resolution.getRejectReason().translate("en_US"))
					.contains(String.valueOf(processed.getId().getRepoId()))
					.doesNotContain(String.valueOf(eligible.getId().getRepoId()));
		}

		@Test
		@DisplayName("EVERY offending row is named, not just the first one found")
		void everyOffendingRowIsNamed()
		{
			final DeliveryPlanning closed = planning().closed(true).processed(true).build();
			final DeliveryPlanning delivered = planning().processed(true).build();
			final DeliveryPlanning eligible = planning().build();

			final ProcessPreconditionsResolution resolution =
					process.checkNoneProcessed(DeliveryPlanningList.of(closed, eligible, delivered));

			assertThat(resolution.getRejectReason().translate("en_US"))
					.contains(String.valueOf(closed.getId().getRepoId()))
					.contains(String.valueOf(delivered.getId().getRepoId()))
					.doesNotContain(String.valueOf(eligible.getId().getRepoId()));
		}

		@Test
		@DisplayName("the refusal is ATOMIC: nothing is produced for the eligible rows either")
		void refusalProducesNothing()
		{
			final DeliveryPlanning eligible = planning().build();
			final DeliveryPlanning processed = planning().processed(true).build();

			assertThatThrownBy(() -> process.receive(DeliveryPlanningList.of(eligible, processed)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(String.valueOf(processed.getId().getRepoId()));

			assertThat(process.received)
					.as("nothing may be produced when the selection is refused")
					.isEmpty();
		}
	}

	/**
	 * The minimal shape of a real receive action: guard the WHOLE selection, then produce per row. It records
	 * what it produced so a refusal can be shown to produce nothing - a guard moved into the per-row loop, or
	 * degraded into a per-row skip, would leave {@code received} non-empty.
	 */
	private static class ReceivingTestProcess extends ReceiptLogisticsViewBasedProcess
	{
		private final List<DeliveryPlanningId> received = new ArrayList<>();

		@Override
		protected String doIt()
		{
			return MSG_OK;
		}

		private void receive(final DeliveryPlanningList selectedDeliveryPlannings)
		{
			assertNoneProcessed(selectedDeliveryPlannings);

			for (final DeliveryPlanning deliveryPlanning : selectedDeliveryPlannings)
			{
				received.add(deliveryPlanning.getId());
			}
		}
	}
}
