package de.metas.product;

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
import java.util.Collection;
import java.util.Properties;

import org.adempiere.exceptions.ProductNotOnPriceListException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_PO;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;

import de.metas.adempiere.model.I_M_Product;

public interface IProductPA extends ISingletonService
{
	I_M_Product retrieveProduct(Properties ctx, String value, boolean throwEx, String trxName);

	I_M_Product retrieveProduct(Properties ctx, int productId, boolean throwEx, String trxName);

	Collection<MAttributeSetInstance> retrieveSerno(int productId, String text, String trxName);

	I_M_AttributeSet retrieveAttributeSet(int attributeSetId, String trxName);

	I_M_Product_PO retrieveProductPO(int productId, int bPartnerId, String trxName);

	/**
	 * Loads the lines of completed {@link I_M_Requisition}s for a product and warehouse.
	 * 
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return the requisition lines for the given product and warehouse.
	 */
	Collection<I_M_RequisitionLine> retrieveRequisitionlines(int productId, int warehouseId, String trxName);

	Collection<I_M_RequisitionLine> retrieveRequisitionlines(String docNo, String trxName);

	I_M_Requisition retrieveRequisition(int requisitionId, String trxName);

	Collection<I_M_Requisition> retrieveRequisitions(String docStatus, String trxName);

	I_M_Replenish retrieveReplenish(int productId, int warehouseId, String trxName);

	I_M_PriceList retrievePriceList(int priceListId, String trxName);

	I_M_PriceList_Version retrievePriceListVersion(Properties ctx, int plvId, String trxName);

	/**
	 * For the given M_PriceList_ID and C_Location_ID this method returns a price list that has the same {@link I_M_PricingSystem#COLUMNNAME_M_PricingSystem_ID} and
	 * {@link I_M_PriceList#COLUMNNAME_IsSOPriceList} as the pl with the given M_PriceList_ID and has the same {@link I_C_Location#COLUMNNAME_C_Country_ID} as the given C_Location_ID. If there is no
	 * such priceList, that price list with C_Location_ID=0 is returned. If there also isn't a PL with C_Location_ID=0, <code>null</code> is returned.
	 * 
	 * @param ctx
	 * @param priceListId
	 * @param locationId
	 * @param trxName
	 * @return
	 */
	MPriceList retrievePriceListBySibling(Properties ctx, int priceListId, int bPartnerLocationId, String trxName);

	/**
	 * 
	 * @param ctx
	 * @param pricingSystemId
	 * @param locationId
	 * @param isSOPriceList
	 * @param trxName
	 * @return the price list for the given pricing system and location or <code>null</code>.
	 */
	I_M_PriceList retrievePriceListByPricingSyst(Properties ctx, int pricingSystemId, int bPartnerLocationId, boolean isSOPriceList, String trxName);

	/**
	 * 
	 * @param productId
	 * @param bPartnerId
	 * @param priceListId
	 * @param qty
	 * @param soTrx
	 * @return
	 * @throws ProductNotOnPriceListException
	 */
	BigDecimal retrievePriceStd(int productId, int bPartnerId, int priceListId,
			BigDecimal qty, boolean soTrx);

	Collection<MProductPrice> retrieveProductPrices(int priceListVersionId, String trxName);

	Collection<I_M_ProductScalePrice> retrieveScalePrices(int productPriceId, String trxName);

	I_M_ProductScalePrice retrieveOrCreateScalePrices(int productPriceId, BigDecimal qty, boolean createNew, String trxName);

	I_M_ProductScalePrice createScalePrice(String trxName);

	MProduct retrieveCategoryProduct(Properties ctx, I_M_Product_Category category, String trxName);

	I_C_UOM retrieveProductUOM(Properties ctx, int productId);

}
