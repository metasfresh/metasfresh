package de.metas.device.adempiere;

import java.util.Collection;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.DeviceConfig.Builder.IDeviceParameterValueSupplier;
import de.metas.device.adempiere.DeviceConfig.Builder.IDeviceRequestClassnamesSupplier;
import de.metas.device.api.IDevice;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.device.adempiere
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
 * {@link IDevice} configuration.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DeviceConfig
{
	public static DeviceConfig.Builder builder(@NonNull final String deviceName)
	{
		return new Builder(deviceName);
	}

	private final String deviceName;
	private final Set<AttributeCode> assignedAttributeCodes;
	private final String deviceClassname;
	private final IDeviceParameterValueSupplier parameterValueSupplier;
	private final IDeviceRequestClassnamesSupplier requestClassnamesSupplier;
	private final ImmutableSet<WarehouseId> assignedWarehouseIds;

	private DeviceConfig(final DeviceConfig.Builder builder)
	{
		deviceName = builder.getDeviceName();
		assignedAttributeCodes = builder.getAssignedAttributeCodes();
		deviceClassname = builder.getDeviceClassname();
		parameterValueSupplier = builder.getParameterValueSupplier();
		requestClassnamesSupplier = builder.getRequestClassnamesSupplier();
		assignedWarehouseIds = builder.getAssignedWareouseIds();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("deviceName", deviceName)
				.add("assignedAttributeCodes", assignedAttributeCodes)
				.add("deviceClassname", deviceClassname)
				.add("assignedWarehouseId", assignedWarehouseIds)
				.toString();
	}

	public String getDeviceName()
	{
		return deviceName;
	}

	public Set<AttributeCode> getAssignedAttributeCodes()
	{
		return assignedAttributeCodes;
	}

	public boolean isAvailableForAttributeCode(final AttributeCode attributeCode)
	{
		return assignedAttributeCodes.contains(attributeCode);
	}

	public String getDeviceClassname()
	{
		return deviceClassname;
	}

	public String getParameterValue(final String parameterName, final String defaultValue)
	{
		return parameterValueSupplier.getDeviceParamValue(deviceName, parameterName, defaultValue);
	}

	public Set<String> getRequestClassnames(final AttributeCode attributeCode)
	{
		return requestClassnamesSupplier.getDeviceRequestClassnames(deviceName, attributeCode);
	}

	/** @return warehouse IDs where this device is available; empty means that it's available to any warehouse */
	public ImmutableSet<WarehouseId> getAssignedWarehouseIds()
	{
		return assignedWarehouseIds;
	}

	public static final class Builder
	{
		private final String deviceName;
		private Collection<AttributeCode> assignedAttributeCodes;
		private String deviceClassname;
		private IDeviceParameterValueSupplier parameterValueSupplier;
		private IDeviceRequestClassnamesSupplier requestClassnamesSupplier;
		private Set<WarehouseId> assignedWareouseIds = null;

		private Builder(@NonNull final String deviceName)
		{
			Check.assumeNotEmpty(deviceName, "deviceName is not empty");
			this.deviceName = deviceName;
		}

		public DeviceConfig build()
		{
			return new DeviceConfig(this);
		}

		private String getDeviceName()
		{
			return deviceName;
		}

		public DeviceConfig.Builder setAssignedAttributeCodes(final Collection<AttributeCode> assignedAttributeCodes)
		{
			this.assignedAttributeCodes = assignedAttributeCodes;
			return this;
		}

		private Set<AttributeCode> getAssignedAttributeCodes()
		{
			Check.assumeNotEmpty(assignedAttributeCodes, "assignedAttributeCodes is not empty");
			return ImmutableSet.copyOf(assignedAttributeCodes);
		}

		public DeviceConfig.Builder setDeviceClassname(final String deviceClassname)
		{
			this.deviceClassname = deviceClassname;
			return this;
		}

		private String getDeviceClassname()
		{
			Check.assumeNotEmpty(deviceClassname, "deviceClassname is not empty");
			return deviceClassname;
		}

		@FunctionalInterface
		public interface IDeviceParameterValueSupplier
		{
			String getDeviceParamValue(final String deviceName, final String parameterName, final String defaultValue);
		}

		public DeviceConfig.Builder setParameterValueSupplier(final IDeviceParameterValueSupplier parameterValueSupplier)
		{
			this.parameterValueSupplier = parameterValueSupplier;
			return this;
		}

		private IDeviceParameterValueSupplier getParameterValueSupplier()
		{
			Check.assumeNotNull(parameterValueSupplier, "Parameter parameterValueSupplier is not null");
			return parameterValueSupplier;
		}

		@FunctionalInterface
		public interface IDeviceRequestClassnamesSupplier
		{
			Set<String> getDeviceRequestClassnames(final String deviceName, final AttributeCode attributeCode);
		}

		public DeviceConfig.Builder setRequestClassnamesSupplier(final IDeviceRequestClassnamesSupplier requestClassnamesSupplier)
		{
			this.requestClassnamesSupplier = requestClassnamesSupplier;
			return this;
		}

		private IDeviceRequestClassnamesSupplier getRequestClassnamesSupplier()
		{
			Check.assumeNotNull(requestClassnamesSupplier, "Parameter requestClassnamesSupplier is not null");
			return requestClassnamesSupplier;
		}

		public DeviceConfig.Builder setAssignedWarehouseIds(final Set<WarehouseId> assignedWareouseIds)
		{
			this.assignedWareouseIds = assignedWareouseIds;
			return this;
		}

		private ImmutableSet<WarehouseId> getAssignedWareouseIds()
		{
			return assignedWareouseIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(assignedWareouseIds);
		}
	}

}
