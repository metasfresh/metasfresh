package de.metas.hostkey.api.impl;

import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.UUID;
import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.IHostKeyStorage;
import de.metas.hostkey.spi.impl.IniBasedHostKeyStorage;
import de.metas.logging.LogManager;

public class HostKeyBL implements IHostKeyBL
{
	private static final transient Logger logger = LogManager.getLogger(HostKeyBL.class);

	private Supplier<IHostKeyStorage> _hostKeyStorage = () -> new IniBasedHostKeyStorage();

	@Override
	public void setHostKeyStorage(final Supplier<IHostKeyStorage> hostKeyStorage)
	{
		Check.assumeNotNull(hostKeyStorage, "Parameter hostKeyStorage is not null");

		_hostKeyStorage = hostKeyStorage;
	}

	private IHostKeyStorage getHostKeyStorage()
	{
		Check.assumeNotNull(_hostKeyStorage, "hostKeyStorage shall be configured"); // shall not happen
		return _hostKeyStorage.get();
	}

	@Override
	public String getCreateHostKey()
	{
		
		String hostkey = null;
		final Properties ctx = 	Env.getCtx(); 
		final int userId = Env.getAD_User_ID(ctx);
		if (userId > 0)
		{
			final I_AD_User user = InterfaceWrapperHelper.create(ctx, userId, I_AD_User.class, ITrx.TRXNAME_None);
			if (user!=null && user.isLoginAsHostKey() )
			{
				final de.metas.adempiere.model.I_AD_User userLogin = InterfaceWrapperHelper.create(user, de.metas.adempiere.model.I_AD_User.class);
				hostkey = userLogin.getLogin();
			}
		}

		final IHostKeyStorage hostKeyStorage = getHostKeyStorage();
		if (Check.isEmpty(hostkey, true))
		{
			hostkey = hostKeyStorage.getHostKey();
			if (Check.isEmpty(hostkey, true) || isHostKeyBlacklisted(hostkey))
			{
				// generate hostkey if not found
				hostkey = generateHostKey();
				
				logger.info("HostKey generated: {}", hostkey);
			}
		}
		
		

		// Always set back the cookie, because we want to renew the expire date
		hostKeyStorage.setHostKey(hostkey);

		logger.debug("HostKey found: {}", hostkey);
		return hostkey;
	}

	private boolean isHostKeyBlacklisted(final String hostkey)
	{
		return "1841f0fe-c913-4857-98f1-dfba6a759f65".equals(hostkey); // FIXME HARDCODED dirty workaround: this hostKey is blacklisted, ignore it => figure out if we still need it
	}

	@Override
	public final String generateHostKey()
	{
		final String hostkey = UUID.randomUUID().toString();
		return hostkey;
	}

}
