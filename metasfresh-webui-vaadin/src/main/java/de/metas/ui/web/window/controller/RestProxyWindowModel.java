package de.metas.ui.web.window.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.google.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.base.Throwables;

import de.metas.logging.LogManager;
import de.metas.ui.web.Application;
import de.metas.ui.web.WebSocketConfig;
import de.metas.ui.web.json.JsonHelper;
import de.metas.ui.web.window.PropertyNameSet;
import de.metas.ui.web.window.datasource.SaveResult;
import de.metas.ui.web.window.model.WindowModel;
import de.metas.ui.web.window.shared.action.ActionsList;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.datatype.PropertyPathSet;
import de.metas.ui.web.window.shared.datatype.PropertyPathValue;
import de.metas.ui.web.window.shared.datatype.PropertyPathValuesDTO;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;
import de.metas.ui.web.window.shared.event.ModelEvent;

/*
 * #%L
 * metasfresh-webui-vaadin
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

public class RestProxyWindowModel implements WindowModel
{
	private static final Logger logger = LogManager.getLogger(RestProxyWindowModel.class);

	private final EventBus eventBus = new EventBus(getClass().getName());

	private RestTemplate restTemplate;
	@Autowired
	private TaskScheduler taskScheduler;

	private Integer windowNo;

	private ListenableFuture<StompSession> futureWebSocketSession;

	public RestProxyWindowModel()
	{
		super();
		Application.autowire(this);

		// REST connection
		{
			final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			messageConverters.add(new MappingJackson2HttpMessageConverter(JsonHelper.createObjectMapper()));
			restTemplate = new RestTemplate(messageConverters);
			
			final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	        requestFactory.setReadTimeout(2000);
	        requestFactory.setConnectTimeout(2000);
	        restTemplate.setRequestFactory(requestFactory);
		}

		// WebSocket connection
		{
			final List<Transport> transports = new ArrayList<>(1);
			transports.add(new WebSocketTransport(new StandardWebSocketClient()));
			final WebSocketClient transport = new SockJsClient(transports);
			final WebSocketStompClient stompClient = new WebSocketStompClient(transport);

			stompClient.setMessageConverter(new MappingJackson2MessageConverter());
			stompClient.setTaskScheduler(taskScheduler); // for heartbeats, receipts

			final String url = "ws://127.0.0.1:8080" + WebSocketConfig.ENDPOINT;
			final StompSessionHandler handler = new RestProxyWindowModel_StompSessionHandler();
			futureWebSocketSession = stompClient.connect(url, handler);
		}
	}

	private final void subscribeToWindowNoWebSocket(final int windowNo)
	{
		try
		{
			final StompSession session = futureWebSocketSession.get(10, TimeUnit.SECONDS);
			session.subscribe(WebSocketConfig.buildWindowTopicName(windowNo), new RestProxyWindowModel_StompFrameHandler());
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	private static final String buildURL(final String path)
	{
		return "http://localhost:8080/rest/api/windows" + path;
	}

	private final String buildWindowNoURL(final String operation)
	{
		final String params = null;
		return buildWindowNoURL(operation, params);
	}

	private final String buildWindowNoURL(final String operation, final String params)
	{
		if (windowNo == null)
		{
			throw new IllegalStateException("Window not created yet");
		}
		String url = buildURL(operation + "/" + windowNo);

		if (params != null)
		{
			if (!params.startsWith("/"))
			{
				url += "/";
			}
			url += params;
		}

		return url;
	}

	@Override
	public void setRootPropertyDescriptorFromWindow(final int windowId)
	{
		final int windowNo = restTemplate.getForObject(buildURL("/openWindow/" + windowId), Integer.class);
		this.windowNo = windowNo;

		subscribeToWindowNoWebSocket(windowNo);
	}

	@Override
	public void subscribe(final Object subscriberObj)
	{
		// TODO: implement with WebSocket
		eventBus.register(subscriberObj);
	}

	@Override
	public void unsubscribe(final Object subscriberObj)
	{
		// TODO: implement with WebSocket
		eventBus.unregister(subscriberObj);
	}

	@Override
	public ViewPropertyDescriptor getViewRootPropertyDescriptor()
	{
		return restTemplate.getForObject(buildWindowNoURL("/getViewRootPropertyDescriptor"), ViewPropertyDescriptor.class);
	}

	@Override
	public boolean hasPreviousRecord()
	{
		return restTemplate.getForObject(buildWindowNoURL("/hasPreviousRecord"), Boolean.class);
	}

	@Override
	public boolean hasNextRecord()
	{
		return restTemplate.getForObject(buildWindowNoURL("/hasNextRecord"), Boolean.class);
	}

	@Override
	public PropertyValuesDTO getPropertyValuesDTO(final PropertyNameSet selectedPropertyNames)
	{
		return restTemplate.postForObject(buildWindowNoURL("/getPropertyValues"), selectedPropertyNames, PropertyValuesDTO.class);
	}

	@Override
	public PropertyPathValuesDTO getPropertyPathValuesDTO(final PropertyPathSet selectedPropertyPaths)
	{
		return restTemplate.postForObject(buildWindowNoURL("/getPropertyPathValues"), selectedPropertyPaths, PropertyPathValuesDTO.class);
	}

	@Override
	public boolean hasProperty(final PropertyPath propertyPath)
	{
		return restTemplate.postForObject(buildWindowNoURL("/hasProperty"), propertyPath, Boolean.class);
	}

	@Override
	public void setProperty(final PropertyPath propertyPath, final Object value)
	{
		final PropertyPathValue propertyPathValue = PropertyPathValue.of(propertyPath, value);
		restTemplate.postForObject(buildWindowNoURL("/setProperty"), propertyPathValue, Void.class);
	}

	@Override
	public Object getProperty(final PropertyPath propertyPath)
	{
		final PropertyPathSet selectedPropertyPaths = PropertyPathSet.of(propertyPath);
		final PropertyPathValuesDTO values = getPropertyPathValuesDTO(selectedPropertyPaths);
		if (!values.containsKey(propertyPath))
		{
			throw new RuntimeException("ProperyPath " + propertyPath + " does not exist");
		}
		return values.get(propertyPath);
	}

	@Override
	public Object getPropertyOrNull(final PropertyPath propertyPath)
	{
		final PropertyPathSet selectedPropertyPaths = PropertyPathSet.of(propertyPath);
		final PropertyPathValuesDTO values = getPropertyPathValuesDTO(selectedPropertyPaths);
		return values.get(propertyPath);
	}

	@Override
	public void newRecordAsCopyById(final Object recordId)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public SaveResult saveRecord()
	{
		return restTemplate.getForObject(buildWindowNoURL("saveRecord"), SaveResult.class);
	}

	@Override
	public ActionsList getActions()
	{
		return restTemplate.getForObject(buildWindowNoURL("/getActions"), ActionsList.class);
	}

	@Override
	public ActionsList getChildActions(final String actionId)
	{
		return restTemplate.getForObject(buildWindowNoURL("/getChildActions", actionId), ActionsList.class);
	}

	@Override
	public void executeAction(final String actionId)
	{
		restTemplate.getForObject(buildWindowNoURL("/executeAction", actionId), Void.class);
	}

	@Override
	public ViewCommandResult executeCommand(final ViewCommand command) throws Exception
	{
		final String url = buildWindowNoURL("/executeCommand");

		logger.debug("Executing command {} on ", command, url);
		final ViewCommandResult result = restTemplate.postForObject(url, command, ViewCommandResult.class);
		logger.debug("Got result for command on {}: {}", command, result);

		return result;
	}

	private final class RestProxyWindowModel_StompSessionHandler extends StompSessionHandlerAdapter
	{
		@Override
		public void afterConnected(final StompSession session, final StompHeaders connectedHeaders)
		{
			System.out.println("websocket-client after connected: session=" + session + ", headers=" + connectedHeaders);
		}

		@Override
		public void handleFrame(final StompHeaders headers, final Object payload)
		{
			System.out.println("websocket-client handleFrame: headers=" + headers + ", payload=" + payload);
		}

		@Override
		public void handleException(final StompSession session, final StompCommand command, final StompHeaders headers,
				final byte[] payload, final Throwable exception)
		{
			System.err.println("websocket-client handleException: session=" + session + ", command=" + command + ", headers=" + headers);
			exception.printStackTrace();
		}

		/**
		 * This implementation is empty.
		 */
		@Override
		public void handleTransportError(final StompSession session, final Throwable exception)
		{
			System.err.println("websocket-client handleTransportError: session=" + session);
			exception.printStackTrace();
		}

	}

	private final class RestProxyWindowModel_StompFrameHandler implements StompFrameHandler
	{

		@Override
		public Type getPayloadType(final StompHeaders headers)
		{
			return ModelEvent.class;
		}

		@Override
		public void handleFrame(final StompHeaders headers, final Object payload)
		{
			System.out.println("websocket-client - frame handler - handleFrame: headers=" + headers + ", payload=" + payload);

			final ModelEvent event = (ModelEvent)payload;
			eventBus.post(event);
		}

	}
}
