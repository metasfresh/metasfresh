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

import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import java.util.Optional;

import static de.metas.deliveryplanning.DeliveryPlanningAllocTestHelper.allocatedTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Whether a selection may be added to / moved to / removed from a delivery instruction, and - when it may not -
 * WHICH single reason the planner is given.
 */
class DeliveryPlanningAddRemoveRejectionTest
{
	private static int nextId = 1;

	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningRepository deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionRepository,
				deliveryInstructionService,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());
	}

	// ------------------------------------------------------------------ helpers

	private static DeliveryPlanning.DeliveryPlanningBuilder deliveryPlanning()
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.transportDirection(TransportDirection.Outgoing)
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

	private static String textOf(@NonNull final Optional<ITranslatableString> reason)
	{
		assertThat(reason).as("a rejection reason").isPresent();
		return reason.get().translate("en_US");
	}

	private String addToRejectionTextOf(@Nullable final ShipperTransportationId target, final DeliveryPlanning... deliveryPlannings)
	{
		return textOf(deliveryPlanningService.getAddToRejectionReason(DeliveryPlanningList.of(deliveryPlannings), target));
	}

	private String moveToRejectionTextOf(@Nullable final ShipperTransportationId target, final DeliveryPlanning... deliveryPlannings)
	{
		return textOf(deliveryPlanningService.getMoveToRejectionReason(DeliveryPlanningList.of(deliveryPlannings), target));
	}

	private static String keyOf(final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
	}

	@Nested
	@DisplayName("add to")
	class AddTo
	{
		@Test
		@DisplayName("an open, unallocated selection of one direction is accepted onto a draft instruction")
		void admissibleSelectionIsAccepted()
		{
			assertThat(deliveryPlanningService.getAddToRejectionReason(
					DeliveryPlanningList.of(deliveryPlanning().build(), deliveryPlanning().build()),
					deliveryInstruction(DocStatus.Drafted)))
					.isEmpty();
		}

		@Test
		@DisplayName("a planning already on ANOTHER draft instruction is REFUSED, and the reason names Move")
		void onAnotherDraftInstructionIsRefusedAndPointsAtMove()
		{
			final DeliveryPlanning onAnotherDraft = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), onAnotherDraft))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction_UseMove)
							+ " - " + onAnotherDraft.getId().getRepoId());
		}

		@Test
		@DisplayName("ONE allocated row refuses the WHOLE selection - the action is all-or-nothing, not partly performed")
		void oneAllocatedRowRefusesTheWholeSelection()
		{
			final DeliveryPlanning allocated = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), deliveryPlanning().build(), allocated))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction_UseMove)
							+ " - " + allocated.getId().getRepoId());
		}

		@Test
		@DisplayName("a planning on a COMPLETED instruction refuses the whole selection, naming it")
		void onCompletedInstructionIsRefused()
		{
			final DeliveryPlanning onCompleted = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Completed)))
					.build();

			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), deliveryPlanning().build(), onCompleted))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction)
							+ " - " + onCompleted.getId().getRepoId());
		}

		@Test
		@DisplayName("a closed planning is refused, naming it")
		void closedPlanningIsRefused()
		{
			final DeliveryPlanning closed = deliveryPlanning().closed(true).build();

			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), deliveryPlanning().build(), closed))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closed.getId().getRepoId());
		}

		@Test
		@DisplayName("a selection spanning two directions is refused, because the picker correlates on one")
		void twoDirectionsIsRefused()
		{
			assertThat(addToRejectionTextOf(
					deliveryInstruction(DocStatus.Drafted),
					deliveryPlanning().transportDirection(TransportDirection.Outgoing).build(),
					deliveryPlanning().transportDirection(TransportDirection.Incoming).build()))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection)
							+ " - " + keyOf(AggregationKeyField.Direction.getLabel()));
		}

		@Test
		@DisplayName("a target that is no longer a draft is refused")
		void completedTargetIsRefused()
		{
			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Completed), deliveryPlanning().build()))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_TargetInstructionNotDraft));
		}

		@Test
		@DisplayName("the precondition, which has no target yet, still judges the selection")
		void preconditionWithoutTargetStillJudgesTheSelection()
		{
			// null target = the parameter dialog has not been shown yet
			assertThat(deliveryPlanningService.getAddToRejectionReason(
					DeliveryPlanningList.of(deliveryPlanning().build()),
					null))
					.isEmpty();

			final DeliveryPlanning closed = deliveryPlanning().closed(true).build();

			assertThat(addToRejectionTextOf(null, closed))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closed.getId().getRepoId());
		}

		@Test
		@DisplayName("a planning on a draft AND a completed instruction is refused - ANY non-draft leg forbids")
		void anyNonDraftInstructionRefuses()
		{
			final DeliveryPlanning onBoth = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted), deliveryInstruction(DocStatus.Completed)))
					.build();

			assertThat(addToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), onBoth))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction)
							+ " - " + onBoth.getId().getRepoId());
		}
	}

	@Nested
	@DisplayName("remove from")
	class RemoveFrom
	{
		@Test
		@DisplayName("a planning on a draft instruction is accepted")
		void onDraftInstructionIsAccepted()
		{
			final DeliveryPlanning allocated = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(allocated))).isEmpty();
		}

		/**
		 * Reversed contract: removal used to be the one action a closed planning was still allowed. It is not - removal
		 * deactivates the allocation, drops the release number and resets the dates, which is exactly the mutation
		 * closing forbids. Re-open first, then remove.
		 */
		@Test
		@DisplayName("a CLOSED planning is REFUSED, naming it - re-open it first")
		void closedPlanningIsRefused()
		{
			final DeliveryPlanning closedAndAllocated = deliveryPlanning()
					.closed(true)
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(closedAndAllocated))))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closedAndAllocated.getId().getRepoId());
		}

		@Test
		@DisplayName("ONE closed row refuses the whole selection - the action is all-or-nothing")
		void oneClosedRowRefusesTheWholeSelection()
		{
			final DeliveryPlanning open = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();
			final DeliveryPlanning closed = deliveryPlanning()
					.closed(true)
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(open, closed))))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closed.getId().getRepoId());
		}

		/**
		 * Reachable only through a re-opened planning: a closed one is refused by the rule above, so the sequence the
		 * planner is pointed at - re-open, then remove - is the one that has to work.
		 */
		@Test
		@DisplayName("a RE-OPENED planning is accepted again")
		void reOpenedPlanningIsAccepted()
		{
			final DeliveryPlanning reOpened = deliveryPlanning()
					.closed(false)
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(reOpened))).isEmpty();
		}

		@Test
		@DisplayName("a planning on a COMPLETED instruction is refused, naming it")
		void onCompletedInstructionIsRefused()
		{
			final DeliveryPlanning onCompleted = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Completed)))
					.build();

			assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(onCompleted))))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction)
							+ " - " + onCompleted.getId().getRepoId());
		}

		@Test
		@DisplayName("a selection on no instruction at all is refused, naming the rows")
		void nothingAllocatedIsRefused()
		{
			final DeliveryPlanning notAllocated = deliveryPlanning().build();

			assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(notAllocated))))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NotOnDeliveryInstruction)
							+ " - " + notAllocated.getId().getRepoId());
		}

		/**
		 * A planning on several legs, one of them already completed: ANY non-draft instruction refuses - the completed
		 * leg cannot be altered, and partially performing the action is exactly what these rules refuse.
		 */
		@Test
		@DisplayName("a planning on a draft AND a completed instruction is refused - ANY non-draft leg forbids")
		void anyNonDraftInstructionRefuses()
		{
			final DeliveryPlanning onBoth = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted), deliveryInstruction(DocStatus.Completed)))
					.build();

			assertThat(textOf(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(onBoth))))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction)
							+ " - " + onBoth.getId().getRepoId());
		}

		@Test
		@DisplayName("a planning on TWO draft instructions is accepted - every leg is still alterable")
		void allDraftInstructionsIsAccepted()
		{
			final DeliveryPlanning onTwoDrafts = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted), deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(deliveryPlanningService.getRemoveFromRejectionReason(DeliveryPlanningList.of(onTwoDrafts))).isEmpty();
		}

		@Test
		@DisplayName("an unallocated row alongside an allocated one does not refuse the selection")
		void unallocatedRowIsSkippedNotRefused()
		{
			final DeliveryPlanning allocated = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(deliveryPlanningService.getRemoveFromRejectionReason(
					DeliveryPlanningList.of(allocated, deliveryPlanning().build())))
					.isEmpty();
		}
	}

	@Nested
	@DisplayName("move to")
	class MoveTo
	{
		/**
		 * The precondition half of the closed refusal {@code closedPlanningIsRefused} below already covers with a
		 * target: with NO target the button's state is decided, and that is the moment the closed row has to make Move
		 * unavailable rather than only failing once the planner has picked a destination.
		 */
		@Test
		@DisplayName("the precondition, which has no target yet, already refuses a closed planning")
		void closedPlanningIsRefusedByThePreconditionWithoutTarget()
		{
			final DeliveryPlanning closedAndAllocated = deliveryPlanning()
					.closed(true)
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			// null target = the parameter dialog has not been shown yet, which is when the button's state is decided
			assertThat(moveToRejectionTextOf(null, closedAndAllocated))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closedAndAllocated.getId().getRepoId());
		}

		@Test
		@DisplayName("a planning on ANOTHER draft instruction is accepted onto a draft target")
		void onAnotherDraftInstructionIsAccepted()
		{
			final DeliveryPlanning onAnotherDraft = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(deliveryPlanningService.getMoveToRejectionReason(
					DeliveryPlanningList.of(onAnotherDraft),
					deliveryInstruction(DocStatus.Drafted)))
					.isEmpty();
		}

		/**
		 * The other half of the exclusivity: exactly the selection add-to accepts is the one move-to refuses, so a
		 * planner is never offered both and never offered neither.
		 */
		@Test
		@DisplayName("an UNALLOCATED planning is refused, naming it - that selection belongs to Add")
		void unallocatedPlanningIsRefused()
		{
			final DeliveryPlanning notAllocated = deliveryPlanning().build();

			assertThat(moveToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), notAllocated))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NotOnDeliveryInstruction)
							+ " - " + notAllocated.getId().getRepoId());
		}

		@Test
		@DisplayName("ONE unallocated row refuses the WHOLE selection - all-or-nothing, unlike remove-from's skip")
		void oneUnallocatedRowRefusesTheWholeSelection()
		{
			final DeliveryPlanning allocated = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();
			final DeliveryPlanning notAllocated = deliveryPlanning().build();

			assertThat(moveToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), allocated, notAllocated))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NotOnDeliveryInstruction)
							+ " - " + notAllocated.getId().getRepoId());
		}

		@Test
		@DisplayName("a planning on a COMPLETED instruction refuses the whole selection, naming it")
		void onCompletedInstructionIsRefused()
		{
			final DeliveryPlanning onCompleted = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Completed)))
					.build();
			final DeliveryPlanning onDraft = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(moveToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), onDraft, onCompleted))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_OnCompletedDeliveryInstruction)
							+ " - " + onCompleted.getId().getRepoId());
		}

		@Test
		@DisplayName("a closed planning is refused, naming it - it is still being put ON an instruction")
		void closedPlanningIsRefused()
		{
			final DeliveryPlanning closed = deliveryPlanning()
					.closed(true)
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(moveToRejectionTextOf(deliveryInstruction(DocStatus.Drafted), closed))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
							+ " - " + closed.getId().getRepoId());
		}

		@Test
		@DisplayName("a target that is no longer a draft is refused")
		void completedTargetIsRefused()
		{
			final DeliveryPlanning onDraft = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			assertThat(moveToRejectionTextOf(deliveryInstruction(DocStatus.Completed), onDraft))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_TargetInstructionNotDraft));
		}

		@Test
		@DisplayName("the precondition, which has no target yet, still judges the selection")
		void preconditionWithoutTargetStillJudgesTheSelection()
		{
			final DeliveryPlanning onDraft = deliveryPlanning()
					.allocations(allocatedTo(deliveryInstruction(DocStatus.Drafted)))
					.build();

			// null target = the parameter dialog has not been shown yet
			assertThat(deliveryPlanningService.getMoveToRejectionReason(DeliveryPlanningList.of(onDraft), null)).isEmpty();

			final DeliveryPlanning notAllocated = deliveryPlanning().build();

			assertThat(moveToRejectionTextOf(null, notAllocated))
					.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NotOnDeliveryInstruction)
							+ " - " + notAllocated.getId().getRepoId());
		}
	}
}
