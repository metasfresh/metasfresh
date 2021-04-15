package de.metas.rest_api.v1.ordercandidates;

import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v1.response.JsonAttachment;
import de.metas.common.ordercandidates.v1.response.JsonOLCandCreateBulkResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface OrderCandidatesRestEndpoint
{
	ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidate(JsonOLCandCreateRequest request);

	ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidates(JsonOLCandCreateBulkRequest bulkRequest);

	ResponseEntity<JsonAttachment> attachFile(
			String dataSourceName,
			String externalReference,
			List<String> tagKeyValuePairs,
			MultipartFile file) throws IOException;
}
