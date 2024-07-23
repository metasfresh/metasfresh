package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
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

	int ddOrderId;
	int ddOrderLineId;
	DocStatus ddOrderDocStatus;

	//
	// Forward manufacturing
	@Nullable PPOrderRef ppOrderRef;

	boolean advised;

	@NonNull @Default Flag pickDirectlyIfFeasible = Flag.FALSE;
	@NonNull BigDecimal qty;

	public static DistributionDetail ofRecord(@NonNull final I_MD_Candidate_Dist_Detail distributionDetailRecord)
	{
		return DistributionDetail.builder()
				.advised(distributionDetailRecord.isAdvised())
				.pickDirectlyIfFeasible(Flag.of(distributionDetailRecord.isPickDirectlyIfFeasible()))
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIdsOrNull(distributionDetailRecord.getDD_NetworkDistribution_ID(), distributionDetailRecord.getDD_NetworkDistributionLine_ID()))
				.productPlanningId(ProductPlanningId.ofRepoIdOrNull(distributionDetailRecord.getPP_Product_Planning_ID()))
				.plantId(ResourceId.ofRepoIdOrNull(distributionDetailRecord.getPP_Plant_ID()))
				.ddOrderId(distributionDetailRecord.getDD_Order_ID())
				.ddOrderLineId(distributionDetailRecord.getDD_OrderLine_ID())
				.ddOrderDocStatus(DocStatus.ofNullableCode(distributionDetailRecord.getDD_Order_DocStatus()))
				.ppOrderRef(extractPPOrderRef(distributionDetailRecord))
				.qty(distributionDetailRecord.getPlannedQty())
				.shipperId(ShipperId.ofRepoIdOrNull(distributionDetailRecord.getM_Shipper_ID()))
				.build();
	}

	@Nullable
	private static PPOrderRef extractPPOrderRef(final I_MD_Candidate_Dist_Detail record)
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
		final PPOrderRef ppOrderRefNew = PPOrderRef.withPPOrderId(ppOrderRef, newPPOrderId);
		if (Objects.equals(this.ppOrderRef, ppOrderRefNew))
		{
			return this;
		}

		return toBuilder().ppOrderRef(ppOrderRefNew).build();
	}
}
