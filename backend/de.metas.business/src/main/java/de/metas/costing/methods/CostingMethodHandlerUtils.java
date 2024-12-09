package de.metas.costing.methods;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
<<<<<<< HEAD
=======
import de.metas.common.util.CoalesceUtil;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
<<<<<<< HEAD
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostPrice;
=======
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailPreviousAmounts;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
import de.metas.organization.OrgId;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
<<<<<<< HEAD
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
=======
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	public CostPrice convertToUOM(
			@NonNull final CostPrice costPrice,
			@NonNull final UomId targetUomId,
			@NonNull final ProductId productId
	)
	{
		return costPrice.convertAmounts(targetUomId, costAmount -> {
			final ProductPrice productPrice = ProductPrice.builder()
					.productId(productId)
					.uomId(costPrice.getUomId())
					.money(costAmount.toMoney())
					.build();
			final ProductPrice productPriceConv = convertToUOM(productPrice, targetUomId);
			return CostAmount.ofProductPrice(productPriceConv);
		});
	}

	@NonNull
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public Quantity convertToUOM(
			@NonNull final Quantity qty,
			@NonNull final UomId targetUomId,
			@NonNull final ProductId productId
	)
	{
		return uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
	}

<<<<<<< HEAD
=======
	@NonNull
	public UnaryOperator<Quantity> convertQuantityToUOM(
			@NonNull final UomId targetUomId,
			@NonNull final ProductId productId
	)
	{
		return qty -> uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	protected final Optional<CostDetail> getExistingCostDetail(final CostDetailCreateRequest request)
	{
		return costDetailsService.getExistingCostDetail(request);
=======
	public CostDetailCreateResultsList toCostDetailCreateResultsList(final Collection<CostDetail> costDetails)
	{
		return costDetailsService.toCostDetailCreateResultsList(costDetails);
	}

	public List<CostDetail> getExistingCostDetails(final CostDetailCreateRequest request)
	{
		return costDetailsService.getExistingCostDetails(request);
	}

	public List<CostDetail> updateDateAcct(@NonNull final Collection<CostDetail> costDetails, @NonNull final Instant newDateAcct)
	{
		return costDetails.stream()
				.map(costDetail -> costDetailsService.updateDateAcct(costDetail, newDateAcct))
				.collect(Collectors.toList());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public final CostPrice getCurrentCostPrice(final CostDetailCreateRequest request)
	{
		return getCurrentCost(request).getCostPrice();
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public final void saveCurrentCost(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}

	public CostAmount convertToAcctSchemaCurrency(
			final CostAmount amt,
			final CostDetailCreateRequest request)
	{
<<<<<<< HEAD
		final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		if (CurrencyId.equals(amt.getCurrencyId(), acctCurrencyId))
		{
			return amt;
		}

<<<<<<< HEAD
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(request)
=======
		final CurrencyConversionContext conversionCtx = conversionCtxSupplier.get()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.withPrecision(acctSchema.getCosting().getCostingPrecision());

		final CurrencyConversionResult result = convert(
				conversionCtx,
				amt.toMoney(),
				acctCurrencyId);

<<<<<<< HEAD
		return CostAmount.of(result.getAmount(), acctCurrencyId);
=======
		return CostAmount.of(result.getAmount(), acctCurrencyId, result.getSourceAmount(), result.getSourceCurrencyId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@NonNull
	public CurrencyConversionResult convert(
			final CurrencyConversionContext conversionCtx,
			final Money price,
			final CurrencyId acctCurrencyId)
	{
		return currencyBL.convert(conversionCtx, price, acctCurrencyId);
	}

<<<<<<< HEAD
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
=======
	private CurrencyConversionContext getCurrencyConversionContext(final CostDetailCreateRequest request)
	{
		final CurrencyConversionContext currencyConversionContext = request.getCurrencyConversionContext();
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> currencyConversionContext,
				() -> currencyBL.createCurrencyConversionContext(
						request.getDate(),
						(CurrencyConversionTypeId)null,
						request.getClientId(),
						request.getOrgId()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
