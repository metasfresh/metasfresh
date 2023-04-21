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

package de.metas.contacts.invoice.interim.service.impl;

import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTerm;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTermId;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTermLine;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTermLineId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermLineDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm_Line;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class InterimInvoiceFlatrateTermLineDAO implements IInterimInvoiceFlatrateTermLineDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	@NonNull
	public InterimInvoiceFlatrateTermLineId createInterimInvoiceLine(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, @NonNull final InOutAndLineId inOutAndLineId)
	{
		return save(InterimInvoiceFlatrateTermLine.builder()
				.interimInvoiceFlatrateTermId(interimInvoiceFlatrateTerm.getId())
				.inOutAndLineId(inOutAndLineId)
				.build());
	}

	@Override
	@Nullable
	public InterimInvoiceFlatrateTermLineId getByInOutLineId(@NonNull final InOutLineId inOutLineId)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_M_InOutLine_ID, inOutLineId)
				.orderByDescending(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID)
				.create()
				.firstId(InterimInvoiceFlatrateTermLineId::ofRepoIdOrNull);
	}

	@Override
	public void setInvoiceLineToLines(@NonNull final InvoiceCandidateId invoiceCandidateId, @NonNull final InterimInvoiceFlatrateTermId id)
	{
		invoiceCandDAO.retrieveIlForIc(invoiceCandidateId)
				.stream()
				.findFirst()
				.ifPresent((invoiceLine) -> getByInterimInvoiceFlatrateTermId(id).forEach(line -> setInvoiceLineToLine(line, invoiceLine)));
	}

	private void setInvoiceLineToLine(final InterimInvoiceFlatrateTermLine line, final I_C_InvoiceLine invoiceLine)
	{
		final InvoiceLineId invoiceLineId = InvoiceLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());

		save(line.toBuilder()
				.invoiceLineId(invoiceLineId)
				.build());
	}

	@Override
	@Nullable
	public InterimInvoiceFlatrateTermLine getById(@NonNull final InterimInvoiceFlatrateTermLineId id)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID, id)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	@Override
	@NonNull
	public Collection<InterimInvoiceFlatrateTermLine> getByInterimInvoiceFlatrateTermId(@NonNull final InterimInvoiceFlatrateTermId id)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm_Line.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, id)
				.create()
				.iterateAndStream()
				.map(this::fromDbObject)
				.collect(Collectors.toList());
	}

	@Override
	@NonNull
	public InterimInvoiceFlatrateTermLineId save(@NonNull final InterimInvoiceFlatrateTermLine interimInvoiceFlatrateTermLine)
	{
		final I_C_InterimInvoice_FlatrateTerm_Line dbObject = toDbObject(interimInvoiceFlatrateTermLine);
		InterfaceWrapperHelper.save(dbObject);
		return InterimInvoiceFlatrateTermLineId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_Line_ID());
	}

	private InterimInvoiceFlatrateTermLine fromDbObject(@NonNull final I_C_InterimInvoice_FlatrateTerm_Line dbObject)
	{
		return InterimInvoiceFlatrateTermLine.builder()
				.id(InterimInvoiceFlatrateTermLineId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_Line_ID()))
				.interimInvoiceFlatrateTermId(InterimInvoiceFlatrateTermId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_ID()))
				.inOutAndLineId(InOutAndLineId.ofRepoId(dbObject.getM_InOut_ID(), dbObject.getM_InOutLine_ID()))
				.invoiceLineId(InvoiceLineId.ofRepoIdOrNull(dbObject.getC_Invoice_ID(), dbObject.getC_InvoiceLine_ID()))
				.build();
	}

	private I_C_InterimInvoice_FlatrateTerm_Line toDbObject(@NonNull final InterimInvoiceFlatrateTermLine object)
	{
		final I_C_InterimInvoice_FlatrateTerm_Line dbObject = InterfaceWrapperHelper.loadOrNew(object.getId(), I_C_InterimInvoice_FlatrateTerm_Line.class);

		dbObject.setC_InterimInvoice_FlatrateTerm_ID(object.getInterimInvoiceFlatrateTermId().getRepoId());

		final InvoiceLineId invoiceLineId = object.getInvoiceLineId();
		if (invoiceLineId != null)
		{
			dbObject.setC_Invoice_ID(InvoiceId.toRepoId(invoiceLineId.getInvoiceId()));
			dbObject.setC_InvoiceLine_ID(InvoiceLineId.toRepoId(invoiceLineId));

		}

		final InOutAndLineId inOutAndLineId = object.getInOutAndLineId();
			dbObject.setM_InOut_ID(InOutId.toRepoId(inOutAndLineId.getInOutId()));
			dbObject.setM_InOutLine_ID(InOutLineId.toRepoId(inOutAndLineId.getInOutLineId()));
		return dbObject;
	}

}
