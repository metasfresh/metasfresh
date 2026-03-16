package de.metas.acct.account_info.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class AccountHierarchyView extends AbstractCustomView<AccountHierarchyRow>
{
	public static AccountHierarchyView cast(IView view) { return (AccountHierarchyView)view; }

	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	@Builder
	private AccountHierarchyView(
			@NonNull final ViewId viewId,
			@NonNull final AccountHierarchyViewData rowsData,
			@NonNull final DocumentFilterDescriptor filterDescriptor,
			@NonNull @Singular final ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
		this.relatedProcesses = relatedProcesses;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) { return null; }

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() { return relatedProcesses; }

	@Override
	public DocumentFilterList getFilters()
	{
		return DocumentFilterList.ofNullable(getRowsDataCasted().getFilter());
	}

	private AccountHierarchyViewData getRowsDataCasted()
	{
		return AccountHierarchyViewData.cast(super.getRowsData());
	}
}
