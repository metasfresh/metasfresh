package org.compiere.wf;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MColumn;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.model.X_AD_Workflow;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MWFNode extends X_AD_WF_Node
{
	private static final long serialVersionUID = 4330589837679937718L;

	public MWFNode(final Properties ctx, final int AD_WF_Node_ID, final String trxName)
	{
		super(ctx, AD_WF_Node_ID, trxName);
		if (is_new())
		{
			//	setAD_WF_Node_ID (0);
			//	setAD_Workflow_ID (0);
			//	setValue (null);
			//	setName (null);
			setAction(ACTION_WaitSleep);
			setCost(BigDecimal.ZERO);
			setDuration(0);
			setEntityType(ENTITYTYPE_UserMaintained);    // U
			setIsCentrallyMaintained(true);    // Y
			setJoinElement(JOINELEMENT_XOR);    // X
			setDurationLimit(0);
			setSplitElement(SPLITELEMENT_XOR);    // X
			setWaitingTime(0);
			setXPosition(0);
			setYPosition(0);
		}
	}

	public MWFNode(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}    //	setClientOrg

	private String getActionInfo()
	{
		final String action = getAction();
		if (ACTION_AppsProcess.equals(action))
			return "Process:AD_Process_ID=" + getAD_Process_ID();
		else if (ACTION_DocumentAction.equals(action))
			return "DocumentAction=" + getDocAction();
		else if (ACTION_AppsReport.equals(action))
			return "Report:AD_Process_ID=" + getAD_Process_ID();
		else if (ACTION_AppsTask.equals(action))
			return "Task:AD_Task_ID=" + getAD_Task_ID();
		else if (ACTION_SetVariable.equals(action))
			return "SetVariable:AD_Column_ID=" + getAD_Column_ID();
		else if (ACTION_SubWorkflow.equals(action))
			return "Workflow:AD_Workflow_ID=" + getAD_Workflow_ID();
		else if (ACTION_UserChoice.equals(action))
			return "UserChoice:AD_Column_ID=" + getAD_Column_ID();
		/*
		else if (ACTION_UserWorkbench.equals(action))
			return "Workbench:?";*/
		else if (ACTION_UserForm.equals(action))
			return "Form:AD_Form_ID=" + getAD_Form_ID();
		else if (ACTION_UserWindow.equals(action))
			return "Window:AD_Window_ID=" + getAD_Window_ID();
		else if (ACTION_WaitSleep.equals(action))
			return "Sleep:WaitTime=" + getWaitTime();
		else
			return "??";
	}

	@Override
	public String toString()
	{
		return "MWFNode[" + get_ID() + "-" + getName() + ",Action=" + getActionInfo() + "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (X_AD_Workflow.WORKFLOWTYPE_Manufacturing.equals(getAD_Workflow().getWorkflowType()))
		{
			setAction(ACTION_WaitSleep);
			return true;
		}

		final String action = getAction();
		if (action.equals(ACTION_WaitSleep))
		{
			;
		}
		else if (action.equals(ACTION_AppsProcess) || action.equals(ACTION_AppsReport))
		{
			if (getAD_Process_ID() <= 0)
			{
				throw new FillMandatoryException(COLUMNNAME_AD_Process_ID);
			}
		}
		else if (action.equals(ACTION_AppsTask))
		{
			if (getAD_Task_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Task_ID");
			}
		}
		else if (action.equals(ACTION_DocumentAction))
		{
			if (getDocAction() == null || getDocAction().length() == 0)
			{
				throw new FillMandatoryException("DocAction");
			}
		}
		else if (action.equals(ACTION_EMail))
		{
			if (getR_MailText_ID() <= 0)
			{
				throw new FillMandatoryException("R_MailText_ID");
			}
		}
		else if (action.equals(ACTION_SetVariable))
		{
			if (getAttributeValue() == null)
			{
				throw new FillMandatoryException("AttributeValue");
			}
		}
		else if (action.equals(ACTION_SubWorkflow))
		{
			if (getAD_Workflow_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Workflow_ID");
			}
		}
		else if (action.equals(ACTION_UserChoice))
		{
			if (getAD_Column_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Column_ID");
			}
		}
		else if (action.equals(ACTION_UserForm))
		{
			if (getAD_Form_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Form_ID");
			}
		}
		else if (action.equals(ACTION_UserWindow))
		{
			if (getAD_Window_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Window_ID");
			}
		}
		//		else if (action.equals(ACTION_UserWorkbench))
		//		{
		//		&& getAD_Workbench_ID() == 0)
		//			log.error("FillMandatory", Msg.getElement(getCtx(), "AD_Workbench_ID"));
		//			return false;
		//		}

		return true;
	}
}
