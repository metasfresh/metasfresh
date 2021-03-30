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

package de.metas.common.bpartner.request;

import static de.metas.common.rest_api.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@ApiModel(description = "Contains the bank account to be inserted or delete. The bank account is identified by IBAN.")
public class JsonRequestBankAccountUpsertItem
{
	@ApiModelProperty(position = 10, allowEmptyValue = false)
	@JsonProperty("iban")
	final String iban;

	@ApiModelProperty(position = 20, allowEmptyValue = true)
	@JsonProperty("currencyCode")
	final String currencyCode;

	@ApiModelProperty(position = 30, required = false, value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("active")
	Boolean active;

	@ApiModelProperty(position = 40, required = false, value = "Sync advise about this contact's individual properties.\n" + PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@JsonCreator
	public JsonRequestBankAccountUpsertItem(
			@JsonProperty("iban") @NonNull String iban,
			@JsonProperty("currencyCode") @Nullable final String currencyCode,
			@JsonProperty("active") @Nullable final Boolean active,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.iban = iban;
		this.currencyCode = currencyCode;
		this.active = active;
		this.syncAdvise = syncAdvise;
	}
}
