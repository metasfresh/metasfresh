package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.shelflife.ShelfLifeTooShortException;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test for the picking shelf-life guard wired in {@code PickingJobPickCommand}.
 *
 * <p>Tests three cases:
 * <ol>
 *   <li><b>Guard fires</b>: workplace flag ON + HU best-before undercuts (bestBefore &lt; deliveryDate + guaranteedDays)
 *       + {@code isShelfLifeConfirmed=false} → {@link ShelfLifeTooShortException} thrown, nothing picked.</li>
 *   <li><b>Confirmed skips guard</b>: same fixture but {@code isShelfLifeConfirmed=true} → pick succeeds.</li>
 *   <li><b>Flag off</b>: workplace flag OFF → pick succeeds regardless of best-before date.</li>
 * </ol>
 *
 * <p>The fixture drives the real {@code PickingJobPickCommand} via
 * {@code PickingJobService.processStepEvent} (the same code path the mobile UI calls).
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobPickShelfLifeGuard_Test
{
	/** guaranteed-days configured on the product */
	private static final int GUARANTEED_DAYS = 10;
	/**
	 * Delivery date for all scenarios.
	 * Threshold = deliveryDate + guaranteedDays = 2021-01-11.
	 */
	private static final LocalDate DELIVERY_DATE = LocalDate.of(2021, 1, 1);
	/**
	 * best-before that undercuts: 2021-01-05 &lt; threshold 2021-01-11 → undercut.
	 */
	private static final LocalDate BEST_BEFORE_UNDERCUT = LocalDate.of(2021, 1, 5);
	/**
	 * Picker user ID — must be assigned to the workspace by the test that uses the flag.
	 */
	private static final UserId PICKER_ID = UserId.ofRepoId(1234);

	private PickingJobTestHelper helper;

	/** Product with GuaranteeDaysMin = GUARANTEED_DAYS. */
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		// Product with guaranteed-days so the shelf-life check has something to evaluate.
		final I_M_Product product = BusinessTestHelper.createProduct("ShelfLifeProduct", helper.uomEach);
		product.setGuaranteeDaysMin(GUARANTEED_DAYS);
		InterfaceWrapperHelper.save(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	// -----------------------------------------------------------------------
	// Shared fixture helpers
	// -----------------------------------------------------------------------

	/**
	 * Creates a VHU with the given best-before date, a packageable pointing at
	 * {@code DELIVERY_DATE}, and a picking job locked to {@code PICKER_ID}.
	 */
	private PickingJob buildPickingJob(final LocalDate bestBeforeDate)
	{
		// HU to pick from — qty 100, best-before as requested
		final de.metas.handlingunits.picking.job.model.HUInfo vhu = helper.createVHUInfo(productId, "100", "QR-SHELFLIFE-VHU1");
		if (bestBeforeDate != null)
		{
			helper.setHUBestBeforeDate(vhu.getId(), bestBeforeDate);
		}

		// Shipment schedule with an explicit delivery date
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("SL_ORDER_001");
		final Packageable packageable = helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.date(DELIVERY_DATE.atStartOfDay(de.metas.handlingunits.picking.job.repository.MockedPickingJobLoaderSupportingServices.ZONE_ID).toInstant())
				.build();

		// Set the virtual DeliveryDate_Effective column on the shipment schedule record.
		helper.setShipmentScheduleDeliveryDateEffective(packageable.getShipmentScheduleId(), DELIVERY_DATE);

		// Picking job locked to PICKER_ID so the guard can look up the workplace by user.
		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(PICKER_ID)
						.aggregationType(PickingJobAggregationType.SALES_ORDER)
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.isAllowPickingAnyHU(false)
						.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));

		return pickingJob;
	}

	/** Returns the single step ID from the given picking job. */
	private PickingJobStepId getSingleStepId(final PickingJob pickingJob)
	{
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		return CollectionUtils.singleElement(line.getSteps().stream()
				.map(PickingJobStep::getId)
				.collect(ImmutableSet.toImmutableSet()));
	}

	/** Returns the single line from the given picking job. */
	private PickingJobLine getSingleLine(final PickingJob pickingJob)
	{
		return CollectionUtils.singleElement(pickingJob.getLines());
	}

	// -----------------------------------------------------------------------
	// Case (a): flag ON + undercut + isShelfLifeConfirmed=false → exception
	// -----------------------------------------------------------------------

	@Nested
	class case_a_flagOn_undercut_notConfirmed
	{
		@Test
		void shelfLifeTooShortException_is_thrown_and_nothing_is_picked()
		{
			// Picking profile with warnShelfLifeUndercut=true; picker assigned to a workplace
			helper.createWorkplaceWithShelfLifeFlag(true, PICKER_ID);

			final PickingJob pickingJob = buildPickingJob(BEST_BEFORE_UNDERCUT);
			final PickingJobLine line = getSingleLine(pickingJob);
			final PickingJobStepId stepId = getSingleStepId(pickingJob);

			// Pick attempt without confirmation → guard fires → exception
			assertThatThrownBy(() ->
					helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickingStepId(stepId)
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.qrCode(pickingJob.getStepById(stepId)
									.getPickFrom(PickingJobStepPickFromKey.MAIN)
									.getPickFromHU()
									.getQrCode()
									.toScannedCode())
							.qtyPicked(new BigDecimal("100"))
							.isShelfLifeConfirmed(false)
							.build())
			)
					.isInstanceOf(ShelfLifeTooShortException.class);

			// Reload to verify: step must still be un-picked (pickedTo is null)
			final PickingJob reloaded = helper.pickingJobService.getById(pickingJob.getId());
			assertThat(reloaded.getStepById(stepId)
					.getPickFrom(PickingJobStepPickFromKey.MAIN)
					.getPickedTo()
			).isNull();
		}
	}

	// -----------------------------------------------------------------------
	// Case (b): flag ON + undercut + isShelfLifeConfirmed=true → pick succeeds
	// -----------------------------------------------------------------------

	@Nested
	class case_b_flagOn_undercut_confirmed
	{
		@Test
		void pick_succeeds_when_shelf_life_confirmed()
		{
			// Same setup: flag ON, PICKER_ID assigned
			helper.createWorkplaceWithShelfLifeFlag(true, PICKER_ID);

			final PickingJob pickingJob = buildPickingJob(BEST_BEFORE_UNDERCUT);
			final PickingJobLine line = getSingleLine(pickingJob);
			final PickingJobStepId stepId = getSingleStepId(pickingJob);

			// Pick with isShelfLifeConfirmed=true → guard is skipped → pick succeeds
			final PickingJob afterPick = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(pickingJob.getStepById(stepId)
							.getPickFrom(PickingJobStepPickFromKey.MAIN)
							.getPickFromHU()
							.getQrCode()
							.toScannedCode())
					.qtyPicked(new BigDecimal("100"))
					.isShelfLifeConfirmed(true)
					.build());

			// Step must now be picked
			assertThat(afterPick.getStepById(stepId)
					.getPickFrom(PickingJobStepPickFromKey.MAIN)
					.getPickedTo()
			).isNotNull();
		}
	}

	// -----------------------------------------------------------------------
	// Case (c): flag OFF → pick succeeds, no shelf-life evaluation
	// -----------------------------------------------------------------------

	@Nested
	class case_c_flagOff
	{
		@Test
		void pick_succeeds_without_shelf_life_check_when_flag_is_off()
		{
			// Picking profile with warnShelfLifeUndercut=false (flag OFF); picker assigned to a workplace
			helper.createWorkplaceWithShelfLifeFlag(false, PICKER_ID);

			// HU best-before clearly undercutting — but guard must NOT fire because flag is off
			final PickingJob pickingJob = buildPickingJob(BEST_BEFORE_UNDERCUT);
			final PickingJobLine line = getSingleLine(pickingJob);
			final PickingJobStepId stepId = getSingleStepId(pickingJob);

			// Pick without confirmation (flag off → guard inactive)
			final PickingJob afterPick = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(pickingJob.getStepById(stepId)
							.getPickFrom(PickingJobStepPickFromKey.MAIN)
							.getPickFromHU()
							.getQrCode()
							.toScannedCode())
					.qtyPicked(new BigDecimal("100"))
					.isShelfLifeConfirmed(false)
					.build());

			// Step must be picked — no exception was thrown, pick went through
			assertThat(afterPick.getStepById(stepId)
					.getPickFrom(PickingJobStepPickFromKey.MAIN)
					.getPickedTo()
			).isNotNull();
		}
	}
}
