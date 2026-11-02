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

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentAttachmentsTest
{
	private static final WindowId BPARTNER_WINDOW_ID = WindowId.of(123);

	private AttachmentEntryService attachmentEntryService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10); // will be in the attachment-entry's CreatedUpdatedInfo

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
	}

	@Test
	void getEntry_attachmentOfTheDocument()
	{
		final I_C_BPartner bpartner = createBPartner();
		final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(bpartner, "file.txt", "data".getBytes());

		final IDocumentAttachmentEntry entry = documentAttachmentsOf(bpartner).getEntry(attachmentDocumentId(attachmentEntry));

		assertThat(entry.getId()).isEqualTo(attachmentDocumentId(attachmentEntry));
		assertThat(entry.getFilename()).isEqualTo("file.txt");
	}

	@Test
	void getEntry_attachmentOfAnotherRecord_isNotFound()
	{
		final I_C_BPartner bpartner = createBPartner();
		final I_C_BPartner otherBPartner = createBPartner();
		final AttachmentEntry otherAttachmentEntry = attachmentEntryService.createNewAttachment(otherBPartner, "secret.pdf", "secret".getBytes());

		final DocumentAttachments documentAttachments = documentAttachmentsOf(bpartner);
		final DocumentId otherAttachmentDocumentId = attachmentDocumentId(otherAttachmentEntry);

		assertThatThrownBy(() -> documentAttachments.getEntry(otherAttachmentDocumentId))
				.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	void deleteEntry_attachmentOfAnotherRecord_isNotFoundAndStaysAttached()
	{
		final I_C_BPartner bpartner = createBPartner();
		final I_C_BPartner otherBPartner = createBPartner();
		final AttachmentEntry otherAttachmentEntry = attachmentEntryService.createNewAttachment(otherBPartner, "secret.pdf", "secret".getBytes());

		final DocumentAttachments documentAttachments = documentAttachmentsOf(bpartner);
		final DocumentId otherAttachmentDocumentId = attachmentDocumentId(otherAttachmentEntry);

		assertThatThrownBy(() -> documentAttachments.deleteEntry(otherAttachmentDocumentId))
				.isInstanceOf(EntityNotFoundException.class);
		assertThat(attachmentEntryService.getByReferencedRecord(otherBPartner))
				.extracting(AttachmentEntry::getId)
				.containsExactly(otherAttachmentEntry.getId());
	}

	@Test
	void deleteEntry_attachmentOfTheDocument()
	{
		final I_C_BPartner bpartner = createBPartner();
		final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(bpartner, "file.txt", "data".getBytes());

		documentAttachmentsOf(bpartner).deleteEntry(attachmentDocumentId(attachmentEntry));

		assertThat(attachmentEntryService.getByReferencedRecord(bpartner)).isEmpty();
	}

	private static I_C_BPartner createBPartner()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		saveRecord(bpartner);
		return bpartner;
	}

	private DocumentAttachments documentAttachmentsOf(@NonNull final I_C_BPartner bpartner)
	{
		return DocumentAttachments.builder()
				.documentPath(DocumentPath.rootDocumentPath(BPARTNER_WINDOW_ID, DocumentId.of(bpartner.getC_BPartner_ID())))
				.recordRef(TableRecordReference.of(bpartner))
				.entityDescriptor(Mockito.mock(DocumentEntityDescriptor.class))
				.websocketPublisher(Mockito.mock(DocumentWebsocketPublisher.class))
				.attachmentEntryService(attachmentEntryService)
				.tableAttachmentListenerService(new TableAttachmentListenerService(new TableAttachmentListenerRepository()))
				.build();
	}

	private static DocumentId attachmentDocumentId(@NonNull final AttachmentEntry attachmentEntry)
	{
		return DocumentId.ofString(DocumentAttachments.ID_PREFIX_Attachment + "_" + attachmentEntry.getId().getRepoId());
	}
}
