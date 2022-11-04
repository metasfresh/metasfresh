/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import lombok.NonNull;
import org.compiere.model.I_M_CostRevaluation;
import org.springframework.stereotype.Component;

@Component
public class CostRevaluationDocumentHandlerProvider implements DocumentHandlerProvider
{
	private final CostRevaluationService costRevaluationService;

	public CostRevaluationDocumentHandlerProvider(
			@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

	@Override
	public String getHandledTableName()
	{
		return I_M_CostRevaluation.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model_ignored)
	{
		return new CostRevaluationDocumentHandler(costRevaluationService);
	}
}
