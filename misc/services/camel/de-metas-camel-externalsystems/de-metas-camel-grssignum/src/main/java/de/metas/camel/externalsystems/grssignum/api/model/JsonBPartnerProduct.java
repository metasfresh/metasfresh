/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonDeserialize(builder = JsonBPartnerProduct.JsonBPartnerProductBuilder.class)
public class JsonBPartnerProduct
{
	@NonNull
	@JsonProperty("MKREDID")
	String bpartnerId;

	@JsonProperty("STDKRED")
	boolean currentVendor;

	@Builder
	public JsonBPartnerProduct(
			@JsonProperty("MKREDID") final @NonNull String bpartnerId,
			@JsonProperty("STDKRED") final @NonNull Integer currentVendor
	)
	{
		this.bpartnerId = bpartnerId;
		this.currentVendor = currentVendor == 1;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBPartnerProductBuilder
	{
	}
}
