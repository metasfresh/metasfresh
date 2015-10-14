package org.adempiere.ad.validationRule.impl;

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


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.util.NamePair;

/**
 * Null validation rule. A null validation rule, does nothing. Is not filtering any records.
 * 
 * @author tsa
 * 
 */
public final class NullValidationRule implements IValidationRule
{
	public static final NullValidationRule instance = new NullValidationRule();

	private NullValidationRule()
	{
		super();
	}

	@Override
	public boolean isImmutable()
	{
		return true;
	}

	@Override
	public boolean isValidationRequired(IValidationContext evalCtx)
	{
		return false;
	}

	@Override
	public String getPrefilterWhereClause(IValidationContext evalCtx)
	{
		return null;
	}

	@Override
	public boolean accept(IValidationContext evalCtx, NamePair item)
	{
		return true;
	}

	@Override
	public List<String> getParameters(IValidationContext evalCtx)
	{
		return Collections.emptyList();
	}

	@Override
	public NamePair getValidValue(Object currentValue)
	{
		return null;
	}
};
