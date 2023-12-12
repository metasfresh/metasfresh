package de.metas.device.accessor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

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

/**
 * List of {@link DeviceAccessor}s.
 * <p>
 * It also contains other information like warningMessage.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class DeviceAccessorsList
{
	static DeviceAccessorsList of(final List<DeviceAccessor> deviceAccessors, final String warningMessage)
	{
		final String warningMessageNorm = StringUtils.trimBlankToNull(warningMessage);
		if (deviceAccessors.isEmpty() && warningMessage == null)
		{
			return EMPTY;
		}

		return new DeviceAccessorsList(deviceAccessors, warningMessageNorm);
	}

	public static Collector<DeviceAccessor, ?, DeviceAccessorsList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(list -> of(list, null));
	}

	static final DeviceAccessorsList EMPTY = new DeviceAccessorsList();

	@NonNull private final ImmutableList<DeviceAccessor> list;
	@NonNull private final ImmutableMap<DeviceId, DeviceAccessor> byId;
	@NonNull private final Optional<String> warningMessage;

	private DeviceAccessorsList(@NonNull final List<DeviceAccessor> list, @Nullable final String warningMessage)
	{
		this.list = ImmutableList.copyOf(list);
		this.byId = Maps.uniqueIndex(list, DeviceAccessor::getId);
		this.warningMessage = StringUtils.trimBlankToOptional(warningMessage);
	}

	/**
	 * empty/null constructor
	 */
	private DeviceAccessorsList()
	{
		this.list = ImmutableList.of();
		this.byId = ImmutableMap.of();
		this.warningMessage = Optional.empty();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("warningMessage", warningMessage)
				.addValue(list.isEmpty() ? null : list)
				.toString();
	}

	public Stream<DeviceAccessor> stream()
	{
		return list.stream();
	}

	public Stream<DeviceAccessor> stream(final WarehouseId warehouseId)
	{
		return stream().filter(deviceAccessor -> deviceAccessor.isAvailableForWarehouse(warehouseId));
	}

	public DeviceAccessor getByIdOrNull(final DeviceId id)
	{
		return byId.get(id);
	}

	/**
	 * Convenient (fluent) method to consume the warningMessage if any.
	 */
	public void consumeWarningMessageIfAny(final Consumer<String> warningMessageConsumer)
	{
		warningMessage.ifPresent(warningMessageConsumer);
	}
}
