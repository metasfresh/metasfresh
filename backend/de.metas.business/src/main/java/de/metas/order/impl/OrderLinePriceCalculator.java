package de.metas.order.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLine;
import de.metas.order.OrderLinePriceAndDiscount;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.order.OrderLineRepository;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.isValueChanged;

/*
 * #%L
 * de.metas.swat.base
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

final class OrderLinePriceCalculator
{
	private static final AdMessageKey MSG_Enforced = AdMessageKey.of("Enforced");
	private static final AdMessageKey MSG_NotEnforced = AdMessageKey.of("NotEnforced");

	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
	private final IOrderLineBL orderLineBL;

	private final OrderLinePriceUpdateRequest request;

	@Builder
	private OrderLinePriceCalculator(
			@NonNull final OrderLinePriceUpdateRequest request,
			@NonNull final IOrderLineBL orderLineBL)
	{
		this.orderLineBL = orderLineBL;
		this.request = request;

	}

	public void updateOrderLine()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();

		// Product was not set yet. There is no point to calculate the prices
		if (orderLine.getM_Product_ID() <= 0)
		{
			return;
		}

		//
		// Calculate Pricing Result
		final IEditablePricingContext pricingCtx = createPricingContext();
		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine())
					.setParameter("log", pricingResult.getLoggableMessages())
					.setParameter("pricingResult", pricingResult);
		}

		OrderLinePriceAndDiscount priceAndDiscount = extractPriceAndDiscount(pricingResult, pricingCtx.getSoTrx());

		//
		// Apply price limit restrictions
		final BooleanWithReason applyPriceLimitRestrictions = checkApplyPriceLimitRestrictions(pricingResult);
		if (applyPriceLimitRestrictions.isTrue())
		{
			final PriceLimitRuleResult priceLimitResult = pricingBL.computePriceLimit(PriceLimitRuleContext.builder()
					.pricingContext(pricingCtx)
					.priceLimit(priceAndDiscount.getPriceLimit())
					.priceActual(priceAndDiscount.getPriceActual())
					.paymentTermId(orderLineBL.getPaymentTermId(orderLine))
					.build());

			priceAndDiscount = priceAndDiscount.enforcePriceLimit(priceLimitResult);
		}
		else
		{
			priceAndDiscount = priceAndDiscount.toBuilder()
					.priceLimitEnforced(false)
					.priceLimitNotEnforcedExplanation(applyPriceLimitRestrictions.getReason())
					.build();
		}

		//
		// Prices
		priceAndDiscount.applyTo(orderLine);
		orderLine.setInvoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn().getCode());
		orderLine.setPriceList(pricingResult.getPriceList());
		orderLine.setPriceStd(pricingResult.getPriceStd());
		orderLine.setPrice_UOM_ID(UomId.toRepoId(pricingResult.getPriceUomId())); // 07090: when setting a priceActual, we also need to specify a PriceUOM

		//
		// C_Currency_ID, M_PriceList_Version_ID
		orderLine.setC_Currency_ID(CurrencyId.toRepoId(pricingResult.getCurrencyId()));
		orderLine.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(pricingResult.getPriceListVersionId()));

		orderLine.setIsCampaignPrice(pricingResult.isCampaignPrice());
		orderLine.setIsPriceEditable(pricingResult.isPriceEditable());
		orderLine.setIsDiscountEditable(pricingResult.isDiscountEditable());
		orderLine.setEnforcePriceLimit(pricingResult.getEnforcePriceLimit().isTrue());
		orderLine.setPriceLimitNote(buildPriceLimitNote(pricingResult.getEnforcePriceLimit()));

		updateOrderLineFromPricingConditionsResult(orderLine, pricingResult.getPricingConditions());

		//
		//
		if (request.isUpdateLineNetAmt())
		{
			if (request.getQtyOverride() != null)
			{
				orderLineBL.updateLineNetAmtFromQty(request.getQtyOverride(), orderLine);
			}
			else
			{
				orderLineBL.updateLineNetAmtFromQty(orderLineBL.getQtyEntered(orderLine), orderLine);
			}
		}

		if (request.isUpdateProfitPriceActual())
		{
			updateProfitPriceActual(orderLine);
		}
	}

	private String buildPriceLimitNote(final BooleanWithReason enforcePriceLimit)
	{
		final ITranslatableString msg;
		if (enforcePriceLimit.isTrue())
		{
			msg = TranslatableStrings.builder()
					.appendADMessage(MSG_Enforced)
					.append(": ")
					.append(enforcePriceLimit.getReason())
					.build();
		}
		else
		{
			msg = TranslatableStrings.builder()
					.appendADMessage(MSG_NotEnforced)
					.append(": ")
					.append(enforcePriceLimit.getReason())
					.build();
		}

		return msg.translate(Language.getBaseAD_Language());
	}

	private void updateOrderLineFromPricingConditionsResult(
			@NonNull final I_C_OrderLine orderLine,
			@Nullable final PricingConditionsResult pricingConditionsResult)
	{
		final PricingSystemId basePricingSystemId;
		final PaymentTermId paymentTermId;
		final BigDecimal paymentDiscount;
		final int discountSchemaId;
		final int discountSchemaBreakId;
		final boolean tempPricingConditions;

		if (pricingConditionsResult != null)
		{
			basePricingSystemId = pricingConditionsResult.getBasePricingSystemId();
			paymentTermId = pricingConditionsResult.getPaymentTermId();

			final PricingConditionsBreak pricingConditionsBreak = pricingConditionsResult.getPricingConditionsBreak();

			paymentDiscount = pricingConditionsBreak != null
					? pricingConditionsBreak.getPaymentDiscountOverrideOrNull().toBigDecimal()
					: null;

			if (pricingConditionsBreak != null
					&& pricingConditionsBreak.getId() != null
					&& hasSameValues(orderLine, pricingConditionsResult))
			{
				final PricingConditionsBreakId pricingConditionsBreakId = pricingConditionsBreak.getId();
				discountSchemaId = pricingConditionsBreakId.getDiscountSchemaId();
				discountSchemaBreakId = pricingConditionsBreakId.getDiscountSchemaBreakId();
				tempPricingConditions = false;
			}
			else
			{
				discountSchemaId = -1;
				discountSchemaBreakId = -1;
				tempPricingConditions = true;
			}
		}
		else
		{
			basePricingSystemId = null;
			paymentTermId = null;
			paymentDiscount = null;
			discountSchemaId = -1;
			discountSchemaBreakId = -1;
			tempPricingConditions = false;
		}

		orderLine.setBase_PricingSystem_ID(PricingSystemId.toRepoId(basePricingSystemId));

		if (!orderLine.isManualPaymentTerm())
		{
			orderLine.setC_PaymentTerm_Override_ID(PaymentTermId.toRepoId(paymentTermId));
			orderLine.setPaymentDiscount(paymentDiscount);
		}

		orderLine.setM_DiscountSchema_ID(discountSchemaId);
		orderLine.setM_DiscountSchemaBreak_ID(discountSchemaBreakId);
		orderLine.setIsTempPricingConditions(tempPricingConditions);
	}

	private static boolean hasSameValues(final I_C_OrderLine orderLine, final PricingConditionsResult pricingConditionsResult)
	{
		final BigDecimal priceStdOverride = pricingConditionsResult.getPriceStdOverride();
		if (priceStdOverride != null && priceStdOverride.compareTo(orderLine.getPriceEntered()) != 0)
		{
			return false;
		}

		final Percent discount = pricingConditionsResult.getDiscount();
		if (discount != null && !discount.equals(Percent.of(orderLine.getDiscount())))
		{
			return false;
		}

		final PaymentTermId paymentTermId = pricingConditionsResult.getPaymentTermId();
		if (paymentTermId != null && paymentTermId.getRepoId() != orderLine.getC_PaymentTerm_Override_ID())
		{
			return false;
		}

		final PricingSystemId basePricingSystemId = pricingConditionsResult.getBasePricingSystemId();
		if (basePricingSystemId != null && basePricingSystemId.getRepoId() != orderLine.getBase_PricingSystem_ID())
		{
			return false;
		}

		return true;
	}

	private IEditablePricingContext createPricingContext()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();

		final boolean isSOTrx = order.isSOTrx();
		final int productId = orderLine.getM_Product_ID();

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(firstGreaterThanZero(orderLine.getC_BPartner_ID(), order.getC_BPartner_ID()));

		final ZonedDateTime date = OrderLineBL.getPriceDate(orderLine, order);

		final Quantity qtyInPriceUOM;
		if (request.getQtyOverride() != null)
		{
			final Quantity qtyOverride = request.getQtyOverride();
			qtyInPriceUOM = orderLineBL.convertQtyToPriceUOM(qtyOverride, orderLine);
		}
		else
		{
			qtyInPriceUOM = orderLineBL.convertQtyEnteredToPriceUOM(orderLine);
		}

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				OrgId.ofRepoId(orderLine.getAD_Org_ID()),
				ProductId.ofRepoId(productId),
				bpartnerId,
				qtyInPriceUOM,
				SOTrx.ofBoolean(isSOTrx));
		pricingCtx.setPriceDate(TimeUtil.asLocalDate(date));

		// 03152: setting the 'ol' to allow the subscription system to compute the right price
		pricingCtx.setReferencedObject(orderLine);

		pricingCtx.setConvertPriceToContextUOM(isConvertPriceToContextUOM(request, pricingCtx.isConvertPriceToContextUOM()));

		//
		// Pricing System / List / Country / Currency
		{
			PricingSystemId pricingSystemId = CoalesceUtil.coalesce(request.getPricingSystemIdOverride(), pricingCtx.getPricingSystemId());
			final PriceListId priceListId = CoalesceUtil.coalesce(request.getPriceListIdOverride(), orderBL.retrievePriceListId(order, pricingSystemId));
			if (pricingSystemId == null && priceListId != null)
			{
				pricingSystemId = priceListDAO.getPricingSystemId(priceListId);
			}
			final CountryId countryId = getCountryId(orderLine).orElse(null);
			pricingCtx.setPricingSystemId(pricingSystemId);
			pricingCtx.setPriceListId(priceListId);
			pricingCtx.setPriceListVersionId(null);
			pricingCtx.setCountryId(countryId);
			pricingCtx.setCurrencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()));
		}

		//
		// Don't calculate the discount in case we are dealing with a percentage discount compensation group line (task 3149)
		if (orderLine.isGroupCompensationLine()
				&& X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount.equals(orderLine.getGroupCompensationType())
				&& X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent.equals(orderLine.getGroupCompensationAmtType()))
		{
			pricingCtx.setDisallowDiscount(true);
		}

		//
		pricingCtx.setForcePricingConditionsBreak(getPricingConditionsBreakFromRequest());

		return pricingCtx;
	}

	private Optional<CountryId> getCountryId(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		return OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine)
				.getBPartnerLocationAndCaptureIdIfExists()
				.map(bpartnerBL::getCountryId);
	}

	private PricingConditionsBreak getPricingConditionsBreakFromRequest()
	{
		if (request.getPricingConditionsBreakOverride() != null)
		{
			return request.getPricingConditionsBreakOverride();
		}

		final I_C_OrderLine orderLine = request.getOrderLine();
		final PricingConditionsBreakId orderLinePricingConditionsBreakId = PricingConditionsBreakId.ofOrNull(orderLine.getM_DiscountSchema_ID(), orderLine.getM_DiscountSchemaBreak_ID());
		final boolean discountNeedsRevalidation = isValueChanged(orderLine, PricingConditionsBreakQuery.getRelevantOrderLineColumns());
		if (orderLinePricingConditionsBreakId != null && !discountNeedsRevalidation)
		{
			final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(orderLinePricingConditionsBreakId.getPricingConditionsId());

			return pricingConditions.getBreakById(orderLinePricingConditionsBreakId);
		}

		return null;
	}

	private static boolean isConvertPriceToContextUOM(final OrderLinePriceUpdateRequest request, final boolean defaultValue)
	{
		final ResultUOM resultUOM = request.getResultUOM();
		if (resultUOM == null)
		{
			return defaultValue;
		}
		else if (resultUOM == ResultUOM.PRICE_UOM)
		{
			return false;
		}
		else if (resultUOM == ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
		{
			final boolean convertToPriceUOM = InterfaceWrapperHelper.isNew(request.getOrderLine());
			return !convertToPriceUOM;
		}
		else if (resultUOM == ResultUOM.CONTEXT_UOM)
		{
			return true;
		}
		else
		{
			throw new AdempiereException("ResultPriceUOM not supported: " + resultUOM);
		}
	}

	private BooleanWithReason checkApplyPriceLimitRestrictions(final IPricingResult pricingResult)
	{
		if (!request.isApplyPriceLimitRestrictions())
		{
			return BooleanWithReason.falseBecause("by request");
		}

		if (!request.getOrderLine().getC_Order().isSOTrx())
		{
			return BooleanWithReason.falseBecause("we enforce price limit only for sales orders");
		}

		return pricingResult.getEnforcePriceLimit();
	}

	private OrderLinePriceAndDiscount extractPriceAndDiscount(final IPricingResult pricingResult, final SOTrx soTrx)
	{
		return OrderLinePriceAndDiscount.builder()
				.precision(pricingResult.getPrecision())
				.priceEntered(extractPriceEntered(pricingResult))
				.priceLimit(pricingResult.getPriceLimit())
				.discount(extractDiscount(pricingResult, soTrx))
				.build()
				.updatePriceActual();
	}

	private BigDecimal extractPriceEntered(final IPricingResult pricingResult)
	{
		if (isAllowChangingPriceEntered())
		{
			return pricingResult.getPriceStd();
		}
		else
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			return orderLine.getPriceEntered();
		}
	}

	private boolean isAllowChangingPriceEntered()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();
		if (orderLine.isManualPrice())
		{
			return false;
		}
		if (request.isUpdatePriceEnteredAndDiscountOnlyIfNotAlreadySet() && orderLine.getPriceEntered().signum() != 0) // task 06727
		{
			return false;
		}

		return true;
	}

	private Percent extractDiscount(final IPricingResult pricingResult, final SOTrx soTrx)
	{
		if (isAllowChangingDiscount(soTrx))
		{
			return pricingResult.getDiscount();
		}
		else
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			return Percent.of(orderLine.getDiscount());
		}
	}

	private boolean isAllowChangingDiscount(@NonNull final SOTrx soTrx)
	{
		final I_C_OrderLine orderLine = request.getOrderLine();
		if (orderLine.isManualDiscount())
		{
			return false;
		}
		if (soTrx.isPurchase())
		{
			return true;
		}

		if (request.isUpdatePriceEnteredAndDiscountOnlyIfNotAlreadySet() && orderLine.getDiscount().signum() != 0) // task 06727
		{
			return false;
		}

		return true;
	}

	private void updateProfitPriceActual(final I_C_OrderLine orderLineRecord)
	{
		final OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
		final ProfitPriceActualFactory profitPriceActualFactory = SpringContextHolder.instance.getBean(ProfitPriceActualFactory.class);

		final OrderLine orderLine = orderLineRepository.ofRecord(orderLineRecord);

		final CalculateProfitPriceActualRequest request = CalculateProfitPriceActualRequest.builder()
				.bPartnerId(orderLine.getBPartnerId())
				.productId(orderLine.getProductId())
				.date(orderLine.getDatePromised().toLocalDate())
				.baseAmount(orderLine.getPriceActual().toMoney())
				.paymentTermId(orderLine.getPaymentTermId())
				.quantity(orderLine.getOrderedQty())
				.build();

		final Money profitBasePrice = profitPriceActualFactory.calculateProfitPriceActual(request);

		orderLineRecord.setProfitPriceActual(profitBasePrice.toBigDecimal());
	}

	public TaxCategoryId computeTaxCategoryId()
	{
		final IPricingContext pricingCtx = createPricingContext()
				.setDisallowDiscount(true); // don't bother computing discounts; we know that the tax category is not related to them.

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine())
					.setParameter("log", pricingResult.getLoggableMessages());
		}

		return pricingResult.getTaxCategoryId();
	}

	public IPricingResult computePrices()
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		return pricingBL.calculatePrice(pricingCtx);
	}

	public PriceLimitRuleResult computePriceLimit()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();

		return pricingBL.computePriceLimit(PriceLimitRuleContext.builder()
				.pricingContext(createPricingContext())
				.priceLimit(orderLine.getPriceLimit())
				.priceActual(orderLine.getPriceActual())
				.paymentTermId(orderLineBL.getPaymentTermId(orderLine))
				.build());
	}
}
