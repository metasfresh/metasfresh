/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class InvoicePayScheduleRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private InvoicePayScheduleLoaderAndSaver newLoaderAndSaver()
	{
		return InvoicePayScheduleLoaderAndSaver.builder()
				.queryBL(queryBL)
				.trxManager(trxManager)
				.build();
	}

	public void create(@NonNull final InvoicePayScheduleCreateRequest request)
	{
		request.getLines()
				.forEach(line -> createLine(line, request.getInvoiceId()));
	}

	private static void createLine(@NonNull final InvoicePayScheduleCreateRequest.Line request, @NonNull final InvoiceId invoiceId)
	{
		final I_C_InvoicePaySchedule record = newInstance(I_C_InvoicePaySchedule.class);
		record.setC_Invoice_ID(invoiceId.getRepoId());
		record.setC_Order_ID(request.getOrderId().getRepoId());
		record.setC_OrderPaySchedule_ID(request.getOrderPayScheduleId().getRepoId());
		record.setDueAmt(request.getDueAmount().toBigDecimal());
		// record.setC_Currency_ID(request.getDueAmount().getCurrencyId().getRepoId()); TODO
		record.setDueDate(TimeUtil.asTimestamp(request.getDueDate()));
		saveRecord(record);
	}

	@NonNull
	public Optional<InvoicePaySchedule> getByOrderId(@NonNull final InvoiceId invoiceId)
	{
		return newLoaderAndSaver().loadByInvoiceId(invoiceId);
	}

	public void deleteByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		queryBL.createQueryBuilder(I_C_InvoicePaySchedule.class)
				.addEqualsFilter(I_C_InvoicePaySchedule.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.delete();
	}

	public void save(final InvoicePaySchedule invoicePaySchedule) {newLoaderAndSaver().save(invoicePaySchedule);}

	public void updateById(@NonNull final InvoiceId invoiceId, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		newLoaderAndSaver().updateById(invoiceId, updater);
	}
}
