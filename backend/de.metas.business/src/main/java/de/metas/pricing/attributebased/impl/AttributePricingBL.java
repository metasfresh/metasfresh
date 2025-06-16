package de.metas.pricing.attributebased.impl;

import com.google.common.collect.ImmutableList;
import de.metas.pricing.IPricingAttribute;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class AttributePricingBL implements IAttributePricingBL
{
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

	@Override
	public void addToASI(@NonNull final IAttributeSetInstanceAware asiAware, final List<IPricingAttribute> pricingAttributes)
	{
		if (asiAware.getM_AttributeSetInstance_ID() <= 0)
		{
			return;
		}

		if (pricingAttributes == null || pricingAttributes.isEmpty())
		{
			return;
		}

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeSetInstance asi = asiAware.getM_AttributeSetInstance();
		for (final IPricingAttribute pricingAttribute : pricingAttributes)
		{
			if (pricingAttribute.getAttributeValue() != null)
			{
				final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asiAware.getM_AttributeSetInstance_ID());
				attributeSetInstanceBL.getCreateAttributeInstance(asiId, pricingAttribute.getAttributeValue());
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

		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final AttributeSetId overrideAttributeSetId = Services.get(IProductBL.class).getAttributeSetId(productId);

		final I_M_AttributeSetInstance resultASI = attributesRepo.prepareCopy(productPriceASI)
				.overrideAttributeSetId(overrideAttributeSetId)
				// IMPORTANT: copy only those which are not empty (task #1272)
				// NOTE: At the moment we use only the M_AttributeValue_ID so that's why we check only that field
				.filter(ai -> ai.getM_AttributeValue_ID() > 0)
				//
				.copy();
		return resultASI;
	}

	@Override
	public List<IPricingAttribute> extractPricingAttributes(@NonNull final I_M_ProductPrice productPrice)
	{
		final I_M_AttributeSetInstance productPriceASI = productPrice.getM_AttributeSetInstance();
		if (productPriceASI == null || productPriceASI.getM_AttributeSetInstance_ID() <= 0)
		{
			return ImmutableList.of();
		}

		return attributesRepo.retrieveAttributeInstances(productPriceASI)
				.stream()
				.map(this::createPricingAttribute)
				.collect(GuavaCollectors.toImmutableList());
	}

	private PricingAttribute createPricingAttribute(final I_M_AttributeInstance instance)
	{
		final I_M_Attribute attribute = attributesRepo.getAttributeById(instance.getM_Attribute_ID());
		final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(instance.getM_AttributeValue_ID());
		final AttributeListValue attributeValue = attributeValueId != null
				? attributesRepo.retrieveAttributeValueOrNull(attribute, attributeValueId)
				: null;

		return new PricingAttribute(attribute, attributeValue);
	}

	// task 08839
	private final static ModelDynAttributeAccessor<IAttributeSetInstanceAware, IProductPriceAware> DYN_ATTR_IProductPriceAware = new ModelDynAttributeAccessor<>(IProductPriceAware.class);

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
	public void setDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware, final IProductPriceAware productPriceAware)
	{
		if (asiAware == null)
		{
			return; // nothing to do
		}
		DYN_ATTR_IProductPriceAware.setValue(asiAware, productPriceAware);
	}

	@Override
	public Optional<IProductPriceAware> getDynAttrProductPriceAttributeAware(final IAttributeSetInstanceAware asiAware)
	{
		return DYN_ATTR_IProductPriceAware.getValueIfExists(asiAware);
	}

	@Value
	private static class PricingAttribute implements IPricingAttribute
	{
		I_M_Attribute attribute;
		AttributeListValue attributeValue;

		private PricingAttribute(@NonNull final I_M_Attribute attribute, @Nullable final AttributeListValue attributeValue)
		{
			this.attribute = attribute;
			this.attributeValue = attributeValue;
		}
	}
}
