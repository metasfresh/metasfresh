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
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import de.metas.i18n.ITranslatableString;
import org.adempiere.mm.attributes.AttributeSetId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableMap;

import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface IProductBL extends ISingletonService
{
	I_M_Product getById(ProductId productId);
	
	ProductId getProductIdByValue(String productValue);

	UOMPrecision getUOMPrecision(I_M_Product product);

	UOMPrecision getUOMPrecision(ProductId productId);

	String getMMPolicy(I_M_Product product);

	String getMMPolicy(int productId);

	/**
	 * @param product
	 * @return true if service (resource, online), i.e. not {@link #isItem(I_M_Product)}
	 * @deprecated please use {@link #getProductType(ProductId)} and {@link ProductType#isService()} instead
	 */
	@Deprecated
	boolean isService(I_M_Product product);

	/**
	 * Check if product is an Item and Stocked
	 *
	 * @return true if stocked and item
	 */
	boolean isStocked(I_M_Product product);

	boolean isStocked(@Nullable ProductId productId);

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
	 * @return product/product category's attribute set or null
	 */
	I_M_AttributeSet getAttributeSetOrNull(ProductId productId);

	I_M_AttributeSetInstance getCreateASI(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID);

	/**
	 * @return UOM used in material storage; never return null;
	 */
	I_C_UOM getStockUOM(I_M_Product product);

	/**
	 * @return UOM used in material storage; never return null;
	 */
	I_C_UOM getStockUOM(int productId);

	/**
	 * @return UOM used in material storage; never return null;
	 */
	default I_C_UOM getStockUOM(@NonNull final ProductId productId)
	{
		return getStockUOM(productId.getRepoId());
	}

	@NonNull
	default UomId getStockUOMId(@NonNull final ProductId productId)
	{
		return getStockUOMId(productId.getRepoId());
	}

	@NonNull
	default UomId getStockUOMId(final int productId)
	{
		return UomId.ofRepoId(getStockUOM(productId).getC_UOM_ID());
	}

	Optional<UomId> getCatchUOMId(ProductId productId);

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
	 * <p>
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

	boolean isASIMandatory(ProductId productId, boolean isSOTrx);

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

	ImmutableMap<ProductId, String> getProductValues(Set<ProductId> productIds);

	String getProductName(ProductId productId);

	ProductType getProductType(ProductId productId);

	ProductCategoryId getDefaultProductCategoryId();

	ITranslatableString getProductNameTrl(@NonNull ProductId productId);
}
