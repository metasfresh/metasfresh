package de.metas.acct.api;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Request for {@link IPostingService#unpost(DocumentUnpostRequest)}.
 * <p>
 * Exactly one of {@link #docTypeId} and {@link #docBaseType} has to be set; it is used (together with {@link #dateAcct}) to check that the accounting period is open.
 */
@Value
public class DocumentUnpostRequest
{
	/**
	 * The document model (e.g. a {@code I_GL_Journal}, {@code I_C_Invoice} or a legacy {@code PO}). It needs the columns {@code Posted}, {@code DocStatus}, {@code AD_Client_ID} and {@code UpdatedBy} (a completed, closed or reversed document without it makes the lookup fail; {@code AD_Org_ID} is read too, with {@code OrgId.ANY} as fallback);
	 * {@code Posted} is set to {@code false} and saved on this very instance.
	 */
	@NonNull Object document;
	@NonNull Instant dateAcct;
	@Nullable DocTypeId docTypeId;
	@Nullable DocBaseType docBaseType;

	@Builder
	private DocumentUnpostRequest(
			@NonNull final Object document,
			@NonNull final Instant dateAcct,
			@Nullable final DocTypeId docTypeId,
			@Nullable final DocBaseType docBaseType)
	{
		if ((docTypeId == null) == (docBaseType == null))
		{
			throw new IllegalArgumentException("Exactly one of docTypeId and docBaseType has to be set but got docTypeId=" + docTypeId + ", docBaseType=" + docBaseType);
		}
		this.document = document;
		this.dateAcct = dateAcct;
		this.docTypeId = docTypeId;
		this.docBaseType = docBaseType;
	}
}
