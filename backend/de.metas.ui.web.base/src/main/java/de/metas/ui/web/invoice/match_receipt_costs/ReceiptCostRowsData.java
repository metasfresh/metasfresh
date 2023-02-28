package de.metas.ui.web.invoice.match_receipt_costs;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Map;

public class ReceiptCostRowsData implements IRowsData<ReceiptCostRow>
{
	@NonNull private final ReceiptCostRowsRepository repository;
	@Getter @Nullable private final DocumentFilter filter;
	@NonNull private final SynchronizedRowsIndexHolder<ReceiptCostRow> rowsHolder;

	@Builder
	private ReceiptCostRowsData(
			@NonNull final ReceiptCostRowsRepository repository,
			@Nullable final DocumentFilter filter)
	{
		this.repository = repository;
		this.filter = filter;
		this.rowsHolder = SynchronizedRowsIndexHolder.of(repository.retrieveRows(filter));
	}

	@Override
	public Map<DocumentId, ReceiptCostRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
		rowsHolder.setRows(repository.retrieveRows(filter));
	}
}
