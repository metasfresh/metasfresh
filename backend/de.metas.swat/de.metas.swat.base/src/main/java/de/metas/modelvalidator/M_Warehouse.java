package de.metas.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_M_Warehouse.class)
@Interceptor(I_M_Warehouse.class)
@Component
public class M_Warehouse
{

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
	public void syncLocation(final I_M_Warehouse warehouse)
	{
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(
				BPartnerId.ofRepoIdOrNull(warehouse.getC_BPartner_ID()),
				warehouse.getC_BPartner_Location_ID()
		);

		if (bPartnerLocationId == null)
		{
			return;
		}

		final I_C_BPartner_Location bpLocation = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);
		if (bpLocation == null)
		{
			return;
		}

		warehouse.setC_Location_ID(bpLocation.getC_Location_ID());
	}

	@CalloutMethod(columnNames = I_M_Warehouse.COLUMNNAME_C_BPartner_ID)
	public void onPartnerChange(final I_M_Warehouse warehouse)
	{
		warehouse.setC_Location_ID(-1);
		warehouse.setC_BPartner_Location_ID(-1);
		warehouse.setAD_User_ID(-1);
	}
}
