package org.adempiere.ad.validationRule.impl;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;

/**
 * Immutable SQL Validation Rule is a validation rule which has only an SQL Where Clause.
 *
 * @author tsa
 *
 */
/* package */final class SQLValidationRule implements IValidationRule
{
	private static final String SYSCONFIG_IsIgnoredValidationCodeFail = "org.adempiere.ad.api.impl.SQLValidationRule.IsIgnoredValidationCodeFail";

	// private static final Logger log = LogManager.getLogger(SQLValidationRule.class);

	private final String name;
	private final IStringExpression whereClause;
	private final boolean isStaticWhereClause;
	private final boolean isIgnoredValidationCodeFail;

	// private String whereClauseParsed;

	public SQLValidationRule(final String name, final String whereClause)
	{
		super();
		this.name = name;
		isIgnoredValidationCodeFail = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_IsIgnoredValidationCodeFail, false);

		this.whereClause = Services.get(IExpressionFactory.class).compileOrDefault(whereClause, IStringExpression.NULL, IStringExpression.class);

		isStaticWhereClause = this.whereClause.getParameters().isEmpty();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("whereClause", whereClause)
				.add("isStaticWhereClause", isStaticWhereClause)
				.add("isIgnoredValidationCodeFail", isIgnoredValidationCodeFail)
				.toString();
	}

	@Override
	public Set<String> getAllParameters()
	{
		return whereClause.getParameters();
	}
	
	@Override
	public boolean isImmutable()
	{
		return isStaticWhereClause;
	}

	@Override
	public IStringExpression getPrefilterWhereClause()
	{
		return whereClause;
	}

	// FIXME REFACTOR THIS
//	@Override
//	public String getPrefilterWhereClause(final IValidationContext evalCtx)
//	{
//		if (isStaticWhereClause)
//		{
//			return whereClause.getExpressionString();
//		}
//
//		final String validation = parseWhereClause(evalCtx);
//		// this.whereClauseParsed = validation;
//		final boolean validationFailed = Check.isEmpty(validation);
//
//		if (!validationFailed)
//		{
//			return validation;
//		}
//
//		// metas: begin: us1261
//		if (isIgnoredValidationCodeFail)
//		{
//			log.debug("Loader NOT Validated BUT IGNORED: {}", this);
//			return "1=1";
//		}
//		// metas: end: us1261
//
//		log.debug("Loader NOT Validated: {}", this);
//		// Bug 1843862 - Lookups not working on Report Viewer window
//		// globalqss - when called from Viewer window ignore error about unparsabe context variables
//		// there is no context in report viewer windows
//		final int windowNo = evalCtx.getWindowNo();
//		if (Ini.isClient() == false ||
//				Env.isRegularOrMainWindowNo(windowNo)
//						&& Env.getWindow(windowNo) != null // metas-ts: in some integration tests, there might be no window at all
//						&& !Env.getWindow(windowNo).getClass().getName().equals("org.compiere.print.Viewer"))
//		{
//			return WHERECLAUSE_ERROR;
//		}
//
//		return validation;
//	}
//
//	private String parseWhereClause(final IValidationContext evalCtx)
//	{
//		if (isStaticWhereClause)
//		{
//			return whereClause.getExpressionString();
//		}
//
//		final boolean ignoreUnparsable = false;
//		final String whereClauseParsedNow = whereClause.evaluate(evalCtx, ignoreUnparsable);
//		return whereClauseParsedNow;
//	}
	
	@Override
	public final INamePairPredicate getPostQueryFilter()
	{
		return INamePairPredicate.NULL;
	}
}
