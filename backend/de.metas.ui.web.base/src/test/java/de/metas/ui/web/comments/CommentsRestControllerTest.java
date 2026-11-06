/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.comments;

import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentPermissionException;
import de.metas.ui.web.window.exceptions.DocumentPermissionException.DocumentPermission;
import de.metas.ui.web.window.model.DocumentCollection;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Makes sure that a document's comments can only be accessed by users who are allowed to view that document.
 */
class CommentsRestControllerTest
{
	private static final String WINDOW_ID = "123";
	private static final String DOCUMENT_ID = "1000123";
	private static final DocumentPath DOCUMENT_PATH = DocumentPath.rootDocumentPath(WindowId.fromJson(WINDOW_ID), DOCUMENT_ID);
	private static final String VIEWABLE_DOCUMENT_ID = "1000124";
	private static final DocumentPath VIEWABLE_DOCUMENT_PATH = DocumentPath.rootDocumentPath(WindowId.fromJson(WINDOW_ID), VIEWABLE_DOCUMENT_ID);

	private CommentsService commentsService;
	private CommentsRestController restController;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DocumentCollection documentCollection = mock(DocumentCollection.class);
		doThrow(DocumentPermissionException.noAccess(DocumentPermission.WindowAccess))
				.when(documentCollection).assertCanViewRootDocument(DOCUMENT_PATH);

		commentsService = mock(CommentsService.class);

		restController = new CommentsRestController(
				mock(UserSession.class),
				mock(DocumentDescriptorFactory.class),
				documentCollection,
				commentsService);
	}

	@Test
	void getAll_noViewAccess()
	{
		assertThatThrownBy(() -> restController.getAll(WINDOW_ID, DOCUMENT_ID))
				.isInstanceOf(DocumentPermissionException.class);
		verifyNoMoreInteractions(commentsService);
	}

	@Test
	void addComment_viewAccess()
	{
		final JSONCommentCreateRequest request = new JSONCommentCreateRequest("comment");

		restController.addComment(WINDOW_ID, VIEWABLE_DOCUMENT_ID, request);

		verify(commentsService).addComment(VIEWABLE_DOCUMENT_PATH, request);
	}

	@Test
	void addComment_noViewAccess()
	{
		final JSONCommentCreateRequest request = new JSONCommentCreateRequest("injected");

		assertThatThrownBy(() -> restController.addComment(WINDOW_ID, DOCUMENT_ID, request))
				.isInstanceOf(DocumentPermissionException.class);
		verifyNoMoreInteractions(commentsService);
	}
}
