package org.adempiere.ad.language.impl;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Language;
import org.compiere.util.DB;

public class LanguageDAO implements ILanguageDAO
{
	private static final String SQL_retriveBaseLanguage_1P = "SELECT " + I_AD_Language.COLUMNNAME_AD_Language
			+ " FROM " + I_AD_Language.Table_Name
			+ " WHERE " + I_AD_Language.COLUMNNAME_IsBaseLanguage + "=?";

	@Override
	public final String retrieveBaseLanguage()
	{
		// NOTE: because this method is called right after database connection is established
		// we cannot use the Query API which is requiring MLanguage.getBaseLanguage() to be set

		final String baseADLanguage = DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_retriveBaseLanguage_1P, true);
		Check.assumeNotEmpty(baseADLanguage, "Base AD_Language shall be defined in database");

		return baseADLanguage;
	}
}
