/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.IClientUI;

/**
 *	Session Model.
 *	Maintained in AMenu.
 *
 *  @author Jorg Janke
 *  @version $Id: MSession.java,v 1.3 2006/07/30 00:58:05 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1810182 ] Session lost after cache reset
 * 			<li>BF [ 1892156 ] MSession is not really cached
 */
public class MSession extends X_AD_Session
{
	/**
	 *
	 */
	private static final long serialVersionUID = 480745219310430126L;


	/**
	 * 	Get existing or create local session
	 *	@param ctx context
	 *	@param createNew create if not found
	 *	@return session session
	 */
	public static MSession get (Properties ctx, boolean createNew)
	{
		int AD_Session_ID = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
		MSession session = get(ctx, AD_Session_ID, false);
		// Create New
		if (session == null && createNew)
		{
			session = new MSession (ctx, ITrx.TRXNAME_None);	//	local session
			if (session.save())
			{
				session.updateContext(true); // force=true
				AD_Session_ID = session.getAD_Session_ID();
				s_sessions.put(AD_Session_ID, session);
			}
		}
		return session;
	}	//	get

	// t.schoeneberg@metas.de, task 03132: extracting code, adding public method to load/chache an AD_Session by AD_Session_ID
	public static MSession get(Properties ctx, int AD_Session_ID)
	{
		return get(ctx, AD_Session_ID, false);
	}

	private static MSession get(Properties ctx, int AD_Session_ID, boolean updateCtx)
	{
		if (AD_Session_ID <= 0)
		{
			return null;
		}

		MSession session = getFromCache(ctx, AD_Session_ID);

		// Try to load
		if (session == null)
		{
			session = new MSession(ctx, AD_Session_ID, ITrx.TRXNAME_None);
			if (session.getAD_Session_ID() != AD_Session_ID)
			{
				// no session found for given AD_Session_ID, a warning shall be already logged
				return null;
			}
			s_sessions.put(AD_Session_ID, session);
		}

		// Update context
		if (updateCtx)
		{
			session.updateContext(true); // force=true
		}


		return session;
	}
	// end of t.schoeneberg@metas.de, task 03132

	/**
	 * 	Get existing or create remote session
	 *	@param ctx context
	 *	@param Remote_Addr remote address
	 *	@param Remote_Host remote host
	 *	@param WebSession web session
	 *	@return session
	 */
	public static MSession get (Properties ctx, String Remote_Addr, String Remote_Host, String WebSession)
	{
		final int AD_Session_ID = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
		MSession session = get(ctx, AD_Session_ID, false);
		if (session == null)
		{
			session = new MSession (ctx, Remote_Addr, Remote_Host, WebSession, ITrx.TRXNAME_None);	//	remote session
			session.save();
			session.updateContext(true); // force=true
			s_sessions.put(AD_Session_ID, session);
		}
		return session;
	}	//	get

	private static final MSession getFromCache(Properties ctx, int AD_Session_ID)
	{
		if (AD_Session_ID <= 0)
		{
			return null;
		}

		final MSession session = s_sessions.get(AD_Session_ID);
		if (session == null)
		{
			return null;
		}
		if (session.getAD_Session_ID() != AD_Session_ID)
		{
			return null;
		}
		if (!Check.equals(session.get_TrxName(), ITrx.TRXNAME_None))
		{
			// Invalid transaction
			return null;
		}
		if (!Env.isSame(session.getCtx(), ctx))
		{
			// Session was created in a different context
			// we must check this because else we can corrupt other's context
			return null;
		}

		return session;
	}

	/**	Sessions					*/
	private static CCache<Integer, MSession> s_sessions = CCache.newLRUCache(I_AD_Session.Table_Name, 100, 0);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Session_ID id
	 *	@param trxName transaction
	 */
	public MSession (Properties ctx, int AD_Session_ID, String trxName)
	{
		super(ctx, AD_Session_ID, trxName);
		if (AD_Session_ID == 0)
		{
			setProcessed (false);

			//
			// Set Client Info if available - 04442
			if ((Ini.isClient())  // task 08569: only try it if we are running in any client mode
					&& Services.isAvailable(IClientUI.class))
			{
				setClient_Info(Services.get(IClientUI.class).getClientInfo());
			}
			else
			{
				setClient_Info("N/A");
			}

		}
	}	//	MSession

	/**
	 * 	Load Costructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSession(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MSession

	/**
	 * 	New (remote) Constructor
	 *	@param ctx context
	 *	@param Remote_Addr remote address
	 *	@param Remote_Host remote host
	 *	@param webSessionId web session
	 *	@param trxName transaction
	 */
	private MSession (Properties ctx, String Remote_Addr, String Remote_Host, String webSessionId, String trxName)
	{
		this (ctx, 0, trxName);
		if (Remote_Addr != null)
			setRemote_Addr(Remote_Addr);
		if (Remote_Host != null)
			setRemote_Host(Remote_Host);
		if (webSessionId != null)
			setWebSession(webSessionId);
		setDescription(Adempiere.getMainVersion() + "_" + Adempiere.getDateVersion() + " " + Adempiere.getImplementationVersion());
		setAD_Role_ID(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Role_ID));
		setLoginDate(Env.getContextAsDate(ctx, Env.CTXNAME_Date));
	}	//	MSession

	/**
	 * 	New (local) Constructor
	 *	@param ctx context
	 *	@param trxName transaction
	 */
	public MSession(Properties ctx, String trxName)
	{
		this(ctx, 0, trxName);

		final IHostIdentifier localHost = NetUtils.getLocalHost();
		setRemote_Addr(localHost.getIP());
		setRemote_Host(localHost.getHostName());

		setDescription(Adempiere.getMainVersion() + "_"
				+ Adempiere.getDateVersion() + " "
				+ Adempiere.getImplementationVersion());
		setAD_Role_ID(Env.getContextAsInt(ctx, "#AD_Role_ID"));
		setLoginDate(Env.getContextAsDate(ctx, "#Date"));

	}	// MSession

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MSession[")
			.append(getAD_Session_ID())
			.append(",AD_User_ID=").append(getAD_User_ID())
			.append(",").append(getCreated())
			.append(",Remote=").append(getRemote_Addr());
		final String removeHost = getRemote_Host();
		if (removeHost != null && removeHost.length() > 0)
			sb.append(",").append(removeHost);

		// metas: display also exported attributes
		for (int i = 0, cols = get_ColumnCount(); i < cols; i++)
		{
			if (isContextAttribute(i))
			{
				final String columnName = get_ColumnName(i);
				final String value = get_ValueAsString(columnName);
				sb.append(",").append(columnName).append("=").append(value);
			}
		}
		// metas: if is in a transaction, this info is also valuable
		if (!Check.equals(get_TrxName(), ITrx.TRXNAME_None))
		{
			sb.append(",trxName=" + get_TrxName());
		}

		sb.append(",Processed=" + isProcessed());

		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Session Logout
	 */
	public void logout()
	{
		final boolean alreadyProcessed = isProcessed();

		// Fire BeforeLogout event only if current session is not yet closes(i.e. processed)
		if (!alreadyProcessed)
		{
			ModelValidationEngine.get().fireBeforeLogout(this);
		}

		setProcessed(true);
		save();
		s_sessions.remove(getAD_Session_ID());

		// Fire AfterLogout event only if current session was closed right now
		if (!alreadyProcessed && isProcessed())
		{
			ModelValidationEngine.get().fireAfterLogout(this);
		}
	}	//	logout

	/**
	 * Set Login User.
	 * At this moment is just updating CreatedBy column
	 * @param AD_User_ID
	 */
	// metas
	public void setAD_User_ID(int AD_User_ID)
	{
		set_ValueNoCheck(COLUMNNAME_CreatedBy, AD_User_ID);
	}
	
	public static void setAD_User_ID_AndSave(final I_AD_Session session, final int AD_User_ID)
	{
		final MSession sessionPO = LegacyAdapters.convertToPO(session);
		if (sessionPO.getAD_User_ID() != AD_User_ID)
		{
			sessionPO.setAD_User_ID(AD_User_ID);
			sessionPO.saveEx();
		}
	}

	/**
	 * @return Logged in user (i.e. CreatedBy)
	 */
	// metas
	public int getAD_User_ID()
	{
		return getCreatedBy();
	}

	public static final String CTX_Prefix = "#AD_Session.";

	private static final List<String> CTX_IgnoredColumnNames = ImmutableList.of(
			COLUMNNAME_AD_Session_ID // this one will be exported particularly
			, COLUMNNAME_AD_Client_ID
			, COLUMNNAME_AD_Org_ID
			, COLUMNNAME_Created
			, COLUMNNAME_CreatedBy
			, COLUMNNAME_Updated
			, COLUMNNAME_UpdatedBy
			, COLUMNNAME_IsActive
			, COLUMNNAME_Processed
			, COLUMNNAME_Remote_Addr
			, COLUMNNAME_Remote_Host
			, COLUMNNAME_WebSession);

	/**
	 * Export attributes from session to context.
	 *
	 * Used context prefix is {@link #CTX_Prefix}.
	 *
	 * Attributes that will be exported to context are: String with FieldLength <= 60.
	 *
	 * @param force if true, update even if current AD_Session_ID from context differs from this one.
	 * @return true if context was updated
	 */
	public boolean updateContext(boolean force)
	{
		final int sessionId = getAD_Session_ID();
		if (sessionId <= 0)
		{
			log.warn("Cannot update context because session is not saved yet");
			return false;
		}

		if (!isActive())
		{
			log.debug("Cannot update context because session is not active");
			return false;
		}

		if (isProcessed())
		{
			log.debug("Cannot update context because session is processed");
			return false;
		}


		final Properties ctx = getCtx();

		//
		// If not force, update the context only if the context #AD_Session_ID is same as our session ID.
		// Even if there is no value in context, the session won't be updated.
		// Keep this logic because we are calling this method on afterSave too.
		final int ctxSessionId = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
		if (ctxSessionId != sessionId && !force)
		{
			log.debug("Different AD_Session_ID found in context and force=false. Skip updating.");
			return false;
		}

		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, sessionId);

		final int cols = get_ColumnCount();
		for (int i = 0; i < cols; i++)
		{
			if (!isContextAttribute(i))
			{
				continue;
			}
			final String columnName = get_ColumnName(i);
			final String value = get_ValueAsString(columnName);
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

		final String columnName = get_ColumnName(columnIndex);
		if(columnName == null)
		{
			return false;
		}

		if(CTX_IgnoredColumnNames.contains(columnName))
		{
			return false;
		}

		final POInfo poInfo = getPOInfo();
		final int displayType = poInfo.getColumnDisplayType(columnIndex);
		if (displayType == DisplayType.String)
		{
			return poInfo.getFieldLength(columnIndex) <= 60;
		}

		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return false;
		}

		// Update context only if it's for same session
		updateContext(false);

		return true;
	}

}	//	MSession

