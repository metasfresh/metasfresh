package de.metas.acct.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;

/**
 * This is the main entry point for all document posting(accounting) APIs
 *
 * @author tsa
 */
public interface IPostingService extends ISingletonService
{
	/**
	 * @return true if the accounting module is active; false if the accounting module is COMPLETELLY off
	 */
	boolean isEnabled();

	void schedule(@NonNull DocumentPostRequest request);

	void schedule(@NonNull DocumentPostMultiRequest requests);

	void postAfterCommit(@NonNull DocumentPostMultiRequest requests);

	/**
	 * Posts the document right now, in the caller's thread, under a fresh context carrying only the request's client
	 * (so the caller's role, org and user cannot influence the posting), and logs the success in the posting log.
	 * <p>
	 * Unlike {@link #postAfterCommit(DocumentPostMultiRequest)}, which runs after the commit and only logs/notifies on failure,
	 * any posting error is thrown to the caller (and neither logged nor notified: that is up to the caller).
	 */
	void postNowAndThrow(@NonNull DocumentPostRequest request);

	/**
	 * Un-posts the document: checks that the accounting period is open, deletes the document's accounting records ({@code Fact_Acct}), sets {@code Posted=false}
	 * and, if the document is still completed or closed ({@code DocStatus} CO or CL), schedules it for posting again.
	 * <p>
	 * Same meaning as the database function {@code de_metas_acct.fact_acct_unpost}; use this instead of calling {@code IFactAcctDAO} from business code
	 * whenever the document is meant to stay booked. A document that is not completed or closed (drafted, reversed, voided, in progress) is just un-posted, not queued.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if the period is closed
	 */
	void unpost(@NonNull DocumentUnpostRequest request);
}
