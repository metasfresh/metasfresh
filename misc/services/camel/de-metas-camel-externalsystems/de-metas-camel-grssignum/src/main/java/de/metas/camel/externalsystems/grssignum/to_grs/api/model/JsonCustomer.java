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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonDeserialize(builder = JsonCustomer.JsonCustomerBuilder.class)
public class JsonCustomer
{
	@NonNull
	@JsonProperty("FLAG")
	Integer flag;

	@NonNull
	@JsonProperty("METASFRESHID")
	JsonMetasfreshId id;

	@NonNull
	@JsonProperty("MKDID")
	String bpartnerValue;

	@NonNull
	@JsonProperty("MATCHCODE")
	String name;

	@JsonProperty("METASFRESHURL")
	String url;

	@JsonProperty("MID")
	String tenantId;

	@JsonProperty("KREDITORENNR")
	Integer creditorId;

	@JsonProperty("DEBITORENNR")
	Integer debtorId;

	@JsonProperty("ANHANGORDNER")
	String bpartnerDirPath;

	@JsonProperty("INAKTIV")
	Integer inactiveBit;

	@JsonProperty("KDDATA")
	List<JsonCustomerLocation> locations;

	@JsonProperty("PERSDATA")
	List<JsonCustomerContact> contacts;

	@JsonProperty("REZDET")
	List<String> bpartnerProductExternalReferences;

	@Builder
	public JsonCustomer(
			@JsonProperty("FLAG") final @NonNull Integer flag,
			@JsonProperty("METASFRESHID") final @NonNull JsonMetasfreshId id,
			@JsonProperty("MKDID") final @NonNull String bpartnerValue,
			@JsonProperty("MATCHCODE") final @NonNull String name,
			@JsonProperty("METASFRESHURL") final @Nullable String url,
			@JsonProperty("MID") final @Nullable String tenantId,
			@JsonProperty("KREDITORENNR") final @Nullable Integer creditorId,
			@JsonProperty("DEBITORENNR") final @Nullable Integer debtorId,
			@JsonProperty("ANHANGORDNER") final @Nullable String bpartnerDirPath,
			@JsonProperty("INAKTIV") final int inactive,
			@JsonProperty("KDDATA") final @Nullable List<JsonCustomerLocation> locations,
			@JsonProperty("PERSDATA") final @Nullable List<JsonCustomerContact> contacts,
			@JsonProperty("REZDET") final @Nullable List<String> bpartnerProductExternalReferences)
	{
		this.flag = flag;
		this.id = id;
		this.bpartnerValue = bpartnerValue;
		this.url = url;
		this.name = name;
		this.inactiveBit = inactive;
		this.tenantId = tenantId;
		this.creditorId = creditorId;
		this.debtorId = debtorId;
		this.bpartnerDirPath = bpartnerDirPath;
		this.locations = locations;
		this.contacts = contacts;
		this.bpartnerProductExternalReferences = bpartnerProductExternalReferences;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonCustomerBuilder
	{
	}

	@JsonIgnore
	public boolean isActive()
	{
		return inactiveBit != 1;
	}
}