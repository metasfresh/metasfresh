package de.metas.device.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.warehouse.WarehouseId;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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

@ToString(of = { "deviceName", "assignedAttributeCodes", "deviceClassname", "assignedWarehouseIds" })
public final class DeviceConfig
{
	public static DeviceConfig.Builder builder(@NonNull final String deviceName)
	{
		return new Builder(deviceName);
	}

	@Getter
	@NonNull private final String deviceName;

	@Getter
	private final ImmutableSet<AttributeCode> assignedAttributeCodes;

	@Getter
	private final String deviceClassname;
	private final IDeviceParameterValueSupplier parameterValueSupplier;
	private final IDeviceRequestClassnamesSupplier requestClassnamesSupplier;
	
	@Getter
	@NonNull
	private final ImmutableList<String> beforeHooksClassname;
	
	@Getter
	@NonNull
	private final ImmutableMap<String, String> deviceConfigParams;

	/**
	 * warehouse IDs where this device is available; empty means that it's available to any warehouse
	 */
	@Getter
	private final ImmutableSet<WarehouseId> assignedWarehouseIds;

	private DeviceConfig(final DeviceConfig.Builder builder)
	{
		deviceName = builder.getDeviceName();
		assignedAttributeCodes = builder.getAssignedAttributeCodes();
		deviceClassname = builder.getDeviceClassname();
		parameterValueSupplier = builder.getParameterValueSupplier();
		requestClassnamesSupplier = builder.getRequestClassnamesSupplier();
		assignedWarehouseIds = builder.getAssignedWarehouseIds();
		beforeHooksClassname = builder.getBeforeHooksClassname();
		deviceConfigParams = builder.getDeviceConfigParams();
	}

	public String getParameterValue(final String parameterName, final String defaultValue)
	{
		return parameterValueSupplier.getDeviceParamValue(deviceName, parameterName, defaultValue);
	}

	public Set<String> getRequestClassnames(final AttributeCode attributeCode)
	{
		return requestClassnamesSupplier.getDeviceRequestClassnames(deviceName, attributeCode);
	}

	public Optional<String> getDeviceConfigParamValue(@NonNull final String parameterName)
	{
		return deviceConfigParams.keySet()
				.stream()
				.filter(paramKey -> paramKey.contains(parameterName))
				.findFirst()
				.map(deviceConfigParams::get);
	}

	public static final class Builder
	{
		private final String deviceName;
		private Collection<AttributeCode> assignedAttributeCodes;
		private String deviceClassname;
		private IDeviceParameterValueSupplier parameterValueSupplier;
		private IDeviceRequestClassnamesSupplier requestClassnamesSupplier;
		private Set<WarehouseId> assignedWareouseIds = null;
		private ImmutableList<String> beforeHooksClassname;
		private ImmutableMap<String, String> deviceConfigParams;

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

		private ImmutableSet<AttributeCode> getAssignedAttributeCodes()
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

		private ImmutableSet<WarehouseId> getAssignedWarehouseIds()
		{
			return assignedWareouseIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(assignedWareouseIds);
		}

		@NonNull
		public DeviceConfig.Builder setBeforeHooksClassname(@NonNull final ImmutableList<String> beforeHooksClassname)
		{
			this.beforeHooksClassname = beforeHooksClassname;
			return this;
		}
		
		@NonNull
		private ImmutableList<String> getBeforeHooksClassname()
		{
			return Optional.ofNullable(beforeHooksClassname).orElseGet(ImmutableList::of);
		}

		@NonNull
		public DeviceConfig.Builder setDeviceConfigParams(@NonNull final ImmutableMap<String, String> deviceConfigParams)
		{
			this.deviceConfigParams = deviceConfigParams;
			return this;
		}

		@NonNull
		private ImmutableMap<String, String> getDeviceConfigParams()
		{
			return deviceConfigParams == null ? ImmutableMap.of() : deviceConfigParams;
		}
	}

}
