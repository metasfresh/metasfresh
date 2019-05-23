package de.metas.handlingunits.age;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.springframework.stereotype.Service;

@Service
public class AgeAttributesService
{
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

	private final CCache<Integer, AgeValues> cache = CCache.<Integer, AgeValues>builder()
			.tableName(I_M_AttributeValue.Table_Name)
			.build();

	public AgeValues getAgeValues()
	{
		return cache.getOrLoad(0, this::retrieveAgeValues);
	}

	@SuppressWarnings("UnstableApiUsage")
	@NonNull
	private AgeValues retrieveAgeValues()
	{
		final AttributeId ageId = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_Age);
		final I_M_Attribute age = attributesRepo.getAttributeById(ageId);

		final ImmutableSet<Integer> agesInMonths = attributesRepo.retrieveAttributeValues(age)
				.stream()
				.map(it -> Integer.valueOf(it.getValue()))
				.sorted()
				.collect(ImmutableSet.toImmutableSet());

		return AgeValues.ofAgeInMonths(agesInMonths);
	}
}
