/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.descriptor.decorator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceLineAllocId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OpenInvoiceDecorator implements IDocumentDecorator
{
	private static final AdMessageKey OPEN_INVOICES_FOR_CANDIDATE_MESSAGE_KEY = AdMessageKey.of("OPEN_INVOICES_EXISTS_FOR_CANDIDATE");

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final CCache<InvoiceCandidateId, ImmutableSet<InvoiceId>> invoiceCandidateId2InvoiceIds = CCache.<InvoiceCandidateId, ImmutableSet<InvoiceId>>builder()
			.tableName(I_C_Invoice_Line_Alloc.Table_Name)
			.invalidationKeysMapper(recordRef -> getInvoiceCandidateId(recordRef).map(ImmutableList::of).orElseGet(ImmutableList::of))
			.build();

	private final CCache<InvoiceId, Boolean> invoiceId2OpenStatus = CCache.<InvoiceId, Boolean>builder()
			.tableName(I_C_Invoice.Table_Name)
			.invalidationKeysMapper(recordRef -> ImmutableList.of(recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId)))
			.build();

	@Override
	@NonNull
	public BooleanWithReason isReadOnly(final @NonNull TableRecordReference tableRecordReference)
	{
		if (!tableRecordReference.getTableName().equals(I_C_Invoice_Candidate.Table_Name))
		{
			return BooleanWithReason.FALSE;
		}

		final InvoiceCandidateId invoiceCandidateId = tableRecordReference
				.getIdAssumingTableName(I_C_Invoice_Candidate.Table_Name, InvoiceCandidateId::ofRepoId);

		final Set<InvoiceId> invoiceIds = invoiceCandidateId2InvoiceIds.getOrLoad(invoiceCandidateId, this::getInvoiceIdsForCandidateId);

		if (invoiceIds.isEmpty())
		{
			return BooleanWithReason.FALSE;
		}

		final ImmutableSet<InvoiceId> openInvoiceIds = invoiceIds.stream()
				.filter(invoiceId -> invoiceId2OpenStatus.getOrLoad(invoiceId, this::isOpen))
				.collect(ImmutableSet.toImmutableSet());

		if (!openInvoiceIds.isEmpty())
		{
			final String openInvoiceIdsMessage = openInvoiceIds.stream()
					.map(InvoiceId::getRepoId)
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			final ITranslatableString readOnlyReason = msgBL.getTranslatableMsgText(OPEN_INVOICES_FOR_CANDIDATE_MESSAGE_KEY, openInvoiceIdsMessage);

			return BooleanWithReason.trueBecause(readOnlyReason);
		}

		return BooleanWithReason.FALSE;
	}

	private boolean isOpen(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		return invoice != null && !invoice.isProcessed();
	}

	@NonNull
	private ImmutableSet<InvoiceId> getInvoiceIdsForCandidateId(@NonNull final InvoiceCandidateId candidateId)
	{
		return invoiceCandDAO.getInvoicesForCandidateId(candidateId)
				.stream()
				.map(I_C_Invoice::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Optional<InvoiceCandidateId> getInvoiceCandidateId(@NonNull final TableRecordReference invoiceLineAllocReference)
	{
		final InvoiceLineAllocId invoiceLineAllocId = invoiceLineAllocReference
				.getIdAssumingTableName(I_C_Invoice_Line_Alloc.Table_Name, InvoiceLineAllocId::ofRepoId);

		return Optional.ofNullable(invoiceCandDAO.getInvoiceLineAlloc(invoiceLineAllocId))
				.map(I_C_Invoice_Line_Alloc::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId);
	}
}
