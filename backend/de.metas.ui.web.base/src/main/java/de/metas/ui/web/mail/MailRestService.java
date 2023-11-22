/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.mail;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.EmailAttachment;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.ReportResultData;
import de.metas.ui.web.print.WebuiDocumentPrintRequest;
import de.metas.ui.web.print.WebuiDocumentPrintService;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MailRestService
{
	private final AttachmentEntryService attachmentEntryService;
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final UserSession userSession;
	private final WebuiDocumentPrintService documentPrintService;

	public MailRestService(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final UserSession userSession,
			@NonNull final WebuiDocumentPrintService documentPrintService)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.userSession = userSession;
		this.documentPrintService = documentPrintService;
	}

	@NonNull
	public List<EmailAttachment> getEmailAttachments(
			@NonNull final DocumentPath documentPath,
			@NonNull final String tagName)
	{
		final TableRecordReference recordRef = documentDescriptorFactory.getTableRecordReference(documentPath);

		final WebuiDocumentPrintRequest printRequest = WebuiDocumentPrintRequest.builder()
				.flavor(DocumentReportFlavor.EMAIL)
				.documentPath(documentPath)
				.userId(userSession.getLoggedUserId())
				.roleId(userSession.getLoggedRoleId())
				.build();

		final Stream<EmailAttachment> emailAttachments = documentPrintService.createDocumentPrint(printRequest)
				.map(MailRestService::toEmailAttachment).stream();

		return Stream.concat(emailAttachments, attachmentEntryService.streamEmailAttachments(recordRef, tagName))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private static EmailAttachment toEmailAttachment(final ReportResultData contextDocumentPrint)
	{
		return EmailAttachment.builder()
				.filename(contextDocumentPrint.getReportFilename())
				.attachmentDataSupplier(contextDocumentPrint::getReportDataByteArray)
				.build();
	}
}
