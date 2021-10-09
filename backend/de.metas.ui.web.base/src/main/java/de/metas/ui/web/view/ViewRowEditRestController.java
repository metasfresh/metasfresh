/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.ui.web.comments.CommentsService;
import de.metas.ui.web.comments.ViewRowCommentsSummary;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API for editing a view row.
 *
 * @author metas-dev <dev@metasfresh.com>
 * Task https://github.com/metasfresh/metasfresh-webui-api/issues/577
 */
@RestController
@RequestMapping(ViewRowEditRestController.ENDPOINT)
public class ViewRowEditRestController
{
	private static final String PARAM_WindowId = ViewRestController.PARAM_WindowId;
	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_RowId = "rowId";
	private static final String PARAM_FieldName = "fieldName";
	/* package */ static final String ENDPOINT = ViewRestController.ENDPOINT + "/{" + PARAM_ViewId + "}/{" + PARAM_RowId + "}/edit";

	@Autowired
	private UserSession userSession;

	@Autowired
	private IViewsRepository viewsRepo;

	@Autowired
	private DocumentCollection documentsCollection;

	@Autowired
	private CommentsService commentsService;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	private IEditableView getEditableView(final ViewId viewId)
	{
		final IView view = viewsRepo.getView(viewId);
		return IEditableView.asEditableView(view);
	}

	private RowEditingContext createRowEditingContext(final DocumentId rowId)
	{
		return RowEditingContext.builder()
				.rowId(rowId)
				.documentsCollection(documentsCollection)
				.build();
	}

	@PatchMapping
	public JSONViewRow patchRow(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@RequestBody final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);

		final IEditableView view = getEditableView(viewId);
		final RowEditingContext editingCtx = createRowEditingContext(rowId);
		view.patchViewRow(editingCtx, fieldChangeRequests);

		final IViewRow row = view.getById(rowId);
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		final JSONOptions jsonOpts = newJSONOptions();

		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(row);

		return JSONViewRow.ofRow(row, rowOverrides, jsonOpts, viewRowCommentsSummary);
	}

	@GetMapping("/{fieldName}/typeahead")
	public JSONLookupValuesPage getFieldTypeahead(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@PathVariable(PARAM_FieldName) final String fieldName,
			@RequestParam("query") final String query)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);

		final IEditableView view = getEditableView(viewId);
		final RowEditingContext editingCtx = createRowEditingContext(rowId);
		return view.getFieldTypeahead(editingCtx, fieldName, query)
				.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language());
	}

	@GetMapping("/{fieldName}/dropdown")
	public JSONLookupValuesList getFieldDropdown(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_RowId) final String rowIdStr,
			@PathVariable(PARAM_FieldName) final String fieldName)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);

		final IEditableView view = getEditableView(viewId);
		final RowEditingContext editingCtx = createRowEditingContext(rowId);
		return view.getFieldDropdown(editingCtx, fieldName)
				.transform(this::toJSONLookupValuesList);
	}
}
