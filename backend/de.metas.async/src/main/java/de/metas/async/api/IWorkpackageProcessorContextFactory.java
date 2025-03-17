package de.metas.async.api;

import de.metas.async.AsyncBatchId;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

public interface IWorkpackageProcessorContextFactory extends ISingletonService
{
	/**
	 * Associate the given {@code asyncBatchId} (or {@code null} with the current thread.
	 * Called to aworkpackage's AsyncBatchId while the WP is processed. 
	 * So, if the workpackage-processor enqueues another workpackage, it will automatically inherit the parent-WP's AsyncBatchId.
	 *
	 * @return the async batch Id that was formerly associate with the current thread, or <code>null</code>.
	 */
	AsyncBatchId setThreadInheritedAsyncBatch(@Nullable AsyncBatchId asyncBatch);

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
	void setThreadInheritedPriority(@Nullable String priority);

	/**
	 * Associate the given {@code asyncBatchId} (or {@code null} with the current thread as the current workpackge async batch Id.
	 *
	 * It should only be called from {@code WorkpackageProcessorTask#beforeWorkpackageProcessing()} && {@code WorkpackageProcessorTask#afterWorkpackageProcessed(boolean)} ()}
	 *
	 * @return the async batch Id that was formerly associated with the current thread, or {@code null}.
	 */
	AsyncBatchId setThreadInheritedWorkpackageAsyncBatch(@Nullable AsyncBatchId asyncBatch);

	/**
	 * Get batch id from the inherited thread or {@code null}
	 */
	AsyncBatchId getThreadInheritedWorkpackageAsyncBatch();
}
