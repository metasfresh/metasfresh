/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class DhlSequenceNumber
{
	private final String sequenceNumber;

	private DhlSequenceNumber(@NonNull final String sequenceNumber)
	{
		this.sequenceNumber = sequenceNumber;
	}

	@NonNull
	public static DhlSequenceNumber of(final int number)
	{
		return new DhlSequenceNumber(Integer.toString(number));
	}

	@NonNull
	public static DhlSequenceNumber of(@NonNull final String sequenceNumber)
	{
		return new DhlSequenceNumber(sequenceNumber);
	}

}
