package de.metas.ui.web.pickingV2.packageable.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.jasper.client.JRClient;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickRowsRepository;
import de.metas.util.Services;
import lombok.NonNull;

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

public class PackageablesView_PrintPicklist extends PackageablesViewBasedProcess
{
	final private static AdProcessId PickListPdf_AD_Process_ID = AdProcessId.ofRepoId(541202);

	@Autowired
	private ProductsToPickRowsRepository productsToPickRowsRepository;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkPreconditionsApplicable_SingleSelectedRow();
	}

	@Override
	protected String doIt()
	{
		final PackageableRow row = getSingleSelectedRow();

		// pick
		productsToPickRowsRepository.pick(row);

		// print
		final byte[] pickList = printPicklist(row);

		// preview
		getResult().setReportData(
				pickList,
				buildFilename(row),
				OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private String buildFilename(@NonNull final PackageableRow row)
	{

		final String customer = row.getCustomer().getDisplayName();
		final String docuemntNo = row.getOrderDocumentNo();

		return Joiner.on("_")
				.skipNulls()
				.join(docuemntNo, customer)
				+ ".pdf";
	}

	private byte[] printPicklist(@NonNull final PackageableRow row)
	{

		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(PickListPdf_AD_Process_ID);
		pinstance.setIsProcessing(true);
		InterfaceWrapperHelper.save(pinstance);

		final List<ProcessInfoParameter> piParams = new ArrayList<>();
		final String orderNo = row.getOrderDocumentNo();
		piParams.add(ProcessInfoParameter.of(I_M_Packageable_V.COLUMNNAME_OrderDocumentNo, orderNo));
		Services.get(IADPInstanceDAO.class).saveParameterToDB(PInstanceId.ofRepoId(pinstance.getAD_PInstance_ID()), piParams);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(PickListPdf_AD_Process_ID)
				.setAD_PInstance(pinstance)
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final JRClient jrClient = JRClient.get();
		final byte[] pdf = jrClient.report(jasperProcessInfo);

		return pdf;
	}
}
