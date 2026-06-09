/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import de.metas.edi.api.EDIExportStatus;
import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.IExternalSystemInvocationErrorListener;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Error listener for EDI exports via external system.
 * Updates EDI_ExportStatus to Error when external system invocation fails.
 * <p>
 * This listener applies only to EDI error context and queries M_InOut and C_Invoice tables
 * by EDI_AD_PInstance_ID to find affected documents.
 */
@Component
@RequiredArgsConstructor
public class EDIExternalSystemErrorListener implements IExternalSystemInvocationErrorListener
{
	@NonNull private static final Logger logger = LogManager.getLogger(EDIExternalSystemErrorListener.class);
	@NonNull private final EDIInOutDAO ediInoutDAO;
	@NonNull private final EDIInvoiceDAO ediInvoiceDAO;

	@Override
	public boolean applies(@NonNull final ExternalSystemErrorContext errorContext)
	{
		return errorContext.isEDI();
	}

	@Override
	public void onInvocationError(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemErrorContext errorContext,
			@NonNull final String errorMessage)
	{
		//safety check
		if (!applies(errorContext))
		{
			logger.debug("Error context '{}' does not match EDI, skipping", errorContext.getCode());
			return;
		}
		updateEDIStatusInOuts(pInstanceId, errorMessage);
		updateEDIStatusInvoices(pInstanceId, errorMessage);
	}

	private void updateEDIStatusInOuts(@NonNull final PInstanceId pInstanceId, @NonNull final String errorMessage)
	{
		ediInoutDAO.getByEDIPInstanceId(pInstanceId).forEach(inOut -> {
			inOut.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
			inOut.setEDIErrorMsg(errorMessage);
			ediInoutDAO.save(inOut);
		});
	}

	private void updateEDIStatusInvoices(@NonNull final PInstanceId pInstanceId, @NonNull final String errorMessage)
	{
		ediInvoiceDAO.getByEDIPInstanceId(pInstanceId).forEach(invoice -> {
			invoice.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
			invoice.setEDIErrorMsg(errorMessage);
			ediInvoiceDAO.save(invoice);
		});
	}
}
