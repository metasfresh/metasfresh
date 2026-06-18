package de.metas.ui.web.view.descriptor;

import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
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
 * Per-{@link WindowId} hook to adjust the {@link SqlViewBinding.Builder} of a <b>standard AD-window-backed</b> view,
 * without introducing a {@link de.metas.ui.web.view.ViewProfile} (unlike {@link de.metas.ui.web.view.SqlViewCustomizer},
 * which is keyed by {@code (windowId, profileId)} and therefore surfaces a profile selector in the UI).
 * <p>
 * Implementors are discovered by Spring and autowired into {@link SqlViewFactory}.
 * When a binding is built for the implementor's {@link #getWindowId()},
 * {@link #customizeSqlViewBinding(SqlViewBinding.Builder)} is invoked after the builder has been populated from the
 * AD metadata, so it can override binding properties (e.g. {@code queryIfNoFilters}).
 * <p>
 * At most one customizer per {@link WindowId} is allowed.
 */
public interface SqlViewBindingCustomizer
{
	WindowId getWindowId();

	void customizeSqlViewBinding(SqlViewBinding.Builder builder);
}
