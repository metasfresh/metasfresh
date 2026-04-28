package de.metas.picking.process;

import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

public class M_PickingSlot_PrintSingleQRCode extends JavaProcess implements IProcessDefaultParametersProvider
{
	private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);

	private static final String PARAM_RenderedQRCode = "RenderedQRCode";

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (parameter.getColumnName().equals(PARAM_RenderedQRCode))
		{
			return getQRCode().toGlobalQRCodeJsonString();
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final PickingSlotQRCode qrCode = getQRCode();
		pickingSlotBL.createPDF(qrCode);

		final QRCodePDFResource pdf = pickingSlotBL.createPDF(qrCode);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private PickingSlotQRCode getQRCode()
	{
		final PickingSlotId pickingSlotId = getPickingSlotId();
		return pickingSlotBL.getPickingSlotQRCode(pickingSlotId);
	}

	private PickingSlotId getPickingSlotId()
	{
		return getRecordIdAssumingTableName(I_M_PickingSlot.Table_Name, PickingSlotId::ofRepoId);
	}

}
