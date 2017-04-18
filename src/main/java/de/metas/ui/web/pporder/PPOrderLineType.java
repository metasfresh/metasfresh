package de.metas.ui.web.pporder;

import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.handlingunits.HUDocumentViewType;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum PPOrderLineType implements IDocumentViewType
{
	MainProduct("MainProduct") //
	, BOMLine_Component("BOM_Component") //
	, BOMLine_ByCoProduct("BOM_ByCoProduct") //
	//
	, HU_LU(HUDocumentViewType.LU) //
	, HU_TU(HUDocumentViewType.TU) //
	, HU_VHU(HUDocumentViewType.VHU) //
	, HU_Storage(HUDocumentViewType.HUStorage) //
	;

	private final String name;
	private final String iconName;
	private final HUDocumentViewType huDocumentViewType;

	private PPOrderLineType(final String name)
	{
		this.name = name;
		this.iconName = HUDocumentViewType.LU.getIconName(); // FIXME: just use the LU icon for now
		this.huDocumentViewType = null;
	}

	private PPOrderLineType(HUDocumentViewType huType)
	{
		this.name = huType.getName();
		this.iconName = huType.getIconName();
		this.huDocumentViewType = huType;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getIconName()
	{
		return iconName;
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

	public static final PPOrderLineType ofHUDocumentViewType(final HUDocumentViewType huType)
	{
		PPOrderLineType type = huType2type.get(huType);
		if(type == null)
		{
			throw new IllegalArgumentException("No type found for " + huType);
		}
		return type;
	}
	
	private static final ImmutableMap<HUDocumentViewType, PPOrderLineType> huType2type = Stream.of(values())
			.filter(type -> type.huDocumentViewType != null)
			.collect(GuavaCollectors.toImmutableMapByKey(type -> type.huDocumentViewType));
}
