package de.metas.pricing.attributebased;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;

import com.google.common.base.Optional;

import de.metas.adempiere.model.I_M_ProductPrice;

public interface IAttributePricingBL extends ISingletonService
{

	/**
	 * Gets attribute pricing based on product and attribute set instance. This method uses {@link IAttributePricingDAO#retrieveFilteredPriceAttributes(de.metas.adempiere.model.I_M_ProductPrice)}.
	 * This way certain {@code M_ProductPrice_Attribute} records can be ignored by the generic implementation, if they are supposed to be handled by dedicated code in other modules.
	 * 
	 * @param ctx
	 * @param m_Product_ID
	 * @param m_AttributeSetInstance_ID
	 * @param priceListVersion
	 * @return
	 */
	I_M_ProductPrice_Attribute getPriceAttribute(Properties ctx,
			int m_Product_ID,
			int m_AttributeSetInstance_ID,
			I_M_PriceList_Version priceListVersion,
			String trxName);

	/**
	 * Creates new attribute pricing entries based on the base pricelist version.
	 * 
	 * @param ctx
	 * @param targetPriceListVersion the (new) PLV for which we create/update prices
	 * @param newProductPricesFilter filter used to decide if an {@link I_M_ProductPrice} in <code>targetPriceListVersion</code> shall be considered. If filter returns false then it won't be
	 *            considered. If filter is null, everything will be considered
	 * @param oldProductPrices the old product prices from the base PLV
	 * @param trxName
	 */
	void createUpdateProductPriceAttributes(Properties ctx,
			I_M_PriceList_Version targetPriceListVersion,
			Predicate<de.metas.adempiere.model.I_M_ProductPrice> newProductPricesFilter,
			Iterator<de.metas.adempiere.model.I_M_ProductPrice> oldProductPrices,
			String trxName);

	/**
	 * @param productPriceAttribute
	 * @param instances
	 * @return true if each {@link I_M_ProductPrice_Attribute_Line} of {@link I_M_ProductPrice_Attribute} is matching at least one of the <code>instances</code>.
	 */
	boolean isValidProductPriceAttribute(I_M_ProductPrice_Attribute productPriceAttribute, List<I_M_AttributeInstance> instances);

	/**
	 * Generates a new ASI based on {@link I_M_ProductPrice_Attribute}'s attributes.
	 * 
	 * @param productPriceAttribute
	 * @return new generated ASI
	 */
	I_M_AttributeSetInstance generateASI(I_M_ProductPrice_Attribute productPriceAttribute);

	/**
	 * For each {@link IPricingAttribute} of the given <code>pricingResult</code>, add or update one {@link I_M_AttributeInstance} to the given <code>asiAware</code>'s ASI.<br>
	 * If the given asiAware had no ASI or if the given pricing result has an empty list of {@link IPricingAttribute}s, then do nothing.
	 * 
	 * @param pricingResult
	 * @param asiAware
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	void addToASI(IPricingResult pricingResult, IAttributeSetInstanceAware asiAware);

	/**
	 * 
	 * 
	 * @param productId
	 * @param priceListVersion
	 * @param strictDefault see {@link IAttributePricingDAO#retrieveDefaultProductPriceAttribute(de.metas.adempiere.model.I_M_ProductPrice, boolean)} for an explanation of this parameter
	 * @return
	 */
	I_M_ProductPrice_Attribute getDefaultAttributePriceOrNull(
			int productId,
			I_M_PriceList_Version priceListVersion,
			boolean strictDefault
			);

	/**
	 * For each line of the given <code>productPriceAttribute</code>, add a {@link IPricingAttribute} to the given <code>pricingAttributes</code> list.
	 * 
	 * @param productPriceAttribute
	 * @param pricingAttributes
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	void addToPricingAttributes(I_M_ProductPrice_Attribute productPriceAttribute, List<IPricingAttribute> pricingAttributes);

	/**
	 * Attach the given <code>productPriceAttributeAware</code> to the given <code>asiAware</code>. Note that the implemationtion uses {@link org.adempiere.model.InterfaceWrapperHelper} at the core,
	 * so the given asiAware needs to be handled by its internal wrappers (PO or GridTab or POJO).
	 * 
	 * @param asiAware
	 * @param productPriceAttributeAware
	 * @task http://dewiki908/mediawiki/index.php/08839_Import_of_Orders_per_Excel-Pricelist_%28100553254746%29
	 */
	void setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware, Optional<IProductPriceAttributeAware> productPriceAttributeAware);

	/**
	 * See {@link #setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware, Optional)}.
	 * 
	 * @param asiAware
	 * @return
	 */
	Optional<IProductPriceAttributeAware> getDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware);
}
