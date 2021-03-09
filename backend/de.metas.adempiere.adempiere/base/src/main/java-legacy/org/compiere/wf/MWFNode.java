package org.compiere.wf;

import de.metas.util.Services;
import de.metas.workflow.service.IADWorkflowDAO;
import org.compiere.model.X_AD_WF_Node;

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
		Services.get(IADWorkflowDAO.class).onBeforeSave(this, newRecord);
		return true;
	}
}
