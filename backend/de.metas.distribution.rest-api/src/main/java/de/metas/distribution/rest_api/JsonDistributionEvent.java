package de.metas.distribution.rest_api;

import de.metas.common.util.CoalesceUtil;
import de.metas.distribution.workflows_api.DistributionJobLineId;
import de.metas.distribution.workflows_api.DistributionJobStepId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class JsonDistributionEvent
{
	//
	// Step Identifier
	@NonNull String wfProcessId;
	@NonNull String wfActivityId;
	@Nullable DistributionJobLineId lineId;
	@Nullable DistributionJobStepId distributionStepId;

	@Value
	@Builder
	@Jacksonized
	public static class PickFrom
	{
		@Nullable String qrCode;
	}

	@Nullable PickFrom pickFrom;

	@Value
	@Builder
	@Jacksonized
	public static class DropTo {}

	@Nullable DropTo dropTo;

	@Builder
	@Jacksonized
	private JsonDistributionEvent(
			@NonNull final String wfProcessId,
			@NonNull final String wfActivityId,
			@Nullable final DistributionJobLineId lineId,
			@Nullable final DistributionJobStepId distributionStepId,
			//
			@Nullable final PickFrom pickFrom,
			@Nullable final DropTo dropTo)
	{
		if (CoalesceUtil.countNotNulls(pickFrom, dropTo) != 1)
		{
			throw new AdempiereException("One and only one action like pickFrom, dropTo etc shall be specified in an event.");
		}
		if (lineId == null && distributionStepId == null)
		{
			throw new AdempiereException("At least lineId or distributionStepId must pe set");
		}

		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;
		this.lineId = lineId;
		this.distributionStepId = distributionStepId;
		//
		this.pickFrom = pickFrom;
		this.dropTo = dropTo;
	}
}
