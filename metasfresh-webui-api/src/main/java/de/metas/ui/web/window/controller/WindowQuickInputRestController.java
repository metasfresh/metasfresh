package de.metas.ui.web.window.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.exceptions.NotImplementedException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTabQuickInput;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailQuickInputDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

	private final AtomicInteger nextDocumentId = new AtomicInteger(1);

	private final CCache<Integer, QuickInput> _quickInputDocuments = CCache.newLRUCache("QuickInputDocuments", 200, 0);

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@GetMapping("/layout")
	public JSONDocumentLayoutTabQuickInput getLayout(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentIdStr_NOTUSED //
			, @PathVariable("tabId") final String tabIdStr //
	)
	{
		final DocumentLayoutDetailQuickInputDescriptor layout = documentsCollection.getDocumentDescriptor(adWindowId)
				.getLayout()
				.getDetail(DetailId.fromJson(tabIdStr))
				.getQuickInput()
				.orElse(null);

		return JSONDocumentLayoutTabQuickInput.fromNullable(layout, newJSONOptions());
	}

	@GetMapping("/{quickInputId}")
	public JSONDocument getOrCreate(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("quickInputId") final int quickInputId //
	)
	{
		userSession.assertLoggedIn();

		final QuickInput quickInput;
		if (quickInputId <= 0)
		{
			quickInput = createQuickInput(adWindowId, documentIdStr, tabIdStr);
			putQuickInputDocument(quickInput);
		}
		else
		{
			quickInput = getQuickInputReadonly(quickInputId);
		}

		return JSONDocument.ofDocument(quickInput.getQuickInputDocument(), newJSONOptions());
	}

	@RequestMapping(value = "/{quickInputId}/attribute/{fieldName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList getFieldTypeaheadValues(
			@PathVariable("quickInputId") final int quickInputId //
			, @PathVariable("fieldName") final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return getQuickInputReadonly(quickInputId)
				.getFieldTypeaheadValues(fieldName, query);
	}

	@RequestMapping(value = "/{quickInputId}/attribute/{fieldName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList getFieldDropdownValues(
			@PathVariable("quickInputId") final int quickInputId //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		userSession.assertLoggedIn();

		return getQuickInputReadonly(quickInputId)
				.getFieldDropdownValues(fieldName);
	}

	@PatchMapping("/{quickInputId}")
	public List<JSONDocument> processChanges(
			@PathVariable("quickInputId") final int quickInputId //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		return processQuickInput(quickInputId, (quickInput) -> {
			quickInput.processValueChanges(events);
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJSONOptions());
		});
	}

	@PatchMapping("{quickInputId}/complete")
	public JSONDocument complete(@PathVariable("quickInputId") final int quickInputId)
	{
		userSession.assertLoggedIn();

		final QuickInput quickInput = getQuickInputReadonly(quickInputId);

		return Execution.callInNewExecution("quickInput.complete", () -> {
			final Document document = quickInput.complete();
			return JSONDocument.ofDocument(document, newJSONOptions());
		});
	}

	private <RT> RT processQuickInput(final int quickInputId, final Function<QuickInput, RT> processor)
	{
		return Execution.callInNewExecution("processQuickInput-" + quickInputId, () -> {
			final QuickInput quickInput = getQuickInputForWriting(quickInputId);
			final RT retValue = processor.apply(quickInput);

			putQuickInputDocument(quickInput);

			return retValue;
		});

	}

	private QuickInput createQuickInput(final int adWindowId, final String documentIdStr, final String tabIdStr)
	{
		final DetailId detailId = DetailId.fromJson(tabIdStr);

		final DocumentEntityDescriptor quickInputDescriptor = documentsCollection.getDocumentEntityDescriptor(adWindowId)
				.getIncludedEntityByDetailId(detailId)
				.getQuickInputDescriptor();

		return Execution.callInNewExecution("quickInput.create", () -> {
			final Document quickInputDocument = Document.builder()
					.setEntityDescriptor(quickInputDescriptor)
					// .setShadowParentDocumentEvaluatee(parentDocument) // TODO: we would need it for context variables evaluation in lookups and other logics
					.setDocumentIdSupplier(() -> nextDocumentId.getAndIncrement())
					.initializeAsNewDocument()
					.build();

			return QuickInput.of(adWindowId, documentIdStr, detailId, quickInputDocument)
					.bindRootDocument(documentsCollection);
		});
	}

	private QuickInput getQuickInputForWriting(final int quickInputId)
	{
		return getQuickInput(quickInputId, CopyMode.CheckOutWritable);
	}

	private QuickInput getQuickInputReadonly(final int quickInputId)
	{
		return getQuickInput(quickInputId, CopyMode.CheckInReadonly);
	}

	private QuickInput getQuickInput(final int quickInputId, final CopyMode copyMode)
	{
		return _quickInputDocuments
				.getOrElseThrow(quickInputId, () -> new EntityNotFoundException("No quick input document found for ID=" + quickInputId))
				.copy(copyMode)
				.bindRootDocument(documentsCollection);
	}

	private void putQuickInputDocument(final QuickInput quickInput)
	{
		Check.assumeNotNull(quickInput, "Parameter quickInput is not null");
		_quickInputDocuments.put(quickInput.getId(), quickInput.copy(CopyMode.CheckInReadonly));
	}

	private static final class QuickInput
	{
		public static final QuickInput of(final int adWindowId, final String documentId, final DetailId detailId, final Document quickInputDocument)
		{
			return new QuickInput(adWindowId, documentId, detailId, quickInputDocument);
		}

		private final DocumentPath rootDocumentPath;
		private final DetailId detailId;
		private final Document quickInputDocument;

		private transient Document rootDocument;

		private QuickInput(final int adWindowId, final String documentId, final DetailId detailId, final Document quickInputDocument)
		{
			super();
			rootDocumentPath = DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, documentId);
			this.detailId = detailId;

			Check.assumeNotNull(quickInputDocument, "Parameter quickInputDocument is not null");
			this.quickInputDocument = quickInputDocument;
		}

		/** Copy constructor */
		private QuickInput(final QuickInput from, final CopyMode copyMode)
		{
			super();
			rootDocumentPath = from.rootDocumentPath;
			detailId = from.detailId;
			quickInputDocument = from.quickInputDocument.copy(copyMode);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("rootDocumentPath", rootDocumentPath)
					.add("detailId", detailId)
					.add("quickInputDocument", quickInputDocument)
					.toString();
		}

		public int getId()
		{
			return quickInputDocument.getDocumentIdAsInt();
		}

		public Document getQuickInputDocument()
		{
			return quickInputDocument;
		}

		public QuickInput copy(final CopyMode copyMode)
		{
			return new QuickInput(this, copyMode);
		}

		public QuickInput bindRootDocument(final DocumentCollection documentsCollection)
		{
			rootDocument = documentsCollection.getDocument(rootDocumentPath);
			quickInputDocument.setShadowParentDocumentEvaluatee(rootDocument.asEvaluatee());

			return this;
		}

		public Document getRootDocument()
		{
			if (rootDocument == null)
			{
				throw new IllegalStateException("root document not set for " + this);
			}
			return rootDocument;
		}

		public void processValueChanges(final List<JSONDocumentChangedEvent> events)
		{
			quickInputDocument.processValueChanges(events, () -> "direct update from rest API");
		}

		/**
		 * @return newly created document
		 */
		public Document complete()
		{
			
			// TODO call the API to create the new Document and return it!
			throw new NotImplementedException();
		}

		public JSONLookupValuesList getFieldDropdownValues(final String fieldName)
		{
			return getQuickInputDocument()
					.getFieldLookupValues(fieldName)
					.transform(JSONLookupValuesList::ofLookupValuesList);
		}

		public JSONLookupValuesList getFieldTypeaheadValues(final String fieldName, final String query)
		{
			return getQuickInputDocument()
					.getFieldLookupValuesForQuery(fieldName, query)
					.transform(JSONLookupValuesList::ofLookupValuesList);
		}

	}
}
