package de.metas.ui.web.view;

import de.metas.ui.web.material.stockperweek.StockPerWeekSelectionFactory;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
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
 * Registers the {@link StockPerWeekViewDataRepository} for the standalone "Bestand pro Woche"
 * window (542159) so its row selection and page render are served from {@code MD_Stock_PerWeek_fn} rather
 * than by re-materializing {@code MD_Stock_PerWeek_V}. Every other window keeps the default repository.
 */
@Component
public class StockPerWeekViewDataRepositoryFactory implements ViewDataRepositoryFactory
{
	@Override
	public WindowId getWindowId()
	{
		return StockPerWeekSelectionFactory.WINDOW_ID;
	}

	@Override
	public SqlViewDataRepository createViewDataRepository(@NonNull final SqlViewBinding sqlViewBinding)
	{
		return new StockPerWeekViewDataRepository(sqlViewBinding, new StockPerWeekSelectionFactory(sqlViewBinding));
	}
}
