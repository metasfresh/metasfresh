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

package de.metas.ui.web.view;

import com.google.common.collect.ImmutableSet;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.relation_type.RelationTypeId;
import de.metas.document.references.related_documents.relation_type.RelationTypeRelatedDocumentsProvidersFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.PO;

import java.util.Collections;
import java.util.List;

/**
 * Process implementation for opening related documents in an overlay window (as grid view).
 * <p>
 * This process is automatically assigned when a process has Type = 'RelationTypeInOverlay'.
 * It retrieves related documents based on the configured AD_RelationType_ID and displays them in a modal overlay.
 */
public class RelationTypeInOverlayProcess extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey MSG_NO_RELATED_DOCS_FOUND = AdMessageKey.of("NO_RELATED_DOCS_FOUND");

	@NonNull private final RelationTypeRelatedDocumentsProvidersFactory relationTypeProvidersFactory = SpringContextHolder.instance.getBean(RelationTypeRelatedDocumentsProvidersFactory.class);
	@NonNull private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(ViewsRepository.class);

	@Override
	protected String doIt()
	{
		final TableRecordReference recordRef = getRecordRef();

		final RelationTypeId relationTypeId = getRelationTypeId();

		// Load the source record as a PO
		final PO sourcePO = new GenericPO(recordRef.getTableName(), getCtx(), recordRef.getRecord_ID(), get_TrxName());
		if (sourcePO.get_ID() <= 0)
		{
			throw new AdempiereException("Cannot load source record: " + recordRef);
		}

		// Create zoom source from the current record
		final IZoomSource zoomSource = POZoomSource.of(sourcePO, getProcessInfo().getAdWindowId());

		// Get the specific provider for this relation type and retrieve related documents
		final List<RelatedDocumentsCandidateGroup> relatedDocumentGroups = relationTypeProvidersFactory
				.findRelatedDocumentsProvider(relationTypeId)
				.map(docProvider -> docProvider.retrieveRelatedDocumentsCandidates(zoomSource, null))
				.orElse(Collections.emptyList());

		if (relatedDocumentGroups.isEmpty())
		{
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}

		// Get the first group and create a view from it. We're expecting exactly one group.
		final RelatedDocumentsCandidateGroup firstGroup = relatedDocumentGroups.get(0);

		final IView popupView = createView(recordRef, firstGroup);

		getResult().setWebuiViewToOpen(
				ProcessExecutionResult.WebuiViewToOpen.modalOverlay(popupView.getViewId().getViewId()));

		return MSG_OK;
	}

	private IView createView(@NonNull final TableRecordReference recordReference, @NonNull final RelatedDocumentsCandidateGroup firstGroup)
	{
		if (firstGroup.getCandidates().isEmpty())
		{
			throw new AdempiereException("Relation group contains no candidates");
		}

		final RelatedDocumentsId relatedDocumentsId = RelatedDocumentsId.ofString("AD_RelationType_ID-" + getRelationTypeId().getRepoId());

		final WindowId srcWindowId = WindowId.of(getProcessInfo().getAdWindowId());
		final WindowId targetWindowId = WindowId.of(firstGroup.getTargetWindowId());

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
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (context.getAdWindowId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No AD_Window_ID");
		}
		return ProcessPreconditionsResolution.accept();

	}
}
