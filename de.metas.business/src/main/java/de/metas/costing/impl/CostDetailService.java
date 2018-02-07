package de.metas.costing.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandler;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyRate;
import de.metas.logging.LogManager;
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
		return costingMethodHandler.createCost(request);
	}

	private CostDetailCreateRequest convertToAcctSchemaCurrency(final CostDetailCreateRequest request)
	{
		if (request.getAmt().signum() == 0)
		{
			return request;
		}

		final MAcctSchema as = MAcctSchema.get(Env.getCtx(), request.getAcctSchemaId());
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

		return request.toBuilder()
				.amt(CostAmount.of(amtConv, acctCurrencyId))
				.build();
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
	public void reverseAndDeleteForDocument(final CostingDocumentRef documentRef)
	{
		costDetailsRepo.getAllForDocument(documentRef)
				.forEach(this::reverseAndDelete);
	}

	private void reverseAndDelete(final I_M_CostDetail costDetail)
	{
		final int costElementId = costDetail.getM_CostElement_ID();
		final CostElement costElement = costElementRepo.getById(costElementId);
		getCostingMethodHandler(costElement.getCostingMethod())
				.beforeDelete(costDetail);

		costDetailsRepo.delete(costDetail);
	}

	private Stream<CostDetailCreateRequest> explodeAcctSchemas(final CostDetailCreateRequest request)
	{
		if (request.getAcctSchemaIdOrZero() > 0)
		{
			return Stream.of(request);
		}

		return Services.get(IAcctSchemaDAO.class)
				.retrieveClientAcctSchemas(request.getClientId())
				.stream()
				.map(LegacyAdapters::<I_C_AcctSchema, MAcctSchema> convertToPO)
				.filter(acctSchema -> acctSchema.isAllowPostingForOrg(request.getOrgId()))
				.map(acctSchema -> request.toBuilder()
						.acctSchemaId(acctSchema.getC_AcctSchema_ID())
						.build());
	}

	private Stream<CostDetailCreateRequest> explodeCostElements(final CostDetailCreateRequest request)
	{
		return extractCostElements(request)
				.stream()
				.map(costElement -> request.toBuilder()
						.costElement(costElement)
						.build());
	}

	private List<CostElement> extractCostElements(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();
		if (costElement != null)
		{
			return ImmutableList.of(costElement);
		}

		return costElementRepo.getMaterialCostingMethods(request.getClientId());
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

	private CostingDocumentRef extractDocumentRef(final I_M_CostDetail cd)
	{
		if (cd.getM_MatchPO_ID() > 0)
		{
			return CostingDocumentRef.ofMatchPOId(cd.getM_MatchPO_ID());
		}
		else if (cd.getM_MatchInv_ID() > 0)
		{
			if (cd.isSOTrx())
			{
				throw new AdempiereException("Sales invoice line not supported: " + cd);
			}
			return CostingDocumentRef.ofMatchInvoiceId(cd.getM_MatchInv_ID());
		}
		else if (cd.getM_InOutLine_ID() > 0)
		{
			if (!cd.isSOTrx())
			{
				throw new AdempiereException("Material receipt line not supported: " + cd);
			}
			return CostingDocumentRef.ofShipmentLineId(cd.getM_InOutLine_ID());
		}
		else if (cd.getM_MovementLine_ID() > 0)
		{
			return cd.isSOTrx() ? CostingDocumentRef.ofOutboundMovementLineId(cd.getM_MovementLine_ID()) : CostingDocumentRef.ofInboundMovementLineId(cd.getM_MovementLine_ID());
		}
		else if (cd.getM_InventoryLine_ID() > 0)
		{
			return CostingDocumentRef.ofInventoryLineId(cd.getM_InventoryLine_ID());
		}
		else if (cd.getM_ProductionLine_ID() > 0)
		{
			return CostingDocumentRef.ofProductionLineId(cd.getM_ProductionLine_ID());
		}
		else if (cd.getC_ProjectIssue_ID() > 0)
		{
			return CostingDocumentRef.ofProjectIssueId(cd.getC_ProjectIssue_ID());
		}
		else if (cd.getPP_Cost_Collector_ID() > 0)
		{
			return CostingDocumentRef.ofCostCollectorId(cd.getPP_Cost_Collector_ID());
		}
		else
		{
			throw new AdempiereException("Cannot extract " + CostingDocumentRef.class + " from " + cd);
		}
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final CostingMethod costingMethod, final int orderLineId)
	{
		return getCostingMethodHandler(costingMethod)
				.calculateSeedCosts(costSegment, orderLineId);
	}
}
