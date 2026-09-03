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
import de.metas.deliveryplanning.interceptor.M_Delivery_Planning;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Closing a delivery planning sets the flag and NOTHING else: the allocation, its shipping package, the
 * {@code ReleaseNo} and the instruction's synced-down dates all survive, whether the instruction it rides on is
 * a draft or completed. Closed is a terminal indicator, not an action.
 */
class DeliveryPlanningCloseSemanticsTest
{
	private static final int PRODUCT_ID = 540010;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		// the REAL interceptor: it no longer watches IsClosed at all, and registering it is what proves a close
		// still reaches none of its remaining handlers
		POJOLookupMap.get().addModelValidator(new M_Delivery_Planning(deliveryPlanningService));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers

	private I_M_Delivery_Planning deliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private ShipperTransportationId deliveryInstruction(@NonNull final String documentNo, @NonNull final String docStatus)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(docStatus);
		InterfaceWrapperHelper.save(record);
		return ShipperTransportationId.ofRepoId(record.getM_ShipperTransportation_ID());
	}

	/** Puts the given planning on the given instruction the way a previous action (Combine/AddTo) would have left it. */
	private void allocateTo(@NonNull final ShipperTransportationId deliveryInstructionId, @NonNull final I_M_Delivery_Planning record)
	{
		final DeliveryPlanningId id = idOf(record);

		deliveryPlanningRepository.createAllocations(
				deliveryInstructionId,
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(id)
						.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
								.build())
						.build()));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(id), deliveryInstructionId);
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	private IQueryFilter<I_M_Delivery_Planning> selectionOf(final I_M_Delivery_Planning... records)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(
						I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID,
						Arrays.stream(records).map(DeliveryPlanningCloseSemanticsTest::idOf).collect(ImmutableList.toImmutableList()));
	}

	private List<I_M_Delivery_Planning_Alloc> allActiveAllocations()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	private boolean shippingPackageExists(final int shippingPackageId)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
				.create()
				.anyMatch();
	}

	private I_M_Delivery_Planning_Alloc activeAllocationOf(@NonNull final I_M_Delivery_Planning record)
	{
		return allActiveAllocations().stream()
				.filter(alloc -> alloc.getM_Delivery_Planning_ID() == record.getM_Delivery_Planning_ID())
				.findFirst()
				.orElseThrow(() -> new AssertionError("no active allocation for delivery planning " + record.getM_Delivery_Planning_ID()));
	}

	private int shippingPackageIdOf(@NonNull final I_M_Delivery_Planning record)
	{
		return activeAllocationOf(record).getM_ShippingPackage_ID();
	}

	private boolean shippingPackageIsActive(final int shippingPackageId)
	{
		return InterfaceWrapperHelper.load(shippingPackageId, I_M_ShippingPackage.class).isActive();
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("close: a planning allocated to a DRAFT instruction keeps its allocation, package, ReleaseNo and dates")
	void allocatedToDraftInstruction_changesNothingButTheFlag()
	{
		final ShipperTransportationId draft = deliveryInstruction("DRAFT-1", DocStatus.Drafted.getCode());
		// gives the instruction a date the allocation syncs down, so "the close changed nothing else" has
		// something observable to hold on to
		final I_M_ShipperTransportation draftRecord = InterfaceWrapperHelper.load(draft, I_M_ShipperTransportation.class);
		draftRecord.setETD(Timestamp.valueOf("2026-03-20 00:00:00"));
		InterfaceWrapperHelper.save(draftRecord);
		final I_M_Delivery_Planning planning = deliveryPlanning();
		allocateTo(draft, planning);
		final I_M_Delivery_Planning_Alloc allocBefore = activeAllocationOf(planning);
		final int allocationId = allocBefore.getM_Delivery_Planning_Alloc_ID();
		final int packageId = allocBefore.getM_ShippingPackage_ID();
		final String releaseNoBefore = reload(planning).getReleaseNo();
		assertThat(reload(planning).getETD()).as("sanity: the allocation synced the instruction's date down first").isNotNull();

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));

		final I_M_Delivery_Planning closed = reload(planning);
		assertThat(closed.isClosed()).isTrue();
		assertThat(closed.getReleaseNo()).as("the release number survives - closing releases nothing").isEqualTo(releaseNoBefore);
		assertThat(closed.getM_ShipperTransportation_ID()).as("and so does the instruction reference").isEqualTo(draft.getRepoId());
		assertThat(closed.getETD())
				.as("and the instruction's synced-down date - closing resets no dates")
				.isEqualTo(Timestamp.valueOf("2026-03-20 00:00:00"));
		assertThat(allActiveAllocations()).as("the allocation is still active").hasSize(1);
		assertThat(InterfaceWrapperHelper.load(allocationId, I_M_Delivery_Planning_Alloc.class).isActive()).isTrue();
		assertThat(shippingPackageIsActive(packageId)).as("and its shipping package").isTrue();
	}

	@Test
	@DisplayName("close: a planning allocated to a COMPLETED instruction is closed too - the state the complete and re-activate guards then refuse")
	void allocatedToCompletedInstruction_isAllowed()
	{
		final ShipperTransportationId completed = deliveryInstruction("COMPLETED-1", DocStatus.Completed.getCode());
		final I_M_Delivery_Planning planning = deliveryPlanning();
		allocateTo(completed, planning);
		final int packageId = shippingPackageIdOf(planning);
		final String releaseNoBefore = reload(planning).getReleaseNo();

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));

		final I_M_Delivery_Planning closed = reload(planning);
		assertThat(closed.isClosed()).as("closing is harmless on a completed instruction, so it is not refused").isTrue();
		assertThat(closed.getReleaseNo()).isEqualTo(releaseNoBefore);
		assertThat(closed.getM_ShipperTransportation_ID()).isEqualTo(completed.getRepoId());
		assertThat(allActiveAllocations()).as("the completed instruction keeps every load it carries").hasSize(1);
		assertThat(shippingPackageExists(packageId)).isTrue();
	}

	@Test
	@DisplayName("close: a selection of two closes both and leaves both allocations alone")
	void selectionOfTwo_closesBothAndTouchesNoAllocation()
	{
		final ShipperTransportationId draft = deliveryInstruction("DRAFT-2", DocStatus.Drafted.getCode());
		final I_M_Delivery_Planning first = deliveryPlanning();
		allocateTo(draft, first);
		final ShipperTransportationId completed = deliveryInstruction("COMPLETED-2", DocStatus.Completed.getCode());
		final I_M_Delivery_Planning second = deliveryPlanning();
		allocateTo(completed, second);

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(first, second));

		assertThat(reload(first).isClosed()).isTrue();
		assertThat(reload(second).isClosed()).isTrue();
		assertThat(allActiveAllocations()).as("both allocations survive").hasSize(2);
	}

	@Test
	@DisplayName("close precondition: a MIXED selection is refused before the button is offered, and names the closed one")
	void mixedSelectionIsRefusedBeforeTheButtonIsOffered()
	{
		final I_M_Delivery_Planning open = deliveryPlanning();
		final I_M_Delivery_Planning alreadyClosed = deliveryPlanning();
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(alreadyClosed));

		final Optional<ITranslatableString> rejection = deliveryPlanningService.getCloseRejectionReason(
				deliveryPlanningService.getBySelection(selectionOf(open, alreadyClosed)));

		// closing is all-or-nothing, so the precondition has to refuse the whole selection here: a precondition
		// that only asked "is any of them still open?" would offer the button and let doIt abort the batch
		assertThat(rejection).as("a rejection reason").isPresent();
		assertThat(rejection.get().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message()
						+ " - " + idOf(alreadyClosed).getRepoId());
	}

	@Test
	@DisplayName("close precondition: a selection in which every planning is still open is accepted")
	void selectionOfOnlyOpenPlanningsIsOffered()
	{
		final I_M_Delivery_Planning first = deliveryPlanning();
		final I_M_Delivery_Planning second = deliveryPlanning();

		assertThat(deliveryPlanningService.getCloseRejectionReason(
				deliveryPlanningService.getBySelection(selectionOf(first, second))))
				.isEmpty();
	}

	@Test
	@DisplayName("re-open precondition: a MIXED selection is refused before the button is offered, and names the open one")
	void reOpenOfMixedSelectionIsRefusedBeforeTheButtonIsOffered()
	{
		final I_M_Delivery_Planning open = deliveryPlanning();
		final I_M_Delivery_Planning alreadyClosed = deliveryPlanning();
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(alreadyClosed));

		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReOpenRejectionReason(
				deliveryPlanningService.getBySelection(selectionOf(open, alreadyClosed)));

		// re-opening is all-or-nothing, so the precondition has to refuse the whole selection here: a precondition
		// that only asked "is any of them closed?" would offer the button and let doIt abort the batch
		assertThat(rejection).as("a rejection reason").isPresent();
		assertThat(rejection.get().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_Open.toAD_Message()
						+ " - " + idOf(open).getRepoId());
	}

	@Test
	@DisplayName("re-open precondition: a selection in which every planning is already closed is accepted")
	void selectionOfOnlyClosedPlanningsIsOfferedForReOpen()
	{
		final I_M_Delivery_Planning first = deliveryPlanning();
		final I_M_Delivery_Planning second = deliveryPlanning();
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(first, second));

		assertThat(deliveryPlanningService.getReOpenRejectionReason(
				deliveryPlanningService.getBySelection(selectionOf(first, second))))
				.isEmpty();
	}

	// ------------------------------------------------------------------ Task Q10: Processed follows closed-or-delivered

	/**
	 * A planning is "delivered" the same way E3's {@code IsDelivered} virtual column defines it - an
	 * {@code M_InOut_ID} is set - without going through the real receipt/shipment flow (that interceptor is
	 * Task Q11's territory, deliberately untouched here).
	 */
	private static void markDelivered(@NonNull final I_M_Delivery_Planning record)
	{
		record.setM_InOut_ID(999999);
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * The invariant Task Q10 exists to enforce: {@code Processed == (IsClosed || IsDelivered)}. Reads
	 * {@code M_InOut_ID} directly rather than the generated {@code isDelivered()} getter - that getter proxies a
	 * DB-side virtual column ({@code CASE WHEN M_InOut_ID IS NOT NULL}), which the POJO in-memory test
	 * infrastructure this test class uses does not evaluate, so it would misreport "delivered" as false here even
	 * with {@code M_InOut_ID} set. Reading the column the virtual one is defined from is equally correct and is
	 * what production the invariant statement means - see the plan's Global Constraints on this choice.
	 */
	private static void assertInvariantHolds(@NonNull final I_M_Delivery_Planning record)
	{
		final boolean isDelivered = record.getM_InOut_ID() > 0;
		assertThat(record.isProcessed())
				.as("invariant Processed == (IsClosed || IsDelivered) for M_Delivery_Planning_ID=%s (IsClosed=%s, IsDelivered=%s)",
						record.getM_Delivery_Planning_ID(), record.isClosed(), isDelivered)
				.isEqualTo(record.isClosed() || isDelivered);
	}

	@Test
	@DisplayName("Q10: closing a delivered planning sets Processed, and reopening it KEEPS Processed set")
	void reopen_closedAndDelivered_keepsProcessedSet()
	{
		final I_M_Delivery_Planning planning = deliveryPlanning();
		markDelivered(planning);

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning closed = reload(planning);
		assertInvariantHolds(closed);
		assertThat(closed.isProcessed()).as("close always sets Processed").isTrue();

		deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning reopened = reload(planning);
		assertInvariantHolds(reopened);
		assertThat(reopened.isClosed()).isFalse();
		assertThat(reopened.isProcessed()).as("a delivered planning keeps Processed after reopen").isTrue();
	}

	@Test
	@DisplayName("Q10: closing an undelivered planning sets Processed, and reopening it CLEARS Processed")
	void reopen_closedButNotDelivered_clearsProcessed()
	{
		final I_M_Delivery_Planning planning = deliveryPlanning();

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning closed = reload(planning);
		assertInvariantHolds(closed);
		assertThat(closed.isProcessed()).as("close always sets Processed").isTrue();

		deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning reopened = reload(planning);
		assertInvariantHolds(reopened);
		assertThat(reopened.isClosed()).isFalse();
		assertThat(reopened.isProcessed()).as("an undelivered planning clears Processed after reopen").isFalse();
	}

	@Test
	@DisplayName("Q10: closing a planning that is ALREADY Processed (delivered, then reopened) exercises the skip branch - Processed stays true, never toggled")
	void close_alreadyProcessedFromDelivery_exercisesSkipBranch()
	{
		final I_M_Delivery_Planning planning = deliveryPlanning();
		markDelivered(planning);

		// closed once, then reopened while still delivered: leaves IsClosed=false, Processed=true - the ONLY
		// reachable state in which a SECOND close finds isProcessed() already true, i.e. the ONLY state that
		// exercises closeSelectedDeliveryPlannings' skip branch. Every other test in this class starts each
		// close from an unprocessed planning, so only the "set" branch ever ran before this test existed.
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));
		deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning reopened = reload(planning);
		assertInvariantHolds(reopened);
		assertThat(reopened.isClosed()).isFalse();
		assertThat(reopened.isProcessed()).as("delivered, so still Processed going into the second close").isTrue();

		// The skip branch's own save() still runs unconditionally (IsClosed itself is changing false->true),
		// so an "Updated timestamp changed" or "save call count" check would pass regardless of whether the
		// guarded setProcessed(true) executed - it is not an observable proxy for the skip, and a mock built
		// solely to intercept that one setter call would test the mock, not the behaviour. The state assertion
		// below IS discriminating, though: a regression that inverted the guard (e.g. skip-when-true became
		// clear-when-true) would flip Processed to false here, and the invariant assertion would fail too.
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning closedAgain = reload(planning);
		assertInvariantHolds(closedAgain);
		assertThat(closedAgain.isClosed()).isTrue();
		assertThat(closedAgain.isProcessed()).as("already Processed before this close - the skip branch must leave it true, not toggle it").isTrue();
	}

	@Test
	@DisplayName("Q10: a planning delivered WHILE closed stays Processed on reopen too - IsClosed dominates until it is lifted")
	void reopen_deliveredWhileClosed_keepsProcessed()
	{
		final I_M_Delivery_Planning planning = deliveryPlanning();
		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));
		assertInvariantHolds(reload(planning));

		// delivered AFTER close - simulates a document booked against an already-closed planning
		markDelivered(reload(planning));
		assertInvariantHolds(reload(planning));

		deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(planning));
		final I_M_Delivery_Planning reopened = reload(planning);
		assertInvariantHolds(reopened);
		assertThat(reopened.isProcessed()).as("delivered before reopen, so Processed must survive it").isTrue();
	}
}
