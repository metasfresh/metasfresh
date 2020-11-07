package org.compiere.wf;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.model.X_AD_Workflow;

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
