package de.metas.ui.web.view.event;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

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

public class ViewChangesCollector implements AutoCloseable
{
	public static final ViewChangesCollector newThreadLocalCollector()
	{
		final ViewChangesCollector currentCollector = THREADLOCAL.get();
		if (currentCollector != null && !currentCollector.isClosed())
		{
			throw new IllegalStateException("A collector was already created for current thread");
		}

		final ViewChangesCollector collector = new ViewChangesCollector();
		THREADLOCAL.set(collector);
		return collector;
	}

	public static final ViewChangesCollector getCurrentOrNull()
	{
		//
		// Try get/create transaction level collector
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(currentTrx))
		{
			return currentTrx
					.getProperty(TRXPROPERTY_Name, trx -> {
						final ViewChangesCollector collector = new ViewChangesCollector();
						trx.getTrxListenerManager()
								.newEventListener(TrxEventTiming.AFTER_COMMIT)
								.registerHandlingMethod(innerTrx -> collector.close());
						return collector;
					});
		}

		//
		// Try getting thread level collector
		final ViewChangesCollector threadLocalCollector = THREADLOCAL.get();
		if (threadLocalCollector != null)
		{
			return threadLocalCollector;
		}

		//
		// Fallback: return null
		return null;
	}

	public static final ViewChangesCollector getCurrentOrAutoflush()
	{
		final ViewChangesCollector collector = getCurrentOrNull();
		if (collector != null)
		{
			return collector;
		}

		final boolean autoflush = true;
		return new ViewChangesCollector(autoflush);
	}

	private static final transient Logger logger = LogManager.getLogger(ViewChangesCollector.class);

	private static final transient ThreadLocal<ViewChangesCollector> THREADLOCAL = new ThreadLocal<>();
	private static final String TRXPROPERTY_Name = ViewChangesCollector.class.getName();

	@Autowired
	@Lazy
	private WebsocketSender websocketSender;

	private final boolean autoflush;

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final Map<ViewId, ViewChanges> viewChangesMap = new LinkedHashMap<>();

	private ViewChangesCollector()
	{
		this(false); // autoflush=false
	}

	private ViewChangesCollector(final boolean autoflush)
	{
		super();
		Adempiere.autowire(this);

		this.autoflush = autoflush;
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

		flush();
	}

	private boolean isClosed()
	{
		return closed.get();
	}

	private final void assertNotClosed()
	{
		if (closed.get())
		{
			throw new IllegalStateException("Collector " + this + " was already closed");
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

	public void collectFullyChanged(@NonNull final IView view)
	{
		viewChanges(view).setFullyChanged();

		autoflushIfEnabled();
	}

	public void collectRowsChanged(@NonNull final IView view, final DocumentIdsSelection rowIds)
	{
		viewChanges(view).addChangedRowIds(rowIds);

		autoflushIfEnabled();
	}
	
	public void collectRowsChanged(@NonNull final IView view, final Collection<DocumentId> rowIds)
	{
		viewChanges(view).addChangedRowIds(rowIds);

		autoflushIfEnabled();
	}

	public void collectRowChanged(@NonNull final IView view, final DocumentId rowId)
	{
		viewChanges(view).addChangedRowId(rowId);

		autoflushIfEnabled();
	}

	private void collectFromChanges(final ViewChanges changes)
	{
		viewChanges(changes.getViewId()).collectFrom(changes);

		autoflushIfEnabled();
	}

	private ViewChangesCollector getParentOrNull()
	{
		final ViewChangesCollector threadLocalCollector = getCurrentOrNull();
		if (threadLocalCollector != null && threadLocalCollector != this)
		{
			return threadLocalCollector;
		}

		return null;
	}

	private void flush()
	{
		final List<ViewChanges> changesList = getAndClean();
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
			changesList
					.forEach(parentCollector::collectFromChanges);
		}
		//
		// Fallback: flush to websocket
		else
		{
			logger.trace("Flushing {} to websocket", this);
			changesList.stream()
					.filter(ViewChanges::hasChanges)
					.map(JSONViewChanges::of)
					.forEach(this::sendToWebsocket);
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

	private List<ViewChanges> getAndClean()
	{
		if (viewChangesMap.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<ViewChanges> changesList = ImmutableList.copyOf(viewChangesMap.values());
		viewChangesMap.clear();
		return changesList;
	}

	private void sendToWebsocket(final JSONViewChanges jsonChangeEvent)
	{
		final String endpoint = WebSocketConfig.buildViewNotificationsTopicName(jsonChangeEvent.getViewId());
		try
		{
			websocketSender.convertAndSend(endpoint, jsonChangeEvent);
			logger.debug("Send to websocket {}: {}", endpoint, jsonChangeEvent);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed sending to websocket {}: {}", endpoint, jsonChangeEvent, ex);
		}
	}
}
