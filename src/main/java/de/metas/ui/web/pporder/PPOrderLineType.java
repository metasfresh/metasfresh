package de.metas.ui.web.pporder;

import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.view.IViewRowType;

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

public enum PPOrderLineType implements IViewRowType
{
	MainProduct("MP", true) //
	, BOMLine_Component("CO", false) //
	, BOMLine_ByCoProduct("BY", true) //
	//
	, HU_LU(HUEditorRowType.LU) //
	, HU_TU(HUEditorRowType.TU) //
	, HU_VHU(HUEditorRowType.VHU) //
	, HU_Storage(HUEditorRowType.HUStorage) //
	;

	private final String name;
	private final String iconName;
	private final HUEditorRowType huType;
	
	private final boolean canReceive;
	private final boolean canIssue;

	private PPOrderLineType(final String name, final boolean canReceive)
	{
		this.name = name;
		this.iconName = canReceive ? "PP_Order_Receive" : "PP_Order_Issue"; // see https://github.com/metasfresh/metasfresh-webui-frontend/issues/675#issuecomment-297016790
		this.huType = null;
		
		this.canReceive = canReceive;
		this.canIssue = !canReceive;
	}

	private PPOrderLineType(HUEditorRowType huType)
	{
		this.name = huType.getName();
		this.iconName = huType.getIconName();
		this.huType = huType;
		
		canReceive = false;
		canIssue = false;
	}

	@JsonValue
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

	public static final PPOrderLineType cast(final IViewRowType type)
	{
		return (PPOrderLineType)type;
	}

	public boolean canReceive()
	{
		return canReceive;
	}

	public boolean canIssue()
	{
		return canIssue;
	}
	
	public boolean isBOMLine()
	{
		return this == BOMLine_Component || this == BOMLine_ByCoProduct;
	}
	
	public boolean isHUOrHUStorage()
	{
		return this == HU_LU
				|| this == HU_TU
				|| this == HU_VHU
				|| this == HU_Storage;
	}

	public static final PPOrderLineType ofHUEditorRowType(final HUEditorRowType huType)
	{
		PPOrderLineType type = huType2type.get(huType);
		if(type == null)
		{
			throw new IllegalArgumentException("No type found for " + huType);
		}
		return type;
	}
	
	private static final ImmutableMap<HUEditorRowType, PPOrderLineType> huType2type = Stream.of(values())
			.filter(type -> type.huType != null)
			.collect(GuavaCollectors.toImmutableMapByKey(type -> type.huType));
}
