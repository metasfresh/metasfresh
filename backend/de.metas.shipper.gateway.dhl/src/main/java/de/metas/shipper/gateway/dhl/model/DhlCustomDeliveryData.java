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

import com.google.common.collect.ImmutableList;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class DhlCustomDeliveryData implements CustomDeliveryData
{
	@Singular
	@NonNull
	private final ImmutableList<DhlCustomDeliveryDataDetail> details;

	@NonNull
	public static DhlCustomDeliveryData cast(@NonNull final CustomDeliveryData customDeliveryData)
	{
		return (DhlCustomDeliveryData)customDeliveryData;
	}

	@NonNull
	public ImmutableList<DhlCustomDeliveryDataDetail> getDetails()
	{
		return ImmutableList.copyOf(details);
	}

	@NonNull
	public DhlCustomDeliveryDataDetail getDetailByPackageId(final int packageId)
	{
		//noinspection OptionalGetWithoutIsPresent
		return details.stream()
				.filter(it -> it.getPackageId() == packageId)
				.findFirst()
				.get();
	}

	@NonNull
	public DhlCustomDeliveryDataDetail getDetailBySequenceNumber(@NonNull final DhlSequenceNumber sequenceNumber)
	{
		//noinspection OptionalGetWithoutIsPresent
		return details.stream()
				.filter(it -> it.getSequenceNumber().equals(sequenceNumber))
				.findFirst()
				.get();
	}
}
