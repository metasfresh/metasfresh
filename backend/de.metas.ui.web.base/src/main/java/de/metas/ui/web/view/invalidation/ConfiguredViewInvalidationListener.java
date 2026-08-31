/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.view.invalidation;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Config-driven, standalone cache-reset listener that full-invalidates all WebUI views of a
 * configured window whenever a record of a configured trigger table is (after-commit) cache-reset.
 * <p>
 * Mechanism (see {@code DocumentCacheInvalidationDispatcher} for the reference after-commit + debounce
 * pattern): {@link #reset(CacheInvalidateMultiRequest)} first does a cheap pre-filter against the
 * configured trigger tables (so it is a no-op on any instance that has no config rows), then defers the
 * work to <b>after the current transaction commits</b> (so the push reflects committed data) and
 * <b>debounces</b> it (so a bulk change coalesces into a single refresh). On flush it resolves the
 * changed trigger table names to window ids via {@link WebuiViewInvalidateOnChangeRepository} and
 * full-invalidates every frontend-watched view of those windows via {@link IViewsRepository}.
 * <p>
 * The mapping table ships empty; this component only does work once a row is configured.
 */
@Component
public class ConfiguredViewInvalidationListener implements ICacheResetListener
{
	private static final Logger logger = LogManager.getLogger(ConfiguredViewInvalidationListener.class);

	private static final String SYSCONFIG_BufferMaxSize = "webui.ConfiguredViewInvalidationListener.debouncer.bufferMaxSize";
	private static final int DEFAULT_BufferMaxSize = 500;
	private static final String SYSCONFIG_DelayInMillis = "webui.ConfiguredViewInvalidationListener.debouncer.delayInMillis";
	private static final int DEFAULT_DelayInMillis = 100;

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IViewsRepository viewsRepository;
	private final WebuiViewInvalidateOnChangeRepository configRepository;
	private final Debouncer<String> debouncer;

	public ConfiguredViewInvalidationListener(
			@NonNull final IViewsRepository viewsRepository,
			@NonNull final WebuiViewInvalidateOnChangeRepository configRepository)
	{
		this.viewsRepository = viewsRepository;
		this.configRepository = configRepository;

		this.debouncer = Debouncer.<String>builder()
				.name(ConfiguredViewInvalidationListener.class.getSimpleName() + "-debouncer")
				.bufferMaxSize(sysConfigBL.getIntValue(SYSCONFIG_BufferMaxSize, DEFAULT_BufferMaxSize))
				.delayInMillis(sysConfigBL.getIntValue(SYSCONFIG_DelayInMillis, DEFAULT_DelayInMillis))
				.distinct(true)
				.consumer(this::invalidateViewsForTableNamesNow)
				.build();
		logger.info("debouncer: {}", debouncer);
	}

	@PostConstruct
	private void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this);
	}

	@Override
	public long reset(@NonNull final CacheInvalidateMultiRequest request)
	{
		//
		// Cheap pre-filter: on a vanilla instance (no config rows) this returns immediately.
		final Set<String> triggerTableNames = configRepository.getAllTriggerTableNames();
		if (triggerTableNames.isEmpty())
		{
			return 0;
		}

		final ImmutableSet<String> changedTriggerTableNames = request.getTableNamesEffective()
				.stream()
				.filter(triggerTableNames::contains)
				.collect(ImmutableSet.toImmutableSet());
		if (changedTriggerTableNames.isEmpty())
		{
			return 0;
		}

		//
		// Defer after commit (committed data) + debounce (coalesce bulk changes).
		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isActive(currentTrx))
		{
			currentTrx.accumulateAndProcessAfterCommit(
					ConfiguredViewInvalidationListener.class.getName(),
					changedTriggerTableNames,
					this::enqueue);
		}
		else
		{
			// no active transaction: the reset already refers to committed data
			enqueue(changedTriggerTableNames);
		}

		return changedTriggerTableNames.size();
	}

	private void enqueue(@NonNull final Collection<String> tableNames)
	{
		debouncer.addAll(ImmutableList.copyOf(tableNames));
	}

	/**
	 * Flush: resolve the changed trigger table names to window ids and full-invalidate every
	 * frontend-watched view of those windows. Package-visible so it can be unit-tested directly
	 * (bypassing the after-commit + debounce deferral).
	 */
	@VisibleForTesting
	void invalidateViewsForTableNamesNow(@NonNull final Collection<String> tableNames)
	{
		final ImmutableSet<WindowId> windowIds = tableNames.stream()
				.flatMap(tableName -> configRepository.getWindowIdsToInvalidateForTable(tableName).stream())
				.collect(ImmutableSet.toImmutableSet());
		if (windowIds.isEmpty())
		{
			return;
		}

		final List<IView> viewsToInvalidate = viewsRepository.getViews()
				.stream()
				.filter(view -> windowIds.contains(view.getViewId().getWindowId()))
				.filter(view -> viewsRepository.isWatchedByFrontend(view.getViewId()))
				.collect(ImmutableList.toImmutableList());

		for (final IView view : viewsToInvalidate)
		{
			viewsRepository.invalidateView(view);
		}
	}
}
