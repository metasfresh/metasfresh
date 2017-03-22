package de.metas.async.spi;

import de.metas.async.model.I_C_Async_Batch;

public interface IAsyncBatchListener
{
	void createNotice(I_C_Async_Batch asyncBatch);
}
