/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.session;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.HttpSession;

import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventThreadCleanup;
import org.zkoss.zk.ui.event.EventThreadInit;
import org.zkoss.zk.ui.event.EventThreadResume;
import org.zkoss.zk.ui.event.EventThreadSuspend;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;

/**
 * 
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class SessionContextListener implements ExecutionInit,
		ExecutionCleanup, EventThreadInit, EventThreadResume, EventThreadCleanup, EventThreadSuspend
{
	private static final transient CLogger logger = CLogger.getCLogger(SessionContextListener.class);

	private static final String SERVLET_SESSION_ID = "servlet.sessionId";
	public static final String SESSION_CTX = "WebUISessionContext";

	/**
	 * Setup ServerContext for given execution
	 * 
	 * @param execution
	 */
	public static void setupExecutionContext(final Execution execution)
	{
		final Session session = execution.getDesktop().getSession();
		final HttpSession httpSession = (HttpSession)session.getNativeSession();
		final String sessionId = httpSession.getId();

		Properties sessionCtx = (Properties)session.getAttribute(SESSION_CTX);

		// Validate context
		if (sessionCtx != null)
		{
			final String ctxSessionId = sessionCtx.getProperty(SERVLET_SESSION_ID);
			if (ctxSessionId == null || !ctxSessionId.equals(sessionId))
			{
				sessionCtx = null;
				session.removeAttribute(SESSION_CTX);
			}
		}

		// Create new context
		if (sessionCtx == null)
		{
			sessionCtx = ServerContext.newInstance();
			sessionCtx.setProperty(SERVLET_SESSION_ID, sessionId);
			session.setAttribute(SESSION_CTX, sessionCtx);
		}

		setLocales(sessionCtx);

		final ServerContext serverContext = ServerContext.asServerContext(sessionCtx);
		ServerContext.setCurrentInstance(serverContext);
	}

	private static void cleanupExecutionContext(final Execution execution)
	{
		ServerContext.dispose();
	}

	/**
	 * @param exec
	 * @param parent
	 * 
	 * @see ExecutionInit#init(Execution, Execution)
	 */
	@Override
	public void init(Execution exec, Execution parent)
	{
		if (parent == null)
		{
			setupExecutionContext(exec);
		}
	}

	/**
	 * @param exec
	 * @param parent
	 * @param errs
	 * @see ExecutionCleanup#cleanup(Execution, Execution, List)
	 */
	@Override
	public void cleanup(Execution exec, Execution parent, @SuppressWarnings("rawtypes") List errs)
	{
		if (parent == null)
		{
			cleanupExecutionContext(exec);
		}
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadInit#prepare(Component, Event)
	 */
	@Override
	public void prepare(Component comp, Event evt)
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadInit#init(Component, Event)
	 */
	@Override
	public boolean init(Component comp, Event evt)
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}

		return true;
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadResume#beforeResume(Component, Event)
	 */
	@Override
	public void beforeResume(Component comp, Event evt)
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadResume#afterResume(Component, Event)
	 */
	@Override
	public void afterResume(Component comp, Event evt)
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadResume#abortResume(Component, Event)
	 */
	@Override
	public void abortResume(Component comp, Event evt)
	{
		// Called from servlet thread
		// nothing
	}

	/**
	 * @param comp
	 * @param evt
	 * @param errs
	 * @see EventThreadCleanup#cleanup(Component, Event, List)
	 */
	@Override
	public void cleanup(Component comp, Event evt, @SuppressWarnings("rawtypes") List errs) throws Exception
	{
		final Execution execution = Executions.getCurrent();
		cleanupExecutionContext(execution);
	}

	/**
	 * @param comp
	 * @param evt
	 * @see EventThreadCleanup#complete(Component, Event)
	 */
	@Override
	public void complete(Component comp, Event evt) throws Exception
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}
	}

	@Override
	public void beforeSuspend(Component comp, Event evt, Object obj) throws Exception
	{
		// nothing
	}

	@Override
	public void afterSuspend(Component comp, Event evt) throws Exception
	{
		if (!isContextValid())
		{
			final Execution execution = Executions.getCurrent();
			setupExecutionContext(execution);
		}
	}

	/**
	 * Configure context's language and set's ZK's locale (thread local). If there is not language setting in context, base language will be used.
	 * 
	 * @param ctx context
	 */
	// metas: 03362
	private static void setLocales(final Properties ctx)
	{
		// Make sure Language is set in context
		if (Env.getLanguage(ctx) == null)
		{
			Env.setContext(ctx, Env.CTXNAME_AD_Language, Language.getBaseAD_Language());
		}
		Locales.setThreadLocal(Env.getLanguage(ctx).getLocale());
	}

	public static boolean isContextValid()
	{
		final Execution exec = Executions.getCurrent();
		final Properties serverCtx = ServerContext.getCurrentInstance();
		if (serverCtx == null)
		{
			return false;
		}

		if (serverCtx.isEmpty())
		{
			// empty server context is not a valid context
			return false;
		}

		final Session session = exec.getDesktop().getSession();
		final HttpSession httpSession = (HttpSession)session.getNativeSession();

		// Validate Context
		final String ctxSessionId = serverCtx.getProperty(SERVLET_SESSION_ID);
		final String sessionId = httpSession.getId();
		if (ctxSessionId == null || !ctxSessionId.equals(sessionId))
		{
			logger.log(Level.WARNING, SERVLET_SESSION_ID + " differs: expected={0} but it was {1};\nserverContext={2}", new Object[] { sessionId, ctxSessionId, serverCtx });
			return false;
		}

		final Properties sessionCtx = (Properties)session.getAttribute(SESSION_CTX);
		if (sessionCtx != null)
		{
			if (Env.getAD_Client_ID(sessionCtx) != Env.getAD_Client_ID(serverCtx))
			{
				logger.log(Level.WARNING, "AD_Client_ID differs;\nserverContext=" + serverCtx + "\nsessionCtx=" + sessionCtx);
				return false;
			}
			if (Env.getAD_User_ID(sessionCtx) != Env.getAD_User_ID(serverCtx))
			{
				logger.log(Level.WARNING, "AD_User_ID differs;\nserverContext=" + serverCtx + "\nsessionCtx=" + sessionCtx);
				return false;
			}
			if (Env.getAD_Role_ID(sessionCtx) != Env.getAD_Role_ID(serverCtx))
			{
				logger.log(Level.WARNING, "AD_Role_ID differs;\nserverContext=" + serverCtx + "\nsessionCtx=" + sessionCtx);
				return false;
			}
		}

		return true;
	}
}
