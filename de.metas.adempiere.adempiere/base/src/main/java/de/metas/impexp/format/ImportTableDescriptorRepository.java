package de.metas.impexp.format;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
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
public class ImportTableDescriptorRepository
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

	private static final String COLUMNNAME_C_DataImport_ID = I_C_DataImport.COLUMNNAME_C_DataImport_ID;
	private static final String COLUMNNAME_AD_Issue_ID = I_AD_Issue.COLUMNNAME_AD_Issue_ID;
	private static final String COLUMNNAME_I_LineContent = "I_LineContent";
	private static final String COLUMNNAME_I_LineNo = "I_LineNo";

	private final CCache<AdTableId, ImportTableDescriptor> //
	importTableDescriptors = CCache.<AdTableId, ImportTableDescriptor> builder()
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	public ImportTableDescriptor getByTableId(@NonNull final AdTableId adTableId)
	{
		return importTableDescriptors.getOrLoad(adTableId, this::retrieveByTableId);
	}

	public ImportTableDescriptor getByTableName(@NonNull final String tableName)
	{
		final AdTableId adTableId = AdTableId.ofRepoId(adTablesRepo.retrieveTableId(tableName));

		return getByTableId(adTableId);
	}

	private ImportTableDescriptor retrieveByTableId(@NonNull final AdTableId adTableId)
	{
		final POInfo poInfo = POInfo.getPOInfo(adTableId);
		Check.assumeNotNull(poInfo, "poInfo is not null for AD_Table_ID={}", adTableId);

		final String tableName = poInfo.getTableName();

		final String keyColumnName = poInfo.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new AdempiereException("Table " + tableName + " has not primary key");
		}

		assertColumnNameExists(ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID, poInfo);
		assertColumnNameExists(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg, poInfo);

		return ImportTableDescriptor.builder()
				.tableName(tableName)
				.keyColumnName(keyColumnName)
				//
				.dataImportConfigIdColumnName(columnNameIfExists(COLUMNNAME_C_DataImport_ID, poInfo))
				.adIssueIdColumnName(columnNameIfExists(COLUMNNAME_AD_Issue_ID, poInfo))
				.importLineContentColumnName(columnNameIfExists(COLUMNNAME_I_LineContent, poInfo))
				.importLineNoColumnName(columnNameIfExists(COLUMNNAME_I_LineNo, poInfo))
				.errorMsgMaxLength(poInfo.getFieldLength(ImportTableDescriptor.COLUMNNAME_I_ErrorMsg))
				//
				.build();
	}

	private static void assertColumnNameExists(@NonNull final String columnName, @NonNull final POInfo poInfo)
	{
		if (!poInfo.hasColumnName(columnName))
		{
			throw new AdempiereException("No " + poInfo.getTableName() + "." + columnName + " defined");
		}
	}

	private static String columnNameIfExists(@NonNull final String columnName, @NonNull final POInfo poInfo)
	{
		return poInfo.hasColumnName(columnName) ? columnName : null;

	}

}
