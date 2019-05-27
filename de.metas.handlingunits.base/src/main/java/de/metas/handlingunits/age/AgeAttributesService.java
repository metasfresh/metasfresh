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

import java.util.List;
import java.util.Optional;

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
		final List<I_M_AttributeValue> allAgeValues = getAllAgeValues();
		final ImmutableSet<Integer> agesInMonths = allAgeValues
				.stream()
				.map(it -> Integer.valueOf(it.getValue()))
				.sorted()
				.collect(ImmutableSet.toImmutableSet());

		return AgeValues.ofAgeInMonths(agesInMonths);
	}

	@SuppressWarnings("OptionalIsPresent")
	public int computeDefaultAge()
	{
		final List<I_M_AttributeValue> allAgeValues = getAllAgeValues();

		final Optional<I_M_AttributeValue> nullFieldValueOpt = allAgeValues.stream()
				.filter(I_M_AttributeValue::isNullFieldValue)
				.findFirst();

		final int defaultAge;
		if (nullFieldValueOpt.isPresent())
		{
			defaultAge = Integer.parseInt(nullFieldValueOpt.get().getValue());
		}
		else
		{
			defaultAge = Integer.parseInt(allAgeValues.get(0).getValue());
		}
		return defaultAge;
	}

	private List<I_M_AttributeValue> getAllAgeValues()
	{
		final AttributeId ageId = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_Age);
		final I_M_Attribute age = attributesRepo.getAttributeById(ageId);

		return attributesRepo.retrieveAttributeValues(age);
	}
}
