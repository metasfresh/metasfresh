package de.metas.ui.web.material.cockpit.v2;

import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.DefaultDocumentDescriptorFactory;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ViewFactory(windowId = MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2_String,
		viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class MaterialCockpitV2ViewFactory implements IViewFactory
{
	private final MaterialCockpitV2Filters materialCockpitV2Filters;

	public MaterialCockpitV2ViewFactory(
			@NonNull final MaterialCockpitV2Filters materialCockpitV2Filters,
			@NonNull final DefaultDocumentDescriptorFactory defaultDocumentDescriptorFactory)
	{
		this.materialCockpitV2Filters = materialCockpitV2Filters;
		defaultDocumentDescriptorFactory.addUnsupportedWindowId(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2);
	}

	@Override
	public MaterialCockpitV2View createView(@NonNull final CreateViewRequest request)
	{
		assertWindowId(request);

		final DocumentFilterDescriptorsProvider filterDescriptors = materialCockpitV2Filters.getFilterDescriptors();
		final DocumentFilterList filters = materialCockpitV2Filters.extractDocumentFilters(request);

		final MaterialCockpitV2RowsData rowsData = createRowsData(filters);

		return MaterialCockpitV2View.builder()
				.viewId(request.getViewId())
				.description(TranslatableStrings.empty())
				.filters(filters)
				.filterDescriptors(filterDescriptors)
				.rowsData(rowsData)
				.build();
	}

	private void assertWindowId(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		final WindowId windowId = viewId.getWindowId();

		Check.errorUnless(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2.equals(windowId),
				"Expected WindowId={}, but got {}; request={}",
				MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2, windowId, request);
	}

	private MaterialCockpitV2RowsData createRowsData(@NonNull final DocumentFilterList filters)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(MaterialCockpitV2Util.VIEW_TABLE_NAME).append(" v");

		final MaterialCockpitV2Filters.SqlWhereClause whereClause = materialCockpitV2Filters.toSqlWhereClause(filters);
		if (!whereClause.isEmpty())
		{
			sql.append(" WHERE ").append(whereClause.getSql());
			sqlParams.addAll(whereClause.getParams());
		}

		sql.append(" ORDER BY v.ProductValue, v.M_Warehouse_ID");

		return new MaterialCockpitV2RowsData(sql.toString(), sqlParams);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		Check.errorUnless(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2.equals(windowId),
				"Expected WindowId={}, but got {}",
				MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2, windowId);

		return ViewLayout.builder()
				.setWindowId(windowId)
				.setHasTreeSupport(false)
				.setAllowOpeningRowDetails(false)
				.addElementsFromViewRowClass(MaterialCockpitV2Row.class, viewDataType)
				.setFilters(materialCockpitV2Filters.getFilterDescriptors().getAll())
				.build();
	}
}
