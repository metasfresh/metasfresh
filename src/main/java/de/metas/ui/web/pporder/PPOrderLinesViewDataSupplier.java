package de.metas.ui.web.pporder;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
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

public class PPOrderLinesViewDataSupplier
{
	private final ASIViewRowAttributesProvider asiAttributesProvider;
	private final ExtendedMemorizingSupplier<PPOrderLinesViewData> rowsSupplier;

	@Builder
	private PPOrderLinesViewDataSupplier(
			@NonNull final WindowId viewWindowId,
			final int ppOrderId,
			@Nullable final ASIViewRowAttributesProvider asiAttributesProvider,
			@NonNull final SqlViewBinding huSQLViewBinding)
	{
		this.asiAttributesProvider = asiAttributesProvider;
		rowsSupplier = ExtendedMemorizingSupplier.of(() -> PPOrderLinesLoader.builder(viewWindowId)
				.asiAttributesProvider(asiAttributesProvider)
				.huSQLViewBinding(huSQLViewBinding)
				.build()
				.retrieveData(ppOrderId));
	}

	public PPOrderLinesViewData getData()
	{
		return rowsSupplier.get();
	}

	public void invalidate()
	{
		rowsSupplier.forget();
		if (asiAttributesProvider != null)
		{
			asiAttributesProvider.invalidateAll();
		}
	}
}
