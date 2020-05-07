package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Color;

import org.adempiere.ad.ui.ITable;
import org.adempiere.ad.ui.TableColorProviderAdapter;
import org.adempiere.util.Check;

import de.metas.picking.legacy.form.ITableRowSearchSelectionMatcher;
import de.metas.picking.legacy.form.PackingMd;
import de.metas.picking.legacy.form.TableRowKey;
import de.metas.picking.terminal.form.swing.SwingPickingOKPanel;

/**
 * 
 * This color provider Highlights those mini table rows that are matched by the picking model's {@link ITableRowSearchSelectionMatcher}.
 * 
 */
public class FreshSwingPickingMiniTableColorProvider extends TableColorProviderAdapter
{
	private final SwingPickingOKPanel pickingPanel;

	public FreshSwingPickingMiniTableColorProvider(final SwingPickingOKPanel pickingPanel)
	{
		super();

		Check.assumeNotNull(pickingPanel, "pickingPanel not null");
		this.pickingPanel = pickingPanel;
	}

	@Override
	public Color getBackgroundColor(final ITable table, final int rowIndexModel)
	{
		final PackingMd pickingModel = pickingPanel.getModel();

		final ITableRowSearchSelectionMatcher tableRowSearchSelectionMatcher = pickingModel.getTableRowSearchSelectionMatcher();
		if (tableRowSearchSelectionMatcher.isNull())
		{
			return COLOR_NONE;
		}

		final TableRowKey tableRowKey = pickingModel.getTableRowKeyForRow(rowIndexModel);
		if (tableRowKey == null)
		{
			// shall not happen
			return COLOR_NONE;
		}

		if (tableRowSearchSelectionMatcher.match(tableRowKey))
		{
			return Color.YELLOW;
		}

		return COLOR_NONE;
	}

}
