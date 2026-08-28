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
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The {@code M_Delivery_Planning} AFTER_CHANGE(IsClosed) interceptor: closing a planning allocated
 * to a DRAFT instruction deactivates the allocation and its shipping package; closing one allocated to a COMPLETED
 * instruction is refused, pointing at Re-Activate.
 * <p>
 * Exercised through the REAL, registered {@code M_Delivery_Planning} interceptor (via
 * {@link POJOLookupMap#addModelValidator}, the same mechanism {@link DeliveryPlanningCancelVoidStalenessTest} uses)
 * so {@code closeSelectedDeliveryPlannings}'s save genuinely fires the interceptor - not merely assumed.
 */
class DeliveryPlanningClosedInterceptorTest
{
	private static final int PRODUCT_ID = 540010;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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

		// the REAL interceptor, so IsClosed transitions genuinely fire DeliveryPlanningService#onDeliveryPlanningClosed
		POJOLookupMap.get().addModelValidator(new M_Delivery_Planning(deliveryPlanningService));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers (mirrors DeliveryPlanningMoveAndRemovalTest)

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
						.productId(ProductId.ofRepoId(PRODUCT_ID))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.build()));

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(ImmutableList.of(id), deliveryInstructionId);
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static String keyOf(@NonNull final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
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
						Arrays.stream(records).map(DeliveryPlanningClosedInterceptorTest::idOf).collect(ImmutableList.toImmutableList()));
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
	@DisplayName("close: a planning allocated to a DRAFT instruction has its allocation and shipping package deactivated")
	void close_allocatedToDraftInstruction_deactivatesAllocationAndPackage()
	{
		final ShipperTransportationId draft = deliveryInstruction("DRAFT-1", DocStatus.Drafted.getCode());
		// gives the instruction a date to contaminate the planning with via the sync-down, so the close's reset
		// has something observable to undo
		final I_M_ShipperTransportation draftRecord = InterfaceWrapperHelper.load(draft, I_M_ShipperTransportation.class);
		draftRecord.setETD(Timestamp.valueOf("2026-03-20 00:00:00"));
		InterfaceWrapperHelper.save(draftRecord);
		final I_M_Delivery_Planning planning = deliveryPlanning();
		allocateTo(draft, planning);
		final I_M_Delivery_Planning_Alloc allocBefore = activeAllocationOf(planning);
		final int allocationId = allocBefore.getM_Delivery_Planning_Alloc_ID();
		final int packageId = allocBefore.getM_ShippingPackage_ID();
		assertThat(reload(planning).getETD()).as("sanity: allocation synced the instruction's date down first").isNotNull();

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));

		final I_M_Delivery_Planning closed = reload(planning);
		assertThat(closed.isClosed()).isTrue();
		assertThat(closed.getReleaseNo()).as("the removal clears the release number, same as removeFrom").isNull();
		assertThat(closed.getM_ShipperTransportation_ID()).as("and the instruction reference").isLessThanOrEqualTo(0);
		assertThat(closed.getETD())
				.as("the close reset the date - the planning has no order to derive one from, so the instruction's is gone, not carried over")
				.isNull();
		assertThat(allActiveAllocations()).as("no active allocation remains").isEmpty();
		assertThat(InterfaceWrapperHelper.load(allocationId, I_M_Delivery_Planning_Alloc.class).isActive())
				.as("the allocation row survives, deactivated - the re-booking audit trail this task exists for")
				.isFalse();
		assertThat(shippingPackageIsActive(packageId)).as("its shipping package is deactivated too, not deleted").isFalse();
	}

	@Test
	@DisplayName("close: a planning allocated to a COMPLETED instruction is refused, pointing at Re-Activate")
	void close_allocatedToCompletedInstruction_refused()
	{
		final ShipperTransportationId completed = deliveryInstruction("COMPLETED-1", DocStatus.Completed.getCode());
		final I_M_Delivery_Planning planning = deliveryPlanning();
		allocateTo(completed, planning);
		final int packageId = shippingPackageIdOf(planning);

		// unit-test mode has no AD_Message loaded to translate against (same as DeliveryPlanningClosedGuardsTest's
		// "@Closed@" token), so the message KEY is what identifies the rejection - it is the migration's English
		// translation ("Re-activate the delivery instruction first") that carries the user-facing wording
		assertThatThrownBy(() -> deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_CloseOnCompletedInstruction))
				.hasMessageContaining(String.valueOf(idOf(planning).getRepoId()));

		final I_M_Delivery_Planning reloaded = reload(planning);
		assertThat(reloaded.isClosed()).as("the refused close must not have applied").isFalse();
		assertThat(allActiveAllocations()).as("the allocation must survive the refusal").hasSize(1);
		assertThat(shippingPackageExists(packageId)).isTrue();
	}

	@Test
	@DisplayName("close: a mixed selection (one valid, one refused) applies NOTHING - not even the valid row")
	void close_mixedSelection_appliesNothing()
	{
		final ShipperTransportationId draft = deliveryInstruction("DRAFT-2", DocStatus.Drafted.getCode());
		final I_M_Delivery_Planning valid = deliveryPlanning();
		allocateTo(draft, valid);
		final int validPackageId = shippingPackageIdOf(valid);

		final ShipperTransportationId completed = deliveryInstruction("COMPLETED-3", DocStatus.Completed.getCode());
		final I_M_Delivery_Planning refused = deliveryPlanning();
		allocateTo(completed, refused);

		assertThatThrownBy(() -> deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(valid, refused)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_CloseOnCompletedInstruction));

		final I_M_Delivery_Planning reloadedValid = reload(valid);
		assertThat(reloadedValid.isClosed()).as("the valid row must not have been closed - nothing was applied").isFalse();
		assertThat(reloadedValid.getReleaseNo()).as("and its ReleaseNo must survive").isNotNull();
		assertThat(shippingPackageExists(validPackageId)).as("the valid planning's shipping package must survive").isTrue();
		assertThat(allActiveAllocations()).as("both allocations must survive - nothing was applied").hasSize(2);

		assertThat(reload(refused).isClosed()).isFalse();
	}

	@Test
	@DisplayName("close: an unallocated planning succeeds and is a no-op on allocations")
	void close_unallocatedPlanning_succeedsNoOpOnAllocations()
	{
		final I_M_Delivery_Planning planning = deliveryPlanning();

		deliveryPlanningService.closeSelectedDeliveryPlannings(selectionOf(planning));

		final I_M_Delivery_Planning closed = reload(planning);
		assertThat(closed.isClosed()).isTrue();
		assertThat(allActiveAllocations()).isEmpty();
	}

	@Test
	@DisplayName("reopen: an open planning still errors instead of doing nothing - unaffected by the new interceptor")
	void reOpen_openPlanningStillErrors()
	{
		final I_M_Delivery_Planning open = deliveryPlanning();

		assertThatThrownBy(() -> deliveryPlanningService.reOpenSelectedDeliveryPlannings(selectionOf(open)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Closed=N");

		assertThat(reload(open).isClosed()).isFalse();
	}

	@Test
	@DisplayName("an unrelated column change does not trigger the interceptor - ifColumnsChanged actually filters")
	void unrelatedColumnChange_doesNotTriggerInterceptor()
	{
		// closed already, WITHOUT going through the normal close flow (which would have deactivated the allocation) -
		// allocateTo bypasses that ordering the same way close_allocatedToCompletedInstruction_refused's setup
		// does, so the interceptor's own onDeliveryPlanningClosed body ("if (isClosed())") is reached below with
		// an allocation still present; only ifColumnsChanged stands between that body and every future save
		final I_M_Delivery_Planning planning = deliveryPlanning();
		planning.setIsClosed(true);
		InterfaceWrapperHelper.save(planning);

		final ShipperTransportationId draft = deliveryInstruction("DRAFT-3", DocStatus.Drafted.getCode());
		allocateTo(draft, planning);
		final int packageId = shippingPackageIdOf(planning);

		final I_M_Delivery_Planning toUpdate = reload(planning);
		toUpdate.setWayBillNo("WB-123");
		InterfaceWrapperHelper.save(toUpdate);

		final I_M_Delivery_Planning reloaded = reload(planning);
		assertThat(reloaded.getWayBillNo()).isEqualTo("WB-123");
		assertThat(reloaded.isClosed()).isTrue();
		assertThat(allActiveAllocations()).as("the allocation is untouched by the unrelated save").hasSize(1);
		assertThat(shippingPackageExists(packageId)).as("and its shipping package").isTrue();
	}
}
