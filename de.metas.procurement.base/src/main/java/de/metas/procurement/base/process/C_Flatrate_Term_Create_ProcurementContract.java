package de.metas.procurement.base.process;

import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;

import de.metas.flatrate.interfaces.I_C_BPartner;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.process.ISvrProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.process.SvrProcess;
import de.metas.procurement.base.IPMMContractsBL;
import de.metas.procurement.base.PMMContractBuilder;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
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
/**
 * Process used to create procurement contracts
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class C_Flatrate_Term_Create_ProcurementContract
		extends SvrProcess
		implements ISvrProcessDefaultParametersProvider
{
	// services
	private final transient IPMMContractsBL pmmContractsBL = Services.get(IPMMContractsBL.class);
	
	@Param(mandatory = true, parameterName = "C_Flatrate_Conditions_ID")
	private I_C_Flatrate_Conditions p_C_Flatrate_Conditions;

	@Param(mandatory = true, parameterName = "C_BPartner_ID")
	private I_C_BPartner p_C_BPartner;

	@Param(mandatory = true, parameterName = "Dates")
	private Timestamp p_StartDate;

	@Param(mandatory = true, parameterName = "Dates", parameterTo = true)
	private Timestamp p_EndDate;

	@Param(mandatory = true, parameterName = "PMM_Product_ID")
	private I_PMM_Product p_PMM_Product;

	@Param(mandatory = true, parameterName = "C_UOM_ID")
	private I_C_UOM p_C_UOM;

	private static final String PARAM_NAME_AD_USER_IN_CHARGE_ID = "AD_User_InCharge_ID";
	@Param(mandatory = false, parameterName = PARAM_NAME_AD_USER_IN_CHARGE_ID)
	private I_AD_User p_AD_User_Incharge;

	@Param(mandatory = true, parameterName = "C_Currency_ID")
	private I_C_Currency p_C_Currency;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Flatrate_Term term = PMMContractBuilder.newBuilder()
				.setCtx(getCtx())
				.setFailIfNotCreated(true)
				.setComplete(true)
				.setC_Flatrate_Conditions(p_C_Flatrate_Conditions)
				.setC_BPartner(p_C_BPartner)
				.setStartDate(p_StartDate)
				.setEndDate(p_EndDate)
				.setPMM_Product(p_PMM_Product)
				.setC_UOM(p_C_UOM)
				.setAD_User_InCharge(p_AD_User_Incharge)
				.setC_Currency(p_C_Currency)
				.build();

		setRecordToSelectAfterExecution(TableRecordReference.of(term));

		return MSG_OK;
	}

	/**
	 * If the given <code>parameterName</code> is {@value #PARAM_NAME_AD_USER_IN_CHARGE_ID},<br>
	 * then the method returns the user set in <code>AD_SysConfig</code> {@value #SYSCONFIG_AD_USER_IN_CHARGE}.
	 */
	@Override
	public Object getParameterDefaultValue(final String parameterName)
	{
		if (!PARAM_NAME_AD_USER_IN_CHARGE_ID.equals(parameterName))
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}

		final int adUserInChargeId = pmmContractsBL.getDefaultContractUserInCharge_ID(getCtx());
		if (adUserInChargeId < 0)
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}

		return adUserInChargeId; // we need to return the ID, not the actual record. Otherwise then lookup logic will be confused.
	}
}
