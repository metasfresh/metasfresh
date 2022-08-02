package de.metas.device.config;

import de.metas.device.dummy.DummyDeviceConfigPool;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.net.IHostIdentifier;
import org.springframework.stereotype.Service;

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
public class DeviceConfigPoolFactory
{
	public IDeviceConfigPool createDeviceConfigPool(
			@NonNull final IHostIdentifier clientHost,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		final SysConfigDeviceConfigPool sysConfigDeviceConfigPool = new SysConfigDeviceConfigPool(
				clientHost,
				adClientId,
				adOrgId);

		if (DummyDeviceConfigPool.isEnabled())
		{
			final DummyDeviceConfigPool inMemoryMockedDeviceConfigPool = new DummyDeviceConfigPool();
			return CompositeDeviceConfigPool.compose(sysConfigDeviceConfigPool, inMemoryMockedDeviceConfigPool);
		}
		else
		{
			return sysConfigDeviceConfigPool;
		}
	}
}
