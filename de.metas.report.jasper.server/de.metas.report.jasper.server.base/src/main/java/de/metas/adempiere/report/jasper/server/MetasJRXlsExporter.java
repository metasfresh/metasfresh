package de.metas.adempiere.report.jasper.server;

/*
 * #%L
 * de.metas.report.jasper.server.base
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


import java.util.Map;

import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.export.Cut;
import net.sf.jasperreports.engine.export.CutsInfo;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.apache.poi.ss.SpreadsheetVersion;

/**
 * Extension of {@link JRXlsExporter} which implements our custom features (e.g. {@link #PROPERTY_COLUMN_HIDDEN}).
 * 
 * @author tsa
 *
 */
public class MetasJRXlsExporter extends JRXlsExporter
{
	/**
	 * This property indicates whether the column content is hidden.
	 * 
	 * The property could be set to any element and in case at least one element has this property enabled, the entire column will be hidden.
	 */
	public static final String PROPERTY_COLUMN_HIDDEN = JRPropertiesUtil.PROPERTY_PREFIX + "export.xls.column.hidden";

	public MetasJRXlsExporter()
	{
		super();
		setStandardParameters();
	}

	/**
	 * Configure standard parameters.
	 * Mainly our aim is to get a clean Excel report.
	 */
	private final void setStandardParameters()
	{
		// Set a password to protect the locked cells.
		// NOTE: if no password is set, the cells which are marked as locked won't be locked.
		// NOTE2: instead of using a random password, it's better to have a well known one because we are setting the password only to protect the cells from writting and NOT to secure the document.
		// setParameter(JRXlsAbstractExporterParameter.PASSWORD, UUID.randomUUID().toString());
		setParameter(JRXlsAbstractExporterParameter.PASSWORD, "unlock");

		// Take out as much graphics & formatting as possible.
		// Our aim is to get a clean Excel file.
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, true);
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BACKGROUND, true);
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, true);
		setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
		setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
		setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
		setParameter(JRXlsAbstractExporterParameter.MAXIMUM_ROWS_PER_SHEET, SpreadsheetVersion.EXCEL97.getLastRowIndex());
	}

	@Override
	protected void setParameters()
	{
		super.setParameters();

		// Exporter shall use our "nature" in order to have all properties set to Cuts.
		nature = new MetasJRXlsExporterNature(jasperReportsContext, filter, isIgnoreGraphics, isIgnorePageMargins);
	}

	@Override
	protected void updateColumns(final CutsInfo xCuts)
	{
		super.updateColumns(xCuts);

		//
		// Iterate all xCuts (i.e. columns) and hide those which were marked to be hidden
		for (int col = xCuts.size() - 1; col >= 0; col--)
		{
			final Cut xCut = xCuts.getCut(col);
			if (isHideColumn(xCut))
			{
				sheet.setColumnHidden(col, true);
			}
		}
	}

	/**
	 * @param xCut
	 * @return true if the column shall be hidden in the resulting excel
	 */
	private boolean isHideColumn(final Cut xCut)
	{
		final Map<String, Object> cutProperties = xCut.getPropertiesMap();
		final boolean hidden = cutProperties.containsKey(PROPERTY_COLUMN_HIDDEN)
				&& (Boolean)cutProperties.get(PROPERTY_COLUMN_HIDDEN);
		return hidden;
	}
}
