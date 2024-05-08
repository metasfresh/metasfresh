package de.metas.product;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.product.model.X_M_Product_PlanningSchema;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public enum OnMaterialReceiptWithDestWarehouse implements ReferenceListAwareEnum
{
	CREATE_MOVEMENT(X_M_Product_PlanningSchema.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement), //
	CREATE_DISTRIBUTION_ORDER(X_M_Product_PlanningSchema.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder) //
	;

	private static final ImmutableMap<String, OnMaterialReceiptWithDestWarehouse> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	@Getter
	private String code;

	private OnMaterialReceiptWithDestWarehouse(final String code)
	{
		this.code = code;
	}

	public static OnMaterialReceiptWithDestWarehouse ofCode(@NonNull final String code)
	{
		final OnMaterialReceiptWithDestWarehouse type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + OnMaterialReceiptWithDestWarehouse.class + " found for: " + code);
		}
		return type;
	}

}
