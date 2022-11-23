/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.product;

import de.metas.camel.externalsystems.sap.SAPMappingsUtil;
import de.metas.common.externalsystem.JsonExternalMapping;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_CATEGORY_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_TYPE_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UOM_MAPPINGS;

@Builder
@Getter
public class ExternalMappingsHolder
{
	@NonNull
	private final Map<String, JsonExternalMapping> externalRef2UOMMapping;

	@NonNull
	private final Map<String, JsonExternalMapping> externalRef2ProductCategoryMapping;

	@NonNull
	private final Map<String, JsonExternalMapping> externalValue2ProductType;

	public static ExternalMappingsHolder holdExternalMappings(@NonNull final Map<String, String> parameters)
	{
		return ExternalMappingsHolder.builder()
				.externalRef2UOMMapping(SAPMappingsUtil.getExternalValue2ExternalMapping(parameters, PARAM_UOM_MAPPINGS))

				.externalRef2ProductCategoryMapping(SAPMappingsUtil.getExternalValue2ExternalMapping(parameters, PARAM_PRODUCT_CATEGORY_MAPPINGS))

				.externalValue2ProductType(SAPMappingsUtil.getExternalValue2ExternalMapping(parameters, PARAM_PRODUCT_TYPE_MAPPINGS))
				.build();
	}

	@NonNull
	public Optional<String> resolveUOMSAPValue(@NonNull final String uomValue)
	{
		final JsonExternalMapping externalMappingForUOM = externalRef2UOMMapping.get(uomValue);

		if (externalMappingForUOM == null)
		{
			return Optional.empty();
		}

		Check.assumeNotNull(externalMappingForUOM.getValue(), "JsonExternalMapping.Value is mandatory for UOM external reference!");

		return Optional.of(externalMappingForUOM.getValue());
	}

	@NonNull
	public Optional<JsonMetasfreshId> resolveProductCategorySAPValue(@NonNull final String productCategoryValue)
	{
		final JsonExternalMapping externalMappingForProductCategory = externalRef2ProductCategoryMapping.get(productCategoryValue);

		if (externalMappingForProductCategory == null)
		{
			return Optional.empty();
		}

		Check.assumeNotNull(externalMappingForProductCategory.getMetasfreshId(), "JsonExternalMapping.MetasfreshId is mandatory for ProductCategory external reference!");

		return Optional.of(externalMappingForProductCategory.getMetasfreshId());
	}

	@NonNull
	public Optional<String> resolveProductTypeSAPValue(@NonNull final String productTypeValue)
	{
		final JsonExternalMapping externalMappingForProductType = externalValue2ProductType.get(productTypeValue);

		if (externalMappingForProductType == null)
		{
			return Optional.empty();
		}

		Check.assumeNotNull(externalMappingForProductType.getValue(), "JsonExternalMapping.Value is mandatory for ProductType!");

		return Optional.of(externalMappingForProductType.getValue());
	}
}
