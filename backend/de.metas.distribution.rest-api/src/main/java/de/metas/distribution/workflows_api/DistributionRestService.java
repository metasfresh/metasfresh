package de.metas.distribution.workflows_api;

import de.metas.dao.ValueRestriction;
import de.metas.document.engine.DocStatus;
import de.metas.ddorder.DDOrderService;
import de.metas.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderQuery;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class DistributionRestService
{
	private final DDOrderService ddOrderService;
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private final DistributionJobLoaderSupportingServices loadingSupportServices;

	public DistributionRestService(
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		this.ddOrderService = ddOrderService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;

		this.loadingSupportServices = DistributionJobLoaderSupportingServices.builder()
				.ddOrderService(ddOrderService)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.warehouseBL(Services.get(IWarehouseBL.class))
				.productBL(Services.get(IProductBL.class))
				.orgDAO(Services.get(IOrgDAO.class))
				.build();
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return ddOrderMoveScheduleService.getQtyRejectedReasons();
	}

	public Stream<DDOrderReference> streamActiveReferencesAssignedTo(@NonNull final UserId responsibleId)
	{
		return ddOrderService.streamDDOrders(DDOrderQuery.builder()
						.docStatus(DocStatus.Completed)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.orderBy(DDOrderQuery.OrderBy.PriorityRule)
						.orderBy(DDOrderQuery.OrderBy.DatePromised)
						.build())
				.map(DistributionRestService::toDDOrderReference);
	}

	public Stream<DDOrderReference> streamActiveReferencesNotAssigned()
	{
		return ddOrderService.streamDDOrders(DDOrderQuery.builder()
						.docStatus(DocStatus.Completed)
						.responsibleId(ValueRestriction.isNull())
						.orderBy(DDOrderQuery.OrderBy.PriorityRule)
						.orderBy(DDOrderQuery.OrderBy.DatePromised)
						.build())
				.map(DistributionRestService::toDDOrderReference);
	}

	private static DDOrderReference toDDOrderReference(final I_DD_Order ddOrder)
	{
		return DDOrderReference.builder()
				.ddOrderId(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.documentNo(ddOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID()))
				.fromWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID()))
				.toWarehouseId(WarehouseId.ofRepoId(ddOrder.getM_Warehouse_To_ID()))
				.build();
	}

	public DistributionJob createJob(
			final @NonNull DDOrderId ddOrderId,
			final @NonNull UserId responsibleId)
	{
		return DistributionJobCreateCommand.builder()
				.ddOrderService(ddOrderService)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.loadingSupportServices(loadingSupportServices)
				//
				.ddOrderId(ddOrderId)
				.responsibleId(responsibleId)
				//
				.build().execute();
	}

	public DistributionJob getJobById(final DDOrderId ddOrderId)
	{
		return new DistributionJobLoader(loadingSupportServices)
				.load(ddOrderId);
	}
}
