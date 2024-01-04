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

import com.google.common.annotations.VisibleForTesting;
import com.jgoodies.common.base.Objects;
import de.metas.material.event.commons.AttributeKeyPartType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(doNotUseGetters = true)
final class AttributesKeyPartPattern implements Comparable<AttributesKeyPartPattern>
{
	static final AttributesKeyPartPattern ALL = newOfPart(AttributesKeyPart.ALL);
	static final AttributesKeyPartPattern OTHER = newOfPart(AttributesKeyPart.OTHER);
	static final AttributesKeyPartPattern NONE = newOfPart(AttributesKeyPart.NONE);

	static AttributesKeyPartPattern parseString(@NonNull final String stringRepresentation)
	{
		try
		{
			final int equalsIdx = stringRepresentation.indexOf("=");
			if (equalsIdx < 0)
			{
				final String stringRepresentationNorm = stringRepresentation.trim();
				if (stringRepresentationNorm.endsWith("*"))
				{
					final AttributeId attributeId = AttributeId.ofRepoId(Integer.parseInt(stringRepresentationNorm.substring(0, stringRepresentationNorm.length() - 1)));
					return ofAttributeId(attributeId);

				}
				else
				{
					final int attributeValueIdOrSpecial = Integer.parseInt(stringRepresentationNorm);
					return ofInteger(attributeValueIdOrSpecial);
				}
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

	static AttributesKeyPartPattern ofInteger(final int attributeValueIdOrSpecial)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofInteger(attributeValueIdOrSpecial));
	}

	static AttributesKeyPartPattern ofAttributeValueId(@NonNull final AttributeValueId attributeValueId)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofAttributeValueId(attributeValueId));
	}

	static AttributesKeyPartPattern ofStringAttribute(@NonNull final AttributeId attributeId, @Nullable final String valueStr)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofStringAttribute(attributeId, valueStr));
	}

	static AttributesKeyPartPattern ofNumberAttribute(@NonNull final AttributeId attributeId, @Nullable final BigDecimal valueBD)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofNumberAttribute(attributeId, valueBD));
	}

	static AttributesKeyPartPattern ofDateAttribute(@NonNull final AttributeId attributeId, @Nullable final LocalDate valueDate)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofDateAttribute(attributeId, valueDate));
	}

	@VisibleForTesting
	static AttributesKeyPartPattern ofAttributeId(@NonNull final AttributeId attributeId)
	{
		final AttributeKeyPartPatternType type = AttributeKeyPartPatternType.AttributeId;
		final int specialCode = -1;
		final String sqlLikePart = attributeId.getRepoId() + "=%";
		final AttributeValueId attributeValueId = null;
		final String value = null;
		return new AttributesKeyPartPattern(type, sqlLikePart, specialCode, attributeValueId, attributeId, value);
	}

	@VisibleForTesting
	static AttributesKeyPartPattern ofAttributeIdAndValue(@NonNull final AttributeId attributeId, @NonNull final String valueStr)
	{
		return ofAttributesKeyPart(AttributesKeyPart.ofAttributeIdAndValue(attributeId, valueStr));
	}

	static AttributesKeyPartPattern ofAttributesKeyPart(@NonNull final AttributesKeyPart part)
	{
		//
		// Try cached patterns first:
		final AttributeKeyPartType partType = part.getType();
		if (partType == AttributeKeyPartType.All)
		{
			return AttributesKeyPartPattern.ALL;
		}
		else if (partType == AttributeKeyPartType.Other)
		{
			return AttributesKeyPartPattern.OTHER;
		}
		else if (partType == AttributeKeyPartType.None)
		{
			return AttributesKeyPartPattern.NONE;
		}
		else
		{
			return newOfPart(part);
		}
	}

	private static AttributesKeyPartPattern newOfPart(@NonNull final AttributesKeyPart part)
	{
		final AttributeKeyPartPatternType type = toAttributeKeyPartPatternType(part.getType());
		final int specialCode = part.getSpecialCode();
		final String sqlLikePart = part.getStringRepresentation();
		final AttributeValueId attributeValueId = part.getAttributeValueId();
		final AttributeId attributeId = part.getAttributeId();
		final String value = part.getValue();
		return new AttributesKeyPartPattern(type, sqlLikePart, specialCode, attributeValueId, attributeId, value);
	}

	private static AttributeKeyPartPatternType toAttributeKeyPartPatternType(final AttributeKeyPartType partType)
	{
		if (partType == AttributeKeyPartType.All)
		{
			return AttributeKeyPartPatternType.All;
		}
		else if (partType == AttributeKeyPartType.Other)
		{
			return AttributeKeyPartPatternType.Other;
		}
		else if (partType == AttributeKeyPartType.None)
		{
			return AttributeKeyPartPatternType.None;
		}
		else if (partType == AttributeKeyPartType.AttributeValueId)
		{
			return AttributeKeyPartPatternType.AttributeValueId;
		}
		else if (partType == AttributeKeyPartType.AttributeIdAndValue)
		{
			return AttributeKeyPartPatternType.AttributeIdAndValue;
		}
		else
		{
			throw new AdempiereException("Cannot convert `" + partType + "` to " + AttributeKeyPartPatternType.class);
		}
	}

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final AttributeKeyPartPatternType type;

	private final int specialCode;
	private final AttributeValueId attributeValueId;
	private final AttributeId attributeId;
	private final String value;

	@Getter
	private final String sqlLikePart;

	private AttributesKeyPartPattern(
			@NonNull final AttributeKeyPartPatternType type,
			@NonNull final String sqlLikePart,
			final int specialCode,
			final AttributeValueId attributeValueId,
			final AttributeId attributeId,
			final String value)
	{
		this.type = type;
		this.sqlLikePart = sqlLikePart;

		if (type == AttributeKeyPartPatternType.AttributeValueId)
		{
			Check.assumeNotNull(attributeValueId, "Parameter attributeValueId is not null");
			this.specialCode = 0;
			this.attributeValueId = attributeValueId;
			this.attributeId = null;
			this.value = null;
		}
		else if (type == AttributeKeyPartPatternType.AttributeId)
		{
			Check.assumeNotNull(attributeId, "Parameter attributeId is not null");
			this.specialCode = 0;
			this.attributeValueId = null;
			this.attributeId = attributeId;
			this.value = null;
		}
		else if (type == AttributeKeyPartPatternType.AttributeIdAndValue)
		{
			Check.assumeNotNull(attributeId, "Parameter attributeId is not null");
			this.specialCode = 0;
			this.attributeValueId = null;
			this.attributeId = attributeId;
			this.value = value;
		}
		else if (type == AttributeKeyPartPatternType.All
				|| type == AttributeKeyPartPatternType.Other
				|| type == AttributeKeyPartPatternType.None)
		{
			if (specialCode == 0)
			{
				throw new AdempiereException("Invalid special code");
			}
			this.specialCode = specialCode;
			this.attributeValueId = null;
			this.attributeId = null;
			this.value = null;
		}
		else
		{
			throw new AdempiereException("Unknow type: " + type);
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		if (type == AttributeKeyPartPatternType.All)
		{
			return "ALL";
		}
		else if (type == AttributeKeyPartPatternType.Other)
		{
			return "OTHERS";
		}
		else if (type == AttributeKeyPartPatternType.None)
		{
			return "NONE";
		}
		else if (type == AttributeKeyPartPatternType.AttributeValueId)
		{
			return "attributeValueId=" + attributeValueId.getRepoId();
		}
		else if (type == AttributeKeyPartPatternType.AttributeIdAndValue)
		{
			return attributeId + "=" + String.valueOf(value);
		}
		else if (type == AttributeKeyPartPatternType.AttributeId)
		{
			return attributeId + "=<ANY>";
		}
		else
		{
			// shall not happen
			return "unknown";
		}
	}

	@Override
	public int compareTo(@NonNull final AttributesKeyPartPattern other)
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

		return sqlLikePart.compareTo(other.sqlLikePart);
	}

	private Integer getAttributeValueIdOrSpecialCodeOrNull()
	{
		if (type == AttributeKeyPartPatternType.AttributeValueId)
		{
			return attributeValueId.getRepoId();
		}
		else if (type == AttributeKeyPartPatternType.All
				|| type == AttributeKeyPartPatternType.Other
				|| type == AttributeKeyPartPatternType.None)
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
		return AttributeKeyPartPatternType.AttributeIdAndValue == type ? attributeId.getRepoId() : null;
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

	public boolean matches(@NonNull final AttributesKeyPart part)
	{
		if (type == AttributeKeyPartPatternType.All)
		{
			return true;
		}
		else if (type == AttributeKeyPartPatternType.Other)
		{
			throw new AdempiereException("Invalid pattern " + this);
		}
		else if (type == AttributeKeyPartPatternType.None)
		{
			return AttributesKeyPart.NONE.equals(part);
		}
		else if (type == AttributeKeyPartPatternType.AttributeValueId)
		{
			return AttributeKeyPartType.AttributeValueId.equals(part.getType())
					&& Objects.equals(this.attributeValueId, part.getAttributeValueId());
		}
		else if (type == AttributeKeyPartPatternType.AttributeIdAndValue)
		{
			return AttributeKeyPartType.AttributeIdAndValue.equals(part.getType())
					&& Objects.equals(this.attributeId, part.getAttributeId())
					&& (Check.isEmpty(this.value) //no value means all values allowed
							|| Objects.equals(this.value, part.getValue()));
		}
		else if (type == AttributeKeyPartPatternType.AttributeId)
		{
			return AttributeKeyPartType.AttributeIdAndValue.equals(part.getType())
					&& Objects.equals(this.attributeId, part.getAttributeId());
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
	}

	boolean isWildcardMatching()
	{
		return type == AttributeKeyPartPatternType.AttributeId;
	}

	AttributesKeyPart toAttributeKeyPart(@Nullable final AttributesKey context)
	{
		if (type == AttributeKeyPartPatternType.All)
		{
			return AttributesKeyPart.ALL;
		}
		else if (type == AttributeKeyPartPatternType.Other)
		{
			return AttributesKeyPart.OTHER;
		}
		else if (type == AttributeKeyPartPatternType.None)
		{
			return AttributesKeyPart.NONE;
		}
		else if (type == AttributeKeyPartPatternType.AttributeValueId)
		{
			return AttributesKeyPart.ofAttributeValueId(attributeValueId);
		}
		else if (type == AttributeKeyPartPatternType.AttributeIdAndValue)
		{
			return AttributesKeyPart.ofAttributeIdAndValue(attributeId, value);
		}
		else if (type == AttributeKeyPartPatternType.AttributeId)
		{
			if (context == null)
			{
				throw new AdempiereException("Context is required to convert `" + this + "` to " + AttributesKeyPart.class);
			}
			final String valueEffective = context.getValueByAttributeId(attributeId);
			return AttributesKeyPart.ofAttributeIdAndValue(attributeId, valueEffective);
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
	}
}
