package de.metas.ui.web.view.event;

import de.metas.ui.web.view.ViewId;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewChangesCollectorAutoCloseable implements IAutoCloseable
{
	@NonNull private final ThreadLocal<ViewChangesCollector> threadLocal;

	@NonNull @Getter private final ViewChangesCollector collector;
	private final boolean isNewCollector;

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private final boolean isContextViewIdChanged;
	private final ViewId previousContextViewId;

	ViewChangesCollectorAutoCloseable(
			@NonNull final ThreadLocal<ViewChangesCollector> threadLocal,
			@Nullable final ViewId contextViewId)
	{
		this.threadLocal = threadLocal;

		final ViewChangesCollector currentCollector = threadLocal.get();
		if (currentCollector == null)
		{
			this.collector = new ViewChangesCollector(false);
			this.isNewCollector = true;
			threadLocal.set(this.collector);
		}
		else
		{
			this.collector = currentCollector;
			this.isNewCollector = false;
		}

		if (contextViewId != null)
		{
			this.isContextViewIdChanged = true;
			this.previousContextViewId = this.collector.getContextViewId();
			this.collector.setContextViewId(contextViewId);
		}
		else
		{
			this.isContextViewIdChanged = false;
			this.previousContextViewId = null;
		}
	}

	@Override
	public void close()
	{
		if (closed.getAndSet(true))
		{
			return;
		}

		if (isNewCollector)
		{
			collector.close();
			threadLocal.remove();
		}
		else
		{
			collector.flush();

			if (isContextViewIdChanged)
			{
				collector.setContextViewId(previousContextViewId);
			}
		}
	}
}
