package de.metas.manufacturing.workflows_api.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class JsonCreateIssueScheduleOnTheFlyRequest
{
	@NonNull String wfProcessId;
	@Nullable String wfActivityId;
	@NonNull String huQRCode;

	@Builder
	@Jacksonized
	private JsonCreateIssueScheduleOnTheFlyRequest(
			@NonNull final String wfProcessId,
			@Nullable final String wfActivityId,
			@NonNull final String huQRCode)
	{
		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;
		this.huQRCode = huQRCode;
	}
}
