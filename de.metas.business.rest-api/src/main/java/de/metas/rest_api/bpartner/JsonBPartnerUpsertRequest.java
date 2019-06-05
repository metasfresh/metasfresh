package de.metas.rest_api.bpartner;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.SyncAdvise;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class JsonBPartnerUpsertRequest
{
	List<JsonBPartnerUpsertRequestItem> requestItems;

	@JsonProperty("syncAdvise")
	SyncAdvise syncAdvise;

	public JsonBPartnerUpsertRequest(
			@Singular final List<JsonBPartnerUpsertRequestItem> requestItems,
			@Nullable final SyncAdvise syncAdvise)
	{
		this.requestItems = requestItems;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
