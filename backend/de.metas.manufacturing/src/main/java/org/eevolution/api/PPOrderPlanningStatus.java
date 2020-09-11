package org.eevolution.api;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.X_PP_Order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum PPOrderPlanningStatus
{
	PLANNING(X_PP_Order.PLANNINGSTATUS_Planning), //
	REVIEW(X_PP_Order.PLANNINGSTATUS_Review), //
	COMPLETE(X_PP_Order.PLANNINGSTATUS_Complete), //
	;

	@Getter
	private final String code;

	PPOrderPlanningStatus(final String code)
	{
		this.code = code;
	}

	public static PPOrderPlanningStatus ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static PPOrderPlanningStatus ofCode(@NonNull final String code)
	{
		PPOrderPlanningStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PPOrderPlanningStatus.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PPOrderPlanningStatus> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PPOrderPlanningStatus::getCode);

}
