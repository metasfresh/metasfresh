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


import java.util.List;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.NamePair;

/**
 * SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 * 
 * @author tsa
 * 
 */
public class SQLValidationRule implements IValidationRule
{
	private static final String SYSCONFIG_IsIgnoredValidationCodeFail = "org.adempiere.ad.api.impl.SQLValidationRule.IsIgnoredValidationCodeFail";

	private final CLogger log = CLogger.getCLogger(getClass());

	private final boolean isIgnoredValidationCodeFail;

	private final IStringExpression whereClause;
	private final boolean isStaticWhereClause;
	private String whereClauseParsed;

	public SQLValidationRule(String whereClause)
	{
		super();
		this.isIgnoredValidationCodeFail = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_IsIgnoredValidationCodeFail, false);

		this.whereClause = Services.get(IExpressionFactory.class).compileOrDefault(whereClause, IStringExpression.NULL, IStringExpression.class);

		this.isStaticWhereClause = this.whereClause.getParameters().isEmpty();
	}

	@Override
	public boolean isValidationRequired(IValidationContext evalCtx)
	{
		return true; // FIXME
//		final String whereClauseParsedNow = parseWhereClause(evalCtx);
//		final boolean valid = Util.equals(whereClauseParsedNow, this.whereClauseParsed);
//		return !valid;
	}

	/**
	 * @return true if there are no parameters
	 */
	@Override
	public boolean isImmutable()
	{
		return whereClause.getParameters().isEmpty();
	}

	@Override
	public List<String> getParameters(IValidationContext evalCtx)
	{
		return whereClause.getParameters();
	}

	@Override
	public String getPrefilterWhereClause(IValidationContext evalCtx)
	{
		if (isStaticWhereClause)
		{
			return whereClause.getExpressionString();
		}

		String validation = parseWhereClause(evalCtx);
		this.whereClauseParsed = validation;
		boolean validationFailed = Check.isEmpty(validation);

		if (!validationFailed)
		{
			return validation;
		}

		// metas: begin: us1261
		if (isIgnoredValidationCodeFail)
		{
			log.fine("Loader NOT Validated BUT IGNORED: " + this);
			return "1=1";
		}
		// metas: end: us1261

		log.fine("Loader NOT Validated: " + whereClause);
		// Bug 1843862 - Lookups not working on Report Viewer window
		// globalqss - when called from Viewer window ignore error about unparsabe context variables
		// there is no context in report viewer windows
		final int windowNo = evalCtx.getWindowNo();
		if (Ini.isClient() == false ||
				(Env.isRegularOrMainWindowNo(windowNo)
				&& Env.getWindow(windowNo) != null // metas-ts: in some integration tests, there might be no window at all
				&& !Env.getWindow(windowNo).getClass().getName().equals("org.compiere.print.Viewer")))
		{
			return WHERECLAUSE_ERROR;
		}

		return validation;
	}

	private String parseWhereClause(IValidationContext evalCtx)
	{
		if (isStaticWhereClause)
		{
			return whereClause.getExpressionString();
		}
		
		final boolean ignoreUnparsable = false;
		final String whereClauseParsedNow = whereClause.evaluate(evalCtx, ignoreUnparsable);
		return whereClauseParsedNow;
	}

	/**
	 * This method always returns true!
	 */
	@Override
	public final boolean accept(IValidationContext evalCtx, NamePair item)
	{
		return true;
	}

	@Override
	public String toString()
	{
		return "SQLValidationRule [whereClause=" + whereClause + ", isStaticWhereClause=" + isStaticWhereClause + "]";
	}

	@Override
	public NamePair getValidValue(Object currentValue)
	{
		return null;
	}
}
