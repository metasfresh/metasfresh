/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2026 metas GmbH
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * BPartner-specific upsert response item.
 * Extends {@link JsonResponseUpsertItem} with debtor/creditor numbers persisted for this business partner.
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class JsonResponseBPartnerUpsertItem extends JsonResponseUpsertItem
{
	@ApiModelProperty(value = "Debtor number persisted for this business partner (C_BPartner.DebtorId). Null when not set.")
	@JsonProperty("debtorId")
	@JsonInclude(Include.NON_NULL)
	@Nullable
	Integer debtorId;

	@ApiModelProperty(value = "Creditor number persisted for this business partner (C_BPartner.CreditorId). Null when not set.")
	@JsonProperty("creditorId")
	@JsonInclude(Include.NON_NULL)
	@Nullable
	Integer creditorId;

	@Builder(builderMethodName = "bpartnerUpsertItemBuilder")
	@JsonCreator
	public JsonResponseBPartnerUpsertItem(
			@JsonProperty("identifier") @NonNull final String identifier,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("syncOutcome") @NonNull final SyncOutcome syncOutcome,
			@JsonProperty("debtorId") @Nullable final Integer debtorId,
			@JsonProperty("creditorId") @Nullable final Integer creditorId)
	{
		super(identifier, metasfreshId, syncOutcome);
		this.debtorId = debtorId;
		this.creditorId = creditorId;
	}
}
