package de.metas.manufacturing.workflows_api.rest_api.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.util.CoalesceUtil;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonReceivingTarget;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public class JsonManufacturingOrderEvent
{
	//
	// Activity Identifier
	@NonNull String wfProcessId;
	@NonNull String wfActivityId;

	@Value
	@Builder
	@Jacksonized
	public static class IssueTo
	{
		@NonNull String issueStepId;
		@NonNull String huQRCode;
		@Nullable BigDecimal huWeightGrossBeforeIssue;
		@NonNull BigDecimal qtyIssued;
		@Nullable BigDecimal qtyRejected;
		@Nullable String qtyRejectedReasonCode;
	}

	@Nullable IssueTo issueTo;

	@Value
	@Builder
	@Jacksonized
	public static class ReceiveFrom
	{
		@NonNull String lineId;
		@NonNull BigDecimal qtyReceived;
		@NonNull JsonReceivingTarget aggregateToLU;

		@JsonIgnore
		public FinishedGoodsReceiveLineId getFinishedGoodsReceiveLineId() {return FinishedGoodsReceiveLineId.ofString(lineId);}
	}

	@Nullable ReceiveFrom receiveFrom;

	@Builder
	@Jacksonized
	private JsonManufacturingOrderEvent(
			@NonNull final String wfProcessId,
			@NonNull final String wfActivityId,
			//
			@Nullable final IssueTo issueTo,
			@Nullable final ReceiveFrom receiveFrom)
	{
		if (CoalesceUtil.countNotNulls(issueTo, receiveFrom) != 1)
		{
			throw new AdempiereException("One and only one action like issueTo, receiveFrom etc shall be specified in an event.");
		}

		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;

		this.issueTo = issueTo;
		this.receiveFrom = receiveFrom;
	}
}
