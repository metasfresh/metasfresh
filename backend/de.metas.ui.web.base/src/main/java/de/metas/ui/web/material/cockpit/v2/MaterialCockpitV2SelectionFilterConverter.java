package de.metas.ui.web.material.cockpit.v2;

import de.metas.process.PInstanceId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;

public final class MaterialCockpitV2SelectionFilterConverter implements SqlDocumentFilterConverter
{
	private static final String FILTER_ID = "MaterialCockpitSelection";
	private static final String PARAM_AD_PInstance_ID = "AD_PInstance_ID";

	public static DocumentFilter createSelectionFilter(@NonNull final PInstanceId selectionId)
	{
		return DocumentFilter.builder()
				.setFilterId(FILTER_ID)
				.addParameter(DocumentFilterParam.ofNameEqualsValue(PARAM_AD_PInstance_ID, selectionId.getRepoId()))
				.build();
	}

	private static final String WHERE_IN_T_SELECTION =
			"(QtyDemand_QtySupply_V_ID IN (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID=?))";

	private final SqlDocumentFilterConverter delegate;

	MaterialCockpitV2SelectionFilterConverter(@NonNull final SqlDocumentFilterConverter delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return true;
	}

	@Override
	public FilterSql getSql(
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOpts,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		if (FILTER_ID.equals(filter.getFilterId()))
		{
			final int adPInstanceId = filter.getParameterValueAsInt(PARAM_AD_PInstance_ID, -1);
			return FilterSql.ofWhereClause(WHERE_IN_T_SELECTION, adPInstanceId);
		}

		return delegate.getSql(filter, sqlOpts, context);
	}
}
