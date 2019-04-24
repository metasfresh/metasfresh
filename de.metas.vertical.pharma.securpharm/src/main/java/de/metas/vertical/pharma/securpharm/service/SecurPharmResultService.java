/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.util.lang.RepoIdAware;
import de.metas.vertical.pharma.securpharm.model.*;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class SecurPharmResultService
{
	private final SecurPharmResultRepository resultRepository;

	private final AttachmentEntryService attachmentEntryService;

	public SecurPharmResultService(@NonNull final SecurPharmResultRepository resultRepository, @NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.resultRepository = resultRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	public SecurPharmProductDataResult createAndSaveResult(@NonNull final SecurPharmProductDataResult productDataResult)
	{

		SecurPharmProductDataResult result = resultRepository.createResult(productDataResult);
		addResultAttachment(result.getRequestLogData(), result.getResultId(), I_M_Securpharm_Productdata_Result.Table_Name);
		return result;

	}

	public SecurPharmActionResult createAndSaveResult(@NonNull final SecurPharmActionResult productDataResult)
	{

		SecurPharmActionResult result = resultRepository.createResult(productDataResult);
		addResultAttachment(result.getRequestLogData(), result.getResultId(), I_M_Securpharm_Action_Result.Table_Name);
		return result;

	}

	private void addResultAttachment(@NonNull final SecurPharmRequestLogData logData, @NonNull final RepoIdAware resultId, @NonNull final String tableName)
	{
		if (logData.getResponseData() != null)
		{
			final AttachmentEntryCreateRequest responseDataAttachment = AttachmentEntryCreateRequest
					.fromByteArray(
							MessageFormat.format("Response_{0}", logData.getClientTransactionID()),
							logData.getResponseData().getBytes());
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(tableName, resultId),
					responseDataAttachment);
		}
	}

}
