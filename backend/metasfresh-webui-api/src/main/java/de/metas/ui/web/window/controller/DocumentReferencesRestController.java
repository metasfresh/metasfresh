package de.metas.ui.web.window.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReference;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroup;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.util.Services;
import io.swagger.annotations.Api;
import lombok.NonNull;

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

@Api
@RestController
@RequestMapping(DocumentReferencesRestController.ENDPOINT)
public class DocumentReferencesRestController
{
	public static final String ENDPOINT = WindowRestController.ENDPOINT;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final UserSession userSession;
	private final DocumentReferencesService documentReferencesService;
	private final MenuTreeRepository menuTreeRepository;

	private final ExecutorService sseExecutor;

	public DocumentReferencesRestController(
			@NonNull final UserSession userSession,
			@NonNull final DocumentReferencesService documentReferencesService,
			@NonNull final MenuTreeRepository menuTreeRepository)
	{
		this.userSession = userSession;
		this.documentReferencesService = documentReferencesService;
		this.menuTreeRepository = menuTreeRepository;

		this.sseExecutor = Executors.newCachedThreadPool(CustomizableThreadFactory.builder()
				.setDaemon(true)
				.setThreadNamePrefix(getClass().getSimpleName() + "-SSE-")
				.build());
	}

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.prepareFrom(userSession).build();
	}

	private JSONDocumentReferencesGroupsAggregator newJSONDocumentReferencesGroupsAggregator()
	{
		return JSONDocumentReferencesGroupsAggregator.builder()
				.menuTreeRepository(menuTreeRepository)
				.msgBL(msgBL)
				.jsonOpts(newJSONOptions())
				.userRolePermissionsKey(userSession.getUserRolePermissionsKey())
				.build();
	}

	@GetMapping("/{windowId}/{documentId}/references/sse")
	public SseEmitter streamRootDocumentReferences(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentId)
	{
		final SseEmitter emitter = new SseEmitter();
		try
		{
			userSession.assertLoggedIn();

			final DocumentPath documentPath = DocumentPath.rootDocumentPath(
					WindowId.fromJson(windowIdStr),
					documentId);

			final Stream<DocumentReference> documentReferences = documentReferencesService.getDocumentReferences(
					documentPath,
					userSession.getUserRolePermissions());

			final JSONDocumentReferencesGroupsAggregator aggregator = newJSONDocumentReferencesGroupsAggregator();

			sseExecutor.execute(() -> {
				try
				{
					documentReferences.forEach(documentReference -> aggregator.addAndFlush(documentReference, emitter));
					emitter.send(JSONDocumentReferencesEvent.COMPLETED);
					emitter.complete();
				}
				catch (Exception ex)
				{
					emitter.completeWithError(ex);
				}
			});
		}
		catch (Exception ex)
		{
			emitter.completeWithError(ex);
		}

		return emitter;
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/references")
	public JSONDocumentReferencesGroup getIncludedDocumentReferences(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@PathVariable("rowId") final String rowIdStr)
	{
		userSession.assertLoggedIn();

		// Get document references
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, tabIdStr, rowIdStr);
		final List<DocumentReference> documentReferences = documentReferencesService.getDocumentReferences(documentPath, userSession.getUserRolePermissions())
				.collect(ImmutableList.toImmutableList());

		final JSONOptions jsonOpts = newJSONOptions();
		return JSONDocumentReferencesGroup.builder()
				.caption("References")
				.references(JSONDocumentReference.ofList(documentReferences, jsonOpts))
				.build();
	}
}
