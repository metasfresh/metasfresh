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


import java.util.List;

import org.adempiere.util.Services;
import org.compiere.util.Util;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.model.I_M_HU_PI;

public class CompositeHUKeyNameBuilder extends AbstractHUKeyNameBuilder<CompositeHUKey>
{
	private String htmlPartChildrenCount = null;
	private String lastBuiltName = null;

	public CompositeHUKeyNameBuilder(final CompositeHUKey key)
	{
		super(key);
	}

	@Override
	public String getLastBuiltName()
	{
		return lastBuiltName;
	}

	private final List<IHUKey> getChildren()
	{
		return getKey().getChildren();
	}

	@Override
	public String build()
	{
		final String childrenCountHtml = getHTMLPartChildrenCount();

		// TODO: display Weight, display products

		lastBuiltName = childrenCountHtml;

		return lastBuiltName;
	}

	private String getHTMLPartChildrenCount()
	{
		if (htmlPartChildrenCount != null)
		{
			return htmlPartChildrenCount;
		}

		final List<IHUKey> children = getChildren();
		final int count = children.size();
		String piName = null;
		for (final IHUKey child : children)
		{
			final HUKey huKey = (HUKey)child;
			if (piName == null)
			{
				final I_M_HU_PI huPI = Services.get(IHandlingUnitsBL.class).getPI(huKey.getM_HU());
				piName = huPI.getName();
				break;
			}
		}

		final StringBuilder sb = new StringBuilder();
		if (count == 0)
		{
			sb.append("(empty)"); // FIXME trl
		}
		else
		{
			sb.append(count).append(" x ").append(Util.maskHTML(piName));
		}

		htmlPartChildrenCount = sb.toString();
		return htmlPartChildrenCount;
	}

	@Override
	public void reset()
	{
		htmlPartChildrenCount = null;
	}

	@Override
	public boolean isStale()
	{
		return htmlPartChildrenCount == null;
	}

	@Override
	public void notifyAttributeValueChanged(final IAttributeValue attributeValue)
	{
		// this builder is not affected by any attribute
	}

	@Override
	public void notifyChildChanged(final IHUKey childKey)
	{
		htmlPartChildrenCount = null;
	}
}
