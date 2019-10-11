package de.metas.ui.web.pickingV2.packageable.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
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

	@Autowired
	private PickingCandidateRepository pickingCandidateRepository;

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkPreconditionsApplicable_SingleSelectedRow()
				.and(this::acceptIfPickingCandidatesAreDraft);
	}

	private ProcessPreconditionsResolution acceptIfPickingCandidatesAreDraft()
	{
		final PackageableRow row = getSingleSelectedRow();

		// allow draft pikcing candidates
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByShipmentScheduleIdsAndStatus(row.getShipmentScheduleIds(), PickingCandidateStatus.Draft);
		if (pickingCandidates.size() > 0)
		{
			return ProcessPreconditionsResolution.accept();
		}

		// allow if there is no picking candidate yet, this process will generate before printing
		final boolean existsPickingCandidates = pickingCandidateRepository.existsPickingCandidates(row.getShipmentScheduleIds());
		if (!existsPickingCandidates)
		{
			return ProcessPreconditionsResolution.accept();
		}

		return ProcessPreconditionsResolution.rejectWithInternalReason("no picking product to print");
	}

	@Override
	protected String doIt()
	{
		final PackageableRow row = getSingleSelectedRow();

		// pick
		final List<PickingCandidate> pcickingCandidates = productsToPickRowsRepository.pick(row);

		// save
		pickingCandidateRepository.saveAll(pcickingCandidates);

		// print
		final byte[] pickList = printPicklist(row);

		// preview
		getResult().setReportData(
				pickList,
				buildFilename(row),
				OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private byte[] printPicklist(@NonNull final PackageableRow row)
	{
		final PInstanceRequest pinstanceRequest = createPInstanceRequest(row);
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(PickListPdf_AD_Process_ID)
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final JRClient jrClient = JRClient.get();
		return jrClient.report(jasperProcessInfo);
	}

	private PInstanceRequest createPInstanceRequest(@NonNull final PackageableRow row)
	{
		final String orderNo = row.getOrderDocumentNo();
		final List<ProcessInfoParameter> piParams = ImmutableList.of(ProcessInfoParameter.of(I_M_Packageable_V.COLUMNNAME_OrderDocumentNo, orderNo));

		final PInstanceRequest pinstanceRequest = PInstanceRequest.builder()
				.processId(PickListPdf_AD_Process_ID)
				.processParams(piParams)
				.build();
		return pinstanceRequest;
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
}
