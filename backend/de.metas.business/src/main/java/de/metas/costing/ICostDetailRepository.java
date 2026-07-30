package de.metas.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.product.ProductId;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

public interface ICostDetailRepository
{
	CostDetail create(CostDetail.CostDetailBuilder costDetailBuilder);

	CostDetail updateDateAcct(@NonNull CostDetail costDetail, @NonNull Instant newDateAcct);

	void delete(CostDetail costDetail);

	Optional<CostDetail> firstOnly(CostDetailQuery query);

	Stream<CostDetail> stream(CostDetailQuery query);

	/** @return true if at least one cost detail matches the query */
	boolean hasCostDetails(CostDetailQuery query);

	ImmutableList<CostDetail> list(@NonNull CostDetailQuery query);

	default ImmutableList<CostDetail> listByDocumentRef(@NonNull final CostingDocumentRef documentRef)
	{
		return list(CostDetailQuery.builder()
				.documentRef(documentRef)
				.orderBy(CostDetailQuery.OrderBy.ID_ASC)
				.build());
	}

	default ImmutableList<CostDetail> listByDocumentRefAndAcctSchemaId(
			@NonNull final CostingDocumentRef documentRef,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return list(CostDetailQuery.builder()
				.documentRef(documentRef)
				.acctSchemaId(acctSchemaId)
				.orderBy(CostDetailQuery.OrderBy.ID_ASC)
				.build());
	}

	boolean hasCostDetailsByProductId(ProductId productId);

	/**
	 * @return the earliest changing-costs {@code M_CostDetail} of the given segment+element dated strictly AFTER
	 * {@code asOfDate}, ordered by {@code DateAcct, M_CostDetail_ID}. Its {@code Prev_*} columns hold the state the cost
	 * element was in immediately before that movement — i.e. the state as of {@code asOfDate}.
	 * <p>
	 * Deliberately the same algorithm as the SQL function {@code getCurrentCostInfo}
	 * ({@code de.metas.acct.base/.../ddl/functions/getCurrentCost.sql}), so a point-in-time valuation read done in SQL and
	 * one done here agree by construction.
	 */
	Optional<CostDetail> getFirstChangingCostsDetailAfter(
			@NonNull CostSegmentAndElement costSegmentAndElement,
			@NonNull Instant asOfDate);

	/**
	 * @return the subset of {@code productIds} for which a completed {@code M_CostRevaluation} line has already written a
	 * cost detail on this {@code (acctSchemaId, costElementId)} — i.e. carries an {@code M_CostDetail} with
	 * {@code M_CostRevaluationLine_ID} set. This is a broad, source-agnostic signal: it fires for ANY completed
	 * cost-revaluation line on that element/product, regardless of {@code RevaluationSource} — NOT only a prior
	 * {@code CopyFromCostElement} switch.
	 */
	ImmutableSet<ProductId> retrieveProductIdsWithCostRevaluationSeed(
			@NonNull AcctSchemaId acctSchemaId,
			@NonNull CostElementId costElementId,
			@NonNull Set<ProductId> productIds);
}
