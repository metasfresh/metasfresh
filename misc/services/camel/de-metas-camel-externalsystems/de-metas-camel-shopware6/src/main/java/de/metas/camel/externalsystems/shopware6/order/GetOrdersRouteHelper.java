/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class GetOrdersRouteHelper
{
	@NonNull
	public Optional<JsonExternalSystemShopware6ConfigMappings> getSalesOrderMappingRules(@NonNull final JsonExternalSystemRequest request)
	{
		final String shopware6Mappings = request.getParameters().get(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS);

		if (Check.isBlank(shopware6Mappings))
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(shopware6Mappings, JsonExternalSystemShopware6ConfigMappings.class));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
}
