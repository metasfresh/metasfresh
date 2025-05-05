package de.metas.ui.web.order.sales.process;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/**
 * Asks frontend to create a new Sales Order and to set the BPartner field as current selected partner from this view.
 * <p>
 * NOTE: if you don't find an AD_Process record in database it's because this process is used only by some customers,
 * using their terminology, so we added only there.
 * Original branch is `hard_encoded_uat`.
 */
public class C_BPartner_NewSalesOrderAction extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	static final int SORT_NO = -100000; // show it first

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ExplainedOptional<BPartnerId> partnerId = getBPartnerId();
		if (!partnerId.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(partnerId.getExplanation());
		}

		return ProcessPreconditionsResolution.accept().withSortNo(SORT_NO);
	}

	@Override
	protected String doIt()
	{
		final BPartnerId bpartnerId = getBPartnerId().get();
		getResult().setWebuiNewRecord(BPartnerNewSalesOrderUtils.openNewSalesOrderWindowAndSetBPartner(bpartnerId));
		return MSG_OK;
	}

	public ExplainedOptional<BPartnerId> getBPartnerId()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isSingleDocumentId())
		{
			return ExplainedOptional.emptyBecause("no single selection");
		}

		final DocumentId singleDocumentId = selectedRowIds.getSingleDocumentId();
		final BPartnerId bpartnerId = singleDocumentId.toId(BPartnerId::ofRepoIdOrNull);
		if (bpartnerId == null)
		{
			return ExplainedOptional.emptyBecause("Invalid C_BPartner_ID: " + singleDocumentId);
		}

		return ExplainedOptional.of(bpartnerId);
	}
}
