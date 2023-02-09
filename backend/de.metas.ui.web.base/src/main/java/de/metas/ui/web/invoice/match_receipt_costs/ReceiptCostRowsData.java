package de.metas.ui.web.invoice.match_receipt_costs;

import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.ImmutableRowsIndex;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

public class ReceiptCostRowsData implements IRowsData<ReceiptCostRow>
{
	private final ImmutableRowsIndex<ReceiptCostRow> index;

	@Builder
	private ReceiptCostRowsData(
			@NonNull final List<ReceiptCostRow> rows)
	{
		this.index = ImmutableRowsIndex.of(rows);
	}

	@Override
	public Map<DocumentId, ReceiptCostRow> getDocumentId2TopLevelRows()
	{
		return index.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{

	}
}
