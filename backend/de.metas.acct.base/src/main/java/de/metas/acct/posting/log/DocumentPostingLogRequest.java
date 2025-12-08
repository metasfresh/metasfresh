package de.metas.acct.posting.log;

import de.metas.acct.api.DocumentPostRequest;
import de.metas.error.AdIssueId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder
public class DocumentPostingLogRequest
{
	@NonNull DocumentPostingLogRequestType type;
	@NonNull TableRecordReference documentRef;
	@NonNull ClientId clientId;
	@Nullable UserId userId;
	@Nullable AdIssueId adIssueId;

	public static DocumentPostingLogRequestBuilder builderFrom(@NonNull final DocumentPostRequest postRequest)
	{
		return DocumentPostingLogRequest.builder()
				.documentRef(postRequest.getRecord())
				.clientId(postRequest.getClientId())
				.userId(postRequest.getOnErrorNotifyUserId());
	}
}
