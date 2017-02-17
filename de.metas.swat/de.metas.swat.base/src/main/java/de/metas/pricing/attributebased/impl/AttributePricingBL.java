package de.metas.pricing.attributebased.impl;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.product.IProductBL;

public class AttributePricingBL implements IAttributePricingBL
{
	@Override
	public void addToASI(final IAttributeSetInstanceAware asiAware, final List<IPricingAttribute> pricingAttributes)
	{
		Check.assumeNotNull(asiAware, "Param 'asiAware is not null");

		if (asiAware.getM_AttributeSetInstance_ID() <= 0)
		{
			return;
		}
		
		if(pricingAttributes == null || pricingAttributes.isEmpty())
		{
			return;
		}
		
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeSetInstance asi = asiAware.getM_AttributeSetInstance();
		for (final IPricingAttribute pricingAttribute : pricingAttributes)
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
	public I_M_AttributeSetInstance generateASI(final I_M_ProductPrice productPrice)
	{
		final I_M_AttributeSetInstance productPriceASI = productPrice.getM_AttributeSetInstance();
		if (productPriceASI == null || productPriceASI.getM_AttributeSetInstance_ID() <= 0)
		{
			return null;
		}

		final I_M_Product product = productPrice.getM_Product();
		int overrideM_AttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(product);

		final I_M_AttributeSetInstance resultASI = Services.get(IAttributeDAO.class).copy(productPriceASI, overrideM_AttributeSet_ID);
		return resultASI;
	}

	@Override
	public List<IPricingAttribute> extractPricingAttributes(final I_M_ProductPrice productPrice)
	{
		final I_M_AttributeSetInstance productPriceASI = productPrice.getM_AttributeSetInstance();
		if (productPriceASI == null || productPriceASI.getM_AttributeSetInstance_ID() <= 0)
		{
			return ImmutableList.of();
		}

		return Services.get(IAttributeDAO.class).retrieveAttributeInstances(productPriceASI)
				.stream()
				.map(instance -> createPricingAttribute(instance))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final IPricingAttribute createPricingAttribute(final I_M_AttributeInstance instance)
	{
		return new IPricingAttribute()
		{
			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper("IPricingAttribute")
						.add("attribute", getAttribute())
						.add("attributeValue", getAttributeValue())
						.toString();
			}

			@Override
			public I_M_AttributeValue getAttributeValue()
			{
				return instance.getM_AttributeValue();
			}

			@Override
			public I_M_Attribute getAttribute()
			{
				return instance.getM_Attribute();
			}
		};
	}

	// task 08839
	private final static ModelDynAttributeAccessor<IAttributeSetInstanceAware, IProductPriceAware> DYN_ATTR_IProductPriceAware = new ModelDynAttributeAccessor<IAttributeSetInstanceAware, IProductPriceAware>(IProductPriceAware.class);

	@Override
	public void setDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware, final Optional<IProductPriceAware> productPriceAware)
	{
		if (asiAware == null)
		{
			return; // nothing to do
		}
		DYN_ATTR_IProductPriceAware.setValue(asiAware, productPriceAware.orElse(null));
	}

	@Override
	public Optional<IProductPriceAware> getDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware)
	{
		return DYN_ATTR_IProductPriceAware.getValueIfExists(asiAware);
	}
}
