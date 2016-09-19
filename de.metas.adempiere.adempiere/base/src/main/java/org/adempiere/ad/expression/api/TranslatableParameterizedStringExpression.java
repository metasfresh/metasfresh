package org.adempiere.ad.expression.api;

import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.expression.api.impl.StringExpressionsHelper;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

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
 * WARNING: this is a pure expression whom evaluation depends only on {@link Evaluatee} with one exception, the {@link Language#isBaseLanguage(String)} method used to determine if a given language is
 * the base language.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class TranslatableParameterizedStringExpression implements IStringExpression
{
	public static final TranslatableParameterizedStringExpression of(final String adLanguageParamName, final IStringExpression expressionBaseLang, final IStringExpression expressionTrl)
	{
		final CtxName adLanguageParam = CtxName.parse(adLanguageParamName);
		return new TranslatableParameterizedStringExpression(adLanguageParam, expressionBaseLang, expressionTrl);
	}

	public static final IStringExpression of(final TranslatableParameterizedString translatableString)
	{
		if (translatableString == TranslatableParameterizedString.EMPTY)
		{
			return NullStringExpression.instance;
		}

		final CtxName adLanguageParam = CtxName.parseWithMarkers(translatableString.getAD_LanguageParamName());
		return of(adLanguageParam, translatableString.getStringBaseLanguage(), translatableString.getStringTrlPattern());
	}

	public static final IStringExpression of(final CtxName adLanguageParam, final String expressionBaseLang, final String expressionTrl)
	{
		if (Objects.equals(expressionBaseLang, expressionTrl))
		{
			// => we have no translation, just a constant string expression
			return ConstantStringExpression.of(expressionBaseLang);
		}

		// NOTE: we need to compile the expressions because we don't know if they are constant or not
		final IStringExpression aexpressionBaseLang = StringExpressionCompiler.instance.compile(expressionBaseLang);
		final IStringExpression aexpressionTrl = StringExpressionCompiler.instance.compile(expressionTrl);

		return new TranslatableParameterizedStringExpression(adLanguageParam, aexpressionBaseLang, aexpressionTrl);
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
	private final CtxName adLanguageParam;
	private final Set<String> parameters;

	private TranslatableParameterizedStringExpression(final CtxName adLanguageParam, final IStringExpression expressionBaseLang, final IStringExpression expressionTrl)
	{
		super();

		Check.assumeNotNull(adLanguageParam, "Parameter adLanguageParam is not null");
		this.adLanguageParam = adLanguageParam;

		Check.assumeNotNull(expressionBaseLang, "Parameter expressionBaseLang is not null");
		this.expressionBaseLang = expressionBaseLang;

		Check.assumeNotNull(expressionTrl, "Parameter expressionTrl is not null");
		this.expressionTrl = expressionTrl;

		//
		// Expression parameters: all parameters from both expressions, plus the AD_Language parameter
		this.parameters = ImmutableSet.<String>builder()
				.addAll(expressionBaseLang.getParameters())
				.addAll(expressionTrl.getParameters())
				.add(adLanguageParam.getName())
				.build();
	}

	@Override
	public String toString()
	{
		// NOTE: we display only the base language expression
		// because we want to have a quick human readable string representation,
		// mainly when this expression is part of a composite
		return MoreObjects.toStringHelper("TRL")
				.addValue(expressionBaseLang)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(adLanguageParam, expressionBaseLang, expressionTrl);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}
		final TranslatableParameterizedStringExpression other = (TranslatableParameterizedStringExpression)obj;
		return Objects.equals(adLanguageParam, other.adLanguageParam)
				&& Objects.equals(expressionBaseLang, other.expressionBaseLang)
				&& Objects.equals(expressionTrl, other.expressionTrl);
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
		return adLanguageParam.getName();
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
	public Set<String> getParameters()
	{
		return parameters;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		try
		{
			//
			// Evaluate the adLanguage parameter
			final OnVariableNotFound adLanguageOnVariableNoFound = getOnVariableNotFoundForInternalParameter(onVariableNotFound);
			final String adLanguage = StringExpressionsHelper.evaluateParam(adLanguageParam, ctx, adLanguageOnVariableNoFound);
			if (adLanguage == null || adLanguage == IStringExpression.EMPTY_RESULT)
			{
				return IStringExpression.EMPTY_RESULT;
			}
			else if (adLanguage.isEmpty() && onVariableNotFound == OnVariableNotFound.Empty)
			{
				return "";
			}

			final IStringExpression expressionEffective;
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
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	private static final OnVariableNotFound getOnVariableNotFoundForInternalParameter(final OnVariableNotFound onVariableNotFound)
	{
		switch (onVariableNotFound)
		{
			case Preserve:
				// Preserve is not supported because we don't know which expression to pick if the deciding parameter is not determined
				return OnVariableNotFound.Fail;
			default:
				return onVariableNotFound;
		}
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx)
	{
		try
		{
			boolean changed = false;
			final IStringExpression expressionBaseLangNew = expressionBaseLang.resolvePartial(ctx);
			if (!expressionBaseLang.equals(expressionBaseLangNew))
			{
				changed = true;
			}

			final IStringExpression expressionTrlNew = expressionTrl.resolvePartial(Evaluatees.excludingVariables(ctx, adLanguageParam.getName()));
			if (!changed && !expressionTrl.equals(expressionTrlNew))
			{
				changed = true;
			}

			if (!changed)
			{
				return this;
			}

			return new TranslatableParameterizedStringExpression(adLanguageParam, expressionBaseLangNew, expressionTrlNew);
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}
}
