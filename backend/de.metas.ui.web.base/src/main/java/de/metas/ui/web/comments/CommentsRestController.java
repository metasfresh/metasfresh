/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.comments;

import de.metas.ui.web.comments.json.JSONComment;
import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(CommentsRestController.ENDPOINT)
public class CommentsRestController
{
	protected static final String ENDPOINT = WindowRestController.ENDPOINT + "/{windowId}/{documentId}/comments";
	private final UserSession userSession;

	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final CommentsService commentsService;

	public CommentsRestController(
			final UserSession userSession,
			final DocumentDescriptorFactory documentDescriptorFactory, final CommentsService commentsService)
	{
		this.userSession = userSession;
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.commentsService = commentsService;
	}

	@GetMapping
	public List<JSONComment> getAll(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentId
	)
	{
		userSession.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentId);

		final ZoneId zoneId = JSONOptions.of(userSession).getZoneId();
		return commentsService.getRowCommentsAsJson(documentPath, zoneId);
	}

	@PostMapping
	public void addComment(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentId,
			@RequestBody final JSONCommentCreateRequest jsonCommentCreateRequest
	)
	{
		userSession.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentId);

		commentsService.addComment(documentPath, jsonCommentCreateRequest);
	}
}
