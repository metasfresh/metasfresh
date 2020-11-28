package de.metas.material.event.commons;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;

import de.metas.util.NumberUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@EqualsAndHashCode(doNotUseGetters = true)
public final class AttributesKeyPart implements Comparable<AttributesKeyPart>
{
	public static final AttributesKeyPart ALL = newForSpecialType(AttributeKeyPartType.All);
	public static final AttributesKeyPart OTHER = newForSpecialType(AttributeKeyPartType.Other);
	public static final AttributesKeyPart NONE = newForSpecialType(AttributeKeyPartType.None);

	static AttributesKeyPart parseString(@NonNull final String stringRepresentation)
	{
		try
		{
			final int equalsIdx = stringRepresentation.indexOf("=");
			if (equalsIdx < 0)
			{
				final int attributeValueIdOrSpecial = Integer.parseInt(stringRepresentation.trim());
				return ofInteger(attributeValueIdOrSpecial);
			}
			else
			{
				final String attributeIdStr = stringRepresentation.substring(0, equalsIdx);
				final AttributeId attributeId = AttributeId.ofRepoId(Integer.parseInt(attributeIdStr));
				final String value = stringRepresentation.substring(equalsIdx + 1);
				return ofAttributeIdAndValue(attributeId, value);
			}
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("stringRepresentation", stringRepresentation);
		}
	}

	public static AttributesKeyPart ofInteger(final int attributeValueIdOrSpecial)
	{
		if (attributeValueIdOrSpecial <= 0)
		{
			return ofSpecialCode(attributeValueIdOrSpecial);
		}
		else
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoId(attributeValueIdOrSpecial);
			return ofAttributeValueId(attributeValueId);
		}
	}

	private static AttributesKeyPart ofSpecialCode(final int specialCode)
	{
		if (specialCode == ALL.specialCode)
		{
			return ALL;
		}
		else if (specialCode == OTHER.specialCode)
		{
			return OTHER;
		}
		else if (specialCode == NONE.specialCode)
		{
			return NONE;
		}
		else
		{
			throw new AdempiereException("Invalid special code: " + specialCode);
		}
	}

	public static AttributesKeyPart ofAttributeValueId(@NonNull final AttributeValueId attributeValueId)
	{
		final String stringRepresentation = String.valueOf(attributeValueId.getRepoId());
		final int specialCode = -1;
		final AttributeId attributeId = null;
		final String value = null;
		return new AttributesKeyPart(AttributeKeyPartType.AttributeValueId, stringRepresentation, specialCode, attributeValueId, attributeId, value);
	}

	public static AttributesKeyPart ofStringAttribute(@NonNull final AttributeId attributeId, @Nullable final String valueStr)
	{
		return ofAttributeIdAndValue(attributeId, normalizeStringValue(valueStr));
	}

	public static String normalizeStringValue(final String valueStr)
	{
		return valueStr != null ? valueStr : "";
	}

	public static AttributesKeyPart ofNumberAttribute(@NonNull final AttributeId attributeId, @Nullable final BigDecimal valueBD)
	{
		return ofAttributeIdAndValue(attributeId, normalizeNumberValue(valueBD));
	}

	public static String normalizeNumberValue(@Nullable final BigDecimal valueBD)
	{
		return valueBD != null ? NumberUtils.stripTrailingDecimalZeros(valueBD).toString() : "";

	}

	public static AttributesKeyPart ofDateAttribute(@NonNull final AttributeId attributeId, @Nullable final LocalDate valueDate)
	{
		final String valueStr = normalizeDateValue(valueDate);
		return ofAttributeIdAndValue(attributeId, valueStr);
	}

	public static String normalizeDateValue(@Nullable final LocalDate valueDate)
	{
		return valueDate != null ? valueDate.toString() : "";
	}

	public static AttributesKeyPart ofAttributeIdAndValue(@NonNull final AttributeId attributeId, @NonNull final String value)
	{
		final String stringRepresentation = attributeId.getRepoId() + "=" + value;
		final int specialCode = -1;
		final AttributeValueId attributeValueId = null;
		return new AttributesKeyPart(AttributeKeyPartType.AttributeIdAndValue, stringRepresentation, specialCode, attributeValueId, attributeId, value);
	}

	private static AttributesKeyPart newForSpecialType(final AttributeKeyPartType type)
	{
		final int specialCode = type.getSpecialCode();
		final String stringRepresentation = String.valueOf(specialCode);
		final AttributeValueId attributeValueId = null;
		final AttributeId attributeId = null;
		final String value = null;
		return new AttributesKeyPart(type, stringRepresentation, specialCode, attributeValueId, attributeId, value);
	}

	private final AttributeKeyPartType type;
	private final String stringRepresentation;
	private final int specialCode;
	private final AttributeValueId attributeValueId;
	private final AttributeId attributeId;
	private final String value;

	private AttributesKeyPart(
			@NonNull final AttributeKeyPartType type,
			@NonNull final String stringRepresentation,
			final int specialCode,
			final AttributeValueId attributeValueId,
			final AttributeId attributeId,
			final String value)
	{
		this.type = type;
		this.stringRepresentation = stringRepresentation;

		this.specialCode = specialCode;
		this.attributeValueId = attributeValueId;
		this.attributeId = attributeId;
		this.value = value;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return stringRepresentation;
	}

	@Override
	public int compareTo(@NonNull final AttributesKeyPart other)
	{
		final int cmp1 = compareNullsLast(getAttributeValueIdOrSpecialCodeOrNull(), other.getAttributeValueIdOrSpecialCodeOrNull());
		if (cmp1 != 0)
		{
			return cmp1;
		}

		final int cmp2 = compareNullsLast(getAttributeIdOrNull(), getAttributeIdOrNull());
		if (cmp2 != 0)
		{
			return cmp2;
		}

		return stringRepresentation.compareTo(other.stringRepresentation);
	}

	private Integer getAttributeValueIdOrSpecialCodeOrNull()
	{
		if (type == AttributeKeyPartType.AttributeValueId)
		{
			return attributeValueId.getRepoId();
		}
		else if (type == AttributeKeyPartType.All
				|| type == AttributeKeyPartType.Other
				|| type == AttributeKeyPartType.None)
		{
			return specialCode;
		}
		else
		{
			return null;
		}
	}

	private Integer getAttributeIdOrNull()
	{
		return AttributeKeyPartType.AttributeIdAndValue == type ? attributeId.getRepoId() : null;
	}

	private static int compareNullsLast(final Integer i1, final Integer i2)
	{
		if (i1 == null)
		{
			return +1;
		}
		else if (i2 == null)
		{
			return -1;
		}
		else
		{
			return i1 - i2;
		}
	}
}
