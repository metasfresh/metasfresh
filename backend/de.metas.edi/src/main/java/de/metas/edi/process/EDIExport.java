package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.EDIInvoiceDAO;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

/**
 * EDI-Exports a single EDI document.
 * If there is a validation error, then it updates the record with the error message(s) as well.
 */
public class EDIExport extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final EDIInvoiceDAO ediInvoiceDAO = SpringContextHolder.instance.getBean(EDIInvoiceDAO.class);
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_Invoice invoice = ediInvoiceDAO.getByIdOutOfTrx(InvoiceId.ofRepoId(context.getSingleSelectedRecordId()));
		final EDIExportStatus exportStatus = EDIExportStatus.ofCode(invoice.getEDI_ExportStatus());
		if (!exportStatus.isPendingOrError())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Invoice must be in Pending or Error status (current: " + exportStatus + ")");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_C_Invoice invoice = ediInvoiceDAO.getByIdOutOfTrx(InvoiceId.ofRepoId(getRecord_ID()));
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), EDIWorkpackageProcessor.class);

		queue.newWorkPackage()
				.setPriority(IWorkPackageQueue.PRIORITY_AUTO)
				.addElement(invoice)
				.bindToThreadInheritedTrx()
				.buildAndEnqueue();

		invoice.setEDI_ExportStatus(EDIExportStatus.Enqueued.getCode());
		ediInvoiceDAO.save(invoice);

		return MSG_OK;
	}

}
