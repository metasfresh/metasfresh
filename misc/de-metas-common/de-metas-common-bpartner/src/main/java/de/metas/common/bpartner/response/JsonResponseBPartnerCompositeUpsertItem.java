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

package de.metas.common.bpartner.response;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
public class JsonResponseBPartnerCompositeUpsertItem
{
	@ApiModelProperty(position = 10)
	@JsonInclude(Include.NON_NULL)
	JsonResponseUpsertItem responseBPartnerItem;

	@ApiModelProperty(position = 20)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseLocationItems;

	@ApiModelProperty(position = 30)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseContactItems;

	@ApiModelProperty(position = 40)
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseUpsertItem> responseBankAccountItems;

	@Builder
	@JsonCreator
	public JsonResponseBPartnerCompositeUpsertItem(
			@JsonProperty("responseBPartnerItem") @Nullable final JsonResponseUpsertItem responseBPartnerItem,
			@JsonProperty("responseLocationItems") @Singular final List<JsonResponseUpsertItem> responseLocationItems,
			@JsonProperty("responseContactItems") @Singular final List<JsonResponseUpsertItem> responseContactItems,
			@JsonProperty("responseBankAccountItems") @Singular final List<JsonResponseUpsertItem> responseBankAccountItems)
	{
		this.responseBPartnerItem = responseBPartnerItem;
		this.responseLocationItems = responseLocationItems;
		this.responseContactItems = responseContactItems;
		this.responseBankAccountItems = responseBankAccountItems;
	}


}
