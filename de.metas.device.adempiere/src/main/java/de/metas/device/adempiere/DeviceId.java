package de.metas.device.adempiere;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.adempiere.util.net.IHostIdentifier;

import com.google.common.base.MoreObjects;

import de.metas.device.api.IDevice;

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
 * Device identification object.
 *
 * This object is used to identify and instantiate a {@link IDevice}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class DeviceId
{
	/**
	 * Creates a new device identification object.
	 *
	 * @param deviceName
	 * @param adClientId AD_Client_ID used to fetch the device configuration
	 * @param adOrgId AD_Org_ID used to fetch the device configuration
	 * @param clientHost the name and IP of the client for which we configure the device. Note that the code will first look for configuration parameters for the given host name, then for the IP and
	 *            then (if there are none) fall back to look for parameters for the IP <code>0.0.0.0</code>.
	 */
	public static final DeviceId of(final String deviceName, final int adClientId, final int adOrgId, final IHostIdentifier clientHost)
	{
		return new DeviceId(deviceName, adClientId, adOrgId, clientHost);
	}

	private final String deviceName;
	private final int adClientId;
	private final int adOrgId;
	private final IHostIdentifier clientHost;

	private transient String _toString;
	private transient Integer _hashcode;

	private DeviceId(final String deviceName, final int adClientId, final int adOrgId, final IHostIdentifier clientHost)
	{
		super();
		
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");
		Check.assumeNotNull(clientHost, "Parameter clientHost is not null");
		
		this.deviceName = deviceName;
		this.adClientId = adClientId <= 0 ? 0 : adClientId;
		this.adOrgId = adOrgId <= 0 ? 0 : adOrgId;
		this.clientHost = clientHost;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.add("deviceName", deviceName)
					.add("adClientId", adClientId)
					.add("adOrgId", adOrgId)
					.add("clientHost", clientHost)
					.toString();
		}
		return _toString;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(deviceName, adClientId, adOrgId, clientHost);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof DeviceId)
		{
			final DeviceId other = (DeviceId)obj;
			return Objects.equals(deviceName, other.deviceName)
					&& adClientId == other.adClientId
					&& adOrgId == other.adOrgId
					&& Objects.equals(clientHost, other.clientHost);

		}
		else
		{
			return false;
		}
	}

	public String getDeviceName()
	{
		return deviceName;
	}

	public int getAD_Client_ID()
	{
		return adClientId;
	}

	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	public IHostIdentifier getClientHost()
	{
		return clientHost;
	}
}
