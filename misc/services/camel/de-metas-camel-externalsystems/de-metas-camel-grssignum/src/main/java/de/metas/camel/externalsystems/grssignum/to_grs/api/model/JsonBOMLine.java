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
import java.math.BigDecimal;

@Value
@JsonDeserialize(builder = JsonBOMLine.JsonBOMLineBuilder.class)
public class JsonBOMLine
{
	@NonNull
	@JsonProperty("ARTNR")
	String productValue;

	@NonNull
	@JsonProperty("ARTNRID")
	String productId;

	@Nullable
	@JsonProperty("HERKUNFTSLAND")
	String countryCode;

	@NonNull
	@JsonProperty("POS")
	Integer line;

	@JsonProperty("ANTEIL")
	BigDecimal qtyBOM;

	@NonNull
	@JsonProperty("UOM")
	String uom;

	@Builder
	public JsonBOMLine(
			@JsonProperty("ARTNR") final @NonNull String productValue,
			@JsonProperty("ARTNRID") final @NonNull String productId,
			@JsonProperty("HERKUNFTSLAND") final @Nullable String countryCode,
			@JsonProperty("POS") final @NonNull Integer line,
			@JsonProperty("ANTEIL") final @NonNull BigDecimal qtyBOM,
			@JsonProperty("UOM") final @NonNull String uom)
	{
		this.productValue = productValue;
		this.productId = productId;
		this.countryCode = countryCode;
		this.line = line;
		this.qtyBOM = qtyBOM;
		this.uom = uom;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBOMLineBuilder
	{
	}

}
