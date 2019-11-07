package de.metas.rest_api.invoicecandidates.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.CREATE_OR_MERGE_SYNC_ADVISE_DOC;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
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
public class JsonRequestInvoiceCandidateUpsert
{
	@ApiModelProperty(position = 10, required = true)
	List<JsonRequestInvoiceCandidateUpsertItem> requestItems;

	@ApiModelProperty(position = 20, value = "Sync-advise for individual request items.\n" + CREATE_OR_MERGE_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@Builder
	private JsonRequestInvoiceCandidateUpsert(
			@Singular @NonNull final List<JsonRequestInvoiceCandidateUpsertItem> requestItems,
			@Nullable final SyncAdvise syncAdvise)
	{
		this.requestItems = requestItems;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.CREATE_OR_MERGE);
	}
}
