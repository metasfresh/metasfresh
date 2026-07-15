package de.metas.ui.web.view;

import de.metas.ui.web.material.stockperweek.StockPerWeekSelectionFactory;
import de.metas.ui.web.material.stockperweek.StockPerWeekSelectionFactory.AppliedFilter;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Data repository for the standalone "Bestand pro Woche" window (542159).
 * <p>
 * The row selection is built by {@link StockPerWeekSelectionFactory} from
 * {@code MD_Stock_PerWeek_fn(product, warehouse)}. The default page render joins the persisted selection back
 * to the fully materialized {@code MD_Stock_PerWeek_V} on its MD5-hash PK — which the planner cannot push
 * down, forcing a re-materialization of the entire (~785k row) view for every page. This repository instead
 * renders the page from the <b>same parameterized function</b>, aliased with the view's table name so the
 * generated column/display/key expressions (and therefore the output) are byte-identical to the view render,
 * while the product/warehouse filter reaches the indexed base {@code MD_Candidate} scan.
 * <p>
 * The applied product/warehouse are read back from the selection rows that the selection factory persisted
 * (IntKey2 / IntKey3). No other window is affected: only window 542159 is wired to this repository (see
 * {@link StockPerWeekViewDataRepositoryFactory}).
 * <p>
 * Note: {@code retrieveById} (single-row zoom/refresh) is intentionally left on the standard view-based path.
 * It is <b>not yet</b> re-routed through the function — not because it is impossible: it does receive
 * {@code viewId} (the same handle {@link #buildSelectByPage} uses via
 * {@link StockPerWeekSelectionFactory#readAppliedFilter}), so the same re-parameterization would work here
 * too. It is simply a known, out-of-scope perf residual for a single-row refresh/zoom (not the paged-load
 * hot path this change targets), tracked as a follow-up.
 */
public class StockPerWeekViewDataRepository extends SqlViewDataRepository
{
	private static final String FUNCTION_SOURCE_RELATION_SQL = "MD_Stock_PerWeek_fn(?,?)";

	StockPerWeekViewDataRepository(
			@NonNull final SqlViewBinding sqlViewBinding,
			@NonNull final ViewRowIdsOrderedSelectionFactory selectionFactory)
	{
		super(sqlViewBinding, selectionFactory);
	}

	@Override
	@Nullable
	protected SqlAndParams buildSelectByPage(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final int firstRow,
			final int pageLength)
	{
		final AppliedFilter appliedFilter = StockPerWeekSelectionFactory.readAppliedFilter(viewId.getViewId());
		if (appliedFilter == null)
		{
			// Empty selection (e.g. window opened without a product filter) => empty page.
			return null;
		}

		return getSqlViewBinding().getSqlViewSelect().selectByPageFromSourceRelation(
				viewEvalCtx,
				FUNCTION_SOURCE_RELATION_SQL,
				appliedFilter.toFunctionParams(),
				viewId.getViewId(),
				firstRow,
				pageLength);
	}

	@Override
	@Nullable
	protected SqlAndParams buildSelectRowIdsByPage(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final int firstRow,
			final int pageLength)
	{
		// Reuse the function-sourced page SQL; the row-ids loop reads only the key column, which is present.
		// The extra projected columns are negligible — the cost is the single function evaluation, which is
		// identical whether we project all columns or only the key.
		return buildSelectByPage(viewEvalCtx, viewId, firstRow, pageLength);
	}
}
