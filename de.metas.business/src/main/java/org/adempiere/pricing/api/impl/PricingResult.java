package org.adempiere.pricing.api.impl;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 *
 * NOTEs to developers:
 * <ul>
 * <li>if you want to add a new field here which will be copied from {@link IPricingContext}, please check {@link org.adempiere.pricing.api.impl.PricingBL#createInitialResult(IPricingContext)}.
 * </ul>
 *
 * @author tsa
 *
 */
class PricingResult implements IPricingResult
{
	private int M_PricingSystem_ID = -1;
	private int M_PriceList_ID = -1;
	private int C_Currency_ID = -1;
	private int C_UOM_ID = -1;
	private int M_Product_ID = -1;
	private int M_Product_Category_ID = -1;
	private int M_PriceList_Version_ID = -1;
	private int C_TaxCategory_ID = -1;
	private int M_DiscountSchema_ID = -1;
	private int M_DiscountSchemaBreak_ID = -1;
	private int precision = NO_PRECISION;
	private BigDecimal priceList = BigDecimal.ZERO;
	private BigDecimal priceStd = BigDecimal.ZERO;
	private BigDecimal priceLimit = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
	private boolean enforcePriceLimit = false;
	private boolean taxIncluded = false;
	private boolean isUseDiscountSchema = false;
	private boolean disallowDiscount = false;
	private Timestamp priceDateTS = null;
	private int C_PaymentTerm_ID = -1;

	private boolean isPriceEditable = true;
	private boolean isDiscountEditable = true;

	private boolean calculated = false;

	private final List<IPricingRule> rulesApplied = new ArrayList<>();

	private final List<IPricingAttribute> pricingAttributes = new ArrayList<>();

	@Override
	public int getM_PricingSystem_ID()
	{
		return M_PricingSystem_ID;
	}

	@Override
	public void setM_PricingSystem_ID(final int pricingSystemId)
	{
		M_PricingSystem_ID = pricingSystemId;
	}

	@Override
	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		this.M_PriceList_ID = M_PriceList_ID;
	}

	@Override
	public int getM_PriceList_ID()
	{
		return M_PriceList_ID;
	}

	/**
	 * @return the c_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	/**
	 * @param c_Currency_ID the c_Currency_ID to set
	 */
	@Override
	public void setC_Currency_ID(final int c_Currency_ID)
	{
		C_Currency_ID = c_Currency_ID;
	}

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
	 * @return the precision
	 */
	@Override
	public int getPrecision()
	{
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	@Override
	public void setPrecision(final int precision)
	{
		this.precision = precision;
	}

	/**
	 * @return the priceList
	 */
	@Override
	public BigDecimal getPriceList()
	{
		return priceList;
	}

	/**
	 * @param priceList the priceList to set
	 */
	@Override
	public void setPriceList(final BigDecimal priceList)
	{
		this.priceList = priceList;
	}

	/**
	 * @return the priceStd
	 */
	@Override
	public BigDecimal getPriceStd()
	{
		return priceStd;
	}

	/**
	 * @param priceStd the priceStd to set
	 */
	@Override
	public void setPriceStd(final BigDecimal priceStd)
	{
		this.priceStd = priceStd;
	}

	/**
	 * @return the priceLimit
	 */
	@Override
	public BigDecimal getPriceLimit()
	{
		return priceLimit;
	}

	/**
	 * @param priceLimit the priceLimit to set
	 */
	@Override
	public void setPriceLimit(final BigDecimal priceLimit)
	{
		this.priceLimit = priceLimit;
	}

	/**
	 * @return the discount
	 */
	@Override
	public BigDecimal getDiscount()
	{
		return discount != null ? discount : BigDecimal.ZERO;
	}

	/**
	 * @param discount the discount to set
	 */
	@Override
	public void setDiscount(final BigDecimal discount)
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

	/**
	 * @return the m_Product_Category_ID
	 */
	@Override
	public int getM_Product_Category_ID()
	{
		return M_Product_Category_ID;
	}

	/**
	 * @param m_Product_Category_ID the m_Product_Category_ID to set
	 */
	@Override
	public void setM_Product_Category_ID(final int m_Product_Category_ID)
	{
		M_Product_Category_ID = m_Product_Category_ID;
	}

	/**
	 * @return the m_PriceList_Version_ID
	 */
	@Override
	public int getM_PriceList_Version_ID()
	{
		return M_PriceList_Version_ID;
	}

	/**
	 * @param m_PriceList_Version_ID the m_PriceList_Version_ID to set
	 */
	@Override
	public void setM_PriceList_Version_ID(final int m_PriceList_Version_ID)
	{
		M_PriceList_Version_ID = m_PriceList_Version_ID;
	}

	/**
	 * @return the priceDate
	 */
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
	public List<IPricingRule> getRulesApplied()
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
	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	@Override
	public void setM_Product_ID(final int m_Product_ID)
	{
		M_Product_ID = m_Product_ID;
	}

	@Override
	public int getM_DiscountSchema_ID()
	{
		return M_DiscountSchema_ID;
	}

	@Override
	public void setM_DiscountSchema_ID(final int m_DiscountSchema_ID)
	{
		M_DiscountSchema_ID = m_DiscountSchema_ID;
	}

	/**
	 * @see IPricingAttribute
	 */
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
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		return C_PaymentTerm_ID;
	}

	@Override
	public void setC_PaymentTerm_ID(final int C_PaymentTerm_ID)
	{
		this.C_PaymentTerm_ID = C_PaymentTerm_ID;
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

	@Override
	public int getM_DiscountSchemaBreak_ID()
	{
		return M_DiscountSchemaBreak_ID;
	}

	@Override
	public void setM_DiscountSchemaBreak_ID(final int M_DiscountSchemaBreak_ID)
	{
		this.M_DiscountSchemaBreak_ID = M_DiscountSchemaBreak_ID;
	}
}
