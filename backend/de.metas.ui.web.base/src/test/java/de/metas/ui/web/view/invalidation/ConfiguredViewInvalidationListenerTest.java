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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfiguredViewInvalidationListenerTest
{
	private static final String TRIGGER_TABLE = "M_ReceiptSchedule";
	private static final String UNRELATED_TABLE = "C_Order";

	private final WindowId windowCockpit = WindowId.of(1000001);
	private final WindowId windowOther = WindowId.of(1000002);

	private IViewsRepository viewsRepository;
	private WebuiViewInvalidateOnChangeRepository configRepository;
	private ConfiguredViewInvalidationListener listener;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// The debouncer built in the constructor reads two sysconfig ints; return their defaults.
		final ISysConfigBL sysConfigBL = mock(ISysConfigBL.class);
		when(sysConfigBL.getIntValue(anyString(), anyInt())).thenAnswer(inv -> inv.getArgument(1));
		de.metas.util.Services.registerService(ISysConfigBL.class, sysConfigBL);

		viewsRepository = mock(IViewsRepository.class);
		configRepository = mock(WebuiViewInvalidateOnChangeRepository.class);

		listener = new ConfiguredViewInvalidationListener(viewsRepository, configRepository);
	}

	private IView viewForWindow(final WindowId windowId, final boolean watched)
	{
		final IView view = mock(IView.class);
		final ViewId viewId = ViewId.random(windowId);
		when(view.getViewId()).thenReturn(viewId);
		when(viewsRepository.isWatchedByFrontend(viewId)).thenReturn(watched);
		return view;
	}

	@Test
	void flush_invalidatesOnlyWatchedViewsOfConfiguredWindow()
	{
		when(configRepository.getWindowIdsToInvalidateForTable(TRIGGER_TABLE))
				.thenReturn(ImmutableSet.of(windowCockpit));

		final IView watchedCockpitView1 = viewForWindow(windowCockpit, true);
		final IView watchedCockpitView2 = viewForWindow(windowCockpit, true);
		final IView idleCockpitView = viewForWindow(windowCockpit, false);   // right window, not watched
		final IView otherWindowView = viewForWindow(windowOther, true);      // watched, wrong window
		when(viewsRepository.getViews())
				.thenReturn(ImmutableList.of(watchedCockpitView1, watchedCockpitView2, idleCockpitView, otherWindowView));

		listener.invalidateViewsForTableNamesNow(ImmutableList.of(TRIGGER_TABLE));

		verify(viewsRepository).invalidateView(watchedCockpitView1);
		verify(viewsRepository).invalidateView(watchedCockpitView2);
		verify(viewsRepository, never()).invalidateView(idleCockpitView);
		verify(viewsRepository, never()).invalidateView(otherWindowView);
	}

	@Test
	void flush_unrelatedTable_isNoop()
	{
		when(configRepository.getWindowIdsToInvalidateForTable(UNRELATED_TABLE))
				.thenReturn(ImmutableSet.of());

		listener.invalidateViewsForTableNamesNow(ImmutableList.of(UNRELATED_TABLE));

		// no configured window -> never even enumerate the views, never invalidate
		verify(viewsRepository, never()).getViews();
		verify(viewsRepository, never()).invalidateView(any(IView.class));
	}

	@Test
	void reset_whenNoTriggerTablesConfigured_isNoop()
	{
		when(configRepository.getAllTriggerTableNames()).thenReturn(ImmutableSet.of());

		final long affected = listener.reset(CacheInvalidateMultiRequest.rootRecord(TRIGGER_TABLE, 1));

		org.assertj.core.api.Assertions.assertThat(affected).isZero();
		verify(viewsRepository, never()).getViews();
		verify(viewsRepository, never()).invalidateView(any(IView.class));
		verify(viewsRepository, never()).isWatchedByFrontend(any());
	}

	@Test
	void reset_whenRequestTablesDoNotMatchTriggers_isNoop()
	{
		when(configRepository.getAllTriggerTableNames()).thenReturn(ImmutableSet.of(TRIGGER_TABLE));

		final long affected = listener.reset(CacheInvalidateMultiRequest.rootRecord(UNRELATED_TABLE, 1));

		org.assertj.core.api.Assertions.assertThat(affected).isZero();
		verify(viewsRepository, never()).invalidateView(any(IView.class));
		verify(viewsRepository, never()).getViews();
	}
}
