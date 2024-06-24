package de.metas.manufacturing.job.model;

import de.metas.common.util.CoalesceUtil;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.material.planning.pporder.UserInstructions;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
public class ManufacturingJobActivity
{
	@NonNull ManufacturingJobActivityId id;
	@NonNull String name;
	@NonNull PPRoutingActivityType type;

	@Nullable RawMaterialsIssue rawMaterialsIssue;
	@Nullable FinishedGoodsReceive finishedGoodsReceive;
	@Getter @Nullable GlobalQRCode scannedQRCode;

	@NonNull PPOrderRoutingActivityId orderRoutingActivityId;
	@NonNull PPOrderRoutingActivityStatus routingActivityStatus;

	@NonNull WFActivityStatus status;

	@NonNull PPAlwaysAvailableToUser alwaysAvailableToUser;
	@Nullable UserInstructions userInstructions;

	@Builder(toBuilder = true)
	private ManufacturingJobActivity(
			@NonNull final ManufacturingJobActivityId id,
			@NonNull final String name,
			@NonNull final PPRoutingActivityType type,
			@Nullable final RawMaterialsIssue rawMaterialsIssue,
			@Nullable final FinishedGoodsReceive finishedGoodsReceive,
			@Nullable final GlobalQRCode scannedQRCode,
			@NonNull final PPOrderRoutingActivityId orderRoutingActivityId,
			@NonNull final PPOrderRoutingActivityStatus routingActivityStatus,
			@NonNull final PPAlwaysAvailableToUser alwaysAvailableToUser,
			@Nullable final UserInstructions userInstructions)
	{
		if (CoalesceUtil.countNotNulls(rawMaterialsIssue, finishedGoodsReceive) > 1)
		{
			throw new AdempiereException("Mixing raw material issuing and goods receiving in one activity is not allowed");
		}

		this.id = id;
		this.name = name;
		this.type = type;
		this.rawMaterialsIssue = rawMaterialsIssue;
		this.finishedGoodsReceive = finishedGoodsReceive;
		this.scannedQRCode = scannedQRCode;
		this.orderRoutingActivityId = orderRoutingActivityId;

		this.status = computeStatus(rawMaterialsIssue, finishedGoodsReceive, routingActivityStatus);
		this.routingActivityStatus = toPPOrderRoutingActivityStatus(this.status);

		this.alwaysAvailableToUser = alwaysAvailableToUser;
		this.userInstructions = userInstructions;
	}

	private static WFActivityStatus computeStatus(
			@Nullable final RawMaterialsIssue rawMaterialsIssue,
			@Nullable final FinishedGoodsReceive finishedGoodsReceive,
			@NonNull final PPOrderRoutingActivityStatus routingActivityStatus)
	{
		if (rawMaterialsIssue != null)
		{
			return rawMaterialsIssue.getStatus();
		}
		else if (finishedGoodsReceive != null)
		{
			return finishedGoodsReceive.getStatus();
		}
		else
		{
			return toWFActivityStatus(routingActivityStatus);
		}
	}

	private static WFActivityStatus toWFActivityStatus(final @NonNull PPOrderRoutingActivityStatus routingActivityStatus)
	{
		switch (routingActivityStatus)
		{
			case NOT_STARTED:
				return WFActivityStatus.NOT_STARTED;
			case IN_PROGRESS:
				return WFActivityStatus.IN_PROGRESS;
			case COMPLETED:
			case CLOSED:
			case VOIDED:
				return WFActivityStatus.COMPLETED;
			default:
				throw new AdempiereException("Unknown routingActivityStatus: " + routingActivityStatus);
		}
	}

	private static PPOrderRoutingActivityStatus toPPOrderRoutingActivityStatus(final @NonNull WFActivityStatus wfActivityStatus)
	{
		switch (wfActivityStatus)
		{
			case NOT_STARTED:
				return PPOrderRoutingActivityStatus.NOT_STARTED;
			case IN_PROGRESS:
				return PPOrderRoutingActivityStatus.IN_PROGRESS;
			case COMPLETED:
				return PPOrderRoutingActivityStatus.COMPLETED;
			default:
				throw new AdempiereException("Unknown wfActivityStatus: " + wfActivityStatus);
		}
	}

	public RawMaterialsIssue getRawMaterialsIssueAssumingNotNull()
	{
		if (rawMaterialsIssue == null)
		{
			throw new AdempiereException("Not a raw materials issuing activity");
		}
		return rawMaterialsIssue;
	}

	public FinishedGoodsReceive getFinishedGoodsReceiveAssumingNotNull()
	{
		if (finishedGoodsReceive == null)
		{
			throw new AdempiereException("Not a material receiving activity");
		}
		return finishedGoodsReceive;
	}

	public boolean containsRawMaterialsIssueStep(final PPOrderIssueScheduleId issueScheduleId)
	{
		return rawMaterialsIssue != null && rawMaterialsIssue.containsRawMaterialsIssueStep(issueScheduleId);
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

	public ManufacturingJobActivity withRawMaterialsIssue(@Nullable RawMaterialsIssue rawMaterialsIssue)
	{
		return Objects.equals(this.rawMaterialsIssue, rawMaterialsIssue) ? this : toBuilder().rawMaterialsIssue(rawMaterialsIssue).build();
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

	public ManufacturingJobActivity withFinishedGoodsReceive(@Nullable FinishedGoodsReceive finishedGoodsReceive)
	{
		return Objects.equals(this.finishedGoodsReceive, finishedGoodsReceive) ? this : toBuilder().finishedGoodsReceive(finishedGoodsReceive).build();
	}
}
