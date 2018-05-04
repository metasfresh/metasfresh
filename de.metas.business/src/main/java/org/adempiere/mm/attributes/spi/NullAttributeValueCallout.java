package org.adempiere.mm.attributes.spi;

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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

/**
 * "Null" callout. Use <code>NullAttributeValueCallout.instance</code> if you need an instance.
 *
 */
public final class NullAttributeValueCallout implements IAttributeValueCallout
{
	public static final NullAttributeValueCallout instance = new NullAttributeValueCallout();

	private NullAttributeValueCallout()
	{
		super();
	}

	/**
	 * Method does nothing
	 */
	@Override
	public void onValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueOld, final Object valueNew)
	{
		// nothing
	}

	/**
	 * @return <code>bull</code>
	 */
	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueInitialDefault)
	{
		return valueInitialDefault;
	}

	/**
	 * @return <code>false</code>
	 */
	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isAlwaysEditableUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isDisplayedUI(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return true;
	}
}
