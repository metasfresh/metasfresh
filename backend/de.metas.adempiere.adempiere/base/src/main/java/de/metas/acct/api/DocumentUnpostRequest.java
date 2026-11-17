package de.metas.acct.api;

import de.metas.document.DocBaseType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

/**
 * Request for {@link IPostingService#unpost(DocumentUnpostRequest)}.
 */
@Value
@Builder
public class DocumentUnpostRequest
{
	/**
	 * The document model to un-post (e.g. a {@code I_GL_Journal}, {@code I_C_Invoice} or a legacy {@code PO}); {@code Posted} is reset on this very instance.
	 */
	@NonNull Object document;

	/**
	 * Together with {@link #docBaseType}, used to check that the accounting period is open.
	 */
	@NonNull Instant dateAcct;

	@NonNull DocBaseType docBaseType;
}
