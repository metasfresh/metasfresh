package de.metas.ui.web.pporder;

import de.metas.ui.web.view.IDocumentViewType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public enum PPOrderLineType implements IDocumentViewType
{
	MainProduct("MainProduct")
	, BOMLine_Component("BOM_Component")
	, BOMLine_ByCoProduct("BOM_ByCoProduct")
	, HU("HU")
	;
	
	private final String name;

	private PPOrderLineType(final String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	public static final PPOrderLineType cast(final IDocumentViewType type)
	{
		return (PPOrderLineType)type;
	}
	
	public boolean canReceive()
	{
		return MainProduct == this || BOMLine_ByCoProduct == this;
	}
	
	public boolean canIssue()
	{
		return BOMLine_Component == this;
	}

}
