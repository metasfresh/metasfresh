package org.adempiere.ad.table.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_AD_Table;
import org.compiere.model.X_AD_ImpFormat_Row;
import org.compiere.util.DisplayType;

import de.metas.process.JavaProcess;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_ImpFormat_Row_Create_Based_OnTable extends JavaProcess
{
	private static transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Override
	protected String doIt()
	{
		final I_AD_ImpFormat impFormat = getRecord(I_AD_ImpFormat.class);

		final I_AD_Table table = impFormat.getAD_Table();
		final List<I_AD_Column> columns = tableDAO.retrieveColumnsForTable(table);
		final AtomicInteger index = new AtomicInteger(1);
		columns.stream().filter(column -> !DisplayType.isLookup(column.getAD_Reference_ID()))
				.forEach(column -> {
					final I_AD_ImpFormat_Row impRow = createImpFormatRow(impFormat, column, index);
					index.incrementAndGet();
					addLog("@Created@ @AD_ImpFormat_Row_ID@: {}", impRow);
				});

		return MSG_OK;
	}

	private I_AD_ImpFormat_Row createImpFormatRow(@NonNull final I_AD_ImpFormat impFormat, @NonNull final I_AD_Column column, final AtomicInteger index)
	{
		final I_AD_ImpFormat_Row impRow = newInstance(I_AD_ImpFormat_Row.class);
		impRow.setAD_Column_ID(column.getAD_Column_ID());
		impRow.setAD_ImpFormat_ID(impFormat.getAD_ImpFormat_ID());
		impRow.setName(column.getName());
		final int adRefId = column.getAD_Reference_ID();
		impRow.setDataType(extractDisplayType(adRefId));
		impRow.setSeqNo(index.get());
		impRow.setStartNo(index.get());
		save(impRow);

		return impRow;
	}

	private String extractDisplayType(final int adRefId)
	{
		if (DisplayType.isText(adRefId))
		{
			return X_AD_ImpFormat_Row.DATATYPE_String;
		}
		else if (DisplayType.isNumeric(adRefId))
		{
			return X_AD_ImpFormat_Row.DATATYPE_Number;
		}
		else if (DisplayType.isDate(adRefId))
		{
			return X_AD_ImpFormat_Row.DATATYPE_Date;
		}
		else
		{
			return X_AD_ImpFormat_Row.DATATYPE_Constant;
		}
	}
}
