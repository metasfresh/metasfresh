package de.metas.picking.workflow;

import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.money.MoneyService;
import de.metas.handlingunits.picking.job.carrieradvise.HUShipmentScheduleResolver;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentScheduleService;
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
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Real-fixture tests for {@link PackedHUCarrierAdviseService#resolveInfo}: a real {@link PickingJob} built via
 * {@link PickingJobTestHelper}, a real {@code M_Shipper} + {@code Carrier_Product}, and the REAL
 * {@link CarrierProductRepository} / {@link ShipperRepository} (not mocked) — so the actual advise-availability
 * logic runs against real data. Only the constructor deps that {@code resolveInfo} does not touch are mocked.
 */
class PackedHUCarrierAdviseServiceResolveInfoTest
{
	private PickingJobTestHelper helper;
	private CarrierProductRepository carrierProductRepository;
	private PackedHUCarrierAdviseService service;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
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

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderResolveInfo");
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

	@Test
	void resolveInfo_apiAdviseShipper_nonManual_noTarget_availableReadOnlyWithCaption()
	{
		// A SALES_ORDER job is NOT line-level, so it takes the job-level branch. With no pick target set, this
		// is a read-only DISPLAY of the single (unambiguous) API-advise carrier — there is nothing to advise onto.
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Completed);
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, "en_US");

		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
		assertThat(info.getProductCaption()).isEqualTo("Std Parcel");
	}

	@Test
	void resolveInfo_apiAdviseShipper_manual_isReadOnly()
	{
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Manual);
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, "en_US");

		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
	}

	@Test
	void resolveInfo_nonApiAdviseShipper_notAvailable()
	{
		// A non-API-advise shipper still gets a fallback Carrier_Product, so availability must be gated on the
		// shipper's IsApiCarrierAdvise, not on carrier-product presence.
		final ShipperId shipperId = createShipper("noAdvise", false);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Fallback Parcel");
		final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.NotRequested);
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line, "en_US");

		assertThat(info.isAvailable()).isFalse();
	}

	// ---------------------------------------------------------------------------------------------
	// Job-level (line==null) no-/with-target display branch.
	// Target presence and carrier divergence are controlled via a mocked PickingJob/PickingJobLine
	// (as in the sibling PackedHUCarrierAdviseServiceTest job-level cases); the real
	// CarrierProductRepository + ShipperRepository still decide API-advise eligibility + caption.
	// ---------------------------------------------------------------------------------------------

	private PickingJobLine mockApiAdviseLine(@NonNull final CarrierProduct cp, final boolean manual)
	{
		final PickingJobLine line = mock(PickingJobLine.class);
		when(line.getCarrierProductId()).thenReturn(cp.getId());
		when(line.isManual()).thenReturn(manual);
		when(line.isCarrierAdviseReadOnly()).thenReturn(false);
		return line;
	}

	private static PickingJob mockJobLevelJob(final ImmutableList<PickingJobLine> lines)
	{
		final PickingJob job = mock(PickingJob.class);
		when(job.isLineLevelPickTarget()).thenReturn(false);
		// no pick target → hasExistingTarget=false (read the header, read-only DISPLAY)
		when(job.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getTuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getLines()).thenReturn(lines);
		return job;
	}

	// With a pick target, the read-only flag now comes from the target parcel's OWN shipment schedules
	// (PackedHUCarrierAdviseService.isTargetCarrierAdviseReadOnly). That HU-schedule resolution is not
	// meaningfully reproducible with a mocked PickingJob — in-memory JUnit does not prove HU behaviour
	// (de.metas.handlingunits.base/CLAUDE.md). The with-target editable / read-only cases are covered
	// faithfully with real HU data by nShiftShipment.feature: display _300 (editable), _310 (manual → read-only),
	// _330 (divergent → editable), and the guard scenarios _201/_202.

	@Test
	void resolveInfo_noTarget_singleCarrier_availableReadOnlyWithCaption()
	{
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = mockJobLevelJob(ImmutableList.of(mockApiAdviseLine(cp, false)));
		// the header carries the current (single, API-advise) carrier → its name is the caption
		when(job.getCarrierProductId()).thenReturn(cp.getId());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, "en_US");

		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
		assertThat(info.getProductCaption()).isEqualTo("Std Parcel");
	}

	@Test
	void resolveInfo_noTarget_allLinesSameCarrier_availableReadOnlyWithCaption()
	{
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = mockJobLevelJob(ImmutableList.of(
				mockApiAdviseLine(cp, false), mockApiAdviseLine(cp, false)));
		// the header carries the current (single, API-advise) carrier → its name is the caption
		when(job.getCarrierProductId()).thenReturn(cp.getId());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, "en_US");

		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
		assertThat(info.getProductCaption()).isEqualTo("Std Parcel");
	}

	@Test
	void resolveInfo_noTarget_divergentCarriers_availableReadOnly()
	{
		// No target + divergent carriers: available whenever an API-advise carrier product exists (unavailable
		// only when none does), read-only because there is nothing to advise onto, and no single carrier to caption.
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp1 = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "A");
		final CarrierProduct cp2 = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp2", "B");
		final PickingJob job = mockJobLevelJob(ImmutableList.of(
				mockApiAdviseLine(cp1, false), mockApiAdviseLine(cp2, false)));

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, "en_US");
		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
		assertThat(info.getProductCaption()).isNull();
	}

	@Test
	void resolveInfo_noTarget_noApiAdviseCarrier_notAvailable()
	{
		final ShipperId shipperId = createShipper("noAdvise", false);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Fallback");
		final PickingJob job = mockJobLevelJob(ImmutableList.of(mockApiAdviseLine(cp, false)));

		assertThat(service.resolveInfo(job, null, "en_US").isAvailable()).isFalse();
	}
}
