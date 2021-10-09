/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

@ApiModel(description = "A BPartner with `n` contacts and `n` locations.\n")
@Value
public class JsonRequestComposite
{
	// TODO if an org is given, then verify whether the current user has access to the given org
	@ApiModelProperty(position = 10)
	@JsonInclude(Include.NON_NULL)
	String orgCode;

	@ApiModelProperty(position = 20)
	JsonRequestBPartner bpartner;

	@ApiModelProperty(value = "The location's GLN can be used to lookup the whole bpartner; if multiple locations with GLN are provided, then only the first one is used", //
			position = 30)
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("locations")
	@Getter(AccessLevel.PRIVATE)
	JsonRequestLocationUpsert locations;

	@ApiModelProperty(position = 40)
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("contacts")
	@Getter(AccessLevel.PRIVATE)
	JsonRequestContactUpsert contacts;

	@ApiModelProperty(position = 50)
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("bankAccounts")
	@Getter(AccessLevel.PRIVATE)
	JsonRequestBankAccountsUpsert bankAccounts;

	@ApiModelProperty(position = 60)
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("compositeAlbertaBPartner")
	JsonCompositeAlbertaBPartner compositeAlbertaBPartner;

	@ApiModelProperty(value = "Ths advise is applied to this composite's bpartner or any of its contacts\n"
			+ READ_ONLY_SYNC_ADVISE_DOC, position = 70)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestComposite(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("bpartner") @Nullable final JsonRequestBPartner bpartner,
			@JsonProperty("locations") @Nullable final JsonRequestLocationUpsert locations,
			@JsonProperty("contacts") @Nullable final JsonRequestContactUpsert contacts,
			@JsonProperty("bankAccounts") @Nullable final JsonRequestBankAccountsUpsert bankAccounts,
			@JsonProperty("compositeAlbertaBPartner") @Nullable final JsonCompositeAlbertaBPartner compositeAlbertaBPartner,
			@JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.locations = locations;
		this.contacts = contacts;
		this.bankAccounts = bankAccounts;
		this.compositeAlbertaBPartner = compositeAlbertaBPartner;
		this.syncAdvise = syncAdvise;
	}

	@JsonIgnore
	public JsonRequestLocationUpsert getLocationsNotNull()
	{
		return coalesceNotNull(locations, JsonRequestLocationUpsert.builder().build());
	}

	@JsonIgnore
	public JsonRequestContactUpsert getContactsNotNull()
	{
		return coalesceNotNull(contacts, JsonRequestContactUpsert.builder().build());
	}

	@JsonIgnore
	public JsonRequestBankAccountsUpsert getBankAccountsNotNull()
	{
		return coalesceNotNull(bankAccounts, JsonRequestBankAccountsUpsert.NONE);
	}
}
