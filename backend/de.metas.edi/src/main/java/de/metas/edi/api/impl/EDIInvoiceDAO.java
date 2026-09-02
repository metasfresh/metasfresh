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

import de.metas.edi.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EDIInvoiceDAO
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public I_C_Invoice getByIdOutOfTrx(@NonNull final InvoiceId invoiceId)
	{
		return invoiceDAO.getByIdOutOfTrx(invoiceId, I_C_Invoice.class);
	}

	@NonNull
	public List<I_C_Invoice> getByEDIPInstanceId(@NonNull final PInstanceId ediInstanceId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_EDI_AD_PInstance_ID, ediInstanceId)
				.create()
				.listImmutable();
	}

	public void save(@NonNull final I_C_Invoice invoice)
	{
		InterfaceWrapperHelper.save(invoice);
	}

	public void saveOutOfTrx(@NonNull final I_C_Invoice invoice)
	{
		InterfaceWrapperHelper.save(invoice, ITrx.TRXNAME_None);
	}
}
