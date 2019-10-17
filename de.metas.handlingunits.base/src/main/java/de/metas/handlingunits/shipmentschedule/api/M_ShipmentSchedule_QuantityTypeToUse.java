package de.metas.handlingunits.shipmentschedule.api;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.GuavaCollectors;
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

public enum M_ShipmentSchedule_QuantityTypeToUse
{
	/** only use the shipment schedule's picked qty (which is based on the actually picked HUs). */
	TYPE_PICKED_QTY("P"),

	/** only use the shipment schedule's qty to deliver. */
	TYPE_QTY_TO_DELIVER("D"),

	/** use both picked and shipment schedule's qty to deliver. */
	TYPE_BOTH("PD");

	@Getter
	private final String code;

	private M_ShipmentSchedule_QuantityTypeToUse(final String code)
	{
		this.code = code;
	}

	public static M_ShipmentSchedule_QuantityTypeToUse forCode(@NonNull final String code)
	{
		final M_ShipmentSchedule_QuantityTypeToUse type = code2type.get(code);

		if (type == null)
		{
			throw new AdempiereException("No " + M_ShipmentSchedule_QuantityTypeToUse.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, M_ShipmentSchedule_QuantityTypeToUse> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(M_ShipmentSchedule_QuantityTypeToUse::getCode));

	public boolean isUseBoth()
	{
		return TYPE_BOTH.equals(forCode(code));
	}

	public boolean isOnlyUsePicked()
	{
		return TYPE_PICKED_QTY.equals(forCode(code));
	}

	public boolean isOnlyUseToDeliver()
	{
		return TYPE_QTY_TO_DELIVER.equals(forCode(code));
	}
}
