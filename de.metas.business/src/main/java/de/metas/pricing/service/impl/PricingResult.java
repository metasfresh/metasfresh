package de.metas.pricing.service.impl;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.util.Util;

import de.metas.lang.Percent;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPricingAttribute;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * NOTEs to developers:
 * <ul>
 * <li>if you want to add a new field here which will be copied from {@link IPricingContext}, please check {@link de.metas.pricing.service.impl.PricingBL#createInitialResult(IPricingContext)}.
 * </ul>
 *
 * @author tsa
 *
 */
@ToString
class PricingResult implements IPricingResult
{
	private boolean calculated = false;

	@Getter
	@Setter
	private PricingSystemId pricingSystemId;
	@Getter
	@Setter
	private PriceListId priceListId;
	@Setter
	@Getter
	private CurrencyId currencyId;
	private int C_UOM_ID = -1;
	@Setter
	@Getter
	private ProductId productId;
	@Setter
	@Getter
	private ProductCategoryId productCategoryId;
	@Setter
	@Getter
	private PriceListVersionId priceListVersionId;
	private int C_TaxCategory_ID = -1;
	@Setter
	@Getter
	private PricingConditionsResult pricingConditions;

	@Setter
	@Getter
	private int precision = NO_PRECISION;

	@Setter
	@Getter
	private BigDecimal priceList = BigDecimal.ZERO;

	@Setter
	@Getter
	private BigDecimal priceStd = BigDecimal.ZERO;

	@Setter
	@Getter
	private BigDecimal priceLimit = BigDecimal.ZERO;
	private Percent discount = Percent.ZERO;
	private boolean enforcePriceLimit = false;
	private boolean taxIncluded = false;
	private boolean isUseDiscountSchema = false;
	private boolean disallowDiscount = false;
	private Timestamp priceDateTS = null;

	private boolean isPriceEditable = true;
	private boolean isDiscountEditable = true;

	private final List<IPricingRule> rulesApplied = new ArrayList<>();

	private final List<IPricingAttribute> pricingAttributes = new ArrayList<>();

	/**
	 * @return the c_UOM_ID
	 */
	@Override
	public int getPrice_UOM_ID()
	{
		return C_UOM_ID;
	}

	/**
	 * @param c_UOM_ID the c_UOM_ID to set
	 */
	@Override
	public void setPrice_UOM_ID(final int c_UOM_ID)
	{
		C_UOM_ID = c_UOM_ID;
	}

	/**
	 * @return the discount
	 */
	@Override
	public Percent getDiscount()
	{
		return Util.coalesce(discount, Percent.ZERO);
	}

	/**
	 * @param discount the discount to set
	 */
	@Override
	public void setDiscount(final Percent discount)
	{
		Check.assume(!isDisallowDiscount(), "Method caller is respecting the 'disallowDiscount' property");
		this.discount = discount;
	}

	/**
	 * @return the enforcePriceLimit
	 */
	@Override
	public boolean isEnforcePriceLimit()
	{
		return enforcePriceLimit;
	}

	/**
	 * @param enforcePriceLimit the enforcePriceLimit to set
	 */
	@Override
	public void setEnforcePriceLimit(final boolean enforcePriceLimit)
	{
		this.enforcePriceLimit = enforcePriceLimit;
	}

	/**
	 * @return the taxIncluded
	 */
	@Override
	public boolean isTaxIncluded()
	{
		return taxIncluded;
	}

	/**
	 * @param taxIncluded the taxIncluded to set
	 */
	@Override
	public void setTaxIncluded(final boolean taxIncluded)
	{
		this.taxIncluded = taxIncluded;
	}

	/**
	 * @return true f a discountSchema is set, false otherwise
	 */
	@Override
	public boolean isUsesDiscountSchema()
	{
		return isUseDiscountSchema;
	}

	/**
	 * @param wether of not a discount schema is used
	 */
	@Override
	public void setUsesDiscountSchema(final boolean discountSchema)
	{
		isUseDiscountSchema = discountSchema;
	}

	/**
	 * @return the calculated
	 */
	@Override
	public boolean isCalculated()
	{
		return calculated;
	}

	/**
	 * @param calculated the calculated to set
	 */
	@Override
	public void setCalculated(final boolean calculated)
	{
		this.calculated = calculated;
	}

	@Override
	public Timestamp getPriceDate()
	{
		return priceDateTS;
	}

	/**
	 * @param priceDate the priceDate to set
	 */
	@Override
	public void setPriceDate(final Timestamp priceDate)
	{
		priceDateTS = priceDate;
	}

	@Override
	public void addPricingRuleApplied(@NonNull IPricingRule rule)
	{
		rulesApplied.add(rule);
	}

	@Override
	public List<IPricingRule> getPricingRulesApplied()
	{
		return rulesApplied;
	}

	@Override
	public boolean isDisallowDiscount()
	{
		return disallowDiscount;
	}

	@Override
	public void setDisallowDiscount(final boolean disallowDiscount)
	{
		this.disallowDiscount = disallowDiscount;
	}

	@Override
	public int getC_TaxCategory_ID()
	{
		return C_TaxCategory_ID;
	}

	@Override
	public void setC_TaxCategory_ID(final int C_TaxCategory_ID)
	{
		this.C_TaxCategory_ID = C_TaxCategory_ID;
	}

	@Override
	public List<IPricingAttribute> getPricingAttributes()
	{
		return pricingAttributes;
	}

	@Override
	public void addPricingAttributes(final Collection<IPricingAttribute> pricingAttributesToAdd)
	{
		if (pricingAttributesToAdd == null || pricingAttributesToAdd.isEmpty())
		{
			return;
		}

		pricingAttributes.addAll(pricingAttributesToAdd);
	}

	@Override
	public void setPriceEditable(final boolean isPriceEditable)
	{
		this.isPriceEditable = isPriceEditable;
	}

	@Override
	public boolean isPriceEditable()
	{
		return isPriceEditable;
	}

	@Override
	public void setDiscountEditable(final boolean isDiscountEditable)
	{
		this.isDiscountEditable = isDiscountEditable;
	}

	@Override
	public boolean isDiscountEditable()
	{
		return isDiscountEditable;
	}

	/**
	 * Supposed to be called by the pricing engine.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/4376
	 */
	public void updatePriceScales()
	{
		priceStd = scaleToPrecision(priceStd);
		priceLimit = scaleToPrecision(priceLimit);
		priceList = scaleToPrecision(priceList);
	}

	private BigDecimal scaleToPrecision(@Nullable final BigDecimal priceToRound)
	{
		if (priceToRound == null || precision < 0)
		{
			return priceToRound;
		}
		return priceToRound.setScale(precision, RoundingMode.HALF_UP);
	}
}
