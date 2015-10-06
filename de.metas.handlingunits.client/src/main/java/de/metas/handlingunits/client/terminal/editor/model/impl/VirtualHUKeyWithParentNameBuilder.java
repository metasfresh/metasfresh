package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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


import org.adempiere.util.Check;
import org.compiere.util.Util;

import de.metas.handlingunits.model.I_M_HU;

/**
 * Will do pretty much the same as the ordinary {@link HUKeyNameBuilder}, but it's supposed to display more relevant information to the user (i.e to what IFCO this VPI belongs to).
 *
 * @author al
 */
/* package */class VirtualHUKeyWithParentNameBuilder extends HUKeyNameBuilder
{
	private String htmlPartHUDisplayName = null;

	public VirtualHUKeyWithParentNameBuilder(final HUKey huKey)
	{
		super(huKey);

		Check.assume(huKey.isVirtualPI(), "huKey is a virtualPI: {0}", huKey);
	}

	@Override
	protected final String getHTMLPartHUDisplayName()
	{
		if (htmlPartHUDisplayName == null)
		{
			final I_M_HU parent = handlingUnitsDAO.retrieveParent(getM_HU());

			//
			// Use just the HU's value; do not display the full HU to the user to preserve much-needed space on the key
			final String displayName = parent.getValue(); // handlingUnitsBL.getDisplayName(parent);
			htmlPartHUDisplayName = Util.maskHTML(displayName);
		}
		return htmlPartHUDisplayName;
	}
}
