package de.metas.freighcost.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.model.I_M_FreightCostDetail;
import org.compiere.model.I_C_Country;
import org.springframework.stereotype.Component;

import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Component
@Callout(I_M_FreightCostDetail.class)
public class M_FreightCostDetail
{
	public M_FreightCostDetail()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_M_FreightCostDetail.COLUMNNAME_C_Country_ID })
	public void setCurrencyFromCountry(final I_M_FreightCostDetail detail, final ICalloutField field)
	{
		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

		final int countryRecordId = detail.getC_Country_ID();

		if (countryRecordId <= 0)
		{
			// the country was not set. Do nothing
			return;
		}

		final CountryId countryId = CountryId.ofRepoId(countryRecordId);
		final I_C_Country countryRecord = countryDAO.getById(countryId);

		final int currencyRecordId = countryRecord.getC_Currency_ID();

		detail.setC_Currency_ID(currencyRecordId);
	}
}
