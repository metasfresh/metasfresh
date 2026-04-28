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
}
