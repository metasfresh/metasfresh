package de.metas.pricing;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Result of a pricing calculation
 */
public interface IPricingResult
{
	CurrencyId getCurrencyId();

	void setCurrencyId(CurrencyId currencyId);

	default int getCurrencyRepoId()
	{
		return CurrencyId.toRepoId(getCurrencyId());
	}

	void setPriceUomId(@Nullable final UomId uomId);

	UomId getPriceUomId();

	BigDecimal getPriceList();

	void setPriceList(BigDecimal priceList);

	BigDecimal getPriceStd();

	void setPriceStd(BigDecimal priceStd);

	BigDecimal getPriceLimit();

	void setPriceLimit(BigDecimal priceLimit);

	BooleanWithReason getEnforcePriceLimit();

	void setEnforcePriceLimit(BooleanWithReason enforcePriceLimit);

	Money getPriceStdAsMoney();

	/**
	 * @return discount (between 0 and 100); never null
	 */
	@NonNull
	Percent getDiscount();

	/**
	 * Sets the given discount. After this, {@link #isDiscountCalculated()} will always return {@code true}.
	 */
	void setDiscount(Percent discount);

	boolean isDiscountCalculated();

	CurrencyPrecision getPrecision();

	void setPrecision(CurrencyPrecision precision);

	boolean isTaxIncluded();

	void setTaxIncluded(boolean taxIncluded);

	boolean isUsesDiscountSchema();

	void setUsesDiscountSchema(boolean usesDiscountSchema);

	boolean isDisallowDiscount();

	void setDisallowDiscount(boolean disallowDiscount);

	/**
	 * @return true if the price was calculated successfully
	 */
	boolean isCalculated();

	void setCalculated(boolean calculated);

	PricingSystemId getPricingSystemId();

	void setPricingSystemId(PricingSystemId pricingSystemId);

	void setPriceListId(PriceListId priceListId);

	PriceListId getPriceListId();

	PriceListVersionId getPriceListVersionId();

	void setPriceListVersionId(PriceListVersionId priceListVersionId);

	void setProductCategoryId(@Nullable ProductCategoryId productCategoryId);

	ProductCategoryId getProductCategoryId();

	void addPricingRuleApplied(IPricingRule rule);

	TaxCategoryId getTaxCategoryId();

	void setTaxCategoryId(TaxCategoryId taxCategoryId);

	ProductId getProductId();

	void setProductId(ProductId productId);

	PricingConditionsResult getPricingConditions();

	void setPricingConditions(@Nullable PricingConditionsResult pricingConditions);

	/**
	 * @return the price relevant attributes. Never return {@code null}.
	 */
	List<IPricingAttribute> getPricingAttributes();

	/**
	 * Adds given {@link IPricingAttribute}s to this result.
	 *
	 * @param pricingAttributesToAdd pricing attributes or empty or {@code null}.
	 */
	void addPricingAttributes(final Collection<IPricingAttribute> pricingAttributesToAdd);

	/**
	 * @return the timestamp that was relevant for the price calculation.
	 */
	LocalDate getPriceDate();

	boolean isPriceEditable();

	void setPriceEditable(boolean isPriceEditable);

	boolean isDiscountEditable();

	void setDiscountEditable(boolean isDiscountEditable);

	/**
	 * This info is contained in the pricing master data; it's not relevant for the price per unit, but to compute the invoicable quantity.
	 */
	InvoicableQtyBasedOn getInvoicableQtyBasedOn();

	void setInvoicableQtyBasedOn(InvoicableQtyBasedOn invoicableQtyBasedOn);

	void setCampaignPrice(boolean isCampaignPrice);

	boolean isCampaignPrice();

	IPricingResult setLoggableMessages(ImmutableList<String> singleMessages);

	ImmutableList<String> getLoggableMessages();

	/**
	 * @return {@code true} if the current discount should not be overridden by any other pricing rule, {@code false} otherwise.
	 */
	boolean isDontOverrideDiscountAdvice();

	/**
	 * Can specify if the discount in the pricing rule can be overridden by any other pricing rule.
	 */
	void setDontOverrideDiscountAdvice(boolean dontOverrideDiscountAdvice);

	void setPackingMaterialId(HUPIItemProductId packingMaterialId);

	HUPIItemProductId getPackingMaterialId();
}
