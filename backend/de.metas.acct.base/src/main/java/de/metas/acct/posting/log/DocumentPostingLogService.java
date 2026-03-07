package de.metas.acct.posting.log;

import de.metas.acct.api.DocumentPostRequest;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentPostingLogService
{
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final DocumentPostingLogRepository repository;

	public void logEnqueued(@NonNull final Collection<DocumentPostRequest> postRequests)
	{
		repository.create(
				postRequests.stream()
						.map(postRequest -> DocumentPostingLogRequest.builderFrom(postRequest)
								.type(DocumentPostingLogRequestType.Enqueued)
								.build())
						.collect(Collectors.toList())
		);
	}

	public void logPostingOK(@NonNull final DocumentPostRequest postRequest)
	{
		repository.create(
				DocumentPostingLogRequest.builderFrom(postRequest)
						.type(DocumentPostingLogRequestType.PostingOK)
						.build()
		);
	}

	public void logPostingError(@NonNull final DocumentPostRequest postRequest, @NonNull final AdempiereException metasfreshException)
	{
		final AdIssueId adIssueId = errorManager.createIssue(metasfreshException);

		repository.create(
				DocumentPostingLogRequest.builderFrom(postRequest)
						.type(DocumentPostingLogRequestType.PostingError)
						.adIssueId(adIssueId)
						.build()
		);
	}
}
