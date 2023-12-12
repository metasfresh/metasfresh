package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeBL;
import org.adempiere.mm.attributes.spi.IAttributeValueCalloutAdapter;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGeneratorAdapter;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Retrieve all vendor BPartners for {@link I_M_HU#getC_BPartner_ID()}.
 *
 */
public class HUVendorBPartnerAttributeHandler
		implements IAttributeValueGeneratorAdapter, IAttributeValueCalloutAdapter, IAttributeValuesProviderFactory
{

	private final IHUAttributesBL ihuAttributesBL = Services.get(IHUAttributesBL.class);

	@Override
	public String getAttributeValueType()
	{
		return HUVendorBPartnerAttributeValuesProvider.ATTRIBUTEVALUETYPE;
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final org.compiere.model.I_M_Attribute attribute)
	{
		return new HUVendorBPartnerAttributeValuesProvider(attribute);
	}

	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute, final Object valueInitialDefault)
	{
		// we don't support a value different from null
		Check.assumeNull(valueInitialDefault, "valueInitialDefault should be null");
		return HUVendorBPartnerAttributeValuesProvider.staticNullValue();
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		final I_M_HU hu = ihuAttributesBL.getM_HU_OrNull(attributeSet);
		if (hu == null)
		{
			// If there is no HU (e.g. ASI), consider it editable
			return false;
		}

		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			// Allow editing only Planning HUs
			return true;
		}

		return false;
	}
}
