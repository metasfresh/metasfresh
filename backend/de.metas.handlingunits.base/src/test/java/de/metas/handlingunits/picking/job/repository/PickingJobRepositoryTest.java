package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.user.UserId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;

class PickingJobRepositoryTest
{
	// Services
	private PickingJobRepository pickingJobRepository;
	private MockedPickingJobLoaderSupportingServices loadingSupportServices;

	// Master data:
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final OrderId salesOrderId = OrderId.ofRepoId(2);
	private I_C_UOM uomEach;

	@BeforeAll
	static void beforeAll() {start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		pickingJobRepository = new PickingJobRepository();
		loadingSupportServices = new MockedPickingJobLoaderSupportingServices();

		uomEach = BusinessTestHelper.createUomEach();
	}

	@SuppressWarnings("SameParameterValue")
	private InstantAndOrgId instantAndOrgId(final String instantStr)
	{
		return InstantAndOrgId.ofInstant(Instant.parse(instantStr), orgId);
	}

	private static HUQRCode dummyQRCode(String uuid)
	{
		return HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.fromString(uuid)))
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(12340))
						.caption("Some TU")
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(ProductId.ofRepoId(12341))
						.code("product value")
						.name("product name")
						.build())
				.attributes(ImmutableList.of())
				.build();
	}

	@Test
	void createNewAndGet_then_getById()
	{
		loadingSupportServices.mockQRCode(HuId.ofRepoId(11), dummyQRCode("7a71c408-e7fb-4b5d-a0b8-814896b31659"));
		loadingSupportServices.mockQRCode(HuId.ofRepoId(1001), dummyQRCode("f18c53be-1341-4203-b0f4-fb25fb33a5fa"));

		final OrderAndLineId salesOrderLineId = OrderAndLineId.ofRepoIds(salesOrderId, 8);
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(7);
		final PickingJob jobCreated = pickingJobRepository.createNewAndGet(
				PickingJobCreateRepoRequest.builder()
						.orgId(orgId)
						.salesOrderId(salesOrderId)
						.preparationDate(instantAndOrgId("2021-11-02T07:39:16Z"))
						.deliveryDate(instantAndOrgId("2021-11-02T07:39:16Z"))
						.deliveryBPLocationId(BPartnerLocationId.ofRepoId(3, 4))
						.handoverLocationId(BPartnerLocationId.ofRepoId(3, 4))
						.deliveryRenderedAddress("deliveryRenderedAddress")
						.pickerId(UserId.ofRepoId(5))
						.line(PickingJobCreateRepoRequest.Line.builder()
								.salesOrderAndLineId(salesOrderLineId)
								.shipmentScheduleId(shipmentScheduleId)
								.productId(ProductId.ofRepoId(6))
								.huPIItemProductId(HUPIItemProductId.ofRepoId(6789))
								.qtyToPick(Quantity.of(100, uomEach))
								.pickFromAlternatives(ImmutableSet.of(
										PickingJobCreateRepoRequest.PickFromAlternative.of(
												LocatorId.ofRepoId(21, 22),
												HuId.ofRepoId(1001),
												Quantity.of(999, uomEach))
								))
								.step(PickingJobCreateRepoRequest.Step.builder()
										.salesOrderLineId(salesOrderLineId)
										.shipmentScheduleId(shipmentScheduleId)
										.productId(ProductId.ofRepoId(6))
										.qtyToPick(Quantity.of(100, uomEach))
										.mainPickFrom(PickingJobCreateRepoRequest.StepPickFrom.builder()
												.pickFromLocatorId(LocatorId.ofRepoId(9, 10))
												.pickFromHUId(HuId.ofRepoId(11))
												.build())
										.pickFromAlternatives(ImmutableSet.of(
												PickingJobCreateRepoRequest.StepPickFrom.builder()
														.pickFromLocatorId(LocatorId.ofRepoId(21, 22))
														.pickFromHUId(HuId.ofRepoId(1001))
														.build()
										))
										.packToSpec(PackToSpec.ofTUPackingInstructionsId(HUPIItemProductId.ofRepoId(6789)))
										.build())
								.build())
						.build(),
				loadingSupportServices);
		expect(jobCreated).toMatchSnapshot();

		final PickingJob jobLoaded = pickingJobRepository.getById(jobCreated.getId(), loadingSupportServices);
		Assertions.assertThat(jobLoaded)
				.usingRecursiveComparison()
				.isEqualTo(jobCreated);
	}
}