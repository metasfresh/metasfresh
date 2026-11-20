package de.metas.payment.sumup;

import de.metas.error.AdIssueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import javax.annotation.Nullable;

@Value
@Builder
public class SumUpLogRequest
{
	@NonNull SumUpConfigId configId;
	@NonNull SumUpMerchantCode merchantCode;

	@NonNull HttpMethod method;
	@NonNull String requestURI;
	@Nullable Object requestBody;

	@Nullable HttpStatusCode responseCode;
	@Nullable Object responseBody;
	@Nullable AdIssueId adIssueId;
	
	@Nullable SumUpPOSRef posRef;
}
