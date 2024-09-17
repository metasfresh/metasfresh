package de.metas.pos;

import de.metas.cache.CCache;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_M_PriceList;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class POSConfigService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
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

		return POSConfig.builderFrom(rawConfig)
				.currency(currency)
				.pricePrecision(CurrencyPrecision.ofInt(priceList.getPricePrecision()))
				.build();
	}
}
