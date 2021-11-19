package de.metas.manufacturing.job.model;

import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

@Value
@Builder
public class ManufacturingJobActivity
{
	@NonNull ManufacturingJobActivityId id;
	@NonNull String name;
	@NonNull PPRoutingActivityType type;

	@With
	@Nullable RawMaterialsIssue rawMaterialsIssue;
	@With
	@Nullable FinishedGoodsReceive finishedGoodsReceive;

	@NonNull PPOrderRoutingActivityId orderRoutingActivityId;
	@NonNull PPOrderRoutingActivityStatus status;

	public FinishedGoodsReceive getFinishedGoodsReceiveAssumingNotNull()
	{
		if (finishedGoodsReceive == null)
		{
			throw new AdempiereException("Not an material receiving activity");
		}
		return finishedGoodsReceive;
	}

	public ManufacturingJobActivity withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		if (rawMaterialsIssue != null)
		{
			return withRawMaterialsIssue(rawMaterialsIssue.withChangedRawMaterialsIssueStep(issueScheduleId, mapper));
		}
		else
		{
			return this;
		}
	}

	public ManufacturingJobActivity withChangedReceiveLine(
			@NonNull final FinishedGoodsReceiveLineId id,
			@NonNull UnaryOperator<FinishedGoodsReceiveLine> mapper)
	{
		if (finishedGoodsReceive != null)
		{
			return withFinishedGoodsReceive(finishedGoodsReceive.withChangedReceiveLine(id, mapper));
		}
		else
		{
			return this;
		}
	}

}
