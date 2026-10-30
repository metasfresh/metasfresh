package de.metas.frontend_testing.masterdata.dd_order;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateProcessRequest;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.distribution.mobileui.launchers.DistributionLauncherCaptionProvider;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generates a DD_Order the way production does: saves one {@link DDOrderCandidate} per request entry and runs
 * {@link DDOrderCandidateService#process(DDOrderCandidateProcessRequest)} synchronously.
 */
@Builder
public class DDOrderFromCandidatesCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderCandidateService ddOrderCandidateService;
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	@NonNull private final DistributionJobLoaderSupportingServices distributionJobLoaderSupportingServices;
	@NonNull private final DistributionLauncherCaptionProvider captionProvider;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonDDOrderFromCandidatesRequest request;
	@NonNull private final Identifier identifier;

	public JsonDDOrderResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonDDOrderResponse execute0()
	{
		final WarehouseId fromWarehouseId = context.getId(request.getWarehouseFrom(), WarehouseId.class);
		final WarehouseId toWarehouseId = context.getId(request.getWarehouseTo(), WarehouseId.class);
		final ProductId productId = context.getId(request.getProduct(), ProductId.class);
		final ResourceId plantId = MasterdataContext.DEFAULT_PLANT_ID;
		final ShipperId shipperId = getShipperId();

		// one timestamp for the whole request, so that all candidates share the same DD_Order header key
		final Instant now = SystemTime.asInstant();

		final ImmutableList.Builder<DDOrderCandidate> candidates = ImmutableList.builder();
		for (final JsonDDOrderFromCandidatesRequest.Candidate candidateRequest : request.getCandidates())
		{
			final DDOrderCandidate candidate = DDOrderCandidate.builder()
					.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(MasterdataContext.CLIENT_ID, MasterdataContext.ORG_ID))
					.dateOrdered(now)
					.supplyDate(now)
					.demandDate(now)
					.productId(productId)
					.qtyEntered(Quantity.of(candidateRequest.getQty(), productBL.getStockUOM(productId)))
					.sourceWarehouseId(fromWarehouseId)
					.targetWarehouseId(toWarehouseId)
					.targetPlantId(plantId)
					.shipperId(shipperId)
					.isSimulated(false)
					.forwardPPOrderRef(PPOrderRef.builder()
							.ppOrderId(context.getId(candidateRequest.getPpOrder(), PPOrderId.class))
							.ppOrderBOMLineId(context.getId(candidateRequest.getPpOrderBOMLine(), PPOrderBOMLineId.class))
							.build())
					.build();
			ddOrderCandidateService.save(candidate);
			candidates.add(candidate);
		}

		final ImmutableList<DDOrderCandidate> candidatesList = candidates.build();
		ddOrderCandidateService.process(DDOrderCandidateProcessRequest.builder()
				.userId(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
				.candidates(candidatesList)
				.build());

		final DDOrderId ddOrderId = getSingleDDOrderId(candidatesList);
		final I_DD_Order ddOrder = ddOrderService.getById(ddOrderId);
		context.putIdentifier(identifier, ddOrderId);

		return DDOrderResponseFactory.builder()
				.distributionJobLoaderSupportingServices(distributionJobLoaderSupportingServices)
				.captionProvider(captionProvider)
				.context(context)
				.build()
				.toResponse(ddOrder, fromWarehouseId, toWarehouseId, plantId);
	}

	private ShipperId getShipperId()
	{
		if (request.getShipper() != null)
		{
			return context.getId(request.getShipper(), ShipperId.class);
		}

		final ShipperId defaultShipperId = shipperDAO.getDefault(MasterdataContext.CLIENT_ID);
		if (defaultShipperId == null)
		{
			throw new AdempiereException("No default shipper found. Pass the 'shipper' field (identifier of a shipper created in the 'shippers' section).");
		}
		return defaultShipperId;
	}

	private DDOrderId getSingleDDOrderId(final ImmutableList<DDOrderCandidate> candidates)
	{
		final Set<DDOrderCandidateId> candidateIds = candidates.stream().map(DDOrderCandidate::getIdNotNull).collect(Collectors.toSet());
		final Set<DDOrderId> ddOrderIds = ddOrderCandidateAllocRepository.getByCandidateIds(candidateIds).getDDOrderIds();
		if (ddOrderIds.size() != 1)
		{
			throw new AdempiereException("Expected exactly one DD_Order to be generated from candidates but got " + ddOrderIds)
					.setParameter("candidates", candidates);
		}
		return ddOrderIds.iterator().next();
	}
}
