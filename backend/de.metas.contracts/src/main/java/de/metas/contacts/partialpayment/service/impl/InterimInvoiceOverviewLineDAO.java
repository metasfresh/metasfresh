/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.partialpayment.service.impl;

import de.metas.contacts.partialpayment.InterimInvoiceOverviewId;
import de.metas.contacts.partialpayment.InterimInvoiceOverviewLine;
import de.metas.contacts.partialpayment.InterimInvoiceOverviewLineId;
import de.metas.contacts.partialpayment.service.IInterimInvoiceOverviewLineDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm_Line;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class InterimInvoiceOverviewLineDAO implements IInterimInvoiceOverviewLineDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public InterimInvoiceOverviewLine getById(@NonNull final InterimInvoiceOverviewLineId id)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID, id)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	public void save(@NonNull final InterimInvoiceOverviewLine interimInvoiceOverviewLine)
	{
		InterfaceWrapperHelper.save(toDbObject(interimInvoiceOverviewLine));
	}

	private InterimInvoiceOverviewLine fromDbObject(@NonNull final I_C_InterimInvoice_FlatrateTerm_Line dbObject)
	{
		return InterimInvoiceOverviewLine.builder()
				.id(InterimInvoiceOverviewLineId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_Line_ID()))
				.interimInvoiceOverviewId(InterimInvoiceOverviewId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_ID()))
				.inOutAndLineId(InOutAndLineId.ofRepoId(dbObject.getM_InOut_ID(), dbObject.getM_InOutLine_ID()))
				.invoiceLineId(InvoiceLineId.ofRepoId(dbObject.getC_Invoice_ID(), dbObject.getC_InvoiceLine_ID()))
				.build();
	}

	private I_C_InterimInvoice_FlatrateTerm_Line toDbObject(@NonNull final InterimInvoiceOverviewLine object)
	{
		final I_C_InterimInvoice_FlatrateTerm_Line dbObject = InterfaceWrapperHelper.loadOrNew(object.getId(), I_C_InterimInvoice_FlatrateTerm_Line.class);

		dbObject.setC_InterimInvoice_FlatrateTerm_ID(object.getInterimInvoiceOverviewId().getRepoId());

		final InvoiceLineId invoiceLineId = object.getInvoiceLineId();
		if (invoiceLineId != null)
		{
			dbObject.setC_Invoice_ID(InvoiceId.toRepoId(invoiceLineId.getInvoiceId()));
			dbObject.setC_InvoiceLine_ID(InvoiceLineId.toRepoId(invoiceLineId));

		}

		final InOutAndLineId inOutAndLineId = object.getInOutAndLineId();
		if (inOutAndLineId != null)
		{
			dbObject.setM_InOut_ID(InOutId.toRepoId(inOutAndLineId.getInOutId()));
			dbObject.setC_InvoiceLine_ID(InOutLineId.toRepoId(inOutAndLineId.getInOutLineId()));
		}

		return dbObject;
	}

}
