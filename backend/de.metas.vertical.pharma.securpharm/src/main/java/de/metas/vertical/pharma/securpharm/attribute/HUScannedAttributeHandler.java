/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.attribute;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGeneratorAdapter;
import org.adempiere.mm.attributes.spi.IAttributeValueHandler;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.vertical.pharma.securpharm.service.SecurPharmService;

public class HUScannedAttributeHandler implements IAttributeValueCallout, IAttributeValueHandler, IAttributeValueGeneratorAdapter
{
	@Override
	public void onValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueOld, final Object valueNew)
	{
		// nothing
	}

	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueInitialDefault)
	{
		return SecurPharmAttributesStatus.UNKNOW.getCode();
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return true;
	}

	@Override
	public boolean isAlwaysEditableUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isDisplayedUI(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return Adempiere.getBean(SecurPharmService.class).hasConfig();
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_List;
	}

}
