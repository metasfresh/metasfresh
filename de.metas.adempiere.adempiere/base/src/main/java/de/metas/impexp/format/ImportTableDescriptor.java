package de.metas.impexp.format;

import org.compiere.model.I_C_DataImport_Run;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class ImportTableDescriptor
{
	public static final String COLUMNNAME_C_DataImport_Run_ID = I_C_DataImport_Run.COLUMNNAME_C_DataImport_Run_ID;
	public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";
	public static final String COLUMNNAME_I_IsImported = "I_IsImported";
	public static final String COLUMNNAME_Processed = "Processed";
	public static final String COLUMNNAME_Processing = "Processing";

	@NonNull
	String tableName;
	@NonNull
	String keyColumnName;

	String dataImportConfigIdColumnName;
	String adIssueIdColumnName;

	String importLineContentColumnName;
	String importLineNoColumnName;

	int errorMsgMaxLength;
}
