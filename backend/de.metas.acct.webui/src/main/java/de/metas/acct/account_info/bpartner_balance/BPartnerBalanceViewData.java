package de.metas.acct.account_info.bpartner_balance;

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

class BPartnerBalanceViewData implements IRowsData<BPartnerBalanceRow>
{
	static BPartnerBalanceViewData cast(final IRowsData<BPartnerBalanceRow> rowsData)
	{
		return (BPartnerBalanceViewData)rowsData;
	}

	@NonNull private final BPartnerBalanceViewDataService viewDataService;
	@Getter @Nullable private DocumentFilter filter;
	@NonNull private final SynchronizedRowsIndexHolder<BPartnerBalanceRow> rowsHolder = SynchronizedRowsIndexHolder.empty();

	@Builder
	private BPartnerBalanceViewData(
			@NonNull final BPartnerBalanceViewDataService viewDataService,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.filter = filter;
		loadRows();
	}

	@Override
	public Map<DocumentId, BPartnerBalanceRow> getDocumentId2TopLevelRows()
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
		final ImmutableList<BPartnerBalanceRow> rows = viewDataService.loadRows(filter);
		rowsHolder.setRows(rows);
	}
}
