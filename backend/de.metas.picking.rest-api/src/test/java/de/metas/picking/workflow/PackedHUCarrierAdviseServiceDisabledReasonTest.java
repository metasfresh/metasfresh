package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.carrieradvise.HUShipmentScheduleResolver;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.money.MoneyService;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@code disabledReason} field on {@link CarrierAdviseTargetInfo} computed by
 * {@link PackedHUCarrierAdviseService#resolveInfo}.
 * <p>
 * The disabled reason is a translated human string explaining WHY the "Advise Carrier" button is
 * shown but disabled, so the frontend can surface it as a tooltip / inline message.
 * <p>
 * The test verifies:
 * <ul>
 *   <li>no-target branch (readOnly because nothing to advise onto yet) → NoTarget message</li>
 *   <li>manual/readOnly branch (carrier already set manually) → ReadOnly message</li>
 *   <li>enabled (with existing target, isCarrierAdviseReadOnly=false) → null disabledReason</li>
 *   <li>carrier advise unavailable (non-API shipper) → null disabledReason</li>
 * </ul>
 */
class PackedHUCarrierAdviseServiceDisabledReasonTest
{
	/**
	 * The AD language injected into {@link PackedHUCarrierAdviseService#resolveInfo}.
	 * {@link PlainMsgBL} returns {@code "<lang>_<msgKey>"} so expected strings are predictable
	 * without a live DB.
	 */
	private static final String AD_LANGUAGE = "en_US";

	/** Expected disabled-reason for the "no pick target at all" branch (PlainMsgBL: lang_key). */
	private static final String EXPECTED_NO_TARGET_REASON =
			AD_LANGUAGE + "_" + "de.metas.picking.CarrierAdvise.Disabled.NoTarget";

	/** Expected disabled-reason for the "target exists but nothing picked yet" branch. */
	private static final String EXPECTED_EMPTY_TARGET_REASON =
			AD_LANGUAGE + "_" + "de.metas.picking.CarrierAdvise.Disabled.EmptyTarget";

	/** Expected disabled-reason for the "manually set / read-only" branch. */
	private static final String EXPECTED_READONLY_REASON =
			AD_LANGUAGE + "_" + "de.metas.picking.CarrierAdvise.Disabled.ReadOnly";

	private PickingJobTestHelper helper;
	private CarrierProductRepository carrierProductRepository;
	private PackedHUCarrierAdviseService service;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();

		// Register PlainMsgBL so Services.get(IMsgBL.class) works without a live DB.
		// PlainMsgBL.getMsg(adLanguage, key) returns "<adLanguage>_<key.toAD_Message()>".
		Services.registerService(IMsgBL.class, new PlainMsgBL());

		carrierProductRepository = new CarrierProductRepository();
		final ShipperRepository shipperRepository = new ShipperRepository();

		service = new PackedHUCarrierAdviseService(
				mock(PackedHUShippingInfoService.class),
				mock(HUShipmentScheduleResolver.class),
				ProductRepository.newInstanceForUnitTesting(),
				carrierProductRepository,
				mock(CustomsTariffRepository.class),
				shipperRepository,
				mock(ShipmentScheduleService.class),
				mock(PickingJobRepository.class),
				new MoneyService(new CurrencyRepository()));
	}

	// ------------------------------------------------------------------
	// Helpers
	// ------------------------------------------------------------------

	private ShipperId createShipper(final String name, final boolean apiCarrierAdvise)
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class);
		shipper.setName(name);
		shipper.setIsApiCarrierAdvise(apiCarrierAdvise);
		InterfaceWrapperHelper.save(shipper);
		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());
	}

	private PickingJob createSalesOrderJobWithCarrier(
			@NonNull final CarrierProduct carrierProduct,
			@NonNull final CarrierAdviseStatus advisingStatus)
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		helper.createVHUInfo(productId, "100", "QR-VHU1");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderCarrierAdviseTest");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.carrierProductId(carrierProduct.getId())
				.carrierAdvisingStatus(advisingStatus.getCode())
				.build();

		return helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.aggregationType(PickingJobAggregationType.SALES_ORDER)
						.pickerId(UserId.ofRepoId(1234))
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.isAllowPickingAnyHU(true)
						.build());
	}

	private PickingJobLine mockApiAdviseLine(@NonNull final CarrierProduct cp, final boolean manual)
	{
		final PickingJobLine line = mock(PickingJobLine.class);
		when(line.getCarrierProductId()).thenReturn(cp.getId());
		when(line.isManual()).thenReturn(manual);
		when(line.isCarrierAdviseReadOnly()).thenReturn(false);
		return line;
	}

	private static PickingJob mockJobLevelJobNoTarget(
			@NonNull final ImmutableList<PickingJobLine> lines)
	{
		final PickingJob job = mock(PickingJob.class);
		when(job.isLineLevelPickTarget()).thenReturn(false);
		when(job.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getTuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getLines()).thenReturn(lines);
		return job;
	}

	private static PickingJob mockJobLevelJobWithExistingLuTarget(
			@NonNull final ImmutableList<PickingJobLine> lines)
	{
		// LUPickingTarget.isExistingLU() returns luId != null.  We need both isExistingLU()=true AND
		// getLuId() to return a non-null HuId so that resolveAdviseTargetHuIds can extract the HU id.
		final LUPickingTarget existingLuTarget = mock(LUPickingTarget.class);
		when(existingLuTarget.isExistingLU()).thenReturn(true);
		when(existingLuTarget.getLuId()).thenReturn(HuId.ofRepoId(999));

		final PickingJob job = mock(PickingJob.class);
		when(job.isLineLevelPickTarget()).thenReturn(false);
		when(job.getLuPickingTargetEffective(null)).thenReturn(Optional.of(existingLuTarget));
		when(job.getLines()).thenReturn(lines);
		return job;
	}

	// ------------------------------------------------------------------
	// Tests for job-level (line == null) branches
	// ------------------------------------------------------------------

	@Nested
	class HeaderLevel
	{
		/**
		 * No pick target yet (standard no-target state): the button is shown but disabled.
		 * Reason = "no target" message.
		 */
		@Test
		void noTarget_apiAdvise_disabledReasonIsNoTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Completed);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_NO_TARGET_REASON);
		}

		/**
		 * Manual carrier status at job creation: button shown but disabled with NoTarget reason.
		 * The job starts without a pick target (isCarrierAdviseReadOnly=false by default at creation),
		 * so the disabledReason is NoTarget — the carrier was manually set, but no target exists yet.
		 * The ReadOnly reason requires isCarrierAdviseReadOnly=true on the job header, which is only
		 * set after an advise ran with a manual schedule (tested in
		 * {@link #mocked_noTarget_isCarrierAdviseReadOnly_disabledReasonIsReadOnly()}).
		 */
		@Test
		void manual_apiAdvise_disabledReasonIsNoTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Manual);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_NO_TARGET_REASON);
		}

		/**
		 * Non-API-advise shipper: carrier advise is not available at all.
		 * No disabled reason (reason is only for shown-but-disabled buttons).
		 */
		@Test
		void nonApiAdvise_notAvailable_disabledReasonIsNull()
		{
			final ShipperId shipperId = createShipper("noAdvise", false);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Fallback");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.NotRequested);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isFalse();
			assertThat(info.getDisabledReason()).isNull();
		}

		/**
		 * Existing pick target, isCarrierAdviseReadOnly=false: button is enabled, disabledReason=null.
		 */
		@Test
		void mocked_withExistingTarget_notReadOnly_disabledReasonIsNull()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = mockJobLevelJobWithExistingLuTarget(
					ImmutableList.of(mockApiAdviseLine(cp, false)));
			when(job.getCarrierProductId()).thenReturn(cp.getId());
			when(job.isCarrierAdviseReadOnly()).thenReturn(false);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isFalse();
			assertThat(info.getDisabledReason()).isNull();
		}

		/**
		 * isCarrierAdviseReadOnly=true (manually locked carrier after advise ran): ReadOnly reason takes
		 * priority over NoTarget, even when no pick target exists. The manually locked carrier is the
		 * more specific reason the user should act on.
		 */
		@Test
		void mocked_noTarget_isCarrierAdviseReadOnly_disabledReasonIsReadOnly()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = mockJobLevelJobNoTarget(
					ImmutableList.of(mockApiAdviseLine(cp, false)));
			when(job.getCarrierProductId()).thenReturn(cp.getId());
			when(job.isCarrierAdviseReadOnly()).thenReturn(true);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_READONLY_REASON);
		}

		/**
		 * No target, single carrier (mocked): disabled reason is NoTarget.
		 */
		@Test
		void mocked_noTarget_singleCarrier_disabledReasonIsNoTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = mockJobLevelJobNoTarget(
					ImmutableList.of(mockApiAdviseLine(cp, false)));
			when(job.getCarrierProductId()).thenReturn(cp.getId());

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_NO_TARGET_REASON);
		}

		/**
		 * Target exists but nothing picked yet: read-only with the EmptyTarget reason
		 * (distinct from NoTarget) — a pick target (parcel) was opened but no items have been
		 * picked into it, so there is nothing meaningful to advise onto.
		 */
		@Test
		void mocked_withExistingTarget_nothingPicked_disabledReasonIsEmptyTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = mockJobLevelJobWithExistingLuTarget(
					ImmutableList.of(mockApiAdviseLine(cp, false)));
			when(job.getCarrierProductId()).thenReturn(cp.getId());
			when(job.isCarrierAdviseReadOnly()).thenReturn(false);
			// target exists but nothing picked yet
			when(job.isNothingPicked()).thenReturn(true);

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_EMPTY_TARGET_REASON);
		}
	}

	// ------------------------------------------------------------------
	// Tests for line-level branches (SALES_ORDER delegates to header)
	// ------------------------------------------------------------------

	@Nested
	class LineLevel
	{
		/**
		 * Line-level, no target: SALES_ORDER delegates to header (no existing target → readOnly/noTarget).
		 */
		@Test
		void noTarget_apiAdvise_disabledReasonIsNoTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Completed);
			final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_NO_TARGET_REASON);
		}

		/**
		 * Line-level, manual carrier status at job creation: delegates to the header (SALES_ORDER is
		 * job-level). Header has no pick target and isCarrierAdviseReadOnly=false, so reason = NoTarget.
		 */
		@Test
		void manual_apiAdvise_disabledReasonIsNoTarget()
		{
			final ShipperId shipperId = createShipper("nShift", true);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Manual);
			final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, AD_LANGUAGE);

			assertThat(info.isAvailable()).isTrue();
			assertThat(info.isReadOnly()).isTrue();
			assertThat(info.getDisabledReason()).isEqualTo(EXPECTED_NO_TARGET_REASON);
		}

		/**
		 * Line-level, non-API-advise shipper: not available, no disabled reason.
		 */
		@Test
		void nonApiAdvise_notAvailable_disabledReasonIsNull()
		{
			final ShipperId shipperId = createShipper("noAdvise", false);
			final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Fallback");
			final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.NotRequested);
			final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

			final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, AD_LANGUAGE);

			assertThat(info.isAvailable()).isFalse();
			assertThat(info.getDisabledReason()).isNull();
		}
	}
}
