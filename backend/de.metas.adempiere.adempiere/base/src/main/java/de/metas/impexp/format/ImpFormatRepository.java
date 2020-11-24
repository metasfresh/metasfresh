package de.metas.impexp.format;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.springframework.stereotype.Repository;


import com.google.common.collect.ImmutableList;

import de.metas.util.Services;
import lombok.NonNull;

import java.nio.charset.Charset;
import java.util.Objects;

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
	private final ImportTableDescriptorRepository importTableDescriptorRepo;

	public ImpFormatRepository(@NonNull final ImportTableDescriptorRepository importTableDescriptorRepo)
	{
		this.importTableDescriptorRepo = importTableDescriptorRepo;
	}

	public ImpFormat getById(@NonNull final ImpFormatId impFormatId)
	{
		final I_AD_ImpFormat impFormatRecord = InterfaceWrapperHelper.loadOutOfTrx(impFormatId, I_AD_ImpFormat.class);
		return toImpFormat(impFormatRecord);
	}

	public ImpFormat toImpFormat(@NonNull final I_AD_ImpFormat impFormatRecord)
	{
		final ImpFormatId impFormatId = ImpFormatId.ofRepoId(impFormatRecord.getAD_ImpFormat_ID());
		final ImmutableList<ImpFormatColumn> columns = retrieveColumns(impFormatId);
		final AdTableId adTableId = AdTableId.ofRepoId(impFormatRecord.getAD_Table_ID());
		final ImportTableDescriptor importTableDescriptor = importTableDescriptorRepo.getByTableId(adTableId);

		return ImpFormat.builder()
				.id(impFormatId)
				.name(impFormatRecord.getName())
				.formatType(ImpFormatType.ofCode(impFormatRecord.getFormatType()))
				.multiLine(impFormatRecord.isMultiLine())
				.manualImport(impFormatRecord.isManualImport())
				.importTableDescriptor(importTableDescriptor)
				.columns(columns)
				.charset(Charset.forName(impFormatRecord.getFileCharset()))
				.skipFirstNRows(impFormatRecord.getSkipFirstNRows())
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
				.filter(Objects::nonNull)
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
}
