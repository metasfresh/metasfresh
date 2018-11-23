package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
		final int[] attributeValueIds = attributeSet.getAttributes().stream()
				.sorted(Comparator.comparing(I_M_Attribute::getM_Attribute_ID))
				.map(attribute -> Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(
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
	public static Optional<AttributesKey> createAttributesKeyFromASIAllAttributeValues(final int attributeSetInstanceId)
	{
		return createAttributesKeyWithFilter(
				attributeSetInstanceId,
				ai -> true);
	}

	/**
	 * Similar to {@link #createAttributesKeyFromASIAllAttributeValues(int)}, but only attributes flagged as "storage relevant" are considered.
	 *
	 * @return see {@link #createAttributesKeyFromASIAllAttributeValues(int)}
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIStorageAttributes(final int attributeSetInstanceId)
	{
		return createAttributesKeyWithFilter(
				attributeSetInstanceId,
				ai -> ai.getM_Attribute().isStorageRelevant());
	}

	private static Optional<AttributesKey> createAttributesKeyWithFilter(
			final int attributeSetInstanceId,
			@NonNull final Predicate<? super I_M_AttributeInstance> additionalFilter)
	{
		if (attributeSetInstanceId == AttributeConstants.M_AttributeSetInstance_ID_None)
		{
			return Optional.empty();
		}

		final I_M_AttributeSetInstance attributeSetInstance = load(attributeSetInstanceId, I_M_AttributeSetInstance.class);

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final int[] attributeValueIds = attributeDAO.retrieveAttributeInstances(attributeSetInstance).stream()
				.filter(ai -> ai.getM_AttributeValue_ID() > 0)
				.filter(additionalFilter)
				.sorted(Comparator.comparing(I_M_AttributeInstance::getM_Attribute_ID))
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
		final IAttributeSet attributeSet = createAttributeSetFromStorageAttributesKey(attributesKey);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIFromAttributeSet(attributeSet);

		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	public IAttributeSet createAttributeSetFromStorageAttributesKey(@NonNull final AttributesKey attributesKey)
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
