package org.adempiere.ad.session.impl;

import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;

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

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Session;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.adempiere.form.IClientUI;
import de.metas.cache.CCache;
import de.metas.util.Services;

public class SessionBL implements ISessionBL
{
	/** Sessions */
	private static CCache<Integer, MFSession> s_sessions = CCache.newLRUCache(I_AD_Session.Table_Name, 100, 0);

	private final InheritableThreadLocal<Boolean> disableChangeLogsThreadLocal = new InheritableThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return Boolean.FALSE;
		};
	};

	@Override
	public MFSession getCurrentSession(final Properties ctx)
	{
		final int AD_Session_ID = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
		return getSessionById(ctx, AD_Session_ID);
	}

	@Override
	public MFSession getCurrentOrCreateNewSession(final Properties ctx)
	{
		MFSession session = getCurrentSession(ctx);

		// Create New
		if (session == null)
		{
			session = createMFSession(ctx);
			
			final IHostIdentifier localHost = NetUtils.getLocalHost();
			session.setRemote_Addr(localHost.getIP(), localHost.getHostName());
			session.updateContext(ctx);
			
			s_sessions.put(session.getAD_Session_ID(), session);
		}
		return session;

	}

	private MFSession createMFSession(final Properties ctx)
	{
		final I_AD_Session sessionPO = InterfaceWrapperHelper.create(ctx, I_AD_Session.class, ITrx.TRXNAME_None);
		sessionPO.setProcessed (false);

		//
		// Set Client Info if available - 04442
		if ((Ini.isSwingClient())  // task 08569: only try it if we are running in any client mode
				&& Services.isAvailable(IClientUI.class))
		{
			sessionPO.setClient_Info(Services.get(IClientUI.class).getClientInfo());
		}
		else
		{
			sessionPO.setClient_Info("N/A");
		}

		sessionPO.setDescription(Adempiere.getBuildVersion() + "_" + Adempiere.getDateVersion() + " " + Adempiere.getImplementationVersion());
		sessionPO.setAD_Role_ID(Env.getAD_Role_ID(ctx));
		sessionPO.setLoginDate(Env.getDate(ctx));
		
		// gh #1274: don't invoke hostkeyBL now, because depending on the host key storage
		// we might not yet be ready to get the host key from the storage
		sessionPO.setHostKey(MFSession.HOSTKEY_NOT_YET_DETERMINED);
		
		return new MFSession(sessionPO);
	}


	@Override
	public MFSession getSessionById(final Properties ctx, final int AD_Session_ID)
	{
		if (AD_Session_ID <= 0)
		{
			return null;
		}

		MFSession session = getFromCache(ctx, AD_Session_ID);

		// Try to load from database
		if (session == null)
		{
			final I_AD_Session sessionPO = InterfaceWrapperHelper.create(ctx, AD_Session_ID, I_AD_Session.class, ITrx.TRXNAME_None);
			if (sessionPO == null || sessionPO.getAD_Session_ID() != AD_Session_ID)
			{
				// No session found for given AD_Session_ID, a warning shall be already logged
				return null;
			}
			
			session = new MFSession(sessionPO);
			s_sessions.put(session.getAD_Session_ID(), session);
		}

		return session;
	}

	private final MFSession getFromCache(final Properties ctx, final int AD_Session_ID)
	{
		if (AD_Session_ID <= 0)
		{
			return null;
		}

		final MFSession session = s_sessions.get(AD_Session_ID);
		if (session == null)
		{
			return null;
		}
		if (session.getAD_Session_ID() != AD_Session_ID)
		{
			return null;
		}

		return session;
	}

	@Override
	public void logoutCurrentSession()
	{
		final Properties ctx = Env.getCtx();
		final MFSession session = getCurrentSession(ctx);
		if (session == null)
		{
			// no currently running session
			return;
		}
		
		final boolean alreadyDestroyed = session.isDestroyed();

		// Fire BeforeLogout event only if current session is not yet closes(i.e. processed)
		if (!alreadyDestroyed)
		{
			ModelValidationEngine.get().fireBeforeLogout(session);
		}

		session.setDestroyed();
		Env.removeContext(ctx, Env.CTXNAME_AD_Session_ID);
		
		s_sessions.remove(session.getAD_Session_ID());

		// Fire AfterLogout event only if current session was closed right now
		if (!alreadyDestroyed && session.isDestroyed())
		{
			ModelValidationEngine.get().fireAfterLogout(session);
		}
	}

	@Override
	public void setDisableChangeLogsForThread(final boolean disable)
	{
		disableChangeLogsThreadLocal.set(disable);
	}

	@Override
	public boolean isChangeLogEnabled()
	{
		//
		// Check if it's disabled for current thread
		final Boolean disableChangeLogsThreadLocalValue = disableChangeLogsThreadLocal.get();
		if (Boolean.TRUE.equals(disableChangeLogsThreadLocalValue))
		{
			return false;
		}

		//
		// Check if role allows us to create the change log
		final IUserRolePermissions role = Env.getUserRolePermissions();
		if (role == null || !role.hasPermission(IUserRolePermissions.PERMISSION_ChangeLog))
		{
			return false;
		}

		return true; // enabled
	}
}
