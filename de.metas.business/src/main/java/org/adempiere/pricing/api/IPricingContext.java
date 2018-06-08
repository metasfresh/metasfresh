package org.adempiere.pricing.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;

/**
 * Pricing context
 *
 * @author tsa
 *
 */
public interface IPricingContext extends IContextAware
{
	int getM_Product_ID();

	I_M_Product getM_Product();

	int getM_PricingSystem_ID();

	I_M_PricingSystem getM_PricingSystem();

	int getM_PriceList_ID();

	int getM_PriceList_Version_ID();

	/** @retun price list version or null */
	I_M_PriceList_Version getM_PriceList_Version();

	/**
	 * Gets pricing evaluation date.
	 *
	 * In case no pricing evaluation date was set while the pricing context was build, "now" will be returned.
	 *
	 * @return pricing evaluation date; never returns null.
	 */
	Timestamp getPriceDate();

	int getC_UOM_ID();

	int getC_Currency_ID();

	int getC_BPartner_ID();

	BigDecimal getQty();

	boolean isSOTrx();

	int getAD_Table_ID();

	int getRecord_ID();

	int getPP_Product_BOM_ID();

	int getPP_Product_BOMLine_ID();

	boolean isDisallowDiscount();

	Object getReferencedObject();

	/**
	 * Creates a mutable copy of this pricing context
	 *
	 * @return new mutable copy
	 */
	IEditablePricingContext copy();

	/**
	 * @return context; never return null
	 */
	@Override
	Properties getCtx();

	@Override
	String getTrxName();

	/**
	 * If set, during pricing calculations, prices will be converted from {@link IPricingResult#getPrice_UOM_ID()} to {@link IPricingContext#getC_UOM_ID()}.
	 *
	 * @return true if prices needs to be converted from Price UOM to context UOM
	 */
	boolean isConvertPriceToContextUOM();

	<T> T getProperty(String propertyName, Class<T> clazz);

	/**
	 * Specifies if the pricing engine shall calculate a price or not.
	 *
	 * @return returns the context value or <code>null</code> if unspecified. In this case the pricing engine shall check if the references object&model has a <code>IsManualPrice</code> field to go
	 *         with.
	 */
	Boolean isManualPrice();


	int getC_Country_ID();

	I_C_Country getC_Country();
}
