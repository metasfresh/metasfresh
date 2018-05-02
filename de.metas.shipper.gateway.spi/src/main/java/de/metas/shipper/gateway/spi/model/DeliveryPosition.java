package de.metas.shipper.gateway.spi.model;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
public class DeliveryPosition
{
	int repoId;

	int numberOfPackages;
	int grossWeightKg;
	String content;
	PackageDimensions packageDimensions;

	ImmutableSet<Integer> packageIds;

	@Builder(toBuilder = true)
	private DeliveryPosition(
			final int repoId,
			final int numberOfPackages,
			final int grossWeightKg,
			final String content,
			@Nullable final PackageDimensions packageDimensions,
			@NonNull @Singular final ImmutableSet<Integer> packageIds)
	{
		Check.assume(numberOfPackages > 0, "numberOfPackages > 0");
		Check.assume(grossWeightKg > 0, "grossWeightKg > 0");
		Check.assumeNotEmpty(content, "content is not empty");

		this.repoId = repoId;
		this.numberOfPackages = numberOfPackages;
		this.grossWeightKg = grossWeightKg;
		this.content = content;
		this.packageDimensions = packageDimensions;
		this.packageIds = packageIds;
	}
}
