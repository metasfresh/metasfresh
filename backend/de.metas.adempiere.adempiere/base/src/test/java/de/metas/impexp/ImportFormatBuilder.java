package de.metas.impexp;

import de.metas.impexp.format.ImpFormatColumnDataType;
import de.metas.impexp.format.ImpFormatId;
import de.metas.impexp.format.ImpFormatType;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ImportFormatBuilder
{
	public static ImportFormatBuilder newInstance(final String importTableName)
	{
		return new ImportFormatBuilder(importTableName);
	}

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final String importTableName;
	@Getter
	private final AdTableId importTableId;

	private String name;
	private boolean manualImport;
	private Charset charset;

	private final ArrayList<I_AD_ImpFormat_Row> columnRecords = new ArrayList<>();

	private ImportFormatBuilder(@NonNull final String importTableName)
	{
		this.importTableName = importTableName;
		this.importTableId = AdTableId.ofRepoId(tableDAO.retrieveTableId(importTableName));
	}

	public ImpFormatId build()
	{
		final I_AD_ImpFormat impFormatRecord = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat.class);
		impFormatRecord.setName(name);
		impFormatRecord.setAD_Table_ID(importTableId.getRepoId());
		impFormatRecord.setFormatType(ImpFormatType.COMMA_SEPARATED.getCode());
		impFormatRecord.setIsManualImport(manualImport);
		impFormatRecord.setFileCharset(charset.name());
		saveRecord(impFormatRecord);

		for (I_AD_ImpFormat_Row colRecord : columnRecords)
		{
			colRecord.setAD_ImpFormat_ID(impFormatRecord.getAD_ImpFormat_ID());
			saveRecord(colRecord);
		}

		return ImpFormatId.ofRepoId(impFormatRecord.getAD_ImpFormat_ID());
	}

	public ImportFormatBuilder name(final String name)
	{
		this.name = name;
		return this;
	}

	public ImportFormatBuilder manualImport(boolean manualImport)
	{
		this.manualImport = manualImport;
		return this;
	}

	public ImportFormatBuilder charset(final Charset charset)
	{
		this.charset = charset;
		return this;
	}

	public ImportFormatBuilder stringColumn(final String columnName)
	{
		return column(columnName, ImpFormatColumnDataType.String);
	}

	public ImportFormatBuilder numericColumn(final String columnName)
	{
		return column(columnName, ImpFormatColumnDataType.Number);
	}

	public ImportFormatBuilder column(final String columnName, final ImpFormatColumnDataType type)
	{
		final int adColumnId = tableDAO.retrieveColumn(importTableName, columnName).getAD_Column_ID();

		final I_AD_ImpFormat_Row colRecord = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat_Row.class);
		colRecord.setName("Value");
		colRecord.setAD_Column_ID(adColumnId);
		colRecord.setDataType(type.getCode());
		colRecord.setStartNo(columnRecords.size() + 1);

		columnRecords.add(colRecord);

		return this;

	}

}
