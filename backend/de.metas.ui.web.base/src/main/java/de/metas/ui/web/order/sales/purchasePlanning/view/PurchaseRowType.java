package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.view.IViewRowType;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
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

public enum PurchaseRowType implements IViewRowType
{
	GROUP("G", PPOrderLineType.MainProduct.getIconName()), //
	LINE("L", PPOrderLineType.BOMLine_Component.getIconName()), //
	AVAILABILITY_DETAIL("A", PPOrderLineType.BOMLine_ByCoProduct.getIconName());

	@Getter
	private final String code;
	private final String iconName;

	PurchaseRowType(@NonNull final String code, @NonNull final String iconName)
	{
		this.code = code;
		this.iconName = iconName;
	}

	@Override
	public String getName()
	{
		return iconName;
	}

	@Override
	public String getIconName()
	{
		return iconName;
	}

	public static PurchaseRowType ofCode(final String code)
	{
		final PurchaseRowType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PurchaseRowType.class.getName() + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, PurchaseRowType> typesByCode = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(PurchaseRowType::getCode));
}
