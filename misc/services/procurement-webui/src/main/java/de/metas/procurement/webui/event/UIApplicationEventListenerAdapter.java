package de.metas.procurement.webui.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.UI;

/*
 * #%L
 * metasfresh-procurement-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class UIApplicationEventListenerAdapter implements IApplicationEventListener
{
	private static final Logger logger = LoggerFactory.getLogger(UIApplicationEventListenerAdapter.class);
	private final UI ui;

	public UIApplicationEventListenerAdapter()
	{
		super();
		ui = UI.getCurrent();
		if (ui == null)
		{
			logger.warn("Failed getting the UI. Ignored.", new RuntimeException("trace"));
		}
	}

	@Override
	public final UI getUI()
	{
		return ui;
	}

	@Override
	public void onContractChanged(final ContractChangedEvent event)
	{
		// nothing
	}

	@Override
	public void onRfqChanged(final RfqChangedEvent event)
	{
		// nothing
	}

	@Override
	public void onProductSupplyChanged(final ProductSupplyChangedEvent event)
	{
		// nothing
	}
}
