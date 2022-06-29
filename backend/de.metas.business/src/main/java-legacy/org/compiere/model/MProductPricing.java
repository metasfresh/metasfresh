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

import de.metas.currency.CurrencyPrecision;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
	public MProductPricing(
			OrgId orgId,
			int M_Product_ID, 
			int C_BPartner_ID,
			@Nullable CountryId countryId,
			BigDecimal Qty, 
			boolean isSOTrx)
	{
		pricingCtx = Services.get(IPricingBL.class).createInitialContext(OrgId.toRepoId(orgId), 
																		 M_Product_ID, 
																		 C_BPartner_ID, 
																		 0, 
																		 Qty, 
																		 isSOTrx);
		pricingCtx.setCountryId(countryId); // needed because we might unset the priceList

		result = Services.get(IPricingBL.class).createInitialResult(pricingCtx);
	}	// MProductPricing

	/** Logger */
	protected final Logger log = LogManager.getLogger(getClass());

	/**
	 * Calculate Price
	 *
	 * @return true if calculated
	 */
	public boolean recalculatePrice()
	{
		final boolean recalculate = true;
		return calculatePrice(recalculate);
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
	public Percent getDiscount()
	{
		return result.getDiscount();
	}	// getDiscount

	public ProductId getProductId()
	{
		return pricingCtx.getProductId();
	}

	/**
	 * Set PriceList
	 *
	 * @param M_PriceList_ID pl
	 */
	public void setM_PriceList_ID(int M_PriceList_ID)
	{
		setPriceListId(PriceListId.ofRepoIdOrNull(M_PriceList_ID));
	}	// setM_PriceList_ID

	public void setPriceListId(PriceListId priceListId)
	{
		pricingCtx.setPriceListId(priceListId);
		resetResult();
	}

	/**
	 * Set PriceList Version
	 *
	 * @param M_PriceList_Version_ID plv
	 */
	public void setM_PriceList_Version_ID(int M_PriceList_Version_ID)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(M_PriceList_Version_ID);
		pricingCtx.setPriceListVersionId(priceListVersionId);
		result.setPriceListVersionId(priceListVersionId);
		resetResult();
	}

	/**
	 * Set Price Date
	 *
	 * @param priceDate date
	 */
	public void setPriceDate(Timestamp priceDate)
	{
		pricingCtx.setPriceDate(TimeUtil.asLocalDate(priceDate));
		resetResult();
	}	// setPriceDate

	/**
	 * Round
	 *
	 * @param bd number
	 * @return rounded number
	 */
	private BigDecimal round(BigDecimal bd)
	{
		final CurrencyPrecision precision = result.getPrecision();
		if (precision != null)
		{
			return precision.roundIfNeeded(bd);
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
		return UomId.toRepoId(result.getPriceUomId());
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
	 * Is Price List enforded?
	 *
	 * @return enforce limit
	 */
	public boolean isEnforcePriceLimit()
	{
		calculatePrice(false);
		return result.getEnforcePriceLimit().isTrue();
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
	 */
	public BigDecimal mkPriceStdMinusDiscount()
	{
		calculatePrice(false);
		return result.getDiscount().subtractFromBase(result.getPriceStd(), result.getPrecision().toInt());
	}

	@Override
	public String toString()
	{
		return "MProductPricing ["
				+ pricingCtx
				+ ", " + result
				+ "]";
	}

	public void setConvertPriceToContextUOM(boolean convertPriceToContextUOM)
	{
		pricingCtx.setConvertPriceToContextUOM(convertPriceToContextUOM);
	}

	public void setReferencedObject(Object referencedObject)
	{
		pricingCtx.setReferencedObject(referencedObject);
	}
	// metas: end

	public int getC_TaxCategory_ID()
	{
		return TaxCategoryId.toRepoId(result.getTaxCategoryId());
	}

	public boolean isManualPrice()
	{
		return pricingCtx.getManualPriceEnabled().isTrue();
	}

	public void setManualPrice(boolean manualPrice)
	{
		pricingCtx.setManualPriceEnabled(manualPrice);
	}

	public void throwProductNotOnPriceListException()
	{
		throw new ProductNotOnPriceListException(pricingCtx);
	}
}	// MProductPrice
