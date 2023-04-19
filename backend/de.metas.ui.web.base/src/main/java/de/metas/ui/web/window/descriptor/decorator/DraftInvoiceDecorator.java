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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceLineAllocId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DraftInvoiceDecorator implements IDocumentDecorator
{
	private static final AdMessageKey DRAFT_INVOICES_FOR_CANDIDATE_MESSAGE_KEY = AdMessageKey.of("DRAFT_INVOICES_EXISTS_FOR_CANDIDATE");

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final CCache<InvoiceCandidateId, ImmutableSet<InvoiceId>> invoiceCandidateId2InvoiceIds = CCache.<InvoiceCandidateId, ImmutableSet<InvoiceId>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)// max size
			.tableName(I_C_Invoice_Line_Alloc.Table_Name)
			.invalidationKeysMapper(this::resolveAsInvoiceCandidateIds)
			.build();

	private final CCache<InvoiceId, InvoiceInfo> invoiceId2info = CCache.<InvoiceId, InvoiceInfo>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)// max size
			.tableName(I_C_Invoice.Table_Name)
			.invalidationKeysMapper(this::resolveAsInvoiceIds)
			.build();

	@Override
	@NonNull
	public ReadOnlyInfo isReadOnly(final @NonNull TableRecordReference recordRef)
	{
		final BooleanWithReason isReadOnly = isInvoiceCandidateReadOnly(recordRef);

		return ReadOnlyInfo.builder()
				.isReadOnly(isReadOnly)
				.forceReadOnlySubDocuments(isReadOnly.isTrue())
				.build();
	}

	@NonNull
	private BooleanWithReason isInvoiceCandidateReadOnly(final @NonNull TableRecordReference recordRef)
	{
		if (!recordRef.getTableName().equals(I_C_Invoice_Candidate.Table_Name))
		{
			return BooleanWithReason.FALSE;
		}

		final InvoiceCandidateId invoiceCandidateId = recordRef.getIdAssumingTableName(I_C_Invoice_Candidate.Table_Name, InvoiceCandidateId::ofRepoId);

		final ImmutableSet<InvoiceInfo> draftInvoiceInfoSet = getDraftInvoices(invoiceCandidateId);

		if (draftInvoiceInfoSet.isEmpty())
		{
			return BooleanWithReason.FALSE;
		}

		final String draftedInvoiceSummaryList = draftInvoiceInfoSet.stream()
				.map(InvoiceInfo::getSummary)
				.collect(Collectors.joining(","));

		final ITranslatableString readOnlyReason = TranslatableStrings.adMessage(DRAFT_INVOICES_FOR_CANDIDATE_MESSAGE_KEY, draftedInvoiceSummaryList);
		return BooleanWithReason.trueBecause(readOnlyReason);
	}

	@NonNull
	private ImmutableSet<InvoiceInfo> getDraftInvoices(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final Set<InvoiceId> invoiceIds = invoiceCandidateId2InvoiceIds.getOrLoad(invoiceCandidateId, this::retrieveInvoiceIdsForCandidateId);
		if (invoiceIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Collection<InvoiceInfo> invoiceInfos = invoiceId2info.getAllOrLoad(invoiceIds, this::retrieveInvoiceInfos);

		return invoiceInfos.stream()
				.filter(InvoiceInfo::isDraft)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private ImmutableMap<InvoiceId, InvoiceInfo> retrieveInvoiceInfos(final Set<InvoiceId> invoiceIds)
	{
		return invoiceDAO.getByIdsInTrx(invoiceIds)
				.stream()
				.map(InvoiceInfo::of)
				.collect(ImmutableMap.toImmutableMap(InvoiceInfo::getInvoiceId, Function.identity()));
	}

	@NonNull
	private ImmutableSet<InvoiceId> retrieveInvoiceIdsForCandidateId(@NonNull final InvoiceCandidateId candidateId)
	{
		return invoiceCandDAO.getInvoicesForCandidateId(candidateId)
				.stream()
				.map(I_C_Invoice::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private ImmutableSet<InvoiceCandidateId> resolveAsInvoiceCandidateIds(@NonNull final TableRecordReference recordRef)
	{
		if (!I_C_Invoice_Line_Alloc.Table_Name.equals(recordRef.getTableName()))
		{
			return ImmutableSet.of();
		}

		final InvoiceLineAllocId invoiceLineAllocId = recordRef.getIdAssumingTableName(I_C_Invoice_Line_Alloc.Table_Name, InvoiceLineAllocId::ofRepoId);

		return invoiceCandDAO.getInvoiceCandidateIdByInvoiceLineAllocId(invoiceLineAllocId)
				.map(ImmutableSet::of)
				.orElseGet(ImmutableSet::of);
	}

	@NonNull
	private ImmutableSet<InvoiceId> resolveAsInvoiceIds(@NonNull final TableRecordReference recordRef)
	{
		if (!I_C_Invoice.Table_Name.equals(recordRef.getTableName()))
		{
			return ImmutableSet.of();
		}

		final InvoiceId invoiceId = recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		return ImmutableSet.of(invoiceId);
	}

	@Value
	@Builder
	private static class InvoiceInfo
	{
		@NonNull InvoiceId invoiceId;
		@NonNull String documentNo;
		boolean isDraft;

		@NonNull
		public static InvoiceInfo of(@NonNull final I_C_Invoice invoice)
		{
			return InvoiceInfo.builder()
					.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
					.documentNo(invoice.getDocumentNo())
					.isDraft(DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus()).isDraftedOrInProgress())
					.build();
		}

		public String getSummary()
		{
			return documentNo;
		}
	}
}
