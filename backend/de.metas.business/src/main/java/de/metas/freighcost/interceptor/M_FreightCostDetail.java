package de.metas.freighcost.interceptor;

import de.metas.location.CountryAreaId;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.I_M_FreightCostDetail;
import org.compiere.model.I_C_Country;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
@Interceptor(I_M_FreightCostDetail.class)
public class M_FreightCostDetail
{
	private final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	public M_FreightCostDetail()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_M_FreightCostDetail.COLUMNNAME_C_Country_ID })
	public void setCurrencyFromCountry(final I_M_FreightCostDetail detail, final ICalloutField field)
	{
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_FreightCostDetail.COLUMNNAME_C_CountryArea_ID })
	public void clearCountryIfAreaSet(@NonNull final I_M_FreightCostDetail detail)
	{
		final CountryId countryId = CountryId.ofRepoIdOrNull(detail.getC_Country_ID());
		final CountryAreaId countryAreaId = CountryAreaId.ofRepoIdOrNull(detail.getC_CountryArea_ID());

		if (countryId != null && countryAreaId != null)
		{
			detail.setC_Country_ID(CountryId.toRepoId(null));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_FreightCostDetail.COLUMNNAME_C_Country_ID })
	public void clearAreaIfCountrySet(@NonNull final I_M_FreightCostDetail detail)
	{
		final CountryId countryId = CountryId.ofRepoIdOrNull(detail.getC_Country_ID());
		final CountryAreaId countryAreaId = CountryAreaId.ofRepoIdOrNull(detail.getC_CountryArea_ID());

		if (countryId != null && countryAreaId != null)
		{
			detail.setC_CountryArea_ID(CountryAreaId.toRepoId(null));
		}
	}

}
