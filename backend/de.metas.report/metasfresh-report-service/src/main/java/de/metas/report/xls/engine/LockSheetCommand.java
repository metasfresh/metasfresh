package de.metas.report.xls.engine;

import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.slf4j.Logger;

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
 * Command to lock a sheet using a given password. If some column are to remain unlocked, edit the template accordingly
 * (see src/main/jasperreports/de/metas/docs/deliveryplanning/deliveryplanning.xls for how to use it)
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LockSheetCommand extends AbstractCommand
{
	private static final Logger logger = LogManager.getLogger(LockSheetCommand.class);

	public static final String NAME = "lock-sheet";
	public static final String DEFAULT_XLS_PASSWORD = "unlock";

	@Getter
	@Setter
	private String password;

	private Area _area;

	@Override
	public String getName()
	{
		return NAME;
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

			logger.debug("Locking sheet");
			lockSheet(cellRef);
		//
		return size;
	}

	private void lockSheet(final CellRef cellRef)
	{
		final Transformer transformer = getTransformer();
		if (transformer instanceof PoiTransformer)
		{
			final PoiTransformer poiTransformer = (PoiTransformer)transformer;
			final Workbook poiWorkbook = poiTransformer.getWorkbook();
			if (poiWorkbook == null)
			{
				logger.warn("Cannot lock sheet of {} because there is no workbook found", cellRef);
				return;
			}

			final String sheetName = cellRef.getSheetName();
			final Sheet poiSheet = poiWorkbook.getSheet(sheetName);
			if (poiSheet == null)
			{
				logger.warn("Cannot lock sheet of {} because there is no sheet found", cellRef);
				return;
			}
				poiSheet.protectSheet(CoalesceUtil.coalesceNotNull(password, DEFAULT_XLS_PASSWORD));
				logger.debug("Sheet of {} was locked", cellRef);
		}
		else
		{
			logger.warn("Cannot lock sheet of {} because transformer {} is not supported", cellRef, transformer);
		}
	}

}
