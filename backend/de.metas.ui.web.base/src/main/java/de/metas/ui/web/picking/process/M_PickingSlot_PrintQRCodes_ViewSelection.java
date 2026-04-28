package de.metas.ui.web.picking.process;

import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;

import javax.annotation.Nullable;
import java.util.Set;

public class M_PickingSlot_PrintQRCodes_ViewSelection extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);

	private static final String PARAM_RenderedQRCode = "RenderedQRCode";

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept()
				.withCaptionMapper(caption -> TranslatableStrings.builder()
						.append(caption).append(" (").append(selectedRowIds.getSizeAsTranslatableString()).append(")")
						.build());
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();
		if (PARAM_RenderedQRCode.equals(parameterName))
		{
			final PickingSlotIdAndCaption pickingSlot = getSinglePickingSlotOrNull();
			return pickingSlot != null
					? PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot).toGlobalQRCodeJsonString()
					: null;
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final Set<PickingSlotIdAndCaption> pickingSlots = getPickingSlots();
		final QRCodePDFResource pdf = pickingSlotBL.createQRCodesPDF(pickingSlots);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private Set<PickingSlotIdAndCaption> getPickingSlots()
	{
		final ImmutableSet<PickingSlotId> pickingSlotIds = streamSelectedRows()
				.map(row -> row.getId().toId(PickingSlotId::ofRepoId))
				.collect(ImmutableSet.toImmutableSet());

		return pickingSlotBL.getPickingSlotIdAndCaptions(pickingSlotIds);
	}

	@Nullable
	private PickingSlotIdAndCaption getSinglePickingSlotOrNull()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isSingleDocumentId()) {return null;}

		final PickingSlotId pickingSlotId = selectedRowIds.getSingleDocumentId().toId(PickingSlotId::ofRepoId);
		return pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotId);
	}
}
