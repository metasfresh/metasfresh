package de.metas.device.accessor;

import de.metas.device.config.DeviceConfigPoolFactory;
import de.metas.device.config.IDeviceConfigPool;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

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

@Service
public class DeviceAccessorsHubFactory
{
	private final DeviceConfigPoolFactory configPoolFactory;

	private final ConcurrentHashMap<DeviceAccessorsHubKey, DeviceAccessorsHub> hubsByKey = new ConcurrentHashMap<>();

	public DeviceAccessorsHubFactory(
			@NonNull final DeviceConfigPoolFactory configPoolFactory)
	{
		this.configPoolFactory = configPoolFactory;
	}

	public Optional<DeviceAccessor> getDeviceAccessorById(@NonNull DeviceId deviceId)
	{
		return getDefaultDeviceAccessorsHub().getDeviceAccessorById(deviceId);
	}

	public DeviceAccessorsHub getDefaultDeviceAccessorsHub()
	{
		final IHostIdentifier clientHost = NetUtils.getLocalHost();
		final Properties ctx = Env.getCtx();
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);
		return getDeviceAccessorsHub(clientHost, adClientId, adOrgId);
	}

	public DeviceAccessorsHub getDeviceAccessorsHub(
			@NonNull final IHostIdentifier clientHost,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		return hubsByKey.computeIfAbsent(
				DeviceAccessorsHubKey.of(clientHost, adClientId, adOrgId),
				this::createDeviceAccessorsHub);
	}

	private DeviceAccessorsHub createDeviceAccessorsHub(final DeviceAccessorsHubKey key)
	{
		final IDeviceConfigPool deviceConfigPool = configPoolFactory.createDeviceConfigPool(
				key.getClientHost(),
				key.getAdClientId(),
				key.getAdOrgId());

		return new DeviceAccessorsHub(deviceConfigPool);
	}

	public void cacheReset()
	{
		hubsByKey.clear();
	}

	@Value(staticConstructor = "of")
	private static class DeviceAccessorsHubKey
	{
		@NonNull IHostIdentifier clientHost;
		@NonNull ClientId adClientId;
		@NonNull OrgId adOrgId;
	}
}
