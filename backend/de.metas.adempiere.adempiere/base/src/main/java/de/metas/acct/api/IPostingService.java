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
}
