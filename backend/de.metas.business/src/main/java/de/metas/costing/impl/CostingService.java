package de.metas.costing.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.AggregatedCostPrice;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICostingService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.methods.CostingMethodHandler;
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
import de.metas.util.Services;
import lombok.NonNull;

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

@Component
public class CostingService implements ICostingService
{
	private static final Logger logger = LogManager.getLogger(CostingService.class);

	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final ICostDetailRepository costDetailsRepo;
	private final ICostElementRepository costElementsRepo;
	private final ICurrentCostsRepository currentCostsRepo;

	private final ImmutableSetMultimap<CostingMethod, CostingMethodHandler> costingMethodHandlers;

	public CostingService(
			@NonNull final ICostDetailRepository costDetailsRepo,
			@NonNull final ICostElementRepository costElementsRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final List<CostingMethodHandler> costingMethodHandlers)
	{
		this.costDetailsRepo = costDetailsRepo;
		this.costElementsRepo = costElementsRepo;
		this.currentCostsRepo = currentCostsRepo;
		this.costingMethodHandlers = costingMethodHandlers.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(CostingMethodHandler::getCostingMethod, Function.identity()));
		logger.info("Costing method handlers: {}", this.costingMethodHandlers);
	}

	private AcctSchema getAcctSchemaById(final AcctSchemaId acctSchemaId)
	{
		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		return acctSchemasRepo.getById(acctSchemaId);
	}

	private List<AcctSchema> getAllAcctSchemaByClientId(final ClientId clientId)
	{
		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		return acctSchemasRepo.getAllByClient(clientId);
	}

	@Override
	public AggregatedCostAmount createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final ImmutableList<CostDetailCreateResult> costElementResults = Stream.of(request)
				.flatMap(this::explodeAcctSchemas)
				.map(this::convertToAcctSchemaCurrency)
				.flatMap(this::explodeCostElements)
				.flatMap(this::createCostDetailUsingHandlersAndStream)
				.collect(ImmutableList.toImmutableList());

		if (costElementResults.isEmpty())
		{
			throw new AdempiereException("No costs created for " + request);
		}

		return createCostResult(costElementResults);
	}

	private AggregatedCostAmount createCostResult(final ImmutableList<CostDetailCreateResult> costElementResults)
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
	}

	private Stream<CostDetailCreateResult> createCostDetailUsingHandlersAndStream(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();
		return getCostingMethodHandlers(costElement.getCostingMethod(), request.getDocumentRef())
				.stream()
				.map(handler -> handler.createOrUpdateCost(request))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	private CostDetailCreateRequest convertToAcctSchemaCurrency(final CostDetailCreateRequest request)
	{
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

		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
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
	}

	@Override
	public void voidAndDeleteForDocument(final CostingDocumentRef documentRef)
	{
		costDetailsRepo.getAllForDocument(documentRef)
				.forEach(costDetail -> voidAndDelete(costDetail, documentRef));
	}

	private void voidAndDelete(final CostDetail costDetail, final CostingDocumentRef documentRef)
	{
		if (costDetail.isChangingCosts())
		{
			final CostElementId costElementId = costDetail.getCostElementId();
			final CostElement costElement = costElementsRepo.getById(costElementId);
			final CostDetailVoidRequest request = createCostDetailVoidRequest(costDetail);
			getCostingMethodHandlers(costElement.getCostingMethod(), documentRef)
					.forEach(handler -> handler.voidCosts(request));
		}

		costDetailsRepo.delete(costDetail);
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

		final CostAmount amt = costDetail.getAmt();
		final Quantity qty = costDetail.getQty();

		return CostDetailVoidRequest.builder()
				.costSegmentAndElement(costSegmentAndElement)
				.amt(amt)
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
		// FIXME: we need to handle manufacturing costs, where we have non-material cost elements!!!

		if (request.isAllCostElements())
		{
			return costElementsRepo.getMaterialCostingMethods(request.getClientId());
		}
		else
		{
			return ImmutableList.of(request.getCostElement());
		}
	}

	private Set<CostingMethodHandler> getCostingMethodHandlers(final CostingMethod costingMethod)
	{
		final Set<CostingMethodHandler> costingMethodHandlers = this.costingMethodHandlers.get(costingMethod);
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("No " + CostingMethodHandler.class.getName() + " found for " + costingMethod
					+ ". Available costing methods are: " + this.costingMethodHandlers.keySet());
		}
		return costingMethodHandlers;
	}

	private Set<CostingMethodHandler> getCostingMethodHandlers(final CostingMethod costingMethod, final CostingDocumentRef documentRef)
	{
		Set<CostingMethodHandler> allCostingMethodHandlers = getCostingMethodHandlers(costingMethod);
		final Set<CostingMethodHandler> costingMethodHandlers = allCostingMethodHandlers
				.stream()
				.filter(handler -> isHandledBy(handler, documentRef))
				.collect(ImmutableSet.toImmutableSet());
		if (costingMethodHandlers.isEmpty())
		{
			throw new AdempiereException("None of following costing helpers could handle " + documentRef + ": " + allCostingMethodHandlers);
		}
		return costingMethodHandlers;
	}

	private boolean isHandledBy(final CostingMethodHandler handler, final CostingDocumentRef documentRef)
	{
		final Set<String> handledTableNames = handler.getHandledTableNames();
		return handledTableNames.contains(CostingMethodHandler.ANY)
				|| handledTableNames.contains(documentRef.getTableName());
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(final CostSegment costSegment, final CostingMethod costingMethod, final OrderLineId orderLineId)
	{
		return getCostingMethodHandlers(costingMethod)
				.stream()
				.map(handler -> handler.calculateSeedCosts(costSegment, orderLineId))
				.filter(Predicates.notNull())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(CostAmount::add);
	}

	@Override
	public AggregatedCostAmount createReversalCostDetails(@NonNull final CostDetailReverseRequest request)
	{
		final Set<CostElementId> costElementIdsWithExistingCostDetails = costDetailsRepo
				.getAllForDocumentAndAcctSchemaId(request.getReversalDocumentRef(), request.getAcctSchemaId())
				.stream()
				.map(CostDetail::getCostElementId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<CostDetailCreateResult> costElementResults = costDetailsRepo
				.getAllForDocumentAndAcctSchemaId(request.getInitialDocumentRef(), request.getAcctSchemaId())
				.stream()
				.filter(costDetail -> !costElementIdsWithExistingCostDetails.contains(costDetail.getCostElementId())) // not already created
				.flatMap(costDetail -> createReversalCostDetailsAndStream(costDetail, request))
				.collect(ImmutableList.toImmutableList());
		if (costElementResults.isEmpty())
		{
			throw new AdempiereException("No costs created for " + request);
		}

		return createCostResult(costElementResults);
	}

	private Stream<CostDetailCreateResult> createReversalCostDetailsAndStream(final CostDetail costDetail, final CostDetailReverseRequest reversalRequest)
	{
		final CostElementId costElementId = costDetail.getCostElementId();
		final CostElement costElement = costElementsRepo.getById(costElementId);
		final CostDetailCreateRequest request = createCostDetailCreateRequestFromReversalRequest(reversalRequest, costDetail);
		return getCostingMethodHandlers(costElement.getCostingMethod(), request.getDocumentRef())
				.stream()
				.map(handler -> handler.createOrUpdateCost(request))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	private final CostDetailCreateRequest createCostDetailCreateRequestFromReversalRequest(final CostDetailReverseRequest reversalRequest, final CostDetail costDetail)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(reversalRequest.getAcctSchemaId())
				.clientId(costDetail.getClientId())
				.orgId(costDetail.getOrgId())
				.productId(costDetail.getProductId())
				.attributeSetInstanceId(costDetail.getAttributeSetInstanceId())
				.documentRef(reversalRequest.getReversalDocumentRef())
				.initialDocumentRef(reversalRequest.getInitialDocumentRef())
				.qty(costDetail.getQty().negate())
				.amt(costDetail.getAmt().negate())
				// .currencyConversionTypeId(currencyConversionTypeId) // N/A
				.date(reversalRequest.getDate())
				.description(reversalRequest.getDescription())
				.build();
	}

	@Override
	public Optional<CostPrice> getCurrentCostPrice(final CostSegment costSegment, final CostingMethod costingMethod)
	{
		return currentCostsRepo.getAggregatedCostPriceByCostSegmentAndCostingMethod(costSegment, costingMethod)
				.map(AggregatedCostPrice::getTotalPrice);
	}
}
