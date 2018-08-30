package de.metas.handlingunits.shipmentschedule.api;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum M_ShipmentSchedule_QuantityToUse
{

	TYPE_P("P"), //
	TYPE_D("D"), //
	TYPE_PD("PD");

	@Getter
	private final String code;

	private M_ShipmentSchedule_QuantityToUse(final String code)
	{
		this.code = code;
	}

	public static M_ShipmentSchedule_QuantityToUse forCode(@NonNull final String code)
	{
		final M_ShipmentSchedule_QuantityToUse type = code2type.get(code);

		if (type == null)
		{
			throw new AdempiereException("No " + M_ShipmentSchedule_QuantityToUse.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, M_ShipmentSchedule_QuantityToUse> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(M_ShipmentSchedule_QuantityToUse::getCode));

}
