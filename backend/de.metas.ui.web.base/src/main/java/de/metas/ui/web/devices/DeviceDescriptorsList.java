package de.metas.ui.web.devices;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

@EqualsAndHashCode
@ToString
public class DeviceDescriptorsList
{
	public static final DeviceDescriptorsList EMPTY = new DeviceDescriptorsList();

	public static DeviceDescriptorsList ofList(@NonNull final List<DeviceDescriptor> list)
	{
		return !list.isEmpty() ? new DeviceDescriptorsList(list) : EMPTY;
	}

	private final ImmutableList<DeviceDescriptor> list;

	private DeviceDescriptorsList()
	{
		this.list = ImmutableList.of();
	}

	private DeviceDescriptorsList(@NonNull final List<DeviceDescriptor> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public Stream<DeviceDescriptor> stream()
	{
		return list.stream();
	}
	
	public ImmutableList<DeviceDescriptor> toList()
	{
		return list;
	}

}
