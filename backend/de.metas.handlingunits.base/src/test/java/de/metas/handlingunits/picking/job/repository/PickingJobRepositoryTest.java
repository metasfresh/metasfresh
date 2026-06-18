package de.metas.handlingunits.picking.job.repository;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Line_Carrier_Service;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.spi.model.ResolvedCarrier;
import de.metas.shipping.CarrierProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SnapshotExtension.class)
class PickingJobRepositoryTest
{
	// Services
	private PickingJobRepository pickingJobRepository;
	private MockedPickingJobLoaderSupportingServices loadingSupportServices;

	// Master data:
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final OrderId salesOrderId = OrderId.ofRepoId(2);
	private I_C_UOM uomEach;

	@SuppressWarnings("unused") private Expect expect;

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

	private PickingJob createSampleJob()
	{
		loadingSupportServices.mockQRCode(HuId.ofRepoId(11), dummyQRCode("7a71c408-e7fb-4b5d-a0b8-814896b31659"));
		loadingSupportServices.mockQRCode(HuId.ofRepoId(1001), dummyQRCode("f18c53be-1341-4203-b0f4-fb25fb33a5fa"));

		final OrderAndLineId salesOrderLineId = OrderAndLineId.ofRepoIds(salesOrderId, 8);
		final ShipmentScheduleAndJobScheduleId scheduleId = ShipmentScheduleAndJobScheduleId.ofRepoIds(7, -1);
		final BPartnerLocationId deliveryBPLocationId = BPartnerLocationId.ofRepoId(3, 4);
		return pickingJobRepository.createNewAndGet(
				PickingJobCreateRepoRequest.builder()
						.aggregationType(PickingJobAggregationType.SALES_ORDER)
						.orgId(orgId)
						.salesOrderId(salesOrderId)
						.preparationDate(instantAndOrgId("2021-11-02T07:39:16Z"))
						.deliveryDate(instantAndOrgId("2021-11-02T07:39:16Z"))
						.deliveryBPLocationId(deliveryBPLocationId)
						.handoverLocationId(deliveryBPLocationId)
						.deliveryRenderedAddress("deliveryRenderedAddress")
						.pickerId(UserId.ofRepoId(5))
						.line(PickingJobCreateRepoRequest.Line.builder()
								.salesOrderAndLineId(salesOrderLineId)
								.deliveryBPLocationId(deliveryBPLocationId)
								.scheduleId(scheduleId)
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
										.scheduleId(scheduleId)
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
	}

	@Test
	void createNewAndGet_then_getById()
	{
		final PickingJob jobCreated = createSampleJob();
		expect.toMatchSnapshot(jobCreated);

		final PickingJob jobLoaded = pickingJobRepository.getById(jobCreated.getId(), loadingSupportServices);
		Assertions.assertThat(jobLoaded)
				.usingRecursiveComparison()
				.isEqualTo(jobCreated);
	}

	@Test
	void carrierProductAndAdviseReadOnly_roundTrip_onHeaderAndLine()
	{
		final CarrierProductId carrierProductId = CarrierProductId.ofRepoId(4711);
		final CarrierGoodsTypeId carrierGoodsTypeId = CarrierGoodsTypeId.ofRepoId(8150);
		final ImmutableSet<CarrierServiceId> carrierServices = ImmutableSet.of(
				CarrierServiceId.ofRepoId(8151),
				CarrierServiceId.ofRepoId(8152));

		final PickingJob jobToSave = createSampleJob()
				.withCarrierProductId(carrierProductId)
				.withCarrierAdviseReadOnly(true)
				.withChangedLines(line -> line.toBuilder()
						.carrierProductId(carrierProductId)
						.carrierAdviseReadOnly(true)
						.isManual(true)
						.carrierGoodsTypeId(carrierGoodsTypeId)
						.carrierServices(carrierServices)
						.build());
		pickingJobRepository.save(jobToSave);

		final PickingJob jobLoaded = pickingJobRepository.getById(jobToSave.getId(), loadingSupportServices);

		Assertions.assertThat(jobLoaded.getCarrierProductId()).isEqualTo(carrierProductId);
		Assertions.assertThat(jobLoaded.isCarrierAdviseReadOnly()).isTrue();

		Assertions.assertThat(jobLoaded.getLines()).allSatisfy(line -> {
			Assertions.assertThat(line.getCarrierProductId()).isEqualTo(carrierProductId);
			Assertions.assertThat(line.isCarrierAdviseReadOnly()).isTrue();
			Assertions.assertThat(line.isManual()).as("isManual").isTrue();
			Assertions.assertThat(line.getCarrierGoodsTypeId()).as("carrierGoodsTypeId").isEqualTo(carrierGoodsTypeId);
			Assertions.assertThat(line.getCarrierServices()).as("carrierServices").isEqualTo(carrierServices);
		});
	}

	private int createPickingJobWithLine(
			@NonNull final PickingJobDocStatus docStatus,
			@NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setAD_Org_ID(orgId.getRepoId());
		job.setDocStatus(docStatus.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);

		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setAD_Org_ID(orgId.getRepoId());
		line.setM_Picking_Job_ID(job.getM_Picking_Job_ID());
		line.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);

		return job.getM_Picking_Job_ID();
	}

	@SuppressWarnings("UnusedReturnValue")
	private I_M_Picking_Job_Line createCompletedJobLineWithCarrier(
			@NonNull final ShipmentScheduleId scheduleId,
			@Nullable final CarrierProductId carrierProductId,
			@Nullable final CarrierGoodsTypeId carrierGoodsTypeId,
			@NonNull final Set<CarrierServiceId> carrierServices)
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setAD_Org_ID(orgId.getRepoId());
		// at SEND time the job is already Completed; the resolver must find the line regardless of doc status
		job.setDocStatus(PickingJobDocStatus.Completed.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);

		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setAD_Org_ID(orgId.getRepoId());
		line.setM_Picking_Job_ID(job.getM_Picking_Job_ID());
		line.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		line.setCarrier_Product_ID(CarrierProductId.toRepoId(carrierProductId));
		line.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(carrierGoodsTypeId));
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);

		for (final CarrierServiceId serviceId : carrierServices)
		{
			final I_M_Picking_Job_Line_Carrier_Service assignment = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line_Carrier_Service.class);
			assignment.setAD_Org_ID(orgId.getRepoId());
			assignment.setM_Picking_Job_Line_ID(line.getM_Picking_Job_Line_ID());
			assignment.setCarrier_Service_ID(serviceId.getRepoId());
			assignment.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(assignment);
		}

		return line;
	}

	@Test
	void getCarrierByScheduleIds_lineWins_andNoLineScheduleAbsent()
	{
		// schedule X: a Completed picking-job line carries carrier product 5001 (the LINE value).
		// the shipment-schedule's own carrier (e.g. 9001) differs — the resolver must return the LINE value.
		final ShipmentScheduleId scheduleWithLine = ShipmentScheduleId.ofRepoId(201);
		final CarrierProductId lineProductId = CarrierProductId.ofRepoId(5001);
		final CarrierGoodsTypeId lineGoodsTypeId = CarrierGoodsTypeId.ofRepoId(6001);
		final ImmutableSet<CarrierServiceId> lineServices = ImmutableSet.of(CarrierServiceId.ofRepoId(7001), CarrierServiceId.ofRepoId(7002));
		createCompletedJobLineWithCarrier(scheduleWithLine, lineProductId, lineGoodsTypeId, lineServices);

		// schedule Y: no picking-job line at all → must be absent from the result map (caller falls back to schedule)
		final ShipmentScheduleId scheduleWithoutLine = ShipmentScheduleId.ofRepoId(202);

		final Map<ShipmentScheduleId, ResolvedCarrier> result =
				pickingJobRepository.getCarrierByScheduleIds(ImmutableSet.of(scheduleWithLine, scheduleWithoutLine));

		Assertions.assertThat(result).containsKey(scheduleWithLine);
		final ResolvedCarrier resolved = result.get(scheduleWithLine);
		Assertions.assertThat(resolved.getCarrierProductId()).as("line carrier product wins").isEqualTo(lineProductId);
		Assertions.assertThat(resolved.getCarrierGoodsTypeId()).as("line goods type").isEqualTo(lineGoodsTypeId);
		Assertions.assertThat(resolved.getCarrierServices()).as("line services").isEqualTo(lineServices);

		Assertions.assertThat(result).as("a schedule with no picking-job line is absent (caller falls back)").doesNotContainKey(scheduleWithoutLine);
	}

	@Test
	void getCarrierByScheduleIds_twoLinesSameCarrier_converge()
	{
		// the common production case: a schedule has two picking-job lines carrying the SAME carrier →
		// they reduce to that one carrier (no abort).
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(204);
		final CarrierProductId productId = CarrierProductId.ofRepoId(5003);
		final CarrierGoodsTypeId goodsTypeId = CarrierGoodsTypeId.ofRepoId(6003);
		final ImmutableSet<CarrierServiceId> services = ImmutableSet.of(CarrierServiceId.ofRepoId(7003));
		createCompletedJobLineWithCarrier(scheduleId, productId, goodsTypeId, services);
		createCompletedJobLineWithCarrier(scheduleId, productId, goodsTypeId, services);

		final Map<ShipmentScheduleId, ResolvedCarrier> result =
				pickingJobRepository.getCarrierByScheduleIds(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result).containsKey(scheduleId);
		final ResolvedCarrier resolved = result.get(scheduleId);
		Assertions.assertThat(resolved.getCarrierProductId()).isEqualTo(productId);
		Assertions.assertThat(resolved.getCarrierGoodsTypeId()).isEqualTo(goodsTypeId);
		Assertions.assertThat(resolved.getCarrierServices()).isEqualTo(services);
	}

	@Test
	void getCarrierByScheduleIds_divergentLines_abort()
	{
		// one schedule with two lines carrying DIFFERENT carrier products → no single send-time carrier → abort
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(203);
		createCompletedJobLineWithCarrier(scheduleId, CarrierProductId.ofRepoId(5001), null, ImmutableSet.of());
		createCompletedJobLineWithCarrier(scheduleId, CarrierProductId.ofRepoId(5002), null, ImmutableSet.of());

		Assertions.assertThatThrownBy(() -> pickingJobRepository.getCarrierByScheduleIds(ImmutableSet.of(scheduleId)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("divergent");
	}

	@Test
	void existsActivePickingJobLineForSchedule_voidedJob_isNotBusy()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(101);
		createPickingJobWithLine(PickingJobDocStatus.Voided, scheduleId);

		Assertions.assertThat(pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId))
				.as("a voided picking job must NOT count as busy")
				.isFalse();
	}

	@Test
	void existsActivePickingJobLineForSchedule_completedJob_isNotBusy()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(102);
		createPickingJobWithLine(PickingJobDocStatus.Completed, scheduleId);

		Assertions.assertThat(pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId))
				.as("a completed picking job must NOT count as busy")
				.isFalse();
	}

	@Test
	void existsActivePickingJobLineForSchedule_draftedJob_isBusy()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(103);
		createPickingJobWithLine(PickingJobDocStatus.Drafted, scheduleId);

		Assertions.assertThat(pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId))
				.as("a drafted (in-progress) picking job must count as busy")
				.isTrue();
	}

	@Test
	void existsActivePickingJobLineForSchedule_noLine_isNotBusy()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(104);

		Assertions.assertThat(pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId))
				.as("no picking job line for the schedule must NOT count as busy")
				.isFalse();
	}
}
