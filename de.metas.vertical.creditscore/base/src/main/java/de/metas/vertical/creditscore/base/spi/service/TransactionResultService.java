package de.metas.vertical.creditscore.base.spi.service;

/*
 * #%L
 * de.metas.vertical.creditscore.base.spi.service
 * %%
 * Copyright (C) 2017 metas GmbH
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

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Results;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.CreditScoreRequestLogData;
import de.metas.vertical.creditscore.base.spi.repository.TransactionResultsRepository;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class TransactionResultService
{

	private final TransactionResultsRepository transactionResultsRepository;

	private final AttachmentEntryService attachmentEntryService;

	public TransactionResultService(@NonNull final TransactionResultsRepository transactionResultsRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.transactionResultsRepository = transactionResultsRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	public void createAndSaveResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId)
	{

		int id = transactionResultsRepository.createTransactionResult(creditScore, bPartnerId);

		final CreditScoreRequestLogData requestLogData = creditScore.getRequestLogData();
		if (requestLogData.getRequestData() != null)
		{
			final AttachmentEntryCreateRequest requestDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Request_ {0}", requestLogData.getCustomerTransactionID()),
							requestLogData.getRequestData().getBytes());
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_CS_Transaction_Results.Table_Name, id),
					requestDataAttachment);
		}

		if (requestLogData.getResponseData() != null)
		{
			final AttachmentEntryCreateRequest responseDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Response_ {0}", requestLogData.getCustomerTransactionID()),
							requestLogData.getResponseData().getBytes());
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_CS_Transaction_Results.Table_Name, id),
					responseDataAttachment);
		}

	}

}
