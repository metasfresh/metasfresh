package de.metas.distribution.ddorder.replenishment;

import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseRepository;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Which alloc rows the reconcile's trailing sweep is allowed to drop: only those of a DD_Order that is genuinely gone.
 * A CLOSED order still answers "which DD_Order served this delivery?", so its rows must survive.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceDeadDDOrderSweepTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000001);
	private static final ProductId OTHER_PRODUCT_ID = ProductId.ofRepoId(1000002);
	private static final LocatorId LOCATOR_TO_ID = LocatorId.ofRepoId(2000001, 2000002);
	private static final UomId UOM_ID = UomId.ofRepoId(3000001);

	private DDOrderPickingReplenishmentService service;
	private DDOrderReplenishmentGroupKey groupKey;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Only the DD_Order/DD_OrderLine loads are exercised, so a real DAO plus mocks for the rest suffice.
		service = new DDOrderPickingReplenishmentService(
				mock(PickingJobRepository.class),
				new DDOrderLowLevelDAO(),
				mock(DDOrderService.class),
				mock(DistributionNetworkRepository.class),
				mock(ITrxManager.class),
				mock(DDOrderReplenishmentEventPublisher.class),
				mock(PickingJobScheduleService.class),
				mock(WorkplaceService.class),
				mock(DDOrderMoveScheduleService.class),
				new WarehouseRepository(),
				mock(DDOrderLineContributorRepository.class));

		groupKey = DDOrderReplenishmentGroupKey.builder()
				.productId(PRODUCT_ID)
				.locatorToId(LOCATOR_TO_ID)
				.uomId(UOM_ID)
				.build();
	}

	private DDOrderLineId createLine(@NonNull final String docStatus, final boolean active, @NonNull final ProductId productId)
	{
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setDocStatus(docStatus);
		ddOrder.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(ddOrder);

		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setDD_Order_ID(ddOrder.getDD_Order_ID());
		line.setM_Product_ID(productId.getRepoId());
		line.setM_LocatorTo_ID(LOCATOR_TO_ID.getRepoId());
		line.setC_UOM_ID(UOM_ID.getRepoId());
		InterfaceWrapperHelper.saveRecord(line);

		return DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID());
	}

	@Test
	void aClosedOrdersLineIsNotSwept_onlyVoidedReversedAndDeactivatedOnesAre()
	{
		final DDOrderLineId closedLineId = createLine(X_DD_Order.DOCSTATUS_Closed, true, PRODUCT_ID);
		final DDOrderLineId completedLineId = createLine(X_DD_Order.DOCSTATUS_Completed, true, PRODUCT_ID);
		final DDOrderLineId voidedLineId = createLine(X_DD_Order.DOCSTATUS_Voided, true, PRODUCT_ID);
		// Reverse-Correct delegates to voidIt (reservations cleared, lines processed) but lands on RE, not VO.
		final DDOrderLineId reversedLineId = createLine(X_DD_Order.DOCSTATUS_Reversed, true, PRODUCT_ID);
		final DDOrderLineId deactivatedLineId = createLine(X_DD_Order.DOCSTATUS_Completed, false, PRODUCT_ID);
		// Reactivate/Unlock leave a still-completable order, which must keep its association.
		final DDOrderLineId inProgressLineId = createLine(X_DD_Order.DOCSTATUS_InProgress, true, PRODUCT_ID);
		final DDOrderLineId draftedLineId = createLine(X_DD_Order.DOCSTATUS_Drafted, true, PRODUCT_ID);
		final DDOrderLineId voidedOfAnotherGroupLineId = createLine(X_DD_Order.DOCSTATUS_Voided, true, OTHER_PRODUCT_ID);

		final ImmutableSet<DDOrderLineId> actual = service.findLineIdsOfDeadDDOrders(
				ImmutableSet.of(closedLineId, completedLineId, voidedLineId, reversedLineId, deactivatedLineId,
						inProgressLineId, draftedLineId, voidedOfAnotherGroupLineId),
				groupKey);

		assertThat(actual).containsExactlyInAnyOrder(voidedLineId, reversedLineId, deactivatedLineId);
	}
}
