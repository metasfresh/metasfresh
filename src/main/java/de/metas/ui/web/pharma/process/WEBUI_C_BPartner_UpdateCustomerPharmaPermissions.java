package de.metas.ui.web.pharma.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.PharmaCustomerPermission;
import de.metas.vertical.pharma.PharmaCustomerPermissions;
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

public class WEBUI_C_BPartner_UpdateCustomerPharmaPermissions extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{

	private static final String PARAM_IsPharmaAgentPermission = I_C_BPartner.COLUMNNAME_IsPharmaAgentPermission;
	@Param(parameterName = PARAM_IsPharmaAgentPermission)
	private boolean p_IsPharmaAgentPermission;

	private static final String PARAM_IsPharmaciePermission = I_C_BPartner.COLUMNNAME_IsPharmaciePermission;
	@Param(parameterName = PARAM_IsPharmaciePermission)
	private boolean p_IsPharmaciePermission;

	private static final String PARAM_IsPharmaManufacturerPermission = I_C_BPartner.COLUMNNAME_IsPharmaManufacturerPermission;
	@Param(parameterName = PARAM_IsPharmaManufacturerPermission)
	private boolean p_IsPharmaManufacturerPermission;

	private static final String PARAM_IsPharmaWholesalePermission = I_C_BPartner.COLUMNNAME_IsPharmaWholesalePermission;
	@Param(parameterName = PARAM_IsPharmaWholesalePermission)
	private boolean p_IsPharmaWholesalePermission;

	private static final String PARAM_IsVeterinaryPharmacyPermission = I_C_BPartner.COLUMNNAME_IsVeterinaryPharmacyPermission;
	@Param(parameterName = PARAM_IsVeterinaryPharmacyPermission)
	private boolean p_IsVeterinaryPharmacyPermission;

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{

		final I_C_BPartner bpartner = getRecord(I_C_BPartner.class);
		final PharmaCustomerPermissions pharmaCustomerPermissions = PharmaCustomerPermissions.of(bpartner);

		final String parameterName = parameter.getColumnName();

		if (PARAM_IsPharmaAgentPermission.equals(parameterName))
		{

			return pharmaCustomerPermissions.hasPermission(PharmaCustomerPermission.PHARMA_AGENT);
		}
		if (PARAM_IsPharmaciePermission.equals(parameterName))
		{

			return pharmaCustomerPermissions.hasPermission(PharmaCustomerPermission.PHARMACIE);
		}
		if (PARAM_IsPharmaManufacturerPermission.equals(parameterName))
		{

			return pharmaCustomerPermissions.hasPermission(PharmaCustomerPermission.PHARMA_MANUFACTURER);
		}
		if (PARAM_IsPharmaWholesalePermission.equals(parameterName))
		{

			return pharmaCustomerPermissions.hasPermission(PharmaCustomerPermission.PHARMA_WHOLESALE);
		}
		if (PARAM_IsVeterinaryPharmacyPermission.equals(parameterName))
		{

			return pharmaCustomerPermissions.hasPermission(PharmaCustomerPermission.VETERINARY_PHARMACY);
		}

		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		
		final I_C_BPartner partner = getRecord(I_C_BPartner.class);

		partner.setIsPharmaAgentPermission(p_IsPharmaAgentPermission);
		partner.setIsPharmaciePermission(p_IsPharmaciePermission);
		partner.setIsPharmaManufacturerPermission(p_IsPharmaManufacturerPermission);
		partner.setIsPharmaWholesalePermission(p_IsPharmaWholesalePermission);
		partner.setIsVeterinaryPharmacyPermission(p_IsVeterinaryPharmacyPermission);

		save(partner);

		return MSG_OK;
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
		if (!partner.isCustomer())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText("OnlyCustomers"));
		}

		return ProcessPreconditionsResolution.accept();
	}

}
