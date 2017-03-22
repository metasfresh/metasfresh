package de.metas.request.model.validator;

import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_QualityNote;

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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_R_Request.COLUMNNAME_M_InOut_ID)
	@CalloutMethod(columnNames = { I_R_Request.COLUMNNAME_M_InOut_ID })
	public void onMInOutSet(final de.metas.request.model.I_R_Request request)
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(request.getM_InOut(), I_M_InOut.class);

		if (inout == null)
		{
			// in case the inout was removed or is not set, the DateDelivered shall also be not set
			request.setDateDelivered(null);
		}
		else
		{
			request.setDateDelivered(inout.getMovementDate());
		}

	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = de.metas.request.model.I_R_Request.COLUMNNAME_M_QualityNote_ID)
	@CalloutMethod(columnNames = { de.metas.request.model.I_R_Request.COLUMNNAME_M_QualityNote_ID })
	public void onQualityNoteChanged(final de.metas.request.model.I_R_Request request)
	{
		final I_M_QualityNote qualityNote = request.getM_QualityNote();
		if (qualityNote == null)
		{
			// nothing to do
			return;
		}

		// set the request's performance type with the value form the quality note entry
		final String performanceType = qualityNote.getPerformanceType();

		request.setPerformanceType(performanceType);
	}
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW})
	public void setSalesRep(final I_R_Request request)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(request);
		
		final I_AD_Role role = Services.get(IRoleDAO.class).retrieveRole(ctx);
		
		//task #577: The SalesRep in R_Request will be Role's supervisor
		final I_AD_User supervisor = role.getSupervisor();
		
		if(supervisor != null)
		{
			request.setSalesRep(supervisor);
		}
	}
}
