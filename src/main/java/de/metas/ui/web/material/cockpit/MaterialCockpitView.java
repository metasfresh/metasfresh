package de.metas.ui.web.material.cockpit;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
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

public class MaterialCockpitView extends AbstractCustomView<MaterialCockpitRow>
{
	private ImmutableList<DocumentFilter> filters;

	protected MaterialCockpitView(
			@NonNull final ViewId viewId,
			@NonNull final ITranslatableString description,
			@NonNull final Supplier<List<MaterialCockpitRow>> rowsSupplier,
			@NonNull final ImmutableList<DocumentFilter> filters
			)
	{
		super(viewId, description, rowsSupplier);
		this.filters = filters;
	}

	@Override
	public String getTableNameOrNull(DocumentId documentId)
	{
		return I_X_MRP_ProductInfo_V.Table_Name;
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

}
