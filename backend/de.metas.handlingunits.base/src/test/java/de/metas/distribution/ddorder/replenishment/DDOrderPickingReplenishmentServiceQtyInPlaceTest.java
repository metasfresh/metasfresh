package de.metas.distribution.ddorder.replenishment;

import de.metas.business.BusinessTestHelper;
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
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The in-place quantity write at the lone {@link DDOrderPickingReplenishmentService#updateDDOrderLineQtyInPlace} site:
 * a DD_OrderLine whose goods are already on their way — an IN_PROGRESS {@code DD_Order_MoveSchedule}, the mover has
 * picked from the source but not yet dropped at the workstation — is FROZEN: the reconcile must not rewrite its
 * quantity, because the physical stock the mover is holding is already committed to that line's ordered qty.
 * <p>
 * The freeze is symmetric under movement: neither a grow nor a shrink may be written to a frozen line. A line
 * with no move under way stays freely editable in both directions.
 * <p>
 * The freeze signal is the line-level {@code hasInProgressSchedules} query, stubbed from the set each test declares via
 * {@link #createLine}; nothing declared means "not moving", so silence is never mistaken for a freeze.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderPickingReplenishmentServiceQtyInPlaceTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000001);

	private I_C_UOM uom;
	private UomId uomId;

	private DDOrderMoveScheduleService ddOrderMoveScheduleService;

	private DDOrderPickingReplenishmentService service;

	/** The lines each test froze via {@link #createLine}; the source of truth the freeze stub answers from. */
	private final Set<DDOrderLineId> linesWithMoveInProgress = new HashSet<>();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = BusinessTestHelper.createUOM("PCE");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		// Real DAO so the in-place write actually persists (and a refused write leaves the row untouched); the freeze
		// service is the only collaborator the decision reads, so the rest are plain mocks.
		ddOrderMoveScheduleService = mock(DDOrderMoveScheduleService.class);
		when(ddOrderMoveScheduleService.hasInProgressSchedules(any(DDOrderLineId.class)))
				.thenAnswer(invocation -> linesWithMoveInProgress.contains(invocation.<DDOrderLineId>getArgument(0)));

		service = new DDOrderPickingReplenishmentService(
				mock(PickingJobRepository.class),
				new DDOrderLowLevelDAO(),
				mock(DDOrderService.class),
				mock(DistributionNetworkRepository.class),
				mock(ITrxManager.class),
				mock(DDOrderReplenishmentEventPublisher.class),
				mock(PickingJobScheduleService.class),
				mock(WorkplaceService.class),
				ddOrderMoveScheduleService,
				new WarehouseRepository(),
				new DDOrderLineContributorRepository());
	}

	/**
	 * One replenishment DD_OrderLine of the given ordered qty; {@code frozen} records it as a line a mover is under way
	 * on (an IN_PROGRESS {@code DD_Order_MoveSchedule}), which the freeze stub answers from.
	 */
	private I_DD_OrderLine createLine(@NonNull final String qtyOrdered, final boolean frozen)
	{
		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setM_Product_ID(PRODUCT_ID.getRepoId());
		line.setC_UOM_ID(uomId.getRepoId());
		line.setQtyEntered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setTargetQty(new BigDecimal(qtyOrdered));
		InterfaceWrapperHelper.saveRecord(line);

		if (frozen)
		{
			linesWithMoveInProgress.add(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()));
		}
		return line;
	}

	private Quantity qty(@NonNull final String q) {return Quantity.of(q, uom);}

	private static void assertLineQtyIs(@NonNull final I_DD_OrderLine line, @NonNull final String expected)
	{
		final BigDecimal expectedBD = new BigDecimal(expected);
		assertThat(line.getQtyOrdered()).isEqualByComparingTo(expectedBD);
		assertThat(line.getQtyEntered()).isEqualByComparingTo(expectedBD);
		assertThat(line.getTargetQty()).isEqualByComparingTo(expectedBD);
	}

	@Test
	void aFrozenLine_isNotGrown()
	{
		final I_DD_OrderLine line = createLine("10", true);

		final boolean written = service.updateDDOrderLineQtyInPlace(line, qty("15"));

		assertThat(written).as("a frozen line's qty must not be grown in place").isFalse();
		assertLineQtyIs(line, "10");
	}

	@Test
	void aFrozenLine_isNotShrunk()
	{
		final I_DD_OrderLine line = createLine("10", true);

		final boolean written = service.updateDDOrderLineQtyInPlace(line, qty("7"));

		assertThat(written).as("a frozen line's qty must not be shrunk in place").isFalse();
		assertLineQtyIs(line, "10");
	}

	@Test
	void anIdleLine_isGrownAndShrunkNormally()
	{
		final I_DD_OrderLine line = createLine("10", false);

		final boolean grown = service.updateDDOrderLineQtyInPlace(line, qty("15"));
		assertThat(grown).as("an idle line grows freely").isTrue();
		assertLineQtyIs(line, "15");

		final boolean shrunk = service.updateDDOrderLineQtyInPlace(line, qty("7"));
		assertThat(shrunk).as("an idle line shrinks freely").isTrue();
		assertLineQtyIs(line, "7");
	}
}
