package de.metas.pricing.process;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.report.ReportResultData;
import de.metas.report.server.OutputType;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class M_Pricelist_Excel extends JavaProcess
{
	private static final String AD_PROCESS_VALUE_RV_Fresh_PriceList_ExcelReport = "RV_Fresh_PriceList_ExcelReport";

	@Param(parameterName = I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, mandatory = true)
	private int p_M_Pricelist_Version_ID;

	@Param(parameterName = I_C_BPartner.COLUMNNAME_C_BPartner_ID, mandatory = true)
	private int p_C_BPartner_ID;

	@Param(parameterName = I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, mandatory = true)
	private int p_C_BPartner_Location_ID;



	@Override
	protected String doIt() throws Exception
	{
		final ProcessExecutionResult result = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_ProcessByValue(AD_PROCESS_VALUE_RV_Fresh_PriceList_ExcelReport)
				.addParameter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, p_M_Pricelist_Version_ID)
				.addParameter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, p_C_BPartner_ID)
				.addParameter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, p_C_BPartner_Location_ID)
				.setJRDesiredOutputType(OutputType.XLS)

				.buildAndPrepareExecution()
				.onErrorThrowException()

				.executeSync()
				.getResult();

		getProcessInfo().getResult().setReportData(result.getReportData());

		return MSG_OK;
	}

}
