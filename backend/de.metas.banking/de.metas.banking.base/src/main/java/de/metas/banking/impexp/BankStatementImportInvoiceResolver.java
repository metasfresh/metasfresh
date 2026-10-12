package de.metas.banking.impexp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.UnpaidInvoiceQuery;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.I_I_BankStatement;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Finds the invoice that an imported bank statement line refers to.
 * <ol>
 * <li>{@code I_BankStatement.C_Invoice_ID}, if set (no fallback to the document number if that invoice does not qualify);</li>
 * <li>else the invoice whose document number is the InvoiceDocumentNo, the Memo or the LineDescription.
 * This mirrors the document-number rule of the CAMT import, without its ESR reference leg.
 * If the candidates refer to more than one invoice, none is referenced.</li>
 * </ol>
 * The invoice has to be unpaid and completed or closed, and of the organization of the import record.
 * If the import record already has a business partner, the invoice has to be of that partner.
 * No amount is matched.
 */
class BankStatementImportInvoiceResolver
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@Value
	static class ReferencedInvoice
	{
		@NonNull InvoiceId invoiceId;
		@NonNull BPartnerId bpartnerId;
		boolean sales;
	}

	Optional<ReferencedInvoice> resolve(@NonNull final I_I_BankStatement importRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(importRecord.getAD_Org_ID());
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(importRecord.getC_Invoice_ID());

		final ImmutableSet<String> documentNos;
		if (invoiceId != null)
		{
			documentNos = null;
		}
		else
		{
			documentNos = Stream.of(importRecord.getInvoiceDocumentNo(), importRecord.getMemo(), importRecord.getLineDescription())
					.map(StringUtils::trimBlankToNull)
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());
			if (documentNos.isEmpty())
			{
				return Optional.empty();
			}
		}

		final ImmutableList<I_C_Invoice> invoices = invoiceDAO.retrieveUnpaid(UnpaidInvoiceQuery.builder()
				.onlyInvoiceId(invoiceId)
				.onlyDocumentNos(documentNos)
				.onlyOrgId(orgId)
				.onlyDocStatuses(ImmutableSet.of(DocStatus.Completed, DocStatus.Closed))
				.queryLimit(QueryLimit.TWO)
				.build());
		if (invoices.size() != 1) // none, or ambiguous
		{
			return Optional.empty();
		}

		final I_C_Invoice invoice = invoices.get(0);
		final BPartnerId invoiceBPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final BPartnerId importedBPartnerId = BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID());
		if (importedBPartnerId != null && !importedBPartnerId.equals(invoiceBPartnerId))
		{
			return Optional.empty();
		}

		return Optional.of(new ReferencedInvoice(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), invoiceBPartnerId, invoice.isSOTrx()));
	}
}
