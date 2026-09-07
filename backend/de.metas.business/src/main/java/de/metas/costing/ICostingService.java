package de.metas.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.i18n.ExplainedOptional;
import de.metas.product.ProductId;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

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

public interface ICostingService
{
	CostElement getCostElementById(@NonNull CostElementId costElementId);

	CostDetailCreateResultsList createCostDetail(CostDetailCreateRequest request);

	ExplainedOptional<CostDetailCreateResultsList> createCostDetailOrEmpty(@NonNull CostDetailCreateRequest request);

	CostDetailCreateResultsList createReversalCostDetails(CostDetailReverseRequest request);

	ExplainedOptional<CostDetailCreateResultsList> createReversalCostDetailsOrEmpty(CostDetailReverseRequest request);

	MoveCostsResult moveCosts(MoveCostsRequest request);

	void voidAndDeleteForDocument(CostingDocumentRef documentRef);

	Optional<CostPrice> getCurrentCostPrice(
			CostSegment costSegment,
			CostingMethod costingMethod);

	CostsRevaluationResult revaluateCosts(@NonNull CostsRevaluationRequest request);

	Optional<CurrentCost> getCurrentCost(@NonNull CostSegmentAndElement costSegmentAndElement);

	/**
	 * Point-in-time read of a cost element's cost: the state it was in as of {@code asOfDate}.
	 * <p>
	 * The live {@code M_Cost} row only ever carries the LATEST state, so for a cut-off that lies in the past it yields the
	 * cost AFTER every movement booked since — not the cost AT the cut-off. Therefore the state is reconstructed from the
	 * {@code Prev_*} columns of the first changing-costs {@code M_CostDetail} dated after {@code asOfDate}: those columns
	 * hold exactly the state the element was in immediately before that movement. With no such detail, nothing moved after
	 * the cut-off and the live row IS the state as of {@code asOfDate}.
	 * <p>
	 * The boundary is strictly {@code >}, never {@code >=}: a {@code CopyFromCostElement} switch writes its own opening
	 * anchor dated exactly AT the cut-off, and that anchor must never count as a forward event — otherwise the switch would
	 * seed itself from its own anchor's {@code Prev_*} instead of from the source element's cost at the cut-off.
	 *
	 * @return the cost as of {@code asOfDate}, or empty if the cost element has neither a post-cut-off changing-costs
	 * detail nor a live {@code M_Cost} row for the segment.
	 */
	Optional<CostDetailPreviousAmounts> getCostAsOf(
			@NonNull CostSegmentAndElement costSegmentAndElement,
			@NonNull Instant asOfDate);

	/**
	 * Writes the {@code CopyFromCostElement} switch's opening anchor. For what the "anchor" IS and what it is for, see the
	 * canonical definition on {@code de.metas.costrevaluation.CostRevaluationService#createDetailsForCopyFromCostElement}.
	 */
	void seedCurrentCostFromOpening(
			@NonNull CostSegmentAndElement targetSegmentAndElement,
			@NonNull CostDetailPreviousAmounts opening,
			@NonNull Instant anchorDate,
			@NonNull CostRevaluationLineId lineId);

	void reverseSeededCurrentCost(
			@NonNull CostSegmentAndElement targetSegmentAndElement,
			@NonNull Instant cutoffDate,
			@NonNull CostRevaluationLineId lineId);

	/**
	 * @return the subset of {@code productIds} for which a completed {@code M_CostRevaluation} line has already written a
	 * cost detail on the target {@code (acctSchemaId, costElementId)} — regardless of {@code RevaluationSource}. This is a
	 * broad, source-agnostic signal (a prior {@code CopyFromCostElement} switch OR any other completed cost-revaluation
	 * line on that element/product), NOT restricted to a prior {@code CopyFromCostElement} switch.
	 */
	ImmutableSet<ProductId> retrieveProductIdsAlreadySeededOnCostElement(
			@NonNull AcctSchemaId acctSchemaId,
			@NonNull CostElementId costElementId,
			@NonNull Set<ProductId> productIds);
}
