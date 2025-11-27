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

package de.metas.common.shipping.v2.receipt;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class JsonCreateReceiptInfo
{
	String externalId;
	@NonNull JsonMetasfreshId receiptScheduleId;
	String productSearchKey;
	LocalDateTime dateReceived;
	LocalDate movementDate;
	String externalResourceURL;

	@Nullable BigDecimal movementQuantity;
	@Nullable List<JsonAttributeInstance> attributes;
	@Nullable List<HU> hus;

	@Builder
	@Jacksonized
	private JsonCreateReceiptInfo(
			final String externalId,
			@NonNull final JsonMetasfreshId receiptScheduleId,
			final String productSearchKey,
			final LocalDateTime dateReceived,
			final LocalDate movementDate,
			final String externalResourceURL,
			@Nullable final BigDecimal movementQuantity,
			@Nullable final List<JsonAttributeInstance> attributes,
			@Nullable final List<HU> hus)
	{
		this.externalId = externalId;
		this.receiptScheduleId = receiptScheduleId;
		this.productSearchKey = productSearchKey;
		this.dateReceived = dateReceived;
		this.movementDate = movementDate;
		this.externalResourceURL = externalResourceURL;

		if (hus == null || hus.isEmpty())
		{
			this.movementQuantity = Check.assumeNotNull(movementQuantity, "movementQuantity shall be not null if hus is null or empty");
			this.attributes = attributes;
			this.hus = null;
		}
		else
		{
			Check.assumeNull(movementQuantity, "movementQuantity shall be null if hus is null or empty");
			Check.assumeNull(attributes, "attributes shall be null if hus is null or empty");
			this.hus = ImmutableList.copyOf(hus);
			this.movementQuantity = null;
			this.attributes = null;
		}
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class HU
	{
		@NonNull BigDecimal qty;
		@Nullable List<JsonAttributeInstance> attributes;
	}

}
