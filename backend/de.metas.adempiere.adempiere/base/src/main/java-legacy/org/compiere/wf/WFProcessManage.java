package org.compiere.wf;

import de.metas.adempiere.model.I_AD_User;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.service.IADWorkflowDAO;

import javax.annotation.Nullable;

/**
 * Manage Workflow Process
 *
 * @author Jorg Janke
 * @version $Id: WFProcessManage.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WFProcessManage extends JavaProcess
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

	private boolean p_IsAbort = false;
	@Nullable
	private UserId p_AD_User_ID;
	@Nullable
	private WFResponsibleId p_AD_WF_Responsible_ID;
	private int p_AD_WF_Process_ID;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter param : getParameters())
		{
			switch (param.getParameterName())
			{
				case "IsAbort":
					p_IsAbort = StringUtils.toBoolean(param.getParameter());
					break;
				case "AD_User_ID":
					p_AD_User_ID = UserId.ofRepoIdOrNullIfSystem(param.getParameterAsInt(-1));
					break;
				case "AD_WF_Responsible_ID":
					p_AD_WF_Responsible_ID = WFResponsibleId.ofRepoIdOrNull(param.getParameterAsInt(-1));
					break;
				default:
					log.warn("Unknown Parameter: {}", param.getParameterName());
					break;
			}
		}

		p_AD_WF_Process_ID = getRecord_ID();
	}    //	prepare

	@Override
	protected String doIt()
	{
		final MWFProcess process = new MWFProcess(getCtx(), p_AD_WF_Process_ID);
		final I_AD_User user = userDAO.retrieveUserOrNull(getCtx(), getAD_User_ID());

		//
		//	Abort
		if (p_IsAbort)
		{
			final String msg = user.getName() + ": Abort";
			process.addTextMsg(msg);
			process.setAD_User_ID(getAD_User_ID());
			process.changeWFStateTo(WFState.Aborted);
			return msg;
		}
		//
		// Apply WF Process Changes
		else
		{
			//
			//	Change User
			final UserId processUserId = UserId.ofRepoId(process.getAD_User_ID());
			if (p_AD_User_ID != null && !UserId.equals(processUserId, p_AD_User_ID))
			{
				final String fromUsername = userDAO.retrieveUserFullname(processUserId);
				final String toUsername = userDAO.retrieveUserFullname(p_AD_User_ID);
				final String msg = user.getName() + ": " + fromUsername + " -> " + toUsername;
				process.addTextMsg(msg);
				process.setAD_User_ID(p_AD_User_ID.getRepoId());
			}

			//
			//	Change Responsible
			final WFResponsibleId processResponsibleId = WFResponsibleId.ofRepoId(process.getAD_WF_Responsible_ID());
			if (p_AD_WF_Responsible_ID != null && !WFResponsibleId.equals(processResponsibleId, p_AD_WF_Responsible_ID))
			{
				final WFResponsible from = workflowDAO.getWFResponsibleById(processResponsibleId);
				final WFResponsible to = workflowDAO.getWFResponsibleById(p_AD_WF_Responsible_ID);
				final String msg = user.getName() + ": " + from.getName() + " -> " + to.getName();
				process.addTextMsg(msg);
				process.setAD_WF_Responsible_ID(p_AD_WF_Responsible_ID.getRepoId());
			}

			process.saveEx();

			return MSG_OK;
		}
	}
}
