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

import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;

/**
 * {@link HUKey} with a {@link VirtualHUKeyWithParentNameBuilder} to display more relevant information to the user.
 *
 * @author al
 */
public class VirtualHUKeyWithParent extends HUKey
{
	public VirtualHUKeyWithParent(final IHUKeyFactory keyFactory, final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		super(keyFactory, hu, documentLine);

		Check.assume(hu.getM_HU_Item_Parent_ID() > 0, "hu has a parent ({0})", hu);
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		return new VirtualHUKeyWithParentNameBuilder(this);
	}
}
