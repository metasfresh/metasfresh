package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUToReport;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IModelTranslationMap;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HULabelService
{
	private final static AdValRuleId HU_LABEL_PRINTING_OPTIONS_VAL_RULE_ID = AdValRuleId.ofRepoId(540604);

	@NonNull private final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final HULabelConfigService huLabelConfigService;
	@NonNull private final HUQRCodesService huQRCodesService;

	private final CCache<Integer, HULabelPrintProcessesList> printProcessesCache = CCache.<Integer, HULabelPrintProcessesList>builder()
			.cacheName("HULabelPrintProcess")
			.tableName(I_AD_Process.Table_Name)
			.initialCapacity(1)
			.build();

	public ExplainedOptional<HULabelConfig> getFirstMatching(final HULabelConfigQuery query)
	{
		return huLabelConfigService.getFirstMatching(query);
	}

	public void print(@NonNull final HULabelPrintRequest request)
	{
		HULabelPrintCommand.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huLabelConfigService(huLabelConfigService)
				.huProcessDAO(huProcessDAO)
				.huQRCodesService(huQRCodesService)
				.huLabelService(this)
				.request(request)
				.build()
				.execute();
	}

	public void printNow(final HULabelDirectPrintRequest request)
	{
		if (request.getHus().isEmpty())
		{
			return;
		}

		final AdProcessId printFormatProcessId = request.getPrintFormatProcessId();
		final HUReportExecutor printExecutor = HUReportExecutor.newInstance()
				.printPreview(false)
				.numberOfCopies(CoalesceUtil.coalesceNotNull(request.getPrintCopies(), PrintCopies.ONE));

		if (request.isOnlyOneHUPerPrint())
		{
			for (final HUToReport hu : request.getHus())
			{
				printExecutor.executeNow(printFormatProcessId, ImmutableList.of(hu));
			}
		}
		else
		{
			printExecutor.executeNow(printFormatProcessId, request.getHus());
		}
	}

	@NonNull
	public HULabelPrintProcessesList getHULabelPrintFormatProcesses()
	{
		return printProcessesCache.getOrLoad(0, this::retrieveHULabelPrintFormatProcesses);
	}

	private HULabelPrintProcessesList retrieveHULabelPrintFormatProcesses()
	{
		return processDAO.retrieveProcessRecordsByValRule(HU_LABEL_PRINTING_OPTIONS_VAL_RULE_ID)
				.stream()
				.map(HULabelService::toHULabelPrintProcess)
				.collect(HULabelPrintProcessesList.collect());
	}

	private static HULabelPrintProcess toHULabelPrintProcess(final I_AD_Process adProcess)
	{
		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);
		return HULabelPrintProcess.builder()
				.processId(AdProcessId.ofRepoId(adProcess.getAD_Process_ID()))
				.name(trlMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.build();
	}
}
