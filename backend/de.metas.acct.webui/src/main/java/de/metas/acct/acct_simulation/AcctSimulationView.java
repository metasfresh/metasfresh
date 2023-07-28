package de.metas.acct.acct_simulation;

import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

public class AcctSimulationView extends AbstractCustomView<AcctRow> implements IEditableView
{
	public static AcctSimulationView cast(final IView view) {return (AcctSimulationView)view;}

	@Builder
	private AcctSimulationView(
			final @NonNull ViewId viewId,
			final @NonNull AcctSimulationViewData rowsData)
	{
		super(viewId, null, rowsData, NullDocumentFilterDescriptorsProvider.instance);
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	protected AcctSimulationViewData getRowsData()
	{
		return AcctSimulationViewData.cast(super.getRowsData());
	}

	public TableRecordReference getDocRecordRef() {return getRowsData().getDocRecordRef();}
}
