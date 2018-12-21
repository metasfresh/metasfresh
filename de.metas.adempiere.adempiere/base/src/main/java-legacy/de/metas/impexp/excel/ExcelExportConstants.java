package de.metas.impexp.excel;

import org.adempiere.service.ISysConfigBL;

import de.metas.util.Services;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
@Builder
public class ExcelExportConstants
{
	public static ExcelExportConstants getFromSysConfig()
	{
		ISysConfigBL sysconfigs = Services.get(ISysConfigBL.class);

		return ExcelExportConstants.builder()
				.maxRowsToAllowCellWidthAutoSize(sysconfigs.getIntValue(SYSCONFIG_MaxRowsToAllowCellWidthAutoSize, DEFAULT_MaxRowsToAllowCellWidthAutoSize))
				.useStreamingWorkbookImplementation(sysconfigs.getBooleanValue(SYSCONFIG_UseStreamingWorkbookImplementation, DEFAULT_UseStreamingWorkbookImplementation))
				.build();
	}

	private static final String SYSCONFIG_MaxRowsToAllowCellWidthAutoSize = "de.metas.excel.MaxRowsToAllowCellWidthAutoSize";
	private static final String SYSCONFIG_UseStreamingWorkbookImplementation = "de.metas.excel.UseStreamingWorkbookImplementation";

	public static final int DEFAULT_MaxRowsToAllowCellWidthAutoSize = 100_000;
	@Default
	private int maxRowsToAllowCellWidthAutoSize = DEFAULT_MaxRowsToAllowCellWidthAutoSize;

	public static final boolean DEFAULT_UseStreamingWorkbookImplementation = false;
	private boolean useStreamingWorkbookImplementation;
}
