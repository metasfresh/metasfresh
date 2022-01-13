/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.tax.interceptor;

import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.organization.OrgId;
import de.metas.tax.api.TypeOfDestCountry;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Tax;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.OUTSIDE_COUNTRY_AREA;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;

@Interceptor(I_C_Tax.class)
@Component
public class C_Tax
{
	private final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Tax.COLUMNNAME_To_Country_ID })
	public void updateTypeOfDestCountry(@NonNull final I_C_Tax tax)
	{
		final CountryId toCountryId = CountryId.ofRepoIdOrNull(tax.getTo_Country_ID());
		if (toCountryId == null)
		{
			final OrgId orgId = OrgId.ofRepoId(tax.getAD_Org_ID());
			if (orgId.isAny())
			{
				tax.setTypeOfDestCountry(WITHIN_COUNTRY_AREA.getCode());
			}
		}
		else
		{
			if (tax.getC_Country_ID() == tax.getTo_Country_ID())
			{
				tax.setTypeOfDestCountry(DOMESTIC.getCode());
			}
			else
			{
				final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(toCountryId);
				final boolean isEULocation = countryAreaBL.isMemberOf(Env.getCtx(),
						ICountryAreaBL.COUNTRYAREAKEY_EU,
						countryCode,
						tax.getUpdated());
				final TypeOfDestCountry typeOfDestCountry = isEULocation ? WITHIN_COUNTRY_AREA : OUTSIDE_COUNTRY_AREA;
				tax.setTypeOfDestCountry(typeOfDestCountry.getCode());

			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Tax.COLUMNNAME_TypeOfDestCountry })
	public void updateToCountryId(@NonNull final I_C_Tax tax)
	{
		final TypeOfDestCountry typeOfDestCountry = TypeOfDestCountry.ofNullableCode(tax.getTypeOfDestCountry());
		final int countryId = tax.getC_Country_ID();
		final int toCountryIdRepoId = tax.getTo_Country_ID();
		if ((DOMESTIC.equals(typeOfDestCountry) && countryId != toCountryIdRepoId) || (countryId == toCountryIdRepoId && !DOMESTIC.equals(typeOfDestCountry)))
		{
			tax.setTo_Country_ID(0);
		}
		else
		{
			final CountryId toCountryId = CountryId.ofRepoIdOrNull(toCountryIdRepoId);
			if (toCountryId != null)
			{
				final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(toCountryId);
				final boolean isEULocation = countryAreaBL.isMemberOf(Env.getCtx(),
						ICountryAreaBL.COUNTRYAREAKEY_EU,
						countryCode,
						Env.getDate());
				if ((isEULocation && !WITHIN_COUNTRY_AREA.equals(typeOfDestCountry)) || (!isEULocation && OUTSIDE_COUNTRY_AREA.equals(typeOfDestCountry)))
				{
					tax.setTo_Country_ID(0);
				}
			}
		}
	}

}
