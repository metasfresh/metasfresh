/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.pos.POSProduct;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pos.returns.POSReturnRequestedLine;
import de.metas.pos.returns.POSReturnResult;
import de.metas.pos.returns.POSReturnService;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for a POS product return: the cashier takes goods back at the till, via
 * {@link POSReturnService#createReturnFromTillPrices} (the production REST entry), and its credit invoice
 * candidate is priced at the till's own price for the product (not the walk-in customer's own sales pricing
 * system).
 */
@RequiredArgsConstructor
public class POS_Return_StepDef
{
	private static final Logger logger = LogManager.getLogger(POS_Return_StepDef.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);
	@NonNull private final POSReturnService posReturnService = SpringContextHolder.instance.getBean(POSReturnService.class);
	@NonNull private final POSTerminalService posTerminalService = SpringContextHolder.instance.getBean(POSTerminalService.class);

	private final C_POS_StepDefData posTable;
	private final M_Product_StepDefData productTable;
	private final M_InOut_StepDefData inoutTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_Payment_StepDefData paymentTable;

	/** Per-scenario token → externalId map, so a retry token resolves to a fresh random UUID the first time it is
	 * seen in this scenario, and to that SAME UUID on every later call — never to a UUID any other scenario/run
	 * could also produce. */
	private final Map<String, UUID> retryTokenToExternalId = new HashMap<>();

	/**
	 * Drives a POS product return end to end through the production entry
	 * {@link POSReturnService#createReturnFromTillPrices}: the step sends only product+qty, and the till's own
	 * current price (and its price UOM) is resolved server-side — never a client-supplied price.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) returned product<br>
	 *   <b>Qty</b> — (required) returned quantity, interpreted server-side in the till's price UOM for the product<br>
	 *   <b>UOM</b> — (required) {@code X12DE355} code (e.g. {@code KGM}) asserted to be the till's price UOM for the product<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, first row only, identifier) alias for the resulting customer-return
	 *   {@code M_InOut}<br>
	 *   <b>OPT.C_Invoice_ID</b> — (optional, first row only, identifier) alias for the resulting credit memo<br>
	 *   <b>OPT.C_Payment_ID</b> — (optional, first row only, identifier) alias for the resulting settlement payment<br>
	 *   <b>OPT.ExternalId</b> — (optional, first row only) an arbitrary token, mapped to a random UUID the first
	 *   time this scenario sees it and to that SAME UUID on every later call; pass the SAME token on a later call
	 *   to simulate a retried request (idempotency: resolves to the SAME return document instead of creating a
	 *   second one). Defaults to a fresh random UUID per call.<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData,
	 * C_Invoice_StepDefData, C_Payment_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return is made at POS terminal till by metasfresh:
	 *   | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
	 *   | product      | 0.3 | KGM | return_1       |
	 * </pre>
	 */
	@And("^a product return is made at POS terminal (\\S+) by (\\S+):$")
	public void posProductReturn(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable)
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final ReturnRequestParams params = buildRequestParams(terminalIdentifier, userLogin, rows);

		final POSReturnResult result = createReturnFromTillPrices(params);

		registerReturnResult(rows, result);
	}

	/**
	 * Same request-building as {@link #posProductReturn}, but asserts the call is REJECTED with the given
	 * AD_Message key — proving the whole return (goods receipt included) rolls back rather than leaving a
	 * partially-created document behind.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as {@link #posProductReturn}, plus:
	 *   <b>OPT.ExternalId</b> — see {@link #posProductReturn}; used by {@code there is no POS return for retry token}
	 *   to look the (non-existent) document up afterwards<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return at POS terminal till by metasfresh fails with AD_Message 'de.metas.pos.Return.NoTaxFound':
	 *   | M_Product_ID | Qty | UOM |
	 *   | product      | 0.3 | KGM |
	 * </pre>
	 */
	@And("^a product return at POS terminal (\\S+) by (\\S+) fails with AD_Message '(.*)':$")
	public void posProductReturnFails(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final String expectedAdMessage,
			@NonNull final DataTable dataTable)
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final ReturnRequestParams params = buildRequestParams(terminalIdentifier, userLogin, rows);

		// AdempiereException#getErrorCode() resolves to AD_Message.ErrorCode when the message has one, falling
		// back to the AdMessageKey itself otherwise (the exact resolution AdempiereException's own constructor
		// does) — resolve the expectation the same way rather than assuming it is always the bare key
		final AdMessageKey expectedKey = AdMessageKey.of(expectedAdMessage);
		final String expectedErrorCode = Optional.ofNullable(msgBL.getErrorCode(expectedKey))
				.orElseGet(expectedKey::toAD_Message);

		assertThatThrownBy(() -> createReturnFromTillPrices(params))
				.as("POS return must be rejected")
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode));
	}

	/**
	 * Proves {@link POSTerminalService#runWithCrossTransactionLock} genuinely serializes two concurrent
	 * {@code createReturn} calls against the SAME terminal for the call's ENTIRE duration. Without this lock, a
	 * second caller could reach phase 3 (cash settlement) while a first caller's own phase 3 is still in flight,
	 * double-refunding the same credit memo; this step proves a real {@code createReturn} call cannot even START
	 * while the lock is held by a concurrent holder, and — once released well within
	 * {@code de.metas.pos.Return.LockTimeoutMillis} — completes with exactly one credit memo, one settlement
	 * payment and one journal line, never two.
	 *
	 * <p>Holds {@link POSTerminalService#runWithCrossTransactionLock} itself (the same lock
	 * {@code POSReturnService#createReturn} takes for its whole body) on a separate thread, instead of racing two
	 * real {@code createReturn} calls and hoping they overlap, so the blocking window it proves — covering all
	 * three phases — is deterministic (a bare race is flaky: the two calls might never actually overlap inside the
	 * critical section).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as {@link #posProductReturn} (incl. {@code OPT.M_InOut_ID}/{@code OPT.C_Invoice_ID}/
	 * {@code OPT.C_Payment_ID}, registered once the call has completed)
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData,
	 * C_Invoice_StepDefData, C_Payment_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return at POS terminal till by metasfresh blocks while the terminal is locked by a concurrent cross-transaction lock:
	 *   | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
	 *   | product      | 0.3 | KGM | return_1       |
	 * </pre>
	 */
	@And("^a product return at POS terminal (\\S+) by (\\S+) blocks while the terminal is locked by a concurrent cross-transaction lock:$")
	public void posProductReturnBlocksOnConcurrentCrossTransactionLock(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable) throws Exception
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final ReturnRequestParams params = buildRequestParams(terminalIdentifier, userLogin, rows);
		final POSTerminalId posTerminalId = params.getPosTerminalId();
		final String returnExternalId = "POSReturn-" + params.getExternalId();

		final ExecutorService executor = Executors.newFixedThreadPool(2);
		final CountDownLatch lockAcquired = new CountDownLatch(1);
		final CountDownLatch releaseSignal = new CountDownLatch(1);
		final AtomicReference<Throwable> lockHolderFailure = new AtomicReference<>();

		try
		{
			// 1) own thread: acquire and HOLD the cross-transaction lock until told to release — the exact same
			// production entry point POSReturnService#createReturn itself calls to wrap its whole body
			final Future<?> lockHolderFuture = executor.submit(() -> holdCrossTransactionLockUntilReleased(posTerminalId, lockAcquired, releaseSignal, lockHolderFailure));

			assertThat(lockAcquired.await(10, TimeUnit.SECONDS)).as("lock holder acquired the cross-transaction lock").isTrue();
			if (lockHolderFailure.get() != null)
			{
				throw AdempiereException.wrapIfNeeded(lockHolderFailure.get());
			}

			// 2) own thread: the real call under test — must not be able to make ANY progress while the lock is held
			final Future<POSReturnResult> createReturnFuture = executor.submit(() -> createReturnFromTillPrices(params));

			// 3) must NOT complete while the lock is held, and must not have created a document yet (so certainly
			// no credit memo or settlement payment either — those can only exist once the M_InOut does)
			assertThatThrownBy(() -> createReturnFuture.get(3, TimeUnit.SECONDS))
					.as("createReturn must block for its ENTIRE duration (all three phases) while the cross-transaction lock is held")
					.isInstanceOf(TimeoutException.class);
			assertThat(countReturnDocumentsByExternalId(returnExternalId))
					.as("no POS return document while the cross-transaction lock is held")
					.isZero();

			// 4) release the lock
			releaseSignal.countDown();
			lockHolderFuture.get(10, TimeUnit.SECONDS);
			if (lockHolderFailure.get() != null)
			{
				throw AdempiereException.wrapIfNeeded(lockHolderFailure.get());
			}

			// 5) must now complete, with exactly one credit memo, settlement payment and journal line
			final POSReturnResult result = createReturnFuture.get(90, TimeUnit.SECONDS);
			assertThat(result).as("createReturn must complete once the cross-transaction lock is released").isNotNull();
			assertThat(countReturnDocumentsByExternalId(returnExternalId)).as("exactly one POS return document once the lock is released").isEqualTo(1);
			assertExactlyOneCreditMemoAndSettlementPaymentByReturnExternalId(returnExternalId, "concurrent cross-transaction-lock scenario");

			registerReturnResult(rows, result);
		}
		finally
		{
			// unconditional cleanup so a failed assertion above can never wedge the stack
			releaseSignal.countDown();
			executor.shutdown();
			if (!executor.awaitTermination(90, TimeUnit.SECONDS))
			{
				executor.shutdownNow();
			}
		}
	}

	/**
	 * Proves {@code createReturn}'s lock-acquire wait is BOUNDED, not indefinite: while a concurrent holder keeps
	 * the cross-transaction lock for longer than the currently-configured {@code de.metas.pos.Return.LockTimeoutMillis},
	 * a real {@code createReturn} call for the SAME terminal gives up once that timeout elapses and rejects with
	 * the given AD_Message ({@code de.metas.pos.Return.TillBusy}) — never creating a return document. Uses the
	 * SAME holder technique as {@link #posProductReturnBlocksOnConcurrentCrossTransactionLock}, but the holder is
	 * never deliberately released within the assertion — it only ever unwinds via its own internal 30s bound
	 * (in {@code finally}, as an unconditional safety net), long after the timeout under test has already fired.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as {@link #posProductReturnFails}
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return at POS terminal till by metasfresh fails with AD_Message 'de.metas.pos.Return.TillBusy' while the terminal is locked by a concurrent cross-transaction lock:
	 *   | M_Product_ID | Qty | UOM | OPT.ExternalId |
	 *   | product      | 0.3 | KGM | tillBusyToken  |
	 * </pre>
	 */
	@And("^a product return at POS terminal (\\S+) by (\\S+) fails with AD_Message '(.*)' while the terminal is locked by a concurrent cross-transaction lock:$")
	public void posProductReturnFailsOnConcurrentCrossTransactionLockTimeout(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final String expectedAdMessage,
			@NonNull final DataTable dataTable) throws Exception
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final ReturnRequestParams params = buildRequestParams(terminalIdentifier, userLogin, rows);
		final POSTerminalId posTerminalId = params.getPosTerminalId();
		final String returnExternalId = "POSReturn-" + params.getExternalId();

		final AdMessageKey expectedKey = AdMessageKey.of(expectedAdMessage);
		final String expectedErrorCode = Optional.ofNullable(msgBL.getErrorCode(expectedKey)).orElseGet(expectedKey::toAD_Message);

		final ExecutorService executor = Executors.newFixedThreadPool(1);
		final CountDownLatch lockAcquired = new CountDownLatch(1);
		final CountDownLatch releaseSignal = new CountDownLatch(1);
		final AtomicReference<Throwable> lockHolderFailure = new AtomicReference<>();

		try
		{
			final Future<?> lockHolderFuture = executor.submit(() -> holdCrossTransactionLockUntilReleased(posTerminalId, lockAcquired, releaseSignal, lockHolderFailure));

			assertThat(lockAcquired.await(10, TimeUnit.SECONDS)).as("lock holder acquired the cross-transaction lock").isTrue();
			if (lockHolderFailure.get() != null)
			{
				throw AdempiereException.wrapIfNeeded(lockHolderFailure.get());
			}

			// the holder is NOT released here — createReturn must give up on its own once the configured
			// (scenario-shortened) LockTimeoutMillis elapses, well before the holder's own 30s safety bound
			assertThatThrownBy(() -> createReturnFromTillPrices(params))
					.as("createReturn must reject once its bounded lock-acquire wait is exhausted")
					.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode));
			assertThat(countReturnDocumentsByExternalId(returnExternalId))
					.as("no POS return document is created when the till is busy")
					.isZero();
		}
		finally
		{
			// unconditional cleanup: release the holder (no-op if it already unwound on its own 30s bound) and
			// wait for the worker thread to actually finish
			releaseSignal.countDown();
			executor.shutdown();
			if (!executor.awaitTermination(30, TimeUnit.SECONDS))
			{
				executor.shutdownNow();
			}
		}
	}

	/**
	 * Runs on the lock-holder worker thread: acquires {@link POSTerminalService#runWithCrossTransactionLock} and
	 * HOLDS it (by never returning from the action) until {@code releaseSignal} fires (or 30s pass). Passes a
	 * generous acquire timeout (60s) since this holder is always the FIRST to contend for the lock, so its
	 * {@code onTimeout} supplier is never expected to run. Needs no {@code callInThreadInheritedTrx} wrapper — the
	 * cross-transaction lock runs on its own dedicated JDBC connection, independent of any thread-inherited
	 * transaction.
	 */
	private void holdCrossTransactionLockUntilReleased(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final CountDownLatch lockAcquired,
			@NonNull final CountDownLatch releaseSignal,
			@NonNull final AtomicReference<Throwable> failure)
	{
		try
		{
			posTerminalService.runWithCrossTransactionLock(
					posTerminalId,
					TimeUnit.SECONDS.toMillis(60),
					() -> {
						lockAcquired.countDown();
						try
						{
							releaseSignal.await(30, TimeUnit.SECONDS);
						}
						catch (final InterruptedException ex)
						{
							Thread.currentThread().interrupt();
							throw AdempiereException.wrapIfNeeded(ex);
						}
						return null;
					},
					() -> new AdempiereException("Lock holder itself failed to acquire the cross-transaction lock — should never happen, it is always first")
							.setParameter("C_POS_ID", posTerminalId));
		}
		catch (final Throwable t)
		{
			logger.error("Cross-transaction lock holder failed for posTerminalId={}", posTerminalId, t);
			failure.set(t);
			lockAcquired.countDown();
		}
	}

	/**
	 * Registers the return's {@code M_InOut}, credit memo and settlement payment under the first row's
	 * {@code OPT.M_InOut_ID}/{@code OPT.C_Invoice_ID}/{@code OPT.C_Payment_ID} identifiers, if given.
	 */
	private void registerReturnResult(@NonNull final List<DataTableRow> rows, @NonNull final POSReturnResult result)
	{
		final I_M_InOut returnRecord = InterfaceWrapperHelper.load(result.getReturnInOutId(), I_M_InOut.class);
		rows.get(0).getAsOptionalIdentifier("M_InOut_ID")
				.ifPresent(returnIdentifier -> inoutTable.putOrReplace(returnIdentifier, returnRecord));

		final I_C_Invoice creditMemoRecord = InterfaceWrapperHelper.load(result.getCreditMemoId(), I_C_Invoice.class);
		rows.get(0).getAsOptionalIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
				.ifPresent(invoiceIdentifier -> invoiceTable.putOrReplace(invoiceIdentifier, creditMemoRecord));

		final I_C_Payment paymentRecord = InterfaceWrapperHelper.load(result.getPaymentId(), I_C_Payment.class);
		rows.get(0).getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.ifPresent(paymentIdentifier -> paymentTable.putOrReplace(paymentIdentifier, paymentRecord));
	}

	/**
	 * Asserts exactly one POS-return {@code M_InOut} exists for the given {@code OPT.ExternalId} retry token —
	 * used after two {@link #posProductReturn} calls with the SAME token to prove the retry resolved back to the
	 * one document instead of creating a second one.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then there is exactly one POS return for retry token retryToken
	 * </pre>
	 */
	@And("^there is exactly one POS return for retry token (\\S+)$")
	public void assertExactlyOnePOSReturnDocument(@NonNull final String retryToken)
	{
		final int count = countPOSReturnDocuments(retryToken);
		assertThat(count).as("exactly one M_InOut must exist for the POS return retried with the same idempotency key").isEqualTo(1);
	}

	/**
	 * Asserts no POS-return {@code M_InOut} exists for the given {@code OPT.ExternalId} retry token — used after
	 * {@link #posProductReturnFails} to prove the failed attempt left no partially-created document behind.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then there is no POS return for retry token noTaxToken
	 * </pre>
	 */
	@And("^there is no POS return for retry token (\\S+)$")
	public void assertNoPOSReturnDocument(@NonNull final String retryToken)
	{
		final int count = countPOSReturnDocuments(retryToken);
		assertThat(count).as("no M_InOut must exist for the rolled-back POS return (retry token=%s)", retryToken).isZero();
	}

	/**
	 * Asserts exactly one credit memo and one settlement payment exist for the POS return retried with the given
	 * retry token — the phase-2/3 (invoicing + cash refund) counterpart to {@link #assertExactlyOnePOSReturnDocument}
	 * (which covers only the phase-1 {@code M_InOut}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then there is exactly one credit memo and settlement payment for retry token retryToken
	 * </pre>
	 */
	@And("^there is exactly one credit memo and settlement payment for retry token (\\S+)$")
	public void assertExactlyOneCreditMemoAndSettlementPayment(@NonNull final String retryToken)
	{
		assertExactlyOneCreditMemoAndSettlementPaymentByReturnExternalId(
				"POSReturn-" + externalIdForRetryToken(retryToken),
				"retry token=" + retryToken);
	}

	/**
	 * Finds the credit memo via the {@code C_InvoiceLine.M_InOutLine_ID} back-reference the invoicing pipeline
	 * itself sets (not a stored identifier on the return), and the settlement payment via {@code C_AllocationLine}
	 * — NOT {@code C_Payment.C_Invoice_ID} directly, per de.metas.business's CLAUDE.md ("NEVER use
	 * C_Payment.C_Invoice_ID as the canonical link between payments and invoices"), mirroring exactly how
	 * production ({@code POSReturnRepository#findSettlementPaymentIds}) looks it up.
	 */
	private void assertExactlyOneCreditMemoAndSettlementPaymentByReturnExternalId(
			@NonNull final String returnExternalId,
			@NonNull final String descriptionSuffix)
	{
		final I_M_InOut returnRecord = queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_ExternalId, returnExternalId)
				.create()
				.firstOnlyNotNull(I_M_InOut.class);

		final List<Integer> returnLineIds = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, returnRecord.getM_InOut_ID())
				.create()
				.listIds();

		final ImmutableSet<Integer> creditMemoIds = queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addInArrayFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, returnLineIds)
				.create()
				.listDistinctAsImmutableSet(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, Integer.class);
		assertThat(creditMemoIds).as("credit memos for the POS return (%s)", descriptionSuffix).hasSize(1);

		final int creditMemoId = creditMemoIds.iterator().next();
		final ImmutableSet<Integer> paymentIds = queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, creditMemoId)
				.addNotNull(I_C_AllocationLine.COLUMNNAME_C_Payment_ID)
				.create()
				.listDistinctAsImmutableSet(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, Integer.class);
		assertThat(paymentIds).as("settlement payments for the POS return (%s)", descriptionSuffix).hasSize(1);

		final I_C_Payment payment = queryBL.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_Payment_ID, paymentIds.iterator().next())
				.create()
				.firstOnlyNotNull(I_C_Payment.class);
		assertThat(payment.isReceipt()).as("settlement payment is an outbound payment (%s)", descriptionSuffix).isFalse();
		assertThat(DocStatus.ofCode(payment.getDocStatus())).as("settlement payment DocStatus (%s)", descriptionSuffix).isEqualTo(DocStatus.Completed);
	}

	private int countPOSReturnDocuments(@NonNull final String retryToken)
	{
		return countReturnDocumentsByExternalId("POSReturn-" + externalIdForRetryToken(retryToken));
	}

	private int countReturnDocumentsByExternalId(@NonNull final String returnExternalId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_ExternalId, returnExternalId)
				.create()
				.count();
	}

	@NonNull
	private UUID externalIdForRetryToken(@NonNull final String retryToken)
	{
		return retryTokenToExternalId.computeIfAbsent(retryToken, ignored -> UUID.randomUUID());
	}

	private POSReturnResult createReturnFromTillPrices(@NonNull final ReturnRequestParams params)
	{
		return posReturnService.createReturnFromTillPrices(
				params.getPosTerminalId(),
				params.getExternalId(),
				params.getCashierId(),
				params.getRequestedLines());
	}

	@NonNull
	private ReturnRequestParams buildRequestParams(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final List<DataTableRow> rows)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(userLogin);

		// reads the till's own products/prices ONCE, the same way POSProductsService does — not per row
		final List<POSProduct> posProducts = posService.getProducts(posTerminalId, SystemTime.asInstant(), null).toList();

		final ImmutableList.Builder<POSReturnRequestedLine> requestedLines = ImmutableList.builder();
		for (final DataTableRow row : rows)
		{
			final ProductId productId = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
			final BigDecimal qty = row.getAsBigDecimal("Qty");
			final UomId expectedPriceUomId = uomDAO.getUomIdByX12DE355(row.getAsUOMCode("UOM"));

			final POSProduct posProduct = posProducts.stream()
					.filter(product -> product.getId().equals(productId))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("Product is not offered at the POS terminal")
							.setParameter("M_Product_ID", productId)
							.setParameter("posTerminalId", posTerminalId));

			// the production entry derives the price UOM server-side from the till (never from the client), and the
			// returned qty is measured in it; assert the scenario's UOM IS that till price UOM instead of sending one
			assertThat(posProduct.getPriceUom().getUomId())
					.as("till price UOM for product %s", productId)
					.isEqualTo(expectedPriceUomId);

			requestedLines.add(POSReturnRequestedLine.builder()
					.productId(productId)
					.qty(qty)
					.build());
		}

		final UUID externalId = rows.get(0).getAsOptionalString("ExternalId")
				.map(this::externalIdForRetryToken)
				.orElseGet(UUID::randomUUID);

		return new ReturnRequestParams(posTerminalId, externalId, cashierId, requestedLines.build());
	}

	/** The production inputs to {@link POSReturnService#createReturnFromTillPrices}: product+qty only, no price. */
	@Value
	private static class ReturnRequestParams
	{
		@NonNull POSTerminalId posTerminalId;
		@NonNull UUID externalId;
		@NonNull UserId cashierId;
		@NonNull ImmutableList<POSReturnRequestedLine> requestedLines;
	}
}
