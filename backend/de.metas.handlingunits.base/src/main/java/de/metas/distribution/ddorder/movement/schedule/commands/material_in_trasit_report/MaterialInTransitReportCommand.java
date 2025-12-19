package de.metas.distribution.ddorder.movement.schedule.commands.material_in_trasit_report;

import de.metas.handlingunits.model.I_M_Locator;
import de.metas.process.ProcessInfo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

@Builder
public class MaterialInTransitReportCommand
{
	private static final String AD_PROCESS_VALUE = "DD_Order_MaterialInTransitReport";
	private static final String PARAM_M_Locator_ID = "M_Locator_ID";

	@NonNull final LocatorId inTransitLocatorId;
	@NonNull String adLanguage;

	public void execute()
	{
		ProcessInfo.builder()
				.setAD_ProcessByValue(AD_PROCESS_VALUE)
				.setReportLanguage(adLanguage)
				.setPrintPreview(false)
				.setRecord(I_M_Locator.Table_Name, inTransitLocatorId) // IMPORTANT, else printing will fail
				.addParameter(PARAM_M_Locator_ID, inTransitLocatorId)
				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync();
	}

}
