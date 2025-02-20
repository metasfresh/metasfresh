package de.metas.procurement.base.process;

import de.metas.contracts.flatrate.process.ProcessUtil;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.procurement.base.IPMMContractsBL;
import de.metas.procurement.base.PMMContractBuilder;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Ini;

import java.sql.Timestamp;
import java.util.Properties;

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
		extends JavaProcess
		implements IProcessDefaultParametersProvider,
		IProcessPrecondition
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
	private CurrencyId p_CurrencyId;

	@Param(mandatory = true, parameterName = "IsComplete")
	private boolean p_isComplete;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if(ProcessUtil.isFlatFeeContract(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not supported for FlatFee contracts");
		}
		return ProcessPreconditionsResolution.accept();
	}
	
	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final I_C_Flatrate_Term term = createTermInOwnTrx();

		// TODO check out and cleanup those different methods
		final int adWindowId = getProcessInfo().getAD_Window_ID();
		if (adWindowId > 0 && !Ini.isSwingClient())
		{
			// this works for the webui
			getResult().setRecordToOpen(TableRecordReference.of(term), adWindowId, OpenTarget.SingleDocument);
		}
		else
		{
			// this is the old code that works for swing
			getResult().setRecordToSelectAfterExecution(TableRecordReference.of(term));
		}
		return MSG_OK;
	}

	private I_C_Flatrate_Term createTermInOwnTrx()
	{
		final TrxCallable<I_C_Flatrate_Term> callable = () -> {
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
					.setCurrencyId(p_CurrencyId)
					.setComplete(p_isComplete) // complete if flag on true, do not complete otherwise
					.build();
			return term;
		};

		// the default config is fine for us
		final ITrxRunConfig config = trxManager.newTrxRunConfigBuilder().build();
		return trxManager.call(ITrx.TRXNAME_None, config, callable);
	}

	/**
	 * If the given <code>parameterName</code> is {@value #PARAM_NAME_AD_USER_IN_CHARGE_ID},<br>
	 * then the method returns the user from {@link IPMMContractsBL#getDefaultContractUserInCharge_ID(Properties)}.
	 */
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();

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
