/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorRequestItem
{
	int numberOfItems;
	@NonNull BigDecimal grossWeightKg;
	@NonNull String productName;
	@NonNull String productValue;
	@Nullable JsonPackageDimensions packageDimensions;

	@JsonIgnore
	public Optional<String> getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE:
				return Optional.of(productValue);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME:
				return Optional.of(productName);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG:
				return Optional.of(grossWeightKg.toPlainString());
			default:
				return Optional.empty();
		}
	}
}
