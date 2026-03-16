package de.metas.acct.account_info.hierarchy;

import com.google.common.collect.ImmutableList;
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

class AccountHierarchyViewData implements IRowsData<AccountHierarchyRow>
{
	static AccountHierarchyViewData cast(final IRowsData<AccountHierarchyRow> rowsData)
	{
		return (AccountHierarchyViewData)rowsData;
	}

	@NonNull private final AccountHierarchyViewDataService viewDataService;
	@Getter @Nullable private DocumentFilter filter;
	@NonNull private final SynchronizedRowsIndexHolder<AccountHierarchyRow> rowsHolder = SynchronizedRowsIndexHolder.empty();

	@Builder
	private AccountHierarchyViewData(
			@NonNull final AccountHierarchyViewDataService viewDataService,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.filter = filter;
		loadRows();
	}

	@Override
	public Map<DocumentId, AccountHierarchyRow> getDocumentId2TopLevelRows()
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
		loadRows();
	}

	private void loadRows()
	{
		final ImmutableList<AccountHierarchyRow> rows = viewDataService.loadRows(filter);
		rowsHolder.setRows(rows);
	}
}
