/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.rest_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonAttributeSetInstance
{
	List<JsonAttributeInstance> attributeInstances;

	@JsonIgnore
	ImmutableMap<String, JsonAttributeInstance> code2Instance;

	@Builder
	@JsonCreator
	private JsonAttributeSetInstance(
			@JsonProperty("attributeInstances") @Singular @NonNull final List<JsonAttributeInstance> attributeInstances)
	{
		this.attributeInstances = attributeInstances;
		this.code2Instance = Maps.uniqueIndex(attributeInstances, JsonAttributeInstance::getAttributeCode);
	}

	@Nullable
	@JsonIgnore
	public String getValueStr(@NonNull final String attributeCode)
	{
		final JsonAttributeInstance instance = code2Instance.get(attributeCode);
		if (instance == null)
		{
			return null;
		}
		return instance.getValueStr();
	}
}
