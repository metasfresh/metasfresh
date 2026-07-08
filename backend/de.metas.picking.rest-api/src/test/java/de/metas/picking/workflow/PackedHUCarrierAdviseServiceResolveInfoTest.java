package de.metas.picking.workflow;

import de.metas.business.BusinessTestHelper;
import de.metas.customstariff.CustomsTariffRepository;
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
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
		SpringContextHolder.registerJUnitBean(ProductRepository.newInstanceForUnitTesting());

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
				mock(PickingJobRepository.class));
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
			@org.jetbrains.annotations.NotNull final CarrierProduct carrierProduct,
			@org.jetbrains.annotations.NotNull final CarrierAdviseStatus advisingStatus)
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
	void resolveInfo_apiAdviseShipper_nonManual_availableEditableWithCaption()
	{
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Completed);
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line);

		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isFalse();
		assertThat(info.getProductCaption()).isEqualTo("Std Parcel");
	}

	@Test
	void resolveInfo_apiAdviseShipper_manual_isReadOnly()
	{
		final ShipperId shipperId = createShipper("nShift", true);
		final CarrierProduct cp = carrierProductRepository.getOrCreateCarrierProduct(shipperId, "cp1", "Std Parcel");
		final PickingJob job = createSalesOrderJobWithCarrier(cp, CarrierAdviseStatus.Manual);
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line);

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

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, line);

		assertThat(info.isAvailable()).isFalse();
	}
}
