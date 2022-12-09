package de.metas.distribution.rest_api;

import de.metas.common.util.CoalesceUtil;
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
	@NonNull String distributionStepId;

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
			@NonNull final String distributionStepId,
			//
			@Nullable final PickFrom pickFrom,
			@Nullable final DropTo dropTo)
	{
		if (CoalesceUtil.countNotNulls(pickFrom, dropTo) != 1)
		{
			throw new AdempiereException("One and only one action like pickFrom, dropTo etc shall be specified in an event.");
		}

		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;
		this.distributionStepId = distributionStepId;
		//
		this.pickFrom = pickFrom;
		this.dropTo = dropTo;
	}
}
