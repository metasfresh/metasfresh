package de.metas.letter;

import de.metas.i18n.Language;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.Evaluatee;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Builder
public class BoilerPlate
{
	@NonNull
	BoilerPlateId id;

	@NonNull
	Language language;

	@NonNull
	IStringExpression subject;

	@NonNull
	IStringExpression textSnippet;

	public String evaluateSubject(@NonNull final Evaluatee evalCtx)
	{
		return subject.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Empty);
	}

	public String evaluateTextSnippet(@NonNull final Evaluatee evalCtx)
	{
		return textSnippet.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve);
	}

}
