package de.metas.handlingunits.attributes.sscc18.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.ecs.xhtml.code;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.model.I_M_HU;

public class SSCC18AttributeValueGenerator extends AbstractAttributeValueGenerator
{

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
	}

	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return true;
	}

	/**
	 * @param ctx the context used to determine (from {@link code AD_Client_ID} and {@code AD_Org_ID}) which manufacturer code to use.
	 */
	@Override
	public String generateStringValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		final ISSCC18CodeBL creator = Services.get(ISSCC18CodeBL.class);

		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);

		// We use M_HU_ID for SSCC18 serial number (06852)
		final int serialNumber = hu.getM_HU_ID();
		Check.errorIf(serialNumber <= 0, "M_HU_ID={} for M_HU={}", serialNumber, hu);

		final SSCC18 sscc18 = creator.generate(ctx, serialNumber);
		return creator.toString(sscc18, false); // humanReadable=false
	}
}
