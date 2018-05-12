package de.metas.ui.web.window.model.lookup;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.adempiere.ad.dao.cache.CacheInvalidateMultiRequest;
import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.compiere.util.ICacheResetListener;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * This component listens to all cache invalidation events (see {@link CacheMgt}) and invalidates the lookup descriptors which depend on a given table(s).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class LookupCacheInvalidationDispatcher implements ICacheResetListener
{
	private static final String TRXPROP_TableNamesToInvalidate = LookupCacheInvalidationDispatcher.class + ".TableNamesToInvalidate";

	private final Executor async;

	public LookupCacheInvalidationDispatcher()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(LookupCacheInvalidationDispatcher.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);

		async = Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@PostConstruct
	private void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this);
	}

	@Override
	public int reset(final CacheInvalidateMultiRequest multiRequest)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(currentTrx))
		{
			async.execute(() -> resetNow(extractTableNames(multiRequest)));
		}
		else
		{
			final TableNamesToResetCollector collector = currentTrx.getProperty(TRXPROP_TableNamesToInvalidate, trx -> {
				final TableNamesToResetCollector c = new TableNamesToResetCollector();
				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.AFTER_COMMIT)
						.registerHandlingMethod(innerTrx -> {
							final Set<String> tableNames = c.toSet();
							if (tableNames.isEmpty())
							{
								return;
							}
							async.execute(() -> resetNow(tableNames));
						});
				return c;
			});

			collector.addTableNames(extractTableNames(multiRequest));
		}

		return 1; // not relevant
	}

	private Set<String> extractTableNames(final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			// not relevant for our lookups
			return ImmutableSet.of();
		}

		return multiRequest.getRequests()
				.stream()
				.filter(request -> !request.isAll()) // not relevant for our lookups
				.map(CacheInvalidateRequest::getTableNameEffective)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	private void resetNow(final Set<String> tableNames)
	{
		if (tableNames.isEmpty())
		{
			return;
		}

		LookupDataSourceFactory.instance.cacheInvalidateOnRecordsChanged(tableNames);
	}

	private static final class TableNamesToResetCollector
	{
		private final Set<String> tableNames = new HashSet<>();

		public Set<String> toSet()
		{
			return ImmutableSet.copyOf(tableNames);
		}

		public void addTableNames(final Collection<String> tableNamesToAdd)
		{
			tableNames.addAll(tableNamesToAdd);
		}
	}
}
