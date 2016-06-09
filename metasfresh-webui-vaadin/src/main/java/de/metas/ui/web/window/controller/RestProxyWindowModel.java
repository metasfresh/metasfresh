package de.metas.ui.web.window.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.metas.logging.LogManager;
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

	private RestTemplate restTemplate = new RestTemplate();
	private Integer windowNo;

	public RestProxyWindowModel()
	{
		super();

		final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter(JsonHelper.createObjectMapper()));
		restTemplate = new RestTemplate(messageConverters);
	}

	@Override
	public void setRootPropertyDescriptorFromWindow(final int windowId)
	{
		windowNo = restTemplate.getForObject(buildURL("/openWindow/" + windowId), Integer.class);
	}

	@Override
	public void subscribe(final Object subscriberObj)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(final Object subscriberObj)
	{
		// TODO Auto-generated method stub

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
}
