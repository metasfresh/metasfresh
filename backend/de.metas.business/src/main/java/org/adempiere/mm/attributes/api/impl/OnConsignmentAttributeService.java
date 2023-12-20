package org.adempiere.mm.attributes.api.impl;

import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class OnConsignmentAttributeService
{
	final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

	public AttributeId getOnConsignmentAttributeId()
	{
		return attributesRepo.retrieveAttributeIdByValueOrNull(AttributeConstants.OnConsignment);
	}

	public I_M_Attribute getOnConsignmentAttribute()
	{
		final AttributeId attributeId = getOnConsignmentAttributeId();
		return attributesRepo.getAttributeById(attributeId);
	}

	public boolean isOnConsignment(@Nullable final I_M_AttributeSetInstance asi)
	{
		return StringUtils.toBoolean(getOnConsignmentValueOrNull(asi));
	}

	private String getOnConsignmentValueOrNull(@Nullable final I_M_AttributeSetInstance asi)
	{
		if (asi == null)
		{
			return null;
		}

		final AttributeId onConsignmentAttributeId = getOnConsignmentAttributeId();
		if (onConsignmentAttributeId == null)
		{
			return null;
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance onConsignmentAI = attributesRepo.retrieveAttributeInstance(asiId, onConsignmentAttributeId);

		if (onConsignmentAI == null)
		{
			return null;
		}

		return onConsignmentAI.getValue();
	}
}
