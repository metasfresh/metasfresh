package de.metas.rest_api.utils;

import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@UtilityClass
public class ApiAPMHelper
{
	public SpanMetadata createMetadataFor(@NonNull final String restEndpointName)
	{
		return SpanMetadata.builder()
				.name("REST-API - " + restEndpointName)
				.type(Type.REST_API_PROCESSING.getCode())
				.build();
	}
}
