/**
 *
 */
package de.metas.async.api.impl;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.letters.spi.INotifyAsyncBatch;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cg
 *
 */
public class AsyncBatchListeners implements IAsyncBatchListeners
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final Map<String, IAsyncBatchListener> listenersList = new HashMap<>();

	@Override
	public void registerAsyncBatchNoticeListener(final IAsyncBatchListener l, final String asyncBatchType)
	{
		if (listenersList.containsKey(asyncBatchType))
		{

			throw new IllegalStateException(l + " has already been added");
		}
		else
		{
			listenersList.put(asyncBatchType, l);
		}

	}

	@Override
	public void applyListener(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final String internalName = asyncBatchBL.getAsyncBatchTypeInternalName(asyncBatch).orElse(null);
		if(internalName != null)
		{
			final IAsyncBatchListener listener = getListener(internalName);
			listener.createNotice(asyncBatch);
		}
	}

	private IAsyncBatchListener getListener(final String ascyncBatchType)
	{
		IAsyncBatchListener l = listenersList.get(ascyncBatchType);

		// retrieve default implementation
		if (l == null)
		{
			l = listenersList.get(AsyncBatchDAO.ASYNC_BATCH_TYPE_DEFAULT);
		}

		return l;
	}
	
	private final List<INotifyAsyncBatch> asycnBatchNotifiers = new ArrayList<>();
	
	@Override
	public void registerAsyncBatchNotifier(INotifyAsyncBatch notifyAsyncBatch)
	{
		Check.assume(!asycnBatchNotifiers.contains(notifyAsyncBatch), "Every notifier is added only once");
		asycnBatchNotifiers.add(notifyAsyncBatch);
	}

	@Override
	public void notify(I_C_Async_Batch asyncBatch)
	{
		for (INotifyAsyncBatch notifier : asycnBatchNotifiers)
		{
			notifier.sendNotifications(asyncBatch);
		}
		
	}
}
