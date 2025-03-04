package de.metas.resource.process;

import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ResourceId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.compiere.SpringContextHolder;

import java.util.Objects;
import java.util.Set;

public class S_Resource_PrintQRCodes extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final ResourceQRCodePrintService resourceQRCodePrintService = SpringContextHolder.instance.getBean(ResourceQRCodePrintService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ImmutableSet<ResourceId> resourceIds = getSelectedResourceIds();
		final QRCodePDFResource pdf = resourceQRCodePrintService.createPDF(resourceIds);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private ImmutableSet<ResourceId> getSelectedResourceIds()
	{
		final IView view = getView();

		return getSelectedRowIdsAsSet()
				.stream()
				.map(view::getTableRecordReferenceOrNull)
				.filter(Objects::nonNull)
				.map(recordRef -> ResourceId.ofRepoId(recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private Set<DocumentId> getSelectedRowIdsAsSet()
	{
		final IView view = getView();
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		final Set<DocumentId> rowIdsEffective;
		if (rowIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (rowIds.isAll())
		{
			rowIdsEffective = view.streamByIds(DocumentIdsSelection.ALL)
					.map(IViewRow::getId)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			rowIdsEffective = rowIds.toSet();
		}
		return rowIdsEffective;
	}
}
