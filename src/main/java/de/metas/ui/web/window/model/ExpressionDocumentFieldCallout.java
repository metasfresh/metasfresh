package de.metas.ui.web.window.model;

import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.IDocumentFieldCallout;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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
 * {@link IDocumentFieldCallout} implementation which computes field's value based on a given {@link IExpression}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ExpressionDocumentFieldCallout implements IDocumentFieldCallout
{
	public static final ExpressionDocumentFieldCallout of(final String targetFieldName, final IExpression<?> expression)
	{
		return new ExpressionDocumentFieldCallout(targetFieldName, expression);
	}

	private static final Logger logger = LogManager.getLogger(ExpressionDocumentFieldCallout.class);

	private final String id;
	private final String targetFieldName;
	private final IExpression<?> expression;

	private ExpressionDocumentFieldCallout(final String targetFieldName, final IExpression<?> expression)
	{
		super();

		Check.assumeNotEmpty(targetFieldName, "fieldName is not empty");
		this.targetFieldName = targetFieldName;

		Check.assumeNotNull(expression, "Parameter expression is not null");
		this.expression = expression;

		id = targetFieldName + "-" + expression.toString();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("targetFieldName", targetFieldName)
				.add("expression", expression)
				.add("id", id)
				.toString();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return expression.getParameters();
	}

	@Override
	public void execute(final ICalloutExecutor calloutExecutor, final ICalloutField calloutField) throws Exception
	{
		final Document document = extractDocument(calloutField);
		final Evaluatee ctx = document.asEvaluatee();

		final Object value = expression.evaluate(ctx, OnVariableNotFound.ReturnNoResult);
		if (expression.isNoResult(value))
		{
			logger.trace("Got no value for {}. Skip setting {}", expression, targetFieldName);
			return;
		}

		final ReasonSupplier reason = () -> "From expression: " + expression;
		document.setValue(targetFieldName, value, reason);
	}

	private static final Document extractDocument(final ICalloutField calloutField)
	{
		final IDocumentField documentField = DocumentFieldAsCalloutField.unwrap(calloutField);
		return documentField.getDocument();
	}

}
