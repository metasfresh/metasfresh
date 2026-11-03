/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.compiere.model.I_M_CostRevaluation;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Status menu of a completed cost revaluation ("Kosten Neubewertung"):
 * <ul>
 * <li>never Re-Activate;</li>
 * <li>never Reverse-Correct or Reverse-Accrual (Storno): reversing a posted revaluation would reset the product's cost;</li>
 * <li>Void only for a {@link RevaluationSource#Manual} revaluation where nothing was booked yet,
 * i.e. no line has its revaluation {@code M_CostDetail} (see {@link CostRevaluationDocumentHandler#voidIt});</li>
 * <li>Close stays as offered by the defaults.</li>
 * </ul>
 * The parameters include {@code Posted}, so the cached menu is refreshed once the document gets posted.
 */
@Component
public class CostRevaluationDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(
			I_M_CostRevaluation.COLUMNNAME_M_CostRevaluation_ID,
			I_M_CostRevaluation.COLUMNNAME_RevaluationSource,
			I_M_CostRevaluation.COLUMNNAME_Posted);

	@NonNull private final CostRevaluationService costRevaluationService;

	public CostRevaluationDocActionOptionsCustomizer(@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

	@Override
	public String getAppliesToTableName()
	{
		return I_M_CostRevaluation.Table_Name;
	}

	@Override
	public Set<String> getParameters()
	{
		return PARAMETERS;
	}

	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		if (!IDocument.STATUS_Completed.equals(optionsCtx.getDocStatus()))
		{
			return;
		}

		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());
		docActions.remove(IDocument.ACTION_ReActivate);
		docActions.remove(IDocument.ACTION_Reverse_Correct);
		docActions.remove(IDocument.ACTION_Reverse_Accrual);
		docActions.remove(IDocument.ACTION_Void);
		if (isVoidAllowed(optionsCtx))
		{
			docActions.add(IDocument.ACTION_Void);
		}
		optionsCtx.setDocActions(ImmutableSet.copyOf(docActions));
	}

	private boolean isVoidAllowed(@NonNull final DocActionOptionsContext optionsCtx)
	{
		final RevaluationSource source = RevaluationSource.ofNullableCode(optionsCtx.getParameterValue(I_M_CostRevaluation.COLUMNNAME_RevaluationSource));
		if (source != RevaluationSource.Manual)
		{
			return false;
		}

		final CostRevaluationId costRevaluationId = toCostRevaluationIdOrNull(optionsCtx.getParameterValue(I_M_CostRevaluation.COLUMNNAME_M_CostRevaluation_ID));
		if (costRevaluationId == null)
		{
			return false;
		}

		return !costRevaluationService.hasAnyLineWithCostDetail(costRevaluationId);
	}

	@Nullable
	private static CostRevaluationId toCostRevaluationIdOrNull(@Nullable final String value)
	{
		return CostRevaluationId.ofRepoIdOrNull(NumberUtils.asInt(value, -1));
	}
}
