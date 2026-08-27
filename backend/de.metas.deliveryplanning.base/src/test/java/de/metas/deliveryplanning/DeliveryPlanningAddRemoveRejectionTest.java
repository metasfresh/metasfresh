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

import de.metas.deliveryplanning.DeliveryPlanningList.AdmissibilityField;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Whether a selection may be added to / removed from a delivery instruction, and - when it may not - WHICH single
 * reason the planner is given.
 * <p>
 * Three of these are the acceptance criteria themselves rather than incidental cases: a planning on a COMPLETED
 * instruction is refused outright by both actions, a CLOSED planning is refused by add-to but explicitly ALLOWED by
 * remove-from, and a selection spanning two directions is refused because the target picker correlates on exactly
 * one.
 * <p>
 * The repository is real, over the unit-test in-memory store, because the completed-instruction rule is decided by
 * the instruction's own {@code DocStatus} - mocking that away would test the wiring and not the rule.
 */
class DeliveryPlanningAddRemoveRejectionTest
{
	private static int nextId = 1;

	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)),
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class));
	}

	// ------------------------------------------------------------------ helpers

	private static DeliveryPlanning.DeliveryPlanningBuilder deliveryPlanning()
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.type(TransportDirection.Outgoing)
				.shipperId(ShipperId.ofRepoId(540001));
	}

	private static ShipperTransportationId deliveryInstruction(@NonNull final DocStatus docStatus)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocStatus(docStatus.getCode());
		record.setProcessed(!docStatus.isDrafted());
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	/**
	 * The rejection rendered as text. In unit-test mode {@code IMsgBL} renders an AD_Message as its key followed
	 * by its parameters, so the rendered text is exactly what lets an assertion see WHICH message was chosen AND
	 * which items it names, without any AD_Message row having to exist.
	 */
	private static String textOf(@NonNull final Optional<ITranslatableString> reason)
	{
		assertThat(reason).as("a rejection reason").isPresent();
		return reason.get().translate("en_US");
	}

	private String addToRejectionTextOf(@Nullable final ShipperTransportationId target, final DeliveryPlanning... deliveryPlannings)
	{
		return textOf(deliveryPlanningService.getAddToRejectionReason(DeliveryPlanningList.of(deliveryPlannings), target));
	}

	private static String keyOf(final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
	}

	// ------------------------------------------------------------------ add to

	@Test
	@DisplayName("add to: an open, unallocated selection of one direction is accepted onto a draft instruction")
	void addTo_admissibleSelectionIsAccepted()
	{
		assertThat(deliveryPlanningService.getAddToRejectionReason(
				DeliveryPlanningList.of(deliveryPlanning().build(), deliveryPlanning().build()),
				deliveryInstruction(DocStatus.Drafted)))
				.isEmpty();
	}

	@Test
	@DisplayName("add to: a planning already on ANOTHER draft instruction is accepted - that is the move")
	void addTo_onAnotherDraftInstructionIsTheMove()
	{
		final DeliveryPlanning onAnotherDraft = deliveryPlanning()
				.deliveryInstructionId(deliveryInstruction(DocStatus.Drafted))
				.build();

		assertThat(deliveryPlanningService.getAddToRejectionReason(
				DeliveryPlanningList.of(onAnotherDraft),
				deliveryInstruction(DocStatus.Drafted)))
				.isEmpty();
	}

	@Test
	@DisplayName("add to: a planning on a COMPLETED instruction refuses the whole selection, naming it")
	void addTo_onCompletedInstructionIsRefused()
	{
		final DeliveryPlanning onCompleted = deliveryPlanning()
				.deliveryInstructionId(deliveryInstruction(DocStatus.Completed))
				.build();

		assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), deliveryPlanning().build(), onCompleted))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction))
				.contains(String.valueOf(onCompleted.getId().getRepoId()));
	}

	@Test
	@DisplayName("add to: a closed planning is refused, naming it")
	void addTo_closedPlanningIsRefused()
	{
		final DeliveryPlanning closed = deliveryPlanning().closed(true).build();

		assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), deliveryPlanning().build(), closed))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings))
				.contains(String.valueOf(closed.getId().getRepoId()));
	}

	@Test
	@DisplayName("add to: a selection spanning two directions is refused, because the picker correlates on one")
	void addTo_twoDirectionsIsRefused()
	{
		assertThat(addToRejectionTextOf(
				deliveryInstruction(DocStatus.Drafted),
				deliveryPlanning().type(TransportDirection.Outgoing).build(),
				deliveryPlanning().type(TransportDirection.Incoming).build()))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection))
				.contains(keyOf(AdmissibilityField.Direction.getLabel()));
	}

	@Test
	@DisplayName("add to: a target that is no longer a draft is refused")
	void addTo_completedTargetIsRefused()
	{
		assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Completed), deliveryPlanning().build()))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_TargetInstructionNotDraft));
	}

	@Test
	@DisplayName("add to: the precondition, which has no target yet, still judges the selection")
	void addTo_preconditionWithoutTargetStillJudgesTheSelection()
	{
		// null target = the parameter dialog has not been shown yet
		assertThat(deliveryPlanningService.getAddToRejectionReason(
				DeliveryPlanningList.of(deliveryPlanning().build()),
				null))
				.isEmpty();

		assertThat(addToRejectionTextOf(null, deliveryPlanning().closed(true).build()))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings));
	}

	// ------------------------------------------------------------------ remove from

	@Test
	@DisplayName("remove from: a planning on a draft instruction is accepted")
	void removeFrom_onDraftInstructionIsAccepted()
	{
		final DeliveryPlanning allocated = deliveryPlanning()
				.deliveryInstructionId(deliveryInstruction(DocStatus.Drafted))
				.build();

		assertThat(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(allocated))).isEmpty();
	}

	@Test
	@DisplayName("remove from: a CLOSED planning is allowed - closing it is why it is coming off the truck")
	void removeFrom_closedPlanningIsAllowed()
	{
		final DeliveryPlanning closedAndAllocated = deliveryPlanning()
				.closed(true)
				.deliveryInstructionId(deliveryInstruction(DocStatus.Drafted))
				.build();

		assertThat(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(closedAndAllocated))).isEmpty();
	}

	@Test
	@DisplayName("remove from: a planning on a COMPLETED instruction is refused, naming it")
	void removeFrom_onCompletedInstructionIsRefused()
	{
		final DeliveryPlanning onCompleted = deliveryPlanning()
				.deliveryInstructionId(deliveryInstruction(DocStatus.Completed))
				.build();

		assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(onCompleted))))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction))
				.contains(String.valueOf(onCompleted.getId().getRepoId()));
	}

	@Test
	@DisplayName("remove from: a selection on no instruction at all is refused, naming the rows")
	void removeFrom_nothingAllocatedIsRefused()
	{
		final DeliveryPlanning notAllocated = deliveryPlanning().build();

		assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(notAllocated))))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NotOnDeliveryInstruction))
				.contains(String.valueOf(notAllocated.getId().getRepoId()));
	}

	@Test
	@DisplayName("remove from: an unallocated row alongside an allocated one does not refuse the selection")
	void removeFrom_unallocatedRowIsSkippedNotRefused()
	{
		final DeliveryPlanning allocated = deliveryPlanning()
				.deliveryInstructionId(deliveryInstruction(DocStatus.Drafted))
				.build();

		assertThat(deliveryPlanningService.getRemoveFromRejectionReason(
				DeliveryPlanningList.of(allocated, deliveryPlanning().build())))
				.isEmpty();
	}
}
