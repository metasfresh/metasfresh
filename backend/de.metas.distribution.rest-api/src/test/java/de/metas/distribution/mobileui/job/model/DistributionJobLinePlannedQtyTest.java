package de.metas.distribution.mobileui.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.distribution.mobileui.external_services.hu.HUInfo;
import de.metas.distribution.mobileui.external_services.product.ProductInfo;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link DistributionJobLine#getQtyMoved()} and {@link DistributionJobLine#isPlannedQtyFullyMoved()} — the
 * quantity-based completion predicate of the mobile distribution job.
 *
 * <p>The pure arithmetic (partial drop, exact match, in-transit, no steps) lives here because a Playwright run cannot
 * enumerate those four cases cheaply; the gate itself is proven end-to-end in the mobile UI.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DistributionJobLinePlannedQtyTest
{
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Stk", 0, 0);
	}

	@Test
	void partialDrop_isNotFullyMoved()
	{
		// This is exactly the case the pre-existing isFullyMoved() gets WRONG — the reason isPlannedQtyFullyMoved() exists:
		// steps are created on pick, so a single dropped step of 6 makes "every existing step was dropped" true
		// while 9 of the planned 15 have not been moved at all.
		final DistributionJobLine line = line("15", step(1, "6", true));

		assertThat(line.isFullyMoved()).isTrue();

		assertThat(line.isPlannedQtyFullyMoved()).isFalse();
		assertThat(line.getQtyMoved().toBigDecimal()).isEqualByComparingTo("6");

		// What the mover is told: both when the completion is refused and when the give-up affordance asks him to
		// confirm, so the abandoned quantity is named the same way in both places.
		assertThat(line.getQtyOutstanding().toBigDecimal()).isEqualByComparingTo("9");
		assertThat(line.describeQtyOutstanding().getDefaultValue()).contains("9").contains("P1");
	}

	@Test
	void severalDroppedStepsSummingToThePlannedQty_isFullyMoved()
	{
		final DistributionJobLine line = line("15", step(1, "10", true), step(2, "5", true));

		assertThat(line.getQtyMoved().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(line.isPlannedQtyFullyMoved()).isTrue();
	}

	@Test
	void pickedButNotDroppedStep_isNotFullyMoved()
	{
		// Picked and still on the trolley: in transit is not moved.
		final DistributionJobLine line = line("15", step(1, "15", false));

		assertThat(line.isPlannedQtyFullyMoved()).isFalse();
		assertThat(line.getQtyMoved().toBigDecimal()).isEqualByComparingTo("0");
	}

	@Test
	void noSteps_isNotFullyMoved()
	{
		final DistributionJobLine line = line("15");

		assertThat(line.getQtyMoved().toBigDecimal()).isEqualByComparingTo("0");
		assertThat(line.getQtyMoved().getUomId()).isEqualTo(line.getQtyToMove().getUomId());
		assertThat(line.isPlannedQtyFullyMoved()).isFalse();
	}

	private DistributionJobLine line(final String qtyToMove, final DistributionJobStep... steps)
	{
		return DistributionJobLine.builder()
				.id(DistributionJobLineId.ofDDOrderLineId(DDOrderLineId.ofRepoId(1)))
				.product(ProductInfo.builder()
						.productId(ProductId.ofRepoId(1000001))
						.caption(TranslatableStrings.anyLanguage("P1"))
						.build())
				.qtyToMove(Quantity.of(qtyToMove, uom))
				.pickFromLocator(locator(201))
				.dropToLocator(locator(301))
				.steps(ImmutableList.copyOf(steps))
				.build();
	}

	private DistributionJobStep step(final int scheduleId, final String qtyPicked, final boolean isDroppedToLocator)
	{
		return DistributionJobStep.builder()
				.id(DistributionJobStepId.ofScheduleId(DDOrderMoveScheduleId.ofRepoId(scheduleId)))
				.qtyToMoveTarget(Quantity.of(qtyPicked, uom))
				.pickFromHU(huInfo(scheduleId))
				.qtyPicked(Quantity.of(qtyPicked, uom))
				.isPickedFromLocator(true)
				.isDroppedToLocator(isDroppedToLocator)
				.build();
	}

	private static HUInfo huInfo(final int huId)
	{
		return HUInfo.builder()
				.id(HuId.ofRepoId(huId))
				.qrCode(HUQRCode.builder()
						.id(HUQRCodeUniqueId.random())
						.packingInfo(HUQRCodePackingInfo.builder()
								.huUnitType(HUQRCodeUnitType.TU)
								.packingInstructionsId(HuPackingInstructionsId.ofRepoId(540000))
								.caption("TU")
								.build())
						.attributes(ImmutableList.of())
						.build())
				.build();
	}

	private static LocatorInfo locator(final int locId)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(100, locId);
		return LocatorInfo.builder()
				.locatorId(locatorId)
				.qrCode(LocatorQRCode.builder().locatorId(locatorId).caption("L" + locId).build())
				.caption("L" + locId)
				.build();
	}
}
