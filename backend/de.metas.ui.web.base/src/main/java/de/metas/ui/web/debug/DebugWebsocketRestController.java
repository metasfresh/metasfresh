package de.metas.ui.web.debug;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.JSONViewChanges;
import de.metas.ui.web.view.event.ViewChanges;
import de.metas.ui.web.websocket.WebsocketActiveSubscriptionsIndex;
import de.metas.ui.web.websocket.WebsocketEventLogRecord;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.websocket.WebsocketSubscriptionId;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.GuavaCollectors;
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

@RestController
@RequestMapping(DebugWebsocketRestController.ENDPOINT)
public class DebugWebsocketRestController
{
	public static final String ENDPOINT = DebugRestController.ENDPOINT + "/websocket";

	private final UserSession userSession;
	private WebsocketSender websocketSender;
	private WebsocketActiveSubscriptionsIndex websocketActiveSubscriptionsIndex;

	public DebugWebsocketRestController(
			@NonNull final UserSession userSession,
			@NonNull final WebsocketSender websocketSender,
			@NonNull final WebsocketActiveSubscriptionsIndex websocketActiveSubscriptionsIndex)
	{
		this.userSession = userSession;
		this.websocketSender = websocketSender;
		this.websocketActiveSubscriptionsIndex = websocketActiveSubscriptionsIndex;
	}

	private void assertLoggedIn()
	{
		userSession.assertLoggedIn();
	}

	@PostMapping("/post")
	public void postToWebsocket(
			@RequestParam("endpoint") final String endpoint,
			@RequestBody final String messageStr)
	{
		assertLoggedIn();

		final Charset charset = Charset.forName("UTF-8");
		final Map<String, Object> headers = ImmutableMap.<String, Object> builder()
				.put("simpMessageType", SimpMessageType.MESSAGE)
				.put("contentType", new MimeType("application", "json", charset))
				.build();
		final Message<?> message = new GenericMessage<>(messageStr.getBytes(charset), headers);
		websocketSender.sendMessage(
				WebsocketTopicName.ofString(endpoint),
				message);
	}

	@PostMapping("/view/fireRowsChanged")
	public void sendWebsocketViewChangedNotification(
			@RequestParam("viewId") final String viewIdStr,
			@RequestParam("changedIds") final String changedIdsStr)
	{
		assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final DocumentIdsSelection changedRowIds = DocumentIdsSelection.ofCommaSeparatedString(changedIdsStr);
		sendWebsocketViewChangedNotification(viewId, changedRowIds);
	}

	void sendWebsocketViewChangedNotification(
			final ViewId viewId,
			final DocumentIdsSelection changedRowIds)
	{
		final ViewChanges viewChanges = new ViewChanges(viewId);
		viewChanges.addChangedRowIds(changedRowIds);
		JSONViewChanges jsonViewChanges = JSONViewChanges.of(viewChanges);

		final WebsocketTopicName endpoint = WebsocketTopicNames.buildViewNotificationsTopicName(jsonViewChanges.getViewId());
		try
		{
			websocketSender.convertAndSend(endpoint, jsonViewChanges);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("json", jsonViewChanges);
		}
	}

	@GetMapping("/logging/config")
	public void setWebsocketLoggingConfig(
			@RequestParam("enabled") final boolean enabled,
			@RequestParam(value = "maxLoggedEvents", defaultValue = "500") final int maxLoggedEvents)
	{
		userSession.assertLoggedIn();

		websocketSender.setLogEventsEnabled(enabled);
		websocketSender.setLogEventsMaxSize(maxLoggedEvents);
	}

	@GetMapping("/logging/events")
	public List<WebsocketEventLogRecord> getWebsocketLoggedEvents(
			@RequestParam(value = "destinationFilter", required = false) final String destinationFilter)
	{
		userSession.assertLoggedIn();

		return websocketSender.getLoggedEvents(destinationFilter);
	}

	@GetMapping("/activeSubscriptions")
	public Map<String, ?> getActiveSubscriptions()
	{
		userSession.assertLoggedIn();

		final ImmutableMap<WebsocketTopicName, Collection<WebsocketSubscriptionId>> map = websocketActiveSubscriptionsIndex.getSubscriptionIdsByTopicName().asMap();

		final ImmutableMap<String, ImmutableList<String>> stringsMap = map.entrySet()
				.stream()
				.map(entry -> GuavaCollectors.entry(
						entry.getKey().getAsString(), // topic
						entry.getValue().stream()
								.map(WebsocketSubscriptionId::getAsString)
								.collect(ImmutableList.toImmutableList())))
				.collect(GuavaCollectors.toImmutableMap());

		return stringsMap;
	}
}
