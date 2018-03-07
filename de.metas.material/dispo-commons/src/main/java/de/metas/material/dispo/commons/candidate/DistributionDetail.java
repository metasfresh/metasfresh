package de.metas.material.dispo.commons.candidate;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DistributionDetail implements BusinessCaseDetail
{
	public static DistributionDetail forDistributionDetailRecord(
			@NonNull final I_MD_Candidate_Dist_Detail distributionDetailRecord)
	{
		final DistributionDetail distributionDetail = DistributionDetail.builder()
				.advised(distributionDetailRecord.isAdvised())
				.pickDirectlyIfFeasible(distributionDetailRecord.isPickDirectlyIfFeasible())
				.networkDistributionLineId(distributionDetailRecord.getDD_NetworkDistributionLine_ID())
				.productPlanningId(distributionDetailRecord.getPP_Product_Planning_ID())
				.plantId(distributionDetailRecord.getPP_Plant_ID())
				.ddOrderId(distributionDetailRecord.getDD_Order_ID())
				.ddOrderLineId(distributionDetailRecord.getDD_OrderLine_ID())
				.ddOrderDocStatus(distributionDetailRecord.getDD_Order_DocStatus())
				.plannedQty(distributionDetailRecord.getPlannedQty())
				.shipperId(distributionDetailRecord.getM_Shipper_ID())
				.build();

		return distributionDetail;
	}

	int productPlanningId;

	int plantId;

	int networkDistributionLineId;

	int shipperId;

	int ddOrderId;

	int ddOrderLineId;

	String ddOrderDocStatus;

	boolean advised;

	boolean pickDirectlyIfFeasible;

	BigDecimal plannedQty;

	public static DistributionDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		final boolean canBeCast = businessCaseDetail != null && businessCaseDetail instanceof DistributionDetail;
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

}
