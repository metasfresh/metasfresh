package de.metas.money;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.ICurrencyDAO;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class CurrencyRepository
{
	public Currency getById(@NonNull final CurrencyId currencyId)
	{
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_Currency currencyRecord = currencyDAO.retrieveCurrency(Env.getCtx(), currencyId.getRepoId());

		return ofRecord(currencyRecord);
	}

	public Currency getById(final int currencyId)
	{
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_Currency currencyRecord = currencyDAO.retrieveCurrency(Env.getCtx(), currencyId);

		return ofRecord(currencyRecord);
	}

	private static Currency ofRecord(@NonNull final I_C_Currency currencyRecord)
	{
		return Currency.builder()
				.id(CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID()))
				.precision(currencyRecord.getStdPrecision())
				.threeLetterCode(currencyRecord.getISO_Code())
				.build();
	}

	public CurrencyId getForBPartnerLocationId(final BPartnerLocationId bpartnerLocationId)
	{

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

		final I_C_BPartner_Location bPartnerLocationRecord = bPartnerDAO.getBPartnerLocationById(bpartnerLocationId);

		final LocationId locationId = LocationId.ofRepoId(bPartnerLocationRecord.getC_Location_ID());

		final I_C_Location locationRecord = locationDAO.getById(locationId);
		final CountryId countryId = CountryId.ofRepoId(locationRecord.getC_Country_ID());

		final CurrencyId currencyId = countryDAO.getCountryCurrencyId(countryId).orElse(null);

		// TODO Improve this
		if (currencyId == null)
		{
			throw new AdempiereException("Please, set a currency fro the country " + countryId);
		}

		return currencyId;
	}
}
