package de.metas.report.xls.engine;

import org.adempiere.util.Check;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Command to hide a range of columns if a given condition is met.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HideColumnIfCommand extends AbstractCommand
{
	private static final Logger logger = LogManager.getLogger(HideColumnIfCommand.class);

	public static final String NAME = "hide-column";

	private String condition;

	private Area _area;

	@Override
	public String getName()
	{
		return NAME;
	}

	/**
	 * Gets test condition as JEXL expression string
	 *
	 * @return test condition
	 */
	public String getCondition()
	{
		return condition;
	}

	/**
	 * Sets test condition as JEXL expression string
	 *
	 * @param condition condition to test
	 */
	public void setCondition(final String condition)
	{
		this.condition = condition;
	}

	private boolean isConditionTrue(final Context context)
	{
		try
		{
			final Boolean conditionResult = Util.isConditionTrue(getTransformationConfig().getExpressionEvaluator(), condition, context);
			return Boolean.TRUE.equals(conditionResult);
		}
		catch (Exception e)
		{
			logger.error("Failed evaluating expression '{}'. Considering it false.", condition, e);
			return false;
		}
	}

	@Override
	public Command addArea(final Area area)
	{
		super.addArea(area);

		if (_area == null)
		{
			_area = area;
		}
		else
		{
			throw new IllegalArgumentException("Adding more than one area is not allowed");
		}

		return this;
	}

	private Area getArea()
	{
		Check.assumeNotNull(_area, "area not null");
		return _area;
	}

	@Override
	public Size applyAt(final CellRef cellRef, final Context context)
	{
		//
		// Process area
		final Area area = getArea();
		final Size size = area.applyAt(cellRef, context);

		//
		// If given condition is evaluated as true, hide given columns
		if (isConditionTrue(context))
		{
			logger.debug("Hiding column of {} because condition '{}' was evaluated as true", cellRef, condition);
			hideColumn(cellRef);
		}
		else
		{
			logger.debug("Skip hiding column of {} because condition '{}' was evaluated as false", cellRef, condition);
		}

		//
		return size;
	}

	private void hideColumn(final CellRef cellRef)
	{
		final Transformer transformer = getTransformer();
		if (transformer instanceof PoiTransformer)
		{
			final PoiTransformer poiTransformer = (PoiTransformer)transformer;
			final Workbook poiWorkbook = poiTransformer.getWorkbook();
			if (poiWorkbook == null)
			{
				logger.warn("Cannot hide column of {} because there is no workbook found", cellRef);
				return;
			}

			final String sheetName = cellRef.getSheetName();
			final Sheet poiSheet = poiWorkbook.getSheet(sheetName);
			if (poiSheet == null)
			{
				logger.warn("Cannot hide column of {} because there is no sheet found", cellRef);
				return;
			}

			final AreaRef areaRef = getArea().getAreaRef();
			final CellRef areaFirstCell = areaRef.getFirstCellRef();
			final CellRef areaLastCell = areaRef.getLastCellRef();
			final int firstColumn = Math.min(areaFirstCell.getCol(), areaLastCell.getCol());
			final int lastColumn = Math.max(areaFirstCell.getCol(), areaLastCell.getCol());
			for (int col = firstColumn; col <= lastColumn; col++)
			{
				poiSheet.setColumnHidden(col, true);
				// poiSheet.setColumnWidth(cellRef.getCol(), 0);
				logger.debug("Column of {} was hidden", cellRef);
			}
		}
		else
		{
			logger.warn("Cannot hide column of {} because transformer {} is not supported", transformer);
		}
	}

}
