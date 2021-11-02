package de.metas.handlingunits.picking.job.repository;

import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

class PickingJobCreateRepoCommand
{
	private final PickingJobLoaderSupportingServices loadingSupportServices;
	private final PickingJobCreateRepoRequest request;

	private PickingJobLoaderAndSaver loader;

	public PickingJobCreateRepoCommand(
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices,
			@NonNull PickingJobCreateRepoRequest request)
	{
		this.loadingSupportServices = loadingSupportServices;

		this.request = request;
	}

	public PickingJob execute()
	{
		this.loader = PickingJobLoaderAndSaver.forLoading(loadingSupportServices);

		final I_M_Picking_Job record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		final OrgId orgId = request.getOrgId();
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_Order_ID(request.getSalesOrderId().getRepoId());
		record.setPreparationDate(request.getPreparationDate().toTimestamp());
		record.setC_BPartner_ID(request.getDeliveryBPLocationId().getBpartnerId().getRepoId());
		record.setC_BPartner_Location_ID(request.getDeliveryBPLocationId().getRepoId());
		record.setDeliveryToAddress(request.getDeliveryRenderedAddress());
		record.setPicking_User_ID(request.getPickerId().getRepoId());
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setProcessed(false);
		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		request.getLines().forEach(line -> createLineRecord(line, pickingJobId, request.getOrgId()));

		return loader.loadById(pickingJobId);
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
		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);

		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());
		line.getSteps().forEach(step -> createStepRecord(step, pickingJobId, pickingJobLineId, orgId));
	}

	private void createStepRecord(
			@NonNull final PickingJobCreateRepoRequest.Step request,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobLineId pickingJobLineId,
			@NonNull final OrgId orgId)
	{
		final I_M_Picking_Job_Step record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
		record.setM_Picking_Job_ID(pickingJobId.getRepoId());
		record.setM_Picking_Job_Line_ID(pickingJobLineId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		record.setC_Order_ID(request.getSalesOrderLineId().getOrderRepoId());
		record.setC_OrderLine_ID(request.getSalesOrderLineId().getOrderLineRepoId());

		//
		// What?
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(request.getQtyToPick().toBigDecimal());

		//
		// From where?
		record.setPickFrom_Warehouse_ID(request.getLocatorId().getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(request.getLocatorId().getRepoId());
		record.setPickFrom_HU_ID(request.getPickFromHUId().getRepoId());

		InterfaceWrapperHelper.save(record);

		loader.addAlreadyLoadedFromDB(record);
	}
}
