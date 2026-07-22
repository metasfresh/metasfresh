package de.metas.ui.web.view;

import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;

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
 * Per-window seam that lets a specific WebUI window supply a customized {@link SqlViewDataRepository}
 * while still reusing the whole standard {@link SqlViewFactory#createView} layout/filter/binding pipeline.
 * <p>
 * {@link SqlViewFactory} keeps building the {@link SqlViewBinding} exactly as before; it only delegates the
 * final <i>row-data repository</i> instantiation to the factory registered for the window (if any). Windows
 * without a registered factory keep the default {@code new SqlViewDataRepository(binding)} behavior unchanged.
 * <p>
 * Implementations are Spring beans, discovered as a list by {@link SqlViewFactory}.
 */
public interface ViewDataRepositoryFactory
{
	WindowId getWindowId();

	SqlViewDataRepository createViewDataRepository(SqlViewBinding sqlViewBinding);
}
