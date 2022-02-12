package de.metas.device.adempiere;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
 * Attribute's devices hub.
 * <p>
 * Maintains a list of devices assigned to each attribute.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class AttributesDevicesHub
{
	private static final transient Logger logger = LogManager.getLogger(AttributesDevicesHub.class);
	private final transient IDeviceBL deviceBL = Services.get(IDeviceBL.class);

	private final IDeviceConfigPool deviceConfigPool;

	private final ConcurrentHashMap<AttributeCode, AttributeDeviceAccessorsList> attributeCode2deviceAccessors = new ConcurrentHashMap<>();

	public AttributesDevicesHub(@NonNull final IDeviceConfigPool deviceConfigPool)
	{
		this.deviceConfigPool = deviceConfigPool;
		this.deviceConfigPool.addListener(new IDeviceConfigPoolListener()
		{
			@Override
			public void onConfigurationChanged(final IDeviceConfigPool deviceConfigPool1)
			{
				attributeCode2deviceAccessors.clear();
				logger.info("Reset {} because configuration changed for {}", this, deviceConfigPool1);
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

	@Nullable
	public AttributeDeviceAccessor getAttributeDeviceAccessorById(final String id)
	{
		return deviceConfigPool.getAllAttributeCodes()
				.stream()
				.map(this::getAttributeDeviceAccessors)
				.map(deviceAccessorsList -> deviceAccessorsList.getByIdOrNull(id))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	public AttributeDeviceAccessorsList getAttributeDeviceAccessors(final AttributeCode attributeCode)
	{
		return attributeCode2deviceAccessors.computeIfAbsent(attributeCode, this::createAttributeDeviceAccessor);
	}

	private AttributeDeviceAccessorsList createAttributeDeviceAccessor(final AttributeCode attributeCode)
	{
		final List<DeviceConfig> deviceConfigsForThisAttribute = deviceConfigPool.getDeviceConfigsForAttributeCode(attributeCode);
		logger.info("Devices configs for attribute {}: {}", attributeCode, deviceConfigsForThisAttribute);
		if (deviceConfigsForThisAttribute.isEmpty())
		{
			return AttributeDeviceAccessorsList.EMPTY;
		}

		final String deviceDisplayNameCommonPrefix = extractDeviceDisplayNameCommonPrefixForDeviceConfigs(deviceConfigsForThisAttribute);

		final ImmutableList.Builder<AttributeDeviceAccessor> deviceAccessors = ImmutableList.builder();
		final StringBuilder warningMessage = new StringBuilder();

		for (final DeviceConfig deviceConfig : deviceConfigsForThisAttribute)
		{
			// trying to access the device.
			final IDevice device;
			try
			{
				device = deviceBL.createAndConfigureDevice(deviceConfig);
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

			final List<IDeviceRequest<ISingleValueResponse>> allRequestsFor = deviceBL.getAllRequestsFor(deviceConfig, attributeCode, ISingleValueResponse.class);
			logger.info("Found these requests for {} and attribute {}: {}", deviceConfig, attributeCode, allRequestsFor);

			// NOTE: usually we expect one element (maximum) in allRequestsFor
			for (final IDeviceRequest<ISingleValueResponse> request : allRequestsFor)
			{
				final String deviceName = deviceConfig.getDeviceName();

				final AttributeDeviceAccessor deviceAccessor = AttributeDeviceAccessor.builder()
						.displayName(TranslatableStrings.anyLanguage(createDeviceDisplayName(deviceDisplayNameCommonPrefix, deviceName)))
						.device(device)
						.deviceName(deviceName)
						.attributeCode(attributeCode)
						.assignedWarehouseIds(deviceConfig.getAssignedWarehouseIds())
						.request(request)
						.build();

				deviceAccessors.add(deviceAccessor);
			}
		}

		return AttributeDeviceAccessorsList.of(deviceAccessors.build(), warningMessage.toString());
	}

	private static String extractDeviceDisplayNameCommonPrefixForDeviceConfigs(final List<DeviceConfig> deviceConfigs)
	{
		final List<String> deviceNames = deviceConfigs.stream().map(DeviceConfig::getDeviceName).collect(Collectors.toList());
		return extractDeviceDisplayNameCommonPrefix(deviceNames);
	}

	/**
	 * Extracts device names common prefix.
	 * <p>
	 * If there are more than one device for this attribute, we use the device names' first characters for the button texts.
	 * How many chars we need depends of how log the common prefix is.
	 *
	 * @return common prefix
	 */
	@VisibleForTesting
	/* package */ static String extractDeviceDisplayNameCommonPrefix(final List<String> deviceNames)
	{
		if (deviceNames.size() <= 1)
		{
			return ""; // only one device => we will do with the device name's first character
		}
		else
		{
			return StringUtils.getCommonPrefix(deviceNames.toArray(new String[0]));
		}
	}

	@VisibleForTesting
	/* package */ static String createDeviceDisplayName(
			@Nullable final String deviceDisplayNameCommonPrefix,
			@NonNull final String deviceName)
	{
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");

		if (deviceDisplayNameCommonPrefix == null || deviceDisplayNameCommonPrefix.isEmpty())
		{
			return String.valueOf(deviceName.charAt(0));
		}
		else if (deviceDisplayNameCommonPrefix.equals(deviceName))
		{
			return deviceDisplayNameCommonPrefix;
		}
		else
		{
			return deviceDisplayNameCommonPrefix + deviceName.charAt(deviceDisplayNameCommonPrefix.length());
		}
	}
}
