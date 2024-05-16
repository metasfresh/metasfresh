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
import java.util.List;

@Value
@JsonDeserialize(builder = JsonBOM.JsonBOMBuilder.class)
public class JsonBOM
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

	@Nullable
	@JsonProperty("VERLUST")
	BigDecimal scrap;

	@Nullable
	@JsonProperty("GTIN")
	String gtin;

	@Nullable
	@JsonProperty("METASFRESHID")
	String bPartnerMetasfreshId;

	@Nullable
	@JsonProperty("ANHANGDATEI")
	String attachmentFilePath;

	@NonNull
	@JsonProperty("DETAIL")
	List<JsonBOMLine> bomLines;

	@Builder
	public JsonBOM(
			@JsonProperty("FLAG") final @NonNull Integer flag,
			@JsonProperty("ARTNR") final @NonNull String productValue,
			@JsonProperty("ARTNRID") final @NonNull String productId,
			@JsonProperty("TEXT") final @Nullable String name1,
			@JsonProperty("TEXT2") final @Nullable String name2,
			@JsonProperty("INAKTIV") final int inactive,
			@JsonProperty("VERLUST") final @Nullable BigDecimal scrap,
			@JsonProperty("GTIN") final @Nullable String gtin,
			@JsonProperty("METASFRESHID") final @Nullable String bPartnerMetasfreshId,
			@JsonProperty("ANHANGDATEI") final @Nullable String attachmentFilePath,
			@JsonProperty("DETAIL") final @NonNull List<JsonBOMLine> bomLines)
	{
		this.flag = flag;
		this.productValue = productValue;
		this.productId = productId;
		this.name1 = name1;
		this.name2 = name2;
		this.isActive = inactive != 1;
		this.scrap = scrap;
		this.gtin = gtin;
		this.bPartnerMetasfreshId = bPartnerMetasfreshId;
		this.attachmentFilePath = attachmentFilePath;
		this.bomLines = bomLines;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBOMBuilder
	{
	}

}
