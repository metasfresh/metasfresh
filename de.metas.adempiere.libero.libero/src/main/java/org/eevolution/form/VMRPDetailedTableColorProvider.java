package org.eevolution.form;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import org.adempiere.plaf.AdempierePLAF;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPNoteBL;

/**
 * Colors {@link VMRPDetailed} rows based on what kind of notices we have linked to {@link I_PP_MRP} records:
 * <ul>
 * <li>Errors (Red) - if there is a MRP note of type error (see {@link IMRPNoteBL#getMRPErrorCodes()})
 * <li>Warnings (Yellow) - if there are MRP notes but none of them are errors
 * <li>None - if there are no MRP notes
 * </ul>
 * 
 * @author tsa
 *
 */
/* package */final class VMRPDetailedTableColorProvider extends TableColorProviderAdapter
{
	private static final Color COLOR_WARNING = new Color(255, 255, 150);

	private int idColumnIndex;

	public VMRPDetailedTableColorProvider(final int idColumnIndex)
	{
		super();
		this.idColumnIndex = idColumnIndex;
	}

	@Override
	public Color getBackgroundColor(final ITable table, final int rowIndexModel)
	{
		final VMRPDetailedIDColumn mrpColumn = getMRPColumn(table, rowIndexModel);
		if (mrpColumn == null)
		{
			return COLOR_NONE;
		}

		final int countAllMRPNotices = mrpColumn.getCountAllMRPNotices();
		final int countErrorMRPNotices = mrpColumn.getCountErrorMRPNotices();
		if (countErrorMRPNotices > 0)
		{
			return AdempierePLAF.getFieldBackground_Error();
		}
		else if (countAllMRPNotices > 0)
		{
			return COLOR_WARNING;
		}
		else
		{
			return COLOR_NONE;
		}
	}

	private VMRPDetailedIDColumn getMRPColumn(final ITable table, int rowIndexModel)
	{
		if (idColumnIndex < 0)
		{
			return null;
		}
		final Object data = table.getModelValueAt(rowIndexModel, idColumnIndex);
		if (data instanceof VMRPDetailedIDColumn)
		{
			return (VMRPDetailedIDColumn)data;
		}

		return null;
	}
}
