package de.metas.material.event.attributes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.util.Check;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class AttributesKeyWithASI
{
	@JsonCreator
	public static AttributesKeyWithASI of(
			@JsonProperty("attributesKey") @Nullable final AttributesKey attributesKey,
			@JsonProperty("attributeSetInstanceId") @NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributesKey == null || attributesKey.isNone())
		{
			Check.assume(attributeSetInstanceId == null || attributeSetInstanceId.isNone(), "ASI expected to be NONE but it was {}", attributeSetInstanceId);
			return NONE;
		}
		else
		{
			return new AttributesKeyWithASI(attributesKey, attributeSetInstanceId);
		}
	}

	private static final AttributesKeyWithASI NONE = new AttributesKeyWithASI();

	@JsonProperty("attributesKey")
	AttributesKey attributesKey;

	@JsonProperty("attributeSetInstanceId")
	AttributeSetInstanceId attributeSetInstanceId;

	private AttributesKeyWithASI(
			@NonNull final AttributesKey attributesKey,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId.isNone())
		{
			throw new AdempiereException("Invalid attributeSetInstanceId. It shall be not NONE for " + attributesKey);
		}
		this.attributesKey = attributesKey;
		this.attributeSetInstanceId = attributeSetInstanceId;
	}

	private AttributesKeyWithASI()
	{
		this.attributesKey = null;
		this.attributeSetInstanceId = AttributeSetInstanceId.NONE;
	}
}
