package de.metas.request.model.validator;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Validator(I_R_Request.class)
@Callout(I_R_Request.class)
public class R_Request
{

	
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_R_Request.COLUMNNAME_R_RequestType_ID)
	@CalloutMethod(columnNames = { I_R_Request.COLUMNNAME_R_RequestType_ID })
	public void onRequestTypeChange(final I_R_Request request)
	{
		final I_R_RequestType requestType = request.getR_RequestType();

		if (requestType == null)
		{
			// in case the request type is deleted, the R_RequestType_InternalName must be set to null
			request.setR_RequestType_InternalName(null);
		}

		else
		{
			// set the internal name of the request type
			request.setR_RequestType_InternalName(requestType.getInternalName());
		}
	}
}
