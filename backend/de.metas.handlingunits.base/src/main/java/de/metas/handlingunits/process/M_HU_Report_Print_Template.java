package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.List;

abstract class M_HU_Report_Print_Template extends JavaProcess implements IProcessPrecondition
{
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	protected abstract AdProcessId getPrintFormatProcessId();

	@Override
	@RunOutOfTrx
	protected final String doIt()
	{
		final I_M_HU hu = getSelectedHU();

		generateQRCode(hu);

		final ReportResultData labelReport = createLabelReport(hu);
		getResult().setReportData(labelReport);

		return MSG_OK;
	}

	private I_M_HU getSelectedHU()
	{
		final List<I_M_HU> hus = handlingUnitsBL.getBySelectionId(getPinstanceId());
		return CollectionUtils.singleElement(hus);
	}

	private void generateQRCode(final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		huQRCodesService.generateForExistingHUs(HUQRCodeGenerateForExistingHUsRequest.ofHuId(huId));
	}

	private ReportResultData createLabelReport(@NonNull final I_M_HU hu)
	{
		final HUToReport huToReport = HUToReportWrapper.of(hu);
		return HUReportExecutor.newInstance()
				.printPreview(true)
				.executeNow(getPrintFormatProcessId(), ImmutableList.of(huToReport))
				.getReportData();
	}
}
