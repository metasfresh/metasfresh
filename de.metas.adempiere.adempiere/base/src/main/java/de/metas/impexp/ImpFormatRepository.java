package de.metas.impexp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class ImpFormatRepository
{
	public ImpFormat getById(@NonNull final ImpFormatId impFormatId)
	{
		final I_AD_ImpFormat impFormatRecord = InterfaceWrapperHelper.loadOutOfTrx(impFormatId, I_AD_ImpFormat.class);
		return toImpFormat(impFormatRecord);
	}

	public ImpFormat toImpFormat(@NonNull final I_AD_ImpFormat impFormatRecord)
	{
		final ImpFormatId impFormatId = ImpFormatId.ofRepoId(impFormatRecord.getAD_ImpFormat_ID());
		final ImmutableList<ImpFormatColumn> columns = retrieveColumns(impFormatId);
		final ImpFormatTableInfo tableInfo = retrieveImpFormatTableInfo(impFormatRecord.getAD_Table_ID());

		return ImpFormat.builder()
				.name(impFormatRecord.getName())
				.formatType(ImpFormatType.ofCode(impFormatRecord.getFormatType()))
				.multiLine(impFormatRecord.isMultiLine())
				.manualImport(impFormatRecord.isManualImport())
				.tableInfo(tableInfo)
				.columns(columns)
				.build();
	}

	private ImmutableList<ImpFormatColumn> retrieveColumns(@NonNull final ImpFormatId impFormatId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_ImpFormat_Row.class)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_ID, impFormatId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_ImpFormat_Row.COLUMNNAME_SeqNo)
				.create()
				.stream(I_AD_ImpFormat_Row.class)
				.map(this::toImpFormatRowOrNull)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private ImpFormatColumn toImpFormatRowOrNull(final I_AD_ImpFormat_Row rowRecord)
	{
		final I_AD_Column adColumn = rowRecord.getAD_Column();
		if (!adColumn.isActive())
		{
			return null;
		}

		return ImpFormatColumn.builder()
				.name(rowRecord.getName())
				.columnName(adColumn.getColumnName())
				.startNo(rowRecord.getStartNo())
				.endNo(rowRecord.getEndNo())
				.dataType(ImpFormatColumnDataType.ofCode(rowRecord.getDataType()))
				.maxLength(adColumn.getFieldLength())
				.dataFormat(rowRecord.getDataFormat())
				.decimalSeparator(DecimalSeparator.ofNullableStringOrDot(rowRecord.getDecimalPoint()))
				.divideBy100(rowRecord.isDivideBy100())
				.constantValue(rowRecord.getConstantValue())
				.build();
	}

	private ImpFormatTableInfo retrieveImpFormatTableInfo(final int adTableId)
	{
		final POInfo poInfo = POInfo.getPOInfo(adTableId);
		Check.assumeNotNull(poInfo, "poInfo is not null for AD_Table_ID={}", adTableId);

		final String tableName = poInfo.getTableName();

		final String tablePK = poInfo.getKeyColumnName();
		if (tablePK == null)
		{
			throw new AdempiereException("Table " + tableName + " has not primary key");
		}

		final String dataImportConfigIdColumnName = poInfo.hasColumnName(I_C_DataImport.COLUMNNAME_C_DataImport_ID)
				? I_C_DataImport.COLUMNNAME_C_DataImport_ID
				: null;

		// Set Additional Table Info
		String tableUnique1 = "";
		String tableUnique2 = "";
		String tableUniqueParent = "";
		String tableUniqueChild = "";

		if (adTableId == 532)		// I_Product
		{
			tableUnique1 = "UPC";						// UPC = unique
			tableUnique2 = "Value";
			tableUniqueChild = "VendorProductNo";		// Vendor No may not be unique !
			tableUniqueParent = "BPartner_Value";		// Makes it unique
		}
		else if (adTableId == 533)		// I_BPartner
		{
			// gody: 20070113 to allow multiple contacts per BP
			// m_tableUnique1 = "Value"; // the key
		}
		else if (adTableId == 534)		// I_ElementValue
		{
			tableUniqueParent = "ElementName";			// the parent key
			tableUniqueChild = "Value";					// the key
		}
		else if (adTableId == 535)		// I_ReportLine
		{
			tableUniqueParent = "ReportLineSetName";		// the parent key
			tableUniqueChild = "Name";					// the key
		}

		return ImpFormatTableInfo.builder()
				.tableName(tableName)
				.tablePK(tablePK)
				.tableUnique1(tableUnique1)
				.tableUnique2(tableUnique2)
				.tableUniqueParent(tableUniqueParent)
				.tableUniqueChild(tableUniqueChild)
				.dataImportConfigIdColumnName(dataImportConfigIdColumnName)
				.build();
	}
}
