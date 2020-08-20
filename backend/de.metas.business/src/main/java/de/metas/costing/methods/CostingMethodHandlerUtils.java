package de.metas.costing.methods;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.MoveCostsRequest;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
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
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final ICostDetailService costDetailsService;
	private final ICurrentCostsRepository currentCostsRepo;

	public CostingMethodHandlerUtils(
			@NonNull final CurrencyRepository currenciesRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailService costDetailsService)
	{
		this.currenciesRepo = currenciesRepo;
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsService = costDetailsService;
	}

	public QuantityUOMConverter getQuantityUOMConverter()
	{
		return uomConversionBL;
	}

	public ProductPrice convertToUOM(final ProductPrice costPrice, final UomId uomId)
	{
		final CurrencyPrecision precision = currenciesRepo.getCostingPrecision(costPrice.getCurrencyId());
		return uomConversionBL.convertProductPriceToUom(costPrice, uomId, precision);
	}

	public AcctSchema getAcctSchemaById(final AcctSchemaId acctSchemaId)
	{
		return acctSchemaRepo.getById(acctSchemaId);
	}

	public CostSegmentAndElement extractCostSegmentAndElement(final CostDetailCreateRequest request)
	{
		return costDetailsService.extractCostSegmentAndElement(request);
	}

	public CostSegmentAndElement extractOutboundCostSegmentAndElement(@NonNull final MoveCostsRequest request)
	{
		return costDetailsService.extractOutboundCostSegmentAndElement(request);
	}

	public CostSegmentAndElement extractInboundCostSegmentAndElement(@NonNull final MoveCostsRequest request)
	{
		return costDetailsService.extractInboundCostSegmentAndElement(request);
	}

	public CostDetailCreateResult createCostDetailRecordWithChangedCosts(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostDetailPreviousAmounts previousCosts)
	{
		return costDetailsService.createCostDetailRecordWithChangedCosts(request, previousCosts);
	}

	public CostDetailCreateResult createCostDetailRecordNoCostsChanged(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostDetailPreviousAmounts currentCosts)
	{
		return costDetailsService.createCostDetailRecordNoCostsChanged(request, currentCosts);
	}

	public CostDetailCreateResult toCostDetailCreateResult(final CostDetail costDetail)
	{
		return costDetailsService.toCostDetailCreateResult(costDetail);
	}

	protected final Optional<CostDetail> getExistingCostDetail(final CostDetailCreateRequest request)
	{
		return costDetailsService.getExistingCostDetail(request);
	}

	public final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegmentAndElement costSegmentAndElement = extractCostSegmentAndElement(request);
		return getCurrentCost(costSegmentAndElement);
	}

	public final CurrentCost getCurrentCost(final CostDetail costDetail)
	{
		final CostSegmentAndElement costSegmentAndElement = costDetailsService.extractCostSegmentAndElement(costDetail);
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
		final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		if (CurrencyId.equals(amt.getCurrencyId(), acctCurrencyId))
		{
			return amt;
		}

		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(request)
				.withPrecision(acctSchema.getCosting().getCostingPrecision());

		final CurrencyConversionResult result = convert(
				conversionCtx,
				amt.toMoney(),
				acctCurrencyId);

		return CostAmount.of(result.getAmount(), acctCurrencyId);
	}

	public CurrencyConversionResult convert(CurrencyConversionContext conversionCtx, Money price, CurrencyId acctCurencyId)
	{
		return currencyBL.convert(conversionCtx, price, acctCurencyId);
	}

	private CurrencyConversionContext createCurrencyConversionContext(final CostDetailCreateRequest request)
	{
		return createCurrencyConversionContext(
				request.getDate(),
				request.getCurrencyConversionTypeId(),
				request.getClientId(),
				request.getOrgId());
	}

	public CurrencyConversionContext createCurrencyConversionContext(
			final LocalDate dateAcct,
			final CurrencyConversionTypeId conversionTypeId,
			final ClientId clientId,
			final OrgId orgId)
	{
		return currencyBL.createCurrencyConversionContext(
				dateAcct,
				conversionTypeId,
				clientId,
				orgId);
	}

	public Stream<CostDetail> streamAllCostDetailsAfter(final CostDetail costDetail)
	{
		return costDetailsService.streamAllCostDetailsAfter(costDetail);
	}
}
