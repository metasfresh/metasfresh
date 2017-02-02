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
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.util.Check;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

public class WeightTareAdjustAttributeValueCallout extends AbstractWeightAttributeValueCallout
{
	/**
	 * Fires WeightGross recalculation based on existing WeightNet & the new WeightTare value
	 */
	@Override
	public void onValueChanged0(final IAttributeValueContext attributeValueContext_IGNORED,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute_IGNORED,
			final Object valueOld_IGNORED,
			final Object valueNew_IGNORED)
	{
		recalculateWeightNet(attributeSet);
	}

	/**
	 * Returns the summed weight of the packing material of the given <code>attributeSet</code>'s HU.
	 * <p>
	 * NOTE: does <b>not</b> descent into sub-HUs to also add their tare values. This is good, because this value is used in bottom-up-propagation, i.e. the children tare values are added during
	 * propagation.
	 */
	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueInitialDefault)
	{
		// we don't support a value different from null
		Check.assumeNull(valueInitialDefault, "valueInitialDefault null");

		return BigDecimal.ZERO;
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	}

	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return true;
	}

	@Override
	public BigDecimal generateNumericValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return BigDecimal.ZERO;
	}

	@Override
	protected boolean isExecuteCallout(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		return !Check.equals(valueOld, valueNew);
	}
}
