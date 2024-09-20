package de.metas.pos;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.cache.CCache;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_M_PriceList;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class POSConfigService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final POSConfigRawRepository configRepository;
	@NonNull private final CurrencyRepository currencyRepository;

	private final CCache<Integer, POSConfig> cache = CCache.<Integer, POSConfig>builder()
			.tableName(I_C_POS.Table_Name)
			.additionalTableNameToResetFor(I_M_PriceList.Table_Name)
			.additionalTableNameToResetFor(I_C_Currency.Table_Name)
			.build();

	@NonNull
	public POSConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	@NonNull
	private POSConfig retrieveConfig()
	{
		final POSConfigRaw rawConfig = configRepository.getConfig();
		final I_M_PriceList priceList = priceListDAO.getById(rawConfig.getPriceListId());
		if (priceList == null)
		{
			throw new AdempiereException("No price list found for ID: " + rawConfig.getPriceListId());
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		final Currency currency = currencyRepository.getById(currencyId);

		return POSConfig.builder()
				.id(rawConfig.getId())
				.cashbookId(rawConfig.getCashbookId())
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(priceList.getM_PricingSystem_ID(), priceList.getM_PriceList_ID()))
				.isTaxIncluded(priceList.isTaxIncluded())
				.pricePrecision(CurrencyPrecision.ofInt(priceList.getPricePrecision()))
				.shipFrom(extractShipFrom(rawConfig))
				.walkInCustomerShipToLocationId(extractWalkInCustomerShipTo(rawConfig))
				.salesRepId(rawConfig.getSalesRepId())
				.salesOrderDocTypeId(rawConfig.getSalesOrderDocTypeId())
				.currency(currency)
				.build();
	}

	private POSShipFrom extractShipFrom(final POSConfigRaw rawConfig)
	{
		final WarehouseId shipFromWarehouseId = rawConfig.getShipFromWarehouseId();
		return POSShipFrom.builder()
				.warehouseId(shipFromWarehouseId)
				.orgId(warehouseBL.getWarehouseOrgId(shipFromWarehouseId))
				.countryId(warehouseBL.getCountryId(shipFromWarehouseId))
				.build();
	}

	private @NonNull BPartnerLocationAndCaptureId extractWalkInCustomerShipTo(final POSConfigRaw rawConfig)
	{
		return BPartnerLocationAndCaptureId.ofRecord(
				bpartnerDAO.retrieveBPartnerLocation(BPartnerLocationQuery.builder()
						.type(BPartnerLocationQuery.Type.SHIP_TO)
						.bpartnerId(rawConfig.getWalkInCustomerId())
						.build())
		);
	}
}
