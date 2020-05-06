package de.metas.procurement.webui.util;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.cache.Cache;
import com.google.gwt.thirdparty.guava.common.cache.CacheBuilder;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.UI;

import elemental.json.JsonArray;

/*
 * #%L
 * de.metas.procurement.webui
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

/**
 * Helper class to add Swipe behavior to Vaadin components
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class SwipeHelper
{
	public static final SwipeHelper getCurrent()
	{
		final VaadinSession session = UI.getCurrent().getSession();
		SwipeHelper swipe = session.getAttribute(SwipeHelper.class);
		if (swipe == null)
		{
			swipe = new SwipeHelper();
			session.setAttribute(SwipeHelper.class, swipe);
		}
		return swipe;
	}

	private static final String JS_FUNC_OnSwipe = SwipeHelper.class.getName() + ".onSwipe";

	private final AtomicBoolean jsFunctionsRegistered = new AtomicBoolean(false);
	private final Cache<Component, ComponentSwipe> component2swipe = CacheBuilder.newBuilder()
			.weakKeys()
			.build();

	private final Cache<String, ComponentSwipe> componentId2swipe = CacheBuilder.newBuilder()
			.weakValues()
			.build();

	private SwipeHelper()
	{
		super();
	}

	public synchronized void enable(final Component component, final SwipeHandler handler)
	{
		Preconditions.checkNotNull(component, "component is null");
		final String componentId = component.getId();
		Preconditions.checkNotNull(componentId, "componentId is null");

		final ComponentSwipe swipe = new ComponentSwipe(componentId, handler);

		component2swipe.put(component, swipe);
		componentId2swipe.put(componentId, swipe);

		registerJavaScriptFunctionsIfNeeded();

		final JavaScript javaScript = JavaScript.getCurrent();
		javaScript.execute(""
				+ "if (!window.swipesMap) { window.swipesMap={}; }"
				//
				+ "window.swipesMap['" + componentId + "'] = Swiped.init({"
				+ "query: '#" + componentId + "'"
				+ ", left: 300"
				+ ", right: 300"
				+ ", tolerance: 200"
				+ ", onOpen: function() { " + JS_FUNC_OnSwipe + "('" + componentId + "'); }"
				+ "});"
				//
				+ "\n console.log('Enabled swiping for element: " + componentId + "');"
				);
	}

	@SuppressWarnings("serial")
	private final void registerJavaScriptFunctionsIfNeeded()
	{
		if (jsFunctionsRegistered.getAndSet(true))
		{
			return; // already registered
		}

		final JavaScript javaScript = JavaScript.getCurrent();
		javaScript.addFunction(JS_FUNC_OnSwipe, new JavaScriptFunction()
		{
			@Override
			public void call(final JsonArray arguments)
			{
				invokeOnSwipe(arguments);
			}
		});
	}

	private void invokeOnSwipe(final JsonArray arguments)
	{
		final String componentId = arguments.getString(0);
		final ComponentSwipe swipe = componentId2swipe.getIfPresent(componentId);
		if (swipe == null)
		{
			// TODO: thow exception/log
			return;
		}

		swipe.invokeOnSwipe();
	}

	public static final class ComponentSwipe
	{
		private final SwipeHandler handler;
		private final String componentId;

		public ComponentSwipe(final String componentId, final SwipeHandler handler)
		{
			super();

			this.componentId = componentId;

			Preconditions.checkNotNull(handler, "handler is null");
			this.handler = handler;
		}

		private void invokeOnSwipe()
		{
			handler.onSwipe(this);
		}

		public void open()
		{
			JavaScript.getCurrent().execute("window.swipesMap['" + componentId + "'].open();");
		}

		public void close()
		{
			JavaScript.getCurrent().execute("window.swipesMap['" + componentId + "'].close(); console.log('swipe.close for " + componentId + "');");
		}

		public void destroy()
		{
			JavaScript.getCurrent().execute("window.swipesMap['" + componentId + "'].destroy(false);"); // isRemoveNode=false
		}
	}

	public static interface SwipeHandler
	{
		void onSwipe(final ComponentSwipe swipe);
	}
}
