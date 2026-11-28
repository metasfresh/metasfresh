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

package de.metas.ui.web.attachments;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.attachments.json.JSONAttachURLRequest;
import de.metas.ui.web.attachments.json.JSONAttachment;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.ui.web.window.exceptions.DocumentPermissionException;
import de.metas.ui.web.window.exceptions.DocumentPermissionException.DocumentPermission;
import de.metas.ui.web.window.model.DocumentCollection;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Makes sure that a document's attachments and archives can only be accessed by users who are allowed to view that document.
 */
class DocumentAttachmentsRestControllerTest
{
	private static final String WINDOW_ID = "123";
	private static final String DOCUMENT_ID = "1000123";
	private static final DocumentPath DOCUMENT_PATH = DocumentPath.rootDocumentPath(WindowId.fromJson(WINDOW_ID), DOCUMENT_ID);
	private static final String VIEWABLE_DOCUMENT_ID = "1000124";
	private static final DocumentPath VIEWABLE_DOCUMENT_PATH = DocumentPath.rootDocumentPath(WindowId.fromJson(WINDOW_ID), VIEWABLE_DOCUMENT_ID);

	private DocumentDescriptorFactory documentDescriptorFactory;
	private AttachmentEntryService attachmentEntryService;
	private DocumentAttachmentsRestController restController;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final UserSession userSession = mock(UserSession.class);
		final IUserRolePermissions permissions = mock(IUserRolePermissions.class);
		when(userSession.getUserRolePermissions()).thenReturn(permissions);
		when(permissions.hasPermission(any())).thenReturn(true);

		final DocumentCollection documentCollection = mock(DocumentCollection.class);
		doThrow(DocumentPermissionException.noAccess(DocumentPermission.WindowAccess))
				.when(documentCollection).assertCanViewRootDocument(DOCUMENT_PATH);

		documentDescriptorFactory = mock(DocumentDescriptorFactory.class);
		when(documentDescriptorFactory.getTableRecordReference(VIEWABLE_DOCUMENT_PATH)).thenReturn(TableRecordReference.of(I_C_BPartner.Table_Name, 1000124));
		when(documentDescriptorFactory.getDocumentEntityDescriptor(VIEWABLE_DOCUMENT_PATH)).thenReturn(mock(DocumentEntityDescriptor.class));

		attachmentEntryService = mock(AttachmentEntryService.class);

		restController = new DocumentAttachmentsRestController(
				userSession,
				documentDescriptorFactory,
				documentCollection,
				mock(DocumentWebsocketPublisher.class),
				attachmentEntryService,
				mock(TableAttachmentListenerService.class));
	}

	@Test
	void getAttachments_viewAccess()
	{
		final List<JSONAttachment> attachments = restController.getAttachments(WINDOW_ID, VIEWABLE_DOCUMENT_ID);

		assertThat(attachments).isEmpty();
		verify(attachmentEntryService).getByReferencedRecord(TableRecordReference.of(I_C_BPartner.Table_Name, 1000124));
	}

	@Test
	void getAttachments_noViewAccess()
	{
		assertThatThrownBy(() -> restController.getAttachments(WINDOW_ID, DOCUMENT_ID))
				.isInstanceOf(DocumentPermissionException.class);
		assertNoAttachmentsTouched();
	}

	@Test
	void getAttachmentById_noViewAccess()
	{
		assertThatThrownBy(() -> restController.getAttachmentById(WINDOW_ID, DOCUMENT_ID, "ATT_1"))
				.isInstanceOf(DocumentPermissionException.class);
		assertNoAttachmentsTouched();
	}

	@Test
	void attachFile_noViewAccess()
	{
		final MultipartFile file = mock(MultipartFile.class);

		assertThatThrownBy(() -> restController.attachFile(WINDOW_ID, DOCUMENT_ID, file))
				.isInstanceOf(DocumentPermissionException.class);
		assertNoAttachmentsTouched();
	}

	@Test
	void attachURL_noViewAccess() throws Exception
	{
		final JSONAttachURLRequest request = new ObjectMapper().readValue("{\"name\":\"link\",\"url\":\"https://example.com\"}", JSONAttachURLRequest.class);

		assertThatThrownBy(() -> restController.attachURL(WINDOW_ID, DOCUMENT_ID, request))
				.isInstanceOf(DocumentPermissionException.class);
		assertNoAttachmentsTouched();
	}

	@Test
	void deleteAttachmentById_noViewAccess()
	{
		assertThatThrownBy(() -> restController.deleteAttachmentById(WINDOW_ID, DOCUMENT_ID, "ATT_1"))
				.isInstanceOf(DocumentPermissionException.class);
		assertNoAttachmentsTouched();
	}

	private void assertNoAttachmentsTouched()
	{
		verifyNoMoreInteractions(documentDescriptorFactory, attachmentEntryService);
	}
}
