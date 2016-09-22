/**
 * 
 */
package de.metas.async.api;

import org.adempiere.util.ISingletonService;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IAsyncBatchListener;

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
}
