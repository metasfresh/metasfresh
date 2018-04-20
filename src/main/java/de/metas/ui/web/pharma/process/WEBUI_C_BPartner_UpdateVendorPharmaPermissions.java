package de.metas.ui.web.pharma.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.PharmaVendorPermission;
import de.metas.vertical.pharma.PharmaVendorPermissions;
import de.metas.vertical.pharma.model.I_C_BPartner;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_C_BPartner_UpdateVendorPharmaPermissions extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final String PARAM_IsPharmaVendorAgentPermission = I_C_BPartner.COLUMNNAME_IsPharmaVendorAgentPermission;
	@Param(parameterName = PARAM_IsPharmaVendorAgentPermission)
	private boolean p_IsPharmaVendorAgentPermission;
	
	private static final String PARAM_IsPharmaVendorManufacturerPermission = I_C_BPartner.COLUMNNAME_IsPharmaVendorManufacturerPermission;
	@Param(parameterName = PARAM_IsPharmaVendorManufacturerPermission)
	private boolean p_IsPharmaVendorManufacturerPermission;
	
	private static final String PARAM_IsPharmaVendorWholesalePermission = I_C_BPartner.COLUMNNAME_IsPharmaVendorWholesalePermission;
	@Param(parameterName = PARAM_IsPharmaVendorWholesalePermission)
	private boolean p_IsPharmaVendorWholesalePermission;

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_C_BPartner bpartner = getRecord(I_C_BPartner.class);
		final PharmaVendorPermissions pharmaVendorPermissions = PharmaVendorPermissions.of(bpartner);

		final String parameterName = parameter.getColumnName();
		
		if(PARAM_IsPharmaVendorAgentPermission.equals(parameterName))
		{
			return pharmaVendorPermissions.hasPermission(PharmaVendorPermission.PHARMA_AGENT);
		}
		if(PARAM_IsPharmaVendorManufacturerPermission.equals(parameterName))
		{
			return pharmaVendorPermissions.hasPermission(PharmaVendorPermission.PHARMA_MANUFACTURER);
		}
		if(PARAM_IsPharmaVendorWholesalePermission.equals(parameterName))
		{
			return pharmaVendorPermissions.hasPermission(PharmaVendorPermission.PHARMA_WHOLESALE);
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}

	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_BPartner partner = context.getSelectedModel(I_C_BPartner.class);
		if (!partner.isVendor())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText("OnlyVendors"));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_BPartner partner = getRecord(I_C_BPartner.class);

		partner.setIsPharmaVendorAgentPermission(p_IsPharmaVendorAgentPermission);
		partner.setIsPharmaVendorManufacturerPermission(p_IsPharmaVendorManufacturerPermission);
		partner.setIsPharmaVendorWholesalePermission(p_IsPharmaVendorWholesalePermission);

		save(partner);

		return MSG_OK;
	}

}
