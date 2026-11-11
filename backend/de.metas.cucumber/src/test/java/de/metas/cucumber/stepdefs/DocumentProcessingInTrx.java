package de.metas.cucumber.stepdefs;

import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;

/**
 * Runs a document action like the WebUI does.
 */
@UtilityClass
public class DocumentProcessingInTrx
{
	/**
	 * Runs the doc-action <b>and</b> the subsequent save of the document header in ONE transaction, like the WebUI does
	 * (it runs each document action within a transaction).
	 * <p>
	 * Without an enclosing transaction, {@link IDocumentBL#processEx(Object, String, String)} lets the document engine commit
	 * its own transaction <i>before</i> it saves the header with the new {@code DocStatus}. An async consumer running in between
	 * sees the doc-action's side effects together with the old {@code DocStatus}. E.g. on reactivate, the invoice candidate recompute
	 * consumes the {@code C_Invoice_Candidate_Recompute} markers while still reading the document as completed,
	 * and the candidate keeps its pre-reactivation values.
	 * <p>
	 * Inside the enclosing transaction the engine does not own the transaction, so its deadlock retry does not apply (neither does it in the WebUI).
	 * A step that relies on that retry (e.g. a simulated deadlock on completion) must call {@code processEx} directly.
	 */
	public void processEx(@NonNull final Object document, @NonNull final String docAction, @Nullable final String expectedDocStatus)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> documentBL.processEx(document, docAction, expectedDocStatus));
	}
}
