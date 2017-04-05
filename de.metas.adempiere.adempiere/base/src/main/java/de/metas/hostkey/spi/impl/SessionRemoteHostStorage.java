package de.metas.hostkey.spi.impl;

import java.util.Properties;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.hostkey.spi.IHostKeyStorage;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/1274
 */
public class SessionRemoteHostStorage implements IHostKeyStorage
{

	/**
	 * Does nothing, because with this storage the hostkey does not have to be set/persisted my metasfresh
	 */
	@Override
	public void setHostKey(String hostkey)
	{
		// nothing
	}

	/**
	 * @return the current session's {@code Remote_Host} value.
	 * @throws an exception if the current session can't be obtained or if the current session has an empty {@code Remote_Host} value.
	 */
	@Override
	public String getHostKey()
	{
		final ISessionBL sessionBL = Services.get(ISessionBL.class);

		final Properties ctx = Env.getCtx();

		final MFSession currentSession = sessionBL.getCurrentSession(ctx);
		Check.errorIf(currentSession == null, "the current session is null; ctx={}", ctx);

		final String result = currentSession.getRemote_Host();
		Check.errorIf(Check.isEmpty(result, true), "the current session's Remote_Host value is empty; currentSession={}; Env.getCtx()={}", currentSession, ctx);

		return result;
	}
}
