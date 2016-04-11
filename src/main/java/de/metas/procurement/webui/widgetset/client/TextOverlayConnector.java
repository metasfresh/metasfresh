package de.metas.procurement.webui.widgetset.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;

import de.metas.procurement.webui.widgetset.TextOverlay;

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

@SuppressWarnings("serial")
@Connect(TextOverlay.class)
public class TextOverlayConnector extends AbstractExtensionConnector
{
	public static final String CLASSNAME = "overlay-text";

	private final Element overlayElement = DOM.createSpan();

	public TextOverlayConnector()
	{
		overlayElement.setClassName(CLASSNAME);
	}

	@Override
	protected void extend(ServerConnector target)
	{
		// Get the extended widget
		final Widget widget = ((ComponentConnector)target).getWidget();
		widget.getElement().appendChild(overlayElement);
	}

	@Override
	public TextOverlayState getState()
	{
		return (TextOverlayState)super.getState();
	}

	@OnStateChange("text")
	void onTextChanged()
	{
		final String text = getState().text;
		overlayElement.setInnerText(text);
	}
}
