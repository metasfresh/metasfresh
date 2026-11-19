package de.metas.frontend_testing.masterdata.hu_label_config;

import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreateHULabelConfigCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonHULabelConfigRequest request;

	public void execute()
	{
		final BPartnerId bpartnerId = context.getId(request.getBpartner(), BPartnerId.class);
		final HuUnitType huUnitType = request.getHuUnitType();

		final I_M_HU_Label_Config record = InterfaceWrapperHelper.newInstance(I_M_HU_Label_Config.class);
		record.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setSeqNo(10);
		final HULabelSourceDocType sourceDocType = HULabelSourceDocType.ofNullableCode(request.getSourceDocType());
		record.setHU_SourceDocType(sourceDocType != null ? sourceDocType.getCode() : null);
		record.setIsApplyToLUs(huUnitType == HuUnitType.LU);
		record.setIsApplyToTUs(huUnitType == HuUnitType.TU);
		record.setIsApplyToCUs(huUnitType == HuUnitType.VHU);
		record.setLabelReport_Process_ID(request.getProcess());
		record.setIsAutoPrint(request.isAutoPrint());
		record.setAutoPrintCopies(1);
		record.setIsActive(true);
		saveRecord(record);
	}
}
