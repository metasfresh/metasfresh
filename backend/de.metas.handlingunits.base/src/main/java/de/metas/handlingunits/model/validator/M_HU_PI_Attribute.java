/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.model.validator;

import de.metas.handlingunits.HuPackingInstructionsAttributeId;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_HU_PI_Attribute.class)
@Component
public class M_HU_PI_Attribute
{
	private final HUUniqueAttributesService service;

	public M_HU_PI_Attribute(@NonNull final HUUniqueAttributesService service)
	{
		this.service = service;
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_M_HU_PI_Attribute.COLUMNNAME_IsUnique)
	public void handleHUUniqueAttributes(final I_M_HU_PI_Attribute huPIAttribute)
	{
		final boolean isUnique = huPIAttribute.isUnique();
		final HuPackingInstructionsAttributeId huPiAttributeId = HuPackingInstructionsAttributeId.ofRepoId(huPIAttribute.getM_HU_PI_Attribute_ID());

		if (isUnique)
		{
			service.validateHUUniqueAttribute(huPiAttributeId);
			service.createOrUpdateHUUniqueAttribute(huPiAttributeId);
		}
		else
		{
			service.deleteHUUniqueAttributesForHUPIAttribute(huPiAttributeId);
		}

		service.updateLinkedHUAttributes(huPiAttributeId, isUnique);
	}
}
