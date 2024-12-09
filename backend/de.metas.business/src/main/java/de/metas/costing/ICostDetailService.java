package de.metas.costing;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.product.ProductId;
import lombok.NonNull;

<<<<<<< HEAD
=======
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface ICostDetailService
{
<<<<<<< HEAD
	boolean hasCostDetailsForProductId(ProductId productId);

	Optional<CostDetail> getExistingCostDetail(CostDetailCreateRequest request);

	Stream<CostDetail> streamAllCostDetailsAfter(CostDetail costDetail);
=======
	CostDetail updateDateAcct(@NonNull CostDetail costDetail, @NonNull Instant newDateAcct);

	boolean hasCostDetailsForProductId(ProductId productId);

	List<CostDetail> getExistingCostDetails(CostDetailCreateRequest request);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	List<CostDetail> getAllForDocument(CostingDocumentRef documentRef);

	List<CostDetail> getAllForDocumentAndAcctSchemaId(CostingDocumentRef documentRef, AcctSchemaId acctSchemaId);

	CostDetailCreateResult toCostDetailCreateResult(CostDetail costDetail);

<<<<<<< HEAD
=======
	CostDetailCreateResultsList toCostDetailCreateResultsList(Collection<CostDetail> costDetails);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
	Stream<CostDetail> stream(@NonNull CostDetailQuery query);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
