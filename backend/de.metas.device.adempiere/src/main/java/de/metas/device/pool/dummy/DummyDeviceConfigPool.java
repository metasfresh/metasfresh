package de.metas.device.pool.dummy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.mm.attributes.AttributeCode;
import org.slf4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.DeviceConfig;
import de.metas.device.adempiere.IDeviceConfigPool;
import de.metas.device.adempiere.IDeviceConfigPoolListener;
import de.metas.logging.LogManager;
import de.metas.util.NumberUtils;
import lombok.NonNull;

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

public class DummyDeviceConfigPool implements IDeviceConfigPool
{
	private static final Logger logger = LogManager.getLogger(DummyDeviceConfigPool.class);

	private static final AtomicBoolean enabled = new AtomicBoolean();
	private static final StaticStateHolder staticState = new StaticStateHolder();

	private static BigDecimal responseMinValue = BigDecimal.valueOf(100);
	private static BigDecimal responseMaxValue = BigDecimal.valueOf(900);

	public static boolean isEnabled()
	{
		return enabled.get();
	}

	public static void setEnabled(final boolean enabled)
	{
		DummyDeviceConfigPool.enabled.set(enabled);
		logger.info("Enabled: {}", DummyDeviceConfigPool.enabled.get());
	}

	public static void setResponseMinValue(final int responseMinValue)
	{
		DummyDeviceConfigPool.responseMinValue = BigDecimal.valueOf(responseMinValue);
	}

	public static BigDecimal getResponseMinValue()
	{
		return responseMinValue;
	}

	public static void setResponseMaxValue(final int responseMaxValue)
	{
		DummyDeviceConfigPool.responseMaxValue = BigDecimal.valueOf(responseMaxValue);
	}

	public static BigDecimal getResponseMaxValue()
	{
		return responseMaxValue;
	}

	@Override
	public void addListener(final IDeviceConfigPoolListener listener)
	{
		// skip adding the listener
	}

	@Override
	public Set<AttributeCode> getAllAttributeCodes()
	{
		if (!isEnabled())
		{
			return ImmutableSet.of();
		}

		return staticState.getAllAttributeCodes();
	}

	@Override
	public List<DeviceConfig> getDeviceConfigsForAttributeCode(AttributeCode attributeCode)
	{
		if (!isEnabled())
		{
			return ImmutableList.of();
		}

		return staticState.getDeviceConfigsForAttributeCode(attributeCode);
	}

	public static ImmutableList<DeviceConfig> getAllDevices()
	{
		return staticState.getAllDevices();
	}

	public static DeviceConfig addDevice(@NonNull final DummyDeviceAddRequest request)
	{
		final DeviceConfig device = DeviceConfig.builder(request.getDeviceName())
				.setAssignedAttributeCodes(request.getAssignedAttributeCodes())
				.setDeviceClassname(DummyDevice.class.getName())
				.setParameterValueSupplier(DummyDeviceConfigPool::getDeviceParamValue)
				.setRequestClassnamesSupplier(DummyDeviceConfigPool::getDeviceRequestClassnames)
				.setAssignedWarehouseIds(request.getOnlyWarehouseIds())
				.build();

		staticState.addDevice(device);

		logger.info("Added device: {}", device);
		return device;
	}

	public static void removeDeviceByName(@NonNull final String deviceName)
	{
		staticState.removeDeviceByName(deviceName);
	}

	private static String getDeviceParamValue(final String deviceName, final String parameterName, final String defaultValue)
	{
		return defaultValue;
	}

	private static Set<String> getDeviceRequestClassnames(final String deviceName, final AttributeCode attributeCode)
	{
		return DummyDevice.getDeviceRequestClassnames();
	}

	public static DummyDeviceResponse generateRandomResponse()
	{
		final BigDecimal value = NumberUtils.randomBigDecimal(responseMinValue, responseMaxValue, 3);
		return new DummyDeviceResponse(value);
	}

	//
	//
	//
	//
	//

	private static class StaticStateHolder
	{
		private HashMap<String, DeviceConfig> devicesByName = new HashMap<>();
		private ArrayListMultimap<AttributeCode, DeviceConfig> devicesByAttributeCode = ArrayListMultimap.create();

		public synchronized void addDevice(final DeviceConfig device)
		{
			final String deviceName = device.getDeviceName();
			final DeviceConfig existingDevice = removeDeviceByName(deviceName);

			devicesByName.put(deviceName, device);
			device.getAssignedAttributeCodes()
					.forEach(attributeCode -> devicesByAttributeCode.put(attributeCode, device));

			logger.info("addDevice: {} -> {}", existingDevice, device);
		}

		public synchronized DeviceConfig removeDeviceByName(@NonNull final String deviceName)
		{
			final DeviceConfig existingDevice = devicesByName.remove(deviceName);
			if (existingDevice != null)
			{
				existingDevice.getAssignedAttributeCodes()
						.forEach(attributeCode -> devicesByAttributeCode.remove(attributeCode, existingDevice));
			}

			return existingDevice;
		}

		public synchronized ImmutableList<DeviceConfig> getAllDevices()
		{
			return ImmutableList.copyOf(devicesByName.values());
		}

		public synchronized ImmutableList<DeviceConfig> getDeviceConfigsForAttributeCode(@NonNull final AttributeCode attributeCode)
		{
			return ImmutableList.copyOf(devicesByAttributeCode.get(attributeCode));
		}

		public ImmutableSet<AttributeCode> getAllAttributeCodes()
		{
			return ImmutableSet.copyOf(devicesByAttributeCode.keySet());
		}
	}
}
