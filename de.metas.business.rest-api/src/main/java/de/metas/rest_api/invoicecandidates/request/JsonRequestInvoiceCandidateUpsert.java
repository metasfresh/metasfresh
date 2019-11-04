package de.metas.rest_api.invoicecandidates.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

import java.util.List;

import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
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
public class JsonRequestInvoiceCandidateUpsert
{
	@ApiModelProperty(position = 10)
	@Singular
	List<JsonRequestInvoiceCandidateUpsertItem> requestItems;

	@ApiModelProperty(position = 20, value = "Sync-advise for individual items.\n" + PARENT_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;
}
