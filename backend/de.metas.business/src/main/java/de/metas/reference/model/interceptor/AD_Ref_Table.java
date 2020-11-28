package de.metas.reference.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.ModelValidator;

import de.metas.security.impl.ParsedSql;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.business
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

@Interceptor(I_AD_Ref_Table.class)
public class AD_Ref_Table
{
	public static final transient AD_Ref_Table instance = new AD_Ref_Table();

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Ref_Table.COLUMNNAME_WhereClause)
	public void lowerCaseWhereClause(final I_AD_Ref_Table refTable)
	{
		final String whereClause = refTable.getWhereClause();
		if (Check.isEmpty(whereClause, true))
		{
			// nothing to do
			return;
		}

		final String adaptedWhereClause = ParsedSql.rewriteWhereClauseWithLowercaseKeyWords(whereClause);

		refTable.setWhereClause(adaptedWhereClause);

	}

}
