/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.order.IOrderLineBL;

/**
 * Product Price Calculations
 * 
 * @author Jorg Janke
 * @version $Id: MProductPricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * 
 * @deprecated Please use {@link IPricingBL}
 */
@Deprecated
public class MProductPricing
{
	private final IEditablePricingContext pricingCtx;
	private IPricingResult result;
	private boolean calculationExecuted = false;

	/**
	 * Constructor
	 * 
	 * @param M_Product_ID product
	 * @param C_BPartner_ID partner
	 * @param Qty quantity
	 * @param isSOTrx SO or PO
	 */
	public MProductPricing(int M_Product_ID, int C_BPartner_ID, BigDecimal Qty, boolean isSOTrx)
	{
		pricingCtx = Services.get(IPricingBL.class).createInitialContext(M_Product_ID, C_BPartner_ID, 0, Qty, isSOTrx);
		result = Services.get(IPricingBL.class).createInitialResult(pricingCtx);
	}	// MProductPricing

	/** Logger */
	protected final Logger log = LogManager.getLogger(getClass());

	/**
	 * Calculate Price
	 * 
	 * @return true if calculated
	 */
	public boolean calculatePrice()
	{
		return calculatePrice(true);
	}	// calculatePrice

	private boolean calculatePrice(boolean recalculate)
	{
		if (recalculate || !calculationExecuted)
		{
			final IPricingBL pricingService = Services.get(IPricingBL.class);
			result = pricingService.calculatePrice(pricingCtx);
			calculationExecuted = true;
		}
		return result.isCalculated();
	}	// calculatePrice

	private void resetResult()
	{
		calculationExecuted = false;
	}

	/**
	 * Is Tax Included
	 * 
	 * @return tax included
	 */
	public boolean isTaxIncluded()
	{
		return result.isTaxIncluded();
	}	// isTaxIncluded

	/**************************************************************************
	 * Calculate Discount Percentage based on Standard/List Price
	 * 
	 * @return Discount
	 */
	public BigDecimal getDiscount()
	{
		return result.getDiscount();
	}	// getDiscount

	/**************************************************************************
	 * Get Product ID
	 * 
	 * @return id
	 */
	public int getM_Product_ID()
	{
		return pricingCtx.getM_Product_ID();
	}

	/**
	 * Get PriceList ID
	 * 
	 * @return pl
	 */
	public int getM_PriceList_ID()
	{
		return pricingCtx.getM_PriceList_ID();
	}	// getM_PriceList_ID

	/**
	 * Set PriceList
	 * 
	 * @param M_PriceList_ID pl
	 */
	public void setM_PriceList_ID(int M_PriceList_ID)
	{
		pricingCtx.setM_PriceList_ID(M_PriceList_ID);
		resetResult();
	}	// setM_PriceList_ID

	/**
	 * Get PriceList Version
	 * 
	 * @return plv
	 */
	public int getM_PriceList_Version_ID()
	{
		return result.getM_PriceList_Version_ID();
	}	// getM_PriceList_Version_ID

	/**
	 * Set PriceList Version
	 * 
	 * @param M_PriceList_Version_ID plv
	 */
	public void setM_PriceList_Version_ID(int M_PriceList_Version_ID)
	{
		pricingCtx.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		result.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		resetResult();
	}	// setM_PriceList_Version_ID

	/**
	 * Get Price Date
	 * 
	 * @return date
	 */
	public Timestamp getPriceDate()
	{
		return pricingCtx.getPriceDate();
	}	// getPriceDate

	/**
	 * Set Price Date
	 * 
	 * @param priceDate date
	 */
	public void setPriceDate(Timestamp priceDate)
	{
		pricingCtx.setPriceDate(priceDate);
		resetResult();
	}	// setPriceDate

	/**
	 * Get Precision
	 * 
	 * @return precision - -1 = no rounding
	 */
	public int getPrecision()
	{
		return result.getPrecision();
	}	// getPrecision

	/**
	 * Round
	 * 
	 * @param bd number
	 * @return rounded number
	 */
	private BigDecimal round(BigDecimal bd)
	{
		final int precision = result.getPrecision();
		if (precision != IPricingResult.NO_PRECISION	// -1 = no rounding
				&& bd.scale() > precision)
		{
			return bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		return bd;
	}	// round

	/**************************************************************************
	 * Get C_UOM_ID
	 * 
	 * @return uom
	 */
	public int getC_UOM_ID()
	{
		calculatePrice(false);
		return result.getPrice_UOM_ID();
	}

	/**
	 * Get Price List
	 * 
	 * @return list
	 */
	public BigDecimal getPriceList()
	{
		calculatePrice(false);
		return round(result.getPriceList());
	}

	/**
	 * Get Price Std
	 * 
	 * @return std
	 */
	public BigDecimal getPriceStd()
	{
		calculatePrice(false);
		return round(result.getPriceStd());
	}

	/**
	 * Get Price Limit
	 * 
	 * @return limit
	 */
	public BigDecimal getPriceLimit()
	{
		calculatePrice(false);
		return round(result.getPriceLimit());
	}

	/**
	 * Get Price List Currency
	 * 
	 * @return currency
	 */
	public int getC_Currency_ID()
	{
		calculatePrice(false);
		return result.getC_Currency_ID();
	}

	/**
	 * Is Price List enforded?
	 * 
	 * @return enforce limit
	 */
	public boolean isEnforcePriceLimit()
	{
		calculatePrice(false);
		return result.isEnforcePriceLimit();
	}	// isEnforcePriceLimit

	/**
	 * Is a DiscountSchema active?
	 * 
	 * @return active Discount Schema
	 */
	public boolean isDiscountSchema()
	{
		return !result.isDisallowDiscount()
				&& result.isUsesDiscountSchema();
	}	// isDiscountSchema

	/**
	 * Is the Price Calculated (i.e. found)?
	 * 
	 * @return calculated
	 */
	public boolean isCalculated()
	{
		return result.isCalculated();
	}	// isCalculated

	/**
	 * Convenience method to get priceStd with the discount already subtracted. Note that the result matches the former behavior of {@link #getPriceStd()}.
	 * 
	 * @return
	 */
	// metas
	public BigDecimal mkPriceStdMinusDiscount()
	{
		calculatePrice(false);
		return Services.get(IOrderLineBL.class).subtractDiscount(result.getPriceStd(), result.getDiscount(), result.getPrecision());
	}

	@Override
	public String toString()
	{
		return "MProductPricing ["
				+ pricingCtx
				+ ", " + result
				+ "]";
	}

	public int getC_BPartner_ID()
	{
		return pricingCtx.getC_BPartner_ID();
	}

	public BigDecimal getQty()
	{
		return pricingCtx.getQty();
	}

	public int getAD_Table_ID()
	{
		return pricingCtx.getAD_Table_ID();
	}

	public void setAD_Table_ID(int adTableId)
	{
		pricingCtx.setAD_Table_ID(adTableId);
	}

	public int getRecord_ID()
	{
		return pricingCtx.getRecord_ID();
	}

	public void setRecord_ID(int recordId)
	{
		pricingCtx.setRecord_ID(recordId);
	}
	
	public void setConvertPriceToContextUOM(boolean convertPriceToContextUOM)
	{
		pricingCtx.setConvertPriceToContextUOM(convertPriceToContextUOM);
	}

	public Object getReferencedObject()
	{
		return pricingCtx.getReferencedObject();
	}

	public void setReferencedObject(Object referencedObject)
	{
		pricingCtx.setReferencedObject(referencedObject);
	}
	// metas: end

	public int getC_TaxCategory_ID()
	{
		return result.getC_TaxCategory_ID();
	}
	
	public boolean isManualPrice()
	{
		return pricingCtx.isManualPrice();
	}

	public void setManualPrice(boolean manualPrice)
	{
		pricingCtx.setManualPrice(manualPrice);
	}
}	// MProductPrice
