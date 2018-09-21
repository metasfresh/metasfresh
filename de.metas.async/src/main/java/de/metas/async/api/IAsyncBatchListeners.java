/**
 * 
 */
package de.metas.async.api;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.letters.spi.INotifyAsyncBatch;
import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IAsyncBatchListeners extends ISingletonService
{

	/**
	 * register listeners for creating notice for async batch
	 * 
	 * @param l
	 */
	void registerAsyncBatchNoticeListener(IAsyncBatchListener l, final String asyncBatchType);

	/**
	 * create notice
	 */
	void applyListener(I_C_Async_Batch asyncBatch);

	/**
	 * register notifiers
	 * @param notifyAsyncBatch
	 */
	void registerAsyncBatchNotifier(INotifyAsyncBatch notifyAsyncBatch);
	
	/**
	 * notify the fact that async batch was processed or one of his WPs was processed
	 * @param asyncBatch
	 */
	void notify(I_C_Async_Batch asyncBatch);
}
