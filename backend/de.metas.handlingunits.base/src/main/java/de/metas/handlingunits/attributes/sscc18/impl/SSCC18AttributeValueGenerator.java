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

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import java.util.Properties;

public class SSCC18AttributeValueGenerator extends AbstractAttributeValueGenerator
{

	private final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);

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

	@Override
	public String generateStringValue(
			final Properties ctx_IGNORED,
			@NonNull final IAttributeSet attributeSet,
			final I_M_Attribute attribute_IGNORED)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);

		// Just don't use the M_HU_ID as serial number. It introduced FUD as multiple packs can have the same HU and each pack needs an individual SSCC.
		final SSCC18 sscc18 = sscc18CodeBL.generate(OrgId.ofRepoIdOrAny(hu.getAD_Org_ID()));
		return sscc18.asString();
	}
}
