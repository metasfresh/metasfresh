package de.metas.distribution.ddordercandidate.material_dispo;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_App) // only one handler should bother itself with these events
@RequiredArgsConstructor
public class DDOrderCandidateRequestedEventHandler
		implements MaterialEventHandler<DDOrderCandidateRequestedEvent>
{
	@NonNull private static final Logger logger = LogManager.getLogger(DDOrderCandidateRequestedEventHandler.class);
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;

	@Override
	public Collection<Class<? extends DDOrderCandidateRequestedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		final DDOrderCandidate ddOrderCandidate = toDDOrderCandidate(event);
		ddOrderCandidateRepository.save(ddOrderCandidate);

		Loggables.withLogger(logger, Level.DEBUG).addLog("Created DD Order candidate: {}", ddOrderCandidate);
	}

	private static DDOrderCandidate toDDOrderCandidate(@NonNull final DDOrderCandidateRequestedEvent event)
	{
		final DDOrderCandidateData data = event.getDdOrderCandidateData();

		return DDOrderCandidate.builder()
				.orgId(data.getOrgId())
				.dateOrdered(event.getDateOrdered())
				.datePromised(data.getDatePromised())
				//
				.productId(ProductId.ofRepoId(data.getProductId()))
				//.hupiItemProductId(...) // TODO
				.qty(Quantitys.create(data.getQty(), UomId.ofRepoId(data.getUomId())))
				//.qtyTUs(...) // TODO
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(data.getAttributeSetInstanceId()))
				//
				.sourceWarehouseId(data.getSourceWarehouseId())
				.targetWarehouseId(data.getTargetWarehouseId())
				.targetPlantId(data.getTargetPlantId())
				.shipperId(data.getShipperId())
				//
				.isSimulated(data.isSimulated())
				//
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(data.getSalesOrderLineId()))
				//
				.distributionNetworkAndLineId(data.getDistributionNetworkAndLineId())
				.productPlanningId(data.getProductPlanningId())
				//
				.traceId(event.getTraceId())
				.materialDispoGroupId(data.getMaterialDispoGroupId())
				.build();
	}

}
