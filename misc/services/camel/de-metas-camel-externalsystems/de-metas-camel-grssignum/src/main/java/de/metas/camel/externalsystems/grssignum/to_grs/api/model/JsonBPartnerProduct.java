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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonBPartnerProduct.JsonBPartnerProductBuilder.class)
public class JsonBPartnerProduct
{
	@Nullable
	@JsonProperty("MKREDID")
	String bpartnerId;

	@JsonProperty("STDKRED")
	boolean currentVendor;

	@JsonProperty("LIEFERANTENFREIGABE")
	boolean isExcludedFromPurchase;

	@JsonProperty("INAKTIV")
	boolean isActive;

	@Nullable
	@JsonProperty("METASFRESHID")
	String bPartnerMetasfreshId;

	@Nullable
	@JsonProperty("ROHKREDDATA")
	JsonBPartnerProductAdditionalInfo attachmentAdditionalInfos;

	@Builder
	public JsonBPartnerProduct(
			@JsonProperty("MKREDID") final @Nullable String bpartnerId,
			@JsonProperty("STDKRED") final @NonNull Integer currentVendor,
			@JsonProperty("LIEFERANTENFREIGABE") final int approvedForPurchase,
			@JsonProperty("INAKTIV") final int inactive,
			@JsonProperty("METASFRESHID") final @Nullable String bPartnerMetasfreshId,
			@JsonProperty("ROHKREDDATA") final @Nullable JsonBPartnerProductAdditionalInfo attachmentAdditionalInfos)
	{
		this.bpartnerId = bpartnerId;
		this.currentVendor = currentVendor == 1;
		this.isExcludedFromPurchase = approvedForPurchase != 1;
		this.isActive = inactive != 1;
		this.bPartnerMetasfreshId = bPartnerMetasfreshId;
		this.attachmentAdditionalInfos = attachmentAdditionalInfos;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBPartnerProductBuilder
	{
	}
}
