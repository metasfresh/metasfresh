package de.metas.ui.web.order.pricingconditions.view;

import static de.metas.util.Check.assumeNotNull;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableList;

import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.TranslatableStrings;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.PriceAndDiscount;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * metasfresh-webui-api
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

public class PricingConditionsView extends AbstractCustomView<PricingConditionsRow> implements IEditableView
{
	public static PricingConditionsView cast(final Object viewObj)
	{
		return (PricingConditionsView)viewObj;
	}

	private final PricingConditionsRowData rowsData;
	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private PricingConditionsView(
			final ViewId viewId,
			final PricingConditionsRowData rowsData,
			@Singular final List<RelatedProcessDescriptor> relatedProcessDescriptors,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptors)
	{
		super(viewId, TranslatableStrings.empty(), rowsData, filterDescriptors);
		this.rowsData = rowsData;
		this.relatedProcessDescriptors = ImmutableList.copyOf(relatedProcessDescriptors);
	}

	private PricingConditionsView(final PricingConditionsView from, final PricingConditionsRowData rowsData)
	{
		super(from.getViewId(), from.getDescription(), rowsData, from.getFilterDescriptors());
		this.rowsData = rowsData;
		this.relatedProcessDescriptors = from.relatedProcessDescriptors;
	}

	public OrderLineId getOrderLineId()
	{
		return rowsData.getOrderLineId();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}

	@Override
	public LookupValuesList getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		return getById(ctx.getRowId()).getFieldTypeahead(fieldName, query);
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		return getById(ctx.getRowId()).getFieldDropdown(fieldName);
	}

	public boolean hasEditableRow()
	{
		return rowsData.hasEditableRow();
	}

	public PricingConditionsRow getEditableRow()
	{
		return rowsData.getEditableRow();
	}

	public void patchEditableRow(@NonNull final PricingConditionsRowChangeRequest request)
	{
		rowsData.patchEditableRow(request);
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return rowsData.getFilters();
	}

	public PricingConditionsView filter(final DocumentFilterList filters)
	{
		return new PricingConditionsView(this, rowsData.filter(filters));
	}

	public void updateSalesOrderLineIfPossible()
	{
		if (!hasEditableRow())
		{
			return;
		}

		final PricingConditionsRow editableRow = getEditableRow();

		final BigDecimal basePriceAmt = editableRow.getBasePriceAmt();
		final CurrencyId currencyId = editableRow.getCurrencyId();
		final Money basePriceFromRow = Money.ofOrNull(basePriceAmt, currencyId);

		final PricingConditionsBreak pricingConditionsBreak = editableRow.getPricingConditionsBreak();

		updateOrderLineRecord(pricingConditionsBreak, basePriceFromRow);
	}

	private void updateOrderLineRecord(
			@NonNull final PricingConditionsBreak pricingConditionsBreak,
			@Nullable final Money basePrice)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final I_C_OrderLine orderLineRecord = ordersRepo.getOrderLineById(getOrderLineId());
		orderLineRecord.setIsTempPricingConditions(pricingConditionsBreak.isTemporaryPricingConditionsBreak());

		if (pricingConditionsBreak.isTemporaryPricingConditionsBreak())
		{
			orderLineRecord.setM_DiscountSchema_ID(-1);
			orderLineRecord.setM_DiscountSchemaBreak_ID(-1);

			final PriceSpecification price = pricingConditionsBreak.getPriceSpecification();

			final PriceSpecificationType type = price.getType();
			if (type == PriceSpecificationType.NONE)
			{
				//
			}
			else if (type == PriceSpecificationType.BASE_PRICING_SYSTEM)
			{
				assumeNotNull(basePrice, "If type={}, then the given basePrice may not be null; pricingConditionsBreak={}", type, pricingConditionsBreak);

				final BigDecimal priceEntered = limitPrice(basePrice.toBigDecimal(), orderLineRecord);

				orderLineRecord.setIsManualPrice(true);
				orderLineRecord.setPriceEntered(priceEntered);
				orderLineRecord.setC_Currency_ID(basePrice.getCurrencyId().getRepoId());

				orderLineRecord.setBase_PricingSystem_ID(price.getBasePricingSystemId().getRepoId());
			}
			else if (type == PriceSpecificationType.FIXED_PRICE)
			{
				final Money fixedPrice = price.getFixedPrice();
				Check.assumeNotNull(fixedPrice, "fixedPrice shall not be null for {}", price);

				final BigDecimal priceEntered = limitPrice(fixedPrice.toBigDecimal(), orderLineRecord);

				orderLineRecord.setIsManualPrice(true);
				orderLineRecord.setPriceEntered(priceEntered);
				orderLineRecord.setC_Currency_ID(fixedPrice.getCurrencyId().getRepoId());

				orderLineRecord.setBase_PricingSystem_ID(-1);
			}

			orderLineRecord.setIsManualDiscount(true);
			orderLineRecord.setDiscount(pricingConditionsBreak.getDiscount().toBigDecimal());

			orderLineRecord.setIsManualPaymentTerm(true); // make sure it's not overwritten by whatever the system comes up with when we save the orderLine.
			orderLineRecord.setC_PaymentTerm_Override_ID(PaymentTermId.toRepoId(pricingConditionsBreak.getDerivedPaymentTermIdOrNull()));
			orderLineRecord.setPaymentDiscount(Percent.toBigDecimalOrNull(pricingConditionsBreak.getPaymentDiscountOverrideOrNull()));

			//
			// PriceActual & Discount
			Percent discountEffective = pricingConditionsBreak.getDiscount();
			final BigDecimal priceActual = discountEffective.subtractFromBase(orderLineRecord.getPriceEntered(), CurrencyPrecision.TWO.toInt());
			final BigDecimal priceActualEffective = limitPrice(priceActual, orderLineRecord);
			if (priceActualEffective.compareTo(priceActual) != 0)
			{
				discountEffective = PriceAndDiscount.calculateDiscountFromPrices(
						orderLineRecord.getPriceEntered(),
						priceActualEffective,
						CurrencyPrecision.TWO);
			}
			//
			orderLineRecord.setIsManualDiscount(true);
			orderLineRecord.setDiscount(discountEffective.toBigDecimal());
			orderLineRecord.setPriceActual(priceActualEffective);

		}
		else
		{
			final PricingConditionsBreakId pricingConditionsBreakId = pricingConditionsBreak.getId();
			orderLineRecord.setM_DiscountSchema_ID(pricingConditionsBreakId.getDiscountSchemaId());
			orderLineRecord.setM_DiscountSchemaBreak_ID(pricingConditionsBreakId.getDiscountSchemaBreakId());

			orderLineRecord.setIsManualDiscount(false);
			orderLineRecord.setIsManualPrice(false);
			orderLineRecord.setIsManualPaymentTerm(false);

			final OrderLinePriceUpdateRequest orderLinePriceUpdateRequest = OrderLinePriceUpdateRequest
					.prepare(orderLineRecord)
					.pricingConditionsBreakOverride(pricingConditionsBreak)
					.build();
			orderLineBL.updatePrices(orderLinePriceUpdateRequest);
		}

		orderLineBL.updateLineNetAmtFromQtyEntered(orderLineRecord);
		orderLineBL.setTaxAmtInfo(orderLineRecord);

		InterfaceWrapperHelper.save(orderLineRecord);
	}

	private static BigDecimal limitPrice(final BigDecimal price, final I_C_OrderLine orderLineRecord)
	{
		if (!orderLineRecord.isEnforcePriceLimit())
		{
			return price;
		}

		final BigDecimal priceLimit = orderLineRecord.getPriceLimit();
		if (priceLimit.signum() == 0)
		{
			return price;
		}

		return price.max(priceLimit);
	}
}
