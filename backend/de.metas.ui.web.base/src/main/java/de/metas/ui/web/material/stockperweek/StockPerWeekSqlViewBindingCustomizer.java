package de.metas.ui.web.material.stockperweek;

import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewBindingCustomizer;
import de.metas.ui.web.window.datatypes.WindowId;
import org.springframework.stereotype.Component;

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

/**
 * Makes the standard AD window 542159 "Bestand pro Woche" (view {@code MD_Stock_PerWeek_V}) open <b>empty</b>:
 * rows are loaded only once the user applies a filter (product / warehouse / week range).
 * <p>
 * The backing view holds ~782k rows; opening the window standalone previously triggered a full scan
 * into {@code T_WEBUI_ViewSelection}. Setting {@code queryIfNoFilters=false} flips on the existing
 * {@code SqlViewRowIdsOrderedSelectionFactory} guard: when no filter is applied the view returns a
 * zero-row selection plus the {@code webui.view.emptyReason.pleaseFilterFirst.*} hint, and never scans.
 * <p>
 * A zoom into this window arrives <i>with</i> a (sticky) filter, so it loads rows normally — only the
 * unfiltered standalone open is affected.
 * <p>
 * Localized to this one {@link WindowId} via {@link SqlViewBindingCustomizer} so neither the global
 * {@code GridTabVO} default nor any AD_Tab metadata is touched.
 */
@Component
public class StockPerWeekSqlViewBindingCustomizer implements SqlViewBindingCustomizer
{
	private static final WindowId WINDOW_ID = WindowId.of(542159);

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public void customizeSqlViewBinding(final SqlViewBinding.Builder builder)
	{
		builder.queryIfNoFilters(false);
	}
}
