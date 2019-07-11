package de.metas.costing.methods;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
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

@Service
public class CostingMethodHandlerUtils
{
	private final IAcctSchemaDAO acctSchemaRepo = Services.get(IAcctSchemaDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final CurrencyRepository currenciesRepo;
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final ICostDetailRepository costDetailsRepo;
	private final ICurrentCostsRepository currentCostsRepo;

	public CostingMethodHandlerUtils(
			@NonNull final CurrencyRepository currenciesRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailRepository costDetailsRepo)
	{
		this.currenciesRepo = currenciesRepo;

		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsRepo = costDetailsRepo;
	}

	public CostSegment extractCostSegment(final CostDetail costDetail)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(costDetail.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(costDetail.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(costDetail.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(costDetail.getProductId())
				.clientId(costDetail.getClientId())
				.orgId(costDetail.getOrgId())
				.attributeSetInstanceId(costDetail.getAttributeSetInstanceId())
				.build();
	}

	private CostSegmentAndElement extractCostSegmentAndElement(final CostDetail costDetail)
	{
		return extractCostSegment(costDetail)
				.withCostElementId(costDetail.getCostElementId());
	}

	public CostSegmentAndElement extractCostSegmentAndElement(final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(request.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(request.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(request.getProductId())
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(request.getCostElementId())
				.build();
	}

	public final CostDetailCreateResult createCostDetailRecordWithChangedCosts(@NonNull final CostDetailCreateRequest request, @NonNull final CurrentCost previousCosts)
	{
		final CostDetail costDetail = costDetailsRepo.create(request.toCostDetailBuilder()
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(previousCosts)));

		return createCostDetailCreateResult(costDetail, request);
	}

	public CostDetailCreateResult createCostDetailRecordNoCostsChanged(@NonNull final CostDetailCreateRequest request)
	{
		final CostDetail costDetail = costDetailsRepo.create(request.toCostDetailBuilder()
				.changingCosts(false));

		return createCostDetailCreateResult(costDetail, request);
	}

	public CostDetailCreateResult createCostDetailCreateResult(final CostDetail costDetail, final CostDetailCreateRequest request)
	{
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(costDetail))
				.costElement(request.getCostElement())
				.amt(costDetail.getAmt())
				.qty(costDetail.getQty())
				.build();
	}

	protected final CostDetail getExistingCostDetailOrNull(final CostDetailCreateRequest request)
	{
		final CostDetailQuery costDetailQuery = extractCostDetailQuery(request);
		return costDetailsRepo.getCostDetailOrNull(costDetailQuery);
	}

	private static CostDetailQuery extractCostDetailQuery(final CostDetailCreateRequest request)
	{
		return CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.costElementId(request.getCostElementId()) // assume request's costing element is set
				.documentRef(request.getDocumentRef())
				// .productId(request.getProductId())
				// .attributeSetInstanceId(request.getAttributeSetInstanceId())
				.build();
	}

	public final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegmentAndElement costSegmentAndElement = extractCostSegmentAndElement(request);
		return getCurrentCost(costSegmentAndElement);
	}

	public final CurrentCost getCurrentCost(final CostDetail costDetail)
	{
		final CostSegmentAndElement costSegmentAndElement = extractCostSegmentAndElement(costDetail);
		return getCurrentCost(costSegmentAndElement);
	}

	public final CurrentCost getCurrentCost(final CostSegmentAndElement costSegmentAndElement)
	{
		return currentCostsRepo.getOrCreate(costSegmentAndElement);
	}

	public final CostPrice getCurrentCostPrice(final CostDetailCreateRequest request)
	{
		return getCurrentCost(request).getCostPrice();
	}

	public final void saveCurrentCost(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}

	public CostAmount convertToAcctSchemaCurrency(final CostAmount amt, final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(request.getAcctSchemaId());
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		if (CurrencyId.equals(amt.getCurrencyId(), acctCurrencyId))
		{
			return amt;
		}

		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(request)
				.withPrecision(acctSchema.getCosting().getCostingPrecision());

		final CurrencyConversionResult result = currencyBL.convert(
				conversionCtx,
				amt.getValue(),
				amt.getCurrencyId(),
				acctCurrencyId);

		return CostAmount.of(result.getAmount(), acctCurrencyId);
	}

	private CurrencyConversionContext createCurrencyConversionContext(final CostDetailCreateRequest request)
	{
		return currencyBL.createCurrencyConversionContext(
				request.getDate(),
				request.getCurrencyConversionTypeId(),
				request.getClientId(),
				request.getOrgId());
	}

	public Stream<CostDetail> streamAllCostDetailsAfter(final CostDetail costDetail)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(costDetail.getProductId(), costDetail.getAcctSchemaId());
		return costDetailsRepo.streamOrderedById(CostDetailQuery.builder()
				.acctSchemaId(costDetail.getAcctSchemaId())
				.costElementId(costDetail.getCostElementId())
				.productId(costDetail.getProductId())
				.attributeSetInstanceId(costingLevel.effectiveValueOrNull(costDetail.getAttributeSetInstanceId()))
				.clientId(costingLevel.effectiveValue(costDetail.getClientId()))
				.orgId(costingLevel.effectiveValueOrNull(costDetail.getOrgId()))
				.afterCostDetailId(costDetail.getId())
				.build());
	}

	public CurrencyPrecision getCostingPrecision(CurrencyId currencyId)
	{
		return currenciesRepo.getCostingPrecision(currencyId);
	}
}
