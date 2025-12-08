/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.window.descriptor.sql;

import com.google.common.collect.ImmutableSet;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.ui.web.window.WindowConstants;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import java.util.Optional;
import java.util.Set;

/**
 * Default expression used to provide a sequence number for fields with AD_Sequence_ID set
 */
public class DocSequenceAwareFieldStringExpression implements IStringExpression
{
	private static final String PARAMETER_AD_Client_ID = WindowConstants.FIELDNAME_AD_Client_ID;
	private static final Set<CtxName> PARAMETERS = ImmutableSet.of(CtxNames.parse(PARAMETER_AD_Client_ID));

	@NonNull
	private final DocSequenceId sequenceId;

	public DocSequenceAwareFieldStringExpression(@NonNull final DocSequenceId sequenceId)
	{
		this.sequenceId = sequenceId;
	}

	@Override
	public String getExpressionString()
	{
		return "@CustomSequenceNo@";
	}

	@Override
	public Set<CtxName> getParameters()
	{
		return PARAMETERS;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final IExpressionEvaluator.OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final ClientId adClientId = Optional.ofNullable(ctx.get_ValueAsInt(PARAMETER_AD_Client_ID, null))
				.map(ClientId::ofRepoId)
				.orElseGet(() -> ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx())));

		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		final String sequenceNo = documentNoFactory.forSequenceId(sequenceId)
				.setFailOnError(onVariableNotFound.equals(IExpressionEvaluator.OnVariableNotFound.Fail))
				.setClientId(adClientId)
				.build();

		if (sequenceNo == null && onVariableNotFound == IExpressionEvaluator.OnVariableNotFound.Fail)
		{
			throw new AdempiereException("Failed to compute sequence!")
					.appendParametersToMessage()
					.setParameter("sequenceId", sequenceId);
		}

		return sequenceNo;
	}

	@NonNull
	public static DocSequenceAwareFieldStringExpression of(@NonNull final DocSequenceId sequenceId)
	{
		return new DocSequenceAwareFieldStringExpression(sequenceId);
	}
}
