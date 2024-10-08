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
				.listIds(POSTerminalId::ofRepoId);
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
