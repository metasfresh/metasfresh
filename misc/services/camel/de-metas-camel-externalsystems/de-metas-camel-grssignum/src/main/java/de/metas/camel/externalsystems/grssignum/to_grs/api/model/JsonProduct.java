/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2023 metas GmbH
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
import java.util.List;

@Value
@JsonDeserialize(builder = JsonProduct.JsonProductBuilder.class)
public class JsonProduct
{

	@NonNull
	@JsonProperty("FLAG")
	Integer flag;

	@NonNull
	@JsonProperty("ARTNR")
	String productValue;

	@NonNull
	@JsonProperty("ARTNRID")
	String productId;

	@Nullable
	@JsonProperty("TEXT")
	String name1;

	@Nullable
	@JsonProperty("TEXT2")
	String name2;

	@JsonProperty("INAKTIV")
	boolean isActive;

	@JsonProperty("BIO")
	boolean isBio;

	@JsonProperty("HALAL")
	boolean isHalal;

	@Nullable
	@JsonProperty("KRED")
	List<JsonBPartnerProduct> bPartnerProducts;

	@Builder
	@JsonIgnoreProperties(ignoreUnknown = true)
	public JsonProduct(
			@JsonProperty("FLAG") final @NonNull Integer flag,
			@JsonProperty("ARTNR") final @NonNull String productValue,
			@JsonProperty("ARTNRID") final @NonNull String productId,
			@JsonProperty("TEXT") final @Nullable String name1,
			@JsonProperty("TEXT2") final @Nullable String name2,
			@JsonProperty("INAKTIV") final int inactive,
			@JsonProperty("BIO") final int bio,
			@JsonProperty("HALAL") final int halal,
			@JsonProperty("KRED") final @Nullable List<JsonBPartnerProduct> bPartnerProducts)
	{
		this.flag = flag;
		this.productValue = productValue;
		this.productId = productId;
		this.name1 = name1;
		this.name2 = name2;
		this.isActive = inactive != 1;
		this.isBio = bio == 1;
		this.isHalal = halal == 1;
		this.bPartnerProducts = bPartnerProducts;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonProductBuilder
	{
	}
}
