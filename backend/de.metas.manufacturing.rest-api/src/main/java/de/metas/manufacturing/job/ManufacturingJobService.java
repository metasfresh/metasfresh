package de.metas.manufacturing.job;

import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.order.PPOrderIssueScheduleRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Stream;

@Service
public class ManufacturingJobService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPPOrderBL ppOrderBL;
	private final IPPOrderRoutingRepository ppOrderRoutingActivity;
	private final PPOrderIssueScheduleRepository ppOrderIssueScheduleRepository;
	private final HUReservationService huReservationService;
	private final ManufacturingJobLoaderSupportingServices loadingSupportServices;

	public ManufacturingJobService(
			final PPOrderIssueScheduleRepository ppOrderIssueScheduleRepository,
			final HUReservationService huReservationService)
	{
		this.ppOrderIssueScheduleRepository = ppOrderIssueScheduleRepository;

		this.loadingSupportServices = ManufacturingJobLoaderSupportingServices.builder()
				.warehouseBL(Services.get(IWarehouseBL.class))
				.productBL(Services.get(IProductBL.class))
				.ppOrderBL(ppOrderBL = Services.get(IPPOrderBL.class))
				.ppOrderBOMBL(Services.get(IPPOrderBOMBL.class))
				.ppOrderRoutingRepository(ppOrderRoutingActivity = Services.get(IPPOrderRoutingRepository.class))
				.ppOrderIssueScheduleRepository(ppOrderIssueScheduleRepository)
				.build();
		this.huReservationService = huReservationService;
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId) {return newLoader().load(ppOrderId);}

	@NonNull
	private ManufacturingJobLoader newLoader() {return new ManufacturingJobLoader(loadingSupportServices);}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(
			final @NonNull UserId responsibleId,
			final @NonNull QueryLimit suggestedLimit)
	{
		final ArrayList<ManufacturingJobReference> result = new ArrayList<>();

		//
		// Already started jobs
		streamAlreadyStartedJobs(responsibleId)
				.forEach(result::add);

		//
		// New possible jobs
		if (!suggestedLimit.isLimitHitOrExceeded(result))
		{
			streamJobCandidatesToCreate()
					.limit(suggestedLimit.minusSizeOf(result).toIntOr(Integer.MAX_VALUE))
					.forEach(result::add);
		}

		return result.stream();
	}

	private Stream<ManufacturingJobReference> streamAlreadyStartedJobs(@NonNull final UserId responsibleId)
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, true));
	}

	private Stream<ManufacturingJobReference> streamJobCandidatesToCreate()
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.isNull())
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, false));
	}

	private ManufacturingJobReference toManufacturingJobReference(final I_PP_Order ppOrder, final boolean isJobStarted)
	{
		return ManufacturingJobReference.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.documentNo(ppOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(orgDAO::getTimeZone))
				.productName(loadingSupportServices.getProductName(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
				.qtyRequiredToProduce(loadingSupportServices.getQuantities(ppOrder).getQtyRequiredToProduce())
				.isJobStarted(isJobStarted)
				.build();
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		return ManufacturingJobCreateCommand.builder()
				.trxManager(trxManager)
				.ppOrderBL(ppOrderBL)
				.huReservationService(huReservationService)
				.orderIssueScheduleRepository(ppOrderIssueScheduleRepository)
				.loadingSupportServices(loadingSupportServices)
				//
				.ppOrderId(ppOrderId)
				.responsibleId(responsibleId)
				.build()
				.execute();
	}

	public ManufacturingJob withActivityCompleted(ManufacturingJob job, ManufacturingJobActivityId jobActivityId)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();
		final ManufacturingJobActivity jobActivity = job.getActivityById(jobActivityId);
		final PPOrderRoutingActivityId orderRoutingActivityId = jobActivity.getOrderRoutingActivityId();

		final PPOrderRouting orderRouting = ppOrderRoutingActivity.getByOrderId(ppOrderId);
		final PPOrderRouting orderRoutingBeforeChange = orderRouting.copy();
		orderRouting.completeActivity(orderRoutingActivityId);

		if (!orderRouting.equals(orderRoutingBeforeChange))
		{
			ppOrderRoutingActivity.save(orderRouting);
			return getJobById(ppOrderId);
		}
		else
		{
			return job;
		}
	}
}
