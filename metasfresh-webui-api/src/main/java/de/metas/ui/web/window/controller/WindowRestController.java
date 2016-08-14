package de.metas.ui.web.window.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.util.JSONConverters;
import de.metas.ui.web.window.util.LastDocumentTracker;

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

@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	@Autowired
	private LastDocumentTracker lastDocumentsTracker; // for debugging

	private final void autologin()
	{
		// FIXME: debug logging
		LogManager.setLoggerLevel(de.metas.ui.web.window.WindowConstants.logger, Level.INFO);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.Document.class, Level.TRACE);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.DocumentField.class, Level.TRACE);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.DocumentFieldChangedEventCollector.class, Level.DEBUG); // to have the "reason" in JSON
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.sql.SqlDocumentRepository.class, null);
		//
		LogManager.setLoggerLevel(org.adempiere.ad.callout.api.impl.CalloutExecutor.class, Level.INFO);
		//
		// LogManager.dumpAllLevelsUpToRoot(de.metas.ui.web.window.WindowConstants.logger);
		// LogManager.dumpAllLevelsUpToRoot(LogManager.getLogger(DocumentFieldChangedEventCollector.class));

		// FIXME: only for testing
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "en_US");
		Env.setContext(ctx, Env.CTXNAME_ShowAcct, false);

		Services.get(IValuePreferenceBL.class)
				.getAllWindowPreferences(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), Env.getAD_User_ID(ctx))
				.stream()
				.flatMap(userValuePreferences -> userValuePreferences.values().stream())
				.forEach(userValuePreference -> Env.setPreference(ctx, userValuePreference));

		userSession.setLocale(Env.getLanguage(ctx).getLocale());
		userSession.setLoggedIn(true);
	}

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONDocumentLayout layout(@RequestParam(name = "type", required = true) final int adWindowId)
	{
		autologin();

		final DocumentLayoutDescriptor layout = documentCollection.getDocumentDescriptorFactory()
				.getDocumentDescriptor(adWindowId)
				.getLayout();
		return JSONDocumentLayout.of(layout);
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<List<Map<String, Object>>> data(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowId //
	)
	{
		autologin();

		try (Execution execution = Execution.startExecution())
		{
			final List<Document> documents = documentCollection.getDocuments(adWindowId, idStr, detailId, rowId);
			return JSONConverters.documentsToJsonObject(documents);
		}
	}

	@RequestMapping(value = "/commit", method = RequestMethod.PATCH)
	public List<Map<String, Object>> commit(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		autologin();

		try (final Execution execution = Execution.startExecution())
		{
			//
			// Fetch the document
			final DocumentId documentId = DocumentId.of(idStr);
			final DocumentId rowId = DocumentId.fromNullable(rowIdStr);
			final Document document = documentCollection.getDocument(adWindowId, documentId, detailId, rowId);
			final boolean isNew = document.isNew();

			//
			// Apply changes
			for (final JSONDocumentChangedEvent event : events)
			{
				if (JSONDocumentChangedEvent.OPERATION_Replace.equals(event.getOperation()))
				{
					document.setValue(event.getPath(), event.getValue());
				}
				else
				{
					throw new IllegalArgumentException("Unknown operation: " + event);
				}
			}

			//
			// Try saving it
			documentCollection.saveIfPossible(document);

			//
			// Make sure we collected all changes
			// TODO: optimization: it would be better if this would happen auto-magically
			if (isNew)
			{
				execution.getFieldChangedEventsCollector().collectFrom(document);
			}

			//
			// Return the changes
			return JSONConverters.toJsonObject(execution.getFieldChangedEventsCollector());
		}
	}

	@RequestMapping(value = "/typeahead", method = RequestMethod.GET)
	public List<Map<String, String>> typeahead(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestParam(name = "field", required = true) final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		autologin();

		final DocumentId documentId = DocumentId.of(idStr);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("Invalid id: " + documentId);
		}

		final DocumentId rowId = DocumentId.fromNullable(rowIdStr);
		if (rowId != null && rowId.isNew())
		{
			throw new IllegalArgumentException("Invalid rowId: " + rowId);
		}

		final Document document = documentCollection.getDocument(adWindowId, documentId, detailId, rowId);
		final List<LookupValue> lookupValues = document.getFieldLookupValuesForQuery(fieldName, query);

		return JSONConverters.lookupValuesToJsonObject(lookupValues);
	}

	@RequestMapping(value = "/dropdown", method = RequestMethod.GET)
	public List<Map<String, String>> dropdown(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestParam(name = "field", required = true) final String fieldName //
	)
	{
		autologin();

		DocumentId documentId = DocumentId.of(idStr);
		if (documentId.isNew())
		{
			// FIXME: we use this only for debugging
			documentId = lastDocumentsTracker.getLastDocumentId(adWindowId, detailId, documentId);
		}
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("Invalid id: " + documentId);
		}

		final DocumentId rowId = DocumentId.fromNullable(rowIdStr);
		if (rowId != null && rowId.isNew())
		{
			throw new IllegalArgumentException("Invalid rowId: " + rowId);
		}

		final Document document = documentCollection.getDocument(adWindowId, documentId, detailId, rowId);
		final List<LookupValue> lookupValues = document.getFieldLookupValues(fieldName);

		return JSONConverters.lookupValuesToJsonObject(lookupValues);
	}

	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public void cacheReset()
	{
		CacheMgt.get().reset(); // FIXME: debugging - while debugging is useful to reset all caches
		documentCollection.cacheReset();
	}
}
