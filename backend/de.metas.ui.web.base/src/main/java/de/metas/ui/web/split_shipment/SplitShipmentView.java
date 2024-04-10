package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.util.lang.IAutoCloseable;
import org.jetbrains.annotations.Nullable;

public class SplitShipmentView extends AbstractCustomView<SplitShipmentRow> implements IEditableView
{
	private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	public static SplitShipmentView cast(final IView view) {return (SplitShipmentView)view;}

	@Builder
	private SplitShipmentView(
			@NonNull final ViewId viewId,
			@NonNull final SplitShipmentRows rows,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, TranslatableStrings.empty(), rows, NullDocumentFilterDescriptorsProvider.instance);
		this.relatedProcesses = relatedProcesses;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	public LookupValuesPage getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query) {throw new UnsupportedOperationException();}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName) {throw new UnsupportedOperationException();}

	@Override
	protected SplitShipmentRows getRowsData() {return SplitShipmentRows.cast(super.getRowsData());}

	@Override
	public ImmutableList<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() {return relatedProcesses;}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}

	public void deleteRowsByIds(final DocumentIdsSelection rowIds)
	{
		try (final IAutoCloseable ignored = ViewChangesCollector.currentOrNewThreadLocalCollector(getViewId()))
		{
			getRowsData().deleteByIds(rowIds);
		}
	}

	public ShipmentScheduleId getShipmentScheduleId() { return getRowsData().getShipmentScheduleId();}

	public boolean hasRowsToProcess() {return getRowsData().hasRowsToProcess();}

}
