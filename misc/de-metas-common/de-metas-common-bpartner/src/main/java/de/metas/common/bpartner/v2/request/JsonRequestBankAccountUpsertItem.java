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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Contains the bank account to be inserted or deleted. The bank account is identified by IBAN.")
public class JsonRequestBankAccountUpsertItem
{
	@Setter
	@NonNull
	@Schema(minLength = 1)
	@JsonProperty("identifier")
	private String identifier;

	@Setter
	@NonNull
	@Schema(minLength = 1)
	@JsonProperty("iban")
	private String iban;

	@JsonProperty("name")
	private String name;
	@Schema(hidden = true)
	private boolean nameSet;

	@JsonProperty("qrIban")
	private String qrIban;
	@Schema(hidden = true)
	private boolean qrIbanSet;

	@Schema(nullable = true)
	@JsonProperty("currencyCode")
	private String currencyCode;
	@Schema(hidden = true)
	private boolean currencyCodeSet;

	@Schema(description = "If not specified but required (e.g. because a new bank account is created), then `true` is assumed")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("active")
	private Boolean active;
	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "If not specified but required (e.g. because a new bank account is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("isDefault")
	private Boolean isDefault;
	@Schema(hidden = true)
	private boolean isDefaultSet;

	@JsonProperty("accountName")
	private String accountName;
	@Schema(hidden = true)
	private boolean accountNameSet;

	@JsonProperty("accountStreet")
	private String accountStreet;
	@Schema(hidden = true)
	private boolean accountStreetSet;

	@JsonProperty("accountZip")
	private String accountZip;
	@Schema(hidden = true)
	private boolean accountZipSet;

	@JsonProperty("accountCity")
	private String accountCity;
	@Schema(hidden = true)
	private boolean accountCitySet;

	@JsonProperty("accountCountry")
	private String accountCountry;
	@Schema(hidden = true)
	private boolean accountCountrySet;

	@Setter
	@Schema(description = "Sync advise about this contact's individual properties.\n" + PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	public void setName(final String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setQrIban(final String qrIban)
	{
		this.qrIban = qrIban;
		this.qrIbanSet = true;
	}

	public void setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
		this.currencyCodeSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setIsDefault(final Boolean isDefault)
	{
		this.isDefault = isDefault;
		this.isDefaultSet = true;
	}

	public void setAccountName(final String accountName)
	{
		this.accountName = accountName;
		this.accountNameSet = true;
	}

	public void setAccountStreet(final String accountStreet)
	{
		this.accountStreet = accountStreet;
		this.accountStreetSet = true;
	}

	public void setAccountZip(final String accountZip)
	{
		this.accountZip = accountZip;
		this.accountZipSet = true;
	}

	public void setAccountCity(final String accountCity)
	{
		this.accountCity = accountCity;
		this.accountCitySet = true;
	}

	public void setAccountCountry(final String accountCountry)
	{
		this.accountCountry = accountCountry;
		this.accountCountrySet = true;
	}

	public boolean getIsActive()
	{
		return CoalesceUtil.coalesceNotNull(active, true);
	}
}
