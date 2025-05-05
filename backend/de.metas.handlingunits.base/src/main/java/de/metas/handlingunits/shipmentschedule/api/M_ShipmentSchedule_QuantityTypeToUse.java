package de.metas.handlingunits.shipmentschedule.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
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

public enum M_ShipmentSchedule_QuantityTypeToUse implements ReferenceListAwareEnum
{
	/**
	 * only use the shipment schedule's picked qty (which is based on the actually picked HUs).
	 */
	TYPE_PICKED_QTY("P"),

	/**
	 * only use the shipment schedule's qty to deliver.
	 */
	TYPE_QTY_TO_DELIVER("D"),

	/**
	 * use both picked and shipment schedule's qty to deliver.
	 */
	TYPE_BOTH("PD"),

	TYPE_SPLIT_SHIPMENT("SPLIT_SHIPMENT"),
	
	;

	@Getter
	private final String code;

	private static final ValuesIndex<M_ShipmentSchedule_QuantityTypeToUse> index = ReferenceListAwareEnums.index(values());

	private M_ShipmentSchedule_QuantityTypeToUse(final String code)
	{
		this.code = code;
	}

	public static M_ShipmentSchedule_QuantityTypeToUse ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isUseBoth()
	{
		return TYPE_BOTH == this;
	}

	public boolean isOnlyUsePicked()
	{
		return TYPE_PICKED_QTY == this;
	}

	public boolean isOnlyUseToDeliver()
	{
		return TYPE_QTY_TO_DELIVER == this;
	}
}
