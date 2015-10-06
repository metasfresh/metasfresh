/**
 * 
 */
package org.adempiere.apps.toolbar;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * @author tsa
 * 
 */
public class AProcessModel
{
	private static final String ACTION_Name = "Process";

	private final CLogger logger = CLogger.getCLogger(getClass());

	public String getActionName()
	{
		return ACTION_Name;
	}

	public List<I_AD_Process> fetchProcesses(final Properties ctx, final GridTab gridTab)
	{
		if (gridTab == null)
		{
			return Collections.emptyList();
		}

		final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
		Check.assumeNotNull(role, "No role found for {0}", ctx);

		final int AD_Table_ID = gridTab.getAD_Table_ID();
		final List<I_AD_Process> list = Services.get(IADProcessDAO.class).retrieveProcessesForTable(ctx, AD_Table_ID);

		for (final Iterator<I_AD_Process> it = list.iterator(); it.hasNext();)
		{
			final I_AD_Process process = it.next();

			// Filter out processes on which we don't have access
			final Boolean accessRW = role.checkProcessAccess(process.getAD_Process_ID());
			if (accessRW == null)
			{
				logger.log(Level.FINE, "Removing process {0} because user has no access at all to it", process);
				it.remove();
				continue;
			}
			else if (!accessRW)
			{
				logger.log(Level.FINE, "Removing process {0} because user has only readonly access to it", process);
				it.remove();
				continue;
			}

			// Filter out processes which have preconditions which don't apply
			if (!isPreconditionApplicable(process, gridTab))
			{
				logger.log(Level.FINE, "Removing process {0} because preconditions were not met", process);
				it.remove();
				continue;
			}
		}

		return list;
	}

	/**
	 * Gets display name to be used in Gear
	 * 
	 * @param process
	 * @return process display name
	 */
	public String getDisplayName(final I_AD_Process process)
	{
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);
		String name = processTrl.getName();

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			name += "/" + process.getValue();
		}

		return name;
	}

	/**
	 * Gets description/tooltip to be used in Gear
	 * 
	 * @param process
	 * @return description/tooltip
	 */
	public String getDescription(final I_AD_Process process)
	{
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);
		String description = processTrl.getDescription();

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if (description == null)
			{
				description = "";
			}
			else
			{
				description += "\n - ";
			}
			description += "Classname:" + process.getClassname() + ", ID=" + process.getAD_Process_ID();
		}

		return description;
	}

	boolean isPreconditionApplicable(final I_AD_Process process, final GridTab gridTab)
	{
		final String classname = getClassnameOrNull(process);
		if (classname == null)
		{
			// no classname => always display the process (there no one to ask)
			return true;
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = getClass().getClassLoader();
		}

		final Class<?> processClass;
		try
		{
			processClass = classLoader.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			logger.log(Level.SEVERE, "Cannot load class: " + classname, e);
			return false;
		}

		if (!ISvrProcessPrecondition.class.isAssignableFrom(processClass))
		{
			return true;
		}

		final ISvrProcessPrecondition svrProcessPrecondit;
		try
		{
			svrProcessPrecondit = processClass.asSubclass(ISvrProcessPrecondition.class).newInstance();
			return svrProcessPrecondit.isPreconditionApplicable(gridTab);
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}
	}

	private final String getClassnameOrNull(final I_AD_Process process)
	{
		//
		// First try: Check process classname
		if (!Check.isEmpty(process.getClassname(), true))
		{
			return process.getClassname();
		}

		//
		// Second try: form classname (05089)
		final I_AD_Form form = process.getAD_Form();
		if (form != null && !Check.isEmpty(form.getClassname(), true))
		{
			return form.getClassname();
		}

		return null;
	}

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 * 
	 * @param adTableId
	 * @param adProcessId
	 * @deprecated Please use {@link IADProcessDAO#registerTableProcess(int, int)}
	 */
	@Deprecated
	public static void registerProcess(final int adTableId, final int adProcessId)
	{
		Services.get(IADProcessDAO.class).registerTableProcess(adTableId, adProcessId);
	}
}
