/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common.mapping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.InvokeExternalSystemParametersUtil;
import de.metas.common.externalsystem.JsonExternalMapping;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_CATEGORY_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_TYPE_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UOM_MAPPINGS;

@Builder
@Value
public class ExternalMappingsHolder
{
	@NonNull
	ImmutableMap<String, JsonExternalMapping> externalRef2UOM;

	@NonNull
	ImmutableMap<String, JsonExternalMapping> externalRef2ProductCategory;

	@NonNull
	ImmutableMap<String, JsonExternalMapping> externalRef2ProductType;

	@NonNull
	public static ExternalMappingsHolder of(@NonNull final Map<String, String> parameters)
	{
		return ExternalMappingsHolder.builder()
				.externalRef2UOM(getExternalValue2ExternalMapping(parameters, PARAM_UOM_MAPPINGS))
				.externalRef2ProductCategory(getExternalValue2ExternalMapping(parameters, PARAM_PRODUCT_CATEGORY_MAPPINGS))
				.externalRef2ProductType(getExternalValue2ExternalMapping(parameters, PARAM_PRODUCT_TYPE_MAPPINGS))
				.build();
	}

	@NonNull
	public Optional<String> resolveUOMValue(@Nullable final String externalValue)
	{
		return Optional.ofNullable(externalValue)
				.map(externalRef2UOM::get)
				.map(JsonExternalMapping::getValue);
	}

	@NonNull
	public Optional<JsonMetasfreshId> resolveProductCategoryId(@Nullable final String externalValue)
	{
		return Optional.ofNullable(externalValue)
				.map(externalRef2ProductCategory::get)
				.map(JsonExternalMapping::getMetasfreshId);
	}

	@NonNull
	public Optional<JsonMetasfreshId> resolveProductCategoryIdByMatchingValue(@Nullable final String externalValue)
	{
		if (externalValue == null || Check.isBlank(externalValue))
		{
			return Optional.empty();
		}

		return externalRef2ProductCategory.keySet()
				.stream()
				.filter(externalValue::contains)
				.findFirst()
				.map(externalRef2ProductCategory::get)
				.map(JsonExternalMapping::getMetasfreshId);
	}

	@NonNull
	public Optional<String> resolveProductTypeValue(@Nullable final String externalValue)
	{
		return Optional.ofNullable(externalValue)
				.map(externalRef2ProductType::get)
				.map(JsonExternalMapping::getValue);
	}

	public boolean hasProductCategoryMappings()
	{
		return !externalRef2ProductCategory.isEmpty();
	}

	public boolean hasProductTypeMappings()
	{
		return !externalRef2ProductType.isEmpty();
	}

	@NonNull
	private static ImmutableMap<String, JsonExternalMapping> getExternalValue2ExternalMapping(@NonNull final Map<String, String> parameters, @NonNull final String param)
	{
		return Optional.ofNullable(InvokeExternalSystemParametersUtil.getDeserializedParameter(parameters, param, JsonExternalMapping[].class))
				.map(ImmutableList::copyOf)
				.orElseGet(ImmutableList::of)
				.stream()
				.collect(ImmutableMap.toImmutableMap(JsonExternalMapping::getExternalValue, Function.identity()));
	}
}
