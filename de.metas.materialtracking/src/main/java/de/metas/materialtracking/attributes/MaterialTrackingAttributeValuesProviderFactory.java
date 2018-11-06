package de.metas.materialtracking.attributes;

import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.compiere.model.I_M_Attribute;

public class MaterialTrackingAttributeValuesProviderFactory
		implements IAttributeValuesProviderFactory
{
	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final I_M_Attribute attribute)
	{
		return new MaterialTrackingAttributeValuesProvider(attribute);
	}
}
