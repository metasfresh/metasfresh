package de.metas.device.adempiere.impl;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.service.ClientId;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.util.Env;

import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.device.adempiere.IDeviceConfigPool;
import de.metas.device.adempiere.IDeviceConfigPoolFactory;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

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

public class DevicesHubFactory implements IDevicesHubFactory
{
	private final IDeviceConfigPoolFactory deviceConfigPoolFactory = Services.get(IDeviceConfigPoolFactory.class);

	private final ConcurrentHashMap<AttributesDevicesHubKey, AttributesDevicesHub> devicesHubsByKey = new ConcurrentHashMap<>();

	@Override
	public AttributesDevicesHub getDefaultAttributesDevicesHub()
	{
		final IHostIdentifier clientHost = NetUtils.getLocalHost();
		final Properties ctx = Env.getCtx();
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);
		return getAttributesDevicesHub(clientHost, adClientId, adOrgId);
	}

	@Override
	public AttributesDevicesHub getAttributesDevicesHub(
			@NonNull final IHostIdentifier clientHost,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		return devicesHubsByKey.computeIfAbsent(
				AttributesDevicesHubKey.of(clientHost, adClientId, adOrgId),
				this::createAttributesDevicesHub);
	}

	private AttributesDevicesHub createAttributesDevicesHub(final AttributesDevicesHubKey key)
	{
		final IDeviceConfigPool deviceConfigPool = deviceConfigPoolFactory.createDeviceConfigPool(
				key.getClientHost(),
				key.getAdClientId(),
				key.getAdOrgId());

		return new AttributesDevicesHub(deviceConfigPool);
	}

	@Override
	public void cacheReset()
	{
		devicesHubsByKey.clear();
	}

	@Value(staticConstructor = "of")
	private static class AttributesDevicesHubKey
	{
		@NonNull
		IHostIdentifier clientHost;
		@NonNull
		ClientId adClientId;
		@NonNull
		OrgId adOrgId;
	}
}
