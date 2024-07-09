/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttribute;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.handlingunits.mobileui.config.MobileUIHUManager;
import de.metas.handlingunits.mobileui.config.MobileUIHUManagerRepository;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandlingUnitsManagerRestService
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final MobileUIHUManagerRepository mobileUIHUManagerRepository;

	@NonNull
	public JsonHU getHUTailoredByHUManager(
			@NonNull final JsonHU fullJsonHU,
			@NonNull final OrgId orgId)
	{
		final MobileUIHUManager mobileUIHUManager = mobileUIHUManagerRepository.getHUManagerConfig(orgId);

		return fullJsonHU.toBuilder()
				.attributes2(applyConfig(fullJsonHU.getAttributes2(), mobileUIHUManager))
				.build();
	}

	@NonNull
	private JsonHUAttributes applyConfig(
			@NonNull final JsonHUAttributes attributes,
			@NonNull final MobileUIHUManager config)
	{
		final ImmutableList<AttributeId> huManagerAttributeIds = config.getSortedAttributeIds();

		if (huManagerAttributeIds.isEmpty())
		{
			return attributes;
		}

		final ImmutableMap<AttributeId, AttributeCode> attributeId2Code = attributeDAO.getAttributeId2CodeByIds(huManagerAttributeIds);

		final ImmutableList<JsonHUAttribute> list = huManagerAttributeIds.stream()
				.map(attributeId2Code::get)
				.map(AttributeCode::getCode)
				.map(attributes::getAttributeByCode)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());

		return JsonHUAttributes.builder()
				.list(list)
				.build();
	}
}
