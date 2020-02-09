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
import de.metas.order.OrderId;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.CreditScoreRequestLogData;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.base.spi.repository.TransactionResultId;
import de.metas.vertical.creditscore.base.spi.repository.TransactionResultRepository;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Optional;

@Service
public class TransactionResultService
{

	private final TransactionResultRepository transactionResultsRepository;

	private final AttachmentEntryService attachmentEntryService;

	public TransactionResultService(@NonNull final TransactionResultRepository transactionResultsRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.transactionResultsRepository = transactionResultsRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	public TransactionResult createAndSaveResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId
			, final OrderId orderId)
	{

		TransactionResult transactionResult = transactionResultsRepository.createTransactionResult(creditScore, bPartnerId, orderId);
		addResultAttachements(creditScore, transactionResult.getTransactionResultId());
		return transactionResult;

	}

	private void addResultAttachements(@NonNull final CreditScore creditScore, @NonNull final TransactionResultId transactionResultId)
	{
		final CreditScoreRequestLogData requestLogData = creditScore.getRequestLogData();
		if (requestLogData.getRequestData() != null)
		{
			final AttachmentEntryCreateRequest requestDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Request_{0}", requestLogData.getCustomerTransactionID()),
							requestLogData.getRequestData().getBytes(StandardCharsets.UTF_8));
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_CS_Transaction_Result.Table_Name, transactionResultId),
					requestDataAttachment);
		}

		if (requestLogData.getResponseData() != null)
		{
			final AttachmentEntryCreateRequest responseDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Response_{0}", requestLogData.getCustomerTransactionID()),
							requestLogData.getResponseData().getBytes(StandardCharsets.UTF_8));
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_CS_Transaction_Result.Table_Name, transactionResultId),
					responseDataAttachment);
		}
	}

	public Optional<TransactionResult> findLastTransactionResult(@NonNull final String paymentRule, @NonNull final BPartnerId bPartnerId)
	{
		return transactionResultsRepository
				.getLastTransactionResult(paymentRule, bPartnerId);
	}

}
