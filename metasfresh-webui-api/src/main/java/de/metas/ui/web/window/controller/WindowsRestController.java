package de.metas.ui.web.window.controller;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.compiere.util.Env;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.window.PropertyNameSet;
import de.metas.ui.web.window.datasource.SaveResult;
import de.metas.ui.web.window.model.WindowModel;
import de.metas.ui.web.window.model.WindowModelImpl;
import de.metas.ui.web.window.shared.action.ActionsList;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.datatype.PropertyPathSet;
import de.metas.ui.web.window.shared.datatype.PropertyPathValue;
import de.metas.ui.web.window.shared.datatype.PropertyPathValuesDTO;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;
import io.swagger.annotations.ApiOperation;

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

@RestController
@RequestMapping(value = WindowsRestController.ENDPOINT)
public class WindowsRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/windows";

	private final AtomicInteger _nextWindowNo = new AtomicInteger(1);
	private final Map<Integer, WindowModelInstance> _windowModels = Maps.newConcurrentMap();

	private final void autologin()
	{
		// FIXME: only for testing
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_DE");
	}

	@RequestMapping(value = "/openWindow/{adWindowId}", method = RequestMethod.GET)
	@ApiOperation(value = "Creates a new window instance", notes = "Returns the WindowNo.")
	public int openWindow(@PathVariable("adWindowId") final int adWindowId)
	{
		autologin();

		final int windowNo = _nextWindowNo.getAndIncrement();

		final WindowModelInstance windowModelInstance = new WindowModelInstance(windowNo, adWindowId);
		_windowModels.put(windowNo, windowModelInstance);

		return windowNo;
	}

	@RequestMapping(value = "/closeWindow/{windowNo}", method = RequestMethod.GET)
	public void closeWindow(@PathVariable("windowNo") final int windowNo)
	{
		autologin();

		final WindowModelInstance windowModelInstance = _windowModels.remove(windowNo);
		if (windowModelInstance == null)
		{
			throw new IllegalArgumentException("No window model found for " + windowNo);
		}

		windowModelInstance.dispose();
	}

	private final WindowModel getWindowModel(final int windowNo)
	{
		autologin();

		final WindowModelInstance windowModelInstance = _windowModels.get(windowNo);
		if (windowModelInstance == null)
		{
			throw new IllegalArgumentException("No window model found for " + windowNo);
		}
		return windowModelInstance.getWindowModel();
	}

	@RequestMapping(value = "/getViewRootPropertyDescriptor/{windowNo}", method = RequestMethod.GET)
	public ViewPropertyDescriptor getViewRootPropertyDescriptor(@PathVariable("windowNo") final int windowNo)
	{
		return getWindowModel(windowNo).getViewRootPropertyDescriptor();
	}

	@RequestMapping(value = "/getPropertyValues/{windowNo}", method = RequestMethod.POST)
	public PropertyValuesDTO getPropertyValuesDTO(@PathVariable("windowNo") final int windowNo, @RequestBody final PropertyNameSet selectedPropertyNames)
	{
		final WindowModel windowModel = getWindowModel(windowNo);
		return windowModel.getPropertyValuesDTO(selectedPropertyNames);
	}

	@RequestMapping(value = "/getPropertyPathValues/{windowNo}", method = RequestMethod.POST)
	public PropertyPathValuesDTO getPropertyPathValuesDTO(@PathVariable("windowNo") final int windowNo, @RequestBody final PropertyPathSet selectedPropertyNames)
	{
		final WindowModel windowModel = getWindowModel(windowNo);
		return windowModel.getPropertyPathValuesDTO(selectedPropertyNames);
	}

	@RequestMapping(value = "/setProperty/{windowNo}", method = RequestMethod.POST)
	public void setProperty(@PathVariable("windowNo") final int windowNo, @RequestBody final PropertyPathValue propertyPathValue)
	{
		final PropertyPath propertyPath = propertyPathValue.getPropertyPath();
		final Object value = propertyPathValue.getValue();
		getWindowModel(windowNo).setProperty(propertyPath, value);
	}

	@RequestMapping(value = "/hasProperty/{windowNo}", method = RequestMethod.POST)
	public boolean hasProperty(@PathVariable("windowNo") final int windowNo, @RequestBody final PropertyPath propertyPath)
	{
		return getWindowModel(windowNo).hasProperty(propertyPath);
	}

	@RequestMapping(value = "/executeCommand/{windowNo}", method = RequestMethod.POST)
	public ViewCommandResult executeCommand(@PathVariable("windowNo") final int windowNo, @RequestBody final ViewCommand command) throws Exception
	{
		final ViewCommandResult result = getWindowModel(windowNo).executeCommand(command);
		return result;
	}

	@RequestMapping(value = "/executeAction/{windowNo}/{actionId}", method = RequestMethod.GET)
	public void executeAction(@PathVariable("windowNo") final int windowNo, @PathVariable("actionId") final String actionId)
	{
		getWindowModel(windowNo).executeAction(actionId);
	}

	@RequestMapping(value = "/getActions/{windowNo}", method = RequestMethod.GET)
	public ActionsList getActions(@PathVariable("windowNo") final int windowNo)
	{
		return getWindowModel(windowNo).getActions();
	}

	@RequestMapping(value = "/getChildActions/{windowNo}/{actionId}", method = RequestMethod.GET)
	public ActionsList getChildActions(@PathVariable("windowNo") final int windowNo, @PathVariable("actionId") final String actionId)
	{
		return getWindowModel(windowNo).getChildActions(actionId);
	}

	@RequestMapping(value = "/saveRecord/{windowNo}", method = RequestMethod.GET)
	public SaveResult saveRecord(@PathVariable("windowNo") final int windowNo)
	{
		return getWindowModel(windowNo).saveRecord();
	}

	@RequestMapping(value = "/hasPreviousRecord/{windowNo}", method = RequestMethod.GET)
	public boolean hasPreviousRecord(@PathVariable("windowNo") final int windowNo)
	{
		return getWindowModel(windowNo).hasPreviousRecord();
	}

	@RequestMapping(value = "/hasNextRecord/{windowNo}", method = RequestMethod.GET)
	public boolean hasNextRecord(@PathVariable("windowNo") final int windowNo)
	{
		return getWindowModel(windowNo).hasNextRecord();
	}

	private static class WindowModelInstance
	{
		// private final int windowNo;
		private final WindowModelImpl windowModel;
		private final WindowModelListener2WebSocket modelEventForwarder;

		public WindowModelInstance(final int windowNo, final int adWindowId)
		{
			super();

			// this.windowNo = windowNo;
			this.windowModel = new WindowModelImpl();
			windowModel.setRootPropertyDescriptorFromWindow(adWindowId);

			modelEventForwarder = new WindowModelListener2WebSocket(windowNo);
			windowModel.subscribe(modelEventForwarder);
		}

		public WindowModelImpl getWindowModel()
		{
			return windowModel;
		}

		public void dispose()
		{
			windowModel.unsubscribe(modelEventForwarder);

			// TODO: really dispose the model
		}
	}
}
