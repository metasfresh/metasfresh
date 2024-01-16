package de.metas.handlingunits.material.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.handlingunits.base
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
@Builder(toBuilder = true)
final class HUAttributeChange
{
	@NonNull
	final HuId huId;
	@NonNull
	final AttributeId attributeId;
	@NonNull
	final AttributeValueType attributeValueType;

	final Object valueNew;
	final Object valueOld;

	@NonNull
	private Instant date;

	public HUAttributeChange mergeWithNextChange(final HUAttributeChange nextChange)
	{
		Check.assumeEquals(huId, nextChange.huId, "Invalid huId for {}. Expected: {}", nextChange, huId);
		Check.assumeEquals(attributeId, nextChange.attributeId, "Invalid attributeId for {}. Expected: {}", nextChange, attributeId);
		Check.assumeEquals(attributeValueType, nextChange.attributeValueType, "Invalid attributeValueType for {}. Expected: {}", nextChange, attributeValueType);

		return toBuilder()
				.valueNew(nextChange.getValueNew())
				.date(nextChange.getDate())
				.build();
	}

	public AttributesKeyPart getNewAttributeKeyPartOrNull()
	{
		return toAttributeKeyPartOrNull(valueNew);
	}

	public AttributesKeyPart getOldAttributeKeyPartOrNull()
	{
		return toAttributeKeyPartOrNull(valueOld);
	}

	private AttributesKeyPart toAttributeKeyPartOrNull(final Object value)
	{
		if (AttributeValueType.STRING.equals(attributeValueType))
		{
			final String valueStr = value != null ? value.toString() : null;
			return AttributesKeyPart.ofStringAttribute(attributeId, valueStr);
		}
		else if (AttributeValueType.NUMBER.equals(attributeValueType))
		{
			final BigDecimal valueBD = NumberUtils.asBigDecimal(value);

			return BigDecimal.ZERO.equals(valueBD)
					? null
					: AttributesKeyPart.ofNumberAttribute(attributeId, valueBD);
		}
		else if (AttributeValueType.DATE.equals(attributeValueType))
		{
			final LocalDate valueDate = TimeUtil.asLocalDate(value);
			return AttributesKeyPart.ofDateAttribute(attributeId, valueDate);
		}
		else if (AttributeValueType.LIST.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = (AttributeValueId)value;
			return attributeValueId != null
					? AttributesKeyPart.ofAttributeValueId(attributeValueId)
					: null;
		}
		else
		{
			throw new AdempiereException("Unknown attribute value type: " + attributeValueType);
		}
	}
}
