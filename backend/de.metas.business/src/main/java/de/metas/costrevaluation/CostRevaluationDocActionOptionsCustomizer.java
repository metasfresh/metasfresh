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
import org.compiere.model.I_M_CostRevaluation;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class CostRevaluationDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{
	@Override
	public String getAppliesToTableName()
	{
		return I_M_CostRevaluation.Table_Name;
	}

	/**
	 * A completed revaluation offers Void: it is allowed as long as nothing was booked (see {@link CostRevaluationDocumentHandler#voidIt}).
	 */
	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		if (!IDocument.STATUS_Completed.equals(optionsCtx.getDocStatus()))
		{
			return;
		}

		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());
		docActions.add(IDocument.ACTION_Void);
		optionsCtx.setDocActions(ImmutableSet.copyOf(docActions));
	}
}
