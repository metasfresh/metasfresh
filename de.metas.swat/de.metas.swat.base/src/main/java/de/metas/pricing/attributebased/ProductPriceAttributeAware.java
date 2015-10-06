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


import javax.annotation.concurrent.Immutable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.DisplayType;

import com.google.common.base.Optional;

/**
 * {@link IProductPriceAttributeAware} plain and immutable implementation.
 * 
 * @author tsa
 *
 */
@Immutable
public class ProductPriceAttributeAware implements IProductPriceAttributeAware
{
	/**
	 * Introspects given model and returns {@link IProductPriceAttributeAware} is possible.
	 * 
	 * Basically a model to be {@link IProductPriceAttributeAware}
	 * <ul>
	 * <li>it has to implement that interface and in this case it will be returned right away.
	 * <li>or it needs to have the {@link IProductPriceAttributeAware#COLUMNNAME_M_ProductPrice_Attribute_ID} column.
	 * </ul>
	 * 
	 * @param model
	 * @return {@link IProductPriceAttributeAware}.
	 */
	public static final Optional<IProductPriceAttributeAware> ofModel(final Object model)
	{
		if (model == null)
		{
			return Optional.absent();
		}
		if (model instanceof IProductPriceAttributeAware)
		{
			final IProductPriceAttributeAware productPriceAttributeAware = (IProductPriceAttributeAware)model;
			return Optional.of(productPriceAttributeAware);
		}

		// If model does not have "M_ProductPrice_Attribute_ID" we consider that it's not ProductPriceAttributeAware
		if (!InterfaceWrapperHelper.hasModelColumnName(model, COLUMNNAME_M_ProductPrice_Attribute_ID))
		{
			return Optional.absent();
		}

		final Integer productPriceAttributeId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_M_ProductPrice_Attribute_ID);
		final boolean productPriceAttributeIdSet = productPriceAttributeId != null && productPriceAttributeId > 0;

		final boolean isExplicitProductPriceAttribute;
		if (InterfaceWrapperHelper.hasModelColumnName(model, COLUMNNAME_IsExplicitProductPriceAttribute))
		{
			final Object isExplicitObj = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_IsExplicitProductPriceAttribute);
			isExplicitProductPriceAttribute = DisplayType.toBoolean(isExplicitObj, false);
		}
		else
		{
			// In case the "IsExplicitProductPriceAttribute" column is missing,
			// we are considering it as Explicit if the actual M_ProductPrice_Attribute_ID is set.
			isExplicitProductPriceAttribute = productPriceAttributeIdSet;
		}

		final IProductPriceAttributeAware productPriceAttributeAware = new ProductPriceAttributeAware(
				isExplicitProductPriceAttribute,
				productPriceAttributeIdSet ? productPriceAttributeId : -1);
		return Optional.of(productPriceAttributeAware);
	}

	private final boolean isExplicitProductPriceAttribute;
	private final int productPriceAttributeId;

	private ProductPriceAttributeAware(final boolean isExplicitProductPriceAttribute, final int productPriceAttributeId)
	{
		super();
		this.isExplicitProductPriceAttribute = isExplicitProductPriceAttribute;
		this.productPriceAttributeId = productPriceAttributeId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean isExplicitProductPriceAttribute()
	{
		return isExplicitProductPriceAttribute;
	}

	@Override
	public int getM_ProductPrice_Attribute_ID()
	{
		return productPriceAttributeId;
	}
}
