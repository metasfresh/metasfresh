package de.metas.costing.methods;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
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
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

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

	@NonNull
	public ProductPrice convertToUOM(
			@NonNull final ProductPrice costPrice,
			@NonNull final UomId uomId)
	{
		final CurrencyPrecision precision = currenciesRepo.getCostingPrecision(costPrice.getCurrencyId());
		return uomConversionBL.convertProductPriceToUom(costPrice, uomId, precision);
	}

	@NonNull
	public Quantity convertToUOM(
			@NonNull final Quantity qty,
			@NonNull final UomId targetUomId,
			@NonNull final ProductId productId
	)
	{
		return uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
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

	public List<CostDetail> getExistingCostDetails(final CostDetailCreateRequest request)
	{
		return costDetailsService.getExistingCostDetails(request);
	}

	public CostDetail updateDateAcct(@NonNull final CostDetail costDetail, @NonNull final Instant newDateAcct)
	{
		return costDetailsService.updateDateAcct(costDetail, newDateAcct);
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

	public final void saveCurrentCost(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}

	public CostAmount convertToAcctSchemaCurrency(
			final CostAmount amt,
			final CostDetailCreateRequest request)
	{
		final AcctSchemaId acctSchemaId = request.getAcctSchemaId();

		return convertToAcctSchemaCurrency(
				amt,
				() -> getCurrencyConversionContext(request),
				acctSchemaId);
	}

	public CostAmount convertToAcctSchemaCurrency(
			@NonNull final CostAmount amt,
			@NonNull final Supplier<CurrencyConversionContext> conversionCtxSupplier,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final AcctSchema acctSchema = getAcctSchemaById(acctSchemaId);
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		if (CurrencyId.equals(amt.getCurrencyId(), acctCurrencyId))
		{
			return amt;
		}

		final CurrencyConversionContext conversionCtx = conversionCtxSupplier.get()
				.withPrecision(acctSchema.getCosting().getCostingPrecision());

		final CurrencyConversionResult result = convert(
				conversionCtx,
				amt.toMoney(),
				acctCurrencyId);

		return CostAmount.of(result.getAmount(), acctCurrencyId, result.getSourceAmount(), result.getSourceCurrencyId());
	}

	@NonNull
	public CurrencyConversionResult convert(
			final CurrencyConversionContext conversionCtx,
			final Money price,
			final CurrencyId acctCurrencyId)
	{
		return currencyBL.convert(conversionCtx, price, acctCurrencyId);
	}

	private CurrencyConversionContext getCurrencyConversionContext(final CostDetailCreateRequest request)
	{
		final CurrencyConversionContext currencyConversionContext = request.getCurrencyConversionContext();
		if (currencyConversionContext != null)
		{
			return currencyConversionContext;
		}
		else
		{
			return currencyBL.createCurrencyConversionContext(
					request.getDate(),
					(CurrencyConversionTypeId)null,
					request.getClientId(),
					request.getOrgId());
		}
	}
}
