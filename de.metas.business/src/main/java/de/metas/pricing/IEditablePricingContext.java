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

import org.adempiere.bpartner.BPartnerId;

import de.metas.pricing.conditions.PricingConditionsBreak;

/**
 * A {@link IPricingContext} which also have setters.
 *
 * This object is used for creating the pricing context.
 */
public interface IEditablePricingContext extends IPricingContext
{
	/**
	 * Set's this context's referenced object to the given {@code referencedObject}, and set's this context's trxName to be the given referencedObject's trxName.
	 *
	 * @param referencedObject
	 */
	IEditablePricingContext setReferencedObject(final Object referencedObject);

	IEditablePricingContext setRecord_ID(final int record_ID);

	IEditablePricingContext setAD_Table_ID(final int aD_Table_ID);

	IEditablePricingContext setSOTrx(final boolean isSOTrx);

	IEditablePricingContext setQty(final BigDecimal qty);

	IEditablePricingContext setBPartnerId(final BPartnerId bpartnerId);

	IEditablePricingContext setC_Currency_ID(final int c_Currency_ID);

	IEditablePricingContext setC_UOM_ID(final int c_UOM_ID);

	IEditablePricingContext setPriceDate(final Timestamp priceDate);

	IEditablePricingContext setM_PricingSystem_ID(final int pricingSystemId);

	IEditablePricingContext setM_PriceList_Version_ID(final int m_PriceList_Version_ID);

	IEditablePricingContext setM_PriceList_ID(final int M_PriceList_ID);

	IEditablePricingContext setM_Product_ID(final int m_Product_ID);

	/**
	 * Set this to <code>true</code> to indicate to the pricing engine that discounts shall <b>not</b> be computed and applied to the result.
	 *
	 * @param disallowDiscount
	 */
	IEditablePricingContext setDisallowDiscount(boolean disallowDiscount);

	IEditablePricingContext setForcePricingConditionsBreak(PricingConditionsBreak forcePricingConditionsBreak);

	IEditablePricingContext setTrxName(String trxName);

	/**
	 *
	 * @param convertPriceToContextUOM
	 * @see IPricingContext#isConvertPriceToContextUOM()
	 */
	IEditablePricingContext setConvertPriceToContextUOM(boolean convertPriceToContextUOM);

	IEditablePricingContext setProperty(String propertyName, Object value);

	default IEditablePricingContext setProperty(String propertyName)
	{
		return setProperty(propertyName, Boolean.TRUE);
	}

	/**
	 * See {@link IPricingContext#isManualPrice()}.
	 *
	 * @param isManualPrice
	 */
	IEditablePricingContext setManualPrice(boolean isManualPrice);

	/**
	 * When setting this and {@link #setM_PricingSystem_ID(int)}, no <code>M_PriceList_ID</code> or <code>M_PriceListVersion_ID</code> needs to be set.
	 *
	 * @param c_Country_ID
	 */
	IEditablePricingContext setC_Country_ID(int c_Country_ID);

	IEditablePricingContext setFailIfNotCalculated(boolean failIfNotCalculated);

	IEditablePricingContext setSkipCheckingPriceListSOTrxFlag(boolean skipCheckingPriceListSOTrxFlag);
}
