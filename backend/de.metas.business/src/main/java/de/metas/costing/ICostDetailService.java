package de.metas.costing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public interface ICostDetailService
{
	boolean hasCostDetailsForProductId(ProductId productId);

	Optional<CostDetail> getExistingCostDetail(CostDetailCreateRequest request);

	Stream<CostDetail> streamAllCostDetailsAfter(CostDetail costDetail);

	List<CostDetail> getAllForDocument(CostingDocumentRef documentRef);

	List<CostDetail> getAllForDocumentAndAcctSchemaId(CostingDocumentRef documentRef, AcctSchemaId acctSchemaId);

	CostDetailCreateResult toCostDetailCreateResult(CostDetail costDetail);

	CostSegmentAndElement extractCostSegmentAndElement(CostDetail costDetail);

	CostSegmentAndElement extractCostSegmentAndElement(CostDetailCreateRequest request);

	CostSegmentAndElement extractOutboundCostSegmentAndElement(MoveCostsRequest request);

	CostSegmentAndElement extractInboundCostSegmentAndElement(MoveCostsRequest request);

	CostDetailCreateResult createCostDetailRecordNoCostsChanged(
			@NonNull CostDetailCreateRequest request,
			@NonNull CostDetailPreviousAmounts previousCosts);

	CostDetailCreateResult createCostDetailRecordWithChangedCosts(
			@NonNull CostDetailCreateRequest request,
			@NonNull CostDetailPreviousAmounts previousCosts);

	CostDetail create(CostDetailBuilder costDetailBuilder);

	void delete(CostDetail costDetail);

}
