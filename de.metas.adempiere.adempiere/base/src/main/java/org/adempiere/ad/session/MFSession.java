package org.adempiere.ad.session;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Session;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.impl.SessionRemoteHostStorage;
import de.metas.logging.LogManager;

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
 * Metasfresh session
 */
public class MFSession
{
	/**
	 * New sessions receive this value until the time {@link #getHostKey()} is invoked for the first time.
	 * Then, this value is replaced with the return value from {@link IHostKeyBL#getCreateHostKey()}.
	 * We invoke the hostKeyBL late, because in the case of {@link SessionRemoteHostStorage}, the hostkey can only be determined after a successful login.
	 */
	public static final String HOSTKEY_NOT_YET_DETERMINED = "<not-yet-determined>";

	private static final transient Logger log = LogManager.getLogger(MFSession.class);

	private static final String CTX_Prefix = "#AD_Session.";

	private static final List<String> CTX_IgnoredColumnNames = ImmutableList.of(
			I_AD_Session.COLUMNNAME_AD_Session_ID // this one will be exported particularly
			, I_AD_Session.COLUMNNAME_AD_Client_ID //
			, I_AD_Session.COLUMNNAME_AD_Org_ID //
			, I_AD_Session.COLUMNNAME_Created, I_AD_Session.COLUMNNAME_CreatedBy //
			, I_AD_Session.COLUMNNAME_Updated, I_AD_Session.COLUMNNAME_UpdatedBy //
			, I_AD_Session.COLUMNNAME_IsActive //
			, I_AD_Session.COLUMNNAME_Processed //
			, I_AD_Session.COLUMNNAME_Remote_Addr, I_AD_Session.COLUMNNAME_Remote_Host, I_AD_Session.COLUMNNAME_WebSession //
	);

	private final I_AD_Session sessionPO;
	private final transient Map<String, Object> transientProperties = new HashMap<>();

	public MFSession(final I_AD_Session sessionPO)
	{
		Check.assumeNotNull(sessionPO, "Parameter sessionPO is not null");
		this.sessionPO = sessionPO;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Session_ID", getAD_Session_ID())
				.add("AD_User_ID", getAD_User_ID())
				.add("Remote", getRemote_Addr())
				.add("Destroyed", isDestroyed())
				.toString();
	}

	public int getAD_Session_ID()
	{
		return sessionPO.getAD_Session_ID();
	}

	public int getAD_Client_ID()
	{
		return sessionPO.getAD_Client_ID();
	}

	public int getAD_Org_ID()
	{
		return sessionPO.getAD_Org_ID();
	}

	public int getAD_Role_ID()
	{
		return sessionPO.getAD_Role_ID();
	}

	public void setLoginUsername(final String loginUsername)
	{
		if (Objects.equals(loginUsername, sessionPO.getLoginUsername()))
		{
			return;
		}

		sessionPO.setLoginUsername(loginUsername);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public int getAD_User_ID()
	{
		return sessionPO.getCreatedBy();
	}

	public void setAD_User_ID(final int adUserId)
	{
		// TODO: introduce I_AD_Session.AD_User_ID
		if (sessionPO.getCreatedBy() == adUserId)
		{
			return;
		}

		final PO po = InterfaceWrapperHelper.getStrictPO(sessionPO);
		po.set_ValueNoCheck(I_AD_Session.COLUMNNAME_CreatedBy, adUserId);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public void setLoginInfo(final int AD_Client_ID, final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID, final Timestamp loginDate)
	{
		final PO po = LegacyAdapters.convertToPO(sessionPO);
		po.set_ValueNoCheck(I_AD_Session.COLUMNNAME_AD_Client_ID, AD_Client_ID); // Force AD_Client_ID update
		sessionPO.setAD_Org_ID(AD_Org_ID);
		sessionPO.setAD_Role_ID(AD_Role_ID);
		po.set_ValueNoCheck(I_AD_Session.COLUMNNAME_CreatedBy, AD_User_ID); // FIXME: introduce AD_User_ID
		sessionPO.setLoginDate(loginDate);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public Timestamp getLoginDate()
	{
		return sessionPO.getLoginDate();
	}

	public String getRemote_Addr()
	{
		return sessionPO.getRemote_Addr();
	}

	public String getRemote_Host()
	{
		return sessionPO.getRemote_Addr();
	}

	public void setRemote_Addr(final String remoteAddr, final String remoteHost)
	{
		sessionPO.setRemote_Addr(remoteAddr);
		sessionPO.setRemote_Host(remoteHost);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public void setLoginIncorrect()
	{
		sessionPO.setIsLoginIncorrect(true);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public boolean isDestroyed()
	{
		return sessionPO.isProcessed();
	}

	public void setDestroyed()
	{
		sessionPO.setProcessed(true);
		InterfaceWrapperHelper.save(sessionPO);
	}

	/**
	 * Stores the given {@code hostKey} in the {@link I_AD_Session} this instance encapsulates and also updates the given {@code ctxToUpdate} (unless the given context is {@code null}!).
	 * 
	 * @param hostKey the key to store
	 * @param ctxToUpdate may be {@code null}; the context to update.
	 */
	public void setHostKey(final String hostKey, final Properties ctxToUpdate)
	{
		sessionPO.setHostKey(hostKey);
		InterfaceWrapperHelper.save(sessionPO);

		if (ctxToUpdate != null)
		{
			Env.setContext(ctxToUpdate, CTX_Prefix + I_AD_Session.COLUMNNAME_HostKey, hostKey); // now this should also solve gh #1314
		}
	}

	/**
	 * Gets or creates a hostkey for this session. If the hostkey was already determined it is just returned.<br>
	 * If the hostkey is created (via {@link IHostKeyBL}), it is not only returned, but also {@link #setHostKey(String, Properties)} is called.
	 * 
	 * @param ctxToUpdate; maybe be {@code null}; will be passed to {@link #setHostKey(String, Properties)} in case that method is called.
	 * 
	 * @return the hostkey which is currently mostly used for printing.
	 */
	public String getHostKey(final Properties ctxToUpdate)
	{
		final String hostKey = sessionPO.getHostKey();

		if (!HOSTKEY_NOT_YET_DETERMINED.equals(hostKey))
		{
			return hostKey; // the host key was already determined. Nothing more to do.
		}
		
		// get the session's host key
		final IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);
		final String newHostKey = hostKeyBL.getCreateHostKey();
		log.info("Setting AD_Session.HostKey={} for sessionPO={}", newHostKey, sessionPO);

		setHostKey(newHostKey, ctxToUpdate);
		return newHostKey;
	}

	public void setWebSessionId(final String webSessionId)
	{
		sessionPO.setWebSession(webSessionId);
		InterfaceWrapperHelper.save(sessionPO);
	}

	public <T> T getTransientProperty(final String name)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)transientProperties.get(name);
		return value;
	}

	public void putTransientProperty(final String name, final Object value)
	{
		transientProperties.put(name, value);
	}

	/**
	 * Export attributes from session to context.
	 *
	 * Used context prefix is {@link #CTX_Prefix}.
	 *
	 * Attributes that will be exported to context are: String with FieldLength <= 60.
	 *
	 * @return true if context was updated
	 */
	public boolean updateContext(final Properties ctx)
	{
		final int sessionId = getAD_Session_ID();
		if (sessionId <= 0)
		{
			log.warn("Cannot update context because session is not saved yet");
			return false;
		}

		if (!sessionPO.isActive())
		{
			log.debug("Cannot update context because session is not active");
			return false;
		}

		if (isDestroyed())
		{
			log.debug("Cannot update context because session is destroyed");
			return false;
		}

		//
		// If not force, update the context only if the context #AD_Session_ID is same as our session ID.
		// Even if there is no value in context, the session won't be updated.
		// Keep this logic because we are calling this method on afterSave too.
		final int ctxSessionId = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
		if (ctxSessionId > 0 && ctxSessionId != sessionId)
		{
			log.debug("Different AD_Session_ID found in context and force=false.");
		}

		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, sessionId);

		final PO po = InterfaceWrapperHelper.getStrictPO(sessionPO);
		final int cols = po.get_ColumnCount();
		for (int i = 0; i < cols; i++)
		{
			if (!isContextAttribute(i))
			{
				continue;
			}
			final String columnName = po.get_ColumnName(i);
			final String value = po.get_ValueAsString(columnName);
			Env.setContext(ctx, CTX_Prefix + columnName, value);
		}

		return true;
	}

	private boolean isContextAttribute(final int columnIndex)
	{
		if (columnIndex < 0)
		{
			return false;
		}

		final PO po = InterfaceWrapperHelper.getStrictPO(sessionPO);
		final String columnName = po.get_ColumnName(columnIndex);
		if (columnName == null)
		{
			return false;
		}

		if (CTX_IgnoredColumnNames.contains(columnName))
		{
			return false;
		}

		final POInfo poInfo = po.getPOInfo();
		final int displayType = poInfo.getColumnDisplayType(columnIndex);
		if (displayType == DisplayType.String)
		{
			return poInfo.getFieldLength(columnIndex) <= 60;
		}

		return true;
	}

}
