package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableSet;
import de.metas.mpackage.PackageId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Deprecated
public class DeliveryPosition
{
	int repoId;

	int numberOfPackages;
	BigDecimal grossWeightKg;
	String content;

	@Nullable
	PackageDimensions packageDimensions;

	@Nullable
	CustomDeliveryData customDeliveryData;

	ImmutableSet<PackageId> packageIds;

	@Builder(toBuilder = true)
	private DeliveryPosition(
			final int repoId,
			final int numberOfPackages,
			@NonNull final BigDecimal grossWeightKg,
			final String content,
			@Nullable final PackageDimensions packageDimensions,
			@Nullable final CustomDeliveryData customDeliveryData,
			@Singular final ImmutableSet<PackageId> packageIds)
	{
		Check.assume(numberOfPackages > 0, "numberOfPackages > 0");
		Check.assume(grossWeightKg.signum() > 0, "grossWeightKg > 0");
		//Check.assumeNotEmpty(content, "content is not empty");

		this.repoId = repoId;
		this.numberOfPackages = numberOfPackages;
		this.grossWeightKg = grossWeightKg;
		this.content = content;
		this.packageDimensions = packageDimensions;
		this.customDeliveryData = customDeliveryData;
		this.packageIds = packageIds;
	}
}
