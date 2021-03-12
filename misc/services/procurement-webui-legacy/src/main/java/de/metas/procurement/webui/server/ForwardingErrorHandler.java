package de.metas.procurement.webui.server;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorMessage;
import com.vaadin.ui.AbstractComponent;

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
public class ForwardingErrorHandler extends AbstractErrorHandler
{
	public static ForwardingErrorHandler of(final AbstractComponent targetComponent)
	{
		return new ForwardingErrorHandler(targetComponent);
	}

	private final AbstractComponent component;

	private ForwardingErrorHandler(final AbstractComponent component)
	{
		super();
		this.component = Preconditions.checkNotNull(component);
	}

	@Override
	protected void displayError(final ErrorMessage errorMessage, final ErrorEvent event)
	{
		if (component != null)
		{
			component.setComponentError(errorMessage);
		}
	}
}
