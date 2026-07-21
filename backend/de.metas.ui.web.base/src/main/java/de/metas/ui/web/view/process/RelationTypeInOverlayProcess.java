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

//please keep package in sync with de.metas.process.model.interceptor.AD_Process.RELATION_TYPE_IN_OVERLAY_PROCESS_CLASSNAME
package de.metas.ui.web.view.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.relation_type.RelationTypeId;
import de.metas.document.references.related_documents.relation_type.RelationTypeRelatedDocumentsProvidersFactory;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessOpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewsRepository;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.MQuery;
import org.compiere.model.PO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static de.metas.ui.web.view.SqlViewFactory.MSG_NO_RELATED_DOCS_FOUND;

/**
 * Process implementation for opening related documents via an AD_RelationType.
 * <p>
 * The display mode is configured per AD_Process via {@code AD_Process.OpenTarget}
 * (see {@link ProcessOpenTarget}): modal overlay (default — historical behaviour
 * when the column is NULL) or new browser tab.
 * <p>
 * This process is automatically assigned when AD_Process.Type='RelationTypeInOverlay'.
 */
public class RelationTypeInOverlayProcess extends JavaProcess implements IProcessPrecondition
{
	private static final String UNION_FILTER_ID = "RelationTypeInOverlay-union";

	@NonNull private final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory;
	@NonNull private final IViewsRepository viewsRepo;

	public RelationTypeInOverlayProcess()
	{
		this.relationTypeProvidersFactory = SpringContextHolder.instance.getBean(RelationTypeRelatedDocumentsProvidersFactory.class);
		this.viewsRepo = SpringContextHolder.instance.getBean(ViewsRepository.class);
	}

	// Constructor for testing
	private RelationTypeInOverlayProcess(
			@NonNull final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory,
			@NonNull final IViewsRepository viewsRepo)
	{
		this.relationTypeProvidersFactory = relationTypeProvidersFactory;
		this.viewsRepo = viewsRepo;
	}

	@VisibleForTesting
	public static RelationTypeInOverlayProcess newInstanceForUnitTesting(
			@NonNull final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final de.metas.process.ProcessInfo processInfo,
			@NonNull final IZoomSource zoomSource)
	{
		final RelationTypeInOverlayProcess process = new RelationTypeInOverlayProcess(relationTypeProvidersFactory, viewsRepo)
		{
			@Override
			protected IZoomSource createZoomSource(@NonNull final TableRecordReference recordRef)
			{
				return zoomSource;
			}
		};
		process.init(processInfo);
		return process;
	}

	// Constructor for testing multi-selection: inject the selected record refs and a per-ref zoom source
	@VisibleForTesting
	public static RelationTypeInOverlayProcess newInstanceForUnitTesting(
			@NonNull final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final de.metas.process.ProcessInfo processInfo,
			@NonNull final List<TableRecordReference> selectedRecordRefs,
			@NonNull final Map<TableRecordReference, IZoomSource> zoomSourcesByRecordRef)
	{
		final RelationTypeInOverlayProcess process = new RelationTypeInOverlayProcess(relationTypeProvidersFactory, viewsRepo)
		{
			@Override
			protected List<TableRecordReference> getSelectedSourceRecordRefs()
			{
				return ImmutableList.copyOf(selectedRecordRefs);
			}

			@Override
			protected IZoomSource createZoomSource(@NonNull final TableRecordReference recordRef)
			{
				final IZoomSource zoomSource = zoomSourcesByRecordRef.get(recordRef);
				if (zoomSource == null)
				{
					// simulate an unloadable / concurrently-deleted source record (see the production createZoomSource)
					throw new AdempiereException("Cannot load source record: " + recordRef);
				}
				return zoomSource;
			}
		};
		process.init(processInfo);
		return process;
	}

	@Override
	protected String doIt()
	{
		final RelationTypeId relationTypeId = getRelationTypeId();
		final List<TableRecordReference> sourceRecordRefs = getSelectedSourceRecordRefs();
		if (sourceRecordRefs.isEmpty())
		{
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}

		final ViewId viewId = sourceRecordRefs.size() == 1
				? createSingleSourceView(sourceRecordRefs.get(0), relationTypeId)
				: createCombinedView(sourceRecordRefs, relationTypeId);

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder().viewId(viewId.getViewId()).target(getOpenTarget()).build());

		return MSG_OK;
	}

	private ViewId createSingleSourceView(@NonNull final TableRecordReference recordRef, @NonNull final RelationTypeId relationTypeId)
	{
		// Create zoom source from the current record.
		// A source record that cannot be loaded (e.g. a Purchase Cockpit demand row with no backing record,
		// or a concurrently-deleted row) must surface the friendly "no related documents" message instead of a raw 500.
		final IZoomSource zoomSource;
		try
		{
			zoomSource = createZoomSource(recordRef);
		}
		catch (final Exception ex)
		{
			addLog("Cannot resolve related documents for {}: {}", recordRef, ex.getLocalizedMessage());
			log.warn("Cannot resolve related documents for {}", recordRef, ex);
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}

		// Get the specific provider for this relation type and retrieve related documents
		final List<RelatedDocumentsCandidateGroup> relatedDocumentGroups = retrieveRelatedDocumentGroups(relationTypeId, zoomSource);

		if (relatedDocumentGroups.isEmpty())
		{
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}
		else if (relatedDocumentGroups.size() > 1)
		{
			addLog("RelationType {} returned {} groups; using only the first. RelationType may be misconfigured.",
					relationTypeId, relatedDocumentGroups.size());
		}

		final RelatedDocumentsCandidateGroup firstGroup = relatedDocumentGroups.get(0);
		return createView(recordRef, WindowId.of(firstGroup.getTargetWindowId())).getViewId();
	}

	/**
	 * Opens a single combined view showing the union of the related documents of all selected source records.
	 * <p>
	 * We cannot rely on {@code CreateViewRequest.referencingDocumentPaths} here: {@code SqlViewFactory} derives the
	 * related-documents sticky filter from {@code getSingleReferencingDocumentPathOrNull()} (i.e. only the first path),
	 * which would collapse the union to a single source. Instead we OR the per-source related-documents SQL where-clauses
	 * into one sticky filter.
	 */
	private ViewId createCombinedView(@NonNull final List<TableRecordReference> sourceRecordRefs, @NonNull final RelationTypeId relationTypeId)
	{
		AdWindowId targetWindowId = null;
		final List<String> whereClauses = new ArrayList<>();

		for (final TableRecordReference recordRef : sourceRecordRefs)
		{
			final List<RelatedDocumentsCandidateGroup> groups;
			try
			{
				final IZoomSource zoomSource = createZoomSource(recordRef);
				groups = retrieveRelatedDocumentGroups(relationTypeId, zoomSource);
			}
			catch (final Exception ex)
			{
				// A single unloadable/concurrently-deleted source must not abort the whole combined view;
				// skip it and keep the union of the remaining selected records.
				addLog("Skipping {} because its related documents could not be resolved: {}", recordRef, ex.getLocalizedMessage());
				log.warn("Skipping {} while building the combined related-documents view", recordRef, ex);
				continue;
			}

			for (final RelatedDocumentsCandidateGroup group : groups)
			{
				if (targetWindowId == null)
				{
					targetWindowId = group.getTargetWindowId();
				}
				else if (!Objects.equals(targetWindowId, group.getTargetWindowId()))
				{
					addLog("RelationType {} returned target window {} for {} but the combined view uses {}; ignoring that group.",
							relationTypeId, group.getTargetWindowId(), recordRef, targetWindowId);
					continue;
				}

				// A candidate whose MQuery has no where-clause would match the whole target table and cannot be OR'ed into the union; it is intentionally skipped below. Safe for the current relation types, whose candidates always carry a where-clause; revisit if reused elsewhere.
				for (final RelatedDocumentsCandidate candidate : group.getCandidates())
				{
					final MQuery query = candidate.getQuerySupplier().getQuery();
					final String whereClause = query != null ? query.getWhereClause(true) : null;
					if (!Check.isBlank(whereClause))
					{
						whereClauses.add("(" + whereClause + ")");
					}
				}
			}
		}

		// Note: whereClauses is only ever appended to after targetWindowId has been set, so non-empty implies non-null window
		if (whereClauses.isEmpty())
		{
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}

		final String combinedWhereClause = String.join(" OR ", whereClauses);
		final DocumentFilter unionFilter = DocumentFilter.builder()
				.filterId(UNION_FILTER_ID)
				.parameter(DocumentFilterParam.ofSqlWhereClause(true, combinedWhereClause))
				.build();

		return createCombinedFilterView(WindowId.of(targetWindowId), unionFilter).getViewId();
	}

	private IView createCombinedFilterView(@NonNull final WindowId targetWindowId, @NonNull final DocumentFilter unionFilter)
	{
		final RelatedDocumentsId relatedDocumentsId = RelatedDocumentsId.ofString("AD_RelationType_ID-" + getRelationTypeId().getRepoId());
		final CreateViewRequest request = CreateViewRequest.builder(targetWindowId, JSONViewDataType.grid)
				.setDocumentReferenceId(WebuiDocumentReferenceId.ofRelatedDocumentsId(relatedDocumentsId))
				.addStickyFilters(unionFilter)
				.setUseAutoFilters(true)
				.build();
		return viewsRepo.createView(request);
	}

	private List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentGroups(
			@NonNull final RelationTypeId relationTypeId,
			@NonNull final IZoomSource zoomSource)
	{
		return relationTypeProvidersFactory
				.findRelatedDocumentsProvider(relationTypeId)
				.map(docProvider -> docProvider.retrieveRelatedDocumentsCandidates(zoomSource, null))
				.orElse(Collections.emptyList());
	}

	/**
	 * Resolves the selected source record(s):
	 * <ul>
	 * <li>single-record context (incl. single-document window): the one record ref;
	 * <li>multi-row view selection: {@link de.metas.process.ProcessInfo} carries no single record but a selection where-clause,
	 *     which we run against the source table to get all selected record refs.
	 * </ul>
	 */
	protected List<TableRecordReference> getSelectedSourceRecordRefs()
	{
		// Note: getRecordRefOrNull() accepts record_id 0 (it only rejects negative ids), so a view row that resolves to
		// e.g. RV_PurchaseCockpit/0 (a row with no single backing record) would otherwise be treated as a loadable single
		// record. Require a real (>0) id here; otherwise fall through to the view selection where-clause below.
		final TableRecordReference singleRecordRef = getProcessInfo().getRecordRefOrNull();
		if (singleRecordRef != null && singleRecordRef.getRecord_ID() > 0)
		{
			return ImmutableList.of(singleRecordRef);
		}

		final String tableName = getProcessInfo().getTableNameOrNull();
		final IQueryFilter<Object> selectionFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (tableName == null || selectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(tableName, PlainContextAware.newWithThreadInheritedTrx(getCtx()))
				.filter(selectionFilter)
				.create()
				.listIds()
				.stream()
				.map(recordId -> TableRecordReference.of(tableName, recordId))
				.collect(ImmutableList.toImmutableList());
	}

	private ViewOpenTarget getOpenTarget()
	{
		final ProcessOpenTarget processOpenTarget = CoalesceUtil.coalesce(getProcessInfo().getOpenTarget(), ProcessOpenTarget.ModalOverlay);
		if (processOpenTarget == ProcessOpenTarget.NewBrowserTab)
		{
			return ViewOpenTarget.NewBrowserTab;
		}
		else if (processOpenTarget == ProcessOpenTarget.ModalOverlay)
		{
			return ViewOpenTarget.ModalOverlay;
		}
		else
		{
			// Defensive fallback for any future ProcessOpenTarget value not yet mapped here — unreachable with current values
			log.warn("Unknown processOpenTarget {}. Returning {}", processOpenTarget, ViewOpenTarget.ModalOverlay);
			return ViewOpenTarget.ModalOverlay;
		}
	}

	protected IZoomSource createZoomSource(@NonNull final TableRecordReference recordRef)
	{
		// Load the source record as a PO
		final PO sourcePO = new GenericPO(recordRef.getTableName(), getCtx(), recordRef.getRecord_ID(), get_TrxName());
		if (sourcePO.get_ID() <= 0)
		{
			throw new AdempiereException("Cannot load source record: " + recordRef);
		}

		return POZoomSource.of(sourcePO, getProcessInfo().getAdWindowId());
	}

	private IView createView(@NonNull final TableRecordReference recordReference, @NonNull final WindowId targetWindowId)
	{

		final RelatedDocumentsId relatedDocumentsId = RelatedDocumentsId.ofString("AD_RelationType_ID-" + getRelationTypeId().getRepoId());

		final AdWindowId adWindowId = Check.assumeNotNull(
				getProcessInfo().getAdWindowId(),
				"AD_Window_ID is required for process {}", getProcessInfo().getAdProcessId());
		final WindowId srcWindowId = WindowId.of(adWindowId);

		final DocumentPath srcDocument = DocumentPath.rootDocumentPath(srcWindowId, recordReference.getRecord_ID());
		final CreateViewRequest request = CreateViewRequest.builder(targetWindowId, JSONViewDataType.grid)
				.setReferencingDocumentPaths(ImmutableSet.of(srcDocument))
				.setDocumentReferenceId(WebuiDocumentReferenceId.ofRelatedDocumentsId(relatedDocumentsId))
				.setUseAutoFilters(true)
				.build();

		return viewsRepo.createView(request);
	}

	@NonNull
	private RelationTypeId getRelationTypeId()
	{
		return Check.assumeNotNull(getProcessInfo().getAdRelationTypeId(), "No AD_Process.AD_RelationType_ID defined for process {}", getProcessInfo().getAdProcessId());
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.getAdWindowId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No AD_Window_ID");
		}
		return ProcessPreconditionsResolution.accept();

	}
}
