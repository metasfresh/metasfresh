package de.metas.acct.acct_simulation;

import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

public class AcctSimulationViewData implements IEditableRowsData<AcctRow>
{
	public static AcctSimulationViewData cast(final IRowsData<AcctRow> data) {return (AcctSimulationViewData)data;}

	private final AcctSimulationViewDataService dataService;
	@Getter private final @NonNull TableRecordReference docRecordRef;
	private final @NonNull ClientId clientId;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<AcctRow> rowsHolder = SynchronizedRowsIndexHolder.empty();

	@Builder
	private AcctSimulationViewData(
			final @NonNull AcctSimulationViewDataService dataService,
			final @NonNull TableRecordReference docRecordRef,
			final @NonNull ClientId clientId)
	{
		this.dataService = dataService;
		this.docRecordRef = docRecordRef;
		this.clientId = clientId;

		loadRows();
	}

	@Override
	public Map<DocumentId, AcctRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows();}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		loadRows();
	}

	private void loadRows()
	{
		rowsHolder.setRows(dataService.retrieveRows(docRecordRef, clientId));
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		rowsHolder.changeRowById(ctx.getRowId(), row -> row.withPatch(fieldChangeRequests));
	}

}
