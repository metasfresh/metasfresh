package de.metas.handlingunits.hutransaction;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.X_M_HU_Trx_Attribute;
import de.metas.order.DeliveryRule;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public enum HUTransactionAttributeOperation implements ReferenceListAwareEnum
{
	SAVE(X_M_HU_Trx_Attribute.OPERATION_Save), //
	DROP(X_M_HU_Trx_Attribute.OPERATION_Drop) //
	;

	@Getter
	private final String code;

	HUTransactionAttributeOperation(@NonNull final String code)
	{
		this.code = code;
	}

	public static HUTransactionAttributeOperation ofCode(@NonNull final String code)
	{
		final HUTransactionAttributeOperation type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUTransactionAttributeOperation.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, HUTransactionAttributeOperation> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), HUTransactionAttributeOperation::getCode);

	public static String toCodeOrNull(final DeliveryRule type)
	{
		return type != null ? type.getCode() : null;
	}
}
