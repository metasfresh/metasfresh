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
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductPrice;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerTradeMarginService
{
	private static final Logger logger = LogManager.getLogger(CustomerTradeMarginService.class);
	private final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IUOMConversionBL conversionBL = Services.get(IUOMConversionBL.class);

	private final MoneyService moneyService;
	private final CustomerTradeMarginRepository customerTradeMarginRepository;

	public CustomerTradeMarginService(
			@NonNull final MoneyService moneyService,
			@NonNull final CustomerTradeMarginRepository customerTradeMarginRepository)
	{
		this.moneyService = moneyService;
		this.customerTradeMarginRepository = customerTradeMarginRepository;
	}

	@NonNull
	public CustomerTradeMargin getById(@NonNull final CustomerTradeMarginId customerTradeMarginId)
	{
		return customerTradeMarginRepository.getById(customerTradeMarginId);
	}

	@NonNull
	public Optional<ProductPrice> calculateSalesRepNetUnitPrice(@NonNull final ComputeSalesRepPriceRequest request)
	{
		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(request.getSalesRepId(), request.getSoTrx());

		if (pricingSystemId == null)
		{
			loggable.addLog("createSalesRepPricingResult - No pricingSystemId set for bpartner={}" + request.getSalesRepId());
			return Optional.empty();
		}

		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(request.getSalesRepId())
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.build();

		final I_C_BPartner_Location salesRepShipToLocation = bPartnerDAO.retrieveBPartnerLocation(bPartnerLocationQuery);

		if (salesRepShipToLocation == null)
		{
			loggable.addLog("createSalesRepPricingResult - No bpartnerSalesRepLocation found for bpartner={}" + request.getSalesRepId());
			return Optional.empty();
		}

		final LocationId locationId = LocationId.ofRepoId(salesRepShipToLocation.getC_Location_ID());
		final CountryId countryId = locationDAO.getCountryIdByLocationId(locationId);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, countryId, request.getSoTrx());

		if (priceListId == null)
		{
			loggable.addLog("createSalesRepPricingResult - No priceListId set for bpartner={}" + request.getSalesRepId());
			return Optional.empty();
		}

		final OrgId salesRepOrgId = OrgId.ofRepoId(salesRepShipToLocation.getAD_Org_ID());

		final IEditablePricingContext salesRepPricingCtx =
				pricingBL.createInitialContext(salesRepOrgId,
								request.getProductId(),
								request.getSalesRepId(),
								request.getQty(),
								request.getSoTrx())
						.setPricingSystemId(pricingSystemId)
						.setCountryId(countryId)
						.setPriceListId(priceListId)
						.setConvertPriceToContextUOM(false);

		final IPricingResult salesRepPriceResult = pricingBL.calculatePrice(salesRepPricingCtx);

		if (!salesRepPriceResult.isCalculated())
		{
			loggable.addLog("createSalesRepPricingResult - Price not calculated for bpartner={}" + request.getSalesRepId());
			return Optional.empty();
		}

		updatePricingResultToMatchUOM(salesRepPriceResult, request.getQty().getUomId());

		final Money salesRepNetUnitPriceWithoutTax = deductTaxes(salesRepOrgId,
													   salesRepShipToLocation,
													   request,
													   salesRepPriceResult);

		final Money salesRepNetUnitPrice = convertToCustomerCurrency(salesRepOrgId, request, salesRepNetUnitPriceWithoutTax);

		return Optional.of(ProductPrice.builder()
				.money(salesRepNetUnitPrice)
				.productId(salesRepPriceResult.getProductId())
				.uomId(salesRepPriceResult.getPriceUomId())
				.build());
	}

	@NonNull
	private Money deductTaxes(
			@NonNull final OrgId salesRepOrgId,
			@NonNull final I_C_BPartner_Location salesRepBillToLocation,
			@NonNull final ComputeSalesRepPriceRequest request,
			@NonNull final IPricingResult salesRepPricingResult)
	{
		final BPartnerLocationId salesRepBillToLocationId = BPartnerLocationId.ofRepoId(salesRepBillToLocation.getC_BPartner_ID(),
				salesRepBillToLocation.getC_BPartner_Location_ID());

		final BPartnerLocationAndCaptureId salesRepBillToLocationAndCapture = BPartnerLocationAndCaptureId.of(salesRepBillToLocationId,
				LocationId.ofRepoId(salesRepBillToLocation.getC_Location_ID()));

		final VatCodeId vatCodeId = null;
		final TaxId taxId = taxBL.getTaxNotNull(
				null,
				salesRepPricingResult.getTaxCategoryId(),
				salesRepPricingResult.getProductId().getRepoId(),
				Objects.requireNonNull(TimeUtil.asTimestamp(request.getCommissionDate())),
				salesRepOrgId,
				(WarehouseId)null,
				salesRepBillToLocationAndCapture,
				request.getSoTrx(),
				vatCodeId);

		final Tax taxRecord = taxDAO.getTaxById(taxId);

		final BigDecimal taxAdjustedAmount = taxRecord.calculateBaseAmt(
				salesRepPricingResult.getPriceStd(),
				salesRepPricingResult.isTaxIncluded(),
				salesRepPricingResult.getPrecision().toInt());

		return Money.of(taxAdjustedAmount, salesRepPricingResult.getCurrencyId());
	}

	@NonNull
	private Money convertToCustomerCurrency(
			@NonNull final OrgId salesRepOrgId,
			@NonNull final ComputeSalesRepPriceRequest request,
			@NonNull final Money salesRepUnitPrice)
	{
		final CurrencyConversionContext currencyConversionContext = currencyBL.createCurrencyConversionContext(
				LocalDateAndOrgId.ofLocalDate(request.getCommissionDate(), salesRepOrgId),
				ConversionTypeMethod.Spot,
				Env.getClientId());

		return moneyService.convertMoneyToCurrency(salesRepUnitPrice, request.getCustomerCurrencyId(), currencyConversionContext);
	}

	private void updatePricingResultToMatchUOM(@NonNull final IPricingResult salesRepPricingResult, @NonNull final UomId targetUOMId)
	{
		final ProductPrice salesRepProductPrice = ProductPrice.builder()
				.productId(salesRepPricingResult.getProductId())
				.uomId(salesRepPricingResult.getPriceUomId())
				.money(Money.of(salesRepPricingResult.getPriceStd(), salesRepPricingResult.getCurrencyId()))
				.build();

		final ProductPrice productPriceInTargetUOM = conversionBL.convertProductPriceToUom(salesRepProductPrice, targetUOMId, salesRepPricingResult.getPrecision());

		salesRepPricingResult.setPriceStd(productPriceInTargetUOM.toBigDecimal());
		salesRepPricingResult.setPriceUomId(targetUOMId);
	}
}
