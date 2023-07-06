package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
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

public class OIViewData implements IRowsData<OIRow>
{
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
}
