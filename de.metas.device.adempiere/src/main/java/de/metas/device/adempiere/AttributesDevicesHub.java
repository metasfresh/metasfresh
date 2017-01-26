package de.metas.device.adempiere;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.CCache;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Attribute's devices hub.
 *
 * Maintains a list of devices assigned to each attribute.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AttributesDevicesHub
{
	private static final transient Logger logger = LogManager.getLogger(AttributesDevicesHub.class);
	private final transient IDeviceBL deviceBL = Services.get(IDeviceBL.class);

	private final IHostIdentifier clientHost;
	private final int adClientId;
	private final int adOrgId;

	private final transient CCache<String, AttributeDeviceAccessorsList> attributeCode2deviceAccessors = CCache
			.<String, AttributeDeviceAccessorsList> newCache("DevicesHub_DeviceAccessorsByAttributeCode", 0, 0)
			.addResetForTableName(I_AD_SysConfig.Table_Name);

	public AttributesDevicesHub(final IHostIdentifier host, final int adClientId, final int adOrgId)
	{
		super();
		clientHost = host;
		this.adClientId = adClientId >= 0 ? adClientId : 0;
		this.adOrgId = adOrgId >= 0 ? adOrgId : 0;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("host", clientHost)
				.add("AD_Client_ID", adClientId)
				.add("AD_Org_ID", adOrgId)
				.toString();
	}

	public AttributeDeviceAccessorsList getAttributeDeviceAccessors(final String attributeCode)
	{
		return attributeCode2deviceAccessors.getOrLoad(attributeCode, () -> createAttributeDeviceAccessor(attributeCode));
	}

	@SuppressWarnings("rawtypes")
	private final AttributeDeviceAccessorsList createAttributeDeviceAccessor(final String attributeCode)
	{
		final List<String> deviceNamesForThisAttribute = deviceBL.getAllDeviceNamesForAttrAndHost(attributeCode, clientHost, adClientId, adOrgId);
		logger.info("Devices for host {} and attributte {}: {}", clientHost, attributeCode, deviceNamesForThisAttribute);
		if (deviceNamesForThisAttribute.isEmpty())
		{
			return AttributeDeviceAccessorsList.EMPTY;
		}

		final String deviceDisplayNameCommonPrefix = extractDeviceDisplayNameCommonPrefix(deviceNamesForThisAttribute);

		final ImmutableList.Builder<AttributeDeviceAccessor> deviceAccessors = ImmutableList.builder();
		final StringBuilder warningMessage = new StringBuilder();

		for (final String deviceName : deviceNamesForThisAttribute)
		{
			final DeviceId deviceId = DeviceId.of(deviceName, adClientId, adOrgId, clientHost);
			// trying to access the device.
			final IDevice device;
			try
			{
				device = deviceBL.createAndConfigureDeviceOrReturnExisting(deviceId);
			}
			catch (final Exception e)
			{
				final String msg = String.format("Unable to access device identified by %s. Details:\n%s", deviceId, e.getLocalizedMessage());
				logger.warn(msg + ". Skipped", e);

				if (warningMessage.length() > 0)
				{
					warningMessage.append("\n");
				}
				warningMessage.append(msg);

				continue;
			}

			final AttributeDeviceId attributeDeviceId = AttributeDeviceId.of(deviceId, attributeCode);
			final List<IDeviceRequest<ISingleValueResponse>> allRequestsFor = deviceBL.getAllRequestsFor(attributeDeviceId, ISingleValueResponse.class);
			logger.info("Found these requests for deviceName {} and attribute {}: {}", deviceName, attributeCode, allRequestsFor);

			for (final IDeviceRequest<ISingleValueResponse> request : allRequestsFor)
			{
				final String displayName = createDeviceDisplayName(deviceDisplayNameCommonPrefix, deviceName);
				final AttributeDeviceAccessor deviceAccessor = new AttributeDeviceAccessor(attributeDeviceId, displayName, device, request);
				deviceAccessors.add(deviceAccessor);
			}
		}

		return AttributeDeviceAccessorsList.of(deviceAccessors.build(), warningMessage.toString());
	}

	/**
	 * Extracts device names common prefix.
	 *
	 * If there are more than one device for this attribute, we use the device names' first characters for the button texts.
	 * How many chars we need depends of how log the common prefix is.
	 *
	 * @param deviceNames
	 * @return common prefix
	 */
	@VisibleForTesting
	/* package */static final String extractDeviceDisplayNameCommonPrefix(final List<String> deviceNames)
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
	/* package */static final String createDeviceDisplayName(final String deviceDisplayNameCommonPrefix, final String deviceName)
	{
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");

		if (deviceDisplayNameCommonPrefix == null || deviceDisplayNameCommonPrefix.isEmpty())
		{
			return String.valueOf(deviceName.charAt(0));
		}

		return deviceDisplayNameCommonPrefix + deviceName.charAt(deviceDisplayNameCommonPrefix.length());
	}

	/**
	 * Facade used to access a given preconfigured device using a preconfigured {@link IDeviceRequest}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static final class AttributeDeviceAccessor
	{
		private final AttributeDeviceId attributeDeviceId;
		private final String displayName;
		private final IDevice device;
		private final IDeviceRequest<ISingleValueResponse> request;

		private AttributeDeviceAccessor(final AttributeDeviceId attributeDeviceId, final String displayName, final IDevice device, final IDeviceRequest<ISingleValueResponse> request)
		{
			super();

			Check.assumeNotNull(attributeDeviceId, "Parameter attributeDeviceId is not null");
			Check.assumeNotNull(device, "Parameter device is not null");

			this.attributeDeviceId = attributeDeviceId;
			this.displayName = displayName;
			this.device = device;
			this.request = request;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("attributeDeviceId", attributeDeviceId)
					.add("displayName", displayName)
					.add("request", request)
					.add("device", device)
					.toString();
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public synchronized Object acquireValue()
		{
			logger.debug("This: {}, Device: {}; Request: {}", this, device, request);

			final ISingleValueResponse response = device.accessDevice(request);
			logger.debug("Device {}; Response: {}", device, response);

			return response.getSingleValue();
		}
	}

	/**
	 * List of {@link AttributeDeviceAccessor}s.
	 *
	 * It also contains other informations like {@link AttributeDeviceAccessorsList#getWarningMessage()}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 */
	@Immutable
	public static final class AttributeDeviceAccessorsList
	{
		private static final AttributeDeviceAccessorsList of(final List<AttributeDeviceAccessor> attributeDeviceAccessors, final String warningMessage)
		{
			final String warningMessageNorm = Check.isEmpty(warningMessage, true) ? null : warningMessage;
			if (attributeDeviceAccessors.isEmpty() && warningMessage == null)
			{
				return EMPTY;
			}

			return new AttributeDeviceAccessorsList(attributeDeviceAccessors, warningMessageNorm);
		}

		private static final AttributeDeviceAccessorsList EMPTY = new AttributeDeviceAccessorsList();

		private final ImmutableList<AttributeDeviceAccessor> attributeDeviceAccessors;
		private final String warningMessage;

		private AttributeDeviceAccessorsList(final List<AttributeDeviceAccessor> attributeDeviceAccessors, final String warningMessage)
		{
			super();
			this.attributeDeviceAccessors = ImmutableList.copyOf(attributeDeviceAccessors);
			this.warningMessage = warningMessage;
		}

		/** empty/null constructor */
		private AttributeDeviceAccessorsList()
		{
			super();
			attributeDeviceAccessors = ImmutableList.of();
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

		public List<AttributeDeviceAccessor> getAttributeDeviceAccessors()
		{
			return attributeDeviceAccessors;
		}

		public Stream<AttributeDeviceAccessor> stream()
		{
			return attributeDeviceAccessors.stream();
		}

		/**
		 * @return warning message(s) fired while the devices were created or configured
		 */
		public String getWarningMessage()
		{
			return warningMessage;
		}

		/**
		 * Convenient (fluent) method to consume {@link #getWarningMessage()} if any.
		 *
		 * @param warningMessageConsumer
		 */
		public AttributeDeviceAccessorsList consumeWarningMessageIfAny(final Consumer<String> warningMessageConsumer)
		{
			if (Check.isEmpty(warningMessage, true))
			{
				return this;
			}

			warningMessageConsumer.accept(warningMessage);

			return this;
		}
	}
}
