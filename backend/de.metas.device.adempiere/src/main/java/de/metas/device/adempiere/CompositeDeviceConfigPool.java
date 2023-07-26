package de.metas.device.adempiere;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeCode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.device.adempiere
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

@ToString
public class CompositeDeviceConfigPool implements IDeviceConfigPool
{
	public static IDeviceConfigPool of(@NonNull final Collection<IDeviceConfigPool> pools)
	{
		Check.assumeNotEmpty(pools, "pools is not empty");

		if (pools.size() == 1)
		{
			return pools.iterator().next();
		}
		else
		{
			return new CompositeDeviceConfigPool(pools);
		}
	}

	public static IDeviceConfigPool compose(
			@NonNull final IDeviceConfigPool pool1,
			@NonNull final IDeviceConfigPool pool2)
	{
		return new CompositeDeviceConfigPool(ImmutableList.of(pool1, pool2));
	}

	private final ImmutableList<IDeviceConfigPool> pools;

	private CompositeDeviceConfigPool(@NonNull final Collection<IDeviceConfigPool> pools)
	{
		this.pools = ImmutableList.copyOf(pools);
	}

	@Override
	public void addListener(@NonNull final IDeviceConfigPoolListener listener)
	{
		pools.forEach(pool -> pool.addListener(listener));
	}

	@Override
	public Set<AttributeCode> getAllAttributeCodes()
	{
		return pools.stream()
				.flatMap(pool -> pool.getAllAttributeCodes().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<DeviceConfig> getDeviceConfigsForAttributeCode(@NonNull final AttributeCode attributeCode)
	{
		return pools.stream()
				.flatMap(pool -> pool.getDeviceConfigsForAttributeCode(attributeCode).stream())
				.collect(ImmutableList.toImmutableList());
	}

}
