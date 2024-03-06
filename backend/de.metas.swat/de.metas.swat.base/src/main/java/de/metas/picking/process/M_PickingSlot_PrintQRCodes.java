package de.metas.picking.process;

import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

import java.util.Set;

public class M_PickingSlot_PrintQRCodes extends JavaProcess
{
	private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);

	@Param(parameterName = "M_PickingSlot_ID")
	private PickingSlotId pickingSlotId;

	@Override
	protected String doIt()
	{
		final Set<PickingSlotIdAndCaption> pickingSlotIdAndCaptions = getPickingSlotIdAndCaptions();
		final QRCodePDFResource pdf = pickingSlotBL.createQRCodesPDF(pickingSlotIdAndCaptions);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());
		return MSG_OK;
	}

	private Set<PickingSlotIdAndCaption> getPickingSlotIdAndCaptions()
	{
		return pickingSlotId != null
				? ImmutableSet.of(pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotId))
				: pickingSlotBL.getPickingSlotIdAndCaptions(PickingSlotQuery.ALL);
	}
}
