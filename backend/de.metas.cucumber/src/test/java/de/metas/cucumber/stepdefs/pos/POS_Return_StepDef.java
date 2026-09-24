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
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.pos.POSProduct;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pos.returns.POSReturnLine;
import de.metas.pos.returns.POSReturnRequest;
import de.metas.pos.returns.POSReturnResult;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
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
 * {@link POSService#createReturn}, and its credit invoice candidate is priced at the till's own price for the
 * product (not the walk-in customer's own sales pricing system).
 */
@RequiredArgsConstructor
public class POS_Return_StepDef
{
	private static final Logger logger = LogManager.getLogger(POS_Return_StepDef.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);
	@NonNull private final POSTerminalService posTerminalService = SpringContextHolder.instance.getBean(POSTerminalService.class);

	private final C_POS_StepDefData posTable;
	private final M_Product_StepDefData productTable;
	private final M_InOut_StepDefData inoutTable;

	/** Per-scenario token → externalId map, so a retry token resolves to a fresh random UUID the first time it is
	 * seen in this scenario, and to that SAME UUID on every later call — never to a UUID any other scenario/run
	 * could also produce. */
	private final Map<String, UUID> retryTokenToExternalId = new HashMap<>();

	/**
	 * Drives a POS product return end to end: reads the till's current price for each returned product the same
	 * way {@code POSProductsService} does (from the terminal's own price list, not the walk-in customer's), then
	 * hands it to {@link POSService#createReturn}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) returned product<br>
	 *   <b>Qty</b> — (required) returned quantity<br>
	 *   <b>UOM</b> — (required) {@code X12DE355} code the quantity is expressed in (e.g. {@code KGM})<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, first row only, identifier) alias for the resulting customer-return
	 *   {@code M_InOut}<br>
	 *   <b>OPT.ExternalId</b> — (optional, first row only) an arbitrary token, mapped to a random UUID the first
	 *   time this scenario sees it and to that SAME UUID on every later call; pass the SAME token on a later call
	 *   to simulate a retried request (idempotency: resolves to the SAME return document instead of creating a
	 *   second one). Defaults to a fresh random UUID per call.<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData
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
		final POSReturnRequest request = buildRequest(terminalIdentifier, userLogin, rows);

		final POSReturnResult result = posService.createReturn(request);

		registerReturnInOut(rows, result);
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
		final POSReturnRequest request = buildRequest(terminalIdentifier, userLogin, rows);

		// AdempiereException#getErrorCode() resolves to AD_Message.ErrorCode when the message has one, falling
		// back to the AdMessageKey itself otherwise (the exact resolution AdempiereException's own constructor
		// does) — resolve the expectation the same way rather than assuming it is always the bare key
		final AdMessageKey expectedKey = AdMessageKey.of(expectedAdMessage);
		final String expectedErrorCode = Optional.ofNullable(msgBL.getErrorCode(expectedKey))
				.orElseGet(expectedKey::toAD_Message);

		assertThatThrownBy(() -> posService.createReturn(request))
				.as("POS return must be rejected")
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).as("AD_Message").isEqualTo(expectedErrorCode));
	}

	/**
	 * Proves the terminal's {@code C_POS} row lock genuinely SERIALIZES two concurrent requests, rather than
	 * only resolving a retry after the fact once one has already committed (that idempotency-only case is
	 * {@link #posProductReturn} called twice with the same {@code OPT.ExternalId}, asserted via
	 * {@link #assertExactlyOnePOSReturnDocument}).
	 *
	 * <p>In production this models a client that resends a request — e.g. after a network timeout — while the
	 * server is still mid-flight on the first attempt for the SAME terminal. Locking the {@code C_POS} row
	 * directly, on a separate thread/transaction, instead of racing two real {@code createReturn} calls and
	 * hoping they overlap, makes the blocking window deterministic: a bare race is flaky (the two calls might
	 * never actually overlap inside the critical section), so this step controls the window explicitly instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as {@link #posProductReturn} (incl. {@code OPT.M_InOut_ID}, registered once the
	 * call has completed)
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return at POS terminal till by metasfresh blocks while the terminal is locked by a concurrent transaction:
	 *   | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
	 *   | product      | 0.3 | KGM | return_1       |
	 * </pre>
	 */
	@And("^a product return at POS terminal (\\S+) by (\\S+) blocks while the terminal is locked by a concurrent transaction:$")
	public void posProductReturnBlocksOnConcurrentLock(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable) throws Exception
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final POSReturnRequest request = buildRequest(terminalIdentifier, userLogin, rows);
		final POSTerminalId posTerminalId = request.getPosTerminalId();
		final String returnExternalId = "POSReturn-" + request.getExternalId();

		final ExecutorService executor = Executors.newFixedThreadPool(2);
		// signals crossing the two worker threads and the main (step) thread — no StepDefData/shared step-def
		// state is touched by either worker; they only see the plain posTerminalId/request captured above and
		// report back exclusively via these latches and the Futures below
		final CountDownLatch lockAcquired = new CountDownLatch(1);
		final CountDownLatch releaseSignal = new CountDownLatch(1);
		final AtomicReference<Throwable> lockHolderFailure = new AtomicReference<>();

		try
		{
			// 1) own thread, own (thread-inherited) transaction: acquire and HOLD the row lock until told to
			// release. Goes through POSTerminalService (not POSTerminalRepository directly) so this exercises
			// the exact same production entry point POSReturnService itself calls.
			final Future<?> lockHolderFuture = executor.submit(() -> holdLockUntilReleased(posTerminalId, lockAcquired, releaseSignal, lockHolderFailure));

			assertThat(lockAcquired.await(10, TimeUnit.SECONDS)).as("lock holder acquired the C_POS row lock").isTrue();
			if (lockHolderFailure.get() != null)
			{
				throw AdempiereException.wrapIfNeeded(lockHolderFailure.get());
			}

			// 2) own thread, own (thread-inherited) transaction: the real call under test
			final Future<POSReturnResult> createReturnFuture = executor.submit(() -> posService.createReturn(request));

			// 3) must NOT complete while the lock is held, and must not have created a document yet
			assertThatThrownBy(() -> createReturnFuture.get(3, TimeUnit.SECONDS))
					.as("createReturn must block while the terminal's row lock is held by the concurrent transaction")
					.isInstanceOf(TimeoutException.class);
			assertThat(countReturnDocumentsByExternalId(returnExternalId)).as("no POS return document while the lock is held").isZero();

			// 4) release the lock
			releaseSignal.countDown();
			lockHolderFuture.get(10, TimeUnit.SECONDS);
			if (lockHolderFailure.get() != null)
			{
				throw AdempiereException.wrapIfNeeded(lockHolderFailure.get());
			}

			// 5) must now complete, and exactly one document must exist
			final POSReturnResult result = createReturnFuture.get(30, TimeUnit.SECONDS);
			assertThat(result).as("createReturn must complete once the lock is released").isNotNull();
			assertThat(countReturnDocumentsByExternalId(returnExternalId)).as("exactly one POS return document once the lock is released").isEqualTo(1);

			registerReturnInOut(rows, result);
		}
		finally
		{
			// unconditional cleanup so a failed assertion above can never wedge the stack: unblock the lock
			// holder (no-op if already released) and wait for both worker threads to actually finish
			releaseSignal.countDown();
			executor.shutdown();
			if (!executor.awaitTermination(30, TimeUnit.SECONDS))
			{
				executor.shutdownNow();
			}
		}
	}

	/**
	 * Runs on the lock-holder worker thread, in its own (thread-inherited) transaction: acquires the terminal's
	 * {@code C_POS} row lock and HOLDS it until {@code releaseSignal} fires (or 30s pass). Any failure is logged and
	 * handed back to the step thread via {@code failure}; {@code lockAcquired} is counted down either way so the
	 * step thread never waits on a lock holder that already died.
	 */
	private void holdLockUntilReleased(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final CountDownLatch lockAcquired,
			@NonNull final CountDownLatch releaseSignal,
			@NonNull final AtomicReference<Throwable> failure)
	{
		trxManager.callInThreadInheritedTrx(() -> {
			try
			{
				posTerminalService.lockForUpdate(posTerminalId);
				lockAcquired.countDown();
				releaseSignal.await(30, TimeUnit.SECONDS);
			}
			catch (final Throwable t)
			{
				logger.error("Lock holder failed for posTerminalId={}", posTerminalId, t);
				failure.set(t);
				lockAcquired.countDown();
			}
			return null;
		});
	}

	/**
	 * Registers the return's {@code M_InOut} under the first row's {@code OPT.M_InOut_ID} identifier, if given.
	 */
	private void registerReturnInOut(@NonNull final List<DataTableRow> rows, @NonNull final POSReturnResult result)
	{
		final I_M_InOut returnRecord = InterfaceWrapperHelper.load(result.getReturnInOutId(), I_M_InOut.class);
		rows.get(0).getAsOptionalIdentifier("M_InOut_ID")
				.ifPresent(returnIdentifier -> inoutTable.putOrReplace(returnIdentifier, returnRecord));
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

	@NonNull
	private POSReturnRequest buildRequest(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final List<DataTableRow> rows)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		// resolves and validates the login; POSReturnRequest itself carries no cashierId
		StepDefUtil.getUserIdByLogin(userLogin);
		final POSTerminal terminal = posService.getPOSTerminalById(posTerminalId);

		// reads the till's own products/prices ONCE, the same way POSProductsService does — not per row
		final List<POSProduct> posProducts = posService.getProducts(posTerminalId, SystemTime.asInstant(), null).toList();

		final ImmutableList.Builder<POSReturnLine> lines = ImmutableList.builder();
		for (final DataTableRow row : rows)
		{
			final ProductId productId = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
			final BigDecimal qty = row.getAsBigDecimal("Qty");
			final UomId uomId = uomDAO.getUomIdByX12DE355(row.getAsUOMCode("UOM"));

			final POSProduct posProduct = posProducts.stream()
					.filter(product -> product.getId().equals(productId))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("Product is not offered at the POS terminal")
							.setParameter("M_Product_ID", productId)
							.setParameter("posTerminalId", posTerminalId));

			// price UOM (posProduct.getUom()) is the product's own configured price UOM, a separate concept from
			// the Qty column's UOM above; POSReturnService rejects a genuine mismatch against the invoice
			// candidate's own price UOM
			lines.add(POSReturnLine.builder()
					.productId(productId)
					.qty(Quantity.of(qty, uomDAO.getById(uomId)))
					.price(Money.of(posProduct.getPrice().getAsBigDecimal(), terminal.getCurrencyId()))
					.priceUomId(posProduct.getUom().getUomId())
					.build());
		}

		final UUID externalId = rows.get(0).getAsOptionalString("ExternalId")
				.map(this::externalIdForRetryToken)
				.orElseGet(UUID::randomUUID);

		return POSReturnRequest.builder()
				.posTerminalId(posTerminalId)
				.externalId(externalId)
				.lines(lines.build())
				.build();
	}
}
