package de.metas.workflow.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.api.IWorkflowBL;
import de.metas.workflow.api.IWorkflowDAO;
import de.metas.workflow.model.I_C_Doc_Responsible;
import de.metas.workflow.service.IADWorkflowDAO;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import java.util.Properties;

public class WorkflowBL implements IWorkflowBL
{
	@Override
	public I_C_Doc_Responsible createDocResponsible(@NonNull final Object doc, int adOrgId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(doc);
		final String trxName = InterfaceWrapperHelper.getTrxName(doc);

		final String tableName = InterfaceWrapperHelper.getModelTableName(doc);
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = InterfaceWrapperHelper.getId(doc);

		final WFResponsible wfResponsible = getOrgWFResponsible(OrgId.ofRepoIdOrAny(adOrgId));

		I_C_Doc_Responsible docResponsible = Services.get(IWorkflowDAO.class).retrieveDocResponsible(doc);
		if (docResponsible == null)
		{
			docResponsible = InterfaceWrapperHelper.create(ctx, I_C_Doc_Responsible.class, trxName);
		}
		docResponsible.setAD_Org_ID(adOrgId);
		docResponsible.setAD_Table_ID(tableId);
		docResponsible.setRecord_ID(recordId);
		docResponsible.setAD_WF_Responsible_ID(wfResponsible.getId().getRepoId());

		final IUserDAO userDAO = Services.get(IUserDAO.class);

		if (wfResponsible.isInvoker())
		{
			final UserId userResponsibleId = Env.getLoggedUserId(ctx); // logged user
			docResponsible.setAD_User_Responsible_ID(userResponsibleId.getRepoId());
			docResponsible.setAD_WF_Responsible_Name(userDAO.retrieveUserFullname(userResponsibleId));

		}
		else if (wfResponsible.isHuman())
		{
			final UserId userResponsibleId = wfResponsible.getUserId();
			docResponsible.setAD_User_Responsible_ID(userResponsibleId.getRepoId());
			docResponsible.setAD_WF_Responsible_Name(userDAO.retrieveUserFullname(userResponsibleId));
		}
		else
		{
			docResponsible.setAD_User_Responsible_ID(-1); // no user responsible
			docResponsible.setAD_WF_Responsible_Name(wfResponsible.getName());
		}

		InterfaceWrapperHelper.save(docResponsible);

		return docResponsible;
	}

	private WFResponsible getOrgWFResponsible(@NonNull final OrgId orgId)
	{
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

		final OrgInfo orgInfo = orgDAO.getOrgInfoById(orgId);
		final WFResponsibleId wfResponsibleId = orgInfo.getWorkflowResponsibleId();
		final WFResponsible wfResponsible = wfResponsibleId != null ? workflowDAO.getWFResponsibleById(wfResponsibleId) : null;
		if (wfResponsible != null)
		{
			return wfResponsible;
		}

		//
		// Default: Invoker
		return workflowDAO.getWFResponsibleById(WFResponsibleId.Invoker);
	}
}
