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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

@Value
@Builder
@Schema(description = "Contains the bank account to be inserted or delete. The bank account is identified by IBAN.")
public class JsonRequestBankAccountUpsertItem
{
	@Schema(nullable = false)
	@JsonProperty("iban")
	String iban;

	@Schema(nullable = true)
	@JsonProperty("currencyCode")
	String currencyCode;

	@Schema(required = false, description = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("active")
	Boolean active;

	@Schema(nullable = true)
	@JsonProperty("accountName")
	String accountName;

	@Schema(nullable = true)
	@JsonProperty("accountStreet")
	String accountStreet;

	@Schema(nullable = true)
	@JsonProperty("accountZip")
	String accountZip;

	@Schema(nullable = true)
	@JsonProperty("accountCity")
	String accountCity;

	@Schema(nullable = true)
	@JsonProperty("accountCountry")
	String accountCountry;

	@Setter
	@Schema(description = "Sync advise about this contact's individual properties.\n" + PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@JsonCreator
	public JsonRequestBankAccountUpsertItem(
			@JsonProperty("iban") @NonNull String iban,
			@JsonProperty("currencyCode") @Nullable final String currencyCode,
			@JsonProperty("active") @Nullable final Boolean active,
			@JsonProperty("accountName") @Nullable final String accountName,
			@JsonProperty("accountStreet") @Nullable final String accountStreet,
			@JsonProperty("accountZip") @Nullable final String accountZip,
			@JsonProperty("accountCity") @Nullable final String accountCity,
			@JsonProperty("accountCountry") @Nullable final String accountCountry,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.iban = iban;
		this.currencyCode = currencyCode;
		this.active = active;
		this.accountName = accountName;
		this.accountStreet = accountStreet;
		this.accountZip = accountZip;
		this.accountCity = accountCity;
		this.accountCountry = accountCountry;
		this.syncAdvise = syncAdvise;
	}

	public boolean getIsActive()
	{
		return CoalesceUtil.coalesceNotNull(active, true);
	}
}
