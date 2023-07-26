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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.PlainTriggerDocumentId;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceService;
import de.metas.contracts.commission.commissioninstance.services.CommissionPointsService;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * Allows a sales rep offer a lower price to a customer and in turn receive a lower commission.
 */
public class CustomerTradeMarginPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CustomerTradeMarginPricingRule.class);

	private final CustomerTradeMarginService customerTradeMarginService = SpringContextHolder.instance.getBean(CustomerTradeMarginService.class);

	private final CommissionInstanceService commissionInstanceService = SpringContextHolder.instance.getBean(CommissionInstanceService.class);

	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	@Override
	public boolean applies(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		if (!result.isCalculated())
		{
			loggable.addLog("applies - pricingResult.isCalculated=false -> return false");
			return false;
		}
		final BPartnerId customerId = pricingCtx.getBPartnerId();
		if (customerId == null)
		{
			loggable.addLog("applies - CustomerId is null; -> return false");
			return false;
		}

		final BPartnerId salesRepId = Services.get(IBPartnerBL.class).getBPartnerSalesRepId(customerId);
		if (salesRepId == null)
		{
			loggable.addLog("applies - SalesRepId is null for customerId={}; -> return false",customerId.getRepoId());
			return false;
		}

		final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria = CustomerTradeMarginSearchCriteria.builder()
				.customerId(customerId)
				.salesRepId(salesRepId)
				.requestedDate(pricingCtx.getPriceDate())
				.build();

		if (!customerTradeMarginService.getCustomerTradeMarginForCriteria(customerTradeMarginSearchCriteria).isPresent())
		{
			loggable.addLog("applies - missing customer trade margins; customerTradeMarginSearchCriteria={}; -> return false", customerTradeMarginSearchCriteria);
			return false;
		}

		loggable.addLog("applies  customerTradeMarginSearchCriteria={}; -> return true", customerTradeMarginSearchCriteria);
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

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		if (!customerTradeMarginSettings.isPresent())
		{
			loggable.addLog("calculate - missing customer trade margins; customerTradeMarginSearchCriteria={}; -> return", customerTradeMarginSearchCriteria);
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
			loggable.addLog("calculate - Not applying! Forecast commission instance couldn't be created!");
			return;
		}

		if (forecastCommissionInstance.get().hasSimulationContracts())
		{
			loggable.addLog("calculate - Not applying! Forecast commission instance contains simulation contracts!");
			return;
		}

		final Map<CommissionShare, CommissionPoints> share2TradedCommissionPoints = customerTradeMarginService
				.getTradedCommissionPointsFor(
						customerTradeMarginSettings.get(),
						forecastCommissionInstance.get().getShares());

		if (share2TradedCommissionPoints.isEmpty())
		{
			loggable.addLog("calculate - Not applying! tradedCommissionPointsPerPriceUOM couldn't be calculated");
			return;
		}

		Money customerTradeMarginPerPriceUOMSum = Money.zero(result.getCurrencyId());

		for (final Entry<CommissionShare, CommissionPoints> shareAndPoints : share2TradedCommissionPoints.entrySet())
		{
			final CommissionShare share = shareAndPoints.getKey();
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
					loggable.addLog("calculate - Not applying! tradedCommissionPoints monetary amount couldn't be calculated");
					return;
				}

				final Money customerTradeMarginPerPriceUOM = moneyService
						.convertMoneyToCurrency(tradedCommissionPointsValue.get(), result.getCurrencyId());

				customerTradeMarginPerPriceUOMSum = customerTradeMarginPerPriceUOMSum.add(customerTradeMarginPerPriceUOM);
			}
		}

		result.setBaseCommissionPointsPerPriceUOM(forecastCommissionInstance.get().getCurrentTriggerData().getForecastedBasePoints().toBigDecimal());
		result.setTradedCommissionPercent(Percent.of(customerTradeMarginSettings.get().getMarginPercent()));

		result.setPriceStd(priceBeforeApplyingRule.toBigDecimal().subtract(customerTradeMarginPerPriceUOMSum.toBigDecimal()));
		result.setCalculated(true);

		loggable
				.addLog("calculate - Price before applying rule: {} currencyID: {}, Price after applying rule: {} currencyID :{}",
						priceBeforeApplyingRule.toBigDecimal(), priceBeforeApplyingRule.getCurrencyId().getRepoId(),
						result.getPriceStd(), result.getCurrencyId().getRepoId());
	}

	private Optional<CommissionInstance> createForecastCommissionInstanceForOneQtyInPriceUOM(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final ProductPrice productPrice,
			@NonNull final BPartnerId salesRepId,
			@NonNull final BPartnerId customerId)
	{
		// we need the commission points per one qty of product in pricing UOM
		final Quantity forecastQty = Quantitys.create(BigDecimal.ONE, productPrice.getUomId());
		final Money forecastNetAmt = moneyService.multiply(forecastQty, productPrice);

		final CommissionTriggerDocument commissionTriggerDocument = CommissionTriggerDocument
				.builder()
				.triggerType(CommissionTriggerType.Plain)
				.orgId(pricingCtx.getOrgId())
				.id(PlainTriggerDocumentId.INSTANCE)
				.salesRepBPartnerId(salesRepId)
				.customerBPartnerId(customerId)
				.productId(pricingCtx.getProductId())
				.commissionDate(pricingCtx.getPriceDate())
				.updated(SystemTime.asInstant())
				.forecastCommissionPoints(CommissionPoints.of(forecastNetAmt.toBigDecimal()))
				.commissionPointsToInvoice(CommissionPoints.ZERO)
				.invoicedCommissionPoints(CommissionPoints.ZERO)
				.tradedCommissionPercent(Percent.ZERO)
				.build();

		final Optional<CommissionInstance> commissionInstance = commissionInstanceService.createCommissionInstance(commissionTriggerDocument);
		if (!commissionInstance.isPresent())
		{
			return Optional.empty();
		}
		return Optional.of(commissionInstance.get());
	}
}
