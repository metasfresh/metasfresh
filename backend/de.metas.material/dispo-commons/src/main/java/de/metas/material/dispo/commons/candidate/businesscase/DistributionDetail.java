package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class DistributionDetail implements BusinessCaseDetail
{
	@Nullable ProductPlanningId productPlanningId;
	@Nullable ResourceId plantId;
	@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;
	@Nullable ShipperId shipperId;

	@Nullable DDOrderRef ddOrderRef;
	DocStatus ddOrderDocStatus;

	//
	// Forward manufacturing
	@Nullable PPOrderRef forwardPPOrderRef;

	boolean advised;

	@NonNull @Default Flag pickDirectlyIfFeasible = Flag.FALSE;
	@NonNull BigDecimal qty;

	public static DistributionDetail ofRecord(@NonNull final I_MD_Candidate_Dist_Detail record)
	{
		return DistributionDetail.builder()
				.advised(record.isAdvised())
				.pickDirectlyIfFeasible(Flag.of(record.isPickDirectlyIfFeasible()))
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIdsOrNull(record.getDD_NetworkDistribution_ID(), record.getDD_NetworkDistributionLine_ID()))
				.productPlanningId(ProductPlanningId.ofRepoIdOrNull(record.getPP_Product_Planning_ID()))
				.plantId(ResourceId.ofRepoIdOrNull(record.getPP_Plant_ID()))
				.ddOrderRef(extractDDOrderRef(record))
				.ddOrderDocStatus(DocStatus.ofNullableCode(record.getDD_Order_DocStatus()))
				.forwardPPOrderRef(extractForwardPPOrderRef(record))
				.qty(record.getPlannedQty())
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.build();
	}

	@Nullable
	private static DDOrderRef extractDDOrderRef(final I_MD_Candidate_Dist_Detail record)
	{
		final int ddOrderCandidateId = record.getDD_Order_Candidate_ID();
		final int ddOrderId = record.getDD_Order_ID();
		if (ddOrderCandidateId <= 0 && ddOrderId <= 0)
		{
			return null;
		}

		return DDOrderRef.builder()
				.ddOrderCandidateId(ddOrderCandidateId)
				.ddOrderId(ddOrderId)
				.ddOrderLineId(record.getDD_OrderLine_ID())
				.build();
	}

	@Nullable
	private static PPOrderRef extractForwardPPOrderRef(final I_MD_Candidate_Dist_Detail record)
	{
		final int ppOrderCandidateId = record.getPP_Order_Candidate_ID();
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(record.getPP_Order_ID());
		if (ppOrderCandidateId <= 0 && ppOrderId == null)
		{
			return null;
		}

		return PPOrderRef.builder()
				.ppOrderCandidateId(ppOrderCandidateId)
				.ppOrderLineCandidateId(record.getPP_OrderLine_Candidate_ID())
				.ppOrderId(ppOrderId)
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(record.getPP_Order_BOMLine_ID()))
				.build();
	}

	public static DistributionDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		final boolean canBeCast = businessCaseDetail instanceof DistributionDetail;
		if (canBeCast)
		{
			return cast(businessCaseDetail);
		}
		return null;
	}

	public static DistributionDetail cast(@NonNull final BusinessCaseDetail businessCaseDetail)
	{
		return (DistributionDetail)businessCaseDetail;
	}

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.DISTRIBUTION;
	}

	public BusinessCaseDetail withPPOrderId(@Nullable final PPOrderId newPPOrderId)
	{
		final PPOrderRef ppOrderRefNew = PPOrderRef.withPPOrderId(forwardPPOrderRef, newPPOrderId);
		if (Objects.equals(this.forwardPPOrderRef, ppOrderRefNew))
		{
			return this;
		}

		return toBuilder().forwardPPOrderRef(ppOrderRefNew).build();
	}

	public DistributionDetail withDdOrderCandidateId(final int ddOrderCandidateIdNew)
	{
		return withDDOrderRef(DDOrderRef.withDdOrderCandidateId(ddOrderRef, ddOrderCandidateIdNew));
	}

	private DistributionDetail withDDOrderRef(final DDOrderRef ddOrderRefNew)
	{
		return Objects.equals(this.ddOrderRef, ddOrderRefNew)
				? this
				: toBuilder().ddOrderRef(ddOrderRefNew).build();
	}
}
