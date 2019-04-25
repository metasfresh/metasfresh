/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.attribute;

import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueHandler;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Attribute;

public class HUScannedAttributeHandler implements IAttributeValueCallout, IAttributeValueHandler
{
	@Override
	public void onValueChanged(IAttributeValueContext attributeValueContext, IAttributeSet attributeSet, I_M_Attribute attribute, Object valueOld, Object valueNew)
	{
	}

	@Override
	public Object generateSeedValue(IAttributeSet attributeSet, I_M_Attribute attribute, Object valueInitialDefault)
	{
		return ScannedAttributeValue.N.name();
	}

	@Override
	public boolean isReadonlyUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isAlwaysEditableUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isDisplayedUI(IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return Adempiere.getBean(SecurPharmService.class).hasConfig();
	}
}
