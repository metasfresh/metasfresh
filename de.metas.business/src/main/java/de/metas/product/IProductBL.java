package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

public interface IProductBL extends ISingletonService
{
	int getUOMPrecision(I_M_Product product);

	default int getUOMPrecision(final int productId)
	{
		Check.assume(productId > 0, "productId > 0");
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		return getUOMPrecision(product);
	}

	String getMMPolicy(I_M_Product product);

	/**
	 * @param product
	 * @return true if item
	 */
	boolean isItem(I_M_Product product);
	
	default boolean isItem(final int productId)
	{
		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		return isItem(product);
	}

	/**
	 * @param product
	 * @return true if service (resource, online), i.e. not {@link #isItem(I_M_Product)}
	 */
	boolean isService(I_M_Product product);

	/**
	 * Check if product is an Item and Stocked
	 *
	 * @return true if stocked and item
	 */
	boolean isStocked(I_M_Product product);

	boolean isStocked(int productId);

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 *
	 * @param product
	 * @return M_AttributeSet_ID or {@link IAttributeDAO#M_AttributeSet_ID_None}
	 */
	int getM_AttributeSet_ID(I_M_Product product);

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 *
	 * @param ctx
	 * @param productId
	 * @return M_AttributeSet_ID or {@link IAttributeDAO#M_AttributeSet_ID_None}
	 */
	int getM_AttributeSet_ID(Properties ctx, int productId);

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 *
	 * @param product
	 * @return
	 */
	I_M_AttributeSet getM_AttributeSet(I_M_Product product);

	I_M_AttributeSetInstance getCreateASI(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID);

	/**
	 * Get Product Costing Level
	 *
	 * @param as accounting schema
	 * @return product costing level
	 */
	String getCostingLevel(I_M_Product product, I_C_AcctSchema as);

	/**
	 * Get Product Costing Method
	 *
	 * @param C_AcctSchema_ID accounting schema ID
	 * @return product costing method
	 */
	String getCostingMethod(I_M_Product product, I_C_AcctSchema as);

	/** @return UOM used in material storage; never return null; */
	I_C_UOM getStockingUOM(I_M_Product product);

	/** @return UOM used in material storage; never return null; */
	I_C_UOM getStockingUOM(int productId);

	/**
	 * Gets product standard Weight in <code>uomTo</code>.
	 *
	 * @param product
	 * @param uomTo
	 * @return product's standard weight in <code>uomTo</code>
	 */
	BigDecimal getWeight(I_M_Product product, I_C_UOM uomTo);

	/**
	 * Checks if given product is a Trading Product.
	 *
	 * A product is considered a Trading Product when it is Purchased and it is also Sold.
	 *
	 * @param product
	 * @return true if it's a trading product
	 */
	boolean isTradingProduct(I_M_Product product);
}
