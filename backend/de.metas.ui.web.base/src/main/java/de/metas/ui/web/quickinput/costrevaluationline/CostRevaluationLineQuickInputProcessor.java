/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.quickinput.costrevaluationline;

import com.google.common.collect.ImmutableSet;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;

import java.math.BigDecimal;
import java.util.Set;

public class CostRevaluationLineQuickInputProcessor implements IQuickInputProcessor
{
	@NonNull private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final ICostRevaluationLineQuickInput costRevaluationLineQuickInput = quickInput.getQuickInputDocumentAs(ICostRevaluationLineQuickInput.class);

		final ProductId productId = ProductId.ofRepoId(costRevaluationLineQuickInput.getM_Product_ID());
		final BigDecimal newCostPrice = costRevaluationLineQuickInput.getNewCostPrice();

		final I_M_CostRevaluation costRevaluationRecord = quickInput.getRootDocumentAs(I_M_CostRevaluation.class);
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(costRevaluationRecord.getM_CostRevaluation_ID());

		final CostRevaluationLineId createdLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, newCostPrice);

		final DocumentId documentId = DocumentId.of(createdLineId.getRepoId());
		return ImmutableSet.of(documentId);
	}
}
