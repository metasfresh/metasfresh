/**
 * 
 */
package de.metas.web.desktop;

/*
 * #%L
 * de.metas.swat.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.webui.desktop.DefaultDesktop;
import org.zkoss.zk.ui.Component;

import de.metas.web.component.trl.TranslatableLabelSupport;
import de.metas.web.component.trl.TrlDesktopHeaderGadget;

/**
 * Generic Metas Desktop
 * 
 * @author tsa
 * 
 */
public class MetasDesktop extends DefaultDesktop
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6248864444064802601L;

	public MetasDesktop()
	{
		super();
	}

	@Override
	protected Component doCreatePart(final Component parent)
	{
		final Component comp = super.doCreatePart(parent);

		createAdditionalHeaderPanels();

		return comp;
	}

	private void createAdditionalHeaderPanels()
	{
		final Component panel = getHeaderPanel().getAdditionalPanel();

		if (TranslatableLabelSupport.isEnabled())
		{
			panel.appendChild(new TrlDesktopHeaderGadget());
		}
	}
}
