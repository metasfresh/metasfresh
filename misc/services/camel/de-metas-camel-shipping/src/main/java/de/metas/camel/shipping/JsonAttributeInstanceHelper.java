/*
 * #%L
 * de-metas-camel-shipping
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

package de.metas.camel.shipping;

import de.metas.camel.shipping.shipment.AttributeCode;
import de.metas.common.rest_api.JsonAttributeInstance;
import lombok.NonNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class JsonAttributeInstanceHelper
{
	private final static Log log = LogFactory.getLog(JsonAttributeInstanceHelper.class);

	@NonNull
	public static Optional<JsonAttributeInstance> buildAttribute(@NonNull final AttributeCode attributeCode, @NonNull final Object value)
	{
		final JsonAttributeInstance.JsonAttributeInstanceBuilder builder = JsonAttributeInstance.builder()
				.attributeName(attributeCode.getAttributeCode())
				.attributeCode(attributeCode.getAttributeCode());

		switch (attributeCode.getAttributeValueType())
		{
			case STRING:
				builder.valueStr((String) value);
				break;
			case NUMBER:
				builder.valueNumber((BigDecimal) value);
				break;
			case DATE:
				builder.valueDate((LocalDate) value);
				break;
			default:
				log.debug("The given attribute type is not supported! AttributeType: " + attributeCode.getAttributeValueType() + " -> returning null!");
				return Optional.empty();
		}

		return Optional.of(builder.build());
	}
}
