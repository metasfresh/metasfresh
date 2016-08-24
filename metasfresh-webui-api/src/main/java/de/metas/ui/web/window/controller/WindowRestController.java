package de.metas.ui.web.window.controller;

import java.util.List;
import java.util.Properties;

import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController implements IWindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";

	private static final Logger logger = LogManager.getLogger(WindowRestController.class);

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	private static final transient Splitter FIELDS_LIST_SPLITTER = Splitter.on(",")
			.trimResults()
			.omitEmptyStrings();

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	private final void autologin()
	{
		// FIXME: debug logging
		LogManager.setLoggerLevel(de.metas.ui.web.window.WindowConstants.logger, Level.INFO);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.Document.class, Level.TRACE);
		LogManager.setLoggerLevel("de.metas.ui.web.window.model.DocumentField", Level.TRACE);
		LogManager.setLoggerLevel(de.metas.ui.web.window.controller.Execution.class, Level.TRACE);
		WindowConstants.setProtocolDebugging(true);
		LogManager.setLoggerLevel(de.metas.ui.web.window.model.sql.SqlDocumentRepository.class, null);
		//
		LogManager.setLoggerLevel(org.adempiere.ad.callout.api.impl.CalloutExecutor.class, Level.INFO);
		//
		// LogManager.setLoggerLevel("org.adempiere.ad.expression.api", Level.TRACE); // logic expressions debugging
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
		Env.setContext(ctx, "#C_UOM_ID", 100);

		Services.get(IValuePreferenceBL.class)
				.getAllWindowPreferences(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), Env.getAD_User_ID(ctx))
				.stream()
				.flatMap(userValuePreferences -> userValuePreferences.values().stream())
				.forEach(userValuePreference -> Env.setPreference(ctx, userValuePreference));

		userSession.setLocale(Env.getLanguage(ctx).getLocale());
		userSession.setLoggedIn(true);
	}

	@Override
	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONDocumentLayout layout(@RequestParam(name = "type", required = true) final int adWindowId)
	{
		autologin();

		final DocumentLayoutDescriptor layout = documentCollection.getDocumentDescriptorFactory()
				.getDocumentDescriptor(adWindowId)
				.getLayout();
		return JSONDocumentLayout.of(layout);
	}

	@Override
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<JSONDocument> data(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestParam(name = "fields", required = false) @ApiParam("comma separated field names") final String fieldsListStr //
	)
	{
		autologin();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.allowNullRowId()
				.build();

		final List<String> fieldsList;
		if (fieldsListStr != null && !fieldsListStr.isEmpty())
		{
			fieldsList = FIELDS_LIST_SPLITTER.splitToList(fieldsListStr);
		}
		else
		{
			fieldsList = ImmutableList.of();
		}

		final List<Document> documents = documentCollection.getDocuments(documentPath);
		return JSONDocument.ofDocumentsList(documents, fieldsList);
	}

	@Override
	@RequestMapping(value = "/commit", method = RequestMethod.PATCH)
	public List<JSONDocument> commit(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		autologin();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.allowNewDocumentId()
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.allowNewRowId()
				.build();

		return Execution.callInNewExecution("window.commit", () -> commit0(documentPath, events));
	}

	private List<JSONDocument> commit0(final DocumentPath documentPath, final List<JSONDocumentChangedEvent> events)
	{
		//
		// Fetch the document in writing mode
		final Document document = documentCollection.getOrCreateDocumentForWriting(documentPath);

		//
		// Apply changes
		for (final JSONDocumentChangedEvent event : events)
		{
			if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
			{
				document.processValueChange(event.getPath(), event.getValue(), REASON_Value_DirectSetFromCommitAPI);
			}
			else
			{
				throw new IllegalArgumentException("Unknown operation: " + event);
			}
		}

		// Push back the changed document
		documentCollection.commit(document);

		//
		// Make sure all events were collected for the case when we just created the new document
		// FIXME: this is a workaround and in case we find out all events were collected, we just need to remove this.
		if (documentPath.isNewDocument())
		{
			final boolean somethingCollected = Execution.getCurrentDocumentChangesCollector().collectFrom(document, REASON_Value_DirectSetFromCommitAPI);
			if (somethingCollected)
			{
				logger.warn("We would expect all events to be auto-magically collected but it seems that not all of them were collected!", new Exception("StackTrace"));
			}
		}

		//
		// Return the changes
		return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector());
	}

	@Override
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public List<JSONDocument> delete(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
	)
	{
		autologin();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();

		return Execution.callInNewExecution("window.delete", () -> {
			documentCollection.delete(documentPath);
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector());
		});
	}

	@Override
	@RequestMapping(value = "/typeahead", method = RequestMethod.GET)
	public List<JSONLookupValue> typeahead(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestParam(name = "field", required = true) final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		autologin();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();

		final Document document = documentCollection.getDocument(documentPath);
		final List<LookupValue> lookupValues = document.getFieldLookupValuesForQuery(fieldName, query);
		return JSONLookupValue.ofLookupValuesList(lookupValues);
	}

	@Override
	@RequestMapping(value = "/dropdown", method = RequestMethod.GET)
	public List<JSONLookupValue> dropdown(
			@RequestParam(name = "type", required = true) final int adWindowId //
			, @RequestParam(name = "id", required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = "tabid", required = false) final String detailId //
			, @RequestParam(name = "rowId", required = false) final String rowIdStr //
			, @RequestParam(name = "field", required = true) final String fieldName //
	)
	{
		autologin();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();

		final Document document = documentCollection.getDocument(documentPath);
		final List<LookupValue> lookupValues = document.getFieldLookupValues(fieldName);

		return JSONLookupValue.ofLookupValuesList(lookupValues);
	}

	@Override
	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public void cacheReset()
	{
		CacheMgt.get().reset(); // FIXME: debugging - while debugging is useful to reset all caches
		documentCollection.cacheReset();
	}
}
