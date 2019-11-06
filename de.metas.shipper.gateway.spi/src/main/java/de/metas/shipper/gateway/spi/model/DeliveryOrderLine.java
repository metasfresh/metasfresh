/*
 * #%L
 * de.metas.shipper.gateway.spi
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

package de.metas.shipper.gateway.spi.model;

import de.metas.mpackage.PackageId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DeliveryOrderLine
{
	int repoId;

	@Nullable
	String content;

	private final int grossWeightKg;

	@Nullable
	PackageDimensions packageDimensions;

	@Nullable
	CustomDeliveryData customDeliveryData;

	@Nullable
	PackageId packageId;

	@Builder(toBuilder = true)
	private DeliveryOrderLine(
			final int repoId,
			final int grossWeightKg,
			@Nullable final String content,
			@Nullable final PackageDimensions packageDimensions,
			@Nullable final CustomDeliveryData customDeliveryData,
			@Nullable final PackageId packageId)
	{
		Check.assume(grossWeightKg > 0, "grossWeightKg > 0");

		this.repoId = repoId;
		this.grossWeightKg = grossWeightKg;
		this.content = content;
		this.packageDimensions = packageDimensions;
		this.customDeliveryData = customDeliveryData;
		this.packageId = packageId;
	}
}
