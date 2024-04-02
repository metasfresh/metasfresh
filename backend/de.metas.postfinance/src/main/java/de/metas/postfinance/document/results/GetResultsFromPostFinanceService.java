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
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log;
import de.metas.organization.OrgId;
import de.metas.postfinance.B2BServiceWrapper;
import de.metas.postfinance.docoutboundlog.PostFinanceLog;
import de.metas.postfinance.docoutboundlog.PostFinanceLogCreateRequest;
import de.metas.postfinance.docoutboundlog.PostFinanceLogRepository;
import de.metas.postfinance.document.export.PostFinanceExportException;
import de.metas.postfinance.jaxb.DownloadFile;
import de.metas.postfinance.processprotocol.Envelope;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetResultsFromPostFinanceService
{
	@NonNull private final B2BServiceWrapper b2BServiceWrapper;
	@NonNull private final PostFinanceLogRepository postFinanceLogRepository;
	@NonNull private final AttachmentEntryService attachmentEntryService;

	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);

	public void handleResultsFromPostFinance(@NonNull final OrgId orgId, @NonNull final TableRecordReference adPInstanceReference)
	{
		final List<DownloadFile> processProtocol = b2BServiceWrapper.getProcessProtocol(orgId);
		processProtocol.forEach(file -> {

			final AttachmentEntryCreateRequest attachmentEntryCreateRequest = createAttachmentRequest(file);
			attachmentEntryService.createNewAttachment(adPInstanceReference, attachmentEntryCreateRequest);

			try
			{
				final JAXBContext jc = JAXBContext.newInstance(Envelope.class);
				final Unmarshaller unmarshaller = jc.createUnmarshaller();
				final ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getData().getValue());

				final Envelope envelope = (Envelope)unmarshaller.unmarshal(inputStream);

				handleTransactionDetails(envelope.getTransactionDetails(), attachmentEntryCreateRequest);
			}
			catch (final JAXBException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}

		});
	}

	private void handleTransactionDetails(@NonNull final Envelope.TransactionDetails transactionDetails,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		if(EnumUtils.isValidEnum(ProcessingStatusWithError.class, transactionDetails.getStatus()))
		{
			handleResultsWithErrors(transactionDetails, attachmentEntryCreateRequest);
		}
		else
		{
			handleValidResults(transactionDetails, attachmentEntryCreateRequest);
		}
	}

	private void handleResultsWithErrors(@NonNull final Envelope.TransactionDetails transactionDetails,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final String transactionID = transactionDetails.getTransactionID();
		final Optional<PostFinanceLog> postFinanceLogOptional = postFinanceLogRepository.retrieveLatestLogWithTransactionId(transactionID);

		if (postFinanceLogOptional.isEmpty())
		{
			return;
		}
		final DocOutboundLogId docOutboundLogId = postFinanceLogOptional.get().getDocOutboundLogId();

		final String errorMessage;
		boolean isException = true;
		if ("05".equals(transactionDetails.getReasonCode()))
		{
			errorMessage = "The document was sent again before receiving processing result of valid document. Ignoring " + transactionDetails.getReasonCode() + " " + transactionDetails.getReasonText();
			isException = false;
		}
		else
		{
			errorMessage = transactionDetails.getStatus() + " " + transactionDetails.getReasonCode() + " " + transactionDetails.getReasonText();
		}
		final PostFinanceLogCreateRequest postFinanceLogCreateRequest = PostFinanceLogCreateRequest.builder()
				.docOutboundLogId(docOutboundLogId)
				.transactionId(transactionID)
				.postFinanceExportException(isException ? new PostFinanceExportException(transactionDetails.getReasonCode() + " " + transactionDetails.getReasonText()) : null)
				.message(errorMessage)
				.build();

		sendResultToDocOutbound(attachmentEntryCreateRequest,
								postFinanceLogCreateRequest,
								docOutboundLogId);

	}

	private void handleValidResults(@NonNull final Envelope.TransactionDetails transactionDetails,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final String transactionID = transactionDetails.getTransactionID();
		final Optional<PostFinanceLog> postFinanceLogOptional = postFinanceLogRepository.retrieveLatestLogWithTransactionId(transactionID);

		if (postFinanceLogOptional.isEmpty())
		{
			return;
		}

		final DocOutboundLogId docOutboundLogId = postFinanceLogOptional.get().getDocOutboundLogId();
		final PostFinanceLogCreateRequest postFinanceLogCreateRequest = PostFinanceLogCreateRequest.builder()
				.docOutboundLogId(docOutboundLogId)
				.transactionId(transactionID)
				.message("PostFinance result status " + transactionDetails.getStatus())
				.build();

		sendResultToDocOutbound(attachmentEntryCreateRequest,
								postFinanceLogCreateRequest,
								docOutboundLogId);

	}

	private void sendResultToDocOutbound(final @NonNull AttachmentEntryCreateRequest attachmentEntryCreateRequest,
			@NonNull final PostFinanceLogCreateRequest postFinanceLogCreateRequest,
			@NonNull final DocOutboundLogId docOutboundLogId)
	{
		final TableRecordReference docOutboundLogReference = TableRecordReference.of(I_C_Doc_Outbound_Log.Table_Name, docOutboundLogId);

		attachmentEntryService.createNewAttachment(docOutboundLogReference, attachmentEntryCreateRequest);

		final PostFinanceLog postFinanceLog = postFinanceLogRepository.create(postFinanceLogCreateRequest);

		if (postFinanceLog.isError())
		{
			docOutboundDAO.setPostFinanceExportStatus(docOutboundLogId, X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_Error);
		}
	}

	@NonNull
	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final DownloadFile downloadFile)
	{
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
