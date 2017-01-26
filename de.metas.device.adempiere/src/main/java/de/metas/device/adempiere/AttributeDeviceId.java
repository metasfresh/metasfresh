package de.metas.device.adempiere;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

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
 * Device + attribute identifier.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class AttributeDeviceId
{
	public static final AttributeDeviceId of(final DeviceId deviceId, final String attributeCode)
	{
		return new AttributeDeviceId(deviceId, attributeCode);
	}

	private final DeviceId deviceId;
	private final String attributeCode;

	private transient String _toString;
	private transient Integer _hashcode;

	private AttributeDeviceId(final DeviceId deviceId, final String attributeCode)
	{
		super();
		Check.assumeNotNull(deviceId, "Parameter deviceId is not null");
		Check.assumeNotEmpty(attributeCode, "attributeCode is not empty");
		
		this.deviceId = deviceId;
		this.attributeCode = attributeCode;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.add("deviceId", deviceId)
					.add("attributeCode", attributeCode)
					.toString();
		}
		return _toString;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(deviceId, attributeCode);
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

		if (obj instanceof AttributeDeviceId)
		{
			final AttributeDeviceId other = (AttributeDeviceId)obj;
			return Objects.equals(deviceId, other.deviceId)
					&& Objects.equals(attributeCode, other.attributeCode);
		}
		else
		{
			return false;
		}
	}

	public DeviceId getDeviceId()
	{
		return deviceId;
	}

	public String getDeviceName()
	{
		return deviceId.getDeviceName();
	}

	public String getAttributeCode()
	{
		return attributeCode;
	}

	public int getAD_Client_ID()
	{
		return deviceId.getAD_Client_ID();
	}

	public int getAD_Org_ID()
	{
		return deviceId.getAD_Org_ID();
	}
}
