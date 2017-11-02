package de.metas.ui.web.quickinput;

import java.util.List;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONQuickInputLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import io.swagger.annotations.Api;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Api
@RestController
@RequestMapping(value = WindowQuickInputRestController.ENDPOINT)
public class WindowQuickInputRestController
{
	public static final String ENDPOINT = WindowRestController.ENDPOINT + "/{windowId}/{documentId}/{tabId}/quickInput";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentsCollection;

	@Autowired
	private QuickInputDescriptorFactoryService quickInputDescriptors;
	@Autowired
	private NewRecordDescriptorsProvider newRecordDescriptorsProvider;

	@Autowired
	private DocumentWebsocketPublisher websocketPublisher;

	private final CCache<DocumentId, QuickInput> _quickInputDocuments = CCache.newLRUCache("QuickInputDocuments", 200, 0);

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder(userSession)
				.setNewRecordDescriptorsProvider(newRecordDescriptorsProvider)
				.build();
	}

	@RequestMapping(method = RequestMethod.HEAD)
	public ResponseEntity<Object> checkSupported(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr_NOTUSED //
			, @PathVariable("tabId") final String tabIdStr //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentEntityDescriptor includedDocumentDescriptor = documentsCollection.getDocumentEntityDescriptor(windowId)
				.getIncludedEntityByDetailId(DetailId.fromJson(tabIdStr));

		if (quickInputDescriptors.hasQuickInputEntityDescriptor(includedDocumentDescriptor))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/layout")
	public JSONQuickInputLayoutDescriptor getLayout(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr_NOTUSED //
			, @PathVariable("tabId") final String tabIdStr //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentEntityDescriptor includedDocumentDescriptor = documentsCollection.getDocumentEntityDescriptor(windowId)
				.getIncludedEntityByDetailId(DetailId.fromJson(tabIdStr));

		final QuickInputDescriptor quickInputDescriptor = quickInputDescriptors.getQuickInputEntityDescriptor(includedDocumentDescriptor);
		if (quickInputDescriptor == null)
		{
			return null;
		}

		final QuickInputLayoutDescriptor layout = quickInputDescriptor.getLayout();
		return JSONQuickInputLayoutDescriptor.fromNullable(layout, newJSONOptions());
	}

	@PostMapping
	public JSONDocument create(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath rootDocumentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		final DetailId detailId = DetailId.fromJson(tabIdStr);

		return Execution.callInNewExecution("quickInput.create", () -> {
			final QuickInput quickInput = documentsCollection.forRootDocumentReadonly(rootDocumentPath, rootDocument -> {
				// Make sure we can edit our root document. Fail fast.
				DocumentPermissionsHelper.assertCanEdit(rootDocument, userSession.getUserRolePermissions());

				final DocumentEntityDescriptor includedDocumentDescriptor = rootDocument.getEntityDescriptor().getIncludedEntityByDetailId(detailId);

				final QuickInputDescriptor quickInputDescriptor = quickInputDescriptors.getQuickInputEntityDescriptor(includedDocumentDescriptor);

				try
				{
					return QuickInput.builder()
							.setQuickInputDescriptor(quickInputDescriptor)
							.setRootDocumentPath(rootDocument.getDocumentPath())
							.build()
							.bindRootDocument(rootDocument)
							.assertTargetWritable();
				}
				catch (Exception ex)
				{
					// Avoid showing "weird" exception to use, so we return HTTP 404 which is interpreted by frontend
					// see https://github.com/metasfresh/metasfresh-webui-frontend/issues/487
					throw EntityNotFoundException.wrapIfNeeded(ex);
				}
			});

			commit(quickInput);

			return JSONDocument.ofDocument(quickInput.getQuickInputDocument(), newJSONOptions());
		});
	}

	private final <R> R forQuickInputReadonly(final QuickInputPath quickInputPath, final Function<QuickInput, R> quickInputProcessor)
	{
		return documentsCollection.forDocumentReadonly(quickInputPath.getRootDocumentPath(), rootDocument -> {
			try (final IAutoCloseable c = getQuickInputNoLock(quickInputPath).lockForReading())
			{
				final QuickInput quickInput = getQuickInputNoLock(quickInputPath).copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance)
						.bindRootDocument(rootDocument)
						.assertTargetWritable();
				return quickInputProcessor.apply(quickInput);
			}
		});
	}

	private final <R> R forQuickInputWritable(final QuickInputPath quickInputPath, final IDocumentChangesCollector changesCollector, final Function<QuickInput, R> quickInputProcessor)
	{
		return documentsCollection.forRootDocumentWritable(quickInputPath.getRootDocumentPath(), changesCollector, rootDocument -> {
			try (final IAutoCloseable c = getQuickInputNoLock(quickInputPath).lockForWriting())
			{
				final QuickInput quickInput = getQuickInputNoLock(quickInputPath).copy(CopyMode.CheckOutWritable, changesCollector)
						.bindRootDocument(rootDocument)
						.assertTargetWritable();

				final R result = quickInputProcessor.apply(quickInput);

				commit(quickInput);

				return result;
			}
		});
	}

	@GetMapping("/{quickInputId}")
	public JSONDocument getById(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final String quickInputIdStr //
	)
	{
		userSession.assertLoggedIn();

		final QuickInputPath quickInputPath = QuickInputPath.of(windowIdStr, documentIdStr, tabIdStr, quickInputIdStr);
		return forQuickInputReadonly(quickInputPath, quickInput -> JSONDocument.ofDocument(quickInput.getQuickInputDocument(), newJSONOptions()));
	}

	@RequestMapping(value = "/{quickInputId}/field/{fieldName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList getFieldTypeaheadValues(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final String quickInputIdStr //
			, @PathVariable("fieldName") final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		final QuickInputPath quickInputPath = QuickInputPath.of(windowIdStr, documentIdStr, tabIdStr, quickInputIdStr);
		return forQuickInputReadonly(quickInputPath, quickInput -> quickInput.getFieldTypeaheadValues(fieldName, query));
	}

	@RequestMapping(value = "/{quickInputId}/field/{fieldName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList getFieldDropdownValues(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final String quickInputIdStr //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		userSession.assertLoggedIn();

		final QuickInputPath quickInputPath = QuickInputPath.of(windowIdStr, documentIdStr, tabIdStr, quickInputIdStr);
		return forQuickInputReadonly(quickInputPath, quickInput -> quickInput.getFieldDropdownValues(fieldName));
	}

	@PatchMapping("/{quickInputId}")
	public List<JSONDocument> processChanges(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final String quickInputIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final QuickInputPath quickInputPath = QuickInputPath.of(windowIdStr, documentIdStr, tabIdStr, quickInputIdStr);
		return Execution.callInNewExecution("quickInput-writable-" + quickInputPath, () -> {
			final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();

			forQuickInputWritable(quickInputPath, changesCollector, quickInput -> {
				quickInput.processValueChanges(events);

				changesCollector.setPrimaryChange(quickInput.getDocumentPath());
				return null; // void
			});

			// Extract and send websocket events
			final List<JSONDocument> jsonDocumentEvents = JSONDocument.ofEvents(changesCollector, newJSONOptions());
			websocketPublisher.convertAndPublish(jsonDocumentEvents);

			return jsonDocumentEvents;
		});
	}

	@PostMapping("{quickInputId}/complete")
	public JSONDocument complete(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final String quickInputIdStr //
	)
	{
		userSession.assertLoggedIn();

		final QuickInputPath quickInputPath = QuickInputPath.of(windowIdStr, documentIdStr, tabIdStr, quickInputIdStr);
		final IDocumentChangesCollector changesCollector = NullDocumentChangesCollector.instance;
		return Execution.callInNewExecution("quickInput-writable-" + quickInputPath, () -> {
			return forQuickInputWritable(quickInputPath, changesCollector, quickInput -> {
				final Document document = quickInput.complete();
				return JSONDocument.ofDocument(document, newJSONOptions());
			});
		});
	}

	private final QuickInput getQuickInputNoLock(final QuickInputPath quickInputPath)
	{
		return _quickInputDocuments
				.getOrElseThrow(quickInputPath.getQuickInputId(), () -> new EntityNotFoundException("No quick input document found for " + quickInputPath));
	}

	private void commit(final QuickInput quickInput)
	{
		Check.assumeNotNull(quickInput, "Parameter quickInput is not null");

		if (quickInput.isCompleted())
		{
			_quickInputDocuments.remove(quickInput.getId());
		}
		else
		{
			_quickInputDocuments.put(quickInput.getId(), quickInput.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance));
		}
	}
}
