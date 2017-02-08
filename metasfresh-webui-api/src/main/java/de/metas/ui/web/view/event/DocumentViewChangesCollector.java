package de.metas.ui.web.view.event;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.websocket.WebSocketConfig;
import groovy.transform.Immutable;

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

public class DocumentViewChangesCollector implements AutoCloseable
{
	public static final DocumentViewChangesCollector newThreadLocalCollector()
	{
		final DocumentViewChangesCollector currentCollector = THREADLOCAL.get();
		if (currentCollector != null && !currentCollector.isClosed())
		{
			throw new IllegalStateException("A collector was already created for current thread");
		}

		final DocumentViewChangesCollector collector = new DocumentViewChangesCollector();
		THREADLOCAL.set(collector);
		return collector;
	}

	public static final DocumentViewChangesCollector getCurrentOrNull()
	{
		//
		// Try get/create transaction level collector
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(currentTrx))
		{
			return currentTrx
					.getProperty(TRXPROPERTY_Name, trx -> {
						final DocumentViewChangesCollector collector = new DocumentViewChangesCollector();
						trx.getTrxListenerManager().onAfterCommit(() -> collector.close());
						return collector;
					});
		}

		//
		// Try getting thread level collector
		final DocumentViewChangesCollector threadLocalCollector = THREADLOCAL.get();
		if (threadLocalCollector != null)
		{
			return threadLocalCollector;
		}

		//
		// Fallback: return null
		return null;
	}

	public static final DocumentViewChangesCollector getCurrentOrAutoflush()
	{
		final DocumentViewChangesCollector collector = getCurrentOrNull();
		if (collector != null)
		{
			return collector;
		}

		final boolean autoflush = true;
		return new DocumentViewChangesCollector(autoflush);
	}

	private static final transient Logger logger = LogManager.getLogger(DocumentViewChangesCollector.class);

	private static final transient ThreadLocal<DocumentViewChangesCollector> THREADLOCAL = new ThreadLocal<>();
	private static final String TRXPROPERTY_Name = DocumentViewChangesCollector.class.getName();

	@Autowired
	@Lazy
	private SimpMessagingTemplate websocketMessagingTemplate;
	
	private final boolean autoflush;

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final Map<DocumentViewChangesKey, DocumentViewChanges> viewChangesMap = new LinkedHashMap<>();


	private DocumentViewChangesCollector()
	{
		this(false); // autoflush=false
	}

	private DocumentViewChangesCollector(final boolean autoflush)
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

	private DocumentViewChanges viewChanges(final IDocumentViewSelection view)
	{
		return viewChanges(new DocumentViewChangesKey(view.getViewId(), view.getAD_Window_ID()));
	}

	private DocumentViewChanges viewChanges(final DocumentViewChangesKey key)
	{
		assertNotClosed();
		return viewChangesMap.computeIfAbsent(key, k -> new DocumentViewChanges(key.getViewId(), key.getAD_Window_ID()));
	}

	public void collectFullyChanged(final IDocumentViewSelection view)
	{
		viewChanges(view).setFullyChanged();
		
		autoflushIfEnabled();
	}

	private void collectFromChanges(final DocumentViewChanges changes)
	{
		final DocumentViewChangesKey key = new DocumentViewChangesKey(changes.getViewId(), changes.getAD_Window_ID());
		viewChanges(key).collectFrom(changes);
		
		autoflushIfEnabled();
	}

	private DocumentViewChangesCollector getParentOrNull()
	{
		final DocumentViewChangesCollector threadLocalCollector = getCurrentOrNull();
		if (threadLocalCollector != null && threadLocalCollector != this)
		{
			return threadLocalCollector;
		}

		return null;
	}

	private void flush()
	{
		final List<DocumentViewChanges> changesList = getAndClean();
		if(changesList.isEmpty())
		{
			return;
		}
		
		//
		// Try flushing to parent collector if any
		final DocumentViewChangesCollector parentCollector = getParentOrNull();
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
					.map(changes -> JSONDocumentViewChanges.of(changes))
					.forEach(this::sendToWebsocket);
		}
	}
	
	private void autoflushIfEnabled()
	{
		if(!autoflush)
		{
			return;
		}
		
		flush();
	}

	private List<DocumentViewChanges> getAndClean()
	{
		if(viewChangesMap.isEmpty())
		{
			return ImmutableList.of();
		}
		
		final List<DocumentViewChanges> changesList = ImmutableList.copyOf(viewChangesMap.values());
		viewChangesMap.clear();
		return changesList;
	}

	private void sendToWebsocket(final JSONDocumentViewChanges jsonChangeEvent)
	{
		final String endpoint = WebSocketConfig.buildDocumentViewNotificationsTopicName(jsonChangeEvent.getViewId());
		try
		{
			websocketMessagingTemplate.convertAndSend(endpoint, jsonChangeEvent);
			logger.debug("Send to websocket {}: {}", endpoint, jsonChangeEvent);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed sending to websocket {}: {}", endpoint, jsonChangeEvent, ex);
		}
	}

	@Immutable
	private static final class DocumentViewChangesKey
	{
		private final String viewId;
		private final int adWindowId;

		private transient Integer _hashcode;

		private DocumentViewChangesKey(final String viewId, final int adWindowId)
		{
			super();
			this.viewId = viewId;
			this.adWindowId = adWindowId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("viewId", viewId)
					.add("AD_Window_ID", adWindowId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			if (_hashcode == null)
			{
				_hashcode = Objects.hash(viewId, adWindowId);
			}
			return _hashcode;
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			if (obj instanceof DocumentViewChangesKey)
			{
				final DocumentViewChangesKey other = (DocumentViewChangesKey)obj;
				return Objects.equals(viewId, other.viewId)
						&& adWindowId == other.adWindowId;
			}
			else
			{
				return false;
			}
		}

		public String getViewId()
		{
			return viewId;
		}

		public int getAD_Window_ID()
		{
			return adWindowId;
		}
	}
}
