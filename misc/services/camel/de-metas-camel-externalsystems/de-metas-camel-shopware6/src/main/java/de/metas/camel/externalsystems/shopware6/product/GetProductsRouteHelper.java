/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.shopware6.tax.TaxCategoryProvider;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonUOM;
import de.metas.common.externalsystem.JsonUOMMapping;
import de.metas.common.externalsystem.JsonUOMMappings;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetProductsRouteHelper
{
	@NonNull
	public ImmutableMap<String, JsonUOM> getUOMMappingRules(@NonNull final JsonExternalSystemRequest request)
	{
		final String shopware6UOMMappings = request.getParameters().get(ExternalSystemConstants.PARAM_UOM_MAPPINGS);

		if (Check.isBlank(shopware6UOMMappings))
		{
			return ImmutableMap.of();
		}

		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		try
		{
			return mapper.readValue(shopware6UOMMappings, JsonUOMMappings.class)
					.getJsonUOMMappingList()
					.stream()
					.collect(ImmutableMap.toImmutableMap(JsonUOMMapping::getExternalCode, JsonUOMMapping::getUom));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	public TaxCategoryProvider getTaxCategoryProvider(@NonNull final JsonExternalSystemRequest request)
	{
		return TaxCategoryProvider.builder()
				.normalRates(request.getParameters().get(ExternalSystemConstants.PARAM_NORMAL_VAT_RATES))
				.reducedRates(request.getParameters().get(ExternalSystemConstants.PARAM_REDUCED_VAT_RATES))
				.build();
	}
}
