package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
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
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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
				.huQRCode(helper.getQRCode(vhu1))
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
				.huQRCode(helper.getQRCode(vhu1))
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
				.huQRCode(vhu1.getQrCode())
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
											//.pickingCandidateId(pickedTo.getActualPickedHUs().get(0).getPickingCandidateId()) // N/A
											.build()))
							.build());
		}
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.huQRCode(vhu1.getQrCode())
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(vhu1);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
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
							.build());
		}

		@Test
		void gs1ProductNotFound()
		{
			final ProductId productId = createProduct("97311876341810");
			helper.createVHU(productId, "100");

			final PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickingStepId(stepId)
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.huQRCode(GS1HUQRCode.fromString("019731187634181131030075201527080910501"))
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
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			createProduct("97311876341811");

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickingStepId(stepId)
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.huQRCode(GS1HUQRCode.fromString("019731187634181131030075201527080910501"))
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
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			assertThatThrownBy(
					() -> helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
							.pickingLineId(line.getId())
							.pickingStepId(stepId)
							.pickFromKey(PickingJobStepPickFromKey.MAIN)
							.eventType(PickingJobStepEventType.PICK)
							.huQRCode(EAN13HUQRCode.fromString("2859414004825").orElseThrow())
							.qtyPicked(new BigDecimal("1"))
							.qtyRejectedReasonCode(null)
							.build())
			)
					.hasMessageStartingWith("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
		}

		@Test
		void ean13Valid()
		{
			// remark: we use 6 digits from productNo while our EAN13 contains 5 digits product no
			// we expect product to be valid

			final ProductId productId = createProduct("594143");
			helper.createVHU(productId, "100");

			PickingJob pickingJob = createPickingJob(productId, "100");
			System.out.println("Created " + pickingJob);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

			helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
					.pickingLineId(line.getId())
					.pickingStepId(stepId)
					.pickFromKey(PickingJobStepPickFromKey.MAIN)
					.eventType(PickingJobStepEventType.PICK)
					.huQRCode(EAN13HUQRCode.fromString("2859414004825").orElseThrow())
					.qtyPicked(new BigDecimal("1"))
					.qtyRejectedReasonCode(null)
					.build());
		}

	}
}