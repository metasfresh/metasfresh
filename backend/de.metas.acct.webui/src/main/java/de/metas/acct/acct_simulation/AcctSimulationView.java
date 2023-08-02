package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
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

import javax.annotation.Nullable;
import java.util.List;

public class AcctSimulationView extends AbstractCustomView<AcctRow> implements IEditableView
{
	private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	public static AcctSimulationView cast(final IView view) {return (AcctSimulationView)view;}

	@Builder
	private AcctSimulationView(
			final @NonNull AcctSimulationViewData rowsData,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(rowsData.getViewId(), null, rowsData, NullDocumentFilterDescriptorsProvider.instance);
		this.relatedProcesses = relatedProcesses;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() {return relatedProcesses;}

	@Override
	protected AcctSimulationViewData getRowsData()
	{
		return AcctSimulationViewData.cast(super.getRowsData());
	}

	public AcctSimulationDocInfo getDocInfo() {return getRowsData().getDocInfo();}

	public LookupValuesPage getFieldTypeahead(RowEditingContext ctx, String fieldName, String query) {return getById(ctx.getRowId()).getFieldTypeahead(fieldName, query);}

	public LookupValuesList getFieldDropdown(RowEditingContext ctx, String fieldName) {return getById(ctx.getRowId()).getFieldDropdown(fieldName);}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}

	private void fireViewFullyChanged() {ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(this);}

	public void addNewRow()
	{
		getRowsData().addNewRow();
		fireViewFullyChanged();
	}

	public void removeRowsById(@NonNull final DocumentIdsSelection rowIds)
	{
		getRowsData().removeRowsById(rowIds);
		fireViewFullyChanged();
	}

	public void updateSimulation() {
		getRowsData().updateSimulation();
		fireViewFullyChanged();
	}

	public void save()
	{
		getRowsData().save();
	}

}
