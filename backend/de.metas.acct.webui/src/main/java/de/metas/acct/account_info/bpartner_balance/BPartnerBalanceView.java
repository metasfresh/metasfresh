package de.metas.acct.account_info.bpartner_balance;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

public class BPartnerBalanceView extends AbstractCustomView<BPartnerBalanceRow>
{
	public static BPartnerBalanceView cast(IView view) { return (BPartnerBalanceView)view; }

	@Builder
	private BPartnerBalanceView(
			@NonNull final ViewId viewId,
			@NonNull final BPartnerBalanceViewData rowsData,
			@NonNull final DocumentFilterDescriptor filterDescriptor)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) { return null; }

	@Override
	public DocumentFilterList getFilters()
	{
		return DocumentFilterList.ofNullable(getRowsDataCasted().getFilter());
	}

	private BPartnerBalanceViewData getRowsDataCasted()
	{
		return BPartnerBalanceViewData.cast(super.getRowsData());
	}
}
