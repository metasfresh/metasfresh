package de.metas.device.accessor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.device.api.hook.BeforeAcquireValueHook;
import de.metas.device.config.DeviceConfig;
import de.metas.device.config.IDeviceConfigPool;
import de.metas.device.config.IDeviceConfigPoolListener;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
 * Maintains a list of device accessors assigned to each attribute.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class DeviceAccessorsHub
{
	private static final Logger logger = LogManager.getLogger(DeviceAccessorsHub.class);
	private final IDeviceConfigPool deviceConfigPool;

	private final CCache<AttributeCode, DeviceAccessorsList> cache = CCache.<AttributeCode, DeviceAccessorsList>builder()
			.build();

	public DeviceAccessorsHub(@NonNull final IDeviceConfigPool deviceConfigPool)
	{
		this.deviceConfigPool = deviceConfigPool;
		this.deviceConfigPool.addListener(new IDeviceConfigPoolListener()
		{
			@Override
			public void onConfigurationChanged(final IDeviceConfigPool deviceConfigPool)
			{
				cache.reset();
				logger.info("Reset {} because configuration changed for {}", this, deviceConfigPool);
			}
		});
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("deviceConfigPool", deviceConfigPool)
				.toString();
	}

	public Stream<DeviceAccessor> streamAllDeviceAccessors()
	{
		return deviceConfigPool.getAllAttributeCodes()
				.stream()
				.map(this::getDeviceAccessors)
				.flatMap(DeviceAccessorsList::stream);
	}

	public Optional<DeviceAccessor> getDeviceAccessorById(@NonNull final DeviceId id)
	{
		return deviceConfigPool.getAllAttributeCodes()
				.stream()
				.map(this::getDeviceAccessors)
				.map(deviceAccessorsList -> deviceAccessorsList.getByIdOrNull(id))
				.filter(Objects::nonNull)
				.findFirst();
	}

	public DeviceAccessorsList getDeviceAccessors(final AttributeCode attributeCode)
	{
		return cache.getOrLoad(attributeCode, this::createDeviceAccessorsList);
	}

	private DeviceAccessorsList createDeviceAccessorsList(final AttributeCode attributeCode)
	{
		final List<DeviceConfig> deviceConfigsForThisAttribute = deviceConfigPool.getDeviceConfigsForAttributeCode(attributeCode);
		logger.info("Devices configs for attribute {}: {}", attributeCode, deviceConfigsForThisAttribute);
		if (deviceConfigsForThisAttribute.isEmpty())
		{
			return DeviceAccessorsList.EMPTY;
		}

		final ImmutableList.Builder<DeviceAccessor> deviceAccessors = ImmutableList.builder();
		final StringBuilder warningMessage = new StringBuilder();

		for (final DeviceConfig deviceConfig : deviceConfigsForThisAttribute)
		{
			// trying to access the device and instantiate hooks.
			final IDevice device;
			final ImmutableList<BeforeAcquireValueHook> beforeHooks;
			try
			{
				device = DeviceInstanceUtils.createAndConfigureDevice(deviceConfig);
				
				beforeHooks = DeviceInstanceUtils.instantiateHooks(deviceConfig);
			}
			catch (final Exception e)
			{
				final String msg = String.format("Unable to access device identified by %s. Details:\n%s", deviceConfig, e.getLocalizedMessage());
				logger.warn(msg + ". Skipped", e);

				if (warningMessage.length() > 0)
				{
					warningMessage.append("\n");
				}
				warningMessage.append(msg);

				continue;
			}

			final List<IDeviceRequest<ISingleValueResponse>> allRequestsFor = DeviceInstanceUtils.getAllRequestsFor(deviceConfig, attributeCode, ISingleValueResponse.class);
			logger.info("Found these requests for {} and attribute {}: {}", deviceConfig, attributeCode, allRequestsFor);

			// NOTE: usually we expect one element (maximum) in allRequestsFor
			for (final IDeviceRequest<ISingleValueResponse> request : allRequestsFor)
			{
				final String deviceName = deviceConfig.getDeviceName();
				final DeviceId deviceId = DeviceId.ofString(deviceName + "-" + attributeCode.getCode() + "-" + request.getClass().getSimpleName());

				final DeviceAccessor deviceAccessor = DeviceAccessor.builder()
						.id(deviceId)
						.deviceConfig(deviceConfig)
						.displayName(TranslatableStrings.anyLanguage(deviceName))
						.device(device)
						.request(request)
						.beforeHooks(beforeHooks)
						.build();

				deviceAccessors.add(deviceAccessor);
			}
		}

		return DeviceAccessorsList.of(deviceAccessors.build(), warningMessage.toString());
	}
}
