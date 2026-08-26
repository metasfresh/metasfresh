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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * What a whole-selection action costs in round trips.
 * <p>
 * Combine, Add to and Remove from all run synchronously on a grid selection of up to a hundred rows that the
 * planner is waiting on, and all three read the same delivery-planning records twice over - once to build the
 * allocation requests, once to stamp the {@code ReleaseNo}. Every one of those reads has to be ONE batch load, so
 * each test here asserts {@code times(n)} on the batch method AND {@code never()} on the per-row one: a test that
 * only checked the outcome would stay green with the per-row load put back, which is exactly how this defect
 * reached a second and a third call site after it had already been removed from {@code getBySelection}.
 * <p>
 * The repository is a spy over the REAL one, on the unit-test in-memory store, so the loads actually happen while
 * the call pattern stays countable.
 */
class DeliveryPlanningBatchLoadingTest
{
	/**
	 * Only ever read back as a {@code ProductId}: the planning carries its own UOM, so the stock UOM - the one
	 * lookup that would need a product record - is never consulted.
	 */
	private static final int PRODUCT_ID = 540010;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = Mockito.spy(new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers

	private I_M_Delivery_Planning deliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setM_Delivery_Planning_Type(X_M_Delivery_Planning.M_DELIVERY_PLANNING_TYPE_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private I_M_ShipperTransportation draftDeliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/**
	 * The selection the process would hand over. {@code extractDeliveryPlannings} is the one seam stubbed here -
	 * the selection itself is a WebUI-side artefact, and a fresh iterator per call is what a re-query would give.
	 */
	private IQueryFilter<I_M_Delivery_Planning> selectionOf(@NonNull final List<I_M_Delivery_Planning> records)
	{
		@SuppressWarnings("unchecked") final IQueryFilter<I_M_Delivery_Planning> filter = Mockito.mock(IQueryFilter.class);
		Mockito.doAnswer(invocation -> records.iterator())
				.when(deliveryPlanningRepository).extractDeliveryPlannings(filter);
		return filter;
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	private List<I_M_Delivery_Planning_Alloc> allocationsInLineNoOrder()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo).endOrderBy()
				.create()
				.list();
	}

	/**
	 * One batch load per read of the selection, and not a single per-row one.
	 */
	private void assertBatchLoadedExactly(final int batchLoads)
	{
		Mockito.verify(deliveryPlanningRepository, Mockito.times(batchLoads)).getByIds(Mockito.any());
		Mockito.verify(deliveryPlanningRepository, Mockito.never()).getById(Mockito.any());
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("add-to reads the selection in two batch loads - never one planning at a time")
	void addToBatchLoadsTheSelection()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);
		final I_M_ShipperTransportation target = draftDeliveryInstruction("TARGET-1");

		deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(target.getM_ShipperTransportation_ID()));

		// one for the allocation requests, one for the ReleaseNo stamping
		assertBatchLoadedExactly(2);

		// the batch did not cost the outcome: every planning is on the target and carries its own ReleaseNo
		for (final I_M_Delivery_Planning record : records)
		{
			final I_M_Delivery_Planning stamped = reload(record);
			assertThat(stamped.getM_ShipperTransportation_ID()).isEqualTo(target.getM_ShipperTransportation_ID());
			assertThat(stamped.getReleaseNo()).startsWith("TARGET-1-" + record.getM_Delivery_Planning_ID() + "-");
		}

		// nor the ORDER: the requests reach createAllocations in the order the ids were given, so the LineNos
		// follow the selection rather than the encounter order of the batch query
		assertThat(allocationsInLineNoOrder())
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID, I_M_Delivery_Planning_Alloc::getLineNo)
				.containsExactly(
						tuple(records.get(0).getM_Delivery_Planning_ID(), 10),
						tuple(records.get(1).getM_Delivery_Planning_ID(), 20),
						tuple(records.get(2).getM_Delivery_Planning_ID(), 30));
	}

	@Test
	@DisplayName("remove-from reads the selection in one batch load - never one planning at a time")
	void removeFromBatchLoadsTheSelection()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);
		final I_M_ShipperTransportation source = draftDeliveryInstruction("SOURCE-2");
		deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(source.getM_ShipperTransportation_ID()));

		Mockito.clearInvocations(deliveryPlanningRepository);
		deliveryPlanningService.removeFrom(selection);

		assertBatchLoadedExactly(1);

		for (final I_M_Delivery_Planning record : records)
		{
			final I_M_Delivery_Planning cleared = reload(record);
			assertThat(cleared.getReleaseNo()).isNull();
			assertThat(cleared.getM_ShipperTransportation_ID()).isLessThanOrEqualTo(0);
		}
	}

	@Test
	@DisplayName("stamping from an already-created instruction reads the selection in one batch load")
	void stampingFromAnAlreadyLoadedInstructionBatchLoadsTheSelection()
	{
		// the overload combine() uses: it has just created the instruction, so it already holds the record
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final I_M_ShipperTransportation deliveryInstruction = draftDeliveryInstruction("COMBINED-3");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(
				records.stream().map(DeliveryPlanningBatchLoadingTest::idOf).collect(ImmutableList.toImmutableList()),
				deliveryInstruction);

		assertBatchLoadedExactly(1);

		for (final I_M_Delivery_Planning record : records)
		{
			assertThat(reload(record).getReleaseNo()).startsWith("COMBINED-3-" + record.getM_Delivery_Planning_ID() + "-");
		}
	}

	@Test
	@DisplayName("a dangling id throws instead of dropping that planning out of the batch")
	void aDanglingIdThrows()
	{
		final I_M_Delivery_Planning present = deliveryPlanning();
		final DeliveryPlanningId dangling = DeliveryPlanningId.ofRepoId(999999);
		final I_M_ShipperTransportation deliveryInstruction = draftDeliveryInstruction("COMBINED-4");

		assertThatThrownBy(() -> deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(
				ImmutableList.of(idOf(present), dangling),
				deliveryInstruction))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(I_M_Delivery_Planning.Table_Name);

		assertThat(reload(present).getReleaseNo())
				.as("the whole batch is resolved before the first write, so nothing is half-stamped")
				.isNull();
	}

	/**
	 * The half of add-to's all-or-nothing guarantee that a unit test can prove: the rejection is decided for the
	 * WHOLE selection before the first write, so an inadmissible row leaves the admissible ones untouched rather
	 * than half-moved.
	 * <p>
	 * The other half - the transaction rolling the writes back when one fails PART-WAY - is not assertable here:
	 * the unit-test store has no rollback (its {@code PlainTrx.rollbackNative} only flips a status flag), so a
	 * failure-injection test would go red against correct code. That half belongs to a cucumber scenario, where the
	 * transaction is real.
	 */
	@Test
	@DisplayName("an inadmissible row in the selection leaves the admissible ones untouched")
	void anInadmissibleRowStopsTheWholeMove()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);

		// one of them is already on a COMPLETED instruction, which neither action may move it off
		final I_M_ShipperTransportation completed = draftDeliveryInstruction("COMPLETED-5");
		completed.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.save(completed);
		deliveryPlanningRepository.createAllocations(
				ShipperTransportationId.ofRepoId(completed.getM_ShipperTransportation_ID()),
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(idOf(records.get(1)))
						.productId(ProductId.ofRepoId(PRODUCT_ID))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						.build()));
		final int allocationsBefore = allocationsInLineNoOrder().size();

		final I_M_ShipperTransportation target = draftDeliveryInstruction("TARGET-5");
		assertThatThrownBy(() -> deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(target.getM_ShipperTransportation_ID())))
				.isInstanceOf(AdempiereException.class);

		assertThat(allocationsInLineNoOrder())
				.as("nothing was allocated to the target, and nothing was taken off the completed instruction")
				.hasSize(allocationsBefore);
		for (final I_M_Delivery_Planning record : records)
		{
			assertThat(reload(record).getReleaseNo())
					.as("no planning of the selection was re-stamped")
					.isNull();
		}
	}
}
