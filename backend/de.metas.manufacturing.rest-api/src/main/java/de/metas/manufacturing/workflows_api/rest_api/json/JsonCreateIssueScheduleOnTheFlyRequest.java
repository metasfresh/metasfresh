package de.metas.manufacturing.workflows_api.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
public class JsonCreateIssueScheduleOnTheFlyRequest
{
	@NonNull String wfProcessId;
	@NonNull String huQRCode;

	@Builder
	@Jacksonized
	private JsonCreateIssueScheduleOnTheFlyRequest(
			@NonNull final String wfProcessId,
			@NonNull final String huQRCode)
	{
		this.wfProcessId = wfProcessId;
		this.huQRCode = huQRCode;
	}
}
