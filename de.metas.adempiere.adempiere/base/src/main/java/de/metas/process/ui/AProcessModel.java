/**
 *
 */
package de.metas.process.ui;

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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.Icon;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Process;
import org.compiere.model.MTreeNode;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessPreconditionChecker;

/**
 * @author tsa
 *
 */
public class AProcessModel
{
	private static final String ACTION_Name = "Process";

	private final Logger logger = LogManager.getLogger(getClass());

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
		Check.assumeNotNull(role, "No role found for {}", ctx);

		final int AD_Table_ID = gridTab.getAD_Table_ID();
		final List<I_AD_Process> list = Services.get(IADProcessDAO.class).retrieveProcessesForTable(ctx, AD_Table_ID);

		for (final Iterator<I_AD_Process> it = list.iterator(); it.hasNext();)
		{
			final I_AD_Process process = it.next();

			// Filter out processes on which we don't have access
			final Boolean accessRW = role.checkProcessAccess(process.getAD_Process_ID());
			if (accessRW == null)
			{
				logger.debug("Removing process {} because user has no access at all to it", process);
				it.remove();
				continue;
			}
			else if (!accessRW)
			{
				logger.debug("Removing process {} because user has only readonly access to it", process);
				it.remove();
				continue;
			}

			// Filter out processes which have preconditions which don't apply
			if (!isPreconditionApplicable(process, gridTab))
			{
				logger.debug("Removing process {} because preconditions were not met", process);
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

	public Icon getIcon(final I_AD_Process process)
	{
		final String iconName;
		if (process.getAD_Form_ID() > 0)
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_WINDOW);
		}
		else if (process.getAD_Workflow_ID() > 0)
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_WORKFLOW);
		}
		else if (process.isReport())
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_REPORT);
		}
		else
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_PROCESS);
		}

		return Images.getImageIcon2(iconName);
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

	@VisibleForTesting
	boolean isPreconditionApplicable(final I_AD_Process process, final GridTab gridTab)
	{
		return ProcessPreconditionChecker.newInstance()
				.setProcess(process)
				.setPreconditionsContext(gridTab)
				.checkApplies();
	}
}
