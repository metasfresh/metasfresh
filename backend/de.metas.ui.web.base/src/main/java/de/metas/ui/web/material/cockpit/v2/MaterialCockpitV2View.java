package de.metas.ui.web.material.cockpit.v2;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

public class MaterialCockpitV2View extends AbstractCustomView<MaterialCockpitV2Row>
{
	private final DocumentFilterList filters;
	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private MaterialCockpitV2View(
			@NonNull final ViewId viewId,
			@NonNull final ITranslatableString description,
			@NonNull final IRowsData<MaterialCockpitV2Row> rowsData,
			@NonNull final DocumentFilterList filters,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptors,
			@Singular final List<RelatedProcessDescriptor> relatedProcessDescriptors)
	{
		super(viewId, description, rowsData, filterDescriptors);
		this.filters = filters;
		this.relatedProcessDescriptors = ImmutableList.copyOf(relatedProcessDescriptors);
	}

	public static MaterialCockpitV2View cast(final IView view)
	{
		return (MaterialCockpitV2View)view;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return MaterialCockpitV2Util.VIEW_TABLE_NAME;
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return filters;
	}

	@Override
	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return DocumentQueryOrderByList.ofList(
				ImmutableList.of(
						DocumentQueryOrderBy.byFieldName("ProductValue"),
						DocumentQueryOrderBy.byFieldName("M_Warehouse_ID")));
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}
}
