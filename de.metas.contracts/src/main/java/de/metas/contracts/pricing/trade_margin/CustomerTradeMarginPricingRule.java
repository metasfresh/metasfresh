/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.contracts.pricing.trade_margin;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceService;
import de.metas.contracts.commission.commissioninstance.services.CommissionPointsService;
import de.metas.contracts.commission.commissioninstance.services.CreateForecastCommissionInstanceRequest;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

public class CustomerTradeMarginPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CustomerTradeMarginPricingRule.class);

	private final CustomerTradeMarginService customerTradeMarginService = SpringContextHolder.instance.getBean(CustomerTradeMarginService.class);

	private final CommissionInstanceService commissionInstanceService = SpringContextHolder.instance.getBean(CommissionInstanceService.class);

	@Override
	public boolean applies(IPricingContext pricingCtx, IPricingResult result)
	{
		if (!result.isCalculated())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying due to missing calculated price!");
			return false;
		}
		final BPartnerId customerId = pricingCtx.getBPartnerId();

		final BPartnerId salesRepId = Services.get(IBPartnerBL.class).getBPartnerSalesRepId(customerId);

		if (salesRepId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying due to missing sales rep!");
			return false;
		}

		final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria = CustomerTradeMarginSearchCriteria.builder()
				.customerId(customerId)
				.salesRepId(salesRepId)
				.requestedDate(pricingCtx.getPriceDate())
				.build();

		if (!customerTradeMarginService.getCustomerTradeMarginForCriteria(customerTradeMarginSearchCriteria).isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying due to missing customer trade margin settings!");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(IPricingContext pricingCtx, IPricingResult result)
	{
		final BPartnerId customerId = pricingCtx.getBPartnerId();

		final BPartnerId salesRepId = Services.get(IBPartnerBL.class).getBPartnerSalesRepId(customerId);

		final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria = CustomerTradeMarginSearchCriteria.builder()
				.customerId(customerId)
				.salesRepId(salesRepId)
				.requestedDate(pricingCtx.getPriceDate())
				.build();

		final Optional<CustomerTradeMarginSettings> customerTradeMarginSettings = customerTradeMarginService.getCustomerTradeMarginForCriteria(customerTradeMarginSearchCriteria);

		if (!customerTradeMarginSettings.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying due to missing customer trade margin settings!");
			return;
		}

		final ProductPrice priceBeforeApplyingRule = ProductPrice
				.builder()
				.productId(pricingCtx.getProductId())
				.money(Money.of(result.getPriceStd(), result.getCurrencyId()))
				.uomId(result.getPriceUomId())
				.build();

		final Optional<CommissionInstance> forecastCommissionInstance = createForecastCommissionInstanceForOneQtyInPriceUOM(pricingCtx, priceBeforeApplyingRule, salesRepId, customerId);

		if (!forecastCommissionInstance.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying! Forecast commission instance couldn't be created!");
			return;
		}


		final Map<SalesCommissionShare, CommissionPoints> share2TradedCommissionPoints = customerTradeMarginService
				.getTradedCommissionPointsFor(
						customerTradeMarginSettings.get(),
						forecastCommissionInstance.get().getShares());

		if (share2TradedCommissionPoints.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying! tradedCommissionPointsPerPriceUOM couldn't be calculated");
			return;
		}

		Money customerTradeMarginPerPriceUOMSum = Money.zero(result.getCurrencyId());

		for (final Entry<SalesCommissionShare, CommissionPoints> shareAndPoints : share2TradedCommissionPoints.entrySet())
		{
			final SalesCommissionShare share = shareAndPoints.getKey();
			try (final MDCCloseable shareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, share.getId()))
			{
				final CommissionContract salesRepCommissionContract = share.getContract();
				final CommissionPoints tradedCommissionPointsPerPriceUOM = shareAndPoints.getValue();

				final CommissionPointsService commissionPointsService = SpringContextHolder.instance.getBean(CommissionPointsService.class);
				final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

				final Optional<Money> tradedCommissionPointsValue = commissionPointsService
						.getCommissionPointsValue(
								tradedCommissionPointsPerPriceUOM,
								salesRepCommissionContract.getId(),
								pricingCtx.getPriceDate());

				if (!tradedCommissionPointsValue.isPresent())
				{
					Loggables.withLogger(logger, Level.DEBUG).addLog("Not applying! tradedCommissionPoints monetary amount couldn't be calculated");
					return;
				}

				final Money customerTradeMarginPerPriceUOM = moneyService
						.convertMoneyToCurrency(tradedCommissionPointsValue.get(), result.getCurrencyId());

				customerTradeMarginPerPriceUOMSum = customerTradeMarginPerPriceUOMSum.add(customerTradeMarginPerPriceUOM);
			}
		}

		result.setBaseCommissionPointsPerPriceUOM(forecastCommissionInstance.get().getCurrentTriggerData().getForecastedPoints().toBigDecimal());
		result.setTradedCommissionPercent(Percent.of(customerTradeMarginSettings.get().getMarginPercent()));

		result.setPriceStd(priceBeforeApplyingRule.toBigDecimal().subtract(customerTradeMarginPerPriceUOMSum.toBigDecimal()));
		result.setCalculated(true);

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Price before applying rule: {} currencyID: {}, Price after applying rule: {} currencyID :{}",
						priceBeforeApplyingRule.toBigDecimal(), priceBeforeApplyingRule.getCurrencyId().getRepoId(),
						result.getPriceStd(), result.getCurrencyId().getRepoId());
	}

	private Optional<CommissionInstance> createForecastCommissionInstanceForOneQtyInPriceUOM(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final ProductPrice productPrice,
			@NonNull final BPartnerId salesRepId,
			@NonNull final BPartnerId customerId)
	{
		final CreateForecastCommissionInstanceRequest createForecastCommissionPerPriceUOMReq = CreateForecastCommissionInstanceRequest
				.builder()
				// we need the commission points per one qty of product in pricing UOM
				.forecastQty(Quantitys.create(BigDecimal.ONE, productPrice.getUomId()))
				.productPrice(productPrice)
				.customerId(customerId)
				.salesRepId(salesRepId)
				.dateOrdered(pricingCtx.getPriceDate())
				.productId(pricingCtx.getProductId())
				.build();

		final ImmutableList<CommissionInstance> commissionInstances = commissionInstanceService.getCommissionInstanceFor(createForecastCommissionPerPriceUOMReq);
		if (commissionInstances.isEmpty())
		{
			return Optional.empty();
		}

		if (commissionInstances.size() > 1)
		{
			throw new AdempiereException("With customer trade margin, only one commissionInstance for a sales rep is allowed")
					.appendParametersToMessage()
					.setParameter("request", createForecastCommissionPerPriceUOMReq)
					.setParameter("resultingCommissionInstances", commissionInstances);
		}

		return Optional.of(commissionInstances.get(0));
	}
}
