package de.metas.ui.web.view.event;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import de.metas.websocket.sender.WebsocketSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.SpringContextHolder;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Collects view changes and sends them to frontend via WebSocket or to a parent collector.
 */
public class ViewChangesCollector implements IAutoCloseable
{
	@Nullable
	private static ViewChangesCollector getCurrentOrNull()
	{
		//
		// Try getting thread level collector
		ViewChangesCollector threadLocalCollector = THREADLOCAL.get();
		if (threadLocalCollector != null)
		{
			if (threadLocalCollector.isClosed())
			{
				// shall not happen
				THREADLOCAL.remove();
				//noinspection UnusedAssignment
				threadLocalCollector = null;
			}
			else
			{
				return threadLocalCollector;
			}
		}

		//
		// Try get/create transaction level collector
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(currentTrx))
		{
			return currentTrx.getPropertyAndProcessAfterCommit(
					ViewChangesCollector.class.getName(),
					ViewChangesCollector::new,
					ViewChangesCollector::close);
		}

		//
		// Fallback: return null
		return null;
	}

	public static ViewChangesCollector getCurrentOrAutoflush()
	{
		final ViewChangesCollector collector = getCurrentOrNull();
		if (collector != null)
		{
			return collector;
		}

		final boolean autoflush = true;
		return new ViewChangesCollector(autoflush);
	}

	public static ViewChangesCollectorAutoCloseable currentOrNewThreadLocalCollector()
	{
		return new ViewChangesCollectorAutoCloseable(THREADLOCAL, null);
	}

	public static ViewChangesCollectorAutoCloseable currentOrNewThreadLocalCollector(@NonNull ViewId contextViewId)
	{
		return new ViewChangesCollectorAutoCloseable(THREADLOCAL, contextViewId);
	}

	private static final Logger logger = LogManager.getLogger(ViewChangesCollector.class);

	private static final ThreadLocal<ViewChangesCollector> THREADLOCAL = new ThreadLocal<>();

	private transient WebsocketSender _websocketSender; // lazy

	private final boolean autoflush;

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final LinkedHashMap<ViewId, ViewChanges> viewChangesMap = new LinkedHashMap<>();

	@Nullable @Getter(AccessLevel.PACKAGE) @Setter(AccessLevel.PACKAGE) private ViewId contextViewId = null;

	private static final boolean DEBUG = false;
	@Nullable private final String createdStackTrace;
	@Nullable private String closedStackTrace;

	private ViewChangesCollector()
	{
		this(false); // autoflush=false
	}

	ViewChangesCollector(final boolean autoflush)
	{
		this.autoflush = autoflush;
		this.createdStackTrace = DEBUG ? Trace.toOneLineStackTraceString() : null;
	}

	@Override
	public void close()
	{
		// Mark as closed. Stop here if already marked as closed.
		final boolean alreadyClosed = closed.getAndSet(true);
		if (alreadyClosed)
		{
			return;
		}

		closedStackTrace = DEBUG ? Trace.toOneLineStackTraceString() : null;

		flush();
	}

	private boolean isClosed()
	{
		return closed.get();
	}

	private void assertNotClosed()
	{
		if (closed.get())
		{
			throw new IllegalStateException("Collector " + this + " was already closed"
					+ "\n\nCreated stacktrace: " + CoalesceUtil.coalesce(createdStackTrace, "?")
					+ "\n\nClosed stacktrace: " + CoalesceUtil.coalesce(closedStackTrace, "?"));
		}
	}

	private ViewChanges viewChanges(@NonNull final IView view)
	{
		return viewChanges(view.getViewId());
	}

	private ViewChanges viewChanges(final ViewId viewId)
	{
		assertNotClosed();
		return viewChangesMap.computeIfAbsent(viewId, ViewChanges::new);
	}

	public void collectFullyChanged()
	{
		if (contextViewId != null)
		{
			collectFullyChanged(contextViewId);
		}
	}

	public void collectFullyChanged(@NonNull final IView view)
	{
		collectFullyChanged(view.getViewId());
	}

	public void collectFullyChanged(@NonNull final ViewId viewId)
	{
		viewChanges(viewId).setFullyChanged();

		autoflushIfEnabled();
	}

	public void collectRowsChanged(@NonNull final IView view, final DocumentIdsSelection rowIds)
	{
		viewChanges(view).addChangedRowIds(rowIds);

		autoflushIfEnabled();
	}

	public void collectRowsAndHeaderPropertiesChanged(@NonNull final IView view, final DocumentIdsSelection rowIds)
	{
		final ViewChanges viewChanges = viewChanges(view);
		viewChanges.addChangedRowIds(rowIds);
		viewChanges.setHeaderPropertiesChanged();

		autoflushIfEnabled();
	}

	public void collectRowsChanged(@NonNull final IView view, @NonNull final Collection<DocumentId> rowIds)
	{
		if (rowIds.isEmpty())
		{
			return;
		}

		viewChanges(view).addChangedRowIds(rowIds);

		autoflushIfEnabled();
	}

	public void collectRowChanged(@NonNull final IView view, final DocumentId rowId)
	{
		collectRowChanged(view.getViewId(), rowId);
	}

	public void collectRowChanged(@NonNull final ViewId viewId, final DocumentId rowId)
	{
		viewChanges(viewId).addChangedRowId(rowId);

		autoflushIfEnabled();
	}

	public void collectHeaderPropertiesChanged(@NonNull final IView view)
	{
		viewChanges(view).setHeaderPropertiesChanged();

		autoflushIfEnabled();
	}

	public void collectHeaderPropertiesChanged(@NonNull final ViewId viewId)
	{
		viewChanges(viewId).setHeaderPropertiesChanged();

		autoflushIfEnabled();
	}

	private void collectFromChanges(@NonNull final ViewChanges changes)
	{
		viewChanges(changes.getViewId()).collectFrom(changes);

		autoflushIfEnabled();
	}

	@Nullable
	private ViewChangesCollector getParentOrNull()
	{
		final ViewChangesCollector threadLocalCollector = getCurrentOrNull();
		if (threadLocalCollector != null && threadLocalCollector != this)
		{
			return threadLocalCollector;
		}

		return null;
	}

	void flush()
	{
		final ImmutableList<ViewChanges> changesList = getAndClean();
		if (changesList.isEmpty())
		{
			return;
		}

		//
		// Try flushing to parent collector if any
		final ViewChangesCollector parentCollector = getParentOrNull();
		if (parentCollector != null)
		{
			logger.trace("Flushing {} to parent collector: {}", this, parentCollector);
			changesList.forEach(parentCollector::collectFromChanges);
		}
		//
		// Fallback: flush to websocket
		else
		{
			logger.trace("Flushing {} to websocket", this);
			final ImmutableList<JSONViewChanges> jsonChangeEvents = changesList.stream()
					.filter(ViewChanges::hasChanges)
					.map(JSONViewChanges::of)
					.collect(ImmutableList.toImmutableList());

			sendToWebsocket(jsonChangeEvents);
		}
	}

	private void autoflushIfEnabled()
	{
		if (!autoflush)
		{
			return;
		}

		flush();
	}

	private ImmutableList<ViewChanges> getAndClean()
	{
		if (viewChangesMap.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<ViewChanges> changesList = ImmutableList.copyOf(viewChangesMap.values());
		viewChangesMap.clear();
		return changesList;
	}

	private void sendToWebsocket(@NonNull final List<JSONViewChanges> jsonChangeEvents)
	{
		if (jsonChangeEvents.isEmpty())
		{
			return;
		}

		WebsocketSender websocketSender = _websocketSender;
		if (websocketSender == null)
		{
			websocketSender = this._websocketSender = SpringContextHolder.instance.getBean(WebsocketSender.class);
		}

		try
		{
			websocketSender.convertAndSend(jsonChangeEvents);
			logger.debug("Sent to websocket: {}", jsonChangeEvents);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed sending to websocket {}: {}", jsonChangeEvents, ex);
		}
	}
}
