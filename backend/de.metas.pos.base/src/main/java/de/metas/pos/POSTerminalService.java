package de.metas.pos;

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
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class POSTerminalService
{
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
	 * Locks the terminal's {@code C_POS} row for the rest of the caller's transaction, serializing two concurrent
	 * callers against the SAME terminal (e.g. two in-flight requests carrying the same idempotency key) — the
	 * second blocks here until the first commits, by which point its result already exists for the second to find.
	 */
	public void lockForUpdate(@NonNull final POSTerminalId posTerminalId)
	{
		posTerminalRepository.lockForUpdate(posTerminalId);
	}

	/**
	 * Runs {@code action} while holding a Postgres advisory lock keyed on the given POS terminal, for the WHOLE
	 * duration of {@code action} — even across separate top-level transactions {@code action} opens internally,
	 * unlike {@link #lockForUpdate} which only lasts until the caller's OWN transaction commits. Used by
	 * {@code POSReturnService#createReturn} to serialize its three separate top-level transactions (goods
	 * receipt/pricing, credit-memo generation, cash settlement) end to end against the same terminal, so two
	 * concurrent callers (e.g. two in-flight retries) can never interleave into each other's phases.
	 * <p>
	 * BOUNDED: polls to acquire the lock for at most {@code timeoutMillis} before giving up — never blocks
	 * indefinitely, so a single stuck caller (e.g. {@code action} hanging on a slow async wait) cannot freeze every
	 * OTHER caller for the same terminal. Carries no policy of its own for what "giving up" means: the caller reads
	 * its own timeout value and decides how to react to an empty result (e.g. a user-facing rejection message) —
	 * this method's job stops at "did we get the lock in time, yes or no".
	 *
	 * @return empty if the lock could not be acquired within {@code timeoutMillis}
	 */
	@NonNull
	public <T> Optional<T> tryRunWithCrossTransactionLock(
			@NonNull final POSTerminalId posTerminalId,
			final long timeoutMillis,
			@NonNull final Supplier<T> action)
	{
		return posTerminalRepository.tryRunWithCrossTransactionLock(posTerminalId, timeoutMillis, action);
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
