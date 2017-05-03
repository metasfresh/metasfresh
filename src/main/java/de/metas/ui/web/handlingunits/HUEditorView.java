package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.ProcessInstanceResult.SelectViewRowsAction;
import de.metas.ui.web.process.descriptor.ProcessLayout.ProcessLayoutType;
import de.metas.ui.web.process.view.ViewAction;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.process.view.ViewActionParam;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
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

public class HUEditorView implements IView
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static HUEditorView cast(final IView view)
	{
		return (HUEditorView)view;
	}

	private final ViewId parentViewId;

	private final ViewId viewId;

	private final Set<DocumentPath> referencingDocumentPaths;

	private final ViewActionDescriptorsList actions;
	private final HUEditorViewBuffer rowsBuffer;
	private final HUEditorRowAttributesProvider huAttributesProvider;

	private HUEditorView(final Builder builder)
	{
		parentViewId = builder.getParentViewId();

		this.huAttributesProvider = HUEditorRowAttributesProvider.builder()
				//.readonly(isHighVolume)
				.build();

		final HUEditorViewRepository huEditorRepo = HUEditorViewRepository.builder()
				.windowId(builder.getWindowId())
				.referencingTableName(builder.getReferencingTableName())
				.attributesProvider(huAttributesProvider)
				.build();

//		final boolean isHighVolume = false; // TODO: high volume is work in progress
//		if (isHighVolume)
//		{
//			final HUEditorViewBuffer_HighVolume rowsBuffer = new HUEditorViewBuffer_HighVolume(builder.getWindowId(), huEditorRepo);
//			this.rowsBuffer = rowsBuffer;
//			this.viewId = rowsBuffer.getViewId();
//		}
//		else
		{
			this.rowsBuffer = new HUEditorViewBuffer_FullyCached(huEditorRepo, builder.getHUIds());
			this.viewId = ViewId.random(builder.getWindowId());
		}

		referencingDocumentPaths = builder.getReferencingDocumentPaths();

		this.actions = builder.actions;
	}

	@Override
	public ViewId getParentViewId()
	{
		return parentViewId;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public String getTableName()
	{
		return I_M_HU.Table_Name;
	}

	@Override
	public long size()
	{
		return rowsBuffer.size();
	}

	@Override
	public void close()
	{
		invalidateAllNoNotify();
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
		final List<HUEditorRow> page = rowsBuffer
				.streamPage(firstRow, pageLength, orderBys)
				.collect(GuavaCollectors.toImmutableList());

		return ViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, page);
	}

	@Override
	public ViewActionDescriptorsList getActions()
	{
		return actions;
	}

	@Override
	public HUEditorRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return rowsBuffer.getById(rowId);
	}

	@Override
	public List<HUEditorRow> getByIds(final Set<DocumentId> rowId)
	{
		return streamByIds(rowId).collect(ImmutableList.toImmutableList());
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> rowIds)
	{
		Check.assumeNotEmpty(rowIds, "rowIds is not empty");
		// NOTE: ignoring non integer IDs because those might of HUStorage records, about which we don't care
		final Set<Integer> huIds = DocumentId.toIntSetIgnoringNonInts(rowIds);

		return I_M_HU.COLUMNNAME_M_HU_ID + " IN " + DB.buildSqlList(huIds);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return referencingDocumentPaths;
	}

	public void invalidateAll()
	{
		invalidateAllNoNotify();

		ViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void invalidateAllNoNotify()
	{
		huAttributesProvider.invalidateAll();
		rowsBuffer.invalidateAll();
	}

	public void addHUsAndInvalidate(final Collection<I_M_HU> husToAdd)
	{
		if (rowsBuffer.addHUIds(extractHUIds(husToAdd)))
		{
			invalidateAll();
		}
	}

	public void addHUAndInvalidate(final I_M_HU hu)
	{
		if (hu == null || hu.getM_HU_ID() <= 0)
		{
			return;
		}

		if (rowsBuffer.addHUIds(ImmutableSet.of(hu.getM_HU_ID())))
		{
			invalidateAll();
		}
	}

	public void removesHUsAndInvalidate(final Collection<I_M_HU> husToRemove)
	{
		if (rowsBuffer.removeHUIds(extractHUIds(husToRemove)))
		{
			invalidateAll();
		}
	}

	private static final Set<Integer> extractHUIds(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return ImmutableSet.of();
		}

		return hus.stream().filter(hu -> hu != null).map(I_M_HU::getM_HU_ID).collect(Collectors.toSet());
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO: notifyRecordsChanged:
		// get M_HU_IDs from recordRefs,
		// find the top level records from this view which contain our HUs
		// invalidate those top levels only

		final Set<Integer> huIdsToCheck = recordRefs.stream()
				.filter(recordRef -> I_M_HU.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> recordRef.getRecord_ID())
				.collect(ImmutableSet.toImmutableSet());

		final boolean containsSomeRecords = rowsBuffer.containsAnyOfHUIds(huIdsToCheck);
		if (!containsSomeRecords)
		{
			return;
		}

		invalidateAll();
	}

	@Override
	public Stream<HUEditorRow> streamByIds(final Collection<DocumentId> rowIds)
	{
		return rowsBuffer.streamByIdsExcludingIncludedRows(rowIds);
	}

	/** @return top level rows and included rows recursive stream */
	public Stream<HUEditorRow> streamAllRecursive()
	{
		return rowsBuffer.streamAllRecursive();
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> rowIds, final Class<T> modelClass)
	{
		final Set<Integer> huIds = streamByIds(rowIds)
				.filter(HUEditorRow::isPureHU)
				.map(HUEditorRow::getM_HU_ID)
				.collect(GuavaCollectors.toImmutableSet());
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_HU> hus = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);

		return InterfaceWrapperHelper.createList(hus, modelClass);
	}

	@ViewAction(caption = "Barcode", layoutType = ProcessLayoutType.SingleOverlayField)
	public SelectViewRowsAction actionSelectHUsByBarcode( //
			@ViewActionParam(caption = "Barcode", widgetType = DocumentFieldWidgetType.Text) final String barcode //
	// , final Set<DocumentId> selectedRowIds //
			)
	{
		// Search for matching rowIds by barcode
		final Set<DocumentId> matchingRowIds = rowsBuffer.getRowIdsMatchingBarcode(barcode);
		if (matchingRowIds.isEmpty())
		{
			throw new AdempiereException("Nothing found for '" + barcode + "'");
		}

		// Join matching rowIds with currently selected ones
		final Set<DocumentId> rowIds = ImmutableSet.<DocumentId> builder()
				.addAll(matchingRowIds)
				// .addAll(selectedDocumentIds) // don't keep the previously selected ones (see https://github.com/metasfresh/metasfresh-webui-api/issues/313 )
				.build();

		return SelectViewRowsAction.builder()
				.viewId(getViewId())
				.rowIds(rowIds)
				.build();

	}

	//
	//
	//

	public static final class Builder
	{
		private ViewId parentViewId;
		private WindowId windowId;

		private String referencingTableName;
		private Set<DocumentPath> referencingDocumentPaths;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
		private Collection<Integer> huIds;

		private Builder()
		{
			super();
		}

		public HUEditorView build()
		{
			return new HUEditorView(this);
		}

		public Builder setParentViewId(final ViewId parentViewId)
		{
			this.parentViewId = parentViewId;
			return this;
		}

		private ViewId getParentViewId()
		{
			return parentViewId;
		}

		public Builder setWindowId(WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		private WindowId getWindowId()
		{
			return windowId;
		}

		public Builder setHUIds(final Collection<Integer> huIds)
		{
			this.huIds = huIds;
			return this;
		}

		private Collection<Integer> getHUIds()
		{
			if (huIds == null || huIds.isEmpty())
			{
				return ImmutableSet.of();
			}
			return huIds;
		}

		public Builder setReferencingDocumentPaths(final String referencingTableName, final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingTableName = referencingTableName;
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private String getReferencingTableName()
		{
			return referencingTableName;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		public Builder setActions(@NonNull final ViewActionDescriptorsList actions)
		{
			this.actions = actions;
			return this;
		}
	}
}
