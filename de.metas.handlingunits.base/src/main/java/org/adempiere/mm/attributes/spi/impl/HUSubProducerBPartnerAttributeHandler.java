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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeBL;
import org.adempiere.mm.attributes.spi.AttributeValueCalloutAdapter;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeValue;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

/**
 * Retrieve all subproducer BPartners for {@link I_M_HU#getC_BPartner_ID()}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06135_Gesch%C3%A4ftspartner_Unterlieferanten_%28108858601576%29
 */
public class HUSubProducerBPartnerAttributeHandler
		extends AttributeValueCalloutAdapter
		implements IAttributeValueGenerator, IAttributeValuesProviderFactory
{

	public HUSubProducerBPartnerAttributeHandler()
	{
		super();
	}

	@Override
	public String getAttributeValueType()
	{
		return HUSubProducerBPartnerAttributeValuesProvider.ATTRIBUTEVALUETYPE;
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final org.compiere.model.I_M_Attribute attribute)
	{
		return new HUSubProducerBPartnerAttributeValuesProvider(attribute);
	}

	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public String generateStringValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public BigDecimal generateNumericValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public Date generateDateValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public I_M_AttributeValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	/**
	 * Calls {@link ISubProducerAttributeBL#updateAttributesOnSubProducerChanged(Properties, IAttributeSet, boolean)} if at least one of valueOld and valueNew can be converted to not-zero BigDecimals.
	 *
	 * @param valueOld old attribute value. converted to BigDecimal using {@link ConversionHelper#toBigDecimal(Object)}
	 * @param valueNew analog to valueOld
	 */
	@Override
	public void onValueChanged(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final org.compiere.model.I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		final BigDecimal valueOldBD = ConversionHelper.toBigDecimal(valueOld);
		final BigDecimal valueNewBD = ConversionHelper.toBigDecimal(valueNew);
		if (valueNewBD.signum() == 0 && valueOldBD.signum() == 0)
		{
			// nothing to change in this case
			return;
		}

		// task 08782: goal of this parameter: we don't want to reset a pre-existing value unless the procuser is actually changed.
		final boolean subProducerInitialized = valueNewBD.signum() != 0 && valueOldBD.signum() == 0;

		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		Services.get(ISubProducerAttributeBL.class).updateAttributesOnSubProducerChanged(ctx, attributeSet, subProducerInitialized);
	}

	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute, final Object valueInitialDefault)
	{
		// we don't support a value different from null
		Check.assumeNull(valueInitialDefault, "valueInitialDefault null");
		return HUSubProducerBPartnerAttributeValuesProvider.staticNullValue();
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU_OrNull(attributeSet);
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
