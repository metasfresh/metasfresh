package org.adempiere.ad.validationRule.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.Env.Scope;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class GridTabValidationContext implements IValidationContext
{
	private static final transient Logger logger = LogManager.getLogger(GridTabValidationContext.class);

	private final Properties ctx;
	private final int windowNo;
	private final int tabNo;
	private final String contextTableName;
	private final String tableName;


	public GridTabValidationContext(final Properties ctx, final int windowNo, final int tabNo, final String tableName)
	{
		Check.assume(ctx != null, "context not null");
		// any windowNo, even Env.WINDOW_None is allowed
		Check.assume(tableName != null, "tableName not null");

		if (windowNo == Env.WINDOW_MAIN && tabNo <= 0)
		{
			final AdempiereException ex = new AdempiereException("Possible invalid definition of GridTabValidationContext: windowNo=" + windowNo + ", tabNo=" + tabNo + ", tableName=" + tableName);
			logger.warn(ex.getLocalizedMessage(), ex);
		}

		this.ctx = ctx;
		this.windowNo = windowNo;
		this.tabNo = tabNo;
		this.tableName = tableName;

		final int contextTableId = Env.getContextAsInt(ctx, windowNo, tabNo, GridTab.CTX_AD_Table_ID, true);
		if (contextTableId <= 0)
		{
			this.contextTableName = null;
			// accept even if there is no tableId found, because maybe we are using the field in custom interfaces
			// throw new AdempiereException("No AD_Table_ID found for WindowNo=" + windowNo + ", TabNo=" + tabNo);
		}
		else
		{
			this.contextTableName = Services.get(IADTableDAO.class).retrieveTableName(contextTableId);
			if (Check.isEmpty(contextTableName, true))
			{
				throw new AdempiereException("No TableName found for AD_Table_ID=" + contextTableId);
			}
		}
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Gets the context value for the given {@code variableName} by calling {@link Env#getContext(Properties, int, int, String, Scope)} with {@link Scope#Window}.
	 */
	@Override
	public String get_ValueAsString(final String variableName)
	{
		Check.assumeNotNull(variableName, "variableName not null");
		
		if(PARAMETER_ContextTableName.equals(variableName))
		{
			return contextTableName;
		}

		// only checking the window scope; global scope might contain default values (e.g. #C_DocTypeTarget_ID) that might confuse a validation rule
		final String value = Env.getContext(ctx, windowNo, tabNo, variableName, Scope.Window);
		if (Env.isNumericPropertyName(variableName) && Env.isPropertyValueNull(variableName, value))
		{
			// Because empty fields are not exported in context, even if they are present in Tab
			// is better to return "-1" as default value, to make this explicit
			// and also to not make Env.parseContext log a WARNING message
			return "-1";
		}
		return value;
	}

	@Override
	public String toString()
	{
		return "GridTabValidationContext [windowNo=" + windowNo
				+ ", tabNo=" + tabNo
				+ ", contextTableName=" + contextTableName
				+ ", tableName=" + tableName
				+ ", ctx=" + ctx + "]";
	}
}
