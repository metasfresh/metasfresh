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

package de.metas.invoice.paymentschedule.repository;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePaySchedule;
import de.metas.invoice.paymentschedule.service.InvoicePayScheduleCreateRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
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
		request.getLines().forEach(line -> createLine(line, request.getInvoiceId()));
	}

	private void createLine(@NonNull final InvoicePayScheduleCreateRequest.Line request, @NonNull final InvoiceId invoiceId)
	{
		final I_C_InvoicePaySchedule record = newInstance(I_C_InvoicePaySchedule.class);
		record.setC_Invoice_ID(invoiceId.getRepoId());
		InvoicePayScheduleConverter.updateRecord(record, request);
		saveRecord(record);
	}

	public void deleteByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		queryBL.createQueryBuilder(I_C_InvoicePaySchedule.class)
				.addEqualsFilter(I_C_InvoicePaySchedule.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.delete();
	}

	public void updateById(@NonNull final InvoiceId invoiceId, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		newLoaderAndSaver().updateById(invoiceId, updater);
	}

	public void updateByIds(@NonNull final Set<InvoiceId> invoiceIds, @NonNull final Consumer<InvoicePaySchedule> updater)
	{
		newLoaderAndSaver().updateByIds(invoiceIds, updater);
	}

	public Optional<InvoicePaySchedule> getByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return newLoaderAndSaver().loadByInvoiceId(invoiceId);
	}
}
