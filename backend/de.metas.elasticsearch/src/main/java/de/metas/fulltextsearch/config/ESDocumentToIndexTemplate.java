/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.config;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.Evaluatee;

import java.util.Set;

@Value
public class ESDocumentToIndexTemplate
{
	@NonNull IStringExpression jsonExpression;

	public static ESDocumentToIndexTemplate ofJsonString(final String json)
	{
		return new ESDocumentToIndexTemplate(IStringExpression.compile(json));
	}

	private ESDocumentToIndexTemplate(@NonNull final IStringExpression jsonExpression)
	{
		this.jsonExpression = jsonExpression;
	}

	public ESDocumentToIndex resolve(
			@NonNull final Evaluatee evalCtx,
			@NonNull final String documentId)
	{
		final ToJsonEvaluatee evalCtxEffective = new ToJsonEvaluatee(evalCtx);
		return ESDocumentToIndex.builder()
				.documentId(documentId)
				.json(jsonExpression.evaluate(evalCtxEffective, IExpressionEvaluator.OnVariableNotFound.Fail))
				.build();
	}

	public Set<ESFieldName> getESFieldNames()
	{
		return resolve(variableName -> "dummy", "documentId").getESFieldNames();

	}
}
