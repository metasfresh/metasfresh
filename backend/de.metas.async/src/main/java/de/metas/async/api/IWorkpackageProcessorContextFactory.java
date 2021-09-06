package de.metas.async.api;

import de.metas.async.AsyncBatchId;
import de.metas.util.ISingletonService;

public interface IWorkpackageProcessorContextFactory extends ISingletonService
{
	/**
	 * Associate the given {@code asyncBatchId} (or {@code null} with the current thread.
	 *
	 * @return the async batch Id that was formerly associate with the current thread, or <code>null</code>.
	 */
	AsyncBatchId setThreadInheritedAsyncBatch(AsyncBatchId asyncBatch);

	/**
	 * Get batch id from the inherited thread or {@code null}
	 */
	AsyncBatchId getThreadInheritedAsyncBatchId();


	/**
	 * Get the priority associated with the current thread
	 */
	String getThreadInheritedPriority();

	/**
	 * Associate the given <code>priority</code> with the current thread. Method is called before a workpackage is processed. If the WP-processor itself creawtes a follow-up workpackage, then the
	 * framework will assign this priority to the new workpackage, so that the follow-up package inherit the original package's priority.
	 */
	void setThreadInheritedPriority(String priority);

}
