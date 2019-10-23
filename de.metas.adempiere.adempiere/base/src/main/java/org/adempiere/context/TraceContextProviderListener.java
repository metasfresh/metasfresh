package org.adempiere.context;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.compiere.util.Env;

import com.google.common.collect.MapMaker;

public class TraceContextProviderListener implements IContextProviderListener
{
	private static final String CTXNAME_ThreadName = Thread.class.getName() + ".Name";
	private static final String CTXNAME_ThreadId = Thread.class.getName() + ".Id";

	private final Set<Properties> activeContexts = Collections.newSetFromMap(
			new MapMaker()
					.weakKeys()
					.<Properties, Boolean> makeMap()
			);

	@Override
	public void onContextCreated(final Properties ctx)
	{
		activeContexts.add(ctx);
	}

	@Override
	public void onChildContextCreated(final Properties ctx, final Properties childCtx)
	{
		activeContexts.add(childCtx);
	}

	@Override
	public void onContextCheckOut(final Properties ctx)
	{
		final Thread thread = Thread.currentThread();
		ctx.setProperty(CTXNAME_ThreadName, thread.getName());
		ctx.setProperty(CTXNAME_ThreadId, String.valueOf(thread.getId()));
	}

	@Override
	public void onContextCheckIn(final Properties ctxNew, final Properties ctxOld)
	{
		activeContexts.remove(ctxOld);
		activeContexts.add(ctxNew);
	}

	public String[] getActiveContextsInfo()
	{
		final List<Properties> activeContextsCopy = new ArrayList<>(activeContexts);
		final int count = activeContextsCopy.size();
		final List<String> activeContextsInfo = new ArrayList<>(count);
		int index = 1;
		for (final Properties ctx : activeContextsCopy)
		{
			if (ctx == null)
			{
				continue;
			}
			final String ctxInfo = index + "/" + count + ". " + toInfoString(ctx);
			activeContextsInfo.add(ctxInfo);

			index++;
		}

		return activeContextsInfo.toArray(new String[activeContextsInfo.size()]);
	}

	private String toInfoString(final Properties ctx)
	{
		final String threadName = (String)ctx.get(CTXNAME_ThreadName);
		final String threadId = (String)ctx.get(CTXNAME_ThreadId);
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		final int adRoleId = Env.getAD_Role_ID(ctx);
		final int adSessionId = Env.getAD_Session_ID(ctx);

		return "Thread=" + threadName + "(" + threadId + ")"
				//
				+ "\n"
				+ ", Client/Org=" + adClientId + "/" + adOrgId
				+ ", User/Role=" + adUserId + "/" + adRoleId
				+ ", SessionId=" + adSessionId
				//
				+ "\n"
				+ ", id=" + System.identityHashCode(ctx)
				+ ", " + ctx.getClass()
				//
				+ "\n"
				+ ", " + ctx.toString();
	}
}
