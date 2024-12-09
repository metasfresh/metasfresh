package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
<<<<<<< HEAD
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.AggregatedCostPrice;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
=======
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
import de.metas.costing.CostDetailQuery;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
<<<<<<< HEAD
=======
import de.metas.costing.CostElementType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
<<<<<<< HEAD
=======
import de.metas.costing.CostsRevaluationRequest;
import de.metas.costing.CostsRevaluationResult;
import de.metas.costing.CurrentCost;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICostingService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.costing.methods.CostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
<<<<<<< HEAD
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
=======
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
=======
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public AggregatedCostAmount createCostDetail(@NonNull final CostDetailCreateRequest request)
=======
	public CostElement getCostElementById(@NonNull final CostElementId costElementId)
	{
		return costElementsRepo.getById(costElementId);
	}

	@Override
	public CostDetailCreateResultsList createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		return createCostDetailOrEmpty(request).orElseThrow();
	}

	@Override
	public ExplainedOptional<CostDetailCreateResultsList> createCostDetailOrEmpty(@NonNull final CostDetailCreateRequest request)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final ImmutableList<CostDetailCreateResult> costElementResults = Stream.of(request)
				.flatMap(this::explodeAcctSchemas)
				.map(this::convertToAcctSchemaCurrency)
				.flatMap(this::explodeCostElements)
				.flatMap(this::createCostDetailUsingHandlersAndStream)
				.collect(ImmutableList.toImmutableList());

		if (costElementResults.isEmpty())
		{
<<<<<<< HEAD
			throw new AdempiereException("No costs created for " + request);
		}

		return toAggregatedCostAmount(costElementResults);
	}

	private static AggregatedCostAmount toAggregatedCostAmount(final List<CostDetailCreateResult> costElementResults)
	{
		Check.assumeNotEmpty(costElementResults, "costElementResults is not empty");

		final CostSegment costSegment = costElementResults
				.stream()
				.map(CostDetailCreateResult::getCostSegment)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one CostSegment found in " + costElementResults)));

		final Map<CostElement, CostAmount> amountsByCostElement = costElementResults
				.stream()
				.collect(Collectors.toMap(
						CostDetailCreateResult::getCostElement, // keyMapper
						CostDetailCreateResult::getAmt, // valueMapper
						CostAmount::add)); // mergeFunction

		return AggregatedCostAmount.builder()
				.costSegment(costSegment)
				.amounts(amountsByCostElement)
				.build();
=======
			return ExplainedOptional.emptyBecause("No costs created for " + request);
		}
		else
		{
			return ExplainedOptional.of(CostDetailCreateResultsList.ofList(costElementResults));
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private Stream<CostDetailCreateResult> createCostDetailUsingHandlersAndStream(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();
		return getCostingMethodHandlers(costElement.getCostingMethod(), request.getDocumentRef())
				.stream()
<<<<<<< HEAD
				.map(handler -> {
					try
					{
						return handler.createOrUpdateCost(request);
=======
				.flatMap(handler -> {
					try
					{
						return handler.createOrUpdateCost(request).stream();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					}
					catch (final Exception ex)
					{
						throw AdempiereException.wrapIfNeeded(ex)
								.setParameter("request", request)
								.appendParametersToMessage();
					}
<<<<<<< HEAD
				})
				.filter(Optional::isPresent)
				.map(Optional::get);
=======
				});
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private CostDetailCreateRequest convertToAcctSchemaCurrency(final CostDetailCreateRequest request)
	{
<<<<<<< HEAD
		if (request.getAmt().isZero())
		{
			return request;
		}

		final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		if (request.getAmt().getCurrencyId().equals(acctCurrencyId))
		{
			return request;
		}

		final CurrencyConversionContext conversionCtx = currencyConversionBL.createCurrencyConversionContext(
				request.getDate(),
				request.getCurrencyConversionTypeId(),
				request.getClientId(),
				request.getOrgId())
				.withPrecision(acctSchema.getCosting().getCostingPrecision());

		final CurrencyConversionResult amtConversionResult = currencyConversionBL.convert(
				conversionCtx,
				request.getAmt().getValue(),
				request.getAmt().getCurrencyId(),
				acctCurrencyId);

		return request.withAmount(CostAmount.of(amtConversionResult.getAmount(), acctCurrencyId));
=======
		return request.withAmount(utils.convertToAcctSchemaCurrency(request.getAmt(), request));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
		final CostAmount amt = costDetail.getAmt();
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final Quantity qty = costDetail.getQty();

		return CostDetailVoidRequest.builder()
				.costSegmentAndElement(costSegmentAndElement)
<<<<<<< HEAD
				.amt(amt)
=======
				.amt(costDetail.getAmt())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		return request.isAllCostElements()
				? getAllCostElements(request.getClientId())
				: ImmutableList.of(request.getCostElement());
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private List<CostElement> extractCostElements(final MoveCostsRequest request)
	{
<<<<<<< HEAD
		return request.isAllCostElements()
				? getAllCostElements(request.getClientId())
				: ImmutableList.of(Objects.requireNonNull(request.getCostElement()));
	}

	private List<CostElement> getAllCostElements(@NonNull final ClientId clientId)
	{
		// FIXME: we need to handle manufacturing costs, where we have non-material cost elements!!!
		return costElementsRepo.getMaterialCostingMethods(clientId);
	}

	private Set<CostingMethodHandler> getCostingMethodHandlers(final CostingMethod costingMethod)
=======
		return request.isExplicitCostElement()
				? ImmutableList.of(Objects.requireNonNull(request.getCostElement()))
				: costElementsRepo.getByTypes(request.getClientId(), CostElementType.Material, CostElementType.Overhead);
	}

	private Set<CostingMethodHandler> getCostingMethodHandlers(@NonNull final CostingMethod costingMethod)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final Set<CostingMethodHandler> costingMethodHandlers = this.costingMethodHandlers.get(costingMethod);
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("No " + CostingMethodHandler.class.getName() + " found for " + costingMethod
					+ ". Available costing methods are: " + this.costingMethodHandlers.keySet());
		}
		return costingMethodHandlers;
	}

<<<<<<< HEAD
	private Set<CostingMethodHandler> getCostingMethodHandlers(
			final CostingMethod costingMethod,
			final CostingDocumentRef documentRef)
	{
		final Set<CostingMethodHandler> allCostingMethodHandlers = getCostingMethodHandlers(costingMethod);
		final Set<CostingMethodHandler> costingMethodHandlers = allCostingMethodHandlers
=======
	private ImmutableSet<CostingMethodHandler> getCostingMethodHandlers(
			@NonNull final CostingMethod costingMethod,
			@NonNull final CostingDocumentRef documentRef)
	{
		final Set<CostingMethodHandler> allCostingMethodHandlers = getCostingMethodHandlers(costingMethod);
		final ImmutableSet<CostingMethodHandler> costingMethodHandlers = allCostingMethodHandlers
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.stream()
				.filter(handler -> isHandledBy(handler, documentRef))
				.collect(ImmutableSet.toImmutableSet());
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("None of following costing helpers could handle " + documentRef + ": " + allCostingMethodHandlers);
		}
		return costingMethodHandlers;
	}

<<<<<<< HEAD
	private boolean isHandledBy(
			final CostingMethodHandler handler,
			final CostingDocumentRef documentRef)
	{
		final Set<String> handledTableNames = handler.getHandledTableNames();
		return handledTableNames.contains(CostingMethodHandler.ANY)
				|| handledTableNames.contains(documentRef.getTableName());
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(
			final CostSegment costSegment,
			final CostingMethod costingMethod,
			final OrderLineId orderLineId)
	{
		return getCostingMethodHandlers(costingMethod)
				.stream()
				.map(handler -> handler.calculateSeedCosts(costSegment, orderLineId))
				.filter(Objects::nonNull)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(CostAmount::add);
	}

	@Override
	public AggregatedCostAmount createReversalCostDetails(@NonNull final CostDetailReverseRequest reversalRequest)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
						costDetail -> costDetail.getCostElementId(),
=======
						CostDetail::getCostElementId,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			throw new AdempiereException("No costs created for " + reversalRequest);
		}

		return toAggregatedCostAmount(costDetailCreateResults);
	}

	private ImmutableList<CostDetailCreateResult> createReversalCostDetails(
=======
			return ExplainedOptional.emptyBecause("No costs created for " + reversalRequest);
		}

		return ExplainedOptional.of(CostDetailCreateResultsList.ofList(costDetailCreateResults));
	}

	private List<CostDetailCreateResult> createReversalCostDetails(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.map(handler -> handler.createOrUpdateCost(request))
				.filter(Optional::isPresent)
				.map(Optional::get)
=======
				.flatMap(handler -> handler.createOrUpdateCost(request).stream())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.qty(costDetail.getQty().negate())
				.amt(costDetail.getAmt().negate())
=======
				.amtType(costDetail.getAmtType())
				.amt(costDetail.getAmt().negate())
				.qty(costDetail.getQty().negate())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======

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
		final CurrentCost currentCost = currentCostsRepo.getOrCreate(costSegmentAndElement);
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
