package de.metas.inbound.mail.config;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.inbound.mail.model.I_C_InboundMailConfig;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class InboundEMailConfigChangesDispatcher implements ICacheResetListener
{
	private static final Logger logger = LogManager.getLogger(InboundEMailConfigChangesDispatcher.class);

	private final ImmutableList<InboundEMailConfigChangedListener> listeners;

	public InboundEMailConfigChangesDispatcher(
			@NonNull final Optional<List<InboundEMailConfigChangedListener>> listeners)
	{
		CacheMgt.get().addCacheResetListener(I_C_InboundMailConfig.Table_Name, this);

		this.listeners = ImmutableList.copyOf(listeners.orElseGet(ImmutableList::of));
		logger.info("Initialized with listeners: {}", listeners);
	}

	@Override
	public long reset(final CacheInvalidateMultiRequest multiRequest)
	{
		if (listeners.isEmpty())
		{
			return 0;
		}

		final Set<InboundEMailConfigId> changedConfigIds = extractConfigIds(multiRequest);
		if (changedConfigIds.isEmpty())
		{
			return 0;
		}

		listeners.forEach(listener -> fireListener(listener, changedConfigIds));
		return 1;
	}

	private void fireListener(final InboundEMailConfigChangedListener listener, final Set<InboundEMailConfigId> changedConfigIds)
	{
		try
		{
			listener.onInboundEMailConfigChanged(changedConfigIds);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed while invoking {} with {}. Skipped.", listener, changedConfigIds);
		}
	}

	private static Set<InboundEMailConfigId> extractConfigIds(final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			return ImmutableSet.of();
		}

		return multiRequest.getRecordsEffective()
				.streamByTableName(I_C_InboundMailConfig.Table_Name)
				.map(recordRef -> InboundEMailConfigId.ofRepoId(recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

}
