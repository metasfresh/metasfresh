/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.organization.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.CountryId;
import de.metas.util.Services;
import org.compiere.model.I_C_Fiscal_Representation;
import org.springframework.stereotype.Service;

@Service
public class FiscalRepresentationBL
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public void updateCountryId(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getC_BPartner_Location_ID() > 0)
		{
			final BPartnerLocationId bpLocation = BPartnerLocationId.ofRepoId(fiscalRep.getC_BPartner_Representative_ID(), fiscalRep.getC_BPartner_Location_ID());
			final CountryId countryId = bpartnerDAO.retrieveBPartnerLocationCountryId(bpLocation);
			fiscalRep.setTo_Country_ID(countryId.getRepoId());
		}
	}

	public boolean isValidFromDate(final I_C_Fiscal_Representation fiscalRep)
	{
		return fiscalRep.getValidFrom().before(fiscalRep.getValidTo());
	}

	public boolean isValidToDate(final I_C_Fiscal_Representation fiscalRep)
	{
		return fiscalRep.getValidTo().after(fiscalRep.getValidFrom());
	}

	public void updateValidTo(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRep.setValidTo(fiscalRep.getValidFrom());
	}

	public void updateValidFrom(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRep.setValidFrom(fiscalRep.getValidTo());
	}

}
