package de.metas.ui.web.pporder;

import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.Map;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum PPOrderLineRowType
{
	IssuedOrReceivedHU("IssuedOrReceivedHU"), //
	PP_Order(I_PP_Order.Table_Name), //
	PP_OrderBomLine(I_PP_Order_BOMLine.Table_Name), //
	Source_HU(I_M_Source_HU.Table_Name);

	@Getter
	private final String code;

	private PPOrderLineRowType(@NonNull final String code)
	{
		this.code = code;
	}

	public static PPOrderLineRowType forCode(@NonNull final String code)
	{
		final PPOrderLineRowType type = code2type.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PPOrderLineRowType.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, PPOrderLineRowType> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(PPOrderLineRowType::getCode));

	public boolean isPP_OrderBomLine() {return PP_OrderBomLine.equals(this);}

}
