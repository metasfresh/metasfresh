package de.metas.ui.web.picking;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Picking editor's view left-hand side view which lists one or more {@link PackageableRow} records to be picked.
 * <p>
 * Note that technically this view also contains the right-hand side {@link PickingSlotView}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PackageableView implements IView
{
	private final PickingCandidateCommand pickingCandidateCommand;

	public static PackageableView cast(final IView view)
	{
		return (PackageableView)view;
	}

	private final ViewId viewId;
	private final ITranslatableString description;
	private final ExtendedMemorizingSupplier<Map<DocumentId, PackageableRow>> rowsSupplier;

	private final ConcurrentHashMap<DocumentId, PickingSlotView> pickingSlotsViewByRowId = new ConcurrentHashMap<>();

	@Builder
	private PackageableView(@NonNull final ViewId viewId,
			@NonNull final ITranslatableString description,
			@NonNull final Supplier<List<PackageableRow>> rowsSupplier,
			@NonNull final PickingCandidateCommand pickingCandidateCommand)
	{
		this.viewId = viewId;
		this.description = description != null ? description : ITranslatableString.empty();
		this.rowsSupplier = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rowsSupplier.get(), PackageableRow::getId));
		this.pickingCandidateCommand = pickingCandidateCommand;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public ITranslatableString getDescription()
	{
		return description;
	}

	@Override
	public JSONViewDataType getViewType()
	{
		return JSONViewDataType.grid;
	}

	@Override
	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getTableName()
	{
		return I_M_Packageable_V.Table_Name;
	}

	@Override
	public ViewId getParentViewId()
	{
		return null;
	}

	@Override
	public DocumentId getParentRowId()
	{
		return null;
	}

	private final Map<DocumentId, PackageableRow> getRows()
	{
		return rowsSupplier.get();
	}

	@Override
	public long size()
	{
		return getRows().size();
	}

	@Override
	public void close()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		
		final Properties ctx = Env.getCtx();
		final boolean closeCandidatesNow = sysConfigBL.getBooleanValue("WEBUI_Picking.Close_PickingCandidatesOnWindowClose", false, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		if (!closeCandidatesNow)
		{
			return; // nothing to do.
		}

		final List<Integer> shipmentScheduleIds = getRows()
				.values().stream()
				.map(row -> row.getShipmentScheduleId())
				.collect(Collectors.toList());

		pickingCandidateCommand.setCandidatesClosed(shipmentScheduleIds);
	}

	@Override
	public void invalidateAll()
	{
		rowsSupplier.forget();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	@Override
	public int getQueryLimit()
	{
		return -1;
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return false;
	}

	@Override
	public ViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<PackageableRow> pageRows = getRows().values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : 30)
				.collect(ImmutableList.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, pageRows);
	}

	@Override
	public PackageableRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		final PackageableRow row = getRows().get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Just throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns an empty list.
	 */
	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	/**
	 * Just returns {@code null}.
	 */
	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		return null;
	}

	/**
	 * Returns {@code false}.
	 */
	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final DocumentIdsSelection rowIds, final Class<T> modelClass)
	{
		final Set<Integer> shipmentScheduleIds = rowIds.toIntSet();
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_Packageable_V> packables = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.list(I_M_Packageable_V.class);
		return InterfaceWrapperHelper.createList(packables, modelClass);
	}

	/**
	 * Also supports {@link DocumentIdsSelection#ALL}, because there won't be too many packageable lines at one time.
	 */
	@Override
	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRows().values().stream();
		}
		return rowIds.stream().map(this::getById);
	}

	/**
	 * Does nothing
	 */
	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
	}

	/* package */ void setPickingSlotView(@NonNull final DocumentId rowId, @NonNull final PickingSlotView pickingSlotView)
	{
		pickingSlotsViewByRowId.put(rowId, pickingSlotView);
	}

	/* package */ void removePickingSlotView(@NonNull final DocumentId rowId)
	{
		pickingSlotsViewByRowId.remove(rowId);
	}

	/* package */ PickingSlotView getPickingSlotViewOrNull(@NonNull final DocumentId rowId)
	{
		return pickingSlotsViewByRowId.get(rowId);
	}

	/* package */ PickingSlotView computePickingSlotViewIfAbsent(@NonNull final DocumentId rowId, @NonNull final Supplier<PickingSlotView> pickingSlotViewFactory)
	{
		return pickingSlotsViewByRowId.computeIfAbsent(rowId, id -> pickingSlotViewFactory.get());
	}
}
