package de.metas.pricing.attributebased.impl;

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

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import com.google.common.base.Optional;

import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IAttributePricingDAO;
import de.metas.pricing.attributebased.IProductPriceAttributeAware;
import de.metas.pricing.attributebased.I_M_ProductPrice;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute_Line;
import de.metas.product.IProductBL;

public class AttributePricingBL implements IAttributePricingBL
{

	@Override
	public I_M_ProductPrice_Attribute getPriceAttribute(final Properties ctx,
			final int m_Product_ID,
			final int m_AttributeSetInstance_ID,
			final I_M_PriceList_Version priceListVersion,
			final String trxName)
	{
		if (m_AttributeSetInstance_ID <= 0)
		{
			return null;
		}

		final I_M_AttributeSetInstance attributeSetInstance = InterfaceWrapperHelper.create(ctx, m_AttributeSetInstance_ID, I_M_AttributeSetInstance.class, trxName);

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final IAttributePricingDAO attributePricingDAO = Services.get(IAttributePricingDAO.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(priceListDAO.retrieveProductPriceOrNull(priceListVersion, m_Product_ID), I_M_ProductPrice.class);
		if (productPrice == null
				|| !productPrice.isAttributeDependant() // task 08804
		)
		{
			return null;
		}

		final List<I_M_AttributeInstance> instances = attributeDAO.retrieveAttributeInstances(attributeSetInstance);
		final List<I_M_ProductPrice_Attribute> productPriceAttributes = attributePricingDAO.retrieveFilteredPriceAttributes(productPrice);

		for (final I_M_ProductPrice_Attribute productPriceAttribute : productPriceAttributes)
		{

			if (isValidProductPriceAttribute(productPriceAttribute, instances))
			{
				return productPriceAttribute;
			}
		}
		return null;

	}

	@Override
	public boolean isValidProductPriceAttribute(
			final I_M_ProductPrice_Attribute productPriceAttribute,
			final List<I_M_AttributeInstance> instances)
	{
		boolean isValid = true;
		final List<I_M_ProductPrice_Attribute_Line> attributeLines = Services.get(IAttributePricingDAO.class).retrieveAttributeLines(productPriceAttribute);
		for (final I_M_AttributeInstance instance : instances)
		{
			for (final I_M_ProductPrice_Attribute_Line line : attributeLines)
			{
				if (line.getM_Attribute_ID() != instance.getM_Attribute_ID())
				{
					continue;
				}

				if (!line.getM_AttributeValue().equals(instance.getM_AttributeValue()))
				{
					isValid = false;
					break;
				}
				else
				{
					// We need at least one match to consider the attribute pricing valid.
					isValid = true;
					break;
				}
			}
			if (!isValid)
			{
				break;
			}
		}
		return isValid;
	}

	@Override
	public void createUpdateProductPriceAttributes(final Properties ctx,
			final I_M_PriceList_Version targetPriceListVersion,
			final Predicate<de.metas.adempiere.model.I_M_ProductPrice> newProductPricesFilter,
			final Iterator<de.metas.adempiere.model.I_M_ProductPrice> oldProductPrices,
			final String trxName)
	{
		if (targetPriceListVersion.getM_Pricelist_Version_Base_ID() < 1)
		{
			// No base pricelist. Nothing to do.
			return;
		}

		while (oldProductPrices.hasNext())
		{

			final I_M_ProductPrice oldProductPrice = InterfaceWrapperHelper.create(oldProductPrices.next(), I_M_ProductPrice.class);

			//
			// Skip old product price records which are not attribute dependent
			if (!oldProductPrice.isAttributeDependant())
			{
				continue;
			}

			final I_M_ProductPrice newProductPrice = InterfaceWrapperHelper.create(
					Services.get(IPriceListDAO.class).retrieveProductPriceOrNull(targetPriceListVersion, oldProductPrice.getM_Product_ID()),
					I_M_ProductPrice.class);

			if (newProductPrice == null)
			{
				// New M_ProductPrice was not found for old M_ProductPrice
				// Case: productPriceIterator contains all product prices from base price list version but the process decided to not copy all
				// => Skip it
				continue;
			}

			if (newProductPricesFilter != null && !newProductPricesFilter.evaluate(newProductPrice))
			{
				// New M_ProductPrice shall be skipped per filter's request
				continue;
			}

			//
			// Make sure new product price is attribute dependent
			// NOTE: old product price was already evaluated and it is attribute dependent
			newProductPrice.setIsAttributeDependant(true);
			InterfaceWrapperHelper.save(newProductPrice);

			//
			// Create Product Price Attribute records
			final List<I_M_ProductPrice_Attribute> productPriceAttributes = Services.get(IAttributePricingDAO.class).retrieveAllPriceAttributes(oldProductPrice);
			for (final I_M_ProductPrice_Attribute productPriceAttribute : productPriceAttributes)
			{
				final I_M_ProductPrice_Attribute newProductPriceAttribute = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice_Attribute.class, trxName);
				InterfaceWrapperHelper.copyValues(productPriceAttribute, newProductPriceAttribute);
				newProductPriceAttribute.setM_ProductPrice_ID(newProductPrice.getM_ProductPrice_ID());
				newProductPriceAttribute.setIsActive(productPriceAttribute.isActive());
				InterfaceWrapperHelper.save(newProductPriceAttribute);

				final List<I_M_ProductPrice_Attribute_Line> attributeLines = Services.get(IAttributePricingDAO.class).retrieveAttributeLines(productPriceAttribute);
				for (final I_M_ProductPrice_Attribute_Line attributeLine : attributeLines)
				{
					final I_M_ProductPrice_Attribute_Line newAttributeLine = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice_Attribute_Line.class, trxName);
					InterfaceWrapperHelper.copyValues(attributeLine, newAttributeLine);
					newAttributeLine.setM_ProductPrice_Attribute(newProductPriceAttribute);
					newAttributeLine.setIsActive(attributeLine.isActive());
					InterfaceWrapperHelper.save(newAttributeLine);
				}
			}
		}

	}

	@Override
	public void addToASI(final IPricingResult pricingResult, final IAttributeSetInstanceAware asiAware)
	{
		Check.assumeNotNull(pricingResult, "Param 'pricingResult is not null");
		Check.assumeNotNull(asiAware, "Param 'asiAware is not null");

		if (asiAware.getM_AttributeSetInstance_ID() <= 0)
		{
			return;
		}
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_AttributeSetInstance asi = asiAware.getM_AttributeSetInstance();
		for (final IPricingAttribute pricingAttribute : pricingResult.getPricingAttributes())
		{
			if (pricingAttribute.getAttributeValue() != null)
			{
				attributeSetInstanceBL.getCreateAttributeInstance(asi, pricingAttribute.getAttributeValue());
			}
		}
		attributeSetInstanceBL.setDescription(asi);
		InterfaceWrapperHelper.save(asi);
	}

	@Override
	public I_M_AttributeSetInstance generateASI(final I_M_ProductPrice_Attribute productPriceAttribute)
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_AttributeSetInstance resultASI = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, productPriceAttribute);

		//
		// Get & Set M_AttributeSet_ID
		final org.compiere.model.I_M_ProductPrice productPrice = productPriceAttribute.getM_ProductPrice();
		final I_M_Product product = productPrice.getM_Product();
		final int attributeSetId = Services.get(IProductBL.class).getM_AttributeSet_ID(product);
		resultASI.setM_AttributeSet_ID(attributeSetId);

		//
		// Save ASI (header)
		InterfaceWrapperHelper.save(resultASI);

		//
		// Create ASI Lines (i.e. M_AttributeInstaces)
		final List<I_M_ProductPrice_Attribute_Line> attributeLines = Services.get(IAttributePricingDAO.class).retrieveAttributeLines(productPriceAttribute);
		for (final I_M_ProductPrice_Attribute_Line attributeLine : attributeLines)
		{
			attributeSetInstanceBL.getCreateAttributeInstance(resultASI, attributeLine.getM_AttributeValue());
		}

		//
		// Build and set ASI description
		attributeSetInstanceBL.setDescription(resultASI);
		InterfaceWrapperHelper.save(resultASI);

		return resultASI;
	}

	@Override
	public I_M_ProductPrice_Attribute getDefaultAttributePriceOrNull(
			final int productId,
			final I_M_PriceList_Version priceListVersion,
			final boolean strictDefault
			)
	{
		Check.assume(productId > 0, "Product is not null");
		Check.assumeNotNull(priceListVersion, "Pricelist version is not null");

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final IAttributePricingDAO attributePricingDAO = Services.get(IAttributePricingDAO.class);

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(priceListDAO.retrieveProductPrice(priceListVersion, productId), I_M_ProductPrice.class);

		final de.metas.pricing.attributebased.I_M_ProductPrice_Attribute defaultProductPriceAttribute = attributePricingDAO.retrieveDefaultProductPriceAttribute(productPrice, strictDefault);
		return InterfaceWrapperHelper.create(defaultProductPriceAttribute, I_M_ProductPrice_Attribute.class);
	}

	@Override
	public void addToPricingAttributes(final I_M_ProductPrice_Attribute productPriceAttribute, final List<IPricingAttribute> pricingAttributes)
	{
		final IAttributePricingDAO attributePricingDAO = Services.get(IAttributePricingDAO.class);
		final List<I_M_ProductPrice_Attribute_Line> attributeLines = attributePricingDAO.retrieveAttributeLines(productPriceAttribute);

		for (final I_M_ProductPrice_Attribute_Line attributeLine : attributeLines)
		{
			pricingAttributes.add(new IPricingAttribute()
			{
				@Override
				public I_M_AttributeValue getAttributeValue()
				{
					return attributeLine.getM_AttributeValue();
				}

				@Override
				public I_M_Attribute getAttribute()
				{
					return attributeLine.getM_Attribute();
				}

				@Override
				public String toString()
				{
					return "IPricingAttribute[Attribute=" + getAttribute() + ";AttributeValue=" + getAttributeValue() + "]";
				}
			});
		}
	}

	// task 08839
	private final static ModelDynAttributeAccessor<IAttributeSetInstanceAware, IProductPriceAttributeAware> DYN_ATTR_IProductPriceAttributeAware =
			new ModelDynAttributeAccessor<IAttributeSetInstanceAware, IProductPriceAttributeAware>(IProductPriceAttributeAware.class);

	@Override
	public void setDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware, final Optional<IProductPriceAttributeAware> hasExplicitASI)
	{
		if (asiAware == null)
		{
			return; // nothing to do
		}
		DYN_ATTR_IProductPriceAttributeAware.setValue(asiAware, hasExplicitASI.orNull());
	}

	@Override
	public Optional<IProductPriceAttributeAware> getDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware)
	{
		final IProductPriceAttributeAware value = DYN_ATTR_IProductPriceAttributeAware.getValue(asiAware, null);
		return Optional.fromNullable(value);
	}
}
