package org.adempiere.ad.expression.api;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.TranslatableParameterizedString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Similar with {@link TranslatableParameterizedString} but works on {@link IStringExpression}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class TranslatableParameterizedStringExpression implements IStringExpression
{
	public static final TranslatableParameterizedStringExpression of(final String adLanguageParamName, final IStringExpression expressionBaseLang, final IStringExpression expressionTrl)
	{
		return new TranslatableParameterizedStringExpression(adLanguageParamName, expressionBaseLang, expressionTrl);
	}

	public static final IStringExpression getExpressionBaseLang(final IStringExpression expression)
	{
		if (expression instanceof TranslatableParameterizedStringExpression)
		{
			return ((TranslatableParameterizedStringExpression)expression).getExpressionBaseLang();
		}
		else
		{
			return expression;
		}
	}

	public static final IStringExpression getExpressionTrl(final IStringExpression expression)
	{
		if (expression instanceof TranslatableParameterizedStringExpression)
		{
			return ((TranslatableParameterizedStringExpression)expression).getExpressionTrl();
		}
		else
		{
			return expression;
		}
	}

	public static final String getAD_LanguageParamName(final IStringExpression expression)
	{
		if (expression instanceof TranslatableParameterizedStringExpression)
		{
			return ((TranslatableParameterizedStringExpression)expression).getAD_LanguageParamName();
		}
		else
		{
			return null;
		}
	}

	private final IStringExpression expressionBaseLang;
	private final IStringExpression expressionTrl;
	private final String adLanguageParamName;
	private final List<String> parameters;

	private TranslatableParameterizedStringExpression(final String adLanguageParamName, final IStringExpression expressionBaseLang, final IStringExpression expressionTrl)
	{
		super();

		Check.assumeNotEmpty(adLanguageParamName, "adLanguageParamName is not empty");
		this.adLanguageParamName = adLanguageParamName;

		Check.assumeNotNull(expressionBaseLang, "Parameter expressionBaseLang is not null");
		this.expressionBaseLang = expressionBaseLang;

		Check.assumeNotNull(expressionTrl, "Parameter expressionTrl is not null");
		this.expressionTrl = expressionTrl;

		final Set<String> parameters = new LinkedHashSet<>();
		parameters.addAll(expressionBaseLang.getParameters());
		parameters.addAll(expressionTrl.getParameters());
		parameters.add(adLanguageParamName);

		this.parameters = ImmutableList.copyOf(parameters);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("baseLang", expressionBaseLang)
				.add("trl", expressionTrl)
				.add("adLanguageParamName", adLanguageParamName)
				.toString();
	}

	public IStringExpression getExpressionBaseLang()
	{
		return expressionBaseLang;
	}

	public IStringExpression getExpressionTrl()
	{
		return expressionTrl;
	}

	public String getAD_LanguageParamName()
	{
		return adLanguageParamName;
	}

	@Override
	public String getExpressionString()
	{
		return expressionBaseLang.getExpressionString();
	}

	@Override
	public String getFormatedExpressionString()
	{
		return expressionBaseLang.getFormatedExpressionString();
	}

	@Override
	public List<String> getParameters()
	{
		return parameters;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final IStringExpression expressionEffective;
		final String adLanguage = ctx.get_ValueAsString(adLanguageParamName);
		if (Language.isBaseLanguage(adLanguage))
		{
			expressionEffective = expressionBaseLang;
		}
		else
		{
			expressionEffective = expressionTrl;
		}

		return expressionEffective.evaluate(ctx, onVariableNotFound);
	}

	@Override
	public List<Object> getExpressionChunks()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx)
	{
		final IStringExpression expressionBaseLangNew = expressionBaseLang.resolvePartial(ctx);
		final IStringExpression expressionTrlNew = expressionTrl.resolvePartial(Evaluatees.excludingVariables(ctx, adLanguageParamName));
		return new TranslatableParameterizedStringExpression(adLanguageParamName, expressionBaseLangNew, expressionTrlNew);
	}
}
