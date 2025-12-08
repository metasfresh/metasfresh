/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.changelog.JsonChangeInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonResponseBPBankAccount
{
	public static final String ID = "id";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String IBAN = "iban";
	public static final String CURRENCY_ID = "currencyId";
	public static final String ACTIVE = "active";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String ACCOUNT_STREET = "accountStreet";
	public static final String ACCOUNT_ZIP = "accountZip";
	public static final String ACCOUNT_CITY = "accountCity";
	public static final String ACCOUNT_COUNTRY = "accountCountry";

	@NonNull
	JsonMetasfreshId metasfreshId;
	@NonNull
	JsonMetasfreshId metasfreshBPartnerId;
	@NonNull
	JsonMetasfreshId currencyId;
	@NonNull
	String iban;
	boolean active;

	@Nullable String accountName;

	@Nullable String accountStreet;

	@Nullable String accountZip;

	@Nullable String accountCity;

	@Nullable String accountCountry;
	
	@Nullable
	JsonChangeInfo changeInfo;

	@Builder(toBuilder = true)
	@JsonCreator
	public JsonResponseBPBankAccount(
			@JsonProperty(ID) @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty(BPARTNER_ID) @NonNull final JsonMetasfreshId metasfreshBPartnerId,
			@JsonProperty(CURRENCY_ID) @NonNull final JsonMetasfreshId currencyId,
			@JsonProperty(IBAN) @NonNull final String iban,
			@JsonProperty(ACTIVE) final boolean active,
			@JsonProperty(ACCOUNT_NAME) @Nullable final String accountName,
			@JsonProperty(ACCOUNT_STREET) @Nullable final String accountStreet,
			@JsonProperty(ACCOUNT_ZIP) @Nullable final String accountZip,
			@JsonProperty(ACCOUNT_CITY) @Nullable final String accountCity,
			@JsonProperty(ACCOUNT_COUNTRY) @Nullable final String accountCountry,
			@JsonProperty("changeInfo") final JsonChangeInfo changeInfo)
	{
		this.metasfreshId = metasfreshId;
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.currencyId = currencyId;
		this.iban = iban;
		this.active = active;
		this.accountName = accountName;
		this.accountStreet = accountStreet;
		this.accountZip = accountZip;
		this.accountCity = accountCity;
		this.accountCountry = accountCountry;
		this.changeInfo = changeInfo;
	}
}
