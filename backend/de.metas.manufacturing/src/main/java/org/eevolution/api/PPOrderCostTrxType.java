package org.eevolution.api;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.X_PP_Order_Cost;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public enum PPOrderCostTrxType
{
	MainProduct(X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_MainProduct, true), //
	ByProduct(X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_ByProduct, true), //
	CoProduct(X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_CoProduct, true), //
	MaterialIssue(X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_MaterialIssue, false), //
	ResourceUtilization(X_PP_Order_Cost.PP_ORDER_COST_TRXTYPE_ResourceUtilization, false), //
	;

	@Getter
	private String code;
	@Getter
	private boolean outboundCost;

	private PPOrderCostTrxType(final String code, final boolean outboundCost)
	{
		this.code = code;
	}

	public static PPOrderCostTrxType ofCode(@NonNull final String code)
	{
		PPOrderCostTrxType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PPOrderCostTrxType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PPOrderCostTrxType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PPOrderCostTrxType::getCode);

	public static PPOrderCostTrxType ofBOMComponentType(@NonNull final BOMComponentType bomComponentType)
	{
		if (bomComponentType.isByProduct())
		{
			return ByProduct;
		}
		else if (bomComponentType.isCoProduct())
		{
			return CoProduct;
		}
		else if (bomComponentType.isIssue())
		{
			return MaterialIssue;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + bomComponentType + " to " + PPOrderCostTrxType.class.getName());
		}
	}

	public boolean isCoProduct()
	{
		return this == CoProduct;
	}
}
