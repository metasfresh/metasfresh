/*
 * #%L
 * de-metas-common-shipping
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

package de.metas.common.shipment.mpackage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;
import de.metas.common.MeasurableRequest;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonCreateShippingPackagesRequest.JsonCreateShippingPackagesRequestBuilder.class)
public class JsonCreateShippingPackagesRequest extends MeasurableRequest
{
	@JsonProperty("packageInfos")
	List<JsonCreateShippingPackageInfo> packageInfos;

	@Override
	@JsonIgnore
	public int getSize()
	{
		return packageInfos.size();
	}

	@Builder
	public JsonCreateShippingPackagesRequest(@Nullable final List<JsonCreateShippingPackageInfo> packageInfos)
	{
		this.packageInfos = packageInfos != null
				? packageInfos.stream().filter(Objects::nonNull).collect(ImmutableList.toImmutableList())
				: ImmutableList.of();
	}

	@JsonPOJOBuilder(withPrefix = "")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class JsonCreateShippingPackagesRequestBuilder
	{
	}
}
