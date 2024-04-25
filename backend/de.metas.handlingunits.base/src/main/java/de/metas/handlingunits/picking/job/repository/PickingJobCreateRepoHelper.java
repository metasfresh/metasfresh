package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_HUAlternative;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternativeId;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

class PickingJobCreateRepoHelper
{
	private final PickingJobLoaderAndSaver loader;

	public PickingJobCreateRepoHelper(@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		this.loader = PickingJobLoaderAndSaver.forLoading(loadingSupportServices);
	}

	public PickingJob createPickingJob(@NonNull final PickingJobCreateRepoRequest request)
	{
		final PickingJobId pickingJobId = createPickingJobHeader(request);
		final OrgId orgId = request.getOrgId();

		request.getLines()
				.stream()
				.flatMap(line -> line.getPickFromAlternatives()
						.stream()
						.map(alt -> PickFromAlternativeCreateRequest.builder()
								.pickFromLocatorId(alt.getPickFromLocatorId())
								.pickFromHUId(alt.getPickFromHUId())
								.productId(line.getProductId())
								.qtyAvailable(alt.getQtyAvailable())
								.pickingJobId(pickingJobId)
								.build()))
				.distinct()
				.forEach(from -> createPickFromAlternative(from, orgId));

		request.getLines().forEach(line -> createLineRecord(line, pickingJobId, orgId));

		return loader.loadById(pickingJobId);
	}

	@NonNull
	private PickingJobId createPickingJobHeader(@NonNull final PickingJobCreateRepoRequest request)
	{
		final I_M_Picking_Job record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		final OrgId orgId = request.getOrgId();
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_Order_ID(request.getSalesOrderId().getRepoId());
		record.setPreparationDate(request.getPreparationDate().toTimestamp());
		record.setDeliveryDate(request.getDeliveryDate().toTimestamp());
		record.setC_BPartner_ID(request.getDeliveryBPLocationId().getBpartnerId().getRepoId());
		record.setC_BPartner_Location_ID(request.getDeliveryBPLocationId().getRepoId());
		record.setDeliveryToAddress(request.getDeliveryRenderedAddress());
		record.setPicking_User_ID(request.getPickerId().getRepoId());
		record.setIsAllowPickingAnyHU(request.isAllowPickingAnyHU());
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setProcessed(false);
		record.setHandover_Location_ID(BPartnerLocationId.toRepoId(request.getHandoverLocationId()));
		record.setHandover_Partner_ID(BPartnerId.toRepoId(request.getHandoverLocationId().getBpartnerId()));
		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);

		return PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
	}

	private void createLineRecord(
			@NonNull final PickingJobCreateRepoRequest.Line line,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final OrgId orgId)
	{
		final I_M_Picking_Job_Line record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		record.setM_Picking_Job_ID(pickingJobId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_Product_ID(line.getProductId().getRepoId());
		record.setM_HU_PI_Item_Product_ID(line.getHuPIItemProductId().getRepoId());
		record.setQtyToPick(line.getQtyToPick().toBigDecimal());
		record.setC_UOM_ID(line.getQtyToPick().getUomId().getRepoId());
		record.setC_Order_ID(OrderAndLineId.toOrderRepoId(line.getSalesOrderAndLineId()));
		record.setC_OrderLine_ID(OrderAndLineId.toOrderLineRepoId(line.getSalesOrderAndLineId()));
		record.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(line.getShipmentScheduleId()));
		record.setCatch_UOM_ID(UomId.toRepoId(line.getCatchWeightUomId()));
		InterfaceWrapperHelper.save(record);
		loader.addAlreadyLoadedFromDB(record);

		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());
		line.getSteps().forEach(step -> createStepRecord(step, pickingJobId, pickingJobLineId, orgId));
	}

	private void createStepRecord(
			@NonNull final PickingJobCreateRepoRequest.Step step,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobLineId pickingJobLineId,
			@NonNull final OrgId orgId)
	{
		final I_M_Picking_Job_Step record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
		record.setIsDynamic(false);
		record.setM_Picking_Job_ID(pickingJobId.getRepoId());
		record.setM_Picking_Job_Line_ID(pickingJobLineId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_ShipmentSchedule_ID(step.getShipmentScheduleId().getRepoId());
		record.setC_Order_ID(step.getSalesOrderLineId().getOrderRepoId());
		record.setC_OrderLine_ID(step.getSalesOrderLineId().getOrderLineRepoId());

		//
		// What?
		record.setM_Product_ID(step.getProductId().getRepoId());
		record.setC_UOM_ID(step.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(step.getQtyToPick().toBigDecimal());

		//
		// Main PickFrom
		record.setPickFrom_Warehouse_ID(step.getMainPickFrom().getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(step.getMainPickFrom().getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(step.getMainPickFrom().getPickFromHUId().getRepoId());

		//
		// Packing
		step.getPackToSpec()
				.map(new PackToSpec.CaseMapper<Void>()
				{
					@Override
					public Void packToVirtualHU()
					{
						record.setPackTo_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
						return null;
					}

					@Override
					public Void packToTU(final HUPIItemProductId tuPackingInstructionsId)
					{
						record.setPackTo_HU_PI_Item_Product_ID(tuPackingInstructionsId.getRepoId());
						return null;
					}

					@Override
					public Void packToGenericHU(final HuPackingInstructionsId genericPackingInstructionsId)
					{
						throw new AdempiereException("Packing to generic packing instructions is not supported: " + genericPackingInstructionsId);
					}
				});

		InterfaceWrapperHelper.save(record);
		loader.addAlreadyLoadedFromDB(record);
		final PickingJobStepId pickingJobStepId = PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID());

		step.getPickFromAlternatives()
				.forEach(pickFrom -> createStepHUAlternativeRecord(
						pickFrom,
						step.getProductId(),
						pickingJobStepId,
						pickingJobId,
						orgId));
	}

	private void createStepHUAlternativeRecord(
			@NonNull final PickingJobCreateRepoRequest.StepPickFrom pickFrom,
			@NonNull final ProductId productId,
			@NonNull final PickingJobStepId pickingJobStepId,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final OrgId orgId)
	{
		final I_M_Picking_Job_Step_HUAlternative record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step_HUAlternative.class);
		record.setM_Picking_Job_ID(pickingJobId.getRepoId());
		record.setM_Picking_Job_Step_ID(pickingJobStepId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_Picking_Job_HUAlternative_ID(loader.getPickingJobHUAlternativeId(pickingJobId, pickFrom.getPickFromHUId(), productId).getRepoId());
		record.setPickFrom_Warehouse_ID(pickFrom.getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(pickFrom.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(pickFrom.getPickFromHUId().getRepoId());
		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);
	}

	private void createPickFromAlternative(
			@NonNull final PickFromAlternativeCreateRequest from,
			@NonNull final OrgId orgId)
	{
		final I_M_Picking_Job_HUAlternative record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_HUAlternative.class);
		record.setM_Picking_Job_ID(from.getPickingJobId().getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setPickFrom_Warehouse_ID(from.getPickFromLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(from.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(from.getPickFromHUId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setC_UOM_ID(from.getQtyAvailable().getUomId().getRepoId());
		record.setQtyAvailable(from.getQtyAvailable().toBigDecimal());
		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);

		PickingJobPickFromAlternativeId.ofRepoId(record.getM_Picking_Job_HUAlternative_ID());
	}

	//
	//
	//

	@Value
	@Builder
	private static class PickFromAlternativeCreateRequest
	{
		@NonNull LocatorId pickFromLocatorId;
		@NonNull HuId pickFromHUId;
		@NonNull ProductId productId;
		@NonNull Quantity qtyAvailable;
		@NonNull PickingJobId pickingJobId;
	}
}
