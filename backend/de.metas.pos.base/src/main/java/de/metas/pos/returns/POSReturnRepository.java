package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Tables: M_InOut, C_AllocationLine
 * Repository Cluster: POSReturnRepository, POSReturnService
 */
@Repository
public class POSReturnRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Looks up an existing POS-return {@code M_InOut} by its {@code ExternalId} — so a retried request (same
	 * {@link POSReturnRequest#getExternalId()}) does not create a second return document.
	 *
	 * <p>Only an active, non-reversed and non-voided document counts as "existing": a reversed/voided return no
	 * longer represents the credit, so a retry after a reversal must create a fresh one rather than resolve back
	 * to the dead document.
	 */
	@NonNull
	public Optional<InOutId> findReturnIdByExternalId(@NonNull final String externalId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_ExternalId, externalId)
				.addOnlyActiveRecordsFilter()
				.addNotInArrayFilter(I_M_InOut.COLUMNNAME_DocStatus, ImmutableList.of(DocStatus.Voided.getCode(), DocStatus.Reversed.getCode()))
				.create()
				.firstIdOnlyOptional(InOutId::ofRepoIdOrNull);
	}

	/**
	 * Finds the credit memo's already-completed outbound settlement payment(s) via {@code C_AllocationLine} — NOT
	 * {@code C_Payment.C_Invoice_ID} directly, per this workspace's payment-to-invoice-linking rule (de.metas.business
	 * CLAUDE.md: "NEVER use C_Payment.C_Invoice_ID as the canonical link between payments and invoices"). Used only
	 * on a retry, where {@code POSReturnService#ensureSettlement} skips creating a second payment because the
	 * credit memo is already paid.
	 */
	@NonNull
	public ImmutableSet<PaymentId> findSettlementPaymentIds(@NonNull final InvoiceId creditMemoId)
	{
		final ImmutableSet<Integer> paymentIds = queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, creditMemoId)
				.addNotNull(I_C_AllocationLine.COLUMNNAME_C_Payment_ID)
				.create()
				.listDistinctAsImmutableSet(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, Integer.class);

		return paymentIds.stream().map(PaymentId::ofRepoId).collect(ImmutableSet.toImmutableSet());
	}
}
