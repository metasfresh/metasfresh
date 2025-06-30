/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@AllArgsConstructor
@Getter
public enum ShipmentDateRule implements ReferenceListAwareEnum
{
	Today("T"),
	DeliveryDate("D"),
	DeliveryDateOrToday("O");

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<ShipmentDateRule> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static ShipmentDateRule ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Value
	@Builder
	public static class ShipmentDateRuleFlags
	{
		boolean isShipDateToday;
		boolean isShipDateDeliveryDay;
	}

	public ShipmentDateRuleFlags getFlags()
	{
		return switch (this)
		{
			case Today -> ShipmentDateRuleFlags.builder()
					.isShipDateToday(true)
					.isShipDateDeliveryDay(false)
					.build();
			case DeliveryDate -> ShipmentDateRuleFlags.builder()
					.isShipDateToday(false)
					.isShipDateDeliveryDay(true)
					.build();
			case DeliveryDateOrToday -> ShipmentDateRuleFlags.builder()
					.isShipDateToday(false)
					.isShipDateDeliveryDay(false)
					.build();
		};
	}
}
