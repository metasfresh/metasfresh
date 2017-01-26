package de.metas.device.adempiere.impl;

import java.util.Properties;

import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.device.adempiere.IDevicesHubFactory;

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

public class DevicesHubFactory implements IDevicesHubFactory
{
	private final transient CCache<ArrayKey, AttributesDevicesHub> devicesHubsByKey = CCache.newLRUCache("DevicesHubByKey", 20, 0);

	@Override
	public AttributesDevicesHub getDefaultAttributesDevicesHub()
	{
		final IHostIdentifier host = NetUtils.getLocalHost();
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		return getAttributesDevicesHub(host, adClientId, adOrgId);
	}
	
	@Override
	public AttributesDevicesHub getAttributesDevicesHub(final IHostIdentifier host, final int adClientId, final int adOrgId)
	{
		final ArrayKey devicesHubKey = ArrayKey.of(host, adClientId, adOrgId);
		return devicesHubsByKey.getOrLoad(devicesHubKey, () -> new AttributesDevicesHub(host, adClientId, adOrgId));
	}

}
