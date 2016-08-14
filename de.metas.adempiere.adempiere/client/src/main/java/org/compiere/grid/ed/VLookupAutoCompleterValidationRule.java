package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.util.NamePair;

/* package */class VLookupAutoCompleterValidationRule implements IValidationRule
{
	// private static final transient Logger logger = CLogMgt.getLogger(VLookupAutoCompleterValidationRule.class);

	public static final Object SEARCHSQL_PLACEHOLDER = new String("***SEARCH SQL PLACEHOLDER***");

	private final String whereClause;
	private final List<Object> paramsTemplate;
	private final boolean immutable;

	public VLookupAutoCompleterValidationRule(final String whereClause, final List<Object> paramsTemplate)
	{
		super();

		this.whereClause = whereClause;
		this.paramsTemplate = paramsTemplate;
		this.immutable = !paramsTemplate.contains(SEARCHSQL_PLACEHOLDER);
	}

	@Override
	public boolean isImmutable()
	{
		return immutable;
	}

	@Override
	public String getPrefilterWhereClause(IValidationContext evalCtx)
	{
		return whereClause;
	}

	@Override
	public List<String> getParameters(IValidationContext evalCtx)
	{
		return Collections.emptyList();
	}
	
	public List<Object> getParameterValues(final String searchSQL)
	{
		final List<Object> params = new ArrayList<Object>(paramsTemplate);
		if (immutable)
		{
			return params;
		}

		for (int i = 0; i < params.size(); i++)
		{
			if (params.get(i) == SEARCHSQL_PLACEHOLDER)
			{
				params.set(i, searchSQL);
			}
		}
		return params;
	}

	@Override
	public boolean accept(IValidationContext evalCtx, NamePair item)
	{
		return true;
	}

	@Override
	public NamePair getValidValue(Object currentValue)
	{
		return null;
	}
}
