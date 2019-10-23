package org.adempiere.mm.attributes.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributeKeyPartType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
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
	//@formatter:off
	private static IAttributeDAO attributesRepo() { return Services.get(IAttributeDAO.class); }
	private static IAttributeSetInstanceBL asiService() { return Services.get(IAttributeSetInstanceBL.class); }
	//@formatter:on

	/**
	 * @return see {@link #createAttributesKeyFromASIAllAttributes(int)} for why we return an {@link Optional}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromAttributeSet(@NonNull final IAttributeSet attributeSet)
	{
		final Collection<I_M_Attribute> attributes = attributeSet.getAttributes();
		if (attributes.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<AttributesKeyPart> parts = attributes.stream()
				.map(attribute -> createAttributesKeyPart(attributeSet, attribute))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());

		if (parts.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofParts(parts));
	}

	private static AttributesKeyPart createAttributesKeyPart(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final String attributeKey = attribute.getValue();
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = attributeSet.getValueAsString(attributeKey);
			return AttributesKeyPart.ofStringAttribute(attributeId, valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal valueBD = attributeSet.getValueAsBigDecimal(attributeKey);
			return AttributesKeyPart.ofNumberAttribute(attributeId, valueBD);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate valueDate = attributeSet.getValueAsLocalDate(attributeKey);
			return AttributesKeyPart.ofDateAttribute(attributeId, valueDate);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = attributeSet.getAttributeValueIdOrNull(attributeKey);
			return attributeValueId != null
					? AttributesKeyPart.ofAttributeValueId(attributeValueId)
					: null;
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + attributeValueType)
					.appendParametersToMessage()
					.setParameter("attributeSet", attributeSet)
					.setParameter("attribute", attribute);
		}
	}

	/**
	 * @return and optional that is empty if no attribute values could be extracted from the given {@code attributeSetInstanceId}.<br>
	 *         In that case it's up to the caller to interpret the empty result.<br>
	 *         That can for example be done using using {@link Optional#orElse(Object)} with {@link AttributesKey#NONE}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIAllAttributes(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return createAttributesKeyFromASI(attributeSetInstanceId, Predicates.alwaysTrue());
	}

	/**
	 * Similar to {@link #createAttributesKeyFromASIAllAttributes(int)}, but only attributes flagged as "storage relevant" are considered.
	 * <p>
	 * Please make sure the output of this method is in sync with the DB function @{code GenerateASIStorageAttributesKey}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIStorageAttributes(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId.isNone())
		{
			return Optional.empty();
		}

		return createAttributesKeyFromASI(attributeSetInstanceId, asiService()::isStorageRelevant);
	}

	private static Optional<AttributesKey> createAttributesKeyFromASI(
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final Predicate<? super I_M_AttributeInstance> additionalFilter)
	{
		if (attributeSetInstanceId.isNone())
		{
			return Optional.empty();
		}

		final ImmutableSet<AttributesKeyPart> parts = attributesRepo().retrieveAttributeInstances(attributeSetInstanceId)
				.stream()
				.filter(additionalFilter)
				.map(ai -> createAttributesKeyPart(ai))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());

		if (parts.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofParts(parts));
	}

	private static AttributesKeyPart createAttributesKeyPart(final I_M_AttributeInstance ai)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesRepo().getAttributeById(attributeId);
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = ai.getValue();
			return AttributesKeyPart.ofStringAttribute(attributeId, valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final boolean isNull = InterfaceWrapperHelper.isNull(ai, I_M_AttributeInstance.COLUMNNAME_ValueNumber);
			final BigDecimal valueBD = isNull ? null : ai.getValueNumber();
			return AttributesKeyPart.ofNumberAttribute(attributeId, valueBD);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate valueDate = TimeUtil.asLocalDate(ai.getValueDate());
			return AttributesKeyPart.ofDateAttribute(attributeId, valueDate);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(ai.getM_AttributeValue_ID());
			return attributeValueId != null
					? AttributesKeyPart.ofAttributeValueId(attributeValueId)
					: null;
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + attributeValueType)
					.appendParametersToMessage()
					.setParameter("attributeInstance", ai)
					.setParameter("attribute", attribute);
		}
	}

	public AttributeSetInstanceId createAttributeSetInstanceFromAttributesKey(@NonNull final AttributesKey attributesKey)
	{
		if (attributesKey.isNone())
		{
			return AttributeSetInstanceId.NONE;
		}

		final ImmutableAttributeSet attributeSet = toImmutableAttributeSet(attributesKey);
		final I_M_AttributeSetInstance asi = asiService().createASIFromAttributeSet(attributeSet);
		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	public static ImmutableAttributeSet toImmutableAttributeSet(@NonNull final AttributesKey attributesKey)
	{
		try
		{
			final ImmutableAttributeSet.Builder builder = ImmutableAttributeSet.builder();
			final HashSet<AttributeValueId> attributeValueIds = new HashSet<>();

			for (final AttributesKeyPart part : attributesKey.getParts())
			{
				if (part.getType() == AttributeKeyPartType.AttributeIdAndValue)
				{
					final AttributeId attributeId = part.getAttributeId();
					final Object value = part.getValue();
					final AttributeValueId attributeValueId = null;

					builder.attributeValue(attributeId, value, attributeValueId);
				}
				else if (part.getType() == AttributeKeyPartType.AttributeValueId)
				{
					attributeValueIds.add(part.getAttributeValueId());
				}
			}

			for (final AttributeListValue attributeValueRecord : attributesRepo().retrieveAttributeValuesByIds(attributeValueIds))
			{
				builder.attributeValue(attributeValueRecord);
			}

			return builder.build();
		}
		catch (final RuntimeException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("attributesKey", attributesKey);
		}
	}
}
