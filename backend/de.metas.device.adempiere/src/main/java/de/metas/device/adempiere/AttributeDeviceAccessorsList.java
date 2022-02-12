package de.metas.device.adempiere;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.function.Consumer;
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
 * List of {@link AttributeDeviceAccessor}s.
 *
 * It also contains other information like warningMessage.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Immutable
public final class AttributeDeviceAccessorsList
{
	static AttributeDeviceAccessorsList of(final List<AttributeDeviceAccessor> attributeDeviceAccessors, final String warningMessage)
	{
		final String warningMessageNorm = Check.isEmpty(warningMessage, true) ? null : warningMessage;
		if (attributeDeviceAccessors.isEmpty() && warningMessage == null)
		{
			return EMPTY;
		}

		return new AttributeDeviceAccessorsList(attributeDeviceAccessors, warningMessageNorm);
	}

	static final AttributeDeviceAccessorsList EMPTY = new AttributeDeviceAccessorsList();

	private final ImmutableList<AttributeDeviceAccessor> attributeDeviceAccessors;
	private final ImmutableMap<DeviceId, AttributeDeviceAccessor> attributeDeviceAccessorsById;
	private final String warningMessage;

	private AttributeDeviceAccessorsList(final List<AttributeDeviceAccessor> attributeDeviceAccessors, final String warningMessage)
	{
		this.attributeDeviceAccessors = ImmutableList.copyOf(attributeDeviceAccessors);
		attributeDeviceAccessorsById = Maps.uniqueIndex(attributeDeviceAccessors, AttributeDeviceAccessor::getPublicId);
		this.warningMessage = warningMessage;
	}

	/** empty/null constructor */
	private AttributeDeviceAccessorsList()
	{
		attributeDeviceAccessors = ImmutableList.of();
		attributeDeviceAccessorsById = ImmutableMap.of();
		warningMessage = null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("warningMessage", warningMessage)
				.addValue(attributeDeviceAccessors.isEmpty() ? null : attributeDeviceAccessors)
				.toString();
	}

	public Stream<AttributeDeviceAccessor> stream()
	{
		return attributeDeviceAccessors.stream();
	}

	public Stream<AttributeDeviceAccessor> stream(final WarehouseId warehouseId)
	{
		return stream()
				.filter(attributeDeviceAccessor -> attributeDeviceAccessor.isAvailableForWarehouse(warehouseId));
	}

	public AttributeDeviceAccessor getByIdOrNull(final DeviceId id)
	{
		return attributeDeviceAccessorsById.get(id);
	}

	/**
	 * Convenient (fluent) method to consume the warningMessage if any.
	 */
	public void consumeWarningMessageIfAny(final Consumer<String> warningMessageConsumer)
	{
		if (warningMessage == null || Check.isBlank(warningMessage))
		{
			return;
		}

		warningMessageConsumer.accept(warningMessage);
	}
}
