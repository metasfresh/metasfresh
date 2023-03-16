/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.spi.impl;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
public final class CalculateShippingDateRule
{
	/**
	 * The shipment date is today's date.
	 */
	public static final CalculateShippingDateRule TODAY = new CalculateShippingDateRule(Type.TODAY, null);

	/**
	 * the shipment date is the shipment schedule's effective delivery date, even if that date is in the past
	 */
	public static final CalculateShippingDateRule DELIVERY_DATE = new CalculateShippingDateRule(Type.DELIVERY_DATE, null);

	/**
	 * the shipment date is the shipment schedule's effective delivery date or today if that effective delivery date would be in the past.
	 */
	public static final CalculateShippingDateRule DELIVERY_DATE_OR_TODAY = new CalculateShippingDateRule(Type.DELIVERY_DATE_OR_TODAY, null);

	public static CalculateShippingDateRule fixedDate(@NonNull LocalDate fixedDate) {return new CalculateShippingDateRule(Type.FIXED_DATE, fixedDate);}

	private enum Type
	{
		TODAY,
		DELIVERY_DATE,
		DELIVERY_DATE_OR_TODAY,
		FIXED_DATE
	}

	@NonNull private final Type type;
	@Nullable private final LocalDate fixedDate;

	private CalculateShippingDateRule(@NonNull final Type type, @Nullable final LocalDate fixedDate)
	{
		this.type = type;
		this.fixedDate = fixedDate;
	}

	interface CaseMapper<T>
	{
		T today();

		T deliveryDate();

		T deliveryDateOrToday();

		T fixedDate(@NonNull LocalDate fixedDate);
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		switch (type)
		{
			case TODAY:
				return mapper.today();
			case DELIVERY_DATE:
				return mapper.deliveryDate();
			case DELIVERY_DATE_OR_TODAY:
				return mapper.deliveryDateOrToday();
			case FIXED_DATE:
				final LocalDate fixedDate = Check.assumeNotNull(this.fixedDate, "fixedDate is set");
				return mapper.fixedDate(fixedDate);
			default:
				throw new AdempiereException("Unknown type: " + this);
		}
	}
}
