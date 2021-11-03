package de.metas.manufacturing.job;

import de.metas.dao.ValueRestriction;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderDAO;
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
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPPOrderDAO ppOrderDAO;
	private final IPPOrderRoutingRepository ppOrderRoutingActivity;
	private final ManufacturingJobLoaderSupportingServices loadingSupportServices;

	public ManufacturingJobService()
	{
		this.loadingSupportServices = ManufacturingJobLoaderSupportingServices.builder()
				.productBL(Services.get(IProductBL.class))
				.ppOrderDAO(ppOrderDAO = Services.get(IPPOrderDAO.class))
				.ppOrderBOMBL(Services.get(IPPOrderBOMBL.class))
				.ppOrderRoutingRepository(ppOrderRoutingActivity = Services.get(IPPOrderRoutingRepository.class))
				.build();
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId)
	{
		return newLoader().load(ppOrderId);
	}

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
		return ppOrderDAO.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, true));
	}

	private Stream<ManufacturingJobReference> streamJobCandidatesToCreate()
	{
		return ppOrderDAO.streamManufacturingOrders(ManufacturingOrderQuery.builder()
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
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		setResponsible(ppOrder, responsibleId);

		return newLoader()
				.addToCache(ppOrder)
				.load(ppOrderId);
	}

	private void setResponsible(@NonNull final I_PP_Order ppOrder, @NonNull final UserId responsibleId)
	{
		final UserId previousResponsibleId = UserId.ofRepoIdOrNullIfSystem(ppOrder.getAD_User_Responsible_ID());
		if (UserId.equals(previousResponsibleId, responsibleId))
		{
			//noinspection UnnecessaryReturnStatement
			return;
		}
		else if (previousResponsibleId != null)
		{
			throw new AdempiereException("Order is already assigned");
		}
		else
		{
			ppOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			ppOrderDAO.save(ppOrder);
		}
	}

	@NonNull
	private ManufacturingJobLoader newLoader()
	{
		return new ManufacturingJobLoader(loadingSupportServices);
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
