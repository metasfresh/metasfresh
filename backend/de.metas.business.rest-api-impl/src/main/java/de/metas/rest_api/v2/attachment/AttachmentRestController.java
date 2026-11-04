/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.attachment;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentResponse;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/attachment" })
@Profile(Profiles.PROFILE_App)
public class AttachmentRestController
{
	private final AttachmentRestService attachmentRestService;

	public AttachmentRestController(final AttachmentRestService attachmentRestService)
	{
		this.attachmentRestService = attachmentRestService;
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Attachment successfully created"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to invoke attachment endpoint"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request could not be processed")
	})
	@PostMapping()
	public ResponseEntity<JsonAttachmentResponse> createAttachment(@RequestBody @NonNull final JsonAttachmentRequest jsonAttachmentRequest)
	{
		return ResponseEntity.ok(attachmentRestService.createAttachment(jsonAttachmentRequest));
	}
}
