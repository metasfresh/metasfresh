package de.metas.async.api;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.util.ISingletonService;

public interface IWorkpackageProcessorContextFactory extends ISingletonService
{
	/**
	 * Associate the given <code>asyncBatch</code> with the current thread
	 * 
	 * @param asyncBatch
	 * @return the async batch that was formerly associate with the current thread, or <code>null</code>.
	 */
	I_C_Async_Batch setThreadInheritedAsyncBatch(I_C_Async_Batch asyncBatch);

	/**
	 * Get batch id from the inherited thread
	 * 
	 * @return
	 */
	int getThreadInheritedAsyncBatchId();

	/**
	 * Get the async batch associated with the current thread (or <code>null</code>).
	 * 
	 * @return
	 */
	I_C_Async_Batch getThreadInheritedAsyncBatch();

	/**
	 * Get the priority associated with the current thread
	 * 
	 * @return
	 */
	String getThreadInheritedPriority();

	/**
	 * Associate the given <code>priority</code> with the current thread. Method is called before a workpackage is processed. If the WP-processor itself creawtes a follow-up workpackage, then the
	 * framework will assign this priority to the new workpackage, so that the follow-up package inherit the original package's priority.
	 * 
	 * @param priority
	 */
	void setThreadInheritedPriority(String priority);

}
