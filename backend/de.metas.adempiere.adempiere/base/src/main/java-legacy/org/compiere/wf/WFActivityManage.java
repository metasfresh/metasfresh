package org.compiere.wf;

import de.metas.i18n.AdMessageKey;
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
 * Manage Workflow Activity
 *
 * @author Jorg Janke
 * @version $Id: WFActivityManage.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WFActivityManage extends JavaProcess
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

	private static final AdMessageKey MSG_ActivityAssignedToYou = AdMessageKey.of("ActivityAssignedToYou");

	private boolean p_IsAbort = false;
	@Nullable
	private UserId p_AD_User_ID;
	@Nullable
	private WFResponsibleId p_AD_WF_Responsible_ID;
	private int p_AD_WF_Activity_ID;

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

		p_AD_WF_Activity_ID = getRecord_ID();
	}    //	prepare

	@Override
	protected String doIt()
	{
		final MWFActivity activity = new MWFActivity(getCtx(), p_AD_WF_Activity_ID);
		final UserId userId = getUserId();
		final String username = userDAO.retrieveUserFullname(userId);

		//
		//	Abort
		if (p_IsAbort)
		{
			final String msg = username + ": Abort";
			activity.addTextMsg(msg);
			activity.setUserId(userId);
			// 2007-06-14, matthiasO.
			// Set the 'processed'-flag when an activity is aborted; not setting this flag
			// will leave the activity in an "unmanagable" state
			activity.setProcessed(true);
			activity.changeWFStateTo(WFState.Aborted);
			return msg;
		}
		//
		// Apply WF Activity Changes
		else
		{
			//
			//	Change User
			final UserId activityUserId = activity.getUserId();
			if (p_AD_User_ID != null && !UserId.equals(activityUserId, p_AD_User_ID))
			{
				final String fromUsername = userDAO.retrieveUserFullname(activityUserId);
				final String toUsername = userDAO.retrieveUserFullname(p_AD_User_ID);
				final String msg = username + ": " + fromUsername + " -> " + toUsername;
				activity.addTextMsg(msg);
				activity.forwardTo(p_AD_User_ID, MSG_ActivityAssignedToYou, null);
			}

			//
			//	Change Responsible
			final WFResponsibleId activityResponsibleId = activity.getWFResponsibleId();
			if (p_AD_WF_Responsible_ID != null && !WFResponsibleId.equals(activityResponsibleId, p_AD_WF_Responsible_ID))
			{
				final WFResponsible from = workflowDAO.getWFResponsibleById(activityResponsibleId);
				final WFResponsible to = workflowDAO.getWFResponsibleById(p_AD_WF_Responsible_ID);
				final String msg = username + ": " + from.getName() + " -> " + to.getName();
				activity.addTextMsg(msg);
				activity.setWFResponsibleId(p_AD_WF_Responsible_ID);
			}

			activity.saveEx();

			return MSG_OK;
		}
	}
}
