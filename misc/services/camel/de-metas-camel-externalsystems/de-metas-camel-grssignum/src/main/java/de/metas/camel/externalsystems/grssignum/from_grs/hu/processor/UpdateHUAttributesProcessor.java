/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.hu.processor;

import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUUpdate;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

public class UpdateHUAttributesProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final JsonHUUpdate requestBody = exchange.getIn().getBody(JsonHUUpdate.class);

		final JsonHUAttributesRequest huUpdateCamelRequest = JsonHUAttributesRequest.builder()
				.huId(requestBody.getId())
				.attributes(toHUAttributes(requestBody.getAttributes()))
				.build();

		exchange.getIn().setBody(huUpdateCamelRequest, JsonHUAttributesRequest.class);
	}

	@NonNull
	private static JsonHUAttributes toHUAttributes(@NonNull final Map<String, Object> attributes)
	{
		final JsonHUAttributes attributesToUpdate = new JsonHUAttributes();
		attributes.forEach(attributesToUpdate::putAttribute);

		return attributesToUpdate;
	}
}
