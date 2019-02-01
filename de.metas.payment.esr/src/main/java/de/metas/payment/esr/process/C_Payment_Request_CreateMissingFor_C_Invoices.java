package de.metas.payment.esr.process;

import java.util.Iterator;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.IQuery;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.archive.async.spi.impl.DocOutboundWorkpackageProcessor;
import de.metas.payment.esr.api.IESRBL;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class C_Payment_Request_CreateMissingFor_C_Invoices extends JavaProcess
{
	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Invoice> invoicesToFix = retrieveInvoicesToProcess();

		while (invoicesToFix.hasNext())
		{
			final I_C_Invoice invoiceToFix = invoicesToFix.next();
			Services
					.get(ITrxManager.class)
					.run(() -> createProcessSingleInvoice(invoiceToFix));
		}

		return MSG_OK;
	}

	private void createProcessSingleInvoice(@NonNull final I_C_Invoice invoiceRecord)
	{
		final IESRBL esrbl = Services.get(IESRBL.class);
		final boolean paymentRequestCreated = esrbl.createESRPaymentRequest(invoiceRecord);
		if (!paymentRequestCreated)
		{
			return; // nothing else to do
		}

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		final I_C_Queue_WorkPackage workPackage = workPackageQueueFactory
				.getQueueForEnqueuing(getCtx(), DocOutboundWorkpackageProcessor.class)
				.newBlock()
				.setAD_PInstance_Creator_ID(getPinstanceId())
				.newWorkpackage()
				.bindToThreadInheritedTrx()
				.addElement(invoiceRecord)
				.setUserInChargeId(Env.getAD_User_ID())
				.build();

		Loggables.get().addLog("Enqueued workpackage for C_Invoice={}; C_Queue_WorkPackage={}", invoiceRecord, workPackage);
	}

	private final Iterator<I_C_Invoice> retrieveInvoicesToProcess()
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocStatus, X_C_Invoice.DOCSTATUS_Completed)
				.filter(getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false)))
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.setOption(IQuery.OPTION_ReturnReadOnlyRecords, true) // we expect the actual invoice record not to be touched!
				.create()
				.iterate(I_C_Invoice.class);
	}

}
