/*
 * #%L
 * de.metas.banking.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.banking.importfile.listener;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentLinksRequest;
import de.metas.attachments.AttachmentService;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.banking.importfile.BankStatementImportFileRepository;
import de.metas.banking.importfile.BankStatementImportFileService;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import org.adempiere.banking.model.I_C_BankStatement_Import_File;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A bank statement import file holds exactly one file: linking a second attachment to it is refused.
 * Runs through {@link AttachmentService}, i.e. the same path as a user upload.
 */
class BankStatementImportFileAttachmentListenerTest
{
	private static final String MSG_MULTIPLE_ATTACHMENTS_NOT_ALLOWED = "MultipleAttachmentsNotAllowedFAILURE";

	private AttachmentService attachmentService;
	private I_C_BankStatement_Import_File importFileRecord;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		attachmentService = AttachmentService.createInstanceForUnitTesting();
		SpringContextHolder.registerJUnitBean(attachmentService);
		SpringContextHolder.registerJUnitBean(new BankStatementImportFileService(new BankStatementImportFileRepository()));

		registerListenerForImportFileTable();

		importFileRecord = newInstance(I_C_BankStatement_Import_File.class);
		saveRecord(importFileRecord);
	}

	private static void registerListenerForImportFileTable()
	{
		final I_AD_JavaClass_Type javaClassType = newInstance(I_AD_JavaClass_Type.class);
		javaClassType.setName(AttachmentListener.class.getSimpleName());
		javaClassType.setClassname(AttachmentListener.class.getName());
		saveRecord(javaClassType);

		final I_AD_JavaClass javaClass = newInstance(I_AD_JavaClass.class);
		javaClass.setAD_JavaClass_Type_ID(javaClassType.getAD_JavaClass_Type_ID());
		javaClass.setName(BankStatementImportFileAttachmentListener.class.getSimpleName());
		javaClass.setClassname(BankStatementImportFileAttachmentListener.class.getName());
		saveRecord(javaClass);

		// same message as the shipped AD_Table_AttachmentListener record of C_BankStatement_Import_File
		final I_AD_Message failureMessage = newInstance(I_AD_Message.class);
		failureMessage.setValue(MSG_MULTIPLE_ATTACHMENTS_NOT_ALLOWED);
		failureMessage.setMsgText("Multiple attachments not allowed.");
		saveRecord(failureMessage);

		final I_AD_Table_AttachmentListener listenerRecord = newInstance(I_AD_Table_AttachmentListener.class);
		listenerRecord.setAD_Table_ID(getTableId(I_C_BankStatement_Import_File.class));
		listenerRecord.setAD_JavaClass_ID(javaClass.getAD_JavaClass_ID());
		listenerRecord.setSeqNo(10);
		listenerRecord.setIsSendNotification(false);
		listenerRecord.setAD_Message_ID(failureMessage.getAD_Message_ID());
		saveRecord(listenerRecord);
	}

	@Test
	void firstAttachment_isLinked_andSetsTheFileName()
	{
		attachmentService.createNewAttachment(importFileRecord, "statement1.xml", "<a/>".getBytes(StandardCharsets.UTF_8));

		assertThat(attachmentService.getByReferencedRecord(importFileRecord))
				.extracting(AttachmentEntry::getFilename)
				.containsExactly("statement1.xml");
		refresh(importFileRecord);
		assertThat(importFileRecord.getFileName()).isEqualTo("statement1.xml");
	}

	@Test
	void secondAttachment_isRefused_andNotLinked()
	{
		attachmentService.createNewAttachment(importFileRecord, "statement1.xml", "<a/>".getBytes(StandardCharsets.UTF_8));

		assertThatThrownBy(() -> attachmentService.createNewAttachment(importFileRecord, "statement2.xml", "<b/>".getBytes(StandardCharsets.UTF_8)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_MULTIPLE_ATTACHMENTS_NOT_ALLOWED)
				.matches(e -> ((AdempiereException)e).isUserValidationError());

		assertThat(attachmentService.getByReferencedRecord(TableRecordReference.of(importFileRecord)))
				.extracting(AttachmentEntry::getFilename)
				.containsExactly("statement1.xml");
		refresh(importFileRecord);
		assertThat(importFileRecord.getFileName()).isEqualTo("statement1.xml");
	}

	@Test
	void relinkingTheSameAttachment_isAllowed()
	{
		final AttachmentEntry entry = attachmentService.createNewAttachment(importFileRecord, "statement1.xml", "<a/>".getBytes(StandardCharsets.UTF_8));

		attachmentService.shareAttachmentLinks(
				ImmutableList.of(importFileRecord),
				ImmutableList.of(importFileRecord));

		assertThat(attachmentService.getByReferencedRecord(importFileRecord))
				.extracting(AttachmentEntry::getId)
				.containsExactly(entry.getId());
	}

	@Test
	void linkingAnExistingAttachmentFromAnotherRecord_isRefused()
	{
		attachmentService.createNewAttachment(importFileRecord, "statement1.xml", "<a/>".getBytes(StandardCharsets.UTF_8));

		final I_C_BankStatement_Import_File otherImportFileRecord = newInstance(I_C_BankStatement_Import_File.class);
		saveRecord(otherImportFileRecord);
		final AttachmentEntry otherEntry = attachmentService.createNewAttachment(otherImportFileRecord, "statement2.xml", "<b/>".getBytes(StandardCharsets.UTF_8));

		final AttachmentLinksRequest linkToFirstRecord = AttachmentLinksRequest.builder()
				.attachmentEntryId(otherEntry.getIdNonNull())
				.linksToAdd(ImmutableList.of(TableRecordReference.of(importFileRecord)))
				.build();
		assertThatThrownBy(() -> attachmentService.handleAttachmentLinks(linkToFirstRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_MULTIPLE_ATTACHMENTS_NOT_ALLOWED);

		assertThat(attachmentService.getByReferencedRecord(importFileRecord))
				.extracting(AttachmentEntry::getFilename)
				.containsExactly("statement1.xml");
	}

	@Test
	void linkingToSeveralRecords_oneRefused_noneIsLinked()
	{
		attachmentService.createNewAttachment(importFileRecord, "statement1.xml", "<a/>".getBytes(StandardCharsets.UTF_8));

		final I_C_BankStatement_Import_File otherImportFileRecord = newInstance(I_C_BankStatement_Import_File.class);
		saveRecord(otherImportFileRecord);
		final AttachmentEntry otherEntry = attachmentService.createNewAttachment(otherImportFileRecord, "statement2.xml", "<b/>".getBytes(StandardCharsets.UTF_8));

		final I_C_BankStatement_Import_File emptyImportFileRecord = newInstance(I_C_BankStatement_Import_File.class);
		saveRecord(emptyImportFileRecord);

		final AttachmentLinksRequest linkToEmptyAndFirstRecord = AttachmentLinksRequest.builder()
				.attachmentEntryId(otherEntry.getIdNonNull())
				.linksToAdd(ImmutableList.of(TableRecordReference.of(emptyImportFileRecord), TableRecordReference.of(importFileRecord)))
				.build();
		assertThatThrownBy(() -> attachmentService.handleAttachmentLinks(linkToEmptyAndFirstRecord))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_MULTIPLE_ATTACHMENTS_NOT_ALLOWED);

		assertThat(attachmentService.getByReferencedRecord(emptyImportFileRecord)).isEmpty();
		refresh(emptyImportFileRecord);
		assertThat(emptyImportFileRecord.getFileName()).isNull();
	}
}
