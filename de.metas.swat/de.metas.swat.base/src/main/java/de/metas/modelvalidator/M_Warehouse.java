package de.metas.modelvalidator;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;

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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_M_Warehouse;

@Validator(I_M_Warehouse.class)
@Callout(I_M_Warehouse.class)
public class M_Warehouse
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
	@CalloutMethod(columnNames = I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
	public void syncLocation(I_M_Warehouse warehouse)
	{
		final I_C_BPartner_Location bpLocation = warehouse.getC_BPartner_Location();
		if (bpLocation == null)
		{
			return;
		}
		warehouse.setC_Location_ID(bpLocation.getC_Location_ID());
	}
}
