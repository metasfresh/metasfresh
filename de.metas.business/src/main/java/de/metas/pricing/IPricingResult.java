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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import de.metas.lang.Percent;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.rules.IPricingRule;

/**
 * Result of a pricing calculation
 *
 */
public interface IPricingResult
{
	int NO_PRECISION = -1;

	int getC_Currency_ID();

	void setC_Currency_ID(int currencyId);

	int getPrice_UOM_ID();

	void setPrice_UOM_ID(int uomId);

	BigDecimal getPriceList();

	void setPriceList(BigDecimal priceList);

	BigDecimal getPriceStd();

	void setPriceStd(BigDecimal priceStd);

	BigDecimal getPriceLimit();

	void setPriceLimit(BigDecimal priceLimit);

	boolean isEnforcePriceLimit();

	void setEnforcePriceLimit(boolean enforcePriceLimit);

	/**
	 *
	 * @return discount (between 0 and 100); never null
	 */
	Percent getDiscount();

	void setDiscount(Percent discount);

	int getPrecision();

	void setPrecision(int precision);

	boolean isTaxIncluded();

	void setTaxIncluded(boolean taxIncluded);

	boolean isUsesDiscountSchema();

	void setUsesDiscountSchema(boolean usesDiscountSchema);

	boolean isDisallowDiscount();

	void setDisallowDiscount(boolean disallowDiscount);

	/**
	 *
	 * @return true if the price was calculated successfully
	 */
	boolean isCalculated();

	void setCalculated(boolean calculated);

	int getM_PricingSystem_ID();

	void setM_PricingSystem_ID(final int pricingSystemId);

	void setM_PriceList_ID(int tM_PriceList_ID);

	int getM_PriceList_ID();

	void setM_PriceList_Version_ID(int M_PriceList_Version_ID);

	int getM_PriceList_Version_ID();

	void setM_Product_Category_ID(int m_Product_Category_ID);

	int getM_Product_Category_ID();

	List<IPricingRule> getRulesApplied();

	int getC_TaxCategory_ID();

	void setC_TaxCategory_ID(int C_TaxCategory_ID);

	int getM_Product_ID();

	void setM_Product_ID(int m_Product_ID);

	PricingConditionsResult getPricingConditions();

	void setPricingConditions(PricingConditionsResult pricingConditions);

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
	 *
	 * @return the timestamp that was relevant for the price calculation.
	 */
	Timestamp getPriceDate();

	/**
	 * See {@link #getPriceDate()}.
	 *
	 * @param priceDate
	 */
	void setPriceDate(Timestamp priceDate);

	boolean isPriceEditable();

	void setPriceEditable(boolean isPriceEditable);

	boolean isDiscountEditable();

	void setDiscountEditable(boolean isDiscountEditable);
}
