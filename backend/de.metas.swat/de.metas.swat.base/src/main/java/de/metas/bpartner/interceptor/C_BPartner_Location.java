package de.metas.bpartner.interceptor;

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

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;

@Validator(I_C_BPartner_Location.class)
public class C_BPartner_Location
{
	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.bpartner.callout.C_BPartner_Location());
	}

	/**
	 * Update {@link I_C_BPartner_Location#COLUMNNAME_Address} field right before new. Updating on TYPE_BEFORE_CHANGE is not needed because C_Location_ID is not changing and if user edits the address,
	 * then {@link org.adempiere.bpartner.callout.BPartnerLocation#evalInput(GridTab)} is managing the case.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void updateAddressString(final I_C_BPartner_Location bpLocation)
	{
		Services.get(IBPartnerBL.class).setAddress(bpLocation);
	}

}
