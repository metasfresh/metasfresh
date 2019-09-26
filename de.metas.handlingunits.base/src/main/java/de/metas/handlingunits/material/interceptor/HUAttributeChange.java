package de.metas.handlingunits.material.interceptor;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;

import de.metas.handlingunits.HuId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

	public HUAttributeChange mergeWithNextChange(final HUAttributeChange nextChange)
	{
		Check.assumeEquals(huId, nextChange.huId, "Invalid huId for {}. Expected: {}", nextChange, huId);
		Check.assumeEquals(attributeId, nextChange.attributeId, "Invalid attributeId for {}. Expected: {}", nextChange, attributeId);
		Check.assumeEquals(attributeValueType, nextChange.attributeValueType, "Invalid attributeValueType for {}. Expected: {}", nextChange, attributeValueType);

		return toBuilder()
				.valueNew(nextChange.getValueNew())
				.build();
	}
}
