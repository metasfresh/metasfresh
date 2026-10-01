package de.metas.pos;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.cache.CCache;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_M_PriceList;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class POSTerminalService
{
	// how often a caller blocked waiting for the terminal's cross-transaction advisory lock re-polls
	// pg_try_advisory_lock; short enough that the caller-supplied timeout (de.metas.pos.Return.LockTimeoutMillis,
	// read by POSReturnService) is honored closely, long enough not to hammer the DB with a tight spin loop
	private static final long POLL_INTERVAL_MILLIS = 250;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final POSTerminalRepository posTerminalRepository;

	private final CCache<POSTerminalId, POSTerminal> cache = CCache.<POSTerminalId, POSTerminal>builder()
			.tableName(I_C_POS.Table_Name)
			.additionalTableNameToResetFor(I_M_PriceList.Table_Name)
			.additionalTableNameToResetFor(I_C_Currency.Table_Name)
			.build();

	@NonNull
	public POSTerminal getPOSTerminalById(final POSTerminalId posTerminalId)
	{
		return cache.getOrLoad(posTerminalId, this::retrievePOSTerminalById);
	}

	/**
	 * Runs {@code action} while holding a Postgres advisory lock keyed on the given POS terminal, for the WHOLE
	 * duration of {@code action} — even across separate top-level transactions {@code action} opens internally,
	 * unlike a transaction-scoped row lock, which only lasts until the caller's OWN transaction commits. This lets
	 * a caller serialize several separate top-level transactions end to end against the same terminal, so two
	 * concurrent callers can never interleave into each other's phases.
	 * <p>
	 * BOUNDED: polls to acquire the lock for at most {@code timeoutMillis} before giving up — never blocks
	 * indefinitely, so a single stuck caller (e.g. {@code action} hanging on a slow async wait) cannot freeze every
	 * OTHER caller for the same terminal. On giving up, throws the {@link RuntimeException} {@code onTimeout}
	 * supplies — the caller decides what that means (e.g. a user-facing rejection message); this method's own
	 * job stops at "did we get the lock in time, yes or no". On success, returns whatever {@code action} itself
	 * returns, VERBATIM — including {@code null} — with no wrapping in between, so a timeout can never be
	 * confused with {@code action} legitimately returning {@code null} (the ambiguity an {@code Optional<T>}
	 * return type would have).
	 *
	 * @throws RuntimeException the one {@code onTimeout} supplies, if the lock could not be acquired within
	 * {@code timeoutMillis}
	 */
	@NonNull
	public <T> T runWithCrossTransactionLock(
			@NonNull final POSTerminalId posTerminalId,
			final long timeoutMillis,
			@NonNull final Supplier<T> action,
			@NonNull final Supplier<? extends RuntimeException> onTimeout)
	{
		// the repository owns the JDBC connection + the two advisory-lock SQL primitives (tryAcquire/release);
		// the bounded poll/timeout policy — the clock and the sleep — lives HERE, not in the repository
		return posTerminalRepository.runWithAdvisoryLockConnection(
				posTerminalId,
				(tryAcquire, release) -> runWithBoundedAcquire(
						tryAcquire,
						release,
						timeoutMillis,
						POLL_INTERVAL_MILLIS,
						action,
						onTimeout));
	}

	/**
	 * The generic "poll a bounded number of times to acquire, then run-and-return-verbatim or throw" algorithm
	 * behind {@link #runWithCrossTransactionLock}. Package-private + static: a pure timing algorithm over the
	 * caller-supplied {@code tryAcquire}/{@code release} — no DB connection, no instance state, no AD-context
	 * dependency — so it can be unit-tested directly with fake acquire/release suppliers (see
	 * {@code POSTerminalServiceTest}). Lives in the service, not the repository, because a repository must carry
	 * no clock/loop/sleep.
	 */
	@NonNull
	static <T> T runWithBoundedAcquire(
			@NonNull final BooleanSupplier tryAcquire,
			@NonNull final Runnable release,
			final long timeoutMillis,
			final long pollIntervalMillis,
			@NonNull final Supplier<T> action,
			@NonNull final Supplier<? extends RuntimeException> onTimeout)
	{
		boolean locked = false;
		Throwable primaryError = null;
		try
		{
			// REAL elapsed time via a monotonic clock — NOT SystemTime, which is the business clock and is
			// frozen in integration tests ("metasfresh has date and time ..."); a frozen SystemTime would make
			// the deadline unreachable, so the poll loop would never time out and the till-busy guard would
			// never fire (cucumber S28210_TC20).
			final Stopwatch stopwatch = Stopwatch.createStarted();
			while (!(locked = tryAcquire.getAsBoolean()))
			{
				if (stopwatch.elapsed(TimeUnit.MILLISECONDS) >= timeoutMillis)
				{
					throw onTimeout.get();
				}
				sleepQuietly(pollIntervalMillis);
			}

			return action.get();
		}
		catch (final RuntimeException | Error e)
		{
			primaryError = e;
			throw e;
		}
		finally
		{
			if (locked)
			{
				try
				{
					release.run();
				}
				catch (final RuntimeException releaseError)
				{
					// A failing release (pg_advisory_unlock throwing) must NEVER mask the error the caller is
					// already propagating — e.g. the business rejection the cashier needs to see — nor be
					// swallowed when the action succeeded (the advisory lock would then leak on the pooled
					// connection and wedge the terminal as permanently "till busy"). So: attach it to the
					// in-flight error if there is one, otherwise let it propagate on its own.
					if (primaryError != null)
					{
						primaryError.addSuppressed(releaseError);
					}
					else
					{
						throw releaseError;
					}
				}
			}
		}
	}

	private static void sleepQuietly(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (final InterruptedException ex)
		{
			Thread.currentThread().interrupt();
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	public Collection<POSTerminal> getPOSTerminals()
	{
		final Set<POSTerminalId> posTerminalIds = retrievePOSTerminalsIds();
		return getPOSTerminalsByIds(posTerminalIds);
	}

	public Collection<POSTerminal> getPOSTerminalsByIds(final Set<POSTerminalId> posTerminalIds)
	{
		return cache.getAllOrLoad(posTerminalIds, this::retrievePOSTerminalsByIds);
	}

	@NonNull
	private POSTerminal retrievePOSTerminalById(@NonNull final POSTerminalId posTerminalId)
	{
		final I_C_POS record = retrieveRecordById(posTerminalId);
		return fromRecord(record);
	}

	private I_C_POS retrieveRecordById(@NonNull POSTerminalId posTerminalId)
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS.COLUMNNAME_C_POS_ID, posTerminalId)
				.create()
				.firstOnly(I_C_POS.class);
	}

	private Map<POSTerminalId, POSTerminal> retrievePOSTerminalsByIds(Set<POSTerminalId> posTerminalIds)
	{
		if (posTerminalIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_POS.COLUMNNAME_C_POS_ID, posTerminalIds)
				.create()
				.stream()
				.map(this::fromRecord)
				.collect(GuavaCollectors.toImmutableMapByKey(POSTerminal::getId));
	}

	private Set<POSTerminalId> retrievePOSTerminalsIds()
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.idsAsSet(POSTerminalId::ofRepoId);
	}

	private POSTerminal fromRecord(final I_C_POS record)
	{
		final PriceListId priceListId = PriceListId.ofRepoId(record.getM_PriceList_ID());
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		if (priceList == null)
		{
			throw new AdempiereException("No price list found for ID: " + priceListId);
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		final Currency currency = currencyRepository.getById(currencyId);

		return POSTerminal.builder()
				.id(POSTerminalId.ofRepoId(record.getC_POS_ID()))
				.name(record.getName())
				.cashbookId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.paymentProcessorConfig(extractPaymentProcessorConfig(record))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(priceList.getM_PricingSystem_ID(), priceList.getM_PriceList_ID()))
				.isTaxIncluded(priceList.isTaxIncluded())
				.pricePrecision(CurrencyPrecision.ofInt(priceList.getPricePrecision()))
				.shipFrom(extractShipFrom(record))
				.workplaceId(WorkplaceId.ofRepoIdOrNull(record.getC_Workplace_ID()))
				.walkInCustomerShipToLocationId(extractWalkInCustomerShipTo(record))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(record.getC_DocTypeOrder_ID()))
				.currency(currency)
				.cashJournalId(POSCashJournalId.ofRepoIdOrNull(record.getC_POS_Journal_ID()))
				.cashLastBalance(Money.of(record.getCashLastBalance(), currencyId))
				.build();
	}

	private static void updateRecord(final I_C_POS record, final @NonNull POSTerminal from)
	{
		record.setC_POS_Journal_ID(POSCashJournalId.toRepoId(from.getCashJournalId()));
		record.setCashLastBalance(from.getCashLastBalance().toBigDecimal());
	}

	@Nullable
	private static POSTerminalPaymentProcessorConfig extractPaymentProcessorConfig(final I_C_POS record)
	{
		final POSPaymentProcessorType type = POSPaymentProcessorType.ofNullableCode(record.getPOSPaymentProcessor());
		if (type == null)
		{
			return null;
		}

		return POSTerminalPaymentProcessorConfig.builder()
				.type(type)
				.sumUpConfigId(SumUpConfigId.ofRepoIdOrNull(record.getSUMUP_Config_ID()))
				.build();
	}

	private POSShipFrom extractShipFrom(final I_C_POS record)
	{
		final WarehouseId shipFromWarehouseId = WarehouseId.ofRepoId(record.getM_Warehouse_ID());
		return POSShipFrom.builder()
				.warehouseId(shipFromWarehouseId)
				.clientAndOrgId(warehouseBL.getWarehouseClientAndOrgId(shipFromWarehouseId))
				.countryId(warehouseBL.getCountryId(shipFromWarehouseId))
				.build();
	}

	private @NonNull BPartnerLocationAndCaptureId extractWalkInCustomerShipTo(final I_C_POS record)
	{
		final BPartnerId walkInCustomerId = BPartnerId.ofRepoId(record.getC_BPartnerCashTrx_ID());
		return BPartnerLocationAndCaptureId.ofRecord(
				bpartnerDAO.retrieveBPartnerLocation(BPartnerLocationQuery.builder()
						.type(BPartnerLocationQuery.Type.SHIP_TO)
						.bpartnerId(walkInCustomerId)
						.build())
		);
	}

	public POSTerminal updateById(@NonNull final POSTerminalId posTerminalId, @NonNull final UnaryOperator<POSTerminal> updater)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final I_C_POS record = retrieveRecordById(posTerminalId);
			final POSTerminal posTerminalBeforeChange = fromRecord(record);
			final POSTerminal posTerminal = updater.apply(posTerminalBeforeChange);
			if (Objects.equals(posTerminal, posTerminalBeforeChange))
			{
				return posTerminalBeforeChange;
			}

			updateRecord(record, posTerminal);
			InterfaceWrapperHelper.save(record);
			return posTerminal;
		});
	}
}
