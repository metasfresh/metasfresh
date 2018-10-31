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

import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.uom.UomId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IProductBL extends ISingletonService
{
	int getUOMPrecision(I_M_Product product);

	int getUOMPrecision(int productId);

	String getMMPolicy(I_M_Product product);

	String getMMPolicy(int productId);

	/**
	 * @param product
	 * @return true if item
	 */
	boolean isItem(I_M_Product product);

	boolean isItem(ProductId productId);

	default boolean isItem(int productId)
	{
		return isItem(ProductId.ofRepoId(productId));
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

	boolean isDiverse(ProductId productId);

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 * 
	 * @return {@link AttributeSetId}; never returns null
	 */
	AttributeSetId getAttributeSetId(I_M_Product product);

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 *
	 * @return {@link AttributeSetId}; never returns null
	 */
	AttributeSetId getAttributeSetId(ProductId productId);

	default AttributeSetId getAttributeSetId(final int productId)
	{
		return getAttributeSetId(ProductId.ofRepoId(productId));
	}

	/**
	 * If the product has an Attribute Set take it from there; If not, take it from the product category of the product
	 *
	 * @param product
	 * @return
	 */
	I_M_AttributeSet getM_AttributeSet(I_M_Product product);

	I_M_AttributeSetInstance getCreateASI(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID);

	CostingLevel getCostingLevel(I_M_Product product, I_C_AcctSchema as);

	CostingLevel getCostingLevel(int productId, I_C_AcctSchema as);

	CostingLevel getCostingLevel(int productId, int acctSchemaId);

	/**
	 * Get Product Costing Method
	 *
	 * @param C_AcctSchema_ID accounting schema ID
	 * @return product costing method
	 */
	CostingMethod getCostingMethod(I_M_Product product, I_C_AcctSchema as);

	CostingMethod getCostingMethod(int productId, I_C_AcctSchema as);

	/** @return UOM used in material storage; never return null; */
	I_C_UOM getStockingUOM(I_M_Product product);

	/** @return UOM used in material storage; never return null; */
	I_C_UOM getStockingUOM(int productId);

	/** @return UOM used in material storage; never return null; */
	default I_C_UOM getStockingUOM(@NonNull final ProductId productId)
	{
		return getStockingUOM(productId.getRepoId());
	}

	default UomId getStockingUOMId(@NonNull final ProductId productId)
	{
		return getStockingUOMId(productId.getRepoId());
	}

	default UomId getStockingUOMId(final int productId)
	{
		return UomId.ofRepoId(getStockingUOM(productId).getC_UOM_ID());
	}

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

	/**
	 * Check if ASI is mandatory
	 *
	 * @param product
	 * @param isSOTrx is outgoing trx?
	 * @return true if ASI is mandatory, false otherwise
	 */
	boolean isASIMandatory(I_M_Product product, boolean isSOTrx);

	boolean isASIMandatory(int productId, boolean isSOTrx);

	/**
	 * Has the Product Instance Attribute
	 *
	 * @return true if instance attributes
	 */
	boolean isInstanceAttribute(ProductId productId);

	boolean isProductInCategory(ProductId productId, ProductCategoryId expectedProductCategoryId);

	String getProductValueAndName(ProductId productId);

	@Deprecated
	default String getProductValueAndName(final int productId)
	{
		return getProductValueAndName(ProductId.ofRepoIdOrNull(productId));
	}

	String getProductValue(ProductId productId);

	String getProductName(ProductId productId);
}
