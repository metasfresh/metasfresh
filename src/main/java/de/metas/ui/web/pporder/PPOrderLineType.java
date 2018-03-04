package de.metas.ui.web.pporder;

import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.view.IViewRowType;
import lombok.NonNull;

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
	MainProduct("MP", true, I_PP_Order.Table_Name),
	BOMLine_Component("CO", false, I_PP_Order_BOMLine.Table_Name),
	BOMLine_ByCoProduct("BY", true, I_PP_Order_BOMLine.Table_Name),

	HU_LU(HUEditorRowType.LU, I_M_HU.Table_Name),
	HU_TU(HUEditorRowType.TU, I_M_HU.Table_Name), 
	HU_VHU(HUEditorRowType.VHU, I_M_HU.Table_Name), 
	HU_Storage(HUEditorRowType.HUStorage, I_M_HU_Storage.Table_Name)
	;

	private final String name;
	private final String iconName;
	private final HUEditorRowType huType;

	private final String tableName;

	private final boolean canReceive;
	private final boolean canIssue;

	private PPOrderLineType(
			@NonNull final String name,
			final boolean canReceive,
			@NonNull final String tableName)
	{
		this.name = name;
		this.iconName = canReceive ? "PP_Order_Receive" : "PP_Order_Issue"; // see https://github.com/metasfresh/metasfresh-webui-frontend/issues/675#issuecomment-297016790
		this.huType = null;
		this.tableName = tableName;

		this.canReceive = canReceive;
		this.canIssue = !canReceive;
	}

	private PPOrderLineType(
			@NonNull final HUEditorRowType huType,
			@NonNull final String tableName)
	{
		this.name = huType.getName();
		this.iconName = huType.getIconName();
		this.huType = huType;
		this.tableName = tableName;

		canReceive = false;
		canIssue = false;
	}

	@JsonValue
	@Override
	public String getName()
	{
		return name;
	}

	public String getTableName()
	{
		return tableName;
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
		if (type == null)
		{
			throw new IllegalArgumentException("No type found for " + huType);
		}
		return type;
	}

	private static final ImmutableMap<HUEditorRowType, PPOrderLineType> huType2type = Stream.of(values())
			.filter(type -> type.huType != null)
			.collect(GuavaCollectors.toImmutableMapByKey(type -> type.huType));
}
