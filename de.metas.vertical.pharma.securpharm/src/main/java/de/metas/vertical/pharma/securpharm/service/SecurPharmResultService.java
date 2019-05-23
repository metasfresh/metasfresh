/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import java.text.MessageFormat;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResultId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmRequestLogData;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import lombok.NonNull;

@Service
public class SecurPharmResultService
{
	private final SecurPharmResultRepository resultRepository;
	private final AttachmentEntryService attachmentEntryService;

	public SecurPharmResultService(
			@NonNull final SecurPharmResultRepository resultRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.resultRepository = resultRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	public void saveNew(@NonNull final SecurPharmProductDataResult result)
	{
		resultRepository.saveNew(result);
		addResultAttachment(result.getRecordRef(), result.getRequestLogData());
	}

	public void saveNew(@NonNull final SecurPharmActionResult result)
	{
		resultRepository.saveNew(result);
		addResultAttachment(result.getRecordRef(), result.getRequestLogData());
	}

	private void addResultAttachment(
			@NonNull final TableRecordReference recordRef,
			@NonNull final SecurPharmRequestLogData logData)
	{
		if (logData.getResponseData() != null)
		{
			final AttachmentEntryCreateRequest responseDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Response_{0}", logData.getClientTransactionId()),
							logData.getResponseData().getBytes());
			attachmentEntryService.createNewAttachment(recordRef, responseDataAttachment);
		}
	}

	SecurPharmProductDataResult getProductDataResultById(@NonNull final SecurPharmProductDataResultId productDataResultId)
	{
		return resultRepository.getProductDataResultById(productDataResultId);
	}

}
