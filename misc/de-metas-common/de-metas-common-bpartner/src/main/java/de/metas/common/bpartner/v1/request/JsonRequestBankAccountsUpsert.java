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

package de.metas.common.bpartner.v1.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.rest_api.v1.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@Schema
public class JsonRequestBankAccountsUpsert
{
	public static final JsonRequestBankAccountsUpsert NONE = builder().build();

	@Schema
	List<JsonRequestBankAccountUpsertItem> requestItems;

	@Schema(description = "Sync-advise for individual items.\n" + PARENT_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder
	public JsonRequestBankAccountsUpsert(
			@Singular @JsonProperty("requestItems") final List<JsonRequestBankAccountUpsertItem> requestItems,
			@Nullable @JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.requestItems = coalesce(requestItems, ImmutableList.of());
		this.syncAdvise = syncAdvise;
	}
}
