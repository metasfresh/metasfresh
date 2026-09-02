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
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.MQuery;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.ui.web.view.SqlViewFactory.MSG_NO_RELATED_DOCS_FOUND;

/**
 * Process implementation for opening related documents via an AD_RelationType.
 * <p>
 * The display mode is configured per AD_Process via {@code AD_Process.OpenTarget}
 * (see {@link ProcessOpenTarget}): modal overlay (default — historical behaviour
 * when the column is NULL) or new browser tab.
 * <p>
 * Whether the jump also applies the target window's own default filters is configured per AD_Process via
 * {@code AD_Process.IsUseAutoFilters}: {@code 'Y'} (default — historical behaviour) applies them; {@code 'N'}
 * shows exactly the rows the relation resolved, ignoring the target window's default filters.
 * <p>
 * This process is automatically assigned when AD_Process.Type='RelationTypeInOverlay'.
 */
public class RelationTypeInOverlayProcess extends JavaProcess implements IProcessPrecondition
{
	private static final String UNION_FILTER_ID = "RelationTypeInOverlay-union";

	/**
	 * Upper bound for how many rows may be selected before the process is offered.
	 * Guards against building an unbounded per-source OR-union / running per-row zoom resolution.
	 * Set the sysconfig to {@code 0} (or negative) to disable the limit.
	 */
	@VisibleForTesting
	static final String SYSCONFIG_MaxSelectionSize = "de.metas.ui.web.view.process.RelationTypeInOverlayProcess.MaxSelectionSize";
	private static final int DEFAULT_MaxSelectionSize = 100;

	@NonNull private final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory;
	@NonNull private final IViewsRepository viewsRepo;
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

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

	/**
	 * Single test factory for both single- and multi-selection scenarios.
	 * <p>
	 * The real axis is <em>how the zoom source is resolved</em>, not single vs multi: {@code zoomSourcesByRecordRef}
	 * maps each selected record ref to its zoom source, and any ref that is absent from the map makes
	 * {@code createZoomSource} throw — which lets a test simulate an unloadable / concurrently-deleted source
	 * (a "fixed zoom" is simply a single-entry map).
	 *
	 * @param selectedRecordRefs the selection to inject; pass {@code null} to let the real
	 *                           {@link #getSelectedSourceRecordRefs()} resolve it from the {@code processInfo}.
	 */
	@VisibleForTesting
	public static RelationTypeInOverlayProcess newInstanceForUnitTesting(
			@NonNull final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final de.metas.process.ProcessInfo processInfo,
			@Nullable final List<TableRecordReference> selectedRecordRefs,
			@NonNull final Map<TableRecordReference, IZoomSource> zoomSourcesByRecordRef)
	{
		final RelationTypeInOverlayProcess process = new RelationTypeInOverlayProcess(relationTypeProvidersFactory, viewsRepo)
		{
			@Override
			protected List<TableRecordReference> getSelectedSourceRecordRefs()
			{
				return selectedRecordRefs != null
						? ImmutableList.copyOf(selectedRecordRefs)
						: super.getSelectedSourceRecordRefs();
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
	 * <p>
	 * We deliberately do not use the framework's {@code ProcessExecutionResult#setRecordsToOpen(records, windowId)}
	 * convenience (as e.g. Material Cockpit does): it materializes the full related-id set up front and hardcodes
	 * {@code GridView} + {@code SAME_TAB_OVERLAY}, whereas the sticky-filter view stays lazy (the grid pages through the
	 * filter) and honours {@link #getOpenTarget()} (ModalOverlay / NewBrowserTab).
	 */
	private ViewId createCombinedView(@NonNull final List<TableRecordReference> sourceRecordRefs, @NonNull final RelationTypeId relationTypeId)
	{
		WindowId targetWindowId = null;
		// LinkedHashSet: dedupe identical per-source where-clauses (e.g. two rows resolving to the same candidate)
		// while keeping a stable order in the combined filter.
		final Set<String> whereClauses = new LinkedHashSet<>();

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
				final WindowId groupWindowId = WindowId.of(group.getTargetWindowId());
				if (targetWindowId == null)
				{
					targetWindowId = groupWindowId;
				}
				else if (!WindowId.equals(targetWindowId, groupWindowId))
				{
					addLog("RelationType {} returned target window {} for {} but the combined view uses {}; ignoring that group.",
							relationTypeId, groupWindowId, recordRef, targetWindowId);
					continue;
				}

				// A candidate whose MQuery has no where-clause would match the whole target table and cannot
				// be OR'ed into the union, so it is intentionally skipped below. Safe for the current relation
				// types, whose candidates always carry a where-clause; revisit if reused for other relation types.
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

		return createCombinedFilterView(targetWindowId, unionFilter).getViewId();
	}

	private IView createCombinedFilterView(@NonNull final WindowId targetWindowId, @NonNull final DocumentFilter unionFilter)
	{
		final RelatedDocumentsId relatedDocumentsId = RelatedDocumentsId.ofString("AD_RelationType_ID-" + getRelationTypeId().getRepoId());
		final CreateViewRequest request = CreateViewRequest.builder(targetWindowId, JSONViewDataType.grid)
				.setDocumentReferenceId(WebuiDocumentReferenceId.ofRelatedDocumentsId(relatedDocumentsId))
				.addStickyFilters(unionFilter)
				.setUseAutoFilters(getProcessInfo().isUseAutoFilters())
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
		// Single-record context: use it directly. isRecordSet() requires a real (>0) id, so a view row that resolves to
		// e.g. RV_PurchaseCockpit/0 (a row with no single backing record) is not treated as a loadable single record and
		// falls through to the view-selection resolution below.
		if (getProcessInfo().isRecordSet())
		{
			return ImmutableList.of(getProcessInfo().getRecordRefNotNull());
		}

		// Multi-row view selection: resolve the AD_PInstance selection where-clause to the selected record refs.
		// We keep the explicit guards (rather than letting retrieveSelectedRecordsQueryBuilder decide) because that
		// helper treats record_id 0 as a valid single record, whereas here id 0 must surface as @NoSelection@.
		final String tableName = getProcessInfo().getTableNameOrNull();
		if (tableName == null || getProcessInfo().getQueryFilterOrElse(null) == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		// applyActiveRecordsFilter=false: the source can be a view (e.g. RV_PurchaseCockpit) with no IsActive column, and
		// the user has already picked the exact rows, so an IsActive restriction would both break the SQL and be wrong here.
		return retrieveSelectedRecordsQueryBuilder(Object.class, false)
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
				.setUseAutoFilters(getProcessInfo().isUseAutoFilters())
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
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final int maxSelectionSize = sysConfigBL.getIntValue(SYSCONFIG_MaxSelectionSize, DEFAULT_MaxSelectionSize);
		if (maxSelectionSize > 0 && context.isMoreThanAllowedSelected(maxSelectionSize))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(maxSelectionSize);
		}

		return ProcessPreconditionsResolution.accept();
	}
}
