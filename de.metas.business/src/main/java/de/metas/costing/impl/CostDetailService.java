package de.metas.costing.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.MAcctSchema;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandler;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyRate;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
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
public class CostDetailService implements ICostDetailService
{
	private static final Logger logger = LogManager.getLogger(CostDetailService.class);

	private final ICostDetailRepository costDetailsRepo;
	private final ICostElementRepository costElementRepo;
	private final ImmutableMap<CostingMethod, CostingMethodHandler> costingMethodHandlers;

	public CostDetailService(
			@NonNull final ICostDetailRepository costDetailsRepo,
			@NonNull final ICostElementRepository costElementRepo,
			@NonNull final List<CostingMethodHandler> costingMethodHandlers)
	{
		this.costDetailsRepo = costDetailsRepo;
		this.costElementRepo = costElementRepo;
		this.costingMethodHandlers = Maps.uniqueIndex(costingMethodHandlers, CostingMethodHandler::getCostingMethod);
		logger.info("Costing method handlers: {}", this.costingMethodHandlers);
	}

	@Override
	public CostResult createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final ImmutableList<CostDetailCreateResult> costElementResults = Stream.of(request)
				.flatMap(this::explodeAcctSchemas)
				.map(this::convertToAcctSchemaCurrency)
				.flatMap(this::explodeCostElements)
				.map(this::createCostDetailUsingHandler)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		if (costElementResults.isEmpty())
		{
			throw new AdempiereException("No costs created for " + request);
		}

		return createCostResult(costElementResults);
	}

	private CostResult createCostResult(final ImmutableList<CostDetailCreateResult> costElementResults)
	{
		final CostSegment costSegment = costElementResults
				.stream()
				.map(CostDetailCreateResult::getCostSegment)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one CostSegment found in " + costElementResults)));

		final ImmutableMap<CostElement, CostAmount> amountsByCostElement = costElementResults
				.stream()
				.collect(ImmutableMap.toImmutableMap(CostDetailCreateResult::getCostElement, CostDetailCreateResult::getAmt));

		return CostResult.builder()
				.costSegment(costSegment)
				.amounts(amountsByCostElement)
				.build();
	}

	private CostDetailCreateResult createCostDetailUsingHandler(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();
		final CostingMethodHandler costingMethodHandler = getCostingMethodHandler(costElement.getCostingMethod());
		return costingMethodHandler.createOrUpdateCost(request);
	}

	private CostDetailCreateRequest convertToAcctSchemaCurrency(final CostDetailCreateRequest request)
	{
		if (request.getAmt().signum() == 0)
		{
			return request;
		}

		final MAcctSchema as = MAcctSchema.get(request.getAcctSchemaId());
		final int acctCurrencyId = as.getC_Currency_ID();
		if (request.getAmt().getCurrencyId() == acctCurrencyId)
		{
			return request;
		}

		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final ICurrencyConversionContext conversionCtx = currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asTimestamp(request.getDate()),
				request.getCurrencyConversionTypeId(),
				request.getClientId(),
				request.getOrgId());
		final ICurrencyRate rate = currencyConversionBL.getCurrencyRate(conversionCtx, request.getAmt().getCurrencyId(), acctCurrencyId);
		final BigDecimal amtConv = rate.convertAmount(request.getAmt().getValue(), as.getCostingPrecision());

		return request.deriveByAmount(CostAmount.of(amtConv, acctCurrencyId));
	}

	@Override
	public void processIfCostImmediate(final I_M_CostDetail costDetail)
	{
		// assume already processed
	}

	@Override
	public void processAllForProduct(final int productId)
	{
		// assume already processed
	}

	@Override
	public void voidAndDeleteForDocument(final CostingDocumentRef documentRef)
	{
		costDetailsRepo.getAllForDocument(documentRef)
				.forEach(this::voidAndDelete);
	}

	private void voidAndDelete(final I_M_CostDetail costDetail)
	{
		if (costDetail.isChangingCosts())
		{
			final int costElementId = costDetail.getM_CostElement_ID();
			final CostElement costElement = costElementRepo.getById(costElementId);
			final CostDetailVoidRequest request = createCostDetailVoidRequest(costDetail);
			getCostingMethodHandler(costElement.getCostingMethod())
					.voidCosts(request);
		}

		costDetailsRepo.delete(costDetail);
	}

	private CostDetailVoidRequest createCostDetailVoidRequest(final I_M_CostDetail costDetail)
	{
		final int acctSchemaId = costDetail.getC_AcctSchema_ID();
		final I_C_AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).retrieveAcctSchemaById(acctSchemaId);
		final int costTypeId = acctSchema.getM_CostType_ID();

		final IProductBL productBL = Services.get(IProductBL.class);
		final int productId = costDetail.getM_Product_ID();
		final CostingLevel costingLevel = productBL.getCostingLevel(productId, acctSchema);
		final I_C_UOM productUOM = Services.get(IProductBL.class).getStockingUOM(productId);

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.productId(productId)
				.clientId(costDetail.getAD_Client_ID())
				.orgId(costDetail.getAD_Org_ID())
				.attributeSetInstanceId(costDetail.getM_AttributeSetInstance_ID())
				.build();

		final CostElement costElement = costElementRepo.getById(costDetail.getM_CostElement_ID());

		final CostAmount amt = CostAmount.of(costDetail.getAmt(), acctSchema.getC_Currency_ID());
		final Quantity qty = Quantity.of(costDetail.getQty(), productUOM);

		return CostDetailVoidRequest.builder()
				.costSegment(costSegment)
				.costElement(costElement)
				.amt(amt)
				.qty(qty)
				.build();
	}

	private Stream<CostDetailCreateRequest> explodeAcctSchemas(final CostDetailCreateRequest request)
	{
		return extractAcctSchemas(request)
				.stream()
				.map(LegacyAdapters::<I_C_AcctSchema, MAcctSchema> convertToPO)
				.filter(acctSchema -> acctSchema.isAllowPostingForOrg(request.getOrgId()))
				.map(acctSchema -> request.deriveByAcctSchemaId(acctSchema.getC_AcctSchema_ID()));
	}

	private List<I_C_AcctSchema> extractAcctSchemas(final CostDetailCreateRequest request)
	{
		if (request.isAllAcctSchemas())
		{
			return Services.get(IAcctSchemaDAO.class).retrieveClientAcctSchemas(request.getClientId());
		}
		else
		{
			I_C_AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).retrieveAcctSchemaById(request.getAcctSchemaId());
			return ImmutableList.of(acctSchema);
		}
	}

	private Stream<CostDetailCreateRequest> explodeCostElements(final CostDetailCreateRequest request)
	{
		return extractCostElements(request)
				.stream()
				.map(costElement -> request.deriveByCostElement(costElement));
	}

	private List<CostElement> extractCostElements(final CostDetailCreateRequest request)
	{
		if (request.isAllCostElements())
		{
			return costElementRepo.getMaterialCostingMethods(request.getClientId());
		}
		else
		{
			return ImmutableList.of(request.getCostElement());
		}
	}

	private CostingMethodHandler getCostingMethodHandler(final CostingMethod costingMethod)
	{
		final CostingMethodHandler costingMethodHandler = costingMethodHandlers.get(costingMethod);
		if (costingMethodHandler == null)
		{
			throw new AdempiereException("No " + CostingMethodHandler.class + " foudn for " + costingMethod);
		}
		return costingMethodHandler;
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final CostingMethod costingMethod, final int orderLineId)
	{
		return getCostingMethodHandler(costingMethod)
				.calculateSeedCosts(costSegment, orderLineId);
	}

	@Override
	public CostResult createReversalCostDetails(@NonNull final CostDetailReverseRequest request)
	{
		final Set<Integer> costElementIdsWithExistingCostDetails = costDetailsRepo
				.getAllForDocumentAndAcctSchemaId(request.getReversalDocumentRef(), request.getAcctSchemaId())
				.stream()
				.map(I_M_CostDetail::getM_CostElement_ID)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<CostDetailCreateResult> costElementResults = costDetailsRepo
				.getAllForDocumentAndAcctSchemaId(request.getInitialDocumentRef(), request.getAcctSchemaId())
				.stream()
				.filter(costDetail -> !costElementIdsWithExistingCostDetails.contains(costDetail.getM_CostElement_ID())) // not already created
				.map(costDetail -> createReversalCostDetail(costDetail, request))
				.collect(ImmutableList.toImmutableList());

		return createCostResult(costElementResults);
	}

	private CostDetailCreateResult createReversalCostDetail(final I_M_CostDetail costDetail, final CostDetailReverseRequest reversalRequest)
	{
		final CostElement costElement = costElementRepo.getById(costDetail.getM_CostElement_ID());
		final CostDetailCreateRequest request = createCostDetailCreateRequestFromReversalRequest(reversalRequest, costDetail);
		return getCostingMethodHandler(costElement.getCostingMethod())
				.createOrUpdateCost(request);
	}

	private final CostDetailCreateRequest createCostDetailCreateRequestFromReversalRequest(final CostDetailReverseRequest reversalRequest, final I_M_CostDetail costDetail)
	{
		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.loadOutOfTrx(reversalRequest.getAcctSchemaId(), I_C_AcctSchema.class);
		final CostAmount amt = CostAmount.of(costDetail.getAmt().negate(), acctSchema.getC_AcctSchema_ID());

		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(costDetail.getM_Product_ID());
		final Quantity qty = Quantity.of(costDetail.getQty().negate(), uom);

		return CostDetailCreateRequest.builder()
				.acctSchemaId(reversalRequest.getAcctSchemaId())
				.clientId(costDetail.getAD_Client_ID())
				.orgId(costDetail.getAD_Org_ID())
				.productId(costDetail.getM_Product_ID())
				.attributeSetInstanceId(costDetail.getM_AttributeSetInstance_ID())
				.documentRef(reversalRequest.getReversalDocumentRef())
				.initialDocumentRef(reversalRequest.getInitialDocumentRef())
				.qty(qty)
				.amt(amt)
				// .currencyConversionTypeId(currencyConversionTypeId) // N/A
				.date(reversalRequest.getDate())
				.description(reversalRequest.getDescription())
				.build();
	}

}
