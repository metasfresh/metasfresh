package de.metas.ui.web.material.cockpit.stockdetails;

import de.metas.handlingunits.model.I_M_HU_Stock_Detail_V;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;

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

public class StockDetailsView extends AbstractCustomView<StockDetailsRow>
{
	private final ViewId parentViewId;

	protected StockDetailsView(
			final ViewId viewId,
			final ViewId parentViewId,
			final IRowsData<StockDetailsRow> rowsData,
			final DocumentFilterDescriptorsProvider viewFilterDescriptors)
	{
		super(
				Check.assumeNotNull(viewId, "viewId"),
				null, // description
				Check.assumeNotNull(rowsData, "rowsData"),
				Check.assumeNotNull(viewFilterDescriptors, "viewFilterDescriptors"));
		this.parentViewId = parentViewId;
	}

	public static StockDetailsView cast(IView view)
	{
		return (StockDetailsView)view;
	}

	@Override
	public String getTableNameOrNull(DocumentId IGNORED)
	{
		return I_M_HU_Stock_Detail_V.Table_Name;
	}

	@Override
	public ViewId getParentViewId()
	{
		return parentViewId;
	}
}
