package de.metas.ui.web.view;

import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;

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
 * API for editing a view row.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/577
 */
@RestController
@RequestMapping(ViewRowEditRestController.ENDPOINT)
public class ViewRowEditRestController
{
	private static final String PARAM_WindowId = ViewRestController.PARAM_WindowId;
	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_RowId = "rowId";
	/* package */ static final String ENDPOINT = ViewRestController.ENDPOINT + "/{" + PARAM_ViewId + "}/{" + PARAM_RowId + "}/edit";

	@Autowired
	private UserSession userSession;

	@Autowired
	private IViewsRepository viewsRepo;

	@Autowired
	private DocumentCollection documentsCollection;

	@PatchMapping
	public JSONViewRow patchRow(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);

		final IView view = viewsRepo.getView(viewId);
		final DocumentPath documentPath = view.getById(rowId).getDocumentPath();

		Services.get(ITrxManager.class)
				.runInThreadInheritedTrx(() -> documentsCollection.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
					//
					// Process changes and the save the document
					document.processValueChanges(events, ReasonSupplier.NONE);
					document.saveIfValidAndHasChanges();

					//
					// Important: before allowing the document to be stored back in documents collection,
					// we need to make sure it's valid and saved.
					final DocumentValidStatus validStatus = document.getValidStatus();
					if (!validStatus.isValid())
					{
						throw new AdempiereException(validStatus.getReason());
					}
					final DocumentSaveStatus saveStatus = document.getSaveStatus();
					if (saveStatus.isNotSaved())
					{
						throw new AdempiereException(saveStatus.getReason());
					}

					//
					return null; // nothing/not important
				}));

		view.invalidateRowById(rowId);
		ViewChangesCollector.getCurrentOrAutoflush().collectRowChanged(view, rowId);
		
		documentsCollection.invalidateRootDocument(documentPath);

		final IViewRow row = view.getById(rowId);
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		return JSONViewRow.ofRow(row, rowOverrides, userSession.getAD_Language());
	}

	@GetMapping("/{fieldName}/typeahead")
	public JSONLookupValuesList getViewFieldTypeahead(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@PathVariable("fieldName") final String fieldName,
			@RequestParam("query") final String query)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);
		final IView view = viewsRepo.getView(viewId);
		final DocumentPath documentPath = view.getById(rowId).getDocumentPath();

		final IDocumentChangesCollector changesCollector = NullDocumentChangesCollector.instance;
		return documentsCollection.forDocumentReadonly(documentPath, changesCollector, document -> document.getFieldLookupValuesForQuery(fieldName, query))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{fieldName}/dropdown")
	public JSONLookupValuesList getViewFieldTypeahead(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@PathVariable("fieldName") final String fieldName)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);
		final IView view = viewsRepo.getView(viewId);
		final DocumentPath documentPath = view.getById(rowId).getDocumentPath();

		final IDocumentChangesCollector changesCollector = NullDocumentChangesCollector.instance;
		return documentsCollection.forDocumentReadonly(documentPath, changesCollector, document -> document.getFieldLookupValues(fieldName))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

}
