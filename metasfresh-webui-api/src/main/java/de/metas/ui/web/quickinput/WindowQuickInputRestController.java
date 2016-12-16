package de.metas.ui.web.quickinput;

import java.util.List;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
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
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.controller.WindowRestController;
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

	@Autowired
	private QuickInputProcessorFactory quickInputProcessorFactory;

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

	@PostMapping
	public JSONDocument create(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
	)
	{
		userSession.assertLoggedIn();

		final QuickInput quickInput = createQuickInput(adWindowId, documentIdStr, tabIdStr);
		putQuickInput(quickInput);

		return JSONDocument.ofDocument(quickInput.getQuickInputDocument(), newJSONOptions());
	}

	@GetMapping("/{quickInputId}")
	public JSONDocument get(@PathVariable("quickInputId") final int quickInputId)
	{
		userSession.assertLoggedIn();

		final QuickInput quickInput = getQuickInputReadonly(quickInputId);

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

	@PostMapping("{quickInputId}/complete")
	public JSONDocument complete(@PathVariable("quickInputId") final int quickInputId)
	{
		userSession.assertLoggedIn();

		final QuickInput quickInput = getQuickInputReadonly(quickInputId);

		final JSONDocument jsonDocument = Execution.callInNewExecution("quickInput.complete", () -> {
			final Document document = quickInput.complete();
			return JSONDocument.ofDocument(document, newJSONOptions());
		});

		removeQuickInput(quickInput);

		return jsonDocument;
	}
	
	@PatchMapping("{quickInputId}/complete")
	@Deprecated
	public JSONDocument complete_DEPRECATED(@PathVariable("quickInputId") final int quickInputId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return complete(quickInputId);
	}


	private <RT> RT processQuickInput(final int quickInputId, final Function<QuickInput, RT> processor)
	{
		return Execution.callInNewExecution("processQuickInput-" + quickInputId, () -> {
			final QuickInput quickInput = getQuickInputForWriting(quickInputId);
			final RT retValue = processor.apply(quickInput);

			putQuickInput(quickInput);

			return retValue;
		});

	}

	private QuickInput createQuickInput(final int adWindowId, final String documentIdStr, final String tabIdStr)
	{
		final DocumentEntityDescriptor quickInputDescriptor = documentsCollection.getDocumentEntityDescriptor(adWindowId)
				.getIncludedEntityByDetailId(DetailId.fromJson(tabIdStr))
				.getQuickInputDescriptor();

		return Execution.callInNewExecution("quickInput.create", () -> {
			return QuickInput.builder()
					.setQuickInputDescriptor(quickInputDescriptor)
					.setRootDocumentPath(adWindowId, documentIdStr)
					.setQuickInputProcessorFactory(quickInputProcessorFactory)
					.build()
					.bindRootDocument(documentsCollection)
					.assertTargetWritable();
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
				.bindRootDocument(documentsCollection)
				.assertTargetWritable();
	}

	private void putQuickInput(final QuickInput quickInput)
	{
		Check.assumeNotNull(quickInput, "Parameter quickInput is not null");
		_quickInputDocuments.put(quickInput.getId(), quickInput.copy(CopyMode.CheckInReadonly));
	}

	private void removeQuickInput(final QuickInput quickInput)
	{
		Check.assumeNotNull(quickInput, "Parameter quickInput is not null");
		_quickInputDocuments.remove(quickInput.getId());
	}
}
