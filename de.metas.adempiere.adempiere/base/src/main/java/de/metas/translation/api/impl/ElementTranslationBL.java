package de.metas.translation.api.impl;

import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import de.metas.translation.api.IElementTranslationBL;

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

public class ElementTranslationBL implements IElementTranslationBL
{
	public static final String FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update = "update_TRL_Tables_On_AD_Element_TRL_Update";
	public static final String FUNCTION_Update_FieldTranslation_From_AD_Name_Element = "update_FieldTranslation_From_AD_Name_Element";

	@Override
	public void updateTranslations(final int elementId, final String adLanguage)
	{
		// Update Columns, Fields, Parameters, Print Info translation tables
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		{
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update, elementId, adLanguage), null);
		}
	}

	private String addUpdateFunctionCall(final String functionCall, final int elementId, final String adLanguage)
	{
		// #1044
		// Add the prefix DDL so the statement will appear in the migration script
		// Usually, the select statements are not migrated ( see org.compiere.dbPort.Convert.logMigrationScript(String, String).dontLog())
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + elementId + "," + DB.TO_STRING(adLanguage) + ") ";
	}

	@Override
	public void updateFieldTranslationsFromAD_Name(int elementId)
	{

		final String trxName = ITrx.TRXNAME_ThreadInherited;
		{
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForFieldTRL(FUNCTION_Update_FieldTranslation_From_AD_Name_Element, elementId), null);
		}

	}

	private String addUpdateFunctionCallForFieldTRL(final String functionCall, int elementId)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + elementId +") ";
	}
}
