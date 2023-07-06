package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.ui.web.document.filter.DocumentFilter;
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
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OIViewData implements IEditableRowsData<OIRow>
{
	public static OIViewData cast(final IRowsData<OIRow> rowsData)
	{
		return (OIViewData)rowsData;
	}

	//
	// services
	@NonNull private final OIViewDataService viewDataService;

	//
	// parameters
	@NonNull private final AcctSchemaId acctSchemaId;
	@NonNull private final PostingType postingType;
	@Getter @Nullable private final DocumentFilter filter;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<OIRow> rowsHolder;

	@Builder
	private OIViewData(
			@NonNull final OIViewDataService viewDataService,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingType postingType,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.acctSchemaId = acctSchemaId;
		this.postingType = postingType;
		this.filter = filter;

		this.rowsHolder = SynchronizedRowsIndexHolder.of(viewDataService.retrieveRows(acctSchemaId, postingType, filter));
	}

	@Override
	public Map<DocumentId, OIRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows();}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll() {rowsHolder.setRows(viewDataService.retrieveRows(acctSchemaId, postingType, filter));}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		rowsHolder.changeRowById(ctx.getRowId(), row -> applyChanges(row, fieldChangeRequests));
	}

	private static OIRow applyChanges(@NonNull final OIRow row, @NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final OIRow.OIRowBuilder changedRowBuilder = row.toBuilder();

		for (final JSONDocumentChangedEvent event : fieldChangeRequests)
		{
			event.assertReplaceOperation();

			switch (event.getPath())
			{
				case OIRow.FIELD_Selected:
				{
					changedRowBuilder.selected(event.getValueAsBoolean(false));
					break;
				}
				case OIRow.FIELD_OpenAmountOverrde:
				{
					BigDecimal value = event.getValueAsBigDecimal(null);
					if (value != null && value.signum() == 0)
					{
						value = null;
					}
					changedRowBuilder.openAmountOverride(value);
					break;
				}
			}
		}

		return changedRowBuilder.build();
	}

	public void markRowsAsSelected(final DocumentIdsSelection rowIds)
	{
		rowsHolder.changeRowsByIds(rowIds, row -> row.withSelected(true));
	}

	public boolean hasSelectedRows()
	{
		return rowsHolder.anyMatch(OIRow::isSelected);
	}
}
