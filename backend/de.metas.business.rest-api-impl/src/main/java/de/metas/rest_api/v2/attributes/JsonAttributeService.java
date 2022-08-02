/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.attributes;

import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class JsonAttributeService
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	@NonNull
	public Optional<AttributeSetInstanceId> computeAttributeSetInstanceFromJson(@Nullable final JsonAttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null || attributeSetInstance.getAttributeInstances().isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableAttributeSet.Builder attributeSetBuilder = ImmutableAttributeSet.builder();

		attributeSetInstance.getAttributeInstances()
				.forEach(jsonAttribute -> attributeSetBuilder.attributeValue(AttributeCode.ofString(jsonAttribute.getAttributeCode()), jsonAttribute.getValue()));

		final I_M_AttributeSetInstance newASI = attributeSetInstanceBL.createASIFromAttributeSet(attributeSetBuilder.build());

		return Optional.of(AttributeSetInstanceId.ofRepoId(newASI.getM_AttributeSetInstance_ID()));
	}
}
