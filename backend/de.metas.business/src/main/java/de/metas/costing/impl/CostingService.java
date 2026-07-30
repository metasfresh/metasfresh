package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Range;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.AggregatedCostPrice;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostsRevaluationRequest;
import de.metas.costing.CostsRevaluationResult;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICostingService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.costing.methods.CostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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

@Service
public class CostingService implements ICostingService
{
	private static final Logger logger = LogManager.getLogger(CostingService.class);

	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final CostingMethodHandlerUtils utils;
	private final ICostDetailService costDetailsService;
	private final ICostElementRepository costElementsRepo;
	private final ICurrentCostsRepository currentCostsRepo;

	private final ImmutableSetMultimap<CostingMethod, CostingMethodHandler> costingMethodHandlers;

	public CostingService(
			@NonNull final CostingMethodHandlerUtils utils,
			@NonNull final ICostDetailService costDetailsService,
			@NonNull final ICostElementRepository costElementsRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final List<CostingMethodHandler> costingMethodHandlers)
	{
		this.utils = utils;
		this.costDetailsService = costDetailsService;
		this.costElementsRepo = costElementsRepo;
		this.currentCostsRepo = currentCostsRepo;

		this.costingMethodHandlers = costingMethodHandlers
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						CostingMethodHandler::getCostingMethod,
						Function.identity()));
		logger.info("Costing method handlers: {}", this.costingMethodHandlers);
	}

	private AcctSchema getAcctSchemaById(final AcctSchemaId acctSchemaId)
	{
		return acctSchemasRepo.getById(acctSchemaId);
	}

	private List<AcctSchema> getAllAcctSchemaByClientId(final ClientId clientId)
	{
		return acctSchemasRepo.getAllByClient(clientId);
	}

	@Override
	public CostElement getCostElementById(@NonNull final CostElementId costElementId)
	{
		return costElementsRepo.getById(costElementId);
	}

	@Override
	public Optional<CurrentCost> getCurrentCost(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		return Optional.ofNullable(currentCostsRepo.getOrNull(costSegmentAndElement));
	}

	@Override
	public Optional<CostDetailPreviousAmounts> getCostAsOf(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final Instant asOfDate)
	{
		final Optional<CostDetail> firstDetailAfter = costDetailsService.getFirstChangingCostsDetailAfter(costSegmentAndElement, asOfDate);
		if (firstDetailAfter.isPresent())
		{
			// That detail's Prev_* IS the state as of asOfDate: the state right before the first movement booked after it.
			return firstDetailAfter.map(CostDetail::getPreviousAmounts);
		}

		// Nothing moved after asOfDate, so the live M_Cost row still carries the state as of asOfDate.
		return getCurrentCost(costSegmentAndElement).map(CostDetailPreviousAmounts::of);
	}

	@Override
	public CostDetailCreateResultsList createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		return createCostDetailOrEmpty(request).orElseThrow();
	}

	@Override
	public ExplainedOptional<CostDetailCreateResultsList> createCostDetailOrEmpty(@NonNull final CostDetailCreateRequest request)
	{
		final ImmutableList<CostDetailCreateResult> costElementResults = Stream.of(request)
				.flatMap(this::explodeAcctSchemas)
				.map(this::convertToAcctSchemaCurrency)
				.flatMap(this::explodeCostElements)
				.flatMap(this::createCostDetailUsingHandlersAndStream)
				.collect(ImmutableList.toImmutableList());

		if (costElementResults.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No costs created for " + request);
		}
		else
		{
			return ExplainedOptional.of(CostDetailCreateResultsList.ofList(costElementResults));
		}
	}

	private Stream<CostDetailCreateResult> createCostDetailUsingHandlersAndStream(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();
		return getCostingMethodHandlers(costElement.getCostingMethod(), request.getDocumentRef())
				.stream()
				.flatMap(handler -> {
					try
					{
						// a handler returning null for a no-op path must not NPE the pipeline
						final CostDetailCreateResultsList results = handler.createOrUpdateCost(request);
						return results != null ? results.stream() : Stream.empty();
					}
					catch (final Exception ex)
					{
						throw AdempiereException.wrapIfNeeded(ex)
								.setParameter("request", request)
								.appendParametersToMessage();
					}
				});
	}

	private CostDetailCreateRequest convertToAcctSchemaCurrency(final CostDetailCreateRequest request)
	{
		return request.withAmount(utils.convertToAcctSchemaCurrency(request.getAmt(), request));
	}

	@Override
	public void voidAndDeleteForDocument(final CostingDocumentRef documentRef)
	{
		costDetailsService.getAllForDocument(documentRef)
				.forEach(costDetail -> voidAndDelete(costDetail, documentRef));
	}

	private void voidAndDelete(
			final CostDetail costDetail,
			final CostingDocumentRef documentRef)
	{
		if (costDetail.isChangingCosts())
		{
			final CostElementId costElementId = costDetail.getCostElementId();
			final CostElement costElement = costElementsRepo.getById(costElementId);
			final CostDetailVoidRequest request = createCostDetailVoidRequest(costDetail);
			getCostingMethodHandlers(costElement.getCostingMethod(), documentRef)
					.forEach(handler -> handler.voidCosts(request));
		}

		costDetailsService.delete(costDetail);
	}

	private CostDetailVoidRequest createCostDetailVoidRequest(final CostDetail costDetail)
	{
		final AcctSchemaId acctSchemaId = costDetail.getAcctSchemaId();
		final AcctSchema acctSchema = getAcctSchemaById(acctSchemaId);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		final ProductId productId = costDetail.getProductId();
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);

		final CostSegmentAndElement costSegmentAndElement = CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.productId(productId)
				.clientId(costDetail.getClientId())
				.orgId(costDetail.getOrgId())
				.attributeSetInstanceId(costDetail.getAttributeSetInstanceId())
				.costElementId(costDetail.getCostElementId())
				.build();

		final Quantity qty = costDetail.getQty();

		return CostDetailVoidRequest.builder()
				.costSegmentAndElement(costSegmentAndElement)
				.amt(costDetail.getAmt())
				.qty(qty)
				.build();
	}

	private Stream<CostDetailCreateRequest> explodeAcctSchemas(final CostDetailCreateRequest request)
	{
		return streamAcctSchemas(request)
				.map(AcctSchema::getId)
				.map(request::withAcctSchemaId);
	}

	private Stream<AcctSchema> streamAcctSchemas(final CostDetailCreateRequest request)
	{
		if (request.isAllAcctSchemas())
		{
			return getAllAcctSchemaByClientId(request.getClientId())
					.stream()
					.filter(acctSchema -> acctSchema.isAllowPostingForOrg(request.getOrgId()));
		}
		else
		{
			final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
			if (!acctSchema.isAllowPostingForOrg(request.getOrgId()))
			{
				logger.warn("Accounting schema does not allow posting for given organization: {}", request);
				return Stream.empty();
			}
			return Stream.of(acctSchema);
		}
	}

	private Stream<CostDetailCreateRequest> explodeCostElements(final CostDetailCreateRequest request)
	{
		return extractCostElements(request)
				.stream()
				.map(request::withCostElement);
	}

	private List<CostElement> extractCostElements(final CostDetailCreateRequest request)
	{
		if (request.isExplicitCostElement())
		{
			return ImmutableList.of(request.getCostElement());
		}
		else if (request.getDocumentRef().isMatchInv())
		{
			return costElementsRepo.getByTypes(request.getClientId(), CostElementType.Material);
		}
		else if (request.isOutbound())
		{
			return costElementsRepo.getByTypes(request.getClientId(), CostElementType.Material, CostElementType.Overhead);
		}
		else // inbound
		{
			return costElementsRepo.getByTypes(request.getClientId(), CostElementType.Material);
		}
	}

	private List<CostElement> extractCostElements(final MoveCostsRequest request)
	{
		return request.isExplicitCostElement()
				? ImmutableList.of(Objects.requireNonNull(request.getCostElement()))
				: costElementsRepo.getByTypes(request.getClientId(), CostElementType.Material, CostElementType.Overhead);
	}

	private Set<CostingMethodHandler> getCostingMethodHandlers(@NonNull final CostingMethod costingMethod)
	{
		final Set<CostingMethodHandler> costingMethodHandlers = this.costingMethodHandlers.get(costingMethod);
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("No " + CostingMethodHandler.class.getName() + " found for " + costingMethod
					+ ". Available costing methods are: " + this.costingMethodHandlers.keySet());
		}
		return costingMethodHandlers;
	}

	private ImmutableSet<CostingMethodHandler> getCostingMethodHandlers(
			@NonNull final CostingMethod costingMethod,
			@NonNull final CostingDocumentRef documentRef)
	{
		final Set<CostingMethodHandler> allCostingMethodHandlers = getCostingMethodHandlers(costingMethod);
		final ImmutableSet<CostingMethodHandler> costingMethodHandlers = allCostingMethodHandlers
				.stream()
				.filter(handler -> isHandledBy(handler, documentRef))
				.collect(ImmutableSet.toImmutableSet());
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("None of following costing helpers could handle " + documentRef + ": " + allCostingMethodHandlers);
		}
		return costingMethodHandlers;
	}

	private CostingMethodHandler getSingleCostingMethodHandler(
			@NonNull final CostingMethod costingMethod,
			@NonNull final CostingDocumentRef documentRef)
	{
		CostingMethodHandler eligibleHandler = null;
		final Set<CostingMethodHandler> allCostingMethodHandlers = getCostingMethodHandlers(costingMethod);
		for (final CostingMethodHandler handler : allCostingMethodHandlers)
		{
			if (isHandledBy(handler, documentRef))
			{
				if (eligibleHandler == null)
				{
					eligibleHandler = handler;
				}
				else
				{
					throw new AdempiereException("More than one eligible handlers found for " + costingMethod + " and " + documentRef + ": " + eligibleHandler + ", " + handler);
				}
			}
		}

		if (eligibleHandler == null)
		{
			throw new AdempiereException("No eligible handler found for " + costingMethod + " and " + documentRef);
		}

		return eligibleHandler;
	}

	private static boolean isHandledBy(
			@NonNull final CostingMethodHandler handler,
			@NonNull final CostingDocumentRef documentRef)
	{
		final Set<String> handledTableNames = handler.getHandledTableNames();
		return handledTableNames.contains(documentRef.getTableName());
	}

	@Override
	public CostDetailCreateResultsList createReversalCostDetails(@NonNull final CostDetailReverseRequest reversalRequest)
	{
		return createReversalCostDetailsOrEmpty(reversalRequest).orElseThrow();
	}

	@Override
	public ExplainedOptional<CostDetailCreateResultsList> createReversalCostDetailsOrEmpty(@NonNull final CostDetailReverseRequest reversalRequest)
	{
		final List<CostDetail> initialDocCostDetails = costDetailsService.getAllForDocumentAndAcctSchemaId(reversalRequest.getInitialDocumentRef(), reversalRequest.getAcctSchemaId());
		if (initialDocCostDetails.isEmpty())
		{
			throw new AdempiereException("Initial document has no cost details: " + reversalRequest);
		}

		final ImmutableMap<CostElementId, CostDetail> existingCostDetails = costDetailsService
				.getAllForDocumentAndAcctSchemaId(reversalRequest.getReversalDocumentRef(), reversalRequest.getAcctSchemaId())
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						CostDetail::getCostElementId,
						costDetail -> costDetail));

		final ArrayList<CostDetailCreateResult> costDetailCreateResults = new ArrayList<>();

		for (final CostDetail initialDocCostDetail : initialDocCostDetails)
		{
			final CostElementId costElementId = initialDocCostDetail.getCostElementId();
			final CostDetail existingCostDetail = existingCostDetails.get(costElementId);
			if (existingCostDetail != null)
			{
				final CostDetailCreateResult result = utils.toCostDetailCreateResult(existingCostDetail);
				costDetailCreateResults.add(result);
			}
			else
			{
				final List<CostDetailCreateResult> results = createReversalCostDetails(initialDocCostDetail, reversalRequest);
				costDetailCreateResults.addAll(results);
			}
		}

		if (costDetailCreateResults.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No costs created for " + reversalRequest);
		}

		return ExplainedOptional.of(CostDetailCreateResultsList.ofList(costDetailCreateResults));
	}

	private List<CostDetailCreateResult> createReversalCostDetails(
			@NonNull final CostDetail costDetail,
			@NonNull final CostDetailReverseRequest reversalRequest)
	{
		final CostElementId costElementId = costDetail.getCostElementId();
		final CostElement costElement = costElementsRepo.getByIdIfExists(costElementId).orElse(null);
		if (costElement == null)
		{
			// cost element was disabled in meantime
			return ImmutableList.of();
		}

		final CostDetailCreateRequest request = toCostDetailCreateRequestFromReversalRequest(reversalRequest, costDetail, costElement);
		return getCostingMethodHandlers(costElement.getCostingMethod(), request.getDocumentRef())
				.stream()
				.flatMap(handler -> handler.createOrUpdateCost(request).stream())
				.collect(ImmutableList.toImmutableList());
	}

	private static CostDetailCreateRequest toCostDetailCreateRequestFromReversalRequest(
			@NonNull final CostDetailReverseRequest reversalRequest,
			@NonNull final CostDetail costDetail,
			@NonNull final CostElement costElement)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(reversalRequest.getAcctSchemaId())
				.clientId(costDetail.getClientId())
				.orgId(costDetail.getOrgId())
				.productId(costDetail.getProductId())
				.attributeSetInstanceId(costDetail.getAttributeSetInstanceId())
				.documentRef(reversalRequest.getReversalDocumentRef())
				.initialDocumentRef(reversalRequest.getInitialDocumentRef())
				.costElement(costElement)
				.amtType(costDetail.getAmtType())
				.amt(costDetail.getAmt().negate())
				.qty(costDetail.getQty().negate())
				// .currencyConversionTypeId(currencyConversionTypeId) // N/A
				.date(reversalRequest.getDate())
				.description(reversalRequest.getDescription())
				.build();
	}

	@Override
	public Optional<CostPrice> getCurrentCostPrice(
			final CostSegment costSegment,
			final CostingMethod costingMethod)
	{
		return currentCostsRepo.getAggregatedCostPriceByCostSegmentAndCostingMethod(costSegment, costingMethod)
				.map(AggregatedCostPrice::getTotalPrice);
	}

	@Override
	public MoveCostsResult moveCosts(@NonNull final MoveCostsRequest request)
	{
		MoveCostsResult result = null;

		final List<CostElement> costElements = extractCostElements(request);
		if (costElements.isEmpty())
		{
			throw new AdempiereException("No active cost elements found for " + request);
		}

		for (final CostElement costElement : costElements)
		{
			final MoveCostsRequest requestEffective = request.withCostElement(costElement);

			for (final CostingMethodHandler handler : getCostingMethodHandlers(costElement.getCostingMethod(), request.getOutboundDocumentRef()))
			{
				final MoveCostsResult partialResult = handler.createMovementCosts(requestEffective);

				result = result != null
						? result.add(partialResult)
						: partialResult;
			}
		}

		if (result == null)
		{
			throw new AdempiereException("No costs for " + request);
		}

		return result;
	}

	@Override
	public CostsRevaluationResult revaluateCosts(@NonNull CostsRevaluationRequest request)
	{
		final CostSegmentAndElement costSegmentAndElement = request.getCostSegmentAndElement();
		final Instant evaluationStartDate = request.getEvaluationStartDate();
		final CostAmount newCostPrice = request.getNewCostPrice();

		//
		// Fetch cost details for our cost segment, starting from evaluation start date
		final ImmutableList<CostDetail> costDetails = costDetailsService.stream(
						CostDetailQuery.builderFrom(costSegmentAndElement)
								.dateAcctRage(Range.atLeast(evaluationStartDate))
								.orderBy(CostDetailQuery.OrderBy.DATE_ACCT_ASC)
								.orderBy(CostDetailQuery.OrderBy.ID_ASC)
								.build())
				.collect(ImmutableList.toImmutableList());

		//
		// Restore current costs at the time before evaluation date
		final CostsRevaluationResult.CostsRevaluationResultBuilder result = CostsRevaluationResult.builder();
		final CurrentCost currentCost = currentCostsRepo.getOrCreateForUpdate(costSegmentAndElement);
		if (!costDetails.isEmpty())
		{
			final CostDetail firstCostDetail = costDetails.get(0);
			currentCost.setFrom(firstCostDetail.getPreviousAmounts());
		}
		//
		final CostsRevaluationResult.CurrentCostBeforeEvaluation currentCostBeforeEvaluation = CostsRevaluationResult.CurrentCostBeforeEvaluation.builder()
				.qty(currentCost.getCurrentQty())
				.costPriceOld(currentCost.getCostPrice().getOwnCostPrice())
				.costPriceNew(newCostPrice)
				.build();
		currentCost.setOwnCostPrice(newCostPrice);
		result.currentCostBeforeEvaluation(currentCostBeforeEvaluation);

		//
		// Iterate all cost details, calculate adjustments and update the current costs
		final CostingMethod costingMethod = costElementsRepo.getById(costSegmentAndElement.getCostElementId()).getCostingMethod();
		for (final CostDetail costDetail : costDetails)
		{
			// Cost details which were not changing the costs (so are there only for recording)
			// are not relevant for cost adjustment.
			if (!costDetail.isChangingCosts())
			{
				continue;
			}

			final CostingMethodHandler handler = getSingleCostingMethodHandler(costingMethod, costDetail.getDocumentRef());
			final CostDetailAdjustment costDetailAdjustment = handler.recalculateCostDetailAmountAndUpdateCurrentCost(costDetail, currentCost);
			result.costDetailAdjustment(costDetailAdjustment);
		}

		//
		result.currentCostAfterEvaluation(CostsRevaluationResult.CurrentCostAfterEvaluation.builder()
				.qty(currentCost.getCurrentQty())
				.costPriceComputed(currentCost.getCostPrice().getOwnCostPrice())
				.build());

		//
		return result.build();
	}

	/**
	 * {@code CopyFromCostElement} complete-time seed: sets the target element's {@code M_Cost} to {@code opening} and
	 * writes one zero-delta anchor {@code M_CostDetail} carrying the same {@code opening} as its {@code Prev_*}.
	 * <p>
	 * Canonical definition of the "anchor" term:
	 * {@code de.metas.costrevaluation.CostRevaluationService#createDetailsForCopyFromCostElement}.
	 */
	@Override
	public void seedCurrentCostFromOpening(
			@NonNull final CostSegmentAndElement targetSegmentAndElement,
			@NonNull final CostDetailPreviousAmounts opening,
			@NonNull final Instant anchorDate,
			@NonNull final CostRevaluationLineId lineId)
	{
		final CurrentCost currentCost = currentCostsRepo.getOrCreateForUpdate(targetSegmentAndElement);
		currentCost.setFrom(opening);
		currentCostsRepo.save(currentCost);

		final CostElement targetCostElement = getCostElementById(targetSegmentAndElement.getCostElementId());

		// The anchor carries the SAME `opening` as its Prev_*, so a later product_costs_recreate_from_date restores
		// M_Cost from it and reproduces exactly this state when there are no in-range transactions (recompute-survival).
		utils.createCostDetailRecordWithChangedCosts(
				CostDetailCreateRequest.builder()
						.costSegment(targetSegmentAndElement.toCostSegment())
						.costElement(targetCostElement)
						.documentRef(CostingDocumentRef.ofCostRevaluationLineId(lineId))
						.qty(opening.getQty().toZero())
						.amt(opening.getCumulatedAmt().toZero())
						.date(anchorDate)
						.build(),
				opening);
	}

	/**
	 * Returns the subset of {@code productIds} for which a completed {@code M_CostRevaluation} line has already written a
	 * cost detail on the target {@code (acctSchemaId, costElementId)} — regardless of {@code RevaluationSource}. Broad,
	 * source-agnostic signal (not restricted to a prior {@code CopyFromCostElement} switch); see
	 * {@link de.metas.costing.impl.CostDetailRepository#retrieveProductIdsWithCostRevaluationSeed} for the query.
	 */
	@Override
	public ImmutableSet<ProductId> retrieveProductIdsAlreadySeededOnCostElement(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final Set<ProductId> productIds)
	{
		return costDetailsService.retrieveProductIdsWithCostRevaluationSeed(acctSchemaId, costElementId, productIds);
	}

	/**
	 * Value-neutral, symmetric undo of {@link #seedCurrentCostFromOpening}: removes the opening-anchor
	 * {@code M_CostDetail} and resets the target element's {@code M_Cost} to its pre-switch (absent/zero) state.
	 * <p>
	 * Refused when a cost event dated strictly AFTER the cut-off has already built on the seed — the moving
	 * average has moved on, so restoring the opening would corrupt it. The seed's own anchor is dated AT the
	 * cut-off, so it is excluded by the strictly-after filter and never counts as a forward event.
	 */
	@Override
	public void reverseSeededCurrentCost(
			@NonNull final CostSegmentAndElement targetSegmentAndElement,
			@NonNull final Instant cutoffDate,
			@NonNull final CostRevaluationLineId lineId)
	{
		final boolean hasPostCutoffCostEvents = costDetailsService.hasCostDetails(
				CostDetailQuery.builderFrom(targetSegmentAndElement)
						.dateAcctRage(Range.greaterThan(cutoffDate))
						.build());
		if (hasPostCutoffCostEvents)
		{
			throw new AdempiereException("Cost revaluation cannot be reversed: a cost event after the cut-off date has"
					+ " already built on the seeded Moving Average Invoice opening balance for product "
					+ targetSegmentAndElement.getProductId() + ".");
		}

		// Remove the opening-anchor cost detail(s). The anchor is a changing-costs detail, so voidCosts runs, but it
		// is value-neutral here (anchor qty=0/amt=0 → addToCurrentQtyAndCumulate(0,0)); the explicit CurrentCost reset
		// below supersedes it regardless. Net effect: the anchor is deleted and the target's value is reset.
		voidAndDeleteForDocument(CostingDocumentRef.ofCostRevaluationLineId(lineId));

		// Reset the target element's current cost back to absent/zero (the pre-switch state). The row is kept
		// (not deleted), mirroring the seed which used getOrCreate.
		final CurrentCost currentCost = currentCostsRepo.getOrNull(targetSegmentAndElement);
		if (currentCost != null)
		{
			currentCost.setFrom(CostDetailPreviousAmounts.builder()
					.costPrice(currentCost.getCostPrice().withZeroOwnCostPrice().withZeroComponentsCostPrice())
					.qty(currentCost.getCurrentQty().toZero())
					.cumulatedAmt(currentCost.getCumulatedAmt().toZero())
					.cumulatedQty(currentCost.getCumulatedQty().toZero())
					.build());
			currentCostsRepo.save(currentCost);
		}
	}
}
