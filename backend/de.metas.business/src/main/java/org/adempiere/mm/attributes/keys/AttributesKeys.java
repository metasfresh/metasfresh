/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.event.commons.AttributeKeyPartType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public final class AttributesKeys
{
	//@formatter:off
	private static IAttributeDAO attributesRepo() { return Services.get(IAttributeDAO.class); }
	private static IAttributeSetInstanceBL asiService() { return Services.get(IAttributeSetInstanceBL.class); }
	//@formatter:on

	/**
	 * @return see {@link #createAttributesKeyFromASIAllAttributes(AttributeSetInstanceId)} for why we return an {@link Optional}.
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
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (parts.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofParts(parts));
	}

	@Nullable
	private static AttributesKeyPart createAttributesKeyPart(@NonNull final IAttributeSet attributeSet, @NonNull final I_M_Attribute attribute)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = attributeSet.getValueAsString(attributeCode);

			return Check.isNotBlank(valueStr)
					? AttributesKeyPart.ofStringAttribute(attributeId, valueStr)
					: null;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal valueBD = attributeSet.getValueAsBigDecimal(attributeCode);
			return BigDecimal.ZERO.compareTo(CoalesceUtil.coalesceNotNull(valueBD, BigDecimal.ZERO)) != 0
					? AttributesKeyPart.ofNumberAttribute(attributeId, valueBD)
					: null;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate valueDate = attributeSet.getValueAsLocalDate(attributeCode);
			return valueDate != null
					? AttributesKeyPart.ofDateAttribute(attributeId, valueDate)
					: null;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			return Optional.ofNullable(attributeSet.getAttributeValueIdOrNull(attributeCode))
					.map(AttributesKeyPart::ofAttributeValueId)
					.orElse(null);
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
	 * In that case it's up to the caller to interpret the empty result.<br>
	 * That can for example be done using using {@link Optional#orElse(Object)} with {@link AttributesKey#NONE}.
	 */
	public static Optional<AttributesKey> createAttributesKeyFromASIAllAttributes(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return createAttributesKeyFromASI(attributeSetInstanceId, i_m_attributeInstance -> true);
	}

	/**
	 * Similar to {@link #createAttributesKeyFromASIAllAttributes(AttributeSetInstanceId)}, but only attributes flagged as "storage relevant" are considered.
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
				.map(AttributesKeys::createAttributesKeyPart)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (parts.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(AttributesKey.ofParts(parts));
	}

	@Nullable
	private static AttributesKeyPart createAttributesKeyPart(@NonNull final I_M_AttributeInstance ai)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesRepo().getAttributeById(attributeId);
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = ai.getValue();
			if (Check.isBlank(valueStr))
			{
				return null;
			}
			return AttributesKeyPart.ofStringAttribute(attributeId, valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{

			final BigDecimal valueBD = ai.getValueNumber();

			if (BigDecimal.ZERO.compareTo(CoalesceUtil.coalesceNotNull(valueBD, BigDecimal.ZERO)) == 0)
			{
				return null;
			}

			return AttributesKeyPart.ofNumberAttribute(attributeId, valueBD);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate valueDate = TimeUtil.asLocalDate(ai.getValueDate());
			if (valueDate == null)
			{
				return null;
			}
			return AttributesKeyPart.ofDateAttribute(attributeId, valueDate);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(ai.getM_AttributeValue_ID());
			if (attributeValueId == null)
			{
				return null;
			}
			return AttributesKeyPart.ofAttributeValueId(attributeValueId);
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + attributeValueType)
					.appendParametersToMessage()
					.setParameter("attributeInstance", ai)
					.setParameter("attribute", attribute);
		}
	}

	@NonNull
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

	public static AttributesKey pruneEmptyParts(@NonNull final AttributesKey attributesKey)
	{
		final ImmutableList<AttributesKeyPart> notBlankParts = attributesKey.getParts().stream()
				.filter(p -> Check.isNotBlank(p.getValue()))
				.collect(ImmutableList.toImmutableList());

		return AttributesKey.ofParts(notBlankParts);
	}
}
