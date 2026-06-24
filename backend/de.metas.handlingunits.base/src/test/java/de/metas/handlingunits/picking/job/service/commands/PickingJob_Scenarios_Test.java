package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJob_Scenarios_Test
{
	private PickingJobTestHelper helper;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	@Disabled("Disabled because splitting of a part of top level VHU leads to something which is not physically identifiable (no QR code)")
	@Test
	void pickCU_QtyToPick_LessThan_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "130");
		helper.createQRCode(vhu1, "QR-VHU1");
		System.out.println("VHU1: " + vhu1);

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder001");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.build();

		//
		// Create job
		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(UserId.ofRepoId(1234))
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.isAllowPickingAnyHU(false) // we need a plan built
						.build());
		System.out.println("Created " + pickingJob);
		final PickingJobStepId stepId = CollectionUtils.singleElement(pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		//
		// Pick the whole quantity
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(helper.getQRCode(vhu1).toScannedCode())
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		HuId pickFromHUId;
		{
			System.out.println("After pick: " + pickingJob);
			assertThat(pickFromHUId = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHUId()).isNotEqualTo(vhu1);
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
			HUStorageExpectation.newExpectation().product(productId).qty("30").assertExpected(vhu1);

			{
				final PickingJobStepPickedTo pickedTo = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
				assertThat(pickedTo).isNotNull();
				assertThat(pickedTo.getQtyPicked()).isEqualTo(Quantity.of("100", helper.uomEach));

				assertThat(pickedTo.getActualPickedHUs()).hasSize(1);
				{
					final PickingJobStepPickedToHU pickedToHU = pickedTo.getActualPickedHUs().get(0);
					assertThat(pickedToHU.getActualPickedHU().getId()).isEqualTo(pickFromHUId);
					HUStorageExpectation.newExpectation().product(productId).qty(pickedTo.getQtyPicked()).assertExpected(pickFromHUId);

					// final PickingCandidate pickingCandidate = helper.pickingCandidateRepository.getById(pickedToHU.getPickingCandidateId());
					// assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromHUId));
					// assertThat(pickingCandidate.getPackedToHuId()).isEqualTo(pickFromHUId);
				}
			}
		}

		//
		// Unpick
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(helper.getQRCode(vhu1).toScannedCode())
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU())
					.isEqualTo(HUInfo.builder().id(pickFromHUId).qrCode(helper.getQRCode(pickFromHUId)).build());
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
		}
	}

	@Test
	void pickCU_QtyToPick_EqualsTo_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);

		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory("P1-Category", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		final HUInfo vhu1 = helper.createVHUInfo(productId, "100", "QR-VHU1");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder002");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false) // we need a plan built
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		System.out.println("Created " + pickingJob);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(vhu1.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		{
			System.out.println("After pick: " + pickingJob);

			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(vhu1);

			final PickingJobStepPickedTo pickedTo = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
			assertThat(pickedTo)
					.isNotNull()
					.isEqualTo(PickingJobStepPickedTo.builder()
							.actualPickedHUs(ImmutableList.of(
									PickingJobStepPickedToHU.builder()
											.qtyPicked(Quantity.of("100", helper.uomEach))
											.pickFromHUId(vhu1.getId())
											.actualPickedHU(vhu1)
											.createdAt(SystemTime.asInstant())
											//.pickingCandidateId(pickedTo.getActualPickedHUs().get(0).getPickingCandidateId()) // N/A
											.build()))
							.build());
		}
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(vhu1.getQrCode().toScannedCode())
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(vhu1);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
		}
	}

	/**
	 * Tests for the partial-unpick-by-product path (subset UNPICK via productId + qtyToUnpick).
	 * <p>
	 * These tests cover backend branches that the Playwright UI tests cannot drive
	 * (JUnit/cucumber only for paths unreachable via the mobile UI).
	 */
	@Nested
	class PartialUnpickByProduct
	{
		private ProductCategoryId productCategoryId;

		@BeforeEach
		void beforeEach()
		{
			this.productCategoryId = BusinessTestHelper.createProductCategory("PUP-Category", null);
		}

		private ProductId createProduct(@NonNull final String value)
		{
			final I_M_Product product = BusinessTestHelper.createProduct(value, helper.uomEach);
			product.setM_Product_Category_ID(productCategoryId.getRepoId());
			InterfaceWrapperHelper.save(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		/**
		 * Over-qty rejection: requesting more than the total packed qty for a product is rejected
		 * with a clear "exceeds" message.
		 * <p>
		 * Setup: 1 VHU with qty=6 picked → packed qty = 6.
		 * Action: request subset-UNPICK of qty=7 (> packed qty).
		 * <p>
		 * Expected exception message must contain "exceeds" (dedicated over-qty guard).
		 */
		@Test
		void overQtyRejected()
		{
			final ProductId productId = createProduct("OQR-P1");
			final HUInfo vhu1 = helper.createVHUInfo(productId, "6", "QR-OQR-VHU1");

			final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("OQR-salesOrder");
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(productId)
					.qtyToDeliver("6")
					.build();

			PickingJob pickingJob = helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.SALES_ORDER)
									.pickerId(UserId.ofRepoId(1234))
									.salesOrderId(orderAndLineId.getOrderId())
									.deliveryBPLocationId(helper.shipToBPLocationId)
									.isAllowPickingAnyHU(false)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));

			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final PickingJobStepId stepId = CollectionUtils.singleElement(
					line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			// Pick 6 from the single VHU
			pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(vhu1.getQrCode().toScannedCode())
					.qtyPicked(new BigDecimal("6"))
					.build());

			// Attempt subset-UNPICK of qty=7 (exceeds packed qty of 6) — must be rejected with "exceeds"
			final PickingJob finalPickingJob = pickingJob;
			assertThatThrownBy(() -> helper.pickingJobService.processStepEvent(finalPickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.UNPICK)
					.qrCode(vhu1.getQrCode().toScannedCode())
					.unpickProductId(productId)
					.qtyToUnpick(new BigDecimal("7"))
					.build()))
					.hasMessageContaining("exceeds");
		}

		/**
		 * Multi-CU LIFO boundary selection: when a product is packed across multiple CUs,
		 * the LIFO selection picks the most-recently-packed CU first, and splits the boundary CU
		 * to deliver exactly the requested qty.
		 * <p>
		 * Setup: 2 VHUs of qty=3 each (VHU1 packed first, VHU2 packed second).
		 * LIFO order for unpick: VHU2 (newer) first, then VHU1 (older).
		 * Action: request subset-UNPICK of qty=2.
		 * Expected: boundary CU (VHU2, qty=3) is split — 2 units removed, 1 unit remains.
		 * Net picked qty across all steps = 4 (=3+3−2).
		 */
		@Test
		void multiCuLifoBoundarySelection()
		{
			final ProductId productId = createProduct("MCLB-P1");
			final HUInfo vhu1 = helper.createVHUInfo(productId, "3", "QR-MCLB-VHU1");
			final HUInfo vhu2 = helper.createVHUInfo(productId, "3", "QR-MCLB-VHU2");

			final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("MCLB-salesOrder");
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(productId)
					.qtyToDeliver("6")
					.build();

			PickingJob pickingJob = helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.SALES_ORDER)
									.pickerId(UserId.ofRepoId(1234))
									.salesOrderId(orderAndLineId.getOrderId())
									.deliveryBPLocationId(helper.shipToBPLocationId)
									.isAllowPickingAnyHU(false)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));

			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final ImmutableList<PickingJobStepId> stepIds = line.getSteps().stream()
					.map(PickingJobStep::getId)
					.collect(ImmutableList.toImmutableList());
			assertThat(stepIds).as("expect 2 steps (one per VHU)").hasSize(2);

			final PickingJobStepId stepId1 = stepIds.get(0);
			final PickingJobStepId stepId2 = stepIds.get(1);

			// Determine which step's pickFromHU matches VHU1 and VHU2
			final PickingJobStep rawStep1 = pickingJob.getStepById(stepId1);
			final PickingJobStep rawStep2 = pickingJob.getStepById(stepId2);
			final HUInfo vhuForStep1 = rawStep1.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU();
			final HUInfo vhuForStep2 = rawStep2.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU();

			// Pick step whose pickFrom = VHU1 first (older timestamp)
			final PickingJobStepId pickFirstStepId = vhuForStep1 != null && vhuForStep1.getId().equals(vhu1.getId()) ? stepId1 : stepId2;
			final HUInfo pickFirstVhu = vhuForStep1 != null && vhuForStep1.getId().equals(vhu1.getId()) ? vhu1 : vhu2;
			final PickingJobStepId pickSecondStepId = pickFirstStepId.equals(stepId1) ? stepId2 : stepId1;
			final HUInfo pickSecondVhu = pickFirstVhu.getId().equals(vhu1.getId()) ? vhu2 : vhu1;

			// Pick VHU1 first (at T1 = older) → lower createdAt
			SystemTime.setFixedTimeSource("2025-01-01T10:00:00+00:00");
			pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(pickFirstStepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(pickFirstVhu.getQrCode().toScannedCode())
					.qtyPicked(new BigDecimal("3"))
					.build());

			// Pick VHU2 second (at T2 = newer) → higher createdAt → LIFO picks this first
			SystemTime.setFixedTimeSource("2025-01-01T11:00:00+00:00");
			pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(pickSecondStepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(pickSecondVhu.getQrCode().toScannedCode())
					.qtyPicked(new BigDecimal("3"))
					.build());

			SystemTime.resetTimeSource();

			// Total packed qty = 6 (3 + 3). Request partial unpick of 2.
			// LIFO: VHU2 (newer, T2) is boundary CU — qty=3 > remaining=2 — must be split.
			// Expected after split: VHU2 contributes 2 removed, 1 stays → net picked = 4.
			final PickingJob finalPickingJob = pickingJob;
			final PickingJob afterUnpick = helper.pickingJobService.processStepEvent(finalPickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.eventType(PickingJobStepEventType.UNPICK)
					.qrCode(pickSecondVhu.getQrCode().toScannedCode())
					.unpickProductId(productId)
					.qtyToUnpick(new BigDecimal("2"))
					.build());

			// Assert net packed qty = 4 (6 - 2)
			final BigDecimal netPickedQty = afterUnpick.streamSteps()
					.filter(step -> ProductId.equals(step.getProductId(), productId))
					.flatMap(step -> step.getPickFromKeys().stream()
							.map(key -> step.getPickFrom(key).getQtyPicked().orElse(null))
							.filter(qty -> qty != null))
					.map(qty -> qty.toBigDecimal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			assertThat(netPickedQty).isEqualByComparingTo(new BigDecimal("4"));

			// LIFO lock: the OLDER CU (VHU1, picked first at T1) is drawn LAST, so a qty-2 unpick
			// never touches it — its step must still carry the full 3. A FIFO implementation would
			// split VHU1 first and fail this assertion (net qty alone cannot distinguish LIFO from FIFO).
			final Quantity vhu1QtyAfter = afterUnpick.getStepById(pickFirstStepId)
					.getPickFrom(PickingJobStepPickFromKey.MAIN)
					.getQtyPicked()
					.orElse(null);
			assertThat(vhu1QtyAfter).as("older CU (VHU1) must remain in a step after the unpick").isNotNull();
			assertThat(vhu1QtyAfter.toBigDecimal())
					.as("older CU (VHU1) is untouched under LIFO (FIFO would have split it)")
					.isEqualByComparingTo(new BigDecimal("3"));
		}
	}

	@Nested
	class pick_GS1
	{
		private ProductCategoryId productCategoryId;

		@BeforeEach
		void beforeEach()
		{
			this.productCategoryId = BusinessTestHelper.createProductCategory("PC", null);
		}

		private ProductId createProduct(@NonNull String gtin)
		{
			final I_M_Product product = BusinessTestHelper.createProduct(gtin, helper.uomEach);
			product.setGTIN(gtin);
			product.setM_Product_Category_ID(productCategoryId.getRepoId());
			InterfaceWrapperHelper.save(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		@SuppressWarnings("SameParameterValue")
		private PickingJob createPickingJob(final ProductId productId, String qtyToDeliver)
		{
			final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder");
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(productId)
					.qtyToDeliver(qtyToDeliver)
					.build();

			return helper.pickingJobService.createPickingJob(
					PickingJobCreateRequest.builder()
							.aggregationType(PickingJobAggregationType.SALES_ORDER)
							.pickerId(UserId.ofRepoId(1234))
							.salesOrderId(orderAndLineId.getOrderId())
							.deliveryBPLocationId(helper.shipToBPLocationId)
							.isAllowPickingAnyHU(false) // we need a plan built
							.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		}

		@Test
		void gs1ProductNotFound()
		{
			final ProductId productId = createProduct("97311876341810");
			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.qrCode(ScannedCode.ofString("019731187634181131030075201527080910501"))
							.qtyPicked(new BigDecimal("1"))
							.qtyRejectedReasonCode(null)
							.build())
			)
					.hasMessageStartingWith("NotFound M_Product_ID: GTIN 97311876341811");
		}

		@Test
		void gs1ProductNotMatching()
		{
			final ProductId productId = createProduct("97311876341810");
			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());

			createProduct("97311876341811");

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.qrCode(ScannedCode.ofString("019731187634181131030075201527080910501"))
							.qtyPicked(new BigDecimal("1"))
							.qtyRejectedReasonCode(null)
							.build())
			)
					.hasMessageStartingWith("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
		}
	}

	@Nested
	class pick_EAN13
	{
		private ProductCategoryId productCategoryId;

		@BeforeEach
		void beforeEach()
		{
			this.productCategoryId = BusinessTestHelper.createProductCategory("PC", null);
		}

		private ProductId createProduct(@NonNull String productValue)
		{
			final I_M_Product product = BusinessTestHelper.createProduct(productValue, helper.uomEach);
			product.setValue(productValue);
			product.setM_Product_Category_ID(productCategoryId.getRepoId());
			InterfaceWrapperHelper.save(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		@SuppressWarnings("SameParameterValue")
		private PickingJob createPickingJob(final ProductId productId, String qtyToDeliver)
		{
			final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder");
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(productId)
					.qtyToDeliver(qtyToDeliver)
					.build();

			return helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.SALES_ORDER)
									.pickerId(UserId.ofRepoId(1234))
									.salesOrderId(orderAndLineId.getOrderId())
									.deliveryBPLocationId(helper.shipToBPLocationId)
									.isAllowPickingAnyHU(false) // we need a plan built
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		}

		@Test
		void ean13ProductNotMatching()
		{
			final ProductId productId = createProduct("123456");
			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.qrCode(EAN13HUQRCode.fromString("2859414004825").orElseThrow().toScannedCode())
							.qtyPicked(new BigDecimal("1"))
							.qtyRejectedReasonCode(null)
							.build())
			)
					.hasMessageStartingWith("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
		}

		@Test
		void ean13Prefix28Valid()
		{
			// remark: we use 6 digits from productNo while our EAN13 contains 5 digits product no
			// we expect product to be valid

			final ProductId productId = createProduct("594143");
			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(EAN13HUQRCode.fromString("2859414004825").orElseThrow().toScannedCode())
					.qtyPicked(new BigDecimal("1"))
					.qtyRejectedReasonCode(null)
					.build());
		}

		@Test
		void ean13Prefix29Valid()
		{
			final I_M_Product product = BusinessTestHelper.createProduct("594143", helper.uomEach);
			product.setValue("594143");
			product.setM_Product_Category_ID(productCategoryId.getRepoId());
			product.setEAN13_ProductCode("4888");
			InterfaceWrapperHelper.save(product);
			final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.qrCode(EAN13HUQRCode.fromString("2948882005745").orElseThrow().toScannedCode())
					.qtyPicked(new BigDecimal("1"))
					.qtyRejectedReasonCode(null)
					.build());
		}
	}
}