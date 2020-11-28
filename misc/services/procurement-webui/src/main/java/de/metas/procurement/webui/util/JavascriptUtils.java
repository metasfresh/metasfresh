package de.metas.procurement.webui.util;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.JavaScript;

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
 * Miscellaneous JavaScript helper methods.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class JavascriptUtils
{
	public static final String RESOURCE_MainJS = "vaadin://js/mfprocurement.js";
	public static final String RESOURCE_JQuery = "vaadin://js/jquery.min.js";
	public static final String RESOURCE_Swiped = "vaadin://js/swiped.min.js";

	/**
	 * Inject a JavaScript code snippet which will select the text content when the text field gains focus.
	 * 
	 * Please keep in mind:
	 * <ul>
	 * <li>This function requires {@link #RESOURCE_JQuery}.
	 * <li><code>component</code> has to have the ID set.
	 * </ul>
	 * 
	 * @param component
	 */
	public static void enableSelectAllOnFocus(final AbstractTextField component)
	{
		Preconditions.checkNotNull(component, "component is null");

		final String componentId = component.getId();
		Preconditions.checkNotNull(componentId, "componentId is null");

		Page.getCurrent().getJavaScript().execute("$('#" + componentId + "').focus(function(){$(this).select();});");
	}
	
	public static void setBeforePageUnloadMessage(final String message)
	{
		final JavaScript javaScript = Page.getCurrent().getJavaScript();
		if (message == null || message.trim().isEmpty())
		{
			javaScript.execute("$(window).off('beforeunload');");
		}
		else
		{
			// TODO: escape the message
			javaScript.execute("$(window).on('beforeunload', function() { return \"" + message + "\"; });");
		}
	}
}
