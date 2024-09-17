package de.metas.pos.rest_api.json;

import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
public class JsonContext
{
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull @Getter private final String adLanguage;

	public String getCurrencySymbol(final CurrencyId currencyId)
	{
		return currencyRepository.getById(currencyId).getSymbol().translate(adLanguage);
	}
}
