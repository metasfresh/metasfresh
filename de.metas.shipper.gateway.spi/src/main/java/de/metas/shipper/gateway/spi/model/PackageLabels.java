package de.metas.shipper.gateway.spi.model;

import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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

/**
 * Labels for one package.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class PackageLabels
{
	private final OrderId orderId;
	private final HWBNumber hwbNumber;
	private final PackageLabelType defaultLabelType;
	@Getter(AccessLevel.NONE)
	private final ImmutableMap<PackageLabelType, PackageLabel> labels;

	@Builder
	private PackageLabels(
			@NonNull final OrderId orderId,
			@NonNull final HWBNumber hwbNumber,
			@NonNull final PackageLabelType defaultLabelType,
			@NonNull @Singular final ImmutableList<PackageLabel> labels)
	{
		Check.assumeNotEmpty(labels, "labels is not empty");

		this.orderId = orderId;
		this.hwbNumber = hwbNumber;
		this.defaultLabelType = defaultLabelType;
		this.labels = Maps.uniqueIndex(labels, PackageLabel::getType);
		Check.assume(this.labels.containsKey(defaultLabelType), "defaultLabelType={} shall be present in {}", defaultLabelType, labels);
	}

	public Set<PackageLabelType> getLabelTypes()
	{
		return labels.keySet();
	}

	public PackageLabel getPackageLabel(final PackageLabelType type)
	{
		final PackageLabel packageLabel = labels.get(type);
		Check.assumeNotNull(packageLabel, "package label of type={} shall exist in {}", type, labels);
		return packageLabel;
	}

	public PackageLabel getDefaultPackageLabel()
	{
		return getPackageLabel(getDefaultLabelType());
	}
}
