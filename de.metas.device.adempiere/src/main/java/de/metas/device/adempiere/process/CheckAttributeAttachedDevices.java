package de.metas.device.adempiere.process;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.model.I_M_Attribute;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import de.metas.device.adempiere.IDeviceBL;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Checks all attribute attached devices.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CheckAttributeAttachedDevices extends JavaProcess
{
	// services
	private final transient IDeviceBL deviceBL = Services.get(IDeviceBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	//
	// parameters
	@Param(parameterName = "Host")
	private String p_Host;
	@Param(parameterName = "Counter")
	private int p_AccessTimes;
	private static final int DEFAULT_AccessTimes = 10;
	@Param(parameterName = "M_Attribute_ID")
	private int p_M_Attribute_ID;

	//
	// state
	private int countDevicesChecked = 0;

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IHostIdentifier host = getHost();
		addLog("Using host: " + host);

		final int accessTimesPerDevice = p_AccessTimes > 0 ? p_AccessTimes : DEFAULT_AccessTimes;
		addLog("Access times per device: " + p_AccessTimes);

		streamAllAttributes()
				.map((attribute) -> new DeviceConfig(host, attribute))
				.flatMap((deviceConfig) -> deviceConfig.explodeByDeviceName())
				.flatMap((deviceConfig) -> deviceConfig.explodeByDeviceRequest())
				.forEach((deviceConfig) -> deviceConfig.accessDeviceNTimes(accessTimesPerDevice));

		if (countDevicesChecked == 0)
		{
			throw new AdempiereException("No devices found");
		}

		return MSG_OK;
	}

	private final IHostIdentifier getHost() throws UnknownHostException
	{
		if (p_Host == null)
		{
			return NetUtils.getLocalHost();
		}
		else
		{
			return NetUtils.of(p_Host);
		}
	}

	private final Stream<I_M_Attribute> streamAllAttributes()
	{
		final IQueryBuilder<I_M_Attribute> queryBuilder = queryBL
				.createQueryBuilder(I_M_Attribute.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				//
				.orderBy()
				.addColumn(I_M_Attribute.COLUMN_M_Attribute_ID)
				.endOrderBy();

		if (p_M_Attribute_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Attribute.COLUMN_M_Attribute_ID, p_M_Attribute_ID);
		}

		return queryBuilder
				.create()
				.stream(I_M_Attribute.class);
	}

	@Immutable
	private final class DeviceConfig
	{
		private final IHostIdentifier host;
		private final I_M_Attribute attribute;
		private String deviceName;
		private Optional<IDevice> _device; // lazy
		@SuppressWarnings("rawtypes")
		private IDeviceRequest<ISingleValueResponse> deviceRequest;

		public DeviceConfig(final IHostIdentifier host, final I_M_Attribute attribute)
		{
			super();
			this.host = host;
			this.attribute = attribute;
			deviceName = null;
			_device = null;
			deviceRequest = null;
		}

		private DeviceConfig(final DeviceConfig deviceConfig)
		{
			super();
			host = deviceConfig.host;
			attribute = deviceConfig.attribute;
			deviceName = deviceConfig.deviceName;
			_device = deviceConfig._device;
			deviceRequest = deviceConfig.deviceRequest;
		}

		@Override
		public String toString()
		{
			final Optional<IDevice> device = _device;

			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("attribute", attribute)
					.add("deviceName", deviceName)
					.add("device", device == null ? null : device.orNull())
					.add("deviceRequest", deviceRequest)
					.toString();
		}

		private DeviceConfig deriveWithDeviceName(final String deviceName)
		{
			Preconditions.checkNotNull(deviceName, "deviceName");

			final DeviceConfig deviceConfig = new DeviceConfig(this);
			deviceConfig.deviceName = deviceName;
			return deviceConfig;
		}

		public Stream<DeviceConfig> explodeByDeviceName()
		{
			Preconditions.checkArgument(deviceName == null, "deviceName shall not be set");
			return deviceBL.getAllDeviceNamesForAttrAndHost(attribute, host)
					.stream()
					.map((currentDeviceName) -> deriveWithDeviceName(currentDeviceName));
		}

		public IDevice getDevice()
		{
			if (_device == null)
			{
				final IDevice deviceInstance = createAndConfigureDevice();
				_device = Optional.fromNullable(deviceInstance);
			}

			return _device.orNull();
		}

		private IDevice createAndConfigureDevice()
		{
			try
			{
				return deviceBL.createAndConfigureDeviceOrReturnExisting(getCtx(), deviceName, host);
			}
			catch (final Exception e)
			{
				final String errmsg = "Error: Unable to access device '" + deviceName + "' from host " + host + ". Details:\n" + e.getLocalizedMessage();
				addLog(errmsg);
				log.warn(errmsg, e);
				return null;
			}
		}

		@SuppressWarnings("rawtypes")
		private DeviceConfig deriveWithRequest(final IDeviceRequest<ISingleValueResponse> deviceRequest)
		{
			Preconditions.checkNotNull(deviceRequest, "deviceRequest");

			final DeviceConfig deviceConfig = new DeviceConfig(this);
			deviceConfig.deviceRequest = deviceRequest;
			return deviceConfig;
		}

		@SuppressWarnings("rawtypes")
		public IDeviceRequest<ISingleValueResponse> getDeviceRequest()
		{
			return deviceRequest;
		}

		public Stream<DeviceConfig> explodeByDeviceRequest()
		{
			Preconditions.checkNotNull(deviceName, "deviceName");

			@SuppressWarnings("rawtypes")
			final List<IDeviceRequest<ISingleValueResponse>> deviceRequests = deviceBL.getAllRequestsFor(deviceName, attribute, ISingleValueResponse.class);
			if (deviceRequests.isEmpty())
			{
				addLog("Warning: got no requests for " + this);
				return Stream.empty();
			}

			return deviceRequests
					.stream()
					.map((deviceRequest) -> deriveWithRequest(deviceRequest));
		}

		public void accessDeviceNTimes(final int times)
		{
			final IDevice device = getDevice();
			if (device == null)
			{
				log.info("No device found for {}", this);
				return;
			}

			IntStream.rangeClosed(1, times)
					.forEach((time) -> accessDevice(time));
		}

		@SuppressWarnings("rawtypes")
		private void accessDevice(final int time)
		{
			log.info("Accessing({}) {}", time, this);

			final IDevice device = getDevice();
			if (device == null)
			{
				log.info("No device found for {}", this);
				return;
			}

			countDevicesChecked++;

			final IDeviceRequest<ISingleValueResponse> request = getDeviceRequest();
			final Stopwatch stopwatch = Stopwatch.createStarted();

			try
			{
				log.debug("Getting response from {}", this);

				final ISingleValueResponse response = device.accessDevice(request);
				log.debug("Got respose from {}: {}", this, response);

				addLog("OK(" + time + "): accessed " + this + " and got " + response + " in " + stopwatch);
			}
			catch (final Exception ex)
			{
				final String errmsg = "Error(" + time + "): Failed accessing " + this + ": " + ex.getLocalizedMessage();
				addLog(errmsg);
				log.warn(errmsg, ex);
			}
		}

	}

}
