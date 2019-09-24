package org.adempiere.mm.attributes.api;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet.Builder;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public final class AttributesKeys
{

	/**
	 * @return see {@link #createAttributesKeyFromASIAllAttributeValues(int)} for why we return an {@link Optional}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromAttributeSet(
			@NonNull final IAttributeSet attributeSet)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final int[] attributeValueIds = attributeSet.getAttributes().stream()
				.sorted(Comparator.comparing(I_M_Attribute::getM_Attribute_ID))
				.map(attribute -> attributesRepo.retrieveAttributeValueOrNull(
						attribute,
						attributeSet.getValueAsString(attribute)))
				.filter(Predicates.notNull())
				.mapToInt(I_M_AttributeValue::getM_AttributeValue_ID)
				.toArray();

		if (attributeValueIds.length == 0)
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofAttributeValueIds(attributeValueIds));
	}

	/**
	 * @return and optional that is empty if no attribute values could be extracted from the given {@code attributeSetInstanceId}.<br>
	 *         In that case it's up to the caller to interpret the empty result.<br>
	 *         That can for example be done using using {@link Optional#orElse(Object)} with {@link AttributesKey#NONE}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIAllAttributeValues(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return createAttributesKeyWithFilter(attributeSetInstanceId, Predicates.alwaysTrue());
	}

	/**
	 * Similar to {@link #createAttributesKeyFromASIAllAttributeValues(int)}, but only attributes flagged as "storage relevant" are considered.
	 * <p>
	 * Please make sure the output of this method is in sync with the DB function @{code GenerateASIStorageAttributesKey}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIStorageAttributes(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId.isNone())
		{
			return Optional.empty();
		}

		final IAttributeSetInstanceBL asiService = Services.get(IAttributeSetInstanceBL.class);
		return createAttributesKeyWithFilter(attributeSetInstanceId, asiService::isStorageRelevant);
	}

	private static Optional<AttributesKey> createAttributesKeyWithFilter(
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final Predicate<? super I_M_AttributeInstance> additionalFilter)
	{
		if (attributeSetInstanceId.isNone())
		{
			return Optional.empty();
		}

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final I_M_AttributeSetInstance attributeSetInstance = attributesRepo.getAttributeSetInstanceById(attributeSetInstanceId);

		final int[] attributeValueIds = attributesRepo.retrieveAttributeInstances(attributeSetInstance)
				.stream()
				.filter(ai -> ai.getM_AttributeValue_ID() > 0)
				.filter(additionalFilter)
				// no point in sorting; AttributesKey.ofAttributeValueIds(..) does its own sorting.
				.mapToInt(I_M_AttributeInstance::getM_AttributeValue_ID)
				.toArray();

		if (attributeValueIds.length == 0)
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofAttributeValueIds(attributeValueIds));
	}

	public AttributeSetInstanceId createAttributeSetInstanceFromAttributesKey(@NonNull final AttributesKey attributesKey)
	{
		if (attributesKey.isNone())
		{
			return AttributeSetInstanceId.NONE;
		}

		final ImmutableAttributeSet attributeSet = createAttributeSetFromStorageAttributesKey(attributesKey);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIFromAttributeSet(attributeSet);

		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	public ImmutableAttributeSet createAttributeSetFromStorageAttributesKey(@NonNull final AttributesKey attributesKey)
	{
		final Builder builder = ImmutableAttributeSet.builder();
		for (final I_M_AttributeValue attributeValueRecord : extractAttributeSetFromStorageAttributesKey(attributesKey))
		{
			builder.attributeValue(attributeValueRecord);
		}
		return builder.build();
	}

	private List<I_M_AttributeValue> extractAttributeSetFromStorageAttributesKey(@NonNull final AttributesKey attributesKey)
	{
		final Collection<Integer> attributeValueIds = attributesKey.getAttributeValueIds();
		if (attributeValueIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeValue.class)
				.addInArrayFilter(I_M_AttributeValue.COLUMN_M_AttributeValue_ID, attributeValueIds)
				.orderBy(I_M_AttributeValue.COLUMN_M_AttributeValue_ID)
				.create()
				.list(I_M_AttributeValue.class);
	}
}
