package de.metas.frontend_testing.masterdata.invoice;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.DB;
import de.metas.interfaces.I_C_OrderLine;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class InvoiceCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(InvoiceCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final Duration INVOICE_CANDIDATE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration INVOICE_CANDIDATE_POLL_SLEEP = Duration.ofMillis(1000);

	private static final Duration INVOICE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration INVOICE_POLL_SLEEP = Duration.ofMillis(1000);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonInvoiceCreateRequest request;
	@NonNull private final Identifier identifier;

	/**
	 * Enqueueing (step 2) MUST commit before we poll for the resulting invoice (step 3): the actual invoice
	 * generation runs in a background workpackage processor, on its OWN connection, which can only see
	 * ENQUEUE's writes (the {@code C_Queue_WorkPackage} row) once they're committed. Wrapping steps 1-3 in one
	 * ambient transaction (as this used to) holds that row uncommitted for as long as step 3 polls — the
	 * background processor can never see it, so the poll always times out, deterministically, every time.
	 * {@link #enqueueForInvoicing} therefore runs in its own fresh, immediately-committing transaction, and
	 * {@link #pollForInvoice} runs outside any wrapper, same as {@link #pollForInvoiceCandidates}'s callers
	 * elsewhere in this module poll outside a write transaction.
	 */
	public JsonInvoiceCreateResponse execute()
	{
		final OrderId orderId = resolveOrderId();

		trxManager.runInNewTrx(() -> enqueueForInvoicing(orderId));

		// 3. Poll for the generated invoice
		final I_C_Invoice invoice = pollForInvoice(orderId);

		// Register under this masterdata section's own key (e.g. "INV1") so a LATER section can reference this
		// invoice by identifier - e.g. Backend.expect({ pos: { invoices: [{ invoice: 'INV1', ... }] } }). Without
		// this, AssertPOSExpectationsCommand#assertInvoice's context lookup misses and falls back to parsing the
		// identifier string itself as a raw InvoiceId, which throws.
		context.putIdentifier(identifier, InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		return JsonInvoiceCreateResponse.builder()
				.id(String.valueOf(invoice.getC_Invoice_ID()))
				.documentNo(invoice.getDocumentNo())
				.build();
	}

	private void enqueueForInvoicing(final OrderId orderId)
	{
		// 1. Poll for invoice candidates
		final List<I_C_Invoice_Candidate> invoiceCandidates = pollForInvoiceCandidates(orderId);
		if (invoiceCandidates.isEmpty())
		{
			throw new AdempiereException("No invoice candidates found for order " + orderId);
		}

		// 2. Enqueue candidates for invoicing (with default invoicing params)
		final ImmutableSet<InvoiceCandidateId> candidateIds = invoiceCandidates.stream()
				.map(ic -> InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final PInstanceId selectionId = DB.createT_Selection(candidateIds, ITrx.TRXNAME_None);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(true);

		invoiceCandBL.enqueueForInvoicing()
				.setInvoicingParams(invoicingParams)
				.setFailIfNothingEnqueued(true)
				.prepareAndEnqueueSelection(selectionId);
	}

	private OrderId resolveOrderId()
	{
		if (request.getSalesOrder() != null)
		{
			return context.getId(request.getSalesOrder(), OrderId.class);
		}
		else if (request.getPurchaseOrder() != null)
		{
			return context.getId(request.getPurchaseOrder(), OrderId.class);
		}
		else
		{
			throw new AdempiereException("Either salesOrder or purchaseOrder must be set in the invoice request");
		}
	}

	private List<I_C_Invoice_Candidate> pollForInvoiceCandidates(final OrderId orderId)
	{
		// Get order line IDs
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);
		if (orderLines.isEmpty())
		{
			throw new AdempiereException("No order lines found for order " + orderId);
		}

		final Set<Integer> orderLineRepoIds = orderLines.stream()
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.collect(Collectors.toSet());

		final Stopwatch stopwatch = Stopwatch.createStarted();

		while (true)
		{
			final List<I_C_Invoice_Candidate> candidates = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
					.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLineRepoIds)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
					.create()
					.list();

			if (!candidates.isEmpty())
			{
				logger.info("Found {} invoice candidates for order {} after {}", candidates.size(), orderId, stopwatch);
				return candidates;
			}

			if (stopwatch.elapsed().compareTo(INVOICE_CANDIDATE_POLL_TIMEOUT) > 0)
			{
				throw new AdempiereException("Timeout waiting for invoice candidates for order " + orderId + ". Waited " + stopwatch.elapsed());
			}

			logger.info("No invoice candidates yet for order {}. Sleeping... (elapsed: {})", orderId, stopwatch);
			sleep(INVOICE_CANDIDATE_POLL_SLEEP);
		}
	}

	private I_C_Invoice pollForInvoice(final OrderId orderId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		while (true)
		{
			// Look for invoices referencing this order
			final List<I_C_Invoice> invoices = queryBL.createQueryBuilder(I_C_Invoice.class)
					.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_Order_ID, orderId.getRepoId())
					.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocStatus, "CO") // completed
					.orderBy(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
					.create()
					.list();

			if (!invoices.isEmpty())
			{
				logger.info("Found {} invoices for order {} after {}", invoices.size(), orderId, stopwatch);
				return invoices.get(invoices.size() - 1); // return the latest one
			}

			if (stopwatch.elapsed().compareTo(INVOICE_POLL_TIMEOUT) > 0)
			{
				throw new AdempiereException("Timeout waiting for invoice generation for order " + orderId + ". Waited " + stopwatch.elapsed());
			}

			logger.info("No completed invoice yet for order {}. Sleeping... (elapsed: {})", orderId, stopwatch);
			sleep(INVOICE_POLL_SLEEP);
		}
	}

	private static void sleep(@NonNull final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e);
		}
	}
}
