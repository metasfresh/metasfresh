package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_CostDetail;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface ICostDetailService extends ISingletonService
{

	void createCostDetail(CostDetailCreateRequest request);

	void reverseAndDeleteForDocument(CostingDocumentRef documentRef);

	void reversePartialQty(CostDetailQuery query, BigDecimal qty);

	void process(I_M_CostDetail costDetail);

	void processIfCostImmediate(I_M_CostDetail costDetail);

	void processAllForProduct(int productId);

	boolean isDelta(I_M_CostDetail costDetail);

	void onCostDetailDeleted(I_M_CostDetail costDetail);

	/** @return seed cost or null */
	BigDecimal calculateSeedCosts(CostSegment costSegment, String costingMethod, final int orderLineId);

}
