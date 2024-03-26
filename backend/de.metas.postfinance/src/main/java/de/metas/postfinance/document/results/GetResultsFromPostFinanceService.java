/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.document.results;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.organization.OrgId;
import de.metas.postfinance.B2BServiceWrapper;
import de.metas.postfinance.customerregistration.util.XMLUtil;
import de.metas.postfinance.docoutboundlog.PostFinanceLog;
import de.metas.postfinance.docoutboundlog.PostFinanceLogCreateRequest;
import de.metas.postfinance.docoutboundlog.PostFinanceLogRepository;
import de.metas.postfinance.document.export.PostFinanceExportException;
import de.metas.postfinance.document.results.model.XmlProcessResult;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetResultsFromPostFinanceService
{
	@NonNull private final B2BServiceWrapper b2BServiceWrapper;
	@NonNull private final PostFinanceLogRepository postFinanceLogRepository;
	@NonNull private final AttachmentEntryService attachmentEntryService;

	public void handleResultsFromPostFinance(@NonNull final OrgId orgId)
	{
		final List<DownloadFile> processProtocol = b2BServiceWrapper.getProcessProtocol(orgId);
		processProtocol.forEach(file -> handleResultsFromPostFinance (file, orgId));
	}

	private void handleResultsFromPostFinance(@NonNull final DownloadFile downloadFile, @NonNull final OrgId orgId)
	{

		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = createAttachmentRequest(downloadFile);

		final List<XmlProcessResult> xmlProcessResults = XMLUtil.getXmlProcessProtocol(downloadFile)
						.processResults(); // TODO HERE
		xmlProcessResults.forEach(processResult -> handleProcessResult(processResult, attachmentEntryCreateRequest, orgId));




	}

	private void handleProcessResult(final XmlProcessResult processResult, final AttachmentEntryCreateRequest attachmentEntryCreateRequest, final OrgId orgId)
	{
		final String transactionID = processResult.transactionID();
		final PostFinanceLog postFinanceLog = postFinanceLogRepository.retrieveLatestLogWithTransactionId(transactionID)
				.orElseThrow();

		final TableRecordReference docOutboundLogReference = TableRecordReference.of(I_C_Doc_Outbound_Log.Table_Name, postFinanceLog.getDocOutboundLogId());

		attachmentEntryService.createNewAttachment(docOutboundLogReference,attachmentEntryCreateRequest);

		final @NonNull PostFinanceLogCreateRequest postFinanceLogCreateRequest = PostFinanceLogCreateRequest.builder()
				.docOutboundLogId(postFinanceLog.getDocOutboundLogId())
				.transactionId(transactionID)
				.postFinanceExportException(new PostFinanceExportException(processResult.reasonCode() + " " + processResult.reasonText()))
				.message(processResult.reasonCode() + " " + processResult.reasonText())
				.build();

		postFinanceLogRepository.create(postFinanceLogCreateRequest);

	}

	@NonNull
	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final DownloadFile downloadFile)
	{
		// TODO refactor to not have duplicate code
		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.build();

		return AttachmentEntryCreateRequest
				.builderFromByteArray(
						downloadFile.getFilename().getValue(),
						downloadFile.getData().getValue())
				.tags(attachmentTags)
				.build();
	}


}
